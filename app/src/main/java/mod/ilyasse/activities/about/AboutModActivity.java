package mod.ilyasse.activities.about;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse(discordInviteLink)));
            }
        });

        requestDataListener = new RequestNetwork.RequestListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
                try {

                    JSONObject json = new JSONObject(response);

                    discordInviteLink = json.getString("discordInviteLink");

                    JSONArray modders = json.getJSONArray("moddersteam");

                    JSONArray changelog = json.getJSONArray("changelog");
                    for (int i = 0; i < modders.length(); i++) {
                        moddersMap = new HashMap<>();
                        moddersMap.put("isTitled", modders.getJSONObject(i)
                                .getString("isTitled"));
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
                    _shadAnim(loading, "translationY", 50, 400);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            _shadAnim(fab, "translationY", 0, 300);
                            _shadAnim(fab, "alpha", 1, 300);
                            _shadAnim(loading, "translationY", -1000, 300);
                            _shadAnim(loading, "alpha", 0, 300);
                        }
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
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        MyPagerAdapter adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        root.addView(viewPager);

        tablayout.setSelectedTabIndicatorColor(Color.parseColor("#008DCD"));
        //replaced with xml attributes instead of changing texts colors programmatically

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

    private void _circularImage(final ImageView _image, final String _url) {
        Glide.with(this)
                .load(_url)
                .placeholder(Resources.drawable.ic_user)
                .into(_image);
    }

    private void _advancedCorners(final View _view, final String _color, final double _n1, final double _n2, final double _n3, final double _n4) {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(Color.parseColor(_color));
        gd.setCornerRadii(new float[]{(int) _n1, (int) _n1, (int) _n2, (int) _n2, (int) _n4, (int) _n4, (int) _n3, (int) _n3});
        _view.setBackground(gd);
    }

    private void _shadAnim(final View _view, final String _propertyName, final double _value, final double _duration) {
        ObjectAnimator anim = new ObjectAnimator();
        anim.setTarget(_view);
        anim.setPropertyName(_propertyName);
        anim.setFloatValues((float) _value);
        anim.setDuration((long) _duration);
        anim.start();
    }

    private void rippleRound(final View _view, final String _focus, final String _pressed, final double _round) {
        GradientDrawable GG = new GradientDrawable();
        GG.setColor(Color.parseColor(_focus));
        GG.setCornerRadius((float) _round);
        RippleDrawable RE = new RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_pressed)}), GG, null);
        _view.setBackground(RE);
    }

    // PagerAdapter got obfuscated to kk
    private class MyPagerAdapter extends kk {

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
            LayoutInflater _inflater = getLayoutInflater();
            View _v = _inflater.inflate(Resources.layout.about_moddersview, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
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

            _circularImage(userIcon, Objects.requireNonNull(modders.get(position).get("modder_img")).toString());
            userName.setText(Objects.requireNonNull(modders.get(position).get("modder_username")).toString());
            description.setText(Objects.requireNonNull(modders.get(position).get("modder_description")).toString());
            _advancedCorners(sidebar, "#008DCD", 0, 30, 0, 30);
            Object isTitled = modders.get(position).get("isTitled");
            if (isTitled instanceof String) {
                if (Boolean.parseBoolean((String) isTitled)) {
                    title.setText(Objects.requireNonNull(modders.get(position).get("title")).toString());
                    title.setVisibility(View.VISIBLE);
                } else {
                    title.setVisibility(View.GONE);
                }
            } else if (isTitled instanceof Boolean) {
                if ((boolean) isTitled) {
                    title.setText(Objects.requireNonNull(modders.get(position).get("title")).toString());
                    title.setVisibility(View.VISIBLE);
                } else {
                    title.setVisibility(View.GONE);
                }
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
            if (isTitled instanceof String) {
                if (Boolean.parseBoolean((String) isTitled)) {
                    title.setText(Objects.requireNonNull(changelog.get(position).get("title")).toString());
                    title.setVisibility(View.VISIBLE);
                } else {
                    title.setVisibility(View.GONE);
                }
            } else if (isTitled instanceof Boolean) {
                if ((boolean) isTitled) {
                    title.setText(Objects.requireNonNull(changelog.get(position).get("title")).toString());
                    title.setVisibility(View.VISIBLE);
                } else {
                    title.setVisibility(View.GONE);
                }
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
