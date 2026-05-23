package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drivebase {
    private DcMotor LF;
    private DcMotor RF;
    private DcMotor LB;
    private DcMotor RB;

    private static double SPEED_MULTIPLIER = 0.7;

    public Drivebase(HardwareMap hardwareMap) {
        //Initialise the motors
        this.LF = hardwareMap.get(DcMotor.class, LF_DRIVE.getMotorName());
        this.RF = hardwareMap.get(DcMotor.class, RF_DRIVE.getMotorName());
        this.LB = hardwareMap.get(DcMotor.class, LB_DRIVE.getMotorName());
        this.RB = hardwareMap.get(DcMotor.class, RB_DRIVE.getMotorName());

        //Set direction
        this.LF.setDirection(LF_DRIVE.getDirection());
        this.LB.setDirection(LB_DRIVE.getDirection());
        this.RF.setDirection(RF_DRIVE.getDirection());
        this.RB.setDirection(RB_DRIVE.getDirection());

        this.LF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.LB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.RF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.RB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setMotorSpeeds(double LFSpeed, double RFSpeed, double LBSpeed, double RBSpeed) {
        // Method for control of individual motors
        LF.setPower(LFSpeed);
        RF.setPower(RFSpeed);
        LB.setPower(LBSpeed);
        RB.setPower(RBSpeed);
    }

    public void powerMotors(double power) {
        LF.setPower(power);
        RF.setPower(power);
        LB.setPower(power);
        RB.setPower(power);
    }

    public void resetEncoder(DcMotor motor) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void drive(double stickX, double stickY, double rotation) {
        double y = stickY; // Remember, Y stick value is reversed
        double x = stickX * 1.1; // Counteract imperfect strafing
        double rx = rotation;

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        this.LF.setPower(frontLeftPower*SPEED_MULTIPLIER);
        this.LB.setPower(backLeftPower*SPEED_MULTIPLIER);
        this.RF.setPower(frontRightPower*SPEED_MULTIPLIER);
        this.RB.setPower(backRightPower*SPEED_MULTIPLIER);
    }

    public void update(Gamepad gamepad1, Gamepad gamepad2) {
        // === DRIVER 1 INPUTS ===
        double stickX1 = gamepad1.left_stick_x;
        double stickY1 = -gamepad1.left_stick_y;
        double rotation1 = gamepad1.right_stick_x;

        // === DRIVER 2 INPUTS (always slow) ===
        double stickX2 = gamepad2.left_stick_x;   // scale for slower control
        double stickY2 = -gamepad2.left_stick_y;
        double rotation2 = gamepad2.right_stick_x;

        // === COMBINE INPUTS ===
        double stickX = stickX1 + stickX2;
        double stickY = stickY1 + stickY2;
        double rotation = rotation1 + rotation2;

        // Normalize if total > 1 to prevent clipping
        double max = Math.max(1.0, Math.abs(stickX) + Math.abs(stickY) + Math.abs(rotation));
        stickX /= max;
        stickY /= max;
        rotation /= max;

        drive(stickX, stickY, rotation);
    }
}