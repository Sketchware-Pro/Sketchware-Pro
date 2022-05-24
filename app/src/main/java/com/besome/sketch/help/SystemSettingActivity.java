package com.besome.sketch.help;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.editor.property.PropertySwitchItem;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.R;

import a.a.a.DB;
import a.a.a.mB;
import a.a.a.xB;

public class SystemSettingActivity extends BaseAppCompatActivity {

    private final Intent v = new Intent();
    private LinearLayout o;
    private DB u;

    public SystemSettingActivity() {
    }

    private void a(int key, int resName, int resDescription, boolean value) {
        a(key, xB.b().a(getApplicationContext(), resName), xB.b().a(getApplicationContext(), resDescription), value);
    }

    private void a(int key, String name, String description, boolean value) {
        PropertySwitchItem switchItem = new PropertySwitchItem(this);
        switchItem.setKey(key);
        switchItem.setName(name);
        switchItem.setDesc(description);
        switchItem.setValue(value);
        o.addView(switchItem);
    }

    @Override
    public void onBackPressed() {
        if (!isSettingsSaved()) {
            setResult(-1, v);
            finish();
        }
    }

    @Override
    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        setContentView(R.layout.system_settings);
        Toolbar n = (Toolbar) findViewById(R.id.toolbar);
        a(n);
        d().d(true);
        d().e(true);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(this, R.string.main_drawer_title_system_settings));
        n.setNavigationOnClickListener(view -> {
            if (!mB.a()) onBackPressed();
        });
        o = (LinearLayout) findViewById(R.id.content);
        u = new DB(getApplicationContext(), "P12");
        a(0, R.string.system_settings_title_setting_vibration,
                R.string.system_settings_description_setting_vibration,
                u.a("P12I0", true));

        a(1, R.string.system_settings_title_automatically_save,
                R.string.system_settings_description_automatically_save
                , u.a("P12I2", false));
    }

    private boolean isSettingsSaved() {
        for (int i = 0; i < o.getChildCount(); i++) {
            View childAtView = o.getChildAt(i);
            if (childAtView instanceof PropertySwitchItem) {
                PropertySwitchItem propertySwitchItem = (PropertySwitchItem) childAtView;
                switch (propertySwitchItem.getKey()) {
                    case 0:
                        u.a("P12I0", propertySwitchItem.getValue());
                        return true;

                    case 1:
                        u.a("P12I2", propertySwitchItem.getValue());
                        return true;
                }
            }
        }
        return false;
    }
}
