package com.besome.sketch.editor.manage.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.sketchware.remod.Resources;

import java.util.ArrayList;

import a.a.a.aB;
import a.a.a.rq;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class PresetSettingActivity extends BaseDialogActivity implements View.OnClickListener {

    public boolean A = false;
    public ImageView t;
    public ImageView u;
    public ImageView v;
    public TextView w;
    public int x;
    public int y = 0;
    public ArrayList<ProjectFileBean> z;

    public final void f(String str) {
        int i;
        switch (x) {
            case 276:
                i = rq.e(str);
                break;
            case 277:
                i = rq.a(str);
                break;
            case 278:
                i = rq.c(str);
                break;
            default:
                i = -1;
                break;
        }
        v.setImageResource(i);
        w.setText(str);
    }

    public final void n() {
        Intent intent = new Intent();
        intent.putExtra("preset_data", z.get(y));
        setResult(-1, intent);
        finish();
    }

    public final void o() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), Resources.string.preset_setting_title));
        aBVar.a(Resources.drawable.ic_detail_setting_48dp);
        aBVar.setCancelable(false);
        aBVar.a(xB.b().a(getApplicationContext(), Resources.string.preset_setting_edit_warning));
        aBVar.b(xB.b().a(getApplicationContext(), Resources.string.common_word_ok), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n();
            }
        });
        aBVar.a(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case Resources.id.btn_left:
                if (y == 0) {
                    y = z.size() - 1;
                } else {
                    y--;
                }
                f(z.get(y).presetName);
                return;

            case Resources.id.btn_right:
                if (y == z.size() - 1) {
                    y = 0;
                } else {
                    y++;
                }
                f(z.get(y).presetName);
                return;

            case Resources.id.common_dialog_cancel_button:
                setResult(0);
                finish();
                return;

            case Resources.id.common_dialog_ok_button:
                if (!A) {
                    n();
                } else {
                    o();
                }
        }
    }

    @Override
    // com.besome.sketch.lib.base.BaseDialogActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.manage_screen_activity_add_view_preset_setting);
        e(xB.b().a(getApplicationContext(), Resources.string.preset_setting_title));
        x = getIntent().getIntExtra("request_code", -1);
        if (getIntent().hasExtra("edit_mode")) {
            A = true;
        }
        t = findViewById(Resources.id.btn_left);
        t.setOnClickListener(this);
        u = findViewById(Resources.id.btn_right);
        u.setOnClickListener(this);
        v = findViewById(Resources.id.img_activity);
        w = findViewById(Resources.id.tv_activity_name);
        if (x == 276) {
            z = rq.d();
        } else if (x == 277) {
            z = rq.b();
        } else {
            z = rq.c();
        }
        d(xB.b().a(getApplicationContext(), Resources.string.common_word_import));
        b(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel));
        r.setOnClickListener(this);
        s.setOnClickListener(this);
        f(z.get(y).presetName);
    }
}
