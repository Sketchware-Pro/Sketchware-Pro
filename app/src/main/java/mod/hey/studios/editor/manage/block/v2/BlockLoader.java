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

//6.3.0

public class BlockLoader {

    private static ArrayList<ExtraBlockInfo> blocks;
    private static ArrayList<HashMap<String, Object>> palettes;

    //public static final String NOT_FOUND = "/*BLOCK NOT FOUND*/";


    static {
        loadCustomBlocks();
    }

    //NEW CLASS FOR OPTIMIZED CUSTOM BLOCK LOADING

    public static ExtraBlockInfo getBlockInfo(String block_name) {

        if (blocks == null) {
            loadCustomBlocks();
        }

        for (ExtraBlockInfo info : blocks) {
            if (info.getName().equals(block_name)) {
                return info;
            }
        }

        ExtraBlockInfo in = new ExtraBlockInfo();
        in.setName(block_name);
        //in.setCode(NOT_FOUND);
        in.isMissing = true;
        return in;
    }


    //6.3.0
    public static ExtraBlockInfo getBlockFromProject(String sc_id, String block_name) {
        File file = new File(Environment.getExternalStorageDirectory(),
                ".sketchware/data/" + sc_id + "/custom_blocks");

        if (file.exists()) {

            try {

                ArrayList<ExtraBlockInfo> infos = new Gson().fromJson(
                        FileUtil.readFile(file.getAbsolutePath()),
                        new TypeToken<ArrayList<ExtraBlockInfo>>() {
                        }.getType()
                );

                //TODO

                //   log("getBlockFromProject read success:" + new Gson().toJson(infos));

                for (ExtraBlockInfo inf : infos) {
                    if (block_name.equals(inf.getName())) {
                        return inf;
                    }
                }

            } catch (Exception e) {
                //TODO
                // log("getBlockFromProject catch:" + e.toString());
            }
        }

        ExtraBlockInfo in = new ExtraBlockInfo();
        in.setName(block_name);
        in.isMissing = true;
        return in;
    }

    public static void log(String s) {
        /*String path = new File(Environment.getExternalStorageDirectory(), ".sketchware/debug.txt").getAbsolutePath();

        FileUtil.writeFile(path,
                       FileUtil.readFile(path) + "\n" + s);*/
    }


    private static void loadCustomBlocks() {
        //ExtraBlockClassInfo.loadEBCI();

        palettes = new PaletteSelector().getPaletteSelector();

        blocks = new ArrayList<>();

        ArrayList<HashMap<String, Object>> arrList = ExtraBlockFile.getExtraBlockData();

        for (int j = 0; j < arrList.size(); j++) {
            HashMap<String, Object> map = arrList.get(j);

            if (!map.containsKey("name")) {
                continue;
            }

            ExtraBlockInfo info = new ExtraBlockInfo();

            info.setName(map.get("name").toString());

            if (map.containsKey("spec")) {
                info.setSpec(map.get("spec").toString());
            }

            if (map.containsKey("spec2")) {
                info.setSpec2(map.get("spec2").toString());
            }

            if (map.containsKey("code")) {
                info.setCode(map.get("code").toString());
            }

            if (map.containsKey("color")) {
                info.setColor(Color.parseColor(map.get("color").toString()));
            } else {
                if (!map.containsKey("palette")) {
                    continue;
                } else {
                    for (int m = 0; m < palettes.size(); m++) {
                        if (Integer.valueOf(map.get("palette").toString()) == palettes.get(m).get("index")) {
                            info.setPaletteColor((Integer) palettes.get(m).get("color"));
                        }
                    }
                }
            }

            blocks.add(info);
        }
    }

    public static void refresh() {
        loadCustomBlocks();
    }
}
