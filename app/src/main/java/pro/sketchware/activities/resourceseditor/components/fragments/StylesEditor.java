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

public class StylesEditor extends Fragment {

    private ResourcesEditorFragmentBinding binding;

    public StylesAdapter adapter;
    private PropertyInputItem.AttributesAdapter attributesAdapter;

    private final ArrayList<StyleModel> stylesList = new ArrayList<>();
    private HashMap<Integer, String> notesMap = new HashMap<>();

    public final StylesEditorManager stylesEditorManager = new StylesEditorManager();

    public boolean hasUnsavedChanges;
    private String filePath;
    private ResourcesEditorActivity activity;

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

    public void updateStylesList(String filePath, int updateMode, boolean hasUnsavedChangesStatus) {
        hasUnsavedChanges = hasUnsavedChangesStatus;
        this.filePath = filePath;
        boolean isSkippingMode = updateMode == 1;
        boolean isMergeAndReplace = updateMode == 2;

        ArrayList<StyleModel> defaultStyles;

        if ((activity.variant.isEmpty() || hasUnsavedChanges) && !FileUtil.isExistFile(filePath)) {
            String generatedContent = activity.yq.getXMLStyle();
            defaultStyles = stylesEditorManager.parseStylesFile(generatedContent);
        } else {
            defaultStyles = stylesEditorManager.parseStylesFile(FileUtil.readFileIfExist(filePath));
        }

        if (isSkippingMode) {
            HashSet<String> existingStyleNames = new HashSet<>();
            for (StyleModel existingStyle : stylesList) {
                existingStyleNames.add(existingStyle.getStyleName());
            }

            for (StyleModel style : defaultStyles) {
                if (!existingStyleNames.contains(style.getStyleName())) {
                    stylesList.add(style);
                }
            }
        } else {
            if (isMergeAndReplace) {
                HashSet<String> newStyleNames = new HashSet<>();
                for (StyleModel style : defaultStyles) {
                    newStyleNames.add(style.getStyleName());
                }

                stylesList.removeIf(existingStyle -> newStyleNames.contains(existingStyle.getStyleName()));
            } else {
                stylesList.clear();
            }
            stylesList.addAll(defaultStyles);
        }

        activity.runOnUiThread(() -> {
            notesMap = new HashMap<>(stylesEditorManager.notesMap);
            adapter = new StylesAdapter(stylesList, this, notesMap);
            binding.recyclerView.setAdapter(adapter);
            activity.checkForInvalidResources();
            updateNoContentLayout();
            if (hasUnsavedChanges) {
                this.filePath = activity.stylesFilePath;
            }
        });
    }

    private void updateNoContentLayout() {
        if (stylesList.isEmpty()) {
            binding.noContentLayout.setVisibility(View.VISIBLE);
            binding.noContentTitle.setText(String.format(Helper.getResString(R.string.resource_manager_no_list_title), "Styles"));
            binding.noContentBody.setText(String.format(Helper.getResString(R.string.resource_manager_no_list_body), "styles"));
        } else {
            binding.noContentLayout.setVisibility(View.GONE);
        }
    }

