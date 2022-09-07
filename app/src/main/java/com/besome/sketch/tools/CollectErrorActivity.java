package com.besome.sketch.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sketchware.remod.BuildConfig;
import com.sketchware.remod.R;

import java.io.File;
import java.util.HashMap;

import a.a.a.GB;
import a.a.a.xB;
import mod.RequestNetwork;
import mod.RequestNetworkController;
import mod.SketchwareUtil;

public class CollectErrorActivity extends Activity {
    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RequestNetwork requestNetwork = new RequestNetwork(this);
        WebhookListener listener = new WebhookListener();
        Intent intent = getIntent();
        if (intent != null) {
            final String error = intent.getStringExtra("error");

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(xB.b().a(getApplicationContext(), R.string.common_error_an_error_occurred))
                    .setMessage("An error occurred while running Sketchware Pro. " +
                            "Do you want to report this error log so that we can fix it? " +
                            "No personal information will be included.")
                    .setPositiveButton("Send", null)
                    .setNegativeButton("Cancel", (dialogInterface, which) -> finish())
                    .setNeutralButton("Show error", null) // null to set proper onClick listeners later without dismissing the AlertDialog
                    .show();

            TextView messageView = dialog.findViewById(android.R.id.message);

            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v -> {
                messageView.setTextIsSelectable(true);
                messageView.setText(error);
            });
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                PackageInfo info;

                try {
                    info = getPackageManager().getPackageInfo(getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    messageView.setTextIsSelectable(true);
                    messageView.setText("Somehow couldn't get package info. Stack trace:\n" + Log.getStackTraceString(e));
                    return;
                }

                long fileSizeInBytes = new File(info.applicationInfo.sourceDir).length();

                String deviceInfo = "Sketchware Pro " + info.versionName + " (" + info.versionCode + ")\n"
                        + "base.apk size: " + Formatter.formatFileSize(this, fileSizeInBytes) + " (" + fileSizeInBytes + " B)\n"
                        + "Locale: " + GB.g(getApplicationContext()) + "\n"
                        + "SDK version: " + Build.VERSION.SDK_INT + "\n"
                        + "Brand: " + Build.BRAND + "\n"
                        + "Manufacturer: " + Build.MANUFACTURER + "\n"
                        + "Model: " + Build.MODEL + "\n";

                new Thread(() -> {
                    String stackTrace = error;
                    String webhookContent;
                    int i = 0;
                    do {
                        int maxLength = i == 0 ?
                                2000 - deviceInfo.length() - 5 /* \n```\n */ - 3 /* ``` */
                                : 2000 - 5 /* \n```\n */ - 3 /* ``` */;
                        webhookContent = i == 0 ? deviceInfo : "";
                        webhookContent += "\n```\n";

                        if (stackTrace.length() > maxLength) {
                            String toProcess = stackTrace.substring(0, maxLength);
                            int lastNewlineIndex = toProcess.lastIndexOf('\n');

                            if (lastNewlineIndex != -1) {
                                webhookContent += toProcess.substring(0, lastNewlineIndex + 1);
                                stackTrace = stackTrace.substring(lastNewlineIndex + 1, stackTrace.length() - 1);
                            } else {
                                webhookContent += toProcess;
                                stackTrace = stackTrace.substring(maxLength);
                            }
                        } else {
                            webhookContent += stackTrace;
                            stackTrace = "";
                        }

                        webhookContent += "```";

                        HashMap<String, Object> params = new HashMap<>();
                        params.put("content", webhookContent);
                        requestNetwork.setParams(params, RequestNetworkController.REQUEST_BODY);
                        requestNetwork.startRequestNetworkSynchronized(RequestNetworkController.POST, BuildConfig.CRASH_REPORT_WEBHOOK_URL, new Gson().toJson(params), listener);
                        i++;
                    } while (!stackTrace.isEmpty());

                    if (!listener.hasFailed()) {
                        runOnUiThread(() -> SketchwareUtil.toast("Sending crash logs…", Toast.LENGTH_LONG));
                    }
                }).start();
            });
        }
    }

    private class WebhookListener implements RequestNetwork.RequestListener {

        private boolean failed = false;

        @Override
        public void onResponse(String tag, String response, HashMap<String, Object> responseHeaders) {
            SketchwareUtil.toast("Report sent!");
            finish();
        }

        @Override
        public void onErrorResponse(String tag, String message) {
            failed = true;
            SketchwareUtil.toast("Couldn't report the error. You can try reporting the crash manually in our Discord server.", Toast.LENGTH_LONG);
        }

        public boolean hasFailed() {
            return failed;
        }
    }
}