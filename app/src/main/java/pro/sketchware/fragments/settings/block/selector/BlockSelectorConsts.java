package pro.sketchware.fragments.settings.block.selector;

import android.os.Environment;

import java.io.File;

public class BlockSelectorConsts {
    public static final File BLOCK_SELECTORS_FILE = new File(Environment.getExternalStorageDirectory(), ".sketchware/resources/block/My Block/menu.json");
    public static final File EXPORT_FILE = new File(Environment.getExternalStorageDirectory(), "/.sketchware/resources/block/export/menu/All_Menus.json");
    public static final String TAG = "BlockSelectorManager";
}