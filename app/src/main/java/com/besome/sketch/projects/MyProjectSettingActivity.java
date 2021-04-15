package com.besome.sketch.projects;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.android.dx.io.Opcodes;
import com.android.sdklib.internal.avd.AvdManager;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.google.android.flexbox.BuildConfig;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import a.a.a.AC;
import a.a.a.GB;
import a.a.a.HB;
import a.a.a.LB;
import a.a.a.MA;
import a.a.a.UB;
import a.a.a.VB;
import a.a.a.Zx;
import a.a.a.aB;
import a.a.a.gB;
import a.a.a.iB;
import a.a.a.lC;
import a.a.a.mB;
import a.a.a.nB;
import a.a.a.oB;
import a.a.a.oC;
import a.a.a.pC;
import a.a.a.qC;
import a.a.a.rC;
import a.a.a.sC;
import a.a.a.tC;
import a.a.a.uC;
import a.a.a.vC;
import a.a.a.wB;
import a.a.a.wC;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.xC;
import a.a.a.yB;
import a.a.a.yC;
import a.a.a.zC;

public class MyProjectSettingActivity extends BaseDialogActivity implements View.OnClickListener {
    public EditText A;
    public EditText B;
    public EditText C;
    public LinearLayout D;
    public ImageView E;
    public ImageView F;
    public LinearLayout G;
    public ImageView H;
    public TextView I;
    public TextView J;
    public UB K;
    public VB L;
    public LB M;
    public boolean N = false;
    public boolean O = false;
    public boolean P = false;
    public int Q = 1;
    public int R = 1;
    public int S;
    public int T;
    public int U;
    public int V;
    public boolean W;
    public final String[] t = {"color_accent", "color_primary", "color_primary_dark", "color_control_highlight", "color_control_normal"};
    public final String[] u = {"colorAccent", "colorPrimary", "colorPrimaryDark", "colorControlHighlight", "colorControlNormal"};
    public int[] v = new int[this.t.length];
    public String w;
    public TextInputLayout x;
    public TextInputLayout y;
    public TextInputLayout z;

    public class a extends LinearLayout {
        public Context a;
        public int b;
        public LinearLayout c;
        public TextView d;
        public TextView e;

        public a(Context context, int i) {
            super(context);
            a(context, i);
        }

