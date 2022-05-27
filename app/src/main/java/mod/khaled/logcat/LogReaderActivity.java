package mod.khaled.logcat;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static mod.SketchwareUtil.getDip;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mod.SketchwareUtil;
import mod.hasrat.lib.BaseTextWatcher;
import mod.hey.studios.util.Helper;

public class LogReaderActivity extends AppCompatActivity {

    private final BroadcastReceiver logger = new Logger();
    private final Pattern logPattern = Pattern.compile("^(.*\\d) ([VADEIW]) (.*): (.*)");
    private PopupMenu options;
    private String pkgFilter = "";
    private boolean autoScroll = false;

    private final ArrayList<HashMap<String, Object>> mainList = new ArrayList<>();
    private ArrayList<String> pkgFilterList = new ArrayList<>();

    private EditText filterEdittext;
    private ImageView menu;
    private ImageView back;
    private RecyclerView recyclerview;

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        LinearLayout parent = new LinearLayout(LogReaderActivity.this);
        parent.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        parent.setOrientation(LinearLayout.VERTICAL);

        final LinearLayout base = new LinearLayout(LogReaderActivity.this);
        base.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, (int) getDip(52)));
        base.setPadding((int) getDip(10), (int) getDip(0), (int) getDip(4), (int) getDip(0));
        base.setGravity(Gravity.CENTER_VERTICAL);
        base.setOrientation(LinearLayout.HORIZONTAL);
        base.setBackgroundColor(0xff008dcd);
        base.setElevation((float) (int) getDip(1));

        back = new ImageView(LogReaderActivity.this);
        back.setImageResource(R.drawable.arrow_back_white_48dp);
        back.setLayoutParams(new LinearLayout.LayoutParams((int) getDip(40), (int) getDip(40)));
        back.setPadding((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));

        filterEdittext = new EditText(LogReaderActivity.this);
        filterEdittext.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
        filterEdittext.setPadding((int) getDip(8), (int) getDip(2), (int) getDip(8), (int) getDip(2));
        filterEdittext.setGravity(Gravity.CENTER_VERTICAL);
        filterEdittext.setTextSize((float) 15);
        filterEdittext.setHint("Search log");
        filterEdittext.setBackgroundTintList(ColorStateList.valueOf(0xffffffff));
        filterEdittext.setTextColor(0xffffffff);
        filterEdittext.setSingleLine(true);
        base.setElevation((float) (int) getDip(1));

        menu = new ImageView(LogReaderActivity.this);
        menu.setImageResource(R.drawable.ic_more_vert_white_24dp);
        menu.setColorFilter(0xffffffff, PorterDuff.Mode.SRC_ATOP);
        menu.setLayoutParams(new LinearLayout.LayoutParams((int) getDip(40), (int) getDip(40)));
        menu.setPadding((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));

        recyclerview = new RecyclerView(LogReaderActivity.this);
        recyclerview.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        recyclerview.setPadding((int) getDip(4), 0, (int) getDip(4), 0);

        base.addView(back, 0);
        base.addView(filterEdittext, 1);
        base.addView(menu, 2);
        parent.addView(base, 0);
        parent.addView(recyclerview, 1);

        setContentView(parent);
        initialize();
        initializeLogic();
    }

    private void initialize() {
        options = new PopupMenu(getApplicationContext(), menu);
        options.getMenu().add("Clear all");
        options.getMenu().add("Filter by package");
        options.getMenu().add("Auto scroll").setCheckable(true).setChecked(true);
        options.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getTitle().toString()) {
                case "Clear all":
                    mainList.clear();
                    ((RecyclerviewAdapter) recyclerview.getAdapter()).deleteAll();
                    break;

                case "Filter by package":
                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle("Filter by package name")
                            .setMessage("For multiple package names, separate them with a comma (,).");
                    final EditText _e = new EditText(LogReaderActivity.this);
                    _e.setText(pkgFilter);
                    builder.setView(_e);
                    builder.setPositiveButton("Apply", (dialog, which) -> {
                        pkgFilter = _e.getText().toString();
                        pkgFilterList = new ArrayList<>(Arrays.asList(pkgFilter.split(",")));
                        filterEdittext.setText(filterEdittext.getText().toString());
                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                    builder.create().show();
                    break;

                case "Auto scroll": {
                    menuItem.setChecked(!menuItem.isChecked());
                    autoScroll = menuItem.isChecked();
                    if (autoScroll) {
                        ((LinearLayoutManager) recyclerview.getLayoutManager()).scrollToPosition(recyclerview.getAdapter().a() - 1);
                    }
                    break;
                }
            }
            return true;
        });

        filterEdittext.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String _charSeq = s.toString();
                if (_charSeq.equals("") && (pkgFilterList.size() == 0)) {
                    recyclerview.setAdapter(new RecyclerviewAdapter(mainList));
                } else {
                    ArrayList<HashMap<String, Object>> filteredList = new ArrayList<>();
                    for (HashMap<String, Object> m : mainList) {
                        if (pkgFilterList.size() != 0) {
                            if (m.containsKey("pkgName") && pkgFilterList.contains(m.get("pkgName").toString())) {
                                if (m.get("logRaw").toString().toLowerCase().contains(_charSeq.toLowerCase())) {
                                    filteredList.add(m);
                                }
                            }
                        } else if (m.get("logRaw").toString().toLowerCase().contains(_charSeq.toLowerCase())) {
                            filteredList.add(m);
                        }
                    }
                    recyclerview.setAdapter(new RecyclerviewAdapter(filteredList));
                }
            }
        });

        menu.setOnClickListener(v -> options.show());
        back.setOnClickListener(Helper.getBackPressedClickListener(this));
    }

    private void initializeLogic() {
        recyclerview.setAdapter(new RecyclerviewAdapter(new ArrayList<>()));
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        autoScroll = true;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.sketchware.remod.ACTION_NEW_DEBUG_LOG");
        registerReceiver(logger, intentFilter);
    }

    private class Logger extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            HashMap<String, Object> map = new HashMap<>();
            if (intent.hasExtra("log") && (intent.getStringExtra("log") != null)) {
                if (intent.hasExtra("pkgName")) {
                    map.put("pkgName", intent.getStringExtra("pkgName"));
                }
                map.put("logRaw", intent.getStringExtra("log"));
                if (intent.getStringExtra("log") == null) return;

                Matcher matcher = logPattern.matcher(intent.getStringExtra("log"));
                if (matcher.matches()) {
                    //group 1 = pid, time stuff & idk
                    //group 2 = log type (a,d,e,i,w)
                    //group 3 = log tag,headers
                    //group 4 = everything else
                    map.put("date", matcher.group(1).trim());
                    map.put("type", matcher.group(2).trim());
                    map.put("header", matcher.group(3));
                    map.put("body", matcher.group(4));
                    map.put("culturedLog", "true");
                }

                mainList.add(map);
                if (pkgFilterList.size() == 0) {
                    if (!filterEdittext.getText().toString().equals("")) {
                        if (map.get("logRaw").toString().toLowerCase().contains(filterEdittext.getText().toString().toLowerCase())) {
                            ((RecyclerviewAdapter) recyclerview.getAdapter()).updateList(map);
                        }
                    } else {
                        ((RecyclerviewAdapter) recyclerview.getAdapter()).updateList(map);
                    }
                } else if (map.containsKey("pkgName") && pkgFilterList.contains(map.get("pkgName").toString())) {
                    if (!filterEdittext.getText().toString().equals("")) {
                        if (map.get("logRaw").toString().toLowerCase().contains(filterEdittext.getText().toString().toLowerCase())) {
                            ((RecyclerviewAdapter) recyclerview.getAdapter()).updateList(map);
                        }
                    } else {
                        ((RecyclerviewAdapter) recyclerview.getAdapter()).updateList(map);
                    }
                }
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        filterEdittext.clearFocus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(logger);
    }

    private class RecyclerviewAdapter extends RecyclerView.a<RecyclerviewAdapter.ViewHolder> {

        private final ArrayList<HashMap<String, Object>> data;

        public void updateList(final HashMap<String, Object> _map) {
            data.add(_map);
            recyclerview.getAdapter().d(data.size() + 1);

            if (autoScroll) {
                ((LinearLayoutManager) recyclerview.getLayoutManager()).scrollToPosition(data.size() - 1);
            }
        }

        public void deleteAll() {
            data.clear();
            recyclerview.getAdapter().c();
        }

        public RecyclerviewAdapter(ArrayList<HashMap<String, Object>> _arr) {
            data = _arr;
        }

        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            final LinearLayout _v = new LinearLayout(LogReaderActivity.this);
            _v.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            _v.setOrientation(LinearLayout.VERTICAL);

            LinearLayout clickListener = new LinearLayout(LogReaderActivity.this);
            clickListener.setTag("clickListener");
            clickListener.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            clickListener.setOrientation(LinearLayout.HORIZONTAL);
            clickListener.setBackgroundColor(0xffffffff);

            LinearLayout divider = new LinearLayout(LogReaderActivity.this);
            divider.setTag("divider");
            divider.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, (int) getDip(1)));
            divider.setBackgroundColor(0xffe0e0e0);

            TextView type = new TextView(LogReaderActivity.this);
            type.setTag("type");
            type.setFocusable(false);
            type.setClickable(false);
            type.setText("U");
            type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            type.setTypeface(null, Typeface.BOLD);
            type.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            type.setLayoutParams(new LinearLayout.LayoutParams((int) getDip(22), MATCH_PARENT));
            type.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            type.setTextColor(0xffffffff);
            type.setBackgroundColor(0xff000000);

            final LinearLayout detailHolder = new LinearLayout(LogReaderActivity.this);
            detailHolder.setTag("detailHolder");
            detailHolder.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            detailHolder.setOrientation(LinearLayout.VERTICAL);
            detailHolder.setPadding((int) getDip(3), (int) getDip(3), (int) getDip(3), (int) getDip(3));
            detailHolder.setClickable(false);

            TextView date_header = new TextView(LogReaderActivity.this);
            date_header.setTag("date_header");
            date_header.setFocusable(false);
            date_header.setClickable(false);
            date_header.setVisibility(View.GONE);
            date_header.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            date_header.setTypeface(null, Typeface.BOLD);
            date_header.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
            date_header.setTextColor(0xff757575);

            TextView log = new TextView(LogReaderActivity.this);
            log.setTag("log");
            log.setFocusable(false);
            log.setClickable(false);
            log.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            log.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
            log.setTextColor(0xff000000);

            TextView pkgName = new TextView(LogReaderActivity.this);
            pkgName.setTag("pkgName");
            pkgName.setFocusable(false);
            pkgName.setClickable(false);
            pkgName.setVisibility(View.GONE);
            pkgName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            pkgName.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
            pkgName.setTextColor(0xff757575);

            detailHolder.addView(date_header, 0);
            detailHolder.addView(log, 1);
            detailHolder.addView(pkgName, 2);
            clickListener.addView(type, 0);
            clickListener.addView(detailHolder, 1);
            _v.addView(clickListener, 0);
            _v.addView(divider, 1);

            return new ViewHolder(_v);
        }

        @Override
        public void b(ViewHolder _holder, final int _position) {
            View _view = _holder.b;
            final LinearLayout clickListener = _view.findViewWithTag("clickListener");
            final LinearLayout divider = _view.findViewWithTag("divider");
            final TextView type = _view.findViewWithTag("type");
            final TextView date_header = _view.findViewWithTag("date_header");
            final TextView log = _view.findViewWithTag("log");
            final TextView pkgName = _view.findViewWithTag("pkgName");

            if (data.get(_position).containsKey("pkgName")) {
                pkgName.setText(data.get(_position).get("pkgName").toString());
                pkgName.setVisibility(View.VISIBLE);
            } else {
                pkgName.setVisibility(View.GONE);
            }
            if (data.get(_position).containsKey("culturedLog")) {
                date_header.setVisibility(View.VISIBLE);
                type.setText(data.get(_position).get("type").toString());
                date_header.setText(data.get(_position).get("date").toString().concat(" | ".concat(data.get(_position).get("header").toString())));
                switch (data.get(_position).get("type").toString()) {
                    case "A": {
                        type.setBackgroundColor(0xFF9C27B0);
                        break;
                    }
                    case "D": {
                        type.setBackgroundColor(0xFF2196F3);
                        break;
                    }
                    case "E": {
                        type.setBackgroundColor(0xFFF44336);
                        break;
                    }
                    case "I": {
                        type.setBackgroundColor(0xFF4CAF50);
                        break;
                    }
                    case "V": {
                        type.setBackgroundColor(0xFF000000);
                    }
                    case "W": {
                        type.setBackgroundColor(0xFFFFC107);
                        break;
                    }
                    default: {
                        type.setBackgroundColor(0xFF000000);
                        type.setText("U");
                        break;
                    }
                }
                log.setText(data.get(_position).get("body").toString());
                try {
                    if (data.get(_position).get("date").toString().equals(data.get(_position + 1).get("date").toString())) {
                        divider.setVisibility(View.GONE);
                        try {
                            if (data.get(_position).get("pkgName").toString().equals(data.get(_position + 1).get("pkgName").toString())) {
                                pkgName.setVisibility(View.GONE);
                            } else {
                                pkgName.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception ignored) {
                            pkgName.setVisibility(View.VISIBLE);
                        }
                        try {
                            if (data.get(_position).get("header").toString().equals(data.get(_position + 1).get("header").toString())) {
                                date_header.setVisibility(View.GONE);
                            } else {
                                date_header.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception ignored) {
                            date_header.setVisibility(View.VISIBLE);
                        }
                    } else {
                        divider.setVisibility(View.VISIBLE);
                    }
                } catch (Exception ignored) {
                    divider.setVisibility(View.VISIBLE);
                }
            } else {
                log.setText(data.get(_position).get("logRaw").toString());
                type.setBackgroundColor(0xFF000000);
                type.setText("U");
                date_header.setVisibility(View.GONE);
                divider.setVisibility(View.VISIBLE);
            }
            clickListener.setOnLongClickListener(_view1 -> {
                SketchwareUtil.toast("Copied to clipboard");
                ((ClipboardManager) getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", data.get(_position).get("logRaw").toString()));
                return true;
            });
        }

        @Override
        public int a() {
            return data.size();
        }

        private class ViewHolder extends RecyclerView.v {
            public ViewHolder(View v) {
                super(v);
            }
        }
    }
}
