package com.besome.sketch.design;

import static pro.sketchware.utility.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.common.SrcViewerActivity;
import com.besome.sketch.editor.manage.ManageCollectionActivity;
import com.besome.sketch.editor.manage.font.ManageFontActivity;
import com.besome.sketch.editor.manage.image.ManageImageActivity;
import com.besome.sketch.editor.manage.library.ManageLibraryActivity;
import com.besome.sketch.editor.manage.sound.ManageSoundActivity;
import com.besome.sketch.editor.manage.view.ManageViewActivity;
import com.besome.sketch.editor.view.ProjectFileSelector;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.CustomViewPager;
import com.besome.sketch.tools.CompileLogActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.topjohnwu.superuser.Shell;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import a.a.a.DB;
import a.a.a.GB;
import a.a.a.ProjectBuilder;
import a.a.a.ViewEditorFragment;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.bC;
import a.a.a.br;
import a.a.a.cC;
import a.a.a.jC;
import a.a.a.kC;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.rs;
import a.a.a.wq;
import a.a.a.yB;
import a.a.a.yq;
import a.a.a.zy;
import dev.chrisbanes.insetter.Insetter;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.Magnifier;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import io.github.rosemoe.sora.widget.schemes.SchemeDarcula;
import mod.agus.jcoderz.editor.manage.permission.ManagePermissionActivity;
import mod.agus.jcoderz.editor.manage.resource.ManageResourceActivity;
import mod.hey.studios.activity.managers.assets.ManageAssetsActivity;
import mod.hey.studios.activity.managers.java.ManageJavaActivity;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.build.BuildSettingsDialog;
import mod.hey.studios.compiler.kotlin.KotlinCompilerBridge;
import mod.hey.studios.project.custom_blocks.CustomBlocksDialog;
import mod.hey.studios.project.proguard.ManageProguardActivity;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.project.stringfog.ManageStringFogFragment;
import mod.hey.studios.project.stringfog.StringfogHandler;
import mod.hey.studios.util.Helper;
import mod.hey.studios.util.SystemLogPrinter;
import mod.hilal.saif.activities.android_manifest.AndroidManifestInjection;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.jbk.build.BuildProgressReceiver;
import mod.jbk.build.BuiltInLibraries;
import mod.jbk.code.CodeEditorColorSchemes;
import mod.jbk.code.CodeEditorLanguages;
import mod.jbk.diagnostic.CompileErrorSaver;
import mod.jbk.diagnostic.MissingFileException;
import mod.jbk.util.LogUtil;
import mod.khaled.logcat.LogReaderActivity;
import mod.remaker.util.ThemeUtils;
import pro.sketchware.R;
import pro.sketchware.activities.appcompat.ManageAppCompatActivity;
import pro.sketchware.databinding.ProgressMsgBoxBinding;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.apk.ApkSignatures;

