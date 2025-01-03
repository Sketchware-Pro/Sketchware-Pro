package pro.sketchware.activities.resources.editors.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Objects;

import a.a.a.XB;
import a.a.a.Zx;
import a.a.a.aB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.activities.resources.editors.ResourcesEditorActivity;
import pro.sketchware.activities.resources.editors.utils.ColorsEditorManager;
import pro.sketchware.databinding.ColorEditorAddBinding;
import pro.sketchware.databinding.ResourcesEditorFragmentBinding;
import pro.sketchware.activities.resources.editors.adapters.ColorsAdapter;
import pro.sketchware.activities.resources.editors.models.ColorModel;
import pro.sketchware.utility.PropertiesUtil;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.XmlUtil;

public class ColorsEditor extends Fragment {

    public static String contentPath;
    private final ArrayList<ColorModel> colorList = new ArrayList<>();
    private ResourcesEditorFragmentBinding binding;
    public ColorsAdapter adapter;
    private Activity activity;
    private Zx colorpicker;

    public final ColorsEditorManager colorsEditorManager = new ColorsEditorManager();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ResourcesEditorFragmentBinding.inflate(inflater, container, false);
        initialize();
        updateColorsList(contentPath);
        ((ResourcesEditorActivity) requireActivity()).checkForInvalidResources();
        return binding.getRoot();
    }

    public void updateColorsList(String contentPath) {
        colorsEditorManager.parseColorsXML(colorList, FileUtil.readFileIfExist(contentPath));
        adapter = new ColorsAdapter(colorList, (ResourcesEditorActivity) activity);
        binding.recyclerView.setAdapter(adapter);
        updateNoContentLayout();
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
        activity = requireActivity();

        contentPath = ((ResourcesEditorActivity) activity).colorsFilePath;

        colorsEditorManager.contentPath = contentPath;

        colorpicker = new Zx(activity, 0xFFFFFFFF, false, false);

        colorsEditorManager.parseColorsXML(colorList, FileUtil.readFileIfExist(contentPath));
    }

    public boolean checkForUnsavedChanges() {
        if (!FileUtil.isExistFile(contentPath) && colorList.isEmpty()) {
            return false;
        }
        String originalXml = FileUtil.readFileIfExist(contentPath);
        String newXml = colorsEditorManager.convertListToXml(colorList);
        return !Objects.equals(XmlUtil.replaceXml(newXml), XmlUtil.replaceXml(originalXml));
    }

    public void showDeleteDialog(int position) {
        aB dialog = new aB(activity);
        dialog.a(R.drawable.ic_mtrl_delete);
        dialog.b(xB.b().a(activity, R.string.color_editor_delete_color));
        dialog.a(xB.b().a(activity, R.string.picker_color_message_delete_all_custom_color));
        dialog.b(xB.b().a(activity, R.string.common_word_delete), v -> {
            colorList.remove(position);
            adapter.notifyItemRemoved(position);
            adapter.notifyItemRangeChanged(position, colorList.size());
            updateNoContentLayout();
            dialog.dismiss();
        });
        dialog.a(xB.b().a(activity, R.string.common_word_cancel), v -> dialog.dismiss());
        dialog.show();
    }

    public void showColorEditDialog(ColorModel colorModel, int position) {
        aB dialog = new aB(activity);
        ColorEditorAddBinding dialogBinding = ColorEditorAddBinding.inflate(getLayoutInflater());
        new XB(activity, dialogBinding.colorValueInputLayout, dialogBinding.colorPreview);

        if (colorModel != null) {
            dialogBinding.colorKeyInput.setText(colorModel.getColorName());
            dialogBinding.colorPreview.setBackgroundColor(PropertiesUtil.parseColor(colorsEditorManager.getColorValue(activity.getApplicationContext(), colorModel.getColorValue(), 3)));

            if (colorModel.getColorValue().startsWith("@")) {
                dialogBinding.colorValueInput.setText(colorModel.getColorValue().replace("@", ""));
                dialogBinding.hash.setText("@");
                dialogBinding.colorValueInput.setEnabled(false);
                dialogBinding.hash.setEnabled(false);
                dialogBinding.colorValueInputLayout.setError(null);
            } else {
                dialogBinding.colorValueInput.setText(colorModel.getColorValue().replace("#", ""));
                dialogBinding.hash.setText("#");
            }

            dialog.b("Edit color");

        } else {
            dialog.b("Create new color");
            dialogBinding.colorPreview.setBackgroundColor(0xFFFFFF);
        }

        dialog.b("Save", v1 -> {
            String key = Objects.requireNonNull(dialogBinding.colorKeyInput.getText()).toString();
            String value = Objects.requireNonNull(dialogBinding.colorValueInput.getText()).toString();

            if (key.isEmpty() || value.isEmpty()) {
                SketchwareUtil.toast("Please fill in all fields", Toast.LENGTH_SHORT);
                return;
            }

            if (value.startsWith("#")) {
                if (!PropertiesUtil.isHexColor(value)) {
                    SketchwareUtil.toast("Please enter a valid HEX color", Toast.LENGTH_SHORT);
                }
                return;
            }

            if (colorModel != null) {
                colorModel.setColorName(key);

                if (dialogBinding.hash.getText().equals("@")) {
                    colorModel.setColorValue("@" + value);
                } else {
                    colorModel.setColorValue("#" + value);
                }

                adapter.notifyItemChanged(position);
            } else {
                addColor(key, value);
            }
            dialog.dismiss();
        });

        dialogBinding.colorPreviewCard.setOnClickListener(v -> {
            colorpicker.a(new Zx.b() {
                @Override
                public void a(int colorInt) {
                    String selectedColorHex = "#" + String.format("%06X", colorInt & 0x00FFFFFF);
                    dialogBinding.colorPreviewCard.setCardBackgroundColor(PropertiesUtil.parseColor(selectedColorHex));
                    dialogBinding.colorValueInput.setText(selectedColorHex.replace("#", ""));
                    dialogBinding.colorValueInput.setEnabled(true);
                    dialogBinding.hash.setEnabled(true);
                    dialogBinding.hash.setText("#");
                }

                @Override
                public void a(String var1, int var2) {
                }
            });
            colorpicker.showAtLocation(v, Gravity.CENTER, 0, 0);
        });

        if (colorModel != null) {
            dialog.configureDefaultButton("Delete", v1 -> {
                colorList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, colorList.size());
                updateNoContentLayout();
                dialog.dismiss();
            });
        }

        dialog.a(getString(R.string.cancel), v1 -> dialog.dismiss());
        dialog.a(dialogBinding.getRoot());
        dialog.show();
    }

    private void addColor(String name, String value) {
        ColorModel newItem = new ColorModel(name, "#" + value);
        for (int i = 0; i < colorList.size(); i++) {
            if (colorList.get(i).getColorName().equals(name)) {
                colorList.set(i, newItem);
                adapter.notifyItemChanged(i);
                return;
            }
        }
        colorList.add(newItem);
        adapter.notifyItemInserted(colorList.size() - 1);
    }

    public void saveColorsFile() {
        if (FileUtil.isExistFile(contentPath) || !colorList.isEmpty()) {
            XmlUtil.saveXml(contentPath, colorsEditorManager.convertListToXml(colorList));
        }
    }
}
