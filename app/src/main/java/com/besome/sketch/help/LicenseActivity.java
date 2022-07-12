package com.besome.sketch.help;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.R;

import a.a.a.mB;
import a.a.a.oB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class LicenseActivity extends BaseAppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oss);

        Toolbar toolbar = findViewById(R.id.toolbar);
        a(toolbar);
        d().d(true);
        d().e(true);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(Helper.getResString(R.string.program_information_title_open_source_license));
        toolbar.setNavigationOnClickListener(view -> {
            if (!mB.a()) onBackPressed();
        });

        TextView licensesText = findViewById(R.id.tv_oss);
        licensesText.setText(new oB().b(getApplicationContext(), "oss.txt"));
        licensesText.setAutoLinkMask(Linkify.WEB_URLS);
        licensesText.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