public class DesignActivity extends BaseAppCompatActivity implements View.OnClickListener {
    public static String sc_id;
    private ImageView xmlLayoutOrientation;
    private boolean B;
    private int currentTabNumber;
    private CustomViewPager viewPager;
    private CoordinatorLayout coordinatorLayout;
    private DrawerLayout drawer;
    private yq q;
    private DB r;
    private DB t;
    private Button buildSettings;
    private Button runProject;
    private ProjectFileSelector projectFileSelector;
    private final ActivityResultLauncher<Intent> openImageManager = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (projectFileSelector != null) {
                projectFileSelector.syncState();
            }
        }
    });
    public final ActivityResultLauncher<Intent> changeOpenFile = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            assert result.getData() != null;
            projectFileSelector.setXmlFileName(result.getData().getParcelableExtra("project_file"));
        }
    });
    private ViewEditorFragment viewTabAdapter;
    private final ActivityResultLauncher<Intent> openLibraryManager = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (projectFileSelector != null) {
                projectFileSelector.syncState();
            }
            if (viewTabAdapter != null) {
                viewTabAdapter.n();
            }
        }
    });
    private final ActivityResultLauncher<Intent> openViewManager = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (viewTabAdapter != null) {
                projectFileSelector.syncState();
            }
        }
    });
    private final ActivityResultLauncher<Intent> openCollectionManager = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            viewTabAdapter.j();
        }
    });
    private final ActivityResultLauncher<Intent> openResourcesManager = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            viewTabAdapter.i();
        }
    });
    private rs eventTabAdapter;
    private br componentTabAdapter;
    private BuildTask currentBuildTask;

    /**
     * Saves the app's version information to the currently opened Sketchware project file.
     */
    private void saveVersionCodeInformationToProject() {
        HashMap<String, Object> projectMetadata = lC.b(sc_id);
        if (projectMetadata != null) {
            projectMetadata.put("sketchware_ver", GB.d(getApplicationContext()));
            lC.b(sc_id, projectMetadata);
        }
    }

    private void loadProject(boolean haveSavedState) {
        jC.a(sc_id, haveSavedState);
        jC.b(sc_id, haveSavedState);
        kC var2 = jC.d(sc_id, haveSavedState);
        jC.c(sc_id, haveSavedState);
        cC.c(sc_id);
        bC.d(sc_id);
        if (!haveSavedState) {
            var2.f();
            var2.g();
            var2.e();
        }
    }

    public void setTouchEventEnabled(boolean touchEventEnabled) {
        if (touchEventEnabled) {
            viewPager.enableTouchEvent();
        } else {
            viewPager.disableTouchEvent();
        }
    }

    /**
     * Shows a Snackbar indicating that a problem occurred while compiling. The user can click on "SHOW" to get to {@link CompileLogActivity}.
     *
     * @param error The error, to be later displayed as text in {@link CompileLogActivity}
     */
    private void indicateCompileErrorOccurred(String error) {
        new CompileErrorSaver(sc_id).writeLogsToFile(error);
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Show compile log", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(Helper.getResString(R.string.common_word_show), v -> {
            if (!mB.a()) {
                snackbar.dismiss();
                Intent intent = new Intent(getApplicationContext(), CompileLogActivity.class);
                intent.putExtra("error", error);
                intent.putExtra("sc_id", sc_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        snackbar.show();
    }

    @Override
    public void finish() {
        jC.a();
        cC.a();
        bC.a();
        setResult(RESULT_CANCELED, getIntent());
        super.finish();
    }

    private void checkForUnsavedProjectData() {
        if (jC.c(sc_id).g() || jC.b(sc_id).g() || jC.d(sc_id).q() || jC.a(sc_id).d() || jC.a(sc_id).c()) {
            askIfToRestoreOldUnsavedProjectData();
        }
    }

    /**
     * Opens the debug APK to install.
     */
    private void installBuiltApk() {
        if (!ConfigActivity.isSettingEnabled(ConfigActivity.SETTING_ROOT_AUTO_INSTALL_PROJECTS)) {
            requestPackageInstallerInstall();
        } else {
            File apkUri = new File(q.finalToInstallApkPath);
            long length = apkUri.length();
            Shell.getShell(shell -> {
                if (shell.isRoot()) {
                    List<String> stdout = new LinkedList<>();
                    List<String> stderr = new LinkedList<>();

                    Shell.cmd("cat " + apkUri + " | pm install -S " + length).to(stdout, stderr).submit(result -> {
                        if (result.isSuccess()) {
                            SketchwareUtil.toast("Package installed successfully!");
                            if (ConfigActivity.isSettingEnabled(ConfigActivity.SETTING_ROOT_AUTO_OPEN_AFTER_INSTALLING)) {
                                Intent launcher = getPackageManager().getLaunchIntentForPackage(q.packageName);
                                if (launcher != null) {
                                    startActivity(launcher);
                                } else {
                                    SketchwareUtil.toastError("Couldn't launch project, either not installed or not with launcher activity.");
                                }
                            }
                        } else {
                            String sharedErrorMessage = "Failed to install package, result code: " + result.getCode() + ". ";
                            SketchwareUtil.toastError(sharedErrorMessage + "Logs are available in /Internal storage/.sketchware/debug.txt", Toast.LENGTH_LONG);
                            LogUtil.e("DesignActivity", sharedErrorMessage + "stdout: " + stdout + ", stderr: " + stderr);
                        }
                    });
                } else {
                    SketchwareUtil.toastError("No root access granted. Continuing using default package install prompt.");
                    requestPackageInstallerInstall();
                }
            });
        }
    }

    private void requestPackageInstallerInstall() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= 24) {
            Uri apkUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", new File(q.finalToInstallApkPath));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(q.finalToInstallApkPath)), "application/vnd.android.package-archive");
        }

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else if (viewTabAdapter.g()) {
            viewTabAdapter.a(false);
        } else {
            if (currentTabNumber > 0) {
                currentTabNumber--;
                viewPager.setCurrentItem(currentTabNumber);
            } else if (t.c("P12I2")) {
                k();
                saveChangesAndCloseProject();
            } else {
                showSaveBeforeQuittingDialog();
            }
        }
    }

    private void saveChangesAndCloseProject() {
        k();
        SaveChangesProjectCloser saveChangesProjectCloser = new SaveChangesProjectCloser(this);
        saveChangesProjectCloser.execute();
    }

    private void saveProject() {
        k();
        ProjectSaver projectSaver = new ProjectSaver(this);
        projectSaver.execute();
    }

    @Override
    public void onClick(View v) {
        if (!mB.a()) {
            if (v.getId() == R.id.btn_execute) {
                BuildTask buildTask = new BuildTask(this);
                currentBuildTask = buildTask;
                buildTask.execute();
            } else if (v.getId() == R.id.btn_compiler_opt) {
                PopupMenu popupMenu = new PopupMenu(this, buildSettings);
                Menu menu = popupMenu.getMenu();

                menu.add(Menu.NONE, 1, Menu.NONE, "Build Settings");
                menu.add(Menu.NONE, 2, Menu.NONE, "Clean temporary files");
                menu.add(Menu.NONE, 3, Menu.NONE, "Show last compile error");
                menu.add(Menu.NONE, 5, Menu.NONE, "Show source code");
                if (FileUtil.isExistFile(q.finalToInstallApkPath)) {
                    menu.add(Menu.NONE, 4, Menu.NONE, "Install last built APK");
                    menu.add(Menu.NONE, 6, Menu.NONE, "Show Apk signatures");
                }

                popupMenu.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case 1 -> new BuildSettingsDialog(this, sc_id).show();
                        case 2 -> new Thread(() -> {
                            FileUtil.deleteFile(q.projectMyscPath);
                            runOnUiThread(() ->
                                    SketchwareUtil.toast("Done cleaning temporary files!"));
                        }).start();
                        case 3 -> new CompileErrorSaver(sc_id).showLastErrors(this);
                        case 4 -> {
                            if (FileUtil.isExistFile(q.finalToInstallApkPath)) {
                                installBuiltApk();
                            } else {
                                SketchwareUtil.toast("APK doesn't exist anymore");
                            }
                        }
                        case 5 -> showCurrentActivitySrcCode();
                        case 6 -> {
                            ApkSignatures apkSignatures = new ApkSignatures(this, q.finalToInstallApkPath);
                            apkSignatures.showSignaturesDialog();
                        }
                        default -> {
                            return false;
                        }
                    }

                    return true;
                });

                popupMenu.show();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.design);
        if (!isStoragePermissionGranted()) {
            finish();
        }

        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
        }

        r = new DB(getApplicationContext(), "P1");
        t = new DB(getApplicationContext(), "P12");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setSubtitle(sc_id);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        drawer = findViewById(R.id.drawer_layout);
        Insetter.builder()
                .margin(WindowInsetsCompat.Type.navigationBars())
                .applyToView(findViewById(R.id.container));
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        coordinatorLayout = findViewById(R.id.layout_coordinator);
        buildSettings = findViewById(R.id.btn_compiler_opt);
        buildSettings.setOnClickListener(this);
        runProject = findViewById(R.id.btn_execute);
        runProject.setText(Helper.getResString(R.string.common_word_run));
        runProject.setOnClickListener(this);
        xmlLayoutOrientation = findViewById(R.id.img_orientation);
        projectFileSelector = findViewById(R.id.file_selector);
        projectFileSelector.setScId(sc_id);
        projectFileSelector.setOnSelectedFileChangeListener((i, projectFileBean) -> {
            if (i == 0) {
                if (viewTabAdapter != null && projectFileBean != null) {
                    int orientation = projectFileBean.orientation;
                    if (orientation == ProjectFileBean.ORIENTATION_PORTRAIT) {
                        xmlLayoutOrientation.setImageResource(R.drawable.ic_screen_portrait_grey600_24dp);
                    } else if (orientation == ProjectFileBean.ORIENTATION_LANDSCAPE) {
                        xmlLayoutOrientation.setImageResource(R.drawable.ic_screen_landscape_grey600_24dp);
                    } else {
                        xmlLayoutOrientation.setImageResource(R.drawable.ic_screen_rotation_grey600_24dp);
                    }
                    viewTabAdapter.a(projectFileBean);
                }
            } else if (i == 1) {
                if (eventTabAdapter != null) {
                    if (projectFileBean != null) {
                        eventTabAdapter.setCurrentActivity(projectFileBean);
                        eventTabAdapter.refreshEvents();
                    } else {
                        return;
                    }
                }
                if (componentTabAdapter != null && projectFileBean != null) {
                    componentTabAdapter.setProjectFile(projectFileBean);
                    componentTabAdapter.refreshData();
                }
            }
        });
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (currentTabNumber == 1) {
                    if (eventTabAdapter != null) {
                        eventTabAdapter.c();
                    }
                } else if (currentTabNumber == 2 && componentTabAdapter != null) {
                    componentTabAdapter.unselectAll();
                }
                if (position == 0) {
                    if (viewTabAdapter != null) {
                        viewTabAdapter.c(true);
                        xmlLayoutOrientation.setVisibility(View.VISIBLE);
                        projectFileSelector.setFileType(0);
                        projectFileSelector.syncState();
                    }
                } else if (position == 1) {
                    if (viewTabAdapter != null) {
                        xmlLayoutOrientation.setVisibility(View.GONE);
                        viewTabAdapter.c(false);
                        projectFileSelector.setFileType(1);
                        projectFileSelector.syncState();
                        if (eventTabAdapter != null) {
                            eventTabAdapter.refreshEvents();
                        }
                    }
                } else {
                    if (viewTabAdapter != null) {
                        viewTabAdapter.c(false);
                        xmlLayoutOrientation.setVisibility(View.GONE);
                        projectFileSelector.setFileType(1);
                        projectFileSelector.syncState();
                        if (componentTabAdapter != null) {
                            componentTabAdapter.refreshData();
                        }
                    }
                }
                currentTabNumber = position;
            }
        });
        viewPager.getAdapter().notifyDataSetChanged();
        ((TabLayout) findViewById(R.id.tab_layout)).setupWithViewPager(viewPager);

        IntentFilter filter = new IntentFilter(BuildTask.ACTION_CANCEL_BUILD);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(buildCancelReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(buildCancelReceiver, filter);
        }

    }

    private boolean isBuildingInTheBackground() {
        return new BuildSettings(sc_id).getValue(BuildSettings.SETTING_ENABLE_BACKGROUND_BUILDING, BuildSettings.SETTING_GENERIC_VALUE_FALSE).equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(buildCancelReceiver);
    }

    private final BroadcastReceiver buildCancelReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (BuildTask.ACTION_CANCEL_BUILD.equals(intent.getAction())) {
                if (currentBuildTask != null) {
                    currentBuildTask.cancelBuild();
                }
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.design_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.design_actionbar_titleopen_drawer) {
            if (!drawer.isDrawerOpen(GravityCompat.END)) {
                drawer.openDrawer(GravityCompat.END);
            }
        } else if (itemId == R.id.design_option_menu_title_save_project) {
            saveProject();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        k();

        HashMap<String, Object> projectInfo = lC.b(sc_id);
        getSupportActionBar().setTitle(yB.c(projectInfo, "my_ws_name"));
        q = new yq(getApplicationContext(), wq.d(sc_id), projectInfo);

        try {
            ProjectLoader projectLoader = new ProjectLoader(this, savedInstanceState);
            projectLoader.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SystemLogPrinter.stop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isStoragePermissionGranted()) {
            finish();
        }

        long freeMegabytes = GB.c();
        if (freeMegabytes < 100L && freeMegabytes > 0L) {
            warnAboutInsufficientStorageSpace();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        projectFileSelector.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
        if (!isStoragePermissionGranted()) {
            finish();
        }

        if (!B) {
            UnsavedChangesSaver unsavedChangesSaver = new UnsavedChangesSaver(this);
            unsavedChangesSaver.execute();
        }
    }

    /**
     * Show a dialog asking about saving the project before quitting.
     */
    private void showSaveBeforeQuittingDialog() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.design_quit_title_exit_projet));
        dialog.a(R.drawable.ic_mtrl_exit);
        dialog.a(Helper.getResString(R.string.design_quit_message_confirm_save));
        dialog.b(Helper.getResString(R.string.design_quit_button_save_and_exit), v -> {
            if (!mB.a()) {
                dialog.dismiss();
                try {
                    saveChangesAndCloseProject();
                } catch (Exception e) {
                    e.printStackTrace();
                    h();
                }
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_exit), v -> {
            if (!mB.a()) {
                dialog.dismiss();
                try {
                    k();
                    DiscardChangesProjectCloser discardChangesProjectCloser = new DiscardChangesProjectCloser(this);
                    discardChangesProjectCloser.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                    h();
                }
            }
        });
        dialog.configureDefaultButton(Helper.getResString(R.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    /**
     * Show a dialog warning the user about low free space.
     */
    private void warnAboutInsufficientStorageSpace() {
        aB dialog = new aB(this);
        dialog.b(Helper.getResString(R.string.common_word_warning));
        dialog.a(R.drawable.break_warning_96_red);
        dialog.a(Helper.getResString(R.string.common_message_insufficient_storage_space));
        dialog.b(Helper.getResString(R.string.common_word_ok),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    private void askIfToRestoreOldUnsavedProjectData() {
        B = true;
        aB dialog = new aB(this);
        dialog.a(R.drawable.ic_mtrl_history);
        dialog.b(Helper.getResString(R.string.design_restore_data_title));
        dialog.a(Helper.getResString(R.string.design_restore_data_message_confirm));
        dialog.b(Helper.getResString(R.string.common_word_restore), v -> {
            if (!mB.a()) {
                boolean g = jC.c(sc_id).g();
                boolean g2 = jC.b(sc_id).g();
                boolean q = jC.d(sc_id).q();
                boolean d = jC.a(sc_id).d();
                boolean c = jC.a(sc_id).c();
                if (g) {
                    jC.c(sc_id).h();
                }
                if (g2) {
                    jC.b(sc_id).h();
                }
                if (q) {
                    jC.d(sc_id).r();
                }
                if (d) {
                    jC.a(sc_id).h();
                }
                if (c) {
                    jC.a(sc_id).f();
                }
                if (g) {
                    jC.b(sc_id).a(jC.c(sc_id));
                    jC.a(sc_id).a(jC.c(sc_id).d());
                }
                if (g2 || g) {
                    jC.a(sc_id).a(jC.b(sc_id));
                }
                if (q) {
                    jC.a(sc_id).b(jC.d(sc_id));
                    jC.a(sc_id).c(jC.d(sc_id));
                    jC.a(sc_id).a(jC.d(sc_id));
                }
                projectFileSelector.syncState();
                B = false;
                dialog.dismiss();
            }
        });
        dialog.a(Helper.getResString(R.string.common_word_no), v -> {
            B = false;
            dialog.dismiss();
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void showCurrentActivitySrcCode() {
        ProgressMsgBoxBinding loadingDialogBinding = ProgressMsgBoxBinding.inflate(getLayoutInflater());
        loadingDialogBinding.tvProgress.setText("Generating source code...");
        var loadingDialog = new MaterialAlertDialogBuilder(this)
                .setTitle("Please wait")
                .setCancelable(false)
                .setView(loadingDialogBinding.getRoot())
                .create();
        loadingDialog.show();

        new Thread(() -> {
            String filename = projectFileSelector.getFileName();
            final String source = new yq(getApplicationContext(), sc_id).getFileSrc(filename, jC.b(sc_id), jC.a(sc_id), jC.c(sc_id));

            var dialogBuilder = new MaterialAlertDialogBuilder(this)
                    .setTitle(filename)
                    .setCancelable(false)
                    .setPositiveButton("Dismiss", null);

            runOnUiThread(() -> {
                if (isFinishing()) return;
                loadingDialog.dismiss();

                CodeEditor editor = new CodeEditor(this);
                editor.setTypefaceText(Typeface.MONOSPACE);
                editor.setEditable(false);
                editor.setTextSize(14);
                editor.setText(!source.isEmpty() ? source : "Failed to generate source.");
                editor.getComponent(Magnifier.class).setWithinEditorForcibly(true);

                if (filename.endsWith(".xml")) {
                    editor.setEditorLanguage(CodeEditorLanguages.loadTextMateLanguage(CodeEditorLanguages.SCOPE_NAME_XML));
                    if (ThemeUtils.isDarkThemeEnabled(getApplicationContext())) {
                        editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_DRACULA));
                    } else {
                        editor.setColorScheme(CodeEditorColorSchemes.loadTextMateColorScheme(CodeEditorColorSchemes.THEME_GITHUB));
                    }
                } else {
                    editor.setEditorLanguage(new JavaLanguage());
                    if (ThemeUtils.isDarkThemeEnabled(getApplicationContext())) {
                        editor.setColorScheme(new SchemeDarcula());
                    } else {
                        editor.setColorScheme(new EditorColorScheme());
                    }
                }

                AlertDialog dialog = dialogBuilder.create();
                dialog.setView(editor,
                        (int) getDip(24),
                        (int) getDip(20),
                        (int) getDip(24),
                        (int) getDip(0));
                dialog.show();
            });
        }).start();
    }

    /**
     * Opens {@link LogReaderActivity}.
     */
    void toLogReader() {
        Intent intent = new Intent(getApplicationContext(), LogReaderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", sc_id);
        startActivity(intent);
    }

    /**
     * Opens {@link ManageCollectionActivity}.
     */
    void toCollectionManager() {
        launchActivity(ManageCollectionActivity.class, openCollectionManager);
    }

    /**
     * Opens {@link AndroidManifestInjection}.
     */
    void toAndroidManifestManager() {
        launchActivity(AndroidManifestInjection.class, null,
                new Pair<>("file_name", projectFileSelector.currentJavaFileName));
    }

    /**
     * Opens {@link ManageCustomAttributeActivity}.
     */
    void toAppCompatInjectionManager() {
        launchActivity(ManageAppCompatActivity.class, null,
                new Pair<>("file_name", projectFileSelector.currentXmlFileName));
    }

    /**
     * Opens {@link ManageAssetsActivity}.
     */
    void toAssetManager() {
        launchActivity(ManageAssetsActivity.class, null);
    }

    /**
     * Shows a {@link CustomBlocksDialog}.
     */
    void toCustomBlocksViewer() {
        CustomBlocksDialog.show(this, sc_id);
    }

    /**
     * Opens {@link ManageJavaActivity}.
     */
    void toJavaManager() {
        launchActivity(ManageJavaActivity.class, null,
                new Pair<>("pkgName", q.packageName));
    }

    /**
     * Opens {@link ManagePermissionActivity}.
     */
    void toPermissionManager() {
        launchActivity(ManagePermissionActivity.class, null);
    }

    /**
     * Opens {@link ManageProguardActivity}.
     */
    void toProguardManager() {
        launchActivity(ManageProguardActivity.class, null);
    }

    /**
     * Opens {@link ManageResourceActivity}.
     */
    void toResourceManager() {
        launchActivity(ManageResourceActivity.class, openResourcesManager);
    }

    /**
     * Opens {@link ManageStringFogFragment}.
     */
    void toStringFogManager() {
        var fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag("stringFogFragment") == null) {
            var bottomSheet = new ManageStringFogFragment();
            bottomSheet.show(fragmentManager, "stringFogFragment");
        }
    }

    /**
     * Opens {@link ManageFontActivity}.
     */
    void toFontManager() {
        launchActivity(ManageFontActivity.class, null);
    }

    /**
     * Opens {@link ManageImageActivity}.
     */
    void toImageManager() {
        launchActivity(ManageImageActivity.class, openImageManager);
    }

    /**
     * Opens {@link ManageLibraryActivity}.
     */
    void toLibraryManager() {
        launchActivity(ManageLibraryActivity.class, openLibraryManager);
    }

    /**
     * Opens {@link ManageViewActivity}.
     */
    void toViewManager() {
        launchActivity(ManageViewActivity.class, openViewManager);
    }

    /**
     * Opens {@link ManageSoundActivity}.
     */
    void toSoundManager() {
        launchActivity(ManageSoundActivity.class, null);
    }

    /**
     * Opens {@link SrcViewerActivity}.
     */
    void toSourceCodeViewer() {
        String current = "";
        if (viewPager.getCurrentItem() == 0) {
            try {
                current = viewTabAdapter.d().getXmlName();
            } catch (Exception ignored) {
            }
        } else if (viewPager.getCurrentItem() == 1) {
            try {
                current = eventTabAdapter.getCurrentActivity().getJavaName();
            } catch (Exception ignored) {
            }
        }
        launchActivity(SrcViewerActivity.class, null, new Pair<>("current", current));
    }

    @SafeVarargs
    private void launchActivity(Class<? extends Activity> toLaunch, ActivityResultLauncher<Intent> optionalLauncher, Pair<String, String>... extras) {
        Intent intent = new Intent(getApplicationContext(), toLaunch);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("sc_id", sc_id);
        for (Pair<String, String> extra : extras) {
            intent.putExtra(extra.first, extra.second);
        }

        if (optionalLauncher == null) {
            startActivity(intent);
        } else {
            optionalLauncher.launch(intent);
        }
    }

    private abstract static class BaseTask {
        protected final WeakReference<DesignActivity> activityRef;

        protected BaseTask(DesignActivity activity) {
            this.activityRef = new WeakReference<>(activity);
        }

        protected DesignActivity getActivity() {
            return activityRef.get();
        }
    }

    private static class BuildTask extends BaseTask implements DialogInterface.OnCancelListener, BuildProgressReceiver {
        public static final String ACTION_CANCEL_BUILD = "com.besome.sketch.design.ACTION_CANCEL_BUILD";
        private final BuildingDialog dialog;
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();
        private volatile boolean canceled;
        private volatile boolean isBuildFinished;
        private final NotificationManager notificationManager;
        private final int notificationId = 1;
        private boolean isShowingNotification = false;
        private static final String CHANNEL_ID = "build_notification_channel";

        public BuildTask(DesignActivity activity) {
            super(activity);
            notificationManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
            dialog = new BuildingDialog(activity);
        }

        public void execute() {
            onPreExecute();
            executorService.execute(this::doInBackground);
        }

        private void onPreExecute() {
            DesignActivity activity = getActivity();
            if (activity == null) return;

            activity.runOnUiThread(() -> {
                activity.runProject.setText("Building APK file...");
                activity.runProject.setClickable(false);
                activity.r.a("P1I10", true);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

                if (activity.isBuildingInTheBackground()) {
                    maybeShowNotification();
                } else {
                    maybeShowDialog();
                    dialog.setIsCancelableOnBackPressed(false);
                }
            });
        }

        private void doInBackground() {
            DesignActivity activity = getActivity();
            if (activity == null) return;

            try {
                var q = activity.q;
                var sc_id = DesignActivity.sc_id;
                onProgress("Deleting temporary files...");
                FileUtil.deleteFile(q.projectMyscPath);

                q.c(activity.getApplicationContext());
                q.a();
                q.a(activity.getApplicationContext(), wq.e("600"));
                if (yB.a(lC.b(sc_id), "custom_icon")) {
                    q.aa(wq.e() + File.separator + sc_id + File.separator + "mipmaps");
                    if (yB.a(lC.b(sc_id), "isIconAdaptive", false)) {
                        q.cf("""
                                <?xml version="1.0" encoding="utf-8"?>
                                <adaptive-icon xmlns:android="http://schemas.android.com/apk/res/android" >
                                <background android:drawable="@mipmap/ic_launcher_background"/>
                                <foreground android:drawable="@mipmap/ic_launcher_foreground"/>
                                <monochrome android:drawable="@mipmap/ic_launcher_monochrome"/>
                                </adaptive-icon>""");
                    } else {
                        q.a(wq.e() + File.separator + sc_id + File.separator + "icon.png");
                    }
                }
                
                onProgress("Generating source code...");
                kC kC = jC.d(sc_id);
                kC.b(q.resDirectoryPath + File.separator + "drawable-xhdpi");
                kC = jC.d(sc_id);
                kC.c(q.resDirectoryPath + File.separator + "raw");
                kC = jC.d(sc_id);
                kC.a(q.assetsPath + File.separator + "fonts");

                ProjectBuilder builder = new ProjectBuilder(this, activity.getApplicationContext(), q);

                var fileManager = jC.b(sc_id);
                var dataManager = jC.a(sc_id);
                var libraryManager = jC.c(sc_id);
                q.a(libraryManager, fileManager, dataManager, false);
                builder.buildBuiltInLibraryInformation();
                q.b(fileManager, dataManager, libraryManager, builder.getBuiltInLibraryManager());
                q.f();
                q.e();

                builder.maybeExtractAapt2();
                if (canceled) {
                    return;
                }

                onProgress("Extracting built-in libraries...");
                BuiltInLibraries.extractCompileAssets(this);
                if (canceled) {
                    return;
                }

                onProgress("AAPT2 is running...");
                builder.compileResources();
                if (canceled) {
                    return;
                }

                onProgress("Generating view binding...");
                builder.generateViewBinding();
                if (canceled) {
                    return;
                }

                KotlinCompilerBridge.compileKotlinCodeIfPossible(this, builder);
                if (canceled) {
                    return;
                }

                onProgress("Java is compiling...");
                builder.compileJavaCode();
                if (canceled) {
                    return;
                }

                StringfogHandler stringfogHandler = new StringfogHandler(sc_id);
                stringfogHandler.start(this, builder);
                if (canceled) {
                    return;
                }

                ProguardHandler proguardHandler = new ProguardHandler(sc_id);
                proguardHandler.start(this, builder);
                if (canceled) {
                    return;
                }

                onProgress(builder.getDxRunningText());
                builder.createDexFilesFromClasses();
                if (canceled) {
                    return;
                }

                onProgress("Merging DEX files...");
                builder.getDexFilesReady();
                if (canceled) {
                    return;
                }

                onProgress("Building APK...");
                builder.buildApk();
                if (canceled) {
                    return;
                }

                onProgress("Signing APK...");
                builder.signDebugApk();
                if (canceled) {
                    return;
                }

                activity.installBuiltApk();
                isBuildFinished = true;
            } catch (MissingFileException e) {
                isBuildFinished = true;
                activity.runOnUiThread(() -> {
                    boolean isMissingDirectory = e.isMissingDirectory();

                    aB dialog = new aB(activity);
                    if (isMissingDirectory) {
                        dialog.b("Missing directory detected");
                        dialog.a("A directory important for building is missing. " +
                                "Sketchware Pro can try creating " + e.getMissingFile().getAbsolutePath() +
                                " if you'd like to.");
                        dialog.configureDefaultButton("Create", v -> {
                            dialog.dismiss();
                            if (!e.getMissingFile().mkdirs()) {
                                SketchwareUtil.toastError("Failed to create directory / directories!");
                            }
                        });
                    } else {
                        dialog.b("Missing file detected");
                        dialog.a("A file needed for building is missing. " +
                                "Put the correct file back to " + e.getMissingFile().getAbsolutePath() +
                                " and try building again.");
                    }
                    dialog.b("Dismiss", Helper.getDialogDismissListener(dialog));
                    dialog.show();
                });
            } catch (zy zy) {
                activity.indicateCompileErrorOccurred(zy.getMessage());
            } catch (Throwable tr) {
                LogUtil.e("DesignActivity$BuildTask", "Failed to build project", tr);
                activity.indicateCompileErrorOccurred(Log.getStackTraceString(tr));
            } finally {
                activity.runOnUiThread(this::onPostExecute);
            }
        }

        public void onProgress(String progress) {
            DesignActivity activity = getActivity();
            if (activity == null) return;

            activity.runOnUiThread(() -> {
                if (activity.isBuildingInTheBackground()) {
                    updateNotification(progress);
                } else {
                    if (dialog.isShowing()) {
                        dialog.setProgress(progress);
                    }
                }
            });
        }

        private void onPostExecute() {
            DesignActivity activity = getActivity();
            if (activity == null) return;

            activity.runOnUiThread(() -> {
                if (!activity.isDestroyed()) {
                    if (activity.isBuildingInTheBackground()) {
                        if (isShowingNotification) {
                            notificationManager.cancel(notificationId);
                            isShowingNotification = false;
                        }
                    } else {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                    activity.runProject.setText(Helper.getResString(R.string.common_word_run));
                    activity.runProject.setClickable(true);
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
            });
        }

        @Override
        public void onCancel(DialogInterface dialogInterface) {
            DesignActivity activity = getActivity();
            if (activity == null) return;

            if (!activity.isBuildingInTheBackground()) {
                activity.runOnUiThread(() -> {
                    aB cancelDialog = new aB(activity);
                    cancelDialog.b(activity.getString(R.string.design_cancel_build_title));
                    cancelDialog.a(activity.getString(R.string.design_cancel_build_desc));
                    cancelDialog.a(R.drawable.ic_mtrl_exit);

                    cancelDialog.a(activity.getString(R.string.design_cancel_build_btn_stop), v -> {
                        if (!isBuildFinished) {
                            if (!dialog.isCancelableOnBackPressed()) {
                                dialog.setIsCancelableOnBackPressed(true);
                                maybeShowDialog();
                                canceled = true;
                            }
                            dialog.show();
                            activity.runProject.setText("Canceling build...");
                            onProgress("Canceling build...");
                        }
                        cancelDialog.dismiss();
                    });

                    cancelDialog.b(activity.getString(R.string.design_cancel_build_btn_continue), v -> {
                        if (!isBuildFinished)
                            dialog.show();
                        cancelDialog.dismiss();
                    });

                    cancelDialog.setCancelable(false);
                    cancelDialog.setCanceledOnTouchOutside(false);
                    cancelDialog.show();
                });
            }
        }

        public void cancelBuild() {
            canceled = true;
            if (isShowingNotification) {
                notificationManager.cancel(notificationId);
                isShowingNotification = false;
            }
            DesignActivity activity = getActivity();
            if (activity != null) {
                activity.runOnUiThread(() -> {
                    activity.runProject.setText(Helper.getResString(R.string.common_word_run));
                    activity.runProject.setClickable(true);
                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                });
            }
        }

        private void maybeShowDialog() {
            DesignActivity activity = getActivity();
            if (activity == null) return;

            activity.runOnUiThread(() -> {
                if (!dialog.isShowing()) {
                    dialog.setOnCancelListener(this);
                    dialog.show();
                }
            });
        }

        private void maybeShowNotification() {
            DesignActivity activity = getActivity();
            if (activity == null) return;

            if (!isShowingNotification) {
                createNotificationChannelIfNeeded();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_mtrl_code)
                        .setContentTitle("Building project")
                        .setContentText("Starting build...")
                        .setOngoing(true)
                        .setProgress(0, 0, true)
                        .addAction(R.drawable.ic_cancel_white_96dp, "Cancel build", getCancelPendingIntent());

                notificationManager.notify(notificationId, builder.build());
                isShowingNotification = true;
            }
        }

        private void updateNotification(String progress) {
            DesignActivity activity = getActivity();
            if (activity == null) return;

            NotificationCompat.Builder builder = new NotificationCompat.Builder(activity, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_mtrl_code)
                    .setContentTitle("Building project")
                    .setContentText(progress)
                    .setOngoing(true)
                    .setProgress(0, 0, true)
                    .addAction(R.drawable.ic_cancel_white_96dp, "Cancel Build", getCancelPendingIntent());

            notificationManager.notify(notificationId, builder.build());
        }

        private PendingIntent getCancelPendingIntent() {
            DesignActivity activity = getActivity();
            if (activity == null) return null;

            Intent cancelIntent = new Intent(BuildTask.ACTION_CANCEL_BUILD);
            return PendingIntent.getBroadcast(activity, 0, cancelIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        private void createNotificationChannelIfNeeded() {
            DesignActivity activity = getActivity();
            if (activity == null) return;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "Build Notifications";
                String description = "Notifications for build progress";
                int importance = NotificationManager.IMPORTANCE_LOW;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private static class ProjectLoader extends BaseTask {
        private final Bundle savedInstanceState;
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();

        public ProjectLoader(DesignActivity activity, Bundle savedInstanceState) {
            super(activity);
            this.savedInstanceState = savedInstanceState;
        }

        public void execute() {
            getActivity().k();
            executorService.execute(this::doInBackground);
        }

        private void doInBackground() {
            DesignActivity activity = getActivity();
            if (activity != null) {
                activity.loadProject(savedInstanceState != null);
                activity.runOnUiThread(() -> {
                    if (savedInstanceState != null) {
                        activity.projectFileSelector.onRestoreInstanceState(savedInstanceState);
                        if (savedInstanceState.getInt("file_selector_current_file_type") == 0) {
                            activity.xmlLayoutOrientation.setVisibility(View.VISIBLE);
                        } else {
                            activity.xmlLayoutOrientation.setVisibility(View.GONE);
                        }
                    }

                    activity.projectFileSelector.syncState();
                    activity.h();
                    if (savedInstanceState == null) {
                        activity.checkForUnsavedProjectData();
                    }
                });
            }
        }
    }

    private static class DiscardChangesProjectCloser extends BaseTask {
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();

        public DiscardChangesProjectCloser(DesignActivity activity) {
            super(activity);
        }

        public void execute() {
            getActivity().k();
            executorService.execute(this::doInBackground);
        }

        private void doInBackground() {
            DesignActivity activity = getActivity();
            if (activity != null) {
                var sc_id = DesignActivity.sc_id;
                jC.d(sc_id).v();
                jC.d(sc_id).w();
                jC.d(sc_id).u();
                activity.runOnUiThread(() -> {
                    activity.h();
                    activity.finish();
                });
            }
        }
    }

    private static class ProjectSaver extends BaseTask {
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();

        public ProjectSaver(DesignActivity activity) {
            super(activity);
        }

        public void execute() {
            getActivity().k();
            executorService.execute(this::doInBackground);
        }

        private void doInBackground() {
            DesignActivity activity = getActivity();
            if (activity != null) {
                var sc_id = DesignActivity.sc_id;
                jC.d(sc_id).a();
                jC.b(sc_id).m();
                jC.a(sc_id).j();
                jC.d(sc_id).x();
                jC.c(sc_id).l();
                activity.runOnUiThread(() -> {
                    bB.a(activity.getApplicationContext(), Helper.getResString(R.string.common_message_complete_save), bB.TOAST_NORMAL).show();
                    activity.saveVersionCodeInformationToProject();
                    activity.h();
                    jC.d(sc_id).f();
                    jC.d(sc_id).g();
                    jC.d(sc_id).e();
                });
            }
        }
    }

    private static class SaveChangesProjectCloser extends BaseTask {
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();

        public SaveChangesProjectCloser(DesignActivity activity) {
            super(activity);
        }

        public void execute() {
            getActivity().k();
            executorService.execute(this::doInBackground);
        }

        private void doInBackground() {
            DesignActivity activity = getActivity();
            if (activity != null) {
                var sc_id = DesignActivity.sc_id;
                jC.d(sc_id).a();
                jC.b(sc_id).m();
                jC.a(sc_id).j();
                jC.d(sc_id).x();
                jC.c(sc_id).l();
                jC.d(sc_id).h();
                activity.runOnUiThread(() -> {
                    bB.a(activity.getApplicationContext(), Helper.getResString(R.string.common_message_complete_save), bB.TOAST_NORMAL).show();
                    activity.saveVersionCodeInformationToProject();
                    activity.h();
                    activity.finish();
                });
            }
        }
    }

    private static class UnsavedChangesSaver extends BaseTask {
        private final ExecutorService executorService = Executors.newSingleThreadExecutor();

        public UnsavedChangesSaver(DesignActivity activity) {
            super(activity);
        }

        public void execute() {
            executorService.execute(this::doInBackground);
        }

        private void doInBackground() {
            DesignActivity activity = getActivity();
            if (activity != null) {
                jC.a(sc_id).k();
            }
        }
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final String[] labels;

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            labels = new String[]{
                    Helper.getResString(R.string.design_tab_title_view),
                    Helper.getResString(R.string.design_tab_title_event),
                    Helper.getResString(R.string.design_tab_title_component)};
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return labels[position];
        }

        @Override
        @NonNull
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            if (position == 0) {
                viewTabAdapter = (ViewEditorFragment) fragment;
            } else if (position == 1) {
                eventTabAdapter = (rs) fragment;
            } else {
                componentTabAdapter = (br) fragment;
            }

            return fragment;
        }

        @Override
        @NonNull
        public Fragment getItem(int position) {
            if (position == 0) {
                return new ViewEditorFragment();
            } else {
                return position == 1 ? new rs() : new br();
            }
        }
    }
}
