package pro.sketchware.activities.resourceseditor.components.fragments;

import static pro.sketchware.utility.UI.animateLayoutChanges;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.besome.sketch.lib.ui.ColorPickerDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

import mod.hey.studios.util.Helper;
import mod.hey.studios.util.ProjectFile;
import pro.sketchware.R;
import pro.sketchware.activities.resourceseditor.ResourcesEditorActivity;
import pro.sketchware.activities.resourceseditor.components.adapters.ColorsAdapter;
import pro.sketchware.activities.resourceseditor.components.models.ColorModel;
import pro.sketchware.activities.resourceseditor.components.utils.ColorsEditorManager;
import pro.sketchware.databinding.ColorEditorAddBinding;
import pro.sketchware.databinding.ResourcesEditorFragmentBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.PropertiesUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.XmlUtil;

public class ColorsEditor extends Fragment {

    public static String contentPath;
    public final ArrayList<ColorModel> colorList = new ArrayList<>();
    public final HashMap<String, String> defaultColors = new HashMap<>();
    private final ResourcesEditorActivity activity;
    public ColorsAdapter adapter;
    public boolean hasUnsavedChanges;
    public ColorsEditorManager colorsEditorManager;
    private ResourcesEditorFragmentBinding binding;
    private boolean isNightVariant;
    private HashMap<Integer, String> notesMap = new HashMap<>();

    public ColorsEditor(ResourcesEditorActivity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ResourcesEditorFragmentBinding.inflate(inflater, container, false);
        initialize();
        return binding.getRoot();
    }

    public void updateColorsList(String filePath, int updateMode, boolean hasUnsavedChangesStatus) {
        hasUnsavedChanges = hasUnsavedChangesStatus;
        contentPath = filePath;
        colorsEditorManager.contentPath = contentPath;

        boolean isSkippingMode = updateMode == 1;
        boolean isMergeAndReplace = updateMode == 2;
        colorsEditorManager.isDefaultVariant = activity.variant.isEmpty();
        isNightVariant = activity.variant.contains("night");

        ArrayList<ColorModel> defaultColors = new ArrayList<>();

        if ((activity.variant.isEmpty() || hasUnsavedChanges) && !FileUtil.isExistFile(contentPath)) {
            String generatedContent = activity.yq.getXMLColor();
            colorsEditorManager.parseColorsXML(defaultColors, generatedContent);
        } else {
            colorsEditorManager.parseColorsXML(defaultColors, FileUtil.readFileIfExist(contentPath));
        }
        notesMap = new HashMap<>(colorsEditorManager.notesMap);

        if (isSkippingMode) {
            HashSet<String> existingColorNames = new HashSet<>();
            for (ColorModel existingModel : colorList) {
                existingColorNames.add(existingModel.getColorName());
            }

            for (ColorModel colorModel : defaultColors) {
                if (!existingColorNames.contains(colorModel.getColorName())) {
                    colorList.add(colorModel);
                }
            }
        } else {
            if (isMergeAndReplace) {
                HashSet<String> newColorNames = new HashSet<>();
                for (ColorModel color : defaultColors) {
                    newColorNames.add(color.getColorName());
                }

                colorList.removeIf(existingColor -> newColorNames.contains(existingColor.getColorName()));
            } else {
                colorList.clear();
            }
            colorList.addAll(defaultColors);
        }

        activity.runOnUiThread(() -> {
            adapter = new ColorsAdapter(colorsEditorManager, colorList, activity, notesMap);
            binding.recyclerView.setAdapter(adapter);
            activity.checkForInvalidResources();
            updateNoContentLayout();
            if (hasUnsavedChanges) {
                contentPath = activity.colorsFilePath;
            }
        });
    }

    private void updateNoContentLayout() {
        if (colorList.isEmpty()) {
            binding.noContentLayout.setVisibility(View.VISIBLE);
            binding.noContentTitle.setText(String.format(Helper.getResString(R.string.resource_manager_no_list_title), "Color"));
            binding.noContentBody.setText(String.format(Helper.getResString(R.string.resource_manager_no_list_body), "color"));
        } else {
            binding.noContentLayout.setVisibility(View.GONE);
        }
    }

    private void initialize() {

        defaultColors.put("colorAccent", ProjectFile.COLOR_ACCENT);
        defaultColors.put("colorPrimary", ProjectFile.COLOR_PRIMARY);
        defaultColors.put("colorPrimaryDark", ProjectFile.COLOR_PRIMARY_DARK);
        defaultColors.put("colorControlHighlight", ProjectFile.COLOR_CONTROL_HIGHLIGHT);
        defaultColors.put("colorControlNormal", ProjectFile.COLOR_CONTROL_NORMAL);

        colorsEditorManager = new ColorsEditorManager();
        colorsEditorManager.defaultColors = defaultColors;
    }

    public void showColorEditDialog(ColorModel colorModel, int position) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(activity);
        ColorEditorAddBinding dialogBinding = ColorEditorAddBinding.inflate(getLayoutInflater());

