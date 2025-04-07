package mod.hilal.saif.asd;

import static pro.sketchware.utility.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.Lx;
import a.a.a.Ss;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.EditorAutoCompletion;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.utility.SketchwareUtil;

public class AsdDialog extends Dialog implements DialogInterface.OnDismissListener {
    private static SharedPreferences pref;
    private final Activity act;
    private ViewGroup base;
    private TextView cancel;
    private CodeEditor codeEditor;
    private TextView save;
    private String str;
    private LinearLayout lin;

    public AsdDialog(Activity activity) {
        super(activity);
        act = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_editor_hs_asd);

        codeEditor = findViewById(R.id.editor);
        codeEditor.setTypefaceText(Typeface.MONOSPACE);
        codeEditor.setEditorLanguage(new JavaLanguage());
        codeEditor.setText(str);
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
            } else if (id == R.id.action_switch_language) {
                SketchwareUtil.toast("Currently not supported, sorry!");
            } else if (id == R.id.action_switch_theme) {
                SrcCodeEditor.showSwitchThemeDialog(act, codeEditor, (dialog, which) -> {
                    SrcCodeEditor.selectTheme(codeEditor, which);
                    AsdDialog.pref.edit().putInt("dlg_theme", which).apply();
                    if (isDark()) {
                        lin.setBackgroundColor(0xff292929);
                        save.setBackground(new DialogButtonGradientDrawable()
                                .getIns((int) getDip(4), 0, 0xff333333, 0xff333333));
                        cancel.setBackground(new DialogButtonGradientDrawable()
                                .getIns((int) getDip(4), 0, 0xff333333, 0xff333333));
                    } else {
                        lin.setBackgroundColor(Color.WHITE);
                        save.setBackground(new DialogButtonGradientDrawable()
                                .getIns((int) getDip(4), 0, 0xff2196f3, 0xff2196f3));
                        cancel.setBackground(new DialogButtonGradientDrawable()
                                .getIns((int) getDip(4), 0, 0xff2196f3, 0xff2196f3));
                    }
                    dialog.dismiss();
                });
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
            }
            return true;
        });

        codeEditor.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                0,
                1.0f
        ));
        base = (ViewGroup) codeEditor.getParent();
        addControl();
        getWindow().setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
        );
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOnDismissListener(this);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        pref.edit().putInt("dlg_ts", (int) (codeEditor.getTextSizePx() / act.getResources().getDisplayMetrics().scaledDensity)).apply();
    }

    private boolean isThemeDark(int theme) {
        return theme == 3 || theme == 4;
    }

    private boolean isDark() {
        return isThemeDark(pref.getInt("dlg_theme", 3));
    }

    private void addControl() {
        lin = new LinearLayout(getContext());
        lin.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0.0f
        ));
        lin.setPadding(0, 0, 0, 0);
        lin.setOrientation(LinearLayout.HORIZONTAL);
        if (isDark()) {
            lin.setBackgroundColor(0xff292929);
        } else {
            lin.setBackgroundColor(Color.WHITE);
        }
        cancel = new TextView(getContext());
        cancel.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        ((LinearLayout.LayoutParams) cancel.getLayoutParams()).setMargins(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
        cancel.setText(R.string.common_word_cancel);
        cancel.setTextColor(-1);
        cancel.setPadding(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
        cancel.setGravity(17);
        if (isDark()) {
            cancel.setBackgroundColor(0xff333333);
        } else {
            cancel.setBackgroundColor(0xff008dcd);
        }
        cancel.setTextSize(15.0f);
        lin.addView(cancel);
        save = new TextView(getContext());
        save.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        ((LinearLayout.LayoutParams) save.getLayoutParams()).setMargins(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
        save.setText(R.string.common_word_save);
        save.setTextColor(Color.WHITE);
        save.setPadding(
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8),
                (int) getDip(8)
        );
        save.setGravity(17);
        if (isDark()) {
            save.setBackgroundColor(0xff333333);
        } else {
            save.setBackgroundColor(0xff008dcd);
        }
        save.setTextSize(15.0f);
        lin.addView(save);
        if (isDark()) {
            save.setBackground(new DialogButtonGradientDrawable()
                    .getIns(getDip(4),
                            0,
                            0xff333333,
                            0xff333333));
            cancel.setBackground(new DialogButtonGradientDrawable()
                    .getIns(getDip(4),
                            0,
                            0xff333333,
                            0xff333333));
        } else {
            save.setBackground(new DialogButtonGradientDrawable()
                    .getIns(getDip(4),
                            0,
                            0xff2196f3,
                            0xff2196f3));
            cancel.setBackground(new DialogButtonGradientDrawable()
                    .getIns(getDip(4),
                            0,
                            0xff2196f3,
                            0xff2196f3));
        }
        save.setElevation(getDip(1));
        cancel.setElevation(getDip(1));
        base.addView(lin);
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