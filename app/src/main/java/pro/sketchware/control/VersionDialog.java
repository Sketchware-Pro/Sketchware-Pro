package pro.sketchware.control;

import android.text.TextUtils;
import android.view.LayoutInflater;
import androidx.appcompat.app.AlertDialog;

import com.besome.sketch.projects.MyProjectSettingActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import a.a.a.mB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.DialogAdvancedVersionControlBinding;
import pro.sketchware.lib.validator.VersionNamePostfixValidator;

public class VersionDialog {
    public static final long MAX_VERSION_CODE = 2100000000L;
    public static final long MIN_VERSION_CODE = 1L;

    private final MyProjectSettingActivity activity;
    private final DialogAdvancedVersionControlBinding binding;

    public VersionDialog(MyProjectSettingActivity activity) {
        this.activity = activity;
        LayoutInflater inflater = LayoutInflater.from(activity);
        binding = DialogAdvancedVersionControlBinding.inflate(inflater);
    }

    public void show() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity);
        builder.setIcon(R.drawable.numbers_48);
        builder.setTitle("Advanced Version Control");

        long initialVerCode = MIN_VERSION_CODE;
        try {
            initialVerCode = Long.parseLong(Helper.getText(activity.binding.verCode).trim());
        } catch (Exception ignored) {
        }
        if (initialVerCode < MIN_VERSION_CODE) initialVerCode = MIN_VERSION_CODE;
        if (initialVerCode > MAX_VERSION_CODE) initialVerCode = MAX_VERSION_CODE;
        binding.versionCode.setText(String.valueOf(initialVerCode));

        binding.versionName1.setText(Helper.getText(activity.binding.verName).split(" ")[0]);
        if (Helper.getText(activity.binding.verName).split(" ").length > 1)
            binding.versionName2.setText(Helper.getText(activity.binding.verName).split(" ")[1]);

        builder.setView(binding.getRoot());
        builder.setPositiveButton(Helper.getResString(R.string.common_word_save), null);
        builder.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);

        binding.versionName2.addTextChangedListener(new VersionNamePostfixValidator(activity, binding.tilVersionNameExtra));

        AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String verCode = Helper.getText(binding.versionCode).trim();
            String verName = Helper.getText(binding.versionName1).trim();
            String verNamePostfix = Helper.getText(binding.versionName2).trim();

            boolean validVerCode = false;
            try {
                long parsed = Long.parseLong(verCode);
                if (parsed >= MIN_VERSION_CODE && parsed <= MAX_VERSION_CODE) {
                    validVerCode = true;
                }
            } catch (Exception ignored) {
            }

            boolean validVerName = !TextUtils.isEmpty(verName);

            if (validVerCode) {
                binding.tilVersionCode.setError(null);
            } else {
                binding.tilVersionCode.setError("Version code must be between 1 and 2100000000");
            }

            if (validVerName) {
                binding.tilVersionName.setError(null);
            } else {
                binding.tilVersionName.setError("Invalid Version Name");
            }

            if (!mB.a() && validVerCode && validVerName) {
                activity.binding.verCode.setText(verCode);
                activity.binding.verName.setText(!verNamePostfix.isEmpty() ? verName + " " + verNamePostfix : verName);
                dialog.dismiss();
            }
        });
    }
}
