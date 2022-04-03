package com.besome.sketch;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

public class InitActivity extends Activity implements Runnable {

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
            return;
        }
        //We don't need to do anything here, so let's move on to MainActivity.
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }*/

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
