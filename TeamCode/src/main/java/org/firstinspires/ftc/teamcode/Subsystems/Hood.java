package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Hood {
    private Servo hoodservo;
    private boolean currentlyUp=false;
    private boolean currentlyDown=true;
    Telemetry telemetry;
    double hoodangle=0.5;
    double hoodOffset=0;
    boolean dPadUp=true;
    boolean dPadDown;
    public Hood (HardwareMap hardwaremap, Telemetry telemetry){
        hoodservo = hardwaremap.get(Servo.class, "hoodservo");
        this.telemetry=telemetry;
    }
    public void SetHoodAngle (double hoodangle){
        if (hoodangle>1){
            hoodangle=1;
        }
        if (hoodangle<0){
            hoodangle=0;
        }
        this.hoodangle=hoodangle;
        hoodservo.setPosition(hoodangle);
    }
    public void update (Gamepad gamepad, double hoodpos, double rpm){
        double rpmcalc=rpm*-1;
        double velocityCompensation = 0.0000000015739*rpmcalc*rpmcalc*rpmcalc-0.0000193865*rpmcalc*rpmcalc+0.08003*rpmcalc-110.72053;
        SetHoodAngle(hoodpos+hoodOffset);

        if (gamepad.dpad_up&&!currentlyUp){
            hoodOffset=hoodOffset+0.025;
        } if (gamepad.dpad_down&&!currentlyDown){
            hoodOffset=hoodOffset-0.025;
        }

        currentlyUp=gamepad.dpad_left;
        currentlyDown=gamepad.dpad_right;
        telemetry.addData("hoodposition",hoodpos+hoodOffset);
    }

}
