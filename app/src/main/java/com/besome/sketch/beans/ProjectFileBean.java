package com.besome.sketch.beans;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;

public class ProjectFileBean extends SelectableBean implements Parcelable {

	public static final Creator<ProjectFileBean> CREATOR = new Creator<ProjectFileBean>() {
		public ProjectFileBean createFromParcel(Parcel parcel) {
			return new ProjectFileBean(parcel);
		}

		public ProjectFileBean[] newArray(int i) {
			return new ProjectFileBean[i];
		}
	};
	//Keyboard Options
	public static final int KEYBOARD_STATE_HIDDEN = 2;
	public static final int KEYBOARD_STATE_UNSPECIFIED = 0;
	public static final int KEYBOARD_STATE_VISIBLE = 1;
	//Theme Features
	public static final int OPTION_ACTIVITY_DRAWER = 4;
	public static final int OPTION_ACTIVITY_FAB = 8;
	public static final int OPTION_ACTIVITY_FULLSCREEN = 2;
	public static final int OPTION_ACTIVITY_MASK = 15;
	public static final int OPTION_ACTIVITY_SHIFT = 0;
	public static final int OPTION_ACTIVITY_TOOLBAR = 1;
	//Theme Orientation
	public static final int ORIENTATION_BOTH = 2;
	public static final int ORIENTATION_LANDSCAPE = 1;
	public static final int ORIENTATION_PORTRAIT = 0;
	//Project File Type
	public static final int PROJECT_FILE_TYPE_ACTIVITY = 0;
	public static final int PROJECT_FILE_TYPE_CUSTOM_VIEW = 1;
	public static final int PROJECT_FILE_TYPE_DRAWER = 2;
	//Theme Styles
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

	public ProjectFileBean(int i, String str) {
		this.fileType = i;
		this.fileName = str;
		if (i == THEME_NOACTIONBAR) {
			this.presetName = "Basic List Item";
		} else {
			this.presetName = "Basic Drawer";
		}
		if (i == 0) {
			this.options |= THEME_NOACTIONBAR;
			this.orientation = THEME_DEFAULT;
		} else {
			this.orientation = THEME_FULLSCREEN;
		}
		this.keyboardSetting = THEME_DEFAULT;
		this.theme = THEME_NONE;
	}

	public ProjectFileBean(int i, String str, int i2, int i3, int i4) {
		this.fileType = i;
		this.fileName = str;
		this.orientation = i2;
		this.keyboardSetting = i3;
		this.options = i4;
		this.presetName = "Basic Activity";
		this.theme = THEME_NONE;
	}

	public ProjectFileBean(int i, String str, int i2, int i3, boolean z, boolean z2, boolean z3, boolean z4) {
		this.fileType = i;
		this.fileName = str;
		this.orientation = i2;
		this.keyboardSetting = i3;
		this.presetName = "Basic Activity";
		this.theme = THEME_NONE;
		if (z) {
			this.options |= THEME_NOACTIONBAR;
		}
		if (z2) {
			this.options |= THEME_FULLSCREEN;
		}
		if (z3) {
			this.options |= OPTION_ACTIVITY_FAB;
		}
		if (z4) {
			this.options |= OPTION_ACTIVITY_DRAWER;
		}
	}

	public ProjectFileBean(int mFileType, String mFileName, String mPresetName) {
		this.fileType = mFileType;
		this.fileName = mFileName;
		this.presetName = mPresetName;
		if (mFileType == 0) {
			this.options |= THEME_NOACTIONBAR;
			this.orientation = THEME_DEFAULT;
		} else {
			this.orientation = THEME_FULLSCREEN;
		}
		this.keyboardSetting = THEME_DEFAULT;
		this.theme = THEME_NONE;
	}

	public ProjectFileBean(int i, String str, String str2, int i2, int i3, boolean z, boolean z2, boolean z3, boolean z4) {
		this.fileType = i;
		this.fileName = str;
		this.orientation = i2;
		this.keyboardSetting = i3;
		this.presetName = str2;
		this.theme = THEME_NONE;
		if (z) {
			this.options |= THEME_NOACTIONBAR;
		}
		if (z2) {
			this.options |= THEME_FULLSCREEN;
		}
		if (z3) {
			this.options |= OPTION_ACTIVITY_FAB;
		}
		if (z4) {
			this.options |= OPTION_ACTIVITY_DRAWER;
		}
	}

	public ProjectFileBean(Parcel parcel) {
		this.fileType = parcel.readInt();
		this.fileName = parcel.readString();
		this.orientation = parcel.readInt();
		this.keyboardSetting = parcel.readInt();
		this.options = parcel.readInt();
		this.presetName = parcel.readString();
	}

