package com.besome.sketch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
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
            if (id == R.id.social_slack) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setItems(new String[]{
                                Helper.getResString(R.string.main_drawer_context_menu_title_slack_invitation),
                                Helper.getResString(R.string.main_drawer_context_menu_title_slack_open)},
                        (dialog, which) -> {
                            if (which == 0) {
                                openSlackInvitationInBrowser();
                            } else {
                                if (GB.h(context)) {
                                    try {
                                        context.startActivity(context.getPackageManager().getLaunchIntentForPackage("com.Slack"));
                                    } catch (Exception e1) {
                                        openSlackInvitationInBrowser();
                                    }
                                } else {
                                    bB.a(context, Helper.getResString(R.string.common_message_check_network), bB.TOAST_NORMAL).show();
                                }
                            }
                        });
                AlertDialog create = builder.create();
                create.setCanceledOnTouchOutside(true);
                create.show();
            } else if (id == R.id.social_medium) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.besome_blog_url)));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    context.startActivity(intent);
                } catch (ActivityNotFoundException unused1) {
                    context.startActivity(Intent.createChooser(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.besome_blog_url))),
                            Helper.getResString(R.string.common_word_choose)
                    ));
                }
            } else if (id == R.id.social_fb) {
                String facebookUrl = context.getString(R.string.facebook_url);
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + facebookUrl)));
                } catch (Exception unused) {
                    context.startActivity(Intent.createChooser(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)),
                            Helper.getResString(R.string.common_word_choose)
                    ));
                }
            }
        }
    }

    private void openSlackInvitationInBrowser() {
        if (GB.h(context)) {
            try {
                Intent chooser = new Intent(Intent.ACTION_VIEW, Uri.parse(Helper.getResString(R.string.slack_url_primary)));
                chooser.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(Intent.createChooser(
                        chooser,
                        Helper.getResString(R.string.common_word_choose)
                ));
            } catch (Exception e) {
                Intent chooser = new Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.slack_url_secondary)));
                chooser.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                context.startActivity(Intent.createChooser(
                        chooser,
                        Helper.getResString(R.string.common_word_choose)
                ));
            }
        } else {
            bB.a(context, Helper.getResString(R.string.common_message_check_network), bB.TOAST_NORMAL).show();
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
}
