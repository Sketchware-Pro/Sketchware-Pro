package com.besome.sketch;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

public class InitActivity extends Activity implements Runnable {

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onStop() {
        mHandler.removeCallbacks(this);
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mHandler.postDelayed(this, 2000);
    }

    @Override
    public void run() {
        if (!isFinishing()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
