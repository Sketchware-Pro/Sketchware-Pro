package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;

import com.google.gson.annotations.Expose;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ProjectFileBean extends SelectableBean implements Parcelable {
    public static final Creator<ProjectFileBean> CREATOR = new Creator<>() {
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
    @ActivityOption
    public int options = OPTION_ACTIVITY_SHIFT;
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
            options |= OPTION_ACTIVITY_TOOLBAR;
            orientation = THEME_DEFAULT;
        } else {
            orientation = THEME_FULLSCREEN;
        }
        keyboardSetting = THEME_DEFAULT;
        theme = THEME_NONE;
    }

    public ProjectFileBean(int fileType, String filename, int orientation, int keyboardSetting, @ActivityOption int options) {
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
            options |= OPTION_ACTIVITY_TOOLBAR;
        }
        if (fullscreen) {
            options |= OPTION_ACTIVITY_FULLSCREEN;
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
            options |= OPTION_ACTIVITY_TOOLBAR;
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
            options |= OPTION_ACTIVITY_TOOLBAR;
        }
        if (fullscreen) {
            options |= OPTION_ACTIVITY_FULLSCREEN;
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
        while (name.contains("_")) {
            int index = name.indexOf('_');
            if (index + 1 == name.length()) {
                name = name.substring(0, name.length() - 1);
            } else {
                String firstPart = index == 0 ? "" : name.substring(0, index);
                char camelCase = Character.toUpperCase(name.charAt(index + 1));
                String lastPart = name.substring(index + 2);
                name = firstPart + camelCase + lastPart;
            }
        }
        name += "Activity";
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
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

    public void setActivityOptions(@ActivityOption int options) {
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

    public boolean hasActivityOption(@ActivityOption int option) {
        return ((options & OPTION_ACTIVITY_MASK) & option) == option;
    }

    public void print() {
    }

    public void setOptionsByTheme() {
        if (theme != THEME_NONE) {
            options = OPTION_ACTIVITY_SHIFT;
            if (theme == THEME_DEFAULT) {
                options |= OPTION_ACTIVITY_TOOLBAR;
            } else if (theme != THEME_NOACTIONBAR) {
                options |= OPTION_ACTIVITY_FULLSCREEN;
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

    @IntDef(flag = true,
            value = {OPTION_ACTIVITY_FAB,
                    OPTION_ACTIVITY_DRAWER,
                    OPTION_ACTIVITY_MASK,
                    OPTION_ACTIVITY_FULLSCREEN,
                    OPTION_ACTIVITY_SHIFT,
                    OPTION_ACTIVITY_TOOLBAR})
    @Retention(RetentionPolicy.SOURCE)
    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
    private @interface ActivityOption {
    }
}
