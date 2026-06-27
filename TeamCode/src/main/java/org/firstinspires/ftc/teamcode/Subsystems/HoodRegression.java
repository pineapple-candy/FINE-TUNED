package org.firstinspires.ftc.teamcode.Subsystems;

public class HoodRegression {
    public double Hoodpos(double distance, boolean far) {
        if (far) {
            return  200000000*Math.pow(distance,-4.111);

        } else {
            return 0.5;
        }
    }
}

