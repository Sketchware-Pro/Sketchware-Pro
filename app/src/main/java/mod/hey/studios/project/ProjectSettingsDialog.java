package mod.hey.studios.project;

import static com.besome.sketch.Config.VAR_DEFAULT_MIN_SDK_VERSION;
import static com.besome.sketch.Config.VAR_DEFAULT_TARGET_SDK_VERSION;

import android.app.Activity;
import android.view.View;

import com.besome.sketch.beans.ProjectLibraryBean;

import mod.hey.studios.util.Helper;

import pro.sketchware.R;
import pro.sketchware.databinding.DialogProjectSettingsBinding;

import a.a.a.aB;
import a.a.a.jC;

public class ProjectSettingsDialog {

    private final Activity activity;
    private final ProjectSettings settings;
    private final ProjectLibraryBean projectLibrary;

    public ProjectSettingsDialog(Activity activity, String sc_id) {
        this.activity = activity;
        settings = new ProjectSettings(sc_id);
        projectLibrary = jC.c(sc_id).c();
    }

    public void show() {
        aB dialog = new aB(activity);
        dialog.a(R.drawable.services_48);
        dialog.b("Project configurations");

        DialogProjectSettingsBinding binding = DialogProjectSettingsBinding.inflate(activity.getLayoutInflater());

        binding.etMinimumSdkVersion.setText(settings.getValue(ProjectSettings.SETTING_MINIMUM_SDK_VERSION, String.valueOf(VAR_DEFAULT_MIN_SDK_VERSION)));
        binding.etTargetSdkVersion.setText(settings.getValue(ProjectSettings.SETTING_TARGET_SDK_VERSION, String.valueOf(VAR_DEFAULT_TARGET_SDK_VERSION)));
        binding.etApplicationClassName.setText(settings.getValue(ProjectSettings.SETTING_APPLICATION_CLASS, ".SketchApplication"));

        binding.enableViewbinding.setChecked(
                settings.getValue(ProjectSettings.SETTING_ENABLE_VIEWBINDING, "false").equals("true"));
        binding.cbRemoveOldMethods.setChecked(
                settings.getValue(ProjectSettings.SETTING_DISABLE_OLD_METHODS, "false").equals("true"));
        binding.cbUseNewMaterial3AppTheme.setChecked(
                settings.getValue(ProjectSettings.SETTING_ENABLE_MATERIAL3, "false").equals("true"));
        binding.cbUseDynamicColors.setChecked(settings.isDynamicColorsEnable());
       
        // dont enable if app compat is off
        binding.cbUseNewMaterial3AppTheme.setEnabled(projectLibrary.isEnabled());
        binding.cbUseDynamicColors.setEnabled(projectLibrary.isEnabled());
       
        binding.cbUseNewMaterial3AppTheme.setOnCheckedChangeListener((sw, isChecked) -> {
            binding.cbUseDynamicColors.setOnCheckedChangeListener(null);
            if (!isChecked) {
                binding.cbUseDynamicColors.setChecked(false);
            }
            binding.cbUseDynamicColors.setOnCheckedChangeListener((sw2, isChecked2) -> {
                if (isChecked2) {
                    binding.cbUseNewMaterial3AppTheme.setChecked(true);
                }
            });
        });
        
        binding.cbUseDynamicColors.setOnCheckedChangeListener((sw, isChecked) -> {
            binding.cbUseNewMaterial3AppTheme.setOnCheckedChangeListener(null);
            if (isChecked) {
                binding.cbUseNewMaterial3AppTheme.setChecked(true);
            }
            binding.cbUseNewMaterial3AppTheme.setOnCheckedChangeListener((sw2, isChecked2) -> {
                if (!isChecked2) {
                    binding.cbUseDynamicColors.setChecked(false);
                }
            });
        });
        
        binding.enableViewbinding.setTag(ProjectSettings.SETTING_ENABLE_VIEWBINDING);
        binding.etMinimumSdkVersion.setTag(ProjectSettings.SETTING_MINIMUM_SDK_VERSION);
        binding.etTargetSdkVersion.setTag(ProjectSettings.SETTING_TARGET_SDK_VERSION);
        binding.etApplicationClassName.setTag(ProjectSettings.SETTING_APPLICATION_CLASS);
        binding.cbRemoveOldMethods.setTag(ProjectSettings.SETTING_DISABLE_OLD_METHODS);
        binding.cbUseNewMaterial3AppTheme.setTag(ProjectSettings.SETTING_ENABLE_MATERIAL3);
        binding.cbUseDynamicColors.setTag(ProjectSettings.SETTING_ENABLE_DYNAMIC_COLORS);
        
        dialog.a(binding.getRoot());

        final View[] preferences = {
                binding.etMinimumSdkVersion,
                binding.etTargetSdkVersion,
                binding.etApplicationClassName,
                binding.enableViewbinding,
                binding.cbRemoveOldMethods,
                binding.cbUseNewMaterial3AppTheme,
                binding.cbUseDynamicColors
        };

        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            settings.setValues(preferences);
            dialog.dismiss();
        });
        dialog.show();
    }
}
