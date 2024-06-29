package com.besome.sketch.help;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.core.widget.NestedScrollView;

import com.besome.sketch.editor.property.PropertySwitchItem;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.R;

import a.a.a.mB;
import mod.hey.studios.util.Helper;

public class SystemSettingActivity extends BaseAppCompatActivity {
    private SharedPreferences.Editor preferenceEditor;

    private LinearLayout content;
    private NestedScrollView contentLayout;
    private com.google.android.material.appbar.AppBarLayout appBarLayout;
    private com.google.android.material.appbar.MaterialToolbar topAppBar;
    private com.google.android.material.appbar.CollapsingToolbarLayout collapsingToolbar;

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
        loadPrefences();
    }

    private void loadPrefences() {
        SharedPreferences preferences = getSharedPreferences("P12", Context.MODE_PRIVATE);
        preferenceEditor = preferences.edit();

        addPreference(0, R.string.system_settings_title_setting_vibration, R.string.system_settings_description_setting_vibration, preferences.getBoolean("P12I0", true));
        addPreference(1, R.string.system_settings_title_automatically_save, R.string.system_settings_description_automatically_save, preferences.getBoolean("P12I2", false));
    }

    private void addPreference(int key, int resName, int resDescription, boolean value) {
        PropertySwitchItem switchItem = new PropertySwitchItem(this);
        switchItem.setKey(key);
        switchItem.setName(Helper.getResString(resName));
        switchItem.setDesc(Helper.getResString(resDescription));
        switchItem.setValue(value);
        content.addView(switchItem);
    }

    private boolean saveSettings() {
        for (int i = 0; i < content.getChildCount(); i++) {
            View childAtView = content.getChildAt(i);
            if (childAtView instanceof PropertySwitchItem) {
                PropertySwitchItem propertySwitchItem = (PropertySwitchItem) childAtView;
                if (0 == propertySwitchItem.getKey()) {
                    preferenceEditor.putBoolean("P12I0", propertySwitchItem.getValue());
                } else if (1 == propertySwitchItem.getKey()) {
                    preferenceEditor.putBoolean("P12I2", propertySwitchItem.getValue());
                }
            }
        }
        return preferenceEditor.commit();
    }
}