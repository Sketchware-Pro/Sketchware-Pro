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
            k.setOnClickListener(v -> {
                if (!mB.a()) {
                    finish();
                }
            });
        } else {
            k.setOnClickListener(null);
        }
    }

    public void b(String cancelText) {
        s.setText(cancelText.toUpperCase());
    }

    public void c(String defaultText) {
        q.setText(defaultText.toUpperCase());
    }

    public void d(String okText) {
        r.setText(okText.toUpperCase());
    }

    public void e(String title) {
        o.setText(title);
    }

    public void f(int iconResId) {
        p.setImageResource(iconResId);
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        k.setOnClickListener(v -> {
            if (!mB.a()) {
                finish();
            }
        });
        p.setVisibility(View.GONE);
    }

    @Override
    public void setContentView(int layoutResID) {
        wB.a(this, l, layoutResID);
    }
}
