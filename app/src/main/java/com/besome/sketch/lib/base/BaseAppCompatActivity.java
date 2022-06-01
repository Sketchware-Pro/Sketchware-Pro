package com.besome.sketch.lib.base;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask.Status;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.besome.sketch.SketchApplication;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

import a.a.a.EA;
import a.a.a.MA;
import a.a.a.ZA;
import a.a.a.Zo;
import a.a.a._A;
import a.a.a.lC;
import a.a.a.zd;

public class BaseAppCompatActivity extends AppCompatActivity {

    public Tracker d;
    @Deprecated
    public Context e;
    public ZA f;
    public _A g;
    public ArrayList<MA> h;
    public EA i;
    public Zo j;

    public void a(MA var1) {
        h.add(var1);
    }

    public void a(OnCancelListener var1) {
        if (g != null && !g.isShowing()) {
            g.setOnCancelListener(var1);
            g.show();
        }
    }

    public void a(String var1) {
        if (g != null && g.isShowing()) {
            g.a(var1);
        }
    }

    public void g() {
        for (MA task : h) {
            if (task.getStatus() != Status.FINISHED && !task.isCancelled()) {
                task.cancel(true);
            }
        }
        h.clear();
    }

    public void h() {
        try {
            if (f != null && f.isShowing()) {
                f.dismiss();
            }
        } catch (Exception var2) {
            f = null;
            f = new ZA(this);
        }
    }

    public void i() {
        try {
            if (g != null && g.isShowing()) {
                g.dismiss();
            }
        } catch (Exception var2) {
            g = null;
            g = new _A(this);
        }

    }

    public boolean j() {
        return zd.a(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && zd.a(this, "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }

    public void k() {
        if (f != null && !f.isShowing() && !isFinishing()) {
            f.show();
        }
    }

    @Override
    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        e = getApplicationContext();
        i = new EA(getApplicationContext());
        j = new Zo(getApplicationContext());
        d = ((SketchApplication) getApplication()).a();
        d.enableAdvertisingIdCollection(true);
        d.enableExceptionReporting(true);
        h = new ArrayList<>();
        f = new ZA(this);
        lC.a(getApplicationContext(), false);
        g = new _A(this);
    }

    @Override
    public void onDestroy() {
        g();
        if (f != null && f.isShowing()) {
            f.a();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        if (f != null && f.isShowing()) {
            f.b();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (f != null && f.isShowing()) {
            f.c();
        }
    }
}
