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
        waitForStart();

        while (opModeIsActive() && !isStopRequested())  {
            double rpm = shooter.getRPM();
            drivebase.updateSeb(gamepad1,gamepad2);
            calcs.update(gamepad1, rpm);
            double turretpos = calcs.returnTurret();
            double flywheelPower = calcs.returnPower(); // FLYWHEEL POWER calculated here in this class (w/ PIDs) -> fed as an argument into shooter
            double hoodPos = calcs.returnHood(); // Hood positions are calculated in another class -> fed as an ARGUMENT into hood
            transfer.update(gamepad1);
            turret.update(gamepad2, turretpos);
            hood.update(gamepad2, hoodPos, rpm);
            shooter.update(gamepad1, flywheelPower);
            telemetry.addData("Flywheel RPM", shooter.getRPM());
            telemetry.update();
        }

    }
}