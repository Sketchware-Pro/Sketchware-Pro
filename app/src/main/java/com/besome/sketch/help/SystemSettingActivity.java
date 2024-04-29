package com.besome.sketch.help;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.sketchware.remod.R;

import a.a.a.mB;
import mod.hey.studios.util.Helper;

public class SystemSettingActivity extends BaseAppCompatActivity {

    private LinearLayout contentLayout;
    private SharedPreferences.Editor preferenceEditor;

    private void addPreference(int key, int resName, int resDescription, boolean value) {
        View switchLayout = LayoutInflater.from(this).inflate(R.layout.switch_layout, null);
        SwitchMaterial materialSwitch = switchLayout.findViewById(R.id.material_switch);
        materialSwitch.setChecked(value);
        materialSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (key == 0) {
                preferenceEditor.putBoolean("P12I0", isChecked);
            } else if (key == 1) {
                preferenceEditor.putBoolean("P12I2", isChecked);
            }
            preferenceEditor.apply();
        });

        TextView nameTextView = switchLayout.findViewById(R.id.text_name);
        TextView descTextView = switchLayout.findViewById(R.id.text_desc);
        nameTextView.setText(Helper.getResString(resName));
        descTextView.setText(Helper.getResString(resDescription));

        contentLayout.addView(switchLayout);
    }

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
        setContentView(R.layout.system_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(Helper.getResString(R.string.main_drawer_title_system_settings));
        toolbar.setNavigationOnClickListener(view -> {
            if (!mB.a()) onBackPressed();
        });

        contentLayout = findViewById(R.id.content);
        SharedPreferences preferences = getSharedPreferences("P12", Context.MODE_PRIVATE);
        preferenceEditor = preferences.edit();

        addPreference(0, R.string.system_settings_title_setting_vibration,
                R.string.system_settings_description_setting_vibration,
                preferences.getBoolean("P12I0", true));

        addPreference(1, R.string.system_settings_title_automatically_save,
                R.string.system_settings_description_automatically_save,
                preferences.getBoolean("P12I2", false));
    }

    private boolean saveSettings() {
        return preferenceEditor.commit();
    }
}
