package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Transfer {
    private DcMotor intake1;
    private DcMotor intake2;
    private Servo stopper;

    private static final double STOP_DOWN = 0.123;
    private static final double STOP_UP = 0.4;

    public Transfer(HardwareMap hardwareMap) {
        intake1 = hardwareMap.get(DcMotor.class, INTAKE1.getMotorName());
        intake1.setDirection(INTAKE1.getDirection());
        intake1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake2 = hardwareMap.get(DcMotor.class, INTAKE2.getMotorName());
        intake2.setDirection(INTAKE2.getDirection());
        intake2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        stopper = hardwareMap.get(Servo.class, STOPPER.getServoName());
        stopper.setPosition(STOP_DOWN);
    }

    public void update(Gamepad gamepad) {
        if (gamepad.right_bumper) { // intake
            intake1.setPower(1);
            intake2.setPower(1);
        } else if (gamepad.left_bumper) { // outtake
            intake1.setPower(-1);
            intake2.setPower(-1);
        } else {
            intake1.setPower(0);
            intake2.setPower(0);
        }

        if (gamepad.left_trigger > 0.05) {
            stopper.setPosition(STOP_UP);
        }
        if (gamepad.right_trigger > 0.05) {
            stopper.setPosition(STOP_DOWN);
        }
    }
}