package com.besome.sketch.editor;

import android.animation.ObjectAnimator;
import android.app.Activity;
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
import android.view.LayoutInflater;
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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.BlockCollectionBean;
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
import com.bumptech.glide.load.Key;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.io.File;
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
import mod.hasrat.menu.ExtraMenuBean;
import mod.hey.studios.logic.SourceCodeDialog;
import mod.hey.studios.moreblock.ImportMoreblockHelper;
import mod.hey.studios.moreblock.ReturnMoreblockManager;
import mod.hey.studios.moreblock.importer.MoreblockImporterDialog;
import mod.hilal.saif.asd.asdforall.AsdAll;

public class LogicEditorActivity extends BaseAppCompatActivity implements View.OnClickListener, Vs, View.OnTouchListener, MoreblockImporterDialog.CallBack {
    public Vibrator F;
    public boolean G;
    public DB H;
    public LinearLayout I;
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
    public a oa;
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

    public class a extends RecyclerView.a<a.a2> {
        public int c = -1;
        public final LogicEditorActivity d;

        public class a2 extends RecyclerView.v {
            public ViewGroup t;
            public ImageView u;
            public TextView v;
            public ViewGroup w;
            public final a x;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public a2(a aVar, View view) {
                super(view);
                this.x = aVar;
                this.t = (ViewGroup) view.findViewById(R.id.layout_item);
                this.u = (ImageView) view.findViewById(R.id.img_selected);
                this.v = (TextView) view.findViewById(R.id.tv_block_name);
                this.w = (ViewGroup) view.findViewById(R.id.block_area);
                this.u.setVisibility(View.GONE);
                this.t.setOnClickListener(v -> {
                    a.this.c = j();
                    c(a.this.c);
                });
                this.w.setOnClickListener(v -> {
                    a.this.c = j();
                    c(a.this.c);
                });
            }

            public final void c(int i) {
                if (this.x.d.pa.size() <= 0) {
                    return;
                }
                for (MoreBlockCollectionBean moreBlockCollectionBean : this.x.d.pa) {
                    moreBlockCollectionBean.isSelected = false;
                }
                this.x.d.pa.get(i).isSelected = true;
                this.x.d.oa.c();
            }
        }

        public a(LogicEditorActivity logicEditorActivity) {
            this.d = logicEditorActivity;
        }

        @Override
        public int a() {
            return this.d.pa.size();
        }

        @Override
        public void b(a2 a2Var, int i) {
            MoreBlockCollectionBean moreBlockCollectionBean = this.d.pa.get(i);
            a2Var.u.setVisibility(moreBlockCollectionBean.isSelected ? View.VISIBLE : View.GONE);
            a2Var.v.setText(moreBlockCollectionBean.name);
            a2Var.w.removeAllViews();
            a2Var.w.addView(ImportMoreblockHelper.optimizedBlockView(this.d.getBaseContext(), moreBlockCollectionBean.spec));
        }

        @Override
        public a2 b(ViewGroup viewGroup, int i) {
            return new a2(this, LayoutInflater.from(this.d.getBaseContext()).inflate(R.layout.manage_collection_popup_import_more_block_list_item, viewGroup, false));
        }
    }

    public class b extends MA {
        public final LogicEditorActivity c;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(LogicEditorActivity logicEditorActivity, Context context) {
            super(context);
            this.c = logicEditorActivity;
            logicEditorActivity.a(this);
        }

        @Override
        public void a() {
            this.c.h();
            this.c.finish();
        }

        @Override
        public void a(String str) {
            Toast.makeText(this.a, xB.b().a(this.c.getApplicationContext(), R.string.common_error_failed_to_save), Toast.LENGTH_SHORT).show();
            this.c.h();
        }

        @Override
        public void b() {
            publishProgress("Now saving..");
            this.c.E();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }

    public final void A() {
        Rs rs;
        Rs rs2;
        Rs rs3;
        HashMap<Integer, Rs> hashMap = new HashMap<>();
        ArrayList<BlockBean> a2 = jC.a(this.B).a(this.M.getJavaName(), this.C + "_" + this.D);
        if (a2 == null) {
            return;
        }
        if (a2.size() == 0) {
            e(this.X);
        }
        Iterator<BlockBean> it = a2.iterator();
        boolean z = true;
        while (it.hasNext()) {
            BlockBean next = it.next();
            if (this.D.equals("onTextChanged") && next.opCode.equals("getArg") && next.spec.equals("text")) {
                next.spec = "charSeq";
            }
            Rs b2 = b(next);
            hashMap.put(Integer.valueOf(((Integer) b2.getTag()).intValue()), b2);
            BlockPane blockPane = this.o;
            blockPane.g = Math.max(blockPane.g, ((Integer) b2.getTag()).intValue() + 1);
            this.o.a(b2, 0, 0);
            b2.setOnTouchListener(this);
            if (z) {
                this.o.getRoot().b(b2);
                z = false;
            }
        }
        for (BlockBean next2 : a2) {
            Rs rs4 = hashMap.get(Integer.valueOf(next2.id));
            if (rs4 != null) {
                int i = next2.subStack1;
                if (i >= 0 && (rs3 = hashMap.get(Integer.valueOf(i))) != null) {
                    rs4.e(rs3);
                }
                int i2 = next2.subStack2;
                if (i2 >= 0 && (rs2 = hashMap.get(Integer.valueOf(i2))) != null) {
                    rs4.f(rs2);
                }
                int i3 = next2.nextBlock;
                if (i3 >= 0 && (rs = hashMap.get(Integer.valueOf(i3))) != null) {
                    rs4.b(rs);
                }
                int size = next2.parameters.size();
                for (int i4 = 0; i4 < size; i4++) {
                    String str = next2.parameters.get(i4);
                    if (str != null && str.length() > 0) {
                        if (str.charAt(0) == '@') {
                            Rs rs5 = hashMap.get(Integer.valueOf(Integer.valueOf(str.substring(1)).intValue()));
                            if (rs5 != null) {
                                rs4.a((Ts) rs4.V.get(i4), rs5);
                            }
                        } else {
                            ((Ss) rs4.V.get(i4)).setArgValue(str);
                            rs4.m();
                        }
                    }
                }
            }
        }
        this.o.getRoot().k();
        this.o.b();
    }

    public final void B() {
        if (!this.u) {
            HistoryBlockBean var1 = bC.d(this.B).i(this.s());
            if (var1 == null) {
                this.C();
            } else {
                label58:
                {
                    BlockBean var5;
                    label57:
                    {
                        int var2 = var1.getActionType();
                        int[] var3;
                        if (var2 == 0) {
                            var3 = new int[2];
                            this.o.getLocationOnScreen(var3);
                            this.a(var1.getAddedData(), var1.getCurrentX() + var3[0], var1.getCurrentY() + var3[1], true);
                            if (var1.getCurrentParentData() == null) {
                                break label58;
                            }
                        } else {
                            if (var2 == 1) {
                                var5 = var1.getCurrentUpdateData();
                                break label57;
                            }

                            if (var2 != 2) {
                                if (var2 != 3) {
                                    break label58;
                                }

                                for (BlockBean var7 : var1.getAfterMoveData()) {
                                    this.o.a(var7, true);
                                }

                                var3 = new int[2];
                                this.o.getLocationOnScreen(var3);
                                this.a(var1.getAfterMoveData(), var1.getCurrentX() + var3[0], var1.getCurrentY() + var3[1], true);
                                if (var1.getCurrentParentData() != null) {
                                    this.a(var1.getCurrentParentData(), true);
                                }

                                if (var1.getCurrentOriginalParent() == null) {
                                    break label58;
                                }

                                var5 = var1.getCurrentOriginalParent();
                                break label57;
                            }

                            ArrayList<BlockBean> var6 = var1.getRemovedData();
                            var2 = var6.size();

                            while (true) {
                                --var2;
                                if (var2 < 0) {
                                    if (var1.getCurrentParentData() == null) {
                                        break label58;
                                    }
                                    break;
                                }

                                this.o.a(var6.get(var2), false);
                            }
                        }

                        var5 = var1.getCurrentParentData();
                    }

                    this.a(var5, true);
                }

                this.C();
            }
        }
    }

