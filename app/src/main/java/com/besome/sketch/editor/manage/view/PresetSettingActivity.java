package com.besome.sketch.editor.manage.view;

import a.a.a.Gw;
import a.a.a.Hw;
import a.a.a.aB;
import a.a.a.rq;
import a.a.a.xB;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import java.util.ArrayList;

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
        switch (this.x) {
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
        this.v.setImageResource(i);
        this.w.setText(str);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.besome.sketch.editor.manage.view.PresetSettingActivity */
    /* JADX WARN: Multi-variable type inference failed */
    public final void n() {
        Intent intent = new Intent();
        intent.putExtra("preset_data", this.z.get(this.y));
        setResult(-1, intent);
        finish();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.besome.sketch.editor.manage.view.PresetSettingActivity */
    /* JADX WARN: Multi-variable type inference failed */
    public final void o() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), 2131625758));
        aBVar.a(2131165733);
        aBVar.setCancelable(false);
        aBVar.a(xB.b().a(getApplicationContext(), 2131625757));
        aBVar.b(xB.b().a(getApplicationContext(), 2131625010), new Gw(this));
        aBVar.a(xB.b().a(getApplicationContext(), 2131624974), new Hw(this, aBVar));
        aBVar.show();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.besome.sketch.editor.manage.view.PresetSettingActivity */
    /* JADX WARN: Multi-variable type inference failed */
    public void onClick(View view) {
        switch (view.getId()) {
            case 2131230835:
                int i = this.y;
                if (i == 0) {
                    this.y = this.z.size() - 1;
                } else {
                    this.y = i - 1;
                }
                f(this.z.get(this.y).presetName);
                return;
            case 2131230848:
                if (this.y == this.z.size() - 1) {
                    this.y = 0;
                } else {
                    this.y++;
                }
                f(this.z.get(this.y).presetName);
                return;
            case 2131230909:
                setResult(0);
                finish();
                return;
            case 2131230914:
                if (!this.A) {
                    n();
                    return;
                } else {
                    o();
                    return;
                }
            default:
                return;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.besome.sketch.editor.manage.view.PresetSettingActivity */
    /* JADX WARN: Multi-variable type inference failed */
    @SuppressLint("ResourceType")
    @Override // com.besome.sketch.lib.base.BaseDialogActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427561);
        e(xB.b().a(getApplicationContext(), 2131625758));
        this.x = getIntent().getIntExtra("request_code", -1);
        if (getIntent().hasExtra("edit_mode")) {
            this.A = true;
        }
        this.t = (ImageView) findViewById(2131230835);
        this.t.setOnClickListener(this);
        this.u = (ImageView) findViewById(2131230848);
        this.u.setOnClickListener(this);
        this.v = (ImageView) findViewById(2131231104);
        this.w = (TextView) findViewById(2131231863);
        int i = this.x;
        if (i == 276) {
            this.z = rq.d();
        } else if (i == 277) {
            this.z = rq.b();
        } else {
            this.z = rq.c();
        }
        d(xB.b().a(getApplicationContext(), 2131625002));
        b(xB.b().a(getApplicationContext(), 2131624974));
        this.r.setOnClickListener(this);
        this.s.setOnClickListener(this);
        f(this.z.get(this.y).presetName);
    }
}
