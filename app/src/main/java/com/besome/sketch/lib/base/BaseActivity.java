package com.besome.sketch.lib.base;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;

import com.besome.sketch.SketchApplication;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

import a.a.a.MA;
import a.a.a.ZA;

public class BaseActivity extends Activity {
    public Tracker a;
    @Deprecated
    public Context b;
    public ZA c;
    public ArrayList<MA> d;

    public void a() {
        if (c != null && c.isShowing()) {
            c.dismiss();
        }
    }

    public void a(MA task) {
        d.add(task);
    }

    public void b() {
        if (c != null && !c.isShowing()) {
            c.show();
            new Handler().postDelayed(() -> c.d(), 5000L);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d = new ArrayList<>();
        b = getApplicationContext();
        c = new ZA(this);
        a = ((SketchApplication) getApplication()).a();
        a.enableAdvertisingIdCollection(true);
        a.enableExceptionReporting(true);
    }

    @Override
    public void onDestroy() {
        if (c != null && c.isShowing()) {
            c.a();
        }

        for (MA task : d) {
            if (task.getStatus() != Status.FINISHED) {
                task.cancel(false);
            }
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        if (c != null && c.isShowing()) {
            c.b();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (c != null && c.isShowing()) {
            c.c();
        }
    }
}
