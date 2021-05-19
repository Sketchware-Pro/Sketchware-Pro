package com.besome.sketch.editor.manage.image;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.google.android.gms.analytics.HitBuilders;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import a.a.a.HB;
import a.a.a.MA;
import a.a.a.Op;
import a.a.a.PB;
import a.a.a.bB;
import a.a.a.iB;
import a.a.a.mB;
import a.a.a.uq;
import a.a.a.wq;
import a.a.a.xB;

public class AddImageCollectionActivity extends BaseDialogActivity implements View.OnClickListener {
    public String A = null;
    public int B = 0;
    public int C = 0;
    public TextView D;
    public TextView E;
    public ImageView F;
    public ImageView G;
    public ImageView H;
    public ImageView I;
    public int J = 1;
    public int K = 1;
    public PB L;
    public EditText M;
    public EasyDeleteEditText N;
    public boolean O = false;
    public ProjectResourceBean P = null;
    public int Q;
    public int R;
    public TextView S;
    public CheckBox T;
    public boolean t = false;
    public String u;
    public LinearLayout v = null;
    public LinearLayout w = null;
    public TextView x = null;
    public ArrayList<ProjectResourceBean> y;
    public boolean z = false;

    public final void n() {
        String str = this.A;
        if (str != null && str.length() > 0) {
            int i = this.B;
            if (i == 90 || i == 270) {
                this.J *= -1;
            } else {
                this.K *= -1;
            }
            r();
        }
    }

