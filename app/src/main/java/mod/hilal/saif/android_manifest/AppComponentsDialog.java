package mod.hilal.saif.android_manifest;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import mod.hey.studios.lib.code_editor.CodeEditorEditText;
import mod.hey.studios.lib.code_editor.CodeEditorLayout;
import mod.hey.studios.lib.code_editor.ColorScheme;
import mod.hilal.saif.lib.FileUtil;

public class AppComponentsDialog extends Dialog {
    public Timer _timer = new Timer();
    public Activity act;
    public ViewGroup base;
    public TextView cancel;
    public CodeEditorLayout codeEditor;
    public CodeEditorEditText edito;
    public String s;
    public TextView save;
    public String str;
    public TextView title;
    public TimerTask uu;
    public TextView zoomin;
    public TextView zoomout;

    public AppComponentsDialog(Activity activity, String str2) {
        super(activity);
        this.act = activity;
        this.s = str2;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427787);
        this.codeEditor = findViewById(2131232367);
        this.zoomin = (TextView) findViewById(2131232455);
        this.zoomin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                AppComponentsDialog.this.codeEditor.increaseTextSize();
            }
        });
        this.zoomout = (TextView) findViewById(2131232456);
        this.zoomout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                AppComponentsDialog.this.codeEditor.decreaseTextSize();
            }
        });
        this.codeEditor.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 1.0f));
        this.base = (ViewGroup) this.codeEditor.getParent();
        this.base.setBackground(new GradientDrawable() {

            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                setCornerRadius((float) i);
                setStroke(i2, i3);
                setColor(i4);
                return this;
            }
        }.getIns((int) getDip(4), 0, -1, -1));
        this.title = (TextView) findViewById(2131232366);
        this.title.setText("App Components");
        addControll();
        setListeners();
        getWindow().setLayout(-1, -1);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.codeEditor.start(ColorScheme.XML());
        setCodeEditorText();
        this.edito = this.codeEditor.getEditText();
        this.codeEditor.onCreateOptionsMenu(findViewById(2131232504));
        this.edito.setInputType(655361);
        this.edito.setImeOptions(1);
    }

    public void addControll() {
        final LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2, 0.0f));
        linearLayout.setPadding((int) getDip(0), (int) getDip(0), (int) getDip(0), (int) getDip(0));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        if (this.codeEditor.dark_theme) {
            linearLayout.setBackgroundColor(Color.parseColor("#FF292929"));
        } else {
            linearLayout.setBackgroundColor(-1);
        }
        this.cancel = new TextView(getContext());
        this.cancel.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        ((LinearLayout.LayoutParams) this.cancel.getLayoutParams()).setMargins((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));
        this.cancel.setText("cancel");
        this.cancel.setTextColor(-1);
        this.cancel.setPadding((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));
        this.cancel.setGravity(17);
        if (this.codeEditor.dark_theme) {
            this.cancel.setBackgroundColor(-13421773);
        } else {
            this.cancel.setBackgroundColor(-16740915);
        }
        this.cancel.setTextSize(15.0f);
        linearLayout.addView(this.cancel);
        this.save = new TextView(getContext());
        this.save.setLayoutParams(new LinearLayout.LayoutParams(0, -2, 1.0f));
        ((LinearLayout.LayoutParams) this.save.getLayoutParams()).setMargins((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));
        this.save.setText("save");
        this.save.setTextColor(-1);
        this.save.setPadding((int) getDip(8), (int) getDip(8), (int) getDip(8), (int) getDip(8));
        this.save.setGravity(17);
        if (this.codeEditor.dark_theme) {
            this.save.setBackgroundColor(-13421773);
        } else {
            this.save.setBackgroundColor(-16740915);
        }
        this.save.setTextSize(15.0f);
        linearLayout.addView(this.save);
        if (this.codeEditor.dark_theme) {
            this.save.setBackground(new GradientDrawable() {

                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                    setCornerRadius((float) i);
                    setStroke(i2, i3);
                    setColor(i4);
                    return this;
                }
            }.getIns((int) getDip(4), 0, -13421773, -13421773));
            this.cancel.setBackground(new GradientDrawable() {

                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                    setCornerRadius((float) i);
                    setStroke(i2, i3);
                    setColor(i4);
                    return this;
                }
            }.getIns((int) getDip(4), 0, -13421773, -13421773));
        } else {
            this.save.setBackground(new GradientDrawable() {

                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                    setCornerRadius((float) i);
                    setStroke(i2, i3);
                    setColor(i4);
                    return this;
                }
            }.getIns((int) getDip(4), 0, -14575885, -14575885));
            this.cancel.setBackground(new GradientDrawable() {

                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                    setCornerRadius((float) i);
                    setStroke(i2, i3);
                    setColor(i4);
                    return this;
                }
            }.getIns((int) getDip(4), 0, -14575885, -14575885));
        }
        this.save.setElevation((float) ((int) getDip(1)));
        this.cancel.setElevation((float) ((int) getDip(1)));
        this.base.addView(linearLayout);
        this.uu = new TimerTask() {

            public void run() {
                Activity activity = AppComponentsDialog.this.act;
                final LinearLayout LinearLayout = linearLayout;
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (AppComponentsDialog.this.codeEditor.dark_theme) {
                            linearLayout.setBackgroundColor(Color.parseColor("#FF292929"));
                            AppComponentsDialog.this.save.setBackground(new GradientDrawable() {

                                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                    setCornerRadius((float) i);
                                    setStroke(i2, i3);
                                    setColor(i4);
                                    return this;
                                }
                            }.getIns((int) AppComponentsDialog.this.getDip(4), 0, -13421773, -13421773));
                            AppComponentsDialog.this.cancel.setBackground(new GradientDrawable() {

                                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                    setCornerRadius((float) i);
                                    setStroke(i2, i3);
                                    setColor(i4);
                                    return this;
                                }
                            }.getIns((int) AppComponentsDialog.this.getDip(4), 0, -13421773, -13421773));
                            return;
                        }
                        linearLayout.setBackgroundColor(-1);
                        AppComponentsDialog.this.save.setBackground(new GradientDrawable() {

                            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                setCornerRadius((float) i);
                                setStroke(i2, i3);
                                setColor(i4);
                                return this;
                            }
                        }.getIns((int) AppComponentsDialog.this.getDip(4), 0, -14575885, -14575885));
                        AppComponentsDialog.this.cancel.setBackground(new GradientDrawable() {

                            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                setCornerRadius((float) i);
                                setStroke(i2, i3);
                                setColor(i4);
                                return this;
                            }
                        }.getIns((int) AppComponentsDialog.this.getDip(4), 0, -14575885, -14575885));
                    }
                });
            }
        };
        this._timer.scheduleAtFixedRate(this.uu, 500, 500);
    }

    public void setListeners() {
        this.save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                FileUtil.writeFile(FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(AppComponentsDialog.this.s).concat("/Injection/androidmanifest/app_components.txt"), AppComponentsDialog.this.codeEditor.getText());
                ((InputMethodManager) AppComponentsDialog.this.act.getApplicationContext().getSystemService("input_method")).hideSoftInputFromWindow(AppComponentsDialog.this.edito.getWindowToken(), 0);
                AppComponentsDialog.this.warn("saved");
                AppComponentsDialog.this.dismiss();
            }
        });
        this.cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                ((InputMethodManager) AppComponentsDialog.this.act.getApplicationContext().getSystemService("input_method")).hideSoftInputFromWindow(AppComponentsDialog.this.edito.getWindowToken(), 0);
                AppComponentsDialog.this.dismiss();
            }
        });
    }

    public void setCodeEditorText() {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(this.s).concat("/Injection/androidmanifest/app_components.txt");
        if (FileUtil.isExistFile(concat)) {
            this.str = FileUtil.readFile(concat);
            this.codeEditor.setText(this.str);
            return;
        }
        this.codeEditor.setText("");
    }

    public void show() {
        super.show();
    }

    public void warn(String str2) {
        Toast.makeText(getContext(), str2, Toast.LENGTH_SHORT).show();
    }

    public float getDip(int i) {
        return get_dip(getContext(), i);
    }

    public static float get_dip(Context context, int i) {
        return TypedValue.applyDimension(1, (float) i, context.getResources().getDisplayMetrics());
    }
}
