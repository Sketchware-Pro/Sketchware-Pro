package com.besome.sketch.editor.component;

import a.a.a.Cs;
import a.a.a.Ds;
import a.a.a.Es;
import a.a.a.Fs;
import a.a.a.GB;
import a.a.a.Gs;
import a.a.a.Hs;
import a.a.a.Is;
import a.a.a.Js;
import a.a.a.Ks;
import a.a.a.SB;
import a.a.a.ZB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.ha;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.wB;
import a.a.a.xB;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.google.android.flexbox.FlexItem;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.HashMap;
import mod.agus.jcoderz.component.ManageComponent;
import mod.hilal.saif.components.ComponentsHandler;

public class ComponentAddActivity extends BaseDialogActivity {
    public TextView A;
    public TextView B;
    public ImageView C;
    public ImageView D;
    public ImageView E;
    public String F;
    public ProjectFileBean G;
    public LinearLayout H;
    public TextView I;
    public EditText J;
    public EditText K;
    public EditText L;
    public EditText M;
    public TextInputLayout N;
    public TextInputLayout O;
    public TextInputLayout P;
    public TextInputLayout Q;
    public TextView R;
    public TextView S;
    public ZB T;
    public SB U;
    public SB V;
    public SB W;
    public LinearLayout X;
    public LinearLayout Y;
    public RelativeLayout Z;
    public Button aa;
    public Button ba;
    public RecyclerView t;
    public a u;
    public ArrayList<ComponentBean> v;
    public HashMap<Integer, Pair<Integer, Integer>> w;
    public boolean x;
    public boolean y;
    public TextView z;

    public final boolean n() {
        int i = this.v.get(this.u.c).type;
        String obj = this.J.getText().toString();
        if (!this.T.b()) {
            return false;
        }
        if (i == 2) {
            if (!this.U.b()) {
                return false;
            }
            jC.a(this.F).a(this.G.getJavaName(), i, obj, this.K.getText().toString());
        } else if (i == 6 || i == 14) {
            if (!this.V.b()) {
                return false;
            }
            if (jC.c(this.F).d().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                bB.b(this, xB.b().a(this, 2131625244), 1).show();
                return false;
            }
            jC.a(this.F).a(this.G.getJavaName(), i, obj, this.L.getText().toString());
        } else if (i == 12) {
            if (jC.c(this.F).d().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                bB.b(this, xB.b().a(this, 2131625244), 1).show();
                return false;
            } else if (jC.c(this.F).d().reserved2.trim().length() == 0) {
                bB.b(this, xB.b().a(this, 2131625216), 1).show();
                return false;
            } else {
                jC.a(this.F).a(this.G.getJavaName(), i, obj, this.L.getText().toString());
            }
        } else if (i == 13) {
            if (jC.c(this.F).b().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                bB.b(this, xB.b().a(this, 2131625165), 1).show();
                return false;
            }
            jC.a(this.F).a(this.G.getJavaName(), i, obj);
        } else if (i != 16) {
            jC.a(this.F).a(this.G.getJavaName(), i, obj);
        } else if (this.M.getText().toString().length() == 0 || !this.W.b()) {
            return false;
        } else {
            jC.a(this.F).a(this.G.getJavaName(), i, obj, this.M.getText().toString());
        }
        jC.a(this.F).k();
        return true;
    }

    public final void o() {
        bB.a(this, xB.b().a(this, 2131625125), 1).show();
        mB.a(getApplicationContext(), this.J);
        setResult(-1);
        finish();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        ComponentAddActivity.super.onActivityResult(i, i2, intent);
        if (i == 275 && i2 == -1) {
            this.M.setText(intent.getStringExtra("mime_type"));
        }
    }

    public void onBackPressed() {
        if (this.x) {
            p();
        } else {
            ComponentAddActivity.super.onBackPressed();
        }
    }


