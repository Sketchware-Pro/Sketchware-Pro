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

import com.sketchware.remod.R;

import java.util.ArrayList;
import java.util.List;

public class IconSelectorDialog extends Dialog {

    private final List<Integer> data;
    private final EditText ed;
    private ViewGroup base;

    public IconSelectorDialog(Activity activity, EditText editText) {
        super(activity);
        ed = editText;

        data = new ArrayList<>(2131166368 - 2131165190);
        for (int i = 2131165190; i < 2131166368; i++) {
            data.add(i);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_events);

        RecyclerView dump = findViewById(R.id.list_events);
        base = (ViewGroup) dump.getParent();
        base.removeView(dump);
        setUpViews();
    }

    public void setUpViews() {
        GridView gridView = new GridView(getContext());
        gridView.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
        gridView.setNumColumns(6);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            ed.setText(String.valueOf(data.get(position)));
            dismiss();
        });
        base.addView(gridView);
        gridView.setAdapter(new IconListAdapter(data));
        ((BaseAdapter) gridView.getAdapter()).notifyDataSetChanged();
        if (!ed.getText().toString().isEmpty()) {
            gridView.smoothScrollToPosition(data.indexOf(Integer.parseInt(ed.getText().toString())));
        }
    }

    private View createIcon(int resourceId) {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setGravity(Gravity.CENTER);
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(
                        (int) getDip(50),
                        (int) getDip(50)));
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

    private class IconListAdapter extends BaseAdapter {

        private final List<Integer> iconsList;

        public IconListAdapter(List<Integer> arrayList) {
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
            return createIcon(getItem(position));
        }
    }
}
