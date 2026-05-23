package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Subsystems.Drivebase;
import org.firstinspires.ftc.teamcode.Subsystems.Transfer;

@TeleOp(name="Main")
public class Main extends LinearOpMode {

    private Drivebase drivebase;
    private Transfer transfer;

    @Override
    public void runOpMode() {
        drivebase = new Drivebase(hardwareMap);
        transfer = new Transfer(hardwareMap);
        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            drivebase.update(gamepad1,gamepad2);
            transfer.update(gamepad1);
        }

    }
}