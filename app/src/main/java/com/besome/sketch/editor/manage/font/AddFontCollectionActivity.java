package com.besome.sketch.editor.manage.font;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.besome.sketch.beans.ProjectResourceBean;
import com.besome.sketch.beans.SelectableBean;
import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;

import a.a.a.HB;
import a.a.a.Np;
import a.a.a.Nt;
import a.a.a.WB;
import a.a.a.bB;
import a.a.a.uq;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.yy;

public class AddFontCollectionActivity extends BaseDialogActivity implements View.OnClickListener {
    public TextView A;
    public TextView B;
    public CheckBox C;
    public boolean E;
    public ArrayList<ProjectResourceBean> F;
    public ProjectResourceBean G;
    public String u;
    public int v;
    public TextInputEditText inputEditText;
    public TextInputLayout inputLayout;
    public WB y;
    public ImageView z;
    public boolean t = false;
    public Uri D = null;

    public final ArrayList<String> n() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("app_icon");
        for (ProjectResourceBean projectResourceBean : this.F) {
            arrayList.add(projectResourceBean.resName);
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void o() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("*/*");
        startActivityForResult(Intent.createChooser(intent, xB.b().a(this, R.string.common_word_choose)), 229);
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
                if (this.inputEditText.getText() == null || this.inputEditText.getText().length() <= 0) {
                    int lastIndexOf = a.lastIndexOf("/");
                    int lastIndexOf2 = a.lastIndexOf(".");
                    if (lastIndexOf2 <= 0) {
                        lastIndexOf2 = a.length();
                    }
                    this.inputEditText.setText(a.substring(lastIndexOf + 1, lastIndexOf2));
                }
                this.A.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                this.E = false;
                this.A.setVisibility(View.GONE);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.common_dialog_cancel_button) {
            finish();
        } else if (id != R.id.common_dialog_ok_button) {
        } else {
            p();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        e(xB.b().a(this, R.string.design_manager_font_title_add_font));
        d(xB.b().a(this, R.string.common_word_save));
        b(xB.b().a(this, R.string.common_word_cancel));
        setContentView(R.layout.manage_font_add);
        Intent intent = getIntent();
        this.u = intent.getStringExtra("sc_id");
        this.F = intent.getParcelableArrayListExtra("fonts");
        this.v = intent.getIntExtra("request_code", -1);
        this.G = intent.getParcelableExtra("edit_target");
        if (this.G != null) {
            this.t = true;
        }
        this.C = (CheckBox) findViewById(R.id.chk_collection);
        this.C.setVisibility(View.GONE);
        this.B = (TextView) findViewById(R.id.tv_collection);
        this.B.setVisibility(View.GONE);
        this.inputEditText = findViewById(R.id.ed_input);
        this.inputLayout = findViewById(R.id.ti_input);
        this.z = (ImageView) findViewById(R.id.select_file);
        this.A = (TextView) findViewById(R.id.font_preview);
        this.y = new WB(this, inputLayout, uq.b, n());
        this.A.setText(xB.b().a(this, R.string.design_manager_font_description_look_like_this));
        this.B.setText(xB.b().a(this, R.string.design_manager_title_add_to_collection));
        this.z.setOnClickListener(new Nt(this));
        ((BaseDialogActivity) this).r.setOnClickListener(this);
        ((BaseDialogActivity) this).s.setOnClickListener(this);
        if (this.t) {
            e(xB.b().a(this, R.string.design_manager_font_title_edit_font_name));
            this.y = new WB(this, inputLayout, uq.b, n(), this.G.resName);
            this.inputEditText.setText(this.G.resName);
            this.A.setTypeface(Typeface.createFromFile(a(this.G)));
        }
    }

    public void onResume() {
        super.onResume();
        ((BaseAppCompatActivity) this).d.setScreenName(AddFontCollectionActivity.class.getSimpleName().toString());
        ((BaseAppCompatActivity) this).d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public final void p() {
        if (a(this.y)) {
            if (!this.t) {
                String obj = this.inputEditText.getText().toString();
                String a = HB.a(this, this.D);
                if (a == null) {
                    return;
                }
                ProjectResourceBean projectResourceBean = new ProjectResourceBean(ProjectResourceBean.PROJECT_RES_TYPE_FILE, obj, a);
                ((SelectableBean) projectResourceBean).savedPos = 1;
                ((SelectableBean) projectResourceBean).isNew = true;
                try {
                    Np.g().a(this.u, projectResourceBean);
                    bB.a(this, xB.b().a(getApplicationContext(), R.string.design_manager_message_add_complete), 1).show();
                } catch (Exception e) {
                    int c = -1;
                    // Well, (parts of) the bytecode's lying, yy can be thrown.
                    //noinspection ConstantConditions
                    if (e instanceof yy) {
                        String message = e.getMessage();
                        switch (message) {
                            case "fail_to_copy" -> c = 2;
                            case "file_no_exist" -> c = 1;
                            case "duplicate_name" -> c = 0;
                        }

                        switch (c) {
                            case 0 ->
                                    bB.a(this, xB.b().a(this, R.string.collection_duplicated_name), 1).show();
                            case 1 ->
                                    bB.a(this, xB.b().a(this, R.string.collection_no_exist_file), 1).show();
                            case 2 ->
                                    bB.a(this, xB.b().a(this, R.string.collection_failed_to_copy), 1).show();
                        }
                    }
                }

            }
        } else {
            Np.g().a(this.G, this.inputEditText.getText().toString(), true);
            bB.a(this, xB.b().a(getApplicationContext(), R.string.design_manager_message_edit_complete), 1).show();
        }
        finish();
    }

    public static void a(AddFontCollectionActivity addFontCollectionActivity) {
        addFontCollectionActivity.o();
    }

    public boolean a(WB wb) {
        if (wb.b()) {
            if ((!this.E || this.D == null) && !this.t) {
                this.z.startAnimation(AnimationUtils.loadAnimation(this, R.anim.ani_1));
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
