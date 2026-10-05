package pro.sketchware.control;

import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONObject;

import pro.sketchware.BuildConfig;
import pro.sketchware.utility.Network;

/**
 * Checks a remote manifest for a newer app version and, when the update is marked mandatory,
 * shows a non-dismissable dialog that forces the user to update before continuing.
 *
 * The manifest (update.json) lives at the repository root and is served raw from GitHub:
 * {"versionCode": N, "versionName": "...", "mandatory": true, "url": "...", "notes": "..."}.
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
                showUpdateDialog(activity, mandatory, versionName, notes, url);
            } catch (Exception ignored) {
                // Malformed or unreachable manifest: fail silently, never block the app on our own bug.
            }
        });
    }

    private void showUpdateDialog(AppCompatActivity activity, boolean mandatory,
                                  String versionName, String notes, String url) {
        StringBuilder message = new StringBuilder();
        if (!versionName.isEmpty()) {
            message.append("Version ").append(versionName).append('\n');
        }
        message.append(notes.isEmpty() ? "A new version is available." : notes);

        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity)
                .setTitle("Update available")
                .setMessage(message.toString())
                .setCancelable(!mandatory)
                .setPositiveButton("Update now", null);
        if (!mandatory) {
            builder.setNegativeButton("Later", null);
        }

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(!mandatory);
        dialog.show();

        // Keep the dialog on screen for a mandatory update: tapping "Update now" opens the
        // download page but does not dismiss, so the app stays blocked until the user installs
        // the newer build (which carries a higher versionCode and clears this check).
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> openUrl(activity, url));
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
