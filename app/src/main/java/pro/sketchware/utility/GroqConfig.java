package pro.sketchware.utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import pro.sketchware.R;

/**
 * Classe de configuração para gerenciar a chave da API da Groq
 * Permite salvar e recuperar a chave da API de forma segura
 */
public class GroqConfig {
    
    private static final String PREF_NAME = "groq_config";
    private static final String KEY_API_KEY = "api_key";
    private static final String KEY_ENABLED = "enabled";
    private static final String KEY_RESPONSE_LANGUAGE = "response_language";
    
    // Idiomas disponíveis para resposta da IA
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
    
    // Idioma padrão (Português do Brasil)
    public static final String DEFAULT_LANGUAGE = LANGUAGE_PORTUGUESE_BRAZIL;
    
    /**
     * Salva a chave da API da Groq
     * @param context Contexto da aplicação
     * @param apiKey Chave da API da Groq
     */
    public static void saveApiKey(Context context, String apiKey) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_API_KEY, apiKey);
        editor.apply();
    }
    
    /**
     * Recupera a chave da API da Groq
     * @param context Contexto da aplicação
     * @return Chave da API ou null se não estiver configurada
     */
    public static String getApiKey(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_API_KEY, null);
    }
    
    /**
     * Verifica se a API da Groq está habilitada
     * @param context Contexto da aplicação
     * @return true se estiver habilitada
     */
    public static boolean isEnabled(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_ENABLED, false);
    }
    
    /**
     * Habilita ou desabilita a API da Groq
     * @param context Contexto da aplicação
     * @param enabled true para habilitar, false para desabilitar
     */
    public static void setEnabled(Context context, boolean enabled) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_ENABLED, enabled);
        editor.apply();
    }
    
    /**
     * Salva o idioma de resposta da IA
     * @param context Contexto da aplicação
     * @param language Código do idioma (ex: "pt-BR", "en")
     */
    public static void saveResponseLanguage(Context context, String language) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_RESPONSE_LANGUAGE, language);
        editor.apply();
    }
    
    /**
     * Recupera o idioma de resposta da IA
     * @param context Contexto da aplicação
     * @return Código do idioma ou o idioma padrão se não estiver configurado
     */
    public static String getResponseLanguage(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_RESPONSE_LANGUAGE, DEFAULT_LANGUAGE);
    }
    
    /**
     * Obtém o nome do idioma para exibição
     * @param context Contexto da aplicação
     * @param languageCode Código do idioma
     * @return Nome do idioma em português
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
     * Obtém todos os idiomas disponíveis
     * @return Array com os códigos dos idiomas disponíveis
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
     * Obtém os nomes de exibição de todos os idiomas disponíveis
     * @param context Contexto da aplicação
     * @return Array com os nomes dos idiomas em português
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
     * Verifica se a API da Groq está configurada e disponível
     * @param context Contexto da aplicação
     * @return true se estiver configurada e habilitada
     */
    public static boolean isAvailable(Context context) {
        return isEnabled(context) && !TextUtils.isEmpty(getApiKey(context));
    }
    
    /**
     * Limpa todas as configurações da Groq
     * @param context Contexto da aplicação
     */
    public static void clearConfig(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }
    
    /**
     * Obtém a chave da API da Groq de forma segura
     * Primeiro tenta obter da configuração local, depois da variável de ambiente
     * @param context Contexto da aplicação
     * @return Chave da API ou null se não estiver disponível
     */
    public static String getApiKeySafely(Context context) {
        // Primeiro tenta obter da configuração local
        String localApiKey = getApiKey(context);
        if (!TextUtils.isEmpty(localApiKey) && isEnabled(context)) {
            return localApiKey;
        }
        
        // Se não estiver configurada localmente, tenta a variável de ambiente
        String envApiKey = System.getenv("GROQ_API_KEY");
        if (!TextUtils.isEmpty(envApiKey)) {
            return envApiKey;
        }
        
        return null;
    }
} 