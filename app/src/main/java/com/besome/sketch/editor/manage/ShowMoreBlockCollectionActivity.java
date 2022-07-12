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
import com.besome.sketch.beans.MoreBlockCollectionBean;
import com.besome.sketch.editor.logic.BlockPane;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.FB;
import a.a.a.GB;
import a.a.a.NB;
import a.a.a.Pp;
import a.a.a.Rs;
import a.a.a.Ss;
import a.a.a.Ts;
import a.a.a.bB;
import a.a.a.kq;
import a.a.a.xB;
import mod.SketchwareUtil;
import mod.hasrat.tools.ImageFactory;
import mod.hey.studios.util.Helper;

public class ShowMoreBlockCollectionActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private String moreBlockName;
    private ViewBlockCollectionEditor blockCollectionEditor;
    private BlockPane pane;
    private EditText moreBlockNameEditorText;
    private LinearLayout actionSection;
    private NB moreBlockNameValidator;

    private void addBlocks(ArrayList<BlockBean> blockBeans) {
        HashMap<Integer, Rs> hashMap = new HashMap<>();

        boolean isFirstBlock = true;
        for (BlockBean blockBean : blockBeans) {
            Rs block = getBlock(blockBean);
            int blockId = (Integer) block.getTag();
            hashMap.put(blockId, block);

            pane.g = Math.max(pane.g, blockId + 1);
            pane.a(block, 0, 0);

            if (isFirstBlock) {
                pane.getRoot().b(block);
                isFirstBlock = false;
            }
        }

        for (BlockBean blockBean : blockBeans) {
            Rs block = hashMap.get(Integer.valueOf(blockBean.id));

            if (block != null) {
                int subStack1Id = blockBean.subStack1;
                Rs subStack1;
                if (subStack1Id >= 0 && (subStack1 = hashMap.get(subStack1Id)) != null) {
                    block.e(subStack1);
                }

                int subStack2Id = blockBean.subStack2;
                Rs subStack2;
                if (subStack2Id >= 0 && (subStack2 = hashMap.get(subStack2Id)) != null) {
                    block.f(subStack2);
                }

                int nextBlockId = blockBean.nextBlock;
                Rs nextBlock;
                if (nextBlockId >= 0 && (nextBlock = hashMap.get(nextBlockId)) != null) {
                    block.b(nextBlock);
                }

                ArrayList<String> parameters = blockBean.parameters;
                for (int i = 0; i < parameters.size(); i++) {
                    String parameter = parameters.get(i);

                    if (parameter != null && parameter.length() > 0) {
                        if (parameter.charAt(0) == '@') {
                            Rs parameterBlock = hashMap.get(Integer.valueOf(parameter.substring(1)));
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
        pane.getRoot().k();
        pane.b();
    }

    private void addHeaderBlock(String spec) {
        Rs header = null;
        pane.a(spec, "moreBlock");

        ArrayList<String> parameterSpecs = FB.c(spec);
        int idCounter = 0;
        for (int i = 0; i < parameterSpecs.size(); i++) {
            String parameterSpec = parameterSpecs.get(i);
            if (parameterSpec.charAt(0) == '%') {
                if (parameterSpec.charAt(1) == 'b') {
                    header = new Rs(getBaseContext(), idCounter + 1, parameterSpec.substring(3), "b", "getArg");
                } else if (parameterSpec.charAt(1) == 'd') {
                    header = new Rs(getBaseContext(), idCounter + 1, parameterSpec.substring(3), "d", "getArg");
                } else if (parameterSpec.charAt(1) == 's') {
                    header = new Rs(getBaseContext(), idCounter + 1, parameterSpec.substring(3), "s", "getArg");
                } else if (parameterSpec.charAt(1) == 'm') {
                    String parameterName = parameterSpec.substring(parameterSpec.lastIndexOf(".") + 1);
                    String parameterType = parameterSpec.substring(parameterSpec.indexOf(".") + 1, parameterSpec.lastIndexOf("."));
                    header = new Rs(getBaseContext(), idCounter + 1, parameterName, kq.a(parameterType), kq.b(parameterType), "getArg");
                }

                header.setBlockType(1);
                pane.addView(header);
                pane.getRoot().a((Ts) pane.getRoot().V.get(idCounter), header);
                idCounter++;
            }
        }
        pane.getRoot().k();
    }

    private void resizeBottomViews() {
        int height = getResources().getDisplayMetrics().heightPixels;
        actionSection.measure(0, 0);
        blockCollectionEditor.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ((height - GB.a((Context) this)) - GB.f(this)) - actionSection.getMeasuredHeight()));
        blockCollectionEditor.requestLayout();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.save_button && moreBlockNameValidator.b()) {
            Pp.h().a(moreBlockName, moreBlockNameEditorText.getText().toString(), true);
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

        moreBlockName = getIntent().getStringExtra("block_name");
        blockCollectionEditor = findViewById(R.id.editor);
        blockCollectionEditor.setScrollEnabled(true);
        pane = blockCollectionEditor.getBlockPane();

        EasyDeleteEditText input = findViewById(R.id.ed_input);
        moreBlockNameEditorText = input.getEditText();
        moreBlockNameEditorText.setPrivateImeOptions("defaultInputmode=english;");
        moreBlockNameEditorText.setText(moreBlockName);
        input.setHint(Helper.getResString(R.string.design_manager_block_hint_enter_block_name));

        Button save = findViewById(R.id.save_button);
        save.setText(Helper.getResString(R.string.common_word_save));
        save.setOnClickListener(this);
        moreBlockNameValidator = new NB(this, input.getTextInputLayout(), Pp.h().g());
        actionSection = findViewById(R.id.layout_button);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        MoreBlockCollectionBean moreBlock = Pp.h().a(moreBlockName);
        addHeaderBlock(moreBlock.spec);
        addBlocks(moreBlock.blocks);
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
            if (ImageFactory.saveBitmap(blockCollectionEditor.getChildAt(0), moreBlockName).exists()) {
                SketchwareUtil.toast("Saved image to /Internal storage/sketchware/saved_block/" + moreBlockName + ".png!");
            } else {
                SketchwareUtil.toastError("Couldn't save image");
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
