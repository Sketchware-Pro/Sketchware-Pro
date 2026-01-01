package com.besome.sketch;

import android.os.Environment;

import java.io.File;

public class Config {
    public static final int VAR_DEFAULT_MIN_SDK_VERSION = 21;
    public static final int VAR_DEFAULT_TARGET_SDK_VERSION = 34;

    public static String getProjectSettingsPath(String sc_id) {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/" + sc_id + "/project_config").getAbsolutePath();
    }
}
