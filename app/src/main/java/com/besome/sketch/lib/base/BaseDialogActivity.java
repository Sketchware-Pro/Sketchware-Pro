package com.besome.sketch.lib.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        overridePendingTransition(2130771982, 2130771983);
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
        super.setContentView(2131427378);
        overridePendingTransition(2130771982, 2130771983);
        p = findViewById(2131230913);
        k = findViewById(2131230910);
        l = findViewById(2131230911);
        m = findViewById(2131230908);
        o = findViewById(2131230916);
        n = findViewById(2131230915);
        q = findViewById(2131230912);
        r = findViewById(2131230914);
        s = findViewById(2131230909);
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
