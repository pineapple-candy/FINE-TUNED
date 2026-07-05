package org.firstinspires.ftc.teamcode.OpModes;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.AutoPID;
import org.firstinspires.ftc.teamcode.Subsystems.Hood;
import org.firstinspires.ftc.teamcode.Subsystems.HoodandFlywheelCompensation;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
import org.firstinspires.ftc.teamcode.Util.Constants;
import org.firstinspires.ftc.teamcode.Util.StaticVariables;

import java.util.function.Supplier;

@Autonomous(name="Far Zone Red")
public class FarAutoRed extends LinearOpMode {

    private final double SECONDS_AFTER_ABORT = 25;

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(180));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Transfer transfer = new Transfer(hardwareMap, telemetry);
        Shooter shooter = new Shooter(hardwareMap, telemetry);
        Turret turret = new Turret(hardwareMap, telemetry);
        Hood hood = new Hood(hardwareMap, telemetry);
        HoodandFlywheelCompensation calc = new HoodandFlywheelCompensation(hardwareMap, telemetry, drive);

        TrajectoryActionBuilder wholeTrajectory = drive.actionBuilder(initialPose)
                //Shoot first
                .stopAndAdd(hood.farZone())
                .stopAndAdd(turret.farZone(false))
                .stopAndAdd(new AutoPID.waitUntilRPM(shooter))
                .stopAndAdd(transfer.new startIntakeFire(0.3))
                .stopAndAdd(new AutoPID.waitUntilRPMDrop(shooter))
                .stopAndAdd(transfer.stopIntake())
                .stopAndAdd(new AutoPID.waitUntilRPM(shooter))
                .stopAndAdd(transfer.new startIntakeFire(0.3))
                .stopAndAdd(new AutoPID.waitUntilRPMDrop(shooter))
                .stopAndAdd(transfer.stopIntake())
                .stopAndAdd(new AutoPID.waitUntilRPM(shooter))
                .stopAndAdd(transfer.new startIntakeFire(1))
                .stopAndAdd(new AutoPID.waitUntilRPMDrop(shooter))
                .stopAndAdd(transfer.stopIntake())


                // First stack
                .stopAndAdd(transfer.startIntake())
                .strafeToLinearHeading(new Vector2d(-47,0), Math.toRadians(180), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-50, 80))
