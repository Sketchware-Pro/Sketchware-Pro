package com.besome.sketch.editor.manage.sound;

import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

import java.lang.ref.WeakReference;

import a.a.a.MA;
import a.a.a.Qp;
import a.a.a.Yv;
import a.a.a.mB;
import a.a.a.ow;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageSoundBinding;

public class ManageSoundActivity extends BaseAppCompatActivity implements ViewPager.OnPageChangeListener {

    private final int TAB_COUNT = 2;
    public ManageSoundBinding binding;
    public ow projectSounds;
    public Yv collectionSounds;
    private String sc_id;

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onBackPressed() {
        if (projectSounds.isSelecting) {
            projectSounds.setSelecting(false);
        } else if (collectionSounds.isSelecting()) {
            collectionSounds.resetSelection();
        } else {
            k();
            try {
                projectSounds.stopPlayback();
                collectionSounds.stopPlayback();
                new Handler().postDelayed(() -> new SaveAsyncTask(this).execute(), 500L);
            } catch (Exception e) {
                h();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!super.isStoragePermissionGranted()) {
            finish();
        }

        binding = ManageSoundBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });
        sc_id = savedInstanceState == null ? getIntent().getStringExtra("sc_id") : savedInstanceState.getString("sc_id");

        binding.viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        binding.viewPager.setOffscreenPageLimit(TAB_COUNT);
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
        projectSounds.setSelecting(false);
        collectionSounds.resetSelection();
        if (position == 0) {
            binding.fab.show();
            collectionSounds.stopPlayback();
        } else {
            binding.fab.hide();
            projectSounds.stopPlayback();
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
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        private final String[] titles;

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            titles = new String[TAB_COUNT];
            titles[0] = getTranslatedString(R.string.design_manager_tab_title_this_project);
            titles[1] = getTranslatedString(R.string.design_manager_tab_title_my_collection);
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
            return position == 0 ? new ow() : new Yv();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
