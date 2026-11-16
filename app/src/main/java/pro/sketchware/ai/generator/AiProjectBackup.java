package pro.sketchware.ai.generator;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;

import a.a.a.wq;
import pro.sketchware.utility.FileUtil;

/**
 * Keeps a copy of a project from before the AI last changed it, so the change can be undone.
 * <p>
 * Only the latest copy per project is kept. It holds the project's data folder (views, logic,
 * files, libraries, settings) and its metadata; resources like images aren't touched by AI
 * changes and so aren't copied.
 */
public class AiProjectBackup {

    private static final String BACKUP_ROOT = ".sketchware/temp/ai_backup";
    private static final String DATA_FOLDER = "data";
    private static final String METADATA_FILE = "project";

    private final String sc_id;
    private final File backupFolder;

    public AiProjectBackup(@NonNull String sc_id) {
        this.sc_id = sc_id;
        backupFolder = new File(wq.getAbsolutePathOf(BACKUP_ROOT), sc_id);
    }

    public boolean exists() {
        return new File(backupFolder, METADATA_FILE).exists();
    }

    /**
     * Replaces the project's previous backup with a copy of its current state.
     */
    void create() {
        FileUtil.deleteFile(backupFolder.getAbsolutePath());
        try {
            File data = new File(wq.b(sc_id));
            if (data.exists()) {
                FileUtil.copyDirectory(data, new File(backupFolder, DATA_FOLDER));
            }
            FileUtil.copyFile(metadataFile().getAbsolutePath(), new File(backupFolder, METADATA_FILE).getAbsolutePath());
        } catch (IOException e) {
            FileUtil.deleteFile(backupFolder.getAbsolutePath());
            throw new IllegalStateException("Couldn't back up project " + sc_id + ": " + e.getMessage(), e);
        }
    }

    /**
     * Puts the project back the way it was when {@link #create()} ran.
     */
    public void restore() {
        if (!exists()) {
            throw new IllegalStateException("There's no backup of project " + sc_id + ".");
        }
        String data = wq.b(sc_id);
        FileUtil.deleteFile(data);
        try {
            File backedUpData = new File(backupFolder, DATA_FOLDER);
            if (backedUpData.exists()) {
                FileUtil.copyDirectory(backedUpData, new File(data));
            }
            FileUtil.copyFile(new File(backupFolder, METADATA_FILE).getAbsolutePath(), metadataFile().getAbsolutePath());
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't restore project " + sc_id + ": " + e.getMessage(), e);
        }
    }

    private File metadataFile() {
        return new File(wq.c(sc_id), METADATA_FILE);
    }
}
