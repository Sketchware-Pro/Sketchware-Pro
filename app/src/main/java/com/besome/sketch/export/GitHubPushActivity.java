package com.besome.sketch.export;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import a.a.a.hC;
import a.a.a.kC;
import a.a.a.eC;
import a.a.a.iC;
import a.a.a.ProjectBuilder;
import a.a.a.yB;
import a.a.a.lC;

import a.a.a.wq;
import a.a.a.xq;
import a.a.a.yq;
import mod.hey.studios.util.Helper;
import mod.hey.studios.project.backup.BackupFactory;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pro.sketchware.R;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.FilePathUtil;

public class GitHubPushActivity extends BaseAppCompatActivity {

    private static final String PREF_NAME = "github_prefs";
    private static final String KEY_TOKEN = "github_token";
    private static final String API_BASE = "https://api.github.com";

    private String sc_id;
    private yq project_metadata;
    private TextInputEditText inputToken;
    private TextInputEditText inputRepoName;
    private TextInputEditText inputCommitMessage;
    private TextView textLogs;
    private Button btnPush;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final OkHttpClient client = new OkHttpClient();
    private final StringBuilder logs = new StringBuilder();
    private int totalFiles = 0;
    private int uploadedFiles = 0;

    private TextInputEditText inputBranch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_push);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        sc_id = getIntent().getStringExtra("sc_id");
        project_metadata = new yq(getApplicationContext(), wq.d(sc_id), a.a.a.lC.b(sc_id));

        inputToken = findViewById(R.id.input_token);
        inputRepoName = findViewById(R.id.input_repo_name);
        inputBranch = findViewById(R.id.input_branch);
        inputCommitMessage = findViewById(R.id.input_commit_message);
        textLogs = findViewById(R.id.text_logs);
        btnPush = findViewById(R.id.btn_push);

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        inputToken.setText(prefs.getString(KEY_TOKEN, ""));
        inputRepoName.setText(project_metadata.projectName.toLowerCase().replace(" ", "-"));

        btnPush.setOnClickListener(v -> startPushProcess());
    }

    private void log(String message) {
        runOnUiThread(() -> {
            logs.append(message).append("\n");
            textLogs.setText(logs.toString());
        });
    }

    private void startPushProcess() {
        String token = inputToken.getText().toString().trim();
        String repoName = inputRepoName.getText().toString().trim();
        String branchInput = inputBranch.getText().toString().trim();
        String commitMsg = inputCommitMessage.getText().toString().trim();

        if (token.isEmpty() || repoName.isEmpty() || commitMsg.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Sanitize branch name
        if (branchInput.isEmpty()) branchInput = "main";
        final String targetBranch = branchInput.replace(" ", "-");

        getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().putString(KEY_TOKEN, token).apply();

        btnPush.setEnabled(false);
        logs.setLength(0);
        log("Starting process...");

        executor.execute(() -> {
            try {
                // 1. Export Source
                log("Exporting project sources...");
                File exportDir = new File(FileUtil.getExternalStorageDir(), "sketchware/github_temp/" + sc_id);
                FileUtil.deleteFile(exportDir.getAbsolutePath());
                FileUtil.makeDir(exportDir.getAbsolutePath());

                prepareProjectSources(exportDir);

                totalFiles = countFiles(exportDir);
                uploadedFiles = 0;

                // 2. Authenticate & Get User Info
                log("Authenticating...");
                JSONObject user = githubRequest(token, "GET", "/user", null);
                String username = user.getString("login");
                log("Logged in as: " + username);

                // 3. Check/Create Repo
                log("Checking repository...");
                JSONObject repo;
                try {
                    repo = githubRequest(token, "GET", "/repos/" + username + "/" + repoName, null);
                    log("Repository exists.");
                } catch (IOException e) {
                    log("Repository not found, creating...");
                    JSONObject createRepoBody = new JSONObject();
                    createRepoBody.put("name", repoName);
                    createRepoBody.put("private", true);
                    createRepoBody.put("auto_init", true);
                    repo = githubRequest(token, "POST", "/user/repos", createRepoBody);
                    log("Repository created.");
                }

                // 4. Branch Handling
                String defaultBranch = repo.getString("default_branch");
                String parentCommitSha = null;
                String baseTreeSha = null;
                boolean isNewBranch = false;

                log("Checking branch: " + targetBranch);
                try {
                    // Check if target branch exists
                    JSONObject targetRef = githubRequest(token, "GET", "/repos/" + username + "/" + repoName + "/git/refs/heads/" + targetBranch, null);
                    JSONObject object = targetRef.getJSONObject("object");
                    parentCommitSha = object.getString("sha");
                    log("Branch '" + targetBranch + "' exists.");
                } catch (IOException e) {
                    // Target branch doesn't exist, get info from default branch to branch off
                    log("Branch '" + targetBranch + "' not found. Will create from '" + defaultBranch + "'.");
                    isNewBranch = true;
                    try {
                         JSONObject defaultRef = githubRequest(token, "GET", "/repos/" + username + "/" + repoName + "/git/refs/heads/" + defaultBranch, null);
                         parentCommitSha = defaultRef.getJSONObject("object").getString("sha");
                    } catch (IOException ex) {
                        log("No default branch found (empty repo?). Initial commit.");
                        // Empty repo state, parentCommitSha remains null
                    }
                }

                if (parentCommitSha != null) {
                    JSONObject commit = githubRequest(token, "GET", "/repos/" + username + "/" + repoName + "/git/commits/" + parentCommitSha, null);
                    baseTreeSha = commit.getJSONObject("tree").getString("sha");
                }

                // 5. Upload Blobs & Build Tree
                log("Uploading files...");
                JSONArray treeArray = new JSONArray();
                uploadDirectory(token, username, repoName, exportDir, "", treeArray);

                // 6. Create Tree
                log("Creating tree...");
                JSONObject createTreeBody = new JSONObject();
                createTreeBody.put("tree", treeArray);
                if (baseTreeSha != null) createTreeBody.put("base_tree", baseTreeSha);
                JSONObject newTree = githubRequest(token, "POST", "/repos/" + username + "/" + repoName + "/git/trees", createTreeBody);
                String newTreeSha = newTree.getString("sha");

                // 7. Create Commit
                log("Creating commit...");
                JSONObject createCommitBody = new JSONObject();
                createCommitBody.put("message", commitMsg);
                createCommitBody.put("tree", newTreeSha);
                if (parentCommitSha != null) {
                    JSONArray parents = new JSONArray();
                    parents.put(parentCommitSha);
                    createCommitBody.put("parents", parents);
                }
                JSONObject newCommit = githubRequest(token, "POST", "/repos/" + username + "/" + repoName + "/git/commits", createCommitBody);
                String newCommitSha = newCommit.getString("sha");

                // 8. Update/Create Ref
                if (isNewBranch) {
                    log("Creating branch '" + targetBranch + "'...");
                    JSONObject createRefBody = new JSONObject();
                    createRefBody.put("ref", "refs/heads/" + targetBranch);
                    createRefBody.put("sha", newCommitSha);
                    githubRequest(token, "POST", "/repos/" + username + "/" + repoName + "/git/refs", createRefBody);
                } else {
                    log("Updating branch '" + targetBranch + "'...");
                    JSONObject updateRefBody = new JSONObject();
                    updateRefBody.put("sha", newCommitSha);
                    githubRequest(token, "PATCH", "/repos/" + username + "/" + repoName + "/git/refs/heads/" + targetBranch, updateRefBody);
                }

                log("Success! Pushed to " + targetBranch);
                runOnUiThread(() -> {
                    Toast.makeText(GitHubPushActivity.this, "Push Successful!", Toast.LENGTH_LONG).show();
                    btnPush.postDelayed(() -> finish(), 1500);
                });

            } catch (Exception e) {
                log("Error: " + e.getMessage());
                e.printStackTrace();
            } finally {
                runOnUiThread(() -> btnPush.setEnabled(true));
            }
        });
    }

    private void prepareProjectSources(File exportDir) throws IOException {
        log("Generating source files...");

        hC hCVar = new hC(sc_id);
        kC kCVar = new kC(sc_id);
        eC eCVar = new eC(sc_id);
        iC iCVar = new iC(sc_id);

        hCVar.i();
        kCVar.s();
        eCVar.g();
        eCVar.e();
        iCVar.i();

        // Project Type Template
        project_metadata.a(getApplicationContext(), wq.e(xq.a(sc_id) ? "600" : sc_id));

        // Start generating
        ProjectBuilder builder = new ProjectBuilder(this, project_metadata);
        project_metadata.a(iCVar, hCVar, eCVar, yq.ExportType.ANDROID_STUDIO);
        builder.buildBuiltInLibraryInformation();
        project_metadata.b(hCVar, eCVar, iCVar, builder.getBuiltInLibraryManager());

        // Icons
        if (yB.a(lC.b(sc_id), "custom_icon")) {
             project_metadata.aa(wq.e() + File.separator + sc_id + File.separator + "mipmaps");
        }

        project_metadata.a();
        kCVar.b(project_metadata.resDirectoryPath + File.separator + "drawable-xhdpi");
        kCVar.c(project_metadata.resDirectoryPath + File.separator + "raw");
        kCVar.a(project_metadata.assetsPath + File.separator + "fonts");
        project_metadata.f();

        // Apply Overrides (Custom Files from data dir)
        FilePathUtil util = new FilePathUtil();
        File pathJava = new File(util.getPathJava(sc_id));
        File pathRes = new File(util.getPathResource(sc_id));
        File pathAssets = new File(util.getPathAssets(sc_id));

        if (pathJava.exists()) {
             log("Applying custom Java overrides...");
             FileUtil.copyDirectory(pathJava, new File(project_metadata.javaFilesPath + File.separator + project_metadata.packageNameAsFolders));
        }
        if (pathRes.exists()) {
             log("Applying custom Resource overrides...");
             FileUtil.copyDirectory(pathRes, new File(project_metadata.resDirectoryPath));
        }
        if (pathAssets.exists()) {
             log("Applying custom Asset overrides...");
             FileUtil.copyDirectory(pathAssets, new File(project_metadata.assetsPath));
        }

        // Copy generated project from mysc to exportDir
        File myscRoot = new File(project_metadata.projectMyscPath);
        
        // Copy root files
        copyFileIfExists(new File(myscRoot, "build.gradle"), new File(exportDir, "build.gradle"));
        copyFileIfExists(new File(myscRoot, "settings.gradle"), new File(exportDir, "settings.gradle"));
        copyFileIfExists(new File(myscRoot, "gradle.properties"), new File(exportDir, "gradle.properties"));
        
        // Copy app module
        File appDir = new File(exportDir, "app");
        FileUtil.makeDir(appDir.getAbsolutePath());
        
        copyFileIfExists(new File(myscRoot, "app/build.gradle"), new File(appDir, "build.gradle"));
        copyFileIfExists(new File(myscRoot, "app/proguard-rules.pro"), new File(appDir, "proguard-rules.pro"));
        
        File srcDir = new File(myscRoot, "app/src");
        if (srcDir.exists()) {
             FileUtil.copyDirectory(srcDir, new File(appDir, "src"));
        }

        FileUtil.writeFile(new File(exportDir, "README.md").getAbsolutePath(), "# " + project_metadata.projectName);
        log("Generation complete.");
    }

    private void copyFileIfExists(File src, File dest) {
         if (src.exists()) {
             FileUtil.copyFile(src.getAbsolutePath(), dest.getAbsolutePath());
         }
    }

    private void uploadDirectory(String token, String owner, String repo, File directory, String pathPrefix, JSONArray treeArray) throws IOException, JSONException {
        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            String path = pathPrefix.isEmpty() ? file.getName() : pathPrefix + "/" + file.getName();
            if (file.isDirectory()) {
                uploadDirectory(token, owner, repo, file, path, treeArray);
            } else {
                // Skip very large files or binaries if needed, or handle as base64
                String content = FileUtil.readFile(file.getAbsolutePath());
                JSONObject blobBody = new JSONObject();
                blobBody.put("content", content);
                blobBody.put("encoding", "utf-8"); // Simplified: assuming text files. For images/binaries need base64.

                JSONObject blob = githubRequest(token, "POST", "/repos/" + owner + "/" + repo + "/git/blobs", blobBody);
                
                JSONObject treeEntry = new JSONObject();
                treeEntry.put("path", path);
                treeEntry.put("mode", "100644");
                treeEntry.put("type", "blob");
                treeEntry.put("sha", blob.getString("sha"));
                treeArray.put(treeEntry);

                uploadedFiles++;
                int progress = (int) ((uploadedFiles / (float) totalFiles) * 100);
                runOnUiThread(() -> {
                     int start = logs.lastIndexOf("Uploading files...");
                     if (start != -1) {
                         int end = logs.indexOf("\n", start);
                         if (end == -1) end = logs.length();
                         logs.replace(start, end, "Uploading files... " + progress + "%");
                         textLogs.setText(logs.toString());
                     }
                });
            }
        }
    }

    private JSONObject githubRequest(String token, String method, String endpoint, JSONObject body) throws IOException, JSONException {
        Request.Builder builder = new Request.Builder()
                .url(API_BASE + endpoint)
                .header("Authorization", "token " + token)
                .header("Accept", "application/vnd.github.v3+json");

        if (body != null) {
            builder.method(method, RequestBody.create(body.toString(), MediaType.parse("application/json")));
        } else {
             builder.method(method, null);
        }

        try (Response response = client.newCall(builder.build()).execute()) {
            String respStr = response.body().string();
            if (!response.isSuccessful()) {
                throw new IOException("GitHub API Error: " + response.code() + " " + respStr);
            }
            return new JSONObject(respStr);
        }
    }

    private int countFiles(File dir) {
        int count = 0;
        File[] files = dir.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    count += countFiles(f);
                } else {
                    count++;
                }
            }
        }
        return count;
    }
}
