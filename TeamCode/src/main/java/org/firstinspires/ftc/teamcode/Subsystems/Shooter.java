package org.firstinspires.ftc.teamcode.Subsystems;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.teamcode.Util.Constants.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Shooter {
    Telemetry telemetry;
    private DcMotor S1;
    private DcMotor S2;
    private DcMotorEx S1encoder;
    public boolean shooting=false;
    boolean alreadypressed;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        S1 = hardwareMap.get(DcMotorEx.class,SHOOTER1.getMotorName());
        S2 = hardwareMap.get(DcMotor.class,SHOOTER2.getMotorName());
        S1encoder = hardwareMap.get(DcMotorEx.class,SHOOTER1.getMotorName());

        S1.setDirection(SHOOTER1.getDirection());
        S2.setDirection(SHOOTER2.getDirection());
    }

    public double getRPM() {
        double ticksPerSecond = S1encoder.getVelocity();
        return (ticksPerSecond / 28.0) * 60.0;
    }

    public void shoot(double power) {
        S1.setPower(power);
        S2.setPower(power);
    }

    public void update(Gamepad gamepad, double flywheelPower) {
        if (shooting) {
            shoot(flywheelPower);
        } else {
            shoot(0);
        }

        if (gamepad.b && !shooting && !alreadypressed){
            shooting=true;
        } else if (gamepad.b && !alreadypressed){
            shooting=false;
        }

        alreadypressed=gamepad.b;
        telemetry.addData("flywheelpower",flywheelPower);
   }
}
