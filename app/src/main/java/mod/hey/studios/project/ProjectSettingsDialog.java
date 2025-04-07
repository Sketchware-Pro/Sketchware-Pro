package mod.hey.studios.project;

import static com.besome.sketch.Config.VAR_DEFAULT_MIN_SDK_VERSION;
import static com.besome.sketch.Config.VAR_DEFAULT_TARGET_SDK_VERSION;

import android.app.Activity;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import pro.sketchware.databinding.DialogProjectSettingsBinding;

public class ProjectSettingsDialog {

    private final Activity activity;
    private final ProjectSettings settings;

    public ProjectSettingsDialog(Activity activity, String sc_id) {
        this.activity = activity;
        settings = new ProjectSettings(sc_id);
    }

    public void show() {
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        DialogProjectSettingsBinding binding = DialogProjectSettingsBinding.inflate(activity.getLayoutInflater());

        dialog.setOnShowListener(bsd -> {
            var b = (BottomSheetDialog) bsd;
            var parent = b.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (parent != null) {
                BottomSheetBehavior<View> behavior = BottomSheetBehavior.from(parent);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setSkipCollapsed(true);
            }
        });

        binding.etMinimumSdkVersion.setText(settings.getValue(ProjectSettings.SETTING_MINIMUM_SDK_VERSION, String.valueOf(VAR_DEFAULT_MIN_SDK_VERSION)));
        binding.etTargetSdkVersion.setText(settings.getValue(ProjectSettings.SETTING_TARGET_SDK_VERSION, String.valueOf(VAR_DEFAULT_TARGET_SDK_VERSION)));
        binding.etApplicationClassName.setText(settings.getValue(ProjectSettings.SETTING_APPLICATION_CLASS, ".SketchApplication"));

        binding.cbEnableViewbinding.setChecked(
                settings.getValue(ProjectSettings.SETTING_ENABLE_VIEWBINDING, "false").equals("true"));
        binding.cbRemoveOldMethods.setChecked(
                settings.getValue(ProjectSettings.SETTING_DISABLE_OLD_METHODS, "false").equals("true"));
        binding.cbUseNewMaterialComponentsAppTheme.setChecked(
                settings.getValue(ProjectSettings.SETTING_ENABLE_BRIDGELESS_THEMES, "false").equals("true"));

        binding.enableViewbinding.setOnClickListener(v -> binding.cbEnableViewbinding.performClick());
        binding.removeOldMethods.setOnClickListener(v -> binding.cbRemoveOldMethods.performClick());
        binding.useNewMaterialComponentsAppTheme.setOnClickListener(v -> binding.cbUseNewMaterialComponentsAppTheme.performClick());

        binding.etMinimumSdkVersion.setTag(ProjectSettings.SETTING_MINIMUM_SDK_VERSION);
        binding.etTargetSdkVersion.setTag(ProjectSettings.SETTING_TARGET_SDK_VERSION);
        binding.etApplicationClassName.setTag(ProjectSettings.SETTING_APPLICATION_CLASS);
        binding.cbEnableViewbinding.setTag(ProjectSettings.SETTING_ENABLE_VIEWBINDING);
        binding.cbRemoveOldMethods.setTag(ProjectSettings.SETTING_DISABLE_OLD_METHODS);
        binding.cbUseNewMaterialComponentsAppTheme.setTag(ProjectSettings.SETTING_ENABLE_BRIDGELESS_THEMES);

        dialog.setContentView(binding.getRoot());

        final View[] preferences = {
                binding.etMinimumSdkVersion,
                binding.etTargetSdkVersion,
                binding.etApplicationClassName,
                binding.cbEnableViewbinding,
                binding.cbRemoveOldMethods,
                binding.cbUseNewMaterialComponentsAppTheme
        };

        binding.save.setOnClickListener(v -> {
            settings.setValues(preferences);
            dialog.dismiss();
        });
        binding.cancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
