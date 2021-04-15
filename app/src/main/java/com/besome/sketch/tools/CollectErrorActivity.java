package com.besome.sketch.tools;

import a.a.a.SH;
import a.a.a.TH;
import a.a.a.rB;
import a.a.a.xB;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import com.android.sdklib.internal.avd.HardwareProperties;
import com.besome.sketch.SketchApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class CollectErrorActivity extends Activity {
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        if (intent != null) {
            String stringExtra = intent.getStringExtra("error");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(xB.b().a(getApplicationContext(), 2131624908));
            builder.setMessage("An error occurred while running application.\\nDo you want to send this error log?");
            builder.setPositiveButton((CharSequence) "send", (DialogInterface.OnClickListener) new SH(this, stringExtra));
            builder.setNegativeButton((CharSequence) HardwareProperties.BOOLEAN_NO, (DialogInterface.OnClickListener) new TH(this));
            builder.create().show();
        }
    }

    public void onResume() {
        super.onResume();
        Tracker a2 = ((SketchApplication) getApplication()).a();
        a2.setScreenName(CollectErrorActivity.class.getSimpleName().toString());
        a2.send(new HitBuilders.ScreenViewBuilder().build());
    }

    class a extends AsyncTask<String, String, String> {
        public a() {
        }

        public String doInBackground(String... strArr) {
            new rB().a(strArr[0]);
            return null;
        }

        public void onPostExecute(String str) {
            super.onPostExecute(str);
            CollectErrorActivity.this.finish();
        }
    }
}
