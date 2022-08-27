package com.besome.sketch.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sketchware.remod.R;

import java.util.HashMap;

import a.a.a.GB;
import a.a.a.xB;
import mod.RequestNetwork;
import mod.RequestNetworkController;

public class CollectErrorActivity extends Activity {
    private RequestNetwork reqnet;
    private RequestNetwork.RequestListener listener;
    private final String webUrl = "webhook url here";
    private AlertDialog dialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reqnet = new RequestNetwork(this);
        listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String tag, String message, HashMap<String, Object> param3) {
                Toast.makeText(getApplicationContext(), "Report sent!", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onErrorResponse(String tag, String message) {
                Toast.makeText(getApplicationContext(), "Cannot report the error. You can try reporting manually to our discord server", Toast.LENGTH_LONG).show();
            }
        };
        Intent intent = getIntent();
        if (intent != null) {
            final String error = intent.getStringExtra("error");
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle(xB.b().a(getApplicationContext(), R.string.common_error_an_error_occurred));
            dialogBuilder.setMessage("An error occurred while running application.\n\nDo you want to report this error log so that we can fix it?\nNo personal information will be included.");
            dialogBuilder.setPositiveButton("send", (dialog, which) -> {
                // left blank intentionally
            });
            dialogBuilder.setNegativeButton("no", (dialog, which) -> finish());
            dialogBuilder.setNeutralButton("Show error", (dialog, which) -> {
                // do nothing because it's gonna be overrided later to stop dialog to dismiss when button is clicked to show error
            });
            dialog = dialogBuilder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v -> {
                TextView messageView = dialog.findViewById(android.R.id.message);
                messageView.setTextIsSelectable(true);
                messageView.setText(error);
            });
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                StringBuilder content = new StringBuilder("```\nSKETCHWARE ver=" + GB.d(getApplicationContext())
                        + "\nLocale=" + GB.g(getApplicationContext())
                        + "\nVERSION.RELEASE: " + Build.VERSION.RELEASE
                        + "\nBRAND: " + Build.BRAND
                        + "\nMANUFACTURER: " + Build.MANUFACTURER
                        + "\nMODEL: " + Build.MODEL);

                HashMap<String, Object> map = new HashMap<>();
                if ((content + "\r\n\n" + error).length() > 2000) {
                    map.put("content", content + "\n```");
                    reqnet.setParams(map, RequestNetworkController.REQUEST_BODY);
                    reqnet.startRequestNetwork("POST", webUrl, new Gson().toJson(map), listener);
                    map = new HashMap<>();
                    map.put("content", "```\n" + error + "\n```");
                } else {
                    content.append("\r\n\n" + error);
                    content.append("\n```");
                    map.put("content", content.toString());
                    //idk why it's needed every time before starting request, without this the webhook doesn't get sent
                }
                reqnet.setParams(map, RequestNetworkController.REQUEST_BODY);
                reqnet.startRequestNetwork("POST", webUrl, new Gson().toJson(map), listener);
                Toast.makeText(getApplicationContext(), "Sending crash logs...", Toast.LENGTH_SHORT).show();
            });
        }
    }
}