        public final void a(Context context, int i) {
            this.a = context;
            this.b = i;
            setTag(Integer.valueOf(i));
            wB.a(context, this, 2131427582);
            this.c = (LinearLayout) findViewById(2131231413);
            this.d = (TextView) findViewById(2131230904);
            this.e = (TextView) findViewById(2131231561);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        Uri data;
        Bundle extras;
        MyProjectSettingActivity.super.onActivityResult(i, i2, intent);
        if (i != 207) {
            if (i == 216 && i2 == -1 && (extras = intent.getExtras()) != null) {
                try {
                    Bitmap bitmap = (Bitmap) extras.getParcelable(AvdManager.DATA_FOLDER);
                    this.H.setImageBitmap(bitmap);
                    this.N = true;
                    a(bitmap, p());
                } catch (Exception unused) {
                }
            }
        } else if (i2 == -1 && (data = intent.getData()) != null) {
            String a2 = HB.a(getApplicationContext(), data);
            Bitmap a3 = iB.a(a2, 96, 96);
            try {
                int attributeInt = new ExifInterface(a2).getAttributeInt("Orientation", -1);
                Bitmap a4 = iB.a(a3, attributeInt != 3 ? attributeInt != 6 ? attributeInt != 8 ? 0 : 270 : 90 : 180);
                this.H.setImageBitmap(a4);
                a(a4, p());
                this.N = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case 2131230761:
                w();
                return;
            case 2131230779:
                t();
                return;
            case 2131230909:
                finish();
                return;
            case 2131230914:
                mB.a(view);
                if (q()) {
                    new b(getApplicationContext()).execute(new Void[0]);
                    return;
                }
                return;
            case 2131230934:
            default:
                return;
            case 2131231188:
                if (this.F.getVisibility() == 0) {
                    this.F.setVisibility(8);
                    return;
                } else {
                    this.F.setVisibility(0);
                    return;
                }
            case 2131232306:
            case 2131232307:
                v();
                return;
        }
    }

    @Override // com.besome.sketch.lib.base.BaseDialogActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2131427583);
        if (!super.j()) {
            finish();
        }
        this.w = getIntent().getStringExtra("sc_id");
        this.O = getIntent().getBooleanExtra("is_update", false);
        this.P = getIntent().getBooleanExtra("advanced_open", false);
        ((TextView) findViewById(2131231899)).setText(xB.b().a(this, 2131625679));
        findViewById(2131230934).setOnClickListener(this);
        findViewById(2131230779).setOnClickListener(this);
        findViewById(2131230761).setOnClickListener(this);
        this.I = (TextView) findViewById(2131232306);
        this.I.setOnClickListener(this);
        this.J = (TextView) findViewById(2131232307);
        this.J.setOnClickListener(this);
        this.x = findViewById(2131231805);
        this.y = findViewById(2131231826);
        this.z = findViewById(2131231828);
        this.x.setHint(xB.b().a(this, 2131625680));
        this.y.setHint(xB.b().a(this, 2131625681));
        this.z.setHint(xB.b().a(this, 2131625682));
        this.A = (EditText) findViewById(2131231024);
        this.B = (EditText) findViewById(2131231040);
        this.C = (EditText) findViewById(2131231041);
        ((TextView) findViewById(2131231867)).setText(xB.b().a(this, 2131625685));
        this.H = (ImageView) findViewById(2131230778);
        this.M = new LB(getApplicationContext(), this.x);
        this.K = new UB(getApplicationContext(), this.y);
        this.L = new VB(getApplicationContext(), this.z);
        this.B.setPrivateImeOptions("defaultInputmode=english;");
        this.C.setPrivateImeOptions("defaultInputmode=english;");
        this.B.setOnFocusChangeListener(new sC(this));
        this.D = (LinearLayout) findViewById(2131231414);
        this.E = (ImageView) findViewById(2131231188);
        this.E.setOnClickListener(this);
        this.F = (ImageView) findViewById(2131231125);
        this.G = (LinearLayout) findViewById(2131230762);
        this.r.setOnClickListener(this);
        this.s.setOnClickListener(this);
        this.v[0] = getResources().getColor(2131034159);
        this.v[1] = getResources().getColor(2131034174);
        this.v[2] = getResources().getColor(2131034176);
        this.v[3] = getResources().getColor(2131034172);
        this.v[4] = getResources().getColor(2131034173);
        for (int i = 0; i < this.t.length; i++) {
            a aVar = new a(getApplicationContext(), i);
            aVar.e.setText(this.u[i]);
            aVar.d.setBackgroundColor(-1);
            this.D.addView(aVar);
            aVar.setOnClickListener(new tC(this));
        }
        b(xB.b().a(this, 2131624974));
        if (this.O) {
            e(xB.b().a(getApplicationContext(), 2131625671));
            d(xB.b().a(this, 2131625673));
            HashMap b2 = lC.b(this.w);
            this.B.setText(yB.c(b2, "my_sc_pkg_name"));
            this.C.setText(yB.c(b2, "my_ws_name"));
            this.A.setText(yB.c(b2, "my_app_name"));
            this.Q = a(yB.c(b2, "sc_ver_code"), 1);
            f(yB.c(b2, "sc_ver_name"));
            this.I.setText(yB.c(b2, "sc_ver_code"));
            this.J.setText(yB.c(b2, "sc_ver_name"));
            this.N = yB.a(b2, "custom_icon");
            if (this.N) {
                if (Build.VERSION.SDK_INT >= 24) {
                    this.H.setImageURI(FileProvider.a(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", o()));
                } else {
                    this.H.setImageURI(Uri.fromFile(o()));
                }
            }
            int i2 = 0;
            while (true) {
                String[] strArr = this.t;
                if (i2 >= strArr.length) {
                    break;
                }
                int[] iArr = this.v;
                iArr[i2] = yB.a(b2, strArr[i2], iArr[i2]);
                i2++;
            }
            x();
        } else {
            e(xB.b().a(getApplicationContext(), 2131625670));
            d(xB.b().a(this, 2131625672));
            String stringExtra = getIntent().getStringExtra("my_ws_name");
            String stringExtra2 = getIntent().getStringExtra("my_sc_pkg_name");
            String str = this.w;
            if (str == null || str.equals("")) {
                this.w = lC.b();
                stringExtra = lC.c();
                stringExtra2 = "com.my." + stringExtra.toLowerCase();
            }
            this.B.setText(stringExtra2);
            this.C.setText(stringExtra);
            this.A.setText(getIntent().getStringExtra("my_app_name"));
            String stringExtra3 = getIntent().getStringExtra("sc_ver_code");
            String stringExtra4 = getIntent().getStringExtra("sc_ver_name");
            if (stringExtra3 == null || stringExtra3.isEmpty()) {
                stringExtra3 = "1";
            }
            if (stringExtra4 == null || stringExtra4.isEmpty()) {
                stringExtra4 = BuildConfig.VERSION_NAME;
            }
            this.Q = a(stringExtra3, 1);
            f(stringExtra4);
            this.I.setText(stringExtra3);
            this.J.setText(stringExtra4);
            this.N = getIntent().getBooleanExtra("custom_icon", false);
            if (this.N) {
                if (Build.VERSION.SDK_INT >= 24) {
                    this.H.setImageURI(FileProvider.a(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", o()));
                } else {
                    this.H.setImageURI(Uri.fromFile(o()));
                }
            }
            x();
        }
        if (this.P) {
            this.G.setVisibility(0);
            this.B.requestFocus();
        }
    }

    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
        this.d.setScreenName(MyProjectSettingActivity.class.getSimpleName().toString());
        this.d.send(new HitBuilders.ScreenViewBuilder().build());
    }

    public void onStart() {
        MyProjectSettingActivity.super.onStart();
        oB oBVar = new oB();
        oBVar.f(wq.e() + File.separator + this.w);
        oBVar.f(wq.g() + File.separator + this.w);
        oBVar.f(wq.t() + File.separator + this.w);
        oBVar.f(wq.d() + File.separator + this.w);
        File o = o();
        if (!o.exists()) {
            try {
                o.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public final void v() {
        this.R = this.Q;
        aB aBVar = new aB(this);
        aBVar.a(2131166000);
        aBVar.b(xB.b().a(this, 2131625686));
        View a2 = wB.a(getApplicationContext(), 2131427645);
        ((TextView) a2.findViewById(2131231912)).setText(xB.b().a(this, 2131625687));
        ((TextView) a2.findViewById(2131232055)).setText(xB.b().a(this, 2131625688));
        NumberPicker numberPicker = (NumberPicker) a2.findViewById(2131232308);
        NumberPicker numberPicker2 = (NumberPicker) a2.findViewById(2131232309);
        NumberPicker numberPicker3 = (NumberPicker) a2.findViewById(2131232310);
        int i = 0;
        numberPicker.setWrapSelectorWheel(false);
        numberPicker2.setWrapSelectorWheel(false);
        numberPicker3.setWrapSelectorWheel(false);
        int parseInt = Integer.parseInt(this.I.getText().toString());
        int i2 = parseInt - 5;
        int i3 = 1;
        if (i2 <= 0) {
            i2 = 1;
        }
        numberPicker.setMinValue(i2);
        numberPicker.setMaxValue(parseInt + 5);
        numberPicker.setValue(parseInt);
        String[] split = this.J.getText().toString().split("\\.");
        this.U = a(split[0], 1);
        this.V = a(split[1], 0);
        int i4 = this.U;
        if (i4 - 5 > 0) {
            i3 = i4 - 5;
        }
        numberPicker2.setMinValue(i3);
        numberPicker2.setMaxValue(this.U + 5);
        numberPicker2.setValue(this.U);
        int i5 = this.V;
        if (i5 - 20 > 0) {
            i = i5 - 20;
        }
        numberPicker3.setMinValue(i);
        numberPicker3.setMaxValue(this.V + 20);
        numberPicker3.setValue(this.V);
        aBVar.a(a2);
        numberPicker.setOnValueChangedListener(new AC(this));
        numberPicker2.setOnValueChangedListener(new oC(this, numberPicker3));
        numberPicker3.setOnValueChangedListener(new pC(this));
        aBVar.b(xB.b().a(this, 2131625031), new qC(this, numberPicker, aBVar));
        aBVar.a(xB.b().a(this, 2131624974), new rC(this, aBVar));
        aBVar.show();
    }

    public final void w() {
        if (!this.G.isShown()) {
            this.G.setVisibility(0);
            gB.b(this.G, 300, (Animator.AnimatorListener) null);
            return;
        }
        gB.a(this.G, 300, new zC(this));
    }

    public final void x() {
        for (int i = 0; i < this.v.length; i++) {
            ((a) this.D.getChildAt(i)).d.setBackgroundColor(this.v[i]);
        }
    }

    public final void f(String str) {
        try {
            String[] split = str.split("\\.");
            this.S = a(split[0], 1);
            this.T = a(split[1], 0);
        } catch (Exception unused) {
        }
    }

    public final void g(int i) {
        View a2 = wB.a(this, 2131427373);
        a2.setAnimation(AnimationUtils.loadAnimation(this, 2130771968));
        Zx zx = new Zx(a2, this, this.v[i], false, false);
        zx.a(new uC(this, i));
        zx.setAnimationStyle(2130771968);
        zx.showAtLocation(a2, 17, 0, 0);
    }

    public final void n() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), 2131625036));
        aBVar.a(2131165521);
        aBVar.a(xB.b().a(this, 2131625674));
        aBVar.b(xB.b().a(this, 2131625027), new wC(this, aBVar));
        aBVar.a(xB.b().a(this, 2131624974), new xC(this, aBVar));
        aBVar.show();
    }

    public final File o() {
        return new File(p());
    }

    public final String p() {
        return wq.e() + File.separator + this.w + File.separator + "icon.png";
    }

    public final boolean q() {
        return this.K.b() && this.L.b() && this.M.b();
    }
    public final void r() {
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            Context applicationContext = getApplicationContext();
            uri = FileProvider.a(applicationContext, getApplicationContext().getPackageName() + ".provider", o());
        } else {
            uri = Uri.fromFile(o());
        }
        Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        intent.putExtra("output", uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, xB.b().a(this, 2131624976)), (int) Opcodes.REM_DOUBLE_2ADDR);
    }

