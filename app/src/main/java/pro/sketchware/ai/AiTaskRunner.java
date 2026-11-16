package pro.sketchware.ai;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Runs blocking AI work off the main thread and delivers the outcome back on it.
 * <p>
 * Screens own one instance each and {@link #cancel()} it when their views go away, so a result
 * that arrives late is dropped instead of touching destroyed views.
 */
public class AiTaskRunner {

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    /**
     * Incremented by {@link #cancel()}; everything posted by tasks started before that is dropped.
     * Only touched on the main thread.
     */
    private int generation;

    /**
     * Must be called on the main thread.
     */
    public <T> void run(@NonNull Task<T> task, @NonNull Callback<T> callback) {
        MainThread mainThread = new MainThread(generation);
        executor.execute(() -> {
            try {
                T result = task.run(mainThread);
                mainThread.post(() -> callback.onSuccess(result));
            } catch (Exception e) {
                mainThread.post(() -> callback.onError(e));
            }
        });
    }

    /**
     * Drops everything that running or queued tasks still want to deliver. Must be called on the
     * main thread.
     */
    public void cancel() {
        generation++;
        mainHandler.removeCallbacksAndMessages(null);
    }

    public interface Task<T> {
        /**
         * Runs on a background thread.
         *
         * @param mainThread For reporting progress while the task is still running.
         */
        T run(@NonNull MainThread mainThread) throws Exception;
    }

    public interface Callback<T> {
        void onSuccess(T result);

        void onError(@NonNull Exception e);
    }

    /**
     * Posts to the main thread on behalf of one task, for as long as that task isn't cancelled.
     */
    public final class MainThread {

        private final int startedIn;

        private MainThread(int startedIn) {
            this.startedIn = startedIn;
        }

        public void post(@NonNull Runnable runnable) {
            mainHandler.post(() -> {
                if (startedIn == generation) {
                    runnable.run();
                }
            });
        }
    }
}
