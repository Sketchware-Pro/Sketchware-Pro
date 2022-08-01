package com.besome.sketch.editor.manage.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.lib.base.BaseDialogActivity;

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
        dialog.b(Helper.getResString(2131625758));
        dialog.a(2131165733);
        dialog.setCancelable(false);
        dialog.a(Helper.getResString(2131625757));
        dialog.b(Helper.getResString(2131625010), v -> n());
        dialog.a(Helper.getResString(2131624974), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case 2131230835:
                if (y == 0) {
                    y = z.size() - 1;
                } else {
                    y = y - 1;
                }

                applyPresetData(z.get(y).presetName);
                break;

            case 2131230848:
                if (y == z.size() - 1) {
                    y = 0;
                } else {
                    ++y;
                }

                applyPresetData(z.get(y).presetName);
                break;

            case 2131230909:
                setResult(RESULT_CANCELED);
                finish();
                break;

            case 2131230914:
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
        setContentView(2131427561);
        e(Helper.getResString(2131625758));
        requestCode = getIntent().getIntExtra("request_code", RESULT_OK);
        if (getIntent().hasExtra("edit_mode")) {
            A = true;
        }

        t = findViewById(2131230835);
        t.setOnClickListener(this);
        u = findViewById(2131230848);
        u.setOnClickListener(this);
        v = findViewById(2131231104);
        w = findViewById(2131231863);
        if (requestCode == 276) {
            z = rq.d();
        } else if (requestCode == 277) {
            z = rq.b();
        } else {
            z = rq.c();
        }

        d(Helper.getResString(2131625002));
        b(Helper.getResString(2131624974));
        r.setOnClickListener(this);
        s.setOnClickListener(this);
        applyPresetData(z.get(y).presetName);
    }
}
