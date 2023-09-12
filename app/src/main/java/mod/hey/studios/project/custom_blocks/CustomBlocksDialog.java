package mod.hey.studios.project.custom_blocks;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besome.sketch.beans.BlockBean;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.ViewUsedCustomBlocksBinding;

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

    public static void show(Activity context, String sc_id) {
        ViewUsedCustomBlocksBinding dialogBinding = ViewUsedCustomBlocksBinding.inflate(context.getLayoutInflater());
        var blockContainer = dialogBinding.customBlocksContainer;
        var subtitle = "You haven't used any custom blocks in this project.";

        ArrayList<BlockBean> list = new CustomBlocksManager(sc_id).getUsedBlocks();

        if (!list.isEmpty()) {
            subtitle = "You have used " + list.size() + " custom block(s) in this project.";
            for (int i = 0; i < list.size(); i++) {
                BlockBean block = list.get(i);

                dialogBinding.customBlocksContainer.addView(createBlockInfo(context, block, sc_id));
                blockContainer.addView(createBlock(context, block));

                if (i != (list.size() - 1)) {
                    blockContainer.addView(createSpace(context));
                }
            }
        }

        var dialog = new MaterialAlertDialogBuilder(context)
                .setTitle("Used Custom Blocks")
                .setMessage(subtitle)
                .setPositiveButton(R.string.common_word_close, null);

        if (!list.isEmpty())
            dialog.setView(dialogBinding.getRoot());

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