package com.besome.sketch.lib.base;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask.Status;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

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
    public Zo j;
    private ZA lottieDialog;
    protected _A progressDialog;
    private ArrayList<MA> taskList;

    public void a(MA var1) {
        taskList.add(var1);
    }

    public void a(OnCancelListener cancelListener) {
        if (progressDialog != null && !progressDialog.isShowing()) {
            progressDialog.setOnCancelListener(cancelListener);
            progressDialog.show();
        }
    }

    public void a(String var1) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.a(var1);
        }
    }

    public void g() {
        for (MA task : taskList) {
            if (task.getStatus() != Status.FINISHED && !task.isCancelled()) {
                task.cancel(true);
            }
        }
        taskList.clear();
    }

    public void h() {
        try {
            if (lottieDialog != null && lottieDialog.isShowing()) {
                lottieDialog.dismiss();
            }
        } catch (Exception var2) {
            lottieDialog = null;
            lottieDialog = new ZA(this);
        }
    }

    public void i() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception var2) {
            progressDialog = null;
            progressDialog = new _A(this);
        }

    }

    public boolean j() {
        return zd.a(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && zd.a(this, "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }

    public void k() {
        if (lottieDialog != null && !lottieDialog.isShowing() && !isFinishing()) {
            lottieDialog.show();
        }
    }

    @Override
    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        e = getApplicationContext();
        j = new Zo(getApplicationContext());
        d = new Tracker();
        taskList = new ArrayList<>();
        lottieDialog = new ZA(this);
        lC.a(getApplicationContext(), false);
        progressDialog = new _A(this);
    }

    @Override
    public void onDestroy() {
        g();
        if (lottieDialog != null && lottieDialog.isShowing()) {
            lottieDialog.cancelAnimation();
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        if (lottieDialog != null && lottieDialog.isShowing()) {
            lottieDialog.pauseAnimation();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lottieDialog != null && lottieDialog.isShowing()) {
            lottieDialog.resumeAnimation();
        }
    }
}
