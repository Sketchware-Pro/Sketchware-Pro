package com.besome.sketch.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.TextView;
import android.view.View;

import java.util.HashMap;

import com.google.gson.*;
import com.sketchware.remod.Resources;

import mod.RequestNetwork;
import mod.RequestNetworkController;

import a.a.a.GB;
import a.a.a.rB;
import a.a.a.xB;

public class CollectErrorActivity extends Activity {

    private HashMap<String, Object> map = new HashMap<>();
	
    private RequestNetwork reqnet;
    private RequestNetwork.RequestListener listener;
    private String webUrl = "webhook url here";
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
            AlertDialog dialog = new AlertDialog.Builder(this);
            dialog.setTitle(xB.b().a(getApplicationContext(), Resources.string.common_error_an_error_occurred));
            dialog.setMessage("An error occurred while running application.\n\nDo you want to report this error log so that we can fix it?\nNo personal information will be included.");
            dialog.setPositiveButton("send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    // left blank intentionally
                    }
            });
            dialogBuilder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
            });
            dialogBuilder.setNeutralButton("Show error", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialoginterface, int which) {
                        // do nothing because it's gonna be overrided later to stop dialog to dismiss when button is clicked to show error
                    }
            });
            dialog = dialogBuilder.create();
            dialog.show();
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
		        TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
		        messageView.setTextIsSelectable(true);
		        messageView.setText(error);
	            }
            });
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        map.put("username", "Crash Reporter");
                        map.put("avatar_url", "https://i.postimg.cc/FRZTV4jY/Sketchware-Pro.png");
                        StringBuilder content = new StringBuilder("```\nSKETCHWARE ver=" + GB.d(getApplicationContext())
                                    + "\nLocale=" + GB.g(getApplicationContext())
                                    + "\nVERSION.RELEASE: " + Build.VERSION.RELEASE
                                    + "\nBRAND: " + Build.BRAND
                                    + "\nMANUFACTURER: " + Build.MANUFACTURER
                                    + "\nMODEL: " + Build.MODEL);
                        StringBuilder length = new StringBuilder(content.toString() + "\r\n\n" + error);
                        map = new HashMap<>();
                        if (length.length() > 2000) {
                            map.put("content", content.toString() + "\n```");
                            reqnet.setParams(map, RequestNetworkController.REQUEST_BODY);
                            reqnet.startRequestNetwork("POST", webUrl, new Gson().toJson(map), listener);
                            map = new HashMap<>();
                            map.put("content", "```\n" + error + "\n```");
                            reqnet.setParams(map, RequestNetworkController.REQUEST_BODY);
                            reqnet.startRequestNetwork("POST", webUrl, new Gson().toJson(map), listener);
                        } else {
                            content.append("\r\n\n" + error);
                            content.append("\n```");
                            map.put("content", content.toString());
                            //idk why it's needed every time before starting request, without this the webhook doesn't get sent
                            reqnet.setParams(map, RequestNetworkController.REQUEST_BODY);
                            reqnet.startRequestNetwork("POST", webUrl, new Gson().toJson(map), listener);
                        }
                        Toast.makeText(getApplicationContext(), "Sending report...", Toast.LENGTH_SHORT).show();
                    }
                });
        }
    }
}