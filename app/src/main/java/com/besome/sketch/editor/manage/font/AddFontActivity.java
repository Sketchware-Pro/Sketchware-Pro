package com.besome.sketch.editor.manage.font;

import a.a.a.HB;
import a.a.a.Np;
import a.a.a.WB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.uq;
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
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.besome.sketch.lib.ui.EasyDeleteEditText;
import com.google.android.gms.analytics.HitBuilders;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class AddFontActivity extends BaseDialogActivity implements View.OnClickListener {
    public TextView A;
    public TextView B;
    public CheckBox C;
    public Uri D = null;
    public boolean E;
    public String t;
    public int u;
    public EditText v;
    public EasyDeleteEditText w;
    public ArrayList<String> x;
    public WB y;
    public ImageView z;

    public final void n() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, xB.b().a(this, 2131624976)), 229);
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void o() {
        char c;
        if (a(this.y)) {
            String obj = this.v.getText().toString();
            String a2 = HB.a(this, this.D);
            if (a2 != null) {
                ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, obj, a2);
                projectResourceBean.savedPos = 1;
                projectResourceBean.isNew = true;
                if (this.C.isChecked()) {
                    try {
                        Np.g().a(this.t, projectResourceBean);
                    } catch (Exception e) {
                        // Well, (parts of) the bytecode's lying, yy can be thrown.
                        //noinspection ConstantConditions
                        if (e instanceof yy) {
                            String message = e.getMessage();
                            int hashCode = message.hashCode();
                            if (hashCode == -2111590760) {
                                if (message.equals("fail_to_copy")) {
                                    c = 2;
                                    if (c != 0) {
                                    }
                                }
                                c = 65535;
                                if (c != 0) {
                                }
                            } else if (hashCode != -1587253668) {
                                if (hashCode == -105163457 && message.equals("duplicate_name")) {
                                    c = 0;
                                    if (c != 0) {
                                        bB.b(this, xB.b().a(this, 2131624903), 1).show();
                                        return;
                                    } else if (c == 1) {
                                        bB.b(this, xB.b().a(this, 2131624905), 1).show();
                                        return;
                                    } else if (c == 2) {
                                        bB.b(this, xB.b().a(this, 2131624904), 1).show();
                                        return;
                                    } else {
                                        return;
                                    }
                                }
                                c = 65535;
                                if (c != 0) {
                                }
                            } else {
                                if (message.equals("file_no_exist")) {
                                    c = 1;
                                    if (c != 0) {
                                    }
                                }
                                c = 65535;
                                if (c != 0) {
                                }
                            }
                        }
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("resource_bean", projectResourceBean);
                setResult(-1, intent);
                finish();
            }
        }
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onActivityResult(int i, int i2, Intent intent) {
        Uri data;
        super.onActivityResult(i, i2, intent);
        if (i == 229 && this.z != null && i2 == -1 && (data = intent.getData()) != null) {
            this.D = data;
            try {
                String a2 = HB.a(this, this.D);
                if (a2 != null) {
                    a2.substring(a2.lastIndexOf("."));
                    this.E = true;
                    this.A.setTypeface(Typeface.createFromFile(a2));
                    if (this.v.getText() == null || this.v.getText().length() <= 0) {
                        int lastIndexOf = a2.lastIndexOf("/");
                        int lastIndexOf2 = a2.lastIndexOf(".");
                        if (lastIndexOf2 <= 0) {
                            lastIndexOf2 = a2.length();
                        }
                        this.v.setText(a2.substring(lastIndexOf + 1, lastIndexOf2));
                    }
                    this.A.setVisibility(0);
                }
            } catch (Exception e) {
                this.E = false;
                this.A.setVisibility(8);
                e.printStackTrace();
            }
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id2 = view.getId();
        if (id2 == 2131230909) {
            finish();
        } else if (id2 == 2131230914) {
            o();
        }
    }

    @Override // com.besome.sketch.lib.base.BaseDialogActivity, com.besome.sketch.lib.base.BaseAppCompatActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        e(xB.b().a(this, 2131625261));
        d(xB.b().a(this, 2131625031));
        b(xB.b().a(this, 2131624974));
        setContentView(2131427521);
        Intent intent = getIntent();
        this.t = intent.getStringExtra("sc_id");
        this.x = intent.getStringArrayListExtra("font_names");
        this.u = intent.getIntExtra("request_code", -1);
        this.C = (CheckBox) findViewById(2131230887);
        this.B = (TextView) findViewById(2131231913);
        this.w = (EasyDeleteEditText) findViewById(2131230990);
        this.z = (ImageView) findViewById(2131231710);
        this.A = (TextView) findViewById(2131231069);
        this.v = this.w.getEditText();
        this.w.setHint(xB.b().a(this, 2131625259));
        this.y = new WB(this, this.w.getTextInputLayout(), uq.b, this.x);
        this.v.setPrivateImeOptions("defaultInputmode=english;");
        this.A.setText(xB.b().a(this, 2131625258));
        this.B.setText(xB.b().a(this, 2131625289));
        this.z.setOnClickListener(v -> {
            if (!mB.a()) {
                n();
            }
        });
        this.r.setOnClickListener(this);
        this.s.setOnClickListener(this);
        if (this.u == 272) {
            e(xB.b().a(this, 2131625263));
            this.y = new WB(this, this.w.getTextInputLayout(), uq.b, new ArrayList());
            this.v.setText(((ProjectResourceBean) intent.getParcelableExtra("resource_bean")).resName);
            this.v.setEnabled(false);
            this.C.setEnabled(false);
        }
    }

    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        this.d.setScreenName(AddFontActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public boolean a(WB wb) {
        if (!wb.b()) {
            return false;
        }
        if (this.E && this.D != null) {
            return true;
        }
        this.z.startAnimation(AnimationUtils.loadAnimation(this, 2130771980));
        return false;
    }
}
