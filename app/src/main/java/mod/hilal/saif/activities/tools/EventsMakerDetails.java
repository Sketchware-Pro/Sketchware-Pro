package mod.hilal.saif.activities.tools;

import static mod.hilal.saif.activities.tools.EventsMaker.EVENTS_FILE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.HashMap;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class EventsMakerDetails extends Activity {

    private final ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
    private AlertDialog.Builder dia;
    private String lisName;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_custom_attribute);
        if (getIntent().hasExtra("lis_name")) {
            lisName = getIntent().getStringExtra("lis_name");
        }
        setToolbar();
        setupViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshList();
    }

    private void setupViews() {
        FloatingActionButton fab = findViewById(R.id.add_attr_fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), EventsMakerCreator.class);
            intent.putExtra("lis_name", lisName);
            startActivity(intent);
        });
        listView = findViewById(R.id.add_attr_listview);
        refreshList();
    }

    private void a(View view, int i, int i2, boolean z) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(new float[]{i, i, i / 2f, i / 2f, i, i, i / 2f, i / 2f});
        gradientDrawable.setColor(Color.parseColor("#ffffff"));
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#20008DCD")}), gradientDrawable, null);
        view.setElevation((float) i2);
        view.setBackground(rippleDrawable);
        view.setClickable(true);
        view.setFocusable(true);
    }

    public void refreshList() {
        listMap.clear();
        if (FileUtil.isExistFile(EVENTS_FILE.getAbsolutePath())) {
            ArrayList<HashMap<String, Object>> events = new Gson()
                    .fromJson(FileUtil.readFile(EVENTS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < events.size(); i++) {
                if (lisName.equals(events.get(i).get("listener"))) {
                    listMap.add(events.get(i));
                }
            }
            listView.setAdapter(new ListAdapter(listMap));
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }

    private void deleteItem(int position) {
        listMap.remove(position);
        if (FileUtil.isExistFile(EVENTS_FILE.getAbsolutePath())) {
            ArrayList<HashMap<String, Object>> events = new Gson()
                    .fromJson(FileUtil.readFile(EVENTS_FILE.getAbsolutePath()), Helper.TYPE_MAP_LIST);
            for (int i = events.size() - 1; i > -1; i--) {
                if (lisName.equals(events.get(i).get("listener"))) {
                    events.remove(i);
                }
            }
            events.addAll(listMap);
            FileUtil.writeFile(EVENTS_FILE.getAbsolutePath(), new Gson().toJson(events));
            refreshList();
        }
    }

    private void setToolbar() {
        TextView tx_toolbar_title = findViewById(R.id.tx_toolbar_title);
        if (lisName.equals("")) {
            tx_toolbar_title.setText("Activity events");
        } else {
            tx_toolbar_title.setText(lisName);
        }
        ImageView back_icon = findViewById(R.id.ig_toolbar_back);
        back_icon.setOnClickListener(Helper.getBackPressedClickListener(this));
        Helper.applyRippleToToolbarView(back_icon);
    }

    private class ListAdapter extends BaseAdapter {

        private final ArrayList<HashMap<String, Object>> _data;

        public ListAdapter(ArrayList<HashMap<String, Object>> arrayList) {
            _data = arrayList;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int position) {
            return _data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.custom_view_pro, parent, false);
            }
            LinearLayout root = convertView.findViewById(R.id.custom_view_pro_background);
            a(root, (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(2), true);
            ImageView icon = convertView.findViewById(R.id.custom_view_pro_img);
            TextView title = convertView.findViewById(R.id.custom_view_pro_title);
            TextView subtitle = convertView.findViewById(R.id.custom_view_pro_subtitle);
            if (lisName.equals("")) {
                icon.setImageResource(R.drawable.widget_source);
            } else {
                icon.setImageResource(Integer.parseInt(_data.get(position).get("icon").toString()));
            }
            ((LinearLayout) icon.getParent()).setGravity(17);
            title.setText((String) _data.get(position).get("name"));
            if ("".equals(_data.get(position).get("var"))) {
                subtitle.setText("Activity event");
            } else {
                subtitle.setText((String) _data.get(position).get("var"));
            }
            root.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), EventsMakerCreator.class);
                intent.putExtra("lis_name", lisName);
                intent.putExtra("event", (String) _data.get(position).get("name"));
                intent.putExtra("_pos", String.valueOf(position));
                intent.putExtra("_name", (String) _data.get(position).get("name"));
                intent.putExtra("_var", (String) _data.get(position).get("var"));
                intent.putExtra("_lis", (String) _data.get(position).get("listener"));
                intent.putExtra("_icon", (String) _data.get(position).get("icon"));
                intent.putExtra("_desc", (String) _data.get(position).get("description"));
                intent.putExtra("_par", (String) _data.get(position).get("parameters"));
                intent.putExtra("_spec", (String) _data.get(position).get("headerSpec"));
                intent.putExtra("_code", (String) _data.get(position).get("code"));
                startActivity(intent);
            });
            root.setOnLongClickListener(v -> {
                dia = new AlertDialog.Builder(EventsMakerDetails.this)
                        .setTitle((String) _data.get(position).get("name"))
                        .setMessage("Delete this event?")
                        .setPositiveButton(R.string.common_word_delete, (dialog, which) -> deleteItem(position))
                        .setNeutralButton(R.string.common_word_edit, (dialog, which) -> {
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), EventsMakerCreator.class);
                            intent.putExtra("lis_name", lisName);
                            intent.putExtra("event", (String) _data.get(position).get("name"));
                            intent.putExtra("_pos", String.valueOf(position));
                            intent.putExtra("_name", (String) _data.get(position).get("name"));
                            intent.putExtra("_var", (String) _data.get(position).get("var"));
                            intent.putExtra("_lis", (String) _data.get(position).get("listener"));
                            intent.putExtra("_icon", (String) _data.get(position).get("icon"));
                            intent.putExtra("_desc", (String) _data.get(position).get("description"));
                            intent.putExtra("_par", (String) _data.get(position).get("parameters"));
                            intent.putExtra("_spec", (String) _data.get(position).get("headerSpec"));
                            intent.putExtra("_code", (String) _data.get(position).get("code"));
                            startActivity(intent);
                        })
                        .setNegativeButton(R.string.common_word_cancel, null);
                dia.show();
                return true;
            });
            return convertView;
        }
    }
}
