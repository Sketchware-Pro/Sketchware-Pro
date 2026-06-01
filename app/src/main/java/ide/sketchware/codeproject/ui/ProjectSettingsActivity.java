package ide.sketchware.codeproject.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

import java.io.File;
import java.util.HashMap;

import a.a.a.lC;
import ide.sketchware.R;
import ide.sketchware.codeproject.model.CodeProject;
import ide.sketchware.databinding.ActivityProjectSettingsBinding;
import ide.sketchware.utility.FileUtil;

public class ProjectSettingsActivity extends BaseAppCompatActivity {

    private ActivityProjectSettingsBinding binding;
    private String scId;
    private HashMap<String, Object> metadata;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProjectSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        scId = getIntent().getStringExtra("sc_id");
        if (scId == null) {
            finish();
            return;
        }

        metadata = lC.b(scId);
        if (metadata == null) {
            finish();
            return;
        }

        setupToolbar();
        populateFields();
        setupSaveButton();
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void populateFields() {
        binding.inputAppName.setText(getMetaString("my_app_name"));
        binding.inputPackageName.setText(getMetaString("my_sc_pkg_name"));
        binding.inputVersionCode.setText(getMetaString("sc_ver_code"));
        binding.inputVersionName.setText(getMetaString("sc_ver_name"));

        String minSdk = getMetaString("sc_min_sdk");
        if (minSdk.isEmpty()) {
            minSdk = "21";
        }
        binding.inputMinSdk.setText(minSdk);
    }

    private void setupSaveButton() {
        binding.btnSave.setOnClickListener(v -> saveSettings());
    }

    private static final java.util.regex.Pattern PACKAGE_SEGMENT =
            java.util.regex.Pattern.compile("[a-zA-Z][a-zA-Z0-9_]*");

    private void saveSettings() {
        String appName = binding.inputAppName.getText().toString().trim();
        String packageName = binding.inputPackageName.getText().toString().trim();
        String versionCode = binding.inputVersionCode.getText().toString().trim();
        String versionName = binding.inputVersionName.getText().toString().trim();
        String minSdk = binding.inputMinSdk.getText().toString().trim();

        if (appName.isEmpty()) {
            binding.inputAppName.setError(getString(R.string.code_project_error_app_name));
            return;
        }
        if (!isValidPackageName(packageName)) {
            binding.inputPackageName.setError(getString(R.string.code_project_error_package_name));
            return;
        }
        int versionCodeInt;
        try {
            versionCodeInt = Integer.parseInt(versionCode);
            if (versionCodeInt <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            binding.inputVersionCode.setError(getString(R.string.code_project_error_version_code));
            return;
        }
        if (versionName.isEmpty()) {
            binding.inputVersionName.setError(getString(R.string.code_project_error_version_name));
            return;
        }
        int minSdkInt;
        try {
            minSdkInt = Integer.parseInt(minSdk);
            if (minSdkInt <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            binding.inputMinSdk.setError(getString(R.string.code_project_error_min_sdk));
            return;
        }

        // Update metadata
        metadata.put("my_app_name", appName);
        metadata.put("my_sc_pkg_name", packageName);
        metadata.put("sc_ver_code", String.valueOf(versionCodeInt));
        metadata.put("sc_ver_name", versionName);
        metadata.put("sc_min_sdk", String.valueOf(minSdkInt));

        // Save via lC
        lC.a(scId, metadata);

        // Update manifest package attribute on disk
        updateManifestPackage(packageName);
        // Update strings.xml app_name on disk
        updateAppNameInStrings(appName);

        Toast.makeText(this, R.string.code_project_settings_saved, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    private void updateManifestPackage(String packageName) {
        CodeProject project = CodeProject.fromMetadata(metadata);
        String manifestPath = project.getManifestPath();
        java.io.File manifestFile = new java.io.File(manifestPath);
        if (manifestFile.exists()) {
            String content = FileUtil.readFile(manifestPath);
            // Replace package="..." attribute
            content = content.replaceFirst(
                "package=\"[^\"]*\"",
                "package=\"" + packageName + "\""
            );
            FileUtil.writeFile(manifestPath, content);
        }
    }

    private void updateAppNameInStrings(String appName) {
        CodeProject project = CodeProject.fromMetadata(metadata);
        String stringsPath = project.getResPath() + java.io.File.separator + "values"
            + java.io.File.separator + "strings.xml";
        java.io.File stringsFile = new java.io.File(stringsPath);
        if (stringsFile.exists()) {
            String content = FileUtil.readFile(stringsPath);
            // Escape for Android string resource
            String escaped = appName.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("'", "\\'").replace("\"", "\\\"");
            content = content.replaceFirst(
                "<string name=\"app_name\">[^<]*</string>",
                "<string name=\"app_name\">" + escaped + "</string>"
            );
            FileUtil.writeFile(stringsPath, content);
        }
    }

    /**
     * Validates a Java package name: at least 2 segments separated by dots,
     * each segment starts with a letter followed by letters/digits/underscores.
     */
    private boolean isValidPackageName(String name) {
        if (name == null || name.isEmpty()) return false;
        if (name.startsWith(".") || name.endsWith(".")) return false;
        String[] segments = name.split("\\.");
        if (segments.length < 2) return false;
        for (String segment : segments) {
            if (segment.isEmpty() || !PACKAGE_SEGMENT.matcher(segment).matches()) {
                return false;
            }
        }
        return true;
    }

    private String getMetaString(String key) {
        Object value = metadata.get(key);
        return value != null ? value.toString() : "";
    }
}
