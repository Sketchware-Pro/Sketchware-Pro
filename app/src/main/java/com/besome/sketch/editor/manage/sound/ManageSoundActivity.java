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
        new a(getApplicationContext()).execute();
    }

    public void f(int i) {
        n.setCurrentItem(i);
    }

    public Yv l() {
        return q;
    }

    public ow m() {
        return p;
    }

    @Override
    public void onBackPressed() {
        if (p.k) {
            p.a(false);
            return;
        }
        k();
        try {
            p.f();
            q.d();
            if (j.h()) {
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
        m = findViewById(R.id.toolbar);
        setSupportActionBar(m);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(xB.b().a(getApplicationContext(), R.string.design_actionbar_title_manager_sound));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        m.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });
        if (bundle == null) {
            l = getIntent().getStringExtra("sc_id");
        } else {
            l = bundle.getString("sc_id");
        }
        o = findViewById(R.id.tab_layout);
        n = findViewById(R.id.view_pager);
        n.setAdapter(new b(getSupportFragmentManager()));
        n.setOffscreenPageLimit(2);
        n.addOnPageChangeListener(this);
        o.setupWithViewPager(n);
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
        d.setScreenName(ManageSoundActivity.class.getSimpleName());
        d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", l);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            q.d();
        } else {
            p.f();
        }
    }

    class b extends FragmentPagerAdapter {
        public String[] f;

        public b(FragmentManager xf) {
            super(xf);
            f = new String[2];
            f[0] = xB.b().a(getApplicationContext(), R.string.design_manager_tab_title_this_project).toUpperCase();
            f[1] = xB.b().a(getApplicationContext(), R.string.design_manager_tab_title_my_collection).toUpperCase();
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
                q = (Yv) fragment;
            } else {
                p = (ow) fragment;
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
            return f[position];
        }
    }

    class a extends MA {
        public a(Context context) {
            super(context);
            addTask(this);
        }

        @Override
        public void a() {
            h();
            setResult(RESULT_OK);
            finish();
            Qp.g().d();
        }

        @Override
        public void b() {
            try {
                publishProgress("Now processing..");
                p.h();
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(new By(xB.b().a(a, R.string.common_error_unknown)));
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
