package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class ProjectFileBean extends SelectableBean implements Parcelable {

    public static final Creator<ProjectFileBean> CREATOR = new Creator<ProjectFileBean>() {
        @Override
        public ProjectFileBean createFromParcel(Parcel source) {
            return new ProjectFileBean(source);
        }

        @Override
        public ProjectFileBean[] newArray(int size) {
            return new ProjectFileBean[size];
        }
    };

    public static final int KEYBOARD_STATE_HIDDEN = 2;
    public static final int KEYBOARD_STATE_UNSPECIFIED = 0;
    public static final int KEYBOARD_STATE_VISIBLE = 1;

    public static final int OPTION_ACTIVITY_DRAWER = 4;
    public static final int OPTION_ACTIVITY_FAB = 8;
    public static final int OPTION_ACTIVITY_FULLSCREEN = 2;
    public static final int OPTION_ACTIVITY_MASK = 15;
    public static final int OPTION_ACTIVITY_SHIFT = 0;
    public static final int OPTION_ACTIVITY_TOOLBAR = 1;

    public static final int ORIENTATION_BOTH = 2;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 0;

    public static final int PROJECT_FILE_TYPE_ACTIVITY = 0;
    public static final int PROJECT_FILE_TYPE_CUSTOM_VIEW = 1;
    public static final int PROJECT_FILE_TYPE_DRAWER = 2;

    public static final int THEME_DEFAULT = 0;
    public static final int THEME_FULLSCREEN = 2;
    public static final int THEME_NOACTIONBAR = 1;
    public static final int THEME_NONE = -1;
    @Expose
    public String fileName;
    @Expose
    public int fileType;
    @Expose
    public int keyboardSetting;
    @Expose
    public int options = THEME_DEFAULT;
    @Expose
    public int orientation;
    public String presetName;
    @Expose
    @Deprecated
    public int theme = THEME_NONE;

    public ProjectFileBean(int fileType, String filename) {
        this.fileType = fileType;
        fileName = filename;
        if (fileType == THEME_NOACTIONBAR) {
            presetName = "Basic List Item";
        } else {
            presetName = "Basic Drawer";
        }
        if (fileType == PROJECT_FILE_TYPE_ACTIVITY) {
            options |= THEME_NOACTIONBAR;
            orientation = THEME_DEFAULT;
        } else {
            orientation = THEME_FULLSCREEN;
        }
        keyboardSetting = THEME_DEFAULT;
        theme = THEME_NONE;
    }

    public ProjectFileBean(int fileType, String filename, int orientation, int keyboardSetting, int options) {
        this.fileType = fileType;
        fileName = filename;
        this.orientation = orientation;
        this.keyboardSetting = keyboardSetting;
        this.options = options;
        presetName = "Basic Activity";
        theme = THEME_NONE;
    }

    public ProjectFileBean(int fileType, String filename, int orientation, int keyboardSetting, boolean noActionBar, boolean fullscreen, boolean hasFab, boolean hasDrawer) {
        this.fileType = fileType;
        fileName = filename;
        this.orientation = orientation;
        this.keyboardSetting = keyboardSetting;
        presetName = "Basic Activity";
        theme = THEME_NONE;
        if (noActionBar) {
            options |= THEME_NOACTIONBAR;
        }
        if (fullscreen) {
            options |= THEME_FULLSCREEN;
        }
        if (hasFab) {
            options |= OPTION_ACTIVITY_FAB;
        }
        if (hasDrawer) {
            options |= OPTION_ACTIVITY_DRAWER;
        }
    }

    public ProjectFileBean(int fileType, String filename, String presetName) {
        this.fileType = fileType;
        fileName = filename;
        this.presetName = presetName;
        if (fileType == PROJECT_FILE_TYPE_ACTIVITY) {
            options |= THEME_NOACTIONBAR;
            orientation = THEME_DEFAULT;
        } else {
            orientation = THEME_FULLSCREEN;
        }
        keyboardSetting = THEME_DEFAULT;
        theme = THEME_NONE;
    }

    public ProjectFileBean(int fileType, String filename, String presetName, int orientation, int keyboardSetting, boolean noActionBar, boolean fullscreen, boolean hasFab, boolean hasDrawer) {
        this.fileType = fileType;
        fileName = filename;
        this.orientation = orientation;
        this.keyboardSetting = keyboardSetting;
        this.presetName = presetName;
        theme = THEME_NONE;
        if (noActionBar) {
            options |= THEME_NOACTIONBAR;
        }
        if (fullscreen) {
            options |= THEME_FULLSCREEN;
        }
        if (hasFab) {
            options |= OPTION_ACTIVITY_FAB;
        }
        if (hasDrawer) {
            options |= OPTION_ACTIVITY_DRAWER;
        }
    }

    public ProjectFileBean(Parcel parcel) {
        fileType = parcel.readInt();
        fileName = parcel.readString();
        orientation = parcel.readInt();
        keyboardSetting = parcel.readInt();
        options = parcel.readInt();
        presetName = parcel.readString();
    }

    public static String getActivityName(String name) {
        name = name.toLowerCase();
        StringBuilder activityName = new StringBuilder();
        int i = 0;
        while (i < name.length()) {
            int j;
            char charAt = name.charAt(i);
            if (charAt == '_' && i < name.length() - 1) {
                j = i + 1;
                char charAt2 = name.charAt(j);
                if (Character.isLowerCase(charAt2)) {
                    activityName.append(Character.toUpperCase(charAt2));
                } else {
                    activityName.append(charAt);
                    j = i;
                }
            } else if (i == 0) {
                activityName.append(Character.toUpperCase(charAt));
                j = i;
            } else {
                activityName.append(charAt);
                j = i;
            }
            i = j + 1;
        }
        return activityName + "Activity";
    }

    public static Creator<ProjectFileBean> getCreator() {
        return CREATOR;
    }

    public static String getDrawerName(String filename) {
        return "_drawer_" + filename;
    }

    public static String getJavaName(String filename) {
        return getActivityName(filename) + ".java";
    }

    public static String getXmlName(String filename) {
        return filename + ".xml";
    }

    public void copy(ProjectFileBean bean) {
        fileType = bean.fileType;
        fileName = bean.fileName;
        orientation = bean.orientation;
        keyboardSetting = bean.keyboardSetting;
        options = bean.options;
        presetName = bean.presetName;
        theme = THEME_NONE;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getActivityName() {
        return fileType != PROJECT_FILE_TYPE_ACTIVITY ? "" : getActivityName(fileName);
    }

    public int getActivityOptions() {
        return options;
    }

    public void setActivityOptions(int options) {
        this.options = options;
    }

    public String getDrawerName() {
        return fileType != PROJECT_FILE_TYPE_ACTIVITY ? "" : getDrawerName(fileName);
    }

    public String getDrawerXmlName() {
        return fileType != PROJECT_FILE_TYPE_ACTIVITY ? "" : getDrawerName() + ".xml";
    }

    public String getDrawersJavaName() {
        return fileType != THEME_FULLSCREEN ? "" : !fileName.contains("_drawer_") ? "" :
                getJavaName(fileName.substring(OPTION_ACTIVITY_FAB));
    }

    public String getJavaName() {
        return fileType != PROJECT_FILE_TYPE_ACTIVITY ? "" : getJavaName(fileName);
    }

    public String getXmlName() {
        return getXmlName(fileName);
    }

    public boolean hasActivityOption(int option) {
        return ((options & OPTION_ACTIVITY_MASK) & option) == option;
    }

    public void print() {
    }

    public void setOptionsByTheme() {
        if (theme != THEME_NONE) {
            options = THEME_DEFAULT;
            if (theme == THEME_DEFAULT) {
                options |= THEME_NOACTIONBAR;
            } else if (theme != THEME_NOACTIONBAR) {
                options |= THEME_FULLSCREEN;
            }
            theme = THEME_NONE;
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(fileType);
        dest.writeString(fileName);
        dest.writeInt(orientation);
        dest.writeInt(keyboardSetting);
        dest.writeInt(options);
        dest.writeString(presetName);
    }
}
