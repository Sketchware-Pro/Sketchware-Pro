package mod.remaker.settings.adapter;

import static mod.hilal.saif.activities.tools.ConfigActivity.isCurrentBackupDirectory;
import static mod.remaker.settings.model.BackupDirectory.DIFF_CALLBACK;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.sketchware.remod.databinding.BackupDirectoryItemBinding;

import mod.remaker.settings.model.BackupDirectory;

public class BackupDirectoryAdapter extends ListAdapter<BackupDirectory, BackupDirectoryAdapter.BackupDirectoryViewHolder> {
    private BackupDirectoryAction action;

    public BackupDirectoryAdapter() {
        super(DIFF_CALLBACK);
    }

    public interface BackupDirectoryAction {
        void onClick(BackupDirectory directory);
    }

    @NonNull
    @Override
    public BackupDirectoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        BackupDirectoryItemBinding binding = BackupDirectoryItemBinding.inflate(inflater, parent, false);
        return new BackupDirectoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BackupDirectoryViewHolder holder, int position) {
        holder.bindTo(getItem(position), action);
    }

    public void setOnBackupDirectoryAction(BackupDirectoryAction action) {
        this.action = action;
    }

    public static class BackupDirectoryViewHolder extends ViewHolder {
        private BackupDirectoryItemBinding binding;

        BackupDirectoryViewHolder(BackupDirectoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindTo(BackupDirectory directory, BackupDirectoryAction action) {
            String path = directory.path();
            boolean isChecked = isCurrentBackupDirectory(directory);

            binding.getRoot().setOnClickListener(v -> action.onClick(directory));
            binding.name.setText(directory.name());
            binding.indicator.setChecked(isChecked);

            if (TextUtils.isEmpty(path)) {
                binding.path.setVisibility(View.GONE);
            } else {
                binding.path.setText(path);
                binding.path.setVisibility(View.VISIBLE);
            }
        }
    }
}