    public final void o() {
        String str = this.A;
        if (str != null && str.length() > 0) {
            int i = this.B;
            if (i == 90 || i == 270) {
                this.K *= -1;
            } else {
                this.J *= -1;
            }
            r();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onActivityResult(int i, int i2, Intent intent) {
        ImageView imageView;
        super.onActivityResult(i, i2, intent);
        if (i == 215 && (imageView = this.I) != null) {
            imageView.setEnabled(true);
            if (i2 == -1) {
                this.B = 0;
                this.J = 1;
                this.K = 1;
                this.z = true;
                a(intent.getData());
                PB pb = this.L;
                if (pb != null) {
                    pb.a(1);
                }
            }
        }
    }

    public void onClick(View view) {
        if (!mB.a()) {
            switch (view.getId()) {
                case 2131230869:
                    setResult(0);
                    finish();
                    return;
                case 2131230909:
                    finish();
                    return;
                case 2131230914:
                    s();
                    return;
                case 2131231150:
                    n();
                    return;
                case 2131231176:
                    g(this.B + 90);
                    return;
                case 2131231181:
                    this.I.setEnabled(false);
                    if (!this.O) {
                        q();
                        return;
                    }
                    return;
                case 2131231203:
                    o();
                    return;
                default:
                    return;
            }
        }
    }

    @Override
    // androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseDialogActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        e(xB.b().a(this, 2131625272));
        d(xB.b().a(getApplicationContext(), 2131625031));
        setContentView(2131427526);
        Intent intent = getIntent();
        this.y = intent.getParcelableArrayListExtra("images");
        this.u = intent.getStringExtra("sc_id");
        this.P = (ProjectResourceBean) intent.getParcelableExtra("edit_target");
        if (this.P != null) {
            this.O = true;
        }
        this.v = (LinearLayout) findViewById(2131231354);
        this.w = (LinearLayout) findViewById(2131231355);
        this.T = (CheckBox) findViewById(2131230887);
        this.T.setVisibility(View.GONE);
        this.S = (TextView) findViewById(2131231944);
        this.x = (TextView) findViewById(2131232004);
        this.D = (TextView) findViewById(2131231913);
        this.D.setVisibility(View.GONE);
        this.E = (TextView) findViewById(2131231865);
        this.I = (ImageView) findViewById(2131231181);
        this.F = (ImageView) findViewById(2131231176);
        this.G = (ImageView) findViewById(2131231203);
        this.H = (ImageView) findViewById(2131231150);
        this.N = (EasyDeleteEditText) findViewById(2131230990);
        this.M = this.N.getEditText();
        this.M.setPrivateImeOptions("defaultInputmode=english;");
        this.N.setHint(xB.b().a(this, 2131625268));
        this.L = new PB(this, this.N.getTextInputLayout(), uq.b, p());
        this.L.a(1);
        this.E.setText(xB.b().a(this, 2131625272));
        this.I.setOnClickListener(this);
        this.F.setOnClickListener(this);
        this.G.setOnClickListener(this);
        this.H.setOnClickListener(this);
        this.r.setOnClickListener(this);
        this.s.setOnClickListener(this);
        this.z = false;
        this.B = 0;
        this.J = 1;
        this.K = 1;
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        if (this.O) {
            this.P.isEdited = true;
            e(xB.b().a(this, 2131625275));
            this.L = new PB(this, this.N.getTextInputLayout(), uq.b, p(), this.P.resName);
            this.L.a(1);
            this.M.setText(this.P.resName);
            this.T.setVisibility(View.GONE);
            this.D.setVisibility(View.GONE);
            this.E.setVisibility(View.GONE);
            f(a(this.P));
            this.w.setVisibility(View.GONE);
        }
    }

    @Override
    // androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onResume() {
        super.onResume();
        this.d.setScreenName(AddImageCollectionActivity.class.getSimpleName());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public final ArrayList<String> p() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.y.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    public final void q() {
        try {
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, xB.b().a(this, 2131624976)), 215);
        } catch (ActivityNotFoundException unused) {
            bB.b(this, xB.b().a(this, 2131624907), 0).show();
        }
    }

    public final void r() {
        this.I.setImageBitmap(iB.a(iB.a(iB.a(this.A, 1024, 1024), this.C), this.B, this.K, this.J));
    }

    public final void s() {
        if (a(this.L)) {
            new Handler().postDelayed(() -> {
                k();
                new AddImageCollectionActivity.a(getApplicationContext()).execute();
            }, 500);
        }
    }

    public final void t() {
        TextView textView = this.S;
        if (textView != null) {
            textView.setVisibility(View.INVISIBLE);
        }
        LinearLayout linearLayout = this.v;
        if (linearLayout != null && this.w != null && this.x != null) {
            linearLayout.setVisibility(View.GONE);
            this.w.setVisibility(View.VISIBLE);
            this.x.setVisibility(View.GONE);
        }
    }

    public boolean a(PB pb) {
        if (!pb.b()) {
            return false;
        }
        if (this.z || this.A != null) {
            return true;
        }
        this.S.startAnimation(AnimationUtils.loadAnimation(this, 2130771980));
        return false;
    }

    public final void f(String str) {
        this.A = str;
        this.I.setImageBitmap(iB.a(str, 1024, 1024));
        this.Q = str.lastIndexOf("/");
        this.R = str.lastIndexOf(".");
        if (str.endsWith(".9.png")) {
            this.R = str.lastIndexOf(".9.png");
        }
        EditText editText = this.M;
        if (editText != null && (editText.getText() == null || this.M.getText().length() <= 0)) {
            this.M.setText(str.substring(this.Q + 1, this.R));
        }
        try {
            this.C = iB.a(str);
            r();
        } catch (Exception e) {
            e.printStackTrace();
        }
        t();
    }

    public final void g(int i) {
        String str = this.A;
        if (str != null && str.length() > 0) {
            this.B = i;
            if (this.B == 360) {
                this.B = 0;
            }
            r();
        }
    }

    public final void a(Uri uri) {
        String a2;
        if (uri != null && (a2 = HB.a(this, uri)) != null) {
            f(a2);
        }
    }

    public final String a(ProjectResourceBean projectResourceBean) {
        return wq.a() + File.separator + "image" + File.separator + "data" + File.separator + projectResourceBean.resFullName;
    }

    class a extends MA {
        public a(Context context) {
            super(context);
            AddImageCollectionActivity.this.a(this);
        }

        @Override // a.a.a.MA
        public void a() {
            if (AddImageCollectionActivity.this.O) {
                bB.a(AddImageCollectionActivity.this.getApplicationContext(), xB.b().a(AddImageCollectionActivity.this.getApplicationContext(), 2131625279), 0).show();
            } else {
                bB.a(AddImageCollectionActivity.this.getApplicationContext(), xB.b().a(AddImageCollectionActivity.this.getApplicationContext(), 2131625276), 0).show();
            }
            AddImageCollectionActivity.this.h();
            AddImageCollectionActivity.this.finish();
        }

        @Override // a.a.a.MA
        public void b() {
            try {
                publishProgress("Now processing..");
                if (!AddImageCollectionActivity.this.O) {
                    ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, AddImageCollectionActivity.this.M.getText().toString().trim(), AddImageCollectionActivity.this.A);
                    projectResourceBean.savedPos = 1;
                    projectResourceBean.isNew = true;
                    projectResourceBean.rotate = AddImageCollectionActivity.this.B;
                    projectResourceBean.flipVertical = AddImageCollectionActivity.this.J;
                    projectResourceBean.flipHorizontal = AddImageCollectionActivity.this.K;
                    Op.g().a(AddImageCollectionActivity.this.u, projectResourceBean);
                    return;
                }
                Op.g().a(AddImageCollectionActivity.this.P, AddImageCollectionActivity.this.M.getText().toString(), true);
            } catch (Exception e) {
                throw e;
            }
        }

        @Override // a.a.a.MA
        public void a(String str) {
            AddImageCollectionActivity.this.h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}