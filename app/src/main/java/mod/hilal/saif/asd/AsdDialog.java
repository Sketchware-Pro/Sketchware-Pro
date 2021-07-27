package mod.hilal.saif.asd;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.besome.sketch.editor.LogicEditorActivity;
import com.sketchware.remod.Resources;

import a.a.a.Ss;
import io.github.rosemoe.editor.langs.EmptyLanguage;
import io.github.rosemoe.editor.langs.desc.CDescription;
import io.github.rosemoe.editor.langs.desc.CppDescription;
import io.github.rosemoe.editor.langs.desc.JavaScriptDescription;
import io.github.rosemoe.editor.langs.java.JavaLanguage;
import io.github.rosemoe.editor.langs.python.PythonLanguage;
import io.github.rosemoe.editor.langs.universal.UniversalLanguage;
import io.github.rosemoe.editor.widget.CodeEditor;
import mod.SketchwareUtil;
import mod.hey.studios.code.ResHelper;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;

import static mod.SketchwareUtil.getDip;

public class AsdDialog extends Dialog implements DialogInterface.OnDismissListener {

    static SharedPreferences pref;
    public Activity act;
    public ViewGroup base;
    public TextView cancel;
    public View.OnClickListener cancel_l = null;
    public CodeEditor codeEditor;
    public TextView save;
    public View.OnClickListener save_l = null;
    public String str;
    LinearLayout lin;

