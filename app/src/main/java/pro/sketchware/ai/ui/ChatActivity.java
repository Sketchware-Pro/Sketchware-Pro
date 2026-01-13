package pro.sketchware.ai.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pro.sketchware.R;
import pro.sketchware.ai.AIService;
import pro.sketchware.ai.QwenService;
import pro.sketchware.ai.ProjectBuilder;
import pro.sketchware.ai.ui.adapter.ChatAdapter;
import pro.sketchware.ai.ui.adapter.ChatMessage;

public class ChatActivity extends AppCompatActivity {

    private ChatAdapter adapter;
    private android.widget.ListPopupWindow projectPopupWindow;
    private java.util.List<java.util.HashMap<String, Object>> projectList;
    
    // Restored member variables
    private EditText inputField;
    private ProgressBar loadingIndicator;
    private AIService aiService;
    private View btnSend;

    private androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    private RecyclerView historyRecyclerView;
    private HistoryAdapter historyAdapter;
    private java.util.List<ChatSession> sessions = new java.util.ArrayList<>();
    private String currentSessionId;
    
    private androidx.activity.result.ActivityResultLauncher<String> imagePickerLauncher;
    private android.net.Uri selectedImageUri;
    
    // Internal class for Session Data
    private static class ChatSession {
        String id;
        String title;
        long timestamp;
        
        public ChatSession(String id, String title, long timestamp) {
            this.id = id;
            this.title = title;
            this.timestamp = timestamp;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // UI Initialization
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        
        findViewById(R.id.btn_history).setOnClickListener(v -> {
             if (drawerLayout.isDrawerOpen(android.view.Gravity.START)) {
                 drawerLayout.closeDrawer(android.view.Gravity.START);
             } else {
                 loadSessions(); // Refresh list
                 drawerLayout.openDrawer(android.view.Gravity.START);
             }
        });
        
        findViewById(R.id.btn_new_chat).setOnClickListener(v -> startNewChat());
        findViewById(R.id.btn_upload_image).setOnClickListener(v -> imagePickerLauncher.launch("image/*"));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        loadingIndicator = findViewById(R.id.loading_indicator);
        inputField = findViewById(R.id.input_field);
        btnSend = findViewById(R.id.btn_send);
        btnSend.setOnClickListener(v -> sendMessage());
        
        // Image Preview Views
        View imagePreviewContainer = findViewById(R.id.image_preview_container);
        ImageView imagePreview = findViewById(R.id.image_preview);
        findViewById(R.id.btn_remove_image).setOnClickListener(v -> {
            selectedImageUri = null;
            imagePreviewContainer.setVisibility(View.GONE);
        });
        
        historyRecyclerView = findViewById(R.id.history_recycler_view);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        historyAdapter = new HistoryAdapter();
        historyRecyclerView.setAdapter(historyAdapter);

        // Setup Chat RecyclerView
        adapter = new ChatAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Setup AI Service
        aiService = new QwenService();

        // Image Picker Initialization
        imagePickerLauncher = registerForActivityResult(
            new androidx.activity.result.contract.ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    selectedImageUri = uri;
                    imagePreview.setImageURI(uri);
                    imagePreviewContainer.setVisibility(View.VISIBLE);
                    inputField.requestFocus();
                }
            }
        );
        
        // Handle Context
        String initialMessage = "Hello! I am your Sketchware AI Assistant. How can I help?";
        String scId = null;
        if (getIntent().hasExtra("sc_id")) {
            scId = getIntent().getStringExtra("sc_id");
            updateContextWithProject(scId, false); 
            java.util.HashMap<String, Object> metadata = a.a.a.lC.b(scId);
            if (metadata != null) {
                 String projectName = a.a.a.yB.c(metadata, "my_ws_name");
                 initialMessage = "Hello! Working on '" + projectName + "'. How can I help?";
            }
        }
        
        setupProjectMention();

