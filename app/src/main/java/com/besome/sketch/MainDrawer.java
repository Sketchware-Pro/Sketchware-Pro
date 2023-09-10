package com.besome.sketch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.core.view.WindowInsetsCompat;

import com.besome.sketch.help.ProgramInfoActivity;
import com.besome.sketch.help.SystemSettingActivity;
import com.besome.sketch.tools.NewKeyStoreActivity;
import com.google.android.material.navigation.NavigationView;
import com.sketchware.remod.R;

import a.a.a.mB;
import a.a.a.wB;
import dev.chrisbanes.insetter.Insetter;
import dev.chrisbanes.insetter.Side;
import mod.hilal.saif.activities.tools.Tools;
import mod.ilyasse.activities.about.AboutModActivity;

public class MainDrawer extends FrameLayout {
    public MainDrawer(Context context) {
        this(context, null);
    }

    public MainDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        wB.a(context, this, R.layout.main_drawer);
        NavigationView navView = findViewById(R.id.layout_main);
        var layoutDirection = getResources().getConfiguration().getLayoutDirection();
        Insetter.builder()
                .margin(WindowInsetsCompat.Type.navigationBars(),
                        Side.create(layoutDirection == LAYOUT_DIRECTION_LTR,
                                false, layoutDirection == LAYOUT_DIRECTION_RTL, false))
                .applyToView(navView);

        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            initializeSocialLinks(id);
            initializeDrawerItems(id);
            return false;
        });
    }

    private void initializeSocialLinks(@IdRes int id) {
        if (!mB.a()) {
            @StringRes int url = -1;
            if (id == R.id.social_discord) {
                url = R.string.link_discord_invite;
            } else if (id == R.id.social_telegram) {
                url = R.string.link_telegram_invite;
            } else if (id == R.id.social_github) {
                url = R.string.link_github_url;
            }

            if (url != -1) {
                openUrl(getContext().getString(url));
            }
        }
    }

    private void initializeDrawerItems(@IdRes int id) {
        Activity activity = (Activity) getContext();
        if (id == R.id.about_team) {
            Intent intent = new Intent(activity, AboutModActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);
        } else if (id == R.id.changelog) {
            Intent intent = new Intent(activity, AboutModActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("select", "changelog");
            activity.startActivity(intent);
        } else if (id == R.id.system_settings) {
            Intent intent = new Intent(activity, SystemSettingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivityForResult(intent, 107);
        } else if (id == R.id.program_info) {
            Intent intent = new Intent(activity, ProgramInfoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivityForResult(intent, 105);
        } else if (id == R.id.dev_tools) {
            Intent intent = new Intent(activity, Tools.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);
        } else if (id == R.id.create_release_keystore) {
            Intent intent = new Intent(activity, NewKeyStoreActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);
        }
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        getContext().startActivity(intent);
    }
}
