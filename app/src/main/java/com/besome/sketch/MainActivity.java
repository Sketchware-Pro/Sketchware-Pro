package com.besome.sketch;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.besome.sketch.lib.base.BasePermissionAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sketchware.remod.R;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.oB;
import a.a.a.sB;
import a.a.a.wq;
import a.a.a.xB;
import dev.chrisbanes.insetter.Insetter;
import dev.chrisbanes.insetter.Side;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.project.backup.BackupFactory;
import mod.hey.studios.project.backup.BackupRestoreManager;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.ilyasse.activities.about.AboutModActivity;
import mod.jbk.util.LogUtil;
import mod.tyron.backup.CallBackTask;
import mod.tyron.backup.SingleCopyAsyncTask;

public class MainActivity extends BasePermissionAppCompatActivity {
    private final OnBackPressedCallback closeDrawer = new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            setEnabled(false);
            drawerLayout.closeDrawers();
        }
    };

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private DB u;
    private CoordinatorLayout coordinator;
    private Snackbar storageAccessDenied;
    private ProjectsFragment projectsFragment = null;

    @Override
    // onRequestPermissionsResult but for Storage access only, and only when granted
    public void g(int i) {
        if (i == 9501) {
            allFilesAccessCheck();

            if (projectsFragment != null) {
                projectsFragment.refreshProjectsList();
            }
        }
    }

    @Override
    public void h(int i) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
        startActivityForResult(intent, i);
    }

    @Override
    public void l() {
    }

    @Override
    public void m() {
    }

    public void n() {
        if (projectsFragment != null) {
            projectsFragment.refreshProjectsList();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 105:
                    sB.a(this, data.getBooleanExtra("onlyConfig", true));
                    break;

                case 111:
                    invalidateOptionsMenu();
                    break;

                case 113:
                    if (data != null && data.getBooleanExtra("not_show_popup_anymore", false)) {
                        u.a("U1I2", (Object) false);
                    }
                    break;

                case 212:
                    if (!(data.getStringExtra("save_as_new_id") == null ? "" : data.getStringExtra("save_as_new_id")).isEmpty() && isStoragePermissionGranted()) {
                        projectsFragment.refreshProjectsList();
                    }
                    break;
            }
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Main);
        //EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);

        tryLoadingCustomizedAppStrings();
        setContentView(R.layout.main);
        Insetter.builder()
                .padding(WindowInsetsCompat.Type.navigationBars(), Side.create(true, false, true, false))
                .applyToView(findViewById(R.id.layout_coordinator));
        setSupportActionBar(findViewById(R.id.toolbar));

        u = new DB(getApplicationContext(), "U1");
        int u1I0 = u.a("U1I0", -1);
        long u1I1 = u.e("U1I1");
        if (u1I1 <= 0) {
            u.a("U1I1", System.currentTimeMillis());
        }
        if (System.currentTimeMillis() - u1I1 > /* (a day) */ 1000 * 60 * 60 * 24) {
            u.a("U1I0", Integer.valueOf(u1I0 + 1));
        }

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                closeDrawer.setEnabled(true);
                getOnBackPressedDispatcher().addCallback(closeDrawer);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        projectsFragment = (ProjectsFragment) getSupportFragmentManager().findFragmentById(androidx.fragment.R.id.fragment_container_view_tag);

        coordinator = findViewById(R.id.layout_coordinator);

        boolean hasStorageAccess = isStoragePermissionGranted();
        if (!hasStorageAccess) {
            showNoticeNeedStorageAccess();
        }
        if (hasStorageAccess) {
            allFilesAccessCheck();
        }

        if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
            Uri data = getIntent().getData();
            if (data != null) {
                // TODO: Progress indicator while restoring project, possibly from background + notifications
                new SingleCopyAsyncTask(data, this, new CallBackTask() {
                    @Override
                    public void onCopyPreExecute() {
                    }

                    @Override
                    public void onCopyProgressUpdate(int progress) {
                    }

                    @Override
                    public void onCopyPostExecute(String path, boolean wasSuccessful, String reason) {
                        if (wasSuccessful) {
                            BackupRestoreManager manager = new BackupRestoreManager(MainActivity.this, projectsFragment);

                            if (BackupFactory.zipContainsFile(path, "local_libs")) {
                                new MaterialAlertDialogBuilder(MainActivity.this)
                                        .setTitle("Warning")
                                        .setMessage(BackupRestoreManager.getRestoreIntegratedLocalLibrariesMessage(false, -1, -1, null))
                                        .setPositiveButton("Copy", (dialog, which) -> manager.doRestore(path, true))
                                        .setNegativeButton("Don't copy", (dialog, which) -> manager.doRestore(path, false))
                                        .setNeutralButton(R.string.common_word_cancel, null)
                                        .show();
                            } else {
                                manager.doRestore(path, true);
                            }

                            // Clear intent so it doesn't duplicate
                            getIntent().setData(null);
                        } else {
                            SketchwareUtil.toastError("Failed to copy backup file to temporary location: " + reason, Toast.LENGTH_LONG);
                        }
                    }
                }).execute(data);
            }
        } else if (hasStorageAccess && !ConfigActivity.isSettingEnabled(ConfigActivity.SETTING_SKIP_MAJOR_CHANGES_REMINDER)) {
            aB dialog = new aB(this);
            dialog.b("New changes in v6.4.0");
            dialog.a("Just as a reminder; There have been many changes since v6.3.0 fix1, " +
                    "and it's important to know them all if you want your projects to still work.\n" +
                    "You can view all changes whenever you want at the updated About Sketchware Pro screen.");

            dialog.b("View", v -> {
                dialog.dismiss();
                Intent launcher = new Intent(this, AboutModActivity.class);
                launcher.putExtra("select", "majorChanges");
                startActivity(launcher);
            });
            dialog.a("Close", Helper.getDialogDismissListener(dialog));
            dialog.configureDefaultButton("Never show again", v -> {
                ConfigActivity.setSetting(ConfigActivity.SETTING_SKIP_MAJOR_CHANGES_REMINDER, true);
                dialog.dismiss();
            });
            dialog.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        xB.b().a();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onResume() {
        super.onResume();
        /* Check if the device is running low on storage space */
        long freeMegabytes = GB.c();
        if (freeMegabytes < 100 && freeMegabytes > 0) {
            showNoticeNotEnoughFreeStorageSpace();
        }
        if (isStoragePermissionGranted() && storageAccessDenied != null && storageAccessDenied.isShown()) {
            storageAccessDenied.dismiss();
        }
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "MainActivity");
        bundle.putString(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity");
        mAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);
    }

    private void allFilesAccessCheck() {
        if (Build.VERSION.SDK_INT > 29) {
            File optOutFile = new File(getFilesDir(), ".skip_all_files_access_notice");
            boolean granted = Environment.isExternalStorageManager();

            if (!optOutFile.exists() && !granted) {
                aB dialog = new aB(this);
                dialog.a(R.drawable.ic_expire_48dp);
                dialog.b("Android 11 storage access");
                dialog.a("Starting with Android 11, Sketchware Pro needs a new permission to avoid " +
                        "taking ages to build projects. Don't worry, we can't do more to storage than " +
                        "with current granted permissions.");
                dialog.b(Helper.getResString(R.string.common_word_settings), v -> {
                    FileUtil.requestAllFilesAccessPermission(this);
                    dialog.dismiss();
                });
                dialog.a("Skip", Helper.getDialogDismissListener(dialog));
                dialog.configureDefaultButton("Don't show anymore", v -> {
                    try {
                        if (!optOutFile.createNewFile())
                            throw new IOException("Failed to create file " + optOutFile);
                    } catch (IOException e) {
                        Log.e("MainActivity", "Error while trying to create " +
                                "\"Don't show Android 11 hint\" dialog file: " + e.getMessage(), e);
                    }
                    dialog.dismiss();
                });
                dialog.show();
            }
        }
    }

    private void showNoticeNeedStorageAccess() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.common_message_permission_title_storage));
        dialog.a(R.drawable.color_about_96);
        dialog.a(Helper.getResString(R.string.common_message_permission_need_load_project));
        dialog.b(Helper.getResString(R.string.common_word_ok), v -> {
            dialog.dismiss();
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    9501);
        });
        dialog.show();
    }

    private void showNoticeNotEnoughFreeStorageSpace() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.common_message_insufficient_storage_space_title));
        dialog.a(R.drawable.high_priority_96_red);
        dialog.a(Helper.getResString(R.string.common_message_insufficient_storage_space));
        dialog.b(Helper.getResString(R.string.common_word_ok), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public void s() {
        if (storageAccessDenied == null || !storageAccessDenied.isShown()) {
            storageAccessDenied = Snackbar.make(coordinator, Helper.getResString(R.string.common_message_permission_denied), Snackbar.LENGTH_INDEFINITE);
            storageAccessDenied.setAction(Helper.getResString(R.string.common_word_settings), v -> {
                storageAccessDenied.dismiss();
                ActivityCompat.requestPermissions(this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        9501);
            });
            storageAccessDenied.setActionTextColor(Color.YELLOW);
            storageAccessDenied.show();
        }
    }

    //This is annoying Please remove/togglize it
    private void tryLoadingCustomizedAppStrings() {
        // Refresh extracted provided strings file if necessary
        oB oB = new oB();
        try {
            File extractedStringsProvidedXml = new File(wq.m());
            if (oB.a(getApplicationContext(), "localization/strings.xml") !=
                    (extractedStringsProvidedXml.exists() ? extractedStringsProvidedXml.length() : 0)) {
                oB.a(extractedStringsProvidedXml);
                oB.a(getApplicationContext(), "localization/strings.xml", wq.m());
            }
        } catch (Exception e) {
            String message = "Couldn't extract default strings to storage";
            SketchwareUtil.toastError(message + ": " + e.getMessage());
            LogUtil.e("MainActivity", message, e);
        }

        // Actual loading part
        if (xB.b().b(getApplicationContext())) {
            bB.a(getApplicationContext(),
                    Helper.getResString(R.string.message_strings_xml_loaded),
                    0, 80, 0, 128).show();
        }
    }
}