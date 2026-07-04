package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Util.Constants;
import org.firstinspires.ftc.teamcode.Util.StaticVariables;

import java.lang.annotation.Target;

public class HoodandFlywheelCompensation {
    MecanumDrive odo;
    HoodRegression basePos;
    public double flywheelOutput;
    public double hoodOutput;
    Telemetry telemetry;
    PID farPID;
    PID nearPID;
    double TargetRPM=4350;
    private long lastTime;
    private VoltageSensor battery;
    private double currentVelocity;
    private double dt; // Small difference in time
    public double distance;
    public double turretPos;
    double RPM_NEAR_TARGET=3800;

    private double BASE_FAR_FF = 0.6755; // AT 12.5V
    private double BASE_NEAR_FF = 0.61; // AT 12.5V

    private boolean dPadDown = true;
    private boolean dPadUp=false;
    private double farTarget = 4200;
    private double nearTarget = 3300;
    private boolean nearHPZone = false;

    Vector2d GOAL_VECTOR = new Vector2d(0,0);

    double FAR_ZONE_THRESHOLD = 100; // in inches
    double distancefrfr;
    boolean leftBumper=true;
    boolean righBumper=false;

    public HoodandFlywheelCompensation(HardwareMap hardwareMap, Telemetry telemetry, MecanumDrive mecanumDrive) {
        this.telemetry = telemetry;

        farPID = new PID(0.000004, 0.000000001, 0.000);
        farPID.setTarget(farTarget); // target velocity

        nearPID = new PID(0.000002, 0.0000000, 0.000);
        nearPID.setTarget(nearTarget);

        lastTime = System.nanoTime();

        battery = hardwareMap.voltageSensor.iterator().next();

        basePos = new HoodRegression();
        this.odo = mecanumDrive;
    }

    public void update(Gamepad gamepad, double rpm, boolean bangbang, Gamepad gamepad2) {
        telemetryUpdate();
        PIDUpdates();
        odo.updatePoseEstimate();
        currentVelocity = rpm;
        boolean bangbangtrue = true;
        turretPos = getTurretPos();
        distancefrfr=getRange();
        if (bangbangtrue) {
            if (nearOrFar()) {
                if (currentVelocity>-TargetRPM){
                    flywheelOutput = 1;
                } else {
                    flywheelOutput =0.2;
                }
            } else {
                if (currentVelocity>-RPM_NEAR_TARGET){
                    flywheelOutput = 1;
                } else {
                    flywheelOutput =0.2;
                }
            }
        } else {
                if (currentVelocity>-TargetRPM){
                    flywheelOutput = 1;
                } else {
                    flywheelOutput =0.2;
                }
            }
        if (gamepad.y) {
            odo.localizer.setPose(new Pose2d(0, 0, 0));
        }
        if (gamepad.x){
            nearHPZone = true;
        } else if (gamepad.a) {
            nearHPZone = false;
        }

        if (gamepad2.y&&!dPadUp){
            TargetRPM += 100;
        } if (gamepad2.a&&!dPadDown){
            TargetRPM -= 100;
        }
        if (gamepad2.left_bumper&&!leftBumper){
            RPM_NEAR_TARGET+=100;
        } if (gamepad2.right_bumper&&!righBumper){
            RPM_NEAR_TARGET-=100;
        }
        leftBumper = gamepad2.left_bumper;
        righBumper = gamepad2.right_bumper;

        dPadDown = gamepad2.a;
        dPadUp = gamepad2.y;

        telemetry.addData("distance to goal",distancefrfr);
        telemetry.addData("coords",odo.localizer.getPose());
        telemetry.addData("Target", farTarget);
        telemetry.addData("neartarget", RPM_NEAR_TARGET);

    }

    public double getRange(){
        double relXDistance= odo.localizer.getPose().position.x - GOAL_VECTOR.x;
        double relYDistance= odo.localizer.getPose().position.y - GOAL_VECTOR.y;
        distance = Math.sqrt(relXDistance*relXDistance+relYDistance*relYDistance);
        return distance;
    }

    public boolean nearOrFar(){
        return (getRange() > FAR_ZONE_THRESHOLD); // FAR_ZONE_THRESHOLD = 50
    }

    public void telemetryUpdate(){
        telemetry.addData("flywheelPower",flywheelOutput);
        telemetry.addData("hoodPosition", hoodOutput);
        telemetry.addData("targetrpm", TargetRPM);
    }

    public void PIDUpdates(){
        long currentTime = System.nanoTime();
        dt = (currentTime - lastTime) / 1e9;
        lastTime = currentTime;
    }

    public double voltageCompensation(boolean far) {
        if (far) {
            return BASE_FAR_FF * (13 / battery.getVoltage());
        } else {
            return BASE_NEAR_FF * (12.10 / battery.getVoltage());
        }
    }

    double getTurretPos() {

        Pose2d robotPose = new Pose2d(odo.localizer.getPose().position.x, (odo.localizer.getPose().position.y), odo.localizer.getPose().heading.toDouble());
        double sideID = StaticVariables.getLastId();

        // TODO: TP, you must edit the goal vector positions for RED side. I don't understand what the values mean, so I trust you to edit them. I've tracked if ur on blue/red side tracking already.
        if (sideID == Constants.RED_SHOOT_ID) { // ON RED SIDE
            if (!nearHPZone) {
                GOAL_VECTOR = new Vector2d(-118,-115);
            } else {
                GOAL_VECTOR = new Vector2d(8,115);
            }
        } else { // ON BLUE SIDE / NO LAST AUTO SELECTED
            if (!nearHPZone) {
                GOAL_VECTOR = new Vector2d(-118, 115);
            } else {
                GOAL_VECTOR = new Vector2d(8, -115);
            }
        }

        double dx = GOAL_VECTOR.x - robotPose.position.x;
        double dy = GOAL_VECTOR.y - robotPose.position.y;

        double goalAngle = Math.atan2(dy, dx);

        double turretAngle = goalAngle - robotPose.heading.toDouble();
        telemetry.addData("turretangle", turretAngle);

        turretAngle = Math.atan2(Math.sin(turretAngle), Math.cos(turretAngle));

        double degrees = Math.toDegrees(turretAngle);

        double servoDegrees = degrees / 0.6378865979381443;

        servoDegrees = Math.max(-177.5, Math.min(177.5, servoDegrees));

        double servoPos = (servoDegrees / 355.0) + 0.5 ;

        servoPos = Math.max(0.0, Math.min(1.0, servoPos));

        telemetry.addData("turret deg", degrees);

        return servoPos;
    }

    public double returnPower(){
        return flywheelOutput;
    }

    public double returnHood(){
        return hoodOutput;
    }

    public double returnTurret(){
        return turretPos;
    }
    public void updateAuto(double rpm){
        PIDUpdates();
        farPID.setTarget(4200);
        farPID = new PID(0.000004, 0.000000001, 0.000);
        currentVelocity=rpm;
        flywheelOutput = farPID.calculateOutput(currentVelocity, dt)+ voltageCompensation(true);
    }
}
