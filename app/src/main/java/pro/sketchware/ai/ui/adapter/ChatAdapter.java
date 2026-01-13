package pro.sketchware.ai.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import pro.sketchware.R;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_USER = 0;
    private static final int TYPE_AI_TEXT = 1;
    private static final int TYPE_PROJECT_CARD = 2;
    private static final int TYPE_FIX_PROPOSAL = 3;
    private static final int TYPE_CODE_SNIPPET = 4;

    private final List<ChatMessage> messages = new ArrayList<>();
    private String currentScId;

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setProjectContext(String scId) {
        this.currentScId = scId;
    }

    public void addMessage(ChatMessage message) {
        messages.add(message);
        notifyItemInserted(messages.size() - 1);
    }
    
    public void removeMessage(ChatMessage message) {
        int index = messages.indexOf(message);
        if (index != -1) {
            messages.remove(index);
            notifyItemRemoved(index);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = messages.get(position);
        if (message.isUser()) {
            return TYPE_USER;
        } else if (message.getType() == ChatMessage.Type.PROJECT_CARD) {
            return TYPE_PROJECT_CARD;
        } else if (message.getType() == ChatMessage.Type.FIX_PROPOSAL) {
            return TYPE_FIX_PROPOSAL;
        } else if (message.getType() == ChatMessage.Type.CODE_SNIPPET) {
            return TYPE_CODE_SNIPPET;
        } else {
            return TYPE_AI_TEXT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == TYPE_USER) {
            View view = inflater.inflate(R.layout.item_chat_user, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == TYPE_PROJECT_CARD) {
            View view = inflater.inflate(R.layout.item_chat_project_card, parent, false);
            return new ProjectCardViewHolder(view);
        } else if (viewType == TYPE_FIX_PROPOSAL) {
            View view = inflater.inflate(R.layout.item_chat_fix_proposal, parent, false);
            return new FixProposalViewHolder(view);
        } else if (viewType == TYPE_CODE_SNIPPET) {
            View view = inflater.inflate(R.layout.item_chat_snippet, parent, false);
            return new SnippetViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.item_chat_ai, parent, false);
            return new AIViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = messages.get(position);
        
        if (holder instanceof UserViewHolder) {
            ((UserViewHolder) holder).bind(message);
        } else if (holder instanceof AIViewHolder) {
            ((AIViewHolder) holder).bind(message);
        } else if (holder instanceof ProjectCardViewHolder) {
            ((ProjectCardViewHolder) holder).bind(message);
        } else if (holder instanceof FixProposalViewHolder) {
            ((FixProposalViewHolder) holder).bind(message, currentScId);
        } else if (holder instanceof SnippetViewHolder) {
            ((SnippetViewHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        ImageView imageView;

        UserViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.text_content);
            // Dynamically add ImageView if not present in XML (simplest path without changing XML again)
            // Or assume we will add it to XML. Let's add it to XML in a moment.
            // But wait, I can access the parent LinearLayout and add it programmatically or just expect it in XML.
            // Let's expect it in XML. I'll need to update item_chat_user.xml again.
            // Actually, for now, let's just assume I will update the XML next.
            imageView = itemView.findViewById(R.id.image_content);
        }

        void bind(ChatMessage message) {
            content.setText(message.getContent());
            if (message.getImagePath() != null) {
                if (imageView == null) {
                     // Lazy find if I didn't find it in constructor (hacky but safer if XML update trails)
                     imageView = itemView.findViewById(R.id.image_content);
                }
                if (imageView != null) {
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageURI(android.net.Uri.parse(message.getImagePath()));
                }
            } else {
                if (imageView != null) imageView.setVisibility(View.GONE);
            }
        }
    }

    static class AIViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        TextView timestamp;
        View copyBtn;
        ImageView avatar;
        io.noties.markwon.Markwon markwon;

        AIViewHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.text_content);
            timestamp = itemView.findViewById(R.id.text_timestamp);
            copyBtn = itemView.findViewById(R.id.btn_copy);
            // Assuming default structure of item_chat_ai.xml where avatar is the first child (ImageView)
            if (itemView instanceof ViewGroup) {
                View firstChild = ((ViewGroup) itemView).getChildAt(0);
                if (firstChild instanceof ImageView) {
                    avatar = (ImageView) firstChild;
                }
            }
            
            // Configure Markwon with code styling
            markwon = io.noties.markwon.Markwon.builder(itemView.getContext())
                .usePlugin(new io.noties.markwon.AbstractMarkwonPlugin() {
                    @Override
                    public void configureTheme(@NonNull io.noties.markwon.core.MarkwonTheme.Builder builder) {
                        builder.codeBackgroundColor(0xFFF0F2F5); // Lighter gray
                        builder.codeBlockBackgroundColor(0xFFF8F9FA); // Very light gray block
                        builder.codeBlockTextColor(0xFF202124); // Dark text
                        builder.codeTextColor(0xFFD63384); // Pinkish red for inline code
                        builder.blockQuoteColor(0xFFDDDDDD);
                        builder.linkColor(0xFF006C7C); // Teal link color
                    }
                })
                .build();
        }

        void bind(ChatMessage message) {
            if (message.isThinking()) {
                content.setText("Thinking...");
                content.setTypeface(null, android.graphics.Typeface.ITALIC);
                if (timestamp != null) timestamp.setVisibility(View.GONE);
                if (copyBtn != null) copyBtn.setVisibility(View.GONE);
                if (avatar != null) avatar.setVisibility(View.VISIBLE);
            } else {
                markwon.setMarkdown(content, message.getContent());
                content.setTypeface(null, android.graphics.Typeface.NORMAL);
                
                if (message.isContinuation()) {
                    // Hide avatar for seamless look, but keep space (INVISIBLE)
                    if (avatar != null) avatar.setVisibility(View.INVISIBLE);
                    // Hide metadata for continuations
                    if (timestamp != null) timestamp.setVisibility(View.GONE);
                    if (copyBtn != null) copyBtn.setVisibility(View.GONE);
                } else {
                    if (avatar != null) avatar.setVisibility(View.VISIBLE);
                    if (timestamp != null) {
                       timestamp.setVisibility(View.VISIBLE);
                       timestamp.setText(formatTime(System.currentTimeMillis()));
                    }
                    if (copyBtn != null) copyBtn.setVisibility(View.VISIBLE);
                }
                
                if (copyBtn != null) {
                    copyBtn.setOnClickListener(v -> {
                        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) 
                            v.getContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE);
                        android.content.ClipData clip = android.content.ClipData.newPlainText("AI Response", message.getContent());
                        clipboard.setPrimaryClip(clip);
                        android.widget.Toast.makeText(v.getContext(), "Copied to clipboard", android.widget.Toast.LENGTH_SHORT).show();
                    });
                }
            }
        }
        
        private String formatTime(long time) {
             java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
             return sdf.format(new java.util.Date(time));
        }
    }

    static class ProjectCardViewHolder extends RecyclerView.ViewHolder {
        TextView appName;
        TextView packageName;
        View card;
        View openBtn;

        ProjectCardViewHolder(View itemView) {
            super(itemView);
            appName = itemView.findViewById(R.id.text_project_name);
            packageName = itemView.findViewById(R.id.text_project_package);
            card = itemView.findViewById(R.id.card_project);
            openBtn = itemView.findViewById(R.id.btn_open);
        }

        void bind(ChatMessage message) {
            if (message.getCardData() != null) {
                appName.setText(message.getCardData().getOrDefault("name", "Project"));
                packageName.setText(message.getCardData().getOrDefault("package", "com.example"));
                
                String scId = message.getCardData().get("sc_id");
                
                View.OnClickListener openListener = v -> {
                    if (scId != null) {
                        try {
                            android.content.Intent intent = new android.content.Intent(v.getContext(), com.besome.sketch.design.DesignActivity.class);
                            intent.putExtra("sc_id", scId);
                            v.getContext().startActivity(intent);
                        } catch (Exception e) {
                            android.widget.Toast.makeText(v.getContext(), "Error opening project: " + e.getMessage(), android.widget.Toast.LENGTH_SHORT).show();
                        }
                    } else {
                         android.widget.Toast.makeText(v.getContext(), "Project ID not found", android.widget.Toast.LENGTH_SHORT).show();
                    }
                };
                
                card.setOnClickListener(openListener);
                openBtn.setOnClickListener(openListener);
            }
        }
    }


    static class SnippetViewHolder extends RecyclerView.ViewHolder {
        io.github.rosemoe.sora.widget.CodeEditor codeEditor;
        TextView langLabel;
        View copyBtn;
        ImageView avatar;

        SnippetViewHolder(View itemView) {
            super(itemView);
            codeEditor = itemView.findViewById(R.id.code_editor);
            langLabel = itemView.findViewById(R.id.text_language);
            copyBtn = itemView.findViewById(R.id.btn_copy_snippet);
            avatar = itemView.findViewById(R.id.iv_avatar);
            
            codeEditor.setEditable(false);
            codeEditor.setTextSize(12);
        }

        void bind(ChatMessage message) {
            // Handle avatar visibility for continuation
            if (message.isContinuation()) {
                if (avatar != null) avatar.setVisibility(View.INVISIBLE);
            } else {
                if (avatar != null) avatar.setVisibility(View.VISIBLE);
            }

            codeEditor.setText(message.getContent());
            String lang = message.getCardData() != null ? message.getCardData().getOrDefault("language", "java") : "java";
            langLabel.setText(lang);
            
            // Set Language
            if ("xml".equalsIgnoreCase(lang)) {
                 codeEditor.setEditorLanguage(mod.jbk.code.CodeEditorLanguages.loadTextMateLanguage(mod.jbk.code.CodeEditorLanguages.SCOPE_NAME_XML));
            } else {
                 codeEditor.setEditorLanguage(new io.github.rosemoe.sora.langs.java.JavaLanguage());
            }
            
            copyBtn.setOnClickListener(v -> {
                 android.content.ClipboardManager clipboard = (android.content.ClipboardManager) 
                     v.getContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE);
                 android.content.ClipData clip = android.content.ClipData.newPlainText("Snippet", message.getContent());
                 clipboard.setPrimaryClip(clip);
                 android.widget.Toast.makeText(v.getContext(), "Copied", android.widget.Toast.LENGTH_SHORT).show();
            });
        }
    }
    
    // ... existing FixProposalViewHolder ...
    static class FixProposalViewHolder extends RecyclerView.ViewHolder {

        TextView filename;
        TextView filePath;
        TextView description;
        TextView newCode;
        com.google.android.material.button.MaterialButton acceptBtn;
        com.google.android.material.button.MaterialButton rejectBtn;
        View copyBtn;
        View cardContent;

        FixProposalViewHolder(View itemView) {
            super(itemView);
            filename = itemView.findViewById(R.id.text_filename);
            filePath = itemView.findViewById(R.id.text_file_path);
            description = itemView.findViewById(R.id.text_description);
            newCode = itemView.findViewById(R.id.text_new_code);
            acceptBtn = itemView.findViewById(R.id.btn_accept_fix);
            rejectBtn = itemView.findViewById(R.id.btn_reject);
            copyBtn = itemView.findViewById(R.id.btn_copy_code);
            cardContent = itemView.findViewById(R.id.card_fix);
        }

        void bind(ChatMessage message, String scId) {
            java.util.Map<String, String> data = message.getCardData();
            if (data == null) return;

            String fullPath = data.getOrDefault("file", "Unknown File");
            filePath.setText(fullPath);
            
            // Extract filename
            String name = new java.io.File(fullPath).getName();
            filename.setText(name);

            description.setText(data.getOrDefault("description", "Fix available"));
            newCode.setText(data.getOrDefault("replacement", "// No new code"));

            rejectBtn.setOnClickListener(v -> {
               itemView.setVisibility(View.GONE);
               itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
               android.widget.Toast.makeText(v.getContext(), "Proposal discarded", android.widget.Toast.LENGTH_SHORT).show();
            });
            
            copyBtn.setOnClickListener(v -> {
                String code = data.get("replacement");
                if (code != null) {
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager) 
                        v.getContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("Proposed Fix", code);
                    clipboard.setPrimaryClip(clip);
                    android.widget.Toast.makeText(v.getContext(), "Code copied to clipboard", android.widget.Toast.LENGTH_SHORT).show();
                }
            });

            acceptBtn.setOnClickListener(v -> {
                String targetFile = data.get("file");
                String targetContent = data.get("target");
                String replacementContent = data.get("replacement");
                
                if (targetFile != null && replacementContent != null) {
                    try {
                        java.io.File file;
                        // Try to resolve relative path if scId is available
                        if (scId != null) {
                             String projectPath = a.a.a.wq.b(scId);
                             file = new java.io.File(projectPath, targetFile);
                             // If not found, try generic absolute check just in case AI failed instructions
                             if (!file.exists()) {
                                 file = new java.io.File(targetFile);
                             }
                        } else {
                             file = new java.io.File(targetFile);
                        }

                        if (!file.exists()) {
                            // FAST PATH FAILED. DO RECURSIVE SEARCH.
                            // The AI often gives just "MainActivity.java" or "app/src/.../MainActivity.java"
                            // We need to find the specific file in the project.
                            
                            if (scId != null) {
                                // Use wq.d() for 'mysc' (source code) instead of wq.b() for 'data' (metadata)
                                String projectPath = a.a.a.wq.d(scId);
                                java.io.File rootDir = new java.io.File(projectPath);
                                
                                // Extract just the filename to search for
                                String fileName = new java.io.File(targetFile).getName();
                                
                                android.widget.Toast.makeText(v.getContext(), "Recursive search in: " + rootDir.getAbsolutePath(), android.widget.Toast.LENGTH_LONG).show();
                                
                                java.io.File found = findFileRecursive(rootDir, fileName);
                                if (found != null) {
                                    file = found;
                                } else {
                                     android.widget.Toast.makeText(v.getContext(), "File not found recursively in " + rootDir.getName() + ": " + fileName, android.widget.Toast.LENGTH_LONG).show();
                                     return;
                                }
                            } else {
                                // No project context, can't search recursively safely without root.
                                android.widget.Toast.makeText(v.getContext(), "File not found (and no project context): " + file.getAbsolutePath(), android.widget.Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        
                        android.widget.Toast.makeText(v.getContext(), "Applying to: " + file.getName(), android.widget.Toast.LENGTH_SHORT).show();
                        
                        // Read, Replace, Write
                        StringBuilder contentBuilder = new StringBuilder();
                        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(file))) {
                            String line;
                            while ((line = br.readLine()) != null) {
                                contentBuilder.append(line).append("\n");
                            }
                        }
                        
                        String originalText = contentBuilder.toString();
                        String newText = originalText;
                        
                        // Attempt exact match first
                        if (targetContent != null && !targetContent.isEmpty()) {
                            if (originalText.contains(targetContent)) {
                                newText = originalText.replace(targetContent, replacementContent);
                            } else if (originalText.contains(targetContent.trim())) {
                                newText = originalText.replace(targetContent.trim(), replacementContent);
                            } else {
                                // Try ignoring whitespace differences (basic)
                                String normalizedOriginal = originalText.replaceAll("\\s+", " ");
                                String normalizedTarget = targetContent.replaceAll("\\s+", " ");
                                if (normalizedOriginal.contains(normalizedTarget)) {
                                     // This is hard to do replacement on, so fall back to simple check failure
                                     android.widget.Toast.makeText(v.getContext(), "Target code not found (whitespace mismatch). Applying manual patch...", android.widget.Toast.LENGTH_SHORT).show();
                                     // For now, if exact match fails, we might just append or fail.
                                     // Let's stick to fail but user notification
                                     android.widget.Toast.makeText(v.getContext(), "Error: Target code not found in file. Please edit manually.", android.widget.Toast.LENGTH_LONG).show();
                                     return;
                                } else {
                                     android.widget.Toast.makeText(v.getContext(), "Error: Target code segment not found.", android.widget.Toast.LENGTH_LONG).show();
                                     return;
                                }
                            }
                        } else {
                             // Fallback: If no target, maybe append? Or replace all? 
                             // AI usually sends target. If null, maybe it means replace file?
                             // Let's assume append for safety if description says "Add"
                             String desc = data.getOrDefault("description", "").toLowerCase();
                             if (desc.contains("add") || desc.contains("append")) {
                                 newText = originalText + "\n" + replacementContent;
                             } else {
                                 // Dangerous to replace all, but if target is null, maybe that's the intent.
                                 // Let's warn instead.
                                 android.widget.Toast.makeText(v.getContext(), "No target specified. Cannot apply safely.", android.widget.Toast.LENGTH_SHORT).show();
                                 return;
                             }
                        }

                        // Write back
                        try (java.io.FileWriter fw = new java.io.FileWriter(file)) {
                            fw.write(newText);
                        }
                        
                        android.widget.Toast.makeText(v.getContext(), "Fix Applied Successfully!", android.widget.Toast.LENGTH_SHORT).show();
                        
                        // Disable button to prevent double apply
                        acceptBtn.setEnabled(false);
                        acceptBtn.setText("Applied");
             //           acceptBtn.setIconResource(R.drawable.ic_check); // Assuming this icon exists or similar
                        
                    } catch (Exception e) {
                        android.widget.Toast.makeText(v.getContext(), "Failed to apply fix: " + e.getMessage(), android.widget.Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }
            });
        }
        
        private java.io.File findFileRecursive(java.io.File dir, String name) {
            if (!dir.exists() || !dir.isDirectory()) return null;
            
            java.io.File[] files = dir.listFiles();
            if (files == null) return null;
            
            for (java.io.File f : files) {
                if (f.isDirectory()) {
                    // Skip build/generated folders to speed up and avoid wrong files
                    if (f.getName().equals("build") || f.getName().equals(".gradle")) continue;
                    
                    java.io.File found = findFileRecursive(f, name);
                    if (found != null) return found;
                } else {
                    if (f.getName().equalsIgnoreCase(name)) return f; // Case Insensitive
                }
            }
            return null;
        }
    }
}
