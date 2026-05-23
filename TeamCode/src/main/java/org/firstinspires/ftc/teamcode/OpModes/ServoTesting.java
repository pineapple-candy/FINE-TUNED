package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Util.Constants;
import org.firstinspires.ftc.teamcode.Util.Toggle;

@TeleOp(name="Testing Servos")
public class ServoTesting extends LinearOpMode {

    Servo stopper;
    Toggle toggleLeft;
    Toggle toggleRight;

    @Override
    public void runOpMode() {
        stopper = hardwareMap.get(Servo.class, Constants.STOPPER.getServoName());
        toggleLeft = new Toggle();
        toggleRight = new Toggle();
        double position = 0.5;

        waitForStart();
        stopper.setPosition(position);

        while (opModeIsActive() && !isStopRequested()) {
            if (toggleLeft.runToggle(gamepad1.left_bumper)) {
                position += 0.03;
                stopper.setPosition(position);
            }
            if (toggleRight.runToggle(gamepad1.right_bumper)) {
                position -= 0.03;
                stopper.setPosition(position);
            }

            telemetry.addLine("POSITION: " + position);
            telemetry.update();
        }
    }
}
