package mod.jbk.build.progress;

import org.eclipse.jgit.lib.ProgressMonitor;

import java.util.function.Consumer;

public class BuildProgressMonitorForJgit implements ProgressMonitor {

    private final Consumer<String> progressUpdater;
    private String currentTask;
    private int totalWork;
    private int workCompleted;

    public BuildProgressMonitorForJgit(Consumer<String> updater) {
        this.progressUpdater = updater;
    }

    @Override
    public void start(int totalTasks) {
    }

    @Override
    public void beginTask(String title, int totalWork) {
        this.currentTask = title;
        this.totalWork = totalWork;
        this.workCompleted = 0;
        if (progressUpdater != null) {
            progressUpdater.accept(title);
        }
    }

    @Override
    public void update(int completed) {
        this.workCompleted += completed;
    }

    @Override
    public void endTask() {
    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}