package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Shooter {

    private DcMotor S1;
    private DcMotor S2;

    public Shooter(HardwareMap hardwareMap) {
        S1 = hardwareMap.get(DcMotor.class,SHOOTER1.getMotorName());
        S2 = hardwareMap.get(DcMotor.class,SHOOTER2.getMotorName());

        S1.setDirection(SHOOTER1.getDirection());
        S2.setDirection(SHOOTER2.getDirection());
    }

    public void shoot(double power) {
        S1.setPower(power);
        S2.setPower(power);
    }

    public void update(Gamepad gamepad) {
        if (gamepad.left_trigger > 0.05) {
            shoot(1);
        } else if (gamepad.x) {
            shoot(-1);
        } else {
            shoot(0);
        }
    }
}
