package com.besome.sketch.editor.manage.library.compat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.R;

import a.a.a.aB;
import mod.hey.studios.util.Helper;

public class ManageCompatActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private Switch libSwitch;
    private ProjectLibraryBean compatLibraryBean;
    private ProjectLibraryBean firebaseLibraryBean;

    private void showFirebaseNeedDisableDialog() {
        aB dialog = new aB(this);
        dialog.a(R.drawable.chrome_96);
        dialog.a(Helper.getResString(R.string.design_library_appcompat_need_firebase_disable));
        dialog.b(Helper.getResString(R.string.common_word_ok), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void configureLibraryDialog() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.common_word_warning));
        dialog.a(R.drawable.delete_96);
        dialog.a(Helper.getResString(R.string.design_library_message_confirm_uncheck_appcompat_and_design));
        dialog.setCancelable(false);
        dialog.b(Helper.getResString(R.string.common_word_delete), v -> {
            libSwitch.setChecked(false);
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), v -> {
            libSwitch.setChecked(true);
            dialog.dismiss();
        });
        dialog.show();
    }

    private void configure() {
        libSwitch.setChecked("Y".equals(compatLibraryBean.useYn));
    }

    @Override
    public void onBackPressed() {
        compatLibraryBean.useYn = libSwitch.isChecked() ? "Y" : "N";
        Intent intent = new Intent();
        intent.putExtra("compat", compatLibraryBean);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layout_switch) {
            libSwitch.setChecked(!libSwitch.isChecked());
            if (!libSwitch.isChecked() && "Y".equals(firebaseLibraryBean.useYn)) {
                showFirebaseNeedDisableDialog();
                libSwitch.setChecked(true);
                return;
            }

            if ("Y".equals(compatLibraryBean.useYn) && !libSwitch.isChecked()) {
                configureLibraryDialog();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_library_manage_compat);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(Helper.getResString(R.string.design_library_title_appcompat_and_design));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

        compatLibraryBean = getIntent().getParcelableExtra("compat");
        firebaseLibraryBean = getIntent().getParcelableExtra("firebase");
        LinearLayout switchLayout = findViewById(R.id.layout_switch);
        switchLayout.setOnClickListener(this);
        libSwitch = findViewById(R.id.lib_switch);
        ((TextView) findViewById(R.id.tv_desc)).setText(Helper.getResString(R.string.design_library_appcompat_description));
        ((TextView) findViewById(R.id.tv_enable)).setText(Helper.getResString(R.string.design_library_settings_title_enabled));
        ((TextView) findViewById(R.id.tv_warning)).setText(Helper.getResString(R.string.design_library_message_slow_down_compilation_time));
        configure();
    }
}
