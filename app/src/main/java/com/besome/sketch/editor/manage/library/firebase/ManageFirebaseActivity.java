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
import com.sketchware.remod.R;

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class ManageFirebaseActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private Switch libSwitch;
    private TextView tvProjectId;
    private TextView tvAppId;
    private TextView tvApiKey;
    private TextView tvStorageUrl;
    private DB s = null;
    private ProjectLibraryBean firebaseLibraryBean;

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
            bB.a(getApplicationContext(), Helper.getResString(R.string.common_message_check_network), bB.TOAST_NORMAL).show();
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
        dialog.a(R.drawable.chrome_96);
        dialog.b(Helper.getResString(R.string.title_compatible_chrome_browser));
        dialog.a(Helper.getResString(R.string.message_compatible_chrome_brower));
        dialog.b(Helper.getResString(R.string.common_word_ok), v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=com.android.chrome"));
            startActivity(intent);
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void configureLibraryDialog() {
        final aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.common_word_warning));
        dialog.a(R.drawable.delete_96);
        dialog.a(Helper.getResString(R.string.design_library_firebase_dialog_description_confirm_uncheck_firebase));
        dialog.setCancelable(false);
        dialog.b(Helper.getResString(R.string.common_word_delete), v -> {
            firebaseLibraryBean.useYn = "N";
            libSwitch.setChecked(false);
            dialog.dismiss();
        });
        dialog.a(Helper.getResString(R.string.common_word_cancel), v -> {
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
        int id = view.getId();
        if (id == R.id.btn_console) {
            goToConsole();
        } else if (id == R.id.layout_switch) {
            libSwitch.setChecked(!libSwitch.isChecked());
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
        setContentView(R.layout.manage_library_manage_firebase);

        Toolbar toolbar = findViewById(R.id.toolbar);
        a(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().a(xB.b().a(this, R.string.design_library_firebase_title_firebase_manager));
        d().e(true);
        d().d(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));

        s = new DB(getApplicationContext(), "P1");
        firebaseLibraryBean = getIntent().getParcelableExtra("firebase");
        LinearLayout switchLayout = findViewById(R.id.layout_switch);
        switchLayout.setOnClickListener(this);
        libSwitch = findViewById(R.id.lib_switch);
        ((TextView) findViewById(R.id.tv_enable)).setText(Helper.getResString(R.string.design_library_settings_title_enabled));
        ((TextView) findViewById(R.id.tv_title_project_id)).setText(
                xB.b().a(this, R.string.design_library_firebase_title_project_id));
        ((TextView) findViewById(R.id.tv_title_app_id)).setText(
                xB.b().a(this, R.string.design_library_firebase_title_app_id));
        ((TextView) findViewById(R.id.tv_title_api_key)).setText(
                xB.b().a(this, R.string.design_library_firebase_title_api_key));
        ((TextView) findViewById(R.id.tv_title_storage_url)).setText(
                xB.b().a(this, R.string.design_library_firebase_title_storage_bucket_url));
        tvProjectId = findViewById(R.id.tv_project_id);
        tvAppId = findViewById(R.id.tv_app_id);
        tvApiKey = findViewById(R.id.tv_api_key);
        tvStorageUrl = findViewById(R.id.tv_storage_url);
        Button btnConsole = findViewById(R.id.btn_console);
        btnConsole.setText(Helper.getResString(R.string.design_library_firebase_button_goto_firebase_console));
        btnConsole.setOnClickListener(this);
        configure();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manage_firebase_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.menu_firebase_help) {
            openDoc();
        } else if (itemId == R.id.menu_firebase_settings) {
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
