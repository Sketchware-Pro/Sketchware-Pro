package com.besome.sketch.editor.manage.library.firebase;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.github.angads25.filepicker.model.DialogConfigs;
import com.github.angads25.filepicker.model.DialogProperties;
import com.github.angads25.filepicker.view.FilePickerDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.bB;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class ManageFirebaseActivity extends BaseAppCompatActivity implements View.OnClickListener {
    private final String realtime_db = "realtime_db";
    private final String app_id = "app_id";
    private final String api_key = "api_key";
    private final String storage_bucket = "storage_bucket";
    private MaterialSwitch libSwitch;
    private TextView tvProjectId;
    private TextView tvAppId;
    private TextView tvApiKey;
    private TextView tvStorageUrl;
    private DB s = null;
    private ProjectLibraryBean firebaseLibraryBean;
    private final ActivityResultLauncher<Intent> openSettings = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            assert result.getData() != null;
            initializeLibrary(result.getData().getParcelableExtra("firebase"));
        }
    });
    private String sc_id;

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
            intent.setData(Uri.parse("https://sketchware-pro.vercel.app/docs/components/Firebase/connect-to-firebase"));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            startActivity(intent);
        } catch (Exception e) {
            downloadChromeDialog();
        }
    }

    private void downloadChromeDialog() {
        final MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setIcon(R.drawable.chrome_96);
        dialog.setTitle(Helper.getResString(R.string.title_compatible_chrome_browser));
        dialog.setMessage(Helper.getResString(R.string.message_compatible_chrome_brower));
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_ok), (v, which) -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=com.android.chrome"));
            startActivity(intent);
            v.dismiss();
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), null);
        dialog.show();
    }

    private void configureLibraryDialog() {
        final MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle(Helper.getResString(R.string.common_word_warning));
        dialog.setIcon(R.drawable.delete_96);
        dialog.setMessage(Helper.getResString(R.string.design_library_firebase_dialog_description_confirm_uncheck_firebase));
        dialog.setCancelable(false);
        dialog.setPositiveButton(Helper.getResString(R.string.common_word_delete), (v, which) -> {
            firebaseLibraryBean.useYn = "N";
            libSwitch.setChecked(false);
            v.dismiss();
        });
        dialog.setNegativeButton(Helper.getResString(R.string.common_word_cancel), (v, which) -> {
            libSwitch.setChecked(true);
            v.dismiss();
        });
        dialog.show();
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
        } else if (id == R.id.btn_import) {
            showImportJsonDialog();
        } else if (id == R.id.layout_switch) {
            //Enable Disable Firebase
            if (libSwitch.isChecked() || !firebaseLibraryBean.data.isEmpty()) {
                libSwitch.setChecked(!libSwitch.isChecked());
                if ("Y".equals(firebaseLibraryBean.useYn) && !libSwitch.isChecked()) {
                    configureLibraryDialog();
                } else {
                    firebaseLibraryBean.useYn = "Y";
                }
            } else {
                SketchwareUtil.toast("Configure Firebase settings first, either by importing google-services.json, " +
                        "or by manually entering the project's details.", Toast.LENGTH_LONG);
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_library_manage_firebase);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        getSupportActionBar().setTitle(Helper.getResString(R.string.design_library_firebase_title_firebase_manager));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        s = new DB(getApplicationContext(), "P1");
        firebaseLibraryBean = getIntent().getParcelableExtra("firebase");
        sc_id = savedInstanceState != null ? savedInstanceState.getString("sc_id") : getIntent().getStringExtra("sc_id");
        LinearLayout switchLayout = findViewById(R.id.layout_switch);
        switchLayout.setOnClickListener(this);
        libSwitch = findViewById(R.id.lib_switch);
        ((TextView) findViewById(R.id.tv_enable)).setText(Helper.getResString(R.string.design_library_settings_title_enabled));
        ((TextView) findViewById(R.id.tv_title_project_id)).setText(
                Helper.getResString(R.string.design_library_firebase_title_project_id));
        ((TextView) findViewById(R.id.tv_title_app_id)).setText(
                Helper.getResString(R.string.design_library_firebase_title_app_id));
        ((TextView) findViewById(R.id.tv_title_api_key)).setText(
                Helper.getResString(R.string.design_library_firebase_title_api_key));
        ((TextView) findViewById(R.id.tv_title_storage_url)).setText(
                Helper.getResString(R.string.design_library_firebase_title_storage_bucket_url));
        tvProjectId = findViewById(R.id.tv_project_id);
        tvAppId = findViewById(R.id.tv_app_id);
        tvApiKey = findViewById(R.id.tv_api_key);
        tvStorageUrl = findViewById(R.id.tv_storage_url);
        Button btnImport = findViewById(R.id.btn_import);
        btnImport.setOnClickListener(this);
        Button btnConsole = findViewById(R.id.btn_console);
        btnConsole.setText(Helper.getResString(R.string.design_library_firebase_button_goto_firebase_console));
        btnConsole.setOnClickListener(this);
        configure();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Help").setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_mtrl_help)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(Menu.NONE, Menu.NONE, Menu.NONE, "Config").setIcon(AppCompatResources.getDrawable(this, R.drawable.ic_mtrl_settings)).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        String title = menuItem.getTitle().toString();
        switch (title) {
            case "Help":
                openDoc();
                break;

            case "Config":
                toFirebaseActivity();
                break;

            default:
                return false;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void toFirebaseActivity() {
        Intent intent = new Intent(getApplicationContext(), FirebaseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", sc_id);
        intent.putExtra("firebase", firebaseLibraryBean);
        openSettings.launch(intent);
    }

    private void configure() {
        libSwitch.setChecked("Y".equals(firebaseLibraryBean.useYn));

        if (firebaseLibraryBean.data != null) {
            tvProjectId.setText(firebaseLibraryBean.data); //Realtime Database Url
        }

        if (firebaseLibraryBean.reserved1 != null) {
            tvAppId.setText(firebaseLibraryBean.reserved1); //APP ID
        }

        if (firebaseLibraryBean.reserved2 != null) {
            tvApiKey.setText(firebaseLibraryBean.reserved2); //API Key
        }

        if (firebaseLibraryBean.reserved3 != null) {
            tvStorageUrl.setText(firebaseLibraryBean.reserved3); //Storage Bucket
        }
    }

    public void configureImportFirebaseConfigFromJson(String type, String value) {
        switch (type) {
            case realtime_db:
                firebaseLibraryBean.data = value;
                break;
            case app_id:
                firebaseLibraryBean.reserved1 = value;
                break;
            case api_key:
                firebaseLibraryBean.reserved2 = value;
                break;
            case storage_bucket:
                firebaseLibraryBean.reserved3 = value;
                break;
        }
    }

    private void showImportJsonDialog() {
        DialogProperties properties = new DialogProperties();

        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = Environment.getExternalStorageDirectory();
        properties.error_dir = Environment.getExternalStorageDirectory();
        properties.offset = Environment.getExternalStorageDirectory();
        properties.extensions = new String[]{"json"};

        FilePickerDialog pickerDialog = new FilePickerDialog(this, properties, R.style.RoundedCornersDialog);

        pickerDialog.setTitle("Select your google-services.json");
        pickerDialog.setDialogSelectionListener(selections -> {
            // Since the picker's in single mode, only one element can exist.
            String fileContent = FileUtil.readFile(selections[0]);
            parseDataFromGoogleServicesJson(fileContent);
        });

        pickerDialog.show();
    }

    private void parseDataFromGoogleServicesJson(String _data) {
        final String storageBucketRegex = "\"storage_bucket\": ?\"(.*)\"";
        final String appIdRegex = "\"mobilesdk_app_id\": ?\"(.*)\"";
        final String apiKeyRegex = "\"current_key\": ?\"(.*)\"";
        final String rtdbRegex = "\"firebase_url\": ?\"(.*)\"";

        Matcher storageBucketMatcher = Pattern.compile(storageBucketRegex, Pattern.MULTILINE).matcher(_data);
        Matcher appIdMatcher = Pattern.compile(appIdRegex, Pattern.MULTILINE).matcher(_data);
        Matcher apiKeyMatcher = Pattern.compile(apiKeyRegex, Pattern.MULTILINE).matcher(_data);
        Matcher rtdbMatcher = Pattern.compile(rtdbRegex, Pattern.MULTILINE).matcher(_data);

        StringBuilder notFoundLog = new StringBuilder();
        boolean hasNullConfig = false;
        notFoundLog.append("The google-services.json file you selected does not contain the following configurations:\n");

        if (storageBucketMatcher.find()) {
            configureImportFirebaseConfigFromJson(storage_bucket, storageBucketMatcher.group(1));
        } else {
            notFoundLog.append("StorageBucket, ");
            configureImportFirebaseConfigFromJson(storage_bucket, "");
            hasNullConfig = true;
        }
        if (appIdMatcher.find()) {
            configureImportFirebaseConfigFromJson(app_id, appIdMatcher.group(1));
        } else {
            notFoundLog.append("App ID, ");
            configureImportFirebaseConfigFromJson(app_id, "");
            hasNullConfig = true;
        }
        if (apiKeyMatcher.find()) {
            configureImportFirebaseConfigFromJson(api_key, apiKeyMatcher.group(1));
        } else {
            notFoundLog.append("API Key, ");
            configureImportFirebaseConfigFromJson(api_key, "");
            hasNullConfig = true;
        }
        if (rtdbMatcher.find()) {
            configureImportFirebaseConfigFromJson(realtime_db, rtdbMatcher.group(1).replace("https://", ""));
        } else {
            notFoundLog.append("Realtime Database, ");
            configureImportFirebaseConfigFromJson(realtime_db, "");
            hasNullConfig = true;

            //Disable Firebase in case it was enabled
            libSwitch.setChecked(false);
            firebaseLibraryBean.useYn = "N";

        }

        if (hasNullConfig) SketchwareUtil.toastError(notFoundLog.toString());
        configure();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }
}
