package com.besome.sketch.help;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.PropertyOneLineItem;
import com.besome.sketch.lib.ui.PropertyTwoLineItem;
import com.sketchware.remod.R;

import a.a.a.GB;
import a.a.a.mB;
import a.a.a.xB;

public class SystemInfoActivity extends BaseAppCompatActivity {

    private final int m = 0;
    private final int n = 1;
    private final int o = 2;
    private final int p = 3;
    private final int q = 4;
    private final int r = 5;
    private LinearLayout content;

    private void a(int key, String name, String description) {
        PropertyTwoLineItem propertyTwoLineItem = new PropertyTwoLineItem(this);
        propertyTwoLineItem.setKey(key);
        propertyTwoLineItem.setName(name);
        propertyTwoLineItem.setDesc(description);
        content.addView(propertyTwoLineItem);
    }

    private void l() {
        String var1 = xB.b().a(getApplicationContext(), R.string.system_information_title_android_version);
        String var2 = GB.b() + "(" + VERSION.RELEASE + ")";
        a(n, var1, var2);
    }

    private void m() {
        String var1 = xB.b().a(getApplicationContext(), R.string.system_information_title_android_version);
        String var2 = "API - " + VERSION.SDK_INT;
        a(m, var1, var2);
    }

    private void n() {
        PropertyOneLineItem propertyOneLineItem = new PropertyOneLineItem(this);
        propertyOneLineItem.setKey(r);
        propertyOneLineItem.setName(xB.b().a(getApplicationContext(), R.string.system_information_developer_options));
        content.addView(propertyOneLineItem);
        propertyOneLineItem.setOnClickListener(v -> {
            if (!mB.a()) {
                try {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                    startActivity(intent);
                } catch (ActivityNotFoundException ignored) {
                }
            }
        });
    }

    private void o() {
        float[] var1 = GB.b(this);
        a(p, xB.b().a(getApplicationContext(), R.string.system_information_dpi), String.valueOf(var1[0]));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        a(toolbar);
        d().d(true);
        d().e(true);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), R.string.program_information_title_system_information));
        toolbar.setNavigationOnClickListener(v -> {
            if (!mB.a()) onBackPressed();
        });
        content = findViewById(R.id.content);
        m();
        l();
        q();
        o();
        p();
        n();
    }

    private void p() {
        a(q, xB.b().a(getApplicationContext(), R.string.system_information_model_name), Build.MODEL);
    }

    private void q() {
        int[] var1 = GB.c(this);
        String var2 = xB.b().a(getApplicationContext(), R.string.system_information_system_resolution);
        String var3 = var1[0] + " x " + var1[1];
        a(o, var2, var3);
    }
}
