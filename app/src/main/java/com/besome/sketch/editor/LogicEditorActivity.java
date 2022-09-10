package com.besome.sketch.editor;

import static mod.SketchwareUtil.getDip;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.Vibrator;
import android.text.InputType;
import android.util.Pair;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.BlockCollectionBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.HistoryBlockBean;
import com.besome.sketch.beans.MoreBlockCollectionBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.ViewBean;
import com.besome.sketch.editor.component.ComponentAddActivity;
import com.besome.sketch.editor.logic.BlockPane;
import com.besome.sketch.editor.logic.LogicTopMenu;
import com.besome.sketch.editor.logic.PaletteBlock;
import com.besome.sketch.editor.logic.PaletteSelector;
import com.besome.sketch.editor.makeblock.MakeBlockActivity;
import com.besome.sketch.editor.manage.ShowBlockCollectionActivity;
import com.besome.sketch.editor.view.ViewDummy;
import com.besome.sketch.editor.view.ViewLogicEditor;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import a.a.a.DB;
import a.a.a.FB;
import a.a.a.Fx;
import a.a.a.GB;
import a.a.a.Gx;
import a.a.a.Lx;
import a.a.a.MA;
import a.a.a.Mp;
import a.a.a.NB;
import a.a.a.Np;
import a.a.a.Op;
import a.a.a.Pp;
import a.a.a.Qp;
import a.a.a.Rs;
import a.a.a.Ss;
import a.a.a.Ts;
import a.a.a.Us;
import a.a.a.Vs;
import a.a.a.ZB;
import a.a.a.Zx;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.bC;
import a.a.a.eC;
import a.a.a.jC;
import a.a.a.kC;
import a.a.a.kq;
import a.a.a.mB;
import a.a.a.oB;
import a.a.a.sq;
import a.a.a.uq;
import a.a.a.wB;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.xq;
import a.a.a.yq;
import a.a.a.yy;
import dev.aldi.sayuti.block.ExtraPaletteBlock;
import io.github.rosemoe.sora.langs.java.JavaLanguage;
import io.github.rosemoe.sora.widget.CodeEditor;
import io.github.rosemoe.sora.widget.component.Magnifier;
import io.github.rosemoe.sora.widget.schemes.EditorColorScheme;
import mod.hasrat.menu.ExtraMenuBean;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hey.studios.moreblock.importer.MoreblockImporterDialog;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.asd.asdforall.AsdAll;

public class LogicEditorActivity extends BaseAppCompatActivity implements View.OnClickListener, Vs, View.OnTouchListener, MoreblockImporterDialog.CallBack {
    public Vibrator F;
    public boolean G;
    public DB H;
    public LinearLayout J;
    public LinearLayout K;
    public FloatingActionButton L;
    public ProjectFileBean M;
    public LogicTopMenu N;
    public LogicEditorDrawer O;
    public ObjectAnimator U;
    public ObjectAnimator V;
    public ObjectAnimator ba;
    public ObjectAnimator ca;
    public ExtraPaletteBlock extraPaletteBlock;
    public ObjectAnimator fa;
    public ObjectAnimator ga;
    public ArrayList<Pair<Integer, String>> ja;
    public Toolbar k;
    public ArrayList<Pair<Integer, String>> ka;
    public PaletteSelector l;
    public ArrayList<ProjectResourceBean> la;
    public PaletteBlock m;
    public ArrayList<ProjectResourceBean> ma;
    public ViewLogicEditor n;
    public ArrayList<ProjectResourceBean> na;
    public BlockPane o;
    public ViewDummy p;
    public ArrayList<MoreBlockCollectionBean> pa;
    public Rs w;
    public int x;
    public int y;
    public float q = 0.0f;
    public float r = 0.0f;
    public float s = 0.0f;
    public float t = 0.0f;
    public boolean u = false;
    public int[] v = new int[2];
    public int[] z = new int[2];
    public int A = 0;
    public String B = "";
    public String C = "";
    public String D = "";
    public String E = "";
    public oB P = new oB();
    public int S = 0;
    public int T = -30;
    public boolean W = false;
    public boolean X = false;
    public View Y = null;
    public final Handler Z = new Handler();
    public Runnable aa = this::r;
    public boolean da = false;
    public boolean ea = false;
    public boolean ha = false;
    public boolean ia = false;

    private static class ProjectSaver extends MA {
        private final WeakReference<LogicEditorActivity> activity;

        public ProjectSaver(LogicEditorActivity logicEditorActivity) {
            super(logicEditorActivity);
            this.activity = new WeakReference<>(logicEditorActivity);
            logicEditorActivity.a(this);
        }

        @Override
        public void a() {
            this.activity.get().h();
            this.activity.get().finish();
        }

        @Override
        public void a(String str) {
            Toast.makeText(this.a, xB.b().a(this.activity.get().getApplicationContext(), R.string.common_error_failed_to_save), Toast.LENGTH_SHORT).show();
            this.activity.get().h();
        }

        @Override
        public void b() {
            publishProgress("Now saving..");
            this.activity.get().E();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }

    public final void A() {
        ArrayList<BlockBean> a2 = jC.a(this.B).a(this.M.getJavaName(), this.C + "_" + this.D);
        if (a2 != null) {
            if (a2.size() == 0) {
                e(this.X);
            }

            boolean z = true;
            HashMap<Integer, Rs> hashMap = new HashMap<>();
            for (BlockBean next : a2) {
                if (this.D.equals("onTextChanged") && next.opCode.equals("getArg") && next.spec.equals("text")) {
                    next.spec = "charSeq";
                }
                Rs b2 = b(next);
                hashMap.put((Integer) b2.getTag(), b2);
                o.g = Math.max(o.g, (Integer) b2.getTag() + 1);
                this.o.a(b2, 0, 0);
                b2.setOnTouchListener(this);
                if (z) {
                    this.o.getRoot().b(b2);
                    z = false;
                }
            }
            for (BlockBean next2 : a2) {
                Rs block = hashMap.get(Integer.valueOf(next2.id));
                if (block != null) {
                    Rs subStack1RootBlock;
                    if (next2.subStack1 >= 0 && (subStack1RootBlock = hashMap.get(next2.subStack1)) != null) {
                        block.e(subStack1RootBlock);
                    }
                    Rs subStack2RootBlock;
                    if (next2.subStack2 >= 0 && (subStack2RootBlock = hashMap.get(next2.subStack2)) != null) {
                        block.f(subStack2RootBlock);
                    }
                    Rs nextBlock;
                    if (next2.nextBlock >= 0 && (nextBlock = hashMap.get(next2.nextBlock)) != null) {
                        block.b(nextBlock);
                    }
                    for (int i = 0; i < next2.parameters.size(); i++) {
                        String parameter = next2.parameters.get(i);
                        if (parameter != null && parameter.length() > 0) {
                            if (parameter.charAt(0) == '@') {
                                Rs parameterBlock = hashMap.get(Integer.valueOf(parameter.substring(1)));
                                if (parameterBlock != null) {
                                    block.a((Ts) block.V.get(i), parameterBlock);
                                }
                            } else {
                                ((Ss) block.V.get(i)).setArgValue(parameter);
                                block.m();
                            }
                        }
                    }
                }
            }
            this.o.getRoot().k();
            this.o.b();
        }
    }

    public final void B() {
        if (!u) {
            HistoryBlockBean historyBlockBean = bC.d(B).i(s());
            if (historyBlockBean != null) {
                int actionType = historyBlockBean.getActionType();
                if (actionType == HistoryBlockBean.ACTION_TYPE_ADD) {
                    int[] locationOnScreen = new int[2];
                    o.getLocationOnScreen(locationOnScreen);
                    a(historyBlockBean.getAddedData(), historyBlockBean.getCurrentX() + locationOnScreen[0], historyBlockBean.getCurrentY() + locationOnScreen[1], true);
                    if (historyBlockBean.getCurrentParentData() != null) {
                        a(historyBlockBean.getCurrentParentData(), true);
                    }
                } else if (actionType == HistoryBlockBean.ACTION_TYPE_UPDATE) {
                    a(historyBlockBean.getCurrentUpdateData(), true);
                } else if (actionType == HistoryBlockBean.ACTION_TYPE_REMOVE) {
                    ArrayList<BlockBean> removedData = historyBlockBean.getRemovedData();

                    for (int i = removedData.size() - 1; i >= 0; i--) {
                        o.a(removedData.get(i), false);
                    }
                    if (historyBlockBean.getCurrentParentData() != null) {
                        a(historyBlockBean.getCurrentParentData(), true);
                    }
                } else if (actionType == HistoryBlockBean.ACTION_TYPE_MOVE) {
                    for (BlockBean afterMoveData : historyBlockBean.getAfterMoveData()) {
                        o.a(afterMoveData, true);
                    }

                    int[] locationOnScreen = new int[2];
                    o.getLocationOnScreen(locationOnScreen);
                    a(historyBlockBean.getAfterMoveData(), historyBlockBean.getCurrentX() + locationOnScreen[0], historyBlockBean.getCurrentY() + locationOnScreen[1], true);
                    if (historyBlockBean.getCurrentParentData() != null) {
                        a(historyBlockBean.getCurrentParentData(), true);
                    }

                    if (historyBlockBean.getCurrentOriginalParent() != null) {
                        a(historyBlockBean.getCurrentOriginalParent(), true);
                    }
                }
            }
            invalidateOptionsMenu();
        }
    }

    public void C() {
        invalidateOptionsMenu();
    }

    public final void E() {
        eC a2 = jC.a(this.B);
        String javaName = this.M.getJavaName();
        a2.a(javaName, this.C + "_" + this.D, o.getBlocks());
    }

