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
import com.sketchware.remod.Resources;

import a.a.a.aB;
import mod.hey.studios.util.Helper;

public class ManageCompatActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private Switch libSwitch;
    private ProjectLibraryBean compatLibraryBean;
    private ProjectLibraryBean firebaseLibraryBean;

    private void showFirebaseNeedDisableDialog() {
        aB dialog = new aB(this);
        dialog.a(Resources.drawable.chrome_96);
        dialog.a(Helper.getResString(Resources.string.design_library_appcompat_need_firebase_disable));
        dialog.b(Helper.getResString(Resources.string.common_word_ok), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void configureLibraryDialog() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(Resources.string.common_word_warning));
        dialog.a(Resources.drawable.delete_96);
        dialog.a(Helper.getResString(Resources.string.design_library_message_confirm_uncheck_appcompat_and_design));
        dialog.setCancelable(false);
        dialog.b(Helper.getResString(Resources.string.common_word_delete), v -> {
            libSwitch.setChecked(false);
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(Resources.string.common_word_cancel), v -> {
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
        if (v.getId() == Resources.id.layout_switch) {
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
        setContentView(Resources.layout.manage_library_manage_compat);

        Toolbar toolbar = findViewById(Resources.id.toolbar);
        a(toolbar);
        findViewById(Resources.id.layout_main_logo).setVisibility(View.GONE);
        d().a(Helper.getResString(Resources.string.design_library_title_appcompat_and_design));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

        compatLibraryBean = getIntent().getParcelableExtra("compat");
        firebaseLibraryBean = getIntent().getParcelableExtra("firebase");
        LinearLayout switchLayout = findViewById(Resources.id.layout_switch);
        switchLayout.setOnClickListener(this);
        libSwitch = findViewById(Resources.id.lib_switch);
        ((TextView) findViewById(Resources.id.tv_desc)).setText(Helper.getResString(Resources.string.design_library_appcompat_description));
        ((TextView) findViewById(Resources.id.tv_enable)).setText(Helper.getResString(Resources.string.design_library_settings_title_enabled));
        ((TextView) findViewById(Resources.id.tv_warning)).setText(Helper.getResString(Resources.string.design_library_message_slow_down_compilation_time));
        configure();
    }
}
