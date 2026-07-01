package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Drivebase;
import org.firstinspires.ftc.teamcode.Subsystems.Hood;
import org.firstinspires.ftc.teamcode.Subsystems.HoodandFlywheelCompensation;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
import org.firstinspires.ftc.teamcode.Util.Constants;
import org.firstinspires.ftc.teamcode.Util.StaticVariables;

@TeleOp(name="MainSeb")
public class MainSeb extends LinearOpMode {

    private Drivebase drivebase;
    private Transfer transfer;
    private Shooter shooter;
    private Turret turret;
    private Hood hood;
    private HoodandFlywheelCompensation calcs;
    MecanumDrive drive;

    @Override
    public void runOpMode() {
        drivebase = new Drivebase(hardwareMap, telemetry);
        transfer = new Transfer(hardwareMap, telemetry);
        shooter = new Shooter(hardwareMap, telemetry);
        turret = new Turret(hardwareMap, telemetry);
        hood = new Hood(hardwareMap, telemetry);
        drive = new MecanumDrive(hardwareMap, new Pose2d(0,0,0));
        calcs = new HoodandFlywheelCompensation(hardwareMap, telemetry, drive);

        double sideID = StaticVariables.getLastId();
        while (opModeInInit() && !isStarted()) {
            if (sideID == Constants.RED_SHOOT_ID) {
                telemetry.addLine("CURRENT SIDE: RED");
            } else if (sideID == Constants.BLUE_SHOOT_ID) {
                telemetry.addLine("CURRENT SIDE: BLUE");
            } else { // No last id found
                telemetry.addLine("CURRENT SIDE: No last side found! - defaul to Blue");
            }

            if (gamepad1.a) {
                sideID = Constants.RED_SHOOT_ID;
            }
            if (gamepad1.b) {
                sideID = Constants.BLUE_SHOOT_ID;
            }


            telemetry.addLine("To manually change, press A for Red, and B for Blue! (gamepad1)");
            telemetry.update();
        }

        waitForStart();

        while (opModeIsActive() && !isStopRequested())  {
            double rpm = shooter.getRPM();
            boolean bangbang = shooter.ReturnBangbang();
            drivebase.updateSeb(gamepad1,gamepad2);
            calcs.update(gamepad1, rpm, bangbang);
            double turretpos = calcs.returnTurret();
            double flywheelPower = calcs.returnPower(); // FLYWHEEL POWER calculated here in this class (w/ PIDs) -> fed as an argument into shooter
            double hoodPos = calcs.returnHood(); // Hood positions are calculated in another class -> fed as an ARGUMENT into hood
            transfer.update(gamepad1, gamepad2);
            turret.update(gamepad2, turretpos);
            hood.update(gamepad2, hoodPos, rpm);
            shooter.update(gamepad1, flywheelPower);
            telemetry.addData("Flywheel RPM", shooter.getRPM());
            telemetry.update();
        }

    }
}