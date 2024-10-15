package mod.ilyasse.activities.about;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.ActivityAboutAppBinding;

import mod.ilyasse.activities.about.fragments.ChangeLogFragment;
import mod.ilyasse.activities.about.fragments.MajorChangesFragment;
import mod.ilyasse.activities.about.fragments.TeamFragment;
import mod.ilyasse.activities.about.models.AboutAppViewModel;
import mod.ilyasse.activities.about.models.AboutResponseModel;
import mod.ilyasse.utils.Network;
import mod.hey.studios.util.Helper;

public class AboutActivity extends AppCompatActivity {

    private ActivityAboutAppBinding binding;
    private SharedPreferences sharedPref;
    public AboutAppViewModel aboutAppData;
    private final Network network = new Network();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutAppBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        aboutAppData = new ViewModelProvider(this).get(AboutAppViewModel.class);
        sharedPref = getSharedPreferences("AppData", Activity.MODE_PRIVATE);

        initViews();
        initData();
    }

    private void initViews() {
        binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        binding.discordButton.setOnClickListener(v -> {
            String discordLink = aboutAppData.getDiscordInviteLink().getValue();
            if (discordLink != null) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(discordLink)));
            }
        });
        AboutAdapter adapter = new AboutAdapter(this);
        binding.viewPager.setOffscreenPageLimit(3);
        binding.viewPager.setAdapter(adapter);

        String[] tabTitles = new String[]{
                getString(R.string.about_team_title),
                getString(R.string.about_changelog_title),
                getString(R.string.about_majorchanges_title)
        };

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> tab.setText(tabTitles[position])).attach();

        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    binding.discordButton.extend();
                } else {
                    binding.discordButton.shrink();
                }
            }
        });

        String toSelect = getIntent().getStringExtra("select");
        if (toSelect != null) {
            if ("changelog".equals(toSelect)) {
                binding.viewPager.setCurrentItem(1);
            } else if ("majorChanges".equals(toSelect)) {
                binding.viewPager.setCurrentItem(2);
            }
        }
    }

    private void initData() {
        network.get(getString(R.string.link_about_team), response -> {
            if (response != null) {
                sharedPref.edit().putString("aboutData", response).apply();
            } else {
                response = sharedPref.getString("aboutData", null);
            }
            if (response == null) return;

            Gson gson = new Gson();
            AboutResponseModel aboutResponseModel = gson.fromJson(response, AboutResponseModel.class);
            aboutAppData.setDiscordInviteLink(aboutResponseModel.getDiscordInviteLink());
            aboutAppData.setTeamMembers(aboutResponseModel.getTeam());
            aboutAppData.setChangelog(aboutResponseModel.getChangelog());
        });
    }

    // ----------------- classes ----------------- //

    public static class AboutAdapter extends FragmentStateAdapter {
        public AboutAdapter(AppCompatActivity activity) {
            super(activity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return switch (position) {
                case 1 -> new ChangeLogFragment();
                case 2 -> new MajorChangesFragment();
                default -> new TeamFragment();
            };
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
