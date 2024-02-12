package <?package_name?>;

import android.content.Intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.util.Log;

public class SketchLogger {

    /**
     * Logcat Reader Class
     * <p>
     * Uses:
     * <br>
     *  - "new SketchLogger(this).broadcastLog(String)" to manually send a debug log that's then viewable in Logcat Reader
     *  - "new SketchLogger(this).stopLogging()" to stop logging
     */

	private Context context;
	private volatile boolean isRunning = false;

	public SketchLogger(Context context) {
		this.context = context;
	}

	public void startLogging() {
		if (!isRunning) {
			new TaskRunner().executeAsync(new AsyncTask(), data -> {
				//System.out.println(data);
			});
		} else {
			throw new IllegalStateException("Logger already running");
		}
	}

	public void broadcastLog(String log) {
		Intent intent = new Intent();
		intent.setAction("com.sketchware.remod.ACTION_NEW_DEBUG_LOG");
		intent.putExtra("log", log);
		intent.putExtra("packageName", context.getPackageName());
		context.sendBroadcast(intent);
	}

	public void stopLogging() {
		if (isRunning) {
			isRunning = false;
			broadcastLog("Stopping logger by user request.");
		} else {
			throw new IllegalStateException("Logger not running");
		}
	}

	private class AsyncTask implements Callable<String> {
		public AsyncTask() {
		}

		@Override
		public String call() {
			isRunning = true;
			try {
				Runtime.getRuntime().exec("logcat -c");
				Process process = Runtime.getRuntime().exec("logcat");

				try (BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(process.getInputStream()))) {
					String logTxt = bufferedReader.readLine();
					do {
						broadcastLog(logTxt);
					} while (isRunning && ((logTxt = bufferedReader.readLine()) != null));

					if (isRunning) {
						broadcastLog("Logger got killed. Restarting.");
						startLogging();
					} else {
						broadcastLog("Logger stopped.");
					}
				}
			} catch (Exception e) {
				broadcastLog(e.toString());
			}
			return "";
		}
	}

	private static class TaskRunner {
		public interface Callback<T> {
			void onComplete(T result);
		}

		public <T> void executeAsync(Callable<T> callable, Callback<T> callback) {
			Executors.newSingleThreadExecutor().execute(() -> {
				try {
					final T result = callable.call();
					new Handler(Looper.getMainLooper()).post(() -> {
						callback.onComplete(result);
					});
				} catch (Exception e) {
				}
			});
		}
	}
                                              }
