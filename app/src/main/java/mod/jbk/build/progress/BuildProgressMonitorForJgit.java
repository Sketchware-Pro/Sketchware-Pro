package mod.jbk.build.progress;

import org.eclipse.jgit.lib.ProgressMonitor;

import java.util.function.Consumer;

/**
 * A simple JGit ProgressMonitor that forwards task titles to a UI updater.
 * This class acts as a bridge between the JGit background operations and the app's UI thread.
 */
public class BuildProgressMonitorForJgit implements ProgressMonitor {

    /**
     * A functional interface representing the action that will update the UI,
     * for example, by setting the message on a ProgressDialog.
     */
    private final Consumer<String> progressUpdater;
    private int totalWork;
    private int workCompleted;
    private String currentTask;

    /**
     * Constructs a progress monitor.
     *
     * @param updater A Consumer that accepts a String message to be displayed on the UI.
     *                This is typically a method reference to the Activity's progress dialog updater.
     */
    public BuildProgressMonitorForJgit(Consumer<String> updater) {
        this.progressUpdater = updater;
    }

    @Override
    public void start(int totalTasks) {
        // This is called once at the very beginning with the total number of tasks.
        // We don't need to do anything with it for a simple text-based progress dialog.
    }

    @Override
    public void beginTask(String title, int totalWork) {
        // This is called when a new major step of the Git operation begins.
        // This is the most useful information for the user.
        this.workCompleted = 0;
        this.totalWork = totalWork;
        this.currentTask = title;
        
        // Forward the title of the task to the UI.
        progressUpdater.accept(title);
    }

    @Override
    public void update(int completed) {
        // This is called periodically with the amount of work completed in the current task.
        // For a more advanced progress bar, you could use this to show a percentage.
        // For our simple text dialog, we can ignore this to avoid flooding the UI with numbers.
        workCompleted += completed;
        
        if (totalWork > 0 && totalWork != UNKNOWN) {
            int percent = (100 * workCompleted) / totalWork;
            progressUpdater.accept(currentTask + " (" + percent + "%)");
        }
    }

    @Override
    public void endTask() {
        // This is called when the current task is finished.
        // We don't need to do anything special here.
    }

    @Override
    public boolean isCancelled() {
        // This method is checked by JGit to see if the user has requested to cancel the operation.
        // Since our UI doesn't provide a cancel button for the Git process, we always return false.
        return false;
    }
}