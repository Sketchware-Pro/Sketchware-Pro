package com.besome.sketch.editor;

import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.manage.image.ManageImageActivity;
import com.besome.sketch.editor.property.ViewPropertyItems;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.CustomScrollView;
import com.google.android.gms.analytics.HitBuilders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import a.a.a.Kw;
import a.a.a.Ze;
import a.a.a.cC;
import a.a.a.ef;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.ro;
import a.a.a.tx;

public class PropertyActivity extends BaseAppCompatActivity implements Kw {
    public Toolbar k;
    public LinearLayout l;
    public CustomScrollView m;
    public ProjectFileBean n;
    public ViewBean o;
    public boolean p = false;
    public String q;
    public LinearLayout r;
    public ArrayList<Integer> s;
    public RecyclerView t;
    public ViewPropertyItems u;
    public int v = 0;
    public ro w;

    @Override // a.a.a.Kw
    public void a(String str, Object obj) {
    }

    public void l() {
        this.u.setProjectFileBean(this.n);
        this.u.a(this.q, this.o);
        this.l.addView(this.u);
    }

    public final void m() {
        ArrayList<String> m2 = jC.d(this.q).m();
        if (m2.indexOf(this.o.layout.backgroundResource) < 0) {
            this.o.layout.backgroundResource = null;
            tx txVar = (tx) this.l.findViewWithTag("property_background_resource");
            if (txVar != null) {
                txVar.setValue(this.o.layout.backgroundResource);
            }
        }
        ViewBean viewBean = this.o;
        if (viewBean.type == 6 && m2.indexOf(viewBean.image.resName) < 0) {
            this.o.image.resName = "default_image";
            tx txVar2 = (tx) this.l.findViewWithTag("property_image");
            if (txVar2 != null) {
                txVar2.setValue(this.o.image.resName);
            }
        }
        Iterator<String> it = jC.b(this.q).e().iterator();
        while (it.hasNext()) {
            Iterator<ViewBean> it2 = jC.a(this.q).d(it.next()).iterator();
            while (it2.hasNext()) {
                ViewBean next = it2.next();
                if (next.type == 6 && m2.indexOf(next.image.resName) < 0) {
                    next.image.resName = "default_image";
                    this.p = true;
                }
                if (m2.indexOf(next.layout.backgroundResource) < 0) {
                    next.layout.backgroundResource = null;
                    this.p = true;
                }
            }
        }
        Iterator<String> it3 = jC.b(this.q).d().iterator();
        while (it3.hasNext()) {
            for (Map.Entry<String, ArrayList<BlockBean>> entry : jC.a(this.q).b(it3.next()).entrySet()) {
                Iterator<BlockBean> it4 = entry.getValue().iterator();
                while (it4.hasNext()) {
                    BlockBean next2 = it4.next();
                    if ("setImage".equals(next2.opCode)) {
                        if (m2.indexOf(next2.parameters.get(1)) < 0) {
                            next2.parameters.set(1, "default_image");
                        }
                    } else if ("setBgResource".equals(next2.opCode) && m2.indexOf(next2.parameters.get(1)) < 0) {
                        next2.parameters.set(1, "NONE");
                    }
                }
            }
        }
    }

    public void n() {
        this.r.setVisibility(8);
    }

