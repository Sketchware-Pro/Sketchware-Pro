package mod.remaker.settings.model;

import static mod.hilal.saif.activities.tools.ConfigActivity.SETTING_BACKUP_DIRECTORY;
import static mod.hilal.saif.activities.tools.ConfigActivity.getDefaultValue;

import java.util.Objects;

public record BackupDirectory(String name, String path) {
    public static final BackupDirectory DEFAULT_BACKUP_DIRECTORY =
        new BackupDirectory("Default directory", (String) getDefaultValue(SETTING_BACKUP_DIRECTORY));

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BackupDirectory directory = (BackupDirectory) o;
        return Objects.equals(name, directory.name) && Objects.equals(path, directory.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path);
    }
}
