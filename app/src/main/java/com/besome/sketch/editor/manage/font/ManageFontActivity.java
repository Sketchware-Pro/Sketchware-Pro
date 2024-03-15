package com.besome.sketch.editor.manage.font;

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
import a.a.a.Np;
import a.a.a.St;
import a.a.a.Zt;
import a.a.a.mB;
import mod.hey.studios.util.Helper;

public class ManageFontActivity extends BaseAppCompatActivity {

    private String sc_id;
    private ViewPager pager;
    private Zt myCollectionFontsFragment;
    private St thisProjectFontsFragment;

    public void f(int i) {
        pager.setCurrentItem(i);
    }

    public St l() {
        return thisProjectFontsFragment;
    }

    public Zt m() {
        return myCollectionFontsFragment;
    }

    @Override
    public void onBackPressed() {
        if (myCollectionFontsFragment.l) {
            myCollectionFontsFragment.a(false);
        } else {
            k();
            try {
                new Handler().postDelayed(() -> new SaveAsyncTask(this).execute(), 500L);
            } catch (Exception e) {
                e.printStackTrace();
                h();
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_font);

        if (!super.j()) {
            finish();
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(Helper.getResString(R.string.design_actionbar_title_manager_font));
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
        pager = findViewById(R.id.view_pager);
        pager.setAdapter(new TabLayoutAdapter(getSupportFragmentManager()));
        pager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(pager);
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

    private class TabLayoutAdapter extends FragmentPagerAdapter {
        private final String[] labels = new String[2];

        public TabLayoutAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            labels[0] = Helper.getResString(R.string.design_manager_tab_title_this_project).toUpperCase();
            labels[1] = Helper.getResString(R.string.design_manager_tab_title_my_collection).toUpperCase();
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
                myCollectionFontsFragment = (Zt) fragment;
            } else {
                thisProjectFontsFragment = (St) fragment;
            }
            return fragment;
        }

        @Override
        @NonNull
        public Fragment getItem(int position) {
            if (position == 0) {
                return new Zt();
            } else {
                return new St();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return labels[position];
        }
    }

    private static class SaveAsyncTask extends MA {
        private final WeakReference<ManageFontActivity> activityWeakReference;

        public SaveAsyncTask(ManageFontActivity activity) {
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
            Np.g().d();
        }

        @Override
        public void b() {
            activityWeakReference.get().myCollectionFontsFragment.g();
        }

        @Override
        public void a(String str) {
            activityWeakReference.get().h();
        }

    }
}
