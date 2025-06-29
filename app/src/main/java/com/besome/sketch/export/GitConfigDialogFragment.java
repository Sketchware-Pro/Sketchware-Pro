package com.besome.sketch.export;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

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
import pro.sketchware.utility.SketchwareUtil;

public class GitConfigDialogFragment extends DialogFragment {

    public interface GitConfigListener {
        void onGitConfigured();
    }

    private GitConfigListener listener;
    private SharedPreferences prefs;

    private TextInputEditText patEditText;
    private Spinner repoSpinner, branchSpinner;
    private Button authorizeButton, forgetButton;
    private CheckBox rememberTokenCheckbox;
    private View authorizedSection;
    private ProgressBar progressBar;
    private TextView errorText, userInfoText, branchLabel;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final ArrayList<String> repoNames = new ArrayList<>();
    private final ArrayList<JSONObject> repoData = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // MODIFIED: Use the new animated, dynamic color theme
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

        // MODIFIED: Automatically authorize if token is remembered
        if (prefs.getBoolean("remember_token", false)) {
            String savedPat = prefs.getString("github_pat", "");
            patEditText.setText(savedPat);
            rememberTokenCheckbox.setChecked(true);
            if (!savedPat.isEmpty()) {
                authorize();
            }
        }

        authorizeButton.setOnClickListener(v -> authorize());
        forgetButton.setOnClickListener(v -> forgetToken());
        
        view.findViewById(R.id.text_pat_helper).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-personal-access-token-classic"));
            startActivity(intent);
        });

        repoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    fetchBranches(repoData.get(position - 1));
                } else {
                    branchSpinner.setVisibility(View.GONE);
                    branchLabel.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void initializeViews(View view) {
        patEditText = view.findViewById(R.id.edit_text_pat);
        repoSpinner = view.findViewById(R.id.spinner_repos);
        branchSpinner = view.findViewById(R.id.spinner_branches);
        authorizeButton = view.findViewById(R.id.button_authorize);
        forgetButton = view.findViewById(R.id.button_forget_token);
        rememberTokenCheckbox = view.findViewById(R.id.checkbox_remember_token);
        authorizedSection = view.findViewById(R.id.github_authorized_section);
        progressBar = view.findViewById(R.id.progress_bar);
        errorText = view.findViewById(R.id.text_error);
        userInfoText = view.findViewById(R.id.text_user_info);
        // MODIFIED: Find the new branch label
        branchLabel = view.findViewById(R.id.text_branch_label);
    }

    private void setupToolbar(View view) {
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        // MODIFIED: Inflate the new menu with the save icon
        toolbar.inflateMenu(R.menu.dialog_save_menu);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_save) {
                saveConfiguration();
                return true;
            }
            return false;
        });
    }
    
    private void authorize() {
        String token = patEditText.getText().toString().trim();
        if (token.isEmpty()) {
            showError("Personal Access Token cannot be empty.");
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
                String login = user.getString("login");

                HttpURLConnection repoConn = createConnection("https://api.github.com/user/repos?type=owner&sort=updated&per_page=100", token);
                if (repoConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    JSONArray repos = new JSONArray(readResponse(repoConn));
                    handler.post(() -> onReposFetched(repos, login));
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

    private void onReposFetched(JSONArray repos, String login) {
        showLoading(false);
        userInfoText.setText("Authorized as: " + login);
        repoNames.clear();
        repoData.clear();
        repoNames.add("Select a repository...");
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, repoNames);
        repoSpinner.setAdapter(adapter);
        authorizedSection.setVisibility(View.VISIBLE);
    }
    
    private void onBranchesFetched(JSONArray branches) {
        showLoading(false);
        ArrayList<String> branchNames = new ArrayList<>();
        branchNames.add("Select a branch...");
        try {
            for (int i = 0; i < branches.length(); i++) {
                branchNames.add(branches.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            showError("Failed to parse branch data.");
            return;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, branchNames);
        branchSpinner.setAdapter(adapter);
        branchSpinner.setVisibility(View.VISIBLE);
        branchLabel.setVisibility(View.VISIBLE); // MODIFIED: Show the branch label
    }

    private void saveConfiguration() {
        if (repoSpinner.getSelectedItemPosition() <= 0 || branchSpinner.getSelectedItemPosition() <= 0) {
            Toast.makeText(getContext(), "Please select both a repository and a branch.", Toast.LENGTH_SHORT).show();
            return;
        }
        String repoFullName = repoSpinner.getSelectedItem().toString();
        String branchName = branchSpinner.getSelectedItem().toString();
        
        SharedPreferences.Editor editor = prefs.edit();
        if(rememberTokenCheckbox.isChecked()) {
             editor.putString("github_pat", patEditText.getText().toString().trim());
             editor.putBoolean("remember_token", true);
        } else {
             editor.remove("github_pat");
             editor.remove("remember_token");
        }
        editor.putString("git_repo", "https://github.com/" + repoFullName);
        editor.putString("git_branch", branchName);
        editor.apply();

        Toast.makeText(getContext(), "Configuration Saved!", Toast.LENGTH_SHORT).show();
        if (listener != null) {
            listener.onGitConfigured();
        }
        dismiss();
    }
    
    private void forgetToken() {
        prefs.edit().remove("github_pat").remove("remember_token").apply();
        patEditText.setText("");
        rememberTokenCheckbox.setChecked(false);
        authorizedSection.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Forgotten Token.", Toast.LENGTH_SHORT).show();
    }

    private void showLoading(boolean isLoading) {
        progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        authorizeButton.setEnabled(!isLoading);
        if(isLoading) errorText.setVisibility(View.GONE);
    }

    private void showError(String message) {
        showLoading(false);
        errorText.setText(message);
        errorText.setVisibility(View.VISIBLE);
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
        } else {
            throw new ClassCastException(context.toString() + " must implement GitConfigListener");
        }
    }
}