package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;
import java.util.HashMap;

import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class EventsMakerDetails extends Activity {

    private final ArrayList<HashMap<String, Object>> listMap = new ArrayList<>();
    private ViewGroup base;
    private AlertDialog.Builder dia;
    private FloatingActionButton fab;
    private String lisName;
    private ListView listView;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427795);
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
        fab = findViewById(2131232439);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), EventsMakerCreator.class);
                intent.putExtra("lis_name", lisName);
                startActivity(intent);
            }
        });
        listView = findViewById(2131232438);
        refreshList();
    }

    private void a(View view, int i, int i2, boolean z) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setCornerRadii(new float[]{(float) i, (float) i, ((float) i) / 2.0f, ((float) i) / 2.0f, (float) i, (float) i, ((float) i) / 2.0f, ((float) i) / 2.0f});
        gradientDrawable.setColor(Color.parseColor("#ffffff"));
        RippleDrawable rippleDrawable = new RippleDrawable(new ColorStateList(new int[][]{new int[0]}, new int[]{Color.parseColor("#20008DCD")}), gradientDrawable, null);
        view.setElevation((float) i2);
        view.setBackground(rippleDrawable);
        view.setClickable(true);
        view.setFocusable(true);
    }

    public void refreshList() {
        listMap.clear();
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        if (FileUtil.isExistFile(concat)) {
            ArrayList<HashMap<String, Object>> arrayList = new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).get("listener").equals(lisName)) {
                    listMap.add(arrayList.get(i));
                }
            }
            listView.setAdapter(new ListAdapter(listMap));
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }

    private void deleteItem(int position) {
        listMap.remove(position);
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/system/events.json");
        if (FileUtil.isExistFile(concat)) {
            ArrayList<HashMap<String, Object>> arrayList = new Gson().fromJson(FileUtil.readFile(concat), Helper.TYPE_MAP_LIST);
            for (int size = arrayList.size() - 1; size > -1; size--) {
                if (arrayList.get(size).get("listener").equals(lisName)) {
                    arrayList.remove(size);
                }
            }
            arrayList.addAll(listMap);
            FileUtil.writeFile(concat, new Gson().toJson(arrayList));
            refreshList();
        }
    }

    public void setToolbar() {
        if (lisName.equals("")) {
            ((TextView) findViewById(2131232458)).setText("Activity events");
        } else {
            ((TextView) findViewById(2131232458)).setText(lisName);
        }
        ImageView back_icon = findViewById(2131232457);
        back_icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
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
                convertView = getLayoutInflater().inflate(2131427802, null);
            }
            LinearLayout linearLayout = convertView.findViewById(2131232468);
            a(linearLayout, (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(2), true);
            ImageView imageView = convertView.findViewById(2131232469);
            TextView textView = convertView.findViewById(2131232470);
            TextView textView2 = convertView.findViewById(2131232471);
            if (lisName.equals("")) {
                imageView.setImageResource(2131166270);
            } else {
                imageView.setImageResource(Integer.parseInt(_data.get(position).get("icon").toString()));
            }
            ((LinearLayout) imageView.getParent()).setGravity(17);
            textView.setText((String) _data.get(position).get("name"));
            if (_data.get(position).get("var").equals("")) {
                textView2.setText("Activity event");
            } else {
                textView2.setText((String) _data.get(position).get("var"));
            }
            linearLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
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
                }
            });
            linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View v) {
                    dia = new AlertDialog.Builder(EventsMakerDetails.this)
                            .setTitle((String) _data.get(position).get("name"))
                            .setMessage("Delete this event?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteItem(position);
                                }
                            })
                            .setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int which) {
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
                                }
                            })
                            .setNegativeButton("Cancel", null);
                    dia.show();
                    return true;
                }
            });
            return convertView;
        }
    }
}