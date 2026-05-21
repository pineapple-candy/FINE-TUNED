package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Transfer {
    private DcMotor intake1;
    private DcMotor intake2;
    public Transfer(HardwareMap hardwareMap) {
        intake1 = hardwareMap.get(DcMotor.class, INTAKE1.getMotorName());
        intake1.setDirection(INTAKE1.getDirection());
        intake1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake2 = hardwareMap.get(DcMotor.class, INTAKE2.getMotorName());
        intake2.setDirection(INTAKE2.getDirection());
        intake2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void update(Gamepad gamepad) {
        if (gamepad.left_bumper) {
            intake1.setPower(1);
            intake2.setPower(1);
        } else if (gamepad.right_bumper) {
            intake1.setPower(-1);
            intake2.setPower(-1);
        } else {
            intake1.setPower(0);
            intake2.setPower(0);
        }
    }
}