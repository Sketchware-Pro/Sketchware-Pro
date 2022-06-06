package <?package_name?>;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
                .setPositiveButton("Dismiss & Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        clearErrorLog(DebugActivity.this);
                    }
                })
                .create();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                DebugActivity.this.finish();
            }
        });
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                ((TextView) dialog.findViewById(android.R.id.message)).setTextIsSelectable(true);
            }
        });
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