    @SuppressLint("ResourceType")
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427495);
        l();
        m();
        if (bundle == null) {
            Intent intent = getIntent();
            this.F = intent.getStringExtra("sc_id");
            this.G = (ProjectFileBean) intent.getParcelableExtra("project_file");
        } else {
            this.F = bundle.getString("sc_id");
            this.G = (ProjectFileBean) bundle.getParcelable("project_file");
        }
        this.z = (TextView) findViewById(2131231925);
        this.A = (TextView) findViewById(2131231954);
        this.B = (TextView) findViewById(2131232055);
        this.I = (TextView) findViewById(2131232285);
        this.R = (TextView) findViewById(2131231949);
        this.S = (TextView) findViewById(2131231948);
        this.J = (EditText) findViewById(2131230990);
        this.L = (EditText) findViewById(2131230995);
        this.K = (EditText) findViewById(2131230994);
        this.M = (EditText) findViewById(2131230993);
        this.X = (LinearLayout) findViewById(2131231358);
        this.D = (ImageView) findViewById(2131231112);
        this.D.setVisibility(8);
        this.E = (ImageView) findViewById(2131231145);
        this.N = findViewById(2131231816);
        this.O = findViewById(2131231818);
        this.P = findViewById(2131231819);
        this.Q = findViewById(2131231817);
        this.Y = (LinearLayout) findViewById(2131231353);
        this.Z = (RelativeLayout) findViewById(2131231338);
        this.H = (LinearLayout) findViewById(2131231357);
        this.J.setPrivateImeOptions("defaultInputmode=english;");
        this.aa = (Button) findViewById(2131230755);
        this.aa.setText(xB.b().a(getApplicationContext(), 2131624970));
        this.ba = (Button) findViewById(2131230980);
        this.ba.setText(xB.b().a(getApplicationContext(), 2131625091));
        this.z.setText(xB.b().a(getApplicationContext(), 2131625127));
        this.t = findViewById(2131230919);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(0);
        flexboxLayoutManager.setFlexWrap(1);
        flexboxLayoutManager.setJustifyContent(2);
        flexboxLayoutManager.setAlignItems(2);
        this.t.setLayoutManager(flexboxLayoutManager);
        this.u = new a();
        this.t.setHasFixedSize(true);
        this.t.setAdapter(this.u);
        this.Z.setVisibility(8);
        this.C = (ImageView) findViewById(2131231151);
        this.T = new ZB(this, findViewById(2131231816), uq.b, uq.a(), jC.a(this.F).a(this.G));
        this.U = new SB(this, findViewById(2131231818), 1, 20);
        this.V = new SB(this, findViewById(2131231819), 0, 100);
        this.W = new SB(this, findViewById(2131231817), 1, 50);
        this.R.setText(xB.b().a(this, 2131625215));
        this.S.setText(xB.b().a(this, 2131625101));
        this.N.setHint(xB.b().a(this, 2131625124));
        this.O.setHint(xB.b().a(this, 2131625116));
        this.P.setHint(xB.b().a(this, 2131625217));
        this.Q.setHint(xB.b().a(this, 2131625117));
        this.w = new HashMap<>();
        this.D.setOnClickListener(new Cs(this));
        this.aa.setOnClickListener(new Ds(this));
        this.ba.setOnClickListener(new Es(this));
        this.E.setOnClickListener(new Fs(this));
    }

    public void onPostCreate(Bundle bundle) {
        ComponentAddActivity.super.onPostCreate(bundle);
        q();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.d.setScreenName(ComponentAddActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.F);
        bundle.putParcelable("project_file", this.G);
        ComponentAddActivity.super.onSaveInstanceState(bundle);
    }


    @SuppressLint("WrongConstant")
    public final void p() {
        if (!this.y) {
            View currentFocus = getCurrentFocus();
            if (currentFocus != null) {
                ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
            this.y = true;
            this.A.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            this.X.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            this.aa.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            this.ba.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            Pair<Integer, Integer> pair = this.w.get(Integer.valueOf(this.u.c));
            this.Y.animate().translationX((float) ((Integer) pair.first).intValue()).translationY((float) ((Integer) pair.second).intValue()).setDuration(300).setListener(new Gs(this)).start();
        }
    }

    public final void q() {
        this.v = new ArrayList<>();
        this.v.add(new ComponentBean(1));
        this.v.add(new ComponentBean(2));
        this.v.add(new ComponentBean(3));
        this.v.add(new ComponentBean(4));
        this.v.add(new ComponentBean(5));
        this.v.add(new ComponentBean(7));
        this.v.add(new ComponentBean(8));
        this.v.add(new ComponentBean(9));
        this.v.add(new ComponentBean(10));
        this.v.add(new ComponentBean(15));
        this.v.add(new ComponentBean(16));
        this.v.add(new ComponentBean(11));
        this.v.add(new ComponentBean(6));
        this.v.add(new ComponentBean(12));
        this.v.add(new ComponentBean(14));
        this.v.add(new ComponentBean(13));
        this.v.add(new ComponentBean(18));
        this.v.add(new ComponentBean(19));
        this.v.add(new ComponentBean(17));
        this.v.add(new ComponentBean(20));
        this.v.add(new ComponentBean(21));
        this.v.add(new ComponentBean(22));
        this.v.add(new ComponentBean(23));
        this.v.add(new ComponentBean(24));
        this.v.add(new ComponentBean(25));
        this.v.add(new ComponentBean(26));
        ManageComponent.a(this.v);
        this.u.c();
    }

    @SuppressLint("WrongConstant")
    public final void r() {
        String componentDocsUrlByTypeName = ComponentBean.getComponentDocsUrlByTypeName(this.v.get(this.u.c).type);
        if (componentDocsUrlByTypeName.equals("")) {
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625092), 0).show();
            return;
        }
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(268435456);
            intent.setData(Uri.parse("googlechrome://navigate?url=" + componentDocsUrlByTypeName));
            intent.addFlags(1);
            intent.addFlags(2);
            intent.addFlags(64);
            startActivity(intent);
        } catch (Exception unused) {
            t();
        }
    }

    @SuppressLint("WrongConstant")
    public final void s() {
        this.C.setVisibility(0);
        this.Z.setVisibility(0);
        this.X.setVisibility(0);
        this.Y.setVisibility(0);
        this.A.setVisibility(0);
        this.D.setVisibility(0);
        this.t.setVisibility(8);
        this.Y.setTranslationX(FlexItem.FLEX_GROW_DEFAULT);
        this.Y.setTranslationY(FlexItem.FLEX_GROW_DEFAULT);
        ComponentBean componentBean = this.v.get(this.u.c);
        this.I.setVisibility(8);
        this.O.setVisibility(8);
        this.R.setVisibility(8);
        this.S.setVisibility(8);
        this.P.setVisibility(8);
        this.H.setVisibility(8);
        int i = componentBean.type;
        if (i == 2) {
            this.O.setVisibility(0);
        } else if (i == 6) {
            this.R.setVisibility(0);
            this.P.setVisibility(0);
        } else if (i != 11) {
            if (i == 14) {
                this.R.setVisibility(0);
                this.P.setVisibility(0);
            } else if (i == 16) {
                this.S.setVisibility(0);
                this.H.setVisibility(0);
            }
        } else if (!GB.b(this, 4)) {
            this.I.setVisibility(0);
            this.I.setText(xB.b().a(this, 2131625630));
        }
        this.C.setImageResource(ComponentBean.getIconResource(componentBean.type));
        this.z.setText(ComponentBean.getComponentName(getApplicationContext(), componentBean.type));
        TextView textView = this.A;
        xB.b();
        getApplicationContext();
        textView.setText(ComponentsHandler.description(componentBean.type));
        this.A.setAlpha(FlexItem.FLEX_GROW_DEFAULT);
        this.X.setAlpha(FlexItem.FLEX_GROW_DEFAULT);
        this.aa.setAlpha(FlexItem.FLEX_GROW_DEFAULT);
        this.ba.setAlpha(FlexItem.FLEX_GROW_DEFAULT);
        this.X.setTranslationY(300.0f);
        this.A.animate().alpha(1.0f).start();
        this.D.animate().alpha(1.0f).start();
        this.X.animate().alpha(1.0f).translationY(FlexItem.FLEX_GROW_DEFAULT).start();
        this.aa.animate().setStartDelay(150).alpha(1.0f).start();
        this.ba.animate().setStartDelay(150).alpha(1.0f).start();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.besome.sketch.editor.component.ComponentAddActivity */
    /* JADX WARN: Multi-variable type inference failed */
    public final void t() {
        aB aBVar = new aB(this);
        aBVar.a(2131165415);
        aBVar.b(xB.b().a(getApplicationContext(), 2131626412));
        aBVar.a(xB.b().a(getApplicationContext(), 2131625629));
        aBVar.b(xB.b().a(getApplicationContext(), 2131625010), new Hs(this, aBVar));
        aBVar.a(xB.b().a(getApplicationContext(), 2131624974), new Is(this, aBVar));
        aBVar.show();
    }

    public final void u() {
        startActivityForResult(new Intent((Context) this, (Class<?>) ShowFilePickerTypesActivity.class), 275);
    }


    public class a extends RecyclerView.a<ComponentAddActivity.a.C0006a> {
        public int c = -1;
        public RecyclerView d;


        public class C0006a extends RecyclerView.v {
            public ImageView t;
            public TextView u;

            @SuppressLint("ResourceType")
            public C0006a(View view) {
                super(view);
                this.t = (ImageView) view.findViewById(2131231090);
                this.u = (TextView) view.findViewById(2131231561);
                view.setOnClickListener(new Ks(this, a.this));
            }
        }

        public a() {
        }

        public long a(int i) {
            return (long) i;
        }

        public void a(RecyclerView recyclerView) {
            ComponentAddActivity.super.a((ha.a) recyclerView);
            this.d = recyclerView;
        }


        public void b(C0006a aVar, int i) {
            String componentName = ComponentBean.getComponentName(ComponentAddActivity.this.getApplicationContext(), ComponentAddActivity.this.v.get(i).type);
            ((RecyclerView.v) aVar).b.setAlpha(1.0f);
            ((RecyclerView.v) aVar).b.setTranslationX(FlexItem.FLEX_GROW_DEFAULT);
            ((RecyclerView.v) aVar).b.setTranslationY(FlexItem.FLEX_GROW_DEFAULT);
            aVar.u.setAlpha(1.0f);
            aVar.u.setText(componentName);
            aVar.t.setImageResource(ComponentBean.getIconResource(ComponentAddActivity.this.v.get(i).type));
            ComponentAddActivity componentAddActivity = ComponentAddActivity.this;
            if (!componentAddActivity.x) {
                return;
            }
            if (i == this.c) {
                Pair<Integer, Integer> pair = componentAddActivity.w.get(Integer.valueOf(i));
                aVar.u.animate().setDuration(100).alpha(FlexItem.FLEX_GROW_DEFAULT).start();
                long j = (long) 300;
                ((RecyclerView.v) aVar).b.animate().setStartDelay(j).translationX((float) (-((Integer) pair.first).intValue())).translationY((float) (-((Integer) pair.second).intValue())).setDuration(j).setListener(new Js(this, componentName)).start();
                return;
            }
            ((RecyclerView.v) aVar).b.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
        }
        public C0006a b(ViewGroup viewGroup, int i) {
            View a = wB.a(viewGroup.getContext(), 2131427389);
            int a2 = (int) wB.a(viewGroup.getContext(), 76.0f);
            a.setLayoutParams(new FlexboxLayoutManager.LayoutParams(a2, a2));
            return new C0006a(a);
        }

        public int a() {
            return ComponentAddActivity.this.v.size();
        }
    }
}