	public static String getActivityName(String str) {
		StringBuilder stringBuilder;
		String toLowerCase = str.toLowerCase();
		int i = THEME_DEFAULT;
		String str2 = "";
		while (i < toLowerCase.length()) {
			char charAt = toLowerCase.charAt(i);
			StringBuilder stringBuilder2;
			if (charAt == '_' && i < toLowerCase.length() + THEME_NONE) {
				int i2 = i + THEME_NOACTIONBAR;
				char charAt2 = toLowerCase.charAt(i2);
				if (Character.isLowerCase(charAt2)) {
					stringBuilder = new StringBuilder();
					stringBuilder.append(str2);
					stringBuilder.append(Character.toUpperCase(charAt2));
					str2 = stringBuilder.toString();
					i = i2;
				} else {
					stringBuilder2 = new StringBuilder();
					stringBuilder2.append(str2);
					stringBuilder2.append(charAt);
					str2 = stringBuilder2.toString();
				}
			} else if (i == 0) {
				stringBuilder2 = new StringBuilder();
				stringBuilder2.append(str2);
				stringBuilder2.append(Character.toUpperCase(charAt));
				str2 = stringBuilder2.toString();
			} else {
				stringBuilder2 = new StringBuilder();
				stringBuilder2.append(str2);
				stringBuilder2.append(charAt);
				str2 = stringBuilder2.toString();
			}
			i += THEME_NOACTIONBAR;
		}
		stringBuilder = new StringBuilder();
		stringBuilder.append(str2);
		stringBuilder.append(str.contains("_fragment") ? "" : "Activity");
		return stringBuilder.toString();
	}

	public static String getActivityNameOld(String str) {
		StringBuilder stringBuilder;
		String toLowerCase = str.toLowerCase();
		int i = THEME_DEFAULT;
		String str2 = "";
		while (i < toLowerCase.length()) {
			int i2;
			String stringBuilder2;
			char charAt = toLowerCase.charAt(i);
			int i3;
			if (charAt == '_' && i < toLowerCase.length() + THEME_NONE) {
				i2 = i + THEME_NOACTIONBAR;
				char charAt2 = toLowerCase.charAt(i2);
				if (Character.isLowerCase(charAt2)) {
					StringBuilder stringBuilder3 = new StringBuilder();
					stringBuilder3.append(str2);
					stringBuilder3.append(Character.toUpperCase(charAt2));
					stringBuilder2 = stringBuilder3.toString();
				} else {
					stringBuilder = new StringBuilder();
					stringBuilder.append(str2);
					stringBuilder.append(charAt);
					i3 = i;
					stringBuilder2 = stringBuilder.toString();
					i2 = i3;
				}
			} else if (i == 0) {
				stringBuilder = new StringBuilder();
				stringBuilder.append(str2);
				stringBuilder.append(Character.toUpperCase(charAt));
				i3 = i;
				stringBuilder2 = stringBuilder.toString();
				i2 = i3;
			} else {
				stringBuilder = new StringBuilder();
				stringBuilder.append(str2);
				stringBuilder.append(charAt);
				i3 = i;
				stringBuilder2 = stringBuilder.toString();
				i2 = i3;
			}
			str2 = stringBuilder2;
			i = i2 + THEME_NOACTIONBAR;
		}
		stringBuilder = new StringBuilder();
		stringBuilder.append(str2);
		stringBuilder.append("Activity");
		return stringBuilder.toString();
	}

	public static Creator<ProjectFileBean> getCreator() {
		return CREATOR;
	}

	public static String getDrawerName(String str) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("_drawer_");
		stringBuilder.append(str);
		return stringBuilder.toString();
	}

	public static String getJavaName(String str) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getActivityName(str));
		stringBuilder.append(".java");
		return stringBuilder.toString();
	}

	public static String getXmlName(String str) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(str.toLowerCase());
		stringBuilder.append(".xml");
		return stringBuilder.toString();
	}

	public void copy(ProjectFileBean projectFileBean) {
		this.fileType = projectFileBean.fileType;
		this.fileName = projectFileBean.fileName;
		this.orientation = projectFileBean.orientation;
		this.keyboardSetting = projectFileBean.keyboardSetting;
		this.options = projectFileBean.options;
		this.presetName = projectFileBean.presetName;
		this.theme = THEME_NONE;
	}

	public int describeContents() {
		return THEME_DEFAULT;
	}

	public String getActivityName() {
		return this.fileType != 0 ? "" : getActivityName(this.fileName);
	}

	public int getActivityOptions() {
		return this.options;
	}

	public String getDrawerName() {
		return this.fileType != 0 ? "" : getDrawerName(this.fileName);
	}

	public String getDrawerXmlName() {
		if (this.fileType != 0) {
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getDrawerName());
		stringBuilder.append(".xml");
		return stringBuilder.toString();
	}

	public String getDrawersJavaName() {
		return this.fileType != THEME_FULLSCREEN ? "" : this.fileName.indexOf("_drawer_") < 0 ? "" : getJavaName(this.fileName.substring(OPTION_ACTIVITY_FAB));
	}

	public String getJavaName() {
		return this.fileType != 0 ? "" : getJavaName(this.fileName);
	}

	public String getXmlName() {
		return getXmlName(this.fileName);
	}

	public boolean hasActivityOption(int i) {
		return ((this.options & OPTION_ACTIVITY_MASK) & i) == i;
	}

	public void print() {
	}

	public void setActivityOptions(int i) {
		this.options = i;
	}

	public void setOptionsByTheme() {
		int i = this.theme;
		if (i != THEME_NONE) {
			this.options = THEME_DEFAULT;
			if (i == 0) {
				this.options |= THEME_NOACTIONBAR;
			} else if (i != THEME_NOACTIONBAR) {
				this.options |= THEME_FULLSCREEN;
			}
			this.theme = THEME_NONE;
		}
	}

	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeInt(this.fileType);
		parcel.writeString(this.fileName);
		parcel.writeInt(this.orientation);
		parcel.writeInt(this.keyboardSetting);
		parcel.writeInt(this.options);
		parcel.writeString(this.presetName);
	}
}