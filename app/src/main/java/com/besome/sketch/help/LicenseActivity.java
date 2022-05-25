//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.besome.sketch.help;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

import a.a.a.mB;
import a.a.a.oB;
import a.a.a.xB;

public class LicenseActivity extends BaseAppCompatActivity {

    public Toolbar k;

    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        setContentView(2131427605);
        k = findViewById(2131231847);
        a(k);
        d().d(true);
        d().e(true);
        findViewById(2131231370).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), 2131625768));
        k.setNavigationOnClickListener(view -> {
            if (!mB.a()) onBackPressed();
        });
        TextView var2 = findViewById(2131232074);
        var2.setText((new oB()).b(getApplicationContext(), "oss.txt"));
        var2.setAutoLinkMask(1);
        var2.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
