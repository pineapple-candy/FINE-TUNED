package org.firstinspires.ftc.teamcode.Util;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Constants {

    // Miscellaneous

    public static final double GAMEPAD_THRESHOLD = 0.05;
    public static final int RED_SHOOT_ID = 24;
    public static final int BLUE_SHOOT_ID = 20;

    // Mecanum Drive
    public static final MotorProfile LF_DRIVE = new MotorProfile.MotorProfileBuilder()
            .setMotorName("LF")
            .setReversed()
            .build();

    public static final MotorProfile LB_DRIVE = new MotorProfile.MotorProfileBuilder()
            .setMotorName("LB")
            .setReversed()
            .build();

    public static final MotorProfile RF_DRIVE = new MotorProfile.MotorProfileBuilder()
            .setMotorName("RF")
//            .setReversed()
            .build();

    public static final MotorProfile RB_DRIVE = new MotorProfile.MotorProfileBuilder()
            .setMotorName("RB")
//            .setReversed()
            .build();

}