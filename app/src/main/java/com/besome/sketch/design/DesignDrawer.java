package com.besome.sketch.design;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.Resources;

import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;

public class DesignDrawer extends LinearLayout implements View.OnClickListener {

    public final int a = 1;
    public final int b = 2;
    public final int c = 3;
    public final int d = 4;
    public final int e = 5;
    public final int f = 6;
    public final int g = 7;
    public final int h = 8;
    public final int i = 100;
    public final int j = 101;
    public final int k = 102;
    public final int l = 103;
    public final int m = 104;
    public final int n = 300;
    public final int o = 301;
    public Context p;
    public LinearLayout q;
    public LinearLayout r;

    public DesignDrawer(Context context) {
        super(context);
        a(context);
    }

    public DesignDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public final DrawerItem a(int tag, boolean useSeparator, int iconResId, int titleResId, int descriptionResId) {
        DrawerItem a = new DrawerItem(p, tag);
        a.setContent(iconResId, xB.b().a(p, titleResId), xB.b().a(p, descriptionResId));
        a.setTag(tag);
        a.setOnClickListener(this);
        a.setSeparatorVisibility(useSeparator);
        a.setSubSeparatorVisibility(!useSeparator);
        if (tag == 19) {
            a.setSubSeparatorVisibility(false);
        }
        return a;
    }

    public final DrawerItem a(int tag, boolean useSeparator, int iconResId, String title, String description) {
        DrawerItem a = new DrawerItem(p, tag);
        a.setContent(iconResId, title, description);
        a.setTag(tag);
        a.setOnClickListener(this);
        a.setSeparatorVisibility(useSeparator);
        a.setSubSeparatorVisibility(!useSeparator);
        if (tag == 19) {
            a.setSubSeparatorVisibility(false);
        }
        return a;
    }

    public final void a(Context context) {
        p = context;
        wB.a(context, this, Resources.layout.design_drawer);
        TextView tv_title_configuration = findViewById(Resources.id.tv_title_configuration);
        tv_title_configuration.setText(xB.b().a(context, Resources.string.design_drawer_menu_title));
        ((TextView) findViewById(Resources.id.tv_title_global))
                .setText(xB.b().a(context, Resources.string.design_drawer_menu_bottom_title));
        q = findViewById(Resources.id.layout_menus);
        r = findViewById(Resources.id.layout_bottom_menus);
        /* Add collection item */
        r.addView(a(
                1,
                false,
                Resources.drawable.ic_bookmark_red_48dp,
                Resources.string.design_drawer_menu_title_collection,
                Resources.string.design_drawer_menu_description_collection
        ));
        /* Add built-in Library Manager (AppCompat, Firebase, AdMob, Google Maps SDK) */
        /* INCLUDES SECTION SEPARATOR */
        q.addView(a(
                3,
                true,
                Resources.drawable.categorize_48,
                Resources.string.design_drawer_menu_title_library,
                Resources.string.design_drawer_menu_description_library
        ));
        /* Add View Manager */
        q.addView(a(
                4,
                false,
                Resources.drawable.multiple_devices_48,
                Resources.string.design_drawer_menu_title_view,
                Resources.string.design_drawer_menu_description_view
        ));
        /* Add Image Manager */
        q.addView(a(
                5,
                false,
                Resources.drawable.ic_picture_48dp,
                Resources.string.design_drawer_menu_title_image,
                Resources.string.design_drawer_menu_description_image
        ));
        /* Add Sound Manager */
        q.addView(a(
                6,
                false,
                Resources.drawable.ic_sound_wave_48dp,
                Resources.string.design_drawer_menu_title_sound,
                Resources.string.design_drawer_menu_description_sound
        ));
        /* Add Font Manager */
        q.addView(a(
                7,
                false,
                Resources.drawable.ic_font_48dp,
                Resources.string.design_drawer_menu_title_font,
                Resources.string.design_drawer_menu_description_font
        ));
        /* Add Java Manager */
        q.addView(a(
                8,
                false,
                Resources.drawable.java_96,
                Resources.string.text_title_menu_java,
                Resources.string.text_subtitle_menu_java
        ));
        /* Add Resource Manager */
        q.addView(a(
                9,
                false,
                Resources.drawable.file_app_icon,
                Resources.string.text_title_menu_resource,
                Resources.string.text_subtitle_menu_resource
        ));
        /* Add Asset Manager */
        q.addView(a(
                10,
                false,
                Resources.drawable.file_48_blue,
                Resources.string.text_title_menu_assets,
                Resources.string.text_subtitle_menu_assets
        ));
        /* Add Block Manager */
        q.addView(a(
                22,
                false,
                Resources.drawable.block_96_blue,
                "Block Manager",
                "Manage your own blocks to use in Logic Editor"
        ));
        /* Add Permission Manager */
        q.addView(a(
                11,
                false,
                Resources.drawable.plugin_purple_96,
                Resources.string.text_title_menu_permission,
                Resources.string.text_subtitle_menu_permission
        ));
        /* Add AppCompat Injection Manager */
        q.addView(a(
                12,
                false,
                Resources.drawable.ic_property_inject,
                Resources.string.design_drawer_menu_injection,
                Resources.string.design_drawer_menu_injection_subtitle
        ));
        /* Add AndroidManifest Manager */
        q.addView(a(
                13,
                false,
                Resources.drawable.icon8_code_am,
                Resources.string.design_drawer_menu_androidmanifest,
                Resources.string.design_drawer_menu_androidmanifest_subtitle
        ));
        /* Add Used Custom Blocks */
        q.addView(a(
                21,
                false,
                Resources.drawable.block_96_blue,
                Resources.string.design_drawer_menu_customblocks,
                Resources.string.design_drawer_menu_customblocks_subtitle
        ));
        /* Add Local library Manager */
        q.addView(a(
                14,
                false,
                Resources.drawable.open_box_48,
                Resources.string.text_title_menu_local_library,
                Resources.string.text_subtitle_menu_local_library
        ));
        /* Add Native library Manager */
        q.addView(a(20,
                false,
                Resources.drawable.cpp,
                Resources.string.design_drawer_menu_nativelibs,
                Resources.string.design_drawer_menu_nativelibs_subtitle));
        /* Add ProGuard Manager */
        q.addView(a(17,
                false,
                Resources.drawable.connected_96,
                Resources.string.design_drawer_menu_proguard,
                Resources.string.design_drawer_menu_proguard_subtitle));
        /* Add StringFog Manager */
        /* INCLUDES SECTION SEPARATOR */
        q.addView(a(18,
                true,
                Resources.drawable.color_lock_96,
                Resources.string.design_drawer_menu_stringfog,
                Resources.string.design_drawer_menu_stringfog_subtitle));
        /* Add Source Code Viewer */
        q.addView(a(16,
                false,
                Resources.drawable.code_icon,
                Resources.string.design_drawer_menu_title_source_code,
                Resources.string.design_drawer_menu_description_source_code));
        /* Add Direct Code Editor */
        q.addView(a(19,
                false,
                Resources.drawable.notes_alt2,
                Resources.string.design_drawer_menu_title_editor_code,
                Resources.string.design_drawer_menu_subtitle_editor_code));
    }

