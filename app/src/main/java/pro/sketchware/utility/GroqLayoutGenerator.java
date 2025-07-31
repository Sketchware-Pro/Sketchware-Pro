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
     * Interface para callback do resultado da geração
     */
    public interface LayoutGenerationCallback {
        void onSuccess(String xmlLayout);
        void onError(String errorMessage);
    }
    
    /**
     * Gera um layout XML baseado em uma descrição em linguagem natural
     * @param prompt Descrição do layout desejado
     * @param callback Callback para receber o resultado
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
        
        // Construir o prompt completo com instruções específicas
        String fullPrompt = buildLayoutPrompt(prompt);
        
        // Executar em thread separada
        CompletableFuture.runAsync(() -> {
            try {
                String xmlLayout = makeApiRequest(apiKey, fullPrompt);
                callback.onSuccess(xmlLayout);
            } catch (Exception e) {
                Log.e(TAG, "Erro ao gerar layout", e);
                callback.onError(context.getString(R.string.ai_layout_generator_error_generation, e.getMessage()));
            }
        });
    }
    
    /**
     * Constrói o prompt completo com instruções para geração de layout XML
     */
    private String buildLayoutPrompt(String userPrompt) {
        String language = GroqConfig.getResponseLanguage(context);
        String languageInstruction = getLanguageInstruction(language);
        
        // Verificar configurações do projeto
        String projectConfig = getProjectConfiguration();
        
        return String.format(
            "Você é um especialista em desenvolvimento Android e XML layouts. " +
            "Gere apenas o código XML do layout Android baseado na descrição fornecida. " +
            "Siga estas regras estritamente:\n\n" +
            "1. Gere APENAS o código XML, sem explicações ou comentários\n" +
            "2. Use LinearLayout, RelativeLayout, ConstraintLayout ou outros layouts Android padrão\n" +
            "3. Inclua IDs únicos para todos os elementos (ex: android:id=\"@+id/button1\")\n" +
            "4. Use dimensões apropriadas (dp, sp, match_parent, wrap_content)\n" +
            "5. Inclua margens e padding quando apropriado\n" +
            "6. Use cores do Material Design quando especificado\n" +
            "7. Inclua atributos de acessibilidade (contentDescription)\n" +
            "8. Use widgets Android padrão (TextView, Button, EditText, ImageView, etc.)\n" +
            "9. Se mencionar Material Design, use componentes do Material Design\n" +
            "10. O layout deve ser responsivo e seguir as melhores práticas Android\n\n" +
            "%s\n\n" +
            "Configurações do Projeto:\n%s\n\n" +
            "Widgets e Componentes Suportados:\n%s\n\n" +
            "Descrição do layout: %s\n\n" +
            "Gere o XML do layout:",
            languageInstruction,
            projectConfig,
            getSupportedWidgets(),
            userPrompt
        );
    }
    
    /**
     * Obtém instrução específica baseada no idioma configurado
     */
    private String getLanguageInstruction(String language) {
        switch (language) {
            case GroqConfig.LANGUAGE_ENGLISH:
                return "Respond in English. Generate only the XML code.";
            case GroqConfig.LANGUAGE_SPANISH:
                return "Responde en español. Genera solo el código XML.";
            case GroqConfig.LANGUAGE_FRENCH:
                return "Réponds en français. Génère uniquement le code XML.";
            case GroqConfig.LANGUAGE_GERMAN:
                return "Antworte auf Deutsch. Generiere nur den XML-Code.";
            case GroqConfig.LANGUAGE_ITALIAN:
                return "Rispondi in italiano. Genera solo il codice XML.";
            case GroqConfig.LANGUAGE_JAPANESE:
                return "日本語で回答してください。XMLコードのみを生成してください。";
            case GroqConfig.LANGUAGE_KOREAN:
                return "한국어로 답변하세요. XML 코드만 생성하세요.";
            case GroqConfig.LANGUAGE_CHINESE_SIMPLIFIED:
                return "用中文回答。只生成XML代码。";
            case GroqConfig.LANGUAGE_CHINESE_TRADITIONAL:
                return "用繁體中文回答。只生成XML代碼。";
            case GroqConfig.LANGUAGE_RUSSIAN:
                return "Отвечайте на русском языке. Генерируйте только XML-код.";
            case GroqConfig.LANGUAGE_ARABIC:
                return "أجب باللغة العربية. أنشئ كود XML فقط.";
            case GroqConfig.LANGUAGE_HINDI:
                return "हिंदी में जवाब दें। केवल XML कोड उत्पन्न करें।";
            default: // Portuguese (Brazil)
                return "Responda em português. Gere apenas o código XML.";
        }
    }
    
    /**
     * Faz a requisição para a API da Groq
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
                throw new IOException("Erro na API: " + response.code() + " " + response.message());
            }
            
            String responseBody = response.body().string();
            return parseApiResponse(responseBody);
        }
    }
    
    /**
     * Faz o parsing da resposta da API da Groq
     */
    private String parseApiResponse(String responseBody) throws IOException {
        try {
            JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
            
            if (jsonResponse.has("error")) {
                JsonObject error = jsonResponse.getAsJsonObject("error");
                String errorMessage = error.has("message") ? error.get("message").getAsString() : "Erro desconhecido da API";
                throw new IOException("Erro da API Groq: " + errorMessage);
            }
            
            if (!jsonResponse.has("choices") || jsonResponse.getAsJsonArray("choices").size() == 0) {
                throw new IOException("Resposta inválida da API: nenhuma escolha encontrada");
            }
            
            JsonObject choice = jsonResponse.getAsJsonArray("choices").get(0).getAsJsonObject();
            JsonObject message = choice.getAsJsonObject("message");
            String content = message.get("content").getAsString();
            
            // Limpar o conteúdo para extrair apenas o XML
            return cleanXmlContent(content);
            
        } catch (Exception e) {
            Log.e(TAG, "Erro ao fazer parsing da resposta", e);
            throw new IOException("Erro ao processar resposta da API: " + e.getMessage());
        }
    }
    
    /**
     * Limpa o conteúdo da resposta para extrair apenas o XML
     */
    private String cleanXmlContent(String content) {
        // Remove possíveis explicações antes do XML
        int xmlStart = content.indexOf("<?xml");
        if (xmlStart == -1) {
            xmlStart = content.indexOf("<");
        }
        
        if (xmlStart != -1) {
            content = content.substring(xmlStart);
        }
        
        // Remove possíveis explicações após o XML
        int xmlEnd = content.lastIndexOf(">");
        if (xmlEnd != -1) {
            content = content.substring(0, xmlEnd + 1);
        }
        
        // Remove quebras de linha extras e espaços
        content = content.trim();
        
        return content;
    }
    
    /**
     * Verifica se a API da Groq está disponível
     */
    public boolean isAvailable() {
        return GroqConfig.isAvailable(context);
    }
    
    /**
     * Testa a conexão com a API da Groq
     */
    public void testConnection(LayoutGenerationCallback callback) {
        generateLayout("Crie um layout simples com um TextView centralizado", callback);
    }
    
    /**
     * Obtém as configurações do projeto para incluir no prompt
     */
    private String getProjectConfiguration() {
        StringBuilder config = new StringBuilder();
        
        try {
            // Verificar se AppCompat está ativo
            if (isAppCompatEnabled()) {
                config.append("• AppCompat está habilitado - Use componentes compatíveis\n");
                config.append("• Suporte a temas e cores do Material Design\n");
                config.append("• Compatibilidade com versões antigas do Android\n");
                config.append("• Use androidx.appcompat.widget.* quando apropriado\n");
            } else {
                config.append("• AppCompat não está habilitado - Use componentes Android padrão\n");
                config.append("• Evite componentes que requerem AppCompat\n");
            }
            
            // Verificar se Material 3 está ativo
            if (isMaterial3Enabled()) {
                config.append("• Material 3 está habilitado - Use componentes Material 3\n");
                config.append("• Suporte a temas dinâmicos e cores adaptativas\n");
                config.append("• Componentes modernos do Material Design\n");
                config.append("• Use com.google.android.material.* quando apropriado\n");
            } else {
                config.append("• Material 3 não está habilitado - Use Material Design 1\n");
                config.append("• Evite componentes Material 3 específicos\n");
            }
            
            // Verificar versão mínima do Android
            int minSdk = getMinSdkVersion();
            config.append(String.format("• Versão mínima do Android: API %d\n", minSdk));
            
            // Verificar versão alvo do Android
            int targetSdk = getTargetSdkVersion();
            config.append(String.format("• Versão alvo do Android: API %d\n", targetSdk));
            
            // Adicionar recomendações baseadas na versão mínima
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
            
            // Verificar se é um projeto Sketchware
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
     * Obtém a lista de widgets e componentes suportados pelo Sketchware
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
            
            "Widgets Básicos:\n" +
            "• TextView (texto simples)\n" +
            "• EditText (campo de entrada)\n" +
            "• Button (botão simples)\n" +
            "• ImageView (imagem)\n" +
            "• ImageButton (botão com imagem)\n" +
            "• CheckBox (caixa de seleção)\n" +
            "• RadioButton (botão de opção)\n" +
            "• Switch (interruptor)\n" +
            "• ToggleButton (botão alternável)\n" +
            "• SeekBar (barra de progresso)\n" +
            "• ProgressBar (indicador de progresso)\n" +
            "• RatingBar (avaliação)\n" +
            "• Spinner (lista suspensa)\n" +
            "• ListView (lista)\n" +
            "• RecyclerView (lista reciclável)\n" +
            "• WebView (navegador web)\n" +
            "• VideoView (visualizador de vídeo)\n" +
            "• MapView (mapa)\n" +
            "• AdView (anúncio)\n\n" +
            
            "Componentes Material Design:\n" +
            "• MaterialButton (botão material)\n" +
            "• TextInputLayout (campo de entrada material)\n" +
            "• FloatingActionButton (botão flutuante)\n" +
            "• BottomNavigationView (navegação inferior)\n" +
            "• TabLayout (abas)\n" +
            "• Toolbar (barra de ferramentas)\n" +
            "• AppBarLayout (barra de aplicativo)\n" +
            "• CollapsingToolbarLayout (barra colapsável)\n" +
            "• NavigationView (navegação lateral)\n" +
            "• Chip (chip)\n" +
            "• ChipGroup (grupo de chips)\n" +
            "• Snackbar (mensagem flutuante)\n" +
            "• BottomSheet (folha inferior)\n" +
            "• SwipeRefreshLayout (atualização por gesto)\n\n" +
            
            "Atributos Importantes:\n" +
            "• android:id - ID único obrigatório\n" +
            "• android:layout_width/layout_height - Dimensões\n" +
            "• android:layout_margin - Margens externas\n" +
            "• android:padding - Preenchimento interno\n" +
            "• android:background - Cor/fundo\n" +
            "• android:text - Texto\n" +
            "• android:textColor - Cor do texto\n" +
            "• android:textSize - Tamanho do texto\n" +
            "• android:visibility - Visibilidade\n" +
            "• android:enabled - Habilitado/desabilitado\n" +
            "• android:clickable - Clicável\n" +
            "• android:focusable - Focável\n" +
            "• android:contentDescription - Descrição para acessibilidade\n\n" +
            
            "Dimensões Recomendadas:\n" +
            "• Texto: 12sp, 14sp, 16sp, 18sp, 20sp, 24sp\n" +
            "• Margens/Padding: 8dp, 16dp, 24dp, 32dp\n" +
            "• Altura de botões: 48dp (Material Design)\n" +
            "• Altura de campos: 56dp (Material Design)\n" +
            "• Raio de borda: 4dp, 8dp, 16dp\n\n" +
            
            "• botoes tem que ter texto branco e o texto fica centralizado no centro\n\n" +
            "• nunca use @string\n\n" +
            
            "Cores Material Design:\n" +
            "• ?attr/colorPrimary - Cor primária\n" +
            "• ?attr/colorPrimaryVariant - Variante primária\n" +
            "• ?attr/colorSecondary - Cor secundária\n" +
            "• ?attr/colorSurface - Cor de superfície\n" +
            "• ?attr/colorOnSurface - Cor sobre superfície\n" +
            "• ?attr/colorError - Cor de erro\n" +
            "• @android:color/white - Branco\n" +
            "• @android:color/black - Preto\n" +
            "• @android:color/transparent - Transparente";
    }
    
    /**
     * Verifica se o AppCompat está habilitado
     */
    private boolean isAppCompatEnabled() {
        try {
            // Tentar verificar se a biblioteca AppCompat está disponível
            Class.forName("androidx.appcompat.app.AppCompatActivity");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    /**
     * Verifica se o Material 3 está habilitado
     */
    private boolean isMaterial3Enabled() {
        try {
            // Tentar verificar se componentes do Material 3 estão disponíveis
            Class.forName("com.google.android.material.button.MaterialButton");
            Class.forName("com.google.android.material.textfield.TextInputLayout");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
    
    /**
     * Obtém a versão mínima do SDK
     */
    private int getMinSdkVersion() {
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .applicationInfo
                    .minSdkVersion;
        } catch (Exception e) {
            return 21; // Valor padrão
        }
    }
    
    /**
     * Obtém a versão alvo do SDK
     */
    private int getTargetSdkVersion() {
        try {
            return context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0)
                    .applicationInfo
                    .targetSdkVersion;
        } catch (Exception e) {
            return 33; // Valor padrão
        }
    }
    
    /**
     * Obtém informações específicas do projeto Sketchware atual
     */
    private String getSketchwareProjectInfo() {
        StringBuilder info = new StringBuilder();
        
        try {
            // Tentar obter informações do projeto atual
            // Isso pode variar dependendo de como o Sketchware armazena as informações
            
            info.append("Informações do Projeto Sketchware:\n");
            info.append("• Use componentes nativos do Android para máxima compatibilidade\n");
            info.append("• Mantenha layouts simples e eficientes\n");
            info.append("• nunca use strings para textos ou botoes\n");
            info.append("• Use IDs descritivos (ex: login_button, email_field)\n");
            info.append("• Evite layouts muito complexos ou aninhados\n");
            info.append("• Use dimensões em dp para compatibilidade\n");
            info.append("• Inclua contentDescription para acessibilidade\n");
            info.append("• Use cores do tema quando disponível\n");
            info.append("• Mantenha consistência visual\n");
            
        } catch (Exception e) {
            Log.w(TAG, "Erro ao obter informações do projeto Sketchware", e);
            info.append("• Informações específicas do projeto não disponíveis\n");
        }
        
        return info.toString();
    }
    
    /**
     * Gera um prompt mais específico baseado no contexto do Sketchware
     */
    public void generateLayoutWithContext(String prompt, String projectId, LayoutGenerationCallback callback) {
        // Adicionar contexto específico do projeto
        String contextualPrompt = String.format(
            "Contexto do Projeto Sketchware:\n" +
            "• ID do Projeto: %s\n" +
            "• Plataforma: Sketchware-Pro\n" +
            "• Foco: Layouts simples e eficientes\n" +
            "• Compatibilidade: Máxima\n\n" +
            "Descrição do Layout: %s",
            projectId != null ? projectId : "N/A",
            prompt
        );
        
        generateLayout(contextualPrompt, callback);
    }
} 