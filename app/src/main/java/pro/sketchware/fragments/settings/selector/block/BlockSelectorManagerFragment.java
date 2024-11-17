package pro.sketchware.fragments.settings.selector.block;

import static mod.hey.studios.util.Helper.addBasicTextChangedListener;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import a.a.a.aB;
import a.a.a.qA;
import mod.elfilibustero.sketch.lib.ui.SketchFilePickerDialog;
import pro.sketchware.R;
import pro.sketchware.databinding.DialogBlockConfigurationBinding;
import pro.sketchware.databinding.DialogSelectorActionsBinding;
import pro.sketchware.databinding.FragmentBlockSelectorManagerBinding;
import pro.sketchware.fragments.settings.selector.block.details.BlockSelectorDetailsFragment;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class BlockSelectorManagerFragment extends qA {

    private FragmentBlockSelectorManagerBinding binding;

    private List<Selector> selectors = new ArrayList<>();
    private BlockSelectorAdapter adapter;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBlockSelectorManagerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        configureToolbar(binding.toolbar);
        handleInsetts(binding.getRoot());

        adapter = new BlockSelectorAdapter(
                (selector, index) -> openFragment(new BlockSelectorDetailsFragment(index, selectors)),
                (selector, index) -> showActionsDialog(index)
        );

        executorService.execute(() -> {
            if (FileUtil.isExistFile(BlockSelectorConsts.BLOCK_SELECTORS_FILE.getAbsolutePath())) {
                selectors = parseJson(
                        FileUtil.readFile(BlockSelectorConsts.BLOCK_SELECTORS_FILE.getAbsolutePath())
                );
            } else {
                selectors.add(
                        new Selector(
                                "Select typeview:",
                                "typeview",
                                getTypeViewList()
                        )
                );
                saveAllSelectors();
            }
            // Update UI on main thread
            requireActivity().runOnUiThread(() -> {
                binding.list.setAdapter(adapter);
                adapter.submitList(selectors);
            });
        });

        binding.createNew.setOnClickListener(v -> showCreateEditDialog(0, false));

        super.onViewCreated(view, savedInstanceState);
    }

    private List<Selector> parseJson(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Selector>>() {
        }.getType();
        return gson.fromJson(jsonString, listType);
    }

    private void showCreateEditDialog(int index, boolean isEdit) {
        DialogBlockConfigurationBinding dialogBinding = DialogBlockConfigurationBinding.inflate(LayoutInflater.from(requireContext()));
        dialogBinding.tilPalettesPath.setHint("Selector name");
        dialogBinding.tilBlocksPath.setHint("Selector title (ex: Select View:)");

        if (isEdit) {
            dialogBinding.palettesPath.setText(selectors.get(index).getName());
            dialogBinding.blocksPath.setText(selectors.get(index).getTitle());
        }

        addBasicTextChangedListener(dialogBinding.palettesPath, str -> {
            if (itemAlreadyExists(str)) {
                dialogBinding.tilPalettesPath.setError("An item with this name already exists");
            } else {
                dialogBinding.tilPalettesPath.setError(null);
            }
        });

        if ("typeview".equals(Objects.requireNonNull(dialogBinding.palettesPath.getText()).toString())) {
            dialogBinding.palettesPath.setEnabled(false);
            dialogBinding.tilPalettesPath.setOnClickListener(v -> SketchwareUtil.toast("You cannot change the name of this selector"));
        }

        aB dialog = new aB(requireActivity());
        dialog.b(!isEdit ? "New selector" : "Edit selector");
        dialog.a(dialogBinding.getRoot());
        dialog.b(!isEdit ? "Create" : "Save", v -> {
            String selectorName = dialogBinding.palettesPath.getText().toString();
            String selectorTitle = Objects.requireNonNull(dialogBinding.blocksPath.getText()).toString();

            if (selectorName.isEmpty()) {
                SketchwareUtil.toast("Please type the selector's name");
                return;
            }
            if (selectorTitle.isEmpty()) {
                SketchwareUtil.toast("Please type the selector's title");
                return;
            }
            if (!isEdit) {
                if (!itemAlreadyExists(selectorName)) {
                    selectors.add(
                            new Selector(
                                    selectorName,
                                    selectorTitle,
                                    new ArrayList<>()
                            )
                    );
                } else {
                    SketchwareUtil.toast("An item with this name already exists");
                }
            } else {
                selectors.set(index, new Selector(
                        selectorName,
                        selectorTitle,
                        selectors.get(index).getData()
                ));
            }
            saveAllSelectors();
            adapter.notifyDataSetChanged();
            dialog.dismiss();
        });
        dialog.a("Cancel", v -> dialog.dismiss());
        dialog.show();
    }

    private void showActionsDialog(int index) {
        DialogSelectorActionsBinding dialogBinding = DialogSelectorActionsBinding.inflate(LayoutInflater.from(requireContext()));
        aB dialog = new aB(requireActivity());
        dialog.setTitle("Actions");
        dialog.a(dialogBinding.getRoot());

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
            showConfirmationDialog(
                    "Are you sure you want to delete this selector?",
                    confirmDialog -> {
                        selectors.remove(index);
                        saveAllSelectors();
                        adapter.notifyDataSetChanged();
                        confirmDialog.dismiss();
                    },
                    Dialog::dismiss
            );
        });
        dialog.show();
    }

    private void showConfirmationDialog(
            String message,
            ConfirmListener onConfirm,
            CancelListener onCancel
    ) {
        aB dialog = new aB(requireActivity());
        dialog.b("Attention");
        dialog.a(message);
        dialog.b("Yes", v -> onConfirm.onConfirm(dialog));
        dialog.a("Cancel", v -> onCancel.onCancel(dialog));
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
                saveAllSelectors(
                        BlockSelectorConsts.EXPORT_FILE.getAbsolutePath(),
                        "Exported in " + BlockSelectorConsts.EXPORT_FILE.getAbsolutePath()
                );
                return true;
            }
            return false;
        });
    }

    private void showImportSelectorDialog() {
        SketchFilePickerDialog filePickerDialog = new SketchFilePickerDialog(requireActivity())
                .allowExtension("json")
                .setFilePath(FileUtil.getExternalStorageDir())
                .setOnFileSelectedListener((dialog, file) -> {
                    executorService.execute(() -> handleToImportFile(file));
                    dialog.dismiss();
                });
        filePickerDialog.setTitle("Select .json selector file");
        filePickerDialog.a(R.drawable.file_48_blue); // Custom method, need to adjust
        filePickerDialog.setOnDismissListener(filePickerDialog::backPressed);
        filePickerDialog.init();
        filePickerDialog.show();
    }

    private void saveAllSelectors() {
        saveAllSelectors(BlockSelectorConsts.BLOCK_SELECTORS_FILE.getAbsolutePath(), "Saved");
    }

    private void saveAllSelectors(String path, String message) {
        FileUtil.writeFile(
                path,
                getGson().toJson(selectors)
        );
        SketchwareUtil.toast(message);
    }

    private void exportSelector(Selector selector) {
        String path = BlockSelectorConsts.EXPORT_FILE.getAbsolutePath().replace("All_Menus", selector.getName());
        FileUtil.writeFile(
                path,
                getGson().toJson(selector)
        );
        SketchwareUtil.toast("Exported in " + path);
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
                    SketchwareUtil.toastError("Make sure you select a file that contains selector item(s).");
                }
            } else {
                List<Selector> selectorsN = getSelectorsFromFile(file);
                if (selectorsN != null) {
                    selectors.addAll(selectorsN);
                    saveAllSelectors();
                    adapter.notifyDataSetChanged();
                } else {
                    SketchwareUtil.toastError("Make sure you select a file that contains selector item(s).");
                }
            }
        } catch (Exception e) {
            Log.e(BlockSelectorConsts.TAG, e.toString());
            SketchwareUtil.toastError("Make sure you select a file that contains a selector item(s).");
        }
    }

    private Selector getSelectorFromFile(File file) {
        String json = FileUtil.readFile(file.getAbsolutePath());
        try {
            return getGson().fromJson(json, Selector.class);
        } catch (Exception e) {
            Log.e(BlockSelectorConsts.TAG, e.toString());
            SketchwareUtil.toastError("An error occurred while trying to get the selector");
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
            SketchwareUtil.toastError("An error occurred while trying to get the selectors");
            return null;
        }
    }

    private boolean isObject(String jsonString) {
        JsonElement jsonElement = JsonParser.parseString(jsonString);
        return jsonElement.isJsonObject();
    }

    private Gson getGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
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
        List<String> list = new ArrayList<>();
        list.add("View");
        list.add("ViewGroup");
        list.add("LinearLayout");
        list.add("RelativeLayout");
        list.add("ScrollView");
        list.add("HorizontalScrollView");
        list.add("TextView");
        list.add("EditText");
        list.add("Button");
        list.add("RadioButton");
        list.add("CheckBox");
        list.add("Switch");
        list.add("ImageView");
        list.add("SeekBar");
        list.add("ListView");
        list.add("Spinner");
        list.add("WebView");
        list.add("MapView");
        list.add("ProgressBar");
        return list;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    interface ConfirmListener {
        void onConfirm(aB dialog);
    }

    interface CancelListener {
        void onCancel(aB dialog);
    }
}
