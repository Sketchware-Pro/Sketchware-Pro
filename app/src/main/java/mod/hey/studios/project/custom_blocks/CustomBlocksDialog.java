package mod.hey.studios.project.custom_blocks;

import static android.view.View.LAYER_TYPE_HARDWARE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.lib.ui.ColorPickerDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import a.a.a.Rs;
import mod.hey.studios.editor.manage.block.ExtraBlockInfo;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;
import pro.sketchware.R;
import pro.sketchware.databinding.DialogPaletteBinding;
import pro.sketchware.databinding.ItemCustomBlockBinding;
import pro.sketchware.databinding.ViewUsedCustomBlocksBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class CustomBlocksDialog {

    private ViewUsedCustomBlocksBinding dialogBinding;
    private String sc_id;

    private CustomBlocksManager customBlocksManager;
    private ArrayList<BlockBean> customBlocks;

    public void show(Activity context, String sc_id) {
        this.sc_id = sc_id;

        dialogBinding = ViewUsedCustomBlocksBinding.inflate(context.getLayoutInflater());

        var dialog = new MaterialAlertDialogBuilder(context)
                .setTitle(Helper.getResString(R.string.used_custom_blocks))
                .setPositiveButton(Helper.getResString(R.string.common_word_import), null)
                .setNegativeButton(Helper.getResString(R.string.common_word_dismiss), (dialogInterface, which) -> dialogInterface.dismiss())
                .setView(dialogBinding.getRoot())
                .show();

        Executors.newSingleThreadExecutor().execute(() -> {
            customBlocksManager = new CustomBlocksManager(context, sc_id);
            customBlocks = customBlocksManager.getUsedBlocks();

            int missingBlocks = (int) customBlocks.stream().filter(this::isMissingBlock).count();

            if (!customBlocks.isEmpty()) {

                HashMap<Integer, Rs> preloadedBlocks = new HashMap<>();
                for (int i = 0; i < customBlocks.size(); i++) {
                    BlockBean block = customBlocks.get(i);
                    preloadedBlocks.put(i, createBlock(context, block));
                }

                context.runOnUiThread(() -> {

                    String subtitle = "You have used " + customBlocks.size() + " custom block(s) in this project.";

                    if (missingBlocks > 0) {
                        String errorMsg = (missingBlocks == 1)
                                ? "There is one missing block"
                                : "There are " + missingBlocks + " missing blocks";
                        SketchwareUtil.toastError(errorMsg);
                    }

                    dialogBinding.subtitle.setText(subtitle);
                    BlocksAdapter adapter = new BlocksAdapter(customBlocks, preloadedBlocks);
                    dialogBinding.recyclerView.setAdapter(adapter);
                    dialogBinding.progressIndicator.setVisibility(View.GONE);

                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                        ArrayList<BlockBean> selectedBeans = new ArrayList<>();
                        for (BlockBean bean : customBlocks) {
                            if (bean.isSelected) {
                                selectedBeans.add(bean);
                            }
                        }

                        if (selectedBeans.isEmpty()) {
                            SketchwareUtil.toastError("Please select at least one block to import.");
                            return;
                        }

                        importAll(context, customBlocksManager, selectedBeans);
                        dialog.dismiss();
                    });
                });

            } else {
                context.runOnUiThread(() -> {
                    dialogBinding.subtitle.setText("You haven't used any custom blocks in this project");
                    dialogBinding.progressIndicator.setVisibility(View.GONE);
                });
            }

        });
    }

    private void importAll(Context context, CustomBlocksManager customBlocksManager, ArrayList<BlockBean> list) {
        ArrayList<HashMap<String, Object>> blocksList = new ArrayList<>();
        String paletteDir = getConfigPath(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_PALETTE_FILE_PATH);
        String blocksDir = getConfigPath(ConfigActivity.SETTING_BLOCKMANAGER_DIRECTORY_BLOCK_FILE_PATH);

        ArrayList<HashMap<String, Object>> allBlocksList = loadJsonList(blocksDir);
        ArrayList<HashMap<String, Object>> paletteList = loadJsonList(paletteDir);

        if (paletteList.isEmpty()) {
            showCreatePaletteDialog(context, paletteList, paletteDir, customBlocksManager, list, blocksList, allBlocksList, blocksDir);
            return;
        }

        ArrayList<String> paletteNames = new ArrayList<>();
        for (HashMap<String, Object> map : paletteList) {
            if (map.get("name") instanceof String) {
                paletteNames.add((String) map.get("name"));
            }
        }

        AtomicInteger selectedPalette = new AtomicInteger(paletteList.size() - 1);

        new MaterialAlertDialogBuilder(context)
                .setTitle("Import Custom blocks to")
                .setSingleChoiceItems(paletteNames.toArray(new String[0]), selectedPalette.get(), (dialog, which) -> selectedPalette.set(which))
                .setNegativeButton("Create new palette", (dialog, which) -> {
                    showCreatePaletteDialog(context, paletteList, paletteDir, customBlocksManager, list, blocksList, allBlocksList, blocksDir);
                    dialog.dismiss();
                })
                .setPositiveButton("Import", (dialog, which) -> {
                    addBlocksToList(customBlocksManager, list, blocksList, selectedPalette.get() + 9);
                    allBlocksList.addAll(blocksList);
                    FileUtil.writeFile(blocksDir, new Gson().toJson(allBlocksList));
                    BlockLoader.refresh();
                    SketchwareUtil.toast("Blocks imported!");
                })
                .show();
    }

    private String getConfigPath(String settingKey) {
        return FileUtil.getExternalStorageDir() + ConfigActivity.getStringSettingValueOrSetAndGet(
                settingKey, (String) ConfigActivity.getDefaultValue(settingKey));
    }

    private ArrayList<HashMap<String, Object>> loadJsonList(String path) {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        if (FileUtil.isExistFile(path)) {
            try {
                String content = FileUtil.readFile(path);
                if (!content.isEmpty()) {
                    result = new Gson().fromJson(content, Helper.TYPE_MAP_LIST);
                }
            } catch (JsonParseException | NullPointerException ignored) {
            }
        }
        return result;
    }

    private void showCreatePaletteDialog(Context context,
                                         ArrayList<HashMap<String, Object>> paletteList,
                                         String paletteDir,
                                         CustomBlocksManager customBlocksManager,
                                         ArrayList<BlockBean> list,
                                         ArrayList<HashMap<String, Object>> blocksList,
                                         ArrayList<HashMap<String, Object>> allBlocksList,
                                         String blocksDir) {

        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context);
        dialog.setIcon(R.drawable.icon_style_white_96);
        dialog.setTitle("Create a new palette");

        DialogPaletteBinding binding = DialogPaletteBinding.inflate(((Activity) context).getLayoutInflater());

        binding.openColorPalette.setOnClickListener(v -> {
            ColorPickerDialog colorPicker = new ColorPickerDialog((Activity) context, 0, true, false);
            colorPicker.a(new ColorPickerDialog.b() {
                @Override
                public void a(int colorInt) {
                    binding.colorEditText.setText(getHexColor(colorInt));
                }

                @Override
                public void a(String var1, int var2) {
                }
            });
            colorPicker.showAtLocation(binding.openColorPalette, Gravity.CENTER, 0, 0);
        });

        dialog.setView(binding.getRoot());
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_save), (dialogInterface, which) -> {
            String name = Helper.getText(binding.nameEditText);
            String color = Helper.getText(binding.colorEditText);

            if (!validateInput(binding, name, color)) return;

            HashMap<String, Object> newPalette = new HashMap<>();
            newPalette.put("name", name);
            newPalette.put("color", color);
            paletteList.add(newPalette);
            FileUtil.writeFile(paletteDir, new Gson().toJson(paletteList));

            addBlocksToList(customBlocksManager, list, blocksList, paletteList.size() + 8);
            allBlocksList.addAll(blocksList);
            FileUtil.writeFile(blocksDir, new Gson().toJson(allBlocksList));
            BlockLoader.refresh();
            SketchwareUtil.toast("Blocks imported!");
            dialogInterface.dismiss();
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }

    private boolean isMissingBlock(@NonNull BlockBean block) {
        ExtraBlockInfo blockInfo = BlockLoader.getBlockInfo(block.opCode);
        return blockInfo.isMissing && BlockLoader.getBlockFromProject(sc_id, block.opCode).isMissing;
    }

    private boolean validateInput(DialogPaletteBinding binding, String name, String color) {
        if (name.isEmpty()) {
            binding.name.setError("Name cannot be empty");
            binding.name.requestFocus();
            return false;
        }
        if (color.isEmpty()) {
            binding.color.setError("Color cannot be empty");
            binding.color.requestFocus();
            return false;
        }
        try {
            Color.parseColor(color);
        } catch (IllegalArgumentException e) {
            binding.color.setError("Invalid hexadecimal color");
            binding.color.requestFocus();
            return false;
        }
        return true;
    }

    private void addBlocksToList(CustomBlocksManager customBlocksManager,
                                 ArrayList<BlockBean> list,
                                 ArrayList<HashMap<String, Object>> blocksList,
                                 int paletteIndex) {
        for (BlockBean block : list) {
            try {
                HashMap<String, Object> blockData = new HashMap<>();
                blockData.put("name", block.opCode);
                blockData.put("type", block.type);
                blockData.put("typeName", block.typeName);
                blockData.put("spec", block.spec);
                blockData.put("color", getHexColor(block.color));
                blockData.put("spec2", customBlocksManager.getCustomBlockSpec2(block.opCode));
                blockData.put("code", customBlocksManager.getCustomBlockCode(block.opCode));
                blockData.put("palette", String.valueOf(paletteIndex));
                blocksList.add(blockData);
            } catch (Exception ignored) {
            }
        }
    }

    private String getHexColor(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    private Rs createBlock(Context context, BlockBean blockBean) {
        Rs block = new Rs(
                context,
                Integer.parseInt(blockBean.id),
                blockBean.spec,
                blockBean.type,
                blockBean.typeName,
                blockBean.opCode
        );
        block.e = blockBean.color;
        // main reason why some blocks are not showing because Ts class is using View#LAYER_TYPE_SOFTWARE.
        // we are changing it to fix it.
        block.setLayerType(LAYER_TYPE_HARDWARE, null);
        return block;
    }

    public class BlocksAdapter extends RecyclerView.Adapter<BlocksAdapter.ViewHolder> {

        private final ArrayList<BlockBean> blockBeans;
        private final HashMap<Integer, Rs> preloadedBlocks;

        public BlocksAdapter(ArrayList<BlockBean> blockBeans,
                             HashMap<Integer, Rs> preloadedBlocks) {
            this.blockBeans = blockBeans;
            this.preloadedBlocks = preloadedBlocks;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ItemCustomBlockBinding binding = ItemCustomBlockBinding.inflate(
                    LayoutInflater.from(parent.getContext()), parent, false
            );
            return new ViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(blockBeans.get(position), position);
        }

        @Override
        public int getItemCount() {
            return blockBeans.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private final ItemCustomBlockBinding binding;

            public ViewHolder(@NonNull ItemCustomBlockBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind(@NonNull BlockBean block, int position) {
                String blockInfo = getBlockInfo(block);
                binding.tvBlockId.setText(blockInfo);
                addCustomBlockView(binding.customBlocksContainer, itemView.getContext(), block, position);
                setupCheckBox(block);
                setupClickListener(block, blockInfo);
            }

            private void addCustomBlockView(ViewGroup container, Context context, BlockBean block, int position) {
                container.removeAllViews();

                View view = preloadedBlocks.get(position);
                if (view != null) {
                    ViewParent parent = view.getParent();
                    if (parent instanceof ViewGroup) {
                        ((ViewGroup) parent).removeView(view);
                    }
                    container.addView(view);
                } else {
                    container.addView(createBlock(context, block));
                }
            }

            private void setupCheckBox(@NonNull BlockBean block) {
                boolean canImport = isBlockImportable(block);
                binding.checkBox.setEnabled(canImport);

                if (canImport) {
                    binding.checkBox.setChecked(block.isSelected);
                } else {
                    binding.checkBox.setOnCheckedChangeListener(null);
                    binding.checkBox.setChecked(false);
                }
            }

            private void setupClickListener(@NonNull BlockBean block, String blockInfo) {
                boolean canImport = isBlockImportable(block);
                binding.transparentOverlay.setOnClickListener(view -> {
                    if (canImport) {
                        boolean reversedState = !binding.checkBox.isChecked();
                        binding.checkBox.setChecked(reversedState);
                        block.isSelected = reversedState;
                    } else if (blockInfo.equals("Missing")) {
                        SketchwareUtil.toastError("This block is Missing");
                    } else {
                        SketchwareUtil.toastError("This block already exists in your collection");
                    }
                });
            }

            private boolean isBlockImportable(@NonNull BlockBean block) {
                ExtraBlockInfo blockInfo = BlockLoader.getBlockInfo(block.opCode);
                return blockInfo.isMissing && !BlockLoader.getBlockFromProject(sc_id, block.opCode).isMissing;
            }

            private String getBlockInfo(@NonNull BlockBean block) {
                ExtraBlockInfo blockInfo = BlockLoader.getBlockInfo(block.opCode);
                if (BlockLoader.getBlockFromProject(sc_id, block.opCode).isMissing && blockInfo.isMissing) {
                    return "Missing";
                }
                return block.opCode;
            }
        }
    }

}