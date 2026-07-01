    package org.firstinspires.ftc.teamcode.Subsystems;

    import androidx.annotation.NonNull;

    import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
    import com.acmerobotics.roadrunner.Action;
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
        double turretOffset=-0.04;
        boolean activated = false;
        double previousPosition=0.5;
        double SECOND_SERVO_OFFSET = -0.085;
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
            turretservo1.setPosition(servoposition+turretOffset+SECOND_SERVO_OFFSET);
            turretservo2.setPosition(servoposition+turretOffset);

        }

        public void update(Gamepad gamepad, double turretpos){
           if (gamepad.dpad_right&&!dPadUp){
                turretOffset += 0.025;
//                SECOND_SERVO_OFFSET=SECOND_SERVO_OFFSET+0.025;
                activated = true;
            }

            if (gamepad.dpad_left&&!dPadDown){
                turretOffset -= 0.025;
            }

            if (gamepad.x) {
                turretOffset=0;
            }

            dPadUp = gamepad.dpad_right;
            dPadDown = gamepad.dpad_left;

            setTurretAngle(turretpos+turretOffset);

            telemetry.addData("turret", turretpos+turretOffset);
            telemetry.addData("offset", SECOND_SERVO_OFFSET);
            previousPosition = turretpos + turretOffset;
        } // 0.63

        public class farZone implements Action {
            private double turretPosition;
            private boolean blueOrLeft=true;
            public farZone (boolean BlueOrLeft) {
            this.blueOrLeft=blueOrLeft;
            }
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (blueOrLeft) {
                    setTurretAngle(0.16);
                } else{
                    setTurretAngle(0.84);
                }
                return false;
            }
        }

        public Action farZone(boolean blueOrRed) {return new Turret.farZone(blueOrRed);}
    }
