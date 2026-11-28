package mod.hey.studios.project.backup;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import a.a.a.lC;
import dev.pranav.filepicker.FilePickerCallback;
import dev.pranav.filepicker.FilePickerDialogFragment;
import dev.pranav.filepicker.FilePickerOptions;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.activities.main.fragments.projects.ProjectsFragment;
import pro.sketchware.databinding.ProgressMsgBoxBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class BackupRestoreManager {

    private final Activity act;

    // Needed to refresh the project list after restoring
    private ProjectsFragment projectsFragment;

    private HashMap<Integer, Boolean> backupDialogStates;

    public BackupRestoreManager(Activity act) {
        this.act = act;
    }

    public BackupRestoreManager(Activity act, ProjectsFragment projectsFragment) {
        this.act = act;
        this.projectsFragment = projectsFragment;
    }

    public static String getRestoreIntegratedLocalLibrariesMessage(boolean restoringMultipleBackups, int currentRestoringIndex, int totalAmountOfBackups, String filename) {
        if (!restoringMultipleBackups) {
            return Helper.getResString(R.string.backup_restore_local_libs_message);
        } else {
            return Helper.getResString(R.string.backup_restore_local_libs_message_multiple, filename, currentRestoringIndex + 1, totalAmountOfBackups);
        }
    }

    public void backup(String sc_id, String project_name) {
        final String localLibrariesTag = "local libraries";
        final String customBlocksTag = "Custom Blocks";
        backupDialogStates = new HashMap<>();
        backupDialogStates.put(0, false);
        backupDialogStates.put(1, false);

        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(act);
        dialog.setIcon(R.drawable.ic_backup);
        dialog.setTitle(R.string.common_word_backup_options);

        LinearLayout checkboxContainer = new LinearLayout(act);
        checkboxContainer.setOrientation(LinearLayout.VERTICAL);
        checkboxContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        int dip = (int) SketchwareUtil.getDip(8);
        checkboxContainer.setPadding(dip, dip, dip, dip);

        CompoundButton.OnCheckedChangeListener listener = (buttonView, isChecked) -> {
            int index;
            Object tag = buttonView.getTag();
            if (tag instanceof String) {
                switch ((String) tag) {
                    case localLibrariesTag:
                        index = 0;
                        break;

                    case customBlocksTag:
                        index = 1;
                        break;

                    default:
                        return;
                }
                backupDialogStates.put(index, isChecked);
            }
        };

        CheckBox includeLocalLibraries = new CheckBox(act);
        includeLocalLibraries.setTag(localLibrariesTag);
        includeLocalLibraries.setText(R.string.backup_include_used_local_libraries);
        includeLocalLibraries.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        includeLocalLibraries.setOnCheckedChangeListener(listener);
        checkboxContainer.addView(includeLocalLibraries);

        CheckBox includeUsedCustomBlocks = new CheckBox(act);
        includeUsedCustomBlocks.setTag(customBlocksTag);
        includeUsedCustomBlocks.setText(R.string.backup_include_used_custom_blocks);
        includeUsedCustomBlocks.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        includeUsedCustomBlocks.setOnCheckedChangeListener(listener);
        checkboxContainer.addView(includeUsedCustomBlocks);

        dialog.setView(checkboxContainer);
        dialog.setPositiveButton(R.string.common_word_back_up, (v, which) -> {
            v.dismiss();
            doBackup(sc_id, project_name);
        });
        dialog.setNegativeButton(R.string.common_word_cancel, null);
        dialog.show();
    }

    private void doBackup(String sc_id, String project_name) {
        new BackupAsyncTask(new WeakReference<>(act), sc_id, project_name, backupDialogStates)
                .execute("");
    }

    /*** Restore ***/

    public void restore() {
        FilePickerOptions options = new FilePickerOptions();
        options.setMultipleSelection(true);
        options.setExtensions(new String[]{BackupFactory.EXTENSION});
        options.setTitle(Helper.getResString(R.string.backup_select_backups_to_restore, BackupFactory.EXTENSION));

        FilePickerCallback callback = new FilePickerCallback() {
            @Override
            public void onFilesSelected(@NotNull List<? extends File> files) {
                // Sequential queue: one confirmation prompt (and restore) at a time,
                // advanced from RestoreAsyncTask.onPostExecute via processNextBackup.
                // The old loop posted every dialog at once, stacking N prompts.
                processNextBackup(files, 0);
            }
        };

        new FilePickerDialogFragment(options, callback).show(projectsFragment.getChildFragmentManager(), "file_picker");
    }

    private final ExecutorService restoreQueueExecutor = Executors.newSingleThreadExecutor();

    private void processNextBackup(List<? extends File> files, int index) {
        if (index >= files.size()) return;
        File backupFile = files.get(index);
        // Scan the archive off the UI thread, then prompt.
        restoreQueueExecutor.execute(() -> {
            boolean hasLocalLibs = BackupFactory.zipContainsFile(backupFile.getAbsolutePath(), "local_libs");
            act.runOnUiThread(() -> showRestorePrompt(files, index, hasLocalLibs));
        });
    }

    private void showRestorePrompt(List<? extends File> files, int index, boolean hasLocalLibs) {
        if (act.isFinishing() || act.isDestroyed()) {
            return;
        }
        String backupFilePath = files.get(index).getAbsolutePath();
        Runnable next = () -> processNextBackup(files, index + 1);
        if (hasLocalLibs) {
            new MaterialAlertDialogBuilder(act)
                    .setTitle(R.string.common_word_warning)
                    .setMessage(getRestoreIntegratedLocalLibrariesMessage(files.size() > 1, index, files.size(),
                            FileUtil.getFileNameNoExtension(backupFilePath)))
                    .setPositiveButton(R.string.common_word_copy, (dialog, which) -> doRestore(backupFilePath, true, next))
                    .setNegativeButton(R.string.backup_dont_copy, (dialog, which) -> doRestore(backupFilePath, false, next))
                    .setNeutralButton(R.string.common_word_cancel, (dialog, which) -> next.run())
                    // Back also cancels the dialog without a button callback; the queue must
                    // advance (Cancel semantics) or the remaining backups would be stranded.
                    .setOnCancelListener(dialog -> next.run())
                    .show();
        } else {
            doRestore(backupFilePath, false, next);
        }
    }

    public void doRestore(String file, boolean restoreLocalLibs) {
        doRestore(file, restoreLocalLibs, null);
    }

    public void doRestore(String file, boolean restoreLocalLibs, Runnable onComplete) {
        new RestoreAsyncTask(new WeakReference<>(act), file, restoreLocalLibs, projectsFragment, onComplete).execute("");
    }

    private static class BackupAsyncTask extends AsyncTask<String, Integer, String> {

        private final String sc_id;
        private final String project_name;
        private final HashMap<Integer, Boolean> options;
        private final WeakReference<Activity> activityWeakReference;
        private BackupFactory bm;
        private AlertDialog dlg;

        BackupAsyncTask(WeakReference<Activity> activityWeakReference, String sc_id, String project_name, HashMap<Integer, Boolean> options) {
            this.activityWeakReference = activityWeakReference;
            this.sc_id = sc_id;
            this.project_name = project_name;
            this.options = options;
        }

        @Override
        protected void onPreExecute() {
            // The activity may already be collected (config change / finish) - bail instead
            // of null-dereferencing the weak reference while building the dialog.
            if (activityWeakReference.get() == null) return;
            ProgressMsgBoxBinding loadingDialogBinding = ProgressMsgBoxBinding.inflate(LayoutInflater.from(activityWeakReference.get()));
            loadingDialogBinding.tvProgress.setText(R.string.backup_creating_backup);
            dlg = new MaterialAlertDialogBuilder(activityWeakReference.get())
                    .setTitle(R.string.common_word_please_wait)
                    .setCancelable(false)
                    .setView(loadingDialogBinding.getRoot())
                    .create();
            dlg.show();
        }

        @Override
        protected String doInBackground(String... params) {
            bm = new BackupFactory(sc_id);
            bm.setBackupLocalLibs(options.get(0));
            bm.setBackupCustomBlocks(options.get(1));

            bm.backup(activityWeakReference.get(), project_name);

            return "";
        }

        @Override
        protected void onPostExecute(String _result) {
            dlg.dismiss();

            if (bm.getOutFile() != null) {
                SketchwareUtil.toast(Helper.getResString(R.string.backup_created_to, bm.getOutFile().getAbsolutePath()));
            } else {
                SketchwareUtil.toastError(Helper.getResString(R.string.backup_error_with_message, bm.error), Toast.LENGTH_LONG);
            }
        }
    }

    private static class RestoreAsyncTask extends AsyncTask<String, Integer, String> {

        private final WeakReference<Activity> activityWeakReference;
        private final String file;
        private final ProjectsFragment projectsFragment;
        private final boolean restoreLocalLibs;
        private BackupFactory bm;
        private AlertDialog dlg;
        private boolean error = false;
        private final Runnable onComplete;

        RestoreAsyncTask(WeakReference<Activity> activityWeakReference, String file, boolean restoreLocalLibraries, ProjectsFragment projectsFragment) {
            this(activityWeakReference, file, restoreLocalLibraries, projectsFragment, null);
        }

        RestoreAsyncTask(WeakReference<Activity> activityWeakReference, String file, boolean restoreLocalLibraries, ProjectsFragment projectsFragment, Runnable onComplete) {
            this.activityWeakReference = activityWeakReference;
            this.file = file;
            this.projectsFragment = projectsFragment;
            restoreLocalLibs = restoreLocalLibraries;
            this.onComplete = onComplete;
        }

        @Override
        protected void onPreExecute() {
            // The activity may already be collected (config change / finish) - bail instead
            // of null-dereferencing the weak reference while building the dialog.
            if (activityWeakReference.get() == null) return;
            ProgressMsgBoxBinding loadingDialogBinding = ProgressMsgBoxBinding.inflate(LayoutInflater.from(activityWeakReference.get()));
            loadingDialogBinding.tvProgress.setText(R.string.backup_restoring);
            dlg = new MaterialAlertDialogBuilder(activityWeakReference.get())
                    .setTitle(R.string.common_word_please_wait)
                    .setCancelable(false)
                    .setView(loadingDialogBinding.getRoot())
                    .create();
            dlg.show();
        }

        @Override
        protected String doInBackground(String... params) {
            bm = new BackupFactory(lC.b());
            bm.setBackupLocalLibs(restoreLocalLibs);

            try {
                bm.restore(new File(file));
            } catch (Exception e) {
                bm.error = e.getMessage();
                error = true;
            }

            return "";
        }

        @Override
        protected void onPostExecute(String _result) {
            dlg.dismiss();

            if (!bm.isRestoreSuccess() || error) {
                SketchwareUtil.toastError(Helper.getResString(R.string.backup_restore_failed, bm.error), Toast.LENGTH_LONG);
            } else if (projectsFragment != null) {
                projectsFragment.refreshProjectsList();
                SketchwareUtil.toast(Helper.getResString(R.string.common_word_restored_successfully));
            } else {
                SketchwareUtil.toast(Helper.getResString(R.string.backup_restored_successfully_refresh), Toast.LENGTH_LONG);
            }

            if (onComplete != null) {
                onComplete.run();
            }
        }
    }
}
