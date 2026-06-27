package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.teamcode.Util.Constants.SHOOTER1;
import static org.firstinspires.ftc.teamcode.Util.Constants.SHOOTER2;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;

public class HoodandFlywheelCompensation {
    MecanumDrive odo;
    HoodRegression basepos;
    public double flywheelOutput;
    public double hoodOutput;
    Telemetry telemetry;
    PID farPID;
    PID nearPID;
    private long lastTime;
    private VoltageSensor battery;
    private double currentVelocity;
    private double dt;
    public double distance;
    public double turretpos;
    double farFF=0.6755; // AT 12.5V
    double nearFF=0.61; // AT 12.5V
    boolean dPadDown = true;
    boolean dPadUp=false;
    double farTarget = 4200;
    double nearTarget = 3300;
    boolean nearhpzone = false;
    Vector2d goal = new Vector2d(0,0);
    public HoodandFlywheelCompensation(HardwareMap hardwareMap, Telemetry telemetry, MecanumDrive mecanumDrive) {
        this.telemetry = telemetry;

        farPID = new PID(0.000004, 0.000000001, 0.000);
        farPID.setTarget(farTarget); // target velocity

        nearPID = new PID(0.000002, 0.0000000, 0.000);
        nearPID.setTarget(nearTarget);

        lastTime = System.nanoTime();

        battery = hardwareMap.voltageSensor.iterator().next();

        basepos = new HoodRegression();
        this.odo = mecanumDrive;
    }

    public void update(Gamepad gamepad, double rpm) {

        PIDUpdates();
        odo.updatePoseEstimate();
        currentVelocity=rpm;

        turretpos = getturretpos();

        if (nearorfar()) {
            flywheelOutput = farPID.calculateOutput(currentVelocity, dt)+voltageCompensation(true);
            hoodOutput = basepos.Hoodpos(getrange(),true);
        } else {
            flywheelOutput = nearPID.calculateOutput(currentVelocity, dt)+voltageCompensation(false);
            hoodOutput = basepos.Hoodpos(getrange(),false);
        }
        if (gamepad.y==true) {
            odo.localizer.setPose(new Pose2d(0, 0, 0));
        }
        if (gamepad.x==true){
            nearhpzone = true;
        } else if (gamepad.a == true) {
            nearhpzone = false;
        }

        if (gamepad.dpad_up&&!dPadUp){
            farFF=farFF+0.0085;
            farTarget=farTarget+50;
            farPID.setTarget(farTarget);
        } if (gamepad.dpad_down&&!dPadDown){
            farFF=farFF-0.0085;
            farTarget=farTarget-50;
            farPID.setTarget(farTarget);
        }
        dPadDown = gamepad.dpad_down;
        dPadUp = gamepad.dpad_up;

        telemetry.addData("distance to goal",distance);
        telemetry.addData("coords",odo.localizer.getPose());
        telemetry.addData("Target", farTarget);

    }
    public double getrange(){
    double relXDistance=odo.localizer.getPose().position.x-goal.x;
    double relYDistance=odo.localizer.getPose().position.y-goal.y;
    distance = Math.sqrt(relXDistance*relXDistance+relYDistance*relYDistance);
    return distance;
    }

    public boolean nearorfar(){
        if (getrange()<80){
            return false;
        }
        else {
            return true;
        }
    }

    public void telemetryUpdate(){
        telemetry.addData("flywheelPower",flywheelOutput);
        telemetry.addData("hoodPosition", hoodOutput);
    }

    public void PIDUpdates(){
        long currentTime = System.nanoTime();
        dt = (currentTime - lastTime) / 1e9;
        lastTime = currentTime;
    }

    public double voltageCompensation(boolean farornear) {
        if (farornear) {
            return farFF * (13 / battery.getVoltage());
        } else {
            return nearFF * (12.10 / battery.getVoltage());
        }
    }

    double getturretpos() {

        Pose2d robotPose = new Pose2d(odo.localizer.getPose().position.x, (odo.localizer.getPose().position.y), odo.localizer.getPose().heading.toDouble());
        if (nearhpzone==false) {
            goal = new Vector2d(-121, 123);
        } else if (nearhpzone==true){
            goal = new Vector2d(-2, -112);
        }
        double dx = goal.x - robotPose.position.x;
        double dy = goal.y - robotPose.position.y;

        double goalAngle = Math.atan2(dy, dx);

        double turretAngle = goalAngle - robotPose.heading.toDouble();
        telemetry.addData("turretangle", turretAngle);

        turretAngle = Math.atan2(Math.sin(turretAngle), Math.cos(turretAngle));

        double degrees = Math.toDegrees(turretAngle);

        double servoDegrees = degrees / 0.6185567010309278;

        servoDegrees = Math.max(-177.5, Math.min(177.5, servoDegrees));

        double servoPos = (servoDegrees / 355.0) + 0.5 ;

        servoPos = Math.max(0.0, Math.min(1.0, servoPos));

        telemetry.addData("turret deg", degrees);

        return servoPos;
    }
    public double returnpower(){
        return flywheelOutput;
    }

    public double returnhood(){
        return hoodOutput;
    }

    public double returnturret(){
        return turretpos;
    }

}
