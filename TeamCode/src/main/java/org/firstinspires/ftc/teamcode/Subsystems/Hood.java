package org.firstinspires.ftc.teamcode.Subsystems;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Util.Toggle;

public class Hood {
    private Servo hoodservo;
    Telemetry telemetry;

    double hoodAngle = 0.5;
    double hoodOffset = 0;


    public Hood (HardwareMap hardwaremap, Telemetry telemetry){
        hoodservo = hardwaremap.get(Servo.class, "hoodservo");
        this.telemetry=telemetry;
    }
    public void setHoodAngle(double hoodangle){
        hoodangle = Math.max(0, Math.min(1, hoodangle)); // Clamps hood angle to [0,1]

        this.hoodAngle =hoodangle;
        hoodservo.setPosition(hoodangle);
    }


    private Toggle addOffsetToggle = new Toggle();
    private Toggle removeOffsetToggle = new Toggle();

    public void update (Gamepad gamepad, double hoodPos, double rpm){
        double rpmCalc=rpm*-1;

        double velocityCompensation = 0.0000000015739*rpmCalc*rpmCalc*rpmCalc-0.0000193865*rpmCalc*rpmCalc+0.08003*rpmCalc-110.72053;

        setHoodAngle(0.725+hoodOffset);

        if (addOffsetToggle.runToggle(gamepad.dpad_up)){
            hoodOffset += 0.025;
        }

        if (removeOffsetToggle.runToggle(gamepad.dpad_down)){
            hoodOffset -= 0.025;
        }

        if (gamepad.x) {
            hoodOffset = 0;
        }

        telemetry.addLine("Hood position: " + (hoodPos + hoodOffset));
    }

    public class farZone implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            setHoodAngle(0.2);
            return false;
        }
    }
    public Action farZone() {return new Hood.farZone();}
}
