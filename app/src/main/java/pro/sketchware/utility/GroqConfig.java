package pro.sketchware.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import pro.sketchware.R;

/**
 * Configuration class for managing the Groq API key
 * Allows saving and retrieving the API key securely
 */
public class GroqConfig {
    
    private static final String PREF_NAME = "groq_config";
    private static final String KEY_API_KEY = "api_key";
    private static final String KEY_ENABLED = "enabled";
    private static final String KEY_RESPONSE_LANGUAGE = "response_language";
    
    // Available languages for AI response
    public static final String LANGUAGE_PORTUGUESE_BRAZIL = "pt-BR";
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_SPANISH = "es";
    public static final String LANGUAGE_FRENCH = "fr";
    public static final String LANGUAGE_GERMAN = "de";
    public static final String LANGUAGE_ITALIAN = "it";
    public static final String LANGUAGE_JAPANESE = "ja";
    public static final String LANGUAGE_KOREAN = "ko";
    public static final String LANGUAGE_CHINESE_SIMPLIFIED = "zh-CN";
    public static final String LANGUAGE_CHINESE_TRADITIONAL = "zh-TW";
    public static final String LANGUAGE_RUSSIAN = "ru";
    public static final String LANGUAGE_ARABIC = "ar";
    public static final String LANGUAGE_HINDI = "hi";
    
    // Default language (English)
    public static final String DEFAULT_LANGUAGE = LANGUAGE_ENGLISH;
    
    /**
     * Saves the Groq API key
     * @param context Application context
     * @param apiKey Groq API key
     */
    public static void saveApiKey(Context context, String apiKey) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_API_KEY, apiKey);
        editor.apply();
    }
    
    /**
     * Retrieves the Groq API key
     * @param context Application context
     * @return API key or null if not configured
     */
    public static String getApiKey(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_API_KEY, null);
    }
    
    /**
     * Checks if the Groq API is enabled
     * @param context Application context
     * @return true if enabled
     */
    public static boolean isEnabled(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_ENABLED, false);
    }
    
    /**
     * Enables or disables the Groq API
     * @param context Application context
     * @param enabled true to enable, false to disable
     */
    public static void setEnabled(Context context, boolean enabled) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_ENABLED, enabled);
        editor.apply();
    }
    
    /**
     * Saves the AI response language
     * @param context Application context
     * @param language Language code (e.g., "pt-BR", "en")
     */
    public static void saveResponseLanguage(Context context, String language) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_RESPONSE_LANGUAGE, language);
        editor.apply();
    }
    
    /**
     * Retrieves the AI response language
     * @param context Application context
     * @return Language code or default language if not configured
     */
    public static String getResponseLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_RESPONSE_LANGUAGE, DEFAULT_LANGUAGE);
    }
    
    /**
     * Gets the language name for display
     * @param context Application context
     * @param languageCode Language code
     * @return Language name in Portuguese
     */
    public static String getLanguageDisplayName(Context context, String languageCode) {
        switch (languageCode) {
            case LANGUAGE_PORTUGUESE_BRAZIL:
                return context.getString(R.string.groq_ai_language_portuguese_brazil);
            case LANGUAGE_ENGLISH:
                return context.getString(R.string.groq_ai_language_english);
            case LANGUAGE_SPANISH:
                return context.getString(R.string.groq_ai_language_spanish);
            case LANGUAGE_FRENCH:
                return context.getString(R.string.groq_ai_language_french);
            case LANGUAGE_GERMAN:
                return context.getString(R.string.groq_ai_language_german);
            case LANGUAGE_ITALIAN:
                return context.getString(R.string.groq_ai_language_italian);
            case LANGUAGE_JAPANESE:
                return context.getString(R.string.groq_ai_language_japanese);
            case LANGUAGE_KOREAN:
                return context.getString(R.string.groq_ai_language_korean);
            case LANGUAGE_CHINESE_SIMPLIFIED:
                return context.getString(R.string.groq_ai_language_chinese_simplified);
            case LANGUAGE_CHINESE_TRADITIONAL:
                return context.getString(R.string.groq_ai_language_chinese_traditional);
            case LANGUAGE_RUSSIAN:
                return context.getString(R.string.groq_ai_language_russian);
            case LANGUAGE_ARABIC:
                return context.getString(R.string.groq_ai_language_arabic);
            case LANGUAGE_HINDI:
                return context.getString(R.string.groq_ai_language_hindi);
            default:
                return context.getString(R.string.groq_ai_language_portuguese_brazil);
        }
    }
    
    /**
     * Gets all available languages
     * @return Array with available language codes
     */
    public static String[] getAvailableLanguages() {
        return new String[]{
            LANGUAGE_PORTUGUESE_BRAZIL,
            LANGUAGE_ENGLISH,
            LANGUAGE_SPANISH,
            LANGUAGE_FRENCH,
            LANGUAGE_GERMAN,
            LANGUAGE_ITALIAN,
            LANGUAGE_JAPANESE,
            LANGUAGE_KOREAN,
            LANGUAGE_CHINESE_SIMPLIFIED,
            LANGUAGE_CHINESE_TRADITIONAL,
            LANGUAGE_RUSSIAN,
            LANGUAGE_ARABIC,
            LANGUAGE_HINDI
        };
    }
    
    /**
     * Gets display names for all available languages
     * @param context Application context
     * @return Array with language names in Portuguese
     */
    public static String[] getAvailableLanguageNames(Context context) {
        String[] languages = getAvailableLanguages();
        String[] names = new String[languages.length];
        for (int i = 0; i < languages.length; i++) {
            names[i] = getLanguageDisplayName(context, languages[i]);
        }
        return names;
    }
    
    /**
     * Checks if the Groq API is configured and available
     * @param context Application context
     * @return true if configured and enabled
     */
    public static boolean isAvailable(Context context) {
        return isEnabled(context) && !TextUtils.isEmpty(getApiKey(context));
    }
    
    /**
     * Clears all Groq configurations
     * @param context Application context
     */
    public static void clearConfig(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
    
    /**
     * Gets the Groq API key securely
     * First tries to get from local configuration, then environment variable
     * @param context Application context
     * @return API key or null if not available
     */
    public static String getApiKeySafely(Context context) {
        // First tries to get from local configuration
        String localApiKey = getApiKey(context);
        if (!TextUtils.isEmpty(localApiKey) && isEnabled(context)) {
            return localApiKey;
        }
        
        // If not configured locally, tries environment variable
        String envApiKey = System.getenv("GROQ_API_KEY");
        if (!TextUtils.isEmpty(envApiKey)) {
            return envApiKey;
        }
        
        return null;
    }
} 