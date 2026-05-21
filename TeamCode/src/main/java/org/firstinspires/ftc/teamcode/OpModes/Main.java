package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Drivebase;
import org.firstinspires.ftc.teamcode.Util.Constants;

@TeleOp(name="Main")
public class Main extends LinearOpMode {

    private Drivebase drivebase;

    @Override
    public void runOpMode() {
        drivebase.initDrivebase(hardwareMap);
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            drivebase.drive(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
        }

    }
}