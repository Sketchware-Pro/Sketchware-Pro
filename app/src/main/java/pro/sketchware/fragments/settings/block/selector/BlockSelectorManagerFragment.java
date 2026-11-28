package pro.sketchware.fragments.settings.block.selector;

import static mod.hey.studios.util.Helper.addBasicTextChangedListener;
import static pro.sketchware.utility.GsonUtils.getGson;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import a.a.a.qA;
import dev.pranav.filepicker.FilePickerCallback;
import dev.pranav.filepicker.FilePickerDialogFragment;
import dev.pranav.filepicker.FilePickerOptions;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.DialogBlockConfigurationBinding;
import pro.sketchware.databinding.DialogSelectorActionsBinding;
import pro.sketchware.databinding.FragmentBlockSelectorManagerBinding;
import pro.sketchware.fragments.settings.block.selector.details.BlockSelectorDetailsFragment;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class BlockSelectorManagerFragment extends qA {
    private FragmentBlockSelectorManagerBinding binding;
    private ArrayList<Selector> selectors = new ArrayList<>();
    private BlockSelectorAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBlockSelectorManagerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureToolbar(binding.toolbar);

        adapter = new BlockSelectorAdapter((selector, index) -> openFragment(BlockSelectorDetailsFragment.newInstance(index, selectors)), (selector, index) -> showActionsDialog(index));

        if (FileUtil.isExistFile(BlockSelectorConsts.BLOCK_SELECTORS_FILE.getAbsolutePath())) {
            selectors = parseJson(FileUtil.readFile(BlockSelectorConsts.BLOCK_SELECTORS_FILE.getAbsolutePath()));
        } else {
            selectors.add(new Selector("Select typeview:", "typeview", getTypeViewList()));
            saveAllSelectors();
        }

        binding.list.setAdapter(adapter);
        adapter.submitList(selectors);

        binding.createNew.setOnClickListener(v -> showCreateEditDialog(0, false));

        {
            View view1 = binding.appBarLayout;
            int left = view1.getPaddingLeft();
            int top = view1.getPaddingTop();
            int right = view1.getPaddingRight();
            int bottom = view1.getPaddingBottom();

            ViewCompat.setOnApplyWindowInsetsListener(view1, (v, i) -> {
                Insets insets = i.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
                v.setPadding(left + insets.left, top + insets.top, right + insets.right, bottom + insets.bottom);
                return i;
            });
        }

        {
            View view1 = binding.content;
            int left = view1.getPaddingLeft();
            int top = view1.getPaddingTop();
            int right = view1.getPaddingRight();
            int bottom = view1.getPaddingBottom();

            ViewCompat.setOnApplyWindowInsetsListener(view1, (v, i) -> {
                Insets insets = i.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
                v.setPadding(left + insets.left, top, right + insets.right, bottom + insets.bottom);
                return i;
            });
        }

        {
            View view1 = binding.createNew;
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view1.getLayoutParams();
            int end = lp.getMarginEnd();
            int bottom = lp.bottomMargin;

            ViewCompat.setOnApplyWindowInsetsListener(view1, (v, i) -> {
                Insets insets = i.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
                lp.setMarginEnd(end + insets.right);
                lp.bottomMargin = bottom + insets.bottom;
                v.setLayoutParams(lp);
                return i;
            });
        }
    }

    private ArrayList<Selector> parseJson(String jsonString) {
        Type listType = new TypeToken<ArrayList<Selector>>() {
        }.getType();
        return getGson().fromJson(jsonString, listType);
    }

    private void showCreateEditDialog(int index, boolean isEdit) {
        DialogBlockConfigurationBinding dialogBinding = DialogBlockConfigurationBinding.inflate(LayoutInflater.from(requireContext()));
        dialogBinding.tilPalettesPath.setHint(Helper.getResString(R.string.common_word_selector_name));
        dialogBinding.tilBlocksPath.setHint(Helper.getResString(R.string.block_selector_selector_title_hint));

        if (isEdit) {
            dialogBinding.palettesPath.setText(selectors.get(index).getName());
            dialogBinding.blocksPath.setText(selectors.get(index).getTitle());
        }

        addBasicTextChangedListener(dialogBinding.palettesPath, str -> {
            if (itemAlreadyExists(str)) {
                dialogBinding.tilPalettesPath.setError(Helper.getResString(R.string.block_selector_item_name_exists));
            } else {
                dialogBinding.tilPalettesPath.setError(null);
            }
        });

        if ("typeview".equals(Objects.requireNonNull(dialogBinding.palettesPath.getText()).toString())) {
            dialogBinding.palettesPath.setEnabled(false);
            dialogBinding.tilPalettesPath.setOnClickListener(v -> SketchwareUtil.toast(Helper.getResString(R.string.block_selector_cannot_rename_selector)));
        }

        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireActivity());
        dialog.setTitle(!isEdit ? R.string.block_selector_new_selector : R.string.block_selector_edit_selector);
        dialog.setView(dialogBinding.getRoot());
        dialog.setPositiveButton(!isEdit ? R.string.common_word_create : R.string.common_word_save, (v, which) -> {
            String selectorName = Helper.getText(dialogBinding.palettesPath);
            String selectorTitle = Objects.requireNonNull(dialogBinding.blocksPath.getText()).toString();

            if (selectorName.isEmpty()) {
                SketchwareUtil.toast(Helper.getResString(R.string.block_selector_type_selector_name));
                return;
            }
            if (selectorTitle.isEmpty()) {
                SketchwareUtil.toast(Helper.getResString(R.string.block_selector_type_selector_title));
                return;
            }
            if (!isEdit) {
                if (!itemAlreadyExists(selectorName)) {
                    selectors.add(new Selector(selectorTitle, selectorName, new ArrayList<>()));
                } else {
                    SketchwareUtil.toast(Helper.getResString(R.string.block_selector_item_name_exists));
                }
            } else {
                selectors.set(index, new Selector(selectorTitle, selectorName, selectors.get(index).getData()));
            }
            saveAllSelectors();
            adapter.notifyDataSetChanged();
            v.dismiss();
        });
        dialog.setNegativeButton(R.string.common_word_cancel, (v, which) -> v.dismiss());
        dialog.show();
    }

    private void showActionsDialog(int index) {
        DialogSelectorActionsBinding dialogBinding = DialogSelectorActionsBinding.inflate(LayoutInflater.from(requireContext()));
        AlertDialog dialog = new MaterialAlertDialogBuilder(requireActivity()).create();
        dialog.setTitle(R.string.common_word_actions);
        dialog.setView(dialogBinding.getRoot());

        dialogBinding.edit.setOnClickListener(v -> {
            dialog.dismiss();
            showCreateEditDialog(index, true);
        });
        dialogBinding.export.setOnClickListener(v -> {
            dialog.dismiss();
            exportSelector(selectors.get(index));
        });
        if ("typeview".equals(selectors.get(index).getName())) {
            dialogBinding.delete.setVisibility(View.GONE);
        }
        dialogBinding.delete.setOnClickListener(v -> {
            dialog.dismiss();
            showConfirmationDialog(Helper.getResString(R.string.block_selector_confirm_delete_selector), confirmDialog -> {
                selectors.remove(index);
                saveAllSelectors();
                adapter.notifyDataSetChanged();
                confirmDialog.dismiss();
            }, DialogInterface::dismiss);
        });
        dialog.show();
    }

    private void showConfirmationDialog(String message, ConfirmListener onConfirm, CancelListener onCancel) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireActivity());
        dialog.setTitle(R.string.common_word_attention);
        dialog.setMessage(message);
        dialog.setPositiveButton(R.string.common_word_yes, (v, which) -> onConfirm.onConfirm(v));
        dialog.setNegativeButton(R.string.common_word_cancel, (v, which) -> onCancel.onCancel(v));
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void configureToolbar(MaterialToolbar toolbar) {
        super.configureToolbar(toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.import_block_selector_menus) {
                showImportSelectorDialog();
                return true;
            } else if (item.getItemId() == R.id.export_all_block_selector_menus) {
                saveAllSelectors(BlockSelectorConsts.EXPORT_FILE.getAbsolutePath(), Helper.getResString(R.string.block_selector_exported_in, BlockSelectorConsts.EXPORT_FILE.getAbsolutePath()));
                return true;
            }
            return false;
        });
    }

    private void showImportSelectorDialog() {
        FilePickerOptions options = new FilePickerOptions();
        options.setTitle(Helper.getResString(R.string.block_selector_select_json_selector_file));
        options.setExtensions(new String[]{"json"});

        FilePickerCallback callback = new FilePickerCallback() {
            @Override
            public void onFileSelected(@NonNull File file) {
                handleToImportFile(file);
            }

        };

        FilePickerDialogFragment pickerDialog = new FilePickerDialogFragment(options, callback);

        pickerDialog.show(getChildFragmentManager(), "file_picker_dialog");
    }

    private void saveAllSelectors() {
        saveAllSelectors(BlockSelectorConsts.BLOCK_SELECTORS_FILE.getAbsolutePath(), Helper.getResString(R.string.common_word_saved));
    }

    private void saveAllSelectors(String path, String message) {
        FileUtil.writeFile(path, getGson().toJson(selectors));
        SketchwareUtil.toast(message);
    }

    private void exportSelector(Selector selector) {
        String path = BlockSelectorConsts.EXPORT_FILE.getAbsolutePath().replace("All_Menus", selector.getName());
        FileUtil.writeFile(path, getGson().toJson(selector));
        SketchwareUtil.toast(Helper.getResString(R.string.block_selector_exported_in, path));
    }

    private void handleToImportFile(File file) {
        try {
            String json = FileUtil.readFile(file.getAbsolutePath());
            if (isObject(json)) {
                Selector selector = getSelectorFromFile(file);
                if (selector != null) {
                    selectors.add(selector);
                    saveAllSelectors();
                    adapter.notifyDataSetChanged();
                } else {
                    SketchwareUtil.toastError(Helper.getResString(R.string.block_selector_select_file_with_selector_items));
                }
            } else {
                List<Selector> selectorsN = getSelectorsFromFile(file);
                if (selectorsN != null) {
                    selectors.addAll(selectorsN);
                    saveAllSelectors();
                    adapter.notifyDataSetChanged();
                } else {
                    SketchwareUtil.toastError(Helper.getResString(R.string.block_selector_select_file_with_selector_items));
                }
            }
        } catch (Exception e) {
            Log.e(BlockSelectorConsts.TAG, e.toString());
            SketchwareUtil.toastError(Helper.getResString(R.string.block_selector_select_file_with_selector_items));
        }
    }

    private Selector getSelectorFromFile(File file) {
        String json = FileUtil.readFile(file.getAbsolutePath());
        try {
            return getGson().fromJson(json, Selector.class);
        } catch (Exception e) {
            Log.e(BlockSelectorConsts.TAG, e.toString());
            SketchwareUtil.toastError(Helper.getResString(R.string.block_selector_error_getting_selector));
            return null;
        }
    }

    private List<Selector> getSelectorsFromFile(File file) {
        String json = FileUtil.readFile(file.getAbsolutePath());
        Type itemListType = new TypeToken<List<Selector>>() {
        }.getType();
        try {
            return getGson().fromJson(json, itemListType);
        } catch (Exception e) {
            Log.e(BlockSelectorConsts.TAG, e.toString());
            SketchwareUtil.toastError(Helper.getResString(R.string.block_selector_error_getting_selectors));
            return null;
        }
    }

    private boolean isObject(String jsonString) {
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        return jsonElement.isJsonObject();
    }

    private boolean itemAlreadyExists(String toCompare) {
        for (Selector selector : selectors) {
            if (selector.getName().equalsIgnoreCase(toCompare)) {
                return true;
            }
        }
        return false;
    }

    private List<String> getTypeViewList() {
        return List.of("View", "ViewGroup", "LinearLayout", "RelativeLayout", "ScrollView", "HorizontalScrollView", "TextView", "EditText", "Button", "RadioButton", "CheckBox", "Switch", "ImageView", "SeekBar", "ListView", "Spinner", "WebView", "MapView", "ProgressBar");
    }

    interface ConfirmListener {
        void onConfirm(DialogInterface dialog);
    }

    interface CancelListener {
        void onCancel(DialogInterface dialog);
    }
}
