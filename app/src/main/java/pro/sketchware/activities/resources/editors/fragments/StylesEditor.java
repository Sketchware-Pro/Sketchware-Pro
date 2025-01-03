package pro.sketchware.activities.resources.editors.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.besome.sketch.editor.property.PropertyInputItem;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.gson.Gson;

import a.a.a.aB;
import mod.hey.studios.util.Helper;

import pro.sketchware.R;
import pro.sketchware.activities.resources.editors.ResourcesEditorActivity;
import pro.sketchware.activities.resources.editors.utils.AttributeSuggestions;
import pro.sketchware.databinding.PropertyPopupInputTextBinding;
import pro.sketchware.databinding.PropertyPopupParentAttrBinding;
import pro.sketchware.databinding.ResourcesEditorFragmentBinding;
import pro.sketchware.databinding.StyleEditorAddAttrBinding;
import pro.sketchware.databinding.StyleEditorAddBinding;
import pro.sketchware.activities.resources.editors.models.StyleModel;
import pro.sketchware.activities.resources.editors.adapters.StylesAdapter;
import pro.sketchware.activities.resources.editors.utils.StylesEditorManager;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StylesEditor extends Fragment {

    private ResourcesEditorFragmentBinding binding;
    public StylesAdapter adapter;
    private PropertyInputItem.AttributesAdapter attributesAdapter;
    private ArrayList<StyleModel> stylesList;
    public final StylesEditorManager stylesEditorManager = new StylesEditorManager();
    private final AttributeSuggestions attributeSuggestions = new AttributeSuggestions();
    private String filePath;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ResourcesEditorFragmentBinding.inflate(inflater, container, false);
        initialize();
        updateStylesList(filePath);
        ((ResourcesEditorActivity) requireActivity()).checkForInvalidResources();
        return binding.getRoot();
    }

    public void updateStylesList(String filePath) {
        stylesList = new ArrayList<>();
        try {
            stylesList = stylesEditorManager.parseStylesFile(FileUtil.readFileIfExist(filePath));
        } catch (Exception e) {
            SketchwareUtil.toastError(e.getMessage());
        }
        adapter = new StylesAdapter(stylesList, this);
        binding.recyclerView.setAdapter(adapter);
        updateNoContentLayout();
    }

    private void initialize() {
        filePath = ((ResourcesEditorActivity) requireActivity()).stylesFilePath;
    }

    private void updateNoContentLayout() {
        if (stylesList.isEmpty()) {
            binding.noContentLayout.setVisibility(View.VISIBLE);
            binding.noContentTitle.setText(String.format(Helper.getResString(R.string.resource_manager_no_list_title), "Styles"));
            binding.noContentBody.setText(String.format(Helper.getResString(R.string.resource_manager_no_list_body), "Styles"));
        } else {
            binding.noContentLayout.setVisibility(View.GONE);
        }
    }

    public boolean checkForUnsavedChanges() {
        if (!FileUtil.isExistFile(filePath) && stylesList.isEmpty()) {
            return false;
        }
        Gson gson = new Gson();
        return !gson.toJson(stylesList).equals(gson.toJson(stylesEditorManager.parseStylesFile(FileUtil.readFileIfExist(filePath))));
    }

    public void showAddStyleDialog() {
        aB dialog = new aB(requireActivity());
        StyleEditorAddBinding binding = StyleEditorAddBinding.inflate(getLayoutInflater());
        dialog.b("Create new style");
        dialog.b("Create", v1 -> {
            String styleName = Objects.requireNonNull(binding.styleName.getText()).toString();
            String parent = Objects.requireNonNull(binding.styleParent.getText()).toString();

            if (styleName.isEmpty()) {
                SketchwareUtil.toastError("Style name Input is Empty");
                return;
            }

            StyleModel style = new StyleModel(styleName, parent);
            stylesList.add(style);
            adapter.notifyItemInserted(stylesList.size() - 1);
            updateNoContentLayout();
        });
        dialog.a(getString(R.string.cancel), Helper.getDialogDismissListener(dialog));
        dialog.a(binding.getRoot());
        dialog.show();
    }

    public void showEditStyleDialog(int position) {
        StyleModel style = stylesList.get(position);
        aB dialog = new aB(requireActivity());
        StyleEditorAddBinding binding = StyleEditorAddBinding.inflate(getLayoutInflater());

        binding.styleName.setText(style.getStyleName());
        binding.styleParent.setText(style.getParent());

        dialog.b("Edit style");
        dialog.b("Edit", v1 -> {
            String styleName = Objects.requireNonNull(binding.styleName.getText()).toString();
            String parent = Objects.requireNonNull(binding.styleParent.getText()).toString();

            if (styleName.isEmpty()) {
                SketchwareUtil.toastError("Style name Input is Empty");
                return;
            }

            style.setStyleName(styleName);
            style.setParent(parent);

            adapter.notifyItemChanged(position);
        });
        dialog.setDismissOnDefaultButtonClick(false);
        dialog.configureDefaultButton(Helper.getResString(R.string.common_word_delete), view -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Warning")
                    .setMessage("Are you sure you want to delete " + style.getStyleName() + "?")
                    .setPositiveButton(R.string.common_word_yes, (d, w) -> {
                        stylesList.remove(position);
                        adapter.notifyItemRemoved(position);
                        dialog.dismiss();
                        updateNoContentLayout();
                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                    .show();
        });
        dialog.a(getString(R.string.cancel), Helper.getDialogDismissListener(dialog));
        dialog.a(binding.getRoot());
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
                    public void onItemClick(Map<String, String> attributes, String attr) {
                        showAttributeDialog(style, attr);
                    }

                    @Override
                    public void onItemLongClick(Map<String, String> attributes, String attr) {
                        new MaterialAlertDialogBuilder(requireContext())
                                .setTitle("Warning")
                                .setMessage("Are you sure you want to delete " + attr + "?")
                                .setPositiveButton(R.string.common_word_yes, (d, w) -> {
                                    attributes.remove(attr);
                                    style.setAttributes(attributes);
                                    attributesAdapter.submitList(new ArrayList<>(attributes.keySet()));
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
        binding.sourceCode.setVisibility(View.VISIBLE);
        binding.sourceCode.setOnClickListener(
                v -> showAttributesEditorDialog(style));
    }

    private void showAttributeDialog(StyleModel style, String attr) {
        boolean isEditing = !attr.isEmpty();

        aB dialog = new aB(requireActivity());
        StyleEditorAddAttrBinding binding = StyleEditorAddAttrBinding.inflate(getLayoutInflater());
        setupAutoComplete(binding.styleName, binding.styleParent);

        if (isEditing) {
            binding.styleName.setText(attr);
            binding.styleParent.setText(style.getAttribute(attr));
        }

        dialog.b(isEditing ? "Edit attribute" : "Create new attribute");

        dialog.b(Helper.getResString(R.string.common_word_save), v1 -> {
            String attribute = Objects.requireNonNull(binding.styleName.getText()).toString();
            String value = Objects.requireNonNull(binding.styleParent.getText()).toString();

            if (attribute.isEmpty() || value.isEmpty()) {
                SketchwareUtil.toastError("Please fill in all fields");
                return;
            }

            if (!attribute.equals(attr)) style.getAttributes().remove(attr);

            style.addAttribute(attribute, value);
            attributesAdapter.submitList(new ArrayList<>(style.getAttributes().keySet()));
            attributesAdapter.notifyDataSetChanged();
        });

        dialog.a(getString(R.string.cancel), Helper.getDialogDismissListener(dialog));
        dialog.a(binding.getRoot());
        dialog.show();
    }

    public void showAttributesEditorDialog(StyleModel style) {
        aB dialog = new aB(requireActivity());
        PropertyPopupInputTextBinding binding = PropertyPopupInputTextBinding.inflate(getLayoutInflater());

        binding.edInput.setText(stylesEditorManager.getAttributesCode(style));

        dialog.b("Edit all attributes");
        dialog.b("Edit all attributes");
        dialog.b(Helper.getResString(R.string.common_word_save), v1 -> {
            try {
                Map<String, String> attributes = stylesEditorManager.convertAttributesToMap(binding.edInput.getText().toString());
                style.setAttributes(attributes);
                attributesAdapter.submitList(new ArrayList<>(attributes.keySet()));
            } catch (Exception e) {
                SketchwareUtil.toastError("Failed to parse attributes. Please check the format");
            }
        });
        dialog.a(getString(R.string.cancel), Helper.getDialogDismissListener(dialog));
        dialog.a(binding.getRoot());
        dialog.show();
    }

    public void saveStylesFile() {
        if (FileUtil.isExistFile(filePath) || !stylesList.isEmpty()) {
            FileUtil.writeFile(filePath, stylesEditorManager.convertStylesToXML(stylesList));
        }
    }

    private void setupAutoComplete(MaterialAutoCompleteTextView attrView, MaterialAutoCompleteTextView valueView) {
        String[] attributes = attributeSuggestions.ATTRIBUTE_SUGGESTIONS.toArray(new String[0]);

        ArrayAdapter<String> attrAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, attributes);
        attrView.setAdapter(attrAdapter);

        ArrayAdapter<String> valueAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, new ArrayList<>());
        valueView.setAdapter(valueAdapter);

        attrView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String attribute = s.toString().trim().toLowerCase();

                List<String> suggestions = attributeSuggestions.getSuggestions(attribute);

                if (suggestions != null && !suggestions.isEmpty()) {
                    ArrayAdapter<String> newValueAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_dropdown_item_1line, suggestions);
                    valueView.setAdapter(newValueAdapter);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

}
