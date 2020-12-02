package org.geysermc.cumulus.util;

import org.geysermc.cumulus.Forms;

public class FormsInstanceHolder {
    private static Forms instance;

    public static Forms get() {
        return instance;
    }

    public static void set(Forms instance) {
        //todo maybe a debug log or an option to disable/lock it?
        FormsInstanceHolder.instance = instance;
    }
}
