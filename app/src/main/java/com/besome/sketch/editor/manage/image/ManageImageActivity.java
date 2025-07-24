package com.besome.sketch.editor.manage.image;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.*;
import android.view.View.*;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

import java.lang.ref.WeakReference;

import a.a.a.MA;
import a.a.a.Op;
import a.a.a.fu;
import a.a.a.mB;
import a.a.a.pu;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageImageBinding;

public class ManageImageActivity extends BaseAppCompatActivity implements ViewPager.OnPageChangeListener {
    private String sc_id;
    private pu projectImagesFragment;
    private fu collectionImagesFragment;
    private ManageImageBinding binding;

    public static int getImageGridColumnCount(Context context) {
        var displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density) / 100;
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    public void f(int i) {
        binding.viewPager.setCurrentItem(i);
    }

    public fu l() {
        return collectionImagesFragment;
    }

    public pu m() {
        return projectImagesFragment;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (projectImagesFragment.isSelecting) {
            projectImagesFragment.a(false);
        } else if (collectionImagesFragment.isSelecting()) {
            collectionImagesFragment.unselectAll();
            binding.layoutBtnImport.setVisibility(View.GONE);
        } else {
            k();
            new Handler().postDelayed(() -> new SaveImagesAsyncTask(this).execute(), 500L);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ManageImageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!super.isStoragePermissionGranted()) {
            finish();
        }

        setNormalAppBarState(true);
        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
        }

        binding.viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        binding.viewPager.setOffscreenPageLimit(2);
        binding.viewPager.addOnPageChangeListener(this);
        binding.tabLayout.setupWithViewPager(binding.viewPager);


    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.isStoragePermissionGranted()) {
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
        binding.layoutBtnGroup.setVisibility(View.GONE);
        binding.layoutBtnImport.setVisibility(View.GONE);

        if (position == 0) {
            binding.fab.animate().translationY(0F).setDuration(200L).start();
            binding.fab.show();
            collectionImagesFragment.unselectAll();
        } else {
            binding.fab.animate().translationY(400F).setDuration(200L).start();
            binding.fab.hide();
            projectImagesFragment.a(false);
        }
    }

    // Why does this break the icon list in some cases
    // I'll keep it like this until I find a fix
    // or someones else find a fix ^_^
    public void setNormalAppBarState(boolean visible) {
        if (visible) {
            binding.appbarSearch.setVisibility(View.GONE);
            setSupportActionBar(binding.topAppBar);
            binding.topAppBar.setTitle(getTranslatedString(R.string.design_actionbar_title_manager_image));
            binding.topAppBar.setNavigationOnClickListener(v -> {
                if (!mB.a()) {
                    onBackPressed();
                }
            });
        } else {
            binding.topAppBar.setVisibility(View.GONE);
            binding.appbarSearch.setVisibility(View.VISIBLE);
            setSupportActionBar(binding.toolbarSearch);
            binding.toolbarSearch.setTitle("");  // WE DON'T WANT TITLE ELSE BUGGY :P
        }
    }

    private static class SaveImagesAsyncTask extends MA {
        private final WeakReference<ManageImageActivity> activity;

        public SaveImagesAsyncTask(ManageImageActivity activity) {
            super(activity);
            this.activity = new WeakReference<>(activity);
            activity.a(this);
        }

        @Override
        public void a() {
            var activity = this.activity.get();
            activity.h();
            activity.setResult(Activity.RESULT_OK);
            activity.finish();
            Op.g().d();
        }

        @Override
        public void b() {
            activity.get().projectImagesFragment.saveImages();
        }

        @Override
        public void a(String str) {
            activity.get().h();
        }
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        private final String[] labels;

        public PagerAdapter(FragmentManager manager) {
            super(manager);
            labels = new String[2];
            labels[0] = getTranslatedString(R.string.design_manager_tab_title_this_project);
            labels[1] = getTranslatedString(R.string.design_manager_tab_title_my_collection);
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
}
