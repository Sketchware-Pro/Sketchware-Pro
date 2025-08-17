package pro.sketchware.activities.resourceseditor.components.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.besome.sketch.editor.property.PropertyInputItem;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.activities.resourceseditor.ResourcesEditorActivity;
import pro.sketchware.activities.resourceseditor.components.adapters.StylesAdapter;
import pro.sketchware.activities.resourceseditor.components.models.StyleModel;
import pro.sketchware.activities.resourceseditor.components.utils.StylesEditorManager;
import pro.sketchware.databinding.PropertyPopupParentAttrBinding;
import pro.sketchware.databinding.ResourcesEditorFragmentBinding;
import pro.sketchware.databinding.StyleEditorAddAttrBinding;
import pro.sketchware.databinding.StyleEditorAddBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class ThemesEditor extends Fragment {

    private ResourcesEditorFragmentBinding binding;
    private ResourcesEditorActivity activity;

    public StylesAdapter adapter;
    private PropertyInputItem.AttributesAdapter attributesAdapter;

    private final ArrayList<StyleModel> themesList = new ArrayList<>();
    private HashMap<Integer, String> notesMap = new HashMap<>();

    public final StylesEditorManager themesEditorManager = new StylesEditorManager();

    public boolean hasUnsavedChanges;
    private String filePath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (ResourcesEditorActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ResourcesEditorFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void updateThemesList(String filePath, int updateMode, boolean hasUnsavedChangesStatus) {
        hasUnsavedChanges = hasUnsavedChangesStatus;
        this.filePath = filePath;
        boolean isSkippingMode = updateMode == 1;
        boolean isMergeAndReplace = updateMode == 2;

        ArrayList<StyleModel> defaultStyles = themesEditorManager.parseStylesFile(FileUtil.readFileIfExist(filePath));

        if (isSkippingMode) {
            HashSet<String> existingThemeNames = new HashSet<>();
            for (StyleModel existingStyle : themesList) {
                existingThemeNames.add(existingStyle.getStyleName());
            }

            for (StyleModel style : defaultStyles) {
                if (!existingThemeNames.contains(style.getStyleName())) {
                    themesList.add(style);
                }
            }
        } else {
            if (isMergeAndReplace) {
                HashSet<String> newThemeNames = new HashSet<>();
                for (StyleModel theme : defaultStyles) {
                    newThemeNames.add(theme.getStyleName());
                }

                themesList.removeIf(existingStyle -> newThemeNames.contains(existingStyle.getStyleName()));
            } else {
                themesList.clear();
            }
            themesList.addAll(defaultStyles);
        }

        activity.runOnUiThread(() -> {
            notesMap = new HashMap<>(themesEditorManager.notesMap);
            adapter = new StylesAdapter(themesList, this, notesMap);
            binding.recyclerView.setAdapter(adapter);
            activity.checkForInvalidResources();
            updateNoContentLayout();
            if (hasUnsavedChanges) {
                this.filePath = activity.themesFilePath;
            }
        });
    }

    private void updateNoContentLayout() {
        if (themesList.isEmpty()) {
            binding.noContentLayout.setVisibility(View.VISIBLE);
            binding.noContentTitle.setText(String.format(Helper.getResString(R.string.resource_manager_no_list_title), "Themes"));
            binding.noContentBody.setText(String.format(Helper.getResString(R.string.resource_manager_no_list_body), "themes"));
        } else {
            binding.noContentLayout.setVisibility(View.GONE);
        }
    }

    public void showAddThemeDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
        StyleEditorAddBinding binding = StyleEditorAddBinding.inflate(getLayoutInflater());
        dialog.setTitle("Create new theme");
        dialog.setPositiveButton("Create", (d, which) -> {
            String themeName = Objects.requireNonNull(binding.styleName.getText()).toString();
            String parent = Objects.requireNonNull(binding.styleParent.getText()).toString();
            String header = Objects.requireNonNull(binding.styleHeaderInput.getText()).toString();

            if (themeName.isEmpty()) {
                SketchwareUtil.toastError("Theme name Input is Empty");
                return;
            }

            StyleModel theme = new StyleModel(themeName, parent);
            themesList.add(theme);
            int notifyPosition = themesList.size() - 1;
            if (!header.isEmpty()) {
                notesMap.put(notifyPosition, header);
            }
            adapter.notifyItemInserted(notifyPosition);
            updateNoContentLayout();
            hasUnsavedChanges = true;
        });
        dialog.setNegativeButton(getString(R.string.cancel), null);
        dialog.setView(binding.getRoot());
        dialog.show();
    }

    public void showEditThemeDialog(int position) {
        StyleModel theme = themesList.get(position);
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
        StyleEditorAddBinding binding = StyleEditorAddBinding.inflate(getLayoutInflater());

        binding.styleName.setText(theme.getStyleName());
        binding.styleParent.setText(theme.getParent());
        if (notesMap.containsKey(position)) {
            binding.styleHeaderInput.setText(notesMap.get(position));
        }

        dialog.setTitle("Edit theme");
        dialog.setPositiveButton("Edit", (d, which) -> {
            String themeName = Objects.requireNonNull(binding.styleName.getText()).toString();
            String parent = Objects.requireNonNull(binding.styleParent.getText()).toString();
            String header = Objects.requireNonNull(binding.styleHeaderInput.getText()).toString();

            if (themeName.isEmpty()) {
                SketchwareUtil.toastError("Theme name Input is Empty");
                return;
            }

            theme.setStyleName(themeName);
            theme.setParent(parent);
            if (header.isEmpty()) {
                notesMap.remove(position);
            } else {
                notesMap.put(position, header);
            }

            adapter.notifyItemChanged(position);
            hasUnsavedChanges = true;
        });
        dialog.setNeutralButton(Helper.getResString(R.string.common_word_delete), (d, which) -> new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Warning")
                .setMessage("Are you sure you want to delete " + theme.getStyleName() + "?")
                .setPositiveButton(R.string.common_word_yes, (d2, w) -> {
                    themesList.remove(position);
                    notesMap.remove(position);
                    adapter.notifyItemRemoved(position);
                    d.dismiss();
                    updateNoContentLayout();
                    hasUnsavedChanges = true;
                })
                .setNegativeButton("Cancel", null)
                .show());
        dialog.setNegativeButton(getString(R.string.cancel), null);
        dialog.setView(binding.getRoot());
        dialog.show();
    }

    public void showThemeAttributesDialog(int position) {
        StyleModel theme = themesList.get(position);
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        var binding = PropertyPopupParentAttrBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding.getRoot());
        dialog.show();

        binding.title.setText(theme.getStyleName() + " attributes");

        attributesAdapter = new PropertyInputItem.AttributesAdapter();
        attributesAdapter.setOnItemClickListener(
                new PropertyInputItem.AttributesAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(LinkedHashMap<String, String> attributes, String attr) {
                        showAttributeDialog(theme, attr);
                    }

                    @Override
                    public void onItemLongClick(LinkedHashMap<String, String> attributes, String attr) {
                        new MaterialAlertDialogBuilder(requireContext())
                                .setTitle("Warning")
                                .setMessage("Are you sure you want to delete " + attr + "?")
                                .setPositiveButton(R.string.common_word_yes, (d, w) -> {
                                    attributes.remove(attr);
                                    theme.setAttributes(attributes);
                                    attributesAdapter.submitList(new ArrayList<>(attributes.keySet()));
                                    hasUnsavedChanges = true;
                                })
                                .setNegativeButton("Cancel", null)
                                .create()
                                .show();
                    }
                });
        binding.recyclerView.setAdapter(attributesAdapter);
        var dividerItemDecoration =
                new DividerItemDecoration(
                        binding.recyclerView.getContext(), LinearLayoutManager.VERTICAL);
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
        var attributes = theme.getAttributes();
        attributesAdapter.setAttributes(attributes);
        List<String> keys = new ArrayList<>(attributes.keySet());
        attributesAdapter.submitList(keys);

        binding.add.setOnClickListener(
                v -> showAttributeDialog(theme, ""));
    }

    private void showAttributeDialog(StyleModel theme, String attr) {
        boolean isEditing = !attr.isEmpty();

        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
        StyleEditorAddAttrBinding binding = StyleEditorAddAttrBinding.inflate(getLayoutInflater());

        if (isEditing) {
            binding.attrName.setText(attr);
            binding.attrValue.setText(theme.getAttribute(attr));
        }

        dialog.setTitle(isEditing ? "Edit attribute " : "Create new attribute");

        dialog.setPositiveButton(Helper.getResString(R.string.common_word_save), (d, which) -> {
            String attribute = Objects.requireNonNull(binding.attrName.getText()).toString();
            String value = Objects.requireNonNull(binding.attrValue.getText()).toString();

            if (attribute.isEmpty() || value.isEmpty()) {
                SketchwareUtil.toastError("Please fill in all fields");
                return;
            }

            if (!attribute.equals(attr)) theme.getAttributes().remove(attr);

            theme.addAttribute(attribute, value);
            attributesAdapter.submitList(new ArrayList<>(theme.getAttributes().keySet()));
            attributesAdapter.notifyDataSetChanged();
            hasUnsavedChanges = true;
        });

        dialog.setNegativeButton(getString(R.string.cancel), null);
        dialog.setView(binding.getRoot());
        dialog.show();
    }

    public void saveThemesFile() {
        if (hasUnsavedChanges) {
            FileUtil.writeFile(filePath, themesEditorManager.convertStylesToXML(themesList, notesMap));
            hasUnsavedChanges = false;
        }
    }

}
