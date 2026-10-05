package com.besome.sketch.design;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import a.a.a.jC;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.project.stringfog.StringfogHandler;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.DesignDrawerItemBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.ThemeUtils;
import pro.sketchware.utility.UI;

/**
 * Right-hand "Configuration" panel of the design screen: header, search, and four sections
 * (Project, Resources, Code, Build &amp; Security) of compact rows with optional status badges.
 */
public class DesignDrawer extends LinearLayout {
    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener drawerItemClickListener = v -> {
        Activity activity = (Activity) getContext();
        if (!(activity instanceof DesignActivity designActivity)) return;
        int id = v.getId();

        if (id == R.id.item_library_manager) {
            designActivity.toLibraryManager();
        } else if (id == R.id.item_view_manager) {
            designActivity.toViewManager();
        } else if (id == R.id.item_image_manager) {
            designActivity.toImageManager();
        } else if (id == R.id.item_sound_manager) {
            designActivity.toSoundManager();
        } else if (id == R.id.item_font_manager) {
            designActivity.toFontManager();
        } else if (id == R.id.item_java_manager) {
            designActivity.toJavaManager();
        } else if (id == R.id.item_resource_manager) {
            designActivity.toResourceManager();
        } else if (id == R.id.item_resource_editor) {
            designActivity.toResourceEditor();
        } else if (id == R.id.item_assets_manager) {
            designActivity.toAssetManager();
        } else if (id == R.id.item_permission_manager) {
            designActivity.toPermissionManager();
        } else if (id == R.id.item_appcompat_manager) {
            designActivity.toAppCompatInjectionManager();
        } else if (id == R.id.item_manifest_manager) {
            designActivity.toAndroidManifestManager();
        } else if (id == R.id.item_used_custom_blocks) {
            designActivity.toCustomBlocksViewer();
        } else if (id == R.id.item_code_shrinking_manager) {
            designActivity.toProguardManager();
        } else if (id == R.id.item_stringfog_manager) {
            designActivity.toStringFogManager();
        } else if (id == R.id.item_show_src) {
            designActivity.toSourceCodeViewer();
        } else if (id == R.id.item_xml_command_manager) {
            designActivity.toXMLCommandManager();
        } else if (id == R.id.item_logcat_reader) {
            designActivity.toLogReader();
        } else if (id == R.id.item_collection_manager) {
            designActivity.toCollectionManager();
        } else {
            throw new IllegalArgumentException("Invalid item id: " + id);
        }
    };

    private final List<Section> sections = new ArrayList<>();
    private final DrawerLayout.DrawerListener drawerListener = new DrawerLayout.SimpleDrawerListener() {
        @Override
        public void onDrawerOpened(View drawerView) {
            refreshBadges();
        }
    };
    @Nullable
    private DrawerLayout attachedDrawerLayout;

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
        background.setFillColor(ColorStateList.valueOf(ThemeUtils.getColor(context, R.attr.colorSurface)));
        background.initializeElevationOverlay(context);
        setBackground(background);
        setElevation(3f);
        setPadding(0, 0, 0, SketchwareUtil.dpToPx(4));

        UI.addSystemWindowInsetToPadding(this, false, true, true, false);

        addView(createHeader(context));
        addView(createSearchField(context));

