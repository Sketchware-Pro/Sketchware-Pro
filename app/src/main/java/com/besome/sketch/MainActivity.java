package com.besome.sketch;

import android.Manifest;
import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.besome.sketch.lib.base.BasePermissionAppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sketchware.remod.Resources;

import java.io.File;
import java.io.IOException;

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.GC;
import a.a.a.Xf;
import a.a.a.aB;
import a.a.a.gg;
import a.a.a.l;
import a.a.a.nd;
import a.a.a.sB;
import a.a.a.xB;
import mod.SketchwareUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.project.backup.BackupFactory;
import mod.hey.studios.project.backup.BackupRestoreManager;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.ilyasse.activities.about.AboutModActivity;
import mod.tyron.backup.CallBackTask;
import mod.tyron.backup.SingleCopyAsyncTask;

public class MainActivity extends BasePermissionAppCompatActivity implements ViewPager.e {

    private LinearLayout E;
    private FloatingActionButton F;
    private DrawerLayout m;
    private l n;
    private MainDrawer o;
    private ViewPager p;
    private DB u;
    private CoordinatorLayout w;
    private Snackbar x;
    private GC y = null;

    @Override
    public void a(int i) {
    }

    @Override
    public void a(int i, float f, int i2) {
    }

    @Override
    public void g(int i) {
        if (i == 9501 && p.getCurrentItem() == 0 && y != null) {
            y.g();
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

    public void l(int i) {
        if (p != null) p.a(i, true);
    }

    @Override
    public void m() {
    }

    public void n() {
        if (y != null) {
            y.a(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        invalidateOptionsMenu();
        if (resultCode == -1) {
            switch (requestCode) {
                case 105:
                    l(0);
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
                    if (!(data.getStringExtra("save_as_new_id") == null ? "" : data.getStringExtra("save_as_new_id")).isEmpty() && j()) {
                        y.g();
                    }
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (o.isShown()) {
            m.b();
        } else {
            finish();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        n.a(newConfig);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.main);
        u = new DB(getApplicationContext(), "U1");
        int c1 = u.a("U1I0", -1);
        long u1I1Long = u.e("U1I1");
        if (u1I1Long <= 0) {
            u.a("U1I1", System.currentTimeMillis());
        }
        if (System.currentTimeMillis() - u1I1Long > 1000 * 24 * 60 * 60) {
            u.a("U1I0", Integer.valueOf(c1 + 1));
        }
        Toolbar l = findViewById(Resources.id.toolbar);
        a(l);
        d().d(true);
        d().e(true);
        ImageView a1 = findViewById(Resources.id.img_title_logo);
        a1.setOnClickListener(v -> invalidateOptionsMenu());
        o = findViewById(Resources.id.left_drawer);
        m = findViewById(Resources.id.drawer_layout);
        n = new l(this, m, Resources.string.app_name, Resources.string.app_name);
        m.a((DrawerLayout.c) n);
        d().a("");
        p = findViewById(Resources.id.viewpager);
        p.setOffscreenPageLimit(2);
        p.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        p.a(this);
        E = findViewById(Resources.id.layout_qna_bottom);
        F = findViewById(Resources.id.fab);
        w = findViewById(Resources.id.layout_coordinator);
        l(0);
        if (c1 > 0 && !j()) {
            showNoticeNeedStorageAccess();
        }
        allFilesAccessCheck();

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
                            BackupRestoreManager manager = new BackupRestoreManager(MainActivity.this, y);

                            if (BackupFactory.zipContainsFile(path, "local_libs")) {
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Warning")
                                        .setMessage(BackupRestoreManager.getRestoreIntegratedLocalLibrariesMessage(false, -1, -1, null))
                                        .setPositiveButton("Copy", (dialog, which) -> manager.doRestore(path, true))
                                        .setNegativeButton("Don't copy", (dialog, which) -> manager.doRestore(path, false))
                                        .setNeutralButton(Resources.string.common_word_cancel, null)
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
        }

        if (!ConfigActivity.isSettingEnabled(ConfigActivity.SETTING_SKIP_MAJOR_CHANGES_REMINDER)) {
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (n.a(item)) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        n.b();
    }

    @Override
    public void onResume() {
        super.onResume();
        /* Check if the device is running low on storage space */
        long freeMegabytes = GB.c();
        if (freeMegabytes < 100 && freeMegabytes > 0) {
            showNoticeNotEnoughFreeStorageSpace();
        }
        if (j() && x != null && x.j()) {
            x.c();
        }
    }

    private void allFilesAccessCheck() {
        if (Build.VERSION.SDK_INT > 29) {
            File optOutFile = new File(getFilesDir(), ".skip_all_files_access_notice");
            boolean granted = Environment.isExternalStorageManager();

            if (!optOutFile.exists() && !granted) {
                aB dialog = new aB(this);
                dialog.a(Resources.drawable.ic_expire_48dp);
                dialog.b("Android 11 storage access");
                dialog.a("Starting with Android 11, Sketchware Pro needs a new permission to avoid " +
                        "taking ages to build projects. Don't worry, we can't do more to storage than " +
                        "with current granted permissions.");
                dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_settings), v -> {
                    FileUtil.requestAllFilesAccessPermission(this);
                    dialog.dismiss();
                });
                dialog.a("Skip", Helper.getDialogDismissListener(dialog));
                dialog.configureDefaultButton("Don't show anymore", v -> {
                    try {
                        optOutFile.createNewFile();
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
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_message_permission_title_storage));
        dialog.a(Resources.drawable.color_about_96);
        dialog.a(xB.b().a(getApplicationContext(), Resources.string.common_message_permission_need_load_project));
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_ok), v -> {
            dialog.dismiss();
            s();
        });
        dialog.show();
    }

    private void showNoticeNotEnoughFreeStorageSpace() {
        aB dialog = new aB(this);
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_message_insufficient_storage_space_title));
        dialog.a(Resources.drawable.high_priority_96_red);
        dialog.a(xB.b().a(getApplicationContext(), Resources.string.common_message_insufficient_storage_space));
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_ok),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public void s() {
        if (x == null || !x.j()) {
            x = Snackbar.a(w, xB.b().a(getApplicationContext(), Resources.string.common_message_permission_denied), -2);
            x.a(xB.b().a(getApplicationContext(), Resources.string.common_word_settings), v -> {
                x.c();
                nd.a(MainActivity.this, new String[]{
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE},
                        9501);
            });
            x.f(Color.YELLOW);
            x.n();
        }
    }

    @Override
    public void b(int i) {
        if (i == 0) {
            if (j() && y != null && y.f() == 0) {
                y.g();
            }
            E.setVisibility(View.GONE);
            y.h();
        } else if (i == 1) {
            E.setVisibility(View.GONE);
            F.c();
        }
    }

    public void b(String str) {
        if (p != null) {
            p.setCurrentItem(0);
        }
        if (y != null) {
            y.g();
            y.c(str);
        }
    }

    private class PagerAdapter extends gg {

        public PagerAdapter(Xf xf) {
            super(xf);
        }

        @Override
        public int a() {
            return 1;
        }

        @Override
        public Object a(ViewGroup viewGroup, int i) {
            Fragment fragment = (Fragment) super.a(viewGroup, i);
            y = (GC) fragment;
            return fragment;
        }

        @Override
        public Fragment c(int i) {
            return new GC();
        }

        @Override
        public CharSequence a(int i) {
            return xB.b().a(MainActivity.this,
                    Resources.string.main_tab_title_myproject);
        }
    }
}
