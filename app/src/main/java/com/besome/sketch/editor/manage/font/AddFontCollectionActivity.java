package com.besome.sketch.editor.manage.font;

import a.a.a.HB;
import a.a.a.Np;
import a.a.a.Nt;
import a.a.a.WB;
import a.a.a.bB;
import a.a.a.uq;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.yy;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.SelectableBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.google.android.gms.analytics.HitBuilders;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class AddFontCollectionActivity extends BaseDialogActivity implements View.OnClickListener {
    public TextView A;
    public TextView B;
    public CheckBox C;
    public boolean E;
    public ArrayList<ProjectResourceBean> F;
    public ProjectResourceBean G;
    public String u;
    public int v;
    public EditText w;
    public EasyDeleteEditText x;
    public WB y;
    public ImageView z;
    public boolean t = false;
    public Uri D = null;

    public final ArrayList<String> n() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        Iterator<ProjectResourceBean> it = this.F.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().resName);
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void o() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, xB.b().a(this, 0x7f0e0410)), 229);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onActivityResult(int i, int i2, Intent intent) {
        Uri data;
        super/*androidx.fragment.app.FragmentActivity*/.onActivityResult(i, i2, intent);
        if (i == 229 && this.z != null && i2 == -1 && (data = intent.getData()) != null) {
            this.D = data;
            try {
                String a = HB.a(this, this.D);
                if (a == null) {
                    return;
                }
                a.substring(a.lastIndexOf("."));
                this.E = true;
                this.A.setTypeface(Typeface.createFromFile(a));
                if (this.w.getText() == null || this.w.getText().length() <= 0) {
                    int lastIndexOf = a.lastIndexOf("/");
                    int lastIndexOf2 = a.lastIndexOf(".");
                    if (lastIndexOf2 <= 0) {
                        lastIndexOf2 = a.length();
                    }
                    this.w.setText(a.substring(lastIndexOf + 1, lastIndexOf2));
                }
                this.A.setVisibility(0);
            } catch (Exception e) {
                this.E = false;
                this.A.setVisibility(8);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == 0x7f0800bd) {
            finish();
        } else if (id != 0x7f0800c2) {
        } else {
            p();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        e(xB.b().a(this, 0x7f0e052d));
        d(xB.b().a(this, 0x7f0e0447));
        b(xB.b().a(this, 0x7f0e040e));
        setContentView(0x7f0b00c1);
        Intent intent = getIntent();
        this.u = intent.getStringExtra("sc_id");
        this.F = intent.getParcelableArrayListExtra("fonts");
        this.v = intent.getIntExtra("request_code", -1);
        this.G = intent.getParcelableExtra("edit_target");
        if (this.G != null) {
            this.t = true;
        }
        this.C = (CheckBox) findViewById(0x7f0800a7);
        this.C.setVisibility(8);
        this.B = (TextView) findViewById(0x7f0804a9);
        this.B.setVisibility(8);
        this.x = findViewById(0x7f08010e);
        this.z = (ImageView) findViewById(0x7f0803de);
        this.A = (TextView) findViewById(0x7f08015d);
        this.w = this.x.getEditText();
        this.x.setHint(xB.b().a(this, 0x7f0e052b));
        this.y = new WB(this, this.x.getTextInputLayout(), uq.b, n());
        this.w.setPrivateImeOptions("defaultInputmode=english;");
        this.A.setText(xB.b().a(this, 0x7f0e052a));
        this.B.setText(xB.b().a(this, 0x7f0e0549));
        this.z.setOnClickListener(new Nt(this));
        ((BaseDialogActivity) this).r.setOnClickListener(this);
        ((BaseDialogActivity) this).s.setOnClickListener(this);
        if (this.t) {
            e(xB.b().a(this, 0x7f0e0530));
            this.y = new WB(this, this.x.getTextInputLayout(), uq.b, n(), this.G.resName);
            this.w.setText(this.G.resName);
            this.A.setTypeface(Typeface.createFromFile(a(this.G)));
        }
    }

    public void onResume() {
        super/*com.besome.sketch.lib.base.BaseAppCompatActivity*/.onResume();
        ((BaseAppCompatActivity) this).d.setScreenName(AddFontCollectionActivity.class.getSimpleName().toString());
        ((BaseAppCompatActivity) this).d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void p() {
        char c;
        if (a(this.y)) {
            if (!this.t) {
                String obj = this.w.getText().toString();
                String a = HB.a(this, this.D);
                if (a == null) {
                    return;
                }
                ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, obj, a);
                ((SelectableBean) projectResourceBean).savedPos = 1;
                ((SelectableBean) projectResourceBean).isNew = true;
                try {
                    Np.g().a(this.u, projectResourceBean);
                    bB.a(this, xB.b().a(getApplicationContext(), 0x7f0e053c), 1).show();
                } catch (yy e) {
                    String message = e.getMessage();
                    int hashCode = message.hashCode();
                    if (hashCode == -2111590760) {
                        if (message.equals("fail_to_copy")) {
                            c = 2;
                        }
                        c = 65535;
                    } else if (hashCode != -1587253668) {
                        if (hashCode == -105163457 && message.equals("duplicate_name")) {
                            c = 0;
                        }
                        c = 65535;
                    } else {
                        if (message.equals("file_no_exist")) {
                            c = 1;
                        }
                        c = 65535;
                    }
                    if (c == 0) {
                        bB.a(this, xB.b().a(this, 0x7f0e03c7), 1).show();
                        return;
                    } else if (c == 1) {
                        bB.a(this, xB.b().a(this, 0x7f0e03c9), 1).show();
                        return;
                    } else if (c != 2) {
                        return;
                    } else {
                        bB.a(this, xB.b().a(this, 0x7f0e03c8), 1).show();
                        return;
                    }
                }
            } else {
                Np.g().a(this.G, this.w.getText().toString(), true);
                bB.a(this, xB.b().a(getApplicationContext(), 0x7f0e053f), 1).show();
            }
            finish();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean a(WB wb) {
        if (wb.b()) {
            if ((!this.E || this.D == null) && !this.t) {
                this.z.startAnimation(AnimationUtils.loadAnimation(this, 0x7f01000c));
                return false;
            }
            return true;
        }
        return false;
    }

    public final String a(ProjectResourceBean projectResourceBean) {
        return wq.a() + File.separator + "font" + File.separator + "data" + File.separator + projectResourceBean.resFullName;
    }
}
