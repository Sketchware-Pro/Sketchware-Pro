package com.besome.sketch.tools;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.besome.sketch.lib.base.BaseActivity;
import com.google.android.gms.analytics.HitBuilders;

import mod.hey.studios.util.CompileLogHelper;

public class CompileLogActivity extends BaseActivity {

    private TextView e;

    @Override
    public void onResume() {
        super.onResume();
        a.setScreenName(CompileLogActivity.class.getSimpleName());
        a.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427388);
        e = findViewById(2131231922);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
        } else {
            e.setText(CompileLogHelper.colorErrsAndWarnings(intent.getStringExtra("error")));
            e.setTextIsSelectable(true);
        }
    }
}
