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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import mod.hey.studios.lib.code_editor.CodeEditorEditText;
import mod.hey.studios.lib.code_editor.CodeEditorLayout;
import mod.hey.studios.lib.code_editor.ColorScheme;
import mod.hilal.saif.lib.FileUtil;

public class ActComponentsDialog extends Dialog {
    public Timer _timer = new Timer();
    public Activity act;
    public String actName;
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

    public ActComponentsDialog(Activity activity, String str2, String str3) {
        super(activity);
        this.act = activity;
        this.s = str2;
        this.actName = str3;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427787);
        this.codeEditor = findViewById(2131232367);
        this.zoomin = (TextView) findViewById(2131232455);
        this.zoomin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                ActComponentsDialog.this.codeEditor.increaseTextSize();
            }
        });
        this.zoomout = (TextView) findViewById(2131232456);
        this.zoomout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                ActComponentsDialog.this.codeEditor.decreaseTextSize();
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
        this.title.setText(String.valueOf(this.actName) + " Components");
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
                Activity activity = ActComponentsDialog.this.act;
                final LinearLayout LinearLayout = linearLayout;
                activity.runOnUiThread(new Runnable() {

                    public void run() {
                        if (ActComponentsDialog.this.codeEditor.dark_theme) {
                            linearLayout.setBackgroundColor(Color.parseColor("#FF292929"));
                            ActComponentsDialog.this.save.setBackground(new GradientDrawable() {

                                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                    setCornerRadius((float) i);
                                    setStroke(i2, i3);
                                    setColor(i4);
                                    return this;
                                }
                            }.getIns((int) ActComponentsDialog.this.getDip(4), 0, -13421773, -13421773));
                            ActComponentsDialog.this.cancel.setBackground(new GradientDrawable() {

                                public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                    setCornerRadius((float) i);
                                    setStroke(i2, i3);
                                    setColor(i4);
                                    return this;
                                }
                            }.getIns((int) ActComponentsDialog.this.getDip(4), 0, -13421773, -13421773));
                            return;
                        }
                        linearLayout.setBackgroundColor(-1);
                        ActComponentsDialog.this.save.setBackground(new GradientDrawable() {

                            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                setCornerRadius((float) i);
                                setStroke(i2, i3);
                                setColor(i4);
                                return this;
                            }
                        }.getIns((int) ActComponentsDialog.this.getDip(4), 0, -14575885, -14575885));
                        ActComponentsDialog.this.cancel.setBackground(new GradientDrawable() {

                            public GradientDrawable getIns(int i, int i2, int i3, int i4) {
                                setCornerRadius((float) i);
                                setStroke(i2, i3);
                                setColor(i4);
                                return this;
                            }
                        }.getIns((int) ActComponentsDialog.this.getDip(4), 0, -14575885, -14575885));
                    }
                });
            }
        };
        this._timer.scheduleAtFixedRate(this.uu, 500, 500);
    }

    public void setListeners() {
        this.save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(ActComponentsDialog.this.s).concat("/Injection/androidmanifest/activities_components.json");
                ArrayList arrayList = new ArrayList();
                if (FileUtil.isExistFile(concat)) {
                    ArrayList arrayList2 = (ArrayList) new Gson().fromJson(FileUtil.readFile(concat), new TypeToken<ArrayList<HashMap<String, Object>>>() {
                    }.getType());
                    for (int i = 0; i < arrayList2.size(); i++) {
                        if (((String) ((HashMap) arrayList2.get(i)).get("name")).equals(ActComponentsDialog.this.actName)) {
                            ((HashMap) arrayList2.get(i)).put("value", ActComponentsDialog.this.codeEditor.getText());
                            FileUtil.writeFile(concat, new Gson().toJson(arrayList2));
                            ((InputMethodManager) ActComponentsDialog.this.act.getApplicationContext().getSystemService("input_method")).hideSoftInputFromWindow(ActComponentsDialog.this.edito.getWindowToken(), 0);
                            ActComponentsDialog.this.warn("saved");
                            ActComponentsDialog.this.dismiss();
                            return;
                        }
                    }
                    HashMap hashMap = new HashMap();
                    hashMap.put("name", ActComponentsDialog.this.actName);
                    hashMap.put("value", ActComponentsDialog.this.codeEditor.getText());
                    arrayList2.add(hashMap);
                    FileUtil.writeFile(concat, new Gson().toJson(arrayList2));
                    ((InputMethodManager) ActComponentsDialog.this.act.getApplicationContext().getSystemService("input_method")).hideSoftInputFromWindow(ActComponentsDialog.this.edito.getWindowToken(), 0);
                    ActComponentsDialog.this.dismiss();
                    return;
                }
                HashMap hashMap2 = new HashMap();
                hashMap2.put("name", ActComponentsDialog.this.actName);
                hashMap2.put("value", ActComponentsDialog.this.codeEditor.getText());
                arrayList.add(hashMap2);
                FileUtil.writeFile(concat, new Gson().toJson(arrayList));
                ((InputMethodManager) ActComponentsDialog.this.act.getApplicationContext().getSystemService("input_method")).hideSoftInputFromWindow(ActComponentsDialog.this.edito.getWindowToken(), 0);
                ActComponentsDialog.this.dismiss();
            }
        });
        this.cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                ((InputMethodManager) ActComponentsDialog.this.act.getApplicationContext().getSystemService("input_method")).hideSoftInputFromWindow(ActComponentsDialog.this.edito.getWindowToken(), 0);
                ActComponentsDialog.this.dismiss();
            }
        });
    }

    public void setCodeEditorText() {
        String concat = FileUtil.getExternalStorageDir().concat("/.sketchware/data/").concat(this.s).concat("/Injection/androidmanifest/activities_components.json");
        new ArrayList();
        if (FileUtil.isExistFile(concat)) {
            ArrayList arrayList = (ArrayList) new Gson().fromJson(FileUtil.readFile(concat), new TypeToken<ArrayList<HashMap<String, Object>>>() {
            }.getType());
            for (int i = 0; i < arrayList.size(); i++) {
                if (((String) ((HashMap) arrayList.get(i)).get("name")).equals(this.actName)) {
                    this.codeEditor.setText((String) ((HashMap) arrayList.get(i)).get("value"));
                    return;
                }
            }
            return;
        }
        this.codeEditor.setText("");
    }

    public void show() {
        super.show();
    }

    public float getDip(int i) {
        return get_dip(getContext(), i);
    }

    public static float get_dip(Context context, int i) {
        return TypedValue.applyDimension(1, (float) i, context.getResources().getDisplayMetrics());
    }

    public void warn(String str2) {
        Toast.makeText(getContext(), str2, Toast.LENGTH_SHORT).show();
    }
}
