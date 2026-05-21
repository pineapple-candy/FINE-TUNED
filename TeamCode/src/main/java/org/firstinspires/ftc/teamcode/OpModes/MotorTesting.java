package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Util.Constants;

@TeleOp(name="Motor Testing")
public class MotorTesting extends LinearOpMode {

    private DcMotor LFMotor;
    private DcMotor LBMotor;
    private DcMotor RFMotor;
    private DcMotor RBMotor;


    @Override
    public void runOpMode() {
        LFMotor = hardwareMap.get(DcMotor.class, Constants.LF_DRIVE.getMotorName());
        LBMotor = hardwareMap.get(DcMotor.class, Constants.LB_DRIVE.getMotorName());
        RFMotor = hardwareMap.get(DcMotor.class, Constants.RF_DRIVE.getMotorName());
        RBMotor = hardwareMap.get(DcMotor.class, Constants.RB_DRIVE.getMotorName());
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            if (gamepad1.a) {
                LFMotor.setPower(0.5);
            } else if (gamepad1.b) {
                LBMotor.setPower(0.5);
            } else if (gamepad1.x) {
                RFMotor.setPower(0.5);
            } else if (gamepad1.y) {
                RBMotor.setPower(0.5);
            } else {
                LFMotor.setPower(0);
                LBMotor.setPower(0);
                RFMotor.setPower(0);
                RBMotor.setPower(0);
            }
        }
    }
}