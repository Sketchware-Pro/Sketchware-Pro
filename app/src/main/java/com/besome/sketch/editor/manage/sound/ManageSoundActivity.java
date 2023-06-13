package com.besome.sketch.editor.manage.sound;

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
import com.google.android.material.tabs.TabLayout;
import com.sketchware.remod.R;

import java.lang.ref.WeakReference;

import a.a.a.MA;
import a.a.a.Qp;
import a.a.a.Yv;
import a.a.a.mB;
import a.a.a.ow;
import a.a.a.xB;

public class ManageSoundActivity extends BaseAppCompatActivity implements ViewPager.OnPageChangeListener {
    private final int TAB_COUNT = 2;
    private String sc_id;
    private ViewPager viewPager;
    private ow projectSounds;
    private Yv collectionSounds;

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    public void f(int i) {
        viewPager.setCurrentItem(i);
    }

    public Yv l() {
        return collectionSounds;
    }

    public ow m() {
        return projectSounds;
    }

    @Override
    public void onBackPressed() {
        if (projectSounds.k) {
            projectSounds.a(false);
            return;
        }
        k();
        try {
            projectSounds.f();
            collectionSounds.d();
            new Handler().postDelayed(() -> new SaveAsyncTask(this).execute(), 500L);
        } catch (Exception e) {
            e.printStackTrace();
            h();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.j()) {
            finish();
        }
        setContentView(R.layout.manage_sound);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(xB.b().a(getApplicationContext(), R.string.design_actionbar_title_manager_sound));
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
        viewPager.setOffscreenPageLimit(TAB_COUNT);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            collectionSounds.d();
        } else {
            projectSounds.f();
        }
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        private final String[] titles;

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            titles = new String[TAB_COUNT];
            titles[0] = xB.b().a(getApplicationContext(), R.string.design_manager_tab_title_this_project).toUpperCase();
            titles[1] = xB.b().a(getApplicationContext(), R.string.design_manager_tab_title_my_collection).toUpperCase();
        }

        @Override
        public int getCount() {
            return TAB_COUNT;
        }

        @Override
        @NonNull
        public Object instantiateItem(@NonNull ViewGroup viewGroup, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(viewGroup, position);
            if (position != 0) {
                collectionSounds = (Yv) fragment;
            } else {
                projectSounds = (ow) fragment;
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
            return titles[position];
        }
    }

    private static class SaveAsyncTask extends MA {
        private final WeakReference<ManageSoundActivity> activityWeakReference;

        public SaveAsyncTask(ManageSoundActivity activity) {
            super(activity);
            activityWeakReference = new WeakReference<>(activity);
            activity.addTask(this);
        }

        @Override
        public void a() {
            var activity = activityWeakReference.get();
            activity.h();
            activity.setResult(RESULT_OK);
            activity.finish();
            Qp.g().d();
        }

        @Override
        public void b() {
            activityWeakReference.get().projectSounds.saveSounds();
        }

        @Override
        public void a(String str) {
            activityWeakReference.get().h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}
