package pro.sketchware.ai;

import java.util.function.Consumer;

public interface AIService {
    void chat(String prompt, Consumer<String> onSuccess, Consumer<Throwable> onError);
    void chatWithHistory(java.util.List<pro.sketchware.ai.ui.adapter.ChatMessage> history, Consumer<String> onSuccess, Consumer<Throwable> onError);
    void generateBlock(String prompt, Consumer<String> onSuccess, Consumer<Throwable> onError);
    void setSystemContext(String context);
}