    public final void G() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_add_new_list));
        aBVar.a(R.drawable.add_96_blue);
        View a2 = wB.a(this, R.layout.logic_popup_add_list);
        RadioGroup radioGroup = a2.findViewById(R.id.rg_type);
        EditText editText = a2.findViewById(R.id.ed_input);
        ((TextInputLayout) a2.findViewById(R.id.ti_input)).setHint(xB.b().a(getApplicationContext(), R.string.logic_editor_hint_enter_variable_name));
        ((TextView) a2.findViewById(R.id.rb_int)).setText(xB.b().a(getApplicationContext(), R.string.logic_variable_type_number));
        ((TextView) a2.findViewById(R.id.rb_string)).setText(xB.b().a(getApplicationContext(), R.string.logic_variable_type_string));
        ((TextView) a2.findViewById(R.id.rb_map)).setText(xB.b().a(getApplicationContext(), R.string.logic_variable_type_map));
        ZB zb = new ZB(this.e, a2.findViewById(R.id.ti_input), uq.b, uq.a(), jC.a(this.B).a(this.M));
        editText.setPrivateImeOptions("defaultInputmode=english;");
        aBVar.a(a2);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_add), v -> {
            if (zb.b()) {
                int i = 1;
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (checkedRadioButtonId != R.id.rb_int) {
                    if (checkedRadioButtonId == R.id.rb_string) {
                        i = 2;
                    } else if (checkedRadioButtonId == R.id.rb_map) {
                        i = 3;
                    }
                }

                a(i, editText.getText().toString());
                mB.a(getApplicationContext(), editText);
                aBVar.dismiss();
            }
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> {
            mB.a(getApplicationContext(), editText);
            aBVar.dismiss();
        });
        aBVar.show();
    }

    private void showAddNewVariableDialog() {
        aB dialog = new aB(this);
        dialog.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_add_new_variable));
        dialog.a(R.drawable.add_96_blue);

        View customView = wB.a(this, R.layout.logic_popup_add_variable);
        RadioGroup radioGroup = customView.findViewById(R.id.rg_type);
        EditText editText = customView.findViewById(R.id.ed_input);
        ((TextInputLayout) customView.findViewById(R.id.ti_input)).setHint(xB.b().a(getApplicationContext(), R.string.logic_editor_hint_enter_variable_name));
        editText.setPrivateImeOptions("defaultInputmode=english;");
        ((TextView) customView.findViewById(R.id.rb_boolean)).setText(xB.b().a(getApplicationContext(), R.string.logic_variable_type_boolean));
        ((TextView) customView.findViewById(R.id.rb_int)).setText(xB.b().a(getApplicationContext(), R.string.logic_variable_type_number));
        ((TextView) customView.findViewById(R.id.rb_string)).setText(xB.b().a(getApplicationContext(), R.string.logic_variable_type_string));
        ((TextView) customView.findViewById(R.id.rb_map)).setText(xB.b().a(getApplicationContext(), R.string.logic_variable_type_map));
        ZB nameValidator = new ZB(getApplicationContext(), customView.findViewById(R.id.ti_input), uq.b, uq.a(), jC.a(this.B).a(this.M));
        dialog.a(customView);
        dialog.b(xB.b().a(getApplicationContext(), R.string.common_word_add), v -> {
            int variableType = 1;
            if (radioGroup.getCheckedRadioButtonId() == R.id.rb_boolean) {
                variableType = 0;
            } else if (radioGroup.getCheckedRadioButtonId() != R.id.rb_int) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rb_string) {
                    variableType = 2;
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_map) {
                    variableType = 3;
                }
            }

            if (nameValidator.b()) {
                b(variableType, editText.getText().toString());
                mB.a(getApplicationContext(), editText);
                dialog.dismiss();
            }
        });
        dialog.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> {
            mB.a(getApplicationContext(), editText);
            dialog.dismiss();
        });
        dialog.show();
    }

    public final void I() {
        this.pa = Pp.h().f();
        new MoreblockImporterDialog(this, this.pa, this).show();
    }

    public final void J() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_remove_list));
        aBVar.a(R.drawable.delete_96);
        View a2 = wB.a(this, R.layout.property_popup_selector_single);
        ViewGroup viewGroup = a2.findViewById(R.id.rg_content);
        for (Pair<Integer, String> list : jC.a(this.B).j(this.M.getJavaName())) {
            viewGroup.addView(e(list.second));
        }
        aBVar.a(a2);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_remove), v -> {
            int childCount = viewGroup.getChildCount();
            int i = 0;
            while (i < childCount) {
                RadioButton radioButton = (RadioButton) viewGroup.getChildAt(i);
                if (radioButton.isChecked()) {
                    if (!o.b(radioButton.getText().toString())) {
                        if (!jC.a(B).b(M.getJavaName(), radioButton.getText().toString(), C + "_" + D)) {
                            l(radioButton.getText().toString());
                        }
                    }
                    Toast.makeText(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.logic_editor_message_currently_used_list), Toast.LENGTH_SHORT).show();
                    return;
                }
                i++;
            }
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final void K() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_remove_variable));
        aBVar.a(R.drawable.delete_96);
        View a2 = wB.a(this, R.layout.property_popup_selector_single);
        ViewGroup viewGroup = a2.findViewById(R.id.rg_content);
        for (Pair<Integer, String> next : jC.a(this.B).k(this.M.getJavaName())) {
            RadioButton e = e(next.second);
            e.setTag(next.first);
            viewGroup.addView(e);
        }
        aBVar.a(a2);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_remove), v -> {
            int childCount = viewGroup.getChildCount();
            int i = 0;
            while (i < childCount) {
                RadioButton radioButton = (RadioButton) viewGroup.getChildAt(i);
                if (radioButton.isChecked()) {
                    if (!o.c(radioButton.getText().toString())) {
                        if (!jC.a(B).c(M.getJavaName(), radioButton.getText().toString(), C + "_" + D)) {
                            m(radioButton.getText().toString());
                        }
                    }
                    Toast.makeText(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.logic_editor_message_currently_used_variable), Toast.LENGTH_SHORT).show();
                    return;
                }
                i++;
            }
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public void L() {
        try {
            new Handler().postDelayed(() -> new ProjectSaver(this).execute(), 500L);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void M() {
        if (!this.u) {
            HistoryBlockBean var1 = bC.d(this.B).j(this.s());
            if (var1 == null) {
                this.C();
            } else {
                label59:
                {
                    BlockBean var5;
                    label58:
                    {
                        int var2 = var1.getActionType();
                        ArrayList<BlockBean> var3;
                        if (var2 == 0) {
                            var3 = var1.getAddedData();
                            var2 = var3.size();

                            while (true) {
                                --var2;
                                if (var2 < 0) {
                                    if (var1.getPrevParentData() == null) {
                                        break label59;
                                    }

                                    var1.getPrevParentData().print();
                                    break;
                                }

                                this.o.a(var3.get(var2), false);
                            }
                        } else {
                            if (var2 == 1) {
                                var5 = var1.getPrevUpdateData();
                                break label58;
                            }

                            if (var2 != 2) {
                                if (var2 != 3) {
                                    break label59;
                                }

                                for (BlockBean var8 : var1.getBeforeMoveData()) {
                                    this.o.a(var8, true);
                                }

                                int[] var7 = new int[2];
                                this.o.getLocationOnScreen(var7);
                                this.a(var1.getBeforeMoveData(), var1.getPrevX() + var7[0], var1.getPrevY() + var7[1], true);
                                if (var1.getPrevParentData() != null) {
                                    this.a(var1.getPrevParentData(), true);
                                }

                                if (var1.getPrevOriginalParent() == null) {
                                    break label59;
                                }

                                var5 = var1.getPrevOriginalParent();
                                break label58;
                            }

                            var3 = var1.getRemovedData();
                            int[] var4 = new int[2];
                            this.o.getLocationOnScreen(var4);
                            this.a(var3, var1.getCurrentX() + var4[0], var1.getCurrentY() + var4[1], true);
                            if (var1.getPrevParentData() == null) {
                                break label59;
                            }
                        }

                        var5 = var1.getPrevParentData();
                    }

                    this.a(var5, true);
                }

                this.C();
            }
        }
    }

    public Rs a(Rs rs, int i, int i2, boolean z) {
        Rs a2 = this.o.a(rs, i, i2, z);
        if (!z) {
            a2.setOnTouchListener(this);
        }
        return a2;
    }

    public View a(String str, String str2) {
        Ts a2 = this.m.a("", str, str2);
        a2.setTag(str2);
        a2.setClickable(true);
        a2.setOnTouchListener(this);
        return a2;
    }

    public final View a(String str, String str2, String str3) {
        Ts a2 = this.m.a(str, str2, str3);
        a2.setTag(str3);
        a2.setClickable(true);
        a2.setOnTouchListener(this);
        return a2;
    }

    public final View a(String str, String str2, String str3, String str4) {
        Ts a2 = this.m.a(str, str2, str3, str4);
        a2.setTag(str4);
        a2.setClickable(true);
        a2.setOnTouchListener(this);
        return a2;
    }

    public final LinearLayout a(String str, boolean z) {
        Uri fromFile;
        float a2 = wB.a(this, 1.0f);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (60.0f * a2)));
        linearLayout.setGravity(Gravity.CENTER | Gravity.LEFT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1.0f;
        textView.setLayoutParams(layoutParams);
        textView.setText(str);
        linearLayout.addView(textView);
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        int i = (int) (a2 * 48.0f);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(i, i));
        if (!"NONE".equals(str)) {
            if (z) {
                imageView.setImageResource(getResources().getIdentifier(str, "drawable", getApplicationContext().getPackageName()));
            } else {
                if (Build.VERSION.SDK_INT >= 24) {
                    fromFile = FileProvider.a(e, getApplicationContext().getPackageName() + ".provider", new File(jC.d(this.B).f(str)));
                } else {
                    fromFile = Uri.fromFile(new File(jC.d(this.B).f(str)));
                }
                Glide.with(this).load(fromFile).signature(kC.n()).error(R.drawable.ic_remove_grey600_24dp).into(imageView);
            }
            imageView.setBackgroundColor(0xffbdbdbd);
        } else {
            imageView.setBackgroundColor(Color.WHITE);
        }
        linearLayout.addView(imageView);
        return linearLayout;
    }

    public final ArrayList<BlockBean> a(ArrayList<BlockBean> arrayList, int i, int i2, boolean z) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        ArrayList<BlockBean> arrayList2 = new ArrayList<>();
        for (BlockBean next : arrayList) {
            if (next.id != null && !next.id.equals("")) {
                arrayList2.add(next.clone());
            }
        }
        for (BlockBean next2 : arrayList2) {
            if (Integer.parseInt(next2.id) >= 99000000) {
                o.g = o.g + 1;
                hashMap.put(Integer.valueOf(next2.id), o.g);
            } else {
                hashMap.put(Integer.valueOf(next2.id), Integer.valueOf(next2.id));
            }
        }
        int size = arrayList2.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            }
            BlockBean blockBean = arrayList2.get(size);
            if (!a(blockBean)) {
                arrayList2.remove(size);
                hashMap.remove(Integer.valueOf(blockBean.id));
            }
        }
        for (BlockBean block : arrayList2) {
            if (hashMap.containsKey(Integer.valueOf(block.id))) {
                block.id = String.valueOf(hashMap.get(Integer.valueOf(block.id)));
            } else {
                block.id = "";
            }
            for (int j = 0; j < block.parameters.size(); j++) {
                String parameter = block.parameters.get(j);
                if (parameter != null && parameter.length() > 0 && parameter.charAt(0) == '@') {
                    int parameterId = Integer.parseInt(parameter.substring(1));
                    int parameterAsBlockId = hashMap.containsKey(parameterId) ? hashMap.get(parameterId) : 0;
                    if (parameterAsBlockId >= 0) {
                        block.parameters.set(j, '@' + String.valueOf(parameterAsBlockId));
                    } else {
                        block.parameters.set(j, "");
                    }
                }
            }
            if (block.subStack1 >= 0 && hashMap.containsKey(block.subStack1)) {
                block.subStack1 = hashMap.get(block.subStack1);
            }
            if (block.subStack2 >= 0 && hashMap.containsKey(block.subStack2)) {
                block.subStack2 = hashMap.get(block.subStack2);
            }
            if (block.nextBlock >= 0 && hashMap.containsKey(block.nextBlock)) {
                block.nextBlock = hashMap.get(block.nextBlock);
            }
        }
        Rs firstBlock = null;
        for (int j = 0; j < arrayList2.size(); j++) {
            BlockBean blockBean = arrayList2.get(j);
            if (blockBean.id != null && !blockBean.id.equals("")) {
                Rs block = b(blockBean);
                if (j == 0) {
                    firstBlock = block;
                }
                this.o.a(block, i, i2);
                block.setOnTouchListener(this);
            }
        }
        for (BlockBean block : arrayList2) {
            if (block.id != null && !block.id.equals("")) {
                a(block, false);
            }
        }
        if (firstBlock != null && z) {
            firstBlock.p().k();
            this.o.b();
        }
        return arrayList2;
    }

    @Override
    public void a(int i, int i2) {
        this.extraPaletteBlock.setBlock(i, i2);
    }

    public void a(int i, String str) {
        jC.a(this.B).b(this.M.getJavaName(), i, str);
        a(1, 0xffcc5b22);
    }

    public final void a(Rs rs) {
        this.w = null;
        this.y = -1;
        this.x = 0;
        int[] iArr = new int[2];
        this.z = iArr;
        rs.getLocationOnScreen(iArr);
        Rs rs2 = rs.E;
        if (rs2 != null) {
            this.w = rs2;
        }
        Rs rs3 = this.w;
        if (rs3 == null) {
            return;
        }
        if (rs3.ha == (Integer) rs.getTag()) {
            this.x = 0;
        } else if (this.w.ia == (Integer) rs.getTag()) {
            this.x = 2;
        } else if (this.w.ja == (Integer) rs.getTag()) {
            this.x = 3;
        } else if (this.w.V.contains(rs)) {
            this.x = 5;
            this.y = this.w.V.indexOf(rs);
        }
    }

    public void a(Rs rs, float f, float f2) {
        for (View next : rs.V) {
            if ((next instanceof Ss) && next.getX() < f && next.getX() + next.getWidth() > f && next.getY() < f2 && next.getY() + next.getHeight() > f2) {
                new ExtraMenuBean(this).defineMenuSelector((Ss) next);
                return;
            }
        }
    }

    public void a(Ss ss, Object obj) {
        BlockBean clone = ss.E.getBean().clone();
        ss.setArgValue(obj);
        ss.E.m();
        ss.E.p().k();
        ss.E.pa.b();
        bC.d(this.B).a(s(), clone, ss.E.getBean().clone());
        C();
    }

    public final void a(Ss ss, String str) {
        aB var3;
        label54:
        {
            var3 = new aB(this);
            xB var4;
            Context var5;
            int var6;
            if (str.equals("property_image")) {
                var4 = xB.b();
                var5 = this.getApplicationContext();
                var6 = R.string.logic_editor_title_select_image;
            } else {
                if (!str.equals("property_background_resource")) {
                    break label54;
                }

                var4 = xB.b();
                var5 = this.getApplicationContext();
                var6 = R.string.logic_editor_title_select_image_background;
            }

            var3.b(var4.a(var5, var6));
        }

        var3.a(R.drawable.ic_picture_48dp);
        View var12 = wB.a(this, R.layout.property_popup_selector_color);
        RadioGroup var7 = var12.findViewById(R.id.rg);
        LinearLayout var11 = var12.findViewById(R.id.content);
        ArrayList<String> var8 = jC.d(this.B).m();
        if (xq.a(this.B) || xq.b(this.B)) {
            if ("property_image".equals(str)) {
                var8.add(0, "default_image");
            } else if ("property_background_resource".equals(str)) {
                var8.add(0, "NONE");
            }
        }

        for (String value : var8) {
            str = value;
            RadioButton var9 = this.c(str);
            var7.addView(var9);
            if (str.equals(ss.getArgValue())) {
                var9.setChecked(true);
            }

            LinearLayout var10;
            if ((xq.a(this.B) || xq.b(this.B)) && !str.equals("default_image") && !"NONE".equals(str)) {
                var10 = this.a(str, false);
            } else {
                var10 = this.a(str, true);
            }

            var10.setOnClickListener(v -> {
                RadioButton button = (RadioButton) var7.getChildAt(var11.indexOfChild(v));
                button.setChecked(true);
            });
            var11.addView(var10);
        }

        var3.a(var12);
        var3.b(xB.b().a(this.getApplicationContext(), R.string.common_word_save), v -> {
            int childCount = var7.getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    break;
                }
                RadioButton radioButton = (RadioButton) var7.getChildAt(i);
                if (radioButton.isChecked()) {
                    a(ss, radioButton.getTag());
                    break;
                }
                i++;
            }
            var3.dismiss();
        });
        var3.a(xB.b().a(this.getApplicationContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(var3));
        var3.show();
    }

    public void a(Ss ss, boolean z) {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), z ? R.string.logic_editor_title_enter_number_value : R.string.logic_editor_title_enter_string_value));
        aBVar.a(R.drawable.rename_96_blue);
        View a2 = wB.a(this, R.layout.property_popup_input_text);
        EditText editText = a2.findViewById(R.id.ed_input);
        if (z) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            editText.setMaxLines(1);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            editText.setImeOptions(EditorInfo.IME_ACTION_NONE);
        }
        editText.setText(ss.getArgValue().toString());
        aBVar.a(a2);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_save), v -> {
            String text = editText.getText().toString();
            emptyStringSetter:
            {
                if (z) {
                    try {
                        double d = Double.parseDouble(text);
                        if (!Double.isNaN(d) && !Double.isInfinite(d)) {
                            break emptyStringSetter;
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                } else if (text.length() > 0) {
                    if (text.charAt(0) == '@') {
                        text = " " + text;
                        break emptyStringSetter;
                    }
                }

                text = "";
            }

            a(ss, (Object) text);
            mB.a(getApplicationContext(), editText);
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> {
            mB.a(getApplicationContext(), editText);
            aBVar.dismiss();
        });
        aBVar.show();
    }

    public final void a(BlockBean blockBean, boolean z) {
        Rs block = o.a(blockBean.id);
        if (block != null) {
            block.ia = -1;
            block.ja = -1;
            block.ha = -1;

            for (int i = 0; i < blockBean.parameters.size(); i++) {
                String parameter = blockBean.parameters.get(i);
                if (parameter != null) {
                    if (parameter.length() > 0 && parameter.charAt(0) == '@') {
                        int blockId = Integer.parseInt(parameter.substring(1));
                        if (blockId > 0) {
                            Rs parameterBlock = o.a(blockId);
                            if (parameterBlock != null) {
                                block.a((Ts) block.V.get(i), parameterBlock);
                            }
                        }
                    } else {
                        if (block.V.get(i) instanceof Ss) {
                            Ss ss = (Ss) block.V.get(i);
                            String javaName = M.getJavaName();
                            String xmlName = M.getXmlName();
                            if (D.equals("onBindCustomView")) {
                                String customView = jC.a(B).c(M.getXmlName(), C).customView;
                                if (customView != null) {
                                    xmlName = ProjectFileBean.getXmlName(customView);
                                }
                            }

                            if (parameter.length() > 0) {
                                if (ss.b.equals("m")) {
                                    switch (ss.c) {
                                        case "varInt":
                                            jC.a(B).f(javaName, ExtraMenuBean.VARIABLE_TYPE_INTEGER, parameter);
                                            break;

                                        case "varBool":
                                            jC.a(B).f(javaName, ExtraMenuBean.VARIABLE_TYPE_BOOLEAN, parameter);
                                            break;

                                        case "varStr":
                                            jC.a(B).f(javaName, ExtraMenuBean.VARIABLE_TYPE_STRING, parameter);
                                            break;

                                        case "listInt":
                                            jC.a(B).e(javaName, ExtraMenuBean.LIST_TYPE_INTEGER, parameter);
                                            break;

                                        case "listStr":
                                            jC.a(B).e(javaName, ExtraMenuBean.LIST_TYPE_STRING, parameter);
                                            break;

                                        case "listMap":
                                            jC.a(B).e(javaName, ExtraMenuBean.LIST_TYPE_MAP, parameter);
                                            break;

                                        case "list":
                                            boolean b = jC.a(B).e(javaName, ExtraMenuBean.LIST_TYPE_INTEGER, parameter);
                                            if (!b) {
                                                b = jC.a(B).e(javaName, ExtraMenuBean.LIST_TYPE_STRING, parameter);
                                            }

                                            if (!b) {
                                                jC.a(B).e(javaName, ExtraMenuBean.LIST_TYPE_MAP, parameter);
                                            }
                                            break;

                                        case "view":
                                            jC.a(B).h(xmlName, parameter);
                                            break;

                                        case "textview":
                                            jC.a(B).g(xmlName, parameter);
                                            break;

                                        case "checkbox":
                                            jC.a(B).e(xmlName, parameter);
                                            break;

                                        case "imageview":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_IMAGEVIEW, parameter);
                                            break;

                                        case "seekbar":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_SEEKBAR, parameter);
                                            break;

                                        case "calendarview":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_CALENDARVIEW, parameter);
                                            break;

                                        case "adview":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_ADVIEW, parameter);
                                            break;

                                        case "listview":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_LISTVIEW, parameter);
                                            break;

                                        case "spinner":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_SPINNER, parameter);
                                            break;

                                        case "webview":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_WEBVIEW, parameter);
                                            break;

                                        case "switch":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_SWITCH, parameter);
                                            break;

                                        case "progressbar":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_PROGRESSBAR, parameter);
                                            break;

                                        case "mapview":
                                            jC.a(B).g(xmlName, ViewBean.VIEW_TYPE_WIDGET_MAPVIEW, parameter);
                                            break;

                                        case "intent":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_INTENT, parameter);
                                            break;

                                        case "file":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_SHAREDPREF, parameter);
                                            break;

                                        case "calendar":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_CALENDAR, parameter);
                                            break;

                                        case "timer":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_TIMERTASK, parameter);
                                            break;

                                        case "vibrator":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_VIBRATOR, parameter);
                                            break;

                                        case "dialog":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_DIALOG, parameter);
                                            break;

                                        case "mediaplayer":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_MEDIAPLAYER, parameter);
                                            break;

                                        case "soundpool":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_SOUNDPOOL, parameter);
                                            break;

                                        case "objectanimator":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_OBJECTANIMATOR, parameter);
                                            break;

                                        case "firebase":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_FIREBASE, parameter);
                                            break;

                                        case "firebaseauth":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH, parameter);
                                            break;

                                        case "firebasestorage":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE, parameter);
                                            break;

                                        case "gyroscope":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_GYROSCOPE, parameter);
                                            break;

                                        case "interstitialad":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_INTERSTITIAL_AD, parameter);
                                            break;

                                        case "requestnetwork":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_REQUEST_NETWORK, parameter);
                                            break;

                                        case "texttospeech":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_TEXT_TO_SPEECH, parameter);
                                            break;

                                        case "speechtotext":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_SPEECH_TO_TEXT, parameter);
                                            break;

                                        case "bluetoothconnect":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_BLUETOOTH_CONNECT, parameter);
                                            break;

                                        case "locationmanager":
                                            jC.a(B).d(javaName, ComponentBean.COMPONENT_TYPE_LOCATION_MANAGER, parameter);
                                            break;

                                        case "resource_bg":
                                        case "resource":
                                            for (String str : jC.d(B).m()) {
                                                // Like this in vanilla Sketchware. Don't ask me why.
                                                //noinspection StatementWithEmptyBody
                                                if (parameter.equals(str)) {
                                                }
                                            }
                                            break;

                                        case "activity":
                                            for (String str : jC.b(this.B).d()) {
                                                // Like this in vanilla Sketchware. Don't ask me why.
                                                //noinspection StatementWithEmptyBody
                                                if (parameter.equals(str.substring(str.indexOf(".java")))) {
                                                }
                                            }
                                            break;

                                        case "sound":
                                            for (String str : jC.d(this.B).p()) {
                                                // Like this in vanilla Sketchware. Don't ask me why.
                                                //noinspection StatementWithEmptyBody
                                                if (parameter.equals(str)) {
                                                }
                                            }
                                            break;

                                        case "videoad":
                                            jC.a(B).d(xmlName, ComponentBean.COMPONENT_TYPE_REWARDED_VIDEO_AD, parameter);
                                            break;

                                        case "progressdialog":
                                            jC.a(B).d(xmlName, ComponentBean.COMPONENT_TYPE_PROGRESS_DIALOG, parameter);
                                            break;

                                        case "datepickerdialog":
                                            jC.a(B).d(xmlName, ComponentBean.COMPONENT_TYPE_DATE_PICKER_DIALOG, parameter);
                                            break;

                                        case "timepickerdialog":
                                            jC.a(B).d(xmlName, ComponentBean.COMPONENT_TYPE_TIME_PICKER_DIALOG, parameter);
                                            break;

                                        case "notification":
                                            jC.a(B).d(xmlName, ComponentBean.COMPONENT_TYPE_NOTIFICATION, parameter);
                                            break;

                                        case "radiobutton":
                                            jC.a(B).g(xmlName, 19, parameter);
                                            break;

                                        case "ratingbar":
                                            jC.a(B).g(xmlName, 20, parameter);
                                            break;

                                        case "videoview":
                                            jC.a(B).g(xmlName, 21, parameter);
                                            break;

                                        case "searchview":
                                            jC.a(B).g(xmlName, 22, parameter);
                                            break;

                                        case "actv":
                                            jC.a(B).g(xmlName, 23, parameter);
                                            break;

                                        case "mactv":
                                            jC.a(B).g(xmlName, 24, parameter);
                                            break;

                                        case "gridview":
                                            jC.a(B).g(xmlName, 25, parameter);
                                            break;

                                        case "tablayout":
                                            jC.a(B).g(xmlName, 30, parameter);
                                            break;

                                        case "viewpager":
                                            jC.a(B).g(xmlName, 31, parameter);
                                            break;

                                        case "bottomnavigation":
                                            jC.a(B).g(xmlName, 32, parameter);
                                            break;

                                        case "badgeview":
                                            jC.a(B).g(xmlName, 33, parameter);
                                            break;

                                        case "patternview":
                                            jC.a(B).g(xmlName, 34, parameter);
                                            break;

                                        case "sidebar":
                                            jC.a(B).g(xmlName, 35, parameter);
                                            break;

                                        default:
                                            extraPaletteBlock.e(ss.c, parameter);
                                    }
                                }
                            }

                            ss.setArgValue(parameter);
                            block.m();
                        }
                    }
                }
            }

            int subStack1RootBlockId = blockBean.subStack1;
            if (subStack1RootBlockId >= 0) {
                Rs subStack1RootBlock = o.a(subStack1RootBlockId);
                if (subStack1RootBlock != null) {
                    block.e(subStack1RootBlock);
                }
            }

            int subStack2RootBlockId = blockBean.subStack2;
            if (subStack2RootBlockId >= 0) {
                Rs subStack2RootBlock = o.a(subStack2RootBlockId);
                if (subStack2RootBlock != null) {
                    block.f(subStack2RootBlock);
                }
            }

            int nextBlockId = blockBean.nextBlock;
            if (nextBlockId >= 0) {
                Rs nextBlock = o.a(nextBlockId);
                if (nextBlock != null) {
                    block.b(nextBlock);
                }
            }

            block.m();
            if (z) {
                block.p().k();
                o.b();
            }
        }
    }

    public final void a(MoreBlockCollectionBean moreBlockCollectionBean) {
        String str = moreBlockCollectionBean.spec;
        String substring = str.contains(" ") ? str.substring(0, str.indexOf(' ')) : str;
        jC.a(this.B).a(this.M.getJavaName(), substring, str);
        eC a2 = jC.a(this.B);
        String javaName = this.M.getJavaName();
        a2.a(javaName, substring + "_moreBlock", moreBlockCollectionBean.blocks);
        bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), R.string.common_message_complete_save), bB.TOAST_NORMAL).show();
        a(8, 0xff8a55d7);
    }

    public final void a(String str, int i) {
        this.m.a(str, i);
    }

    public final void a(String str, Rs rs) {
        ArrayList<String> arrayList;
        ArrayList<Rs> allChildren = rs.getAllChildren();
        ArrayList<BlockBean> arrayList2 = new ArrayList<>();
        for (Rs child : allChildren) {
            BlockBean blockBean = new BlockBean();
            BlockBean bean = child.getBean();
            blockBean.copy(bean);
            blockBean.id = String.format("99%06d", Integer.valueOf(bean.id));
            int i = bean.subStack1;
            if (i > 0) {
                blockBean.subStack1 = i + 99000000;
            }
            int i2 = bean.subStack2;
            if (i2 > 0) {
                blockBean.subStack2 = i2 + 99000000;
            }
            int i3 = bean.nextBlock;
            if (i3 > 0) {
                blockBean.nextBlock = i3 + 99000000;
            }
            blockBean.parameters.clear();
            for (String next : bean.parameters) {
                if (next.length() <= 1 || next.charAt(0) != '@') {
                    arrayList = blockBean.parameters;
                } else {
                    String format = String.format("99%06d", Integer.valueOf(next.substring(1)));
                    arrayList = blockBean.parameters;
                    next = '@' + format;
                }
                arrayList.add(next);
            }
            arrayList2.add(blockBean);
        }
        try {
            Mp.h().a(str, arrayList2, true);
            this.O.a(str, arrayList2).setOnTouchListener(this);
        } catch (Exception e) {
            // The bytecode is lying. Checked exceptions suck.
            //noinspection ConstantConditions
            if (e instanceof yy) {
                e.printStackTrace();
            } else {
                throw e;
            }
        }
    }

    public final void a(boolean z) {
        this.N.a(z);
    }

    public final boolean a(float f, float f2) {
        return this.N.a(f, f2);
    }

    public final boolean a(BlockBean blockBean) {
        if (blockBean.opCode.equals("getArg")) {
            return true;
        }
        if (blockBean.opCode.equals("definedFunc")) {
            Iterator<Pair<String, String>> it = jC.a(this.B).i(this.M.getJavaName()).iterator();
            boolean z = false;
            while (it.hasNext()) {
                if (blockBean.spec.equals(ReturnMoreblockManager.getMbName(it.next().second))) {
                    z = true;
                }
            }
            if (!z) {
                return false;
            }
        }
        return blockBean.opCode.equals("getVar") || !blockBean.opCode.equals("viewOnClick") || this.D.equals("onBindCustomView");
    }

    public Rs b(BlockBean blockBean) {
        return new Rs(this, Integer.parseInt(blockBean.id), blockBean.spec, blockBean.type, blockBean.typeName, blockBean.opCode);
    }

    private RadioButton getFontRadioButton(String fontName) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText("");
        radioButton.setTag(fontName);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (wB.a(this, 1.0f) * 60.0f));
        radioButton.setGravity(Gravity.CENTER | Gravity.LEFT);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    public void b(int i, String str) {
        jC.a(this.B).c(this.M.getJavaName(), i, str);
        a(0, 0xffee7d16);
    }

    public void b(Rs rs) {
        this.o.b(rs);
    }

    public final void b(Ss ss) {
        View a2 = wB.a(this, R.layout.color_picker);
        a2.setAnimation(AnimationUtils.loadAnimation(this, R.anim.abc_fade_in));
        Zx zx = new Zx(a2, this, (ss.getArgValue() == null || ss.getArgValue().toString().length() <= 0 || ss.getArgValue().toString().indexOf("0xFF") != 0) ? 0 : Color.parseColor(ss.getArgValue().toString().replace("0xFF", "#")), true, false);
        zx.a(i -> {
            if (i == 0) {
                a(ss, (Object) "Color.TRANSPARENT");
            } else {
                a(ss, (Object) String.format("0x%08X", i & (Color.WHITE)));
            }
        });
        zx.setAnimationStyle(R.anim.abc_fade_in);
        zx.showAtLocation(a2, Gravity.CENTER, 0, 0);
    }

    public final void b(MoreBlockCollectionBean moreBlockCollectionBean) {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_more_block_title_change_block_name));
        aBVar.a(R.drawable.more_block_96dp);
        View a2 = wB.a(getBaseContext(), R.layout.property_popup_save_to_favorite);
        ((TextView) a2.findViewById(R.id.tv_favorites_guide)).setText(xB.b().a(getApplicationContext(), R.string.logic_more_block_desc_change_block_name));
        EditText editText = a2.findViewById(R.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        ZB zb = new ZB(getBaseContext(), a2.findViewById(R.id.ti_input), uq.b, uq.a(), jC.a(this.B).a(this.M));
        aBVar.a(a2);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_save), v -> {
            if (zb.b()) {
                String spec = moreBlockCollectionBean.spec;
                String substring = spec.contains(" ") ? spec.substring(spec.indexOf(" ")) : "";
                moreBlockCollectionBean.spec = editText.getText().toString() + substring;
                d(moreBlockCollectionBean);
                mB.a(getApplicationContext(), editText);
                aBVar.dismiss();
            }
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> {
            mB.a(getApplicationContext(), editText);
            aBVar.dismiss();
        });
        aBVar.show();
    }

    public final void b(String str, String str2) {
        TextView a2 = this.m.a(str);
        a2.setTag(str2);
        a2.setSoundEffectsEnabled(true);
        a2.setOnClickListener(this);
    }

    public final void b(String str, String str2, View.OnClickListener onClickListener) {
        TextView a2 = this.m.a(str);
        a2.setTag(str2);
        a2.setSoundEffectsEnabled(true);
        a2.setOnClickListener(onClickListener);
    }

    public final void b(boolean z) {
        this.N.b(z);
    }

    public final boolean b(float f, float f2) {
        return this.N.b(f, f2);
    }

    public final RadioButton c(String str) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText("");
        radioButton.setTag(str);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (wB.a(this, 1.0f) * 60.0f));
        radioButton.setGravity(Gravity.CENTER | Gravity.LEFT);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    public final void c(int i, String str) {
        if (this.ka == null) {
            this.ka = new ArrayList<>();
        }
        for (Pair<Integer, String> next : jC.a(this.B).j(this.M.getJavaName())) {
            if (next.first == i && next.second.equals(str)) {
                return;
            }
        }
        boolean z = false;
        Iterator<Pair<Integer, String>> it2 = this.ka.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Pair<Integer, String> next2 = it2.next();
            if (next2.first == i && next2.second.equals(str)) {
                z = true;
                break;
            }
        }
        if (!z) {
            this.ka.add(new Pair<>(i, str));
        }
    }

    public final void c(Rs rs) {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_block_favorites_save_title));
        aBVar.a(R.drawable.ic_bookmark_red_48dp);
        View a2 = wB.a(this, R.layout.property_popup_save_to_favorite);
        ((TextView) a2.findViewById(R.id.tv_favorites_guide)).setText(xB.b().a(getApplicationContext(), R.string.logic_block_favorites_save_guide));
        EditText editText = a2.findViewById(R.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        NB nb = new NB(this, a2.findViewById(R.id.ti_input), Mp.h().g());
        aBVar.a(a2);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_save), v -> {
            if (nb.b()) {
                a(editText.getText().toString(), rs);
                mB.a(getApplicationContext(), editText);
                aBVar.dismiss();
            }
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> {
            mB.a(getApplicationContext(), editText);
            aBVar.dismiss();
        });
        aBVar.show();
    }

    public void c(Ss ss) {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_enter_string_value));
        aBVar.a(R.drawable.rename_96_blue);
        View a2 = wB.a(this, R.layout.property_popup_input_text);
        ((TextInputLayout) a2.findViewById(R.id.ti_input)).setHint(xB.b().a(this, R.string.property_hint_enter_value));
        EditText editText = a2.findViewById(R.id.ed_input);
        editText.setSingleLine(true);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setText(ss.getArgValue().toString());
        aBVar.a(a2);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_save), v -> {
            a(ss, (Object) editText.getText().toString());
            mB.a(getApplicationContext(), editText);
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> {
            mB.a(getApplicationContext(), editText);
            aBVar.dismiss();
        });
        aBVar.show();
    }

    public final void c(MoreBlockCollectionBean moreBlockCollectionBean) {
        String str = moreBlockCollectionBean.spec;
        boolean z = false;
        if (str.contains(" ")) {
            str = str.substring(0, str.indexOf(' '));
        }
        Iterator<Pair<String, String>> it = jC.a(this.B).i(this.M.getJavaName()).iterator();
        while (true) {
            if (it.hasNext()) {
                if (it.next().first.equals(str)) {
                    z = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (!z) {
            d(moreBlockCollectionBean);
        } else {
            b(moreBlockCollectionBean);
        }
    }

    public void c(String str, String str2) {
        jC.a(this.B).a(this.M.getJavaName(), str, str2);
        a(8, 0xff8a55d7);
    }

    public final void c(boolean z) {
        this.N.c(z);
    }

    public final boolean c(float f, float f2) {
        return this.N.c(f, f2);
    }

    private LinearLayout getFontPreview(String fontName) {
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (wB.a(this, 1.0f) * 60.0f)));
        linearLayout.setGravity(Gravity.CENTER | Gravity.LEFT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView name = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1.0f;
        name.setLayoutParams(layoutParams);
        name.setText(fontName);
        linearLayout.addView(name);
        TextView preview = new TextView(this);
        preview.setLayoutParams(layoutParams);
        preview.setText("Preview");

        Typeface typeface;
        if (fontName.equalsIgnoreCase("default_font")) {
            typeface = Typeface.DEFAULT;
        } else {
            try {
                typeface = Typeface.createFromFile(jC.d(B).d(fontName));
            } catch (RuntimeException e) {
                typeface = Typeface.DEFAULT;
                preview.setText("Couldn't load font");
            }
        }

        preview.setTypeface(typeface);
        linearLayout.addView(preview);
        return linearLayout;
    }

    public final RadioButton d(String str, String str2) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(str + " : " + str2);
        radioButton.setTag(str2);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (wB.a(this, 1.0f) * 40.0f));
        radioButton.setGravity(Gravity.CENTER | Gravity.LEFT);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    public final void d(int i, String str) {
        if (this.ja == null) {
            this.ja = new ArrayList<>();
        }
        for (Pair<Integer, String> next : jC.a(this.B).k(this.M.getJavaName())) {
            if (next.first == i && next.second.equals(str)) {
                return;
            }
        }
        boolean z = false;
        Iterator<Pair<Integer, String>> it2 = this.ja.iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            Pair<Integer, String> next2 = it2.next();
            if (next2.first == i && next2.second.equals(str)) {
                z = true;
                break;
            }
        }
        if (!z) {
            this.ja.add(new Pair<>(i, str));
        }
    }

    public final void d(Ss ss) {
        aB dialog = new aB(this);
        dialog.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_select_font));
        dialog.a(R.drawable.abc_96_color);

        View customView = wB.a(this, R.layout.property_popup_selector_color);
        RadioGroup radioGroup = customView.findViewById(R.id.rg);
        LinearLayout linearLayout = customView.findViewById(R.id.content);
        ArrayList<String> fontNames = jC.d(this.B).k();
        if (xq.a(this.B) || xq.b(this.B)) {
            fontNames.add(0, "default_font");
        }
        for (String fontName : fontNames) {
            RadioButton font = getFontRadioButton(fontName);
            radioGroup.addView(font);
            if (fontName.equals(ss.getArgValue())) {
                font.setChecked(true);
            }
            LinearLayout fontPreview = getFontPreview(fontName);
            fontPreview.setOnClickListener(v -> font.setChecked(true));
            linearLayout.addView(fontPreview);
        }

        dialog.a(customView);
        dialog.b(xB.b().a(getApplicationContext(), R.string.common_word_select), v -> {
            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                if (radioButton.isChecked()) {
                    a(ss, radioButton.getTag());
                    break;
                }
            }
            dialog.dismiss();
        });
        dialog.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public final void d(MoreBlockCollectionBean moreBlockCollectionBean) {
        this.ja = new ArrayList<>();
        this.ka = new ArrayList<>();
        this.la = new ArrayList<>();
        this.ma = new ArrayList<>();
        this.na = new ArrayList<>();
        for (BlockBean next : moreBlockCollectionBean.blocks) {
            if (next.opCode.equals("getVar")) {
                if (next.type.equals("b")) {
                    d(0, next.spec);
                } else if (next.type.equals("d")) {
                    d(1, next.spec);
                } else if (next.type.equals("s")) {
                    d(2, next.spec);
                } else if (next.type.equals("a")) {
                    d(3, next.spec);
                } else if (next.type.equals("l")) {
                    if (next.typeName.equals("List Number")) {
                        c(1, next.spec);
                    } else if (next.typeName.equals("List String")) {
                        c(2, next.spec);
                    } else if (next.typeName.equals("List Map")) {
                        c(3, next.spec);
                    }
                }
            }
            ArrayList<Gx> paramClassInfo = next.getParamClassInfo();
            if (paramClassInfo.size() > 0) {
                for (int i = 0; i < paramClassInfo.size(); i++) {
                    Gx gx = paramClassInfo.get(i);
                    String str = next.parameters.get(i);
                    if (str.length() > 0 && str.charAt(0) != '@') {
                        if (gx.b("boolean.SelectBoolean")) {
                            d(0, str);
                        } else if (gx.b("double.SelectDouble")) {
                            d(1, str);
                        } else if (gx.b("String.SelectString")) {
                            d(2, str);
                        } else if (gx.b("Map")) {
                            d(3, str);
                        } else if (gx.b("ListInt")) {
                            c(1, str);
                        } else if (gx.b("ListString")) {
                            c(2, str);
                        } else if (gx.b("ListMap")) {
                            c(3, str);
                        } else if (gx.b("resource_bg") || gx.b("resource")) {
                            j(str);
                        } else if (gx.b("sound")) {
                            k(str);
                        } else if (gx.b("font")) {
                            i(str);
                        }
                    }
                }
            }
        }
        if (this.ja.size() > 0 || this.ka.size() > 0 || this.la.size() > 0 || this.ma.size() > 0 || this.na.size() > 0) {
            f(moreBlockCollectionBean);
        } else {
            a(moreBlockCollectionBean);
        }
    }

    public final void d(boolean z) {
        this.N.d(z);
    }

    public final boolean d(float f, float f2) {
        return this.N.d(f, f2);
    }

    public final RadioButton e(String str) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(str);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = (int) wB.a(getApplicationContext(), 4.0f);
        layoutParams.bottomMargin = (int) wB.a(getApplicationContext(), 4.0f);
        radioButton.setGravity(Gravity.CENTER | Gravity.LEFT);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    public void e(Ss ss) {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_enter_data_value));
        aBVar.a(R.drawable.rename_96_blue);
        View a2 = wB.a(this, R.layout.property_popup_input_intent_data);
        ((TextView) a2.findViewById(R.id.tv_desc_intent_usage)).setText(xB.b().a(getApplicationContext(), R.string.property_description_component_intent_usage));
        EditText editText = a2.findViewById(R.id.ed_input);
        ((TextInputLayout) a2.findViewById(R.id.ti_input)).setHint(xB.b().a(this, R.string.property_hint_enter_value));
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setText(ss.getArgValue().toString());
        aBVar.a(a2);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_save), v -> {
            a(ss, (Object) editText.getText().toString());
            mB.a(getApplicationContext(), editText);
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> {
            mB.a(getApplicationContext(), editText);
            aBVar.dismiss();
        });
        aBVar.show();
    }

    public final void e(MoreBlockCollectionBean moreBlockCollectionBean) {
        c(moreBlockCollectionBean);
    }

    public void e(boolean z) {
        ObjectAnimator objectAnimator;
        if (!this.W) {
            h(getResources().getConfiguration().orientation);
        }
        if (this.X == z) {
            return;
        }
        this.X = z;
        n();
        if (z) {
            g(false);
            objectAnimator = this.U;
        } else {
            objectAnimator = this.V;
        }
        objectAnimator.start();
        f(getResources().getConfiguration().orientation);
    }

    public final void f(int i) {
        LinearLayout.LayoutParams layoutParams;
        int a2;
        int i2 = ViewGroup.LayoutParams.MATCH_PARENT;
        if (this.X) {
            int i3 = getResources().getDisplayMetrics().widthPixels;
            int i4 = getResources().getDisplayMetrics().heightPixels;
            if (i3 <= i4) {
                i3 = i4;
            }
            if (2 == i) {
                i2 = i3 - ((int) wB.a(this, 320.0f));
                a2 = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                a2 = ((i3 - GB.a(this.e)) - GB.f(this.e)) - ((int) wB.a(this, 240.0f));
            }
            layoutParams = new LinearLayout.LayoutParams(i2, a2);
        } else {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        this.n.setLayoutParams(layoutParams);
        this.n.requestLayout();
    }

    public void f(Ss ss) {
        String str;
        AsdAll asdAll = new AsdAll(this);
        View a2 = wB.a(this, R.layout.property_popup_selector_single);
        ViewGroup viewGroup = a2.findViewById(R.id.rg_content);
        String xmlName = this.M.getXmlName();
        if (this.D.equals("onBindCustomView") && (str = jC.a(this.B).c(this.M.getXmlName(), this.C).customView) != null) {
            xmlName = ProjectFileBean.getXmlName(str);
        }
        asdAll.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_select_view));
        for (Pair<Integer, String> next : jC.a(this.B).d(xmlName, ss.getClassInfo().a())) {
            viewGroup.addView(d(ViewBean.getViewTypeName(next.first), next.second));
        }
        int childCount = viewGroup.getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                break;
            }
            RadioButton radioButton = (RadioButton) viewGroup.getChildAt(i);
            if (ss.getArgValue().toString().equals(radioButton.getTag().toString())) {
                radioButton.setChecked(true);
                break;
            }
            i++;
        }
        asdAll.a(a2);
        asdAll.carry(this, ss, viewGroup);
        asdAll.b(xB.b().a(getApplicationContext(), R.string.common_word_select), v -> {
            int childCount2 = viewGroup.getChildCount();
            int j = 0;
            while (true) {
                if (j >= childCount2) {
                    break;
                }
                RadioButton radioButton = (RadioButton) viewGroup.getChildAt(j);
                if (radioButton.isChecked()) {
                    a(ss, radioButton.getTag());
                    break;
                }
                j++;
            }
            asdAll.dismiss();
        });
        asdAll.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(asdAll));
        asdAll.show();
    }

    public final void f(MoreBlockCollectionBean moreBlockCollectionBean) {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_more_block_title_add_variable_resource));
        aBVar.a(R.drawable.break_warning_96_red);
        aBVar.a(xB.b().a(getApplicationContext(), R.string.logic_more_block_desc_add_variable_resource));
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_continue), v -> {
            for (Pair<Integer, String> integerStringPair : ja) {
                jC.a(B).c(M.getJavaName(), integerStringPair.first, integerStringPair.second);
            }
            for (Pair<Integer, String> integerStringPair : ka) {
                jC.a(B).b(M.getJavaName(), integerStringPair.first, integerStringPair.second);
            }
            for (ProjectResourceBean projectResourceBean : la) {
                g(projectResourceBean.resName);
            }
            for (ProjectResourceBean projectResourceBean : ma) {
                h(projectResourceBean.resName);
            }
            for (ProjectResourceBean projectResourceBean : na) {
                f(projectResourceBean.resName);
            }
            a(moreBlockCollectionBean);
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final void f(String str) {
        if (!Np.g().b(str)) {
            return;
        }
        ProjectResourceBean a2 = Np.g().a(str);
        try {
            this.P.a(wq.a() + File.separator + "font" + File.separator + "data" + File.separator + a2.resFullName, wq.d() + File.separator + this.B + File.separator + a2.resFullName);
            jC.d(this.B).d.add(a2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void f(boolean z) {
        this.N.e(z);
    }

    @Override
    public void finish() {
        bC.d(this.B).b(s());
        super.finish();
    }

    public final void g(int i) {
        RelativeLayout.LayoutParams layoutParams;
        int i2;
        if (2 == i) {
            this.K.setLayoutParams(new LinearLayout.LayoutParams((int) wB.a(this, 320.0f), ViewGroup.LayoutParams.MATCH_PARENT));
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams2.gravity = Gravity.CENTER | Gravity.BOTTOM;
            int dimension = (int) getResources().getDimension(R.dimen.action_button_margin);
            layoutParams2.setMargins(dimension, dimension, dimension, dimension);
            this.L.setLayoutParams(layoutParams2);
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.topMargin = GB.a(this.e);
            i2 = LinearLayout.HORIZONTAL;
        } else {
            this.K.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) wB.a(this, 240.0f)));
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams3.gravity = Gravity.CENTER | Gravity.RIGHT;
            int dimension2 = (int) getResources().getDimension(R.dimen.action_button_margin);
            layoutParams3.setMargins(dimension2, dimension2, dimension2, dimension2);
            this.L.setLayoutParams(layoutParams3);
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            i2 = LinearLayout.VERTICAL;
        }
        J.setOrientation(i2);
        this.J.setLayoutParams(layoutParams);
        h(i);
        f(i);
    }

    public final void g(String str) {
        if (!Op.g().b(str)) {
            return;
        }
        ProjectResourceBean a2 = Op.g().a(str);
        try {
            this.P.a(wq.a() + File.separator + "image" + File.separator + "data" + File.separator + a2.resFullName, wq.g() + File.separator + this.B + File.separator + a2.resFullName);
            jC.d(this.B).b.add(a2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void g(boolean z) {
        if (!this.ha) {
            t();
        }
        if (this.ia == z) {
            return;
        }
        this.ia = z;
        l();
        (z ? this.fa : this.ga).start();
    }

    public final void h(int i) {
        label24:
        {
            label23:
            {
                boolean var2 = this.X;
                if (2 == i) {
                    if (!var2) {
                        this.J.setTranslationX((float) ((int) wB.a(this, 320.0F)));
                        break label23;
                    }
                } else if (!var2) {
                    this.J.setTranslationX(0.0F);
                    this.J.setTranslationY((float) ((int) wB.a(this, 240.0F)));
                    break label24;
                }

                this.J.setTranslationX(0.0F);
            }

            this.J.setTranslationY(0.0F);
        }

        ObjectAnimator var3;
        if (2 == i) {
            this.U = ObjectAnimator.ofFloat(this.J, "TranslationX", 0.0F);
            var3 = ObjectAnimator.ofFloat(this.J, "TranslationX", (float) ((int) wB.a(this, 320.0F)));
        } else {
            this.U = ObjectAnimator.ofFloat(this.J, "TranslationY", 0.0F);
            var3 = ObjectAnimator.ofFloat(this.J, "TranslationY", (float) ((int) wB.a(this, 240.0F)));
        }

        this.V = var3;
        this.U.setDuration(500L);
        this.U.setInterpolator(new DecelerateInterpolator());
        this.V.setDuration(300L);
        this.V.setInterpolator(new DecelerateInterpolator());
        this.W = true;
    }

    public final void h(Ss ss) {
        aB dialog = new aB(this);
        dialog.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_select_sound));
        dialog.a(R.drawable.music_48);

        View customView = wB.a(this, R.layout.property_popup_selector_single);
        RadioGroup radioGroup = customView.findViewById(R.id.rg_content);
        SoundPool soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build())
                .build();
        soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> {
            if (soundPool1 != null) {
                soundPool1.play(sampleId, 1, 1, 1, 0, 1);
            }
        });

        for (String soundName : jC.d(this.B).p()) {
            RadioButton sound = e(soundName);
            radioGroup.addView(sound);
            if (soundName.equals(ss.getArgValue())) {
                sound.setChecked(true);
            }
            sound.setOnClickListener(v -> soundPool.load(jC.d(B).i(sound.getText().toString()), 1));
        }
        dialog.a(customView);
        dialog.b(xB.b().a(getApplicationContext(), R.string.common_word_select), v -> {
            RadioButton checkedRadioButton = (RadioButton) radioGroup.getChildAt(radioGroup.getCheckedRadioButtonId());
            a(ss, (Object) checkedRadioButton.getText().toString());
        });
        dialog.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    public final void h(String str) {
        if (Qp.g().b(str)) {
            ProjectResourceBean a2 = Qp.g().a(str);
            try {
                this.P.a(wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + a2.resFullName, wq.t() + File.separator + this.B + File.separator + a2.resFullName);
                jC.d(this.B).c.add(a2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void h(boolean z) {
        this.N.b(false);
        this.N.a(false);
        this.N.d(false);
        this.N.c(false);
        if (!this.da) {
            x();
        }
        if (this.ea == z) {
            return;
        }
        this.ea = z;
        m();
        (z ? this.ba : this.ca).start();
    }

    public final void i(Ss ss) {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_select_typeface));
        aBVar.a(R.drawable.abc_96_color);
        View a3 = wB.a(this, R.layout.property_popup_selector_single);
        RadioGroup radioGroup = a3.findViewById(R.id.rg_content);
        for (Pair<Integer, String> pair : sq.a("property_text_style")) {
            RadioButton e = e(pair.second);
            radioGroup.addView(e);
            if (pair.second.equals(ss.getArgValue())) {
                e.setChecked(true);
            }
        }
        aBVar.a(a3);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_save), v -> {
            int childCount = radioGroup.getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    break;
                }
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                if (radioButton.isChecked()) {
                    a(ss, (Object) radioButton.getText().toString());
                    break;
                }
                i++;
            }
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final void i(String str) {
        if (this.na == null) {
            this.na = new ArrayList<>();
        }
        for (String value : jC.d(this.B).k()) {
            if (value.equals(str)) {
                return;
            }
        }
        ProjectResourceBean a2 = Np.g().a(str);
        if (a2 == null) {
            return;
        }
        boolean z = false;
        Iterator<ProjectResourceBean> it2 = this.na.iterator();
        while (true) {
            if (it2.hasNext()) {
                if (it2.next().resName.equals(str)) {
                    z = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (z) {
            return;
        }
        this.na.add(a2);
    }

    public final void j(String str) {
        if (this.la == null) {
            this.la = new ArrayList<>();
        }
        for (String value : jC.d(this.B).m()) {
            if (value.equals(str)) {
                return;
            }
        }
        ProjectResourceBean a2 = Op.g().a(str);
        if (a2 == null) {
            return;
        }
        boolean z = false;
        Iterator<ProjectResourceBean> it2 = this.la.iterator();
        while (true) {
            if (it2.hasNext()) {
                if (it2.next().resName.equals(str)) {
                    z = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (z) {
            return;
        }
        this.la.add(a2);
    }

    public final void k(String str) {
        if (this.ma == null) {
            this.ma = new ArrayList<>();
        }
        for (String value : jC.d(this.B).p()) {
            if (value.equals(str)) {
                return;
            }
        }
        ProjectResourceBean a2 = Qp.g().a(str);
        if (a2 == null) {
            return;
        }
        boolean z = false;
        Iterator<ProjectResourceBean> it2 = this.ma.iterator();
        while (true) {
            if (it2.hasNext()) {
                if (it2.next().resName.equals(str)) {
                    z = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (z) {
            return;
        }
        this.ma.add(a2);
    }

    public final void l() {
        if (this.fa.isRunning()) {
            this.fa.cancel();
        }
        if (this.ga.isRunning()) {
            this.ga.cancel();
        }
    }

    public void l(String str) {
        jC.a(this.B).o(this.M.getJavaName(), str);
        a(1, 0xffcc5b22);
    }

    public final void m() {
        if (this.ba.isRunning()) {
            this.ba.cancel();
        }
        if (this.ca.isRunning()) {
            this.ca.cancel();
        }
    }

    public void m(String str) {
        jC.a(this.B).p(this.M.getJavaName(), str);
        a(0, 0xffee7d16);
    }

    public final void n() {
        if (this.U.isRunning()) {
            this.U.cancel();
        }
        if (this.V.isRunning()) {
            this.V.cancel();
        }
    }

    public final void n(String str) {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_block_favorites_delete_title));
        aBVar.a(R.drawable.high_priority_96_red);
        aBVar.a(xB.b().a(getApplicationContext(), R.string.logic_block_favorites_delete_message));
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_delete), v -> {
            Mp.h().a(str, true);
            O.a(str);
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public void o(String str) {
        Intent intent = new Intent(getApplicationContext(), ShowBlockCollectionActivity.class);
        intent.putExtra("block_name", str);
        startActivity(intent);
    }

    public boolean o() {
        int childCount = this.o.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.o.getChildAt(i);
            if (childAt instanceof Rs) {
                ((Rs) childAt).U.equals("Forever");
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 222) {
            if (i2 != Activity.RESULT_OK) {
                return;
            }
            c(intent.getStringExtra("block_name"), intent.getStringExtra("block_spec"));
        } else if (i == 224) {
            if (i2 != Activity.RESULT_OK) {
                return;
            }
            a(7, 0xff2ca5e2);
        } else if (i == 463 && i2 == Activity.RESULT_OK && intent.getBooleanExtra("req_update_design_activity", false)) {
            z();
        }
    }

    @Override
    public void onBackPressed() {
        if (this.ia) {
            g(false);
            return;
        }
        if (X) {
            e(false);
            return;
        }
        k();
        if (!o() || !p()) {
            return;
        }
        L();
    }

    @Override
    public void onClick(View view) {
        if (mB.a()) {
            return;
        }
        if (view.getTag() != null) {
            if (view.getTag().equals("variableAdd")) {
                showAddNewVariableDialog();
            } else if (view.getTag().equals("variableRemove")) {
                K();
            } else if (view.getTag().equals("listAdd")) {
                G();
            } else if (view.getTag().equals("listRemove")) {
                J();
            } else if (view.getTag().equals("blockAdd")) {
                Intent intent = new Intent(getApplicationContext(), MakeBlockActivity.class);
                intent.putExtra("sc_id", this.B);
                intent.putExtra("project_file", this.M);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivityForResult(intent, 222);
            } else if (view.getTag().equals("componentAdd")) {
                Intent intent = new Intent(getApplicationContext(), ComponentAddActivity.class);
                intent.putExtra("sc_id", this.B);
                intent.putExtra("project_file", this.M);
                intent.putExtra("filename", this.M.getJavaName());
                startActivityForResult(intent, 224);
            } else if (view.getTag().equals("blockImport")) {
                I();
            }
        }
        int id = view.getId();
        if (id == R.id.btn_accept) {
            setResult(Activity.RESULT_OK, new Intent());
        } else if (id != R.id.btn_cancel) {
            return;
        } else {
            setResult(0);
        }
        finish();
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        g(configuration.orientation);
    }

    @Override
    public void onCreate(Bundle bundle) {
        Parcelable parcelable;
        ActionBar d;
        super.onCreate(bundle);
        setContentView(R.layout.logic_editor);
        if (!super.j()) {
            finish();
        }
        if (bundle == null) {
            this.B = getIntent().getStringExtra("sc_id");
            this.C = getIntent().getStringExtra("id");
            this.D = getIntent().getStringExtra("event");
            parcelable = getIntent().getParcelableExtra("project_file");
        } else {
            this.B = bundle.getString("sc_id");
            this.C = bundle.getString("id");
            this.D = bundle.getString("event");
            parcelable = bundle.getParcelable("project_file");
        }
        this.M = (ProjectFileBean) parcelable;
        this.H = new DB(this.e, "P1");
        this.T = (int) wB.a(getBaseContext(), (float) this.T);
        this.k = findViewById(R.id.toolbar);
        a(k);
        findViewById(R.id.layout_main_logo).setVisibility(View.GONE);
        d().d(true);
        d().e(true);
        this.k.setNavigationOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });
        this.k.setPopupTheme(R.style.ThemeOverlay_ToolbarMenu);
        this.G = new DB(this.e, "P12").a("P12I0", true);
        this.A = ViewConfiguration.get(this.e).getScaledTouchSlop();
        this.F = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        String stringExtra = getIntent().getStringExtra("event_text");
        if (this.C.equals("onCreate")) {
            d = d();
        } else if (this.C.equals("_fab")) {
            d = d();
            stringExtra = "fab : " + stringExtra;
        } else {
            d = d();
            stringExtra = ReturnMoreblockManager.getMbName(this.C) + " : " + stringExtra;
        }
        d.a(stringExtra);
        this.l = findViewById(R.id.palette_selector);
        l.setOnBlockCategorySelectListener(this);
        this.m = findViewById(R.id.palette_block);
        this.p = findViewById(R.id.dummy);
        this.n = findViewById(R.id.editor);
        this.o = n.getBlockPane();
        this.J = findViewById(R.id.layout_palette);
        this.K = findViewById(R.id.area_palette);
        this.L = findViewById(R.id.fab_toggle_palette);
        L.setOnClickListener(v -> e(!X));
        this.N = findViewById(R.id.top_menu);
        this.O = findViewById(R.id.right_drawer);
        this.j.h();
        this.extraPaletteBlock = new ExtraPaletteBlock(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logic_menu, menu);
        menu.findItem(R.id.menu_logic_redo).setEnabled(false);
        menu.findItem(R.id.menu_logic_undo).setEnabled(false);
        if (this.M == null) {
            return true;
        }
        if (bC.d(this.B).g(s())) {
            menu.findItem(R.id.menu_logic_redo).setIcon(R.drawable.ic_redo_white_48dp);
            menu.findItem(R.id.menu_logic_redo).setEnabled(true);
        } else {
            menu.findItem(R.id.menu_logic_redo).setIcon(R.drawable.ic_redo_grey_48dp);
            menu.findItem(R.id.menu_logic_redo).setEnabled(false);
        }
        if (bC.d(this.B).h(s())) {
            menu.findItem(R.id.menu_logic_undo).setIcon(R.drawable.ic_undo_white_48dp);
            menu.findItem(R.id.menu_logic_undo).setEnabled(true);
        } else {
            menu.findItem(R.id.menu_logic_undo).setIcon(R.drawable.ic_undo_grey_48dp);
            menu.findItem(R.id.menu_logic_undo).setEnabled(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();

        if (itemId == R.id.menu_block_helper) {
            e(false);
            g(!this.ia);
        } else if (itemId == R.id.menu_logic_redo) {
            B();
        } else if (itemId == R.id.menu_logic_undo) {
            M();
        } else if (itemId == R.id.menu_logic_showsource) {
            showSourceCode();
        }

        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        String var2;
        String var8;
        if (this.D.equals("moreBlock")) {
            var2 = jC.a(this.B).b(this.M.getJavaName(), this.C);
            var8 = xB.b().a(this.getApplicationContext(), R.string.root_spec_common_define) + " " + ReturnMoreblockManager.getLogicEditorTitle(var2);
        } else if (this.C.equals("_fab")) {
            var8 = xB.b().a(this.getApplicationContext(), "fab", this.D);
        } else {
            var8 = xB.b().a(this.getApplicationContext(), this.C, this.D);
        }

        this.E = var8;
        this.o.a(this.E, this.D);
        ArrayList<String> var10 = FB.c(this.E);
        int var4 = 0;

        int var6;
        for (int var5 = 0; var4 < var10.size(); var5 = var6) {
            var2 = var10.get(var4);
            var6 = var5;
            if (var2.charAt(0) == '%') {
                label44:
                {
                    Rs var9;
                    if (var2.charAt(1) == 'b') {
                        var9 = new Rs(super.e, var5 + 1, var2.substring(3), "b", "getArg");
                    } else if (var2.charAt(1) == 'd') {
                        var9 = new Rs(super.e, var5 + 1, var2.substring(3), "d", "getArg");
                    } else if (var2.charAt(1) == 's') {
                        var9 = new Rs(super.e, var5 + 1, var2.substring(3), "s", "getArg");
                    } else {
                        if (var2.charAt(1) != 'm') {
                            break label44;
                        }

                        var8 = var2.substring(var2.lastIndexOf(".") + 1);
                        String var7 = var2.substring(var2.indexOf(".") + 1, var2.lastIndexOf("."));
                        var2 = kq.a(var7);
                        var9 = new Rs(super.e, var5 + 1, var8, var2, kq.b(var7), "getArg");
                    }

                    var9.setBlockType(1);
                    this.o.addView(var9);
                    this.o.getRoot().a((Ts) this.o.getRoot().V.get(var5), var9);
                    var9.setOnTouchListener(this);
                    var6 = var5 + 1;
                }
            }

            ++var4;
        }

        this.o.getRoot().k();
        this.g(this.getResources().getConfiguration().orientation);
        this.a(0, 0xffee7d16);
        this.A();
        this.z();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.B);
        bundle.putString("id", this.C);
        bundle.putString("event", this.D);
        bundle.putParcelable("project_file", this.M);
        super.onSaveInstanceState(bundle);
        ArrayList<BlockBean> blocks = this.o.getBlocks();
        eC a2 = jC.a(this.B);
        String javaName = this.M.getJavaName();
        a2.a(javaName, this.C + "_" + this.D, blocks);
        jC.a(this.B).k();
    }

    @Override
    public void onSelected(MoreBlockCollectionBean moreBlockCollectionBean) {
        c(moreBlockCollectionBean);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Rs p;
        BlockBean blockBean;
        Rs p2;
        BlockBean blockBean2;
        int actionMasked = motionEvent.getActionMasked();
        if (motionEvent.getPointerId(motionEvent.getActionIndex()) > 0) {
            return true;
        }
        if (actionMasked == MotionEvent.ACTION_DOWN) {
            this.u = false;
            this.Z.postDelayed(this.aa, ViewConfiguration.getLongPressTimeout() / 2);
            int[] locationOnScreen = new int[2];
            view.getLocationOnScreen(locationOnScreen);
            this.s = locationOnScreen[0];
            this.t = locationOnScreen[1];
            this.q = motionEvent.getRawX();
            this.r = motionEvent.getRawY();
            this.Y = view;
            return true;
        }
        BlockBean blockBean3 = null;
        if (actionMasked == MotionEvent.ACTION_MOVE) {
            if (!this.u) {
                if (Math.abs((this.q - this.s) - motionEvent.getX()) < this.A && Math.abs((this.r - this.t) - motionEvent.getY()) < this.A) {
                    return false;
                }
                this.Y = null;
                this.Z.removeCallbacks(this.aa);
                return false;
            }
            this.Z.removeCallbacks(this.aa);
            float rawX = motionEvent.getRawX();
            float rawY = motionEvent.getRawY();
            this.p.a(view, rawX - s, rawY - t, this.q - this.s, this.r - t, this.S, this.T);
            if (b(motionEvent.getRawX(), motionEvent.getRawY())) {
                this.p.setAllow(true);
                b(true);
                a(false);
                d(false);
                c(false);
                return true;
            }
            b(false);
            if (a(motionEvent.getRawX(), motionEvent.getRawY())) {
                this.p.setAllow(true);
                a(true);
                d(false);
                c(false);
                return true;
            }
            a(false);
            if (d(motionEvent.getRawX(), motionEvent.getRawY())) {
                this.p.setAllow(true);
                d(true);
                c(false);
                return true;
            }
            d(false);
            if (c(motionEvent.getRawX(), motionEvent.getRawY())) {
                this.p.setAllow(true);
                c(true);
                return true;
            }
            c(false);
            this.p.a(this.v);
            if (n.a(v[0], v[1])) {
                this.p.setAllow(true);
                this.o.c((Rs) view, v[0], v[1]);
            } else {
                this.p.setAllow(false);
                this.o.d();
            }
            return true;
        } else if (actionMasked != MotionEvent.ACTION_UP) {
            if (actionMasked == MotionEvent.ACTION_CANCEL) {
                this.Z.removeCallbacks(this.aa);
                this.u = false;
                return false;
            } else if (actionMasked != MotionEvent.ACTION_SCROLL) {
                return true;
            } else {
                this.Z.removeCallbacks(this.aa);
                this.u = false;
                return false;
            }
        } else {
            this.Y = null;
            this.Z.removeCallbacks(this.aa);
            if (!this.u) {
                if (view instanceof Rs) {
                    Rs rs = (Rs) view;
                    if (rs.getBlockType() == 0) {
                        a(rs, motionEvent.getX(), motionEvent.getY());
                    }
                }
                return false;
            }
            this.m.setDragEnabled(true);
            this.n.setScrollEnabled(true);
            this.O.setDragEnabled(true);
            this.p.setDummyVisibility(View.GONE);
            if (!this.p.getAllow()) {
                Rs rs2 = (Rs) view;
                if (rs2.getBlockType() == 0) {
                    this.o.a(rs2, 0);
                    if (w != null) {
                        if (this.x == 0) {
                            w.ha = (Integer) view.getTag();
                        }
                        if (this.x == 2) {
                            this.w.ia = (Integer) view.getTag();
                        }
                        if (this.x == 3) {
                            this.w.ja = (Integer) view.getTag();
                        }
                        if (this.x == 5) {
                            this.w.a((Ts) this.w.V.get(this.y), rs2);
                        }
                        rs2.E = w;
                        p = w.p();
                    } else {
                        p = rs2.p();
                    }
                    p.k();
                }
                q();
            } else if (this.N.b()) {
                Rs rs5 = (Rs) view;
                if (rs5.getBlockType() == 2) {
                    g(true);
                    n(rs5.T);
                } else {
                    b(false);
                    int intValue = Integer.parseInt(rs5.getBean().id);
                    if (w != null) {
                        BlockBean clone = w.getBean().clone();
                        if (x == 0) {
                            clone.nextBlock = intValue;
                        } else if (x == 2) {
                            clone.subStack1 = intValue;
                        } else if (x == 3) {
                            clone.subStack2 = intValue;
                        } else if (x == 5) {
                            clone.parameters.set(this.y, "@" + intValue);
                        }
                        blockBean2 = clone;
                    } else {
                        blockBean2 = null;
                    }
                    ArrayList<Rs> allChildren = rs5.getAllChildren();
                    ArrayList<BlockBean> arrayList = new ArrayList<>();
                    for (Rs allChild : allChildren) {
                        arrayList.add(allChild.getBean().clone());
                    }
                    b(rs5);
                    if (w != null) {
                        blockBean3 = w.getBean().clone();
                    }
                    int[] iArr4 = new int[2];
                    this.o.getLocationOnScreen(iArr4);
                    bC.d(this.B).b(s(), arrayList, ((int) this.s) - iArr4[0], ((int) this.t) - iArr4[1], blockBean2, blockBean3);
                    C();
                }
            } else if (this.N.d()) {
                d(false);
                Rs rs7 = (Rs) view;
                this.o.a(rs7, 0);
                Rs p3;
                if (w != null) {
                    if (this.x == 0) {
                        w.ha = (Integer) view.getTag();
                    }
                    if (this.x == 2) {
                        this.w.ia = (Integer) view.getTag();
                    }
                    if (this.x == 3) {
                        this.w.ja = (Integer) view.getTag();
                    }
                    if (this.x == 5) {
                        this.w.a((Ts) this.w.V.get(this.y), rs7);
                    }
                    rs7.E = w;
                    p3 = w.p();
                } else {
                    p3 = rs7.p();
                }
                p3.k();
                c(rs7);
            } else if (this.N.c()) {
                c(false);
                if (view instanceof Us) {
                    o(((Us) view).T);
                }
            } else if (this.N.a()) {
                a(false);
                Rs rs10 = (Rs) view;
                this.o.a(rs10, 0);
                if (w != null) {
                    if (this.x == 0) {
                        w.ha = (Integer) view.getTag();
                    }
                    if (this.x == 2) {
                        this.w.ia = (Integer) view.getTag();
                    }
                    if (this.x == 3) {
                        this.w.ja = (Integer) view.getTag();
                    }
                    if (this.x == 5) {
                        this.w.a((Ts) this.w.V.get(this.y), rs10);
                    }
                    rs10.E = w;
                    p2 = w.p();
                } else {
                    p2 = rs10.p();
                }
                p2.k();
                ArrayList<Rs> allChildren2 = rs10.getAllChildren();
                ArrayList<BlockBean> arrayList2 = new ArrayList<>();
                for (Rs rs : allChildren2) {
                    BlockBean clone2 = rs.getBean().clone();
                    clone2.id = String.valueOf(Integer.parseInt(clone2.id) + 99000000);
                    if (clone2.nextBlock > 0) {
                        clone2.nextBlock = clone2.nextBlock + 99000000;
                    }
                    if (clone2.subStack1 > 0) {
                        clone2.subStack1 = clone2.subStack1 + 99000000;
                    }
                    if (clone2.subStack2 > 0) {
                        clone2.subStack2 = clone2.subStack2 + 99000000;
                    }
                    for (int i = 0; i < clone2.parameters.size(); i++) {
                        String parameter = clone2.parameters.get(i);
                        if (parameter != null && parameter.length() > 0 && parameter.charAt(0) == '@') {
                            clone2.parameters.set(i, "@" + (Integer.parseInt(parameter.substring(1)) + 99000000));
                        }
                    }
                    arrayList2.add(clone2);
                }
                int[] nLocationOnScreen = new int[2];
                this.n.getLocationOnScreen(nLocationOnScreen);
                int width = nLocationOnScreen[0] + (this.n.getWidth() / 2);
                int a2 = nLocationOnScreen[1] + ((int) wB.a(getApplicationContext(), 4.0f));
                ArrayList<BlockBean> a3 = a(arrayList2, width, a2, true);
                int[] oLocationOnScreen = new int[2];
                this.o.getLocationOnScreen(oLocationOnScreen);
                bC.d(this.B).a(s(), a3, width - oLocationOnScreen[0], a2 - oLocationOnScreen[1], null, null);
                C();
            } else if (view instanceof Rs) {
                this.p.a(this.v);
                Rs rs13 = (Rs) view;
                if (rs13.getBlockType() == 1) {
                    int addTargetId = this.o.getAddTargetId();
                    BlockBean clone3 = addTargetId >= 0 ? this.o.a(addTargetId).getBean().clone() : null;
                    Rs a4 = a(rs13, v[0], v[1], false);
                    if (addTargetId >= 0) {
                        blockBean3 = this.o.a(addTargetId).getBean().clone();
                    }
                    int[] locationOnScreen = new int[2];
                    this.o.getLocationOnScreen(locationOnScreen);
                    bC d = bC.d(this.B);
                    d.a(s(), a4.getBean().clone(), v[0] - locationOnScreen[0], v[1] - locationOnScreen[1], clone3, blockBean3);
                    if (clone3 != null) {
                        clone3.print();
                    }
                    if (blockBean3 != null) {
                        blockBean3.print();
                    }
                } else if (rs13.getBlockType() == 2) {
                    int addTargetId2 = this.o.getAddTargetId();
                    BlockBean clone5 = addTargetId2 >= 0 ? this.o.a(addTargetId2).getBean().clone() : null;
                    ArrayList<BlockBean> data = ((Us) view).getData();
                    ArrayList<BlockBean> a5 = a(data, v[0], v[1], true);
                    if (a5.size() > 0) {
                        Rs a6 = this.o.a(a5.get(0).id);
                        a(a6, v[0], v[1], true);
                        if (addTargetId2 >= 0) {
                            blockBean3 = this.o.a(addTargetId2).getBean().clone();
                        }
                        int[] locationOnScreen = new int[2];
                        this.o.getLocationOnScreen(locationOnScreen);
                        bC d2 = bC.d(this.B);
                        d2.a(s(), a5, v[0] - locationOnScreen[0], v[1] - locationOnScreen[1], clone5, blockBean3);
                    }
                    this.o.c();
                } else {
                    this.o.a(rs13, 0);
                    int intValue2 = Integer.parseInt(rs13.getBean().id);
                    if (w != null) {
                        blockBean = w.getBean().clone();
                        if (x == 0) {
                            blockBean.nextBlock = intValue2;
                        } else if (x == 2) {
                            blockBean.subStack1 = intValue2;
                        } else if (x == 3) {
                            blockBean.subStack2 = intValue2;
                        } else if (x == 5) {
                            blockBean.parameters.set(this.y, "@" + intValue2);
                        }
                    } else {
                        blockBean = null;
                    }
                    Rs a7 = this.o.a(this.o.getAddTargetId());
                    BlockBean clone6 = a7 != null ? a7.getBean().clone() : null;
                    ArrayList<Rs> allChildren3 = rs13.getAllChildren();
                    ArrayList<BlockBean> arrayList3 = new ArrayList<>();
                    for (Rs rs : allChildren3) {
                        arrayList3.add(rs.getBean().clone());
                    }
                    a(rs13, v[0], v[1], true);
                    ArrayList<BlockBean> arrayList4 = new ArrayList<>();
                    for (Rs rs : allChildren3) {
                        arrayList4.add(rs.getBean().clone());
                    }
                    BlockBean clone7 = w != null ? w.getBean().clone() : null;
                    if (a7 != null) {
                        blockBean3 = a7.getBean().clone();
                    }
                    if (blockBean == null || clone7 == null || !blockBean.isEqual(clone7)) {
                        int[] locationOnScreen = new int[2];
                        this.o.getLocationOnScreen(locationOnScreen);
                        int x = locationOnScreen[0];
                        int y = locationOnScreen[1];
                        bC d3 = bC.d(this.B);
                        d3.a(s(), arrayList3, arrayList4, ((int) this.s) - x, ((int) this.t) - y, v[0] - x, v[1] - y, blockBean, clone7, clone6, blockBean3);
                    }
                    this.o.c();
                }
                C();
                this.o.c();
            }
            this.p.setAllow(false);
            h(false);
            this.u = false;
            return true;
        }
    }

    public boolean p() {
        return true;
    }

    public void q() {
    }

    public void r() {
        if (this.Y != null) {
            this.m.setDragEnabled(false);
            this.n.setScrollEnabled(false);
            this.O.setDragEnabled(false);
            if (this.ia) {
                this.g(false);
            }

            if (this.G) {
                this.F.vibrate(100L);
            }

            label28:
            {
                this.u = true;
                if (((Rs) this.Y).getBlockType() == 0) {
                    this.a((Rs) this.Y);
                    this.f(true);
                    this.h(true);
                    this.p.a((Rs) this.Y);
                    this.o.a((Rs) this.Y, 8);
                    this.o.c((Rs) this.Y);
                } else {
                    if (((Rs) this.Y).getBlockType() == 2) {
                        this.f(false);
                        this.h(true);
                        this.p.a((Rs) this.Y);
                        o.a((Rs) Y, ((Us) Y).getData());
                        break label28;
                    }

                    this.p.a((Rs) this.Y);
                }

                this.o.a((Rs) this.Y);
            }

            float var3 = this.q - this.s;
            float var4 = this.r - this.t;
            this.p.a(this.Y, var3, var4, var3, var4, (float) this.S, (float) this.T);
            this.p.a(this.v);
            if (n.a((float) v[0], (float) v[1])) {
                this.p.setAllow(true);
                o.c((Rs) Y, v[0], v[1]);
            } else {
                this.p.setAllow(false);
                this.o.d();
            }
        }
    }

    public final String s() {
        return bC.a(this.M.getJavaName(), this.C, this.D);
    }

    public void showSourceCode() {
        yq yq = new yq(this, this.B);
        yq.a(jC.c(this.B), jC.b(this.B), jC.a(this.B), false);
        String code = new Fx(this.M.getActivityName(), yq.N, "", this.o.getBlocks()).a();

        CodeEditor codeEditor = new CodeEditor(this);
        codeEditor.setColorScheme(new EditorColorScheme());
        codeEditor.setEditable(false);
        codeEditor.setEditorLanguage(new JavaLanguage());
        codeEditor.setText(Lx.j(code, false));
        codeEditor.setTextSize(12);
        codeEditor.setTypefaceText(Typeface.MONOSPACE);
        codeEditor.setWordwrap(false);
        codeEditor.getComponent(Magnifier.class).setWithinEditorForcibly(true);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Source code")
                .setIcon(R.drawable.code_icon)
                .setPositiveButton(R.string.common_word_close, null)
                .create();

        dialog.setView(codeEditor,
                (int) getDip(24),
                (int) getDip(8),
                (int) getDip(24),
                (int) getDip(8));
        dialog.show();
    }

    public final void t() {
        this.fa = ObjectAnimator.ofFloat(this.O, "TranslationX", 0.0f);
        fa.setDuration(500L);
        this.fa.setInterpolator(new DecelerateInterpolator());
        this.ga = ObjectAnimator.ofFloat(O, "TranslationX", O.getHeight());
        ga.setDuration(300L);
        this.ga.setInterpolator(new DecelerateInterpolator());
        this.ha = true;
    }

    public final void x() {
        this.ba = ObjectAnimator.ofFloat(this.N, "TranslationY", 0.0f);
        ba.setDuration(500L);
        this.ba.setInterpolator(new DecelerateInterpolator());
        this.ca = ObjectAnimator.ofFloat(N, "TranslationY", N.getHeight() * (-1));
        ca.setDuration(300L);
        this.ca.setInterpolator(new DecelerateInterpolator());
        this.da = true;
    }

    public final void z() {
        this.O.a();
        for (BlockCollectionBean next : Mp.h().f()) {
            this.O.a(next.name, next.blocks).setOnTouchListener(this);
        }
    }
}
