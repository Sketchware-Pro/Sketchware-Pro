package mod.jbk.build.progress;

import org.eclipse.jgit.lib.ProgressMonitor;

import java.util.function.Consumer;

public class BuildProgressMonitorForJgit implements ProgressMonitor {

    private final Consumer<String> progressUpdater;
    private int totalWork;
    private int workCompleted;
    private String currentTask;

    public BuildProgressMonitorForJgit(Consumer<String> updater) {
        this.progressUpdater = updater;
    }

    @Override
    public void start(int totalTasks) {
    }

    @Override
    public void beginTask(String title, int totalWork) {
        this.workCompleted = 0;
        this.totalWork = totalWork;
        this.currentTask = title;
        progressUpdater.accept(title);
    }

    @Override
    public void update(int completed) {
        workCompleted += completed;
        if (totalWork > 0 && totalWork != UNKNOWN) {
            int percent = (100 * workCompleted) / totalWork;
            progressUpdater.accept(currentTask + " (" + percent + "%)");
        }
    }

    @Override
    public void endTask() {
    }

    @Override
    public boolean isCancelled() {
        return false;
    }
}