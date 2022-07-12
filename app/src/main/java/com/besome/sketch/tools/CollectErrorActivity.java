package com.besome.sketch.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.sketchware.remod.R;

import a.a.a.GB;
import a.a.a.rB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class CollectErrorActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            String error = intent.getStringExtra("error");
            new AlertDialog.Builder(this)
                    .setTitle(Helper.getResString(R.string.common_error_an_error_occurred))
                    .setMessage("An error occurred while running application.\nDo you want to send this error log?")
                    .setPositiveButton("send", (dialog, which) -> new a().execute(("SKETCHWARE ver=" + GB.d(getApplicationContext())
                            + "\nLocale=" + GB.g(getApplicationContext())
                            + "\nVERSION.RELEASE : " + Build.VERSION.RELEASE
                            + "\nBOARD : " + Build.BOARD
                            + "\nBOOTLOADER : " + Build.BOOTLOADER
                            + "\nBRAND : " + Build.BRAND
                            + "\nCPU_ABI : " + Build.CPU_ABI
                            + "\nCPU_ABI2 : " + Build.CPU_ABI2
                            + "\nDISPLAY : " + Build.DISPLAY
                            + "\nFINGERPRINT : " + Build.FINGERPRINT
                            + "\nHARDWARE : " + Build.HARDWARE
                            + "\nMANUFACTURER : " + Build.MANUFACTURER
                            + "\nMODEL : " + Build.MODEL
                            + "\r\n") + error))
                    .setNegativeButton("no", (dialog, which) -> finish()).show();
        }
    }

    class a extends AsyncTask<String, String, String> {

        @Override
        public String doInBackground(String... strArr) {
            new rB().a(strArr[0]);
            return null;
        }

        @Override
        public void onPostExecute(String str) {
            super.onPostExecute(str);
            finish();
        }
    }
}
