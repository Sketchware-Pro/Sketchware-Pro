package mod.remaker.settings.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sketchware.remod.databinding.ChangeBackupDirectoryFragmentBinding;

import java.util.List;

import mod.remaker.settings.BackupDirectoryManager;
import mod.remaker.settings.PreferenceFragment;
import mod.remaker.settings.adapter.BackupDirectoryAdapter;
import mod.remaker.settings.adapter.BackupDirectoryAdapter.BackupDirectoryAction;
import mod.remaker.settings.model.BackupDirectory;

public class ChangeBackupDirectoryFragment extends PreferenceFragment implements BackupDirectoryAction {
    private BackupDirectoryAdapter adapter;
    private ChangeBackupDirectoryFragmentBinding binding;
    private BackupDirectoryManager manager;

    @Override
    public String getTitle(Context context) {
        return "Backup Directory";
    }

    @Override
    public View getContentView(LayoutInflater inflater, ViewGroup container) {
        binding = ChangeBackupDirectoryFragmentBinding.inflate(inflater, container, false);
        adapter = new BackupDirectoryAdapter();
        manager = new BackupDirectoryManager();

        adapter.setOnBackupDirectoryAction(this);
        binding.recyclerView.setAdapter(adapter);
        refreshBackupDirectories();

        return binding.getRoot();
    }

    @Override
    public int getScrollTargetViewId() {
        return binding.recyclerView.getId();
    }

    @Override
    public void onClick(BackupDirectory directory) {
    }

    private void refreshBackupDirectories() {
        List<BackupDirectory> backupDirectories = manager.getBackupDirectories();
        backupDirectories.add(new BackupDirectory("Choose another directory", null));

        adapter.submitList(backupDirectories);
    }
}
