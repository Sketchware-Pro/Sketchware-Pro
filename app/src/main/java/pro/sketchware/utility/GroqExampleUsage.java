package pro.sketchware.utility;

import android.content.Context;
import android.util.Log;

/**
 * Exemplo de uso da funcionalidade da Groq no Sketchware Pro
 * Este arquivo demonstra diferentes maneiras de usar a API da Groq
 */
public class GroqExampleUsage {
    
    private static final String TAG = "GroqExampleUsage";
    
    /**
     * Exemplo 1: Uso básico da GroqErrorExplainer
     */
    public static void exemploBasico(Context context, String errorMessage) {
        GroqErrorExplainer explainer = new GroqErrorExplainer(context);
        
        explainer.explainError(errorMessage, explanation -> {
            if (context instanceof android.app.Activity) {
                ((android.app.Activity) context).runOnUiThread(() -> {
                    Log.d(TAG, "Explicação recebida: " + explanation);
                    // Mostrar a explicação em um dialog
                    new android.app.AlertDialog.Builder(context)
                            .setTitle("🤖 Explicação da IA")
                            .setMessage(explanation)
                            .setPositiveButton("OK", null)
                            .show();
                });
            }
        });
    }
    
    /**
     * Exemplo 2: Uso com contexto adicional
     */
    public static void exemploComContexto(Context context, String errorMessage) {
        GroqErrorExplainer explainer = new GroqErrorExplainer(context);
        
        // Contexto adicional sobre o erro
        String contexto = "Este erro ocorreu durante a compilação de um projeto Android. " +
                         "O usuário estava tentando adicionar uma nova tela ao projeto.";
        
        explainer.explainError(errorMessage, contexto, explanation -> {
            if (context instanceof android.app.Activity) {
                ((android.app.Activity) context).runOnUiThread(() -> {
                    Log.d(TAG, "Explicação recebida: " + explanation);
                    // Processar a explicação...
                });
            }
        });
    }
    
    /**
     * Exemplo 3: Usando ErrorHelper para mostrar erros
     */
    public static void exemploComErrorHelper(Context context, String errorMessage) {
        // Mostrar erro simples
        ErrorHelper.showError(context, errorMessage, "Erro de Compilação");
        
        // Mostrar erro com explicação por IA
        ErrorHelper.showError(context, errorMessage, "Erro de Compilação", true);
        
        // Mostrar erro com confirmação e opção de IA
        ErrorHelper.showErrorWithConfirmation(context, errorMessage, "Erro de Compilação", () -> {
            // Callback executado quando o usuário confirma
            Log.d(TAG, "Usuário confirmou o erro");
        });
    }
    
    /**
     * Exemplo 4: Verificar se a API está disponível
     */
    public static void exemploVerificacaoDisponibilidade(Context context, String errorMessage) {
        if (ErrorHelper.isGroqAvailable(context)) {
            // API disponível, usar explicação por IA
            ErrorHelper.showError(context, errorMessage, "Erro", true);
        } else {
            // API não disponível, mostrar mensagem informativa
            ErrorHelper.showError(context, 
                errorMessage + "\n\n" + ErrorHelper.getGroqNotAvailableMessage(), 
                "Erro");
        }
    }
    
    /**
     * Exemplo 5: Tratamento de erros da API
     */
    public static void exemploTreatmentoErros(Context context, String errorMessage) {
        GroqErrorExplainer explainer = new GroqErrorExplainer(context);
        
        explainer.explainError(errorMessage, explanation -> {
            if (context instanceof android.app.Activity) {
                ((android.app.Activity) context).runOnUiThread(() -> {
                    if (explanation.contains("API key not configured") || 
                        explanation.contains("Failed to get response")) {
                        // Erro na API, mostrar erro original
                        ErrorHelper.showError(context, errorMessage, "Erro");
                    } else {
                        // Sucesso, mostrar explicação da IA
                        new android.app.AlertDialog.Builder(context)
                                .setTitle("🤖 Explicação da IA")
                                .setMessage(explanation)
                                .setPositiveButton("OK", null)
                                .setNegativeButton("Mostrar Original", (dialog, which) -> {
                                    ErrorHelper.showError(context, errorMessage, "Erro Original");
                                })
                                .show();
                    }
                });
            }
        });
    }
    
    /**
     * Exemplo 6: Integração em uma tarefa assíncrona
     */
    public static void exemploIntegracaoAssincrona(Context context, String errorMessage) {
        // Simular uma tarefa assíncrona que pode falhar
        new Thread(() -> {
            try {
                // Simular algum trabalho
                Thread.sleep(1000);
                
                // Simular um erro
                throw new RuntimeException("Erro simulado para teste");
                
            } catch (Exception e) {
                // Quando ocorre um erro, usar a IA para explicar
                if (context instanceof android.app.Activity) {
                    ((android.app.Activity) context).runOnUiThread(() -> {
                        String errorMsg = e.getMessage();
                        if (ErrorHelper.isGroqAvailable(context)) {
                            ErrorHelper.showError(context, errorMsg, "Erro na Tarefa", true);
                        } else {
                            ErrorHelper.showError(context, errorMsg, "Erro na Tarefa");
                        }
                    });
                }
            }
        }).start();
    }
    
    /**
     * Exemplo 7: Personalizar o prompt para a IA
     */
    public static void exemploPromptPersonalizado(Context context, String errorMessage) {
        // Para personalizar o prompt, você pode modificar a classe GroqErrorExplainer
        // ou criar uma subclasse personalizada
        
        GroqErrorExplainer explainer = new GroqErrorExplainer(context);
        
        // Contexto personalizado para a IA
        String contextoPersonalizado = "Este é um erro em um projeto Android criado com Sketchware Pro. " +
                                      "O usuário é iniciante em programação. " +
                                      "Por favor, explique de forma muito simples e didática.";
        
        explainer.explainError(errorMessage, contextoPersonalizado, explanation -> {
            if (context instanceof android.app.Activity) {
                ((android.app.Activity) context).runOnUiThread(() -> {
                    // Mostrar explicação personalizada
                    new android.app.AlertDialog.Builder(context)
                            .setTitle("   Explicação para Iniciantes")
                            .setMessage(explanation)
                            .setPositiveButton("Entendi!", null)
                            .show();
                });
            }
        });
    }
} 