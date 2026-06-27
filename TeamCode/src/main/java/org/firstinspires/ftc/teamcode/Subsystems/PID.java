package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PID {
    private double kp; // Proportional gain
    private double ki; // Integral gain
    private double kd; // Derivative gain
    private double target; // Setpoint
    private double integral; // Integral term accumulation
    private double previousError; // Previous error value

    public PID(double kp, double ki, double kd) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.target = 0.0;
        this.integral = 0.0;
        this.previousError = 0.0;
    }

    public void setTarget(double target) {
        this.target = target;
        this.integral = 0.0;
        this.previousError = 0.0;
    }

    public double calculateOutput(double currentValue, double deltaTime) {
        double error = target - currentValue;

        // Proportional term
        double proportionalTerm = kp * error;

        // Integral term
        integral += error * deltaTime;
        double integralTerm = ki * integral;

        // Derivative term
        double derivativeTerm = kd * ((error - previousError) / deltaTime);

        // Calculate the output value
        double output =  + proportionalTerm + integralTerm + derivativeTerm;

        // Update previous error value
        previousError = error;

        return output;
    }
}

