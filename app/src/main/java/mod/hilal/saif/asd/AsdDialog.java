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
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.Lx;
import a.a.a.Ss;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.utility.EditorUtils;
import pro.sketchware.utility.SketchwareUtil;

public class AsdDialog extends Dialog implements DialogInterface.OnDismissListener {
    private static SharedPreferences pref;
    private Activity act;
    private CodeEditor codeEditor;
    private String str;
    private Button cancel;
    private Button save;

    public AsdDialog(Activity activity) {
        super(activity);
        act = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_editor_hs_asd);

        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        cancel = findViewById(R.id.btn_cancel);
        save = findViewById(R.id.btn_save);

        codeEditor = findViewById(R.id.editor);
        codeEditor.setTypefaceText(EditorUtils.getTypeface(act));
        codeEditor.setText(Lx.j(str, false));
        codeEditor.setWordwrap(false);

        EditorUtils.loadJavaConfig(codeEditor);
        SrcCodeEditor.loadCESettings(act, codeEditor, "dlg");
        pref = SrcCodeEditor.pref;

        Toolbar toolbar = findViewById(R.id.toolbar);

        Menu menu = toolbar.getMenu();
        MenuItem itemWordwrap = menu.findItem(R.id.action_word_wrap);
        MenuItem itemAutocomplete = menu.findItem(R.id.action_autocomplete);
        MenuItem itemAutocompleteSymbolPair = menu.findItem(R.id.action_autocomplete_symbol_pair);

        itemWordwrap.setChecked(pref.getBoolean("dlg_ww", false));
        itemAutocomplete.setChecked(pref.getBoolean("dlg_ac", false));
        itemAutocompleteSymbolPair.setChecked(pref.getBoolean("dlg_acsp", true));

        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_undo) {
                codeEditor.undo();
            } else if (id == R.id.action_redo) {
                codeEditor.redo();
            } else if (id == R.id.action_pretty_print) {
                StringBuilder sb = new StringBuilder();
                String[] split = codeEditor.getText().toString().split("\n");
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
                    codeEditor.setText(code);
                }
            } else if (id == R.id.action_word_wrap) {
                item.setChecked(!item.isChecked());
                codeEditor.setWordwrap(item.isChecked());
                pref.edit().putBoolean("dlg_ww", item.isChecked()).apply();
            } else if (id == R.id.action_autocomplete_symbol_pair) {
                item.setChecked(!item.isChecked());
                codeEditor.getProps().symbolPairAutoCompletion = item.isChecked();
                pref.edit().putBoolean("dlg_acsp", item.isChecked()).apply();
            } else if (id == R.id.action_autocomplete) {
                item.setChecked(!item.isChecked());
                codeEditor.getComponent(EditorAutoCompletion.class).setEnabled(item.isChecked());
                pref.edit().putBoolean("dlg_ac", item.isChecked()).apply();
            } else if (id == R.id.action_paste) {
                codeEditor.pasteText();
            } else if (id == R.id.action_find_replace) {
                codeEditor.getSearcher().stopSearch();
                codeEditor.beginSearchMode();
            }
            return true;
        });

        setOnDismissListener(this);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        pref.edit().putInt("dlg_ts", (int) (codeEditor.getTextSizePx() / act.getResources().getDisplayMetrics().scaledDensity)).apply();
        pref = null;
        act = null;
    }

    public void saveLis(LogicEditorActivity logicEditorActivity, boolean z, Ss ss, AsdDialog asdDialog) {
        save.setOnClickListener(new AsdHandlerCodeEditor(logicEditorActivity, z, ss, asdDialog, codeEditor));
    }

    public void cancelLis(AsdDialog asdDialog) {
        cancel.setOnClickListener(Helper.getDialogDismissListener(asdDialog));
    }

    public void setCon(String str2) {
        str = str2;
    }
}