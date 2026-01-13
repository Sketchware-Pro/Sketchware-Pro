package pro.sketchware.ai.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import pro.sketchware.ai.AIService;
import pro.sketchware.ai.QwenService;

public abstract class BlockGeneratorDialog extends Dialog {

    private EditText input;
    private ProgressBar loading;
    private final AIService aiService;

    public BlockGeneratorDialog(@NonNull Context context) {
        super(context);
        aiService = new QwenService(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        int padding = (int) (16 * getContext().getResources().getDisplayMetrics().density);
        layout.setPadding(padding, padding, padding, padding);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        
        // Ensure dialog window itself is large enough
        if (getWindow() != null) {
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        TextView title = new TextView(getContext());
        title.setText("AI Block Generator");
        title.setTextSize(20);
        title.setTextColor(android.graphics.Color.BLACK);
        layout.addView(title);

        input = new EditText(getContext());
        input.setHint("Describe what you want the block to do...");
        input.setMinLines(3);
        layout.addView(input);

        loading = new ProgressBar(getContext());
        loading.setVisibility(android.view.View.GONE);
        layout.addView(loading);

        Button generateBtn = new Button(getContext());
        generateBtn.setText("Generate");
        generateBtn.setOnClickListener(v -> generate());
        layout.addView(generateBtn);

        setContentView(layout);
    }

    private void generate() {
        String prompt = input.getText().toString().trim();
        if (prompt.isEmpty()) return;

        loading.setVisibility(android.view.View.VISIBLE);
        aiService.generateBlock(prompt, json -> {
            loading.setVisibility(android.view.View.GONE);
            dismiss();
            onBlockGenerated(json);
        }, error -> {
            loading.setVisibility(android.view.View.GONE);
            Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
        });
    }

    public abstract void onBlockGenerated(String jsonResponse);
}
