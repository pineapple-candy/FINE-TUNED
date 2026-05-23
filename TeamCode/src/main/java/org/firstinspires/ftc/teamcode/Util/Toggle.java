package org.firstinspires.ftc.teamcode.Util;

public class Toggle {

    private boolean debounce;

    public Toggle() {}

    public boolean runToggle(boolean button) {
        if (button && !debounce) {
            debounce = button;
            return true;
        }

        debounce = button;
        return false;
    }

}