package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Drivebase;
import org.firstinspires.ftc.teamcode.Subsystems.Hood;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;

@TeleOp(name="Main")
public class Main extends LinearOpMode {

    private Drivebase drivebase;
    private Transfer transfer;
    private Shooter shooter;
    private Turret turret;
    private Hood hood;

    @Override
    public void runOpMode() {
        drivebase = new Drivebase(hardwareMap);
        transfer = new Transfer(hardwareMap);
        shooter = new Shooter(hardwareMap);
        turret = new Turret(hardwareMap, telemetry);
        hood = new Hood(hardwareMap);
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            drivebase.update(gamepad1,gamepad2);
            shooter.update(gamepad1);
            transfer.update(gamepad1);
            turret.update(gamepad1);
            hood.update(gamepad1);
            telemetry.addData("Flywheel RPM", -shooter.getRPM());
            telemetry.update();
        }

    }
}