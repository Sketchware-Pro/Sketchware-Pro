package pro.sketchware.fragments.settings.selector.block

import android.os.Environment

import java.io.File

object BlockSelectorConsts {
    val BLOCK_SELECTORS_FILE = File(Environment.getExternalStorageDirectory(), ".sketchware/resources/block/My Block/menu.json")
    val EXPORT_FILE = File(Environment.getExternalStorageDirectory(), "/.sketchware/resources/block/export/menu/All_Menus.json")
    const val TAG = "BlockSelectorManager"
}