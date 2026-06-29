package org.firstinspires.ftc.teamcode.Util;

public class Timer {

    private double startTime;
    private double endTime;

    public Timer() {

    }

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public double getTime() {
        endTime = System.nanoTime();
        double elapsedTime = (endTime-startTime)/1_000_000_000;

        return elapsedTime;
    }
}
