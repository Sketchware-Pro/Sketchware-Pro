package pro.sketchware.utility;

import android.content.Context;
import android.util.Log;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;

/**
 * Example usage of GroqErrorExplainer with Markwon formatting
 * This class demonstrates how to use the AI-powered error explanation feature
 * with proper markdown formatting
 */
public class GroqExampleUsage {
    
    private static final String TAG = "GroqExampleUsage";
    
    /**
     * Example 1: Basic usage with markdown formatting
     */
    public static void basicExample(Context context, String errorMessage) {
        // Use ErrorHelper which now includes Markwon formatting
        ErrorHelper.showError(context, errorMessage, 
                Helper.getResString(R.string.groq_ai_explanation_title), true);
    }
    
    /**
     * Example 2: Usage with additional context and markdown formatting
     */
    public static void advancedExample(Context context, String errorMessage, String additionalContext) {
        GroqErrorExplainer explainer = new GroqErrorExplainer(context);
        
        explainer.explainError(errorMessage, additionalContext, explanation -> {
            if (context instanceof android.app.Activity) {
                ((android.app.Activity) context).runOnUiThread(() -> {
                    Log.d(TAG, "Formatted explanation received");
                    
                    // Use the new ErrorHelper method with markdown formatting
                    ErrorHelper.showAIExplanation(context, explanation, 
                            Helper.getResString(R.string.groq_ai_explanation_title));
                });
            }
        });
    }
    
    /**
     * Example 3: Custom markdown content display
     */
    public static void showCustomMarkdownContent(Context context, String markdownContent) {
        // This demonstrates how to show any markdown content using the new formatting
        ErrorHelper.showAIExplanation(context, markdownContent, "Custom Content");
    }
    
    /**
     * Example 4: Error with confirmation and AI explanation
     */
    public static void errorWithConfirmation(Context context, String errorMessage) {
        ErrorHelper.showErrorWithConfirmation(context, errorMessage, 
                "Compilation Error", () -> {
                    // This runs when user clicks OK
                    Log.d(TAG, "User confirmed error");
                });
    }
    
    /**
     * Example 5: Check if AI is available before using
     */
    public static void conditionalAIUsage(Context context, String errorMessage) {
        if (ErrorHelper.isGroqAvailable(context)) {
            // AI is available, use it
            ErrorHelper.showError(context, errorMessage, "Error Analysis", true);
        } else {
            // AI is not available, show simple error
            ErrorHelper.showError(context, errorMessage, "Error", false);
        }
    }
} 