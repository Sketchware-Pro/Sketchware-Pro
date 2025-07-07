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
            return "Looks like the backup file you selected contains some Local libraries. Do you want to copy them to your local_libs directory (if they do not already exist)?";
        } else {
            return "Looks like backup file " + filename + " (" + (currentRestoringIndex + 1) + " out of " + totalAmountOfBackups + ") contains some Local libraries. Do you want to copy them to your local_libs directory (if they do not already exist)?";
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
        dialog.setTitle("Backup Options");

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
        includeLocalLibraries.setText("Include used Local libraries");
        includeLocalLibraries.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        includeLocalLibraries.setOnCheckedChangeListener(listener);
        checkboxContainer.addView(includeLocalLibraries);

        CheckBox includeUsedCustomBlocks = new CheckBox(act);
        includeUsedCustomBlocks.setTag(customBlocksTag);
        includeUsedCustomBlocks.setText("Include used Custom Blocks");
        includeUsedCustomBlocks.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        includeUsedCustomBlocks.setOnCheckedChangeListener(listener);
        checkboxContainer.addView(includeUsedCustomBlocks);

        dialog.setView(checkboxContainer);
        dialog.setPositiveButton("Back up", (v, which) -> {
            v.dismiss();
            doBackup(sc_id, project_name);
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
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
        options.setTitle("Select backups to restore (" + BackupFactory.EXTENSION + ")");

        FilePickerCallback callback = new FilePickerCallback() {
            @Override
            public void onFilesSelected(@NotNull List<? extends File> files) {
                for (int i = 0; i < files.size(); i++) {
                    String backupFilePath = files.get(i).getAbsolutePath();

                    if (BackupFactory.zipContainsFile(backupFilePath, "local_libs")) {
                        boolean restoringMultipleBackups = files.size() > 1;

                        new MaterialAlertDialogBuilder(act)
                                .setTitle("Warning")
                                .setMessage(getRestoreIntegratedLocalLibrariesMessage(restoringMultipleBackups, i, files.size(),
                                        FileUtil.getFileNameNoExtension(backupFilePath)))
                                .setPositiveButton("Copy", (dialog, which) -> doRestore(backupFilePath, true))
                                .setNegativeButton("Don't copy", (dialog, which) -> doRestore(backupFilePath, false))
                                .setNeutralButton(R.string.common_word_cancel, null)
                                .show();

                    } else {
                        doRestore(backupFilePath, false);
                    }
                }
            }
        };

        new FilePickerDialogFragment(options, callback).show(projectsFragment.getChildFragmentManager(), "file_picker");
    }

    public void doRestore(String file, boolean restoreLocalLibs) {
        new RestoreAsyncTask(new WeakReference<>(act), file, restoreLocalLibs, projectsFragment).execute("");
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
            ProgressMsgBoxBinding loadingDialogBinding = ProgressMsgBoxBinding.inflate(LayoutInflater.from(activityWeakReference.get()));
            loadingDialogBinding.tvProgress.setText("Creating backup...");
            dlg = new MaterialAlertDialogBuilder(activityWeakReference.get())
                    .setTitle("Please wait")
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
                SketchwareUtil.toast("Successfully created backup to: " + bm.getOutFile().getAbsolutePath());
            } else {
                SketchwareUtil.toastError("Error: " + bm.error, Toast.LENGTH_LONG);
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

        RestoreAsyncTask(WeakReference<Activity> activityWeakReference, String file, boolean restoreLocalLibraries, ProjectsFragment projectsFragment) {
            this.activityWeakReference = activityWeakReference;
            this.file = file;
            this.projectsFragment = projectsFragment;
            restoreLocalLibs = restoreLocalLibraries;
        }

        @Override
        protected void onPreExecute() {
            ProgressMsgBoxBinding loadingDialogBinding = ProgressMsgBoxBinding.inflate(LayoutInflater.from(activityWeakReference.get()));
            loadingDialogBinding.tvProgress.setText("Restoring...");
            dlg = new MaterialAlertDialogBuilder(activityWeakReference.get())
                    .setTitle("Please wait")
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
                SketchwareUtil.toastError("Couldn't restore: " + bm.error, Toast.LENGTH_LONG);
            } else if (projectsFragment != null) {
                projectsFragment.refreshProjectsList();
                SketchwareUtil.toast("Restored successfully");
            } else {
                SketchwareUtil.toast("Restored successfully. Refresh to see the project", Toast.LENGTH_LONG);
            }
        }
    }
}
