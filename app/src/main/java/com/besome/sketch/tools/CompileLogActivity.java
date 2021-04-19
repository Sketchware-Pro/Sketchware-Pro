package com.besome.sketch.tools;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.besome.sketch.lib.base.BaseActivity;
import com.sketchware.remod.Resources;

import mod.hey.studios.util.CompileLogHelper;

public class CompileLogActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.compile_log);
        TextView tv_compile_log = findViewById(Resources.id.tv_compile_log);

        Intent intent = getIntent();
        if (intent == null) {
            finish();
        } else {
            tv_compile_log.setText(CompileLogHelper.colorErrsAndWarnings(intent.getStringExtra("error")));
            tv_compile_log.setTextIsSelectable(true);
        }
    }
}
