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

    private Context context;

    public DesignDrawer(Context context) {
        super(context);
        initialize(context);
    }

    public DesignDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private DrawerItem addDrawerItem(int tag, boolean useSeparator, int iconResId, int titleResId, int descriptionResId) {
        DrawerItem drawerItem = new DrawerItem(context, tag);
        drawerItem.setContent(iconResId, xB.b().a(context, titleResId), xB.b().a(context, descriptionResId));
        drawerItem.setTag(tag);
        drawerItem.setOnClickListener(this);
        drawerItem.setSeparatorVisibility(useSeparator);
        drawerItem.setSubSeparatorVisibility(!useSeparator);
        return drawerItem;
    }

    private void initialize(Context context) {
        this.context = context;
        wB.a(context, this, Resources.layout.design_drawer);
        TextView tv_title_configuration = findViewById(Resources.id.tv_title_configuration);
        tv_title_configuration.setText(xB.b().a(context, Resources.string.design_drawer_menu_title));
        ((TextView) findViewById(Resources.id.tv_title_global))
                .setText(xB.b().a(context, Resources.string.design_drawer_menu_bottom_title));
        LinearLayout menusLayout = findViewById(Resources.id.layout_menus);
        LinearLayout bottomMenusLayout = findViewById(Resources.id.layout_bottom_menus);
        /* Add collection item */
        bottomMenusLayout.addView(addDrawerItem(
                1,
                false,
                Resources.drawable.ic_bookmark_red_48dp,
                Resources.string.design_drawer_menu_title_collection,
                Resources.string.design_drawer_menu_description_collection
        ));
        /* Add built-in Library Manager (AppCompat, Firebase, AdMob, Google Maps SDK) */
        /* INCLUDES SECTION SEPARATOR */
        menusLayout.addView(addDrawerItem(
                3,
                true,
                Resources.drawable.categorize_48,
                Resources.string.design_drawer_menu_title_library,
                Resources.string.design_drawer_menu_description_library
        ));
        /* Add View Manager */
        menusLayout.addView(addDrawerItem(
                4,
                false,
                Resources.drawable.multiple_devices_48,
                Resources.string.design_drawer_menu_title_view,
                Resources.string.design_drawer_menu_description_view
        ));
        /* Add Image Manager */
        menusLayout.addView(addDrawerItem(
                5,
                false,
                Resources.drawable.ic_picture_48dp,
                Resources.string.design_drawer_menu_title_image,
                Resources.string.design_drawer_menu_description_image
        ));
        /* Add Sound Manager */
        menusLayout.addView(addDrawerItem(
                6,
                false,
                Resources.drawable.ic_sound_wave_48dp,
                Resources.string.design_drawer_menu_title_sound,
                Resources.string.design_drawer_menu_description_sound
        ));
        /* Add Font Manager */
        menusLayout.addView(addDrawerItem(
                7,
                false,
                Resources.drawable.ic_font_48dp,
                Resources.string.design_drawer_menu_title_font,
                Resources.string.design_drawer_menu_description_font
        ));
        /* Add Java Manager */
        menusLayout.addView(addDrawerItem(
                8,
                false,
                Resources.drawable.java_96,
                Resources.string.text_title_menu_java,
                Resources.string.text_subtitle_menu_java
        ));
        /* Add Resource Manager */
        menusLayout.addView(addDrawerItem(
                9,
                false,
                Resources.drawable.file_app_icon,
                Resources.string.text_title_menu_resource,
                Resources.string.text_subtitle_menu_resource
        ));
        /* Add Asset Manager */
        menusLayout.addView(addDrawerItem(
                10,
                false,
                Resources.drawable.file_48_blue,
                Resources.string.text_title_menu_assets,
                Resources.string.text_subtitle_menu_assets
        ));
        /* Add Permission Manager */
        menusLayout.addView(addDrawerItem(
                11,
                false,
                Resources.drawable.plugin_purple_96,
                Resources.string.text_title_menu_permission,
                Resources.string.text_subtitle_menu_permission
        ));
        /* Add AppCompat Injection Manager */
        menusLayout.addView(addDrawerItem(
                12,
                false,
                Resources.drawable.ic_property_inject,
                Resources.string.design_drawer_menu_injection,
                Resources.string.design_drawer_menu_injection_subtitle
        ));
        /* Add AndroidManifest Manager */
        menusLayout.addView(addDrawerItem(
                13,
                false,
                Resources.drawable.icon8_code_am,
                Resources.string.design_drawer_menu_androidmanifest,
                Resources.string.design_drawer_menu_androidmanifest_subtitle
        ));
        /* Add Used Custom Blocks */
        menusLayout.addView(addDrawerItem(
                20,
                false,
                Resources.drawable.block_96_blue,
                Resources.string.design_drawer_menu_customblocks,
                Resources.string.design_drawer_menu_customblocks_subtitle
        ));
        /* Add Local library Manager */
        menusLayout.addView(addDrawerItem(
                14,
                false,
                Resources.drawable.open_box_48,
                Resources.string.text_title_menu_local_library,
                Resources.string.text_subtitle_menu_local_library
        ));
        /* Add Native library Manager */
        menusLayout.addView(addDrawerItem(19,
                false,
                Resources.drawable.cpp,
                Resources.string.design_drawer_menu_nativelibs,
                Resources.string.design_drawer_menu_nativelibs_subtitle));
        /* Add ProGuard Manager */
        menusLayout.addView(addDrawerItem(17,
                false,
                Resources.drawable.connected_96,
                Resources.string.design_drawer_menu_proguard,
                Resources.string.design_drawer_menu_proguard_subtitle));
        /* Add StringFog Manager */
        /* INCLUDES SECTION SEPARATOR */
        menusLayout.addView(addDrawerItem(18,
                true,
                Resources.drawable.color_lock_96,
                Resources.string.design_drawer_menu_stringfog,
                Resources.string.design_drawer_menu_stringfog_subtitle));
        /* Add Source Code Viewer */
        menusLayout.addView(addDrawerItem(16,
                false,
                Resources.drawable.code_icon,
                Resources.string.design_drawer_menu_title_source_code,
                Resources.string.design_drawer_menu_description_source_code));
    }

    @Override
    public void onClick(View view) {
        if (!mB.a()) {
            if (context instanceof DesignActivity) {
                DesignActivity designActivity = (DesignActivity) context;
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
                        designActivity.toNativelibs();
                        return;

                    case 20:
                        designActivity.toCustomBlocks();
                        return;

                    case 2:
                    default:
                }
            }
        }
    }

    static class DrawerItem extends LinearLayout {

        private ImageView imgIcon;
        private TextView titleTextView;
        private TextView subTitleTextView;
        private View subSeparator;
        private View separator;

        public DrawerItem(Context context) {
            super(context);
            new DrawerItem(context, 0);
        }

        public DrawerItem(Context context, AttributeSet set) {
            super(context, set);
            new DrawerItem(context, 0);
        }

        public DrawerItem(Context context, int tag) {
            super(context);
            initialize(context, tag);
        }

        public void setContent(int iconResId, String rootTitleText, String subTitleText) {
            imgIcon.setImageResource(iconResId);
            titleTextView.setText(rootTitleText);
            subTitleTextView.setText(subTitleText);
        }

        public final void initialize(Context context, int tag) {
            wB.a(context, this, Resources.layout.design_drawer_item);
            imgIcon = findViewById(Resources.id.img_icon);
            titleTextView = findViewById(Resources.id.tv_root_title);
            subTitleTextView = findViewById(Resources.id.tv_sub_title);
            subSeparator = findViewById(Resources.id.sub_separator);
            separator = findViewById(Resources.id.separator);
        }

        public void setSeparatorVisibility(boolean visible) {
            separator.setVisibility(visible ? VISIBLE : GONE);
        }

        public void setSubSeparatorVisibility(boolean visible) {
            subSeparator.setVisibility(visible ? VISIBLE : GONE);
        }
    }
}