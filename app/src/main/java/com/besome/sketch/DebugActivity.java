package com.besome.sketch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import mod.pranav.purwar.Logger;

public class DebugActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            String error = intent.getStringExtra("error");

            if (error != null) {
                //logging to external file for easy debugging for non-root users
                Logger.log(error);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("An error occurred")
                        .setMessage(error)
                        .setPositiveButton("End Application", (dialog1, which) -> finish())
                        .create();

                TextView messageView = dialog.findViewById(android.R.id.message);
                if (messageView != null) {
                    messageView.setTextIsSelectable(true);
                }

                dialog.show();
            }
        }
    }
}
