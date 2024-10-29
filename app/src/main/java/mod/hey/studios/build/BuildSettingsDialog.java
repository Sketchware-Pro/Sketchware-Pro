package mod.hey.studios.build;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.ProjectConfigLayoutBinding;

import pro.sketchware.utility.SketchwareUtil;

public class BuildSettingsDialog {

    private final Activity activity;
    private final BuildSettings settings;

    public BuildSettingsDialog(Activity activity, String sc_id) {
        this.activity = activity;
        settings = new BuildSettings(sc_id);
    }

    public void show() {
        ProjectConfigLayoutBinding binding = ProjectConfigLayoutBinding.inflate(activity.getLayoutInflater());

        View[] viewArr = {
                binding.edittextAndroidJarPath,
                binding.edittextClasspath,
                binding.radiogroupDexer,
                binding.radiogroupJavaVersion,
                binding.checkboxNoWarnings,
                binding.checkboxNoHttpLegacy,
                binding.checkboxEnableLogcat
        };

        binding.edittextAndroidJarPath.setText(settings.getValue(BuildSettings.SETTING_ANDROID_JAR_PATH, ""));
        binding.edittextClasspath.setText(settings.getValue(BuildSettings.SETTING_CLASSPATH, ""));

        setRadioGroupOptions(binding.radiogroupDexer, new String[]{"Dx", "D8"}, BuildSettings.SETTING_DEXER, "Dx");
        setRadioGroupOptions(binding.radiogroupJavaVersion, BuildSettingsDialogBridge.getAvailableJavaVersions(), BuildSettings.SETTING_JAVA_VERSION, "1.7");

        setCheckBox(binding.checkboxNoWarnings, BuildSettings.SETTING_NO_WARNINGS, true);
        setCheckBox(binding.checkboxNoHttpLegacy, BuildSettings.SETTING_NO_HTTP_LEGACY, false);
        setCheckBox(binding.checkboxEnableLogcat, BuildSettings.SETTING_ENABLE_LOGCAT, true);

        var builder = new MaterialAlertDialogBuilder(activity)
                .setTitle("Build Settings")
                .setIcon(R.drawable.ic_tune_24)
                .setPositiveButton("Save", (dialogInterface, i) -> settings.setValues(viewArr))
                .setNegativeButton("Cancel", null);
        builder.setView(binding.getRoot());
        builder.show();
    }

    private void setRadioGroupOptions(RadioGroup radioGroup, String[] options, String key, String defaultValue) {
        radioGroup.removeAllViews();
        String value = settings.getValue(key, defaultValue);
        for (String option : options) {
            RadioButton radioButton = new RadioButton(activity);
            radioButton.setText(option);
            radioButton.setId(View.generateViewId());
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f));
            if (value.equals(option)) {
                radioButton.setChecked(true);
            }
            radioButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (!isChecked) return;
                if (key.equals(BuildSettings.SETTING_JAVA_VERSION)) {
                    BuildSettingsDialogBridge.handleJavaVersionChange(option);
                } else if (key.equals(BuildSettings.SETTING_DEXER)) {
                    BuildSettingsDialogBridge.handleDexerChange(option);
                }
            });
            radioGroup.addView(radioButton);
        }
        radioGroup.setTag(key);
    }

    private void setCheckBox(CheckBox checkBox, String key, boolean defaultValue) {
        String value = settings.getValue(key, defaultValue ? "true" : "false");
        checkBox.setChecked(value.equals("true"));
        checkBox.setTag(key);

        if (key.equals(BuildSettings.SETTING_NO_HTTP_LEGACY)) {
            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    SketchwareUtil.toast("Note that this option may cause issues if RequestNetwork component is used");
                }
            });
        }
    }
}
