package mod.hey.studios.project.backup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Environment;

import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.sketchware.remod.Resources;

import java.io.File;
import java.util.HashMap;

import a.a.a.GC;
import a.a.a.bB;
import a.a.a.lC;

public class BackupRestoreManager {

    private final Activity act;

    // Needed to refresh the project list after restoring
    private GC gc;

    // Private AlertDialog backup_dlg;
    private HashMap<Integer, Boolean> bckpDialogStates;

    public BackupRestoreManager(Activity act) {
        this.act = act;
    }

    public BackupRestoreManager(Activity act, GC gc) {
        this.act = act;
        this.gc = gc;
    }

    public void backup(final String sc_id, final String project_name) {
        bckpDialogStates = new HashMap<>();
        bckpDialogStates.put(0, false);
        bckpDialogStates.put(1, false);

        AlertDialog.Builder dialog = new AlertDialog.Builder(act);
        dialog.setTitle("Backup options");
        dialog.setMultiChoiceItems(
                new String[]{
                        "Include used local libs",
                        "Include used custom blocks"
                },
                null,
                (dialog1, which, isChecked) -> bckpDialogStates.put(which, isChecked));

        dialog.setPositiveButton(Resources.string.common_word_ok, (p1, p2) -> {
            //if(backup_dlg != null) {
            //final SparseBooleanArray arr = backup_dlg.getListView().getCheckedItemPositions();
            doBackup(sc_id, project_name);
            //}
        });

        dialog.setNegativeButton(Resources.string.common_word_cancel, null);

        AlertDialog backup_dlg = dialog.create();
        backup_dlg.show();
    }

    private void doBackup(final String sc_id, final String project_name /*, final SparseBooleanArray arr*/) {
        new AsyncTask<String, Integer, String>() {
            final boolean error = false;
            BackupFactory bm;
            ProgressDialog dlg;

            @Override
            protected void onPreExecute() {
                dlg = new ProgressDialog(act);
                dlg.setMessage("Creating backup...");
                dlg.setCancelable(false);
                dlg.show();
            }

            @Override
            protected String doInBackground(String... params) {
                bm = new BackupFactory(sc_id);
                bm.setBackupLocalLibs(bckpDialogStates.get(0));
                bm.setBackupCustomBlocks(bckpDialogStates.get(1));

                //     try {
                bm.backup(project_name);
                /* } catch(Exception e) {
                 bm.error = e.toString();
                 error = true;
                 }*/

                return "";
            }

            @Override
            protected void onPostExecute(String _result) {

                dlg.dismiss();

                if (bm.getOutFile() != null && !error) {
                    bB.a(act, "Created backup successfully to " + bm.getOutFile().getAbsolutePath(), 0).show();
                } else {
                    bB.a(act, "Error: " + bm.error, 0).show();
                }
            }
        }.execute("");
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

            //boolean custom_blocks = BackupFactory.zipContainsFile(file, "custom_blocks");
            final boolean local_libs = BackupFactory.zipContainsFile(file, "local_libs");

            if (local_libs) {
                new AlertDialog.Builder(act)
                        .setTitle("Warning")
                        .setMessage("Looks like the backup file you selected contains some local libraries. Do you want to copy them to your local_libs directory (if they do not already exist)?")
                        .setPositiveButton("Copy", (dialog, which) -> doRestore(file, true))
                        .setNegativeButton("Don't copy", (dialog, which) -> doRestore(file, false))
                        .setNeutralButton(Resources.string.common_word_cancel, null)
                        .create().show();
            } else {
                doRestore(file, false);
            }
        });

        fpd.show();
    }


    public void doRestore(final String file, final boolean restoreLocalLibs) {
        new AsyncTask<String, Integer, String>() {
            BackupFactory bm;
            boolean error = false;
            ProgressDialog dlg;

            @Override
            protected void onPreExecute() {
                dlg = new ProgressDialog(act);
                dlg.setMessage("Restoring...");
                dlg.setCancelable(false);
                dlg.show();
            }

            @Override
            protected String doInBackground(String... params) {
                //changed in 6.3.0 fix1
                bm = new BackupFactory(lC.b()/*BackupFactory.getNewScId()*/);
                bm.setBackupLocalLibs(restoreLocalLibs);

                try {
                    bm.restore(new File(file/*files[0]*/));
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
                    bB.a(act, "Couldn't restore: " + bm.error, 0).show();
                    return;
                }

                if (gc != null) {
                    // Refreshes the main list
                    gc.g();
                    bB.a(act, "Restored successfully", 0).show();
                } else {
                    bB.a(act, "Restored successfully. You can refresh the list", 1).show();
                }
            }
        }.execute("");
    }
}