//                .waitSeconds(0.25)
//                .strafeToLinearHeading(new Vector2d(-37.5,0), Math.toRadians(180))
//                .strafeToLinearHeading(new Vector2d(-44,0), Math.toRadians(180))
//                .strafeToLinearHeading(new Vector2d(-44,-5), Math.toRadians(180))
//                .strafeToLinearHeading(new Vector2d(-45,-5), Math.toRadians(180))
//                .waitSeconds(0.25)
                .stopAndAdd(transfer.stopIntake())
                .strafeToLinearHeading(new Vector2d(0,0), Math.toRadians(180), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-50, 80))

                // Shoot second
                .stopAndAdd(hood.farZone())
                .stopAndAdd(turret.farZone(false))
                .stopAndAdd(new AutoPID.waitUntilRPM(shooter))
                .stopAndAdd(transfer.new startIntakeFire(0.3))
                .stopAndAdd(new AutoPID.waitUntilRPMDrop(shooter))
                .stopAndAdd(transfer.stopIntake())
                .stopAndAdd(new AutoPID.waitUntilRPM(shooter))
                .stopAndAdd(transfer.new startIntakeFire(0.3))
                .stopAndAdd(new AutoPID.waitUntilRPMDrop(shooter))
                .stopAndAdd(transfer.stopIntake())
                .stopAndAdd(new AutoPID.waitUntilRPM(shooter))
                .stopAndAdd(transfer.new startIntakeFire(1))
                .stopAndAdd(new AutoPID.waitUntilRPMDrop(shooter))
                .stopAndAdd(transfer.stopIntake())

                // Go to first stack
                .strafeToLinearHeading(new Vector2d(-12,-30),Math.toRadians(180), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-50, 80))
                .stopAndAdd(transfer.startIntake())
                .strafeToLinearHeading(new Vector2d(-45,-30),Math.toRadians(180))
                .waitSeconds(0.5)
                .stopAndAdd(transfer.stopIntake())

                .strafeToLinearHeading(new Vector2d(0,0), Math.toRadians(180), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-50, 80))

                // Shoot third
                .stopAndAdd(hood.farZone())
                .stopAndAdd(turret.farZone(false))
                .stopAndAdd(new AutoPID.waitUntilRPM(shooter))
                .stopAndAdd(transfer.new startIntakeFire(0.3))
                .stopAndAdd(new AutoPID.waitUntilRPMDrop(shooter))
                .stopAndAdd(transfer.stopIntake())
                .stopAndAdd(new AutoPID.waitUntilRPM(shooter))
                .stopAndAdd(transfer.new startIntakeFire(0.3))
                .stopAndAdd(new AutoPID.waitUntilRPMDrop(shooter))
                .stopAndAdd(transfer.stopIntake())
                .stopAndAdd(new AutoPID.waitUntilRPM(shooter))
                .stopAndAdd(transfer.new startIntakeFire(1))
                .stopAndAdd(new AutoPID.waitUntilRPMDrop(shooter))
                .stopAndAdd(transfer.stopIntake())

                // Check for loose balls
                .stopAndAdd(transfer.startIntake())
                .strafeToLinearHeading(new Vector2d(-35,0),Math.toRadians(180), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-50, 80))
                .waitSeconds(0.5)
                .strafeToLinearHeading(new Vector2d(0,0),Math.toRadians(180), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-50, 80))
                .stopAndAdd(transfer.stopIntake())

                // Shoot fourth
                .stopAndAdd(hood.farZone())
                .stopAndAdd(turret.farZone(false))
                .stopAndAdd(new AutoPID.waitUntilRPM(shooter))
                .stopAndAdd(transfer.new startIntakeFire(0.3))
                .stopAndAdd(new AutoPID.waitUntilRPMDrop(shooter))
                .stopAndAdd(transfer.stopIntake())
                .stopAndAdd(new AutoPID.waitUntilRPM(shooter))
                .stopAndAdd(transfer.new startIntakeFire(0.3))
                .stopAndAdd(new AutoPID.waitUntilRPMDrop(shooter))
                .stopAndAdd(transfer.stopIntake())
                .stopAndAdd(new AutoPID.waitUntilRPM(shooter))
                .stopAndAdd(transfer.new startIntakeFire(1))
                .stopAndAdd(new AutoPID.waitUntilRPMDrop(shooter))
                .stopAndAdd(transfer.stopIntake())

                // Autobots, roll out!
                .strafeToLinearHeading(new Vector2d(-45,0),Math.toRadians(180), new TranslationalVelConstraint(80), new ProfileAccelConstraint(-50, 80))
                .endTrajectory();

        waitForStart();

        if (isStopRequested()) return;

        StaticVariables.setLastId(Constants.RED_SHOOT_ID);

        telemetry.addLine("AUTO STARTED");
        telemetry.update();

        Action mainAuto = wholeTrajectory.build();

        Action timeoutAuto = new TimedSwitchAction(
                mainAuto,
                SECONDS_AFTER_ABORT,

                // after x seconds, GO to (-20,0)

                // btw this is techy and i searched online for why () -> is used - if u were just to do new SequentialAction immediately, it'd build it
                // with the pose at the start. BUT! you only want to run it later. So, this is like a method that just returns that.
                // So, its only called when needed and gets the right pose. Cool beans.
                () -> new SequentialAction(
                        transfer.stopIntake(),

                        drive.actionBuilder(drive.localizer.getPose())
                                .strafeToLinearHeading(new Vector2d(-45, 0), Math.toRadians(180))
                                .build()
                )
        );

        Actions.runBlocking(
                new ActionWithUpdate(
                        timeoutAuto,
                        new AutoPID.ShooterUpdateAction(shooter, calc, telemetry)
                )
        );
    }

    // REMEMBER - ACTIONS RETURN TRUE OR FALSE!!!

    public static class TimedSwitchAction implements Action {
        private final Action mainAction;
        private final double switchTimeSeconds;

        // Suppliers are basically - i provide an Action if u call .get() on me - useful for creating the trajectory LATER
        private final Supplier<Action> failoverActionSupplier;

        private final ElapsedTime timer = new ElapsedTime();

        private boolean started = false;
        private boolean switched = false;
        private Action failoverAction = null;

        public TimedSwitchAction(Action mainAction, double switchTimeSeconds, Supplier<Action> failoverActionSupplier) {
            this.mainAction = mainAction;
            this.switchTimeSeconds = switchTimeSeconds;
            this.failoverActionSupplier = failoverActionSupplier;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // If haven't started timer, do so
            if (!started) {
                timer.reset();
                started = true;
            }

            // chek if timer is above seconds
            if (!switched && timer.seconds() >= switchTimeSeconds) {
                switched = true;
                failoverAction = failoverActionSupplier.get();
            }

            // ok yay now i do the other action - when thats done, it returns false, which then ends everythiung
            if (switched) {
                return failoverAction.run(packet);
            }

            return mainAction.run(packet);
        }
    }

    public static class ActionWithUpdate implements Action {
        private final Action mainAction;
        private final Action updateAction;

        public ActionWithUpdate(Action mainAction, Action updateAction) {
            this.mainAction = mainAction;
            this.updateAction = updateAction;
        }

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            updateAction.run(packet); // ALWAYS run update action no matter what.
            return mainAction.run(packet); // If the action with the time delay is done, then yay we done
        }
    }
}