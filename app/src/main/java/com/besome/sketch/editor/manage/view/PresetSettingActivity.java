package com.besome.sketch.editor.manage.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import a.a.a.rq;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;

public class PresetSettingActivity extends BaseDialogActivity implements View.OnClickListener {

    private boolean inEditMode = false;
    private ImageView activity;
    private TextView activityName;
    private int requestCode;
    private int index = 0;
    private ArrayList<ProjectFileBean> presets;

    private void applyPresetData(String presetName) {
        int resDrawable = switch (requestCode) {
            case 276 -> rq.e(presetName);
            case AddCustomViewActivity.REQ_CD_PRESET_ACTIVITY -> rq.a(presetName);
            case 278 -> rq.c(presetName);
            default -> -1;
        };

        activity.setImageResource(resDrawable);
        activityName.setText(presetName);
    }

    private void close() {
        Intent intent = new Intent();
        intent.putExtra("preset_data", presets.get(index));
        setResult(RESULT_OK, intent);
        finish();
    }

    private void confirmBeforeClose() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle(Helper.getResString(R.string.preset_setting_title));
        dialog.setIcon(R.drawable.ic_detail_setting_48dp);
        dialog.setCancelable(false);
        dialog.setMessage(Helper.getResString(R.string.preset_setting_edit_warning));
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_ok), (v, which) -> close());
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_left) {
            if (index == 0) {
                index = presets.size() - 1;
            } else {
                index = index - 1;
            }

            applyPresetData(presets.get(index).presetName);
        } else if (id == R.id.btn_right) {
            if (index == presets.size() - 1) {
                index = 0;
            } else {
                ++index;
            }

            applyPresetData(presets.get(index).presetName);
        } else if (id == R.id.common_dialog_cancel_button) {
            setResult(RESULT_CANCELED);
            finish();
        } else if (id == R.id.common_dialog_ok_button) {
            if (!inEditMode) {
                close();
            } else {
                confirmBeforeClose();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_screen_activity_add_view_preset_setting);
        e(Helper.getResString(R.string.preset_setting_title));
        requestCode = getIntent().getIntExtra("request_code", -1);
        if (getIntent().hasExtra("edit_mode")) {
            inEditMode = true;
        }

        ImageView left = findViewById(R.id.btn_left);
        left.setOnClickListener(this);
        ImageView right = findViewById(R.id.btn_right);
        right.setOnClickListener(this);
        activity = findViewById(R.id.img_activity);
        activityName = findViewById(R.id.tv_activity_name);
        if (requestCode == 276) {
            presets = rq.d();
        } else if (requestCode == AddCustomViewActivity.REQ_CD_PRESET_ACTIVITY) {
            presets = rq.b();
        } else {
            presets = rq.c();
        }

        d(Helper.getResString(R.string.common_word_import));
        b(Helper.getResString(R.string.common_word_cancel));
        r.setOnClickListener(this);
        s.setOnClickListener(this);
        applyPresetData(presets.get(index).presetName);
    }
}
