package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static org.firstinspires.ftc.teamcode.Util.Constants.*;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Util.Timer;
import org.firstinspires.ftc.teamcode.Util.Toggle;

public class Transfer {
    private DcMotor intake1;
    private DcMotor intake2;
    private Servo stopper;
    private Shooter shooter;
    private ElapsedTime timer = new ElapsedTime();
    private static final double STOP_DOWN = 0.123;
    Telemetry telemetry;
    private static final double STOP_UP = 0.35;

    public Transfer(HardwareMap hardwareMap, Telemetry telemetry) {
        shooter= new Shooter(hardwareMap, telemetry);
        intake1 = hardwareMap.get(DcMotor.class, INTAKE1.getMotorName());
        intake1.setDirection(INTAKE1.getDirection());
        intake1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intake2 = hardwareMap.get(DcMotor.class, INTAKE2.getMotorName());
        intake2.setDirection(INTAKE2.getDirection());
        intake2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        stopper = hardwareMap.get(Servo.class, STOPPER.getServoName());
        stopper.setPosition(STOP_DOWN);
        this.telemetry = telemetry;
    }

    private Toggle xButton = new Toggle();
    private Timer xButtonTimer = new Timer();

    public void update(Gamepad gamepad1, Gamepad gamepad2) {
       if(gamepad1.right_bumper&&stopper.getPosition()==0.40){
            if (timer.seconds()>4){
                timer.reset();
            }
            if(shooter.getRPM()>-1000){
                intake1.setPower(0.0);
                intake2.setPower(0.0);
                telemetry.addLine("fullpowertransfer");
            } else {
                intake1.setPower(1);
                intake2.setPower(1);
           }
       } else if (gamepad1.right_bumper) { // intake
            intake1.setPower(1);
            intake2.setPower(1);
       } else if (gamepad1.left_bumper) { // outtake
            intake1.setPower(-1);
            intake2.setPower(-1);
       } else {
            intake1.setPower(0);
            intake2.setPower(0);
       }

       if (gamepad1.left_trigger > 0.05) {
            stopper.setPosition(STOP_UP);
       }
       else {
            stopper.setPosition(STOP_DOWN);
       }


       if (xButton.runToggle(gamepad2.x)) {
           xButtonTimer.startTimer();
       }

       if (gamepad2.x && (xButtonTimer.getTime() > 0.5 && xButtonTimer.getTime() < 2)) {
            stopper.setPosition(STOP_UP);
       } else if (gamepad2.x && (xButtonTimer.getTime() > 2)) {
            intake1.setPower(-1);
            intake2.setPower(-1);
            stopper.setPosition(STOP_UP);
       }
    }

    public class startIntake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            intake1.setPower(1);
            intake2.setPower(1);
            stopper.setPosition(STOP_DOWN);
            return false;
        }
    }

    public class startIntakeFire implements Action {
        private final double power;
        public startIntakeFire(double power){
            this.power=power;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            intake1.setPower(power);
            intake2.setPower(1);
            stopper.setPosition(STOP_UP);
            return false;
        }
    }

    public class stopIntake implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            intake1.setPower(0);
            intake2.setPower(0);
            return false;
        }
    }

    public Action startIntake() {return new startIntake();}
    public Action startIntakeFire(double power) {return new startIntakeFire(power);}
    public Action stopIntake() {return new stopIntake();}

}