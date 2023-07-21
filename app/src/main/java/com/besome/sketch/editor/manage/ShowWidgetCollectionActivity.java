package com.besome.sketch.editor.manage;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
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
        int i = getResources().getDisplayMetrics().heightPixels;
        this.r.measure(0, 0);
        this.n.setLayoutParams(new LinearLayout.LayoutParams(-1, ((i - GB.a(this.e)) - GB.f(this.e)) - this.r.getMeasuredHeight()));
        this.n.requestLayout();
    }

    @Override
    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 != 2131231112) {
            if (id2 == 2131231681 && this.s.b()) {
                Rp.h().a(this.l, this.o.getText().toString(), true);
                bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625279), 0).show();
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
        setContentView(2131427513);
        this.k = (Toolbar) findViewById(2131231847);
        setSupportActionBar(this.k);
        findViewById(2131231370).setVisibility(8);
        getSupportActionBar().setTitle(xB.b().a(getApplicationContext(), 2131625306));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        this.k.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });
        this.l = getIntent().getStringExtra("widget_name");
        this.m = (ViewPane) findViewById(2131231592);
        this.m.setVerticalScrollBarEnabled(true);
        kC kCVar = new kC("", wq.a() + "/image/data/", "", "");
        kCVar.b(Op.g().f());
        this.m.setResourceManager(kCVar);
        this.p = (EasyDeleteEditText) findViewById(2131230990);
        this.o = this.p.getEditText();
        this.o.setPrivateImeOptions("defaultInputmode=english;");
        this.o.setText(this.l);
        this.p.setHint(xB.b().a(this, 2131625305));
        this.q = (Button) findViewById(2131231681);
        this.q.setText(xB.b().a(getApplicationContext(), 2131625031));
        this.q.setOnClickListener(this);
        this.s = new NB(this, this.p.getTextInputLayout(), Rp.h().g());
        this.r = (LinearLayout) findViewById(2131231320);
        this.n = (ScrollView) findViewById(2131231692);
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        a(Rp.h().a(this.l).widgets);
        l();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.d.setScreenName(ShowWidgetCollectionActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public sy a(ViewBean viewBean) {
        View b = this.m.b(viewBean);
        this.m.a(b);
        return (sy) b;
    }
}