    public void o() {
        ViewBean viewBean;
        if (this.o.id.equals("_fab")) {
            viewBean = jC.a(this.q).h(this.n.getXmlName());
        } else {
            viewBean = jC.a(this.q).c(this.n.getXmlName(), this.o.preId);
        }
        cC.c(this.q).a(this.n.getXmlName(), viewBean.clone(), this.o);
        viewBean.copy(this.o);
        Intent intent = new Intent();
        intent.putExtra("view_id", this.o.id);
        intent.putExtra("is_edit_image", this.p);
        intent.putExtra("bean", viewBean);
        setResult(-1, intent);
        finish();
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 209 && i2 == -1 && jC.d(this.q) != null && intent != null) {
            String stringExtra = intent.getStringExtra("sc_id");
            ArrayList<ProjectResourceBean> parcelableArrayListExtra = intent.getParcelableArrayListExtra("result");
            if (jC.d(stringExtra) != null) {
                jC.d(stringExtra).b(parcelableArrayListExtra);
                m();
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onBackPressed() {
        this.u.i(this.o);
        o();
    }

    @Override
    // androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427627);
        if (!super.j()) {
            finish();
        }
        this.w = new ro(getApplicationContext());
        this.k = (Toolbar) findViewById(2131231847);
        a(this.k);
        findViewById(2131231370).setVisibility(8);
        d().d(true);
        d().e(true);
        this.k.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });
        this.k.setPopupTheme(2131689886);
        this.s = new ArrayList<>();
        this.s.add(1);
        this.s.add(2);
        this.s.add(3);
        this.s.add(4);
        this.t = (RecyclerView) findViewById(2131231625);
        this.t.setHasFixedSize(true);
        this.t.setLayoutManager(new LinearLayoutManager(getApplicationContext(), 1, false));
        this.t.setAdapter(new a());
        this.m = (CustomScrollView) findViewById(2131231692);
        this.l = (LinearLayout) findViewById(2131230932);
        if (bundle != null) {
            this.q = bundle.getString("sc_id");
            this.n = (ProjectFileBean) bundle.getParcelable("project_file");
            this.o = (ViewBean) bundle.getParcelable("bean");
        } else {
            this.q = getIntent().getStringExtra("sc_id");
            this.n = (ProjectFileBean) getIntent().getParcelableExtra("project_file");
            this.o = (ViewBean) getIntent().getParcelableExtra("bean");
        }
        d().a(this.o.id.charAt(0) == '_' ? this.o.id.substring(1) : this.o.id);
        this.r = (LinearLayout) findViewById(2131231310);
        this.r.setVisibility(8);
        this.j.h();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131492885, menu);
        return true;
    }

    @Override
    // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 2131231496) {
            p();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        this.u = new ViewPropertyItems(this);
        this.u.setOrientation(1);
        l();
    }

    @Override
    // androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
        if (this.j.h()) {
            n();
        }
        this.d.setScreenName(PropertyActivity.class.getSimpleName());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    // androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.q);
        bundle.putParcelable("project_file", this.n);
        bundle.putParcelable("bean", this.o);
        super.onSaveInstanceState(bundle);
    }

    public void p() {
        Intent intent = new Intent(getApplicationContext(), ManageImageActivity.class);
        intent.setFlags(536870912);
        intent.putExtra("sc_id", this.q);
        startActivityForResult(intent, 209);
    }

    class a extends RecyclerView.a<PropertyActivity.a.ViewHolder> {

        public a() {
        }

        public void b(ViewHolder aVar, int i) {
            ((Integer) PropertyActivity.this.s.get(i)).intValue();
            if (PropertyActivity.this.v == i) {
                ef a2 = Ze.a(aVar.t);
                a2.c(1.1f);
                a2.d(1.1f);
                a2.a(300L);
                a2.a(new AccelerateInterpolator());
                a2.c();
                ef a3 = Ze.a(aVar.t);
                a3.c(1.1f);
                a3.d(1.1f);
                a3.a(300L);
                a3.a(new AccelerateInterpolator());
                a3.c();
                aVar.v.setVisibility(0);
                ColorMatrix colorMatrix = new ColorMatrix();
                colorMatrix.setSaturation(1.0f);
                aVar.t.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                return;
            }
            ef a4 = Ze.a(aVar.t);
            a4.c(1.0f);
            a4.d(1.0f);
            a4.a(300L);
            a4.a(new DecelerateInterpolator());
            a4.c();
            ef a5 = Ze.a(aVar.t);
            a5.c(1.0f);
            a5.d(1.0f);
            a5.a(300L);
            a5.a(new DecelerateInterpolator());
            a5.c();
            aVar.v.setVisibility(8);
            ColorMatrix colorMatrix2 = new ColorMatrix();
            colorMatrix2.setSaturation(0.0f);
            aVar.t.setColorFilter(new ColorMatrixColorFilter(colorMatrix2));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.a
        public ViewHolder b(ViewGroup viewGroup, int i) {
            return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(2131427377, viewGroup, false));
        }

        @Override // androidx.recyclerview.widget.RecyclerView.a
        public int a() {
            return PropertyActivity.this.s.size();
        }

        class ViewHolder extends RecyclerView.v implements View.OnClickListener {
            public ImageView t;
            public TextView u;
            public View v;

            public ViewHolder(View view) {
                super(view);
                this.t = (ImageView) view.findViewById(2131231151);
                this.u = (TextView) view.findViewById(2131232055);
                this.v = view.findViewById(2131231600);
                this.v.setBackgroundResource(2131166204);
                view.setOnClickListener(this);
            }

            public void onClick(View view) {
                if (!mB.a() && j() != -1) {
                    int j = j();
                    a aVar = a.this;
                    int i = PropertyActivity.this.v;
                    if (j != i) {
                        aVar.c(i);
                        PropertyActivity.this.v = j();
                        a aVar2 = a.this;
                        aVar2.c(PropertyActivity.this.v);
                        int intValue = ((Integer) PropertyActivity.this.s.get(PropertyActivity.this.v)).intValue();
                        if (intValue == 1) {
                            PropertyActivity.this.u.d(PropertyActivity.this.o);
                        } else if (intValue == 2) {
                            PropertyActivity.this.u.g(PropertyActivity.this.o);
                        } else if (intValue == 3) {
                            PropertyActivity.this.u.h(PropertyActivity.this.o);
                        } else if (intValue == 4) {
                            PropertyActivity.this.u.f(PropertyActivity.this.o);
                        }
                    }
                }
            }
        }
    }
}