    public final void s() {
        Uri uri;
        Intent intent = new Intent("android.intent.action.PICK", MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        if (Build.VERSION.SDK_INT >= 24) {
            Context applicationContext = getApplicationContext();
            uri = FileProvider.a(applicationContext, getApplicationContext().getPackageName() + ".provider", o());
            intent.addFlags(1);
            intent.addFlags(2);
            intent.addFlags(64);
        } else {
            uri = Uri.fromFile(o());
        }
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 96);
        intent.putExtra("outputY", 96);
        intent.putExtra("scale", true);
        intent.putExtra("output", uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, xB.b().a(this, 2131624976)), (int) Opcodes.ADD_INT_LIT8);
    }
    public final void t() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(xB.b().a(this, 2131625675));
        builder.setItems((CharSequence[]) new String[]{xB.b().a(this, 2131625676), xB.b().a(this, 2131625678), xB.b().a(this, 2131625677)}, (DialogInterface.OnClickListener) new vC(this));
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(true);
        create.show();
    }
    public final void u() {
        this.W = true;
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), 2131625047));
        aBVar.a(2131165391);
        aBVar.a(xB.b().a(this, 2131625684));
        aBVar.b(xB.b().a(this, 2131625010), new yC(this, aBVar));
        aBVar.show();
    }

    public final int a(String str, int i) {
        try {
            return Integer.valueOf(str).intValue();
        } catch (Exception unused) {
            return i;
        }
    }

    public final void a(Bitmap bitmap, String str) {
        Throwable th;
        Exception e;
        File file = new File(str);
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(file);
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream2);
                fileOutputStream2.flush();
                try {
                    fileOutputStream2.close();
                } catch (IOException unused) {
                }
            } catch (Exception e2) {
                e = e2;
                fileOutputStream = fileOutputStream2;
                try {
                    throw e;
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Throwable th3) {
                th = th3;
                fileOutputStream = fileOutputStream2;
                if (fileOutputStream != null) {
                    try {
                        fileOutputStream.close();
                    } catch (IOException unused2) {
                    }
                }
                try {
                    throw th;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        } catch (Exception e3) {
            e = e3;
            try {
                throw e;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    class b extends MA {
        public b(Context context) {
            super(context);
            MyProjectSettingActivity.this.a((MA) this);
            MyProjectSettingActivity.this.k();
        }

        public void a() {
            MyProjectSettingActivity.this.h();
            Intent intent = MyProjectSettingActivity.this.getIntent();
            MyProjectSettingActivity.this.getIntent().putExtra("sc_id", MyProjectSettingActivity.this.w);
            MyProjectSettingActivity.this.getIntent().putExtra("is_new", !MyProjectSettingActivity.this.O);
            MyProjectSettingActivity.this.getIntent().putExtra("index", MyProjectSettingActivity.this.getIntent().getIntExtra("index", -1));
            MyProjectSettingActivity.this.setResult(-1, intent);
            MyProjectSettingActivity.this.finish();
        }

        public void b() {
            int i = 0;
            if (MyProjectSettingActivity.this.O) {
                HashMap hashMap = new HashMap();
                hashMap.put("sc_id", MyProjectSettingActivity.this.w);
                hashMap.put("my_sc_pkg_name", MyProjectSettingActivity.this.B.getText().toString());
                hashMap.put("my_ws_name", MyProjectSettingActivity.this.C.getText().toString());
                hashMap.put("my_app_name", MyProjectSettingActivity.this.A.getText().toString());
                hashMap.put("custom_icon", Boolean.valueOf(MyProjectSettingActivity.this.N));
                hashMap.put("sc_ver_code", MyProjectSettingActivity.this.I.getText().toString());
                hashMap.put("sc_ver_name", MyProjectSettingActivity.this.J.getText().toString());
                hashMap.put("sketchware_ver", Integer.valueOf(GB.d(MyProjectSettingActivity.this.getApplicationContext())));
                while (i < MyProjectSettingActivity.this.t.length) {
                    hashMap.put(MyProjectSettingActivity.this.t[i], Integer.valueOf(MyProjectSettingActivity.this.v[i]));
                    i++;
                }
                lC.b(MyProjectSettingActivity.this.w, hashMap);
                return;
            }
            HashMap hashMap2 = new HashMap();
            String a = new nB().a("yyyyMMddHHmmss");
            hashMap2.put("sc_id", MyProjectSettingActivity.this.w);
            hashMap2.put("my_sc_pkg_name", MyProjectSettingActivity.this.B.getText().toString());
            hashMap2.put("my_ws_name", MyProjectSettingActivity.this.C.getText().toString());
            hashMap2.put("my_app_name", MyProjectSettingActivity.this.A.getText().toString());
            hashMap2.put("my_sc_reg_dt", a);
            hashMap2.put("custom_icon", Boolean.valueOf(MyProjectSettingActivity.this.N));
            hashMap2.put("sc_ver_code", MyProjectSettingActivity.this.I.getText().toString());
            hashMap2.put("sc_ver_name", MyProjectSettingActivity.this.J.getText().toString());
            hashMap2.put("sketchware_ver", Integer.valueOf(GB.d(MyProjectSettingActivity.this.getApplicationContext())));
            while (i < MyProjectSettingActivity.this.t.length) {
                hashMap2.put(MyProjectSettingActivity.this.t[i], Integer.valueOf(MyProjectSettingActivity.this.v[i]));
                i++;
            }
            HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder();
            eventBuilder.setCategory("project");
            eventBuilder.setLabel("create");
            eventBuilder.setAction("new");
            MyProjectSettingActivity.this.d.send(eventBuilder.build());
            lC.a(MyProjectSettingActivity.this.w, hashMap2);
            wq.a(MyProjectSettingActivity.this.getApplicationContext(), MyProjectSettingActivity.this.w);
            new oB().b(wq.b(MyProjectSettingActivity.this.w));
        }

        public void a(String str) {
            MyProjectSettingActivity.this.h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }
    }
}
