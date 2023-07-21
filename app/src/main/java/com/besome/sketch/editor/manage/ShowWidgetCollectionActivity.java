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
import com.google.android.gms.analytics.HitBuilders;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Iterator;

import a.a.a.GB;
import a.a.a.NB;
import a.a.a.Op;
import a.a.a.Rp;
import a.a.a.bB;
import a.a.a.kC;
import a.a.a.mB;
import a.a.a.sy;
import a.a.a.wq;
import a.a.a.xB;

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
        n.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getResources().getDisplayMetrics().heightPixels - GB.a(e) - GB.f(e) - r.getMeasuredHeight()));
        n.requestLayout();
    }

    @Override
    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 != R.id.img_back) {
            if (id2 == R.id.save_button && s.b()) {
                Rp.h().a(l, o.getText().toString(), true);
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.design_manager_message_edit_complete), bB.TOAST_NORMAL).show();
                finish();
                return;
            }
            return;
        }
        onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        l();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.manage_collection_show_widget);
        k = findViewById(R.id.toolbar);
        setSupportActionBar(k);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(xB.b().a(getApplicationContext(), R.string.design_manager_widget_title_actionbar_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        k.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });
        l = getIntent().getStringExtra("widget_name");
        m = findViewById(R.id.pane);
        m.setVerticalScrollBarEnabled(true);
        kC kCVar = new kC("", wq.a() + "/image/data/", "", "");
        kCVar.b(Op.g().f());
        m.setResourceManager(kCVar);
        p = findViewById(R.id.ed_input);
        o = p.getEditText();
        o.setPrivateImeOptions("defaultInputmode=english;");
        o.setText(l);
        p.setHint(xB.b().a(this, R.string.design_manager_widget_hint_enter_widget_name));
        q = findViewById(R.id.save_button);
        q.setText(xB.b().a(getApplicationContext(), R.string.common_word_save));
        q.setOnClickListener(this);
        s = new NB(this, p.getTextInputLayout(), Rp.h().g());
        r = findViewById(R.id.layout_button);
        n = findViewById(R.id.scroll_view);
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        a(Rp.h().a(l).widgets);
        l();
    }

    @Override
    public void onResume() {
        super.onResume();
        d.setScreenName(ShowWidgetCollectionActivity.class.getSimpleName());
        d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public sy a(ViewBean viewBean) {
        View b = m.b(viewBean);
        m.a(b);
        return (sy) b;
    }
}
