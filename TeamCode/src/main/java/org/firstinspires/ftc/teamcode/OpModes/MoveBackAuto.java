package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.Drivebase;
import org.firstinspires.ftc.teamcode.Util.Constants;
import org.firstinspires.ftc.teamcode.Util.StaticVariables;

@Autonomous(name="Move Back Auto")
public class MoveBackAuto extends LinearOpMode {
    private Drivebase drivebase;

    @Override
    public void runOpMode() {
        drivebase = new Drivebase(hardwareMap, telemetry);

        waitForStart();
        StaticVariables.setLastId(Constants.RED_SHOOT_ID);

        drivebase.powerMotors(-0.6);
        sleep(500);
        drivebase.powerMotors(0);
    }
}
