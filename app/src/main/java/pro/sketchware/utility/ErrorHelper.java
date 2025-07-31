package pro.sketchware.utility;

import android.content.Context;
import android.util.Log;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * Helper class to easily show error explanations using Groq AI
 * This class provides convenient methods to show AI-powered error explanations
 * throughout the Sketchware Pro application
 */
public class ErrorHelper {
    
    private static final String TAG = "ErrorHelper";
    
    /**
     * Show an error with optional AI explanation
     * @param context The context to show the dialog in
     * @param errorMessage The error message to display
     * @param title The dialog title (optional)
     */
    public static void showError(Context context, String errorMessage, String title) {
        showError(context, errorMessage, title, false);
    }
    
    /**
     * Show an error with optional AI explanation
     * @param context The context to show the dialog in
     * @param errorMessage The error message to display
     * @param title The dialog title (optional)
     * @param useAI Whether to use AI explanation
     */
    public static void showError(Context context, String errorMessage, String title, boolean useAI) {
        if (useAI) {
            showErrorWithAI(context, errorMessage, title);
        } else {
            showSimpleError(context, errorMessage, title);
        }
    }
    
    /**
     * Show a simple error dialog without AI
     */
    private static void showSimpleError(Context context, String errorMessage, String title) {
        String dialogTitle = title != null ? title : "Error";
        
        new MaterialAlertDialogBuilder(context)
                .setTitle(dialogTitle)
                .setMessage(errorMessage)
                .setPositiveButton("OK", null)
                .show();
    }
    
    /**
     * Show an error dialog with AI explanation
     */
    private static void showErrorWithAI(Context context, String errorMessage, String title) {
        String dialogTitle = title != null ? title : "🤖 AI Error Analysis";
        
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle(dialogTitle)
                .setMessage(GroqErrorExplainer.getLoadingMessage())
                .setPositiveButton("OK", null)
                .setNegativeButton("Show Original", null)
                .setCancelable(false);
        
        var dialog = dialogBuilder.show();
        
        // Get AI explanation
        GroqErrorExplainer explainer = new GroqErrorExplainer(context);
        
        explainer.explainError(errorMessage, explanation -> {
            dialog.setMessage(explanation);
            dialog.setCancelable(true);
        });
        
        // Set up the "Show Original" button
        dialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> {
            dialog.setMessage(errorMessage);
        });
    }
    
    /**
     * Show a confirmation dialog with AI explanation option
     * @param context The context to show the dialog in
     * @param errorMessage The error message
     * @param title The dialog title
     * @param onConfirm Callback when user confirms
     */
    public static void showErrorWithConfirmation(Context context, String errorMessage, String title, 
                                               Runnable onConfirm) {
        
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle(title)
                .setMessage(errorMessage)
                .setPositiveButton("OK", (dialog, which) -> {
                    if (onConfirm != null) {
                        onConfirm.run();
                    }
                });
        
        // Only add AI explanation button if API key is available
        if (GroqConfig.isAvailable(context)) {
            dialogBuilder.setNeutralButton("   AI Explanation", (dialog, which) -> {
                showErrorWithAI(context, errorMessage, title);
            });
        }
        
        dialogBuilder.show();
    }
    
    /**
     * Check if Groq API is available
     * @return true if Groq is configured and enabled
     */
    public static boolean isGroqAvailable(Context context) {
        return GroqConfig.isAvailable(context);
    }
    
    /**
     * Check if Groq API is available (legacy method)
     * @return true if GROQ_API_KEY is set
     */
    public static boolean isGroqAvailable() {
        String apiKey = System.getenv("GROQ_API_KEY");
        return apiKey != null && !apiKey.isEmpty();
    }
    
    /**
     * Get a formatted message when Groq is not available
     */
    public static String getGroqNotAvailableMessage() {
        return GroqErrorExplainer.getNoApiMessage();
    }
} 