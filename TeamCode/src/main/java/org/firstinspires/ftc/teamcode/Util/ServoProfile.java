package org.firstinspires.ftc.teamcode.Util;

public class ServoProfile {
    private String servoName;
    private ServoProfile(ServoProfileBuilder Builder) {
        this.servoName = Builder.servoName;
    }
    public String getServoName() {
        return servoName;
    }
    public static class ServoProfileBuilder {
        private String servoName = "NA";

        public ServoProfileBuilder setServoName(String name){
            this.servoName = name;
            return this;
        }
        public ServoProfile build(){
            return new ServoProfile(this);
        }
    }
}