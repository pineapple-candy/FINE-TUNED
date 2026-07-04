package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Drivebase;

@Autonomous(name="Move Back Auto")
public class MoveBackAuto extends LinearOpMode {
    private Drivebase drivebase;

    @Override
    public void runOpMode() {
        drivebase = new Drivebase(hardwareMap, telemetry);

        waitForStart();

        drivebase.powerMotors(-0.6);
        sleep(2000);
        drivebase.powerMotors(0);
    }
}
