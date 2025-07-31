package pro.sketchware.activities.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.switchmaterial.SwitchMaterial;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.utility.GroqConfig;
import pro.sketchware.utility.SketchwareUtil;

/**
 * Activity to manage Groq API settings
 * Allows the user to configure their Groq API key for error explanations
 */
public class ManageGroqActivity extends BaseAppCompatActivity implements View.OnClickListener {
    
    private SwitchMaterial libSwitch;
    private EditText editApiKey;
    private TextView tvApiKey;
    private TextView tvDesc;
    private TextView tvEnable;
    private TextView tvResponseLanguage;
    private Spinner spinnerLanguage;
    private Button btnOpenDoc;
    private Button btnTest;
    private LinearLayout switchLayout;
    
    private String[] availableLanguages;
    private String[] availableLanguageNames;
    private String selectedLanguage;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_library_manage_groq);
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        
        getSupportActionBar().setTitle(Helper.getResString(R.string.groq_ai_settings));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        
        initializeData();
        initializeViews();
        configure();
    }
    
    private void initializeData() {
        availableLanguages = GroqConfig.getAvailableLanguages();
        availableLanguageNames = GroqConfig.getAvailableLanguageNames(this);
        selectedLanguage = GroqConfig.getResponseLanguage(this);
    }
    
    private void initializeViews() {
        switchLayout = findViewById(R.id.layout_switch);
        switchLayout.setOnClickListener(this);
        
        libSwitch = findViewById(R.id.lib_switch);
        editApiKey = findViewById(R.id.ed_api_key);
        
        tvApiKey = findViewById(R.id.tv_api_key);
        tvApiKey.setText(Helper.getResString(R.string.groq_ai_api_key));
        
        tvDesc = findViewById(R.id.tv_desc);
        tvDesc.setText(Helper.getResString(R.string.groq_ai_description));
        
        tvEnable = findViewById(R.id.tv_enable);
        tvEnable.setText(Helper.getResString(R.string.groq_ai_enable));
        
        tvResponseLanguage = findViewById(R.id.tv_response_language);
        tvResponseLanguage.setText(Helper.getResString(R.string.groq_ai_response_language));
        
        spinnerLanguage = findViewById(R.id.spinner_language);
        setupLanguageSpinner();
        
        btnOpenDoc = findViewById(R.id.btn_open_doc);
        btnOpenDoc.setText(Helper.getResString(R.string.groq_ai_open_docs));
        btnOpenDoc.setOnClickListener(this);
        
        btnTest = findViewById(R.id.btn_test);
        btnTest.setText(Helper.getResString(R.string.groq_ai_test_connection));
        btnTest.setOnClickListener(this);
    }
    
    private void setupLanguageSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_item,
            availableLanguageNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapter);
        
        // Set current selection based on saved configuration
        int selectedIndex = getLanguageIndex(selectedLanguage);
        spinnerLanguage.setSelection(selectedIndex);
        
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = availableLanguages[position];
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }
    
    private int getLanguageIndex(String languageCode) {
        for (int i = 0; i < availableLanguages.length; i++) {
            if (availableLanguages[i].equals(languageCode)) {
                return i;
            }
        }
        return 0; // Returns the first item (English) as default
    }
    
    private void configure() {
        boolean isEnabled = GroqConfig.isEnabled(this);
        String apiKey = GroqConfig.getApiKey(this);
        
        libSwitch.setChecked(isEnabled);
        editApiKey.setText(apiKey);
    }
    
    @Override
    public void onBackPressed() {
        String apiKey = Helper.getText(editApiKey);
        boolean isEnabled = libSwitch.isChecked();
        
        if (isEnabled && TextUtils.isEmpty(apiKey)) {
            SketchwareUtil.toast("API key cannot be empty!", Toast.LENGTH_SHORT);
            return;
        }
        
        // Save configurations
        GroqConfig.setEnabled(this, isEnabled);
        GroqConfig.saveApiKey(this, apiKey);
        GroqConfig.saveResponseLanguage(this, selectedLanguage);
        
        super.onBackPressed();
    }
    
    @Override
    public void onClick(View v) {
        int id = v.getId();
        
        if (id == R.id.btn_open_doc) {
            openDocumentation();
        } else if (id == R.id.btn_test) {
            testConnection();
        } else if (id == R.id.layout_switch) {
            libSwitch.setChecked(!libSwitch.isChecked());
            
            if (libSwitch.isChecked() && TextUtils.isEmpty(Helper.getText(editApiKey))) {
                showApiKeyRequiredDialog();
            }
        }
    }
    
    private void openDocumentation() {
        if (SketchwareUtil.isConnected()) {
            try {
                Uri documentationUrl = Uri.parse("https://console.groq.com/keys");
                Intent openDocIntent = new Intent(Intent.ACTION_VIEW);
                openDocIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                openDocIntent.setData(documentationUrl);
                startActivity(openDocIntent);
            } catch (Exception e) {
                e.printStackTrace();
                showBrowserDialog();
            }
        } else {
            SketchwareUtil.toast(Helper.getResString(R.string.groq_ai_check_internet), Toast.LENGTH_SHORT);
        }
    }
    
    private void showBrowserDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle(Helper.getResString(R.string.groq_ai_browser_required));
        dialog.setMessage(Helper.getResString(R.string.groq_ai_browser_required_message));
        dialog.setPositiveButton(Helper.getResString(R.string.groq_ai_ok), null);
        dialog.show();
    }
    
    private void showApiKeyRequiredDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle(Helper.getResString(R.string.groq_ai_api_key_required));
        dialog.setMessage(Helper.getResString(R.string.groq_ai_api_key_required_message));
        dialog.setPositiveButton(Helper.getResString(R.string.groq_ai_ok), null);
        dialog.setNegativeButton(Helper.getResString(R.string.groq_ai_cancel), (dialogInterface, which) -> {
            libSwitch.setChecked(false);
        });
        dialog.show();
    }
    
    private void testConnection() {
        String apiKey = Helper.getText(editApiKey);
        
        if (TextUtils.isEmpty(apiKey)) {
            SketchwareUtil.toast(Helper.getResString(R.string.groq_ai_enter_api_key_first), Toast.LENGTH_SHORT);
            return;
        }
        
        // Show loading
        btnTest.setEnabled(false);
        btnTest.setText(Helper.getResString(R.string.groq_ai_testing));
        
        // Test the connection to the Groq API
        pro.sketchware.utility.GroqErrorExplainer explainer = 
            new pro.sketchware.utility.GroqErrorExplainer(apiKey);
        
        explainer.explainError("Test connection", "This is a test to verify the API connection.", 
            result -> {
                runOnUiThread(() -> {
                    btnTest.setEnabled(true);
                    btnTest.setText(Helper.getResString(R.string.groq_ai_test_connection));
                    
                    if (result.contains("API key not configured") || 
                        result.contains("Failed to get response")) {
                        showTestResultDialog(false, Helper.getResString(R.string.groq_ai_connection_failed));
                    } else {
                        showTestResultDialog(true, Helper.getResString(R.string.groq_ai_connection_success));
                    }
                });
            });
    }
    
    private void showTestResultDialog(boolean success, String message) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle(success ? Helper.getResString(R.string.groq_ai_success) : Helper.getResString(R.string.groq_ai_error));
        dialog.setMessage(message);
        dialog.setPositiveButton(Helper.getResString(R.string.groq_ai_ok), null);
        dialog.show();
    }
} 