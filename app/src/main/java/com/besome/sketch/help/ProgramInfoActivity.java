package com.besome.sketch.help;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.PropertyOneLineItem;
import com.besome.sketch.lib.ui.PropertyTwoLineItem;
import com.sketchware.remod.R;

import a.a.a.GB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.wB;
import mod.hey.studios.util.Helper;

public class ProgramInfoActivity extends BaseAppCompatActivity implements OnClickListener {

    private static final int ITEM_SYSTEM_INFORMATION = 1;
    private static final int ITEM_DOCS_LOG = 4;
    private static final int ITEM_SOCIAL_NETWORK = 5;
    private static final int ITEM_DISCORD = 6;
    private static final int ITEM_TELEGRAM = 8;
    private static final int ITEM_OPEN_SOURCE_LICENSES = 15;
    private static final int ITEM_SUGGEST_IDEAS = 17;

    private LinearLayout content;

    private void addTwoLineItem(int key, int name, int description) {
        addTwoLineItem(key, getString(name), getString(description));
    }

    private void addTwoLineItem(int key, String name, String description) {
        PropertyTwoLineItem item = new PropertyTwoLineItem(this);
        item.setKey(key);
        item.setName(name);
        item.setDesc(description);
        content.addView(item);
        item.setOnClickListener(this);
    }

    private void addSingleLineItem(int key, int name) {
        addSingleLineItem(key, getString(name));
    }

    private void addSingleLineItem(int key, String name) {
        PropertyOneLineItem item = new PropertyOneLineItem(this);
        item.setKey(key);
        item.setName(name);
        content.addView(item);
        if (key == ITEM_SYSTEM_INFORMATION || key == ITEM_OPEN_SOURCE_LICENSES) {
            item.setOnClickListener(this);
        }
    }

    private void resetDialog() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.program_information_reset_system_title));
        dialog.a(R.drawable.rollback_96);
        View rootView = wB.a(this, R.layout.all_init_popup);
        RadioGroup radioGroup = rootView.findViewById(R.id.rg_type);
        ((RadioButton) rootView.findViewById(R.id.rb_all)).setText(Helper.getResString(R.string.program_information_reset_system_title_all_settings_data));
        ((RadioButton) rootView.findViewById(R.id.rb_only_config)).setText(Helper.getResString(R.string.program_information_reset_system_title_all_settings));
        dialog.a(rootView);
        dialog.b(Helper.getResString(R.string.common_word_yes), (d, which) -> {
            if (!mB.a()) {
                int buttonId = radioGroup.getCheckedRadioButtonId();
                boolean resetOnlySettings = buttonId != R.id.rb_all;
                d.dismiss();
                setResult(RESULT_OK, getIntent().putExtra("onlyConfig", resetOnlySettings));
                finish();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), (d, which) -> Helper.getDialogDismissListener(d));
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            if (v.getId() == R.id.btn_app_init) {
                resetDialog();
            }

            int key;
            if (v instanceof PropertyOneLineItem) {
                key = ((PropertyOneLineItem) v).getKey();
                switch (key) {
                    case ITEM_SYSTEM_INFORMATION -> toSystemInfoActivity();
                    case ITEM_OPEN_SOURCE_LICENSES -> {
                        if (!GB.h(getApplicationContext())) {
                            bB.a(getApplicationContext(), Helper.getResString(R.string.common_message_check_network), bB.TOAST_NORMAL).show();
                        } else {
                            toLicenseActivity();
                        }
                    }
                }
            }

            if (v instanceof PropertyTwoLineItem) {
                key = ((PropertyTwoLineItem) v).getKey();
                switch (key) {
                    case ITEM_DOCS_LOG -> openUrl(getString(R.string.link_docs_url));
                    case ITEM_SUGGEST_IDEAS -> openUrl(getString(R.string.link_ideas_url));
                    case ITEM_TELEGRAM -> openUrl(getString(R.string.link_telegram_invite));
                    case ITEM_DISCORD -> openUrl(getString(R.string.link_discord_invite));
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(Helper.getResString(R.string.main_drawer_title_program_information));
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        content = findViewById(R.id.content);

        TextView version = findViewById(R.id.tv_sketch_ver);
        version.setText(GB.e(getApplicationContext()));
        Button resetSystem = findViewById(R.id.btn_app_init);
        resetSystem.setOnClickListener(this);
        Button checkForUpdates = findViewById(R.id.btn_app_upgrade);
        checkForUpdates.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_github_release)));
            startActivity(intent);
        });
        addTwoLineItem(ITEM_DOCS_LOG, R.string.program_information_title_docs, R.string.link_docs_url);
        addTwoLineItem(ITEM_SUGGEST_IDEAS, R.string.program_information_title_suggest_ideas, R.string.link_ideas_url);
        addSingleLineItem(ITEM_SOCIAL_NETWORK, R.string.title_community);
        addTwoLineItem(ITEM_DISCORD, R.string.title_discord_community, R.string.link_discord_invite);
        addTwoLineItem(ITEM_TELEGRAM, R.string.title_telegram_community, R.string.link_telegram_invite);
        addSingleLineItem(ITEM_SYSTEM_INFORMATION, R.string.program_information_title_system_information);
        addSingleLineItem(ITEM_OPEN_SOURCE_LICENSES, R.string.program_information_title_open_source_license);
    }

    private void toLicenseActivity() {
        Intent intent = new Intent(getApplicationContext(), LicenseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void toSystemInfoActivity() {
        Intent intent = new Intent(getApplicationContext(), SystemInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

}