    public AsdDialog(Activity activity) {
        super(activity);
        act = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.code_editor_hs);
        ResHelper.isInASD = true;
        codeEditor = findViewById(Resources.id.editor);
        codeEditor.setTypefaceText(Typeface.MONOSPACE);
        codeEditor.setOverScrollEnabled(false);
        /* codeEditor.setEdgeEnabled(false); */
        codeEditor.setEditorLanguage(new JavaLanguage());
        codeEditor.setText(str);
        SrcCodeEditor.loadCESettings(act, codeEditor, "dlg");
        pref = SrcCodeEditor.pref;
        ImageView redo = findViewById(Resources.id.menu_view_redo);
        redo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeEditor.undo();
            }
        });
        ImageView undo = findViewById(Resources.id.menu_view_undo);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeEditor.redo();
            }
        });
        ImageView more = findViewById(Resources.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(act, v);
                populateMenu(popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String charSequence = item.getTitle().toString();
                        switch (charSequence) {
                            case "Pretty print":
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
                                    code = SrcCodeEditor.j(code);
                                } catch (Exception e) {
                                    failed = true;
                                    SketchwareUtil.toastError("Your code contains incorrectly nested parentheses");
                                }
                                if (!failed) {
                                    codeEditor.setText(code);
                                }
                                break;

                            case "Switch language":
                                String[] languageItems = new String[]{
                                        "C",
                                        "C++",
                                        "Java",
                                        "JavaScript",
                                        "Python",
                                        "None"
                                };
                                new AlertDialog.Builder(act)
                                        .setTitle("Switch language")
                                        .setSingleChoiceItems(languageItems, -1, new OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (which == 0) {
                                                    codeEditor.setEditorLanguage(new UniversalLanguage(new CDescription()));
                                                } else if (which == 1) {
                                                    codeEditor.setEditorLanguage(new UniversalLanguage(new CppDescription()));
                                                } else if (which == 2) {
                                                    codeEditor.setEditorLanguage(new JavaLanguage());
                                                } else if (which == 3) {
                                                    codeEditor.setEditorLanguage(new UniversalLanguage(new JavaScriptDescription()));
                                                } else if (which == 4) {
                                                    codeEditor.setEditorLanguage(new PythonLanguage());
                                                } else if (which == 5) {
                                                    codeEditor.setEditorLanguage(new EmptyLanguage());
                                                }
                                                dialog.dismiss();
                                            }
                                        }).setNegativeButton(Resources.string.common_word_cancel, null).show();
                                break;

                            case "Find & Replace":
                                codeEditor.getSearcher().stopSearch();
                                codeEditor.beginSearchMode();
                                break;

                            case "Switch theme":
                                String[] themeItems = new String[]{
                                        "Default",
                                        "GitHub",
                                        "Eclipse",
                                        "Dracula",
                                        "VS2019",
                                        "NotepadXX"
                                };
                                new AlertDialog.Builder(act)
                                        .setTitle("Switch theme")
                                        .setSingleChoiceItems(
                                                themeItems, -1, new OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        SrcCodeEditor.selectTheme(codeEditor, which);
                                                        AsdDialog.pref.edit().putInt("dlg_theme", which).apply();
                                                        if (isDark()) {
                                                            lin.setBackgroundColor(Color.parseColor("#FF292929"));
                                                            save.setBackground(new DialogButtonGradientDrawable()
                                                                    .getIns((int) getDip(4), 0, -13421773, -13421773));
                                                            cancel.setBackground(new DialogButtonGradientDrawable()
                                                                    .getIns((int) getDip(4), 0, -13421773, -13421773));
                                                        } else {
                                                            lin.setBackgroundColor(-1);
                                                            save.setBackground(new DialogButtonGradientDrawable()
                                                                    .getIns((int) getDip(4), 0, -14575885, -14575885));
                                                            cancel.setBackground(new DialogButtonGradientDrawable()
                                                                    .getIns((int) getDip(4), 0, -14575885, -14575885));
                                                        }
                                                        dialog.dismiss();
                                                    }
                                                })
                                        .setNegativeButton(Resources.string.common_word_cancel, null)
                                        .show();
                                break;

                            case "Word wrap":
                                item.setChecked(!item.isChecked());
                                codeEditor.setWordwrap(item.isChecked());
                                pref.edit().putBoolean("dlg_ww", item.isChecked()).apply();
                                break;

                            case "Auto complete":
                                item.setChecked(!item.isChecked());
                                codeEditor.setAutoCompletionEnabled(item.isChecked());
                                pref.edit().putBoolean("dlg_ac", item.isChecked()).apply();
                                break;

                            case "Paste":
                                codeEditor.setText(SrcCodeEditor.paste(act));
                                break;

                            default:
                                return false;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        Helper.applyRipple(getContext(), redo);
        Helper.applyRipple(getContext(), undo);
        Helper.applyRipple(getContext(), more);
        findViewById(Resources.id.save).setVisibility(View.GONE);
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

    public void populateMenu(Menu menu) {
        menu.add(0, 0, 0, "Paste");
        menu.add(0, 4, 0, "Word wrap")
                .setCheckable(true)
                .setChecked(pref.getBoolean("dlg_ww", false));
        menu.add(0, 5, 0, "Pretty print");
        menu.add(0, 6, 0, "Switch language");
        menu.add(0, 7, 0, "Switch theme");
        menu.add(0, 8, 0, "Auto complete")
                .setCheckable(true)
                .setChecked(pref.getBoolean("dlg_ac", true));
    }

    public boolean isThemeDark(int theme) {
        return theme == 3 || theme == 4;
    }

    public boolean isDark() {
        return isThemeDark(pref.getInt("dlg_theme", 3));
    }

    public void addControl() {
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
        cancel.setText(Resources.string.common_word_cancel);
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
        save.setText(Resources.string.common_word_save);
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
        save_l = new AsdHandlerCodeEditor(logicEditorActivity, codeEditor.getText().toString(), z, ss, asdDialog, codeEditor);
        save.setOnClickListener(save_l);
    }

    public void cancelLis(LogicEditorActivity logicEditorActivity, AsdDialog asdDialog) {
        cancel_l = new AsdHandlerCodeEditorCancel(logicEditorActivity, codeEditor, asdDialog);
        cancel.setOnClickListener(cancel_l);
    }

    public void setCon(String str2) {
        str = str2;
    }
}