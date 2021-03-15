package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import mod.SketchwareUtil;

public class IconSelectorDialog extends Dialog {

    private final ArrayList<HashMap<String, Object>> data = new ArrayList<>();
    private final EditText ed;
    private ViewGroup base;
    private RecyclerView dump;

    public IconSelectorDialog(Activity activity, EditText editText) {
        super(activity);
        ed = editText;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427775);
        dump = findViewById(2131231449);
        base = (ViewGroup) dump.getParent();
        base.removeView(dump);
        setUpViews();
    }

    public void setUpViews() {
        GridView gridView = new GridView(getContext());
        gridView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 0.0f));
        gridView.setNumColumns(6);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ed.setText(String.valueOf(((Integer) data.get(position).get("n")).intValue()));
                dismiss();
            }
        });
        base.addView(gridView);
        for (int i = 2131165190; i < 2131166368; i++) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("n", i);
            data.add(hashMap);
        }
        gridView.setAdapter(new ListAdapter(data));
        ((BaseAdapter) gridView.getAdapter()).notifyDataSetChanged();
    }

    private View pallette(int resourceId) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 0.0f));
        linearLayout.setGravity(17);
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams((int) SketchwareUtil.getDip(50), (int) SketchwareUtil.getDip(50), 0.0f));
        imageView.setImageResource(resourceId);
        imageView.setPadding((int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4), (int) SketchwareUtil.getDip(4));
        linearLayout.addView(imageView);
        return linearLayout;
    }

    public void show() {
        super.show();
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
        public HashMap<String, Object> getItem(int i) {
            return _data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return pallette((Integer) data.get(position).get("n"));
        }
    }
}