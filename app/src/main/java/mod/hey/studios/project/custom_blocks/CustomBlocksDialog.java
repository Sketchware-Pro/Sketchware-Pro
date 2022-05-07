package mod.hey.studios.project.custom_blocks;

import static mod.SketchwareUtil.getDip;

import android.app.AlertDialog;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.besome.sketch.beans.BlockBean;
import com.sketchware.remod.R;

import java.util.ArrayList;

import a.a.a.Rs;
import mod.hey.studios.editor.manage.block.v2.BlockLoader;

public class CustomBlocksDialog {

    /* Note by Hey! Studios DEV */
    /*
     i had this idea to create a dialog where one can see the custom blocks they used in their project.
     its actually completed, but for some reason i havent released it publicly. will probably do in 6.3.0.
     //16.12.2020 note: i implemented it.
     */

    public static void show(Context c, String sc_id) {
        ScrollView background = new ScrollView(c);

        background.setLayoutParams(new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        background.setPadding(
                (int) getDip(24),
                (int) getDip(8),
                (int) getDip(24),
                0
        );

        LinearLayout blockContainer = new LinearLayout(c);
        blockContainer.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        blockContainer.setOrientation(LinearLayout.VERTICAL);

        background.addView(blockContainer);

        ArrayList<BlockBean> list = new CustomBlocksManager(sc_id).getUsedBlocks();

        if (list.isEmpty()) {
            TextView noteNone = new TextView(c);
            noteNone.setText("None");
            noteNone.setTextColor(0xff000000);
            noteNone.setTextSize(14f);

            blockContainer.addView(noteNone);

        } else {
            for (int i = 0; i < list.size(); i++) {
                BlockBean block = list.get(i);

                blockContainer.addView(createBlockInfo(c, block, sc_id));
                blockContainer.addView(createBlock(c, block));

                if (i != (list.size() - 1)) {
                    blockContainer.addView(createSpace(c));
                }
            }
        }

        new AlertDialog.Builder(c)
                .setTitle("Used Custom Blocks")
                .setPositiveButton(R.string.common_word_close, null)
                .setView(background)
                .show();
    }

    // Helpers

    private static LinearLayout createSpace(Context c) {
        LinearLayout lin = new LinearLayout(c);
        LinearLayout.LayoutParams prm = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (int) getDip(1));

        prm.setMargins(
                0,
                (int) getDip(20),
                0,
                (int) getDip(10)
        );

        lin.setLayoutParams(prm);
        lin.setBackgroundColor(0xff9e9e9e);
        lin.setOrientation(LinearLayout.VERTICAL);

        return lin;
    }

    private static TextView createBlockInfo(Context c, BlockBean bean, String sc_id) {
        TextView tw = new TextView(c);
        tw.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        tw.setTextColor(0xff424242);
        tw.setTextSize(14f);

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
}