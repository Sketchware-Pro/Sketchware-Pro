package mod.hey.studios.project;

import static com.besome.sketch.Config.VAR_DEFAULT_MIN_SDK_VERSION;
import static com.besome.sketch.Config.VAR_DEFAULT_TARGET_SDK_VERSION;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.besome.sketch.beans.ProjectLibraryBean;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import mod.hey.studios.util.Helper;

import pro.sketchware.R;
import pro.sketchware.databinding.DialogProjectSettingsBinding;

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

        binding.enableViewbinding.setChecked(
            settings.getValue(ProjectSettings.SETTING_ENABLE_VIEWBINDING, "false").equals("true"));
        binding.cbRemoveOldMethods.setChecked(
            settings.getValue(ProjectSettings.SETTING_DISABLE_OLD_METHODS, "false").equals("true"));
        binding.cbUseNewMaterial3AppTheme.setChecked(
            settings.getValue(ProjectSettings.SETTING_ENABLE_MATERIAL3, "false").equals("true"));
        binding.cbUseDynamicColors.setChecked(settings.isDynamicColorsEnable());

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

        dialog.setContentView(binding.getRoot());

        final View[] preferences = {
              binding.etMinimumSdkVersion,
              binding.etTargetSdkVersion,
              binding.etApplicationClassName,
              binding.enableViewbinding,
              binding.cbRemoveOldMethods,
              binding.cbUseNewMaterial3AppTheme,
              binding.cbUseDynamicColors
        };

        binding.btnCancel.setOnClickListener(v -> dialog.dismiss());
        binding.btnSave.setOnClickListener(v -> {
            settings.setValues(preferences);
            dialog.dismiss();
        });

        dialog.show();
    }
}