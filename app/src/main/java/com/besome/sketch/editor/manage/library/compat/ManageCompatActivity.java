package com.besome.sketch.editor.manage.library.compat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.editor.manage.library.material3.Material3LibraryManager;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import pro.sketchware.R;
import pro.sketchware.databinding.ManageLibraryManageCompatBinding;

import a.a.a.aB;
import mod.hey.studios.util.Helper;

public class ManageCompatActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private ProjectLibraryBean compatLibraryBean;
    private ProjectLibraryBean firebaseLibraryBean;
    private ManageLibraryManageCompatBinding binding;

    private void showWarningDialog(String value) {
        new MaterialAlertDialogBuilder(this)
                .setIcon(R.drawable.ic_mtrl_warning)
                .setTitle(Helper.getResString(R.string.common_word_warning))
                .setMessage(value)
                .setPositiveButton(R.string.common_word_ok, (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void configureLibraryDialog() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.common_word_warning));
        dialog.a(R.drawable.delete_96);
        dialog.a(Helper.getResString(R.string.design_library_message_confirm_uncheck_appcompat_and_design));
        dialog.setCancelable(false);
        dialog.b(Helper.getResString(R.string.common_word_delete), v -> {
            binding.libSwitch.setChecked(false);
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), v -> {
            binding.libSwitch.setChecked(true);
            dialog.dismiss();
        });
        dialog.show();
    }

    private void configure() {
        binding.libSwitch.setChecked("Y".equals(compatLibraryBean.useYn));
    }

    @Override
    public void onBackPressed() {
        compatLibraryBean.useYn = binding.libSwitch.isChecked() ? "Y" : "N";
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
                showWarningDialog(Helper.getResString(R.string.design_library_appcompat_need_firebase_disable));
                binding.libSwitch.setChecked(true);
                return;
            }

            if (!binding.libSwitch.isChecked() && new Material3LibraryManager(getIntent().getStringExtra("sc_id"), !binding.libSwitch.isChecked()).isMaterial3Enabled()) {
                showWarningDialog(Helper.getResString(R.string.design_library_appcompat_need_m3_disable));
                binding.libSwitch.setChecked(true);
                return;
            }

            if ("Y".equals(compatLibraryBean.useYn) && !binding.libSwitch.isChecked()) {
                configureLibraryDialog();
            }
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
        binding.tvDesc.setText(Helper.getResString(R.string.design_library_appcompat_description));
        binding.tvEnable.setText(Helper.getResString(R.string.design_library_settings_title_enabled));
        binding.tvWarning.setText(Helper.getResString(R.string.design_library_message_slow_down_compilation_time));
        configure();
    }
}
