package com.besome.sketch.editor.manage.image;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
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
import a.a.a.oB;
import a.a.a.uq;
import a.a.a.xB;

public class AddImageActivity extends BaseDialogActivity implements View.OnClickListener {
    public ArrayList<ProjectResourceBean> A;
    public boolean B = false;
    public String C = null;
    public int D = 0;
    public int E = 0;
    public TextView F;
    public TextView G;
    public ImageView H;
    public ImageView I;
    public ImageView J;
    public ImageView K;
    public int L = 1;
    public int M = 1;
    public ArrayList<Uri> N;
    public PB O;
    public EditText P;
    public EasyDeleteEditText Q;
    public String R = "";
    public boolean S = false;
    public ProjectResourceBean T = null;
    public int U;
    public int V;
    public oB W;
    public TextView X;
    public CheckBox Y;
    public boolean t = false;
    public boolean u = false;
    public String v;
    public LinearLayout w = null;
    public LinearLayout x = null;
    public TextView y = null;
    public ArrayList<ProjectResourceBean> z;

    public final void o() {
        String str = this.C;
        if (str != null && str.length() > 0) {
            int i = this.D;
            if (i == 90 || i == 270) {
                this.M *= -1;
            } else {
                this.L *= -1;
            }
            q();
        }
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onActivityResult(int i, int i2, Intent intent) {
        ImageView imageView;
        super.onActivityResult(i, i2, intent);
        if (i == 215 && (imageView = this.K) != null) {
            imageView.setEnabled(true);
            if (i2 == -1) {
                this.G.setVisibility(View.GONE);
                this.D = 0;
                this.L = 1;
                this.M = 1;
                if (intent.getClipData() == null) {
                    this.B = true;
                    this.t = false;
                    a(intent.getData());
                    PB pb = this.O;
                    if (pb != null) {
                        pb.a(1);
                        return;
                    }
                    return;
                }
                ClipData clipData = intent.getClipData();
                if (clipData.getItemCount() == 1) {
                    this.B = true;
                    this.t = false;
                    a(clipData.getItemAt(0).getUri());
                    PB pb2 = this.O;
                    if (pb2 != null) {
                        pb2.a(1);
                        return;
                    }
                    return;
                }
                a(clipData);
                this.t = true;
                PB pb3 = this.O;
                if (pb3 != null) {
                    pb3.a(clipData.getItemCount());
                }
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case 2131230869:
                setResult(0);
                finish();
                return;
            case 2131230909:
                finish();
                return;
            case 2131230914:
                r();
                return;
            case 2131231150:
                n();
                return;
            case 2131231176:
                g(this.D + 90);
                return;
            case 2131231181:
                this.K.setEnabled(false);
                if (this.S) {
                    b(false);
                    return;
                } else {
                    b(true);
                    return;
                }
            case 2131231203:
                o();
                return;
            default:
                return;
        }
    }

    @Override
    // androidx.core.app.ComponentActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseDialogActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        e(xB.b().a(this, 2131625272));
        d(xB.b().a(getApplicationContext(), 2131625031));
        setContentView(0x7f0b00c6);
        Intent intent = getIntent();
        this.A = intent.getParcelableArrayListExtra("images");
        this.v = intent.getStringExtra("sc_id");
        this.R = intent.getStringExtra("dir_path");
        this.T = (ProjectResourceBean) intent.getParcelableExtra("edit_target");
        if (this.T != null) {
            this.S = true;
        }
        this.w = (LinearLayout) findViewById(2131231354);
        this.x = (LinearLayout) findViewById(2131231355);
        this.Y = (CheckBox) findViewById(2131230887);
        this.X = (TextView) findViewById(2131231944);
        this.y = (TextView) findViewById(2131232004);
        this.F = (TextView) findViewById(2131231913);
        this.G = (TextView) findViewById(2131231865);
        this.K = (ImageView) findViewById(2131231181);
        this.H = (ImageView) findViewById(2131231176);
        this.I = (ImageView) findViewById(2131231203);
        this.J = (ImageView) findViewById(2131231150);
        this.Q = (EasyDeleteEditText) findViewById(2131230990);
        this.P = this.Q.getEditText();
        this.P.setPrivateImeOptions("defaultInputmode=english;");
        this.Q.setHint(xB.b().a(this, 2131625268));
        this.O = new PB(this, this.Q.getTextInputLayout(), uq.b, p());
        this.O.a(1);
        this.F.setText(xB.b().a(getApplicationContext(), 2131625289));
        this.G.setText(xB.b().a(getApplicationContext(), 2131625272));
        this.K.setOnClickListener(this);
        this.H.setOnClickListener(this);
        this.I.setOnClickListener(this);
        this.J.setOnClickListener(this);
        this.r.setOnClickListener(this);
        this.s.setOnClickListener(this);
        this.B = false;
        this.D = 0;
        this.L = 1;
        this.M = 1;
        this.W = new oB();
        this.W.f(this.R);
        this.z = new ArrayList<>();
    }

    @Override // androidx.appcompat.app.AppCompatActivity
    public void onPostCreate(Bundle bundle) {
        super.onPostCreate(bundle);
        if (this.S) {
            this.T.isEdited = true;
            e(xB.b().a(this, 2131625274));
            ProjectResourceBean projectResourceBean = this.T;
            this.D = projectResourceBean.rotate;
            this.M = projectResourceBean.flipHorizontal;
            this.L = projectResourceBean.flipVertical;
            this.O = new PB(this, this.Q.getTextInputLayout(), uq.b, p(), this.T.resName);
            this.O.a(1);
            this.P.setText(this.T.resName);
            this.P.setEnabled(false);
            this.Y.setEnabled(false);
            this.G.setVisibility(View.GONE);
            ProjectResourceBean projectResourceBean2 = this.T;
            if (projectResourceBean2.savedPos == 0) {
                f(a(projectResourceBean2));
            } else {
                f(projectResourceBean2.resFullName);
            }
        }
    }

    @Override
    // androidx.fragment.app.FragmentActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onResume() {
        super.onResume();
        this.d.setScreenName(AddImageActivity.class.getSimpleName());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public final ArrayList<String> p() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.A.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    public final void q() {
        this.K.setImageBitmap(iB.a(iB.a(iB.a(this.C, 1024, 1024), this.E), this.D, this.M, this.L));
    }

    public final void r() {
        if (a(this.O)) {
            new Handler().postDelayed(() -> {
                k();
                new AddImageActivity.a(getApplicationContext()).execute();
            }, 500);
        }
    }

    public final void s() {
        TextView textView = this.X;
        if (textView != null) {
            textView.setVisibility(View.INVISIBLE);
        }
        LinearLayout linearLayout = this.w;
        if (linearLayout != null && this.x != null && this.y != null) {
            linearLayout.setVisibility(View.GONE);
            this.x.setVisibility(View.VISIBLE);
            this.y.setVisibility(View.GONE);
        }
    }

    public final void b(boolean z2) {
        try {
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
            if (z2) {
                intent.putExtra("android.intent.extra.ALLOW_MULTIPLE", true);
            }
            startActivityForResult(Intent.createChooser(intent, xB.b().a(this, 2131624976)), 215);
        } catch (ActivityNotFoundException unused) {
            bB.b(this, xB.b().a(this, 2131624907), 0).show();
        }
    }

    public final void f(String str) {
        this.C = str;
        this.K.setImageBitmap(iB.a(str, 1024, 1024));
        this.U = str.lastIndexOf("/");
        this.V = str.lastIndexOf(".");
        if (str.endsWith(".9.png")) {
            this.V = str.lastIndexOf(".9.png");
        }
        EditText editText = this.P;
        if (editText != null && (editText.getText() == null || this.P.getText().length() <= 0)) {
            this.P.setText(str.substring(this.U + 1, this.V));
        }
        try {
            this.E = iB.a(str);
            q();
        } catch (Exception e) {
            e.printStackTrace();
        }
        s();
    }

    public final void g(int i) {
        String str = this.C;
        if (str != null && str.length() > 0) {
            this.D = i;
            if (this.D == 360) {
                this.D = 0;
            }
            q();
        }
    }

    public final void h(int i) {
        LinearLayout linearLayout = this.w;
        if (linearLayout != null && this.x != null && this.y != null) {
            linearLayout.setVisibility(View.VISIBLE);
            this.x.setVisibility(View.GONE);
            this.y.setVisibility(View.VISIBLE);
            TextView textView = this.y;
            StringBuilder sb = new StringBuilder();
            sb.append("+ ");
            sb.append(i - 1);
            sb.append(" more");
            textView.setText(sb.toString());
        }
    }

    public final void n() {
        String str = this.C;
        if (str != null && str.length() > 0) {
            int i = this.D;
            if (i == 90 || i == 270) {
                this.L *= -1;
            } else {
                this.M *= -1;
            }
            q();
        }
    }

    public boolean a(PB pb) {
        if (!pb.b()) {
            return false;
        }
        if (this.B || this.C != null) {
            return true;
        }
        this.X.startAnimation(AnimationUtils.loadAnimation(this, 2130771980));
        return false;
    }

    public final void a(Uri uri) {
        String a2;
        if (uri != null && (a2 = HB.a(this, uri)) != null) {
            f(a2);
        }
    }

    public final void a(ClipData clipData) {
        if (clipData != null) {
            this.N = new ArrayList<>();
            for (int i = 0; i < clipData.getItemCount(); i++) {
                if (i == 0) {
                    a(clipData.getItemAt(i).getUri());
                }
                this.N.add(clipData.getItemAt(i).getUri());
            }
            h(clipData.getItemCount());
        }
    }

    public final String a(ProjectResourceBean projectResourceBean) {
        return this.R + File.separator + projectResourceBean.resFullName;
    }

    class a extends MA {
        public a(Context context) {
            super(context);
            AddImageActivity.this.a(this);
        }

        @Override // a.a.a.MA
        public void a() {
            AddImageActivity.this.h();
            Intent intent = new Intent();
            intent.putExtra("sc_id", AddImageActivity.this.v);
            if (AddImageActivity.this.S) {
                intent.putExtra("image", AddImageActivity.this.T);
            } else {
                intent.putExtra("images", AddImageActivity.this.z);
            }
            AddImageActivity.this.setResult(-1, intent);
            AddImageActivity.this.finish();
        }

        @Override // a.a.a.MA
        public void b() {
            String str;
            String str2 = "";
            ArrayList<String> a2;
            Iterator<String> it;
            char c2 = 0;
            try {
                publishProgress("Now processing..");
                if (AddImageActivity.this.t) {
                    ArrayList<ProjectResourceBean> arrayList = new ArrayList<>();
                    int i = 0;
                    while (i < AddImageActivity.this.N.size()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(AddImageActivity.this.P.getText().toString().trim());
                        sb.append("_");
                        i++;
                        sb.append(i);
                        String sb2 = sb.toString();
                        String a3 = HB.a(AddImageActivity.this.getApplicationContext(), (Uri) AddImageActivity.this.N.get(i));
                        if (a3 != null) {
                            ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, sb2, a3);
                            projectResourceBean.savedPos = 1;
                            projectResourceBean.isNew = true;
                            projectResourceBean.rotate = iB.a(a3);
                            projectResourceBean.flipVertical = 1;
                            projectResourceBean.flipHorizontal = 1;
                            arrayList.add(projectResourceBean);
                        } else {
                            return;
                        }
                    }
                    if (AddImageActivity.this.Y.isChecked()) {
                        Op.g().a(AddImageActivity.this.v, arrayList, true);
                    }
                    AddImageActivity.this.t = false;
                    Iterator<ProjectResourceBean> it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        AddImageActivity.this.z.add(it2.next());
                    }
                } else if (!AddImageActivity.this.S) {
                    ProjectResourceBean projectResourceBean2 = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, AddImageActivity.this.P.getText().toString().trim(), AddImageActivity.this.C);
                    projectResourceBean2.savedPos = 1;
                    projectResourceBean2.isNew = true;
                    projectResourceBean2.rotate = AddImageActivity.this.D;
                    projectResourceBean2.flipVertical = AddImageActivity.this.L;
                    projectResourceBean2.flipHorizontal = AddImageActivity.this.M;
                    if (AddImageActivity.this.Y.isChecked()) {
                        Op.g().a(AddImageActivity.this.v, projectResourceBean2);
                    }
                    AddImageActivity.this.z.add(projectResourceBean2);
                } else if (!AddImageActivity.this.B) {
                    AddImageActivity.this.T.rotate = AddImageActivity.this.D;
                    AddImageActivity.this.T.flipHorizontal = AddImageActivity.this.M;
                    AddImageActivity.this.T.flipVertical = AddImageActivity.this.L;
                    AddImageActivity.this.T.isEdited = true;
                } else {
                    AddImageActivity.this.T.resFullName = AddImageActivity.this.C;
                    AddImageActivity.this.T.savedPos = 1;
                    AddImageActivity.this.T.rotate = AddImageActivity.this.D;
                    AddImageActivity.this.T.flipVertical = AddImageActivity.this.L;
                    AddImageActivity.this.T.flipHorizontal = AddImageActivity.this.M;
                    AddImageActivity.this.T.isEdited = true;
                }
                /*
            } catch (yy e) {
                String message = e.getMessage();
                int hashCode = message.hashCode();
                if (hashCode != -2111590760) {
                    if (hashCode != -1587253668) {
                        if (hashCode == -105163457) {
                            if (message.equals("duplicate_name")) {
                                str = "";
                                if (c2 != 0) {
                                    str2 = xB.b().a(AddImageActivity.this.getApplicationContext(), 2131624903);
                                } else if (c2 == 1) {
                                    str2 = xB.b().a(AddImageActivity.this.getApplicationContext(), 2131624905);
                                } else if (c2 != 2) {
                                    str2 = str;
                                } else {
                                    str2 = xB.b().a(AddImageActivity.this.getApplicationContext(), 2131624904);
                                }
                                a2 = e.a();
                                if (a2 != null && a2.size() > 0) {
                                    it = a2.iterator();
                                    while (it.hasNext()) {
                                        String next = it.next();
                                        if (str.length() > 0) {
                                            str = str + ", ";
                                        }
                                        str = str + next;
                                    }
                                    str2 = str2 + "[" + str + "]";
                                }
                                try {
                                    throw new By(str2);
                                } catch (By by) {
                                    by.printStackTrace();
                                }
                            }
                        }
                    } else if (message.equals("file_no_exist")) {
                        c2 = 1;
                        str = "";
                        if (c2 != 0) {
                        }
                        a2 = e.a();
                        it = a2.iterator();
                        while (it.hasNext()) {
                        }
                        str2 = str2 + "[" + str + "]";
                        try {
                            throw new By(str2);
                        } catch (By by) {
                            by.printStackTrace();
                        }
                    }
                } else if (message.equals("fail_to_copy")) {
                    c2 = 2;
                    str = "";
                    if (c2 != 0) {
                    }
                    a2 = e.a();
                    it = a2.iterator();
                    while (it.hasNext()) {
                    }
                    str2 = str2 + "[" + str + "]";
                    try {
                        throw new By(str2);
                    } catch (By by) {
                        by.printStackTrace();
                    }
                }
                c2 = '\uffff';
                str = "";
                if (c2 != 0) {
                }
                a2 = e.a();
                it = a2.iterator();
                while (it.hasNext()) {
                }
                str2 = str2 + "[" + str + "]";
                try {
                    throw new By(str2);
                } catch (By by) {
                    by.printStackTrace();
                }
                 */
            } catch (Exception ignored) {

            }
            /*} catch (Exception ignored) {
            }*/
        }

        @Override // a.a.a.MA
        public void a(String str) {
            AddImageActivity.this.h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}