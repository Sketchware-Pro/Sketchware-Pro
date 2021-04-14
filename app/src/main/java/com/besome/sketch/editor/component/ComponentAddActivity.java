package com.besome.sketch.editor.component;

import android.animation.Animator;
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

import a.a.a.GB;
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
    public ComponentAddActivity.a u;
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ComponentAddActivity.super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 275 && resultCode == -1) {
            this.M.setText(data.getStringExtra("mime_type"));
        }
    }

    public void onBackPressed() {
        if (this.x) {
            p();
        } else {
            ComponentAddActivity.super.onBackPressed();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(2131427495);
        l();
        m();
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            this.F = intent.getStringExtra("sc_id");
            this.G = intent.getParcelableExtra("project_file");
        } else {
            this.F = savedInstanceState.getString("sc_id");
            this.G = savedInstanceState.getParcelable("project_file");
        }
        this.z = findViewById(2131231925);
        this.A = findViewById(2131231954);
        this.B = findViewById(2131232055);
        this.I = findViewById(2131232285);
        this.R = findViewById(2131231949);
        this.S = findViewById(2131231948);
        this.J = findViewById(2131230990);
        this.L = findViewById(2131230995);
        this.K = findViewById(2131230994);
        this.M = findViewById(2131230993);
        this.X = findViewById(2131231358);
        this.D = findViewById(2131231112);
        this.D.setVisibility(View.GONE);
        this.E = findViewById(2131231145);
        this.N = findViewById(2131231816);
        this.O = findViewById(2131231818);
        this.P = findViewById(2131231819);
        this.Q = findViewById(2131231817);
        this.Y = findViewById(2131231353);
        this.Z = findViewById(2131231338);
        this.H = findViewById(2131231357);
        this.J.setPrivateImeOptions("defaultInputmode=english;");
        this.aa = findViewById(2131230755);
        this.aa.setText(xB.b().a(getApplicationContext(), 2131624970));
        this.ba = findViewById(2131230980);
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
        this.Z.setVisibility(View.GONE);
        this.C = findViewById(2131231151);
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
        this.D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    onBackPressed();
                }
            }
        });
        this.aa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a() && n()) {
                    o();
                }
            }
        });
        this.ba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    r();
                }
            }
        });
        this.E.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u();
            }
        });
    }

    public void onPostCreate(Bundle bundle) {
        ComponentAddActivity.super.onPostCreate(bundle);
        q();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.d.setScreenName(ComponentAddActivity.class.getSimpleName());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putString("sc_id", this.F);
        bundle.putParcelable("project_file", this.G);
        ComponentAddActivity.super.onSaveInstanceState(bundle);
    }


    public final void p() {
        if (!this.y) {
            View currentFocus = getCurrentFocus();
            if (currentFocus != null) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
            this.y = true;
            this.A.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            this.X.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            this.aa.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            this.ba.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            Pair<Integer, Integer> pair = this.w.get(this.u.c);
            this.Y.animate().translationX((float) pair.first).translationY((float) pair.second).setDuration(300).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    y = false;
                    x = false;
                    z.setVisibility(View.GONE);
                    A.setVisibility(View.GONE);
                    D.setVisibility(View.GONE);
                    Y.setVisibility(View.GONE);
                    t.setVisibility(View.VISIBLE);
                    z.setText(xB.b().a(getApplicationContext(), 2131625127));
                    u.c();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            }).start();
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

    public final void r() {
        String componentDocsUrlByTypeName = ComponentBean.getComponentDocsUrlByTypeName(this.v.get(this.u.c).type);
        if (componentDocsUrlByTypeName.equals("")) {
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), 2131625092), 0).show();
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse(componentDocsUrlByTypeName));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            startActivity(intent);
        } catch (Exception unused) {
            t();
        }
    }

    public final void s() {
        this.C.setVisibility(View.VISIBLE);
        this.Z.setVisibility(View.VISIBLE);
        this.X.setVisibility(View.VISIBLE);
        this.Y.setVisibility(View.VISIBLE);
        this.A.setVisibility(View.VISIBLE);
        this.D.setVisibility(View.VISIBLE);
        this.t.setVisibility(View.GONE);
        this.Y.setTranslationX(FlexItem.FLEX_GROW_DEFAULT);
        this.Y.setTranslationY(FlexItem.FLEX_GROW_DEFAULT);
        ComponentBean componentBean = this.v.get(this.u.c);
        this.I.setVisibility(View.GONE);
        this.O.setVisibility(View.GONE);
        this.R.setVisibility(View.GONE);
        this.S.setVisibility(View.GONE);
        this.P.setVisibility(View.GONE);
        this.H.setVisibility(View.GONE);
        int i = componentBean.type;
        if (i == 2) {
            this.O.setVisibility(View.VISIBLE);
        } else if (i == 6) {
            this.R.setVisibility(View.VISIBLE);
            this.P.setVisibility(View.VISIBLE);
        } else if (i != 11) {
            if (i == 14) {
                this.R.setVisibility(View.VISIBLE);
                this.P.setVisibility(View.VISIBLE);
            } else if (i == 16) {
                this.S.setVisibility(View.VISIBLE);
                this.H.setVisibility(View.VISIBLE);
            }
        } else if (!GB.b(this, 4)) {
            this.I.setVisibility(View.VISIBLE);
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

    public final void t() {
        aB aBVar = new aB(this);
        aBVar.a(2131165415);
        aBVar.b(xB.b().a(getApplicationContext(), 2131626412));
        aBVar.a(xB.b().a(getApplicationContext(), 2131625629));
        aBVar.b(xB.b().a(getApplicationContext(), 2131625010), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                    startActivity(intent);
                    aBVar.dismiss();
                }
            }
        });
        aBVar.a(xB.b().a(getApplicationContext(), 2131624974), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aBVar.dismiss();
            }
        });
        aBVar.show();
    }

    public final void u() {
        startActivityForResult(new Intent(this, ShowFilePickerTypesActivity.class), 275);
    }


    public class a extends RecyclerView.a<ComponentAddActivity.a.ViewHolder> {
        public int c = -1;
        public RecyclerView d;


        public a() {
        }

        public long a(int i) {
            return i;
        }

        public void a(RecyclerView recyclerView) {
            ComponentAddActivity.super.a((ha.a) recyclerView);
            this.d = recyclerView;
        }

        public void b(ViewHolder viewHolderVar, int i) {
            String componentName = ComponentBean.getComponentName(ComponentAddActivity.this.getApplicationContext(), ComponentAddActivity.this.v.get(i).type);
            viewHolderVar.b.setAlpha(1.0f);
            viewHolderVar.b.setTranslationX(FlexItem.FLEX_GROW_DEFAULT);
            viewHolderVar.b.setTranslationY(FlexItem.FLEX_GROW_DEFAULT);
            viewHolderVar.u.setAlpha(1.0f);
            viewHolderVar.u.setText(componentName);
            viewHolderVar.t.setImageResource(ComponentBean.getIconResource(ComponentAddActivity.this.v.get(i).type));
            ComponentAddActivity componentAddActivity = ComponentAddActivity.this;
            if (!componentAddActivity.x) {
                return;
            }
            if (i == this.c) {
                Pair<Integer, Integer> pair = componentAddActivity.w.get(i);
                viewHolderVar.u.animate().setDuration(100).alpha(FlexItem.FLEX_GROW_DEFAULT).start();
                long j = 300;
                viewHolderVar.b.animate().setStartDelay(j).translationX((float) (-pair.first)).translationY((float) (-pair.second)).setDuration(j).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        B.setText(componentName);
                        s();
                        y = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                }).start();
                return;
            }
            viewHolderVar.b.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
        }

        public ViewHolder b(ViewGroup viewGroup, int i) {
            View a = wB.a(viewGroup.getContext(), 2131427389);
            int a2 = (int) wB.a(viewGroup.getContext(), 76.0f);
            a.setLayoutParams(new FlexboxLayoutManager.LayoutParams(a2, a2));
            return new ViewHolder(a);
        }

        public int a() {
            return ComponentAddActivity.this.v.size();
        }

        public class ViewHolder extends RecyclerView.v {
            public ImageView t;
            public TextView u;

            public ViewHolder(View view) {
                super(view);
                this.t = view.findViewById(2131231090);
                this.u = view.findViewById(2131231561);
                //a.this
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!y) {
                            y = true;
                            ComponentAddActivity.a.this.c = j();
                            x = true;
                            int[] iArr = new int[2];
                            view.getLocationInWindow(iArr);
                            int[] iArr2 = new int[2];
                            ComponentAddActivity.a.this.d.getLocationInWindow(iArr2);
                            int i = iArr[0] - iArr2[0];
                            w.put(ComponentAddActivity.a.this.c, new Pair<>(i, (int) (((float) (iArr[1] - iArr2[1])) - wB.a(getApplicationContext(), 16.0f))));
                            ComponentAddActivity.a.this.c();
                        }
                    }
                });
            }
        }
    }
}
