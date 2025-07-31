package pro.sketchware.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import pro.sketchware.R;
import pro.sketchware.utility.GroqLayoutGenerator;
import pro.sketchware.utility.SketchwareUtil;

/**
 * Dialog para gerar layouts XML usando IA
 * Permite ao usuário descrever um layout e gerar o código XML automaticamente
 */
public class AiLayoutGeneratorDialog extends Dialog {
    
    private final Context context;
    private final LayoutGenerationCallback callback;
    private final String projectId;
    
    private TextInputEditText promptInput;
    private View progressContainer;
    private MaterialButton generateButton;
    private MaterialButton cancelButton;
    
    private GroqLayoutGenerator layoutGenerator;
    
    /**
     * Interface para callback do resultado da geração
     */
    public interface LayoutGenerationCallback {
        void onLayoutGenerated(String xmlLayout);
        void onError(String errorMessage);
        void onCancelled();
    }
    
    public AiLayoutGeneratorDialog(@NonNull Context context, LayoutGenerationCallback callback) {
        this(context, callback, null);
    }
    
    public AiLayoutGeneratorDialog(@NonNull Context context, LayoutGenerationCallback callback, String projectId) {
        super(context);
        this.context = context;
        this.callback = callback;
        this.projectId = projectId;
        this.layoutGenerator = new GroqLayoutGenerator(context);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Configurar o dialog
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_ai_layout_generator);
        
        // Configurar tamanho da janela
        Window window = getWindow();
        if (window != null) {
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            );
            
            // Aplicar estilos Material Design 3
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.setStatusBarColor(context.getResources().getColor(android.R.color.transparent));
            
            // Configurar animações suaves
            window.setWindowAnimations(R.style.DialogAnimation);
        }
        
        // Inicializar views
        initializeViews();
        setupListeners();
        
        // Verificar se a Groq AI está disponível
        if (!layoutGenerator.isAvailable()) {
            showGroqNotConfiguredDialog();
        }
    }
    
    private void initializeViews() {
        promptInput = findViewById(R.id.prompt_input);
        progressContainer = findViewById(R.id.progress_container);
        generateButton = findViewById(R.id.generate_button);
        cancelButton = findViewById(R.id.cancel_button);
    }
    
    private void setupListeners() {
        generateButton.setOnClickListener(v -> generateLayout());
        cancelButton.setOnClickListener(v -> {
            dismiss();
            if (callback != null) {
                callback.onCancelled();
            }
        });
    }
    
    private void generateLayout() {
        String prompt = promptInput.getText().toString().trim();
        
        if (prompt.isEmpty()) {
            SketchwareUtil.toastError(context.getString(R.string.ai_layout_generator_error_prompt_empty));
            return;
        }
        
        // Mostrar progresso
        showProgress(true);
        
        // Gerar layout com contexto do projeto
        if (projectId != null) {
            layoutGenerator.generateLayoutWithContext(prompt, projectId, new GroqLayoutGenerator.LayoutGenerationCallback() {
                @Override
                public void onSuccess(String xmlLayout) {
                    runOnUiThread(() -> {
                        showProgress(false);
                        dismiss();
                        if (callback != null) {
                            callback.onLayoutGenerated(xmlLayout);
                        }
                    });
                }
                
                @Override
                public void onError(String errorMessage) {
                    runOnUiThread(() -> {
                        showProgress(false);
                        SketchwareUtil.toastError(errorMessage);
                        if (callback != null) {
                            callback.onError(errorMessage);
                        }
                    });
                }
            });
        } else {
            layoutGenerator.generateLayout(prompt, new GroqLayoutGenerator.LayoutGenerationCallback() {
                @Override
                public void onSuccess(String xmlLayout) {
                    runOnUiThread(() -> {
                        showProgress(false);
                        dismiss();
                        if (callback != null) {
                            callback.onLayoutGenerated(xmlLayout);
                        }
                    });
                }
                
                @Override
                public void onError(String errorMessage) {
                    runOnUiThread(() -> {
                        showProgress(false);
                        SketchwareUtil.toastError(errorMessage);
                        if (callback != null) {
                            callback.onError(errorMessage);
                        }
                    });
                }
            });
        }
    }
    
    private void showProgress(boolean show) {
        progressContainer.setVisibility(show ? View.VISIBLE : View.GONE);
        generateButton.setEnabled(!show);
        cancelButton.setEnabled(!show);
        promptInput.setEnabled(!show);
    }
    
    private void showGroqNotConfiguredDialog() {
        new MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.ai_layout_generator_not_configured_title))
            .setMessage(context.getString(R.string.ai_layout_generator_not_configured_message))
            .setPositiveButton(context.getString(R.string.ai_layout_generator_configure), (dialog, which) -> {
                // Abrir configurações da Groq
                openGroqSettings();
                dismiss();
            })
            .setNegativeButton(context.getString(R.string.ai_layout_generator_cancel), (dialog, which) -> {
                dismiss();
                if (callback != null) {
                    callback.onCancelled();
                }
            })
            .setCancelable(false)
            .show();
    }
    
    private void openGroqSettings() {
        try {
            // Tentar abrir a activity de configuração da Groq
            Class<?> groqSettingsClass = Class.forName("pro.sketchware.activities.settings.ManageGroqActivity");
            android.content.Intent intent = new android.content.Intent(context, groqSettingsClass);
            context.startActivity(intent);
        } catch (ClassNotFoundException e) {
            // Se a activity não existir, mostrar mensagem
            SketchwareUtil.toastError(context.getString(R.string.ai_layout_generator_settings_not_found));
        }
    }
    
    private void runOnUiThread(Runnable runnable) {
        if (getWindow() != null && getWindow().getDecorView() != null) {
            getWindow().getDecorView().post(runnable);
        }
    }
} 