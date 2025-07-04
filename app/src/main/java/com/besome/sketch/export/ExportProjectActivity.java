package com.besome.sketch.export;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.errors.NoRemoteRepositoryException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.spongycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import a.a.a.KB;
import a.a.a.MA;
import a.a.a.ProjectBuilder;
import a.a.a.eC;
import a.a.a.hC;
import a.a.a.iC;
import a.a.a.kC;
import a.a.a.lC;
import a.a.a.oB;
import a.a.a.wq;
import a.a.a.xq;
import a.a.a.yB;
import a.a.a.yq;
import kellinwood.security.zipsigner.ZipSigner;
import kellinwood.security.zipsigner.optional.CustomKeySigner;
import kellinwood.security.zipsigner.optional.LoadKeystoreException;
import mod.hey.studios.compiler.kotlin.KotlinCompilerBridge;
import mod.hey.studios.project.proguard.ProguardHandler;
import mod.hey.studios.project.stringfog.StringfogHandler;
import mod.hey.studios.util.Helper;
import mod.jbk.build.BuildProgressReceiver;
import mod.jbk.build.BuiltInLibraries;
import mod.jbk.build.compiler.bundle.AppBundleCompiler;
import mod.jbk.build.progress.BuildProgressMonitorForJgit;
import mod.jbk.export.GetKeyStoreCredentialsDialog;
import mod.jbk.util.TestkeySignBridge;
import pro.sketchware.R;
import pro.sketchware.utility.FilePathUtil;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class ExportProjectActivity extends BaseAppCompatActivity implements GitConfigDialogFragment.GitConfigListener {

    private String sc_id;
    private HashMap<String, Object> sc_metadata;
    private yq project_metadata;
    private SharedPreferences gitPrefs;

    private ViewGroup rootLayout;
    private Button exportProjectButton;
    private Button gitConfigureButton;
    private LinearLayout embeddedProgressLayout;
    private TextView embeddedProgressText;
    private MaterialCardView exportResultCard;
    private TextView exportResultPath;
    private Button exportResultShareButton;
    private ImageView projectIcon;
    private TextView projectAppName, projectPackageName, projectVersionName, projectWorkspaceName;

    private String lastExportedFilePath;
    private String lastExportedFileRelativePath;
    private String lastExportedMimeType;

    private AlertDialog gitProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_project);
        gitPrefs = getSharedPreferences("git_config", MODE_PRIVATE);
        initializeViews();
        setupToolbar();

        if (savedInstanceState == null) {
            sc_id = getIntent().getStringExtra("sc_id");
        } else {
            sc_id = savedInstanceState.getString("sc_id");
        }

        sc_metadata = lC.b(sc_id);
        project_metadata = new yq(getApplicationContext(), wq.d(sc_id), sc_metadata);

        projectAppName.setText(yB.c(sc_metadata, "my_app_name"));
        projectPackageName.setText(yB.c(sc_metadata, "my_sc_pkg_name"));
        String versionName = yB.c(sc_metadata, "my_sc_ver_name");
        String versionCode = yB.c(sc_metadata, "my_sc_ver_code");
        projectVersionName.setText("Version " + versionName + " (" + versionCode + ")");
        projectWorkspaceName.setText("Project: " + yB.c(sc_metadata, "my_ws_name"));

        String iconPath = wq.e() + File.separator + sc_id + File.separator + "icon.png";
        File iconFile = new File(iconPath);
        if (iconFile.exists()) {
            projectIcon.setImageURI(Uri.fromFile(iconFile));
        }

        initializeOutputDirectories();
        exportProjectButton.setOnClickListener(v -> showExportOptionsDialog());

        gitConfigureButton.setOnClickListener(v -> {
            if (getSupportFragmentManager().findFragmentByTag("git_config_dialog") == null) {
                new GitConfigDialogFragment().show(getSupportFragmentManager(), "git_config_dialog");
            }
        });
    }

    private void initializeViews() {
        rootLayout = findViewById(R.id.root_layout);
        exportProjectButton = findViewById(R.id.export_project_button);
        gitConfigureButton = findViewById(R.id.git_configure_button);
        embeddedProgressLayout = findViewById(R.id.embedded_progress_layout);
        embeddedProgressText = findViewById(R.id.embedded_progress_text);
        exportResultCard = findViewById(R.id.export_result_card);
        exportResultPath = findViewById(R.id.export_result_path);
        exportResultShareButton = findViewById(R.id.export_result_share_button);
        projectIcon = findViewById(R.id.project_icon);
        projectAppName = findViewById(R.id.project_app_name);
        projectPackageName = findViewById(R.id.project_package_name);
        projectVersionName = findViewById(R.id.project_version_name);
        projectWorkspaceName = findViewById(R.id.project_workspace_name);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
    }

    private void showExportOptionsDialog() {
        final String[] items = {
                "Signed APK",
                "Android App Bundle (AAB)",
                "Source Code (ZIP)",
                "Commit to Git Repository"
        };

        new MaterialAlertDialogBuilder(this)
                .setTitle("Choose Export Type")
                .setItems(items, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            startApkExport();
                            break;
                        case 1:
                            startAabExport();
                            break;
                        case 2:
                            startSourceCodeExport();
                            break;
                        case 3:
                            startGitExport();
                            break;
                    }
                })
                .show();
    }

    private void setupGitProgressDialog() {
        if (gitProgressDialog != null && gitProgressDialog.isShowing()) {
            return;
        }
        View dialogView = LayoutInflater.from(this).inflate(R.layout.progress_dialog_wave, null);
        gitProgressDialog = new MaterialAlertDialogBuilder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();
    }

    private void updateGitProgressDialog(String message) {
        if (gitProgressDialog == null || !gitProgressDialog.isShowing()) {
            setupGitProgressDialog();
            gitProgressDialog.show();
        }
        TextView progressText = gitProgressDialog.findViewById(R.id.progress_dialog_text);
        if (progressText != null) {
            progressText.setText(message);
        }
    }

    private void dismissGitProgressDialog() {
        if (gitProgressDialog != null && gitProgressDialog.isShowing()) {
            gitProgressDialog.dismiss();
            gitProgressDialog = null;
        }
    }

    private void startGitExport() {
        if (gitPrefs.getString("git_repo", null) == null) {
            new GitConfigDialogFragment().show(getSupportFragmentManager(), "git_config_dialog");
            return;
        }
        updateGitProgressDialog("Starting Git export...");
        new Thread(() -> {
            try {
                prepareProjectSourcesForExport();
                String repoUrl = gitPrefs.getString("git_repo", "");
                String branch = gitPrefs.getString("git_branch", "main");
                String username = gitPrefs.getString("git_user", "");
                String token = gitPrefs.getString("git_pass", "");
                String email = gitPrefs.getString("git_email", "");
                String commitMessage = gitPrefs.getString("git_commit_msg", "Sketchware auto-commit");

                StringBuilder missingConfig = new StringBuilder();
                if (repoUrl.isEmpty()) missingConfig.append("Repository URL, ");
                if (username.isEmpty()) missingConfig.append("Username, ");
                if (token.isEmpty()) missingConfig.append("Token, ");
                if (email.isEmpty()) missingConfig.append("Email for commit author, ");
                if (missingConfig.length() > 0) {
                    String missingItems = missingConfig.substring(0, missingConfig.length() - 2);
                    throw new Exception(missingItems + " not configured. Please check Git settings.");
                }

                String projectName = yB.c(sc_metadata, "my_ws_name").replaceAll("[^a-zA-Z0-9.-]", "_");
                File localRepoPath = new File(wq.s() + File.separator + "git" + File.separator + projectName);
                UsernamePasswordCredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(username, token);
                Git git;
                if (new File(localRepoPath, ".git").exists()) {
                    runOnUiThread(() -> updateGitProgressDialog("Opening repository..."));
                    git = Git.open(localRepoPath);
                    if (!git.status().call().isClean()) {
                        git.stashCreate().call();
                    }
                    runOnUiThread(() -> updateGitProgressDialog("Pulling latest changes..."));
                    git.pull().setCredentialsProvider(credentialsProvider).setRemote("origin").setRemoteBranchName(branch).call();
                } else {
                    runOnUiThread(() -> updateGitProgressDialog("Cloning repository..."));
                    if (localRepoPath.exists()) FileUtil.deleteFile(localRepoPath.getAbsolutePath());
                    localRepoPath.mkdirs();
                    git = Git.cloneRepository()
                            .setURI(repoUrl)
                            .setDirectory(localRepoPath)
                            .setBranch(branch)
                            .setCredentialsProvider(credentialsProvider)
                            .setProgressMonitor(new BuildProgressMonitorForJgit(message -> runOnUiThread(() -> updateGitProgressDialog(message))))
                            .call();
                }

                runOnUiThread(() -> updateGitProgressDialog("Updating project files..."));
                cleanGitRepository(localRepoPath);
                FileUtil.copyDirectory(new File(project_metadata.projectMyscPath), localRepoPath);
                project_metadata.e();
                runOnUiThread(() -> updateGitProgressDialog("Staging files..."));
                git.add().addFilepattern(".").call();

                if (git.status().call().isClean()) {
                    git.close();
                    runOnUiThread(() -> {
                        dismissGitProgressDialog();
                        String successMessage = "No changes detected in the project. Repository is up to date.";
                        showResult(successMessage, null, null, false);
                    });
                    return;
                }

                runOnUiThread(() -> updateGitProgressDialog("Committing changes..."));
                String finalCommitMessage = commitMessage + " (" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(new Date()) + ")";
                git.commit().setAuthor(username, email).setMessage(finalCommitMessage).call();

                runOnUiThread(() -> updateGitProgressDialog("Pushing to remote..."));
                git.push().setCredentialsProvider(credentialsProvider)
                        .setProgressMonitor(new BuildProgressMonitorForJgit(message -> runOnUiThread(() -> updateGitProgressDialog(message))))
                        .call();
                git.close();

                if (!gitPrefs.getBoolean("remember_credentials", false)) {
                    gitPrefs.edit().remove("git_pass_unused").remove("git_user_unused").remove("git_email_unused").apply();
                }

                runOnUiThread(() -> {
                    dismissGitProgressDialog();
                    String successMessage = "Project successfully pushed to:\n" + repoUrl + " (branch: " + branch + ")";
                    showResult(successMessage, null, null, false);
                });
            } catch (InvalidRemoteException | NoRemoteRepositoryException e) {
                project_metadata.e();
                runOnUiThread(() -> {
                    dismissGitProgressDialog();
                    onExportError("Git Error: Invalid Repository\n\nThe URL might be incorrect, or the repository does not exist. Please verify the URL and your network connection.");
                });
            } catch (TransportException e) {
                project_metadata.e();
                runOnUiThread(() -> {
                    dismissGitProgressDialog();
                    String userFriendlyMessage;
                    String detailedMessage = e.getMessage() != null ? e.getMessage().toLowerCase(Locale.ROOT) : "";
                    if (detailedMessage.contains("not authorized") || detailedMessage.contains("authentication not supported")) {
                        userFriendlyMessage = "Authentication Failed.\nPlease check your username and personal access token. The token may be invalid, expired, or lack the necessary permissions.";
                    } else {
                        userFriendlyMessage = "A network transport error occurred.\nPlease check your internet connection and the repository URL.";
                    }
                    onExportError("Git Error: Transport Failed\n\n" + userFriendlyMessage);
                });
            } catch (Exception e) {
                project_metadata.e();
                runOnUiThread(() -> {
                    dismissGitProgressDialog();
                    String message = e.getMessage() != null ? e.getMessage() : "An unknown error occurred.";
                    onExportError("Git Operation Failed\n\n" + message);
                    Log.e("GitExport", "An unexpected error occurred during Git export.", e);
                });
            }
        }).start();
    }

    @Override
    public void onGitConfigured() {
        SketchwareUtil.toast("Configuration saved! You can now commit.");
        startGitExport();
    }

    private void startApkExport() {
        GetKeyStoreCredentialsDialog dialog = new GetKeyStoreCredentialsDialog(this,
                R.drawable.ic_apk_color_48dp,
                "Sign & Export APK",
                "To sign the APK, provide your keystore credentials or use the test key.");
        dialog.setListener(credentials -> {
            showEmbeddedProgress(true, "Starting APK export...");
            BuildingAsyncTask task = new BuildingAsyncTask(this, yq.ExportType.SIGN_APP);
            if (credentials != null) {
                if (credentials.isForSigningWithTestkey()) {
                    task.setSignWithTestkey(true);
                } else {
                    task.configureResultJarSigning(wq.j(), credentials.getKeyStorePassword().toCharArray(),
                            credentials.getKeyAlias(), credentials.getKeyPassword().toCharArray(),
                            credentials.getSigningAlgorithm());
                }
            } else {
                task.disableResultJarSigning();
            }
            task.execute();
        });
        dialog.show();
    }

    private void startAabExport() {
        GetKeyStoreCredentialsDialog dialog = new GetKeyStoreCredentialsDialog(this,
                R.drawable.open_box_48,
                "Sign & Export AAB",
                "AABs must be signed. Provide your keystore credentials to proceed.");
        dialog.setListener(credentials -> {
            showEmbeddedProgress(true, "Starting AAB export...");
            BuildingAsyncTask task = new BuildingAsyncTask(this, yq.ExportType.AAB);
            task.enableAppBundleBuild();
            if (credentials != null) {
                if (credentials.isForSigningWithTestkey()) {
                    task.setSignWithTestkey(true);
                } else {
                    task.configureResultJarSigning(wq.j(), credentials.getKeyStorePassword().toCharArray(),
                            credentials.getKeyAlias(), credentials.getKeyPassword().toCharArray(),
                            credentials.getSigningAlgorithm());
                }
            }
            task.execute();
        });
        dialog.show();
    }

    private void startSourceCodeExport() {
        showEmbeddedProgress(true, "Exporting source code...");
        new Thread(() -> {
            try {
                String exportedSrcFilename = exportProjectSources();
                runOnUiThread(() -> onSourceCodeExportSuccess(exportedSrcFilename));
            } catch (Exception e) {
                runOnUiThread(() -> onExportError(Log.getStackTraceString(e)));
            }
        }).start();
    }

    private void prepareProjectSourcesForExport() throws Exception {
        FileUtil.deleteFile(project_metadata.projectMyscPath);
        hC hCVar = new hC(sc_id);
        kC kCVar = new kC(sc_id);
        eC eCVar = new eC(sc_id);
        iC iCVar = new iC(sc_id);
        hCVar.i();
        kCVar.s();
        eCVar.g();
        eCVar.e();
        iCVar.i();
        project_metadata.a(getApplicationContext(), wq.e(xq.a(sc_id) ? "600" : sc_id));
        ProjectBuilder builder = new ProjectBuilder(this, project_metadata);
        project_metadata.a(iCVar, hCVar, eCVar, yq.ExportType.SOURCE_CODE);
        builder.buildBuiltInLibraryInformation();
        project_metadata.b(hCVar, eCVar, iCVar, builder.getBuiltInLibraryManager());
        if (yB.a(lC.b(sc_id), "custom_icon")) {
            project_metadata.aa(wq.e() + File.separator + sc_id + File.separator + "mipmaps");
            if (yB.a(lC.b(sc_id), "isIconAdaptive", false)) {
                project_metadata.cf("<?xml version=\"1.0\" encoding=\"utf-8\"?><adaptive-icon xmlns:android=\"http://schemas.android.com/apk/res/android\" ><background android:drawable=\"@mipmap/ic_launcher_background\"/><foreground android:drawable=\"@mipmap/ic_launcher_foreground\"/><monochrome android:drawable=\"@mipmap/ic_launcher_monochrome\"/></adaptive-icon>");
            }
        }
        project_metadata.a();
        kCVar.b(project_metadata.resDirectoryPath + File.separator + "drawable-xhdpi");
        kCVar.c(project_metadata.resDirectoryPath + File.separator + "raw");
        kCVar.a(project_metadata.assetsPath + File.separator + "fonts");
        project_metadata.f();
        FilePathUtil util = new FilePathUtil();
        copyExtraFiles(util, "java", project_metadata.javaFilesPath + File.separator + project_metadata.packageNameAsFolders);
        copyExtraFiles(util, "resource", project_metadata.resDirectoryPath);
        copyExtraFiles(util, "assets", project_metadata.assetsPath);
        copyExtraFiles(util, "nativelibs", new File(project_metadata.generatedFilesPath, "jniLibs").getAbsolutePath());
        String pathProguard = util.getPathProguard(sc_id);
        if (FileUtil.isExistFile(pathProguard)) {
            FileUtil.copyFile(pathProguard, project_metadata.proguardFilePath);
        }
    }

    private void cleanGitRepository(File directory) throws IOException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.getName().equals(".git")) continue;
                if (file.isDirectory()) {
                    FileUtil.deleteFile(file.getAbsolutePath());
                } else if (!file.delete()) {
                    throw new IOException("Failed to delete file: " + file);
                }
            }
        }
    }

    private String exportProjectSources() throws Exception {
        prepareProjectSourcesForExport();
        ArrayList<String> toCompress = new ArrayList<>();
        toCompress.add(project_metadata.projectMyscPath);
        String exportedFilename = yB.c(sc_metadata, "my_ws_name") + ".zip";
        String exportedSourcesZipPath = wq.s() + File.separator + "export_src" + File.separator + exportedFilename;
        if (new oB().e(exportedSourcesZipPath)) {
            new oB().c(exportedSourcesZipPath);
        }
        ArrayList<String> toExclude = new ArrayList<>();
        toExclude.add("DebugActivity.java");
        if (!new File(new FilePathUtil().getPathJava(sc_id) + File.separator + "SketchApplication.java").exists()) {
            toExclude.add("SketchApplication.java");
        }
        new KB().a(exportedSourcesZipPath, toCompress, toExclude);
        project_metadata.e();
        return exportedFilename;
    }

    private void copyExtraFiles(FilePathUtil util, String type, String dest) throws IOException {
        File sourceDir;
        if ("java".equals(type)) sourceDir = new File(util.getPathJava(sc_id));
        else if ("resource".equals(type)) sourceDir = new File(util.getPathResource(sc_id));
        else if ("assets".equals(type)) sourceDir = new File(util.getPathAssets(sc_id));
        else if ("nativelibs".equals(type)) sourceDir = new File(util.getPathNativelibs(sc_id));
        else return;
        if (sourceDir.exists()) FileUtil.copyDirectory(sourceDir, new File(dest));
    }

    private void onSourceCodeExportSuccess(String exportedFilename) {
        String fullPath = wq.s() + File.separator + "export_src" + File.separator + exportedFilename;
        String relativePath = File.separator + "sketchware" + File.separator + "export_src" + File.separator + exportedFilename;
        showResult(relativePath, fullPath, "application/zip", true);
    }

    public void onApkExportSuccess(String filename) {
        String fullPath = wq.o() + File.separator + filename;
        String relativePath = File.separator + "sketchware" + File.separator + "signed_apk" + File.separator + filename;
        showResult(relativePath, fullPath, "application/vnd.android.package-archive", false);
    }

    public void onAabExportSuccess(String filename) {
        String signedAabDir = Environment.getExternalStorageDirectory() + File.separator + "sketchware" + File.separator + "signed_aab";
        String fullPath = signedAabDir + File.separator + filename;
        String relativePath = "/sketchware/signed_aab/" + filename;
        showResult(relativePath, fullPath, "application/zip", false);
    }

    public void onExportError(String error) {
        showEmbeddedProgress(false, null);
        dismissGitProgressDialog();
        SketchwareUtil.showAnErrorOccurredDialog(this, error);
    }

    private void showEmbeddedProgress(boolean show, String message) {
        TransitionManager.beginDelayedTransition(rootLayout);
        exportProjectButton.setEnabled(!show);
        gitConfigureButton.setEnabled(!show);
        exportResultCard.setVisibility(View.GONE);
        if (show) {
            embeddedProgressLayout.setVisibility(View.VISIBLE);
            embeddedProgressText.setText(message);
        } else {
            embeddedProgressLayout.setVisibility(View.GONE);
        }
    }

    private void showResult(String relativePath, String fullPath, String mimeType, boolean canShare) {
        showEmbeddedProgress(false, null);
        dismissGitProgressDialog();
        TransitionManager.beginDelayedTransition(rootLayout);
        exportResultPath.setText(relativePath);
        exportResultCard.setVisibility(View.VISIBLE);
        exportResultShareButton.setVisibility(canShare ? View.VISIBLE : View.GONE);
        lastExportedFilePath = fullPath;
        lastExportedFileRelativePath = relativePath;
        lastExportedMimeType = mimeType;
        if (canShare) {
            exportResultShareButton.setOnClickListener(v -> shareLastExport());
        }
    }

    private void shareLastExport() {
        if (lastExportedFilePath != null && !lastExportedFilePath.isEmpty()) {
            try {
                File file = new File(lastExportedFilePath);
                Uri fileUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType(lastExportedMimeType);
                intent.putExtra(Intent.EXTRA_STREAM, fileUri);
                intent.putExtra(Intent.EXTRA_SUBJECT, "Project Export: " + project_metadata.projectName);
                intent.putExtra(Intent.EXTRA_TEXT, "Attached is the exported project file: " + lastExportedFileRelativePath);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, "Share Exported File"));
            } catch (Exception e) {
                SketchwareUtil.toastError("Failed to share file: " + e.getMessage());
            }
        }
    }

    private void initializeOutputDirectories() {
        oB fileUtil = new oB();
        fileUtil.f(wq.o());
        fileUtil.f(wq.s() + File.separator + "export_src");
        fileUtil.f(wq.s() + File.separator + "git");
        fileUtil.f(Environment.getExternalStorageDirectory() + File.separator + "sketchware" + File.separator + "signed_aab");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("sc_id", sc_id);
        super.onSaveInstanceState(outState);
    }

    private static class BuildingAsyncTask extends MA implements DialogInterface.OnCancelListener, BuildProgressReceiver {
        private final WeakReference<ExportProjectActivity> activity;
        private final yq project_metadata;
        private final yq.ExportType exportType;
        private ProjectBuilder builder;
        private boolean canceled = false;
        private boolean buildingAppBundle = false;
        private String signingKeystorePath = null;
        private char[] signingKeystorePassword = null;
        private String signingAliasName = null;
        private char[] signingAliasPassword = null;
        private String signingAlgorithm = null;
        private boolean signWithTestkey = false;

        public BuildingAsyncTask(ExportProjectActivity exportProjectActivity, yq.ExportType exportType) {
            super(exportProjectActivity);
            this.exportType = exportType;
            activity = new WeakReference<>(exportProjectActivity);
            project_metadata = exportProjectActivity.project_metadata;
            activity.get().addTask(this);
        }

        @Override
        public void b() {
            if (canceled) return;
            ExportProjectActivity currentActivity = activity.get();
            if (currentActivity == null) return;
            String sc_id = currentActivity.sc_id;
            try {
                publishProgress("Initializing build...");
                FileUtil.deleteFile(project_metadata.projectMyscPath);
                hC hCVar = new hC(sc_id);
                kC kCVar = new kC(sc_id);
                eC eCVar = new eC(sc_id);
                iC iCVar = new iC(sc_id);
                hCVar.i();
                kCVar.s();
                eCVar.g();
                eCVar.e();
                iCVar.i();
                if (canceled) return;
                File outputFile = new File(getCorrectResultFilename(project_metadata.releaseApkPath));
                if (outputFile.exists() && !outputFile.delete()) {
                    throw new IllegalStateException("Couldn't delete file " + outputFile.getAbsolutePath());
                }
                project_metadata.c(currentActivity);
                if (canceled) return;
                project_metadata.a(currentActivity, wq.e("600"));
                if (canceled) return;
                if (yB.a(lC.b(sc_id), "custom_icon")) {
                    project_metadata.aa(wq.e() + File.separator + sc_id + File.separator + "mipmaps");
                    if (yB.a(lC.b(sc_id), "isIconAdaptive", false)) {
                        project_metadata.cf("<?xml version=\"1.0\" encoding=\"utf-8\"?><adaptive-icon xmlns:android=\"http://schemas.android.com/apk/res/android\" ><background android:drawable=\"@mipmap/ic_launcher_background\"/><foreground android:drawable=\"@mipmap/ic_launcher_foreground\"/><monochrome android:drawable=\"@mipmap/ic_launcher_monochrome\"/></adaptive-icon>");
                    } else {
                        project_metadata.a(wq.e() + File.separator + sc_id + File.separator + "icon.png");
                    }
                }
                project_metadata.a();
                kCVar.b(project_metadata.resDirectoryPath + File.separator + "drawable-xhdpi");
                kCVar.c(project_metadata.resDirectoryPath + File.separator + "raw");
                kCVar.a(project_metadata.assetsPath + File.separator + "fonts");
                builder = new ProjectBuilder(this, currentActivity, project_metadata);
                builder.setBuildAppBundle(buildingAppBundle);
                project_metadata.a(iCVar, hCVar, eCVar, exportType);
                builder.buildBuiltInLibraryInformation();
                project_metadata.b(hCVar, eCVar, iCVar, builder.getBuiltInLibraryManager());
                if (canceled) return;
                publishProgress("Extracting AAPT2 binaries...");
                builder.maybeExtractAapt2();
                if (canceled) return;
                publishProgress("Extracting built-in libraries...");
                BuiltInLibraries.extractCompileAssets(this);
                if (canceled) return;
                publishProgress("AAPT2 is running...");
                builder.compileResources();
                if (canceled) return;
                KotlinCompilerBridge.compileKotlinCodeIfPossible(this, builder);
                if (canceled) return;
                publishProgress("Java is compiling...");
                builder.compileJavaCode();
                if (canceled) return;
                new StringfogHandler(project_metadata.sc_id).start(this, builder);
                if (canceled) return;
                new ProguardHandler(project_metadata.sc_id).start(this, builder);
                if (canceled) return;
                publishProgress(builder.getDxRunningText());
                builder.createDexFilesFromClasses();
                if (canceled) return;
                publishProgress("Merging DEX files...");
                builder.getDexFilesReady();
                if (canceled) return;
                if (buildingAppBundle) {
                    handleAppBundleBuild();
                } else {
                    handleApkBuild();
                }
            } catch (Throwable throwable) {
                if (throwable instanceof LoadKeystoreException && "Incorrect password, or integrity check failed.".equals(throwable.getMessage())) {
                    currentActivity.runOnUiThread(() -> currentActivity.onExportError("Either an incorrect password was entered, or your key store is corrupt."));
                } else {
                    Log.e("AppExporter", "Build failed", throwable);
                    currentActivity.runOnUiThread(() -> currentActivity.onExportError(Log.getStackTraceString(throwable)));
                }
                cancel(true);
            }
        }

        private void handleAppBundleBuild() throws Exception {
            AppBundleCompiler compiler = new AppBundleCompiler(builder);
            publishProgress("Creating app module...");
            compiler.createModuleMainArchive();
            publishProgress("Building app bundle...");
            compiler.buildBundle();
            publishProgress("Signing app bundle...");
            String createdBundlePath = AppBundleCompiler.getDefaultAppBundleOutputFile(project_metadata).getAbsolutePath();
            String signedAppBundleDirectoryPath = Environment.getExternalStorageDirectory() + File.separator + "sketchware" + File.separator + "signed_aab";
            String outputPath = signedAppBundleDirectoryPath + File.separator + getCorrectResultFilename(Uri.fromFile(new File(createdBundlePath)).getLastPathSegment());
            if (signWithTestkey) {
                ZipSigner signer = new ZipSigner();
                signer.setKeymode(ZipSigner.KEY_TESTKEY);
                signer.signZip(createdBundlePath, outputPath);
            } else if (isResultJarSigningEnabled()) {
                Security.addProvider(new BouncyCastleProvider());
                CustomKeySigner.signZip(new ZipSigner(), signingKeystorePath, signingKeystorePassword, signingAliasName, signingAliasPassword, signingAlgorithm, createdBundlePath, outputPath);
            } else {
                FileUtil.copyFile(createdBundlePath, outputPath);
            }
        }

        private void handleApkBuild() throws Exception {
            publishProgress("Building APK...");
            builder.buildApk();
            if (canceled) return;
            publishProgress("Aligning APK...");
            builder.runZipalign(builder.yq.unsignedUnalignedApkPath, builder.yq.unsignedAlignedApkPath);
            if (canceled) return;
            publishProgress("Signing APK...");
            String outputLocation = getCorrectResultFilename(builder.yq.releaseApkPath);
            if (signWithTestkey) {
                TestkeySignBridge.signWithTestkey(builder.yq.unsignedAlignedApkPath, outputLocation);
            } else if (isResultJarSigningEnabled()) {
                Security.addProvider(new BouncyCastleProvider());
                CustomKeySigner.signZip(new ZipSigner(), wq.j(), signingKeystorePassword, signingAliasName, signingAliasPassword, signingAlgorithm, builder.yq.unsignedAlignedApkPath, outputLocation);
            } else {
                FileUtil.copyFile(builder.yq.unsignedAlignedApkPath, outputLocation);
            }
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            canceled = true;
            publishProgress("Canceling process...");
        }

        @Override
        public void onCancelled() {
            super.onCancelled();
            ExportProjectActivity currentActivity = activity.get();
            if (currentActivity == null) return;
            currentActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            currentActivity.showEmbeddedProgress(false, null);
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            activity.get().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        @Override
        public void onProgressUpdate(String... strArr) {
            super.onProgressUpdate(strArr);
            ExportProjectActivity currentActivity = activity.get();
            if (currentActivity != null) {
                currentActivity.showEmbeddedProgress(true, strArr[0]);
            }
        }

        @Override
        public void a() {
            ExportProjectActivity currentActivity = activity.get();
            if (currentActivity == null) return;
            currentActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            if (buildingAppBundle) {
                String aabFilename = getCorrectResultFilename(project_metadata.projectName + ".aab");
                currentActivity.onAabExportSuccess(aabFilename);
            } else {
                String apkFilename = getCorrectResultFilename(project_metadata.projectName + "_release.apk");
                currentActivity.onApkExportSuccess(apkFilename);
            }
        }

        @Override
        public void a(String str) {
            ExportProjectActivity currentActivity = activity.get();
            if (currentActivity == null) return;
            currentActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            currentActivity.onExportError(str);
        }

        public void enableAppBundleBuild() {
            buildingAppBundle = true;
        }

        public void configureResultJarSigning(String keystorePath, char[] keystorePassword, String aliasName, char[] aliasPassword, String signatureAlgorithm) {
            this.signingKeystorePath = keystorePath;
            this.signingKeystorePassword = keystorePassword;
            this.signingAliasName = aliasName;
            this.signingAliasPassword = aliasPassword;
            this.signingAlgorithm = signatureAlgorithm;
        }

        public void setSignWithTestkey(boolean signWithTestkey) {
            this.signWithTestkey = signWithTestkey;
        }

        public void disableResultJarSigning() {
            this.signingKeystorePath = null;
            this.signingKeystorePassword = null;
            this.signingAliasName = null;
            this.signingAliasPassword = null;
            this.signingAlgorithm = null;
        }

        public boolean isResultJarSigningEnabled() {
            return signingKeystorePath != null && signingKeystorePassword != null && signingAliasName != null && signingAliasPassword != null && signingAlgorithm != null;
        }

        private String getCorrectResultFilename(String oldFormatFilename) {
            if (!isResultJarSigningEnabled() && !signWithTestkey) {
                if (buildingAppBundle) {
                    return oldFormatFilename.replace(".aab", ".unsigned.aab");
                } else {
                    return oldFormatFilename.replace("_release", "_release.unsigned");
                }
            }
            return oldFormatFilename;
        }

        @Override
        public void onProgress(String progress, int step) {
            publishProgress(progress);
        }
    }
}