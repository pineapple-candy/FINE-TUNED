package org.firstinspires.ftc.teamcode.Subsystems;

public class HoodRegression {
    public double Hoodpos(double distance, boolean far) {
        if (far) {
            //return -0.0002322635*(Math.pow(distance,3))+0.1003701*(Math.pow(distance,2))+-14.42165*distance+689.14512;
//            return  200000000*Math.pow(distance,-4.111);
            return 0;
        } else {
            return 0;
        }
    }
}

