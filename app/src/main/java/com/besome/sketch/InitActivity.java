package com.besome.sketch;

import android.content.Intent;
import android.os.Bundle;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

public class InitActivity extends BaseAppCompatActivity {

    @Override
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
    }
}
