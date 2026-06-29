package org.firstinspires.ftc.teamcode.Util;

public class StaticVariables {

    private static int sideID = Constants.RED_SHOOT_ID;
    public static void setLastId(int id) {
        sideID = id;
    }
    public static int getLastId() {
        return sideID;
    }

}