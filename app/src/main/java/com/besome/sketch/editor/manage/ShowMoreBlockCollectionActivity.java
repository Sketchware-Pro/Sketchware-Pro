package com.besome.sketch.editor.manage;

import android.annotation.SuppressLint;
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
import mod.hey.studios.util.Helper;
import mod.w3wide.tools.ImageFactory;

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
            Rs block = a(blockBean);
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
                ((height - GB.a((Context) this)) - GB.f((Context) this)) - actionSection.getMeasuredHeight()));
        blockCollectionEditor.requestLayout();
    }

    @SuppressLint("ResourceType")
    public void onClick(View v) {
        if (v.getId() == 2131231681 && moreBlockNameValidator.b()) {
            Pp.h().a(moreBlockName, moreBlockNameEditorText.getText().toString(), true);
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625279), 0).show();
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resizeBottomViews();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427512);
        Toolbar toolbar = findViewById(2131231847);
        a(toolbar);
        findViewById(2131231370).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), 2131625253));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        moreBlockName = getIntent().getStringExtra("block_name");
        blockCollectionEditor = findViewById(2131231015);
        blockCollectionEditor.setScrollEnabled(true);
        pane = blockCollectionEditor.getBlockPane();
        EasyDeleteEditText input = findViewById(2131230990);
        moreBlockNameEditorText = input.getEditText();
        moreBlockNameEditorText.setPrivateImeOptions("defaultInputmode=english;");
        moreBlockNameEditorText.setText(moreBlockName);
        input.setHint(xB.b().a(this, 2131625254));
        Button save = findViewById(2131231681);
        save.setText(xB.b().a(getApplicationContext(), 2131625031));
        save.setOnClickListener(this);
        moreBlockNameValidator = new NB(this, input.getTextInputLayout(), Pp.h().g());
        actionSection = findViewById(2131231320);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        MoreBlockCollectionBean a2 = Pp.h().a(moreBlockName);
        addHeaderBlock(a2.spec);
        addBlocks(a2.blocks);
        resizeBottomViews();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private Rs a(BlockBean blockBean) {
        return new Rs(this, Integer.parseInt(blockBean.id), blockBean.spec, blockBean.type, blockBean.typeName, blockBean.opCode);
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem saveImageItem = menu.add(0, 12, 0, "Save image");
        saveImageItem.setIcon(2131165642);
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
