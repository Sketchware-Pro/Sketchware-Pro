package pro.sketchware.codeproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.besome.sketch.lib.base.BaseAppCompatActivity;

import java.util.HashMap;
import java.util.regex.Pattern;

import a.a.a.lC;
import pro.sketchware.R;
import pro.sketchware.codeproject.model.CodeProject;
import pro.sketchware.codeproject.template.CodeProjectTemplate;
import pro.sketchware.databinding.ActivityCreateCodeProjectBinding;

public class CreateCodeProjectActivity extends BaseAppCompatActivity {

    private static final Pattern PACKAGE_PATTERN = Pattern.compile("^[a-z][a-z0-9]*(\\.[a-z][a-z0-9]*)+$");

    private ActivityCreateCodeProjectBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateCodeProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.btnCreate.setOnClickListener(v -> createProject());
    }

    private void createProject() {
        String appName = getText(binding.inputAppName);
        String packageName = getText(binding.inputPackageName);
        String projectName = getText(binding.inputProjectName);

        if (TextUtils.isEmpty(appName)) {
            binding.inputAppName.setError(getString(R.string.code_project_error_app_name));
            return;
        }
        if (TextUtils.isEmpty(packageName) || !PACKAGE_PATTERN.matcher(packageName).matches()) {
            binding.inputPackageName.setError(getString(R.string.code_project_error_package_name));
            return;
        }
        if (TextUtils.isEmpty(projectName)) {
            binding.inputProjectName.setError(getString(R.string.code_project_error_project_name));
            return;
        }

        String scId = lC.b();

        HashMap<String, Object> metadata = new HashMap<>();
        metadata.put("sc_id", scId);
        metadata.put("my_ws_name", projectName);
        metadata.put("my_sc_pkg_name", packageName);
        metadata.put("my_app_name", appName);
        metadata.put("sc_ver_code", "1");
        metadata.put("sc_ver_name", "1.0");
        metadata.put("sketchware_ver", 150);
        metadata.put(CodeProject.KEY_PROJECT_TYPE, CodeProject.PROJECT_TYPE_CODE);

        lC.a(scId, metadata);

        CodeProject project = CodeProject.fromMetadata(metadata);
        CodeProjectTemplate.generate(project);

        Intent intent = new Intent(this, CodeProjectActivity.class);
        intent.putExtra("sc_id", scId);
        startActivity(intent);

        Intent resultData = new Intent();
        resultData.putExtra("sc_id", scId);
        setResult(RESULT_OK, resultData);
        finish();
    }

    private String getText(com.google.android.material.textfield.TextInputEditText editText) {
        if (editText.getText() == null) return "";
        return editText.getText().toString().trim();
    }
}
