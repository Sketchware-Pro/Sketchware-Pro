package com.besome.sketch.design;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.WindowInsetsCompat;

import com.sketchware.remod.R;

import a.a.a.mB;
import a.a.a.wB;
import dev.chrisbanes.insetter.Insetter;
import mod.hey.studios.util.Helper;

public class DesignDrawer extends LinearLayout implements View.OnClickListener {
    public DesignDrawer(Context context) {
        super(context);
        initialize(context);
    }

    public DesignDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private DrawerItem addDrawerItem(int tag, boolean useSeparator, int iconResId, int titleResId, int descriptionResId) {
        DrawerItem drawerItem = new DrawerItem(getContext());
        drawerItem.setContent(iconResId, Helper.getResString(drawerItem, titleResId), Helper.getResString(drawerItem, descriptionResId));
        drawerItem.setTag(tag);
        drawerItem.setOnClickListener(this);
        drawerItem.setSeparatorVisibility(useSeparator);
        drawerItem.setSubSeparatorVisibility(!useSeparator);
        return drawerItem;
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.design_drawer);
        TextView tv_title_configuration = findViewById(R.id.tv_title_configuration);
        tv_title_configuration.setText(Helper.getResString(tv_title_configuration, R.string.design_drawer_menu_title));
        Insetter.builder()
                .margin(WindowInsetsCompat.Type.statusBars())
                .applyToView(tv_title_configuration);
        TextView global = findViewById(R.id.tv_title_global);
        global.setText(Helper.getResString(global, R.string.design_drawer_menu_bottom_title));
        LinearLayout menusLayout = findViewById(R.id.layout_menus);
        LinearLayout bottomMenusLayout = findViewById(R.id.layout_bottom_menus);
        Insetter.builder()
                .margin(WindowInsetsCompat.Type.navigationBars())
                .applyToView(bottomMenusLayout);
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
        menusLayout.addView(addDrawerItem(22, false,
                R.drawable.icons8_app_components, R.string.design_drawer_menu_title_logcat_reader, R.string.design_drawer_menu_subtitle_logcat_reader));
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            if (getContext() instanceof DesignActivity activity) {
                switch ((Integer) v.getTag()) {
                    case 1:
                        activity.toCollectionManager();
                        return;

                    case 3:
                        activity.toLibraryManager();
                        return;

                    case 4:
                        activity.toViewManager();
                        return;

                    case 5:
                        activity.toImageManager();
                        return;

                    case 6:
                        activity.toSoundManager();
                        return;

                    case 7:
                        activity.toFontManager();
                        return;

                    case 8:
                        activity.toJavaManager();
                        return;

                    case 9:
                        activity.toResourceManager();
                        return;

                    case 10:
                        activity.toAssetManager();
                        return;

                    case 11:
                        activity.toPermissionManager();
                        return;

                    case 12:
                        activity.toAppCompatInjectionManager();
                        return;

                    case 13:
                        activity.toAndroidManifestManager();
                        return;

                    case 14:
                        activity.toLocalLibraryManager();
                        return;

                    case 16:
                        activity.toSourceCodeViewer();
                        return;

                    case 17:
                        activity.toProguardManager();
                        return;

                    case 18:
                        activity.toStringFogManager();
                        return;

                    case 19:
                        activity.toNativeLibraryManager();
                        return;

                    case 20:
                        activity.toCustomBlocksViewer();
                        return;

                    case 22:
                        activity.toLogReader();
                        return;
                    case 2:
                    default:
                }
            }
        }
    }

    private static class DrawerItem extends LinearLayout {
        private final ImageView imgIcon;
        private final TextView titleTextView;
        private final TextView subTitleTextView;
        private final View subSeparator;
        private final View separator;

        public DrawerItem(Context context) {
            this(context, null);
        }

        public DrawerItem(Context context, AttributeSet set) {
            super(context, set);
            wB.a(context, this, R.layout.design_drawer_item);
            imgIcon = findViewById(R.id.img_icon);
            titleTextView = findViewById(R.id.tv_root_title);
            subTitleTextView = findViewById(R.id.tv_sub_title);
            subSeparator = findViewById(R.id.sub_separator);
            separator = findViewById(R.id.separator);
        }

        public void setContent(int iconResId, String rootTitleText, String subTitleText) {
            imgIcon.setImageResource(iconResId);
            titleTextView.setText(rootTitleText);
            subTitleTextView.setText(subTitleText);
        }

        public void setSeparatorVisibility(boolean visible) {
            separator.setVisibility(visible ? VISIBLE : GONE);
        }

        public void setSubSeparatorVisibility(boolean visible) {
            subSeparator.setVisibility(visible ? VISIBLE : GONE);
        }
    }
}