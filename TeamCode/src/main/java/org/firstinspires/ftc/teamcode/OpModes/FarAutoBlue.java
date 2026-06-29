package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.Subsystems.Hood;
import org.firstinspires.ftc.teamcode.Subsystems.HoodandFlywheelCompensation;
import org.firstinspires.ftc.teamcode.Subsystems.Shooter;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;
import org.firstinspires.ftc.teamcode.Subsystems.Turret;
import org.firstinspires.ftc.teamcode.Util.Constants;
import org.firstinspires.ftc.teamcode.Util.StaticVariables;

@Autonomous(name="Far Zone Blue")
public class FarAutoBlue extends LinearOpMode {

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(0, 0, Math.toRadians(180));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Transfer transfer = new Transfer(hardwareMap,telemetry);
        Shooter shooter = new Shooter(hardwareMap,telemetry);
        Turret turret = new Turret(hardwareMap,telemetry);
        Hood hood = new Hood(hardwareMap,telemetry);
        HoodandFlywheelCompensation calc = new HoodandFlywheelCompensation(hardwareMap,telemetry,drive);

        TrajectoryActionBuilder wholeTrajectory = drive.actionBuilder(initialPose)
                //Shoot first
                .stopAndAdd(hood.farZone())
                .stopAndAdd(turret.farZone())

                .stopAndAdd(shooter.startShooting())
                .waitSeconds(5.5)

                .stopAndAdd(transfer.startIntakeFire())
                .waitSeconds(0.4)
                .stopAndAdd(transfer.stopIntake())
                .waitSeconds(0.6)
                .stopAndAdd(transfer.startIntakeFire())
                .waitSeconds(0.4)
                .stopAndAdd(transfer.stopIntake())
                .waitSeconds(0.6)
                .stopAndAdd(transfer.startIntakeFire())
                .waitSeconds(0.4)
                .stopAndAdd(transfer.stopIntake())


                // First stack
                .stopAndAdd(transfer.startIntake())
                .strafeToLinearHeading(new Vector2d(-47,0), Math.toRadians(180))
                .waitSeconds(0.5)
                .stopAndAdd(transfer.stopIntake())
                .strafeToLinearHeading(new Vector2d(0,0), Math.toRadians(180))

                // Shoot second

                .stopAndAdd(transfer.startIntakeFire())
                .waitSeconds(0.4)
                .stopAndAdd(transfer.stopIntake())
                .waitSeconds(0.5)
                .stopAndAdd(transfer.startIntakeFire())
                .waitSeconds(0.4)
                .stopAndAdd(transfer.stopIntake())
                .waitSeconds(0.5)
                .stopAndAdd(transfer.startIntakeFire())
                .waitSeconds(0.4)
                .stopAndAdd(transfer.stopIntake())

                // Go to first stack
                .strafeToLinearHeading(new Vector2d(-12,30),Math.toRadians(180))
                .stopAndAdd(transfer.startIntake())
                .strafeToLinearHeading(new Vector2d(-45,30),Math.toRadians(180))
                .waitSeconds(0.5)
                .stopAndAdd(transfer.stopIntake())

                .strafeToLinearHeading(new Vector2d(0,0), Math.toRadians(180))

                // Shoot third

                .stopAndAdd(transfer.startIntakeFire())
                .waitSeconds(0.4)
                .stopAndAdd(transfer.stopIntake())
                .waitSeconds(0.5)
                .stopAndAdd(transfer.startIntakeFire())
                .waitSeconds(0.4)
                .stopAndAdd(transfer.stopIntake())
                .waitSeconds(0.5)
                .stopAndAdd(transfer.startIntakeFire())
                .waitSeconds(0.4)
                .stopAndAdd(transfer.stopIntake())

                // Check first stack
                .stopAndAdd(transfer.startIntake())
                .strafeToLinearHeading(new Vector2d(-47,0), Math.toRadians(180))
                .strafeToLinearHeading(new Vector2d(0,0), Math.toRadians(180))
                .stopAndAdd(transfer.stopIntake())

                // Shoot fourth

                .stopAndAdd(transfer.startIntakeFire())
                .waitSeconds(0.4)
                .stopAndAdd(transfer.stopIntake())
                .waitSeconds(0.5)
                .stopAndAdd(transfer.startIntakeFire())
                .waitSeconds(0.4)
                .stopAndAdd(transfer.stopIntake())
                .waitSeconds(0.5)
                .stopAndAdd(transfer.startIntakeFire())
                .waitSeconds(0.4)
                .stopAndAdd(transfer.stopIntake())

                .stopAndAdd(shooter.stopShooting())

                .endTrajectory();

        waitForStart();

        StaticVariables.setLastId(Constants.BLUE_SHOOT_ID);

        Actions.runBlocking(
                new SequentialAction(
                        wholeTrajectory.build()
                )
        );
    }
}
