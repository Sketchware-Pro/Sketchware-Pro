package com.besome.sketch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.help.ProgramInfoActivity;
import com.besome.sketch.help.SystemSettingActivity;
import com.besome.sketch.tools.NewKeyStoreActivity;
import com.sketchware.remod.R;

import a.a.a.GB;
import a.a.a.bB;
import a.a.a.ci;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;
import mod.hilal.saif.activities.tools.Tools;
import mod.ilyasse.activities.about.AboutModActivity;

public class MainDrawer extends LinearLayout implements View.OnClickListener {

    public Context mContext;
    public DrawerItemAdapter drawerItemAdapter;
    public ImageView social_fb;
    public ImageView social_medium;
    public ImageView social_slack;

    public MainDrawer(Context context) {
        super(context);
        initialize(context);
    }

    public MainDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private void initialize(Context context) {
        mContext = context;
        wB.a(context, this, R.layout.main_drawer);
        social_fb = findViewById(R.id.social_fb);
        social_medium = findViewById(R.id.social_medium);
        social_slack = findViewById(R.id.social_slack);
        RecyclerView recyclerView = findViewById(R.id.menu_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new ci());
        drawerItemAdapter = new DrawerItemAdapter();
        recyclerView.setAdapter(drawerItemAdapter);
        initializeDrawerItems();
        social_fb.setOnClickListener(this);
        social_medium.setOnClickListener(this);
        social_slack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!mB.a()) {
            int id = view.getId();
            if (id == R.id.social_slack) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
                builder.setItems(new String[]{
                                xB.b().a(getContext(), R.string.main_drawer_context_menu_title_slack_invitation),
                                xB.b().a(getContext(), R.string.main_drawer_context_menu_title_slack_open)},
                        (dialog, which) -> {
                            if (which == 0) {
                                f();
                            } else {
                                if (GB.h(mContext)) {
                                    try {
                                        mContext.startActivity(mContext.getPackageManager().getLaunchIntentForPackage("com.Slack"));
                                    } catch (Exception e1) {
                                        f();
                                    }
                                } else {
                                    bB.a(mContext, xB.b().a(getContext(), R.string.common_message_check_network), bB.TOAST_NORMAL).show();
                                }
                            }
                        });
                AlertDialog create = builder.create();
                create.setCanceledOnTouchOutside(true);
                create.show();
            } else if (id == R.id.social_medium) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mContext.getString(R.string.besome_blog_url)));
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    mContext.startActivity(intent);
                } catch (ActivityNotFoundException unused1) {
                    mContext.startActivity(Intent.createChooser(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(mContext.getString(R.string.besome_blog_url))),
                            xB.b().a(getContext(), R.string.common_word_choose)
                    ));
                }
            } else if (id == R.id.social_fb) {
                String facebookUrl = mContext.getString(R.string.facebook_url);
                try {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + facebookUrl)));
                } catch (Exception unused) {
                    mContext.startActivity(Intent.createChooser(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)),
                            xB.b().a(getContext(), R.string.common_word_choose)));
                }
            }
        }
    }

    /**
     * Initialize (main) drawer items, such as Changelog
     */
    private void initializeDrawerItems() {
        DrawerItem menuAboutModders = DrawerItem.MENU_ABOUT_MODDERS;
        menuAboutModders.icon = R.drawable.side_menu_info_icon_over_white;
        menuAboutModders.title = "About Modders";

        DrawerItem menuChangelog = DrawerItem.MENU_CHANGELOG;
        menuChangelog.icon = R.drawable.icon_file_white_96;
        menuChangelog.title = "Changelog";

        DrawerItem menuSystemSettings = DrawerItem.MENU_SYSTEM_SETTINGS;
        menuSystemSettings.icon = R.drawable.side_menu_setting_icon_over_white;
        menuSystemSettings.title = xB.b().a(getContext(), R.string.main_drawer_title_system_settings);

        DrawerItem menuProgramInfo = DrawerItem.MENU_PROGRAM_INFO;
        menuProgramInfo.icon = R.drawable.side_menu_info_icon_over_white;
        menuProgramInfo.title = xB.b().a(getContext(), R.string.main_drawer_title_program_information);

        DrawerItem menuDeveloperTools = DrawerItem.MENU_DEVELOPER_TOOLS;
        menuDeveloperTools.icon = R.drawable.ic_export_his_white_48dp;
        menuDeveloperTools.title = "Developer Tools";

        DrawerItem menuCreateKeystore = DrawerItem.MENU_CREATE_KEYSTORE;
        menuCreateKeystore.icon = R.drawable.new_96;
        menuCreateKeystore.title = "Create Release Keystore";
    }

    private void f() {
        if (GB.h(mContext)) {
            try {
                Intent chooser = new Intent(Intent.ACTION_VIEW, Uri.parse(xB.b().a(getContext(), R.string.slack_url_primary)));
                chooser.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(Intent.createChooser(
                        chooser,
                        xB.b().a(getContext(), R.string.common_word_choose)
                ));
            } catch (Exception e) {
                Intent chooser = new Intent(Intent.ACTION_VIEW, Uri.parse(mContext.getString(R.string.slack_url_secondary)));
                chooser.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                mContext.startActivity(Intent.createChooser(
                        chooser,
                        xB.b().a(getContext(), R.string.common_word_choose)
                ));
            }
        } else {
            bB.a(mContext, xB.b().a(getContext(), R.string.common_message_check_network), bB.TOAST_NORMAL).show();
        }
    }

    private void changeLogDialog(Context context) {
        Intent aboutModIntent = new Intent();
        aboutModIntent.setClass(context, AboutModActivity.class);
        aboutModIntent.putExtra("select", "changelog");
        context.startActivity(aboutModIntent);
    }

    /**
     * A class representing an item inside the Drawer
     */
    enum DrawerItem {
        MENU_ABOUT_MODDERS,
        MENU_CHANGELOG,
        MENU_SYSTEM_SETTINGS,
        MENU_PROGRAM_INFO,
        MENU_DEVELOPER_TOOLS,
        MENU_CREATE_KEYSTORE;

        /**
         * The label of the item
         */
        public String title;
        /**
         * The resource ID for its icon
         */
        public int icon;

        public int getIcon() {
            return icon;
        }

        public String getTitle() {
            return title;
        }
    }

    class DrawerItemAdapter extends RecyclerView.a<RecyclerView.v> {
        /**
         * Current Drawer item's ID ({@link Enum#ordinal()})
         */
        public int drawerItem = -1;

        public DrawerItemAdapter() {
        }

        public int a() {
            return DrawerItem.values().length + 1;
        }

        public int b(int i) {
            return i == 0 ? 0 : 1;
        }

        public RecyclerView.v b(ViewGroup viewGroup, int i) {
            if (i == 0) {
                return new EmptyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(
                        R.layout.main_drawer_header,
                        viewGroup,
                        false
                ));
            }
            return new MenuItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(
                    R.layout.main_drawer_item,
                    viewGroup,
                    false
            ));
        }

        public void b(RecyclerView.v viewHolder, int i) {
            if (!(viewHolder instanceof EmptyViewHolder)) {
                if (viewHolder instanceof MenuItemHolder) {
                    MenuItemHolder menuItemHolder = (MenuItemHolder) viewHolder;
                    menuItemHolder.t.setImageResource(DrawerItem.values()[i > 0 ? i - 1 : i].getIcon());
                    DrawerItem[] values = DrawerItem.values();
                    if (i > 0) i--;
                    menuItemHolder.u.setText(values[i].getTitle());
                }
            }
        }

        class EmptyViewHolder extends RecyclerView.v {
            public EmptyViewHolder(View view) {
                super(view);
            }
        }

        class MenuItemHolder extends RecyclerView.v implements View.OnClickListener {

            public ImageView t;
            public TextView u;

            public MenuItemHolder(View view) {
                super(view);
                u = view.findViewById(R.id.tv_menu_name);
                t = view.findViewById(R.id.img_icon);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    drawerItem = j() - 1;
                    int id = drawerItem;
                    DrawerItemAdapter.this.c(id);
                    Activity activity = (Activity) MainDrawer.this.getContext();
                    if (id == DrawerItem.MENU_ABOUT_MODDERS.ordinal()) {
                        Intent intent = new Intent(activity, AboutModActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        activity.startActivity(intent);
                    } else if (id == DrawerItem.MENU_CHANGELOG.ordinal()) {
                        changeLogDialog(mContext);
                    } else if (id == DrawerItem.MENU_SYSTEM_SETTINGS.ordinal()) {
                        Intent intent = new Intent(activity, SystemSettingActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        activity.startActivityForResult(intent, 107);
                    } else if (id == DrawerItem.MENU_PROGRAM_INFO.ordinal()) {
                        Intent intent = new Intent(activity, ProgramInfoActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        activity.startActivityForResult(intent, 105);
                    } else if (id == DrawerItem.MENU_DEVELOPER_TOOLS.ordinal()) {
                        Intent intent = new Intent(activity, Tools.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        activity.startActivity(intent);
                    } else if (id == DrawerItem.MENU_CREATE_KEYSTORE.ordinal()) {
                        Intent intent = new Intent(activity, NewKeyStoreActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        activity.startActivity(intent);
                    }
                }
            }
        }
    }
}
