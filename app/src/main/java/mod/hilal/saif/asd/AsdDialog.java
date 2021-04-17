package mod.hilal.saif.asd;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.besome.sketch.editor.LogicEditorActivity;

import a.a.a.Ss;
import a.a.a.bB;
import io.github.rosemoe.editor.langs.EmptyLanguage;
import io.github.rosemoe.editor.langs.desc.CDescription;
import io.github.rosemoe.editor.langs.desc.CppDescription;
import io.github.rosemoe.editor.langs.desc.JavaScriptDescription;
import io.github.rosemoe.editor.langs.java.JavaLanguage;
import io.github.rosemoe.editor.langs.universal.UniversalLanguage;
import io.github.rosemoe.editor.widget.CodeEditor;
import mod.hey.studios.code.ResHelper;
import mod.hey.studios.code.SrcCodeEditor;
import mod.hey.studios.util.Helper;

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
        setContentView(2131427825);
        ResHelper.isInASD = true;
        codeEditor = findViewById(2131231015);
        codeEditor.setTypefaceText(Typeface.MONOSPACE);
        codeEditor.setOverScrollEnabled(false);
        /* codeEditor.setEdgeEnabled(false); */
        codeEditor.setEditorLanguage(new JavaLanguage());
        codeEditor.setText(str);
        SrcCodeEditor.loadCESettings(act, codeEditor, "dlg");
        pref = SrcCodeEditor.pref;
        ImageView imageView = findViewById(2131231541);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeEditor.undo();
            }
        });
        ImageView imageView2 = findViewById(2131231540);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeEditor.redo();
            }
        });
        ImageView imageView3 = findViewById(2131232627);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(act, v);
                populateMenu(popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        _onMenuItemClick(item);
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        Helper.applyRipple(act, imageView);
        Helper.applyRipple(act, imageView2);
        Helper.applyRipple(act, imageView3);
        findViewById(2131232528).setVisibility(8);
        codeEditor.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1.0f));
        base = (ViewGroup) codeEditor.getParent();
        addControll();
        getWindow().setLayout(-1, -1);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setOnDismissListener(this);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        pref.edit().putInt("dlg_ts", (int) (codeEditor.getTextSizePx() / act.getResources().getDisplayMetrics().scaledDensity)).commit();
    }

    public void _onMenuItemClick(MenuItem item) {
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
                boolean z = false;
                String sb2 = sb.toString();
                try {
                    sb2 = SrcCodeEditor.j(sb2);
                } catch (Exception e) {
                    z = true;
                    bB.a(act, "Error: Your code contains incorrectly nested parentheses", 0).show();
                }
                if (!z) {
                    codeEditor.setText(sb2);
                }
                break;

            case "Switch language":
                AlertDialog.Builder title = new AlertDialog.Builder(act).setTitle("Switch language");
                String[] strArr = new String[5];
                strArr[0] = "C";
                strArr[1] = "C++";
                strArr[2] = "Java";
                strArr[3] = "JavaScript";
                strArr[4] = "None";
                title.setSingleChoiceItems(strArr, -1, new OnClickListener() {
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
                            codeEditor.setEditorLanguage(new EmptyLanguage());
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton(17039360, null).show();
                break;

            case "Find & Replace":
                codeEditor.getSearcher().stopSearch();
                codeEditor.beginSearchMode();
                break;

            case "Switch theme":
                new AlertDialog.Builder(act).setTitle("Switch theme").setSingleChoiceItems(new String[]{"Default", "GitHub", "Eclipse", "Darcula", "VS2019", "NotepadXX"}, -1, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SrcCodeEditor.selectTheme(codeEditor, which);
                        AsdDialog.pref.edit().putInt("dlg_theme", which).commit();
                        if (isDark()) {
                            lin.setBackgroundColor(Color.parseColor("#FF292929"));
                            save.setBackground(new GradientDrawable() {
                                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                    setCornerRadius((float) i);
                                    setStroke(i2, i3);
                                    setColor(i4);
                                    return this;
                                }
                            }.getIns((int) getDip(4), 0, -13421773, -13421773));
                            cancel.setBackground(new GradientDrawable() {
                                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                    setCornerRadius((float) i);
                                    setStroke(i2, i3);
                                    setColor(i4);
                                    return this;
                                }
                            }.getIns((int) getDip(4), 0, -13421773, -13421773));
                        } else {
                            lin.setBackgroundColor(-1);
                            save.setBackground(new GradientDrawable() {
                                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                    setCornerRadius((float) i);
                                    setStroke(i2, i3);
                                    setColor(i4);
                                    return this;
                                }
                            }.getIns((int) getDip(4), 0, -14575885, -14575885));
                            cancel.setBackground(new GradientDrawable() {
                                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                    setCornerRadius((float) i);
                                    setStroke(i2, i3);
                                    setColor(i4);
                                    return this;
                                }
                            }.getIns((int) getDip(4), 0, -14575885, -14575885));
                        }
                        dialog.dismiss();
                    }
                }).setNegativeButton(17039360, null).show();
                break;

            case "Word wrap":
                item.setChecked(!item.isChecked());
                codeEditor.setWordwrap(item.isChecked());
                pref.edit().putBoolean("dlg_ww", item.isChecked()).commit();
                break;

            case "Auto complete":
                item.setChecked(!item.isChecked());
                //codeEditor.isAutoCompleteEnabled = item.isChecked();
                pref.edit().putBoolean("dlg_ac", item.isChecked()).commit();
                break;

            case "Paste":
                codeEditor.setText(SrcCodeEditor.paste(act));
                break;
        }
    }

    public void populateMenu(Menu menu) {
        menu.add(0, 0, 0, "Paste");
        menu.add(0, 4, 0, "Word wrap").setCheckable(true).setChecked(pref.getBoolean("dlg_ww", false));
        menu.add(0, 5, 0, "Pretty print");
        menu.add(0, 6, 0, "Switch language");
        menu.add(0, 7, 0, "Switch theme");
        menu.add(0, 8, 0, "Auto complete").setCheckable(true).setChecked(pref.getBoolean("dlg_ac", true));
    }

    public boolean isThemeDark(int theme) {
        return theme == 3 || theme == 4;
    }

    public boolean isDark() {
        return isThemeDark(pref.getInt("dlg_theme", 3));
    }

    public void addControll() {
        lin = new LinearLayout(getContext());
        lin.setLayoutParams(new LinearLayout.LayoutParams(-1, -2, 0.0f));
        lin.setPadding((int) getDip(0), (int) getDip(0), (int) getDip(0), (int) getDip(0));
        lin.setOrientation(LinearLayout.HORIZONTAL);
        if (isDark()) {
            lin.setBackgroundColor(Color.parseColor("#FF292929"));
        } else {
            lin.setBackgroundColor(-1);
        }
        cancel = new TextView(getContext());
        cancel.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        ((LinearLayout.LayoutParams) cancel.getLayoutParams()).setMargins((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));
        cancel.setText("Cancel");
        cancel.setTextColor(-1);
        cancel.setPadding((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));
        cancel.setGravity(17);
        if (isDark()) {
            cancel.setBackgroundColor(-13421773);
        } else {
            cancel.setBackgroundColor(-16740915);
        }
        cancel.setTextSize((float) 15);
        lin.addView(cancel);
        save = new TextView(getContext());
        save.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        ((LinearLayout.LayoutParams) save.getLayoutParams()).setMargins((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));
        save.setText("Save");
        save.setTextColor(-1);
        save.setPadding((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));
        save.setGravity(17);
        if (isDark()) {
            save.setBackgroundColor(-13421773);
        } else {
            save.setBackgroundColor(-16740915);
        }
        save.setTextSize((float) 15);
        lin.addView(save);
        if (isDark()) {
            save.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                    setCornerRadius((float) i);
                    setStroke(i2, i3);
                    setColor(i4);
                    return this;
                }
            }.getIns((int) getDip(4), 0, -13421773, -13421773));
            cancel.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                    setCornerRadius((float) i);
                    setStroke(i2, i3);
                    setColor(i4);
                    return this;
                }
            }.getIns((int) getDip(4), 0, -13421773, -13421773));
        } else {
            save.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                    setCornerRadius((float) i);
                    setStroke(i2, i3);
                    setColor(i4);
                    return this;
                }
            }.getIns((int) getDip(4), 0, -14575885, -14575885));
            cancel.setBackground(new GradientDrawable() {
                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                    setCornerRadius((float) i);
                    setStroke(i2, i3);
                    setColor(i4);
                    return this;
                }
            }.getIns((int) getDip(4), 0, -14575885, -14575885));
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