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
    private final String webUrl = "webhook url here";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RequestNetwork requestNetwork = new RequestNetwork(this);
        RequestNetwork.RequestListener listener = new RequestNetwork.RequestListener() {
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

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle(xB.b().a(getApplicationContext(), R.string.common_error_an_error_occurred))
                    .setMessage("An error occurred while running application.\n" +
                            "\n" +
                            "Do you want to report this error log so that we can fix it?\n" +
                            "No personal information will be included.")
                    .setPositiveButton("send", null)
                    .setNegativeButton("no", (dialogInterface, which) -> finish())
                    .setNeutralButton("Show error", null) // null to set proper onClick listeners later without dismissing the AlertDialog
                    .show();

            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(v -> {
                TextView messageView = dialog.findViewById(android.R.id.message);
                messageView.setTextIsSelectable(true);
                messageView.setText(error);
            });
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                String content = "```\nSKETCHWARE ver=" + GB.d(getApplicationContext())
                        + "\nLocale=" + GB.g(getApplicationContext())
                        + "\nVERSION.RELEASE: " + Build.VERSION.RELEASE
                        + "\nBRAND: " + Build.BRAND
                        + "\nMANUFACTURER: " + Build.MANUFACTURER
                        + "\nMODEL: " + Build.MODEL;

                HashMap<String, Object> map = new HashMap<>();
                if ((content + "\r\n\n" + error).length() > 2000) {
                    map.put("content", content + "\n```");
                    requestNetwork.setParams(map, RequestNetworkController.REQUEST_BODY);
                    requestNetwork.startRequestNetwork("POST", webUrl, new Gson().toJson(map), listener);
                    map = new HashMap<>();
                    map.put("content", "```\n" + error + "\n```");
                } else {
                    content += "\r\n\n" + error;
                    content += "\n```";
                    map.put("content", content);
                    //idk why it's needed every time before starting request, without this the webhook doesn't get sent
                }
                requestNetwork.setParams(map, RequestNetworkController.REQUEST_BODY);
                requestNetwork.startRequestNetwork("POST", webUrl, new Gson().toJson(map), listener);
                Toast.makeText(getApplicationContext(), "Sending crash logs...", Toast.LENGTH_SHORT).show();
            });
        }
    }
}