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

    private String l;
    private ViewBlockCollectionEditor m;
    private BlockPane n;
    private EditText o;
    private LinearLayout r;
    private NB s;

    private void a(ArrayList<BlockBean> arrayList, int i, int i2) {
        Rs rs;
        Rs rs2;
        Rs rs3;
        HashMap<Integer, Rs> hashMap = new HashMap<>();
        Iterator<BlockBean> it = arrayList.iterator();
        Rs rs4 = null;
        boolean z = true;
        while (it.hasNext()) {
            Rs a2 = a(it.next());
            hashMap.put((Integer) a2.getTag(), a2);
            BlockPane blockPane = n;
            blockPane.g = Math.max(blockPane.g, (Integer) a2.getTag() + 1);
            n.a(a2, i, i2);
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
        n.b();
    }

    private void l() {
        int i = getResources().getDisplayMetrics().heightPixels;
        r.measure(0, 0);
        m.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ((i - GB.a(e)) - GB.f(e)) - r.getMeasuredHeight()));
        m.requestLayout();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == Resources.id.save_button && s.b()) {
            Mp.h().a(l, o.getText().toString(), true);
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(),
                    Resources.string.design_manager_message_edit_complete), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        l();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(Resources.layout.manage_collection_show_block);

        Toolbar toolbar = findViewById(Resources.id.toolbar);
        a(toolbar);
        findViewById(Resources.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), Resources.string.design_manager_block_detail_actionbar_title));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

        l = getIntent().getStringExtra("block_name");
        m = findViewById(Resources.id.editor);
        m.setScrollEnabled(true);
        n = m.getBlockPane();

        EasyDeleteEditText input = findViewById(Resources.id.ed_input);
        o = input.getEditText();
        o.setPrivateImeOptions("defaultInputmode=english;");
        o.setText(l);
        input.setHint(xB.b().a(this, Resources.string.design_manager_block_hint_enter_block_name));

        Button save = findViewById(Resources.id.save_button);
        save.setText(xB.b().a(getApplicationContext(), Resources.string.common_word_save));
        save.setOnClickListener(this);
        s = new NB(this, input.getTextInputLayout(), Mp.h().g());
        r = findViewById(Resources.id.layout_button);
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        a(Mp.h().a(l).blocks, 10, 10);
        l();
    }

    private Rs a(BlockBean blockBean) {
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
            if (ImageFactory.saveBitmap(m.getChildAt(0), l).exists()) {
                SketchwareUtil.toast("Saved image to /Internal storage/sketchware/saved_block/" + l + ".png!");
            } else {
                SketchwareUtil.toastError("Couldn't save image");
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
