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
        new b(getApplicationContext()).execute(new Void[0]);
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

    @Override
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
                    new b(getApplicationContext()).execute();
                }, 500L);
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
        setContentView(R.layout.manage_image);
        if (!super.j()) {
            finish();
        }
        this.m = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.m);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(xB.b().a(getApplicationContext(), R.string.design_actionbar_title_manager_image));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        this.n.setAdapter(new a(getSupportFragmentManager()));
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
        this.d.setScreenName(ManageImageActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.l);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            this.q.f();
        } else {
            this.p.a(false);
        }
    }

    class a extends FragmentPagerAdapter {
        public String[] f;

        public a(FragmentManager manager) {
            super(manager);
            this.f = new String[2];
            this.f[0] = xB.b().a(ManageImageActivity.this.getApplicationContext(), R.string.design_manager_tab_title_this_project).toUpperCase();
            this.f[1] = xB.b().a(ManageImageActivity.this.getApplicationContext(), R.string.design_manager_tab_title_my_collection).toUpperCase();
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
                ManageImageActivity.this.q = (fu) fragment;
            } else {
                ManageImageActivity.this.p = (pu) fragment;
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
            return this.f[position];
        }
    }

    class b extends MA {
        public b(Context context) {
            super(context);
            ManageImageActivity.this.a(this);
        }

        @Override
        public void a() {
            ManageImageActivity.this.h();
            ManageImageActivity.this.setResult(Activity.RESULT_OK);
            ManageImageActivity.this.finish();
            Op.g().d();
        }

        @Override
        public void b() {
            publishProgress("Now processing..");
            ManageImageActivity.this.p.i();
        }

        @Override
        public void a(String str) {
            ManageImageActivity.this.h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}
