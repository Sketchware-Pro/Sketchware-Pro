package mod.remaker.settings;

import java.util.List;

import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.remaker.settings.model.BackupDirectory;

public class BackupDirectoryManager {
    public List<BackupDirectory> getBackupDirectories() {
        return ConfigActivity.getCustomBackupDirectories();
    }
}
