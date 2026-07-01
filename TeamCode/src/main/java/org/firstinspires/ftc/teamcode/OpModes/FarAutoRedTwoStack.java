package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.AutoPID;
import org.firstinspires.ftc.teamcode.Subsystems.Hood;
import org.firstinspires.ftc.teamcode.Subsystems.HoodandFlywheelCompensation;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
import org.firstinspires.ftc.teamcode.Util.Constants;
import org.firstinspires.ftc.teamcode.Util.StaticVariables;

@Autonomous(name="Far Zone Red")
public class FarAutoRedTwoStack extends LinearOpMode {

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(180));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Transfer transfer = new Transfer(hardwareMap,telemetry);
        Shooter shooter = new Shooter(hardwareMap,telemetry);
        Turret turret = new Turret(hardwareMap,telemetry);
        Hood hood = new Hood(hardwareMap,telemetry);
        HoodandFlywheelCompensation calc = new HoodandFlywheelCompensation(hardwareMap,telemetry,drive);

        // TODO: Edit this to be Red side.
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
                .strafeToLinearHeading(new Vector2d(-43,-5), Math.toRadians(180))
                .strafeToLinearHeading(new Vector2d(-44 ,-15), Math.toRadians(190))
                .strafeToLinearHeading(new Vector2d(-44,-12), Math.toRadians(180))
                .strafeToLinearHeading(new Vector2d(-47,-12), Math.toRadians(180))
//                .waitSeconds(0.25)
//                .strafeToLinearHeading(new Vector2d(-37.5,0), Math.toRadians(180))
//                .strafeToLinearHeading(new Vector2d(-44,0), Math.toRadians(180))
//                .strafeToLinearHeading(new Vector2d(-44,-5), Math.toRadians(180))
//                .strafeToLinearHeading(new Vector2d(-47,-5), Math.toRadians(180))
//                .waitSeconds(0.25)
                .stopAndAdd(transfer.stopIntake())
                .strafeToLinearHeading(new Vector2d(0,0), Math.toRadians(180))

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
                .strafeToLinearHeading(new Vector2d(-12,-30),Math.toRadians(180))
                .stopAndAdd(transfer.startIntake())
                .strafeToLinearHeading(new Vector2d(-45,-30),Math.toRadians(180))
                .waitSeconds(0.5)
                .stopAndAdd(transfer.stopIntake())

                .strafeToLinearHeading(new Vector2d(0,0), Math.toRadians(180))

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

                // Go to second stack
                .strafeToLinearHeading(new Vector2d(-12,-50),Math.toRadians(180))
                .stopAndAdd(transfer.startIntake())
                .strafeToLinearHeading(new Vector2d(-43,-50),Math.toRadians(180))
                .waitSeconds(0.5)
                .stopAndAdd(transfer.stopIntake())

                .strafeToLinearHeading(new Vector2d(0,0), Math.toRadians(180))

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
                .strafeToLinearHeading(new Vector2d(-20,0),Math.toRadians(180))
                .endTrajectory();

        waitForStart();

        StaticVariables.setLastId(Constants.RED_SHOOT_ID);

        telemetry.addLine("AUTO STARTED");
        telemetry.update();
        Actions.runBlocking(
                new ParallelAction(
                        new AutoPID.ShooterUpdateAction(shooter, calc, telemetry),
                        new SequentialAction(
                                wholeTrajectory.build()
                        )
                )
        );
    }
}
