package com.besome.sketch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class DebugActivity extends Activity {
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new AlertDialog.Builder(this)
                .setTitle("Crash Log")
                .setMessage(getLastSavedErrorLog(this))
                .setNeutralButton("Dismiss", null)
                .setPositiveButton("Dismiss & Clear", (dialogInterface, which) -> clearErrorLog(this))
                .create();

        dialog.setOnDismissListener(dialogInterface -> finish());
        dialog.setOnShowListener(dialogInterface -> ((TextView) dialog.findViewById(android.R.id.message)).setTextIsSelectable(true));
        dialog.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @SuppressLint("ApplySharedPref")
    public static void saveErrorLog(Context context, String errorLog) {
        context.getSharedPreferences("SK_CRASH_LOG", Context.MODE_PRIVATE).edit().putString("errorLog", errorLog).commit();
    }

    public static void clearErrorLog(Context context) {
        context.getSharedPreferences("SK_CRASH_LOG", Context.MODE_PRIVATE).edit().remove("errorLog").apply();
    }

    public static String getLastSavedErrorLog(Context context) {
        return context.getSharedPreferences("SK_CRASH_LOG", Context.MODE_PRIVATE).getString("errorLog", "");
    }
}
