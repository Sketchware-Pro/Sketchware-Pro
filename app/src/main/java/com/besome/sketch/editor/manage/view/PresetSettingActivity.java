package com.besome.sketch.editor.manage.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.sketchware.remod.R;

import java.util.ArrayList;

import a.a.a.aB;
import a.a.a.rq;
import mod.hey.studios.util.Helper;

public class PresetSettingActivity extends BaseDialogActivity implements View.OnClickListener {

    private boolean A = false;
    private ImageView t;
    private ImageView u;
    private ImageView v;
    private TextView w;
    private int requestCode;
    private int y = 0;
    private ArrayList<ProjectFileBean> z;

    private void applyPresetData(String presetName) {
        int resDrawable;
        switch (requestCode) {
            case 276:
                resDrawable = rq.e(presetName);
                break;
            case 277:
                resDrawable = rq.a(presetName);
                break;
            case 278:
                resDrawable = rq.c(presetName);
                break;
            default:
                resDrawable = -1;
        }

        v.setImageResource(resDrawable);
        w.setText(presetName);
    }

    private void n() {
        Intent intent = new Intent();
        intent.putExtra("preset_data", z.get(y));
        setResult(RESULT_OK, intent);
        finish();
    }

    private void o() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.preset_setting_title));
        dialog.a(R.drawable.ic_detail_setting_48dp);
        dialog.setCancelable(false);
        dialog.a(Helper.getResString(R.string.preset_setting_edit_warning));
        dialog.b(Helper.getResString(R.string.common_word_ok), v -> n());
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_left:
                if (y == 0) {
                    y = z.size() - 1;
                } else {
                    y = y - 1;
                }

                applyPresetData(z.get(y).presetName);
                break;

            case R.id.btn_right:
                if (y == z.size() - 1) {
                    y = 0;
                } else {
                    ++y;
                }

                applyPresetData(z.get(y).presetName);
                break;

            case R.id.common_dialog_cancel_button:
                setResult(RESULT_CANCELED);
                finish();
                break;

            case R.id.common_dialog_ok_button:
                if (!A) {
                    n();
                } else {
                    o();
                }
                break;
        }
    }

    @Override
    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        setContentView(R.layout.manage_screen_activity_add_view_preset_setting);
        e(Helper.getResString(R.string.preset_setting_title));
        requestCode = getIntent().getIntExtra("request_code", RESULT_OK);
        if (getIntent().hasExtra("edit_mode")) {
            A = true;
        }

        t = findViewById(R.id.btn_left);
        t.setOnClickListener(this);
        u = findViewById(R.id.btn_right);
        u.setOnClickListener(this);
        v = findViewById(R.id.img_activity);
        w = findViewById(R.id.tv_activity_name);
        if (requestCode == 276) {
            z = rq.d();
        } else if (requestCode == 277) {
            z = rq.b();
        } else {
            z = rq.c();
        }

        d(Helper.getResString(R.string.common_word_import));
        b(Helper.getResString(R.string.common_word_cancel));
        r.setOnClickListener(this);
        s.setOnClickListener(this);
        applyPresetData(z.get(y).presetName);
    }
}
