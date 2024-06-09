package mod.remaker.settings;

import static mod.hilal.saif.activities.tools.ConfigActivity.SETTING_BACKUP_DIRECTORY;

import java.util.List;

import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.remaker.settings.model.BackupDirectory;

public class BackupDirectoryManager {
    public interface BackupDirectoryAddListener {
        void onDirectoryAdd(boolean success, String message);
    }

    public static List<BackupDirectory> getBackupDirectories() {
        return ConfigActivity.getCustomBackupDirectories();
    }

    public static void addBackupDirectory(BackupDirectory directory) {
        addBackupDirectory(directory, null);
    }

    public static void addBackupDirectory(BackupDirectory directory, BackupDirectoryAddListener listener) {
        if (getBackupDirectories().contains(directory)) {
            listener.onDirectoryAdd(false, "This directory already is in list.");
            return;
        }

        ConfigActivity.addCustomBackupDirectory(directory);
        if (listener != null) {
            listener.onDirectoryAdd(true, null);
        }
    }

    public static void changeDefaultBackupDirectory(BackupDirectory directory) {
        ConfigActivity.setSetting(SETTING_BACKUP_DIRECTORY, directory.path());
    }
}