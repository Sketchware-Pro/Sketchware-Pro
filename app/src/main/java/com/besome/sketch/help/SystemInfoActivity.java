package com.besome.sketch.help;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.PropertyOneLineItem;
import com.besome.sketch.lib.ui.PropertyTwoLineItem;

import a.a.a.GB;
import a.a.a.mB;
import a.a.a.xB;

public class SystemInfoActivity extends BaseAppCompatActivity {

    public final int m = 0;
    public final int n = 1;
    public final int o = 2;
    public final int p = 3;
    public final int q = 4;
    public final int r = 5;
    public Toolbar k;
    public LinearLayout l;

    private void a(int key, String name, String description) {
        PropertyTwoLineItem propertyTwoLineItem = new PropertyTwoLineItem(this);
        propertyTwoLineItem.setKey(key);
        propertyTwoLineItem.setName(name);
        propertyTwoLineItem.setDesc(description);
        l.addView(propertyTwoLineItem);
    }

    private void l() {
        String var1 = xB.b().a(getApplicationContext(), 2131626395);
        String var2 = GB.b() + "(" + VERSION.RELEASE + ")";
        a(1, var1, var2);
    }

    private void m() {
        String var1 = xB.b().a(getApplicationContext(), 2131626395);
        String var2 = "API - " + VERSION.SDK_INT;
        a(0, var1, var2);
    }

    private void n() {
        PropertyOneLineItem propertyOneLineItem = new PropertyOneLineItem(this);
        propertyOneLineItem.setKey(5);
        propertyOneLineItem.setName(xB.b().a(getApplicationContext(), 2131626391));
        l.addView(propertyOneLineItem);
        propertyOneLineItem.setOnClickListener(view -> {
            if (!mB.a()) {
                try {
                    Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                    startActivity(intent);
                } catch (ActivityNotFoundException ignored) {
                }
            }
        });
    }

    private void o() {
        float[] var1 = GB.b(this);
        a(3, xB.b().a(getApplicationContext(), 2131626392), String.valueOf(var1[0]));
    }

    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        setContentView(2131427741);
        k = findViewById(2131231847);
        a(k);
        d().d(true);
        d().e(true);
        findViewById(2131231370).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), 2131625772));
        k.setNavigationOnClickListener(view -> {
            if (!mB.a()) onBackPressed();
        });
        l = findViewById(2131230932);
        m();
        l();
        q();
        o();
        p();
        n();
    }

    private void p() {
        a(4, xB.b().a(getApplicationContext(), 2131626393), Build.MODEL);
    }

    private void q() {
        int[] var1 = GB.c(this);
        String var2 = xB.b().a(getApplicationContext(), 2131626394);
        String var3 = var1[0] + " x " + var1[1];
        a(2, var2, var3);
    }
}
