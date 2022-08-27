package com.besome.sketch.lib.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

import a.a.a.mB;
import a.a.a.wB;

public class BaseDialogActivity extends BaseAppCompatActivity {

    public LinearLayout k;
    public ViewGroup l;
    public LinearLayout m;
    public LinearLayout n;
    public TextView o;
    public ImageView p;
    public TextView q;
    public TextView r;
    public TextView s;

    public void a(boolean var1) {
        if (var1) {
            k.setOnClickListener(view -> {
                if (!mB.a()) {
                    finish();
                }
            });
        } else {
            k.setOnClickListener(null);
        }
    }

    public void b(String var1) {
        s.setText(var1.toUpperCase());
    }

    public void c(String var1) {
        q.setText(var1.toUpperCase());
    }

    public void d(String var1) {
        r.setText(var1.toUpperCase());
    }

    public void e(String var1) {
        o.setText(var1);
    }

    public void f(int var1) {
        p.setImageResource(var1);
        p.setVisibility(View.VISIBLE);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
    }

    public void l() {
        m.setVisibility(View.GONE);
    }

    public void m() {
        n.setVisibility(View.GONE);
    }

    @Override
    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        super.setContentView(R.layout.common_dialog_layout);
        overridePendingTransition(R.anim.ani_fade_in, R.anim.ani_fade_out);
        p = findViewById(R.id.common_dialog_icon);
        k = findViewById(R.id.common_dialog_container);
        l = findViewById(R.id.common_dialog_content);
        m = findViewById(R.id.common_dialog_button_layout);
        o = findViewById(R.id.common_dialog_tv_title);
        n = findViewById(R.id.common_dialog_title_layout);
        q = findViewById(R.id.common_dialog_default_button);
        r = findViewById(R.id.common_dialog_ok_button);
        s = findViewById(R.id.common_dialog_cancel_button);
        k.setOnClickListener(view -> {
            if (!mB.a()) {
                finish();
            }
        });
        p.setVisibility(View.GONE);
    }

    @Override
    public void setContentView(int var1) {
        wB.a(this, l, var1);
    }
}
