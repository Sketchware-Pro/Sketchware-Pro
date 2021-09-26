package com.besome.sketch.editor.manage.library.firebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class ManageFirebaseActivity extends BaseAppCompatActivity implements View.OnClickListener {

    public LinearLayout switchLayout;
    public Switch libSwitch;
    public TextView tvProjectId;
    public TextView tvAppId;
    public TextView tvApiKey;
    public TextView tvStorageUrl;
    public Toolbar toolbar;
    public Button btnConsole;
    public DB s = null;
    public ProjectLibraryBean firebaseLibraryBean;

    private void initializeLibrary(ProjectLibraryBean libraryBean) {
        firebaseLibraryBean = libraryBean;
        configure();
    }

    private void goToConsole() {
        if (GB.h(getApplicationContext())) {
            try {
                Uri consoleUrl = Uri.parse("https://console.firebase.google.com");
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(consoleUrl);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                this.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                downloadChromeDialog();
            }
        } else {
            bB.a(getApplicationContext(), Helper.getResString(2131624932), 0).show();
        }

    }

    private void openDoc() {
        try {
            this.s.a("P1I15", true);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("https://docs.sketchware.io/docs/firebase-getting-started.html"));
            intent.addFlags(1);
            intent.addFlags(2);
            intent.addFlags(64);
            this.startActivity(intent);
        } catch (Exception e) {
            downloadChromeDialog();
        }

    }

    private void downloadChromeDialog() {
        final aB dialog = new aB(this);
        dialog.a(2131165415);
        dialog.b(Helper.getResString(2131626412));
        dialog.a(Helper.getResString(2131625629));
        dialog.b(Helper.getResString(2131625010), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(2131624974), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void configureLibraryDialog() {
        final aB dialog = new aB(this);
        dialog.b(Helper.getResString(2131625047));
        dialog.a(2131165524);
        dialog.a(Helper.getResString(2131625214));
        dialog.setCancelable(false);
        dialog.b(Helper.getResString(2131624986), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseLibraryBean.useYn = "N";
                libSwitch.setChecked(false);
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(2131624974), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                libSwitch.setChecked(true);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 237 && resultCode == -1) {
            initializeLibrary((ProjectLibraryBean) data.getParcelableExtra("firebase"));
        }
    }

    @Override
    public void onBackPressed() {
        getIntent().putExtra("firebase", firebaseLibraryBean);
        setResult(-1, getIntent());
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case 2131230815:
                goToConsole();
                break;

            case 2131231408:
                libSwitch.setChecked(libSwitch.isChecked() ^ true);
                if ("Y".equals(firebaseLibraryBean.useYn) && !libSwitch.isChecked()) {
                    configureLibraryDialog();
                } else {
                    firebaseLibraryBean.useYn = "Y";
                }
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427547);
        toolbar = (Toolbar) findViewById(2131231847);
        a(toolbar);
        findViewById(2131231370).setVisibility(8);
        d().a(xB.b().a(super.e, 2131625235));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        s = new DB(getApplicationContext(), "P1");
        firebaseLibraryBean = (ProjectLibraryBean) getIntent().getParcelableExtra("firebase");
        switchLayout = (LinearLayout) findViewById(2131231408);
        switchLayout.setOnClickListener(this);
        libSwitch = (Switch) findViewById(2131231429);
        ((TextView) findViewById(2131231965)).setText(Helper.getResString(2131625249));
        ((TextView) findViewById(2131232232)).setText(xB.b().a(super.e, 2131625238));
        ((TextView) findViewById(2131232199)).setText(xB.b().a(super.e, 2131625232));
        ((TextView) findViewById(2131232197)).setText(xB.b().a(super.e, 2131625231));
        ((TextView) findViewById(2131232241)).setText(xB.b().a(super.e, 2131625239));
        tvProjectId = (TextView) findViewById(2131232089);
        tvAppId = (TextView) findViewById(2131231880);
        tvApiKey = (TextView) findViewById(2131231875);
        tvStorageUrl = (TextView) findViewById(2131232181);
        btnConsole = (Button) findViewById(2131230815);
        btnConsole.setText(Helper.getResString(2131625207));
        btnConsole.setOnClickListener(this);
        configure();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131492876, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131231507:
                openDoc();
                break;
            case 2131231508:
                toFirebaseActivity();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    public void onResume() {
        super.onResume();
    }

    private void toFirebaseActivity() {
        Intent intent = new Intent(getApplicationContext(), FirebaseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("firebase", firebaseLibraryBean);
        startActivityForResult(intent, 237);
    }

    private void configure() {
        libSwitch.setChecked("Y".equals(firebaseLibraryBean.useYn));
        String projectId = firebaseLibraryBean.data;
        if (projectId != null && projectId.length() > 0) {
            tvProjectId.setText(firebaseLibraryBean.data);
        }

        String appId = firebaseLibraryBean.reserved1;
        if (appId != null && appId.length() > 0) {
            tvAppId.setText(firebaseLibraryBean.reserved1);
        }

        String apiKey = firebaseLibraryBean.reserved2;
        if (apiKey != null && apiKey.length() > 0) {
            tvApiKey.setText(firebaseLibraryBean.reserved2);
        }

        String storageUrl = firebaseLibraryBean.reserved3;
        if (storageUrl != null && storageUrl.length() > 0) {
            tvStorageUrl.setText(firebaseLibraryBean.reserved3);
        }

    }
}
