package <?package_name?>;

import android.app.AlarmManager;

import android.app.Application;

import android.app.PendingIntent;

import android.content.Context;

import android.content.Intent;

import android.os.Process;

import android.util.Log;

import android.app.*;

import android.content.SharedPreferences;

import java.io.IOException;

import android.widget.Toast;

public class SketchApplication extends Application {

		

		private SharedPreferences log;

		

		private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

		

		

		public void onCreate() {

				

				

				

				this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();

				

				Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

						@Override

						public void uncaughtException(Thread thread, Throwable throwable) {

								Intent intent = new Intent(getApplicationContext(), DebugActivity.class);

								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

								intent.putExtra("error", Log.getStackTraceString(throwable));

								PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 11111, intent, PendingIntent.FLAG_ONE_SHOT);

								

								AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

								am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, pendingIntent);

								

								android.os.Process.killProcess(android.os.Process.myPid());

								System.exit(1);

								

								uncaughtExceptionHandler.uncaughtException(thread, throwable);

						}

				});

				super.onCreate();

				

				

				/*

	       To enable logcat:

				 1. create a Shared preference with component and file name as log.

				 2. Add this code to enable Logger

				 log.edit().putString("debug", "true").commit();

				 3. To disable logger 

				 log.edit().putString("debug", "false").commit();

            Important:

                  Don't forget to restart your app to enable or disable logcat.

                  Log files are stored in /Android/data/{your application package}/log_time.txt

                  Use this only for debugging purpose as it make take considerable amount of storage space

				*/

				

				

				log = getSharedPreferences("log", Activity.MODE_PRIVATE);

				String name = "log_" + System.currentTimeMillis() + ".txt";

				String file = FileUtil.getPackageDataDir(getApplicationContext()).concat(name);

				if (!log.getString("debug", "").equals("")) {

						if (log.getString("debug", "").equals("false")) {

								//logging has been disabled

						}

						

						else {

								if (log.getString("debug", "").equals("true")) {

										

										

										try {

												java.lang.Process process = Runtime.getRuntime().exec("logcat -c");

												process = Runtime.getRuntime().exec("logcat -f " + file);

										} catch ( IOException ex ) {

												ex.printStackTrace();

												Toast.makeText(getApplicationContext(),

												ex.toString(),

												Toast.LENGTH_LONG)

												.show();

										} 

										

										

										

								}

								else {

										

										//do nothing simply waste of line

										

								}

						}

				}

				

				

		}

}