        if (colorModel != null) {
            dialogBinding.colorKeyInput.setText(colorModel.getColorName());
            dialogBinding.colorValueInput.setText(colorModel.getColorValue());
            if (defaultColors.containsKey(colorModel.getColorName())) {
                dialogBinding.colorKeyInput.setEnabled(false);
            }
            dialogBinding.stringHeaderInput.setText(notesMap.getOrDefault(position, ""));
            dialogBinding.colorPreview.setBackgroundColor(PropertiesUtil.parseColor(colorsEditorManager.getColorValue(activity.getApplicationContext(), colorModel.getColorValue(), 3, isNightVariant)));
            dialogBinding.importantNote.setVisibility(defaultColors.containsKey(colorModel.getColorName()) ? View.VISIBLE : View.GONE);

            dialog.setTitle("Edit color");

        } else {
            dialog.setTitle("Create new color");
            dialogBinding.colorPreview.setBackgroundColor(0xFFFFFF);
        }

        dialogBinding.importantNote.setOnClickListener(view -> {
            animateLayoutChanges(dialogBinding.getRoot());
            dialogBinding.imgColorGuide.setVisibility(dialogBinding.imgColorGuide.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        dialogBinding.colorValueInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString();
                if (value.startsWith("#") && !PropertiesUtil.isHexColor(value)) {
                    dialogBinding.colorValueInputLayout.setError("Invalid HEX color");
                    return;
                }
                dialogBinding.colorValueInput.setError(null);
                dialogBinding.colorPreview.setBackgroundColor(PropertiesUtil.parseColor(colorsEditorManager.getColorValue(activity.getApplicationContext(), s.toString(), 3, isNightVariant)));
            }
        });

        dialogBinding.colorKeyInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                animateLayoutChanges(dialogBinding.getRoot());
                dialogBinding.importantNote.setVisibility(defaultColors.containsKey(s.toString()) ? View.VISIBLE : View.GONE);
            }
        });

        dialog.setPositiveButton("Save", (v1, which) -> {
            String key = Objects.requireNonNull(dialogBinding.colorKeyInput.getText()).toString();
            String value = Objects.requireNonNull(dialogBinding.colorValueInput.getText()).toString();

            if (key.isEmpty() || value.isEmpty()) {
                SketchwareUtil.toastError("Please fill in all fields", Toast.LENGTH_SHORT);
                return;
            }

            if (value.startsWith("#")) {
                if (!PropertiesUtil.isHexColor(value)) {
                    SketchwareUtil.toastError("Please enter a valid HEX color");
                    return;
                }
            }

            if (colorModel != null) {
                colorModel.setColorName(key);
                colorModel.setColorValue(value);

                String headerInput = Objects.requireNonNull(dialogBinding.stringHeaderInput.getText()).toString();
                if (headerInput.isEmpty()) {
                    notesMap.remove(position);
                } else {
                    notesMap.put(position, headerInput);
                }
                adapter.notifyItemChanged(position);

            } else {
                addColor(key, value, Objects.requireNonNull(dialogBinding.stringHeaderInput.getText()).toString());
            }
            hasUnsavedChanges = true;
            updateNoContentLayout();
        });

        dialogBinding.colorPreviewCard.setOnClickListener(v -> {
            ColorPickerDialog colorPicker = new ColorPickerDialog(activity, Objects.requireNonNull(dialogBinding.colorValueInput.getText()).toString(), false, false, activity.sc_id);
            colorPicker.a(new ColorPickerDialog.b() {
                @Override
                public void a(int colorInt) {
                    String selectedColorHex = String.format("#%06X", colorInt & 0x00FFFFFF);
                    dialogBinding.colorPreviewCard.setCardBackgroundColor(PropertiesUtil.parseColor(selectedColorHex));
                    dialogBinding.colorValueInput.setText(selectedColorHex);
                }

                @Override
                public void a(String var1, int var2) {
                }
            });
            colorPicker.materialColorAttr((attr, attrColor) -> dialogBinding.colorValueInput.setText("?" + attr));
            colorPicker.showAtLocation(v, Gravity.CENTER, 0, 0);
        });

        if (colorModel != null && !defaultColors.containsKey(colorModel.getColorName())) {
            dialog.setNeutralButton("Delete", (v1, which) -> {
                colorList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, colorList.size());
                updateNoContentLayout();
                hasUnsavedChanges = true;
                v1.dismiss();
            });
        }

        dialog.setNegativeButton(getString(R.string.cancel), null);
        dialog.setView(dialogBinding.getRoot());
        dialog.show();
    }

    private void addColor(String name, String value, String note) {
        ColorModel newItem = new ColorModel(name, value);
        for (int i = 0; i < colorList.size(); i++) {
            if (colorList.get(i).getColorName().equals(name)) {
                SketchwareUtil.toastError("\"" + name + "\" is already exist");
                return;
            }
        }
        colorList.add(newItem);
        int notifyPosition = colorList.size() - 1;
        if (!note.isEmpty()) {
            notesMap.put(notifyPosition, note);
        }
        adapter.notifyItemInserted(notifyPosition);
        SketchwareUtil.toast(Helper.getResString(R.string.common_word_saved));
    }

    public void saveColorsFile() {
        if (hasUnsavedChanges) {
            XmlUtil.saveXml(contentPath, colorsEditorManager.convertListToXml(colorList, notesMap));
            hasUnsavedChanges = false;
        }
    }
}
