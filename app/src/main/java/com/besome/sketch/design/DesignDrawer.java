package com.besome.sketch.design;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

import a.a.a.mB;
import a.a.a.wB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

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
        drawerItem.setContent(iconResId, Helper.getResString(titleResId), Helper.getResString(descriptionResId));
        drawerItem.setTag(tag);
        drawerItem.setOnClickListener(this);
        drawerItem.setSeparatorVisibility(useSeparator);
        drawerItem.setSubSeparatorVisibility(!useSeparator);
        return drawerItem;
    }

    private void initialize(Context context) {
        this.context = context;
        wB.a(context, this, R.layout.design_drawer);
        TextView tv_title_configuration = findViewById(R.id.tv_title_configuration);
        tv_title_configuration.setText(Helper.getResString(R.string.design_drawer_menu_title));
        ((TextView) findViewById(R.id.tv_title_global)).setText(Helper.getResString(R.string.design_drawer_menu_bottom_title));
        LinearLayout menusLayout = findViewById(R.id.layout_menus);
        LinearLayout bottomMenusLayout = findViewById(R.id.layout_bottom_menus);
        /* Add collection item */
        bottomMenusLayout.addView(addDrawerItem(1, false,
                R.drawable.ic_bookmark_red_48dp, R.string.design_drawer_menu_title_collection, R.string.design_drawer_menu_description_collection
        ));
        /* Add built-in Library Manager (AppCompat, Firebase, AdMob, Google Maps SDK) */
        /* INCLUDES SECTION SEPARATOR */
        menusLayout.addView(addDrawerItem(3, true,
                R.drawable.categorize_48, R.string.design_drawer_menu_title_library, R.string.design_drawer_menu_description_library
        ));
        /* Add View Manager */
        menusLayout.addView(addDrawerItem(4, false,
                R.drawable.multiple_devices_48, R.string.design_drawer_menu_title_view, R.string.design_drawer_menu_description_view
        ));
        /* Add Image Manager */
        menusLayout.addView(addDrawerItem(5, false,
                R.drawable.ic_picture_48dp, R.string.design_drawer_menu_title_image, R.string.design_drawer_menu_description_image
        ));
        /* Add Sound Manager */
        menusLayout.addView(addDrawerItem(6, false,
                R.drawable.ic_sound_wave_48dp, R.string.design_drawer_menu_title_sound, R.string.design_drawer_menu_description_sound
        ));
        /* Add Font Manager */
        menusLayout.addView(addDrawerItem(7, false,
                R.drawable.ic_font_48dp, R.string.design_drawer_menu_title_font, R.string.design_drawer_menu_description_font
        ));
        /* Add Java Manager */
        menusLayout.addView(addDrawerItem(8, false,
                R.drawable.java_96, R.string.text_title_menu_java, R.string.text_subtitle_menu_java
        ));
        /* Add Resource Manager */
        menusLayout.addView(addDrawerItem(9, false,
                R.drawable.file_app_icon, R.string.text_title_menu_resource, R.string.text_subtitle_menu_resource
        ));
        /* Add Asset Manager */
        menusLayout.addView(addDrawerItem(10, false,
                R.drawable.file_48_blue, R.string.text_title_menu_assets, R.string.text_subtitle_menu_assets
        ));
        /* Add Permission Manager */
        menusLayout.addView(addDrawerItem(11, false,
                R.drawable.plugin_purple_96, R.string.text_title_menu_permission, R.string.text_subtitle_menu_permission
        ));
        /* Add AppCompat Injection Manager */
        menusLayout.addView(addDrawerItem(12, false,
                R.drawable.ic_property_inject, R.string.design_drawer_menu_injection, R.string.design_drawer_menu_injection_subtitle
        ));
        /* Add AndroidManifest Manager */
        menusLayout.addView(addDrawerItem(13, false,
                R.drawable.icon8_code_am, R.string.design_drawer_menu_androidmanifest, R.string.design_drawer_menu_androidmanifest_subtitle
        ));
        /* Add Used Custom Blocks */
        menusLayout.addView(addDrawerItem(20, false,
                R.drawable.block_96_blue, R.string.design_drawer_menu_customblocks, R.string.design_drawer_menu_customblocks_subtitle
        ));
        /* Add Local library Manager */
        menusLayout.addView(addDrawerItem(14, false,
                R.drawable.open_box_48, R.string.text_title_menu_local_library, R.string.text_subtitle_menu_local_library
        ));
        /* Add Native library Manager */
        menusLayout.addView(addDrawerItem(19, false,
                R.drawable.cpp, R.string.design_drawer_menu_nativelibs, R.string.design_drawer_menu_nativelibs_subtitle));
        /* Add ProGuard Manager */
        menusLayout.addView(addDrawerItem(17, false,
                R.drawable.connected_96, R.string.design_drawer_menu_proguard, R.string.design_drawer_menu_proguard_subtitle));
        /* Add StringFog Manager */
        /* INCLUDES SECTION SEPARATOR */
        menusLayout.addView(addDrawerItem(18, true,
                R.drawable.color_lock_96, R.string.design_drawer_menu_stringfog, R.string.design_drawer_menu_stringfog_subtitle));
        /* Add Source Code Viewer */
        menusLayout.addView(addDrawerItem(16, false,
                R.drawable.code_icon, R.string.design_drawer_menu_title_source_code, R.string.design_drawer_menu_description_source_code));
        /* Add Logcat Reader */
        menusLayout.addView(addDrawerItem(22,false,
                R.drawable.icons8_app_components,R.string.design_drawer_menu_title_logcat_reader,R.string.design_drawer_menu_subtitle_logcat_reader));
    }

    @Override
    public void onClick(View view) {
        if (!mB.a()) {
            if (context instanceof DesignActivity) {
                DesignActivity designActivity = (DesignActivity) context;
                switch ((Integer) view.getTag()) {
                    case 1:
                        designActivity.toCollectionManager();
                        return;

                    case 3:
                        designActivity.toLibraryManager();
                        return;

                    case 4:
                        designActivity.toViewManager();
                        return;

                    case 5:
                        designActivity.toImageManager();
                        return;

                    case 6:
                        designActivity.toSoundManager();
                        return;

                    case 7:
                        designActivity.toFontManager();
                        return;

                    case 8:
                        designActivity.toJavaManager();
                        return;

                    case 9:
                        designActivity.toResourceManager();
                        return;

                    case 10:
                        designActivity.toAssetManager();
                        return;

                    case 11:
                        designActivity.toPermissionManager();
                        return;

                    case 12:
                        designActivity.toAppCompatInjectionManager();
                        return;

                    case 13:
                        designActivity.toAndroidManifestManager();
                        return;

                    case 14:
                        designActivity.toLocalLibraryManager();
                        return;

                    case 16:
                        designActivity.toSourceCodeViewer();
                        return;

                    case 17:
                        designActivity.toProGuardManager();
                        return;

                    case 18:
                        designActivity.toStringFogManager();
                        return;

                    case 19:
                        designActivity.toNativeLibraryManager();
                        return;

                    case 20:
                        designActivity.toCustomBlocksViewer();
                        return;

                    case 22:
                        designActivity.toLogReader();
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
            wB.a(context, this, R.layout.design_drawer_item);
            imgIcon = findViewById(R.id.img_icon);
            titleTextView = findViewById(R.id.tv_root_title);
            subTitleTextView = findViewById(R.id.tv_sub_title);
            subSeparator = findViewById(R.id.sub_separator);
            separator = findViewById(R.id.separator);
        }

        public void setSeparatorVisibility(boolean visible) {
            separator.setVisibility(visible ? VISIBLE : GONE);
        }

        public void setSubSeparatorVisibility(boolean visible) {
            subSeparator.setVisibility(visible ? VISIBLE : GONE);
        }
    }
}