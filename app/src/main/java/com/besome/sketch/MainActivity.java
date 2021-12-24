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

import com.besome.sketch.acc.MyPageSettingsActivity;
import com.besome.sketch.acc.ProfileActivity;
import com.besome.sketch.bill.InAppActivity;
import com.besome.sketch.bill.SubscribeActivity;
import com.besome.sketch.lib.base.BasePermissionAppCompatActivity;
import com.besome.sketch.shared.project.SharedProjectDetailActivity;
import com.google.ads.consent.ConsentForm;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
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
import a.a.a.zI;
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

    public final int k = 2;
    public ImageView A;
    public FirebaseAnalytics B;
    public int C;
    public boolean D;
    public LinearLayout E;
    public FloatingActionButton F;
    public ConsentForm G = null;
    public Toolbar l;
    public DrawerLayout m;
    public l n;
    public MainDrawer o;
    public ViewPager p;
    public String[] r;
    public int[] s = {Resources.drawable.android_os_96, Resources.drawable.ic_class_48, Resources.drawable.globe_96};
    public DB t;
    public DB u;
    public DB v;
    public CoordinatorLayout w;
    public Snackbar x;
    public GC y = null;
    public zI z = null;

    @Override // androidx.viewpager.widget.ViewPager.e
    public void a(int i) {
    }

    @Override // androidx.viewpager.widget.ViewPager.e
    public void a(int i, float f, int i2) {
    }

    @Override // com.besome.sketch.lib.base.BasePermissionAppCompatActivity
    public void g(int i) {
        if (i == 9501 && p.getCurrentItem() == 0 && y != null) {
            y.g();
        }
    }

    @Override // com.besome.sketch.lib.base.BasePermissionAppCompatActivity
    public void h(int i) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
        startActivityForResult(intent, i);
    }

    @Override // com.besome.sketch.lib.base.BasePermissionAppCompatActivity
    public void l() {
    }

    public void l(int i) {
        if (p != null) {
            p.a(i, true);
        }
    }

    @Override // com.besome.sketch.lib.base.BasePermissionAppCompatActivity
    public void m() {
    }

    public void n() {
        if (y != null) {
            y.a(false);
        }
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        invalidateOptionsMenu();
        if (requestCode == 100) {
            invalidateOptionsMenu();
            if (resultCode == -1) {
                if (i.g().isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivityForResult(intent, 108);
                } else {
                    toMyPageSettingsActivity();
                }
            }
            if (o != null) {
                o.i();
            }
        } else if (requestCode == 105) {
            if (resultCode == -1) {
                l(0);
                sB.a(this, data.getBooleanExtra("onlyConfig", true));
            }
        } else if (requestCode == 111) {
            if (resultCode == -1) {
                invalidateOptionsMenu();
            }
        } else if (requestCode == 113) {
            if (resultCode == -1 && data != null && data.getBooleanExtra("not_show_popup_anymore", false)) {
                u.a("U1I2", (Object) false);
            }
        } else if (requestCode == 212) {
            if (resultCode == -1) {
                if (!(data.getStringExtra("save_as_new_id") == null ? "" : data.getStringExtra("save_as_new_id")).isEmpty() && j()) {
                    y.g();
                }
            }
        } else if (requestCode == 505 && o != null) {
            o.i();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onBackPressed() {
        if (o.isShown()) {
            m.b();
        } else {
            finish();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        n.a(newConfig);
    }

    @Override
    // androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.main);
        t = new DB(getApplicationContext(), "P1");
        u = new DB(getApplicationContext(), "U1");
        v = new DB(getApplicationContext(), "P25");
        C = u.a("U1I0", -1);
        long u1I1Long = u.e("U1I1");
        if (u1I1Long <= 0) {
            u.a("U1I1", System.currentTimeMillis());
        }
        if (System.currentTimeMillis() - u1I1Long > 1000 * 24 * 60 * 60) {
            u.a("U1I0", Integer.valueOf(C + 1));
        }
        D = u.a("U1I2", true);
        r = new String[]{xB.b().a(this,
                Resources.string.main_tab_title_myproject)};
        l = findViewById(Resources.id.toolbar);
        a(l);
        d().d(true);
        d().e(true);
        A = findViewById(Resources.id.img_title_logo);
        A.setOnClickListener(v -> invalidateOptionsMenu());
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
        B = FirebaseAnalytics.getInstance(this);
        try {
            String stringExtra = getIntent().getStringExtra("auto_run_activity");
            if (stringExtra != null) {
                if (!"InAppActivity".equals(stringExtra)) {
                    if (!"SubscribeActivity".equals(stringExtra)) {
                        if ("SharedProjectDetailActivity".equals(stringExtra)) {
                            Intent intent = new Intent(getApplicationContext(), SharedProjectDetailActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            intent.putExtra("shared_id", Integer.parseInt(getIntent().getStringExtra("shared_id")));
                            startActivity(intent);
                        }
                    }
                }
                Intent intent = new Intent(getApplicationContext(), SubscribeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, 505);
            }
        } catch (Exception ignored) {
        }
        if (C > 0 && !j()) {
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
    // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onDestroy() {
        super.onDestroy();
        xB.b().a();
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            String autoRunActivity = intent.getStringExtra("auto_run_activity");
            if (autoRunActivity != null) {
                if (!"InAppActivity".equals(autoRunActivity)) {
                    if (!"SubscribeActivity".equals(autoRunActivity)) {
                        if ("SharedProjectDetailActivity".equals(autoRunActivity)) {
                            Intent launchIntent = new Intent(getApplicationContext(), SharedProjectDetailActivity.class);
                            launchIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            launchIntent.putExtra("shared_id", Integer.parseInt(intent.getStringExtra("shared_id")));
                            startActivity(launchIntent);
                            return;
                        }
                        return;
                    }
                }
                Intent launchIntent = new Intent(getApplicationContext(), InAppActivity.class);
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(launchIntent, 505);
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (n.a(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        n.b();
    }

    @Override
    // androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
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

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity
    public void onStart() {
        super.onStart();
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

    private void toMyPageSettingsActivity() {
        Intent intent = new Intent(getApplicationContext(), MyPageSettingsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivityForResult(intent, 111);
    }

    @Override // androidx.viewpager.widget.ViewPager.e
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

        @Override // a.a.a.kk
        public int a() {
            return 1;
        }

        @Override // a.a.a.gg, a.a.a.kk
        public Object a(ViewGroup viewGroup, int i) {
            Fragment fragment = (Fragment) super.a(viewGroup, i);
            y = (GC) fragment;
            return fragment;
        }

        @Override // a.a.a.gg
        public Fragment c(int i) {
            return new GC();
        }

        @Override // a.a.a.kk
        public CharSequence a(int i) {
            return r[i];
        }
    }
}