    public void showAddStyleDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireActivity());
        StyleEditorAddBinding binding = StyleEditorAddBinding.inflate(getLayoutInflater());
        dialog.setTitle("Create new style");
        dialog.setPositiveButton("Create", (d, which) -> {
            String styleName = Objects.requireNonNull(binding.styleName.getText()).toString();
            String parent = Objects.requireNonNull(binding.styleParent.getText()).toString();
            String header = Objects.requireNonNull(binding.styleHeaderInput.getText()).toString();

            if (styleName.isEmpty()) {
                SketchwareUtil.toastError("Style name Input is Empty");
                return;
            }

            if (stylesEditorManager.isStyleExist(stylesList, styleName)) {
                SketchwareUtil.toastError("\"" + styleName + "\" is already exist");
                return;
            }

            StyleModel style = new StyleModel(styleName, parent);
            stylesList.add(style);
            int notifyPosition = stylesList.size() - 1;
            if (!header.isEmpty()) {
                notesMap.put(notifyPosition, header);
            }
            hasUnsavedChanges = true;
            adapter.notifyItemInserted(notifyPosition);
            updateNoContentLayout();
        });
        dialog.setNegativeButton(getString(R.string.cancel), null);
        dialog.setView(binding.getRoot());
        dialog.show();
    }

    public void showEditStyleDialog(int position) {
        StyleModel style = stylesList.get(position);
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireActivity());
        StyleEditorAddBinding binding = StyleEditorAddBinding.inflate(getLayoutInflater());

        binding.styleName.setText(style.getStyleName());
        binding.styleParent.setText(style.getParent());
        if (notesMap.containsKey(position)) {
            binding.styleHeaderInput.setText(notesMap.get(position));
        }

        dialog.setTitle("Edit style");
        dialog.setPositiveButton("Edit", (d, which) -> {
            String styleName = Objects.requireNonNull(binding.styleName.getText()).toString();
            String parent = Objects.requireNonNull(binding.styleParent.getText()).toString();
            String header = Objects.requireNonNull(binding.styleHeaderInput.getText()).toString();

            if (styleName.isEmpty()) {
                SketchwareUtil.toastError("Style name Input is Empty");
                return;
            }

            style.setStyleName(styleName);
            style.setParent(parent);
            if (header.isEmpty()) {
                notesMap.remove(position);
            } else {
                notesMap.put(position, header);
            }
            hasUnsavedChanges = true;
            adapter.notifyItemChanged(position);
        });
        dialog.setNeutralButton(Helper.getResString(R.string.common_word_delete), (d, which) -> new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Warning")
                .setMessage("Are you sure you want to delete " + style.getStyleName() + "?")
                .setPositiveButton(R.string.common_word_yes, (d2, w) -> {
                    stylesList.remove(position);
                    notesMap.remove(position);
                    adapter.notifyItemRemoved(position);
                    d.dismiss();
                    updateNoContentLayout();
                    hasUnsavedChanges = true;
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show());
        dialog.setNegativeButton(getString(R.string.cancel), null);
        dialog.setView(binding.getRoot());
        dialog.show();
    }

    public void showStyleAttributesDialog(int position) {
        StyleModel style = stylesList.get(position);
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
        var binding = PropertyPopupParentAttrBinding.inflate(getLayoutInflater());
        dialog.setContentView(binding.getRoot());
        dialog.show();

        binding.title.setText(style.getStyleName() + " attributes");

        attributesAdapter = new PropertyInputItem.AttributesAdapter();
        attributesAdapter.setOnItemClickListener(
                new PropertyInputItem.AttributesAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(LinkedHashMap<String, String> attributes, String attr) {
                        showAttributeDialog(style, attr);
                    }

                    @Override
                    public void onItemLongClick(LinkedHashMap<String, String> attributes, String attr) {
                        new MaterialAlertDialogBuilder(requireContext())
                                .setTitle("Warning")
                                .setMessage("Are you sure you want to delete " + attr + "?")
                                .setPositiveButton(R.string.common_word_yes, (d, w) -> {
                                    attributes.remove(attr);
                                    style.setAttributes(attributes);
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
        var attributes = style.getAttributes();
        attributesAdapter.setAttributes(attributes);
        List<String> keys = new ArrayList<>(attributes.keySet());
        attributesAdapter.submitList(keys);

        binding.add.setOnClickListener(
                v -> showAttributeDialog(style, ""));
    }

    private void showAttributeDialog(StyleModel style, String attr) {
        boolean isEditing = !attr.isEmpty();

        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireActivity());
        StyleEditorAddAttrBinding binding = StyleEditorAddAttrBinding.inflate(getLayoutInflater());

        if (isEditing) {
            binding.attrName.setText(attr);
            binding.attrValue.setText(style.getAttribute(attr));
        }

        dialog.setTitle(isEditing ? "Edit attribute" : "Create new attribute");

        dialog.setPositiveButton(Helper.getResString(R.string.common_word_save), (d, which) -> {
            String attribute = Objects.requireNonNull(binding.attrName.getText()).toString();
            String value = Objects.requireNonNull(binding.attrValue.getText()).toString();

            if (attribute.isEmpty() || value.isEmpty()) {
                SketchwareUtil.toastError("Please fill in all fields");
                return;
            }

            if (!attribute.equals(attr)) style.getAttributes().remove(attr);

            style.addAttribute(attribute, value);
            attributesAdapter.submitList(new ArrayList<>(style.getAttributes().keySet()));
            attributesAdapter.notifyDataSetChanged();
            hasUnsavedChanges = true;
        });

        dialog.setNegativeButton(getString(R.string.cancel), null);
        dialog.setView(binding.getRoot());
        dialog.show();
    }

    public void saveStylesFile() {
        if (hasUnsavedChanges) {
            FileUtil.writeFile(filePath, stylesEditorManager.convertStylesToXML(stylesList, notesMap));
            hasUnsavedChanges = false;
        }
    }

}
