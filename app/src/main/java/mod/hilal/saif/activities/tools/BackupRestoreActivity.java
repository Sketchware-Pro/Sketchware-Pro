package mod.hilal.saif.activities.tools;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import dev.pranav.filepicker.FilePickerCallback;
import dev.pranav.filepicker.FilePickerDialogFragment;
import dev.pranav.filepicker.FilePickerOptions;
import pro.sketchware.R;
import pro.sketchware.utility.FileUtil;

public class BackupRestoreActivity extends BaseAppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_restore);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        Button btnBackupAll = findViewById(R.id.btn_backup_all);
        Button btnRestoreAll = findViewById(R.id.btn_restore_all);
        Button btnCleanCache = findViewById(R.id.btn_clean_cache);

        btnBackupAll.setOnClickListener(v -> backupAll());
        btnRestoreAll.setOnClickListener(v -> restoreAll());
        btnCleanCache.setOnClickListener(v -> cleanCache());
    }

    private void cleanCache() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Clean Build Cache")
                .setMessage("Are you sure you want to delete all temporary build files? This will not delete your projects.")
                .setPositiveButton("Clean", (dialog, which) -> {
                    File myscDir = new File(Environment.getExternalStorageDirectory(), ".sketchware/mysc");
                    FileUtil.deleteFile(myscDir.getAbsolutePath());
                    Toast.makeText(this, "Build cache cleaned.", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void backupAll() {
        new BackupTask().execute();
    }

    private void restoreAll() {
        FilePickerOptions options = new FilePickerOptions();
        options.setExtensions(new String[]{"zip"});
        options.setTitle("Select Backup File");
        options.setInitialDirectory(ConfigActivity.getBackupPath());

        FilePickerCallback callback = new FilePickerCallback() {
            @Override
            public void onFilesSelected(List<? extends File> files) {
                if (!files.isEmpty()) {
                    new RestoreTask(files.get(0)).execute();
                }
            }
        };

        new FilePickerDialogFragment(options, callback).show(getSupportFragmentManager(), "file_picker");
    }

    private class BackupTask extends AsyncTask<Void, String, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(BackupRestoreActivity.this);
            dialog.setTitle("Backing up...");
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                File sketchwareDir = new File(Environment.getExternalStorageDirectory(), ".sketchware");
                File backupDir = new File(ConfigActivity.getBackupPath());
                if (!backupDir.exists()) backupDir.mkdirs();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                File backupFile = new File(backupDir, "SketchwarePro_FullBackup_" + timeStamp + ".zip");

                FileOutputStream fos = new FileOutputStream(backupFile);
                ZipOutputStream zos = new ZipOutputStream(fos);

                addFolderToZip(new File(sketchwareDir, "data"), sketchwareDir, zos);
                addFolderToZip(new File(sketchwareDir, "resources"), sketchwareDir, zos);
                addFolderToZip(new File(sketchwareDir, "libs/local_libs"), sketchwareDir, zos);

                zos.close();
                fos.close();
                return "Backup saved to: " + backupFile.getAbsolutePath();
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        }

        private void addFolderToZip(File folder, File root, ZipOutputStream zos) throws Exception {
            if (!folder.exists()) return;
            File[] files = folder.listFiles();
            if (files == null) return;

            for (File file : files) {
                if (file.isDirectory()) {
                    addFolderToZip(file, root, zos);
                } else {
                    String entryName = file.getAbsolutePath().substring(root.getAbsolutePath().length() + 1);
                    ZipEntry entry = new ZipEntry(entryName);
                    zos.putNextEntry(entry);

                    FileInputStream fis = new FileInputStream(file);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    fis.close();
                    zos.closeEntry();
                }
            }
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            new MaterialAlertDialogBuilder(BackupRestoreActivity.this)
                    .setTitle("Backup Result")
                    .setMessage(result)
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    private class RestoreTask extends AsyncTask<Void, String, String> {
        File backupFile;
        ProgressDialog dialog;

        public RestoreTask(File backupFile) {
            this.backupFile = backupFile;
        }

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(BackupRestoreActivity.this);
            dialog.setTitle("Restoring...");
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                File sketchwareDir = new File(Environment.getExternalStorageDirectory(), ".sketchware");

                // Unzip
                FileInputStream fis = new FileInputStream(backupFile);
                ZipInputStream zis = new ZipInputStream(fis);
                FileUtil.extractZipTo(zis, sketchwareDir.getAbsolutePath());

                return "Restore completed successfully.";
            } catch (Exception e) {
                return "Error: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            new MaterialAlertDialogBuilder(BackupRestoreActivity.this)
                    .setTitle("Restore Result")
                    .setMessage(result)
                    .setPositiveButton("OK", null)
                    .show();
        }
    }
}
