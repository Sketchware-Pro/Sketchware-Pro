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

    public Toolbar k;
    public LinearLayout l;

    private void a(int key, int name, int description) {
        a(key, getString(name), getString(description));
    }

    private void a(int key, String name) {
        PropertyOneLineItem item = new PropertyOneLineItem(this);
        item.setKey(key);
        item.setName(name);
        l.addView(item);
        if (key == 1 || key == 2 || key == 14 || key == 15) {
            item.setOnClickListener(this);
        }
    }

    private void a(int key, String name, String description) {
        PropertyTwoLineItem item = new PropertyTwoLineItem(this);
        item.setKey(key);
        item.setName(name);
        item.setDesc(description);
        l.addView(item);
        item.setBackgroundColor(0xfff6f6f6);
        item.setOnClickListener(this);
        if (key != 4) {
            if (key == 6 || key == 8) {
                return;
            }

            if (key != 17) {
                return;
            }
        }

        item.setBackgroundColor(Color.WHITE);
    }

    private void b(int key, int name) {
        a(key, getString(name));
    }

    private void l() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(xB.b().a(getApplicationContext(), R.string.besome_blog_url)));
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, xB.b().a(getApplicationContext(), R.string.common_word_choose)));
    }

    private void resetDialog() {
        aB dialog = new aB(this);
        dialog.b(xB.b().a(getApplicationContext(), R.string.program_information_reset_system_title));
        dialog.a(R.drawable.rollback_96);
        View rootView = wB.a(this, R.layout.all_init_popup);
        RadioGroup radioGroup = rootView.findViewById(R.id.rg_type);
        ((RadioButton) rootView.findViewById(R.id.rb_all)).setText(xB.b().a(getApplicationContext(), R.string.program_information_reset_system_title_all_settings_data));
        ((RadioButton) rootView.findViewById(R.id.rb_only_config)).setText(xB.b().a(getApplicationContext(), R.string.program_information_reset_system_title_all_settings));
        dialog.a(rootView);
        dialog.b(xB.b().a(getApplicationContext(), R.string.common_word_yes), v -> {
            if (!mB.a()) {
                int buttonId = radioGroup.getCheckedRadioButtonId();
                boolean resetOnlySettings = buttonId != R.id.rb_all;
                dialog.dismiss();
                setResult(RESULT_OK, getIntent().putExtra("onlyConfig", resetOnlySettings));
                finish();
            }
        });
        dialog.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> dialog.dismiss());
        dialog.show();
    }

    private void openFacebook() {
        String facebookPageUrl = xB.b().a(getApplicationContext(), R.string.facebook_url);

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + facebookPageUrl)));
        } catch (Exception e) {
            startActivity(Intent.createChooser(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookPageUrl)),
                    xB.b().a(getApplicationContext(), R.string.common_word_choose)));
        }
    }

    private void openIdeasSite() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ideas.sketchware.io/"));
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, xB.b().a(getApplicationContext(), R.string.common_word_choose)));
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
                    case 1:
                        toSystemInfoActivity();
                        break;

                    case 15:
                        if (!GB.h(getApplicationContext())) {
                            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.common_message_check_network), 0).show();
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
                    case 4:
                        if (!GB.h(getApplicationContext())) {
                            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.common_message_check_network), 0).show();
                        } else {
                            openBlog();
                        }
                        break;

                    case 17:
                        openIdeasSite();
                        break;

                    case 8:
                        l();
                        break;

                    case 6:
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
        k = findViewById(R.id.toolbar);
        a(k);
        d().d(true);
        d().e(true);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(this, R.string.main_drawer_title_program_information));
        k.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        l = findViewById(R.id.content);

        TextView version = findViewById(R.id.tv_sketch_ver);
        version.setText("Version " + GB.e(getApplicationContext()));
        Button resetSystem = findViewById(R.id.btn_app_init);
        resetSystem.setText(xB.b().a(getApplicationContext(), R.string.program_information_button_reset_system));
        resetSystem.setOnClickListener(this);
        Button checkForUpdates = findViewById(R.id.btn_app_upgrade);
        checkForUpdates.setText(xB.b().a(getApplicationContext(), R.string.program_information_button_check_update));
        checkForUpdates.setOnClickListener(this);
        a(4, R.string.program_information_title_docs, R.string.docs_url);
        a(17, R.string.program_information_title_suggest_ideas, R.string.ideas_url);
        b(5, R.string.title_community);
        a(6, R.string.title_facebook_community, R.string.facebook_url);
        a(8, R.string.title_besome_blog, R.string.besome_blog_url);
        b(1, R.string.program_information_title_system_information);
        b(15, R.string.program_information_title_open_source_license);
    }

    private void toLicenseActivity() {
        Intent intent = new Intent(getApplicationContext(), LicenseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivity(intent);
    }

    private void openBlog() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.sketchware.io/blog"));
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivity(Intent.createChooser(intent, xB.b().a(getApplicationContext(), R.string.common_word_choose)));
    }

    private void toSystemInfoActivity() {
        Intent intent = new Intent(getApplicationContext(), SystemInfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivity(intent);
    }
}