    public void C() {
        invalidateOptionsMenu();
    }

    public final void E() {
        ArrayList<BlockBean> blocks = this.o.getBlocks();
        eC a2 = jC.a(this.B);
        String javaName = this.M.getJavaName();
        a2.a(javaName, this.C + "_" + this.D, blocks);
    }

    public final void G() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_add_new_list));
        aBVar.a(R.drawable.add_96_blue);
        View a2 = wB.a((Context) this, R.layout.logic_popup_add_list);
        RadioGroup radioGroup = (RadioGroup) a2.findViewById(R.id.rg_type);
        EditText editText = (EditText) a2.findViewById(R.id.ed_input);
        ((TextInputLayout) a2.findViewById(R.id.ti_input)).setHint(xB.b().a(getApplicationContext(), R.string.logic_editor_hint_enter_variable_name));
        ((TextView) a2.findViewById(R.id.rb_int)).setText(xB.b().a(getApplicationContext(), R.string.logic_variable_type_number));
        ((TextView) a2.findViewById(R.id.rb_string)).setText(xB.b().a(getApplicationContext(), R.string.logic_variable_type_string));
        ((TextView) a2.findViewById(R.id.rb_map)).setText(xB.b().a(getApplicationContext(), R.string.logic_variable_type_map));
        ZB zb = new ZB(this.e, (TextInputLayout) a2.findViewById(R.id.ti_input), uq.b, uq.a(), jC.a(this.B).a(this.M));
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

    public final void H() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_add_new_variable));
        aBVar.a(R.drawable.add_96_blue);
        View a2 = wB.a((Context) this, R.layout.logic_popup_add_variable);
        RadioGroup radioGroup = (RadioGroup) a2.findViewById(R.id.rg_type);
        EditText editText = (EditText) a2.findViewById(R.id.ed_input);
        ((TextInputLayout) a2.findViewById(R.id.ti_input)).setHint(xB.b().a(getApplicationContext(), R.string.logic_editor_hint_enter_variable_name));
        editText.setPrivateImeOptions("defaultInputmode=english;");
        ((TextView) a2.findViewById(R.id.rb_boolean)).setText(xB.b().a(getApplicationContext(), R.string.logic_variable_type_boolean));
        ((TextView) a2.findViewById(R.id.rb_int)).setText(xB.b().a(getApplicationContext(), R.string.logic_variable_type_number));
        ((TextView) a2.findViewById(R.id.rb_string)).setText(xB.b().a(getApplicationContext(), R.string.logic_variable_type_string));
        ((TextView) a2.findViewById(R.id.rb_map)).setText(xB.b().a(getApplicationContext(), R.string.logic_variable_type_map));
        ZB zb = new ZB(getApplicationContext(), (TextInputLayout) a2.findViewById(R.id.ti_input), uq.b, uq.a(), jC.a(this.B).a(this.M));
        aBVar.a(a2);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_add), v -> {
            int i;
            if (radioGroup.getCheckedRadioButtonId() == R.id.rb_boolean) {
                i = 0;
            } else {
                if (radioGroup.getCheckedRadioButtonId() != R.id.rb_int) {
                    if (radioGroup.getCheckedRadioButtonId() == R.id.rb_string) {
                        i = 2;
                    } else if (radioGroup.getCheckedRadioButtonId() == R.id.rb_map) {
                        i = 3;
                    }
                }
                i = 1;
            }
            String obj = editText.getText().toString();
            if (!zb.b()) {
                return;
            }
            b(i, obj);
            mB.a(getApplicationContext(), editText);
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> {
            mB.a(getApplicationContext(), editText);
            aBVar.dismiss();
        });
        aBVar.show();
    }

    public final void I() {
        this.pa = Pp.h().f();
        new MoreblockImporterDialog(this, this.pa, this).show();
    }

    public final void J() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_remove_list));
        aBVar.a(R.drawable.delete_96);
        View a2 = wB.a((Context) this, R.layout.property_popup_selector_single);
        ViewGroup viewGroup = (ViewGroup) a2.findViewById(R.id.rg_content);
        new ArrayList();
        for (Pair<Integer, String> list : jC.a(this.B).j(this.M.getJavaName())) {
            viewGroup.addView(e((String) list.second));
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
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> aBVar.dismiss());
        aBVar.show();
    }

    public final void K() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_remove_variable));
        aBVar.a(R.drawable.delete_96);
        View a2 = wB.a((Context) this, R.layout.property_popup_selector_single);
        ViewGroup viewGroup = (ViewGroup) a2.findViewById(R.id.rg_content);
        new ArrayList();
        for (Pair<Integer, String> next : jC.a(this.B).k(this.M.getJavaName())) {
            RadioButton e = e((String) next.second);
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
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> aBVar.dismiss());
        aBVar.show();
    }

    public void L() {
        try {
            new Handler().postDelayed(() -> new b(this, getApplicationContext()).execute(), 500L);
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

                                this.o.a((BlockBean) var3.get(var2), false);
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
        float a2 = wB.a((Context) this, 1.0f);
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
                    Context context = this.e;
                    fromFile = FileProvider.a(context, getApplicationContext().getPackageName() + ".provider", new File(jC.d(this.B).f(str)));
                } else {
                    fromFile = Uri.fromFile(new File(jC.d(this.B).f(str)));
                }
                Glide.with((FragmentActivity) this).load(fromFile).signature((Key) kC.n()).error(R.drawable.ic_remove_grey600_24dp).into(imageView);
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
            String str = next.id;
            if (str != null && !str.equals("")) {
                arrayList2.add(next.clone());
            }
        }
        for (BlockBean next2 : arrayList2) {
            if (Integer.valueOf(next2.id).intValue() >= 99000000) {
                Integer valueOf2 = Integer.valueOf(next2.id);
                BlockPane blockPane = this.o;
                int i3 = blockPane.g;
                blockPane.g = i3 + 1;
                hashMap.put(valueOf2, Integer.valueOf(i3));
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
        for (BlockBean next3 : arrayList2) {
            if (hashMap.containsKey(Integer.valueOf(next3.id))) {
                next3.id = String.valueOf(hashMap.get(Integer.valueOf(next3.id)));
            } else {
                next3.id = "";
            }
            int size2 = next3.parameters.size();
            for (int i4 = 0; i4 < size2; i4++) {
                String str2 = next3.parameters.get(i4);
                if (str2 != null && str2.length() > 0 && str2.charAt(0) == '@') {
                    int intValue = Integer.valueOf(str2.substring(1)).intValue();
                    int valueOf;
                    if ((valueOf = Integer.valueOf(hashMap.containsKey(Integer.valueOf(intValue)) ? ((Integer) hashMap.get(Integer.valueOf(intValue))).intValue() : 0).intValue()) >= 0) {
                        next3.parameters.set(i4, '@' + String.valueOf(valueOf));
                    } else {
                        next3.parameters.set(i4, "");
                    }
                }
            }
            int i5 = next3.subStack1;
            if (i5 >= 0 && hashMap.containsKey(Integer.valueOf(i5))) {
                next3.subStack1 = ((Integer) hashMap.get(Integer.valueOf(next3.subStack1))).intValue();
            }
            int i6 = next3.subStack2;
            if (i6 >= 0 && hashMap.containsKey(Integer.valueOf(i6))) {
                next3.subStack2 = ((Integer) hashMap.get(Integer.valueOf(next3.subStack2))).intValue();
            }
            int i7 = next3.nextBlock;
            if (i7 >= 0 && hashMap.containsKey(Integer.valueOf(i7))) {
                next3.nextBlock = ((Integer) hashMap.get(Integer.valueOf(next3.nextBlock))).intValue();
            }
        }
        Rs rs = null;
        for (int i8 = 0; i8 < arrayList2.size(); i8++) {
            BlockBean blockBean2 = arrayList2.get(i8);
            String str3 = blockBean2.id;
            if (str3 != null && !str3.equals("")) {
                Rs b2 = b(blockBean2);
                if (i8 == 0) {
                    rs = b2;
                }
                this.o.a(b2, i, i2);
                b2.setOnTouchListener(this);
            }
        }
        for (BlockBean next4 : arrayList2) {
            String str4 = next4.id;
            if (str4 != null && !str4.equals("")) {
                a(next4, false);
            }
        }
        if (rs != null && z) {
            rs.p().k();
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
        if (rs3.ha == ((Integer) rs.getTag()).intValue()) {
            this.x = 0;
        } else if (this.w.ia == ((Integer) rs.getTag()).intValue()) {
            this.x = 2;
        } else if (this.w.ja == ((Integer) rs.getTag()).intValue()) {
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
        RadioGroup var7 = (RadioGroup) var12.findViewById(R.id.rg);
        LinearLayout var11 = (LinearLayout) var12.findViewById(R.id.content);
        ArrayList<String> var8 = jC.d(this.B).m();
        if (xq.a(this.B) || xq.b(this.B)) {
            if ("property_image" == str) {
                var8.add(0, "default_image");
            } else if ("property_background_resource" == str) {
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
        var3.a(xB.b().a(this.getApplicationContext(), R.string.common_word_cancel), v -> var3.dismiss());
        var3.show();
    }

    public void a(Ss ss, boolean z) {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), z ? R.string.logic_editor_title_enter_number_value : R.string.logic_editor_title_enter_string_value));
        aBVar.a(R.drawable.rename_96_blue);
        View a2 = wB.a((Context) this, R.layout.property_popup_input_text);
        EditText editText = (EditText) a2.findViewById(R.id.ed_input);
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:303:0x0648, code lost:
        if (r3 == false) goto L110;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void a(BlockBean blockBean, boolean z) {
        Rs a2;
        Rs a3;
        Rs a4;
        char c;
        eC a5;
        eC a6;
        eC a7;
        eC a8;
        eC a9;
        String str;
        Rs a10;
        Rs a11 = this.o.a(blockBean.id);
        if (a11 != null) {
            a11.ia = -1;
            a11.ja = -1;
            a11.ha = -1;
            int size = blockBean.parameters.size();
            for (int i = 0; i < size; i++) {
                String str2 = blockBean.parameters.get(i);
                if (str2 != null) {
                    if (str2.length() > 0 && str2.charAt(0) == '@') {
                        int intValue = Integer.valueOf(str2.substring(1)).intValue();
                        if (intValue > 0 && (a10 = this.o.a(intValue)) != null) {
                            a11.a((Ts) a11.V.get(i), a10);
                        }
                    } else if (a11.V.get(i) instanceof Ss) {
                        Ss ss = (Ss) a11.V.get(i);
                        String javaName = this.M.getJavaName();
                        String xmlName = this.M.getXmlName();
                        if (this.D.equals("onBindCustomView") && (str = jC.a(this.B).c(this.M.getXmlName(), this.C).customView) != null) {
                            xmlName = ProjectFileBean.getXmlName(str);
                        }
                        if (str2.length() > 0 && ss.b == "m") {
                            String str3 = ss.c;
                            int i2 = 23;
                            int i3 = 25;
                            int i4 = 2;
                            switch (str3.hashCode()) {
                                case -2004438503:
                                    if (str3.equals("spinner")) {
                                        c = 15;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1991117057:
                                    if (str3.equals("tablayout")) {
                                        c = '7';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1811660373:
                                    if (str3.equals("soundpool")) {
                                        c = 27;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1677313857:
                                    if (str3.equals("requestnetwork")) {
                                        c = '\"';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1668293067:
                                    if (str3.equals("patternview")) {
                                        c = ';';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1655966961:
                                    if (str3.equals("activity")) {
                                        c = ')';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1558241498:
                                    if (str3.equals("objectanimator")) {
                                        c = 28;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1557883842:
                                    if (str3.equals("viewpager")) {
                                        c = '8';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1421968056:
                                    if (str3.equals("adview")) {
                                        c = '\r';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1420973643:
                                    if (str3.equals("progressdialog")) {
                                        c = ',';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1332085432:
                                    if (str3.equals("dialog")) {
                                        c = 25;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1228095996:
                                    if (str3.equals("datepickerdialog")) {
                                        c = '-';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1197746358:
                                    if (str3.equals("texttospeech")) {
                                        c = '#';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1183762788:
                                    if (str3.equals("intent")) {
                                        c = 20;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1002626734:
                                    if (str3.equals("textview")) {
                                        c = '\b';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -961721768:
                                    if (str3.equals("locationmanager")) {
                                        c = '&';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -889473228:
                                    if (str3.equals("switch")) {
                                        c = 17;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -877150592:
                                    if (str3.equals("imageview")) {
                                        c = '\n';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -823676088:
                                    if (str3.equals("varInt")) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -823666294:
                                    if (str3.equals("varStr")) {
                                        c = 2;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -709456819:
                                    if (str3.equals("searchview")) {
                                        c = '3';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -563351033:
                                    if (str3.equals("firebase")) {
                                        c = 29;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -431674753:
                                    if (str3.equals("bottomnavigation")) {
                                        c = '9';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -351639837:
                                    if (str3.equals("calendarview")) {
                                        c = '\f';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -341064690:
                                    if (str3.equals("resource")) {
                                        c = '(';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -290065014:
                                    if (str3.equals("speechtotext")) {
                                        c = '$';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -178324674:
                                    if (str3.equals("calendar")) {
                                        c = 22;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 2988580:
                                    if (str3.equals("actv")) {
                                        c = '4';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3143036:
                                    if (str3.equals("file")) {
                                        c = 21;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3322014:
                                    if (str3.equals("list")) {
                                        c = 6;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 3619493:
                                    if (str3.equals("view")) {
                                        c = 7;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 24646380:
                                    if (str3.equals("radiobutton")) {
                                        c = '0';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 62092335:
                                    if (str3.equals("firebaseauth")) {
                                        c = 30;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 103652369:
                                    if (str3.equals("mactv")) {
                                        c = '5';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 109627663:
                                    if (str3.equals("sound")) {
                                        c = '*';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 110364485:
                                    if (str3.equals("timer")) {
                                        c = 23;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 181944945:
                                    if (str3.equals("listInt")) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 181948382:
                                    if (str3.equals("listMap")) {
                                        c = 5;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 181954739:
                                    if (str3.equals("listStr")) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 235637425:
                                    if (str3.equals("varBool")) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 319075051:
                                    if (str3.equals("gridview")) {
                                        c = '6';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 325741829:
                                    if (str3.equals("gyroscope")) {
                                        c = ' ';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 344557910:
                                    if (str3.equals("ratingbar")) {
                                        c = '1';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 452783582:
                                    if (str3.equals("videoad")) {
                                        c = '+';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 485199813:
                                    if (str3.equals("mediaplayer")) {
                                        c = 26;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 595233003:
                                    if (str3.equals("notification")) {
                                        c = '/';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 690484860:
                                    if (str3.equals("bluetoothconnect")) {
                                        c = '%';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 837734913:
                                    if (str3.equals("mapview")) {
                                        c = 19;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1105738265:
                                    if (str3.equals("vibrator")) {
                                        c = 24;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1131540166:
                                    if (str3.equals("progressbar")) {
                                        c = 18;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1224424441:
                                    if (str3.equals("webview")) {
                                        c = 16;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1234536982:
                                    if (str3.equals("resource_bg")) {
                                        c = '\'';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1322145871:
                                    if (str3.equals("interstitialad")) {
                                        c = '!';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1333959072:
                                    if (str3.equals("videoview")) {
                                        c = '2';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1346661443:
                                    if (str3.equals("listview")) {
                                        c = 14;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1536891843:
                                    if (str3.equals("checkbox")) {
                                        c = '\t';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1719159444:
                                    if (str3.equals("firebasestorage")) {
                                        c = 31;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1907714723:
                                    if (str3.equals("timepickerdialog")) {
                                        c = '.';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1971813019:
                                    if (str3.equals("seekbar")) {
                                        c = 11;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 2085227356:
                                    if (str3.equals("sidebar")) {
                                        c = '<';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 2113752264:
                                    if (str3.equals("badgeview")) {
                                        c = ':';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    c = 65535;
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    a5 = jC.a(this.B);
                                    i4 = 1;
                                    a5.f(javaName, i4, str2);
                                    break;
                                case 1:
                                    jC.a(this.B).f(javaName, 0, str2);
                                    break;
                                case 2:
                                    a5 = jC.a(this.B);
                                    a5.f(javaName, i4, str2);
                                    break;
                                case 3:
                                    a6 = jC.a(this.B);
                                    i4 = 1;
                                    a6.e(javaName, i4, str2);
                                    break;
                                case 4:
                                    a6 = jC.a(this.B);
                                    a6.e(javaName, i4, str2);
                                    break;
                                case 6:
                                    boolean e = jC.a(this.B).e(javaName, 1, str2);
                                    if (!e) {
                                        e = jC.a(this.B).e(javaName, 2, str2);
                                        break;
                                    }
                                    break;
                                case 5:
                                    a6 = jC.a(this.B);
                                    i4 = 3;
                                    a6.e(javaName, i4, str2);
                                    break;
                                case 7:
                                    jC.a(this.B).h(xmlName, str2);
                                    break;
                                case '\b':
                                    jC.a(this.B).g(xmlName, str2);
                                    break;
                                case '\t':
                                    jC.a(this.B).e(xmlName, str2);
                                    break;
                                case '\n':
                                    a7 = jC.a(this.B);
                                    i2 = 6;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case 11:
                                    a7 = jC.a(this.B);
                                    i2 = 14;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case '\f':
                                    a7 = jC.a(this.B);
                                    i2 = 15;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case '\r':
                                    a7 = jC.a(this.B);
                                    i2 = 17;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case 14:
                                    a7 = jC.a(this.B);
                                    i2 = 9;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case 15:
                                    a7 = jC.a(this.B);
                                    i2 = 10;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case 16:
                                    a7 = jC.a(this.B);
                                    i2 = 7;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case 17:
                                    a7 = jC.a(this.B);
                                    i2 = 13;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case 18:
                                    a7 = jC.a(this.B);
                                    i2 = 8;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case 19:
                                    a7 = jC.a(this.B);
                                    i2 = 18;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case 20:
                                    a8 = jC.a(this.B);
                                    i4 = 1;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case 21:
                                    a8 = jC.a(this.B);
                                    a8.d(javaName, i4, str2);
                                    break;
                                case 22:
                                    a8 = jC.a(this.B);
                                    i4 = 3;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case 23:
                                    a8 = jC.a(this.B);
                                    i4 = 5;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case 24:
                                    a8 = jC.a(this.B);
                                    i4 = 4;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case 25:
                                    a8 = jC.a(this.B);
                                    i4 = 7;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case 26:
                                    a8 = jC.a(this.B);
                                    i4 = 8;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case 27:
                                    a8 = jC.a(this.B);
                                    i4 = 9;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case 28:
                                    a8 = jC.a(this.B);
                                    i4 = 10;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case 29:
                                    a8 = jC.a(this.B);
                                    i4 = 6;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case 30:
                                    a8 = jC.a(this.B);
                                    i4 = 12;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case 31:
                                    a8 = jC.a(this.B);
                                    i4 = 14;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case ' ':
                                    a8 = jC.a(this.B);
                                    i4 = 11;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case '!':
                                    a8 = jC.a(this.B);
                                    i4 = 13;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case '\"':
                                    a8 = jC.a(this.B);
                                    i4 = 17;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case '#':
                                    a8 = jC.a(this.B);
                                    i4 = 18;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case '$':
                                    a8 = jC.a(this.B);
                                    i4 = 19;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case '%':
                                    a8 = jC.a(this.B);
                                    i4 = 20;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case '&':
                                    a8 = jC.a(this.B);
                                    i4 = 21;
                                    a8.d(javaName, i4, str2);
                                    break;
                                case '\'':
                                case '(':
                                    for (String value : jC.d(this.B).m()) {
                                        str2.equals(value);
                                    }
                                    break;
                                case ')':
                                    for (String next : jC.b(this.B).d()) {
                                        str2.equals(next.substring(0, next.indexOf(".java")));
                                    }
                                    break;
                                case '*':
                                    for (String value : jC.d(this.B).p()) {
                                        str2.equals(value);
                                    }
                                    break;
                                case '+':
                                    a9 = jC.a(this.B);
                                    i3 = 22;
                                    a9.d(xmlName, i3, str2);
                                    break;
                                case ',':
                                    jC.a(this.B).d(xmlName, 23, str2);
                                    break;
                                case '-':
                                    jC.a(this.B).d(xmlName, 24, str2);
                                    break;
                                case '.':
                                    a9 = jC.a(this.B);
                                    a9.d(xmlName, i3, str2);
                                    break;
                                case '/':
                                    a9 = jC.a(this.B);
                                    i3 = 26;
                                    a9.d(xmlName, i3, str2);
                                    break;
                                case '0':
                                    a7 = jC.a(this.B);
                                    i2 = 19;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case '1':
                                    a7 = jC.a(this.B);
                                    i2 = 20;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case '2':
                                    a7 = jC.a(this.B);
                                    i2 = 21;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case '3':
                                    a7 = jC.a(this.B);
                                    i2 = 22;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case '4':
                                    a7 = jC.a(this.B);
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case '5':
                                    jC.a(this.B).g(xmlName, 24, str2);
                                    break;
                                case '6':
                                    jC.a(this.B).g(xmlName, 25, str2);
                                    break;
                                case '7':
                                    a7 = jC.a(this.B);
                                    i2 = 30;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case '8':
                                    a7 = jC.a(this.B);
                                    i2 = 31;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case '9':
                                    a7 = jC.a(this.B);
                                    i2 = 32;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case ':':
                                    a7 = jC.a(this.B);
                                    i2 = 33;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case ';':
                                    a7 = jC.a(this.B);
                                    i2 = 34;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                case '<':
                                    a7 = jC.a(this.B);
                                    i2 = 35;
                                    a7.g(xmlName, i2, str2);
                                    break;
                                default:
                                    this.extraPaletteBlock.e(ss.c, str2);
                                    break;
                            }
                        }
                        ss.setArgValue(str2);
                        a11.m();
                    }
                }
            }
            int i5 = blockBean.subStack1;
            if (i5 >= 0 && (a4 = this.o.a(i5)) != null) {
                a11.e(a4);
            }
            int i6 = blockBean.subStack2;
            if (i6 >= 0 && (a3 = this.o.a(i6)) != null) {
                a11.f(a3);
            }
            int i7 = blockBean.nextBlock;
            if (i7 >= 0 && (a2 = this.o.a(i7)) != null) {
                a11.b(a2);
            }
            a11.m();
            if (!z) {
                return;
            }
            a11.p().k();
            this.o.b();
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
                if (blockBean.spec.equals(ReturnMoreblockManager.getMbName((String) it.next().second))) {
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
        return new Rs(this, Integer.valueOf(blockBean.id).intValue(), blockBean.spec, blockBean.type, blockBean.typeName, blockBean.opCode);
    }

    public final RadioButton b(String str) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText("");
        radioButton.setTag(str);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (wB.a((Context) this, 1.0f) * 60.0f));
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
        View a2 = wB.a((Context) this, R.layout.color_picker);
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
        EditText editText = (EditText) a2.findViewById(R.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        ZB zb = new ZB(getBaseContext(), (TextInputLayout) a2.findViewById(R.id.ti_input), uq.b, uq.a(), jC.a(this.B).a(this.M));
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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, (int) (wB.a((Context) this, 1.0f) * 60.0f));
        radioButton.setGravity(Gravity.CENTER | Gravity.LEFT);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    public final void c(int i, String str) {
        if (this.ka == null) {
            this.ka = new ArrayList<>();
        }
        for (Pair<Integer, String> next : jC.a(this.B).j(this.M.getJavaName())) {
            if (((Integer) next.first).intValue() == i && ((String) next.second).equals(str)) {
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
            if (((Integer) next2.first).intValue() == i && ((String) next2.second).equals(str)) {
                z = true;
                break;
            }
        }
        if (!z) {
            this.ka.add(new Pair<>(Integer.valueOf(i), str));
        }
    }

    public final void c(Rs rs) {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_block_favorites_save_title));
        aBVar.a(R.drawable.ic_bookmark_red_48dp);
        View a2 = wB.a((Context) this, R.layout.property_popup_save_to_favorite);
        ((TextView) a2.findViewById(R.id.tv_favorites_guide)).setText(xB.b().a(getApplicationContext(), R.string.logic_block_favorites_save_guide));
        EditText editText = (EditText) a2.findViewById(R.id.ed_input);
        editText.setPrivateImeOptions("defaultInputmode=english;");
        editText.setLines(1);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        NB nb = new NB(this, (TextInputLayout) a2.findViewById(R.id.ti_input), Mp.h().g());
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
        View a2 = wB.a((Context) this, R.layout.property_popup_input_text);
        ((TextInputLayout) a2.findViewById(R.id.ti_input)).setHint(xB.b().a(this, R.string.property_hint_enter_value));
        EditText editText = (EditText) a2.findViewById(R.id.ed_input);
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
                if (((String) it.next().first).equals(str)) {
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

    public final LinearLayout d(String str) {
        float a2 = wB.a((Context) this, 1.0f);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (a2 * 60.0f)));
        linearLayout.setGravity(Gravity.CENTER | Gravity.LEFT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1.0f;
        textView.setLayoutParams(layoutParams);
        textView.setText(str);
        linearLayout.addView(textView);
        TextView textView2 = new TextView(this);
        new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT).weight = 1.0f;
        textView2.setLayoutParams(layoutParams);
        textView2.setText("Preview");
        textView2.setTypeface(!str.toLowerCase().equals("default_font".toLowerCase()) ? Typeface.createFromFile(jC.d(this.B).d(str)) : Typeface.DEFAULT);
        linearLayout.addView(textView2);
        return linearLayout;
    }

    public final RadioButton d(String str, String str2) {
        RadioButton radioButton = new RadioButton(this);
        radioButton.setText(str + " : " + str2);
        radioButton.setTag(str2);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (wB.a((Context) this, 1.0f) * 40.0f));
        radioButton.setGravity(Gravity.CENTER | Gravity.LEFT);
        radioButton.setLayoutParams(layoutParams);
        return radioButton;
    }

    public final void d(int i, String str) {
        if (this.ja == null) {
            this.ja = new ArrayList<>();
        }
        for (Pair<Integer, String> next : jC.a(this.B).k(this.M.getJavaName())) {
            if (((Integer) next.first).intValue() == i && ((String) next.second).equals(str)) {
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
            if (((Integer) next2.first).intValue() == i && ((String) next2.second).equals(str)) {
                z = true;
                break;
            }
        }
        if (!z) {
            this.ja.add(new Pair<>(Integer.valueOf(i), str));
        }
    }

    public final void d(Ss ss) {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_select_font));
        aBVar.a(R.drawable.abc_96_color);
        View a2 = wB.a((Context) this, R.layout.property_popup_selector_color);
        RadioGroup radioGroup = (RadioGroup) a2.findViewById(R.id.rg);
        LinearLayout linearLayout = (LinearLayout) a2.findViewById(R.id.content);
        ArrayList<String> k = jC.d(this.B).k();
        if (xq.a(this.B) || xq.b(this.B)) {
            k.add(0, "default_font");
        }
        for (String next : k) {
            RadioButton b2 = b(next);
            radioGroup.addView(b2);
            if (next.equals(ss.getArgValue())) {
                b2.setChecked(true);
            }
            LinearLayout d = d(next);
            d.setOnClickListener(v -> {
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(linearLayout.indexOfChild(v));
                radioButton.setChecked(true);
            });
            linearLayout.addView(d);
        }
        aBVar.a(a2);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_select), v -> {
            int childCount = radioGroup.getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    break;
                }
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                if (radioButton.isChecked()) {
                    a(ss, radioButton.getTag());
                    break;
                }
                i++;
            }
            aBVar.dismiss();
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> aBVar.dismiss());
        aBVar.show();
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
        View a2 = wB.a((Context) this, R.layout.property_popup_input_intent_data);
        ((TextView) a2.findViewById(R.id.tv_desc_intent_usage)).setText(xB.b().a(getApplicationContext(), R.string.property_description_component_intent_usage));
        EditText editText = (EditText) a2.findViewById(R.id.ed_input);
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
                i2 = i3 - ((int) wB.a((Context) this, 320.0f));
                a2 = ViewGroup.LayoutParams.MATCH_PARENT;
            } else {
                a2 = ((i3 - GB.a(this.e)) - GB.f(this.e)) - ((int) wB.a((Context) this, 240.0f));
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
        View a2 = wB.a((Context) this, R.layout.property_popup_selector_single);
        ViewGroup viewGroup = (ViewGroup) a2.findViewById(R.id.rg_content);
        new ArrayList();
        String xmlName = this.M.getXmlName();
        if (this.D.equals("onBindCustomView") && (str = jC.a(this.B).c(this.M.getXmlName(), this.C).customView) != null) {
            xmlName = ProjectFileBean.getXmlName(str);
        }
        asdAll.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_select_view));
        for (Pair<Integer, String> next : jC.a(this.B).d(xmlName, ss.getClassInfo().a())) {
            viewGroup.addView(d(ViewBean.getViewTypeName(((Integer) next.first).intValue()), (String) next.second));
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
        asdAll.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> asdAll.dismiss());
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
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> aBVar.dismiss());
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
        LinearLayout linearLayout;
        int i2;
        if (2 == i) {
            this.K.setLayoutParams(new LinearLayout.LayoutParams((int) wB.a((Context) this, 320.0f), ViewGroup.LayoutParams.MATCH_PARENT));
            LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams2.gravity = Gravity.CENTER | Gravity.BOTTOM;
            int dimension = (int) getResources().getDimension(R.dimen.action_button_margin);
            layoutParams2.setMargins(dimension, dimension, dimension, dimension);
            this.L.setLayoutParams(layoutParams2);
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.topMargin = GB.a(this.e);
            linearLayout = this.J;
            i2 = LinearLayout.HORIZONTAL;
        } else {
            this.K.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) wB.a((Context) this, 240.0f)));
            LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams3.gravity = Gravity.CENTER | Gravity.RIGHT;
            int dimension2 = (int) getResources().getDimension(R.dimen.action_button_margin);
            layoutParams3.setMargins(dimension2, dimension2, dimension2, dimension2);
            this.L.setLayoutParams(layoutParams3);
            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            linearLayout = this.J;
            i2 = LinearLayout.VERTICAL;
        }
        linearLayout.setOrientation(i2);
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
        SoundPool soundPool;
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_select_sound));
        aBVar.a(R.drawable.music_48);
        View a2 = wB.a((Context) this, R.layout.property_popup_selector_single);
        RadioGroup radioGroup = (RadioGroup) a2.findViewById(R.id.rg_content);
        if (Build.VERSION.SDK_INT >= 21) {
            AudioAttributes.Builder builder = new AudioAttributes.Builder();
            builder.setUsage(AudioAttributes.USAGE_MEDIA);
            builder.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC);
            AudioAttributes build = builder.build();
            SoundPool.Builder builder2 = new SoundPool.Builder();
            builder2.setMaxStreams(1);
            builder2.setAudioAttributes(build);
            soundPool = builder2.build();
        } else {
            soundPool = new SoundPool(1, 3, 0);
        }
        soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> {
            if (soundPool1 != null) {
                soundPool1.play(sampleId, 1, 1, 1, 0, 1);
            }
        });
        for (String next : jC.d(this.B).p()) {
            RadioButton e = e(next);
            radioGroup.addView(e);
            if (next.equals(ss.getArgValue())) {
                e.setChecked(true);
            }
            e.setOnClickListener(v -> {
                soundPool.load(jC.d(B).i(((RadioButton) v).getText().toString()), 1);
            });
        }
        aBVar.a(a2);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.common_word_select), v -> {
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
        });
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> aBVar.dismiss());
        aBVar.show();
    }

    public final void h(String str) {
        if (!Qp.g().b(str)) {
            return;
        }
        ProjectResourceBean a2 = Qp.g().a(str);
        try {
            this.P.a(wq.a() + File.separator + "sound" + File.separator + "data" + File.separator + a2.resFullName, wq.t() + File.separator + this.B + File.separator + a2.resFullName);
            jC.d(this.B).c.add(a2);
        } catch (Exception e) {
            e.printStackTrace();
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
        Pair<Integer, String>[] a2;
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), R.string.logic_editor_title_select_typeface));
        aBVar.a(R.drawable.abc_96_color);
        View a3 = wB.a((Context) this, R.layout.property_popup_selector_single);
        RadioGroup radioGroup = (RadioGroup) a3.findViewById(R.id.rg_content);
        for (Pair<Integer, String> pair : sq.a("property_text_style")) {
            RadioButton e = e((String) pair.second);
            radioGroup.addView(e);
            if (((String) pair.second).equals(ss.getArgValue())) {
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
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> aBVar.dismiss());
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
        aBVar.a(xB.b().a(getApplicationContext(), R.string.common_word_cancel), v -> aBVar.dismiss());
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
        boolean z = this.X;
        if (z) {
            e(!z);
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
        Intent intent;
        int i;
        if (mB.a()) {
            return;
        }
        if (view.getTag() != null) {
            if (view.getTag().equals("variableAdd")) {
                H();
            } else if (view.getTag().equals("variableRemove")) {
                K();
            } else if (view.getTag().equals("listAdd")) {
                G();
            } else if (view.getTag().equals("listRemove")) {
                J();
            } else {
                label45:
                {
                    if (view.getTag().equals("blockAdd")) {
                        intent = new Intent(getApplicationContext(), MakeBlockActivity.class);
                        intent.putExtra("sc_id", this.B);
                        intent.putExtra("project_file", this.M);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        i = 222;
                    } else if (view.getTag().equals("componentAdd")) {
                        intent = new Intent(getApplicationContext(), ComponentAddActivity.class);
                        intent.putExtra("sc_id", this.B);
                        intent.putExtra("project_file", this.M);
                        intent.putExtra("filename", this.M.getJavaName());
                        i = 224;
                    } else if (view.getTag().equals("blockImport")) {
                        I();
                        break label45;
                    } else {
                        break label45;
                    }
                    startActivityForResult(intent, i);
                }
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
        StringBuilder sb;
        String str;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.k = toolbar;
        a(toolbar);
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
        } else {
            if (this.C.equals("_fab")) {
                d = d();
                sb = new StringBuilder();
                str = "fab : ";
            } else {
                d = d();
                sb = new StringBuilder();
                sb.append(ReturnMoreblockManager.getMbName(this.C));
                str = " : ";
            }
            sb.append(str);
            sb.append(stringExtra);
            stringExtra = sb.toString();
        }
        d.a(stringExtra);
        PaletteSelector paletteSelector = (PaletteSelector) findViewById(R.id.palette_selector);
        this.l = paletteSelector;
        paletteSelector.setOnBlockCategorySelectListener(this);
        this.m = (PaletteBlock) findViewById(R.id.palette_block);
        this.p = (ViewDummy) findViewById(R.id.dummy);
        ViewLogicEditor viewLogicEditor = (ViewLogicEditor) findViewById(R.id.editor);
        this.n = viewLogicEditor;
        this.o = viewLogicEditor.getBlockPane();
        this.I = (LinearLayout) findViewById(R.id.layout_ads);
        this.J = (LinearLayout) findViewById(R.id.layout_palette);
        this.K = (LinearLayout) findViewById(R.id.area_palette);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab_toggle_palette);
        this.L = floatingActionButton;
        floatingActionButton.setOnClickListener(v -> e(!X));
        this.N = (LogicTopMenu) findViewById(R.id.top_menu);
        this.O = (LogicEditorDrawer) findViewById(R.id.right_drawer);
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
        switch (menuItem.getItemId()) {
            case R.id.menu_block_helper:
                e(false);
                g(!this.ia);
                break;
            case R.id.menu_logic_redo:
                B();
                break;
            case R.id.menu_logic_undo:
                M();
                break;
            case R.id.menu_logic_showsource:
                showSourceCode();
                break;
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
            var8 = xB.b().a(this.getApplicationContext(), R.string.root_spec_common_define);
            var8 = var8 + " " + ReturnMoreblockManager.getLogicEditorTitle(var2);
        } else if (this.C.equals("_fab")) {
            var8 = xB.b().a(this.getApplicationContext(), "fab", this.D);
        } else {
            var8 = xB.b().a(this.getApplicationContext(), this.C, this.D);
        }

        this.E = var8;
        this.o.a(this.E, this.D);
        ArrayList var10 = FB.c(this.E);
        int var4 = 0;

        int var6;
        for (int var5 = 0; var4 < var10.size(); var5 = var6) {
            var2 = (String) var10.get(var4);
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
                        var6 = var5;
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
        Rs p3;
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
            ViewDummy viewDummy = this.p;
            float rawX = motionEvent.getRawX();
            float f = this.s;
            float rawY = motionEvent.getRawY();
            float f2 = this.t;
            viewDummy.a(view, rawX - f, rawY - f2, this.q - this.s, this.r - f2, this.S, this.T);
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
            ViewLogicEditor viewLogicEditor = this.n;
            int[] iArr2 = this.v;
            if (viewLogicEditor.a(iArr2[0], iArr2[1])) {
                this.p.setAllow(true);
                int[] iArr3 = this.v;
                this.o.c((Rs) view, iArr3[0], iArr3[1]);
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
            HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder();
            eventBuilder.setCategory("editor");
            eventBuilder.setAction("block");
            eventBuilder.setLabel("Block drop");
            this.d.send(eventBuilder.build());
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
                    Rs rs3 = this.w;
                    if (rs3 != null) {
                        if (this.x == 0) {
                            rs3.ha = ((Integer) view.getTag()).intValue();
                        }
                        if (this.x == 2) {
                            this.w.ia = ((Integer) view.getTag()).intValue();
                        }
                        if (this.x == 3) {
                            this.w.ja = ((Integer) view.getTag()).intValue();
                        }
                        if (this.x == 5) {
                            this.w.a((Ts) this.w.V.get(this.y), rs2);
                        }
                        Rs rs4 = this.w;
                        rs2.E = rs4;
                        p = rs4.p();
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
                    int intValue = Integer.valueOf(rs5.getBean().id).intValue();
                    Rs rs6 = this.w;
                    if (rs6 != null) {
                        BlockBean clone = rs6.getBean().clone();
                        int i = this.x;
                        if (i == 0) {
                            clone.nextBlock = intValue;
                        } else if (i == 2) {
                            clone.subStack1 = intValue;
                        } else if (i == 3) {
                            clone.subStack2 = intValue;
                        } else if (i == 5) {
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
                    if (rs6 != null) {
                        blockBean3 = rs6.getBean().clone();
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
                Rs rs8 = this.w;
                if (rs8 != null) {
                    if (this.x == 0) {
                        rs8.ha = ((Integer) view.getTag()).intValue();
                    }
                    if (this.x == 2) {
                        this.w.ia = ((Integer) view.getTag()).intValue();
                    }
                    if (this.x == 3) {
                        this.w.ja = ((Integer) view.getTag()).intValue();
                    }
                    if (this.x == 5) {
                        this.w.a((Ts) this.w.V.get(this.y), rs7);
                    }
                    Rs rs9 = this.w;
                    rs7.E = rs9;
                    p3 = rs9.p();
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
                Rs rs11 = this.w;
                if (rs11 != null) {
                    if (this.x == 0) {
                        rs11.ha = ((Integer) view.getTag()).intValue();
                    }
                    if (this.x == 2) {
                        this.w.ia = ((Integer) view.getTag()).intValue();
                    }
                    if (this.x == 3) {
                        this.w.ja = ((Integer) view.getTag()).intValue();
                    }
                    if (this.x == 5) {
                        this.w.a((Ts) this.w.V.get(this.y), rs10);
                    }
                    Rs rs12 = this.w;
                    rs10.E = rs12;
                    p2 = rs12.p();
                } else {
                    p2 = rs10.p();
                }
                p2.k();
                ArrayList<Rs> allChildren2 = rs10.getAllChildren();
                ArrayList<BlockBean> arrayList2 = new ArrayList<>();
                for (Rs rs : allChildren2) {
                    BlockBean clone2 = rs.getBean().clone();
                    clone2.id = String.valueOf(Integer.valueOf(clone2.id).intValue() + 99000000);
                    int i2 = clone2.nextBlock;
                    if (i2 > 0) {
                        clone2.nextBlock = i2 + 99000000;
                    }
                    int i3 = clone2.subStack1;
                    if (i3 > 0) {
                        clone2.subStack1 = i3 + 99000000;
                    }
                    int i4 = clone2.subStack2;
                    if (i4 > 0) {
                        clone2.subStack2 = i4 + 99000000;
                    }
                    for (int i5 = 0; i5 < clone2.parameters.size(); i5++) {
                        String str = clone2.parameters.get(i5);
                        if (str != null && str.length() > 0 && str.charAt(0) == '@') {
                            String substring = str.substring(1);
                            clone2.parameters.set(i5, "@" + String.valueOf(Integer.valueOf(substring).intValue() + 99000000));
                        }
                    }
                    arrayList2.add(clone2);
                }
                int[] iArr5 = new int[2];
                this.n.getLocationOnScreen(iArr5);
                int width = iArr5[0] + (this.n.getWidth() / 2);
                int a2 = iArr5[1] + ((int) wB.a(getApplicationContext(), 4.0f));
                ArrayList<BlockBean> a3 = a(arrayList2, width, a2, true);
                int[] iArr6 = new int[2];
                this.o.getLocationOnScreen(iArr6);
                bC.d(this.B).a(s(), a3, width - iArr6[0], a2 - iArr6[1], (BlockBean) null, (BlockBean) null);
                C();
            } else if (view instanceof Rs) {
                this.p.a(this.v);
                Rs rs13 = (Rs) view;
                if (rs13.getBlockType() == 1) {
                    int addTargetId = this.o.getAddTargetId();
                    BlockBean clone3 = addTargetId >= 0 ? this.o.a(addTargetId).getBean().clone() : null;
                    int[] iArr7 = this.v;
                    Rs a4 = a(rs13, iArr7[0], iArr7[1], false);
                    if (addTargetId >= 0) {
                        blockBean3 = this.o.a(addTargetId).getBean().clone();
                    }
                    int[] iArr8 = new int[2];
                    this.o.getLocationOnScreen(iArr8);
                    bC d = bC.d(this.B);
                    String s = s();
                    BlockBean clone4 = a4.getBean().clone();
                    int[] iArr9 = this.v;
                    d.a(s, clone4, iArr9[0] - iArr8[0], iArr9[1] - iArr8[1], clone3, blockBean3);
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
                    int[] iArr10 = this.v;
                    ArrayList<BlockBean> a5 = a(data, iArr10[0], iArr10[1], true);
                    if (a5.size() > 0) {
                        Rs a6 = this.o.a(a5.get(0).id);
                        int[] iArr11 = this.v;
                        a(a6, iArr11[0], iArr11[1], true);
                        if (addTargetId2 >= 0) {
                            blockBean3 = this.o.a(addTargetId2).getBean().clone();
                        }
                        int[] iArr12 = new int[2];
                        this.o.getLocationOnScreen(iArr12);
                        bC d2 = bC.d(this.B);
                        String s2 = s();
                        int[] iArr13 = this.v;
                        d2.a(s2, a5, iArr13[0] - iArr12[0], iArr13[1] - iArr12[1], clone5, blockBean3);
                    }
                    this.o.c();
                } else {
                    this.o.a(rs13, 0);
                    int intValue2 = Integer.valueOf(rs13.getBean().id).intValue();
                    Rs rs14 = this.w;
                    if (rs14 != null) {
                        blockBean = rs14.getBean().clone();
                        int i6 = this.x;
                        if (i6 == 0) {
                            blockBean.nextBlock = intValue2;
                        } else if (i6 == 2) {
                            blockBean.subStack1 = intValue2;
                        } else if (i6 == 3) {
                            blockBean.subStack2 = intValue2;
                        } else if (i6 == 5) {
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
                    int[] iArr14 = this.v;
                    a(rs13, iArr14[0], iArr14[1], true);
                    ArrayList<BlockBean> arrayList4 = new ArrayList<>();
                    for (Rs rs : allChildren3) {
                        arrayList4.add(rs.getBean().clone());
                    }
                    BlockBean clone7 = rs14 != null ? rs14.getBean().clone() : null;
                    if (a7 != null) {
                        blockBean3 = a7.getBean().clone();
                    }
                    BlockBean blockBean4 = blockBean3;
                    if (blockBean == null || clone7 == null || !blockBean.isEqual(clone7)) {
                        int[] iArr15 = new int[2];
                        this.o.getLocationOnScreen(iArr15);
                        bC d3 = bC.d(this.B);
                        String s3 = s();
                        int i7 = iArr15[0];
                        int i8 = iArr15[1];
                        int[] iArr16 = this.v;
                        d3.a(s3, arrayList3, arrayList4, ((int) this.s) - i7, ((int) this.t) - i8, iArr16[0] - iArr15[0], iArr16[1] - iArr15[1], blockBean, clone7, clone6, blockBean4);
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

    /* JADX WARN: Removed duplicated region for block: B:21:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00e3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void r() {
        ViewLogicEditor viewLogicEditor;
        int[] iArr;
        if (this.Y == null) {
            return;
        }
        this.m.setDragEnabled(false);
        this.n.setScrollEnabled(false);
        this.O.setDragEnabled(false);
        if (this.ia) {
            g(false);
        }
        if (this.G) {
            this.F.vibrate(100L);
        }
        this.u = true;
        if (((Rs) this.Y).getBlockType() == 0) {
            a((Rs) this.Y);
            f(true);
            h(true);
            this.p.a((Rs) this.Y);
            this.o.a((Rs) this.Y, 8);
            this.o.c((Rs) this.Y);
        } else if (((Rs) this.Y).getBlockType() == 2) {
            f(false);
            h(true);
            this.p.a((Rs) this.Y);
            BlockPane blockPane = this.o;
            View view = this.Y;
            blockPane.a((Rs) view, ((Us) view).getData());
            float f = this.q - this.s;
            float f2 = this.r - this.t;
            this.p.a(this.Y, f, f2, f, f2, this.S, this.T);
            this.p.a(this.v);
            viewLogicEditor = this.n;
            iArr = this.v;
            if (viewLogicEditor.a(iArr[0], iArr[1])) {
                this.p.setAllow(false);
                this.o.d();
                return;
            }
            this.p.setAllow(true);
            int[] iArr2 = this.v;
            this.o.c((Rs) this.Y, iArr2[0], iArr2[1]);
            return;
        } else {
            this.p.a((Rs) this.Y);
        }
        this.o.a((Rs) this.Y);
        float f3 = this.q - this.s;
        float f22 = this.r - this.t;
        this.p.a(this.Y, f3, f22, f3, f22, this.S, this.T);
        this.p.a(this.v);
        viewLogicEditor = this.n;
        iArr = this.v;
        if (viewLogicEditor.a(iArr[0], iArr[1])) {
        }
    }

    public final String s() {
        return bC.a(this.M.getJavaName(), this.C, this.D);
    }

    public void showSourceCode() {
        yq yqVar = new yq(this, this.B);
        yqVar.a(jC.c(this.B), jC.b(this.B), jC.a(this.B), false);
        String a2 = new Fx(this.M.getActivityName(), yqVar.N, "", this.o.getBlocks()).a();
        try {
            a2 = Lx.j(a2);
        } catch (Exception e) {
        }
        SourceCodeDialog.show(this, a2);
    }

    public final void t() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.O, "TranslationX", 0.0f);
        this.fa = ofFloat;
        ofFloat.setDuration(500L);
        this.fa.setInterpolator(new DecelerateInterpolator());
        LogicEditorDrawer logicEditorDrawer = this.O;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(logicEditorDrawer, "TranslationX", logicEditorDrawer.getHeight());
        this.ga = ofFloat2;
        ofFloat2.setDuration(300L);
        this.ga.setInterpolator(new DecelerateInterpolator());
        this.ha = true;
    }

    public final void w() {
        this.pa = Pp.h().f();
        this.oa.c();
    }

    public final void x() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.N, "TranslationY", 0.0f);
        this.ba = ofFloat;
        ofFloat.setDuration(500L);
        this.ba.setInterpolator(new DecelerateInterpolator());
        LogicTopMenu logicTopMenu = this.N;
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(logicTopMenu, "TranslationY", logicTopMenu.getHeight() * (-1));
        this.ca = ofFloat2;
        ofFloat2.setDuration(300L);
        this.ca.setInterpolator(new DecelerateInterpolator());
        this.da = true;
    }

    public final void z() {
        ArrayList<BlockCollectionBean> f = Mp.h().f();
        this.O.a();
        for (BlockCollectionBean next : f) {
            this.O.a(next.name, next.blocks).setOnTouchListener(this);
        }
    }
}
