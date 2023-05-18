package com.besome.sketch.editor.manage.sound;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.material.tabs.TabLayout;
import com.sketchware.remod.R;

import a.a.a.By;
import a.a.a.MA;
import a.a.a.Qp;
import a.a.a.Yv;
import a.a.a.mB;
import a.a.a.ow;
import a.a.a.to;
import a.a.a.xB;
import a.a.a.xo;

public class ManageSoundActivity extends BaseAppCompatActivity implements ViewPager.OnPageChangeListener, to {
    public final int k = 2;
    public String l;
    public Toolbar m;
    public ViewPager n;
    public TabLayout o;
    public ow p;
    public Yv q;

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void d(int i) {
        new a(getApplicationContext()).execute(new Void[0]);
    }

    public void f(int i) {
        this.n.setCurrentItem(i);
    }

    public Yv l() {
        return this.q;
    }

    public ow m() {
        return this.p;
    }

    @Override
    public void onBackPressed() {
        ow owVar = this.p;
        if (owVar.k) {
            owVar.a(false);
            return;
        }
        k();
        try {
            this.p.f();
            this.q.d();
            if (this.j.h()) {
                new Handler().postDelayed(() -> new a(getApplicationContext()).execute(), 500L);
            } else {
                xo.a(getApplicationContext());
            }
        } catch (Exception e) {
            e.printStackTrace();
            h();
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (!super.j()) {
            finish();
        }
        setContentView(R.layout.manage_sound);
        this.m = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.m);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(xB.b().a(getApplicationContext(), R.string.design_actionbar_title_manager_sound));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
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
        this.o = (TabLayout) findViewById(R.id.tab_layout);
        this.n = (ViewPager) findViewById(R.id.view_pager);
        this.n.setAdapter(new b(getSupportFragmentManager()));
        this.n.setOffscreenPageLimit(2);
        this.n.addOnPageChangeListener(this);
        this.o.setupWithViewPager(this.n);
        xo.a((to) this);
    }

    @Override
    public void onDestroy() {
        xo.i();
        super.onDestroy();
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
        this.d.setScreenName(ManageSoundActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.l);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            this.q.d();
        } else {
            this.p.f();
        }
    }

    class b extends FragmentPagerAdapter {
        public String[] f;

        public b(FragmentManager xf) {
            super(xf);
            this.f = new String[2];
            this.f[0] = xB.b().a(ManageSoundActivity.this.getApplicationContext(), R.string.design_manager_tab_title_this_project).toUpperCase();
            this.f[1] = xB.b().a(ManageSoundActivity.this.getApplicationContext(), R.string.design_manager_tab_title_my_collection).toUpperCase();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        @NonNull
        public Object instantiateItem(@NonNull ViewGroup viewGroup, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(viewGroup, position);
            if (position != 0) {
                ManageSoundActivity.this.q = (Yv) fragment;
            } else {
                ManageSoundActivity.this.p = (ow) fragment;
            }
            return fragment;
        }

        @Override
        @NonNull
        public Fragment getItem(int position) {
            if (position != 0) {
                return new Yv();
            }
            return new ow();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return this.f[position];
        }
    }

    class a extends MA {
        public a(Context context) {
            super(context);
            ManageSoundActivity.this.a(this);
        }

        @Override
        public void a() {
            ManageSoundActivity.this.h();
            ManageSoundActivity.this.setResult(RESULT_OK);
            ManageSoundActivity.this.finish();
            Qp.g().d();
        }

        @Override
        public void b() {
            try {
                publishProgress("Now processing..");
                ManageSoundActivity.this.p.h();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(new By(xB.b().a(this.a, R.string.common_error_unknown)));
            }
        }

        @Override
        public void a(String str) {
            ManageSoundActivity.this.h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}
