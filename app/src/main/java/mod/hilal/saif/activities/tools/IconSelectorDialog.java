package mod.hilal.saif.activities.tools;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

import mod.jbk.util.OldResourceIdMapper;
import pro.sketchware.databinding.DialogIconSelectorBinding;
import pro.sketchware.databinding.ItemIconSelectorBinding;

public class IconSelectorDialog {
    private final Activity activity;
    private final List<Integer> data;
    private final TextView toSetText;
    private AlertDialog builder;

    public IconSelectorDialog(Activity activity, TextView toSetText) {
        this.activity = activity;
        this.toSetText = toSetText;

        data = new ArrayList<>(OldResourceIdMapper.HIGHEST_ID - OldResourceIdMapper.LOWEST_ID);
        for (int i = OldResourceIdMapper.LOWEST_ID; i < OldResourceIdMapper.HIGHEST_ID; i++) {
            data.add(i);
        }
    }

    public void show() {
        DialogIconSelectorBinding dialogBinding = DialogIconSelectorBinding.inflate(LayoutInflater.from(activity));

        GridView gridView = dialogBinding.gridView;
        gridView.setAdapter(new IconListAdapter(data));
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            toSetText.setText(String.valueOf(data.get(position)));
            builder.dismiss();
        });

        String iconIdInput = toSetText.getText().toString();
        if (!iconIdInput.isEmpty() && OldResourceIdMapper.isValidIconId(iconIdInput)) {
            gridView.smoothScrollToPosition(data.indexOf(Integer.parseInt(iconIdInput)));
        }

        builder = new MaterialAlertDialogBuilder(activity)
                .setTitle("Select an icon")
                .setView(dialogBinding.getRoot())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create();
        builder.show();
    }

    private static class IconListAdapter extends BaseAdapter {
        private final List<Integer> iconsList;

        public IconListAdapter(List<Integer> arrayList) {
            iconsList = arrayList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemIconSelectorBinding itemBinding;
            if (convertView == null) {
                itemBinding = ItemIconSelectorBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
                convertView = itemBinding.getRoot();
                convertView.setTag(itemBinding);
            } else {
                itemBinding = (ItemIconSelectorBinding) convertView.getTag();
            }

            int oldResourceId = getItem(position);
            itemBinding.iconImage.setImageResource(
                    OldResourceIdMapper.getDrawableFromOldResourceId(oldResourceId)
            );

            return convertView;
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
    }
}