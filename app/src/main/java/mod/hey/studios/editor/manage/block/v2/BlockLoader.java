package mod.hey.studios.editor.manage.block.v2;

import android.graphics.Color;
import android.os.Environment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.aldi.sayuti.block.ExtraBlockFile;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import mod.agus.jcoderz.editor.manage.block.palette.PaletteSelector;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.editor.manage.block.ExtraBlockInfo;

public class BlockLoader {
    private static ArrayList<ExtraBlockInfo> blocks;
    private static ArrayList<HashMap<String, Object>> palettes;

    public static void log(String str) {
    }

    static {
        loadCustomBlocks();
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
        File file = new File(Environment.getExternalStorageDirectory(), new StringBuffer().append(new StringBuffer().append(".sketchware/data/").append(str).toString()).append("/custom_blocks").toString());
        if (file.exists()) {
            try {
                ArrayList<ExtraBlockInfo> arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(file.getAbsolutePath()), new TypeToken<ArrayList<ExtraBlockInfo>>() {
                }.getType());
                log(new StringBuffer().append("getBlockFromProject read success:").append(new Gson().toJson(arrayList)).toString());
                for (ExtraBlockInfo extraBlockInfo : arrayList) {
                    if (str2.equals(extraBlockInfo.getName())) {
                        return extraBlockInfo;
                    }
                }
            } catch (Exception e) {
                log(new StringBuffer().append("getBlockFromProject catch:").append(e.toString()).toString());
            }
        }
        ExtraBlockInfo extraBlockInfo2 = new ExtraBlockInfo();
        extraBlockInfo2.setName(str2);
        extraBlockInfo2.isMissing = true;
        return extraBlockInfo2;
    }

    private static void loadCustomBlocks() {
        palettes = new PaletteSelector().getPaletteSelector();
        blocks = new ArrayList<>();
        ArrayList extraBlockData = ExtraBlockFile.getExtraBlockData();
        for (int i = 0; i < extraBlockData.size(); i++) {
            HashMap hashMap = (HashMap) extraBlockData.get(i);
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
                            extraBlockInfo.setPaletteColor(((Integer) palettes.get(i2).get("color")).intValue());
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
