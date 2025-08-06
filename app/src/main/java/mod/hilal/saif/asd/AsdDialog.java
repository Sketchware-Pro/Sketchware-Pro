package mod.hilal.saif.asd;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.Lx;
import a.a.a.Ss;
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.databinding.CodeEditorHsAsdBinding;
import pro.sketchware.utility.EditorUtils;
import pro.sketchware.utility.SketchwareUtil;

public class AsdDialog extends Dialog implements DialogInterface.OnDismissListener {
    private SharedPreferences pref;
    private Activity act;
    private CodeEditorHsAsdBinding binding;
    private String content;

    public AsdDialog(Activity activity) {
        super(activity);
        act = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = CodeEditorHsAsdBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        binding.editor.setTypefaceText(EditorUtils.getTypeface(act));
        binding.editor.setText(content);
        binding.editor.setWordwrap(false);

        EditorUtils.loadJavaConfig(binding.editor);
        SrcCodeEditor.loadCESettings(act, binding.editor, "dlg");
        pref = SrcCodeEditor.pref;

        Menu menu = binding.toolbar.getMenu();
        MenuItem itemWordwrap = menu.findItem(R.id.action_word_wrap);
        MenuItem itemAutocomplete = menu.findItem(R.id.action_autocomplete);
        MenuItem itemAutocompleteSymbolPair = menu.findItem(R.id.action_autocomplete_symbol_pair);

        itemWordwrap.setChecked(pref.getBoolean("dlg_ww", false));
        itemAutocomplete.setChecked(pref.getBoolean("dlg_ac", false));
        itemAutocompleteSymbolPair.setChecked(pref.getBoolean("dlg_acsp", true));

        binding.toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_undo) {
                binding.editor.undo();
            } else if (id == R.id.action_redo) {
                binding.editor.redo();
            } else if (id == R.id.action_pretty_print) {
                StringBuilder sb = new StringBuilder();
                String[] split = binding.editor.getText().toString().split("\n");
                for (String s : split) {
                    String trim = (s + "X").trim();
                    sb.append(trim.substring(0, trim.length() - 1));
                    sb.append("\n");
                }
                boolean failed = false;
                String code = sb.toString();
                try {
                    code = Lx.j(code, true);
                } catch (Exception e) {
                    failed = true;
                    SketchwareUtil.toastError("Your code contains incorrectly nested parentheses");
                }
                if (!failed) {
                    binding.editor.setText(code);
                }
            } else if (id == R.id.action_word_wrap) {
                item.setChecked(!item.isChecked());
                binding.editor.setWordwrap(item.isChecked());
                pref.edit().putBoolean("dlg_ww", item.isChecked()).apply();
            } else if (id == R.id.action_autocomplete_symbol_pair) {
                item.setChecked(!item.isChecked());
                binding.editor.getProps().symbolPairAutoCompletion = item.isChecked();
                pref.edit().putBoolean("dlg_acsp", item.isChecked()).apply();
            } else if (id == R.id.action_autocomplete) {
                item.setChecked(!item.isChecked());
                binding.editor.getComponent(EditorAutoCompletion.class).setEnabled(item.isChecked());
                pref.edit().putBoolean("dlg_ac", item.isChecked()).apply();
            } else if (id == R.id.action_paste) {
                binding.editor.pasteText();
            } else if (id == R.id.action_find_replace) {
                binding.editor.getSearcher().stopSearch();
                binding.editor.beginSearchMode();
            }
            return true;
        });

        setOnDismissListener(this);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        pref.edit().putInt("dlg_ts", (int) (binding.editor.getTextSizePx() / act.getResources().getDisplayMetrics().scaledDensity)).apply();
        pref = null;
        act = null;
    }

    public void setOnSaveClickListener(LogicEditorActivity logicEditorActivity, boolean z, Ss ss, AsdDialog asdDialog) {
        binding.btnSave.setOnClickListener(new AsdHandlerCodeEditor(logicEditorActivity, z, ss, asdDialog, binding.editor));
    }

    public void setOnCancelClickListener(AsdDialog asdDialog) {
        binding.btnCancel.setOnClickListener(Helper.getDialogDismissListener(asdDialog));
    }

    public void setContent(String content) {
        this.content = content;
    }
}