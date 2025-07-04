package com.mod.studioasinc.gitexport;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.Consumer;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

// The "public" keyword is now required so other packages can access this class.
public class GitHubApiClient { // <-- CHANGED

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client;
    private final Gson gson;
    private final String repoOwner;
    private final String repoName;
    private final String apiBaseUrl;
    private final String token;
    private final Consumer<String> progressUpdater;

    // --- Data Models for JSON parsing ---
    private static class GitObject {
        String sha;
        String url;
    }
    private static class GitRef {
        GitObject object;
    }
    private static class GitCommit {
        GitObject tree;
    }
    private static class TreeEntry {
        String path;
        String mode = "100644"; // file
        String type = "blob";
        String sha;
    }
    private static class NewTree {
        @SerializedName("base_tree")
        String baseTree;
        List<TreeEntry> tree;
    }
    private static class NewCommit {
        String message;
        String[] parents;
        String tree;
    }
    private static class UpdateRef {
        String sha;
    }
    private static class NewBlob {
        String content;
        String encoding = "base64";
    }

    public GitHubApiClient(String repoUrl, String token, Consumer<String> progressUpdater) {
        this.client = new OkHttpClient();
        this.gson = new Gson();
        this.token = token;
        this.progressUpdater = progressUpdater;

        String[] parts = repoUrl.replace(".git", "").split("/");
        this.repoName = parts[parts.length - 1];
        this.repoOwner = parts[parts.length - 2];
        this.apiBaseUrl = "https://api.github.com/repos/" + repoOwner + "/" + repoName;
    }

    public void commitFiles(List<File> files, String baseDir, String branch, String commitMessage, String authorName, String authorEmail) throws IOException {
        progressUpdater.accept("Fetching latest commit...");
        String latestCommitSha = getLatestCommitSha(branch);
        String baseTreeSha = getBaseTreeSha(latestCommitSha);

        List<TreeEntry> treeEntries = new ArrayList<>();
        int i = 1;
        for (File file : files) {
            progressUpdater.accept("Uploading file " + i + "/" + files.size() + ": " + file.getName());
            String relativePath = file.getAbsolutePath().substring(baseDir.length() + 1).replace(File.separator, "/");
            byte[] fileBytes = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            String blobSha = createBlob(fileBytes);

            TreeEntry entry = new TreeEntry();
            entry.path = relativePath;
            entry.sha = blobSha;
            treeEntries.add(entry);
            i++;
        }

        progressUpdater.accept("Creating new file tree...");
        String newTreeSha = createTree(baseTreeSha, treeEntries);

        progressUpdater.accept("Creating commit...");
        String newCommitSha = createCommit(newTreeSha, latestCommitSha, commitMessage, authorName, authorEmail);

        progressUpdater.accept("Updating branch reference...");
        updateBranchRef(branch, newCommitSha);
    }

    private String getLatestCommitSha(String branch) throws IOException {
        Request request = buildRequest("/git/refs/heads/" + branch);
        String json = executeRequest(request, 200);
        GitRef ref = gson.fromJson(json, GitRef.class);
        if (ref == null || ref.object == null) throw new IOException("Could not parse branch reference.");
        return ref.object.sha;
    }

    private String getBaseTreeSha(String commitSha) throws IOException {
        Request request = buildRequest("/git/commits/" + commitSha);
        String json = executeRequest(request, 200);
        GitCommit commit = gson.fromJson(json, GitCommit.class);
        if (commit == null || commit.tree == null) throw new IOException("Could not parse commit data.");
        return commit.tree.sha;
    }

    private String createBlob(byte[] content) throws IOException {
        NewBlob newBlob = new NewBlob();
        newBlob.content = Base64.getEncoder().encodeToString(content);
        RequestBody body = RequestBody.create(gson.toJson(newBlob), JSON);
        Request request = buildRequest("/git/blobs", body);
        String json = executeRequest(request, 201);
        GitObject blob = gson.fromJson(json, GitObject.class);
        if (blob == null) throw new IOException("Failed to create blob.");
        return blob.sha;
    }

    private String createTree(String baseTreeSha, List<TreeEntry> entries) throws IOException {
        NewTree newTree = new NewTree();
        newTree.baseTree = baseTreeSha;
        newTree.tree = entries;
        RequestBody body = RequestBody.create(gson.toJson(newTree), JSON);
        Request request = buildRequest("/git/trees", body);
        String json = executeRequest(request, 201);
        GitObject tree = gson.fromJson(json, GitObject.class);
        if (tree == null) throw new IOException("Failed to create tree.");
        return tree.sha;
    }

    private String createCommit(String treeSha, String parentCommitSha, String message, String authorName, String authorEmail) throws IOException {
        NewCommit newCommit = new NewCommit();
        newCommit.message = message;
        newCommit.tree = treeSha;
        newCommit.parents = new String[]{parentCommitSha};
        RequestBody body = RequestBody.create(gson.toJson(newCommit), JSON);
        Request request = buildRequest("/git/commits", body);
        String json = executeRequest(request, 201);
        GitObject commit = gson.fromJson(json, GitObject.class);
        if (commit == null) throw new IOException("Failed to create commit.");
        return commit.sha;
    }

    private void updateBranchRef(String branch, String commitSha) throws IOException {
        UpdateRef updateRef = new UpdateRef();
        updateRef.sha = commitSha;
        RequestBody body = RequestBody.create(gson.toJson(updateRef), JSON);
        Request request = buildRequest("/git/refs/heads/" + branch, body, "PATCH");
        executeRequest(request, 200);
    }

    private Request buildRequest(String path) {
        return buildRequest(path, null, "GET");
    }

    private Request buildRequest(String path, RequestBody body) {
        return buildRequest(path, body, "POST");
    }

    private Request buildRequest(String path, RequestBody body, String method) {
        return new Request.Builder()
                .url(apiBaseUrl + path)
                .header("Authorization", "token " + token)
                .header("Accept", "application/vnd.github.v3+json")
                .method(method, body)
                .build();
    }

    private String executeRequest(Request request, int expectedCode) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            if (response.code() != expectedCode) {
                String errorBody = response.body() != null ? response.body().string() : "No response body";
                throw new IOException("Unexpected API response code: " + response.code() + ". Body: " + errorBody);
            }
            return response.body().string();
        }
    }
}