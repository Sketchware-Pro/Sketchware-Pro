package com.besome.sketch.tools;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;

import a.a.a.GB;
import pro.sketchware.utility.SketchwareUtil;

public class CollectErrorActivity extends BaseAppCompatActivity {
    private boolean isErrorExpanded = false;
    private String originalMessage;
    private String fullError;

    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent == null || !intent.hasExtra("error")) {
            finish();
            return;
        }

        fullError = intent.getStringExtra("error");
        originalMessage = "An error occurred while running Sketchware Pro.\n\n" +
                "Do you want to report this error log so that we can fix it? " +
                "No personal information will be included.";

        var dialog = new MaterialAlertDialogBuilder(this)
                .setTitle("Error Occurred")
                .setMessage(originalMessage)
                .setPositiveButton("Copy", null)
                .setNegativeButton("Cancel", (d, which) -> finishAffinity())
                .setNeutralButton("Expand", null)
                .setOnCancelListener(d -> finishAffinity()) // Closes app if user taps outside or presses back
                .show();

        TextView messageView = dialog.findViewById(android.R.id.message);

        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v -> {
            if (isErrorExpanded) {
                messageView.setText(originalMessage);
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setText("Expand");
            } else {
                messageView.setTextIsSelectable(true);
                messageView.setText(fullError);
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setText("Collapse");
            }
            isErrorExpanded = !isErrorExpanded;
        });

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            try {
                PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
                long fileSizeInBytes = new File(info.applicationInfo.sourceDir).length();

                String deviceInfo = "Sketchware Pro " + info.versionName + " (" + info.versionCode + ")\n"
                        + "base.apk size: " + Formatter.formatFileSize(this, fileSizeInBytes) + " (" + fileSizeInBytes + " B)\n"
                        + "Locale: " + GB.g(this) + "\n"
                        + "SDK version: " + Build.VERSION.SDK_INT + "\n"
                        + "Brand: " + Build.BRAND + "\n"
                        + "Manufacturer: " + Build.MANUFACTURER + "\n"
                        + "Model: " + Build.MODEL;

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("error", deviceInfo + "\n\n```\n" + fullError + "\n```");
                clipboard.setPrimaryClip(clip);
                SketchwareUtil.toast("Copied", Toast.LENGTH_LONG);
                finishAffinity(); // Closes the app after copying
            } catch (PackageManager.NameNotFoundException e) {
                messageView.setTextIsSelectable(true);
                messageView.setText("Couldn't get package info:\n" + Log.getStackTraceString(e));
            } catch (Exception e) {
                SketchwareUtil.toast("Failed to copy: " + e.getMessage(), Toast.LENGTH_LONG);
            }
        });
    }
}