package pro.sketchware.utility;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import pro.sketchware.R;

/**
 * Classe para gerar layouts XML usando prompts da Groq AI
 * Permite criar layouts Android XML através de descrições em linguagem natural
 */
public class GroqLayoutGenerator {
    
    private static final String TAG = "GroqLayoutGenerator";
    private static final String GROQ_API_URL = "https://api.groq.com/openai/v1/chat/completions";
    private static final String MODEL = "llama-3.3-70b-versatile";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    
    private final Context context;
    private final OkHttpClient client;
    private final Gson gson;
    
    public GroqLayoutGenerator(Context context) {
        this.context = context;
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .build();
        this.gson = new Gson();
    }
    
    /**
     * Interface for callback of the generation result
     */
    public interface LayoutGenerationCallback {
        void onSuccess(String xmlLayout);
        void onError(String errorMessage);
    }
    
    /**
     * Generates an XML layout based on a natural language description
     * @param prompt Description of the desired layout
     * @param callback Callback to receive the result
     */
    public void generateLayout(String prompt, LayoutGenerationCallback callback) {
        if (!GroqConfig.isAvailable(context)) {
            callback.onError(context.getString(R.string.ai_layout_generator_error_not_configured));
            return;
        }
        
        String apiKey = GroqConfig.getApiKey(context);
        if (TextUtils.isEmpty(apiKey)) {
            callback.onError(context.getString(R.string.ai_layout_generator_error_api_key));
            return;
        }
        
        // Build complete prompt with specific instructions
        String fullPrompt = buildLayoutPrompt(prompt);
        
        // Execute in separate thread
        CompletableFuture.runAsync(() -> {
            try {
                String xmlLayout = makeApiRequest(apiKey, fullPrompt);
                callback.onSuccess(xmlLayout);
            } catch (Exception e) {
                Log.e(TAG, "Error generating layout", e);
                callback.onError(context.getString(R.string.ai_layout_generator_error_generation, e.getMessage()));
            }
        });
    }
    
    /**
     * Builds the complete prompt with instructions for XML layout generation
     */
    private String buildLayoutPrompt(String userPrompt) {
        String language = GroqConfig.getResponseLanguage(context);
        String languageInstruction = getLanguageInstruction(language);
        
        // Check project configuration
        String projectConfig = getProjectConfiguration();
        
        return String.format(
            "%s\n%s\n\n" +
            "%s\n" +
            "%s\n" +
            "%s\n" +
            "%s\n" +
            "%s\n" +
            "%s\n" +
            "%s\n" +
            "%s\n" +
            "%s\n" +
            "%s\n\n" +
            "%s\n\n" +
            "%s\n\n" +
            "%s\n\n" +
            "%s\n\n" +
            "%s",
            context.getString(R.string.groq_layout_generator_prompt_expert),
            context.getString(R.string.groq_layout_generator_prompt_rules),
            context.getString(R.string.groq_layout_generator_rule_1),
            context.getString(R.string.groq_layout_generator_rule_2),
            context.getString(R.string.groq_layout_generator_rule_3),
            context.getString(R.string.groq_layout_generator_rule_4),
            context.getString(R.string.groq_layout_generator_rule_5),
            context.getString(R.string.groq_layout_generator_rule_6),
            context.getString(R.string.groq_layout_generator_rule_7),
            context.getString(R.string.groq_layout_generator_rule_8),
            context.getString(R.string.groq_layout_generator_rule_9),
            context.getString(R.string.groq_layout_generator_rule_10),
            String.format(context.getString(R.string.groq_layout_generator_prompt_language_instruction), languageInstruction),
            String.format(context.getString(R.string.groq_layout_generator_prompt_project_config), projectConfig),
            String.format(context.getString(R.string.groq_layout_generator_prompt_supported_widgets), getSupportedWidgets()),
            String.format(context.getString(R.string.groq_layout_generator_prompt_description), userPrompt),
            context.getString(R.string.groq_layout_generator_prompt_generate)
        );
    }
    
