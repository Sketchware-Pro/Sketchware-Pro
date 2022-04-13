package com.besome.sketch.editor.manage.font;

import a.a.a.MA;
import a.a.a.Np;
import a.a.a.St;
import a.a.a.Xf;
import a.a.a.Zt;
import a.a.a.gg;
import a.a.a.mB;
import a.a.a.to;
import a.a.a.xB;
import a.a.a.xo;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.material.tabs.TabLayout;

/* loaded from: classes.dex */
public class ManageFontActivity extends BaseAppCompatActivity implements ViewPager.e, to {

    public final int k = 2;
    public String l;
    public Toolbar m;
    public ViewPager n;
    public TabLayout o;
    public Zt p;
    public St q;

    @Override // androidx.viewpager.widget.ViewPager.e
    public void a(int i) {
    }

    @Override // androidx.viewpager.widget.ViewPager.e
    public void a(int i, float f, int i2) {
    }

    @Override // androidx.viewpager.widget.ViewPager.e
    public void b(int i) {
    }

    public void f(int i) {
        this.n.setCurrentItem(i);
    }

    public St l() {
        return this.q;
    }

    public Zt m() {
        return this.p;
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        Zt zt = this.p;
        if (zt.l) {
            zt.a(false);
            return;
        }
        k();
        try {
            if (this.j.h()) {
                new Handler().postDelayed(() -> new b(e).execute(), 500L);
            } else {
                xo.a(getApplicationContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            h();
        }
    }

    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427520);
        if (!super.j()) {
            finish();
        }
        this.m = (Toolbar) findViewById(2131231847);
        a(this.m);
        findViewById(2131231370).setVisibility(8);
        d().a(xB.b().a(getApplicationContext(), 2131625135));
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

    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        xo.i();
        super.onDestroy();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
    }

    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
        this.d.setScreenName(ManageFontActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.l);
        super.onSaveInstanceState(bundle);
    }

    @Override // a.a.a.to
    public void d(int i) {
        new Handler().postDelayed(() -> new b(e).execute(), 500L);
    }

    /* loaded from: classes.dex */
    class a extends gg {
        public String[] f = new String[2];

        public a(Xf xf) {
            super(xf);
            this.f[0] = xB.b().a(ManageFontActivity.this.getApplicationContext(), 2131625288).toUpperCase();
            this.f[1] = xB.b().a(ManageFontActivity.this.getApplicationContext(), 2131625287).toUpperCase();
        }

        @Override // a.a.a.kk
        public int a() {
            return 2;
        }

        @Override // a.a.a.gg, a.a.a.kk
        public Object a(ViewGroup viewGroup, int i) {
            Fragment fragment = (Fragment) super.a(viewGroup, i);
            if (i != 0) {
                ManageFontActivity.this.q = (St) fragment;
            } else {
                ManageFontActivity.this.p = (Zt) fragment;
            }
            return fragment;
        }

        @Override // a.a.a.gg
        public Fragment c(int i) {
            if (i != 0) {
                return new St();
            }
            return new Zt();
        }

        @Override // a.a.a.kk
        public CharSequence a(int i) {
            return this.f[i];
        }
    }

    /* loaded from: classes.dex */
    class b extends MA {
        public b(Context context) {
            super(context);
            ManageFontActivity.this.a(this);
        }

        @Override // a.a.a.MA
        public void a() {
            ManageFontActivity.this.h();
            ManageFontActivity.this.setResult(-1);
            ManageFontActivity.this.finish();
            Np.g().d();
        }

        @Override // a.a.a.MA
        public void b() {
            try {
                publishProgress("Now processing..");
                ManageFontActivity.this.p.g();
            } catch (Exception e) {
                e.printStackTrace();
                // removed as not compilable (thanks, checked exceptions)
                // throw new By(xB.b().a(ManageFontActivity.this.getApplicationContext(), 0x7f0e03d4));
            }
        }

        @Override // a.a.a.MA
        public void a(String str) {
            ManageFontActivity.this.h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}
