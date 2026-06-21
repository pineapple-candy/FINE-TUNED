package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.teamcode.Util.Constants.*;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drivebase {
    private DcMotor leftFront;
    private DcMotor rightFront;
    private DcMotor leftBack;
    private DcMotor rightBack;

    private static double MAX_SPEED = 0.7;

    public Drivebase(HardwareMap hardwareMap) {
        //Initialise the motors
        this.leftFront = hardwareMap.get(DcMotor.class, LF_DRIVE.getMotorName());
        this.rightFront = hardwareMap.get(DcMotor.class, RF_DRIVE.getMotorName());
        this.leftBack = hardwareMap.get(DcMotor.class, LB_DRIVE.getMotorName());
        this.rightBack = hardwareMap.get(DcMotor.class, RB_DRIVE.getMotorName());

        //Set direction
        this.leftFront.setDirection(LF_DRIVE.getDirection());
        this.leftBack.setDirection(LB_DRIVE.getDirection());
        this.rightFront.setDirection(RF_DRIVE.getDirection());
        this.rightBack.setDirection(RB_DRIVE.getDirection());

        this.leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setMotorSpeeds(double LFSpeed, double RFSpeed, double LBSpeed, double RBSpeed) {
        // Method for control of individual motors
        leftFront.setPower(LFSpeed);
        rightFront.setPower(RFSpeed);
        leftBack.setPower(LBSpeed);
        rightBack.setPower(RBSpeed);
    }

    public void powerMotors(double power) {
        leftFront.setPower(power);
        rightFront.setPower(power);
        leftBack.setPower(power);
        rightBack.setPower(power);
    }

    public void resetEncoder(DcMotor motor) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void drive(double stickX, double stickY, double rotation) {
        double y = -stickY;
        double x = stickX;
        double rx = rotation;

        double magnitude = Math.hypot(x, y);

        if (magnitude > 1.0) {
            x /= magnitude;
            y /= magnitude;
            magnitude = 1.0;
        }

        if (magnitude > 0.05) {
            double angle = Math.atan2(y, x);

            x = Math.cos(angle) * magnitude * MAX_SPEED;
            y = Math.sin(angle) * magnitude * MAX_SPEED;
        }

        double frontLeftPower = y + x + rx;
        double backLeftPower = y - x + rx;
        double frontRightPower = y - x - rx;
        double backRightPower = y + x - rx;

        double max = Math.max(
                Math.max(Math.abs(frontLeftPower), Math.abs(backLeftPower)),
                Math.max(Math.abs(frontRightPower), Math.abs(backRightPower))
        );

        if (max > 1.0) {
            frontLeftPower /= max;
            backLeftPower /= max;
            frontRightPower /= max;
            backRightPower /= max;
        }

        this.leftFront.setPower(frontLeftPower);
        this.leftBack.setPower(backLeftPower);
        this.rightFront.setPower(frontRightPower);
        this.rightBack.setPower(backRightPower);
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

//    // AUTONOMOUS MODES
//    private static final double TICKS_PER_ROTATION = 288.375;
//    private static final double WHEEL_DIAMETER = 9.6; // in cm
//
//    public void resetMecanumEncoder() {
//        resetEncoder(LFName);
//        resetEncoder(LBName);
//        resetEncoder(RFName);
//        resetEncoder(RBName);
//    }
//
//    public void straight(double speed) {
//        powerMotors(speed);
//    }
//
//    public void strafe(double speed) { // RIGHT = positive, LEFT = negative
//        powerMotor(LFName,speed);
//        powerMotor(RFName,-speed);
//        powerMotor(LBName,-speed);
//        powerMotor(RBName,speed);
//    }
//
//    public void rotate(double speed) { // POS: clockwise
//        powerMotor(LFName,speed);
//        powerMotor(RFName,-speed);
//        powerMotor(LBName,speed);
//        powerMotor(RBName,-speed);
//    }
//
//    public void powerStop() {
//        powerMotor(LFName,0);
//        powerMotor(RFName,0);
//        powerMotor(LBName,0);
//        powerMotor(RBName,0);
//    }
//
//    public boolean moveDistance(double cm, double speedMultiplier) {
//        double circumfrence = (WHEEL_DIAMETER)*(Math.PI);
//        double rotations = getMotor(Util.RB_DRIVE_NAME).getCurrentPosition()/TICKS_PER_ROTATION;
//        double distanceTravelled = Math.abs(circumfrence * rotations);
//
//        double absoluteDistance = Math.abs(cm);
//
//        double distanceTravelledRatio = 1-(distanceTravelled/absoluteDistance);
//
//        if (distanceTravelled < absoluteDistance) {
//            if (cm >= 0) {
//                straight(Math.max(0.3,distanceTravelledRatio*speedMultiplier));
//            } else {
//                straight(-Math.max(0.3,distanceTravelledRatio*speedMultiplier));
//            }
//            return (true);
//        } else {
//            powerStop();
//            resetMecanumEncoder();
//            return (false);
//        }
//    }
//
//    public boolean strafeDistance(double cm, double speedMultiplier) {
//        double circumfrence = (WHEEL_DIAMETER)*(Math.PI);
//        double rotations = getMotor(Util.RB_DRIVE_NAME).getCurrentPosition()/TICKS_PER_ROTATION;
//        double distanceTravelled = Math.abs(circumfrence * rotations);
//
//        double absoluteDistance = Math.abs(cm);
//
//        double distanceTravelledRatio = 1-(distanceTravelled/absoluteDistance);
//
//        if (distanceTravelled < absoluteDistance) {
//            if (cm >= 0) {
//                strafe(Math.max(0.44,distanceTravelledRatio*speedMultiplier));
//            } else {
//                strafe(-Math.max(0.44,distanceTravelledRatio*speedMultiplier));
//            }
//            return (true);
//        } else {
//            powerStop();
//            resetMecanumEncoder();
//            return (false);
//        }
//    }
}