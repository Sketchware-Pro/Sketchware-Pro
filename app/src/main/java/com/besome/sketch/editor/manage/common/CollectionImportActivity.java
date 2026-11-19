package com.besome.sketch.editor.manage.common;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.editor.manage.common.preview.FontPreviewController;
import com.besome.sketch.editor.manage.common.preview.ImagePreviewController;
import com.besome.sketch.editor.manage.common.preview.SoundPreviewController;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import a.a.a.bB;
import a.a.a.mB;
import a.a.a.uq;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageCollectionImportBinding;
import pro.sketchware.databinding.ManageImportListItemBinding;

/**
 * Unified activity for importing resources (images, sounds, fonts) from the user's collection.
 * Replaces the legacy {@code ManageImageImportActivity}, {@code ManageSoundImportActivity}, and
 * {@code ManageFontImportActivity}.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Resolve initial name conflicts against existing project resources.</li>
 *   <li>Let the user rename each item individually, or apply a common base name.</li>
 *   <li>Surface validation errors both at the input field and in the thumbnail strip.</li>
 *   <li>Hand the validated, renamed beans back to the caller via {@code setResult}.</li>
 * </ul>
 *
 * <p>Validation is performed in-house via {@link #getValidationError(String, int, boolean)} and
 * surfaced through {@link #applyInputValidation()}. The legacy {@code a.a.a.QB} validator is not
 * used because it does not always re-validate when the EditText text is changed programmatically
 * (e.g. when the user taps a thumbnail to switch items), which left the error state stale.
 */
@SuppressLint("NotifyDataSetChanged")
public class CollectionImportActivity extends BaseAppCompatActivity {

    // Resource name must start with a lowercase letter; only lowercase letters,
    // digits and underscores may follow. Mirrors the rule previously enforced by QB.
    private static final Pattern VALID_NAME_PATTERN = Pattern.compile("^[a-z][a-z0-9_]*");

    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 70;

    private ManageCollectionImportBinding binding;

    private ResourceType resourceType;
    private ItemAdapter adapter;

    // ---- Data ----
    private ArrayList<ProjectResourceBean> existingProjectResources;
    private ArrayList<ProjectResourceBean> selectedItems;
    /** Working names shown in the UI. Beans are only mutated at import time. */
    private ArrayList<String> draftNames;
    /** O(1) lookup of names that cannot be reused (project resources + app_icon for images). */
    private final Set<String> reservedNameIndex = new HashSet<>();

    /** Reserved language keywords (e.g. Java keywords) that cannot be used as resource names. */
    private String[] reservedKeywords;

    // ---- Naming mode ----
    private String commonBaseName = null;
    private boolean usingCommonBaseName = false;

    private int selectedIndex = 0;

    // ---- Preview controllers (exactly one is initialized based on resourceType) ----
    private ImagePreviewController imagePreviewController;
    private SoundPreviewController soundPreviewController;
    private FontPreviewController fontPreviewController;

