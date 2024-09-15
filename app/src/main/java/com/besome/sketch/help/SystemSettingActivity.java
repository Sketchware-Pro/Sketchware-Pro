package com.besome.sketch.help;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.core.widget.NestedScrollView;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.sketchware.remod.R;

import mod.hey.studios.util.Helper;

public class SystemSettingActivity extends BaseAppCompatActivity {
    private SharedPreferences.Editor preferenceEditor;

    private LinearLayout content;
    private NestedScrollView contentLayout;
    private AppBarLayout appBarLayout;
    private MaterialToolbar topAppBar;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    public void onBackPressed() {
        if (saveSettings()) {
            setResult(Activity.RESULT_OK, new Intent());
            finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prefences_content_appbar);

        content = findViewById(R.id.content);
        topAppBar = findViewById(R.id.topAppBar);
        appBarLayout = findViewById(R.id.appBarLayout);
        contentLayout = findViewById(R.id.contentLayout);
        collapsingToolbar = findViewById(R.id.collapsingToolbar);

        topAppBar.setTitle(R.string.main_drawer_title_system_settings);
        topAppBar.setNavigationOnClickListener(view -> onBackPressed());
        loadPreferences();
    }

    private void loadPreferences() {
        SharedPreferences preferences = getSharedPreferences("P12", Context.MODE_PRIVATE);
        preferenceEditor = preferences.edit();
    }

    private boolean saveSettings() {
        return preferenceEditor.commit();
    }
}
