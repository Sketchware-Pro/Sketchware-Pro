package com.besome.sketch.editor.manage;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.view.ViewPane;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.sketchware.remod.Resources;

import java.util.ArrayList;
import java.util.Iterator;

import a.a.a.GB;
import a.a.a.NB;
import a.a.a.Op;
import a.a.a.Rp;
import a.a.a.bB;
import a.a.a.kC;
import a.a.a.sy;
import a.a.a.wq;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class ShowWidgetCollectionActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public Toolbar k;
    public String l;
    public ViewPane m;
    public ScrollView n;
    public EditText o;
    public EasyDeleteEditText p;
    public Button q;
    public LinearLayout r;
    public NB s;

    public sy a(ArrayList<ViewBean> arrayList) {
        Iterator<ViewBean> it = arrayList.iterator();
        sy syVar = null;
        while (it.hasNext()) {
            ViewBean next = it.next();
            if (arrayList.indexOf(next) == 0) {
                next.parent = "root";
                next.parentType = 0;
                next.preParent = null;
                next.preParentType = -1;
                syVar = a(next);
            } else {
                a(next);
            }
        }
        return syVar;
    }

    public void l() {
        r.measure(0, 0);
        n.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ((getResources().getDisplayMetrics().heightPixels
                        - GB.a(e)) - GB.f(e)) - r.getMeasuredHeight()
        ));
        n.requestLayout();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == Resources.id.img_back) {
            onBackPressed();
        } else if (id == Resources.id.save_button && s.b()) {
            Rp.h().a(l, o.getText().toString(), true);
            bB.a(getApplicationContext(),
                    xB.b().a(getApplicationContext(),
                            Resources.string.design_manager_message_edit_complete),
                    0).show();
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        l();
    }

    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.manage_collection_show_widget);
        k = findViewById(Resources.id.toolbar);
        a(k);
        findViewById(Resources.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), Resources.string.design_manager_widget_title_actionbar_title));
        d().e(true);
        d().d(true);
        k.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        l = getIntent().getStringExtra("widget_name");
        m = findViewById(Resources.id.pane);
        m.setVerticalScrollBarEnabled(true);
        kC kCVar = new kC("", wq.a() + "/image/data/", "", "");
        kCVar.b(Op.g().f());
        m.setResourceManager(kCVar);
        p = findViewById(Resources.id.ed_input);
        o = p.getEditText();
        o.setPrivateImeOptions("defaultInputmode=english;");
        o.setText(l);
        p.setHint(xB.b().a(this, Resources.string.design_manager_widget_hint_enter_widget_name));
        q = findViewById(Resources.id.save_button);
        q.setText(xB.b().a(getApplicationContext(), Resources.string.common_word_save));
        q.setOnClickListener(this);
        s = new NB(this, p.getTextInputLayout(), Rp.h().g());
        r = findViewById(Resources.id.layout_button);
        n = findViewById(Resources.id.scroll_view);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        a(Rp.h().a(l).widgets);
        l();
    }

    public sy a(ViewBean viewBean) {
        sy b = (sy) m.b(viewBean);
        m.a((View) b);
        return b;
    }
}
