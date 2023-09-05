package com.besome.sketch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.besome.sketch.help.ProgramInfoActivity;
import com.besome.sketch.help.SystemSettingActivity;
import com.besome.sketch.tools.NewKeyStoreActivity;
import com.google.android.material.navigation.NavigationView;
import com.sketchware.remod.R;

import a.a.a.GB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.wB;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.Tools;
import mod.ilyasse.activities.about.AboutModActivity;

public class MainDrawer extends FrameLayout {

    private Context context;

    public MainDrawer(Context context) {
        super(context);
        initialize(context);
    }

    public MainDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private void initialize(Context context) {
        this.context = context;
        wB.a(context, this, R.layout.main_drawer);
        NavigationView navView = findViewById(R.id.layout_main);
        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            initializeSocialLinks(id);
            initializeDrawerItems(id);
            return false;
        });
    }

    private void initializeSocialLinks(int id) {
        if (!mB.a()) {
            if (id == R.id.social_discord) {
                openUrl(context.getString(R.string.link_discord_invite));
            } else if (id == R.id.social_telegram) {
                openUrl(context.getString(R.string.link_telegram_invite));
            } else if (id == R.id.social_github) {
                openUrl(context.getString(R.string.link_github_url));
            }
        }
    }

    private void initializeDrawerItems(int id) {
        Activity activity = (Activity) context;
        if (id == R.id.about_team) {
            Intent intent = new Intent(context, AboutModActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        } else if (id == R.id.changelog) {
            Intent intent = new Intent(context, AboutModActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("select", "changelog");
            context.startActivity(intent);
        } else if (id == R.id.system_settings) {
            Intent intent = new Intent(context, SystemSettingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivityForResult(intent, 107);
        } else if (id == R.id.program_info) {
            Intent intent = new Intent(context, ProgramInfoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivityForResult(intent, 105);
        } else if (id == R.id.dev_tools) {
            Intent intent = new Intent(context, Tools.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        } else if (id == R.id.create_release_keystore) {
            Intent intent = new Intent(context, NewKeyStoreActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
        }
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }
}
