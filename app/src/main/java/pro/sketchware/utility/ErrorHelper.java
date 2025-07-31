package pro.sketchware.utility;

import android.content.Context;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import io.noties.markwon.Markwon;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;

/**
 * Helper class to easily show error explanations using Groq AI
 * This class provides convenient methods to show AI-powered error explanations
 * throughout the Sketchware Pro application
 */
public class ErrorHelper {
    
    private static final String TAG = "ErrorHelper";
    
    /**
     * Initialize Markwon for markdown rendering
     */
    private static Markwon getMarkwon(Context context) {
        return Markwon.builder(context)
                .build();
    }
    
    /**
     * Render markdown content to Spanned
     */
    private static Spanned renderMarkdown(Context context, String markdownContent) {
        try {
            Markwon markwon = getMarkwon(context);
            return markwon.toMarkdown(markdownContent);
        } catch (Exception e) {
            Log.e(TAG, "Error rendering markdown", e);
            // Return plain text if markdown rendering fails
            return new android.text.SpannedString(markdownContent);
        }
    }
    
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
     * Show an error dialog with AI explanation using Markwon formatting
     */
    private static void showErrorWithAI(Context context, String errorMessage, String title) {
        String dialogTitle = title != null ? title : Helper.getResString(R.string.groq_ai_error_analysis);
        
        // Create a TextView to display formatted content
        TextView messageView = new TextView(context);
        messageView.setPadding(50, 50, 50, 50);
        messageView.setTextIsSelectable(true);
        
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(context)
                .setTitle(dialogTitle)
                .setView(messageView)
                .setPositiveButton(Helper.getResString(R.string.groq_ai_ok), null)
                .setNegativeButton(Helper.getResString(R.string.groq_ai_show_original), null)
                .setCancelable(false);
        
        AlertDialog dialog = dialogBuilder.show();
        
        // Show loading message
        messageView.setText(GroqErrorExplainer.getLoadingMessage());
        
        // Get AI explanation
        GroqErrorExplainer explainer = new GroqErrorExplainer(context);
        
        explainer.explainError(errorMessage, explanation -> {
            // Render the AI response with Markwon
            Spanned formattedExplanation = renderMarkdown(context, explanation);
            messageView.setText(formattedExplanation);
            dialog.setCancelable(true);
        });
        
        // Set up the "Show Original" button with toggle functionality
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(v -> {
            String currentButtonText = dialog.getButton(AlertDialog.BUTTON_NEGATIVE).getText().toString();
            if (currentButtonText.equals(Helper.getResString(R.string.groq_ai_show_original))) {
                // Show original error (plain text)
                messageView.setText(errorMessage);
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setText(Helper.getResString(R.string.groq_ai_explanation));
            } else {
                // Show AI explanation again (formatted)
                explainer.explainError(errorMessage, explanation -> {
                    Spanned formattedExplanation = renderMarkdown(context, explanation);
                    messageView.setText(formattedExplanation);
                });
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setText(Helper.getResString(R.string.groq_ai_show_original));
            }
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
     * Show AI explanation in a custom dialog with markdown formatting
     * @param context The context to show the dialog in
     * @param aiContent The AI-generated content to display
     * @param title The dialog title
     */
    public static void showAIExplanation(Context context, String aiContent, String title) {
        String dialogTitle = title != null ? title : "AI Explanation";
        
        // Create a TextView to display formatted content
        TextView messageView = new TextView(context);
        messageView.setPadding(50, 50, 50, 50);
        messageView.setTextIsSelectable(true);
        
        // Render the AI content with Markwon
        Spanned formattedContent = renderMarkdown(context, aiContent);
        messageView.setText(formattedContent);
        
        new MaterialAlertDialogBuilder(context)
                .setTitle(dialogTitle)
                .setView(messageView)
                .setPositiveButton("OK", null)
                .show();
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
     * Test method to verify Markwon is working
     */
    public static void testMarkwon(Context context) {
        try {
            Markwon markwon = getMarkwon(context);
            String testMarkdown = "**Test** *markdown* content";
            Spanned result = markwon.toMarkdown(testMarkdown);
            Log.d(TAG, "Markwon test successful: " + result.toString());
        } catch (Exception e) {
            Log.e(TAG, "Markwon test failed", e);
        }
    }
    
    /**
     * Get a formatted message when Groq is not available
     */
    public static String getGroqNotAvailableMessage() {
        return GroqErrorExplainer.getNoApiMessage();
    }
} 