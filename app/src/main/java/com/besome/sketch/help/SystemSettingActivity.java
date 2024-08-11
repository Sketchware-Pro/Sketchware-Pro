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

import dev.trindadedev.lib.ui.components.preference.PreferenceSwitch;
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

        addPreference(0, R.string.system_settings_title_setting_vibration, R.string.system_settings_description_setting_vibration, preferences.getBoolean("P12I0", true));
        addPreference(1, R.string.system_settings_title_automatically_save, R.string.system_settings_description_automatically_save, preferences.getBoolean("P12I2", false));
    }

    private void addPreference(int key, int resName, int resDescription, boolean value) {
        PreferenceSwitch preferenceSwitch = new PreferenceSwitch(this);
        preferenceSwitch.setTitle(Helper.getResString(resName));
        preferenceSwitch.setDescription(Helper.getResString(resDescription));
        preferenceSwitch.setValue(value);

        preferenceSwitch.setSwitchChangedListener((buttonView, isChecked) -> {
            if (key == 0) {
                preferenceEditor.putBoolean("P12I0", isChecked);
            } else if (key == 1) {
                preferenceEditor.putBoolean("P12I2", isChecked);
            }
        });

        content.addView(preferenceSwitch);
    }

    private boolean saveSettings() {
        return preferenceEditor.commit();
    }
}
