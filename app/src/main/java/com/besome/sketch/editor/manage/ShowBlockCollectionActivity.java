package com.besome.sketch.editor.manage;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.editor.logic.BlockPane;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.GB;
import a.a.a.Mp;
import a.a.a.NB;
import a.a.a.Rs;
import a.a.a.Ss;
import a.a.a.Ts;
import a.a.a.bB;
import a.a.a.xB;
import mod.SketchwareUtil;
import mod.hasrat.tools.ImageFactory;
import mod.hey.studios.util.Helper;

public class ShowBlockCollectionActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private String blockName;
    private ViewBlockCollectionEditor blockCollectionEditor;
    private BlockPane pane;
    private EditText blockNameEditorText;
    private LinearLayout actionSection;
    private NB blockNameValidator;

    private void addBlocks(ArrayList<BlockBean> blocks, int someXValue, int someYValue) {
        HashMap<Integer, Rs> blockIdsWithBlocks = new HashMap<>();
        Rs firstBlock = null;

        boolean isFirstBlock = true;
        for (BlockBean blockBean : blocks) {
            Rs block = getBlock(blockBean);
            int blockId = (Integer) block.getTag();

            blockIdsWithBlocks.put(blockId, block);
            pane.g = Math.max(pane.g, blockId + 1);
            pane.a(block, someXValue, someYValue);

            if (isFirstBlock) {
                firstBlock = block;
                isFirstBlock = false;
            }
        }

        for (BlockBean blockBean : blocks) {
            Rs block = blockIdsWithBlocks.get(Integer.valueOf(blockBean.id));

            if (block != null) {
                int subStack1Id = blockBean.subStack1;
                Rs subStack1;
                if (subStack1Id >= 0 && (subStack1 = blockIdsWithBlocks.get(subStack1Id)) != null) {
                    block.e(subStack1);
                }

                int subStack2Id = blockBean.subStack2;
                Rs subStack2;
                if (subStack2Id >= 0 && (subStack2 = blockIdsWithBlocks.get(subStack2Id)) != null) {
                    block.f(subStack2);
                }

                int nextBlockId = blockBean.nextBlock;
                Rs nextBlock;
                if (nextBlockId >= 0 && (nextBlock = blockIdsWithBlocks.get(nextBlockId)) != null) {
                    block.b(nextBlock);
                }

                ArrayList<String> parameters = blockBean.parameters;
                for (int i = 0; i < parameters.size(); i++) {
                    String parameter = blockBean.parameters.get(i);

                    if (parameter != null && parameter.length() > 0) {
                        if (parameter.charAt(0) == '@') {
                            Rs parameterBlock = blockIdsWithBlocks.get(Integer.valueOf(parameter.substring(1)));
                            if (parameterBlock != null) {
                                block.a((Ts) block.V.get(i), parameterBlock);
                            }
                        } else {
                            ((Ss) block.V.get(i)).setArgValue(parameter);
                            block.m();
                        }
                    }
                }
            }
        }
        firstBlock.k();
        pane.b();
    }

    private void resizeBottomViews() {
        int i = getResources().getDisplayMetrics().heightPixels;
        actionSection.measure(0, 0);
        blockCollectionEditor.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ((i - GB.a((Context) this)) - GB.f(this)) - actionSection.getMeasuredHeight()));
        blockCollectionEditor.requestLayout();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_button && blockNameValidator.b()) {
            Mp.h().a(blockName, blockNameEditorText.getText().toString(), true);
            bB.a(getApplicationContext(), Helper.getResString(R.string.design_manager_message_edit_complete), bB.TOAST_NORMAL).show();
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resizeBottomViews();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_collection_show_block);

        Toolbar toolbar = findViewById(R.id.toolbar);
        a(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(Helper.getResString(R.string.design_manager_block_detail_actionbar_title));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

        blockName = getIntent().getStringExtra("block_name");
        blockCollectionEditor = findViewById(R.id.editor);
        blockCollectionEditor.setScrollEnabled(true);
        pane = blockCollectionEditor.getBlockPane();

        EasyDeleteEditText input = findViewById(R.id.ed_input);
        blockNameEditorText = input.getEditText();
        blockNameEditorText.setPrivateImeOptions("defaultInputmode=english;");
        blockNameEditorText.setText(blockName);
        input.setHint(Helper.getResString(R.string.design_manager_block_hint_enter_block_name));

        Button save = findViewById(R.id.save_button);
        save.setText(Helper.getResString(R.string.common_word_save));
        save.setOnClickListener(this);
        blockNameValidator = new NB(this, input.getTextInputLayout(), Mp.h().g());
        actionSection = findViewById(R.id.layout_button);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        addBlocks(Mp.h().a(blockName).blocks, 10, 10);
        resizeBottomViews();
    }

    private Rs getBlock(BlockBean blockBean) {
        return new Rs(this, Integer.parseInt(blockBean.id), blockBean.spec, blockBean.type, blockBean.typeName, blockBean.opCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem saveImageItem = menu.add(0, 12, 0, "Save image");
        saveImageItem.setIcon(R.drawable.full_image_48);
        saveImageItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 12) {
            if (ImageFactory.saveBitmap(blockCollectionEditor.getChildAt(0), blockName).exists()) {
                SketchwareUtil.toast("Saved image to /Internal storage/sketchware/saved_block/" + blockName + ".png!");
            } else {
                SketchwareUtil.toastError("Couldn't save image");
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