    @Override
    public void onClick(View view) {
        if (!mB.a()) {
            if (p instanceof DesignActivity) {
                DesignActivity designActivity = (DesignActivity) p;
                switch ((Integer) view.getTag()) {
                    case 1:
                        designActivity.t();
                        return;

                    case 3:
                        designActivity.w();
                        return;

                    case 4:
                        designActivity.x();
                        return;

                    case 5:
                        designActivity.v();
                        return;

                    case 6:
                        designActivity.y();
                        return;

                    case 7:
                        designActivity.u();
                        return;

                    case 8:
                        designActivity.toJava();
                        return;

                    case 9:
                        designActivity.toResource();
                        return;

                    case 10:
                        designActivity.toAssets();
                        return;

                    case 11:
                        designActivity.toPermission();
                        return;

                    case 12:
                        designActivity.toAppCompatInjection();
                        return;

                    case 13:
                        designActivity.toAndroidManifest();
                        return;

                    case 14:
                        designActivity.toLocalLibrary();
                        return;

                    case 15:
                        designActivity.toBroadcast();
                        return;

                    case 16:
                        designActivity.z();
                        return;

                    case 17:
                        designActivity.toProguard();
                        return;

                    case 18:
                        designActivity.toStringfog();
                        return;

                    case 19:
                        designActivity.zz();
                        return;

                    case 20:
                        designActivity.toNativelibs();
                        return;

                    case 21:
                        designActivity.toCustomBlocks();
                        return;

                    case 22:
                        designActivity.toBlocksManager();
                        return;

                    case 2:
                    default:
                }
            }
        }
    }

    static class DrawerItem extends LinearLayout {

        public int a;
        public ImageView b;
        public TextView c;
        public TextView d;
        public View e;
        public View f;
        public LinearLayout g;

        public DrawerItem(Context context) {
            super(context);
            new DrawerItem(context, 0);
        }

        public DrawerItem(Context context, AttributeSet set) {
            super(context, set);
            new DrawerItem(context, 0);
        }

        public DrawerItem(Context context, int i) {
            super(context);
            initialize(context, i);
        }

        public void setContent(int iconResId, String rootTitleText, String subTitleText) {
            b.setImageResource(iconResId);
            c.setText(rootTitleText);
            d.setText(subTitleText);
        }

        public final void initialize(Context context, int tag) {
            a = tag;
            wB.a(context, this, Resources.layout.design_drawer_item);
            b = findViewById(Resources.id.img_icon);
            c = findViewById(Resources.id.tv_root_title);
            d = findViewById(Resources.id.tv_sub_title);
            e = findViewById(Resources.id.sub_separator);
            f = findViewById(Resources.id.separator);
            g = findViewById(Resources.id.sub_items);
        }

        public void setSeparatorVisibility(boolean visible) {
            f.setVisibility(visible ? VISIBLE : GONE);
        }

        public void setSubSeparatorVisibility(boolean visible) {
            e.setVisibility(visible ? VISIBLE : GONE);
        }
    }
}
