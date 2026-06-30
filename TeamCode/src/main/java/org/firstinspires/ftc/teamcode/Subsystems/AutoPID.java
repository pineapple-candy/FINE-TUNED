package org.firstinspires.ftc.teamcode.Subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutoPID {

    public static class ShooterUpdateAction implements Action {
        private final Shooter shooter;
        private final HoodandFlywheelCompensation calcs;
        private final Telemetry telemetry;

        public ShooterUpdateAction(Shooter shooter, HoodandFlywheelCompensation calcs, Telemetry telemetry) {
            this.shooter = shooter;
            this.calcs = calcs;
            this.telemetry = telemetry;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            calcs.updateAuto(shooter.getRPM());
            shooter.shoot(calcs.returnPower());
            telemetry.addData("rpm", shooter.getRPM());
            packet.put("rpm", shooter.getRPM());
            telemetry.update();
            return true;
        }
    }

    public static class waitUntilRPM implements Action {
        private static final double targetRPM = -4150;
        private final Shooter shooter;

        public waitUntilRPM(Shooter shooter) {
            this.shooter = shooter;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            double rpm = shooter.getRPM();
            packet.put("rpm", shooter.getRPM());
            return Math.abs(rpm - targetRPM) > 50;
        }
    }

    public static class waitUntilRPMDrop implements Action {

        private final Shooter shooter;
        private double lastrpm = 0;
        private boolean started = false;
        ElapsedTime timer = new ElapsedTime();

        public waitUntilRPMDrop(Shooter shooter) {
            this.shooter = shooter;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            if (!started) {
                timer.reset();
                lastrpm = shooter.getRPM();
                started = true;
            }

            double rpm = shooter.getRPM();
            packet.put("rpm", shooter.getRPM());
            boolean shotDetected = Math.abs(lastrpm) > 4000 && Math.abs(rpm) < 3950;
            lastrpm = rpm;
            if (timer.seconds() > 1.5) {
                started = false;
                return false;
            }
            if (shotDetected) {
                started = false;
                return false;
            } else {
                return true;
            }
        }
    }
}


