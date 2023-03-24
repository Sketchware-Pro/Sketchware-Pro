package mod.khaled.logcat;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static mod.SketchwareUtil.dpToPx;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
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

import androidx.annotation.NonNull;
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
    private String pkgFilter = "";
    private boolean autoScroll = false;

    private final ArrayList<HashMap<String, Object>> mainList = new ArrayList<>();
    private ArrayList<String> pkgFilterList = new ArrayList<>();

    private EditText filterEdittext;
    private RecyclerView recyclerview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        initializeLogic();
    }

    private void initialize() {
        LinearLayout root = new LinearLayout(this);
        root.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        root.setOrientation(LinearLayout.VERTICAL);
        setContentView(root);

        LinearLayout toolbar = (LinearLayout) getLayoutInflater().inflate(R.layout.toolbar_improved, root, false);
        root.addView(toolbar);
        ImageView back = toolbar.findViewById(R.id.ig_toolbar_back);
        TextView title = toolbar.findViewById(R.id.tx_toolbar_title);
        ImageView optionsMenu = toolbar.findViewById(R.id.ig_toolbar_load_file);

        toolbar.removeView(title);
        optionsMenu.setVisibility(View.VISIBLE);
        optionsMenu.setImageResource(R.drawable.ic_more_vert_white_24dp);

        filterEdittext = new EditText(this);
        {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT, 1f);
            params.leftMargin = dpToPx(8);
            params.topMargin = dpToPx(2);
            params.rightMargin = dpToPx(8);
            params.bottomMargin = dpToPx(2);
            params.gravity = Gravity.CENTER_VERTICAL;
            filterEdittext.setLayoutParams(params);
        }
        filterEdittext.setTextSize(15f);
        filterEdittext.setHint("Search log");
        filterEdittext.setBackgroundTintList(ColorStateList.valueOf(0xffffffff));
        filterEdittext.setTextColor(0xffffffff);
        filterEdittext.setSingleLine(true);
        toolbar.addView(filterEdittext, toolbar.indexOfChild(optionsMenu));

        PopupMenu options = new PopupMenu(this, optionsMenu);
        options.getMenu().add("Clear all");
        options.getMenu().add("Filter by package");
        options.getMenu().add("Auto scroll").setCheckable(true).setChecked(true);
        options.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getTitle().toString()) {
                case "Clear all":
                    mainList.clear();
                    ((Adapter) recyclerview.getAdapter()).deleteAll();
                    break;

                case "Filter by package":
                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle("Filter by package name")
                            .setMessage("For multiple package names, separate them with a comma (,).");
                    final EditText _e = new EditText(this);
                    _e.setText(pkgFilter);
                    builder.setView(_e);
                    builder.setPositiveButton("Apply", (dialog, which) -> {
                        pkgFilter = _e.getText().toString();
                        pkgFilterList = new ArrayList<>(Arrays.asList(pkgFilter.split(",")));
                        filterEdittext.setText(filterEdittext.getText().toString());
                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                    builder.show();
                    break;

                case "Auto scroll": {
                    menuItem.setChecked(!menuItem.isChecked());
                    autoScroll = menuItem.isChecked();
                    if (autoScroll) {
                        recyclerview.getLayoutManager().scrollToPosition(recyclerview.getAdapter().getItemCount() - 1);
                    }
                    break;
                }
            }
            return true;
        });

        recyclerview = new RecyclerView(this);
        recyclerview.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        recyclerview.setPadding(dpToPx(4), 0, dpToPx(4), 0);
        root.addView(recyclerview);

        back.setOnClickListener(Helper.getBackPressedClickListener(this));
        Helper.applyRippleToToolbarView(back);

        filterEdittext.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String _charSeq = s.toString();
                if (_charSeq.equals("") && (pkgFilterList.size() == 0)) {
                    recyclerview.setAdapter(new Adapter(mainList));
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
                    recyclerview.setAdapter(new Adapter(filteredList));
                }
            }
        });

        optionsMenu.setOnClickListener(v -> options.show());
        Helper.applyRippleToToolbarView(optionsMenu);
    }

    private void initializeLogic() {
        recyclerview.setAdapter(new Adapter(new ArrayList<>()));
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
                if (intent.hasExtra("packageName")) {
                    map.put("pkgName", intent.getStringExtra("packageName"));
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
                            ((Adapter) recyclerview.getAdapter()).updateList(map);
                        }
                    } else {
                        ((Adapter) recyclerview.getAdapter()).updateList(map);
                    }
                } else if (map.containsKey("pkgName") && pkgFilterList.contains(map.get("pkgName").toString())) {
                    if (!filterEdittext.getText().toString().equals("")) {
                        if (map.get("logRaw").toString().toLowerCase().contains(filterEdittext.getText().toString().toLowerCase())) {
                            ((Adapter) recyclerview.getAdapter()).updateList(map);
                        }
                    } else {
                        ((Adapter) recyclerview.getAdapter()).updateList(map);
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

    private class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private final ArrayList<HashMap<String, Object>> data;

        public void updateList(final HashMap<String, Object> _map) {
            data.add(_map);
            recyclerview.getAdapter().notifyItemInserted(data.size() + 1);

            if (autoScroll) {
                ((LinearLayoutManager) recyclerview.getLayoutManager()).scrollToPosition(data.size() - 1);
            }
        }

        public void deleteAll() {
            data.clear();
            recyclerview.getAdapter().notifyDataSetChanged();
        }

        public Adapter(ArrayList<HashMap<String, Object>> data) {
            this.data = data;
        }

        @Override
        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
            divider.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, dpToPx(1)));
            divider.setBackgroundColor(0xffe0e0e0);

            TextView type = new TextView(LogReaderActivity.this);
            type.setTag("type");
            type.setFocusable(false);
            type.setClickable(false);
            type.setText("U");
            type.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            type.setTypeface(null, Typeface.BOLD);
            type.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            type.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(22), MATCH_PARENT));
            type.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
            type.setTextColor(0xffffffff);
            type.setBackgroundColor(0xff000000);

            final LinearLayout detailHolder = new LinearLayout(LogReaderActivity.this);
            detailHolder.setTag("detailHolder");
            detailHolder.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            detailHolder.setOrientation(LinearLayout.VERTICAL);
            detailHolder.setPadding(dpToPx(3), dpToPx(3), dpToPx(3), dpToPx(3));
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
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            if (data.get(position).containsKey("pkgName")) {
                holder.packageName.setText(data.get(position).get("pkgName").toString());
                holder.packageName.setVisibility(View.VISIBLE);
            } else {
                holder.packageName.setVisibility(View.GONE);
            }
            if (data.get(position).containsKey("culturedLog")) {
                holder.date.setVisibility(View.VISIBLE);
                holder.type.setText(data.get(position).get("type").toString());
                holder.date.setText(data.get(position).get("date").toString() + " | " + (data.get(position).get("header").toString()));
                switch (data.get(position).get("type").toString()) {
                    case "A":
                        holder.type.setBackgroundColor(0xFF9C27B0);
                        break;

                    case "D":
                        holder.type.setBackgroundColor(0xFF2196F3);
                        break;

                    case "E":
                        holder.type.setBackgroundColor(0xFFF44336);
                        break;

                    case "I":
                        holder.type.setBackgroundColor(0xFF4CAF50);
                        break;

                    case "V":
                        holder.type.setBackgroundColor(0xFF000000);
                        break;

                    case "W":
                        holder.type.setBackgroundColor(0xFFFFC107);
                        break;

                    default:
                        holder.type.setBackgroundColor(0xFF000000);
                        holder.type.setText("U");
                }
                holder.logText.setText(data.get(position).get("body").toString());
                try {
                    if (data.get(position).get("date").toString().equals(data.get(position + 1).get("date").toString())) {
                        holder.divider.setVisibility(View.GONE);
                        try {
                            if (data.get(position).get("pkgName").toString().equals(data.get(position + 1).get("pkgName").toString())) {
                                holder.packageName.setVisibility(View.GONE);
                            } else {
                                holder.packageName.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception ignored) {
                            holder.packageName.setVisibility(View.VISIBLE);
                        }
                        try {
                            if (data.get(position).get("header").toString().equals(data.get(position + 1).get("header").toString())) {
                                holder.date.setVisibility(View.GONE);
                            } else {
                                holder.date.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception ignored) {
                            holder.date.setVisibility(View.VISIBLE);
                        }
                    } else {
                        holder.divider.setVisibility(View.VISIBLE);
                    }
                } catch (Exception ignored) {
                    holder.divider.setVisibility(View.VISIBLE);
                }
            } else {
                holder.logText.setText(data.get(position).get("logRaw").toString());
                holder.type.setBackgroundColor(0xFF000000);
                holder.type.setText("U");
                holder.date.setVisibility(View.GONE);
                holder.divider.setVisibility(View.VISIBLE);
            }
            holder.root.setOnLongClickListener(v -> {
                SketchwareUtil.toast("Copied to clipboard");
                ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", data.get(position).get("logRaw").toString()));
                return true;
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {

            public final LinearLayout root;
            public final LinearLayout divider;
            public final TextView type;
            public final LinearLayout details;
            public final TextView date;
            public final TextView logText;
            public final TextView packageName;

            public ViewHolder(View v) {
                super(v);
                root = v.findViewWithTag("clickListener");
                divider = v.findViewWithTag("divider");
                type = v.findViewWithTag("type");
                details = v.findViewWithTag("detailHolder");
                date = v.findViewWithTag("date_header");
                logText = v.findViewWithTag("log");
                packageName = v.findViewWithTag("pkgName");
            }
        }
    }
}
