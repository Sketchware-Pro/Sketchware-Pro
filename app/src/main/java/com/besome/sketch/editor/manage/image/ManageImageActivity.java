package com.besome.sketch.editor.manage.image;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.material.tabs.TabLayout;

import a.a.a.By;
import a.a.a.MA;
import a.a.a.Op;
import a.a.a.Xf;
import a.a.a.fu;
import a.a.a.gg;
import a.a.a.mB;
import a.a.a.pu;
import a.a.a.to;
import a.a.a.xB;
import a.a.a.xo;

public class ManageImageActivity extends BaseAppCompatActivity implements ViewPager.e, to {
    public final int k = 2;
    public String l;
    public Toolbar m;
    public ViewPager n;
    public TabLayout o;
    public pu p;
    public fu q;

    @Override // androidx.viewpager.widget.ViewPager.e
    public void a(int i) {
    }

    @Override // androidx.viewpager.widget.ViewPager.e
    public void a(int i, float f, int i2) {
    }

    @Override // a.a.a.to
    public void d(int i) {
        new b(getApplicationContext()).execute();
    }

    public void f(int i) {
        this.n.setCurrentItem(i);
    }

    public fu l() {
        return this.q;
    }

    public pu m() {
        return this.p;
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onBackPressed() {
        pu puVar = this.p;
        if (puVar.p) {
            puVar.a(false);
            return;
        }
        k();
        try {
            if (this.j.h()) {
                new Handler().postDelayed(() -> {
                    new ManageImageActivity.b(getApplicationContext()).execute();
                }, 500);
            } else {
                xo.a(getApplicationContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            h();
        }
    }

    @Override
    // androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427525);
        if (!super.j()) {
            finish();
        }
        this.m = (Toolbar) findViewById(2131231847);
        a(this.m);
        findViewById(2131231370).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), 2131625136));
        d().e(true);
        d().d(true);
        this.m.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });
        if (bundle == null) {
            this.l = getIntent().getStringExtra("sc_id");
        } else {
            this.l = bundle.getString("sc_id");
        }
        this.o = (TabLayout) findViewById(2131231781);
        this.n = (ViewPager) findViewById(2131232325);
        this.n.setAdapter(new a(getSupportFragmentManager()));
        this.n.setOffscreenPageLimit(2);
        this.n.a(this);
        this.o.setupWithViewPager(this.n);
        xo.a((to) this);
    }

    @Override
    // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onDestroy() {
        xo.i();
        super.onDestroy();
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
    }

    @Override
    // androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
        this.d.setScreenName(ManageImageActivity.class.getSimpleName());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    // androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.l);
        super.onSaveInstanceState(bundle);
    }

    @Override // androidx.viewpager.widget.ViewPager.e
    public void b(int i) {
        if (i == 0) {
            this.q.f();
        } else {
            this.p.a(false);
        }
    }

    class a extends gg {
        public String[] f = new String[2];

        public a(Xf xf) {
            super(xf);
            this.f[0] = xB.b().a(ManageImageActivity.this.getApplicationContext(), 2131625288).toUpperCase();
            this.f[1] = xB.b().a(ManageImageActivity.this.getApplicationContext(), 2131625287).toUpperCase();
        }

        @Override // a.a.a.kk
        public int a() {
            return 2;
        }

        @Override // a.a.a.gg, a.a.a.kk
        public Object a(ViewGroup viewGroup, int i) {
            Fragment fragment = (Fragment) super.a(viewGroup, i);
            if (i != 0) {
                ManageImageActivity.this.q = (fu) fragment;
            } else {
                ManageImageActivity.this.p = (pu) fragment;
            }
            return fragment;
        }

        @Override // a.a.a.gg
        public Fragment c(int i) {
            if (i != 0) {
                return new fu();
            }
            return new pu();
        }

        @Override // a.a.a.kk
        public CharSequence a(int i) {
            return this.f[i];
        }
    }

    class b extends MA {
        public b(Context context) {
            super(context);
            ManageImageActivity.this.a(this);
        }

        @Override // a.a.a.MA
        public void a() {
            ManageImageActivity.this.h();
            ManageImageActivity.this.setResult(-1);
            ManageImageActivity.this.finish();
            Op.g().d();
        }

        @Override // a.a.a.MA
        public void b() {
            try {
                publishProgress("Now processing..");
                ManageImageActivity.this.p.i();
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    throw new By(xB.b().a(ManageImageActivity.this.getApplicationContext(), 2131624916));
                } catch (By by) {
                    by.printStackTrace();
                }
            }
        }

        @Override // a.a.a.MA
        public void a(String str) {
            ManageImageActivity.this.h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}