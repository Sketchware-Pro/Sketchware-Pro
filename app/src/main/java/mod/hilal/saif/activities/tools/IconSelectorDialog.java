package mod.hilal.saif.activities.tools;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IconSelectorDialog extends Dialog {

    private final ArrayList<Integer> data = new ArrayList<>();
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
        gridView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0.0f
                )
        );
        gridView.setNumColumns(6);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            ed.setText(String.valueOf(data.get(position)));
            dismiss();
        });
        base.addView(gridView);
        for (int i = 2131165190; i < 2131166368; i++) {
            data.add(i);
        }
        //data.subList(data.indexOf(2131165192), data.indexOf(2131165274)).clear();
        gridView.setAdapter(new IconListAdapter(data));
        ((BaseAdapter) gridView.getAdapter()).notifyDataSetChanged();
        if (!ed.getText().toString().isEmpty()) {
            gridView.smoothScrollToPosition(data.indexOf(Integer.parseInt(ed.getText().toString()))
            );
        }

    }

    private View pallette(int resourceId) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0.0f
                )
        );
        linearLayout.setGravity(Gravity.CENTER);
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(
                new LinearLayout.LayoutParams(
                        (int) getDip(50),
                        (int) getDip(50),
                        0.0f
                )
        );
        imageView.setImageResource(resourceId);
        imageView.setPadding(
                (int) getDip(4),
                (int) getDip(4),
                (int) getDip(4),
                (int) getDip(4)
        );
        linearLayout.addView(imageView);
        return linearLayout;
    }

    @Override
    public void show() {
        super.show();
    }

    private class IconListAdapter extends BaseAdapter {

        private final ArrayList<Integer> iconsList;

        public IconListAdapter(ArrayList<Integer> arrayList) {
            iconsList = arrayList;
        }

        @Override
        public int getCount() {
            return iconsList.size();
        }

        @Override
        public Integer getItem(int index) {
            return iconsList.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return pallette(getItem(position));
        }
    }
}