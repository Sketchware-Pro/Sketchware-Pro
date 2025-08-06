package com.besome.sketch.design;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.DesignDrawerItemBinding;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.ThemeUtils;
import pro.sketchware.utility.UI;

public class DesignDrawer extends LinearLayout {
    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener drawerItemClickListener = v -> {
        Activity activity = (Activity) getContext();
        if (!(activity instanceof DesignActivity)) return;
        int id = v.getId();

        switch (id) {
            case R.id.item_library_manager -> ((DesignActivity) activity).toLibraryManager();
            case R.id.item_view_manager -> ((DesignActivity) activity).toViewManager();
            case R.id.item_image_manager -> ((DesignActivity) activity).toImageManager();
            case R.id.item_sound_manager -> ((DesignActivity) activity).toSoundManager();
            case R.id.item_font_manager -> ((DesignActivity) activity).toFontManager();
            case R.id.item_java_manager -> ((DesignActivity) activity).toJavaManager();
            case R.id.item_resource_manager -> ((DesignActivity) activity).toResourceManager();
            case R.id.item_resource_editor -> ((DesignActivity) activity).toResourceEditor();
            case R.id.item_assets_manager -> ((DesignActivity) activity).toAssetManager();
            case R.id.item_permission_manager -> ((DesignActivity) activity).toPermissionManager();
            case R.id.item_appcompat_manager ->
                    ((DesignActivity) activity).toAppCompatInjectionManager();
            case R.id.item_manifest_manager ->
                    ((DesignActivity) activity).toAndroidManifestManager();
            case R.id.item_used_custom_blocks -> ((DesignActivity) activity).toCustomBlocksViewer();
            case R.id.item_code_shrinking_manager ->
                    ((DesignActivity) activity).toProguardManager();
            case R.id.item_stringfog_manager -> ((DesignActivity) activity).toStringFogManager();
            case R.id.item_show_src -> ((DesignActivity) activity).toSourceCodeViewer();
            case R.id.item_xml_command_manager -> ((DesignActivity) activity).toXMLCommandManager();
            case R.id.item_logcat_reader -> ((DesignActivity) activity).toLogReader();
            case R.id.item_collection_manager -> ((DesignActivity) activity).toCollectionManager();
            default -> throw new IllegalArgumentException("Invalid item id: " + id);
        }
    };

    public DesignDrawer(Context context) {
        this(context, null);
    }

    @SuppressLint("NonConstantResourceId")
    public DesignDrawer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setFocusable(true);
        setClickable(true);

        ShapeAppearanceModel shape = ShapeAppearanceModel.builder()
                .setTopLeftCornerSize(SketchwareUtil.getDip(24))
                .setBottomLeftCornerSize(SketchwareUtil.getDip(24))
                .build();

        MaterialShapeDrawable background = new MaterialShapeDrawable(shape);
        background.setFillColor(ColorStateList.valueOf(ThemeUtils.getColor(context, R.attr.colorSurfaceContainerLow)));
        background.initializeElevationOverlay(context);
        setBackground(background);
        setElevation(3f);
        setPadding(0, 0, 0, SketchwareUtil.dpToPx(4));

