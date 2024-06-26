package mod.remaker.settings.fragment;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;
import static mod.remaker.util.SettingsUtils.getPermissionContract;
import static mod.remaker.util.UriUtils.resolveUri;
import static java.util.Objects.requireNonNull;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;

import com.sketchware.remod.databinding.ChangeBackupDirectoryFragmentBinding;

import java.io.File;
import java.nio.file.AccessDeniedException;
import java.util.List;

import mod.SketchwareUtil;
import mod.remaker.settings.BackupDirectoryManager;
import mod.remaker.settings.PreferenceFragment;
import mod.remaker.settings.adapter.BackupDirectoryAdapter;
import mod.remaker.settings.adapter.BackupDirectoryAdapter.BackupDirectoryAction;
import mod.remaker.settings.model.BackupDirectory;
import mod.remaker.util.SettingsContracts.PickDirectory;
import mod.remaker.util.UriUtils;

public class ChangeBackupDirectoryFragment extends PreferenceFragment implements BackupDirectoryAction {
    private ActivityResultLauncher<Uri> pickDirectoryLauncher =
        registerForActivityResult(new PickDirectory(), this::onCustomDirectoryPicked);

    private ActivityResultLauncher<String> permissionRequestLauncher =
        registerForActivityResult(getPermissionContract(), isGranted -> {
            if (isGranted) {
                refreshBackupDirectories();
                pickDirectoryLauncher.launch(null);
            } else {
                SketchwareUtil.toastError("No permission to write files.");
            }
        });

    private BackupDirectoryAdapter adapter;
    private ChangeBackupDirectoryFragmentBinding binding;
    private BackupDirectorySelectListener onBackupDirectorySelectListener;

    public interface BackupDirectorySelectListener {
        void onDirectorySelect(BackupDirectory directory);
    }

    @Override
    public String getTitle(Context context) {
        return "Backup Directory";
    }

    @Override
    public View getContentView(LayoutInflater inflater, ViewGroup container) {
        binding = ChangeBackupDirectoryFragmentBinding.inflate(inflater, container, false);
        adapter = new BackupDirectoryAdapter();

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
        if (TextUtils.isEmpty(directory.path())) {
            permissionRequestLauncher.launch(WRITE_EXTERNAL_STORAGE);
        } else {
            if (onBackupDirectorySelectListener != null) {
                onBackupDirectorySelectListener.onDirectorySelect(directory);
            }

            BackupDirectoryManager.changeDefaultBackupDirectory(directory);
        }
    }

    public void setOnBackupDirectorySelectListener(BackupDirectorySelectListener listener) {
        onBackupDirectorySelectListener = listener;
    }

    private void refreshBackupDirectories() {
        List<BackupDirectory> backupDirectories = BackupDirectoryManager.getBackupDirectories();
        backupDirectories.add(new BackupDirectory("Choose another directory", null));

        adapter.submitList(backupDirectories);
    }

    private void onCustomDirectoryPicked(Uri uri) {
        if (uri == null) {
            return;
        }

        new Thread(() -> {
            UriUtils.takePermissions(getContext(), uri, FLAG_GRANT_READ_URI_PERMISSION | FLAG_GRANT_WRITE_URI_PERMISSION);
            File file = requireWriteableNonNull(resolveUri(getContext(), uri), uri);
            BackupDirectory directory = new BackupDirectory(file.getName(), file.getAbsolutePath());

            BackupDirectoryManager.addBackupDirectory(directory, (success, message) -> {
                getActivity().runOnUiThread(() -> {
                    if (!TextUtils.isEmpty(message)) {
                        if (success) {
                            SketchwareUtil.toast(message);
                        } else {
                            SketchwareUtil.toastError(message);
                        }
                    }
                });
            });
            BackupDirectoryManager.changeDefaultBackupDirectory(directory);

            getActivity().runOnUiThread(() -> {
                if (onBackupDirectorySelectListener != null) {
                    onBackupDirectorySelectListener.onDirectorySelect(directory);
                }

                refreshBackupDirectories();
            });
        }).start();
    }

    private File requireWriteableNonNull(File f, Uri uri) {
        File file = requireNonNull(f, "Cannot resolve file name of '" + uri + "'");
        try {
            if (!file.canWrite()) {
                throw new AccessDeniedException(file.toString());
            }
        } catch (AccessDeniedException e) {
            throw new IllegalStateException(e);
        }
        return file;
    }
}
