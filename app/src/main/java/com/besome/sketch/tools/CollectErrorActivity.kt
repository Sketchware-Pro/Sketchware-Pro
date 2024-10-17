package com.besome.sketch.tools

import a.a.a.GB
import a.a.a.xB
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.DialogInterface
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.besome.sketch.lib.base.BaseAppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sketchware.remod.R
import mod.SketchwareUtil
import java.io.File

class CollectErrorActivity : BaseAppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        if (intent != null) {
            val error = intent.getStringExtra("error")

            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle(xB.b().a(applicationContext, R.string.common_error_an_error_occurred))
                .setMessage(
                    "An error occurred while running Sketchware Pro. " +
                            "Do you want to report this error log so that we can fix it? " +
                            "No personal information will be included."
                )
                .setPositiveButton("Copy", null)
                .setNegativeButton(
                    "Cancel"
                ) { _: DialogInterface?, _: Int -> finish() }
                .setNeutralButton(
                    "Show error",
                    null
                ) // null to set proper onClick listeners later without dismissing the AlertDialog
                .show()

            val messageView = dialog.findViewById<TextView>(android.R.id.message)

            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener {
                messageView!!.setTextIsSelectable(true)
                messageView.text = error
            }
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val info: PackageInfo
                try {
                    info = packageManager.getPackageInfo(packageName, 0)
                } catch (e: PackageManager.NameNotFoundException) {
                    messageView!!.setTextIsSelectable(true)
                    messageView.text =
                        "Somehow couldn't get package info. Stack trace:\n" + Log.getStackTraceString(
                            e
                        )
                    return@setOnClickListener
                }

                val fileSizeInBytes = File(info.applicationInfo.sourceDir).length()

                val deviceInfo =
                    ("""Sketchware Pro ${info.versionName} (${info.versionCode})
base.apk size: """ + Formatter.formatFileSize(
                        this,
                        fileSizeInBytes
                    ) + " (" + fileSizeInBytes + " B)\n"
                            + "Locale: " + GB.g(applicationContext) + "\n"
                            + "SDK version: " + Build.VERSION.SDK_INT + "\n"
                            + "Brand: " + Build.BRAND + "\n"
                            + "Manufacturer: " + Build.MANUFACTURER + "\n"
                            + "Model: " + Build.MODEL)

                val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("error", "$deviceInfo\n\n```\n$error\n```")
                clipboard.setPrimaryClip(clip)
                runOnUiThread { SketchwareUtil.toast("Copied", Toast.LENGTH_LONG) }
            }
        }
    }
}