    // ---------------------------------------------------------------------
    // Lifecycle
    // ---------------------------------------------------------------------

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.j()) {
            finish();
            return;
        }

        binding = ManageCollectionImportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                setResult(RESULT_OK);
                finish();
            }
        });

        Intent intent = getIntent();
        resourceType = ResourceType.fromIntent(intent);
        existingProjectResources = extractExistingResources(intent);
        selectedItems = intent.getParcelableArrayListExtra("selected_collections");

        if (selectedItems == null || selectedItems.isEmpty()) {
            finish();
            return;
        }

        // Reserved keywords are shared across the project and never allowed as names.
        reservedKeywords = uq.b;

        buildReservedNameIndex();
        setupPreviewController();
        configureResourceTexts();
        sortAndMarkInitialConflicts();

        // Seed draft names from the (post-sort) bean names.
        draftNames = new ArrayList<>(selectedItems.size());
        for (ProjectResourceBean bean : selectedItems) {
            draftNames.add(bean.resName);
        }

        setupToolbar();
        setupAdapter();
        setupInput();
        setupCommonNameCheckbox();

        // "Apply same naming" is meaningless when there is only one item.
        if (selectedItems.size() <= 1) {
            binding.chkSamename.setEnabled(false);
            binding.chkSamename.setChecked(false);
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        showPreview(selectedIndex);
        // Make sure the field reflects the initial item's validity, not a blank state.
        applyInputValidation();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (soundPreviewController != null) {
            soundPreviewController.pausePlayback();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (soundPreviewController != null) {
            soundPreviewController.releaseMediaPlayer();
        }
    }

    // ---------------------------------------------------------------------
    // Initialization
    // ---------------------------------------------------------------------

    private ArrayList<ProjectResourceBean> extractExistingResources(Intent intent) {
        ArrayList<ProjectResourceBean> list = intent.getParcelableArrayListExtra("project_resources");
        if (list != null) return list;

        return switch (resourceType) {
            case IMAGE -> intent.getParcelableArrayListExtra("project_images");
            case SOUND -> intent.getParcelableArrayListExtra("project_sounds");
            case FONT -> intent.getParcelableArrayListExtra("project_fonts");
        };
    }

    /**
     * Builds the O(1) lookup set of names that new resources cannot take.
     * {@code app_icon} is reserved only for images, never for sounds or fonts.
     */
    private void buildReservedNameIndex() {
        reservedNameIndex.clear();
        if (resourceType == ResourceType.IMAGE) {
            reservedNameIndex.add("app_icon");
        }
        if (existingProjectResources != null) {
            for (ProjectResourceBean bean : existingProjectResources) {
                reservedNameIndex.add(bean.resName);
            }
        }
    }

    private void setupPreviewController() {
        LayoutInflater inflater = getLayoutInflater();
        switch (resourceType) {
            case IMAGE -> imagePreviewController = new ImagePreviewController(
                    this, inflater, binding.previewContainer, true);
            case SOUND -> soundPreviewController = new SoundPreviewController(
                    this, inflater, binding.previewContainer, true);
            case FONT -> {
                fontPreviewController = new FontPreviewController(
                        inflater, binding.previewContainer, true);
                fontPreviewController.hideSelectButton();
            }
        }
    }

    private void configureResourceTexts() {
        switch (resourceType) {
            case IMAGE -> {
                binding.tiInput.setHint(getString(R.string.design_manager_image_hint_enter_image_name));
                binding.chkSamename.setText(R.string.design_manager_image_title_apply_same_naming);
            }
            case SOUND -> {
                binding.tiInput.setHint(getString(R.string.design_manager_sound_hint_enter_sound_name));
                binding.chkSamename.setText(R.string.design_manager_sound_title_apply_same_naming);
            }
            case FONT -> {
                binding.tiInput.setHint(getString(R.string.design_manager_font_hint_enter_font_name));
                binding.chkSamename.setText(R.string.design_manager_font_title_apply_same_naming);
            }
        }
    }

    /**
     * Checks initial name conflicts, shows a status toast, and floats conflicting
     * items to the top so the user sees them first.
     */
    private void sortAndMarkInitialConflicts() {
        ArrayList<ProjectResourceBean> conflicting = new ArrayList<>();
        ArrayList<ProjectResourceBean> valid = new ArrayList<>();

        for (ProjectResourceBean bean : selectedItems) {
            if (isNameReserved(bean.resName)) {
                bean.isDuplicateCollection = true;
                conflicting.add(bean);
            } else {
                bean.isDuplicateCollection = false;
                valid.add(bean);
            }
        }

        if (!conflicting.isEmpty()) {
            bB.b(getApplicationContext(),
                    getString(R.string.design_manager_message_collection_name_conflict),
                    bB.TOAST_WARNING).show();
        } else {
            bB.a(getApplicationContext(),
                    getString(R.string.design_manager_message_collection_name_no_conflict),
                    bB.TOAST_NORMAL).show();
        }

        selectedItems = new ArrayList<>(conflicting.size() + valid.size());
        selectedItems.addAll(conflicting);
        selectedItems.addAll(valid);
    }

    // ---------------------------------------------------------------------
    // UI setup
    // ---------------------------------------------------------------------

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> {
            if (mB.a()) return;
            getOnBackPressedDispatcher().onBackPressed();
        });
        binding.toolbar.setOnMenuItemClickListener(item -> {
            if (mB.a()) return true;
            if (item.getItemId() == R.id.action_import) {
                handleImport();
                return true;
            }
            return false;
        });
        MenuItem importItem = binding.toolbar.getMenu().findItem(R.id.action_import);
        if (importItem != null) {
            importItem.setTitle(getString(R.string.common_word_import).toUpperCase());
        }
    }

    private void setupAdapter() {
        adapter = new ItemAdapter();
        binding.recyclerList.setHasFixedSize(true);
        binding.recyclerList.setAdapter(adapter);
        binding.recyclerList.setLayoutManager(
                new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false));
    }

    private void setupInput() {
        binding.edInput.setText(draftNames.get(selectedIndex));
        updateCountLabel();

        binding.edInput.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();

                if (usingCommonBaseName) {
                    // The shared base name is stored here; the numbered variants
                    // (base_1, base_2, ...) are derived only at import time.
                    commonBaseName = text;
                    applyInputValidation();
                    return;
                }

                draftNames.set(selectedIndex, text);

                // Keep the thumbnail conflict icon in sync with the field's verdict.
                boolean hasConflict = getValidationError(text, selectedIndex, false) != null;
                ProjectResourceBean current = selectedItems.get(selectedIndex);
                if (current.isDuplicateCollection != hasConflict) {
                    current.isDuplicateCollection = hasConflict;
                    adapter.notifyItemChanged(selectedIndex);
                }

                applyInputValidation();
            }
        });
    }

    private void setupCommonNameCheckbox() {
        binding.chkSamename.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showCommonNameDialog();
                usingCommonBaseName = true;
                binding.edInput.setText(commonBaseName != null ? commonBaseName : "");
            } else {
                usingCommonBaseName = false;
                binding.edInput.setText(draftNames.get(selectedIndex));
            }
            applyInputValidation();
        });
    }

    private void showCommonNameDialog() {
        int titleRes = switch (resourceType) {
            case IMAGE -> R.string.design_manager_image_title_apply_same_naming;
            case SOUND -> R.string.design_manager_sound_title_apply_same_naming;
            case FONT -> R.string.design_manager_font_title_apply_same_naming;
        };

        new MaterialAlertDialogBuilder(this)
                .setTitle(titleRes)
                .setMessage("All selected items will share one base name and be numbered "
                        + "automatically (base_1, base_2, ...). Unchecking restores each "
                        + "item's individual name.")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    // ---------------------------------------------------------------------
    // Preview
    // ---------------------------------------------------------------------

    private void showPreview(int index) {
        String filePath = selectedItems.get(index).resFullName;
        switch (resourceType) {
            case IMAGE -> imagePreviewController.loadCollectionPreview(filePath);
            case SOUND -> soundPreviewController.loadSoundFromFile(filePath, false);
            case FONT -> fontPreviewController.loadFont(new File(filePath), true);
        }
    }

    private void updateCountLabel() {
        binding.tvCount.setText((selectedIndex + 1) + " / " + selectedItems.size());
    }

    // ---------------------------------------------------------------------
    // Validation (self-contained, replaces the legacy QB validator)
    // ---------------------------------------------------------------------

    /**
     * Validates the given name against the full rule set:
     * length, allowed characters, reserved project names, reserved language
     * keywords, and duplicates within the current draft.
     *
     * @param name            the candidate name (will be trimmed)
     * @param itemIndex       index of the item this name belongs to (excluded
     *                        from the duplicate-within-draft check)
     * @param allowDuplicates when {@code true} (common-base mode), the
     *                        duplicate-within-draft check is skipped because
     *                        each item will be suffixed with {@code _N} at
     *                        import time, so duplicates in the draft no longer
     *                        collide
     * @return localized error message, or {@code null} when the name is valid
     */
    private String getValidationError(String name, int itemIndex, boolean allowDuplicates) {
        if (name == null) {
            return getString(R.string.common_message_name_unavailable);
        }
        String trimmed = name.trim();

        if (trimmed.isEmpty()) {
            return getString(R.string.common_message_name_unavailable);
        }
        if (trimmed.length() < MIN_NAME_LENGTH) {
            return getString(R.string.invalid_value_min_lenth, MIN_NAME_LENGTH);
        }
        if (trimmed.length() > MAX_NAME_LENGTH) {
            return getString(R.string.invalid_value_max_lenth, MAX_NAME_LENGTH);
        }
        if ("default_image".equals(trimmed) || "NONE".equalsIgnoreCase(trimmed)) {
            return getString(R.string.common_message_name_unavailable);
        }
        if (!Character.isLetter(trimmed.charAt(0))) {
            return getString(R.string.logic_editor_message_variable_name_must_start_letter);
        }
        if (!VALID_NAME_PATTERN.matcher(trimmed).matches()) {
            return getString(R.string.invalid_value_rule_4);
        }
        if (isReservedKeyword(trimmed)) {
            return getString(R.string.logic_editor_message_reserved_keywords);
        }
        if (isNameReserved(trimmed)) {
            return getString(R.string.common_message_name_unavailable);
        }
        if (!allowDuplicates && isDuplicateInDraft(trimmed, itemIndex)) {
            return getString(R.string.common_message_name_unavailable);
        }
        return null;
    }

    /**
     * Re-runs validation for the currently shown name and pushes the result
     * into the {@code TextInputLayout}. Called from three places:
     * <ol>
     *   <li>{@link #onPostCreate(Bundle)} — initial paint</li>
     *   <li>{@link #setupInput()} text watcher — when the user types</li>
     *   <li>The thumbnail click handler — when the user switches items</li>
     * </ol>
     * Centralising the call here is what guarantees the error stays in sync
     * across all entry points instead of relying on QB's TextWatcher firing
     * for programmatic {@code setText()} calls (which it does not always do).
     */
    private void applyInputValidation() {
        String currentName;
        boolean allowDuplicates;
        if (usingCommonBaseName) {
            currentName = commonBaseName != null ? commonBaseName : "";
            // Common-base mode produces base_1, base_2, ... so a base name
            // that happens to match another draft entry does not collide.
            allowDuplicates = true;
        } else {
            currentName = draftNames.get(selectedIndex);
            allowDuplicates = false;
        }

        String error = getValidationError(currentName, selectedIndex, allowDuplicates);
        if (error != null) {
            binding.tiInput.setError(error);
        } else {
            binding.tiInput.setError(null);
        }
    }

    /**
     * True when {@code name} is reserved by the project (an existing resource,
     * or {@code app_icon} for images). Uses the pre-built index — O(1).
     */
    private boolean isNameReserved(String name) {
        return name != null && reservedNameIndex.contains(name);
    }

    /**
     * True when {@code name} matches one of the reserved language keywords.
     */
    private boolean isReservedKeyword(String name) {
        if (reservedKeywords == null) return false;
        for (String keyword : reservedKeywords) {
            if (name.equals(keyword)) {
                return true;
            }
        }
        return false;
    }

    /**
     * True when {@code name} duplicates another item in the current draft,
     * excluding the item at {@code excludeIndex}.
     */
    private boolean isDuplicateInDraft(String name, int excludeIndex) {
        if (name == null || name.isEmpty()) return false;
        for (int i = 0; i < draftNames.size(); i++) {
            if (i == excludeIndex) continue;
            if (name.equals(draftNames.get(i))) return true;
        }
        return false;
    }

    // ---------------------------------------------------------------------
    // Import
    // ---------------------------------------------------------------------

    private void handleImport() {
        ArrayList<String> finalNames = buildFinalNames();
        if (finalNames == null) {
            return;
        }

        // Final gate: re-validate every produced name. This catches both
        // per-item problems and any cross-item duplicates that survived.
        Set<String> seenNames = new HashSet<>();
        for (int i = 0; i < finalNames.size(); i++) {
            String name = finalNames.get(i);
            String error = getValidationError(name, i, true);
            if (error == null && !seenNames.add(name)) {
                error = getString(R.string.common_message_name_unavailable);
            }
            if (error != null) {
                bB.b(this, error + "\n[" + name + "]", bB.TOAST_WARNING).show();
                return;
            }
        }

        // Commit validated names to the beans right before returning the result.
        for (int i = 0; i < selectedItems.size(); i++) {
            selectedItems.get(i).resName = finalNames.get(i);
            selectedItems.get(i).isDuplicateCollection = false;
        }

        if (soundPreviewController != null) {
            soundPreviewController.releaseMediaPlayer();
        }

        Intent result = new Intent();
        result.putParcelableArrayListExtra("results", selectedItems);
        setResult(RESULT_OK, result);
        finish();
    }

    /**
     * Produces the final names for every item. Returns {@code null} (and shows
     * a toast) if any item ends up with an empty draft name.
     */
    private ArrayList<String> buildFinalNames() {
        ArrayList<String> names = new ArrayList<>(selectedItems.size());
        if (usingCommonBaseName) {
            if (commonBaseName == null || commonBaseName.trim().isEmpty()) {
                toastNameUnavailable();
                return null;
            }
            String base = commonBaseName.trim();
            for (int i = 0; i < selectedItems.size(); i++) {
                names.add(base + "_" + (i + 1));
            }
        } else {
            for (int i = 0; i < selectedItems.size(); i++) {
                String name = draftNames.get(i);
                if (name == null || name.trim().isEmpty()) {
                    toastNameUnavailable();
                    return null;
                }
                names.add(name.trim());
            }
        }
        return names;
    }

    private void toastNameUnavailable() {
        bB.b(this, getString(R.string.common_message_name_unavailable),
                bB.TOAST_WARNING).show();
    }

    // ---------------------------------------------------------------------
    // RecyclerView Adapter
    // ---------------------------------------------------------------------

    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            ProjectResourceBean item = selectedItems.get(position);

            holder.binding.imgConflict.setImageResource(
                    item.isDuplicateCollection
                            ? R.drawable.ic_cancel_48dp
                            : R.drawable.ic_ok_48dp);

            if (position == selectedIndex) {
                holder.binding.img.setBackgroundResource(R.drawable.bg_outline_dark_yellow);
            } else if (resourceType == ResourceType.FONT) {
                holder.binding.img.setBackgroundResource(R.drawable.bg_outline);
            } else {
                holder.binding.img.setBackgroundColor(Color.WHITE);
            }

            switch (resourceType) {
                case IMAGE -> Glide.with(getApplicationContext())
                        .load(item.resFullName)
                        .centerCrop()
                        .error(R.drawable.ic_remove_grey600_24dp)
                        .into(new BitmapImageViewTarget(holder.binding.img).getView());
                case SOUND -> SoundPreviewController.loadEmbeddedAlbumArt(
                        CollectionImportActivity.this, item.resFullName, holder.binding.img);
                case FONT -> holder.binding.img.setImageResource(R.drawable.ic_mtrl_font);
            }

            // The strip always shows the original bean name; edits live in the input field.
            holder.binding.tvName.setText(item.resName);
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ManageImportListItemBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false));
        }

        @Override
        public int getItemCount() {
            return selectedItems.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            public final ManageImportListItemBinding binding;

            public ViewHolder(ManageImportListItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.binding = itemBinding;
                binding.img.setOnClickListener(v -> {
                    if (mB.a()) return;
                    if (soundPreviewController != null) {
                        soundPreviewController.pausePlayback();
                    }
                    selectedIndex = getLayoutPosition();
                    showPreview(selectedIndex);
                    updateCountLabel();

                    // Reload the draft (or the shared base name) into the field
                    // for the newly selected item.
                    if (usingCommonBaseName) {
                        CollectionImportActivity.this.binding.edInput.setText(
                                commonBaseName != null ? commonBaseName : "");
                    } else {
                        CollectionImportActivity.this.binding.edInput.setText(
                                draftNames.get(selectedIndex));
                    }

                    // Move the caret to the end and focus the field so the user
                    // can keep typing without an extra tap.
                    CollectionImportActivity.this.binding.edInput.requestFocus();
                    Editable ed = CollectionImportActivity.this.binding.edInput.getText();
                    if (ed != null) {
                        CollectionImportActivity.this.binding.edInput.setSelection(ed.length());
                    }

                    // Re-run validation explicitly. setText() does fire the
                    // TextWatcher when the new text differs, but the previous
                    // item's error could have been left in place if the new
                    // text matches what was already shown; calling this here
                    // guarantees the error always matches the current item.
                    applyInputValidation();
                    adapter.notifyDataSetChanged();
                });
            }
        }
    }
}
