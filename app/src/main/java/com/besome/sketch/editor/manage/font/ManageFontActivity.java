package com.besome.sketch.editor.manage.font;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

import java.lang.ref.WeakReference;

import a.a.a.MA;
import a.a.a.Np;
import a.a.a.mB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.ManageFontBinding;

public class ManageFontActivity extends BaseAppCompatActivity {

    public ImportFontFragment projectFontsFragment;
    public FontManagerFragment collectionFontsFragment;
    public ManageFontBinding binding;
    private String sc_id;

    @Override
    public void onBackPressed() {
        if (projectFontsFragment.isSelecting) {
            projectFontsFragment.setSelectingMode(false);
        } else if (collectionFontsFragment.isSelecting()) {
            collectionFontsFragment.resetSelection();
        } else {
            k();
            try {
                new Handler().postDelayed(() -> new SaveAsyncTask(this).execute(), 500L);
            } catch (Exception e) {
                h();
            }
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ManageFontBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!isStoragePermissionGranted()) {
            finish();
        }

        binding.toolbar.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });

        sc_id = savedInstanceState == null ? getIntent().getStringExtra("sc_id") : savedInstanceState.getString("sc_id");

        binding.viewPager.setAdapter(new TabLayoutAdapter(getSupportFragmentManager()));
        binding.viewPager.setOffscreenPageLimit(2);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                binding.layoutBtnGroup.setVisibility(View.GONE);
                binding.layoutBtnImport.setVisibility(View.GONE);
                collectionFontsFragment.resetSelection();
                projectFontsFragment.setSelectingMode(false);
                changeFabState(position == 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }

    public void changeFabState(boolean state) {
        if (state) {
            binding.fab.animate().translationY(0F).setDuration(200L).start();
            binding.fab.show();
        } else {
            binding.fab.animate().translationY(400F).setDuration(200L).start();
            binding.fab.hide();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isStoragePermissionGranted()) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
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
            activityWeakReference.get().projectFontsFragment.processResources();
        }

        @Override
        public void a(String str) {
            activityWeakReference.get().h();
        }
    }

    private class TabLayoutAdapter extends FragmentPagerAdapter {
        private final String[] labels = new String[2];

        public TabLayoutAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            labels[0] = Helper.getResString(R.string.design_manager_tab_title_this_project);
            labels[1] = Helper.getResString(R.string.design_manager_tab_title_my_collection);
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
                projectFontsFragment = (ImportFontFragment) fragment;
            } else {
                collectionFontsFragment = (FontManagerFragment) fragment;
            }
            return fragment;
        }

        @Override
        @NonNull
        public Fragment getItem(int position) {
            return position == 0 ? new ImportFontFragment() : new FontManagerFragment();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return labels[position];
        }
    }
}
