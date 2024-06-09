package mod.remaker.settings.model;

import static mod.hilal.saif.activities.tools.ConfigActivity.SETTING_BACKUP_DIRECTORY;
import static mod.hilal.saif.activities.tools.ConfigActivity.getDefaultValue;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil.ItemCallback;

import java.util.Objects;

public record BackupDirectory(String name, String path) {
    public static final BackupDirectory DEFAULT_BACKUP_DIRECTORY =
        new BackupDirectory("Default directory", (String) getDefaultValue(SETTING_BACKUP_DIRECTORY));

    public static ItemCallback<BackupDirectory> DIFF_CALLBACK = new ItemCallback<BackupDirectory>() {
        @Override
        public boolean areItemsTheSame(@NonNull BackupDirectory oldItem, @NonNull BackupDirectory newItem) {
            return oldItem.name().equals(newItem.name());
        }

        @Override
        public boolean areContentsTheSame(@NonNull BackupDirectory oldItem, @NonNull BackupDirectory newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BackupDirectory directory = (BackupDirectory) o;
        return Objects.equals(name, directory.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path);
    }
}
