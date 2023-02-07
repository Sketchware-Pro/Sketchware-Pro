package com.besome.sketch.editor.manage.font;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.tabs.TabLayout;
import com.sketchware.remod.R;

import a.a.a.MA;
import a.a.a.Np;
import a.a.a.St;
import a.a.a.Zt;
import a.a.a.mB;
import mod.hey.studios.util.Helper;

public class ManageFontActivity extends BaseAppCompatActivity implements ViewPager.OnPageChangeListener {

    private String sc_id;
    private ViewPager pager;
    private Zt myCollectionFontsFragment;
    private St thisProjectFontsFragment;

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

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
            return;
        }

        k();
        try {
            new Handler().postDelayed(() -> new SaveAsyncTask(this).execute(), 500L);
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
        pager.addOnPageChangeListener(this);
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

        public TabLayoutAdapter(FragmentManager xf) {
            super(xf);
            labels[0] = Helper.getResString(R.string.design_manager_tab_title_this_project).toUpperCase();
            labels[1] = Helper.getResString(R.string.design_manager_tab_title_my_collection).toUpperCase();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            if (position == 0) {
                myCollectionFontsFragment = (Zt) fragment;
            } else {
                thisProjectFontsFragment = (St) fragment;
            }
            return fragment;
        }

        @Override
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

    private class SaveAsyncTask extends MA {

        public SaveAsyncTask(Context context) {
            super(context);
            addTask(this);
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
                myCollectionFontsFragment.g();
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
