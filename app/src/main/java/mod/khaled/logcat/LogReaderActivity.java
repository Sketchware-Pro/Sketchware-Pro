package mod.khaled.logcat;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import com.sketchware.remod.Resources;
import com.sketchware.remod.sources.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mod.SketchwareUtil;

public class LogReaderActivity extends AppCompatActivity {

    private final BroadcastReceiver logger = new logger();
    private final Pattern logPattern = Pattern.compile("^(.*\\d) (A|D|E|I|W) (.*): (.*)");
    private PopupMenu options;
    private String pkgFilter = "";
    private boolean autoScroll = false;

    private final ArrayList<HashMap<String, Object>> mainList = new ArrayList<>();
    private ArrayList<String> pkgFilterList = new ArrayList<>();

    private RecyclerView recyclerview;
    private EditText filterEdittext;

    private AlertDialog.Builder filterPkgDialog;

    @Override
    public void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(Resources.layout.logreader);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        recyclerview = findViewById(R.id.recyclerview);
        filterEdittext = findViewById(R.id.filterEdittext);
        ImageView menu = findViewById(R.id.menu);
        options = new PopupMenu(getApplicationContext(), menu);
        options.getMenu().add("Clear All");
        options.getMenu().add("Filter Package");
        options.getMenu().add("Auto Scroll").setCheckable(true).setChecked(true);
        options.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getTitle().toString()) {
                    case "Clear All": {
                        mainList.clear();
                        ((RecyclerviewAdapter) recyclerview.getAdapter()).deleteAll();
                        break;
                    }
                    case "Filter Package": {
                        filterPkgDialog.setTitle("Filter By Packages");
                        filterPkgDialog.setMessage("Use Comma(,) for Multiple PackageName");
                        final EditText _e = new EditText(LogReaderActivity.this);
                        _e.setText(pkgFilter);
                        filterPkgDialog.setView(_e);
                        filterPkgDialog.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface _dialog, int _which) {
                                pkgFilter = _e.getText().toString();
                                pkgFilterList = new ArrayList<String>(Arrays.asList(pkgFilter.split(",")));
                                filterEdittext.setText(filterEdittext.getText().toString());
                            }
                        });
                        filterPkgDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface _dialog, int _which) {
                                _dialog.dismiss();
                            }
                        });
                        filterPkgDialog.create().show();
                        break;
                    }
                    case "Auto Scroll": {
                        menuItem.setChecked(!menuItem.isChecked());
                        autoScroll = menuItem.isChecked();
                        if (autoScroll) {
                            ((LinearLayoutManager) recyclerview.getLayoutManager()).c((int) (((RecyclerviewAdapter) recyclerview.getAdapter()).a() - 1), (int) 0);
                            //scrollToPositionWithOffset
                        }
                        break;
                    }
                }
                return true;
            }
        });
        filterPkgDialog = new AlertDialog.Builder(this);

        filterEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
                final String _charSeq = _param1.toString();
                if (_charSeq.equals("") && (pkgFilterList.size() == 0)) {
                    recyclerview.setAdapter(new RecyclerviewAdapter(mainList));
                } else {
                    ArrayList<HashMap<String, Object>> filteredList = new ArrayList();
                    for (HashMap m : mainList) {
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

            @Override
            public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {

            }

            @Override
            public void afterTextChanged(Editable _param1) {

            }
        });

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                options.show();
            }
        });
    }

    private void initializeLogic() {
        recyclerview.setAdapter(new RecyclerviewAdapter(new ArrayList<HashMap<String, Object>>()));
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        autoScroll = true;
        IntentFilter nub = new IntentFilter();
        nub.addAction("RECEIVE_NUB_LOGS");
        registerReceiver(logger, nub);
    }

    public class logger extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            HashMap map = new HashMap<>();
            if (intent.hasExtra("log")) {
                if (intent.hasExtra("pkgName")) {
                    map.put("pkgName", intent.getStringExtra("pkgName"));
                }
                map.put("logRaw", intent.getStringExtra("log"));
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
                } else {
                    if (map.containsKey("pkgName") && pkgFilterList.contains(map.get("pkgName").toString())) {
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


    }

    @Override
    public void onStop() {
        super.onStop();
        filterEdittext.clearFocus();
    }

    public class RecyclerviewAdapter extends RecyclerView.a<RecyclerviewAdapter.ViewHolder> {

        ArrayList<HashMap<String, Object>> _data;

        public void updateList(final HashMap _map) {

            _data.add(_map);
            ((RecyclerviewAdapter) recyclerview.getAdapter()).d(_data.size() + 1); //notifyItemInserted

            if (autoScroll) {
                ((LinearLayoutManager) recyclerview.getLayoutManager()).c((int) _data.size() - 1, (int) 0); //scrollToPositionWithOffset
            }
        }

        public void deleteAll() {

            _data.clear();
            ((RecyclerviewAdapter) recyclerview.getAdapter()).c();
            //notifyDataSetChanged();
        }

        public RecyclerviewAdapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public ViewHolder b(ViewGroup parent, int viewType) {
            LayoutInflater _inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View _v = _inflater.inflate(Resources.layout.log_list_customview, null);
            RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            _v.setLayoutParams(_lp);
            return new ViewHolder(_v);
        }

        @Override
        public void b(ViewHolder _holder, final int _position) {
            View _view = _holder.b;

            final LinearLayout clickListener = _view.findViewById(R.id.clickListener);
            final LinearLayout divider = _view.findViewById(R.id.divider2);
            final TextView type = _view.findViewById(R.id.log_type);
            final TextView date_header = _view.findViewById(R.id.date_header);
            final TextView log = _view.findViewById(R.id.log);
            final TextView pkgName = _view.findViewById(R.id.pkgName);

            if (_data.get((int) (_position)).containsKey("pkgName")) {
                pkgName.setText(_data.get((int) _position).get("pkgName").toString());
                pkgName.setVisibility(View.VISIBLE);
            } else {
                pkgName.setVisibility(View.GONE);
            }
            if (_data.get((int) (_position)).containsKey("culturedLog")) {
                date_header.setVisibility(View.VISIBLE);
                type.setText(_data.get((int) _position).get("type").toString());
                date_header.setText(_data.get((int) _position).get("date").toString().concat(" | ".concat(_data.get((int) _position).get("header").toString())));
                switch (_data.get((int) _position).get("type").toString()) {
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
                log.setText(_data.get((int) _position).get("body").toString());
                try {
                    if (_data.get((int) _position).get("date").toString().equals(_data.get((int) _position + 1).get("date").toString())) {
                        divider.setVisibility(View.GONE);
                        try {
                            if (_data.get((int) _position).get("pkgName").toString().equals(_data.get((int) _position + 1).get("pkgName").toString())) {
                                pkgName.setVisibility(View.GONE);
                            } else {
                                pkgName.setVisibility(View.VISIBLE);
                            }
                        } catch (Exception ignored) {
                            pkgName.setVisibility(View.VISIBLE);
                        }
                        try {
                            if (_data.get((int) _position).get("header").toString().equals(_data.get((int) _position + 1).get("header").toString())) {
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
                log.setText(_data.get((int) _position).get("logRaw").toString());
                type.setBackgroundColor(0xFF000000);
                type.setText("U");
                date_header.setVisibility(View.GONE);
                divider.setVisibility(View.VISIBLE);
            }
            clickListener.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View _view) {
                    SketchwareUtil.showMessage(getApplicationContext(), "Copied To Clipboard");
                    ((ClipboardManager) getSystemService(getApplicationContext().CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText("clipboard", log.getText().toString()));
                    return true;
                }
            });
        }


        @Override
        public int a() {
            return _data.size();
        }


        public class ViewHolder extends RecyclerView.v {
            public ViewHolder(View v) {
                super(v);
            }
        }
    }
}
