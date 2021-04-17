package com.besome.sketch.editor.manage;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.editor.logic.BlockPane;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;

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
import mod.w3wide.tools.ImageFactory;

public class ShowBlockCollectionActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public Toolbar k;
    public String l;
    public ViewBlockCollectionEditor m;
    public BlockPane n;
    public EditText o;
    public EasyDeleteEditText p;
    public Button q;
    public LinearLayout r;
    public NB s;

    public final void a(ArrayList<BlockBean> arrayList, int i, int i2) {
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

    public final void l() {
        int i = getResources().getDisplayMetrics().heightPixels;
        r.measure(0, 0);
        m.setLayoutParams(new LinearLayout.LayoutParams(-1, ((i - GB.a(e)) - GB.f(e)) - r.getMeasuredHeight()));
        m.requestLayout();
    }

    @SuppressLint("ResourceType")
    public void onClick(View view) {
        if (view.getId() == 2131231681 && s.b()) {
            Mp.h().a(l, o.getText().toString(), true);
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625279), 0).show();
            finish();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        l();
    }

    @SuppressLint("ResourceType")
    @Override
    // androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427512);
        k = findViewById(2131231847);
        a(k);
        findViewById(2131231370).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), 2131625253));
        d().e(true);
        d().d(true);
        k.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        l = getIntent().getStringExtra("block_name");
        m = findViewById(2131231015);
        m.setScrollEnabled(true);
        n = m.getBlockPane();
        p = findViewById(2131230990);
        o = p.getEditText();
        o.setPrivateImeOptions("defaultInputmode=english;");
        o.setText(l);
        p.setHint(xB.b().a(this, 2131625254));
        q = findViewById(2131231681);
        q.setText(xB.b().a(getApplicationContext(), 2131625031));
        q.setOnClickListener(this);
        s = new NB(this, p.getTextInputLayout(), Mp.h().g());
        r = findViewById(2131231320);
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        a(Mp.h().a(l).blocks, 10, 10);
        l();
    }

    @Override
    // androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onResume() {
        super.onResume();
    }

    public final Rs a(BlockBean blockBean) {
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
            if (ImageFactory.saveBitmap(m.getChildAt(0), l).exists()) {
                SketchwareUtil.toast("Saved image to /Internal storage/sketchware/saved_block/" + l + ".png!");
            } else {
                SketchwareUtil.toastError("Couldn't save image");
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
