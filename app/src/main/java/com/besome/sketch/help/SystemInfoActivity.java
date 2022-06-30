package com.besome.sketch.help;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.PropertyOneLineItem;
import com.besome.sketch.lib.ui.PropertyTwoLineItem;
import com.sketchware.remod.R;

import a.a.a.GB;
import a.a.a.mB;
import a.a.a.xB;

public class SystemInfoActivity extends BaseAppCompatActivity {

    private LinearLayout content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_info);

        Toolbar toolbar = findViewById(R.id.toolbar);
        a(toolbar);
        d().d(true);
        d().e(true);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), R.string.program_information_title_system_information));
        toolbar.setNavigationOnClickListener(v -> {
            if (!mB.a()) onBackPressed();
        });
        content = findViewById(R.id.content);
        addApiLevelInfo();
        addAndroidVersionNameInfo();
        addScreenResolutionInfo();
        addScreenDpiInfo();
        addModelNameInfo();
        addDeveloperOptionsShortcut();
    }

    private void addInfo(int key, String name, String description) {
        PropertyTwoLineItem propertyTwoLineItem = new PropertyTwoLineItem(this);
        propertyTwoLineItem.setKey(key);
        propertyTwoLineItem.setName(name);
        propertyTwoLineItem.setDesc(description);
        content.addView(propertyTwoLineItem);
    }

    private void addAndroidVersionNameInfo() {
        addInfo(1, xB.b().a(getApplicationContext(), R.string.system_information_title_android_version),
                GB.b() + "(" + VERSION.RELEASE + ")");
    }

    private void addApiLevelInfo() {
        addInfo(0, xB.b().a(getApplicationContext(), R.string.system_information_title_android_version),
                "API - " + VERSION.SDK_INT);
    }

    private void addDeveloperOptionsShortcut() {
        PropertyOneLineItem propertyOneLineItem = new PropertyOneLineItem(this);
        propertyOneLineItem.setKey(5);
        propertyOneLineItem.setName(xB.b().a(getApplicationContext(), R.string.system_information_developer_options));
        content.addView(propertyOneLineItem);
        propertyOneLineItem.setOnClickListener(v -> {
            if (!mB.a()) {
                try {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
                    startActivity(intent);
                } catch (ActivityNotFoundException ignored) {
                }
            }
        });
    }

    private void addScreenDpiInfo() {
        float[] dpiXY = GB.b(this);
        addInfo(3, xB.b().a(getApplicationContext(), R.string.system_information_dpi), String.valueOf(dpiXY[0]));
    }

    private void addModelNameInfo() {
        addInfo(4, xB.b().a(getApplicationContext(), R.string.system_information_model_name), Build.MODEL);
    }

    private void addScreenResolutionInfo() {
        int[] widthHeight = GB.c(this);
        addInfo(2, xB.b().a(getApplicationContext(), R.string.system_information_system_resolution),
                widthHeight[0] + " x " + widthHeight[1]);
    }
}
