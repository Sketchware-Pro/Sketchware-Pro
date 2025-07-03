package com.besome.sketch.export;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pro.sketchware.R;

public class GitConfigDialogFragment extends DialogFragment {

    public interface GitConfigListener {
        void onGitConfigured();
    }

    private GitConfigListener listener;
    private SharedPreferences prefs;

    private TextInputEditText patEditText, usernameEditText, emailEditText, commitMsgEditText;
    private AutoCompleteTextView repoAutoComplete, branchAutoComplete;
    private TextInputLayout repoMenuLayout, branchMenuLayout;
    private Button mainActionButton;
    private MaterialButton forgetButton;
    private CheckBox rememberTokenCheckbox;
    private View authorizedSection;
    private TextView userInfoText;
    private MenuItem saveMenuItem;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final ArrayList<JSONObject> repoData = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.Theme_App_Dialog_FullScreen_Git);
        prefs = requireActivity().getSharedPreferences("git_config", Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_git_config, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        setupToolbar(view);

        mainActionButton.setOnClickListener(v -> {
            if (authorizedSection.getVisibility() == View.VISIBLE) {
                saveConfiguration();
            } else {
                authorize();
            }
        });

        forgetButton.setOnClickListener(v -> forgetToken());
        repoAutoComplete.setOnItemClickListener((parent, v, position, id) -> {
            branchAutoComplete.setText("", false);
            fetchBranches(repoData.get(position));
        });

        if (prefs.getBoolean("remember_token", false)) {
            String savedPat = prefs.getString("github_pat", "");
            patEditText.setText(savedPat);
            if (!savedPat.isEmpty()) {
                authorize();
            }
        }

        view.findViewById(R.id.text_pat_helper).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic"));
            startActivity(intent);
        });
    }

    private void initializeViews(View view) {
        patEditText = view.findViewById(R.id.edit_text_pat);
        repoMenuLayout = view.findViewById(R.id.menu_layout_repos);
        repoAutoComplete = view.findViewById(R.id.auto_complete_repos);
        branchMenuLayout = view.findViewById(R.id.menu_layout_branches);
        branchAutoComplete = view.findViewById(R.id.auto_complete_branches);
        mainActionButton = view.findViewById(R.id.button_authorize);
        forgetButton = view.findViewById(R.id.button_forget_token);
        rememberTokenCheckbox = view.findViewById(R.id.checkbox_remember_token);
        authorizedSection = view.findViewById(R.id.github_authorized_section);
        userInfoText = view.findViewById(R.id.text_user_info);
        usernameEditText = view.findViewById(R.id.edit_text_username);
        emailEditText = view.findViewById(R.id.edit_text_email);
        commitMsgEditText = view.findViewById(R.id.edit_text_commit_msg);
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.inflateMenu(R.menu.dialog_save_menu);
        saveMenuItem = toolbar.getMenu().findItem(R.id.action_save);
        saveMenuItem.setVisible(false);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_save) {
                saveConfiguration();
                return true;
            }
            return false;
        });
    }

    private boolean isPatValid(String token) {
        if (TextUtils.isEmpty(token)) {
            return false;
        }
        if (token.startsWith("ghp_")) {
            return token.length() == 40;
        }
        if (token.startsWith("github_pat_")) {
            return token.length() >= 90 && token.length() <= 105;
        }
        return false;
    }

    private void authorize() {
        String token = patEditText.getText().toString().trim();
        if (!isPatValid(token)) {
            showError("Invalid Personal Access Token format.");
            return;
        }

        showLoading(true);
        executor.execute(() -> {
            try {
                HttpURLConnection userConn = createConnection("https://api.github.com/user", token);
                if (userConn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    handler.post(() -> showError("Authorization failed. Check your token and permissions."));
                    return;
                }
                JSONObject user = new JSONObject(readResponse(userConn));

                HttpURLConnection repoConn = createConnection("https://api.github.com/user/repos?type=owner&sort=updated&per_page=100", token);
                if (repoConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    JSONArray repos = new JSONArray(readResponse(repoConn));
                    handler.post(() -> onUserAndReposFetched(user, repos));
                } else {
                    handler.post(() -> showError("Failed to fetch repositories."));
                }
            } catch (Exception e) {
                handler.post(() -> showError("An error occurred: " + e.getMessage()));
            }
        });
    }

    private void fetchBranches(JSONObject repo) {
        String token = patEditText.getText().toString().trim();
        try {
            String branchesUrl = repo.getString("branches_url").replace("{/branch}", "");
            showLoading(true);
            executor.execute(() -> {
                try {
                    HttpURLConnection conn = createConnection(branchesUrl, token);
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        JSONArray branches = new JSONArray(readResponse(conn));
                        handler.post(() -> onBranchesFetched(branches));
                    } else {
                        handler.post(() -> showError("Failed to fetch branches."));
                    }
                } catch (Exception e) {
                    handler.post(() -> showError("An error occurred: " + e.getMessage()));
                }
            });
        } catch (JSONException e) {
            showError("Invalid repository data.");
        }
    }

    private void onUserAndReposFetched(JSONObject user, JSONArray repos) {
        if (getContext() == null || !isAdded()) {
            return;
        }
        showLoading(false);
        try {
            String login = user.getString("login");
            String email = user.optString("email", "");

            userInfoText.setText("Authorized as: " + login);
            usernameEditText.setText(prefs.getString("git_user", login));
            emailEditText.setText(prefs.getString("git_email", email));
            commitMsgEditText.setText(prefs.getString("git_commit_msg", "Sketchware auto-commit"));

        } catch (JSONException e) {
            showError("Failed to parse user data.");
            return;
        }

        forgetButton.setVisibility(View.VISIBLE);
        rememberTokenCheckbox.setChecked(prefs.getBoolean("remember_token", false));

        ArrayList<String> repoNames = new ArrayList<>();
        repoData.clear();
        try {
            for (int i = 0; i < repos.length(); i++) {
                JSONObject repo = repos.getJSONObject(i);
                repoNames.add(repo.getString("full_name"));
                repoData.add(repo);
            }
        } catch (JSONException e) {
            showError("Failed to parse repository data.");
            return;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, repoNames);
        repoAutoComplete.setAdapter(adapter);

        authorizedSection.setVisibility(View.VISIBLE);
        mainActionButton.setText(R.string.common_word_save);
        saveMenuItem.setVisible(true);
    }

    private void onBranchesFetched(JSONArray branches) {
        if (getContext() == null || !isAdded()) {
            return;
        }
        showLoading(false);
        ArrayList<String> branchNames = new ArrayList<>();
        try {
            for (int i = 0; i < branches.length(); i++) {
                branchNames.add(branches.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            showError("Failed to parse branch data.");
            return;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, branchNames);
        branchAutoComplete.setAdapter(adapter);
        branchMenuLayout.setVisibility(View.VISIBLE);
    }

    private void saveConfiguration() {
        String repoFullName = repoAutoComplete.getText().toString();
        String branchName = branchAutoComplete.getText().toString();
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String commitMsg = commitMsgEditText.getText().toString().trim();
        String token = patEditText.getText().toString().trim();

        if (repoFullName.isEmpty() || branchName.isEmpty() || username.isEmpty() || email.isEmpty()) {
            if (getContext() != null) {
                Toast.makeText(getContext(), "Please select a repository/branch and fill in your user details.", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();
        if (rememberTokenCheckbox.isChecked()) {
            editor.putString("github_pat", token);
            editor.putBoolean("remember_token", true);
        } else {
            editor.remove("github_pat");
            editor.remove("remember_token");
        }
        editor.putString("git_repo", "https://github.com/" + repoFullName);
        editor.putString("git_branch", branchName);
        editor.putString("git_user", username);
        editor.putString("git_pass", token);
        editor.putString("git_email", email);
        editor.putString("git_commit_msg", commitMsg.isEmpty() ? "Sketchware auto-commit" : commitMsg);
        editor.apply();

        if (getContext() != null) {
            Toast.makeText(getContext(), "Configuration Saved!", Toast.LENGTH_SHORT).show();
        }
        if (listener != null) {
            listener.onGitConfigured();
        }
        dismiss();
    }

    private void forgetToken() {
        prefs.edit()
                .remove("github_pat")
                .remove("remember_token")
                .remove("git_repo")
                .remove("git_branch")
                .remove("git_user")
                .remove("git_pass")
                .remove("git_email")
                .remove("git_commit_msg")
                .apply();
        patEditText.setText("");
        authorizedSection.setVisibility(View.GONE);
        repoAutoComplete.setText("", false);
        branchAutoComplete.setText("", false);
        branchMenuLayout.setVisibility(View.GONE);
        usernameEditText.setText("");
        emailEditText.setText("");
        commitMsgEditText.setText("");

        forgetButton.setVisibility(View.GONE);
        rememberTokenCheckbox.setChecked(false);

        mainActionButton.setText(R.string.common_word_continue);
        saveMenuItem.setVisible(false);

        if (getContext() != null) {
            Toast.makeText(getContext(), "Forgotten Token and Configuration.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLoading(boolean isLoading) {
        mainActionButton.setEnabled(!isLoading);
        forgetButton.setEnabled(!isLoading);
        patEditText.setEnabled(!isLoading);
        repoMenuLayout.setEnabled(!isLoading);
        branchMenuLayout.setEnabled(!isLoading);
        usernameEditText.setEnabled(!isLoading);
        emailEditText.setEnabled(!isLoading);
        commitMsgEditText.setEnabled(!isLoading);
    }

    private void showError(String message) {
        showLoading(false);
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
    }

    private HttpURLConnection createConnection(String urlString, String token) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Authorization", "token " + token);
        conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
        return conn;
    }

    private String readResponse(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GitConfigListener) {
            listener = (GitConfigListener) context;
        } else if (getParentFragment() instanceof GitConfigListener) {
            listener = (GitConfigListener) getParentFragment();
        } else if (getActivity() instanceof GitConfigListener) {
            listener = (GitConfigListener) getActivity();
        } else {
            throw new ClassCastException(context + " must implement GitConfigListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}