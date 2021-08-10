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
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexItem;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.Resources;

import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.GB;
import a.a.a.SB;
import a.a.a.ZB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.jC;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.wB;
import a.a.a.xB;
import mod.agus.jcoderz.component.ManageComponent;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.components.ComponentsHandler;

public class ComponentAddActivity extends BaseDialogActivity {

    public TextView A;
    public TextView B;
    public ImageView C;
    public ImageView D;
    public ImageView E;
    /**
     * The sc_id of the currently open project
     */
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
        int i = v.get(u.c).type;
        String obj = J.getText().toString();
        if (!T.b()) {
            return false;
        }
        if (i == 2) {
            if (!U.b()) {
                return false;
            }
            jC.a(F).a(G.getJavaName(), i, obj, K.getText().toString());
        } else if (i == 6 || i == 14) {
            if (!V.b()) {
                return false;
            }
            if (jC.c(F).d().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                bB.b(this, xB.b().a(this, Resources.string.design_library_guide_setup_first), 1).show();
                return false;
            }
            jC.a(F).a(G.getJavaName(), i, obj, L.getText().toString());
        } else if (i == 12) {
            if (jC.c(F).d().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                bB.b(this, xB.b().a(this, Resources.string.design_library_guide_setup_first), 1).show();
                return false;
            } else if (jC.c(F).d().reserved2.trim().length() == 0) {
                bB.b(this, xB.b().a(this, Resources.string.design_library_firebase_guide_setup_first), 1).show();
                return false;
            } else {
                jC.a(F).a(G.getJavaName(), i, obj, L.getText().toString());
            }
        } else if (i == 13) {
            if (jC.c(F).b().useYn.equals(ProjectLibraryBean.LIB_USE_N)) {
                bB.b(this, xB.b().a(this, Resources.string.design_library_admob_component_setup_first), 1).show();
                return false;
            }
            jC.a(F).a(G.getJavaName(), i, obj);
        } else if (i != 16) {
            jC.a(F).a(G.getJavaName(), i, obj);
        } else if (M.getText().toString().length() == 0 || !W.b()) {
            return false;
        } else {
            jC.a(F).a(G.getJavaName(), i, obj, M.getText().toString());
        }
        jC.a(F).k();
        return true;
    }

    public final void o() {
        bB.a(this, xB.b().a(this, Resources.string.component_message_component_block_added), 1).show();
        mB.a(getApplicationContext(), J);
        setResult(-1);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 275 && resultCode == -1) {
            M.setText(data.getStringExtra("mime_type"));
        }
    }

    @Override
    public void onBackPressed() {
        if (x) {
            p();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.logic_popup_add_component_temp);
        l();
        m();
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            F = intent.getStringExtra("sc_id");
            G = intent.getParcelableExtra("project_file");
        } else {
            F = savedInstanceState.getString("sc_id");
            G = savedInstanceState.getParcelable("project_file");
        }
        z = findViewById(Resources.id.tv_component_title);
        A = findViewById(Resources.id.tv_description);
        B = findViewById(Resources.id.tv_name);
        I = findViewById(Resources.id.tv_warning);
        R = findViewById(Resources.id.tv_desc_firebase_path);
        S = findViewById(Resources.id.tv_desc_file_picker);
        J = findViewById(Resources.id.ed_input);
        L = findViewById(Resources.id.ed_input_firebase_path);
        K = findViewById(Resources.id.ed_input_filename);
        M = findViewById(Resources.id.ed_input_file_picker);
        X = findViewById(Resources.id.layout_inputs);
        D = findViewById(Resources.id.img_back);
        D.setVisibility(View.GONE);
        E = findViewById(Resources.id.img_file_picker);
        N = findViewById(Resources.id.ti_input);
        O = findViewById(Resources.id.ti_input_filename);
        P = findViewById(Resources.id.ti_input_firebase_path);
        Q = findViewById(Resources.id.ti_input_file_picker);
        Y = findViewById(Resources.id.layout_img_icon);
        Z = findViewById(Resources.id.layout_description);
        H = findViewById(Resources.id.layout_input_file_picker);
        J.setPrivateImeOptions("defaultInputmode=english;");
        aa = findViewById(Resources.id.add_button);
        aa.setText(xB.b().a(getApplicationContext(), Resources.string.common_word_add));
        ba = findViewById(Resources.id.docs_button);
        ba.setText(xB.b().a(getApplicationContext(), Resources.string.component_add_docs_button_title_go_to_docs));
        z.setText(xB.b().a(getApplicationContext(), Resources.string.component_title_add_component));
        t = findViewById(Resources.id.components_list);
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        flexboxLayoutManager.setJustifyContent(JustifyContent.CENTER);
        flexboxLayoutManager.setAlignItems(AlignItems.CENTER);
        t.setLayoutManager(flexboxLayoutManager);
        u = new a();
        t.setHasFixedSize(true);
        t.setAdapter(u);
        Z.setVisibility(View.GONE);
        C = findViewById(Resources.id.img_icon);
        T = new ZB(
                this,
                N,
                uq.b,
                uq.a(),
                jC.a(F).a(G)
        );
        U = new SB(
                this,
                O,
                1,
                20
        );
        V = new SB(
                this,
                P,
                0,
                100
        );
        W = new SB(
                this,
                Q,
                1,
                50
        );
        R.setText(xB.b().a(this, Resources.string.design_library_firebase_guide_path_example));
        S.setText(xB.b().a(this, Resources.string.component_description_file_picker_guide_mime_type_example));
        N.setHint(xB.b().a(this, Resources.string.component_hint_enter_name));
        O.setHint(xB.b().a(this, Resources.string.component_file_hint_enter_file_name));
        P.setHint(xB.b().a(this, Resources.string.design_library_firebase_hint_enter_data_location));
        Q.setHint(xB.b().a(this, Resources.string.component_file_picker_hint_mime_type));
        w = new HashMap<>();
        D.setOnClickListener(v -> {
            if (!mB.a()) {
                onBackPressed();
            }
        });
        aa.setOnClickListener(v -> {
            if (!mB.a() && n()) {
                o();
            }
        });
        ba.setOnClickListener(v -> {
            if (!mB.a()) {
                r();
            }
        });
        E.setOnClickListener(v -> u());
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        q();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("sc_id", F);
        savedInstanceState.putParcelable("project_file", G);
        super.onSaveInstanceState(savedInstanceState);
    }

    public final void p() {
        if (!y) {
            View currentFocus = getCurrentFocus();
            if (currentFocus != null) {
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
            y = true;
            A.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            X.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            aa.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            ba.animate().alpha(FlexItem.FLEX_GROW_DEFAULT).start();
            Pair<Integer, Integer> pair = w.get(u.c);
            Y.animate()
                    .translationX((float) pair.first)
                    .translationY((float) pair.second)
                    .setDuration(300)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            y = false;
                            x = false;
                            Z.setVisibility(View.GONE);
                            A.setVisibility(View.GONE);
                            D.setVisibility(View.GONE);
                            Y.setVisibility(View.GONE);
                            t.setVisibility(View.VISIBLE);
                            z.setText(xB.b().a(getApplicationContext(), Resources.string.component_title_add_component));
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
        v = new ArrayList<>();
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_INTENT));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_SHAREDPREF));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_CALENDAR));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_VIBRATOR));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_TIMERTASK));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_DIALOG));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_MEDIAPLAYER));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_SOUNDPOOL));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_OBJECTANIMATOR));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_CAMERA));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FILE_PICKER));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_GYROSCOPE));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_INTERSTITIAL_AD));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_TEXT_TO_SPEECH));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_SPEECH_TO_TEXT));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_REQUEST_NETWORK));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_BLUETOOTH_CONNECT));
        v.add(new ComponentBean(ComponentBean.COMPONENT_TYPE_LOCATION_MANAGER));
        v.add(new ComponentBean(22));
        v.add(new ComponentBean(23));
        v.add(new ComponentBean(24));
        v.add(new ComponentBean(25));
        v.add(new ComponentBean(26));
        ManageComponent.a(v);
        u.c();
    }

    public final void r() {
        String componentDocsUrlByTypeName = ComponentBean.getComponentDocsUrlByTypeName(v.get(u.c).type);
        if (componentDocsUrlByTypeName.equals("")) {
            bB.a(getApplicationContext(), xB.b().a(getApplicationContext(), Resources.string.component_add_message_docs_updated_soon), 0).show();
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
        C.setVisibility(View.VISIBLE);
        Z.setVisibility(View.VISIBLE);
        X.setVisibility(View.VISIBLE);
        Y.setVisibility(View.VISIBLE);
        A.setVisibility(View.VISIBLE);
        D.setVisibility(View.VISIBLE);
        t.setVisibility(View.GONE);
        Y.setTranslationX(FlexItem.FLEX_GROW_DEFAULT);
        Y.setTranslationY(FlexItem.FLEX_GROW_DEFAULT);
        ComponentBean componentBean = v.get(u.c);
        I.setVisibility(View.GONE);
        O.setVisibility(View.GONE);
        R.setVisibility(View.GONE);
        S.setVisibility(View.GONE);
        P.setVisibility(View.GONE);
        H.setVisibility(View.GONE);
        int type = componentBean.type;
        if (type == 2) {
            O.setVisibility(View.VISIBLE);
        } else if (type == 6) {
            R.setVisibility(View.VISIBLE);
            P.setVisibility(View.VISIBLE);
        } else if (type != 11) {
            if (type == 14) {
                R.setVisibility(View.VISIBLE);
                P.setVisibility(View.VISIBLE);
            } else if (type == 16) {
                S.setVisibility(View.VISIBLE);
                H.setVisibility(View.VISIBLE);
            }
        } else if (!GB.b(this, 4)) {
            I.setVisibility(View.VISIBLE);
            I.setText(xB.b().a(this, Resources.string.message_device_not_support));
        }
        C.setImageResource(ComponentBean.getIconResource(componentBean.type));
        z.setText(ComponentBean.getComponentName(getApplicationContext(), componentBean.type));
        xB.b();
        getApplicationContext();
        A.setText(ComponentsHandler.description(componentBean.type));
        A.setAlpha(FlexItem.FLEX_GROW_DEFAULT);
        X.setAlpha(FlexItem.FLEX_GROW_DEFAULT);
        aa.setAlpha(FlexItem.FLEX_GROW_DEFAULT);
        ba.setAlpha(FlexItem.FLEX_GROW_DEFAULT);
        X.setTranslationY(300.0f);
        A.animate().alpha(1.0f).start();
        D.animate().alpha(1.0f).start();
        X.animate().alpha(1.0f).translationY(FlexItem.FLEX_GROW_DEFAULT).start();
        aa.animate().setStartDelay(150).alpha(1.0f).start();
        ba.animate().setStartDelay(150).alpha(1.0f).start();
    }

    /**
     * Show dialog about installing Google Chrome.
     */
    public final void t() {
        aB dialog = new aB(this);
        dialog.a(Resources.drawable.chrome_96);
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.title_compatible_chrome_browser));
        dialog.a(xB.b().a(getApplicationContext(), Resources.string.message_compatible_chrome_brower));
        dialog.b(xB.b().a(getApplicationContext(), Resources.string.common_word_ok), v -> {
            if (!mB.a()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.android.chrome"));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.a(xB.b().a(getApplicationContext(), Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
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
            super.a(recyclerView);
            d = recyclerView;
        }

        public void b(ViewHolder viewHolderVar, int i) {
            String componentName = ComponentBean.getComponentName(getApplicationContext(), v.get(i).type);
            viewHolderVar.b.setAlpha(1.0f);
            viewHolderVar.b.setTranslationX(FlexItem.FLEX_GROW_DEFAULT);
            viewHolderVar.b.setTranslationY(FlexItem.FLEX_GROW_DEFAULT);
            viewHolderVar.u.setAlpha(1.0f);
            viewHolderVar.u.setText(componentName);
            viewHolderVar.t.setImageResource(ComponentBean.getIconResource(v.get(i).type));
            if (!x) {
                return;
            }
            if (i == this.c) {
                Pair<Integer, Integer> pair = w.get(i);
                viewHolderVar.u.animate()
                        .setDuration(100)
                        .alpha(FlexItem.FLEX_GROW_DEFAULT)
                        .start();
                viewHolderVar.b.animate()
                        .setStartDelay(300)
                        .translationX((float) (-pair.first))
                        .translationY((float) (-pair.second))
                        .setDuration(300)
                        .setListener(new Animator.AnimatorListener() {
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
            viewHolderVar.b.animate()
                    .alpha(FlexItem.FLEX_GROW_DEFAULT)
                    .start();
        }

        public ViewHolder b(ViewGroup viewGroup, int i) {
            View a = wB.a(viewGroup.getContext(), Resources.layout.component_add_item);
            int lengthAndWidth = (int) wB.a(viewGroup.getContext(), 76.0f);
            a.setLayoutParams(new FlexboxLayoutManager.LayoutParams(lengthAndWidth, lengthAndWidth));
            return new ViewHolder(a);
        }

        public int a() {
            return v.size();
        }

        public class ViewHolder extends RecyclerView.v {

            public ImageView t;
            public TextView u;

            public ViewHolder(View view) {
                super(view);
                t = view.findViewById(Resources.id.icon);
                u = view.findViewById(Resources.id.name);
                view.setOnClickListener(v -> {
                    if (!y) {
                        y = true;
                        ComponentAddActivity.a.this.c = j();
                        x = true;
                        int[] viewLocationInWindow = new int[2];
                        view.getLocationInWindow(viewLocationInWindow);
                        int[] dLocationInWindow = new int[2];
                        ComponentAddActivity.a.this.d.getLocationInWindow(dLocationInWindow);
                        int i = viewLocationInWindow[0] - dLocationInWindow[0];
                        w.put(ComponentAddActivity.a.this.c, new Pair<>(i, (int) (((float) (viewLocationInWindow[1] - dLocationInWindow[1])) - wB.a(getApplicationContext(), 16.0f))));
                        ComponentAddActivity.a.this.c();
                    }
                });
            }
        }
    }
}