    /**
     * Gets specific instruction based on configured language
     */
    private String getLanguageInstruction(String language) {
        switch (language) {
            case GroqConfig.LANGUAGE_ENGLISH:
                return context.getString(R.string.groq_language_instruction_english);
            case GroqConfig.LANGUAGE_SPANISH:
                return context.getString(R.string.groq_language_instruction_spanish);
            case GroqConfig.LANGUAGE_FRENCH:
                return context.getString(R.string.groq_language_instruction_french);
            case GroqConfig.LANGUAGE_GERMAN:
                return context.getString(R.string.groq_language_instruction_german);
            case GroqConfig.LANGUAGE_ITALIAN:
                return context.getString(R.string.groq_language_instruction_italian);
            case GroqConfig.LANGUAGE_JAPANESE:
                return context.getString(R.string.groq_language_instruction_japanese);
            case GroqConfig.LANGUAGE_KOREAN:
                return context.getString(R.string.groq_language_instruction_korean);
            case GroqConfig.LANGUAGE_CHINESE_SIMPLIFIED:
                return context.getString(R.string.groq_language_instruction_chinese_simplified);
            case GroqConfig.LANGUAGE_CHINESE_TRADITIONAL:
                return context.getString(R.string.groq_language_instruction_chinese_traditional);
            case GroqConfig.LANGUAGE_RUSSIAN:
                return context.getString(R.string.groq_language_instruction_russian);
            case GroqConfig.LANGUAGE_ARABIC:
                return context.getString(R.string.groq_language_instruction_arabic);
            case GroqConfig.LANGUAGE_HINDI:
                return context.getString(R.string.groq_language_instruction_hindi);
            default: // Portuguese (Brazil)
                return context.getString(R.string.groq_language_instruction_portuguese);
        }
    }
    
    /**
     * Makes the request to the Groq API
     */
    private String makeApiRequest(String apiKey, String prompt) throws IOException {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", MODEL);
        requestBody.addProperty("temperature", 0.3); // Baixa temperatura para respostas mais consistentes
        requestBody.addProperty("max_tokens", 2000);
        
        JsonObject message = new JsonObject();
        message.addProperty("role", "user");
        message.addProperty("content", prompt);
        
        JsonObject[] messages = new JsonObject[]{message};
        requestBody.add("messages", gson.toJsonTree(messages));
        
        RequestBody body = RequestBody.create(requestBody.toString(), JSON);
        Request request = new Request.Builder()
                .url(GROQ_API_URL)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException(String.format(context.getString(R.string.groq_error_api_response), response.code(), response.message()));
            }
            