        ScrollView scrollView = new ScrollView(context);
        scrollView.setFillViewport(true);
        scrollView.setClipToPadding(false);
        addView(scrollView, new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));

        LinearLayout content = new LinearLayout(context);
        content.setOrientation(VERTICAL);
        content.setPadding(SketchwareUtil.dpToPx(12), 0, SketchwareUtil.dpToPx(12), SketchwareUtil.dpToPx(8));
        scrollView.addView(content);
        UI.addSystemWindowInsetToPadding(scrollView, false, false, false, true);

        Section project = addSection(content, "Project");
        addRow(project, R.id.item_library_manager, R.drawable.ic_mtrl_category, R.string.design_drawer_menu_title_library, R.string.design_drawer_menu_description_library);
        addRow(project, R.id.item_view_manager, R.drawable.ic_mtrl_devices, R.string.design_drawer_menu_title_view, R.string.design_drawer_menu_description_view);
        addRow(project, R.id.item_collection_manager, R.drawable.ic_mtrl_bookmark, R.string.design_drawer_menu_title_collection, R.string.design_drawer_menu_description_collection);

        Section resources = addSection(content, "Resources");
        addRow(resources, R.id.item_image_manager, R.drawable.ic_mtrl_image, R.string.design_drawer_menu_title_image, R.string.design_drawer_menu_description_image);
        addRow(resources, R.id.item_sound_manager, R.drawable.ic_mtrl_music, R.string.design_drawer_menu_title_sound, R.string.design_drawer_menu_description_sound);
        addRow(resources, R.id.item_font_manager, R.drawable.ic_mtrl_font, R.string.design_drawer_menu_title_font, R.string.design_drawer_menu_description_font);
        addRow(resources, R.id.item_resource_manager, R.drawable.ic_mtrl_folder, R.string.text_title_menu_resource, R.string.text_subtitle_menu_resource);
        addRow(resources, R.id.item_resource_editor, R.drawable.ic_mtrl_folder_code, R.string.text_title_menu_resource_editor, R.string.text_subtitle_menu_resource_editor);
        addRow(resources, R.id.item_assets_manager, R.drawable.ic_mtrl_file_present, R.string.text_title_menu_assets, R.string.text_subtitle_menu_assets);

        Section code = addSection(content, "Code");
        addRow(code, R.id.item_java_manager, R.drawable.ic_mtrl_java, R.string.text_title_menu_java, R.string.text_subtitle_menu_java);
        addRow(code, R.id.item_show_src, R.drawable.ic_mtrl_frame_source, R.string.design_drawer_menu_title_source_code, R.string.design_drawer_menu_description_source_code);
        addRow(code, R.id.item_xml_command_manager, R.drawable.ic_mtrl_code, R.string.design_drawer_menu_title_xml_command, R.string.design_drawer_menu_description_xml_command);
        addRow(code, R.id.item_used_custom_blocks, R.drawable.ic_mtrl_block, R.string.design_drawer_menu_customblocks, R.string.design_drawer_menu_customblocks_subtitle);
        addRow(code, R.id.item_appcompat_manager, R.drawable.ic_mtrl_inject, R.string.design_drawer_menu_injection, R.string.design_drawer_menu_injection_subtitle);
        addRow(code, R.id.item_manifest_manager, R.drawable.ic_mtrl_deployed_code, R.string.design_drawer_menu_androidmanifest, R.string.design_drawer_menu_androidmanifest_subtitle);

        Section security = addSection(content, "Build & Security");
        addRow(security, R.id.item_permission_manager, R.drawable.ic_mtrl_shield_check, R.string.text_title_menu_permission, R.string.text_subtitle_menu_permission);
        addRow(security, R.id.item_code_shrinking_manager, R.drawable.ic_mtrl_shield_lock, R.string.design_drawer_menu_proguard, R.string.design_drawer_menu_proguard_subtitle);
        addRow(security, R.id.item_stringfog_manager, R.drawable.ic_mtrl_regular_expression, R.string.design_drawer_menu_stringfog, R.string.design_drawer_menu_stringfog_subtitle);
        addRow(security, R.id.item_logcat_reader, R.drawable.ic_mtrl_article, R.string.design_drawer_menu_title_logcat_reader, R.string.design_drawer_menu_subtitle_logcat_reader);

        refreshBadges();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
        if (getParent() instanceof DrawerLayout drawerLayout) {
            attachedDrawerLayout = drawerLayout;
            drawerLayout.addDrawerListener(drawerListener);
        }
        refreshBadges();
    }

    @Override
    protected void onDetachedFromWindow() {
        if (attachedDrawerLayout != null) {
            attachedDrawerLayout.removeDrawerListener(drawerListener);
            attachedDrawerLayout = null;
        }
        super.onDetachedFromWindow();
    }

    @Override
    public void setElevation(float elevation) {
        super.setElevation(elevation);
        MaterialShapeUtils.setElevation(this, elevation);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int maxWidth = SketchwareUtil.dpToPx(320);
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

    private View createHeader(Context context) {
        LinearLayout header = new LinearLayout(context);
        header.setOrientation(HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(SketchwareUtil.dpToPx(20), SketchwareUtil.dpToPx(16), SketchwareUtil.dpToPx(8), SketchwareUtil.dpToPx(8));

        LinearLayout titles = new LinearLayout(context);
        titles.setOrientation(VERTICAL);

        TextView title = new TextView(context);
        title.setText(R.string.design_drawer_menu_title);
        title.setTextSize(20);
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        title.setTextColor(ThemeUtils.getColor(context, R.attr.colorOnSurface));
        titles.addView(title);

        TextView subtitle = new TextView(context);
        subtitle.setText(R.string.design_drawer_project_settings);
        subtitle.setTextSize(13);
        subtitle.setTextColor(ThemeUtils.getColor(context, R.attr.colorOnSurfaceVariant));
        titles.addView(subtitle);

        header.addView(titles, new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));

        ImageButton close = new ImageButton(context);
        close.setImageResource(R.drawable.ic_mtrl_close);
        close.setImageTintList(ColorStateList.valueOf(ThemeUtils.getColor(context, R.attr.colorOnSurfaceVariant)));
        close.setBackgroundResource(R.drawable.bg_borderless_ripple);
        close.setContentDescription(Helper.getResString(R.string.common_word_close));
        close.setOnClickListener(v -> {
            if (getParent() instanceof DrawerLayout drawerLayout) {
                drawerLayout.closeDrawer(GravityCompat.END);
            }
        });
        header.addView(close, new LayoutParams(SketchwareUtil.dpToPx(44), SketchwareUtil.dpToPx(44)));
        return header;
    }

    private View createSearchField(Context context) {
        EditText search = new EditText(context);
        search.setBackgroundResource(R.drawable.palette_search_bg);
        search.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_search_palette, 0, 0, 0);
        search.setCompoundDrawablePadding(SketchwareUtil.dpToPx(8));
        search.setHint(R.string.design_drawer_search_hint);
        search.setSingleLine(true);
        search.setTextSize(14);
        search.setImportantForAutofill(View.IMPORTANT_FOR_AUTOFILL_NO);
        search.setPadding(SketchwareUtil.dpToPx(14), 0, SketchwareUtil.dpToPx(14), 0);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, SketchwareUtil.dpToPx(44));
        lp.setMargins(SketchwareUtil.dpToPx(16), SketchwareUtil.dpToPx(4), SketchwareUtil.dpToPx(16), SketchwareUtil.dpToPx(8));
        search.setLayoutParams(lp);
        return search;
    }

    private Section addSection(ViewGroup parent, String title) {
        Context context = getContext();

        TextView header = new TextView(context);
        header.setText(title.toUpperCase(Locale.ROOT));
        header.setTextSize(12);
        header.setLetterSpacing(0.06f);
        header.setTypeface(null, android.graphics.Typeface.BOLD);
        header.setTextColor(ContextCompat.getColor(context, R.color.event_accent));
        LayoutParams headerLp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        headerLp.setMargins(SketchwareUtil.dpToPx(8), SketchwareUtil.dpToPx(12), 0, SketchwareUtil.dpToPx(6));
        parent.addView(header, headerLp);

        MaterialCardView card = new MaterialCardView(context);
        card.setRadius(SketchwareUtil.getDip(16));
        card.setCardElevation(0f);
        card.setCardBackgroundColor(ThemeUtils.getColor(context, R.attr.colorSurfaceContainerLow));
        card.setStrokeColor(ThemeUtils.getColor(context, R.attr.colorOutlineVariant));
        card.setStrokeWidth(SketchwareUtil.dpToPx(1));

        LinearLayout rows = new LinearLayout(context);
        rows.setOrientation(VERTICAL);
        card.addView(rows);
        parent.addView(card, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        Section section = new Section(header, card, rows);
        sections.add(section);
        return section;
    }

    private void addRow(Section section, int id, int iconResId, int titleResId, int descriptionResId) {
        DrawerItem drawerItem = new DrawerItem(getContext());
        String title = Helper.getResString(drawerItem, titleResId);
        String description = Helper.getResString(drawerItem, descriptionResId);
        drawerItem.setContent(iconResId, title, description);
        drawerItem.setOnClickListener(id, drawerItemClickListener);
        section.rows.addView(drawerItem);
        section.items.add(new Row(drawerItem, id, (title + " " + description).toLowerCase(Locale.ROOT)));
    }

    /** Shows only the rows matching the query; sections without a match are hidden entirely. */
    private void filter(String query) {
        String q = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        for (Section section : sections) {
            int visible = 0;
            for (Row row : section.items) {
                boolean match = q.isEmpty() || row.searchText.contains(q);
                row.view.setVisibility(match ? View.VISIBLE : View.GONE);
                if (match) visible++;
            }
            int visibility = visible > 0 ? View.VISIBLE : View.GONE;
            section.header.setVisibility(visibility);
            section.card.setVisibility(visibility);
        }
    }

    /** Recomputes the status badges (counts / Enabled) from the current project data. */
    private void refreshBadges() {
        String scId = DesignActivity.sc_id;
        if (scId == null || sections.isEmpty()) return;
        for (Section section : sections) {
            for (Row row : section.items) {
                row.view.setBadge(null, false);
            }
        }
        try {
            int enabledLibraries = 0;
            for (ProjectLibraryBean bean : new ProjectLibraryBean[]{jC.c(scId).b(), jC.c(scId).c(), jC.c(scId).d()}) {
                if (bean != null && ProjectLibraryBean.LIB_USE_Y.equals(bean.useYn)) enabledLibraries++;
            }
            if (enabledLibraries > 0) setBadge(R.id.item_library_manager, enabledLibraries + " enabled", true);

            setCountBadge(R.id.item_image_manager, jC.d(scId).m().size());
            setCountBadge(R.id.item_sound_manager, jC.d(scId).p().size());
            setCountBadge(R.id.item_font_manager, jC.d(scId).k().size());
        } catch (Exception ignored) {
            // Project data not ready yet: simply show no badge.
        }
        try {
            // Only read the config when it already exists: the handlers create default files in their constructors.
            String dataDir = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + scId;
            if (FileUtil.isExistFile(dataDir + "/proguard") && new ProguardHandler(scId).isShrinkingEnabled()) {
                setBadge(R.id.item_code_shrinking_manager, "Enabled", true);
            }
            if (FileUtil.isExistFile(dataDir + "/stringfog") && new StringfogHandler(scId).isStringfogEnabled()) {
                setBadge(R.id.item_stringfog_manager, "Enabled", true);
            }
        } catch (Exception ignored) {
        }
    }

    private void setCountBadge(int id, int count) {
        if (count > 0) setBadge(id, String.valueOf(count), false);
    }

    private void setBadge(int id, String text, boolean highlighted) {
        for (Section section : sections) {
            for (Row row : section.items) {
                if (row.id == id) {
                    row.view.setBadge(text, highlighted);
                    return;
                }
            }
        }
    }

    private static class Section {
        final TextView header;
        final MaterialCardView card;
        final LinearLayout rows;
        final List<Row> items = new ArrayList<>();

        Section(TextView header, MaterialCardView card, LinearLayout rows) {
            this.header = header;
            this.card = card;
            this.rows = rows;
        }
    }

    private static class Row {
        final DrawerItem view;
        final int id;
        final String searchText;

        Row(DrawerItem view, int id, String searchText) {
            this.view = view;
            this.id = id;
            this.searchText = searchText;
        }
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

        /** text == null hides the badge; highlighted uses the coral accent, otherwise a neutral chip. */
        public void setBadge(@Nullable String text, boolean highlighted) {
            if (text == null) {
                binding.tvBadge.setVisibility(View.GONE);
                return;
            }
            binding.tvBadge.setText(text);
            binding.tvBadge.setVisibility(View.VISIBLE);
            binding.tvBadge.setBackgroundResource(highlighted ? R.drawable.bg_event_badge_active : R.drawable.bg_event_badge);
            binding.tvBadge.setTextColor(highlighted
                    ? ContextCompat.getColor(getContext(), R.color.event_on_accent)
                    : ThemeUtils.getColor(getContext(), R.attr.colorOnSurfaceVariant));
        }

        public void setOnClickListener(int id, OnClickListener listener) {
            binding.getRoot().setId(id);
            binding.getRoot().setOnClickListener(listener);
        }
    }
}
