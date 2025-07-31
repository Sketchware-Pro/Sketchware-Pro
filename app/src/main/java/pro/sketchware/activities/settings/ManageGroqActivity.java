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
 * Atividade para gerenciar as configurações da API da Groq
 * Permite ao usuário configurar sua chave da API da Groq para explicações de erro
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
        
        getSupportActionBar().setTitle("Groq AI Settings");
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
        tvApiKey.setText("API KEY");
        
        tvDesc = findViewById(R.id.tv_desc);
        tvDesc.setText("Groq AI pode explicar erros de forma inteligente. " +
                      "Você deve inserir a chave da API para usar esta funcionalidade.");
        
        tvEnable = findViewById(R.id.tv_enable);
        tvEnable.setText("Habilitar Groq AI");
        
        tvResponseLanguage = findViewById(R.id.tv_response_language);
        tvResponseLanguage.setText(R.string.groq_ai_response_language);
        
        spinnerLanguage = findViewById(R.id.spinner_language);
        setupLanguageSpinner();
        
        btnOpenDoc = findViewById(R.id.btn_open_doc);
        btnOpenDoc.setText("Abrir Documentação");
        btnOpenDoc.setOnClickListener(this);
        
        btnTest = findViewById(R.id.btn_test);
        btnTest.setText("Testar Conexão");
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
        
        // Definir a seleção atual baseada na configuração salva
        int selectedIndex = getLanguageIndex(selectedLanguage);
        spinnerLanguage.setSelection(selectedIndex);
        
        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = availableLanguages[position];
            }
            
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Não fazer nada
            }
        });
    }
    
    private int getLanguageIndex(String languageCode) {
        for (int i = 0; i < availableLanguages.length; i++) {
            if (availableLanguages[i].equals(languageCode)) {
                return i;
            }
        }
        return 0; // Retorna o primeiro item (Português do Brasil) como padrão
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
            SketchwareUtil.toast("A chave da API não pode estar vazia!", Toast.LENGTH_SHORT);
            return;
        }
        
        // Salvar configurações
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
            SketchwareUtil.toast("Verifique sua conexão com a internet", Toast.LENGTH_SHORT);
        }
    }
    
    private void showBrowserDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle("Navegador Compatível Necessário");
        dialog.setMessage("Para abrir a documentação da Groq, você precisa de um navegador compatível como o Chrome.");
        dialog.setPositiveButton("OK", null);
        dialog.show();
    }
    
    private void showApiKeyRequiredDialog() {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle("Chave da API Necessária");
        dialog.setMessage("Para habilitar a Groq AI, você precisa inserir uma chave da API válida. " +
                         "Você pode obter uma chave gratuita em console.groq.com");
        dialog.setPositiveButton("OK", null);
        dialog.setNegativeButton("Cancelar", (dialogInterface, which) -> {
            libSwitch.setChecked(false);
        });
        dialog.show();
    }
    
    private void testConnection() {
        String apiKey = Helper.getText(editApiKey);
        
        if (TextUtils.isEmpty(apiKey)) {
            SketchwareUtil.toast("Insira uma chave da API primeiro", Toast.LENGTH_SHORT);
            return;
        }
        
        // Mostrar loading
        btnTest.setEnabled(false);
        btnTest.setText("Testando...");
        
        // Testar a conexão com a API da Groq
        pro.sketchware.utility.GroqErrorExplainer explainer = 
            new pro.sketchware.utility.GroqErrorExplainer(apiKey);
        
        explainer.explainError("Test connection", "This is a test to verify the API connection.", 
            result -> {
                runOnUiThread(() -> {
                    btnTest.setEnabled(true);
                    btnTest.setText("Testar Conexão");
                    
                    if (result.contains("API key not configured") || 
                        result.contains("Failed to get response")) {
                        showTestResultDialog(false, "Falha na conexão. Verifique sua chave da API e conexão com a internet.");
                    } else {
                        showTestResultDialog(true, "Conexão bem-sucedida! A API da Groq está funcionando corretamente.");
                    }
                });
            });
    }
    
    private void showTestResultDialog(boolean success, String message) {
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(this);
        dialog.setTitle(success ? "✅ Sucesso" : "❌ Erro");
        dialog.setMessage(message);
        dialog.setPositiveButton("OK", null);
        dialog.show();
    }
} 