        // Load correct session
        if (getIntent().hasExtra("error_log")) {
            startNewChat(); // Force new chat for error reporting context
            String errorLog = getIntent().getStringExtra("error_log");
            
            // Try to extract file from log
            if (scId != null) {
                suspectedErrorFile = extractFileFromLog(errorLog, scId);
            }
            
            String prompt = "I encountered the following error:\n\n```\n" + errorLog + "\n```";
            inputField.setText(prompt);
            sendMessage();
        } else {
             // Default to Start New Chat as requested
             loadSessions();
             startNewChat();
             adapter.addMessage(new ChatMessage(initialMessage, false));
        }
    }
    
    private void startNewChat() {
        // Save current if exists (handled by onPause/onSwitch)
        saveHistory();
        
        currentSessionId = java.util.UUID.randomUUID().toString();
        adapter.getMessages().clear();
        adapter.notifyDataSetChanged();
        
        // Add default welcome if empty
        // adapter.addMessage(new ChatMessage("Start a new conversation...", false)); // Removed redundant welcome
        
        // Create session entry
        ChatSession newSession = new ChatSession(currentSessionId, "New Chat", System.currentTimeMillis());
        sessions.add(0, newSession);
        saveSessions();
        
        // Refresh history list to show "New Chat" at top
        if (historyAdapter != null) historyAdapter.notifyDataSetChanged();
        
        drawerLayout.closeDrawer(android.view.Gravity.START);
    }
    
    private void deleteSession(int position) {
        if (position >= 0 && position < sessions.size()) {
            ChatSession session = sessions.get(position);
            
            // Delete file
            java.io.File file = new java.io.File(getFilesDir(), "chat_" + session.id + ".json");
            if (file.exists()) file.delete();
            
            sessions.remove(position);
            saveSessions();
            historyAdapter.notifyItemRemoved(position);
            
            // If current session deleted, start new
            if (session.id.equals(currentSessionId)) {
                startNewChat();
                adapter.addMessage(new ChatMessage("Previous session deleted. Started new chat.", false));
            }
        }
    }
    
    private void loadSession(String sessionId) {
        saveHistory(); // Save current before switching
        
        currentSessionId = sessionId;
        adapter.getMessages().clear();
        
        try {
            java.io.File file = new java.io.File(getFilesDir(), "chat_" + sessionId + ".json");
            if (file.exists()) {
                StringBuilder sb = new StringBuilder();
                try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) sb.append(line);
                }
                
                java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<java.util.List<ChatMessage>>(){}.getType();
                java.util.List<ChatMessage> history = new com.google.gson.Gson().fromJson(sb.toString(), type);
                
                if (history != null) {
                    for (ChatMessage msg : history) adapter.addMessage(msg);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        
        adapter.notifyDataSetChanged();
        drawerLayout.closeDrawer(android.view.Gravity.START);
    }

    private void loadSessions() {
        try {
            java.io.File file = new java.io.File(getFilesDir(), "chat_sessions.json");
            if (file.exists()) {
                StringBuilder sb = new StringBuilder();
                try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) sb.append(line);
                }
                 java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<java.util.List<ChatSession>>(){}.getType();
                 sessions = new com.google.gson.Gson().fromJson(sb.toString(), type);
                 if (sessions == null) sessions = new java.util.ArrayList<>();
                 
                 // Sort by timestamp desc
                 sessions.sort((s1, s2) -> Long.compare(s2.timestamp, s1.timestamp));
                 
                 historyAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    private void saveSessions() {
        try {
            java.io.File file = new java.io.File(getFilesDir(), "chat_sessions.json");
            String json = new com.google.gson.Gson().toJson(sessions);
             try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
                writer.write(json);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void saveHistory() {
        if (currentSessionId == null) return;
        
        try {
            // Update session title based on first user message if needed
            for (ChatSession s : sessions) {
                if (s.id.equals(currentSessionId)) {
                    s.timestamp = System.currentTimeMillis();
                    // Simple title logic
                    for (ChatMessage m : adapter.getMessages()) {
                        if (m.isUser()) {
                            String content = m.getContent();
                            s.title = content.length() > 30 ? content.substring(0, 30) + "..." : content;
                            break;
                        }
                    }
                    if (s.title.equals("New Chat") && !adapter.getMessages().isEmpty()) {
                         // Fallback title if no user message yet
                         s.title = "Chat " + new java.text.SimpleDateFormat("HH:mm").format(new java.util.Date());
                    }
                    break;
                }
            }
            saveSessions(); // Save metadata logic

            java.io.File file = new java.io.File(getFilesDir(), "chat_" + currentSessionId + ".json");
            String json = new com.google.gson.Gson().toJson(adapter.getMessages());
            try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
                writer.write(json);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Inner Adapter for History
    private class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
        class ViewHolder extends RecyclerView.ViewHolder {
            TextView title, date;
            ViewHolder(View v) {
                super(v);
                title = v.findViewById(R.id.history_title);
                date = v.findViewById(R.id.history_date);
            }
        }

        @androidx.annotation.NonNull
        @Override
        public ViewHolder onCreateViewHolder(@androidx.annotation.NonNull android.view.ViewGroup parent, int viewType) {
            View v = android.view.LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_history, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@androidx.annotation.NonNull ViewHolder holder, int position) {
            ChatSession session = sessions.get(position);
            holder.title.setText(session.title);
            holder.date.setText(new java.text.SimpleDateFormat("MMM dd, HH:mm").format(new java.util.Date(session.timestamp)));
            holder.itemView.setOnClickListener(v -> loadSession(session.id));
            
            holder.itemView.setOnLongClickListener(v -> {
                new androidx.appcompat.app.AlertDialog.Builder(ChatActivity.this)
                    .setTitle("Delete Chat")
                    .setMessage("Are you sure you want to delete this conversation?")
                    .setPositiveButton("Delete", (dialog, which) -> deleteSession(position))
                    .setNegativeButton("Cancel", null)
                    .show();
                return true;
            });
        }

        @Override
        public int getItemCount() { return sessions.size(); }
    }
    
    // ... existing setupProjectMention, updateContextWithProject, sendMessage, processAIResponse ...
    private void setupProjectMention() {
        projectList = a.a.a.lC.a(); // Get all projects
        projectPopupWindow = new android.widget.ListPopupWindow(this);
        projectPopupWindow.setAnchorView(inputField);
        
        // Create simple adapter for the popup
        projectPopupWindow.setAdapter(new android.widget.ArrayAdapter<java.util.HashMap<String, Object>>(this, R.layout.item_project_mention, projectList) {
            @androidx.annotation.NonNull
            @Override
            public View getView(int position, View convertView, @androidx.annotation.NonNull android.view.ViewGroup parent) {
                if (convertView == null) {
                    convertView = android.view.LayoutInflater.from(getContext()).inflate(R.layout.item_project_mention, parent, false);
                }
                java.util.HashMap<String, Object> item = getItem(position);
                TextView name = convertView.findViewById(R.id.project_name);
                TextView id = convertView.findViewById(R.id.project_id);
                
                String projectName = a.a.a.yB.c(item, "my_ws_name");
                String scId = a.a.a.yB.c(item, "sc_id");
                
                name.setText(projectName);
                id.setText("ID: " + scId);
                return convertView;
            }
        });

        projectPopupWindow.setOnItemClickListener((parent, view, position, id) -> {
            java.util.HashMap<String, Object> selectedProject = projectList.get(position);
            String scId = a.a.a.yB.c(selectedProject, "sc_id");
            String projectName = a.a.a.yB.c(selectedProject, "my_ws_name");
            
            // Replace @ with specific text or just append context
            String currentText = inputField.getText().toString();
            int atIndex = currentText.lastIndexOf("@");
            if (atIndex != -1) {
                String newText = currentText.substring(0, atIndex) + "@" + scId + " (" + projectName + ") ";
                inputField.setText(newText);
                inputField.setSelection(newText.length());
            }
            
            updateContextWithProject(scId, true);
            projectPopupWindow.dismiss();
        });

        inputField.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (text.endsWith("@")) {
                    projectPopupWindow.show();
                } else if (!projectPopupWindow.isShowing() && text.contains("@")) {
                     // Provide more robust logic later (e.g., if user deleted space and is back at @)
                }
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });
    }

    private void updateContextWithProject(String sc_id, boolean notifyUser) {
        java.util.HashMap<String, Object> metadata = a.a.a.lC.b(sc_id);
        if (metadata != null) {
            String projectName = a.a.a.yB.c(metadata, "my_ws_name");
            String packageName = a.a.a.yB.c(metadata, "my_sc_pkg_name");
            
            String context = "Current Project Context: Name=" + projectName + ", Package=" + packageName + ", SC_ID=" + sc_id + ".";
            aiService.setSystemContext(context);
            adapter.setProjectContext(sc_id);
            
            if (notifyUser) {
                Toast.makeText(this, "Context set to: " + projectName, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveHistory();
    }

    // Removed broken loadHistory and replaced with loadSessions logic above

    private void sendMessage() {
        String text = inputField.getText().toString().trim();
        if (text.isEmpty() && selectedImageUri == null) return;

        inputField.setText("");
        inputField.setHint("Ask anything..."); // Reset hint
        
        ChatMessage userMessage;
        if (selectedImageUri != null) {
             String base64 = uriToBase64(selectedImageUri);
             userMessage = new ChatMessage(text, true, selectedImageUri.toString(), base64);
             selectedImageUri = null; // Clear after sending
             findViewById(R.id.image_preview_container).setVisibility(View.GONE);
        } else {
             userMessage = new ChatMessage(text, true);
        }
        
        adapter.addMessage(userMessage);
        
        // Add Thinking Message
        ChatMessage thinkingMessage = new ChatMessage("Thinking...", false, true);
        adapter.addMessage(thinkingMessage);

        // Scroll to bottom
        ((RecyclerView) findViewById(R.id.recycler_view)).smoothScrollToPosition(adapter.getItemCount() - 1);

        // Use chatWithHistory to maintain context
        aiService.chatWithHistory(adapter.getMessages(), response -> {
            // Remove thinking message
            adapter.removeMessage(thinkingMessage);
            
            processAIResponse(response);
            ((RecyclerView) findViewById(R.id.recycler_view)).smoothScrollToPosition(adapter.getItemCount() - 1);
            
            // Save history after response so effective title and content is saved
            saveHistory(); // Auto-save after response
            
        }, error -> {
            // Remove thinking message
            adapter.removeMessage(thinkingMessage);
            
            Toast.makeText(this, "AI Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        });
    }
    
    private String uriToBase64(android.net.Uri uri) {
        try {
            java.io.InputStream inputStream = getContentResolver().openInputStream(uri);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes); // Warning: simple read, might not read full stream if large
            // Better to use ByteArrayOutputStream
            // Use ByteArrayOutputStream for safety
            // java.io.ByteArrayOutputStream byteBuffer = new java.io.ByteArrayOutputStream();
            // int bufferSize = 1024;
            // byte[] buffer = new byte[bufferSize];
            // int len = 0;
            // while ((len = inputStream.read(buffer)) != -1) {
            //     byteBuffer.write(buffer, 0, len);
            // }
            // return android.util.Base64.encodeToString(byteBuffer.toByteArray(), android.util.Base64.NO_WRAP);
            
            // Simpler for now for small images, but let's do it right:
             java.io.ByteArrayOutputStream byteBuffer = new java.io.ByteArrayOutputStream();
             int bufferSize = 1024;
             byte[] buffer = new byte[bufferSize];
             int len = 0;
             while ((len = inputStream.read(buffer)) != -1) {
                 byteBuffer.write(buffer, 0, len);
             }
             return "data:image/jpeg;base64," + android.util.Base64.encodeToString(byteBuffer.toByteArray(), android.util.Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String suspectedErrorFile;

    // ... (rest of class) ...

    private String extractFileFromLog(String log, String scId) {
        if (scId == null) return null;
        
        // Find all potential .java filenames in the log
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\b([A-Za-z0-9_$]+\\.java)\\b");
        java.util.regex.Matcher m = p.matcher(log);
        
        // Use wq.d() for 'mysc' (source code)
        String projectPath = a.a.a.wq.d(scId);
        java.io.File projectDir = new java.io.File(projectPath);
        if (!projectDir.exists()) return null;
        
        while (m.find()) {
            String candidate = m.group(1);
            // Check if this file actually exists in the project
            // We need the recursive finder here too.
            // Since we can't easily access the Adapter's private one, let's just do a quick breadth-first search or simple walk.
            // Or just trust the first one that looks user-ish?
            // "Activity.java" is android. "MainActivity.java" is user.
            
            if (isFileInProject(projectDir, candidate)) {
                return candidate;
            }
        }
        return null;
    }
    
    // Simple recursive check
    private boolean isFileInProject(java.io.File dir, String name) {
        if (!dir.isDirectory()) return false;
        java.io.File[] files = dir.listFiles();
        if (files == null) return false;
        
        for (java.io.File f : files) {
             if (f.isDirectory()) {
                 if (f.getName().equals("build") || f.getName().equals(".gradle")) continue;
                 if (isFileInProject(f, name)) return true;
             } else {
                 if (f.getName().equals(name)) return true;
             }
        }
        return false;
    }

    private void processAIResponse(String response) {
        try {
            String jsonString = response.trim();
            if (jsonString.startsWith("```json")) {
                jsonString = jsonString.substring(7);
            } else if (jsonString.startsWith("```")) {
                jsonString = jsonString.substring(3);
            }
            if (jsonString.endsWith("```")) {
                jsonString = jsonString.substring(0, jsonString.length() - 3);
            }
            jsonString = jsonString.trim();
            
            org.json.JSONObject json = new org.json.JSONObject(jsonString);
            if (json.has("action")) {
                String action = json.getString("action");
                
                if ("create_project".equals(action)) {
                     // ... (existing create_project logic) ...
                     String projectName = json.optString("name", "NewProject");
                     String pkgName = json.optString("package", "com.my.newproject");
                     
                     adapter.addMessage(new ChatMessage("I'll create a new project for you.", false));
 
                     new Thread(() -> {
                         try {
                             String sc_id = ProjectBuilder.createProject(ChatActivity.this, projectName, pkgName, projectName);
                             
                             runOnUiThread(() -> {
                                 java.util.Map<String, String> cardData = new java.util.HashMap<>();
                                 cardData.put("name", projectName);
                                 cardData.put("package", pkgName);
                                 cardData.put("sc_id", sc_id);
                                 
                                 adapter.addMessage(new ChatMessage(
                                     "Created project", 
                                     false, 
                                     ChatMessage.Type.PROJECT_CARD, 
                                     cardData
                                 ));
                                 ((RecyclerView) findViewById(R.id.recycler_view)).smoothScrollToPosition(adapter.getItemCount() - 1);
                             });
                             
                         } catch (Exception e) {
                             runOnUiThread(() -> 
                                 adapter.addMessage(new ChatMessage("Error creating project: " + e.getMessage(), false))
                             );
                         }
                     }).start();

                } else if ("fix_error".equals(action)) {
                    // Handle Auto-Fix
                    String file = json.optString("file", null);
                    
                    // Fallback to suspected file from error log if AI missed it
                    if ((file == null || file.trim().isEmpty() || file.equalsIgnoreCase("Unknown File")) && suspectedErrorFile != null) {
                        file = suspectedErrorFile;
                        // Toast to let user know we were smart
                        // Toast.makeText(ChatActivity.this, "Inferred file: " + file, Toast.LENGTH_SHORT).show();
                    }
                    
                    if (file == null) file = "Unknown File";

                    String description = json.optString("description", "Adding fix...");
                    String target = json.optString("target", "");
                    String replacement = json.optString("replacement", "");
                    
                    java.util.Map<String, String> fixData = new java.util.HashMap<>();
                    fixData.put("file", file);
                    fixData.put("description", description);
                    fixData.put("target", target);
                    fixData.put("replacement", replacement);
                    
                    adapter.addMessage(new ChatMessage(
                        "I have found a fix for your error:", 
                        false, 
                        ChatMessage.Type.FIX_PROPOSAL, 
                        fixData
                    ));
                    
                } else {
                     // Parse for code blocks and split
                     parseAndAddContent(response);
                }

            } else {
                 parseAndAddContent(response);
            }
        } catch (org.json.JSONException e) {
             parseAndAddContent(response);
        }
    }
    
    private void parseAndAddContent(String response) {
        // Use a simple, non-greedy regex to find everything between backticks
        // This handles:
        // ```java\ncode``` (Standard)
        // ```java code``` (No newline)
        // ```code``` (No language)
        // ``` java code``` (Space before language)
        java.util.regex.Pattern p = java.util.regex.Pattern.compile("```(.*?)```", java.util.regex.Pattern.DOTALL);
        java.util.regex.Matcher m = p.matcher(response);
        
        int lastIndex = 0;
        boolean foundCode = false;
        boolean isFirstPart = true;
        
        while (m.find()) {
            foundCode = true;
            String preText = response.substring(lastIndex, m.start()).trim();
            if (!preText.isEmpty()) {
                ChatMessage msg = new ChatMessage(preText, false);
                if (!isFirstPart) msg.setContinuation(true);
                adapter.addMessage(msg);
                isFirstPart = false;
            }
            
            // Raw content inside backticks
            String rawContent = m.group(1);
            String language = "text"; 
            String codeContent = rawContent;
            
            if (rawContent != null && !rawContent.trim().isEmpty()) {
                String trimmed = rawContent.trim();
                String[] parts = trimmed.split("\\s+", 2);
                if (parts.length > 0) {
                    String potentialLang = parts[0].trim();
                     // Heuristic: If first word is short and alphanumeric, treat as lang
                    if (potentialLang.matches("^[a-zA-Z0-9]+$") && parts.length > 1) {
                         language = potentialLang;
                         codeContent = parts[1]; // The rest is code
                    } else {
                         codeContent = trimmed;
                    }
                }
            }

            java.util.Map<String, String> data = new java.util.HashMap<>();
            data.put("language", language);
            
            ChatMessage codeMsg = new ChatMessage(codeContent, false, ChatMessage.Type.CODE_SNIPPET, data);
            // Code snippets always look better if they "continue" the flow, or stand alone?
            // If there was preText, this is a continuation. If not (and this is the very first thing), it's not.
            if (!isFirstPart) codeMsg.setContinuation(true);
            adapter.addMessage(codeMsg);
            
            isFirstPart = false;
            lastIndex = m.end();
        }
        
        if (foundCode) {
            String postText = response.substring(lastIndex).trim();
            if (!postText.isEmpty()) {
                ChatMessage postMsg = new ChatMessage(postText, false);
                postMsg.setContinuation(true);
                adapter.addMessage(postMsg);
            }
        } else {
            adapter.addMessage(new ChatMessage(response, false));
        }
    }
}
