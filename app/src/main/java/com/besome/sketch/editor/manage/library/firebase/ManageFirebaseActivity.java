package com.besome.sketch.editor.manage.library.firebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.sketchware.remod.Resources;

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
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(consoleUrl);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
                downloadChromeDialog();
            }
        } else {
            bB.a(getApplicationContext(), Helper.getResString(Resources.string.common_message_check_network), Toast.LENGTH_SHORT).show();
        }
    }

    private void openDoc() {
        try {
            s.a("P1I15", true);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("https://docs.sketchware.io/docs/firebase-getting-started.html"));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            startActivity(intent);
        } catch (Exception e) {
            downloadChromeDialog();
        }
    }

    private void downloadChromeDialog() {
        final aB dialog = new aB(this);
        dialog.a(Resources.drawable.chrome_96);
        dialog.b(Helper.getResString(Resources.string.title_compatible_chrome_browser));
        dialog.a(Helper.getResString(Resources.string.message_compatible_chrome_brower));
        dialog.b(Helper.getResString(Resources.string.common_word_ok), v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=com.android.chrome"));
            startActivity(intent);
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(Resources.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void configureLibraryDialog() {
        final aB dialog = new aB(this);
        dialog.b(Helper.getResString(Resources.string.common_word_warning));
        dialog.a(2131165524);
        dialog.a(Helper.getResString(Resources.string.design_library_firebase_dialog_description_confirm_uncheck_firebase));
        dialog.setCancelable(false);
        dialog.b(Helper.getResString(Resources.string.common_word_delete), v -> {
            firebaseLibraryBean.useYn = "N";
            libSwitch.setChecked(false);
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(Resources.string.common_word_cancel), v -> {
            libSwitch.setChecked(true);
            dialog.dismiss();
        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 237 && resultCode == RESULT_OK) {
            initializeLibrary(data.getParcelableExtra("firebase"));
        }
    }

    @Override
    public void onBackPressed() {
        getIntent().putExtra("firebase", firebaseLibraryBean);
        setResult(RESULT_OK, getIntent());
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case Resources.id.btn_console:
                goToConsole();
                break;

            case Resources.id.layout_switch:
                libSwitch.setChecked(libSwitch.isChecked() ^ true);
                if ("Y".equals(firebaseLibraryBean.useYn) && !libSwitch.isChecked()) {
                    configureLibraryDialog();
                } else {
                    firebaseLibraryBean.useYn = "Y";
                }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.manage_library_manage_firebase);

        toolbar = findViewById(Resources.id.toolbar);
        a(toolbar);
        findViewById(Resources.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(super.e, Resources.string.design_library_firebase_title_firebase_manager));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

        s = new DB(getApplicationContext(), "P1");
        firebaseLibraryBean = getIntent().getParcelableExtra("firebase");
        switchLayout = findViewById(Resources.id.layout_switch);
        switchLayout.setOnClickListener(this);
        libSwitch = findViewById(Resources.id.lib_switch);
        ((TextView) findViewById(Resources.id.tv_enable)).setText(Helper.getResString(Resources.string.design_library_settings_title_enabled));
        ((TextView) findViewById(Resources.id.tv_title_project_id)).setText(xB.b().a(super.e, Resources.string.design_library_firebase_title_project_id));
        ((TextView) findViewById(Resources.id.tv_title_app_id)).setText(xB.b().a(super.e, Resources.string.design_library_firebase_title_app_id));
        ((TextView) findViewById(Resources.id.tv_title_api_key)).setText(xB.b().a(super.e, Resources.string.design_library_firebase_title_api_key));
        ((TextView) findViewById(Resources.id.tv_title_storage_url)).setText(xB.b().a(super.e, Resources.string.design_library_firebase_title_storage_bucket_url));
        tvProjectId = findViewById(Resources.id.tv_project_id);
        tvAppId = findViewById(Resources.id.tv_app_id);
        tvApiKey = findViewById(Resources.id.tv_api_key);
        tvStorageUrl = findViewById(Resources.id.tv_storage_url);
        btnConsole = findViewById(Resources.id.btn_console);
        btnConsole.setText(Helper.getResString(Resources.string.design_library_firebase_button_goto_firebase_console));
        btnConsole.setOnClickListener(this);
        configure();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(Resources.menu.manage_firebase_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case Resources.id.menu_firebase_help:
                openDoc();
                break;
            case Resources.id.menu_firebase_settings:
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
