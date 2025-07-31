package pro.sketchware.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import pro.sketchware.R;
import pro.sketchware.activities.settings.ManageGroqActivity;
import pro.sketchware.utility.GroqLayoutGenerator;
import pro.sketchware.utility.SketchwareUtil;

/**
 * BottomSheet Dialog to generate XML layouts using AI
 * Allows the user to describe a layout and generate the XML code automatically
 * Occupies the full screen when opened
 */
public class AiLayoutGeneratorDialog extends BottomSheetDialog {
    
    private final Context context;
    private final LayoutGenerationCallback callback;
    private final String projectId;
    
    private TextInputEditText promptInput;
    private View progressContainer;
    private MaterialButton generateButton;
    private MaterialButton cancelButton;
    
    private GroqLayoutGenerator layoutGenerator;
    
    /**
     * Interface for callback of the generation result
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
        
        // Configure the BottomSheet to occupy full screen
        setContentView(R.layout.dialog_ai_layout_generator);
        
        // Set the BottomSheet to expand to full screen
        getBehavior().setState(com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED);
        getBehavior().setSkipCollapsed(true);
        getBehavior().setDraggable(true);
        
        // Initialize views
        initializeViews();
        setupListeners();
        
        // Check if Groq AI is available
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
        
        // Show progress
        showProgress(true);
        
        // Generate layout with project context
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
                .setTitle(R.string.ai_layout_generator_title)
                .setMessage(R.string.ai_layout_generator_groq_not_configured)
                .setPositiveButton(R.string.ai_layout_generator_configure_groq, (dialog, which) -> {
                    // Open Groq settings
                    openGroqSettings();
                })
                .setNegativeButton(R.string.common_word_cancel, null)
                .show();
    }
    
    private void openGroqSettings() {
        try {
            // Try to open the Groq settings activity
            Intent intent = new Intent(context, ManageGroqActivity.class);
            context.startActivity(intent);
        } catch (Exception e) {
            // If the activity doesn't exist, show message
            SketchwareUtil.toastError(context.getString(R.string.ai_layout_generator_error_settings_not_found));
        }
    }
    
    private void runOnUiThread(Runnable runnable) {
        if (getWindow() != null && getWindow().getDecorView() != null) {
            getWindow().getDecorView().post(runnable);
        } else {
            // Fallback for BottomSheetDialog
            runnable.run();
        }
    }
} 