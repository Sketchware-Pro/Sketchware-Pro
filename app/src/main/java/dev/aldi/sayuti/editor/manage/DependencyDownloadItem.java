package dev.aldi.sayuti.editor.manage;

import androidx.annotation.NonNull;
import java.util.Locale;
import java.util.Objects;
import org.cosmic.ide.dependency.resolver.api.Artifact;
public class DependencyDownloadItem {

    public enum DownloadState {
        PENDING,
        RESOLVING,
        DOWNLOADING,
        UNZIPPING,
        DEXING,
        COMPLETED,
        ERROR
    }

    private final String name;
    private final String displayName;
    private DownloadState state;
    private String statusMessage;
    private final long bytesDownloaded;
    private final long totalBytes;
    private final int progress; // 0-100
    private String errorMessage;
    private final Artifact artifact;

    public DependencyDownloadItem(@NonNull Artifact artifact) {
        this.artifact = artifact;
        this.name = artifact.toString();
        this.displayName = artifact.getArtifactId() + "-" + artifact.getVersion();
        setState(DownloadState.PENDING);
        bytesDownloaded = 0;
        totalBytes = 0;
        this.progress = 0;
        this.errorMessage = null;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public DownloadState getState() {
        return state;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public int getProgress() {
        return progress;
    }

    public Artifact getArtifact() {
        return artifact;
    }

    public void setState(DownloadState state) {
        this.state = state;
        updateStatusMessage();
    }

    public void setError(String errorMessage) {
        this.state = DownloadState.ERROR;
        this.errorMessage = errorMessage;
        this.statusMessage = "Error: " + errorMessage;
    }

    private void updateStatusMessage() {
        switch (state) {
            case PENDING:
                statusMessage = "Pending...";
                break;
            case RESOLVING:
                statusMessage = "Resolving dependency...";
                break;
            case DOWNLOADING:
                if (totalBytes > 0) {
                    statusMessage = formatBytes(bytesDownloaded) + " / " + formatBytes(totalBytes);
                } else {
                    statusMessage = "Downloading...";
                }
                break;
            case UNZIPPING:
                statusMessage = "Unzipping...";
                break;
            case DEXING:
                statusMessage = "Processing (DEX)...";
                break;
            case COMPLETED:
                statusMessage = "Completed";
                break;
            case ERROR:
                statusMessage = "Error: " + (errorMessage != null ? errorMessage : "Unknown error");
                break;
        }
    }

    private String formatBytes(long bytes) {
        if (bytes < 1024) {
            return bytes + " B";    } else if (bytes < 1048576) { // 1024 * 1024
            return String.format(Locale.getDefault(), "%.1f KB", bytes / 1024.0);
        } else {
            return String.format(Locale.getDefault(), "%.1f MB", bytes / 1048576.0);
        }
    }

    public boolean isCompleted() {
        return state == DownloadState.COMPLETED;
    }

    public boolean isError() {
        return state == DownloadState.ERROR;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DependencyDownloadItem that = (DependencyDownloadItem) obj;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    @Override
    @NonNull
    public String toString() {
        return "DependencyDownloadItem{"
                + "name='" + name + '\''
                + ", displayName='" + displayName + '\''
                + ", state=" + state
                + ", statusMessage='" + statusMessage + '\''
                + ", bytesDownloaded=" + bytesDownloaded
                + ", totalBytes=" + totalBytes
                + ", progress=" + progress
                + ", errorMessage='" + errorMessage + '\''
                + '}';
    }

}