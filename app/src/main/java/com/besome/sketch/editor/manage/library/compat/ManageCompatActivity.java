package com.besome.sketch.editor.manage.library.compat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageLibraryManageCompatBinding;

public class ManageCompatActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ProjectLibraryBean compatLibraryBean;
    private ProjectLibraryBean firebaseLibraryBean;
    private ManageLibraryManageCompatBinding binding;

    private void showFirebaseNeedDisableDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setIcon(R.drawable.chrome_96);
        dialog.setMessage(Helper.getResString(R.string.design_library_appcompat_need_firebase_disable));
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_ok), null);
        dialog.show();
    }

    private void configureLibraryDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle(Helper.getResString(R.string.common_word_warning));
        dialog.setIcon(R.drawable.delete_96);
        dialog.setMessage(Helper.getResString(R.string.design_library_message_confirm_uncheck_appcompat_and_design));
        dialog.setCancelable(false);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_delete), (v, which) -> {
            binding.libSwitch.setChecked(false);
            binding.switchMtrlTheme.setChecked(false);
            binding.switchMtrlWidget.setChecked(false);
            v.dismiss();
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), (v, which) -> {
            binding.libSwitch.setChecked(true);
            binding.switchMtrlTheme.setChecked(!binding.switchMtrlTheme.isChecked());
            binding.switchMtrlWidget.setChecked(!binding.switchMtrlWidget.isChecked());
            v.dismiss();
        });
        dialog.show();
    }

    private void configure() {
        binding.libSwitch.setChecked("Y".equals(compatLibraryBean.useYn));
        binding.switchMtrlTheme.setChecked("true".equals(compatLibraryBean.reserved1));
        binding.switchMtrlWidget.setChecked("true".equals(compatLibraryBean.reserved2));
    }

    @Override
    public void onBackPressed() {
        if (binding.libSwitch.isChecked()) {
            compatLibraryBean.useYn = "Y";
        } else {
            compatLibraryBean.useYn = "N";
            binding.switchMtrlTheme.setChecked(false);
            binding.switchMtrlWidget.setChecked(false);
        }
        compatLibraryBean.reserved1 = String.valueOf(binding.switchMtrlTheme.isChecked());
        compatLibraryBean.reserved2 = String.valueOf(binding.switchMtrlWidget.isChecked());

        Intent intent = new Intent();
        intent.putExtra("compat", compatLibraryBean);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layout_switch) {
            binding.libSwitch.setChecked(!binding.libSwitch.isChecked());
            if (!binding.libSwitch.isChecked() && "Y".equals(firebaseLibraryBean.useYn)) {
                showFirebaseNeedDisableDialog();
                binding.libSwitch.setChecked(true);
                return;
            }

            if ("Y".equals(compatLibraryBean.useYn) && !binding.libSwitch.isChecked()) {
                configureLibraryDialog();
            }
        } else if (v.getId() == binding.layoutMtrlTheme.getId()) {
            binding.switchMtrlTheme.setChecked(!binding.switchMtrlTheme.isChecked());
        } else if (v.getId() == binding.layoutMtrlWidget.getId()) {
            binding.switchMtrlWidget.setChecked(!binding.switchMtrlWidget.isChecked());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageLibraryManageCompatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        compatLibraryBean = getIntent().getParcelableExtra("compat");
        firebaseLibraryBean = getIntent().getParcelableExtra("firebase");
        binding.layoutSwitch.setOnClickListener(this);
        binding.layoutMtrlTheme.setOnClickListener(this);
        binding.layoutMtrlWidget.setOnClickListener(this);
        binding.libSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.switchMtrlTheme.setEnabled(isChecked);
            binding.switchMtrlWidget.setEnabled(isChecked);

        });

        binding.tvDesc.setText(Helper.getResString(R.string.design_library_appcompat_description));
        binding.tvEnable.setText(Helper.getResString(R.string.design_library_settings_title_enabled));
        binding.tvWarning.setText(Helper.getResString(R.string.design_library_message_slow_down_compilation_time));
        configure();
    }
}
