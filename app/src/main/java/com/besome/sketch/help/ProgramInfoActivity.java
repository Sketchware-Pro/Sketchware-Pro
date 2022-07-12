package com.besome.sketch.help;

import android.content.Intent;
import android.graphics.Color;
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
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class ProgramInfoActivity extends BaseAppCompatActivity implements OnClickListener {

    private static final int ITEM_SYSTEM_INFORMATION = 1;
    private static final int ITEM_UPDATE_LOG = 4;
    private static final int ITEM_SOCIAL_NETWORK = 5;
    private static final int ITEM_FACEBOOK = 6;
    private static final int ITEM_MEDIUM = 8;
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
        item.setBackgroundColor(0xfff6f6f6);
        item.setOnClickListener(this);
        if (key != ITEM_UPDATE_LOG) {
            if (key == ITEM_FACEBOOK || key == ITEM_MEDIUM) {
                return;
            }

            if (key != ITEM_SUGGEST_IDEAS) {
                return;
            }
        }

        item.setBackgroundColor(Color.WHITE);
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

    private void openMedium() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Helper.getResString(R.string.besome_blog_url)));
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(Intent.createChooser(intent, Helper.getResString(R.string.common_word_choose)));
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
        dialog.b(Helper.getResString(R.string.common_word_yes), v -> {
            if (!mB.a()) {
                int buttonId = radioGroup.getCheckedRadioButtonId();
                boolean resetOnlySettings = buttonId != R.id.rb_all;
                dialog.dismiss();
                setResult(RESULT_OK, getIntent().putExtra("onlyConfig", resetOnlySettings));
                finish();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void openFacebook() {
        String facebookPageUrl = Helper.getResString(R.string.facebook_url);

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + facebookPageUrl)));
        } catch (Exception e) {
            startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookPageUrl)),
                    Helper.getResString(R.string.common_word_choose)));
        }
    }

    private void openIdeasSite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ideas.sketchware.io/"));
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(Intent.createChooser(intent, Helper.getResString(R.string.common_word_choose)));
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
                    case ITEM_SYSTEM_INFORMATION:
                        toSystemInfoActivity();
                        break;

                    case ITEM_OPEN_SOURCE_LICENSES:
                        if (!GB.h(getApplicationContext())) {
                            bB.a(getApplicationContext(), Helper.getResString(R.string.common_message_check_network), bB.TOAST_NORMAL).show();
                        } else {
                            toLicenseActivity();
                        }
                        break;

                    default:
                }
            }

            if (v instanceof PropertyTwoLineItem) {
                key = ((PropertyTwoLineItem) v).getKey();
                switch (key) {
                    case ITEM_UPDATE_LOG:
                        if (!GB.h(getApplicationContext())) {
                            bB.a(getApplicationContext(), Helper.getResString(R.string.common_message_check_network), bB.TOAST_NORMAL).show();
                        } else {
                            openBlog();
                        }
                        break;

                    case ITEM_SUGGEST_IDEAS:
                        openIdeasSite();
                        break;

                    case ITEM_MEDIUM:
                        openMedium();
                        break;

                    case ITEM_FACEBOOK:
                        openFacebook();
                        break;

                    default:
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        a(toolbar);
        d().d(true);
        d().e(true);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(Helper.getResString(R.string.main_drawer_title_program_information));
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        content = findViewById(R.id.content);

        TextView version = findViewById(R.id.tv_sketch_ver);
        version.setText("Version " + GB.e(getApplicationContext()));
        Button resetSystem = findViewById(R.id.btn_app_init);
        resetSystem.setText(Helper.getResString(R.string.program_information_button_reset_system));
        resetSystem.setOnClickListener(this);
        Button checkForUpdates = findViewById(R.id.btn_app_upgrade);
        checkForUpdates.setText(Helper.getResString(R.string.program_information_button_check_update));
        checkForUpdates.setOnClickListener(this);
        addTwoLineItem(ITEM_UPDATE_LOG, R.string.program_information_title_docs, R.string.docs_url);
        addTwoLineItem(ITEM_SUGGEST_IDEAS, R.string.program_information_title_suggest_ideas, R.string.ideas_url);
        addSingleLineItem(ITEM_SOCIAL_NETWORK, R.string.title_community);
        addTwoLineItem(ITEM_FACEBOOK, R.string.title_facebook_community, R.string.facebook_url);
        addTwoLineItem(ITEM_MEDIUM, R.string.title_besome_blog, R.string.besome_blog_url);
        addSingleLineItem(ITEM_SYSTEM_INFORMATION, R.string.program_information_title_system_information);
        addSingleLineItem(ITEM_OPEN_SOURCE_LICENSES, R.string.program_information_title_open_source_license);
    }

    private void toLicenseActivity() {
        Intent intent = new Intent(getApplicationContext(), LicenseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    private void openBlog() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.sketchware.io/blog"));
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(Intent.createChooser(intent, Helper.getResString(R.string.common_word_choose)));
    }

    private void toSystemInfoActivity() {
        Intent intent = new Intent(getApplicationContext(), SystemInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
