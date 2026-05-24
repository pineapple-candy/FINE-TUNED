package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Util.Constants;
import org.firstinspires.ftc.teamcode.Util.Toggle;

@TeleOp(name="Testing Encoders")
public class EncoderTesting extends LinearOpMode {

    DcMotor RF;

    @Override
    public void runOpMode() {
        RF = hardwareMap.get(DcMotor.class, Constants.RF_DRIVE.getMotorName());

        waitForStart();

        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (opModeIsActive() && !isStopRequested()) {

            if (gamepad1.left_bumper) {
                RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                RF.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            telemetry.addLine("POSITION: " + RF.getCurrentPosition());
            telemetry.update();
        }
    }
}
