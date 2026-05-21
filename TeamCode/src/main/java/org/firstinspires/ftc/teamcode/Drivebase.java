package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Util.Constants;

public class Drivebase {

    //Initialize Variables
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    public void initDrivebase(HardwareMap hardwaremap){

        //name motors

        this.frontLeft = hardwaremap.get(DcMotor.class, Constants.LF_DRIVE.getMotorName());
        this.frontRight = hardwaremap.get(DcMotor.class, Constants.RF_DRIVE.getMotorName());
        this.backLeft = hardwaremap.get(DcMotor.class, Constants.LB_DRIVE.getMotorName());
        this.backRight = hardwaremap.get(DcMotor.class, Constants.RB_DRIVE.getMotorName());

        //Set Direction

        this.frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        this.frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        this.backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        this.backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        //Set Zero Power Behavior

        this.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void drive(double stickX, double stickY, double rotation){
        double y = stickY;
        double x = stickX * 1.1;
        double rx = rotation;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        this.frontLeft.setPower(frontLeftPower);
        this.frontRight.setPower(frontLeftPower);
        this.backLeft.setPower(frontLeftPower);
        this.backRight.setPower(frontLeftPower);
    }
}




