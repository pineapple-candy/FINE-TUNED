package org.firstinspires.ftc.teamcode.Util;

public class StaticVariables {

    private static int sideID = 0;
    public static void setLastId(int id) {
        sideID = id;
    }
    public static int getLastId() {
        return sideID;
    }

}