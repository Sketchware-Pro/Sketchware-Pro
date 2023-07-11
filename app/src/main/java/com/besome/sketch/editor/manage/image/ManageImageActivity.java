package com.besome.sketch.editor.manage.image;

import android.app.Activity;
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

import a.a.a.MA;
import a.a.a.Op;
import a.a.a.fu;
import a.a.a.mB;
import a.a.a.pu;
import a.a.a.to;
import a.a.a.xB;
import a.a.a.xo;

public class ManageImageActivity extends BaseAppCompatActivity implements ViewPager.OnPageChangeListener, to {
    public final int k = 2;
    public String l;
    public Toolbar m;
    public ViewPager n;
    public TabLayout o;
    public pu p;
    public fu q;

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void d(int i) {
        new b(getApplicationContext()).execute();
    }

    public void f(int i) {
        n.setCurrentItem(i);
    }

    public fu l() {
        return q;
    }

    public pu m() {
        return p;
    }

    @Override
    public void onBackPressed() {
        if (p.p) {
            p.a(false);
        } else {
            k();
            try {
                if (j.h()) {
                    new Handler().postDelayed(() ->
                            new b(getApplicationContext()).execute(), 500L);
                } else {
                    xo.a(getApplicationContext());
                }
            } catch (Exception e) {
                e.printStackTrace();
                h();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_image);
        if (!super.j()) {
            finish();
        }
        m = findViewById(R.id.toolbar);
        setSupportActionBar(m);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(xB.b().a(getApplicationContext(), R.string.design_actionbar_title_manager_image));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
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
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
        d.setScreenName(ManageImageActivity.class.getSimpleName().toString());
        d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", l);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            q.f();
        } else {
            p.a(false);
        }
    }

    class a extends FragmentPagerAdapter {
        public String[] f;

        public a(FragmentManager manager) {
            super(manager);
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
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            if (position != 0) {
                q = (fu) fragment;
            } else {
                p = (pu) fragment;
            }
            return fragment;
        }

        @Override
        @NonNull
        public Fragment getItem(int position) {
            if (position != 0) {
                return new fu();
            }
            return new pu();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return f[position];
        }
    }

    class b extends MA {
        public b(Context context) {
            super(context);
            ManageImageActivity.this.a(this);
        }

        @Override
        public void a() {
            h();
            setResult(Activity.RESULT_OK);
            finish();
            Op.g().d();
        }

        @Override
        public void b() {
            publishProgress("Now processing..");
            p.i();
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
