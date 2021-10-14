package com.besome.sketch.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reqnet = new RequestNetwork(this);
        listener = new RequestNetwork.RequestListener() {
		@Override
		public void onResponse(String tag, String message, HashMap<String, Object> param3) {
			finish();
		}
		
		@Override
		public void onErrorResponse(String tag, String message) {
			Toast.makeText(getApplicationContext(), "Cannot report the error. You can try reporting manually to our discord server", Toast.LENGTH_LONG).show();
		}
	};
        Intent intent = getIntent();
        if (intent != null) {
            String error = intent.getStringExtra("error");
            AlertDialog dialog = AlertDialog.Builder(this);
            dialog.setTitle(xB.b().a(getApplicationContext(), Resources.string.common_error_an_error_occurred));
            dialog.setMessage("An error occurred while running application.\nDo you want to send this error log?");
            dialog.setPositiveButton("send", new DialogInterface.OnClickListener() {
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
                        }
                 });
                dialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                });
                dialog.setNeutralButton("Show error", new DialogInterface.onClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
                            messageView.setText(error);
                            messageView.setTextIsSelectable(true);
                        }
                });
                dialog.show();
        }
    }

    class a extends AsyncTask<String, String, String> {

        public String doInBackground(String... strArr) {
            new rB().a(strArr[0]);
            return null;
        }

        public void onPostExecute(String str) {
            super.onPostExecute(str);
            finish();
        }
    }
}
