package org.firstinspires.ftc.teamcode.Util;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.*;

import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class MotorProfile {
    private DcMotorSimple.Direction direction;
    private String motorName;
    private double encoderTicksPerRevolution;

    private MotorProfile(MotorProfileBuilder Builder) {
        this.direction = Builder.direction;
        this.motorName = Builder.motorName;
        this.encoderTicksPerRevolution = Builder.encoderTicksPerRevolution;
    }

    public DcMotorSimple.Direction getDirection() {
        return direction;
    }

    public String getMotorName() {
        return motorName;
    }

    public double getEncoderTicksPerRevolution() {
        return encoderTicksPerRevolution;
    }

    public static class MotorProfileBuilder {
        private DcMotorSimple.Direction direction = FORWARD;
        private String motorName = "NA";
        private double encoderTicksPerRevolution = 0;

        public MotorProfileBuilder setReversed() {
            this.direction = REVERSE;
            return this;
        }

        public MotorProfileBuilder setMotorName(String motorName) {
            this.motorName = motorName;
            return this;
        }

        public MotorProfileBuilder setEncoderTicksPerRevolution(double ticks) {
            this.encoderTicksPerRevolution = ticks;
            return this;
        }

        public MotorProfile build() {
            return new MotorProfile(this);
        }
    }
}