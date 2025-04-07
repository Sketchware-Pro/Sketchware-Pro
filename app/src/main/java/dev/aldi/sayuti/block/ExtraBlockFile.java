package dev.aldi.sayuti.block;

import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import mod.hey.studios.util.Helper;
import mod.hilal.saif.blocks.BlocksHandler;
import pro.sketchware.utility.FileUtil;

public class ExtraBlockFile {

    public static final File EXTRA_BLOCKS_DATA_FILE = new File(Environment.getExternalStorageDirectory(),
            ".sketchware/resources/block/My Block/block.json");
    public static final File EXTRA_BLOCKS_PALETTE_FILE = new File(Environment.getExternalStorageDirectory(),
            ".sketchware/resources/block/My Block/palette.json");

    public static ArrayList<HashMap<String, Object>> getExtraBlockData() {
        ArrayList<HashMap<String, Object>> extraBlocks = new Gson().fromJson(getExtraBlockFile(), Helper.TYPE_MAP_LIST);
        BlocksHandler.builtInBlocks(extraBlocks);

        return extraBlocks;
    }

    /**
     * @return Non-empty content of {@link ExtraBlockFile#EXTRA_BLOCKS_DATA_FILE},
     * as cases of <code>""</code> as file content return <code>"[]"</code>
     */
    public static String getExtraBlockFile() {
        String fileContent;

        if (EXTRA_BLOCKS_DATA_FILE.exists() && !(fileContent = FileUtil.readFile(EXTRA_BLOCKS_DATA_FILE.getAbsolutePath())).isEmpty()) {
            return fileContent;
        } else {
            return "[]";
        }
    }

    public static String getPaletteBlockFile() {
        return FileUtil.readFile(EXTRA_BLOCKS_PALETTE_FILE.getAbsolutePath());
    }

    public static String getExtraBlockJson() {
        return "[]";
    }
}