        ScrollView scrollView = new ScrollView(context);
        scrollView.setFillViewport(true);
        scrollView.setClipToPadding(false);
        addView(scrollView, new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));

        LinearLayout content = new LinearLayout(getContext());
        content.setOrientation(VERTICAL);
        scrollView.addView(content);

        UI.addSystemWindowInsetToPadding(scrollView, false, true, false, false);
        UI.addSystemWindowInsetToPadding(this, false, false, true, true);

        addDrawerSubheaderItem(R.string.design_drawer_menu_title, content);
        addDrawerItem(R.id.item_library_manager, R.drawable.ic_mtrl_category, R.string.design_drawer_menu_title_library, R.string.design_drawer_menu_description_library, content);
        addDrawerItem(R.id.item_view_manager, R.drawable.ic_mtrl_devices, R.string.design_drawer_menu_title_view, R.string.design_drawer_menu_description_view, content);
        addDrawerItem(R.id.item_image_manager, R.drawable.ic_mtrl_image, R.string.design_drawer_menu_title_image, R.string.design_drawer_menu_description_image, content);
        addDrawerItem(R.id.item_sound_manager, R.drawable.ic_mtrl_music, R.string.design_drawer_menu_title_sound, R.string.design_drawer_menu_description_sound, content);
        addDrawerItem(R.id.item_font_manager, R.drawable.ic_mtrl_font, R.string.design_drawer_menu_title_font, R.string.design_drawer_menu_description_font, content);
        addDrawerItem(R.id.item_java_manager, R.drawable.ic_mtrl_java, R.string.text_title_menu_java, R.string.text_subtitle_menu_java, content);
        addDrawerItem(R.id.item_resource_manager, R.drawable.ic_mtrl_folder, R.string.text_title_menu_resource, R.string.text_subtitle_menu_resource, content);
        addDrawerItem(R.id.item_resource_editor, R.drawable.ic_mtrl_folder_code, R.string.text_title_menu_resource_editor, R.string.text_subtitle_menu_resource_editor, content);
        addDrawerItem(R.id.item_assets_manager, R.drawable.ic_mtrl_file_present, R.string.text_title_menu_assets, R.string.text_subtitle_menu_assets, content);
        addDrawerItem(R.id.item_permission_manager, R.drawable.ic_mtrl_shield_check, R.string.text_title_menu_permission, R.string.text_subtitle_menu_permission, content);
        addDrawerItem(R.id.item_appcompat_manager, R.drawable.ic_mtrl_inject, R.string.design_drawer_menu_injection, R.string.design_drawer_menu_injection_subtitle, content);
        addDrawerItem(R.id.item_manifest_manager, R.drawable.ic_mtrl_deployed_code, R.string.design_drawer_menu_androidmanifest, R.string.design_drawer_menu_androidmanifest_subtitle, content);
        addDrawerItem(R.id.item_used_custom_blocks, R.drawable.ic_mtrl_block, R.string.design_drawer_menu_customblocks, R.string.design_drawer_menu_customblocks_subtitle, content);
        addDrawerItem(R.id.item_code_shrinking_manager, R.drawable.ic_mtrl_shield_lock, R.string.design_drawer_menu_proguard, R.string.design_drawer_menu_proguard_subtitle, content);
        addDrawerItem(R.id.item_stringfog_manager, R.drawable.ic_mtrl_regular_expression, R.string.design_drawer_menu_stringfog, R.string.design_drawer_menu_stringfog_subtitle, content);
        addDrawerItem(R.id.item_show_src, R.drawable.ic_mtrl_frame_source, R.string.design_drawer_menu_title_source_code, R.string.design_drawer_menu_description_source_code, content);
        addDrawerItem(R.id.item_xml_command_manager, R.drawable.ic_mtrl_code, R.string.design_drawer_menu_title_xml_command, R.string.design_drawer_menu_description_xml_command, content);
        addDrawerItem(R.id.item_logcat_reader, R.drawable.ic_mtrl_article, R.string.design_drawer_menu_title_logcat_reader, R.string.design_drawer_menu_subtitle_logcat_reader, content);

        // if you want to show text "Global", uncomment next line
        // addDrawerSubheaderItem(R.string.design_drawer_menu_bottom_title, this);
        addDrawerDivider(this);
        addDrawerItem(R.id.item_collection_manager, R.drawable.ic_mtrl_bookmark, R.string.design_drawer_menu_title_collection, R.string.design_drawer_menu_description_collection, this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    @Override
    public void setElevation(float elevation) {
        super.setElevation(elevation);
        MaterialShapeUtils.setElevation(this, elevation);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int maxWidth = SketchwareUtil.dpToPx(300);
        switch (MeasureSpec.getMode(widthSpec)) {
            case MeasureSpec.EXACTLY:
                // nothing
                break;
            case MeasureSpec.AT_MOST:
                widthSpec = MeasureSpec.makeMeasureSpec(Math.min(MeasureSpec.getSize(widthSpec), maxWidth), MeasureSpec.EXACTLY);
                break;
            case MeasureSpec.UNSPECIFIED:
                widthSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.EXACTLY);
                break;
        }
        super.onMeasure(widthSpec, heightSpec);
    }

    private void addDrawerItem(int id, int iconResId, int titleResId, int descriptionResId, ViewGroup view) {
        DrawerItem drawerItem = new DrawerItem(getContext());
        drawerItem.setContent(iconResId, Helper.getResString(drawerItem, titleResId), Helper.getResString(drawerItem, descriptionResId));
        drawerItem.setOnClickListener(id, drawerItemClickListener);
        view.addView(drawerItem);
    }

    private void addDrawerSubheaderItem(@StringRes int subheaderResId, ViewGroup view) {
        TextView subheader = new TextView(getContext());
        subheader.setEllipsize(TextUtils.TruncateAt.END);
        subheader.setGravity(Gravity.CENTER_VERTICAL);
        subheader.setText(subheaderResId);

        LayoutParams textLp = new LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLp.topMargin = SketchwareUtil.dpToPx(8);
        textLp.bottomMargin = SketchwareUtil.dpToPx(8);
        textLp.setMarginStart(SketchwareUtil.dpToPx(20));

        subheader.setLayoutParams(textLp);
        view.addView(subheader);
    }

    private void addDrawerDivider(ViewGroup view) {
        MaterialDivider divider = new MaterialDivider(getContext());
        divider.setDividerInsetEnd(SketchwareUtil.dpToPx(20));
        divider.setDividerInsetStart(SketchwareUtil.dpToPx(20));

        LayoutParams dividerLp = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dividerLp.topMargin = SketchwareUtil.dpToPx(8);
        dividerLp.bottomMargin = SketchwareUtil.dpToPx(8);

        divider.setLayoutParams(dividerLp);
        view.addView(divider);
    }

    private static class DrawerItem extends LinearLayout {
        private final DesignDrawerItemBinding binding;

        public DrawerItem(Context context) {
            this(context, null);
        }

        public DrawerItem(Context context, AttributeSet attrs) {
            super(context, attrs);
            LayoutInflater inflater = LayoutInflater.from(context);
            binding = DesignDrawerItemBinding.inflate(inflater, this, true);
        }

        public void setContent(int iconResId, String rootTitleText, String subTitleText) {
            binding.imgIcon.setImageResource(iconResId);
            binding.tvRootTitle.setText(rootTitleText);
            binding.tvSubTitle.setText(subTitleText);
        }

        public void setOnClickListener(int id, OnClickListener listener) {
            binding.getRoot().setId(id);
            binding.getRoot().setOnClickListener(listener);
        }
    }
}