            String responseBody = response.body().string();
            return parseApiResponse(responseBody);
        }
    }
    
    /**
     * Parses the response from the Groq API
     */
    private String parseApiResponse(String responseBody) throws IOException {
        try {
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
            
            if (jsonResponse.has("error")) {
                JsonObject error = jsonResponse.getAsJsonObject("error");
                String errorMessage = error.has("message") ? error.get("message").getAsString() : context.getString(R.string.groq_error_unknown_api);
                throw new IOException(String.format(context.getString(R.string.groq_error_api_groq), errorMessage));
            }
            
            if (!jsonResponse.has("choices") || jsonResponse.getAsJsonArray("choices").size() == 0) {
                throw new IOException(context.getString(R.string.groq_error_invalid_response));
            }
            
            JsonObject choice = jsonResponse.getAsJsonArray("choices").get(0).getAsJsonObject();
            JsonObject message = choice.getAsJsonObject("message");
            String content = message.get("content").getAsString();
            
            // Clean the content to extract only the XML
            return cleanXmlContent(content);
            
        } catch (Exception e) {
            Log.e(TAG, context.getString(R.string.groq_error_parsing_response), e);
            throw new IOException(String.format(context.getString(R.string.groq_error_processing), e.getMessage()));
        }
    }
    
    /**
     * Cleans the content of the response to extract only the XML
     */
    private String cleanXmlContent(String content) {
        // Remove possible explanations before the XML
        int xmlStart = content.indexOf("<?xml");
        if (xmlStart == -1) {
            xmlStart = content.indexOf("<");
        }
        
        if (xmlStart != -1) {
            content = content.substring(xmlStart);
        }
        
        // Remove possible explanations after the XML
        int xmlEnd = content.lastIndexOf(">");
        if (xmlEnd != -1) {
            content = content.substring(0, xmlEnd + 1);
        }
        
        // Remove extra line breaks and spaces
        content = content.trim();
        
        return content;
    }
    
    /**
     * Checks if the Groq API is available
     */
    public boolean isAvailable() {
        return GroqConfig.isAvailable(context);
    }
    
    /**
     * Tests the connection to the Groq API
     */
    public void testConnection(LayoutGenerationCallback callback) {
        generateLayout("Create a simple layout with a centered TextView", callback);
    }
    
    /**
     * Gets project configuration to include in the prompt
     */
    private String getProjectConfiguration() {
        StringBuilder config = new StringBuilder();
        
        try {
            // Check if AppCompat is enabled
            if (isAppCompatEnabled()) {
                config.append(context.getString(R.string.groq_project_config_appcompat_enabled)).append("\n");
                config.append(context.getString(R.string.groq_project_config_use_appcompat)).append("\n");
            } else {
                config.append(context.getString(R.string.groq_project_config_appcompat_disabled)).append("\n");
                config.append(context.getString(R.string.groq_project_config_avoid_appcompat)).append("\n");
            }
            
            // Check if Material 3 is enabled
            if (isMaterial3Enabled()) {
                config.append(context.getString(R.string.groq_project_config_material3_enabled)).append("\n");
                config.append(context.getString(R.string.groq_project_config_use_material3)).append("\n");
            } else {
                config.append(context.getString(R.string.groq_project_config_material3_disabled)).append("\n");
                config.append(context.getString(R.string.groq_project_config_avoid_material3)).append("\n");
            }
            
            // Check minimum Android version
            int minSdk = getMinSdkVersion();
            config.append(String.format(context.getString(R.string.groq_project_config_min_sdk), minSdk)).append("\n");
            
            // Check target Android version
            int targetSdk = getTargetSdkVersion();
            config.append(String.format(context.getString(R.string.groq_project_config_target_sdk), targetSdk)).append("\n");
            
            // Add recommendations based on minimum version
            if (minSdk >= 21) {
                config.append("• Suporte a Material Design completo\n");
                config.append("• Pode usar CardView, RecyclerView, etc.\n");
            } else {
                config.append("• Versão Android antiga - Use componentes básicos\n");
                config.append("• Evite componentes que requerem API 21+\n");
            }
            
            if (minSdk >= 23) {
                config.append("• Suporte a permissões em tempo de execução\n");
            }
            
            if (minSdk >= 26) {
                config.append("• Suporte a notificações com canais\n");
            }
            
            // Check if it's a Sketchware project
            config.append("• Projeto Sketchware - Use componentes suportados pela plataforma\n");
            config.append("• Mantenha layouts simples e eficientes\n");
            config.append("• Use IDs descritivos e únicos\n");
            
        } catch (Exception e) {
            Log.w(TAG, "Erro ao obter configurações do projeto", e);
            config.append("• Configurações do projeto não disponíveis\n");
            config.append("• Use componentes Android padrão para máxima compatibilidade\n");
        }
        
        return config.toString();
    }
    
    /**
     * Gets the list of widgets and components supported by Sketchware
     */
    private String getSupportedWidgets() {
        return 
            "Layouts:\n" +
            "• LinearLayout (horizontal/vertical)\n" +
            "• RelativeLayout\n" +
            "• ConstraintLayout\n" +
            "• ScrollView (horizontal/vertical)\n" +
            "• GridLayout\n" +
            "• FrameLayout\n" +
            "• CardView\n" +
            "• NestedScrollView\n\n" +
            
            "Basic Widgets:\n" +
            "• TextView (simple text)\n" +
            "• EditText (input field)\n" +
            "• Button (simple button)\n" +
            "• ImageView (image)\n" +
            "• ImageButton (button with image)\n" +
            "• CheckBox (checkbox)\n" +
            "• RadioButton (option button)\n" +
            "• Switch (toggle switch)\n" +
            "• ToggleButton (toggle button)\n" +
            "• SeekBar (progress bar)\n" +
            "• ProgressBar (progress indicator)\n" +
            "• RatingBar (rating)\n" +
            "• Spinner (dropdown list)\n" +
            "• ListView (list)\n" +
            "• RecyclerView (recyclable list)\n" +
            "• WebView (web browser)\n" +
            "• VideoView (video viewer)\n" +
            "• MapView (map)\n" +
            "• AdView (advertisement)\n\n" +
            
            "Material Design Components:\n" +
            "• MaterialButton (material button)\n" +
            "• TextInputLayout (material input field)\n" +
            "• FloatingActionButton (floating action button)\n" +
            "• BottomNavigationView (bottom navigation)\n" +
            "• TabLayout (tabs)\n" +
            "• Toolbar (toolbar)\n" +
            "• AppBarLayout (app bar)\n" +
            "• CollapsingToolbarLayout (collapsible toolbar)\n" +
            "• NavigationView (side navigation)\n" +
            "• Chip (chip)\n" +
            "• ChipGroup (chip group)\n" +
            "• Snackbar (floating message)\n" +
            "• BottomSheet (bottom sheet)\n" +
            "• SwipeRefreshLayout (swipe-to-refresh)\n\n" +
            
            "Important Attributes:\n" +
            "• android:id - Unique required ID\n" +
            "• android:layout_width/layout_height - Dimensions\n" +
            "• android:layout_margin - External margins\n" +
            "• android:padding - Internal padding\n" +
            "• android:background - Color/background\n" +
            "• android:text - Text\n" +
            "• android:textColor - Text color\n" +
            "• android:textSize - Text size\n" +
            "• android:visibility - Visibility\n" +
            "• android:enabled - Enabled/disabled\n" +
            "• android:clickable - Clickable\n" +
            "• android:focusable - Focusable\n" +
            "• android:contentDescription - Description for accessibility\n\n" +
            
            "Recommended Dimensions:\n" +
            "• Text: 12sp, 14sp, 16sp, 18sp, 20sp, 24sp\n" +
            "• Margins/Padding: 8dp, 16dp, 24dp, 32dp\n" +
            "• Button height: 48dp (Material Design)\n" +
            "• Field height: 56dp (Material Design)\n" +
            "• Corner radius: 4dp, 8dp, 16dp\n\n" +
            
            "• buttons must have white text and the text is centered in the center\n\n" +
            "• never use @string\n\n" +
            
            "Material Design Colors:\n" +
            "• ?attr/colorPrimary - Primary color\n" +
            "• ?attr/colorPrimaryVariant - Primary variant\n" +
            "• ?attr/colorSecondary - Secondary color\n" +
            "• ?attr/colorSurface - Surface color\n" +
            "• ?attr/colorOnSurface - On surface color\n" +
            "• ?attr/colorError - Error color\n" +
            "• @android:color/white - White\n" +
            "• @android:color/black - Black\n" +
            "• @android:color/transparent - Transparent";
    }
    
    /**
     * Checks if AppCompat is enabled
     */
    private boolean isAppCompatEnabled() {
        try {
            // Try to check if AppCompat is available
            Class.forName("androidx.appcompat.app.AppCompatActivity");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    /**
     * Checks if Material 3 is enabled
     */
    private boolean isMaterial3Enabled() {
        try {
            // Try to check if Material 3 components are available
            Class.forName("com.google.android.material.button.MaterialButton");
            Class.forName("com.google.android.material.textfield.TextInputLayout");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    /**
     * Gets the minimum SDK version
     */
    private int getMinSdkVersion() {
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .applicationInfo
                    .minSdkVersion;
        } catch (Exception e) {
            return 21; // Default value
        }
    }
    
    /**
     * Gets the target SDK version
     */
    private int getTargetSdkVersion() {
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .applicationInfo
                    .targetSdkVersion;
        } catch (Exception e) {
            return 33; // Default value
        }
    }
    
    /**
     * Gets specific project information for the current Sketchware project
     */
    private String getSketchwareProjectInfo() {
        StringBuilder info = new StringBuilder();
        
        try {
            // Try to get project information
            // This may vary depending on how Sketchware stores information
            
            info.append("Sketchware Project Information:\n");
            info.append("• Use native Android components for maximum compatibility\n");
            info.append("• Keep layouts simple and efficient\n");
            info.append("• never use strings for text or buttons\n");
            info.append("• Use descriptive IDs (e.g., login_button, email_field)\n");
            info.append("• Avoid complex or nested layouts\n");
            info.append("• Use dimensions in dp for compatibility\n");
            info.append("• Include contentDescription for accessibility\n");
            info.append("• Use theme colors when available\n");
            info.append("• Maintain visual consistency\n");
            
        } catch (Exception e) {
            Log.w(TAG, "Error getting Sketchware project information", e);
            info.append("• Specific project information not available\n");
        }
        
        return info.toString();
    }
    
    /**
     * Generates a more specific prompt based on the Sketchware context
     */
    public void generateLayoutWithContext(String prompt, String projectId, LayoutGenerationCallback callback) {
        // Add specific project context
        String contextualPrompt = String.format(
            "Sketchware Project Context:\n" +
            "• Project ID: %s\n" +
            "• Platform: Sketchware-Pro\n" +
            "• Focus: Simple and efficient layouts\n" +
            "• Compatibility: Maximum\n\n" +
            "Layout Description: %s",
            projectId != null ? projectId : "N/A",
            prompt
        );
        
        generateLayout(contextualPrompt, callback);
    }
} 