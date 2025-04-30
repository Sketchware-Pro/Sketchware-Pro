package pro.sketchware.activities.editor.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.concurrent.Executors;

import a.a.a.Lx;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.blocks.generator.EventBlocksGenerator;
import pro.sketchware.databinding.ActivityJavaEventCodeEditorBinding;
import pro.sketchware.utility.EditorUtils;

public class JavaEventCodeEditorActivity extends BaseAppCompatActivity {

    private boolean isHiddenError;
    private String sc_id;

    private ActivityJavaEventCodeEditorBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJavaEventCodeEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String code = getIntent().getStringExtra("code");
        sc_id = getIntent().getStringExtra("sc_id");
        assert code != null;
        binding.toolbar.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        binding.toolbar.setSubtitle(getIntent().getStringExtra("eventName"));
        binding.editor.setTypefaceText(EditorUtils.getTypeface(this));
        binding.editor.setTextSize(14);
        binding.editor.setText(Lx.j(code, false));
        binding.editor.setWordwrap(false);
        EditorUtils.loadJavaConfig(binding.editor);

        binding.save.setVisibility(View.VISIBLE);
        binding.save.setOnClickListener(v -> saveChanges(binding.editor.getText().toString()));
    }

    private void saveChanges(String javaCode) {
        k();
        Executors.newSingleThreadExecutor().execute(() -> {
            EventBlocksGenerator javaEvent2Blocks = new EventBlocksGenerator(sc_id, getIntent().getStringExtra("javaName"), getIntent().getStringExtra("xmlName"), javaCode);
            //noinspection unchecked
            javaEvent2Blocks.setPreLoadedBlockBeans((ArrayList<BlockBean>) getIntent().getSerializableExtra("old_beans"));
            ArrayList<BlockBean> blockBeans = javaEvent2Blocks.getEventBlockBeans();

            runOnUiThread(() -> {
                h();
                if (javaEvent2Blocks.isSuccessfullyBuild()) {
                    Intent intent = new Intent();
                    intent.putExtra("block_beans", blockBeans);
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    String errorMessage = Helper.getResString(R.string.java_parse_error_message);
                    isHiddenError = true;
                    var dialog = new MaterialAlertDialogBuilder(this)
                            .setTitle(Helper.getResString(R.string.common_error_failed_to_save))
                            .setMessage(errorMessage)
                            .setPositiveButton(Helper.getResString(R.string.common_word_ok), (d, w) -> d.dismiss())
                            .setNeutralButton(Helper.getResString(R.string.java_parse_show_error), null)
                            .show();

                    TextView messageView = dialog.findViewById(android.R.id.message);

                    Button dialogNaturalButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

                    dialogNaturalButton.setOnClickListener(v -> {
                        if (messageView != null) {
                            isHiddenError = !isHiddenError;
                            messageView.setTextIsSelectable(!isHiddenError);
                            messageView.setText(isHiddenError ? errorMessage : javaEvent2Blocks.getErrorMessage());
                            dialogNaturalButton.setText(Helper.getResString(isHiddenError ? R.string.java_parse_show_error : R.string.java_parse_hide_error));
                        }
                    });
                }
            });
        });
    }

}