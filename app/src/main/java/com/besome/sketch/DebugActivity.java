package com.besome.sketch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;

public class DebugActivity extends Activity {
    private final String[] exceptionMessages = {"Invalid string operation\n", "Invalid list operation\n", "Invalid arithmetical operation\n", "Invalid toNumber block operation\n", "Invalid intent operation"};
    private final String[] exceptionTypes = {"StringIndexOutOfBoundsException", "IndexOutOfBoundsException", "ArithmeticException", "NumberFormatException", "ActivityNotFoundException"};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        StringBuilder message = new StringBuilder();
        if (intent != null) {
            String error = intent.getStringExtra("error");
            String[] exceptionLines = error.split("\n");
            int i = 0;
            while (true) {
                try {
                    if (i >= exceptionTypes.length) {
                        break;
                    } else if (exceptionLines[0].contains(exceptionTypes[i])) {
                        String exceptionMessage = exceptionMessages[i];
                        int indexOf = exceptionLines[0].indexOf(exceptionTypes[i]) + exceptionTypes[i].length();
                        message = new StringBuilder(exceptionMessage + exceptionLines[0].substring(indexOf));
                        message.append("\n\nDetailed error message:\n").append(error);
                        break;
                    } else {
                        i++;
                    }
                } catch (Exception e) {
                    message.append("\n\nError while getting error: ").append(Log.getStackTraceString(e));
                }
            }
            if (message.length() == 0) {
                message = new StringBuilder(error);
            }
        }
        AlertDialog builder = new AlertDialog.Builder(this)
        .setTitle("An error occurred")
        .setMessage(message.toString())
        .setPositiveButton("End Application", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        })
        .create();
        builder.show();
        TextView messageView=(TextView) builder.findViewById(android.R.id.message);
        if (messageView != null) {
            messageView.setTextIsSelectable(true);
        }
    }
}
