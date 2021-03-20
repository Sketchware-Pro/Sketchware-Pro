package mod.hilal.saif.asd.old;

import static mod.SketchwareUtil.getDip;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besome.sketch.editor.LogicEditorActivity;

import java.util.Timer;
import java.util.TimerTask;

import a.a.a.Ss;
import mod.hey.studios.lib.code_editor.CodeEditorEditText;
import mod.hey.studios.lib.code_editor.CodeEditorLayout;
import mod.hey.studios.lib.code_editor.ColorScheme;

public class AsdOldDialog extends Dialog {
    
    public Timer _timer = new Timer();
    public Activity act;
    public ViewGroup base;
    public TextView cancel;
    public View.OnClickListener cancel_l = null;
    public CodeEditorLayout codeEditor;
    public CodeEditorEditText edito;
    public TextView save;
    public View.OnClickListener save_l = null;
    public String str;
    public TextView title;
    public TimerTask uu;
    public TextView zoomin;
    public TextView zoomout;

    public AsdOldDialog(Activity activity) {
        super(activity);
        act = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427787);
        codeEditor = (CodeEditorLayout) findViewById(2131232367);
        zoomin = (TextView) findViewById(2131232455);
        zoomin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeEditor.increaseTextSize();
            }
        });
        zoomout = (TextView) findViewById(2131232456);
        zoomout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeEditor.decreaseTextSize();
            }
        });
        codeEditor.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1.0f));
        base = (ViewGroup) codeEditor.getParent();
        base.setBackground(new GradientDrawable() {
            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius((float) i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns((int) getDip(4), 0, -1, -1));
        title = (TextView) findViewById(2131232366);
        title.setText("Code Editor");
        addControll();
        getWindow().setLayout(-1, -1);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        codeEditor.start(ColorScheme.JAVA());
        codeEditor.setText(str);
        edito = codeEditor.getEditText();
        codeEditor.onCreateOptionsMenu(findViewById(2131232504));
        edito.setInputType(655361);
        edito.setImeOptions(1);
    }

    public void addControll() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2, 0.0f));
        linearLayout.setPadding((int) getDip(0), (int) getDip(0), (int) getDip(0), (int) getDip(0));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        if (codeEditor.dark_theme) {
            linearLayout.setBackgroundColor(Color.parseColor("#FF292929"));
        } else {
            linearLayout.setBackgroundColor(-1);
        }
        cancel = new TextView(getContext());
        cancel.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        ((LinearLayout.LayoutParams) cancel.getLayoutParams()).setMargins((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));
        cancel.setText("Cancel");
        cancel.setTextColor(-1);
        cancel.setPadding((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));
        cancel.setGravity(17);
        if (codeEditor.dark_theme) {
            cancel.setBackgroundColor(-13421773);
        } else {
            cancel.setBackgroundColor(-16740915);
        }
        cancel.setTextSize((float) 15);
        linearLayout.addView(cancel);
        save = new TextView(getContext());
        save.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        ((LinearLayout.LayoutParams) save.getLayoutParams()).setMargins((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));
        save.setText("Save");
        save.setTextColor(-1);
        save.setPadding((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));
        save.setGravity(17);
        if (codeEditor.dark_theme) {
            save.setBackgroundColor(-13421773);
        } else {
            save.setBackgroundColor(-16740915);
        }
        save.setTextSize((float) 15);
        linearLayout.addView(save);
        if (codeEditor.dark_theme) {
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
        save.setElevation((float) ((int) getDip(1)));
        cancel.setElevation((float) ((int) getDip(1)));
        base.addView(linearLayout);
        uu = new TimerTask() {
            @Override
            public void run() {
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (codeEditor.dark_theme) {
                            linearLayout.setBackgroundColor(Color.parseColor("#FF292929"));
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
                            return;
                        }
                        linearLayout.setBackgroundColor(-1);
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
                });
            }
        };
        _timer.scheduleAtFixedRate(uu, (long) 500, (long) 500);
    }

    public void saveLis(LogicEditorActivity logicEditorActivity, boolean z, Ss ss, AsdOldDialog asdOldDialog) {
        save_l = new AsdOldHandlerCodeEditor(logicEditorActivity, codeEditor.getText(), z, ss, asdOldDialog, edito);
        save.setOnClickListener(save_l);
    }

    public void cancelLis(LogicEditorActivity logicEditorActivity, AsdOldDialog asdOldDialog) {
        cancel_l = new AsdOldHandlerCodeEditorCancel(logicEditorActivity, edito, asdOldDialog);
        cancel.setOnClickListener(cancel_l);
    }

    public void setCon(String str2) {
        str = str2;
    }
}