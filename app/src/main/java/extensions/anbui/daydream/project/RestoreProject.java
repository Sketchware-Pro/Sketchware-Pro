package extensions.anbui.daydream.project;

import static mod.hey.studios.project.backup.BackupRestoreManager.universalDoRestore;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.net.Uri;
import android.view.DragEvent;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import extensions.anbui.daydream.configs.Configs;
import extensions.anbui.daydream.file.FileUtils;
import mod.hey.studios.project.backup.BackupFactory;
import pro.sketchware.R;

public class RestoreProject {
    public static void withFilePath(Activity activity, String filepath) {

        if (!FileUtils.isFileExist(filepath)) return;

        if (Configs.mainActivity == null) return;

        if (BackupFactory.zipContainsFile(filepath, "local_libs")) {
            boolean restoringMultipleBackups = false;
            new MaterialAlertDialogBuilder(activity)
                    .setTitle("Warning")
                    .setMessage("Do you want to copy the libraries contained in this backup to the libraries folder?")
                    .setPositiveButton("Copy", (dialog, which) -> universalDoRestore(Configs.mainActivity, filepath, true))
                    .setNegativeButton("Don't copy", (dialog, which) -> universalDoRestore(Configs.mainActivity, filepath, false))
                    .setNeutralButton(R.string.common_word_cancel, null)
                    .show();

        } else {
            universalDoRestore(Configs.mainActivity, filepath, false);
        }
    }

    public static void setupDropFileTo(Activity activity, View view) {
        view.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        ClipDescription description = event.getClipDescription();
                        return description != null; // Accept to go to event DragEvent.ACTION_DROP

                    case DragEvent.ACTION_DROP:
                        ClipData clipData = event.getClipData();
                        if (clipData != null && clipData.getItemCount() > 0) {
                            Uri uri = clipData.getItemAt(0).getUri();
                            String filePath = FileUtils.getFilePathFromUri(activity, uri);

                            withFilePath(activity, filePath);
                        }
                        return true;
                }
                return true;
            }
        });
    }
}
