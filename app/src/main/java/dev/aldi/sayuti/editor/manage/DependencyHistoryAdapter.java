package dev.aldi.sayuti.editor.manage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import pro.sketchware.databinding.ItemDependencyHistoryBinding;

public class DependencyHistoryAdapter extends ArrayAdapter<String> {
    private final DependencyHistoryManager historyManager;
    private final Runnable onHistoryChanged;

    public DependencyHistoryAdapter(Context context, List<String> dependencies, DependencyHistoryManager historyManager, Runnable onHistoryChanged) {
        super(context, 0, dependencies);
        this.historyManager = historyManager;
        this.onHistoryChanged = onHistoryChanged;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemDependencyHistoryBinding binding;

        if (convertView == null) {
            binding = ItemDependencyHistoryBinding.inflate(LayoutInflater.from(getContext()), parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemDependencyHistoryBinding) convertView.getTag();
        }

        String dependency = getItem(position);
        binding.dependencyText.setText(dependency);
        binding.deleteButton.setOnClickListener(v -> {
            historyManager.removeDependency(dependency);
            remove(dependency);
            notifyDataSetChanged();
            if (onHistoryChanged != null) onHistoryChanged.run();
        });

        return convertView;
    }
}