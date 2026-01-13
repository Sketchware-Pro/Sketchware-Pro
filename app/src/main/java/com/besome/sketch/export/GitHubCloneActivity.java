package com.besome.sketch.export;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import mod.hey.studios.project.backup.BackupRestoreManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pro.sketchware.R;
import pro.sketchware.utility.FileUtil;

public class GitHubCloneActivity extends BaseAppCompatActivity {

    private TextInputEditText inputUrl;
    private TextView textLogs;
    private Button btnClone;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final OkHttpClient client = new OkHttpClient();
    private final StringBuilder logs = new StringBuilder();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_clone);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        inputUrl = findViewById(R.id.input_url);
        textLogs = findViewById(R.id.text_logs);
        btnClone = findViewById(R.id.btn_clone);

        btnClone.setOnClickListener(v -> startCloneProcess());
    }

    private void log(String message) {
        runOnUiThread(() -> {
            logs.append(message).append("\n");
            textLogs.setText(logs.toString());
        });
    }

    private void startCloneProcess() {
        String url = inputUrl.getText().toString().trim();
        if (url.isEmpty()) {
            Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show();
            return;
        }

        btnClone.setEnabled(false);
        logs.setLength(0);
        log("Starting clone process...");

        executor.execute(() -> {
            try {
                // 1. Parse URL
                // Format: https://github.com/owner/repo or with branch /tree/branch
                // Download URL: https://github.com/owner/repo/archive/refs/heads/branch.zip
                String owner = "";
                String repo = "";
                String branch = "main"; // Default

                Pattern p = Pattern.compile("github\\.com/([^/]+)/([^/]+)(?:/tree/(.+))?");
                Matcher m = p.matcher(url);

                if (m.find()) {
                    owner = m.group(1);
                    repo = m.group(2).replace(".git", ""); // Clean repo name
                    if (m.group(3) != null) {
                        branch = m.group(3);
                    }
                } else {
                    log("Invalid GitHub URL format.");
                    return;
                }

                log("Owner: " + owner);
                log("Repo: " + repo);
                log("Branch: " + branch);

                String zipUrl = "https://github.com/" + owner + "/" + repo + "/archive/refs/heads/" + branch + ".zip";
                log("Downloading zip from: " + zipUrl);

                // 2. Download Zip
                File cacheDir = getCacheDir();
                File zipFile = new File(cacheDir, "repo.zip");
                
                Request request = new Request.Builder().url(zipUrl).build();
                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                         // Try 'master' branch fallback if 'main' fails?
                         if (branch.equals("main")) {
                             log("Main branch not found, trying 'master'...");
                             zipUrl = "https://github.com/" + owner + "/" + repo + "/archive/refs/heads/master.zip";
                             Request retryRequest = new Request.Builder().url(zipUrl).build();
                             try (Response retryResponse = client.newCall(retryRequest).execute()){
                                 if (!retryResponse.isSuccessful()) throw new IOException("Failed to download zip: " + retryResponse.code());
                                 saveToFile(retryResponse.body().byteStream(), zipFile);
                             }
                         } else {
                             throw new IOException("Failed to download zip: " + response.code());
                         }
                    } else {
                        saveToFile(response.body().byteStream(), zipFile);
                    }
                }
                log("Download complete.");

                // 3. Unzip
                File unzipDir = new File(FileUtil.getExternalStorageDir(), "sketchware/github_temp_clone");
                FileUtil.deleteFile(unzipDir.getAbsolutePath());
                FileUtil.makeDir(unzipDir.getAbsolutePath());
                
                log("Extracting...");
                unzip(zipFile, unzipDir);
                log("Extraction complete.");

                // 4. Find .swb
                File[] extractedDirs = unzipDir.listFiles();
                if (extractedDirs == null || extractedDirs.length == 0) {
                     log("Error: Empty zip.");
                     return;
                }
                
                // GitHub zip usually extracts to repo-branch/
                File repoDir = extractedDirs[0]; 
                
                File validProjectDir = findProjectDir(unzipDir);
                
                if (validProjectDir != null) {
                    log("Found project in: " + validProjectDir.getAbsolutePath());
                }

                if (validProjectDir != null) {
                    log("Found raw project structure in: " + validProjectDir.getName());
                    log("Repackaging for restoration...");
                    
                    // Repackage into valid SWB structure
                    File repackageDir = new File(getCacheDir(), "repackage_temp");
                    FileUtil.deleteFile(repackageDir.getAbsolutePath());
                    FileUtil.makeDir(repackageDir.getAbsolutePath());
                    
                    // Copy files to repackage dir to match SWB structure
                    // SWB Structure:
                    // /project
                    // /data/
                    // /resources/
                    // /local_libs/ (optional)
                    
                    FileUtil.copyFile(new File(validProjectDir, "project").getAbsolutePath(), new File(repackageDir, "project").getAbsolutePath());
                    FileUtil.copyDirectory(new File(validProjectDir, "data"), new File(repackageDir, "data"));
                    
                    File resourcesDir = new File(validProjectDir, "resources");
                    if (resourcesDir.exists() && resourcesDir.isDirectory()) {
                        FileUtil.copyDirectory(resourcesDir, new File(repackageDir, "resources"));
                    } else {
                        // Create empty resources structure if missing
                        new File(repackageDir, "resources/images").mkdirs();
                        new File(repackageDir, "resources/sounds").mkdirs();
                        new File(repackageDir, "resources/fonts").mkdirs();
                        new File(repackageDir, "resources/icons").mkdirs();
                    }
                    
                    if (new File(validProjectDir, "local_libs").exists()) {
                         FileUtil.copyDirectory(new File(validProjectDir, "local_libs"), new File(repackageDir, "local_libs"));
                    }
                    
                    File tempSwb = new File(getCacheDir(), "temp_project.swb");
                    mod.hey.studios.project.backup.BackupFactory.zipFolder(repackageDir, tempSwb);
                    
                    log("Restoring project...");
                    String newScId = a.a.a.lC.b();
                    mod.hey.studios.project.backup.BackupFactory bm = new mod.hey.studios.project.backup.BackupFactory(newScId);
                    bm.setBackupLocalLibs(false);
                    
                    bm.restore(tempSwb);
                    
                    if (bm.isRestoreSuccess()) {
                        log("Clone & Restore successful!");
                        runOnUiThread(() -> {
                            Toast.makeText(GitHubCloneActivity.this, "Project Cloned & Restored!", Toast.LENGTH_LONG).show();
                            btnClone.postDelayed(this::finish, 1500);
                        });
                    } else {
                        log("Restore failed: " + bm.getError());
                    }
                    
                    // Cleanup
                    FileUtil.deleteFile(repackageDir.getAbsolutePath());
                    FileUtil.deleteFile(tempSwb.getAbsolutePath());

                } else {
                    log("Error: No valid Sketchware project found.");
                    log("The repository does not contain a 'project' file and 'data' folder.");
                    log("Please ensure you Pushed the project using the latest version of Sketchware Pro.");
                }

            } catch (Exception e) {
                log("Error: " + e.getMessage());
                e.printStackTrace();
            } finally {
                runOnUiThread(() -> btnClone.setEnabled(true));
            }
        });
    }
    
    private void saveToFile(InputStream in, File file) throws IOException {
        try (FileOutputStream out = new FileOutputStream(file);
             BufferedInputStream bis = new BufferedInputStream(in)) {
            byte[] buffer = new byte[8192];
            int count;
            while ((count = bis.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }
        }
    }

    private void unzip(File zipFile, File targetDirectory) throws IOException {
        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new java.io.FileInputStream(zipFile)))) {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                File file = new File(targetDirectory, ze.getName());
                File dir = ze.isDirectory() ? file : file.getParentFile();
                if (!dir.isDirectory() && !dir.mkdirs())
                    throw new IOException("Failed to ensure directory: " + dir.getAbsolutePath());
                if (ze.isDirectory())
                    continue;
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    byte[] buffer = new byte[8192];
                    int count;
                    while ((count = zis.read(buffer)) != -1)
                        fos.write(buffer, 0, count);
                }
            }
        }
    }
    
    private File findProjectDir(File dir) {
        File[] files = dir.listFiles();
        if (files == null) return null;
        
        // Check current directory
        boolean hasProject = false;
        boolean hasData = false;
        
        for (File f : files) {
            String name = f.getName();
            // Case-insensitive check
            if (name.equalsIgnoreCase("project") && f.isFile()) {
                hasProject = true;
            }
            if (name.equalsIgnoreCase("data") && f.isDirectory()) {
                hasData = true;
            }
        }
        
        if (hasProject && hasData) {
            return dir;
        }
        
        // Recursive search
        for (File f : files) {
            if (f.isDirectory()) {
                File result = findProjectDir(f);
                if (result != null) return result;
            }
        }
        return null;
    }


}
