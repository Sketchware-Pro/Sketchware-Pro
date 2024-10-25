package mod.hey.studios.project;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;

import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;

public class ProjectSettings {

    /**
     * Setting for the final app's {@code minSdkVersion}
     *
     * @see ApplicationInfo#minSdkVersion
     */
    public static final String SETTING_MINIMUM_SDK_VERSION = "min_sdk";

    /**
     * Setting to make the app's main theme inherit from fully material-styled themes, and not *.Bridge ones
     */
    public static final String SETTING_ENABLE_BRIDGELESS_THEMES = "enable_bridgeless_themes";

    /**
     * Setting for the final app's {@link Application} class
     *
     * @see Application
     */
    public static final String SETTING_APPLICATION_CLASS = "app_class";

    /**
     * Setting for the final app's {@code targetSdkVersion}
     *
     * @see ApplicationInfo#targetSdkVersion
     */
    public static final String SETTING_TARGET_SDK_VERSION = "target_sdk";

    /**
     * Setting to disable showing deprecated methods included in every generated class, e.g. showMessage(String)
     */
    public static final String SETTING_DISABLE_OLD_METHODS = "disable_old_methods";
    public static final String SETTING_GENERIC_VALUE_TRUE = "true";
    public static final String SETTING_GENERIC_VALUE_FALSE = "false";
    private static final String TAG = "ProjectSettings";
    private final String path;
    public String sc_id;
    private HashMap<String, String> hashmap;

    public ProjectSettings(String s) {
        sc_id = s;

        path = getPath();

        if (FileUtil.isExistFile(path)) {
            try {
                hashmap = new Gson().fromJson(FileUtil.readFile(path).trim(), Helper.TYPE_STRING_MAP);
            } catch (Exception e) {
                Log.e("ProjectSettings", "Failed to read project settings for project " + sc_id + "!", e);
                hashmap = new HashMap<>();
                save();
            }
        } else {
            hashmap = new HashMap<>();
        }
    }

    /**
     * @return The configured minimum SDK version. Returns 21 if none or an invalid value was set.
     * @see #SETTING_MINIMUM_SDK_VERSION
     */
    public int getMinSdkVersion() {
        if (hashmap.containsKey(SETTING_MINIMUM_SDK_VERSION)) {
            try {
                //noinspection ConstantConditions because we catch that already
                return Integer.parseInt(hashmap.get(SETTING_MINIMUM_SDK_VERSION));
            } catch (NumberFormatException | NullPointerException e) {
                LogUtil.e(TAG, "Failed to parse the project's minimum SDK version! Defaulting to 21", e);
                return 21;
            }
        } else {
            return 21;
        }
    }

    public String getPath() {
        return new File(Environment.getExternalStorageDirectory(), ".sketchware/data/" + sc_id + "/project_config").getAbsolutePath();
    }

    public String getValue(String key, String defaultValue) {
        if (hashmap.containsKey(key)) {
            if (!hashmap.get(key).isEmpty()) {
                return hashmap.get(key);
            } else {
                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public void setValues(View... views) {
        for (View v : views) {
            if (v.getTag() != null) {
                String key = (String) v.getTag();  // v.getTag(0);
                //String value = (String) v.getTag(1);
                String value;

                if (v instanceof EditText) {
                    value = ((EditText) v).getText().toString();
                } else if (v instanceof CheckBox) {
                    value = ((CheckBox) v).isChecked() ? "true" : "false";
                } else if (v instanceof RadioGroup) {

                    value = getCheckedRbValue((RadioGroup) v);


                    //value = ((RadioGroup)v).getCheckedRadioButtonId()
                } else {
                    continue;
                }

                hashmap.put(key, value);
            }
        }
        save();
    }

    private String getCheckedRbValue(RadioGroup rg) {
        for (int i = 0; i < rg.getChildCount(); i++) {
            RadioButton rb = (RadioButton) rg.getChildAt(i);

            if (rb.isChecked()) {
                return rb.getText().toString();
            }
        }

        return "";
    }

    private void save() {
        FileUtil.writeFile(path, new Gson().toJson(hashmap));
    }
}
