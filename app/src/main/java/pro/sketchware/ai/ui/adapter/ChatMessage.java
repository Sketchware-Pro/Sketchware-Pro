package pro.sketchware.ai.ui.adapter;

import java.util.Map;

public class ChatMessage {
    public enum Type {
        TEXT,
        PROJECT_CARD,
        FIX_PROPOSAL,
        CODE_SNIPPET
    }

    private String content;
    private boolean isUser;
    private Type type;
    private Map<String, String> cardData;
    private String imagePath;
    private String imageBase64;
    private boolean isThinking;
    private boolean isContinuation;

    public ChatMessage(String content, boolean isUser) {
        this(content, isUser, Type.TEXT, null);
    }
    
    // ... constructors ...

    public void setContinuation(boolean continuation) { isContinuation = continuation; }
    public boolean isContinuation() { return isContinuation; }

    public boolean isThinking() { return isThinking; }
    
    public ChatMessage(String content, boolean isUser, String imagePath) {
         this(content, isUser, Type.TEXT, null);
         this.imagePath = imagePath;
    }

    public ChatMessage(String content, boolean isUser, String imagePath, String imageBase64) {
         this(content, isUser, Type.TEXT, null);
         this.imagePath = imagePath;
         this.imageBase64 = imageBase64;
    }
    
    public ChatMessage(String content, boolean isUser, boolean isThinking) {
        this.content = content;
        this.isUser = isUser;
        this.type = Type.TEXT;    
        this.cardData = null;
        this.isThinking = isThinking;
    }

    public ChatMessage(String content, boolean isUser, Type type, Map<String, String> cardData) {
        this.content = content;
        this.isUser = isUser;
        this.type = type;
        this.cardData = cardData;
        this.isThinking = false;
    }
    

    public void setThinking(boolean thinking) { isThinking = thinking; }

    public String getContent() { return content; }
    public boolean isUser() { return isUser; }
    public Type getType() { return type; }
    public Map<String, String> getCardData() { return cardData; }
    public String getImagePath() { return imagePath; }
    public String getImageBase64() { return imageBase64; }
}
