package com.besome.sketch.editor.manage.font;

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
import com.sketchware.remod.testing.R;

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

public class ManageFontActivity extends BaseAppCompatActivity implements ViewPager.e, to {

    public final int k = 2;
    public String l;
    public Toolbar m;
    public ViewPager n;
    public TabLayout o;
    public Zt p;
    public St q;

    @Override
    public void a(int i) {
    }

    @Override
    public void a(int i, float f, int i2) {
    }

    @Override
    public void b(int i) {
    }

    public void f(int i) {
        n.setCurrentItem(i);
    }

    public St l() {
        return q;
    }

    public Zt m() {
        return p;
    }

    @Override
    public void onBackPressed() {
        if (p.l) {
            p.a(false);
            return;
        }
        k();
        try {
            if (j.h()) {
                new Handler().postDelayed(() -> new b(e).execute(), 500L);
            } else {
                xo.a(getApplicationContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            h();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_font);

        if (!super.j()) {
            finish();
        }

        m = findViewById(R.id.toolbar);
        a(m);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(getApplicationContext(), R.string.design_actionbar_title_manager_font));
        d().e(true);
        d().d(true);
        m.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });

        if (savedInstanceState == null) {
            l = getIntent().getStringExtra("sc_id");
        } else {
            l = savedInstanceState.getString("sc_id");
        }
        o = findViewById(R.id.tab_layout);
        n = findViewById(R.id.view_pager);
        n.setAdapter(new a(getSupportFragmentManager()));
        n.setOffscreenPageLimit(2);
        n.a(this);
        o.setupWithViewPager(n);
        xo.a((to) this);
    }

    @Override
    public void onDestroy() {
        xo.i();
        super.onDestroy();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
        d.setScreenName(ManageFontActivity.class.getSimpleName());
        d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", l);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void d(int i) {
        new Handler().postDelayed(() -> new b(e).execute(), 500L);
    }

    class a extends gg {

        public String[] f = new String[2];

        public a(Xf xf) {
            super(xf);
            f[0] = xB.b().a(getApplicationContext(), R.string.design_manager_tab_title_this_project).toUpperCase();
            f[1] = xB.b().a(getApplicationContext(), R.string.design_manager_tab_title_my_collection).toUpperCase();
        }

        @Override
        public int a() {
            return 2;
        }

        @Override
        public Object a(ViewGroup viewGroup, int i) {
            Fragment fragment = (Fragment) super.a(viewGroup, i);
            if (i != 0) {
                q = (St) fragment;
            } else {
                p = (Zt) fragment;
            }
            return fragment;
        }

        @Override
        public Fragment c(int i) {
            if (i != 0) {
                return new St();
            }
            return new Zt();
        }

        @Override
        public CharSequence a(int i) {
            return f[i];
        }
    }

    class b extends MA {

        public b(Context context) {
            super(context);
            ManageFontActivity.this.a(this);
        }

        @Override
        public void a() {
            h();
            setResult(RESULT_OK);
            finish();
            Np.g().d();
        }

        @Override
        public void b() {
            try {
                publishProgress("Now processing..");
                p.g();
            } catch (Exception e) {
                e.printStackTrace();
                // removed as not compilable (thanks, checked exceptions)
                // throw new By(xB.b().a(getApplicationContext(), 0x7f0e03d4));
            }
        }

        @Override
        public void a(String str) {
            h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}
