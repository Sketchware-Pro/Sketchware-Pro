package ide.sketchware.codeproject.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

import java.io.File;
import java.util.HashMap;

import a.a.a.lC;
import ide.sketchware.R;
import ide.sketchware.databinding.ActivityProjectSettingsBinding;

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
        if (packageName.isEmpty() || !packageName.contains(".")) {
            binding.inputPackageName.setError(getString(R.string.code_project_error_package_name));
            return;
        }
        if (versionCode.isEmpty()) {
            binding.inputVersionCode.setError(getString(R.string.code_project_error_version_code));
            return;
        }
        if (versionName.isEmpty()) {
            binding.inputVersionName.setError(getString(R.string.code_project_error_version_name));
            return;
        }

        // Update metadata
        metadata.put("my_app_name", appName);
        metadata.put("my_sc_pkg_name", packageName);
        metadata.put("sc_ver_code", versionCode);
        metadata.put("sc_ver_name", versionName);
        metadata.put("sc_min_sdk", minSdk);

        // Save via lC
        lC.a(scId, metadata);

        Toast.makeText(this, R.string.code_project_settings_saved, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }

    private String getMetaString(String key) {
        Object value = metadata.get(key);
        return value != null ? value.toString() : "";
    }
}
