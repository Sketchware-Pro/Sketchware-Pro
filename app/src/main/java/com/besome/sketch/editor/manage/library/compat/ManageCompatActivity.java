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

import a.a.a.aB;
import mod.hey.studios.util.Helper;

public class ManageCompatActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public Toolbar toolbar;
    public LinearLayout switchLayout;
    public Switch libSwitch;
    public ProjectLibraryBean compatLibraryBean;
    public ProjectLibraryBean firebaseLibraryBean;

    private void showFirebaseNeedDisableDialog() {
        aB dialog = new aB(this);
        dialog.a(2131165415);
        dialog.a(Helper.getResString(2131625200));
        dialog.b(Helper.getResString(2131625010), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void configureLibraryDialog() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(2131625047));
        dialog.a(2131165524);
        dialog.a(Helper.getResString(2131625245));
        dialog.setCancelable(false);
        dialog.b(Helper.getResString(2131624986), new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
                libSwitch.setChecked(false);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(2131624974), new View.OnClickListener() {
        	@Override
            public void onClick(View v) {
                libSwitch.setChecked(true);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void configure() {
        libSwitch.setChecked("Y".equals(compatLibraryBean.useYn));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        String useYn;
        if (libSwitch.isChecked()) {
            useYn = "Y";
        } else {
            useYn = "N";
        }

        compatLibraryBean.useYn = useYn;
        intent.putExtra("compat", compatLibraryBean);
        setResult(-1, intent);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == 2131231408) {
            libSwitch.setChecked(libSwitch.isChecked() ^ true);

            if ("Y".equals(compatLibraryBean.useYn) && !libSwitch.isChecked()) {
                configureLibraryDialog();
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427546);
        toolbar = (Toolbar) findViewById(2131231847);
        a(toolbar);
        findViewById(2131231370).setVisibility(8);
        d().a(Helper.getResString(2131625251));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        compatLibraryBean = (ProjectLibraryBean) getIntent().getParcelableExtra("compat");
        firebaseLibraryBean = (ProjectLibraryBean) getIntent().getParcelableExtra("firebase");
        switchLayout = (LinearLayout) findViewById(2131231408);
        switchLayout.setOnClickListener(this);
        libSwitch = (Switch) findViewById(2131231429);
        ((TextView) findViewById(2131231944)).setText(Helper.getResString(2131625199));
        ((TextView) findViewById(2131231965)).setText(Helper.getResString(2131625249));
        ((TextView) findViewById(2131232285)).setText(Helper.getResString(2131625247));
        configure();
    }

}
