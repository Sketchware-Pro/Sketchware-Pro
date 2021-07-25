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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.sketchware.remod.Resources;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import a.a.a.kk;
import mod.RequestNetwork;
import mod.RequestNetworkController;
import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;

public class AboutModActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private LinearLayout fab;
    private TextView fabLabel;
    private HashMap<String, Object> moddersMap = new HashMap<>();
    private HashMap<String, Object> changelogMap = new HashMap<>();
    private ArrayList<HashMap<String, Object>> moddersList = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> changelogList = new ArrayList<>();
    private TabLayout tablayout;
    private LinearLayout root;
    private LinearLayout trash;
    private LinearLayout moddersRecyclerContainer;
    private LinearLayout changelogRecyclerContainer;
    private RecyclerView moddersRecycler;
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
        setContentView(Resources.layout.about);
        initialize();
        initializeLogic();
    }

    private void initialize() {
        fabLabel = findViewById(Resources.id.fab_label);
        fab = findViewById(Resources.id.fab);
        LinearLayout loading = findViewById(Resources.id.loading_view);
        tablayout = findViewById(Resources.id.tab_layout);
        ImageView back = findViewById(Resources.id.img_back);
        root = findViewById(Resources.id.root);
        trash = findViewById(Resources.id.trash);
        //TODO: Rename layout1, layout2, etc. resource IDs to more descriptive names
        moddersRecyclerContainer = findViewById(Resources.id.layout1);
        changelogRecyclerContainer = findViewById(Resources.id.layout2);
        moddersRecycler = findViewById(Resources.id.recyclerview1);
        changelogRecycler = findViewById(Resources.id.recyclerview2);
        loadingTitle = findViewById(Resources.id.tv_loading);
        loadingDescription = findViewById(Resources.id.tv_loading_desc);
        requestData = new RequestNetwork(this);
        sharedPref = getSharedPreferences("AboutMod", Activity.MODE_PRIVATE);

        back.setOnClickListener(Helper.getBackPressedClickListener(this));

        // RecyclerView$OnScrollListener got obfuscated to RecyclerView$m
        class OnScrollListener extends RecyclerView.m {

            // RecyclerView$OnScrollListener.onScrolled(RecyclerView, int, int) got
            // obfuscated to RecyclerView$m.a(RecyclerView, int, int)
            @Override
            public void a(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 8) {
                    fabLabel.setVisibility(View.GONE);
                } else if (dy < -8) {
                    fabLabel.setVisibility(View.VISIBLE);
                }
            }
        }

        // RecyclerView.addOnScrollListener(RecyclerView$OnScrollListener) got obfuscated
        // to RecyclerView.a(RecyclerView$m)
        moddersRecycler.a(new OnScrollListener());

        // RecyclerView.addOnScrollListener(RecyclerView$OnScrollListener) got obfuscated
        // to RecyclerView.a(RecyclerView$m)
        changelogRecycler.a(new OnScrollListener());

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
                    JSONObject json = new JSONObject(response);

                    discordInviteLink = json.getString("discordInviteLink");
                    sharedPref.edit().putString("discordInviteLinkBackup", discordInviteLink).apply();

                    JSONArray modders = json.getJSONArray("moddersteam");
                    for (int i = 0; i < modders.length(); i++) {
                        moddersMap = new HashMap<>();
                        moddersMap.put("isTitled", modders.getJSONObject(i)
                                .getString("isTitled"));
                        moddersMap.put("isMainModder", modders.getJSONObject(i)
                                .getString("isMainModder"));
                        moddersMap.put("title", modders.getJSONObject(i)
                                .getString("title"));
                        moddersMap.put("modder_username", modders.getJSONObject(i)
                                .getString("modder_username"));
                        moddersMap.put("modder_description", modders.getJSONObject(i)
                                .getString("modder_description"));
                        moddersMap.put("modder_img", modders.getJSONObject(i)
                                .getString("modder_img"));
                        moddersList.add(moddersMap);
                    }
                    sharedPref.edit().putString("moddersBackup", new Gson().toJson(moddersList)).apply();
                    moddersRecycler.setAdapter(new ModdersRecyclerAdapter(moddersList));

                    JSONArray changelog = json.getJSONArray("changelog");
                    for (int i = 0; i < changelog.length(); i++) {
                        changelogMap = new HashMap<>();
                        changelogMap.put("isTitled", changelog.getJSONObject(i)
                                .getString("isTitled"));
                        changelogMap.put("title", changelog.getJSONObject(i)
                                .getString("title"));
                        changelogMap.put("description", changelog.getJSONObject(i)
                                .getString("description"));
                        changelogList.add(changelogMap);
                    }
                    sharedPref.edit().putString("changelogBackup", new Gson().toJson(changelogList)).apply();
                    changelogRecycler.setAdapter(new ChangelogRecyclerAdapter(changelogList));

                    shadAnim(loading, "translationY", 50, 400);
                    new Handler().postDelayed(() -> {
                        shadAnim(fab, "translationY", 0, 300);
                        shadAnim(fab, "alpha", 1, 300);
                        shadAnim(loading, "translationY", -1000, 300);
                        shadAnim(loading, "alpha", 0, 300);
                    }, 200);
                } catch (JSONException e) {
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
                    loadingDescription.setText("Check your internet connection, then try again.");
                } else {
                    moddersList = new Gson().fromJson(sharedPref.getString("moddersBackup", ""), Helper.TYPE_MAP_LIST);
                    changelogList = new Gson().fromJson(sharedPref.getString("changelogBackup", ""), Helper.TYPE_MAP_LIST);
                    moddersRecycler.setAdapter(new ModdersRecyclerAdapter(moddersList));
                    changelogRecycler.setAdapter(new ChangelogRecyclerAdapter(changelogList));
                    loading.setVisibility(View.GONE);
                    if (SketchwareUtil.isConnected()) {
                        fab.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
    }

    private void initializeLogic() {
        moddersRecycler.setLayoutManager(new LinearLayoutManager(this));
        moddersRecycler.setHasFixedSize(true);
        changelogRecycler.setLayoutManager(new LinearLayoutManager(this));
        changelogRecycler.setHasFixedSize(true);
        fab.setVisibility(View.GONE);
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initViewPager();
        requestData.startRequestNetwork(RequestNetworkController.GET, "https://sketchware-pro.github.io/Sketchware-Pro/aboutus.json", "", requestDataListener);
        rippleRound(fab, "#7289DA", "#FFFFFF", 90);
        if ("changelog".equals(getIntent().getStringExtra("select"))) {
            viewPager.setCurrentItem(1);
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
        viewPager.setAdapter(new PagerAdapter());
        viewPager.setCurrentItem(0);
        root.addView(viewPager);

        tablayout.setSelectedTabIndicatorColor(0xff008dcd);
        tablayout.setupWithViewPager(viewPager);

        // ViewPager.addOnPageChangeListener(ViewPager$OnPageChangeListener) got
        // obfuscated to ViewPager.a(ViewPager$e)
        // ViewPager$OnPageChangeListener got obfuscated to ViewPager$e
        viewPager.a(new ViewPager.e() {

            // ViewPager$OnPageChangeListener.onPageScrolled(int, float, int) got obfuscated
            // to ViewPager$e.a(int, float, int)
            @Override
            public void a(int position, float positionOffset, int positionOffsetPixels) {
            }

            // ViewPager$OnPageChangeListener.onPageSelected(int) got obfuscated to
            // ViewPager$e.b(int)
            @Override
            public void b(int position) {
                if (viewPager.getCurrentItem() == 0) {
                    fabLabel.setVisibility(View.VISIBLE);
                } else {
                    if (viewPager.getCurrentItem() == 1) {
                        fabLabel.setVisibility(View.GONE);
                    }
                }
            }

            // ViewPager$OnPageChangeListener.onPageScrollStateChanged(int) got obfuscated to
            // ViewPager$e.a(int)
            public void a(int state) {
            }
        });
    }

    private void circularImage(final ImageView image, final String url) {
        Glide.with(this)
                .load(url)
                .placeholder(Resources.drawable.ic_user)
                .into(image);
    }

    private void advancedCorners(final View view, final String color) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(color));
        gd.setCornerRadii(new float[]{ 0, 0, 30, 30, 30, 30, 0, 0});
        view.setBackground(gd);
    }

    private void shadAnim(final View view, final String propertyName, final double value, final double duration) {
        ObjectAnimator anim = new ObjectAnimator();
        anim.setTarget(view);
        anim.setPropertyName(propertyName);
        anim.setFloatValues((float) value);
        anim.setDuration((long) duration);
        anim.start();
    }

    private void rippleRound(final View view, final String focus, final String pressed, final double round) {
        GradientDrawable GG = new GradientDrawable();
        GG.setColor(Color.parseColor(focus));
        GG.setCornerRadius((float) round);
        RippleDrawable RE = new RippleDrawable(new ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(pressed)}), GG, null);
        view.setBackground(RE);
    }

    // PagerAdapter got obfuscated to kk
    private class PagerAdapter extends kk {

        // PagerAdapter.getCount() got obfuscated to kk.a()
        @Override
        public int a() {
            return 2;
        }

        // PagerAdapter.instantiateItem(ViewGroup, int) got obfuscated to
        // kk.a(ViewGroup, int)
        @Override
        public Object a(ViewGroup container, int position) {

            LayoutInflater inflater = getLayoutInflater();
            View v = inflater.inflate(Resources.layout.about_empty_viewpager, null);

            LinearLayout viewContainer = v.findViewById(Resources.id.linearLayout);

            if (position == 0) {
                ViewGroup parent = (ViewGroup) moddersRecyclerContainer.getParent();
                if (parent != null) {
                    parent.removeView(moddersRecyclerContainer);
                }
                viewContainer.addView(moddersRecyclerContainer);

            } else if (position == 1) {
                ViewGroup parent = (ViewGroup) changelogRecyclerContainer.getParent();
                if (parent != null) {
                    parent.removeView(changelogRecyclerContainer);
                }
                viewContainer.addView(changelogRecyclerContainer);
            }
            container.addView(v, 0);
            return v;
        }

        // PagerAdapter.destroyItem(ViewGroup, int, Object) got obfuscated to
        // kk.a(ViewGroup, int, Object)
        @Override
        public void a(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            trash.addView((View) object);
        }

        // PagerAdapter.getPageTitle(int) got obfuscated to kk.a(int)
        @Override
        public CharSequence a(int position) {
            switch (position) {
                case 0:
                    return "Modder Team";

                case 1:
                    return "Changelog";

                default:
                    return null;
            }
        }

        // PagerAdapter.isViewFromObject(View, Object) got obfuscated to kk.a(View, Object)
        @Override
        public boolean a(View view, Object object) {
            return view == object;
        }

        // PagerAdapter.saveState() got obfuscated to kk.c()
        @Override
        public Parcelable c() {
            return null;
        }
    }

    // RecyclerView$Adapter<T extends RecyclerView.ViewHolder> got obfuscated to
    // RecyclerView$a<VH extends RecyclerView.v>
    // VH stands for ViewHolder?
    private class ModdersRecyclerAdapter extends RecyclerView.a<ModdersRecyclerAdapter.ViewHolder> {

        private final ArrayList<HashMap<String, Object>> modders;

        public ModdersRecyclerAdapter(ArrayList<HashMap<String, Object>> data) {
            modders = data;
        }

        // RecyclerView$Adapter<T extends RecyclerView.ViewHolder>.onCreateViewHolder(ViewGroup, int)
        // got obfuscated to RecyclerView$a<VH extends RecyclerView.v>.b(ViewGroup, int)
        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            View v = getLayoutInflater().inflate(Resources.layout.about_moddersview, null);
            v.setLayoutParams(new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ViewHolder(v);
        }

        // RecyclerView$Adapter<T extends RecyclerView.ViewHolder>.onBindViewHolder(ViewGolder, final int)
        // got obfuscated to RecyclerView$a<VH extends RecyclerView.v>.b(VH, int)
        @Override
        public void b(ViewHolder holder, final int position) {
            // RecyclerView$ViewHolder.itemView got obfuscated to RecyclerView$c.b
            View itemView = holder.b;

            final TextView title = itemView.findViewById(Resources.id.tv_title);
            final LinearLayout sidebar = itemView.findViewById(Resources.id.view_leftline);
            final ImageView userIcon = itemView.findViewById(Resources.id.img_user_icon);
            final TextView userName = itemView.findViewById(Resources.id.tv_user_name);
            final TextView description = itemView.findViewById(Resources.id.tv_description);

            Object modder_img = modders.get(position).get("modder_img");
            if (modder_img instanceof String) {
                circularImage(userIcon, (String) modder_img);
            }

            Object modder_username = modders.get(position).get("modder_username");
            if (modder_username instanceof String) {
                userName.setText((String) modder_username);
            }

            Object modder_description = modders.get(position).get("modder_description");
            if (modder_description instanceof String) {
                description.setText((String) modder_description);
            }

            Object isTitled = modders.get(position).get("isTitled");
            boolean isTitle = false;
            if (isTitled instanceof String) {
                isTitle = Boolean.parseBoolean((String) isTitled);
            } else if (isTitled instanceof Boolean) {
                isTitle = (boolean) isTitled;
            }

            Object isMainModder = modders.get(position).get("isMainModder");
            boolean isMainModderBool = false;
            if (isMainModder instanceof String) {
                isMainModderBool = Boolean.parseBoolean((String) isMainModder);
            } else if (isMainModder instanceof Boolean) {
                isMainModderBool = (boolean) isMainModder;
            }

            Object titleText = modders.get(position).get("title");
            if (isTitle) {
                if (titleText instanceof String) {
                    title.setText((String) titleText);
                    title.setVisibility(View.VISIBLE);
                } else {
                    title.setVisibility(View.GONE);
                }
            } else {
                title.setVisibility(View.GONE);
            }

            if (isMainModderBool) {
                advancedCorners(sidebar, "#008DCD");
            } else {
                advancedCorners(sidebar, "#00CDAB");
            }
        }

        // RecyclerView$Adapter<T extends RecyclerView.ViewHolder>.getItemCount() got obfuscated
        // to RecyclerView$a<VH extends RecyclerView.v>.a()
        @Override
        public int a() {
            return modders.size();
        }

        public class ViewHolder extends RecyclerView.v {

            public ViewHolder(View v) {
                super(v);
            }
        }
    }

    // RecyclerView$Adapter<T extends RecyclerView.ViewHolder> got obfuscated to
    // RecyclerView$a<VH extends RecyclerView.v>
    // VH stands for ViewHolder?
    private class ChangelogRecyclerAdapter extends RecyclerView.a<ChangelogRecyclerAdapter.ViewHolder> {

        private final ArrayList<HashMap<String, Object>> changelog;

        public ChangelogRecyclerAdapter(ArrayList<HashMap<String, Object>> data) {
            changelog = data;
        }

        // RecyclerView$Adapter<T extends RecyclerView.ViewHolder>.onCreateViewHolder(ViewGroup, int)
        // got obfuscated to RecyclerView$a<VH extends RecyclerView.v>.b(ViewGroup, int)
        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            View aboutChangelog = getLayoutInflater().inflate(Resources.layout.about_changelog, null);
            aboutChangelog.setLayoutParams(new RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            return new ViewHolder(aboutChangelog);
        }

        // RecyclerView$Adapter<T extends RecyclerView.ViewHolder>.onBindViewHolder(ViewGolder, final int)
        // got obfuscated to RecyclerView$a<VH extends RecyclerView.v>.b(VH, int)
        @Override
        public void b(ViewHolder holder, final int position) {
            // RecyclerView$ViewHolder.itemView got obfuscated to RecyclerView$c.b
            View itemView = holder.b;

            final TextView title = itemView.findViewById(Resources.id.tv_title);
            final TextView subtitle = itemView.findViewById(Resources.id.tv_sub_title);

            Object isTitled = changelog.get(position).get("isTitled");
            boolean isTitle = false;
            if (isTitled instanceof String) {
                isTitle = Boolean.parseBoolean((String) isTitled);
            } else if (isTitled instanceof Boolean) {
                isTitle = (boolean) isTitled;
            }

            if (isTitle) {
                Object titleText = changelog.get(position).get("title");
                if (titleText instanceof String) {
                    title.setText((String) titleText);
                    title.setVisibility(View.VISIBLE);
                } else {
                    title.setVisibility(View.GONE);
                }
            } else {
                title.setVisibility(View.GONE);
            }

            subtitle.setText(Objects.requireNonNull(changelog.get(position).get("description")).toString());
        }

        // RecyclerView$Adapter<T extends RecyclerView.ViewHolder>.getItemCount() got obfuscated
        // to RecyclerView$a<VH extends RecyclerView.v>.a()
        @Override
        public int a() {
            return changelog.size();
        }

        public class ViewHolder extends RecyclerView.v {

            public ViewHolder(View v) {
                super(v);
            }
        }
    }
}
