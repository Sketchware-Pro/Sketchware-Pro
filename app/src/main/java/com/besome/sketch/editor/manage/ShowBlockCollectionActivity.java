package com.besome.sketch.editor.manage;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.BlockCollectionBean;
import com.besome.sketch.editor.logic.BlockPane;
import com.besome.sketch.lib.base.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.GB;
import a.a.a.Mp;
import a.a.a.NB;
import a.a.a.Rs;
import a.a.a.Ss;
import a.a.a.Ts;
import a.a.a.bB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageCollectionShowBlockBinding;
import pro.sketchware.tools.ImageFactory;
import pro.sketchware.utility.SketchwareUtil;

public class ShowBlockCollectionActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private String blockName;
    private BlockPane pane;
    private EditText blockNameEditorText;
    private NB blockNameValidator;

    private ManageCollectionShowBlockBinding binding;

    private void addBlocks(ArrayList<BlockBean> blocks) {
        HashMap<Integer, Rs> blockIdsWithBlocks = new HashMap<>();
        Rs firstBlock = null;

        boolean isFirstBlock = true;
        for (BlockBean blockBean : blocks) {
            Rs block = getBlock(blockBean);
            int blockId = (Integer) block.getTag();

            blockIdsWithBlocks.put(blockId, block);
            pane.g = Math.max(pane.g, blockId + 1);
            pane.a(block, 10, 10);

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

                    if (parameter != null && !parameter.isEmpty()) {
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
        binding.layoutButton.measure(0, 0);
        binding.editor.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ((i - GB.a((Context) this)) - GB.f(this)) - binding.layoutButton.getMeasuredHeight()));
        binding.editor.requestLayout();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_button && blockNameValidator.b()) {
            Mp.h().a(blockName, Helper.getText(blockNameEditorText), true);
            bB.a(getApplicationContext(), Helper.getResString(R.string.design_manager_message_edit_complete), bB.TOAST_NORMAL).show();
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resizeBottomViews();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageCollectionShowBlockBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(Helper.getResString(R.string.design_manager_block_detail_actionbar_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

        blockName = getIntent().getStringExtra("block_name");
        binding.editor.setScrollEnabled(true);
        pane = binding.editor.getBlockPane();

        blockNameEditorText = binding.edInput.getEditText();
        blockNameEditorText.setPrivateImeOptions("defaultInputmode=english;");
        blockNameEditorText.setText(blockName);
        binding.edInput.setHint(Helper.getResString(R.string.design_manager_block_hint_enter_block_name));

        binding.saveButton.setText(Helper.getResString(R.string.common_word_save));
        binding.saveButton.setOnClickListener(this);
        blockNameValidator = new NB(this, binding.edInput.getTextInputLayout(), Mp.h().g());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        BlockCollectionBean block = Mp.h().a(blockName);
        if (block != null) {
            addBlocks(block.blocks);
            resizeBottomViews();
        } else {
            SketchwareUtil.toastError("Can't open corrupt Block");
            finish();
        }
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 12) {
            if (ImageFactory.saveBitmap(binding.editor.getChildAt(0), blockName).exists()) {
                SketchwareUtil.toast("Saved image to /Internal storage/sketchware/saved_block/" + blockName + ".png!");
            } else {
                SketchwareUtil.toastError("Couldn't save image");
            }
        }

        return super.onOptionsItemSelected(item);
    }
}