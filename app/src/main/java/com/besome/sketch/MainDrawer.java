package com.besome.sketch;

import static com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.view.WindowInsetsCompat;

import com.besome.sketch.help.ProgramInfoActivity;
import com.besome.sketch.tools.NewKeyStoreActivity;
import com.google.android.material.navigation.NavigationView;

import a.a.a.mB;
import dev.chrisbanes.insetter.Insetter;
import dev.chrisbanes.insetter.Side;
import mod.hilal.saif.activities.tools.AppSettings;
import pro.sketchware.R;
import pro.sketchware.activities.about.AboutActivity;

public class MainDrawer extends NavigationView {
    private static final int DEF_STYLE_RES = R.style.Widget_SketchwarePro_NavigationView_Main;

    public MainDrawer(@NonNull Context context) {
        this(context, null);
    }

    public MainDrawer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.navigationViewStyle);
    }

    public MainDrawer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        context = getContext();

        var layoutDirection = context.getResources().getConfiguration().getLayoutDirection();
        Insetter.builder()
                .margin(WindowInsetsCompat.Type.navigationBars(),
                        Side.create(layoutDirection == LAYOUT_DIRECTION_LTR,
                                false, layoutDirection == LAYOUT_DIRECTION_RTL, false))
                .applyToView(this);

        inflateHeaderView(R.layout.main_drawer_header);
        inflateMenu(R.menu.main_drawer_menu);
        setNavigationItemSelectedListener(item -> {
            initializeSocialLinks(item.getItemId());
            initializeDrawerItems(item.getItemId());

            // Return false to prevent selection
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
        Activity activity = unwrap(getContext());
        if (id == R.id.about_team) {
            Intent intent = new Intent(activity, AboutActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);
        } else if (id == R.id.changelog) {
            Intent intent = new Intent(activity, AboutActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("select", "changelog");
            activity.startActivity(intent);
        } else if (id == R.id.program_info) {
            Intent intent = new Intent(activity, ProgramInfoActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivityForResult(intent, 105);
        } else if (id == R.id.app_settings) {
            Intent intent = new Intent(activity, AppSettings.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);
        } else if (id == R.id.create_release_keystore) {
            Intent intent = new Intent(activity, NewKeyStoreActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            activity.startActivity(intent);
        }
    }

    private void openUrl(String url) {
        Activity activity = unwrap(getContext());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
    }

    private Activity unwrap(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        return (Activity) context;
    }
}
