package com.besome.sketch.lib.base;

import android.app.Activity;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

import a.a.a.MA;
import a.a.a.ZA;

public class BaseActivity extends Activity {

    private ZA dialog;
    private ArrayList<MA> taskList;

    public void a() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void a(MA task) {
        taskList.add(task);
    }

    public void b() {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
            new Handler().postDelayed(() -> dialog.d(), 5000L);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskList = new ArrayList<>();
        dialog = new ZA(this);
    }

    @Override
    public void onDestroy() {
        if (dialog != null && dialog.isShowing()) {
            dialog.a();
        }

        for (MA task : taskList) {
            if (task.getStatus() != Status.FINISHED) {
                task.cancel(false);
            }
        }
        super.onDestroy();
    }

    @Override
    public void onPause() {
        if (dialog != null && dialog.isShowing()) {
            dialog.b();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (dialog != null && dialog.isShowing()) {
            dialog.c();
        }
    }
}
