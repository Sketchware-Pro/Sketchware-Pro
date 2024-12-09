package mod.hey.studios.project.custom_blocks;

import static pro.sketchware.utility.SketchwareUtil.getDip;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besome.sketch.beans.BlockBean;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import a.a.a.Zx;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;
import pro.sketchware.R;
import pro.sketchware.databinding.DialogPaletteBinding;
import pro.sketchware.databinding.ViewUsedCustomBlocksBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import a.a.a.aB;
import a.a.a.Rs;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.ThemeUtils;

public class CustomBlocksDialog {
    public static void show(Activity context, String sc_id) {
        ViewUsedCustomBlocksBinding dialogBinding = ViewUsedCustomBlocksBinding.inflate(context.getLayoutInflater());
        var blockContainer = dialogBinding.customBlocksContainer;
        var subtitle = "You haven't used any custom blocks in this project.";

        CustomBlocksManager customBlocksManager = new CustomBlocksManager(sc_id);

        ArrayList<BlockBean> list = customBlocksManager.getUsedBlocks();

        if (!list.isEmpty()) {
            subtitle = "You have used " + list.size() + " custom block(s) in this project.";
            for (int i = 0; i < list.size(); i++) {
                BlockBean block = list.get(i);

                dialogBinding.customBlocksContainer.addView(createBlockInfo(context, block, sc_id));
                blockContainer.addView(createBlock(context, block));

                if (i != (list.size() - 1)) blockContainer.addView(createSpace(context));
            }
        }

        aB dialog = new aB(context);
        dialog.b(Helper.getResString(R.string.used_custom_blocks));

        if (list.isEmpty()) {
            dialog.a(subtitle);
        } else {
            dialog.b(Helper.getResString(R.string.common_word_import_all), v -> {
                importAll(context, customBlocksManager, list);
                dialog.dismiss();
            });
            dialog.a(dialogBinding.getRoot());
        }

        dialog.a(Helper.getResString(R.string.common_word_dismiss), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    // Helpers

    private static LinearLayout createSpace(Context c) {
        LinearLayout lin = new LinearLayout(c);
        LinearLayout.LayoutParams prm = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getDip(1));

        prm.setMargins(0, (int) getDip(10), 0, (int) getDip(0));

        lin.setLayoutParams(prm);
        lin.setBackgroundColor(Color.parseColor("#9E9E9E"));
        lin.setOrientation(LinearLayout.VERTICAL);

        return lin;
    }

    private static TextView createBlockInfo(Context c, BlockBean bean, String sc_id) {
        TextView tw = new TextView(c);
        tw.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tw.setTextColor(ThemeUtils.getColor(tw, R.attr.colorOnSurface));
        String append = "";

        if (BlockLoader.getBlockInfo(bean.opCode).isMissing /*getCode().equals(BlockLoader.NOT_FOUND)*/) {
            // This block is missing
            if (BlockLoader.getBlockFromProject(sc_id, bean.opCode).isMissing) {
                append = " (Missing)";
            } else {
                append = " (Found in data/" + sc_id + "/custom_blocks)";
            }
        }

        tw.setText(bean.opCode + append);
        tw.setPadding(
                0,
                (int) getDip(10),
                0,
                (int) getDip(10)
        );

        return tw;
    }

    private static Rs createBlock(Context c, BlockBean var1) {
        Rs var2 =
                new Rs(
                        c,
                        Integer.parseInt(var1.id),
                        var1.spec,
                        var1.type,
                        var1.typeName,
                        var1.opCode
                );
        var2.e = var1.color;

        return var2;
    }

    private static void importAll(Context context, CustomBlocksManager customBlocksManager, ArrayList<BlockBean> list) {
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
                    SketchwareUtil.toast("Blocks imported!");
                })
                .show();
    }

    private static String getConfigPath(String settingKey) {
        return FileUtil.getExternalStorageDir() + ConfigActivity.getStringSettingValueOrSetAndGet(
                settingKey, (String) ConfigActivity.getDefaultValue(settingKey));
    }

    private static ArrayList<HashMap<String, Object>> loadJsonList(String path) {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        if (FileUtil.isExistFile(path)) {
            try {
                String content = FileUtil.readFile(path);
                if (!content.isEmpty()) {
                    result = new Gson().fromJson(content, Helper.TYPE_MAP_LIST);
                }
            } catch (JsonParseException | NullPointerException ignored) {}
        }
        return result;
    }

    private static void showCreatePaletteDialog(Context context, ArrayList<HashMap<String, Object>> paletteList, String paletteDir,
                                                CustomBlocksManager customBlocksManager, ArrayList<BlockBean> list, ArrayList<HashMap<String, Object>> blocksList,
                                                ArrayList<HashMap<String, Object>> allBlocksList, String blocksDir) {

        aB dialog = new aB((Activity) context);
        dialog.a(R.drawable.icon_style_white_96);
        dialog.b("Create a new palette");

        DialogPaletteBinding binding = DialogPaletteBinding.inflate(((Activity) context).getLayoutInflater());

        binding.openColorPalette.setOnClickListener(v -> {
            Zx colorPicker = new Zx((Activity) context, 0, true, false);
            colorPicker.a(new Zx.b() {
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

        dialog.a(binding.getRoot());
        dialog.b(Helper.getResString(R.string.common_word_save), v -> {
            String name = binding.nameEditText.getText().toString();
            String color = binding.colorEditText.getText().toString();

            if (!validateInput(binding, name, color)) return;

            HashMap<String, Object> newPalette = new HashMap<>();
            newPalette.put("name", name);
            newPalette.put("color", color);
            paletteList.add(newPalette);
            FileUtil.writeFile(paletteDir, new Gson().toJson(paletteList));

            addBlocksToList(customBlocksManager, list, blocksList, paletteList.size() + 8);
            allBlocksList.addAll(blocksList);
            FileUtil.writeFile(blocksDir, new Gson().toJson(allBlocksList));
            SketchwareUtil.toast("Blocks imported!");
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private static boolean validateInput(DialogPaletteBinding binding, String name, String color) {
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

    private static void addBlocksToList(CustomBlocksManager customBlocksManager, ArrayList<BlockBean> list,
                                        ArrayList<HashMap<String, Object>> blocksList, int paletteIndex) {

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
            } catch (Exception ignored) {}
        }
    }

    private static String getHexColor(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }

}