package mod.hey.studios.editor.manage.block.v2;

import android.graphics.Color;
import android.os.Environment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import dev.aldi.sayuti.block.ExtraBlockFile;
import mod.agus.jcoderz.editor.manage.block.palette.PaletteSelector;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.editor.manage.block.ExtraBlockInfo;

public class BlockLoader {
    private static ArrayList<ExtraBlockInfo> blocks;

    static {
        loadCustomBlocks();
    }

    public static void log(String str) {
        // May be used for debugging
    }

    public static ExtraBlockInfo getBlockInfo(String str) {
        if (blocks == null) {
            loadCustomBlocks();
        }

        for (ExtraBlockInfo extraBlockInfo : blocks) {
            if (extraBlockInfo.getName().equals(str)) {
                return extraBlockInfo;
            }
        }

        ExtraBlockInfo extraBlockInfo2 = new ExtraBlockInfo();
        extraBlockInfo2.setName(str);
        extraBlockInfo2.isMissing = true;
        return extraBlockInfo2;
    }

    public static ExtraBlockInfo getBlockFromProject(String str, String str2) {
        File file = new File(Environment.getExternalStorageDirectory(), ".sketchware/data/" + str + "/custom_blocks");

        if (file.exists()) {
            try {
                ArrayList<ExtraBlockInfo> arrayList = new Gson().fromJson(FileUtil.readFile(file.getAbsolutePath()), new TypeToken<ArrayList<ExtraBlockInfo>>() {}.getType());
                log("getBlockFromProject read success:" + new Gson().toJson(arrayList));

                for (ExtraBlockInfo extraBlockInfo : arrayList) {
                    if (str2.equals(extraBlockInfo.getName())) {
                        return extraBlockInfo;

                    }
                }
            } catch (Exception e) {
                log("getBlockFromProject catch:" + e.toString());
            }
        }

        ExtraBlockInfo extraBlockInfo2 = new ExtraBlockInfo();
        extraBlockInfo2.setName(str2);
        extraBlockInfo2.isMissing = true;

        return extraBlockInfo2;
    }

    private static void loadCustomBlocks() {
        ArrayList<HashMap<String, Object>> palettes = new PaletteSelector().getPaletteSelector();
        blocks = new ArrayList<>();

        ArrayList<HashMap<String, Object>> extraBlockData = ExtraBlockFile.getExtraBlockData();
        for (int i = 0; i < extraBlockData.size(); i++) {
            HashMap<String, Object> hashMap = extraBlockData.get(i);

            if (hashMap.containsKey("name")) {
                ExtraBlockInfo extraBlockInfo = new ExtraBlockInfo();
                extraBlockInfo.setName(hashMap.get("name").toString());

                if (hashMap.containsKey("spec")) {
                    extraBlockInfo.setSpec(hashMap.get("spec").toString());
                }

                if (hashMap.containsKey("spec2")) {
                    extraBlockInfo.setSpec2(hashMap.get("spec2").toString());
                }

                if (hashMap.containsKey("code")) {
                    extraBlockInfo.setCode(hashMap.get("code").toString());
                }

                if (hashMap.containsKey("color")) {
                    extraBlockInfo.setColor(Color.parseColor(hashMap.get("color").toString()));
                } else if (hashMap.containsKey("palette")) {
                    for (int i2 = 0; i2 < palettes.size(); i2++) {
                        if (Integer.valueOf(hashMap.get("palette").toString()) == palettes.get(i2).get("index")) {
                            extraBlockInfo.setPaletteColor((Integer) palettes.get(i2).get("color"));
                        }
                    }
                }

                blocks.add(extraBlockInfo);
            }
        }
    }

    public static void refresh() {
        loadCustomBlocks();
    }
}
