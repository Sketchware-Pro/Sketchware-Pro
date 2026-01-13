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

import pro.sketchware.ai.QwenService;

import pro.sketchware.R;

public abstract class CustomBlockGeneratorDialog extends Dialog {

    private EditText input;
    private ProgressBar loading;
    private final QwenService aiService;

    public CustomBlockGeneratorDialog(@NonNull Context context) {
        super(context);
        aiService = new QwenService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom_block_generator);

        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(android.graphics.Color.TRANSPARENT));
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        input = findViewById(R.id.input_field);
        loading = findViewById(R.id.loading_indicator);
        Button generateBtn = findViewById(R.id.generate_button);

        generateBtn.setOnClickListener(v -> generate());
    }

    private void generate() {
        String prompt = input.getText().toString().trim();
        if (prompt.isEmpty()) return;

        loading.setVisibility(android.view.View.VISIBLE);
        aiService.generateCustomBlock(prompt, json -> {
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
