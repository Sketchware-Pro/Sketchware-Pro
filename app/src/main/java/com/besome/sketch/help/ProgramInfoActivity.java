package com.besome.sketch.help;

import android.annotation.SuppressLint;
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
import a.a.a.xB;
import mod.hey.studios.util.Helper;

@SuppressLint("ResourceType")
public class ProgramInfoActivity extends BaseAppCompatActivity implements OnClickListener {

    public Toolbar k;
    public LinearLayout l;

    private void a(int var1, int var2, int var3) {
        a(var1, getString(var2), getString(var3));
    }

    private void a(int var1, String var2) {
        PropertyOneLineItem var3 = new PropertyOneLineItem(this);
        var3.setKey(var1);
        var3.setName(var2);
        l.addView(var3);
        if (var1 == 1 || var1 == 2 || var1 == 14 || var1 == 15) {
            var3.setOnClickListener(this);
        }

    }

    private void a(int var1, String var2, String var3) {
        PropertyTwoLineItem var4 = new PropertyTwoLineItem(this);
        var4.setKey(var1);
        var4.setName(var2);
        var4.setDesc(var3);
        l.addView(var4);
        var4.setBackgroundColor(0xfff6f6f6);
        var4.setOnClickListener(this);
        if (var1 != 4) {
            if (var1 == 6 || var1 == 8) {
                return;
            }

            if (var1 != 17) {
                return;
            }
        }

        var4.setBackgroundColor(0xffffffff);
    }

    private void b(int var1, int var2) {
        a(var1, getString(var2));
    }

    private void l() {
        Intent var1 = new Intent("android.intent.action.VIEW", Uri.parse(xB.b().a(getApplicationContext(), 2131624004)));
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Intent var2 = Intent.createChooser(var1, xB.b().a(getApplicationContext(), 2131624976));
        var1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        var1.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        var1.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivity(var2);
    }

    private void resetDialog() {
        aB dialog = new aB(this);
        dialog.b(xB.b().a(getApplicationContext(), 2131625764));
        dialog.a(2131166052);
        View rootView = wB.a(this, 2131427367);
        RadioGroup radioGroup = rootView.findViewById(2131231670);
        ((RadioButton) rootView.findViewById(2131231650)).setText(xB.b().a(getApplicationContext(), 2131625766));
        ((RadioButton) rootView.findViewById(2131231656)).setText(xB.b().a(getApplicationContext(), 2131625765));
        dialog.a(rootView);
        dialog.b(xB.b().a(getApplicationContext(), 2131625050), view -> {
            if (!mB.a()) {
                int buttonId = radioGroup.getCheckedRadioButtonId();
                boolean onlyConfig = buttonId != 2131231650;
                dialog.dismiss();
                setResult(-1, getIntent().putExtra("onlyConfig", onlyConfig));
                finish();
            }
        });
        dialog.a(xB.b().a(getApplicationContext(), 2131624974), view -> dialog.dismiss());
        dialog.show();
    }

    private void openFacebook() {
        String var1 = xB.b().a(getApplicationContext(), 2131625397);

        try {
            Uri var3 = Uri.parse("fb://facewebmodal/f?href=" + var1);
            Intent var5 = new Intent("android.intent.action.VIEW", var3);
            startActivity(var5);
        } catch (Exception var4) {
            startActivity(Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse(var1)), xB.b().a(getApplicationContext(), 2131624976)));
        }

    }

    private void openIdeasSite() {
        Intent var1 = new Intent("android.intent.action.VIEW", Uri.parse("https://ideas.sketchware.io/"));
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Intent var2 = Intent.createChooser(var1, xB.b().a(getApplicationContext(), 2131624976));
        var1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        var1.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        var1.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivity(var2);
    }

    public void onClick(View view) {
        if (!mB.a()) {
            if (view.getId() == 2131230807) {
                resetDialog();
            }

            int key;
            if (view instanceof PropertyOneLineItem) {
                key = ((PropertyOneLineItem) view).getKey();
                switch (key) {
                    case 1:
                        toSystemInfoActivity();
                        break;

                    case 15:
                        if (!GB.h(getApplicationContext())) {
                            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131624932), 0).show();
                            return;
                        }

                        toLicenseActivity();
                        break;

                }
            }

            if (view instanceof PropertyTwoLineItem) {
                key = ((PropertyTwoLineItem) view).getKey();
                switch (key) {
                    case 4:
                        if (!GB.h(getApplicationContext())) {
                            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131624932), 0).show();
                            return;
                        }
                        openBlog();
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
                }
            }

        }
    }

    @SuppressLint("SetTextI18n")
    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        setContentView(2131427612);
        k = findViewById(2131231847);
        a(k);
        d().d(true);
        d().e(true);
        findViewById(2131231370).setVisibility(View.GONE);
        d().a(xB.b().a(this, 2131625599));
        k.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        l = findViewById(2131230932);
        TextView var2 = findViewById(2131232167);
        var2.setText("Version " + GB.e(getApplicationContext()));
        Button var4 = findViewById(2131230807);
        var4.setText(xB.b().a(getApplicationContext(), 2131625761));
        var4.setOnClickListener(this);
        var4 = findViewById(2131230808);
        var4.setText(xB.b().a(getApplicationContext(), 2131625760));
        var4.setOnClickListener(this);
        a(4, R.string.program_information_title_docs, R.string.docs_url);
        a(17, R.string.program_information_title_suggest_ideas, R.string.ideas_url);
        b(5, R.string.title_community);
        a(6, R.string.title_facebook_community, R.string.facebook_url);
        a(8, R.string.title_besome_blog, R.string.besome_blog_url);
        b(1, R.string.program_information_title_system_information);
        b(15, R.string.program_information_title_open_source_license);
    }

    private void toLicenseActivity() {
        Intent var1 = new Intent(getApplicationContext(), LicenseActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        var1.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        var1.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivity(var1);
    }

    private void openBlog() {
        Intent var1 = new Intent("android.intent.action.VIEW", Uri.parse("https://docs.sketchware.io/blog"));
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Intent var2 = Intent.createChooser(var1, xB.b().a(getApplicationContext(), 2131624976));
        var1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        var1.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        var1.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivity(var2);
    }

    private void toSystemInfoActivity() {
        Intent var1 = new Intent(getApplicationContext(), SystemInfoActivity.class);
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        var1.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        var1.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        var1.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        startActivity(var1);
    }

}
