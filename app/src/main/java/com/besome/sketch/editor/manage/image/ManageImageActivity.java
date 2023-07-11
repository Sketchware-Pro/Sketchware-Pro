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
    private String sc_id;
    private ViewPager viewPager;
    private pu projectImagesFragment;
    private fu collectionImagesFragment;

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void d(int i) {
        new SaveImagesAsyncTask(getApplicationContext()).execute();
    }

    // don't change signature: referenced by La/a/a/fu;
    public void f(int i) {
        viewPager.setCurrentItem(i);
    }

    // don't change signature: referenced by La/a/a/pu;
    public fu l() {
        return collectionImagesFragment;
    }

    // don't change signature: referenced by La/a/a/fu;
    public pu m() {
        return projectImagesFragment;
    }

    @Override
    public void onBackPressed() {
        if (projectImagesFragment.p) {
            projectImagesFragment.a(false);
        } else {
            k();
            try {
                if (j.h()) {
                    new Handler().postDelayed(() ->
                            new SaveImagesAsyncTask(getApplicationContext()).execute(), 500L);
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(xB.b().a(getApplicationContext(), R.string.design_actionbar_title_manager_image));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });
        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
        }
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
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
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            collectionImagesFragment.f();
        } else {
            projectImagesFragment.a(false);
        }
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        private final String[] labels;

        public PagerAdapter(FragmentManager manager) {
            super(manager);
            labels = new String[2];
            labels[0] = xB.b().a(getApplicationContext(), R.string.design_manager_tab_title_this_project).toUpperCase();
            labels[1] = xB.b().a(getApplicationContext(), R.string.design_manager_tab_title_my_collection).toUpperCase();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        @NonNull
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            if (position == 0) {
                projectImagesFragment = (pu) fragment;
            } else {
                collectionImagesFragment = (fu) fragment;
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
            return labels[position];
        }
    }

    private class SaveImagesAsyncTask extends MA {
        public SaveImagesAsyncTask(Context context) {
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
            projectImagesFragment.i();
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
