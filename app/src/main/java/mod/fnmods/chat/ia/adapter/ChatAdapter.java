package mod.fnmods.chat.ia.adapter;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mod.fnmods.chat.ia.ChatIaActivity;
import mod.fnmods.chat.ia.ui.CodeTextView;
import pro.sketchware.R;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<HashMap<String, Object>> messages;

    public ChatAdapter(List<HashMap<String, Object>> messages) {
        this.messages = messages;
    }



    private void renderMessage(String text, LinearLayout container, Context ctx) {
        container.removeAllViews();

        Pattern pattern = Pattern.compile("```(\\w+)?([\\s\\S]*?)```");
        Matcher matcher = pattern.matcher(text);
        int lastEnd = 0;
        while (matcher.find()) {
            String before = text.substring(lastEnd, matcher.start());
            if (!before.trim().isEmpty()) {
                TextView tv = new TextView(ctx);
                tv.setText(before.trim());
                tv.setTextSize(14);
                container.addView(tv);
            }
            View codeView = LayoutInflater.from(ctx)
                    .inflate(R.layout.container_bubble_ia, container, false);

            TextView lang = codeView.findViewById(R.id.txtLang);
            CodeTextView code = codeView.findViewById(R.id.txtCode);
            ImageView copy = codeView.findViewById(R.id.btnCopy);


            String language = matcher.group(1);
            String codeText = matcher.group(2);

            lang.setText(language != null ? language.toUpperCase() : "CODE");
            code.setCode(codeText.trim(),language != null ? language.toUpperCase() : "CODE");
            copy.setOnClickListener(v -> {
                ClipboardManager clipboard =
                        (ClipboardManager) ctx.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("code", codeText.trim());
                clipboard.setPrimaryClip(clip);
               SketchwareUtil.toast("Copied");
            });

            container.addView(codeView);
            lastEnd = matcher.end();
        }
        if (lastEnd < text.length()) {
            String after = text.substring(lastEnd);
            if (!after.trim().isEmpty()) {
                TextView tv = new TextView(ctx);
                tv.setText(after.trim());
                tv.setTextSize(14);
                container.addView(tv);
            }
        }
    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_bubble, parent, false);
        return new ChatViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        HashMap<String, Object> msg = messages.get(position);
        String text = String.valueOf(msg.get("text"));
        boolean isAI = (boolean) msg.get("isAI");
        Log.d("ChatAdapter", "isAI: " + text);
         renderMessage(text, holder.messageText, holder.itemView.getContext());
        int maxWidth = (int) (holder.itemView.getContext().getResources().getDisplayMetrics().widthPixels * 0.8);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
               maxWidth,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        holder.cardBubble.setLayoutParams(params);

        if (isAI) {
            params.gravity = Gravity.START;
            holder.cardBubble.setBackgroundResource(R.drawable.chat_bubble_left);
          } else {
            params.gravity = Gravity.END;
            holder.cardBubble.setBackgroundResource(R.drawable.chat_bubble_right);
         }
        holder.cardBubble.setLayoutParams(params);

        holder.itemView.setOnLongClickListener(v -> {
            ClipboardManager clipboard =
                    (ClipboardManager) holder.itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("code", text);
            clipboard.setPrimaryClip(clip);
            SketchwareUtil.toast("Copied");
            return true;
        });
    }

    @Override
    public int getItemCount() { return messages.size(); }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        LinearLayout cardBubble;
        LinearLayout messageText;

        ChatViewHolder(View itemView) {
            super(itemView);
            cardBubble = itemView.findViewById(R.id.card_bubble);
            messageText = itemView.findViewById(R.id.container);
        }
    }
}