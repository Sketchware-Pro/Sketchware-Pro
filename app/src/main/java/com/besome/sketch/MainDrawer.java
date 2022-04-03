package com.besome.sketch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.help.ProgramInfoActivity;
import com.besome.sketch.help.SystemSettingActivity;
import com.besome.sketch.tools.NewKeyStoreActivity;
import com.sketchware.remod.Resources;

import a.a.a.EA;
import a.a.a.GB;
import a.a.a.Zo;
import a.a.a.bB;
import a.a.a.ci;
import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;
import mod.hilal.saif.activities.tools.Tools;
import mod.ilyasse.activities.about.AboutModActivity;

public class MainDrawer extends LinearLayout {

    public Context a;
    public a b;
    public ImageView c;
    public ImageView d;
    public ImageView e;
    public EA f;
    public Zo g;

    public MainDrawer(Context context) {
        super(context);
        a(context);
    }

    public MainDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    /**
     * Handle onClick of Slack icon
     */
    public final void g() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.a);
        builder.setItems(new String[]{
                        xB.b().a(getContext(), Resources.string.main_drawer_context_menu_title_slack_invitation),
                        xB.b().a(getContext(), Resources.string.main_drawer_context_menu_title_slack_open)},
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            f();
                        } else {
                            e();
                        }
                    }
                });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(true);
        create.show();
    }

    /**
     * Open the change log dialog (originally open Docs?)
     */
    public final void h() {
        changeLogDialog(a);
    }

    public final void a(Context context) {
        a = context;
        f = new EA(context);
        g = new Zo(context);
        wB.a(context, this, 2131427502);
        c = findViewById(Resources.id.social_fb);
        d = findViewById(Resources.id.social_medium);
        e = findViewById(Resources.id.social_slack);
        RecyclerView recyclerView = findViewById(Resources.id.menu_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new ci());
        b = new a();
        recyclerView.setAdapter(b);
        d();
        c();
    }

    /**
     * Handle onClick of Facebook icon
     */
    public final void b() {
        String facebookUrl = a.getString(Resources.string.facebook_url);
        try {
            a.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=" + facebookUrl)));
        } catch (Exception unused) {
            a.startActivity(Intent.createChooser(
                    new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)),
                    xB.b().a(getContext(), Resources.string.common_word_choose)));
        }
    }

    public final void c() {
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    b();
                }
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    a();
                }
            }
        });
        e.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    g();
                }
            }
        });
    }

    /**
     * Initialize (main) drawer items, such as Changelog
     */
    public final void d() {
        DrawerItem aboutModdersItem = DrawerItem.eMenu_orders;
        aboutModdersItem.i = Resources.drawable.side_menu_info_icon_over_white;
        aboutModdersItem.h = "About Modders";

        DrawerItem docsItem = DrawerItem.eMenu_docs;
        docsItem.i = Resources.drawable.icon_file_white_96;
        docsItem.h = "Changelog";

        DrawerItem changelogItem = DrawerItem.eMenu_system_settings;
        changelogItem.i = Resources.drawable.side_menu_setting_icon_over_white;
        changelogItem.h = xB.b().a(getContext(), Resources.string.main_drawer_title_system_settings);

        DrawerItem programInfoItem = DrawerItem.eMenu_program_info;
        programInfoItem.i = Resources.drawable.side_menu_info_icon_over_white;
        programInfoItem.h = xB.b().a(getContext(), Resources.string.main_drawer_title_program_information);

        DrawerItem exportUrlsItem = DrawerItem.eMenu_export_urls;
        exportUrlsItem.i = Resources.drawable.ic_export_his_white_48dp;
        exportUrlsItem.h = "Developer Tools";

        DrawerItem createKeystoreItem = DrawerItem.eMenu_create_keystore;
        createKeystoreItem.i = Resources.drawable.new_96;
        createKeystoreItem.h = "Create Release Keystore";
    }

    public final void e() {
        if (GB.h(a)) {
            try {
                a.startActivity(a.getPackageManager().getLaunchIntentForPackage("com.Slack"));
            } catch (Exception e) {
                f();
            }
        } else {
            bB.a(a, xB.b().a(getContext(), Resources.string.common_message_check_network), Toast.LENGTH_SHORT).show();
        }
    }

    public final void f() {
        if (GB.h(a)) {
            try {
                Intent chooser = new Intent(Intent.ACTION_VIEW, Uri.parse(xB.b().a(getContext(), Resources.string.slack_url_primary)));
                chooser.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                a.startActivity(Intent.createChooser(
                        chooser,
                        xB.b().a(getContext(), Resources.string.common_word_choose)
                ));
            } catch (Exception e) {
                Intent chooser = new Intent(Intent.ACTION_VIEW, Uri.parse(a.getString(Resources.string.slack_url_secondary)));
                chooser.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                a.startActivity(Intent.createChooser(
                        chooser,
                        xB.b().a(getContext(), Resources.string.common_word_choose)
                ));
            }
        } else {
            bB.a(a, xB.b().a(getContext(), Resources.string.common_message_check_network), Toast.LENGTH_SHORT).show();
        }
    }

    public final void a() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(a.getString(Resources.string.besome_blog_url)));
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            a.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            a.startActivity(Intent.createChooser(
                    new Intent(Intent.ACTION_VIEW, Uri.parse(a.getString(Resources.string.besome_blog_url))),
                    xB.b().a(getContext(), 2131624976)
            ));
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
        eMenu_orders,
        eMenu_docs,
        eMenu_system_settings,
        eMenu_program_info,
        //eMenu_language_settings,
        eMenu_export_urls,
        eMenu_create_keystore;

        /**
         * The label of the item
         */
        public String h;
        /**
         * The resource ID for its icon
         */
        public int i;

        public int d() {
            return i;
        }

        public String e() {
            return h;
        }
    }

    class a extends RecyclerView.a<RecyclerView.v> {
        /**
         * Current Drawer item's ID ({@link Enum#ordinal()})
         */
        public int c = -1;

        public a() {
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
                        Resources.layout.main_drawer_header,
                        viewGroup,
                        false
                ));
            }
            return new MenuItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(
                    Resources.layout.main_drawer_item,
                    viewGroup,
                    false
            ));
        }

        public void b(RecyclerView.v viewHolder, int i) {
            if (!(viewHolder instanceof EmptyViewHolder)) {
                if (viewHolder instanceof MenuItemHolder) {
                    MenuItemHolder menuItemHolder = (MenuItemHolder) viewHolder;
                    menuItemHolder.t.setImageResource(DrawerItem.values()[i > 0 ? i - 1 : i].d());
                    DrawerItem[] values = DrawerItem.values();
                    if (i > 0) i--;
                    menuItemHolder.u.setText(values[i].e());
                }
            }
        }

        class EmptyViewHolder extends RecyclerView.v {
            public EmptyViewHolder(View view) {
                super(view);
            }
        }

        class MenuItemHolder extends RecyclerView.v {

            public ImageView t;
            public TextView u;

            public MenuItemHolder(View view) {
                super(view);
                u = view.findViewById(Resources.id.tv_menu_name);
                t = view.findViewById(Resources.id.img_icon);
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!mB.a()) {
                            MainDrawer.a.this.c = j() - 1;
                            int id = MainDrawer.a.this.c;
                            MainDrawer.a.this.c(id);
                            Activity activity = (Activity) MainDrawer.this.getContext();
                            if (id == DrawerItem.eMenu_orders.ordinal()) {
                                Intent intent = new Intent(activity, AboutModActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                activity.startActivity(intent);
                            } else if (id == DrawerItem.eMenu_docs.ordinal()) {
                                MainDrawer.this.h();
                            } else if (id == DrawerItem.eMenu_system_settings.ordinal()) {
                                Intent intent = new Intent(activity, SystemSettingActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                activity.startActivityForResult(intent, 107);
                            } else if (id == DrawerItem.eMenu_program_info.ordinal()) {
                                Intent intent = new Intent(activity, ProgramInfoActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                activity.startActivityForResult(intent, 105);
                            } else if (id == DrawerItem.eMenu_export_urls.ordinal()) {
                                Intent intent = new Intent(activity, Tools.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                activity.startActivity(intent);
                            } else if (id == DrawerItem.eMenu_create_keystore.ordinal()) {
                                Intent intent = new Intent(activity, NewKeyStoreActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                activity.startActivity(intent);
                            }
                        }
                    }
                });
            }
        }
    }
}
