package mod.hey.studios.build;

import static mod.hey.studios.build.BuildSettings.SETTING_ANDROID_JAR_PATH;
import static mod.hey.studios.build.BuildSettings.SETTING_CLASSPATH;
import static mod.hey.studios.build.BuildSettings.SETTING_DEXER;
import static mod.hey.studios.build.BuildSettings.SETTING_ENABLE_LOGCAT;
import static mod.hey.studios.build.BuildSettings.SETTING_JAVA_VERSION;
import static mod.hey.studios.build.BuildSettings.SETTING_JAVA_VERSION_10;
import static mod.hey.studios.build.BuildSettings.SETTING_JAVA_VERSION_11;
import static mod.hey.studios.build.BuildSettings.SETTING_JAVA_VERSION_1_7;
import static mod.hey.studios.build.BuildSettings.SETTING_JAVA_VERSION_1_8;
import static mod.hey.studios.build.BuildSettings.SETTING_JAVA_VERSION_1_9;
import static mod.hey.studios.build.BuildSettings.SETTING_NO_HTTP_LEGACY;
import static mod.hey.studios.build.BuildSettings.SETTING_NO_WARNINGS;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import pro.sketchware.R;
import pro.sketchware.databinding.ProjectConfigLayoutBinding;
import pro.sketchware.utility.SketchwareUtil;

public class BuildSettingsDialog {
    private static int totalViews = 0;

    private static final int VIEW_ANDROIR_JAR_PATH = totalViews++;
    private static final int VIEW_CLASS_PATH = totalViews++;
    private static final int VIEW_DEXER = totalViews++;
    private static final int VIEW_JAVA_VERSION = totalViews++;
    private static final int VIEW_NO_WARNINGS = totalViews++;
    private static final int VIEW_NO_HTTP_LEGACY = totalViews++;
    private static final int VIEW_ENABLE_LOGCAT = totalViews++;

    private final Activity activity;
    private final BuildSettings settings;
    private final ProjectConfigLayoutBinding binding;
    private final View[] views;

    public BuildSettingsDialog(Activity activity, String sc_id) {
        this.activity = activity;

        settings = new BuildSettings(sc_id);
        views = new View[totalViews];
        binding = ProjectConfigLayoutBinding.inflate(activity.getLayoutInflater());

        views[VIEW_ANDROIR_JAR_PATH] = binding.tilAndroidJar.getEditText();
        views[VIEW_CLASS_PATH] = binding.tilClasspath.getEditText();
        views[VIEW_DEXER] = binding.rgDexer;
        views[VIEW_ENABLE_LOGCAT] = binding.cbEnableLogcat;
        views[VIEW_JAVA_VERSION] = binding.rgJavaVersion;
        views[VIEW_NO_HTTP_LEGACY] = binding.cbNoHttpLegacy;
        views[VIEW_NO_WARNINGS] = binding.cbNoWarnings;

        // necessary for mod.hey.studios.project.ProjectSettings, since it checks
        // for the presence of a tag if missing it cannot save the data
        addTagsForViews();
    }

    public static String[] getAvailableJavaVersions() {
        return new String[]{
                SETTING_JAVA_VERSION_1_7,
                SETTING_JAVA_VERSION_1_8,
                SETTING_JAVA_VERSION_1_9,
                SETTING_JAVA_VERSION_10,
                SETTING_JAVA_VERSION_11,
        };
    }

    public static void handleJavaVersionChange(String choice) {
        if (!choice.equals(SETTING_JAVA_VERSION_1_7)) {
            SketchwareUtil.toast("Don't forget to enable D8 to be able to compile Java 8+ code");
        }
    }

    private void addTagsForViews() {
        views[VIEW_ANDROIR_JAR_PATH].setTag(SETTING_ANDROID_JAR_PATH);
        views[VIEW_CLASS_PATH].setTag(SETTING_CLASSPATH);
        views[VIEW_DEXER].setTag(SETTING_DEXER);
        views[VIEW_ENABLE_LOGCAT].setTag(SETTING_ENABLE_LOGCAT);
        views[VIEW_JAVA_VERSION].setTag(SETTING_JAVA_VERSION);
        views[VIEW_NO_HTTP_LEGACY].setTag(SETTING_NO_HTTP_LEGACY);
        views[VIEW_NO_WARNINGS].setTag(SETTING_NO_WARNINGS);
    }

    public void show() {
        assert views != null || views.length != 0;

        getEditText(VIEW_ANDROIR_JAR_PATH).setText(settings.getValue(SETTING_ANDROID_JAR_PATH, ""));
        getEditText(VIEW_CLASS_PATH).setText(settings.getValue(SETTING_CLASSPATH, ""));

        setRadioGroupOptions(getRadioGroup(VIEW_DEXER), new String[]{"Dx", "D8"}, SETTING_DEXER, "Dx");
        setRadioGroupOptions(getRadioGroup(VIEW_JAVA_VERSION), getAvailableJavaVersions(), SETTING_JAVA_VERSION, "1.7");

        setCheckboxValue(getCheckbox(VIEW_NO_WARNINGS), SETTING_NO_WARNINGS, true);
        setCheckboxValue(getCheckbox(VIEW_NO_HTTP_LEGACY), SETTING_NO_HTTP_LEGACY, false);
        setCheckboxValue(getCheckbox(VIEW_ENABLE_LOGCAT), SETTING_ENABLE_LOGCAT, true);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity);
        builder.setIcon(R.drawable.ic_mtrl_tune);
        builder.setTitle("Build Settings");
        builder.setPositiveButton("Save", (d, which) -> settings.setValues(views));
        builder.setNegativeButton("Cancel", null);
        builder.setView(binding.getRoot());
        builder.show();
    }

    private EditText getEditText(int index) {
        return (EditText) views[index];
    }

    private RadioGroup getRadioGroup(int index) {
        return (RadioGroup) views[index];
    }

    private CheckBox getCheckbox(int index) {
        return (CheckBox) views[index];
    }

    private void setRadioGroupOptions(RadioGroup radioGroup, String[] options, String key, String defaultValue) {
        radioGroup.removeAllViews();
        String value = settings.getValue(key, defaultValue);
        for (String option : options) {
            RadioButton radioButton = new RadioButton(radioGroup.getContext());
            radioButton.setText(option);
            radioButton.setId(View.generateViewId());
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(0, -2, 1f));
            if (value.equals(option)) {
                radioButton.setChecked(true);
            }
            radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!isChecked) return;
                if (key.equals(SETTING_JAVA_VERSION)) {
                    handleJavaVersionChange(option);
                }
            });
            radioGroup.addView(radioButton);
        }
    }

    private void setCheckboxValue(CheckBox checkBox, String key, boolean defaultValue) {
        String value = settings.getValue(key, defaultValue ? "true" : "false");
        checkBox.setChecked(value.equals("true"));

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (key.equals(SETTING_NO_HTTP_LEGACY)) {
                    SketchwareUtil.toast("Note that this option may cause issues if RequestNetwork component is used");
                }
            }
        });
    }
}
