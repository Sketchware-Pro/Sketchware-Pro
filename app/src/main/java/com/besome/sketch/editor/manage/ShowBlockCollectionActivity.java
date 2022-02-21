package com.besome.sketch.editor.manage;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.editor.logic.BlockPane;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.sketchware.remod.Resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import a.a.a.GB;
import a.a.a.Mp;
import a.a.a.NB;
import a.a.a.Rs;
import a.a.a.Ss;
import a.a.a.Ts;
import a.a.a.bB;
import a.a.a.xB;
import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;
import mod.w3wide.tools.ImageFactory;

public class ShowBlockCollectionActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private String blockName;
    private ViewBlockCollectionEditor blockCollectionEditor;
    private BlockPane pane;
    private EditText blockNameEditorText;
    private LinearLayout actionSection;
    private NB blockNameValidator;

    private void addBlocks(ArrayList<BlockBean> arrayList, int i, int i2) {
        Rs rs;
        Rs rs2;
        Rs rs3;
        HashMap<Integer, Rs> hashMap = new HashMap<>();
        Iterator<BlockBean> it = arrayList.iterator();
        Rs rs4 = null;
        boolean z = true;
        while (it.hasNext()) {
            Rs a2 = getBlock(it.next());
            hashMap.put((Integer) a2.getTag(), a2);
            pane.g = Math.max(pane.g, (Integer) a2.getTag() + 1);
            pane.a(a2, i, i2);
            if (z) {
                rs4 = a2;
                z = false;
            }
        }
        for (BlockBean next : arrayList) {
            Rs rs5 = hashMap.get(Integer.valueOf(next.id));
            if (rs5 != null) {
                int i3 = next.subStack1;
                if (i3 >= 0 && (rs3 = hashMap.get(i3)) != null) {
                    rs5.e(rs3);
                }
                int i4 = next.subStack2;
                if (i4 >= 0 && (rs2 = hashMap.get(i4)) != null) {
                    rs5.f(rs2);
                }
                int i5 = next.nextBlock;
                if (i5 >= 0 && (rs = hashMap.get(i5)) != null) {
                    rs5.b(rs);
                }
                int size = next.parameters.size();
                for (int i6 = 0; i6 < size; i6++) {
                    String str = next.parameters.get(i6);
                    if (str != null && str.length() > 0) {
                        if (str.charAt(0) == '@') {
                            Rs rs6 = hashMap.get(Integer.valueOf(str.substring(1)));
                            if (rs6 != null) {
                                rs5.a((Ts) rs5.V.get(i6), rs6);
                            }
                        } else {
                            ((Ss) rs5.V.get(i6)).setArgValue(str);
                            rs5.m();
                        }
                    }
                }
            }
        }
        rs4.k();
        pane.b();
    }

    private void resizeBottomViews() {
        int i = getResources().getDisplayMetrics().heightPixels;
        actionSection.measure(0, 0);
        blockCollectionEditor.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ((i - GB.a(e)) - GB.f(e)) - actionSection.getMeasuredHeight()));
        blockCollectionEditor.requestLayout();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == Resources.id.save_button && blockNameValidator.b()) {
            Mp.h().a(blockName, blockNameEditorText.getText().toString(), true);
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(),
                    Resources.string.design_manager_message_edit_complete), Toast.LENGTH_SHORT).show();
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
        setContentView(Resources.layout.manage_collection_show_block);

        Toolbar toolbar = findViewById(Resources.id.toolbar);
        a(toolbar);
        findViewById(Resources.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), Resources.string.design_manager_block_detail_actionbar_title));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

        blockName = getIntent().getStringExtra("block_name");
        blockCollectionEditor = findViewById(Resources.id.editor);
        blockCollectionEditor.setScrollEnabled(true);
        pane = blockCollectionEditor.getBlockPane();

        EasyDeleteEditText input = findViewById(Resources.id.ed_input);
        blockNameEditorText = input.getEditText();
        blockNameEditorText.setPrivateImeOptions("defaultInputmode=english;");
        blockNameEditorText.setText(blockName);
        input.setHint(xB.b().a(this, Resources.string.design_manager_block_hint_enter_block_name));

        Button save = findViewById(Resources.id.save_button);
        save.setText(xB.b().a(getApplicationContext(), Resources.string.common_word_save));
        save.setOnClickListener(this);
        blockNameValidator = new NB(this, input.getTextInputLayout(), Mp.h().g());
        actionSection = findViewById(Resources.id.layout_button);
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
        saveImageItem.setIcon(Resources.drawable.full_image_48);
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
