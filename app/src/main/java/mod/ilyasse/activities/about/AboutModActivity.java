package mod.ilyasse.activities.about;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class AboutModActivity extends AppCompatActivity {
    private final Timer _timer = new Timer();
    private final Intent todiscord = new Intent();
    androidx.viewpager.widget.ViewPager viewPager;
    private LinearLayout fab;
    private TextView fabtxt;
    private HashMap<String, Object> moddersMap = new HashMap<>();
    private HashMap<String, Object> changelogMap = new HashMap<>();
    private ArrayList<HashMap<String, Object>> moddersList = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> changelogList = new ArrayList<>();
    private LinearLayout loadingview;
    private TabLayout tablayout;
    private LinearLayout base;
    private LinearLayout trash;
    private LinearLayout layout1;
    private LinearLayout layout2;
    private RecyclerView recyclerview1;
    private RecyclerView recyclerview2;
    private TextView textview3;
    private TextView textview4;
    private RequestNetwork requestData;
    private RequestNetwork.RequestListener _requestData_request_listener;
    private SharedPreferences sharedPref;
    private TimerTask animTmr;

    public static boolean isConnected(Context _context) {
        ConnectivityManager _connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo _activeNetworkInfo = _connectivityManager.getActiveNetworkInfo();
        return _activeNetworkInfo != null && _activeNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.about);
        initialize();
        initializeLogic();
    }

    private void initialize() {
        fabtxt = findViewById(R.id.fabtxt);
        fab = findViewById(R.id.fab);
        loadingview = findViewById(R.id.loadingview);
        tablayout = findViewById(R.id.tablayout);
        ImageView imageview2 = findViewById(R.id.imageview2);
        base = findViewById(R.id.base);
        trash = findViewById(R.id.trash);
        layout1 = findViewById(R.id.layout1);
        layout2 = findViewById(R.id.layout2);
        recyclerview1 = findViewById(R.id.recyclerview1);
        recyclerview2 = findViewById(R.id.recyclerview2);
        textview3 = findViewById(R.id.textview3);
        textview4 = findViewById(R.id.textview4);
        requestData = new RequestNetwork(this);
        sharedPref = getSharedPreferences("AppData", Activity.MODE_PRIVATE);

        imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                finish();
            }
        });

        recyclerview1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int _scrollState) {
                super.onScrollStateChanged(recyclerView, _scrollState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int _offsetX, int _offsetY) {
                super.onScrolled(recyclerView, _offsetX, _offsetY);
                if (_offsetY > 0) {
                    fabtxt.setVisibility(View.GONE);
                } else {
                    if (_offsetY < 0) {
                        fabtxt.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        recyclerview2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int _scrollState) {
                super.onScrollStateChanged(recyclerView, _scrollState);

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int _offsetX, int _offsetY) {
                super.onScrolled(recyclerView, _offsetX, _offsetY);
                if (_offsetY > 0) {
                    fabtxt.setVisibility(View.GONE);
                } else {
                    if (_offsetY < 0) {
                        fabtxt.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                todiscord.setAction(Intent.ACTION_VIEW);
                todiscord.setData(Uri.parse("https://discord.gg/kq39yhT4rX"));
                startActivity(todiscord);
            }
        });

        _requestData_request_listener = new RequestNetwork.RequestListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                try {

                    JSONObject json = new JSONObject(_param2);

                    JSONArray modders = json.getJSONArray("moddersteam");

                    JSONArray changelog = json.getJSONArray("changelog");
                    for (int modint = 0; modint < modders.length(); modint++) {
                        moddersMap = new HashMap<>();
                        moddersMap.put("isTitled", modders.getJSONObject(modint)
                                .getString("isTitled"));
                        moddersMap.put("title", modders.getJSONObject(modint)
                                .getString("title"));
                        moddersMap.put("modder_username", modders.getJSONObject(modint)
                                .getString("modder_username"));
                        moddersMap.put("modder_description", modders.getJSONObject(modint)
                                .getString("modder_description"));
                        moddersMap.put("modder_img", modders.getJSONObject(modint)
                                .getString("modder_img"));
                        moddersList.add(moddersMap);
                    }
                    sharedPref.edit().putString("moddersBackup", new Gson().toJson(moddersList)).apply();
                    recyclerview1.setAdapter(new Recyclerview1Adapter(moddersList));
                    for (int changeint = 0; changeint < changelog.length(); changeint++) {
                        changelogMap = new HashMap<>();
                        changelogMap.put("isTitled", changelog.getJSONObject(changeint)
                                .getString("isTitled"));
                        changelogMap.put("title", changelog.getJSONObject(changeint)
                                .getString("title"));
                        changelogMap.put("description", changelog.getJSONObject(changeint)
                                .getString("description"));
                        changelogList.add(changelogMap);
                    }
                    sharedPref.edit().putString("changelogBackup", new Gson().toJson(changelogList)).apply();
                    recyclerview2.setAdapter(new Recyclerview2Adapter(changelogList));
                    _shadAnim(loadingview, "translationY", 50, 400);
                    animTmr = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    _shadAnim(fab, "translationY", 0, 300);
                                    _shadAnim(fab, "alpha", 1, 300);
                                    _shadAnim(loadingview, "translationY", -1000, 300);
                                    _shadAnim(loadingview, "alpha", 0, 300);
                                }
                            });
                        }
                    };
                    _timer.schedule(animTmr, 200);
                } catch (JSONException e) {
                    textview3.setText("Something got fucked up");
                    textview4.setText("If you're seeing this, then we missed something lol");
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
                fab.setVisibility(View.VISIBLE);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onErrorResponse(String _param1, String _param2) {
                if (sharedPref.getString("moddersBackup", "").equals("") || sharedPref.getString("changelogBackup", "").equals("")) {
                    textview3.setText("Your device is offline");
                    textview4.setText("Check your internet connection then try again");
                } else {
                    moddersList = new Gson().fromJson(sharedPref.getString("moddersBackup", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                    }.getType());
                    changelogList = new Gson().fromJson(sharedPref.getString("changelogBackup", ""), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                    }.getType());
                    recyclerview1.setAdapter(new Recyclerview1Adapter(moddersList));
                    recyclerview2.setAdapter(new Recyclerview2Adapter(changelogList));
                    loadingview.setVisibility(View.GONE);
                    if (isConnected(getApplicationContext())) {
                        fab.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
    }

    private void initializeLogic() {
        _LOGIC_BACKEND();
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            finish();
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    public void _LOGIC_BACKEND() {
        recyclerview1.setLayoutManager(new LinearLayoutManager(this));
        recyclerview1.setHasFixedSize(true);
        recyclerview2.setLayoutManager(new LinearLayoutManager(this));
        recyclerview2.setHasFixedSize(true);
        fab.setVisibility(View.GONE);
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        initViewPager();
        requestData.startRequestNetwork(RequestNetworkController.GET, "https://ilyassesalama.github.io/sw-pro/aboutus.json", "", _requestData_request_listener);
        rippleRound(fab, "#7289DA", "#FFFFFF", 90);
    }

    public void initViewPager() {
        viewPager = new androidx.viewpager.widget.ViewPager(this);

        viewPager.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        MyPagerAdapter adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        base.addView(viewPager);

        tablayout.setSelectedTabIndicatorColor(Color.parseColor("#008DCD"));
        tablayout.setTabTextColors(Color.parseColor("#424242"), Color.parseColor("#008DCD"));

        tablayout.setupWithViewPager(viewPager);


        viewPager.addOnPageChangeListener(new androidx.viewpager.widget.ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {

                if (viewPager.getCurrentItem() == 0) {
                    fabtxt.setVisibility(View.VISIBLE);
                } else {
                    if (viewPager.getCurrentItem() == 1) {
                        fabtxt.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    public void _circularImage(final ImageView _image, final String _url) {
        Glide.with(this)
                .load(_url)
                .circleCrop()
                .placeholder(R.drawable.userimg)
                .into(_image);

    }

    public void _advancedCorners(final View _view, final String _color, final double _n1, final double _n2, final double _n3, final double _n4) {
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
        gd.setColor(Color.parseColor(_color));
        gd.setCornerRadii(new float[]{(int) _n1, (int) _n1, (int) _n2, (int) _n2, (int) _n4, (int) _n4, (int) _n3, (int) _n3});
        _view.setBackground(gd);
    }

    public void _shadAnim(final View _view, final String _propertyName, final double _value, final double _duration) {
        ObjectAnimator anim = new ObjectAnimator();
        anim.setTarget(_view);
        anim.setPropertyName(_propertyName);
        anim.setFloatValues((float) _value);
        anim.setDuration((long) _duration);
        anim.start();
    }

    public void rippleRound(final View _view, final String _focus, final String _pressed, final double _round) {
        android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
        GG.setColor(Color.parseColor(_focus));
        GG.setCornerRadius((float) _round);
        android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{Color.parseColor(_pressed)}), GG, null);
        _view.setBackground(RE);
    }

    private class MyPagerAdapter extends androidx.viewpager.widget.PagerAdapter {
        public int getCount() {
            return 2;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup collection, int position) {

            LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.about_empty_viewpager, null);

            LinearLayout container = v.findViewById(R.id.linear1);

            if (position == 0) {
                ViewGroup parent = (ViewGroup) layout1.getParent();
                if (parent != null) {
                    parent.removeView(layout1);
                }
                container.addView(layout1);

            } else if (position == 1) {
                ViewGroup parent = (ViewGroup) layout2.getParent();
                if (parent != null) {
                    parent.removeView(layout2);
                }
                container.addView(layout2);
            }
            collection.addView(v, 0);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, @NonNull Object view) {
            collection.removeView((View) view);
            trash.addView((View) view);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Modder Team";
                case 1:
                    return "Changelog";
                default:
                    return null;
            }
        }

        @Override
        public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

    }

    public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;

        public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View _v = _inflater.inflate(R.layout.about_moddersview, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }

        @Override
        public void onBindViewHolder(ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;

            final TextView title = _view.findViewById(R.id.title);

            final LinearLayout sidebar = _view.findViewById(R.id.sidebar);
            final ImageView userimg = _view.findViewById(R.id.userimg);
            final TextView username = _view.findViewById(R.id.username);
            final TextView description = _view.findViewById(R.id.description);

            _circularImage(userimg, _data.get(_position).get("modder_img").toString());
            username.setText(_data.get(_position).get("modder_username").toString());
            description.setText(_data.get(_position).get("modder_description").toString());
            _advancedCorners(sidebar, "#008DCD", 0, 30, 0, 30);
            if ((boolean) _data.get(_position).get("isTitled")) {
                title.setText(_data.get(_position).get("title").toString());
                title.setVisibility(View.VISIBLE);
            } else {
                title.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View v) {
                super(v);
            }
        }

    }

    public class Recyclerview2Adapter extends RecyclerView.Adapter<Recyclerview2Adapter.ViewHolder> {
        ArrayList<HashMap<String, Object>> _data;

        public Recyclerview2Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View _v = _inflater.inflate(R.layout.about_changelog, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }

        @Override
        public void onBindViewHolder(ViewHolder _holder, final int _position) {
            View _view = _holder.itemView;

            final TextView textview1 = _view.findViewById(R.id.textview1);
            final TextView textview2 = _view.findViewById(R.id.textview2);

            textview2.setText(_data.get(_position).get("description").toString());
            if ((boolean) _data.get(_position).get("isTitled")) {
                textview1.setText(_data.get(_position).get("title").toString());
                textview1.setVisibility(View.VISIBLE);
            } else {
                textview1.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View v) {
                super(v);
            }
        }

    }
}
