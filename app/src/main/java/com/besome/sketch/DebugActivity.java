package com.besome.sketch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DebugActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            String error = intent.getStringExtra("error");

            if (error != null) {
                Intent intent=new Intent(context,Debug4Activty.class);
                i.putExtra("error", error);
                startActivity(intent);
                finish();
                }


            }
        }
    }
}
