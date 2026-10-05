package pro.sketchware.control;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import pro.sketchware.BuildConfig;
import pro.sketchware.utility.Network;

/**
 * Checks a remote manifest for a newer app version and, when the update is marked mandatory,
 * shows a non-dismissable dialog that forces the user to update before continuing.
 *
 * The manifest (update.json) lives at the repository root and is served raw from GitHub:
 * {"versionCode": N, "versionName": "...", "mandatory": true, "apkUrl": "...", "url": "...", "notes": "..."}.
 * "apkUrl" is downloaded in-app with a progress bar and then handed to the package installer; when it
 * is missing, "url" is opened in the browser instead.
 */
public class UpdateChecker {

    private static final String MANIFEST_URL =
            "https://raw.githubusercontent.com/prozizou/Sketchware-Pro/main/update.json";

    private final Network network = new Network();

    public void check(AppCompatActivity activity) {
        network.get(MANIFEST_URL, response -> {
            if (response == null || activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
            try {
                JSONObject manifest = new JSONObject(response);
                int latest = manifest.optInt("versionCode", -1);
                if (latest <= BuildConfig.VERSION_CODE) {
                    return;
                }
                boolean mandatory = manifest.optBoolean("mandatory", false);
                String versionName = manifest.optString("versionName", "");
                String notes = manifest.optString("notes", "");
                String url = manifest.optString("url", "");
                String apkUrl = manifest.optString("apkUrl", "");
                showUpdateDialog(activity, mandatory, versionName, notes, url, apkUrl);
            } catch (Exception ignored) {
                // Malformed or unreachable manifest: fail silently, never block the app on our own bug.
            }
        });
    }

    private void showUpdateDialog(AppCompatActivity activity, boolean mandatory,
                                  String versionName, String notes, String url, String apkUrl) {
        StringBuilder message = new StringBuilder();
        if (!versionName.isEmpty()) {
            message.append("Version ").append(versionName).append('\n');
        }
        message.append(notes.isEmpty() ? "A new version is available." : notes);

        float dip = activity.getResources().getDisplayMetrics().density;
        LinearLayout content = new LinearLayout(activity);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding((int) (24 * dip), (int) (8 * dip), (int) (24 * dip), 0);

        TextView messageView = new TextView(activity);
        messageView.setText(message);
        content.addView(messageView);

        LinearProgressIndicator progress = new LinearProgressIndicator(activity);
        progress.setMax(100);
        progress.setVisibility(View.GONE);
        LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        progressParams.topMargin = (int) (16 * dip);
        content.addView(progress, progressParams);

        TextView status = new TextView(activity);
        status.setVisibility(View.GONE);
        status.setPadding(0, (int) (6 * dip), 0, 0);
        content.addView(status);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity)
                .setTitle("Update available")
                .setView(content)
                .setCancelable(!mandatory)
                .setPositiveButton("Update now", null);
        if (!mandatory) {
            builder.setNegativeButton("Later", null);
        }

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(!mandatory);
        dialog.show();

        // The positive button never dismisses the dialog: for a mandatory update the app stays
        // blocked until the newer build (with a higher versionCode) is installed.
        android.widget.Button button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        button.setOnClickListener(v -> {
            if (apkUrl.isEmpty()) {
                openUrl(activity, url);
                return;
            }
            File apk = new File(updatesDir(activity), "update.apk");
            if (apk.isFile() && button.getTag() == Boolean.TRUE) {
                install(activity, apk);
                return;
            }
            button.setEnabled(false);
            progress.setIndeterminate(true);
            progress.setVisibility(View.VISIBLE);
            status.setVisibility(View.VISIBLE);
            status.setText("Starting download\u2026");
            download(activity, apkUrl, apk, (percent, text) -> {
                if (percent >= 0) {
                    progress.setIndeterminate(false);
                    progress.setProgressCompat(percent, true);
                }
                status.setText(text);
            }, error -> {
                button.setEnabled(true);
                if (error == null) {
                    button.setTag(Boolean.TRUE);
                    button.setText("Install");
                    progress.setIndeterminate(false);
                    progress.setProgressCompat(100, true);
                    status.setText("Download complete");
                    install(activity, apk);
                } else {
                    button.setText("Retry");
                    progress.setVisibility(View.GONE);
                    status.setText("Download failed: " + error);
                }
            });
        });
    }

    private interface ProgressListener {
        void onProgress(int percent, String text);
    }

    private interface DoneListener {
        /** @param error null on success */
        void onDone(String error);
    }

    private File updatesDir(AppCompatActivity activity) {
        File dir = new File(activity.getExternalFilesDir(null), "updates");
        //noinspection ResultOfMethodCallIgnored
        dir.mkdirs();
        return dir;
    }

    private void download(AppCompatActivity activity, String apkUrl, File target,
                          ProgressListener progressListener, DoneListener doneListener) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            File partial = new File(target.getPath() + ".part");
            String error = null;
            try {
                Request request = new Request.Builder().url(apkUrl).build();
                try (Response response = new OkHttpClient().newCall(request).execute()) {
                    if (!response.isSuccessful() || response.body() == null) {
                        throw new IllegalStateException("HTTP " + response.code());
                    }
                    long total = response.body().contentLength();
                    try (InputStream in = response.body().byteStream();
                         OutputStream out = new FileOutputStream(partial)) {
                        byte[] buffer = new byte[16 * 1024];
                        long done = 0;
                        int lastPercent = -2;
                        int read;
                        while ((read = in.read(buffer)) != -1) {
                            out.write(buffer, 0, read);
                            done += read;
                            int percent = total > 0 ? (int) (done * 100 / total) : -1;
                            if (percent != lastPercent) {
                                lastPercent = percent;
                                String text = total > 0
                                        ? String.format(Locale.US, "Downloading\u2026 %d%%  (%.1f / %.1f MB)", percent, done / 1048576f, total / 1048576f)
                                        : String.format(Locale.US, "Downloading\u2026 %.1f MB", done / 1048576f);
                                activity.runOnUiThread(() -> progressListener.onProgress(percent, text));
                            }
                        }
                    }
                }
                if (target.exists() && !target.delete()) {
                    throw new IllegalStateException("Cannot replace previous download");
                }
                if (!partial.renameTo(target)) {
                    throw new IllegalStateException("Cannot save the downloaded file");
                }
            } catch (Exception e) {
                //noinspection ResultOfMethodCallIgnored
                partial.delete();
                error = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
            }
            String result = error;
            activity.runOnUiThread(() -> doneListener.onDone(result));
            executor.shutdown();
        });
    }

    private void install(AppCompatActivity activity, File apk) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !activity.getPackageManager().canRequestPackageInstalls()) {
                // The user has to allow installs from this app once; they tap "Install" again afterwards.
                activity.startActivity(new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,
                        Uri.parse("package:" + activity.getPackageName())));
                return;
            }
            Uri uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", apk);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception ignored) {
        }
    }

    private void openUrl(AppCompatActivity activity, String url) {
        if (url == null || url.isEmpty()) {
            return;
        }
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception ignored) {
        }
    }
}
