package mod.hey.studios.project;

import static com.besome.sketch.Config.VAR_DEFAULT_MIN_SDK_VERSION;
import static com.besome.sketch.Config.VAR_DEFAULT_TARGET_SDK_VERSION;

import android.app.Activity;
import android.view.View;

import pro.sketchware.R;
import pro.sketchware.databinding.DialogProjectSettingsBinding;

import a.a.a.aB;
import mod.hey.studios.util.Helper;

public class ProjectSettingsDialog {

    private final Activity activity;
    private final ProjectSettings settings;

    public ProjectSettingsDialog(Activity activity, String sc_id) {
        this.activity = activity;
        settings = new ProjectSettings(sc_id);
    }

    public void show() {
        aB dialog = new aB(activity);
        dialog.a(R.drawable.services_48);
        dialog.b("Project configurations");

        DialogProjectSettingsBinding binding = DialogProjectSettingsBinding.inflate(activity.getLayoutInflater());

        binding.etMinimumSdkVersion.setText(settings.getValue(ProjectSettings.SETTING_MINIMUM_SDK_VERSION, String.valueOf(VAR_DEFAULT_MIN_SDK_VERSION)));
        binding.etTargetSdkVersion.setText(settings.getValue(ProjectSettings.SETTING_TARGET_SDK_VERSION, String.valueOf(VAR_DEFAULT_TARGET_SDK_VERSION)));
        binding.etApplicationClassName.setText(settings.getValue(ProjectSettings.SETTING_APPLICATION_CLASS, ".SketchApplication"));

        binding.cbRemoveOldMethods.setChecked(
                settings.getValue(ProjectSettings.SETTING_DISABLE_OLD_METHODS, "false").equals("true"));
        binding.cbUseNewMaterialComponentsAppTheme.setChecked(
                settings.getValue(ProjectSettings.SETTING_ENABLE_BRIDGELESS_THEMES, "false").equals("true"));

        binding.etMinimumSdkVersion.setTag(ProjectSettings.SETTING_MINIMUM_SDK_VERSION);
        binding.etTargetSdkVersion.setTag(ProjectSettings.SETTING_TARGET_SDK_VERSION);
        binding.etApplicationClassName.setTag(ProjectSettings.SETTING_APPLICATION_CLASS);
        binding.cbRemoveOldMethods.setTag(ProjectSettings.SETTING_DISABLE_OLD_METHODS);
        binding.cbUseNewMaterialComponentsAppTheme.setTag(ProjectSettings.SETTING_ENABLE_BRIDGELESS_THEMES);

        dialog.a(binding.getRoot());

        final View[] preferences = {
                binding.etMinimumSdkVersion,
                binding.etTargetSdkVersion,
                binding.etApplicationClassName,
                binding.cbRemoveOldMethods,
                binding.cbUseNewMaterialComponentsAppTheme
        };

        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            settings.setValues(preferences);
            dialog.dismiss();
        });
        dialog.show();
    }
}
