package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Hood {
    private Servo hoodservo;
    private boolean currentlyUp=false;
    private boolean currentlyDown=false;
    double hoodangle=0.5;
    public Hood (HardwareMap hardwaremap){
    hoodservo = hardwaremap.get(Servo.class, "hoodservo");
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
    public void update (Gamepad gamepad){
        if (gamepad.dpad_up==true&&!currentlyUp){
            SetHoodAngle(hoodangle+0.1);

        }
        else if (gamepad.dpad_down==true&&!currentlyDown){
            SetHoodAngle(hoodangle-0.1);

        }
        currentlyUp=gamepad.dpad_up;
        currentlyDown=gamepad.dpad_down;
    }

}
