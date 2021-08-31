package mod.hey.studios.project.backup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.sketchware.remod.Resources;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;

import a.a.a.GC;
import a.a.a.aB;
import a.a.a.lC;
import a.a.a.xB;
import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;

public class BackupRestoreManager {

    private final Activity act;

    // Needed to refresh the project list after restoring
    private GC gc;

    private HashMap<Integer, Boolean> bckpDialogStates;

    public BackupRestoreManager(Activity act) {
        this.act = act;
    }

    public BackupRestoreManager(Activity act, GC gc) {
        this.act = act;
        this.gc = gc;
    }

    public void backup(final String sc_id, final String project_name) {
        final String localLibrariesTag = "local libraries";
        final String customBlocksTag = "Custom Blocks";
        bckpDialogStates = new HashMap<>();
        bckpDialogStates.put(0, false);
        bckpDialogStates.put(1, false);

        aB dialog = new aB(act);
        dialog.a(Resources.drawable.ic_backup);
        dialog.b("Backup Options");

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
                bckpDialogStates.put(index, isChecked);
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

        dialog.a(checkboxContainer);
        dialog.b("Back up", v -> {
            dialog.dismiss();
            doBackup(sc_id, project_name);
        });
        dialog.a(xB.b().a(act.getApplicationContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void doBackup(final String sc_id, final String project_name /*, final SparseBooleanArray arr*/) {
        new BackupAsyncTask(new WeakReference<>(act), sc_id, project_name, bckpDialogStates)
                .execute("");
    }

    /*** Restore ***/

    public void restore() {
        DialogProperties properties = new DialogProperties();
        properties.selection_mode = 0;
        properties.selection_type = 0;
        properties.root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.error_dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        properties.offset = new File(BackupFactory.getBackupDir());
        properties.extensions = new String[]{BackupFactory.EXTENSION};

        FilePickerDialog fpd = new FilePickerDialog(act, properties);
        fpd.setTitle("Select a backup file (" + BackupFactory.EXTENSION + ")");
        fpd.setDialogSelectionListener(files -> {
            final String file = files[0];

            final boolean local_libs = BackupFactory.zipContainsFile(file, "local_libs");

            if (local_libs) {
                new AlertDialog.Builder(act)
                        .setTitle("Warning")
                        .setMessage("Looks like the backup file you selected contains some local libraries. Do you want to copy them to your local_libs directory (if they do not already exist)?")
                        .setPositiveButton("Copy", (dialog, which) -> doRestore(file, true))
                        .setNegativeButton("Don't copy", (dialog, which) -> doRestore(file, false))
                        .setNeutralButton(Resources.string.common_word_cancel, null)
                        .show();
            } else {
                doRestore(file, false);
            }
        });

        fpd.show();
    }

    public void doRestore(final String file, final boolean restoreLocalLibs) {
        new RestoreAsyncTask(new WeakReference<>(act), file, restoreLocalLibs, gc).execute("");
    }

    private static class BackupAsyncTask extends AsyncTask<String, Integer, String> {

        private final String sc_id;
        private final String project_name;
        private final HashMap<Integer, Boolean> options;
        private final WeakReference<Activity> activityWeakReference;
        private BackupFactory bm;
        private ProgressDialog dlg;

        BackupAsyncTask(WeakReference<Activity> activityWeakReference, String sc_id, String project_name, HashMap<Integer, Boolean> options) {
            this.activityWeakReference = activityWeakReference;
            this.sc_id = sc_id;
            this.project_name = project_name;
            this.options = options;
        }

        @Override
        protected void onPreExecute() {
            dlg = new ProgressDialog(activityWeakReference.get());
            dlg.setMessage("Creating backup...");
            dlg.setCancelable(false);
            dlg.show();
        }

        @Override
        protected String doInBackground(String... params) {
            bm = new BackupFactory(sc_id);
            bm.setBackupLocalLibs(options.get(0));
            bm.setBackupCustomBlocks(options.get(1));

            bm.backup(project_name);

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
        private final GC gc;
        private final boolean restoreLocalLibs;
        private BackupFactory bm;
        private ProgressDialog dlg;
        private boolean error = false;

        RestoreAsyncTask(WeakReference<Activity> activityWeakReference, String file, boolean restoreLocalLibraries, GC gc) {
            this.activityWeakReference = activityWeakReference;
            this.file = file;
            this.gc = gc;
            this.restoreLocalLibs = restoreLocalLibraries;
        }

        @Override
        protected void onPreExecute() {
            dlg = new ProgressDialog(activityWeakReference.get());
            dlg.setMessage("Restoring...");
            dlg.setCancelable(false);
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
            } else if (gc != null) {
                // Refreshes the main list
                gc.g();
                SketchwareUtil.toast("Restored successfully");
            } else {
                SketchwareUtil.toast("Restored successfully. Refresh to see the project", Toast.LENGTH_LONG);
            }
        }
    }
}
