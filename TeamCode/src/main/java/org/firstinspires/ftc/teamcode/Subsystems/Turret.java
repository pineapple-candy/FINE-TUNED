    package org.firstinspires.ftc.teamcode.Subsystems;

    import com.qualcomm.robotcore.hardware.Gamepad;
    import com.qualcomm.robotcore.hardware.HardwareMap;
    import com.qualcomm.robotcore.hardware.Servo;

    import org.firstinspires.ftc.robotcore.external.Telemetry;

    public class Turret {

        private Servo turretservo1;
        private Servo turretservo2;
        private double hoodPosition=0.5;
        boolean dPadUp = false;
        boolean dPadDown = false;

        boolean activated = false;
        Telemetry telemetry;
        public Turret(HardwareMap hardwaremap, Telemetry telemetry){
            turretservo1 = hardwaremap.get(Servo.class, "turretservo2");
            turretservo2 = hardwaremap.get(Servo.class, "turretservo1");
            this.telemetry = telemetry;
        }
        public void setTurretAngle(double hoodPosition){

            if(hoodPosition>1){
                hoodPosition=1;
            }
            if(hoodPosition<0){
                hoodPosition=0;
            }
            this.hoodPosition=hoodPosition;
            double servoposition = hoodPosition;//do funky stuff here at some point
            turretservo1.setPosition(servoposition);
            turretservo2.setPosition(servoposition);

        }

        public void update(Gamepad gamepad){
            telemetry.addLine("OOOHH TESTING GHOST");
            telemetry.addData("1", gamepad.dpad_up);
            telemetry.addData("2", dPadUp);
            telemetry.addData("imposition", turretservo1.getPosition());
            if (gamepad.dpad_right&&!dPadUp){
                telemetry.addLine("it's working!");
                setTurretAngle(hoodPosition+0.1);
                activated = true;
            }
            if(activated) {
                telemetry.addLine("GRAHHHHHHH");
            }
            if (gamepad.dpad_left&&!dPadDown){
                setTurretAngle(hoodPosition-0.1);
                telemetry.addLine("something sinister");
            }
            dPadUp = gamepad.dpad_right;
            dPadDown = gamepad.dpad_left;
        }
    }
