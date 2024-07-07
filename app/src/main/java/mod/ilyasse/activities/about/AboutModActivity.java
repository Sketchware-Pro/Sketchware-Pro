package mod.ilyasse.activities.about;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.LongSerializationPolicy;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.HashMap;

import mod.RequestNetwork;
import mod.RequestNetworkController;
import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;

public class AboutModActivity extends AppCompatActivity {
    private static final String ABOUT_TEAM_URL = "https://sketchware-pro.github.io/Sketchware-Pro/aboutus.json";

    private ViewPager viewPager;
    private ExtendedFloatingActionButton fab;
    private ArrayList<HashMap<String, Object>> teamList = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> changelogList = new ArrayList<>();
    private TabLayout tablayout;
    private LinearLayout root;
    private LinearLayout trash;
    private LinearLayout teamRecyclerContainer;
    private LinearLayout changelogRecyclerContainer;
    private RecyclerView teamRecycler;
    private RecyclerView changelogRecycler;
    private TextView loadingTitle;
    private TextView loadingDescription;
    private RequestNetwork requestData;
    private RequestNetwork.RequestListener requestDataListener;
    private SharedPreferences sharedPref;
    private String discordInviteLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        initialize();
        initializeLogic();
    }

    private void initialize() {
        fab = findViewById(R.id.fab);
        LinearLayout loading = findViewById(R.id.loading_view);
        tablayout = findViewById(R.id.tab_layout);
        ImageView back = findViewById(R.id.img_back);
        root = findViewById(R.id.root);
        trash = findViewById(R.id.trash);
        teamRecyclerContainer = findViewById(R.id.team_container);
        changelogRecyclerContainer = findViewById(R.id.changelog_container);
        teamRecycler = findViewById(R.id.team);
        changelogRecycler = findViewById(R.id.changelog);
        loadingTitle = findViewById(R.id.tv_loading);
        loadingDescription = findViewById(R.id.tv_loading_desc);
        requestData = new RequestNetwork(this);
        sharedPref = getSharedPreferences("AboutMod", Activity.MODE_PRIVATE);

        rippleRound(back, "#ffffff", "#1F000000", 90);
        back.setOnClickListener(Helper.getBackPressedClickListener(this));

        class OnScrollListener extends RecyclerView.OnScrollListener {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 8) {
                    fab.shrink();
                } else if (dy < -8) {
                    fab.extend();
                }
            }
        }

        teamRecycler.addOnScrollListener(new OnScrollListener());

        changelogRecycler.addOnScrollListener(new OnScrollListener());

        fab.setOnClickListener(v -> {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(discordInviteLink)));
            } catch (Exception e) {
                ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", discordInviteLink));
                Toast.makeText(this, "Your device doesn't have a browser app installed. Invite link has been copied to your clipboard.", Toast.LENGTH_LONG).show();
            }
        });

        requestDataListener = new RequestNetwork.RequestListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
                try {
                    GsonBuilder builder = new GsonBuilder();
                    builder.setLongSerializationPolicy(LongSerializationPolicy.STRING);
                    AboutUsData data = builder.create().fromJson(response, AboutUsData.class);

                    if (data.discordInviteLink != null) discordInviteLink = data.discordInviteLink;

                    teamList = data.moddersteam;
                    teamRecycler.setAdapter(new AboutAdapters.TeamRecyclerAdapter(AboutModActivity.this, teamList));

                    changelogList = data.changelog;
                    changelogRecycler.setAdapter(new AboutAdapters.ChangelogRecyclerAdapter(changelogList));

                    SharedPreferences.Editor savedData = sharedPref.edit();

                    if (discordInviteLink != null) {
                        savedData.putString("discordInviteLinkBackup", discordInviteLink);
                    }
                    savedData.putString("moddersBackup", new Gson().toJson(teamList));
                    savedData.putString("changelogBackup", new Gson().toJson(changelogList));

                    savedData.apply();

                    shadAnim(loading, "translationY", -1000, 300);
                    shadAnim(loading, "alpha", 0, 300);
                    new Handler().postDelayed(() -> {
                        shadAnim(fab, "translationY", 0, 300);
                        shadAnim(fab, "alpha", 1, 300);
                    }, 200);
                } catch (JsonParseException e) {
                    loadingTitle.setText("Something went wrong");
                    loadingDescription.setText("We're sorry for any inconvenience. Please contact us on our " +
                            "Discord server if this error keeps showing up:\n" + e.getMessage() +
                            "\n\nStack trace: " + Log.getStackTraceString(e));
                }
                fab.setVisibility(View.VISIBLE);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(String tag, String message) {
                if (sharedPref.getString("moddersBackup", "").isEmpty()
                        || sharedPref.getString("changelogBackup", "").isEmpty()) {
                    loadingTitle.setText("Your device is offline!");
                    loadingDescription.setText("Check your internet connection and try again.");
                } else {
                    teamList = new Gson().fromJson(sharedPref.getString("moddersBackup", ""), Helper.TYPE_MAP_LIST);
                    changelogList = new Gson().fromJson(sharedPref.getString("changelogBackup", ""), Helper.TYPE_MAP_LIST);
                    teamRecycler.setAdapter(new AboutAdapters.TeamRecyclerAdapter(AboutModActivity.this, teamList));
                    changelogRecycler.setAdapter(new AboutAdapters.ChangelogRecyclerAdapter(changelogList));
                    loading.setVisibility(View.GONE);
                    if (SketchwareUtil.isConnected()) {
                        fab.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
    }

    private void initializeLogic() {
        teamRecycler.setLayoutManager(new LinearLayoutManager(this));
        changelogRecycler.setLayoutManager(new LinearLayoutManager(this));
        fab.setVisibility(View.GONE);
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initViewPager();

        requestData.startRequestNetwork(RequestNetworkController.GET, ABOUT_TEAM_URL, "", requestDataListener);
        rippleRound(fab, "#5865F2", "#FFFFFF", 90);

        String toSelect = getIntent().getStringExtra("select");
        if ("changelog".equals(toSelect)) {
            viewPager.setCurrentItem(1);
        } else if ("majorChanges".equals(toSelect)) {
            viewPager.setCurrentItem(2);
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            finish();
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    private void initViewPager() {
        viewPager = new ViewPager(this);

        viewPager.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        viewPager.setAdapter(new PagerAdapterImpl());
        viewPager.setCurrentItem(0);
        root.addView(viewPager);

        tablayout.setSelectedTabIndicatorColor(0xff008dcd);
        tablayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (viewPager.getCurrentItem() == 0) {
                    fab.extend();
                } else {
                    if (viewPager.getCurrentItem() == 1) {
                        fab.shrink();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private void shadAnim(View view, String propertyName, double value, double duration) {
        ObjectAnimator anim = new ObjectAnimator();
        anim.setTarget(view);
        anim.setPropertyName(propertyName);
        anim.setFloatValues((float) value);
        anim.setDuration((long) duration);
        anim.start();
    }

    private void rippleRound(View view, String focus, String pressed, double round) {
        GradientDrawable GG = new GradientDrawable();
        GG.setColor(Color.parseColor(focus));
        GG.setCornerRadius((float) round);
        RippleDrawable RE = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(pressed)}), GG, null);
        view.setBackground(RE);
    }

    private static class AboutUsData {
        String discordInviteLink;
        ArrayList<HashMap<String, Object>> moddersteam;
        ArrayList<HashMap<String, Object>> changelog;
    }

    private class PagerAdapterImpl extends PagerAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        @NonNull
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(R.layout.about_empty_viewpager, container, false);

            LinearLayout viewContainer = v.findViewById(R.id.linearLayout);

            if (position == 0) {
                ViewGroup parent = (ViewGroup) teamRecyclerContainer.getParent();
                if (parent != null) {
                    parent.removeView(teamRecyclerContainer);
                }
                viewContainer.addView(teamRecyclerContainer);

            } else if (position == 1) {
                ViewGroup parent = (ViewGroup) changelogRecyclerContainer.getParent();
                if (parent != null) {
                    parent.removeView(changelogRecyclerContainer);
                }
                viewContainer.addView(changelogRecyclerContainer);
            } else if (position == 2) {
                TextView majorChanges = new TextView(AboutModActivity.this);
                {
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    int tenDp = SketchwareUtil.dpToPx(10);
                    params.leftMargin = tenDp;
                    params.topMargin = tenDp;
                    params.rightMargin = tenDp;
                    majorChanges.setLayoutParams(params);

                    int eightDp = SketchwareUtil.dpToPx(8);
                    majorChanges.setPadding(eightDp, eightDp, eightDp, eightDp);

                    majorChanges.setTextColor(ContextCompat.getColor(AboutModActivity.this,
                            R.color.primary_text_default_material_light));
                    majorChanges.setTextSize(14);
                }

                majorChanges.setText("Major changes to Sketchware Pro will appear here.");
                viewContainer.addView(majorChanges);
            }
            container.addView(v, 0);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            trash.addView((View) object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return switch (position) {
                case 0 -> getString(R.string.about_team_title);
                case 1 -> getString(R.string.about_changelog_title);
                case 2 -> getString(R.string.about_majorchanges_title);
                default -> null;
            };
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }
}
