package com.besome.sketch.projects;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.android.sdklib.internal.avd.AvdManager;
import com.besome.sketch.lib.base.BaseDialogActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.sketchware.remod.Resources;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

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
import a.a.a.wB;
import a.a.a.wq;
import a.a.a.xB;
import a.a.a.yB;
import mod.SketchwareUtil;
import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;
import mod.w3wide.control.VersionDialog;

public class MyProjectSettingActivity extends BaseDialogActivity implements View.OnClickListener {

    public final String[] t = {"color_accent", "color_primary", "color_primary_dark", "color_control_highlight", "color_control_normal"};
    public final String[] u = {"colorAccent", "colorPrimary", "colorPrimaryDark", "colorControlHighlight", "colorControlNormal"};
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
    public int[] v = new int[t.length];
    /**
     * The sc_id of the currently editing project
     */
    public String w;
    public TextInputLayout x;
    public TextInputLayout y;
    public TextInputLayout z;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            SketchwareUtil.toast("Received invalid data");
            finish();
            return;
        }
        Uri uri = data.getData();
        if (requestCode == 207) {
            if (resultCode == -1 && uri != null) {
                String filename = HB.a(getApplicationContext(), uri);
                Bitmap bitmap = iB.a(filename, 96, 96);
                try {
                    int attributeInt = new ExifInterface(filename).getAttributeInt("Orientation", -1);
                    Bitmap newBitmap = iB.a(bitmap, attributeInt != 3 ? attributeInt != 6 ? attributeInt != 8 ? 0 : 270 : 90 : 180);
                    H.setImageBitmap(newBitmap);
                    a(newBitmap, p());
                    N = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Bundle extras = data.getExtras();
            if (requestCode == 216 && resultCode == -1 && extras != null) {
                try {
                    Bitmap bitmap = extras.getParcelable(AvdManager.DATA_FOLDER);
                    H.setImageBitmap(bitmap);
                    N = true;
                    a(bitmap, p());
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case Resources.id.advanced_setting:
                w();
                return;

            case Resources.id.app_icon_layout:
                t();
                return;

            case Resources.id.common_dialog_cancel_button:
                finish();
                return;

            case Resources.id.common_dialog_ok_button:
                mB.a(v);
                if (q()) {
                    new b(getApplicationContext()).execute();
                    return;
                }
                return;

            case Resources.id.contents:
            default:
                return;

            case Resources.id.img_theme_color_help:
                if (F.getVisibility() == View.VISIBLE) {
                    F.setVisibility(View.GONE);
                } else {
                    F.setVisibility(View.VISIBLE);
                }
                return;

            case Resources.id.ver_code:
            case Resources.id.ver_name:
                if (ConfigActivity.isSettingEnabled(ConfigActivity.SETTING_USE_NEW_VERSION_CONTROL)) {
                    new VersionDialog(this).show();
                } else {
                    v();
                }
        }
    }

    /**
     * Shows a Project Version Control dialog, made by Hosni Fraj. Currently unused.
     */
    public final void showNewVersionControl() {
        R = Q;
        final aB dialog = new aB(this);

        dialog.a(Resources.drawable.numbers_48);
        dialog.b("Version Control");
        final LinearLayout base_layout = new LinearLayout(this);
        base_layout.setOrientation(LinearLayout.VERTICAL);

        final LinearLayout version_name_container = new LinearLayout(this);
        version_name_container.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        final TextInputLayout til_version_name = new TextInputLayout(this);
        final EditText version_name_picker = new EditText(this);
        version_name_picker.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        til_version_name.addView(version_name_picker);
        til_version_name.setHint("Version Name");
        til_version_name.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f));
        version_name_container.addView(til_version_name);

        final TextInputLayout til_version_name_postfix = new TextInputLayout(this);
        final EditText version_name_postfix_picker = new EditText(this);
        version_name_postfix_picker.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        til_version_name_postfix.addView(version_name_postfix_picker);
        til_version_name_postfix.setHint("Version Name Postfix");
        til_version_name_postfix.setLayoutParams(new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f));
        version_name_container.addView(til_version_name_postfix);

        final TextInputLayout til_version_code = new TextInputLayout(this);
        final EditText version_code_picker = new EditText(this);
        version_code_picker.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        til_version_code.addView(version_code_picker);
        til_version_code.setHint("Version Code");

        int sc_ver_code = Integer.parseInt(I.getText().toString());
        int sc_ver_code5 = sc_ver_code - 5;
        int min_value1 = 1;
        if (sc_ver_code5 <= 0) {
            // when the v code is less than 1 so he will return t to one like
            // Current v: 6 min v will be 1 so he will return it to 1 but  when the current v 11 thein v will be 11 - 5
            sc_ver_code5 = 1;
        }

        version_code_picker.setText(String.valueOf(sc_ver_code));

        String[] sc_ver_name = J.getText().toString().split("\\.");
        U = a(sc_ver_name[0], min_value1);
        V = a(sc_ver_name[min_value1], 0);
        if (U - 5 > 0) {
            min_value1 = U - 5;
        }

        version_name_picker.setText(String.valueOf(U));
        int version_name = V;
        int min_value = 0;
        if ((version_name - 20) > 0) {
            min_value = version_name - 20;
        }

        version_name_postfix_picker.setText(String.valueOf(V));
        base_layout.addView(version_name_container);
        base_layout.addView(til_version_code);

        dialog.a(base_layout);
        dialog.b(xB.b().a(this, Resources.string.common_word_save), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    I.setText(version_code_picker.getText().toString());
                    J.setText(version_name_picker.getText().toString()
                            + "."
                            + version_name_postfix_picker.getText().toString());
                    dialog.dismiss();
                }
            }
        });

        dialog.a(xB.b().a(this, Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(dialog));
        dialog.show();
    }

    @Override
    // com.besome.sketch.lib.base.BaseDialogActivity, com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(Resources.layout.myproject_setting);
        if (!j()) {
            finish();
        }
        w = getIntent().getStringExtra("sc_id");
        O = getIntent().getBooleanExtra("is_update", false);
        P = getIntent().getBooleanExtra("advanced_open", false);
        ((TextView) findViewById(Resources.id.tv_change_icon))
                .setText(xB.b().a(this, Resources.string.myprojects_settings_description_change_icon));
        findViewById(Resources.id.contents).setOnClickListener(this);
        findViewById(Resources.id.app_icon_layout).setOnClickListener(this);
        findViewById(Resources.id.advanced_setting).setOnClickListener(this);
        I = findViewById(Resources.id.ver_code);
        I.setOnClickListener(this);
        J = findViewById(Resources.id.ver_name);
        J.setOnClickListener(this);
        x = findViewById(Resources.id.ti_app_name);
        y = findViewById(Resources.id.ti_package_name);
        z = findViewById(Resources.id.ti_project_name);
        x.setHint(xB.b().a(this,
                Resources.string.myprojects_settings_hint_enter_application_name));
        y.setHint(xB.b().a(this,
                Resources.string.myprojects_settings_hint_enter_package_name));
        z.setHint(xB.b().a(this,
                Resources.string.myprojects_settings_hint_enter_project_name));
        A = findViewById(Resources.id.et_app_name);
        B = findViewById(Resources.id.et_package_name);
        C = findViewById(Resources.id.et_project_name);
        ((TextView) findViewById(Resources.id.tv_advanced_settings))
                .setText(xB.b().a(this, Resources.string.myprojects_settings_title_advanced_settings));
        H = findViewById(Resources.id.app_icon);
        M = new LB(getApplicationContext(), x);
        K = new UB(getApplicationContext(), y);
        L = new VB(getApplicationContext(), z);
        B.setPrivateImeOptions("defaultInputmode=english;");
        C.setPrivateImeOptions("defaultInputmode=english;");
        B.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                EditText editText = (EditText) v;
                if (!W && !editText.getText().toString().trim().contains("com.my.newproject")) {
                    u();
                }
            }
        });
        D = findViewById(Resources.id.layout_theme_colors);
        E = findViewById(Resources.id.img_theme_color_help);
        E.setOnClickListener(this);
        F = findViewById(Resources.id.img_color_guide);
        G = findViewById(Resources.id.advanced_setting_layout);
        r.setOnClickListener(this);
        s.setOnClickListener(this);
        v[0] = getResources().getColor(Resources.color.color_accent);
        v[1] = getResources().getColor(Resources.color.color_primary);
        v[2] = getResources().getColor(Resources.color.color_primary_dark);
        v[3] = getResources().getColor(Resources.color.color_control_highlight);
        v[4] = getResources().getColor(Resources.color.color_control_normal);
        for (int i = 0; i < t.length; i++) {
            a aVar = new a(getApplicationContext(), i);
            aVar.e.setText(u[i]);
            aVar.d.setBackgroundColor(Color.WHITE);
            D.addView(aVar);
            aVar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mB.a()) {
                        g((Integer) v.getTag());
                    }
                }
            });
        }
        b(xB.b().a(this, Resources.string.common_word_cancel));
        if (O) {
            e(xB.b().a(getApplicationContext(), Resources.string.myprojects_settings_actionbar_title_project_settings));
            d(xB.b().a(this, Resources.string.myprojects_settings_button_save));
            HashMap<String, Object> map = lC.b(w);
            B.setText(yB.c(map, "my_sc_pkg_name"));
            C.setText(yB.c(map, "my_ws_name"));
            A.setText(yB.c(map, "my_app_name"));
            Q = a(yB.c(map, "sc_ver_code"), 1);
            f(yB.c(map, "sc_ver_name"));
            I.setText(yB.c(map, "sc_ver_code"));
            J.setText(yB.c(map, "sc_ver_name"));
            N = yB.a(map, "custom_icon");
            if (N) {
                if (Build.VERSION.SDK_INT >= 24) {
                    H.setImageURI(FileProvider.a(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", o()));
                } else {
                    H.setImageURI(Uri.fromFile(o()));
                }
            }
            int counter = 0;
            while (counter < t.length) {
                int[] iArr = v;
                iArr[counter] = yB.a(map, t[counter], iArr[counter]);
                counter++;
            }
            x();
        } else {
            e(xB.b().a(getApplicationContext(), Resources.string.myprojects_settings_actionbar_title_new_projet));
            d(xB.b().a(this, Resources.string.myprojects_settings_button_create_app));
            String wsName = getIntent().getStringExtra("my_ws_name");
            String scPkgName = getIntent().getStringExtra("my_sc_pkg_name");
            if (w == null || w.equals("")) {
                w = lC.b();
                wsName = lC.c();
                scPkgName = "com.my." + wsName.toLowerCase();
            }
            B.setText(scPkgName);
            C.setText(wsName);
            A.setText(getIntent().getStringExtra("my_app_name"));
            String verCode = getIntent().getStringExtra("sc_ver_code");
            String verName = getIntent().getStringExtra("sc_ver_name");
            if (verCode == null || verCode.isEmpty()) {
                verCode = "1";
            }
            if (verName == null || verName.isEmpty()) {
                verName = "1.0";
            }
            Q = a(verCode, 1);
            f(verName);
            I.setText(verCode);
            J.setText(verName);
            N = getIntent().getBooleanExtra("custom_icon", false);
            if (N) {
                if (Build.VERSION.SDK_INT >= 24) {
                    H.setImageURI(FileProvider.a(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", o()));
                } else {
                    H.setImageURI(Uri.fromFile(o()));
                }
            }
            x();
        }
        if (P) {
            G.setVisibility(View.VISIBLE);
            B.requestFocus();
        }
    }

    @Override // com.besome.sketch.lib.base.BaseAppCompatActivity
    public void onResume() {
        super.onResume();
        if (!j()) {
            finish();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        oB oBVar = new oB();
        oBVar.f(wq.e() + File.separator + w);
        oBVar.f(wq.g() + File.separator + w);
        oBVar.f(wq.t() + File.separator + w);
        oBVar.f(wq.d() + File.separator + w);
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
        R = Q;
        aB aBVar = new aB(this);
        aBVar.a(Resources.drawable.numbers_48);
        aBVar.b(xB.b().a(this, Resources.string.myprojects_settings_version_control_title));
        View view = wB.a(getApplicationContext(), Resources.layout.property_popup_version_control);
        ((TextView) view.findViewById(Resources.id.tv_code))
                .setText(xB.b().a(this, Resources.string.myprojects_settings_version_control_title_code));
        ((TextView) view.findViewById(Resources.id.tv_name))
                .setText(xB.b().a(this, Resources.string.myprojects_settings_version_control_title_name));
        NumberPicker numberPicker = view.findViewById(Resources.id.version_code);
        NumberPicker numberPicker2 = view.findViewById(Resources.id.version_name1);
        NumberPicker numberPicker3 = view.findViewById(Resources.id.version_name2);
        int i = 0;
        numberPicker.setWrapSelectorWheel(false);
        numberPicker2.setWrapSelectorWheel(false);
        numberPicker3.setWrapSelectorWheel(false);
        int parseInt = Integer.parseInt(I.getText().toString());
        int i2 = parseInt - 5;
        int i3 = 1;
        if (i2 <= 0) {
            i2 = 1;
        }
        numberPicker.setMinValue(i2);
        numberPicker.setMaxValue(parseInt + 5);
        numberPicker.setValue(parseInt);
        String[] split = J.getText().toString().split("\\.");
        U = a(split[0], 1);
        V = a(split[1], 0);
        int i4 = U;
        if (i4 - 5 > 0) {
            i3 = i4 - 5;
        }
        numberPicker2.setMinValue(i3);
        numberPicker2.setMaxValue(U + 5);
        numberPicker2.setValue(U);
        int i5 = V;
        if (i5 - 20 > 0) {
            i = i5 - 20;
        }
        numberPicker3.setMinValue(i);
        numberPicker3.setMaxValue(V + 20);
        numberPicker3.setValue(V);
        aBVar.a(view);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (oldVal > newVal && newVal < Q) {
                    picker.setValue(Q);
                }
            }
        });
        numberPicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                U = newVal;
                if (oldVal > newVal) {
                    if (newVal < S) {
                        numberPicker.setValue(S);
                    }
                    if (U == S || V <= T) {
                        numberPicker3.setValue(T);
                        V = T;
                    }
                }
            }
        });
        numberPicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                V = newVal;
                if (oldVal > newVal && newVal < T && U < S) {
                    picker.setValue(T);
                }
            }
        });
        aBVar.b(xB.b().a(this, Resources.string.common_word_save), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mB.a()) {
                    I.setText(String.valueOf(numberPicker.getValue()));
                    J.setText(U + "." + V);
                    aBVar.dismiss();
                }
            }
        });
        aBVar.a(xB.b().a(this, Resources.string.common_word_cancel),
                Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final void w() {
        if (!G.isShown()) {
            G.setVisibility(View.VISIBLE);
            gB.b(G, 300, null);
            return;
        }
        gB.a(G, 300, new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                G.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationStart(Animator animation) {
            }
        });
    }

    public final void x() {
        for (int i = 0; i < v.length; i++) {
            ((a) D.getChildAt(i)).d.setBackgroundColor(v[i]);
        }
    }

    public final void f(String str) {
        try {
            String[] split = str.split("\\.");
            S = a(split[0], 1);
            T = a(split[1], 0);
        } catch (Exception ignored) {
        }
    }

    public final void g(int i) {
        View view = wB.a(this, Resources.layout.color_picker);
        view.setAnimation(AnimationUtils.loadAnimation(this, Resources.anim.abc_fade_in));
        Zx zx = new Zx(view, this, v[i], false, false);
        zx.a(new Zx.b() {
            @Override
            public void a(int i2) {
                v[i] = i2;
                x();
            }
        });
        zx.setAnimationStyle(Resources.anim.abc_fade_in);
        zx.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    public final void n() {
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), Resources.string.common_word_settings));
        aBVar.a(Resources.drawable.default_icon);
        aBVar.a(xB.b().a(this, Resources.string.myprojects_settings_confirm_reset_icon));
        aBVar.b(xB.b().a(this, Resources.string.common_word_reset), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                H.setImageResource(Resources.drawable.default_icon);
                N = false;
                aBVar.dismiss();
            }
        });
        aBVar.a(xB.b().a(this, Resources.string.common_word_cancel), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aBVar.dismiss();
            }
        });
        aBVar.show();
    }

    public final File o() {
        return new File(p());
    }

    public final String p() {
        return wq.e() + File.separator + w + File.separator + "icon.png";
    }

    public final boolean q() {
        return K.b() && L.b() && M.b();
    }

    public final void r() {
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            Context applicationContext = getApplicationContext();
            uri = FileProvider.a(applicationContext, getApplicationContext().getPackageName() + ".provider", o());
        } else {
            uri = Uri.fromFile(o());
        }
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("output", uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(
                intent,
                xB.b().a(this, Resources.string.common_word_choose)),
                207);
    }

    public final void s() {
        Uri uri;
        Intent intent = new Intent(Intent.ACTION_PICK);
        if (Build.VERSION.SDK_INT >= 24) {
            Context applicationContext = getApplicationContext();
            uri = FileProvider.a(applicationContext, getApplicationContext().getPackageName() + ".provider", o());
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(o());
        }
        intent.setDataAndType(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 96);
        intent.putExtra("outputY", 96);
        intent.putExtra("scale", true);
        intent.putExtra("output", uri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(
                intent,
                xB.b().a(this, Resources.string.common_word_choose)),
                216);
    }

    public final void t() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(xB.b().a(this, Resources.string.myprojects_settings_context_menu_title_choose));
        builder.setItems(new String[]{
                xB.b().a(this, Resources.string.myprojects_settings_context_menu_title_choose_gallery),
                xB.b().a(this, Resources.string.myprojects_settings_context_menu_title_choose_gallery_with_crop),
                xB.b().a(this, Resources.string.myprojects_settings_context_menu_title_choose_gallery_default)
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        r();
                        break;

                    case 1:
                        s();
                        break;

                    case 2:
                        if (N) n();
                        break;
                }
            }
        });
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(true);
        create.show();
    }

    public final void u() {
        W = true;
        aB aBVar = new aB(this);
        aBVar.b(xB.b().a(getApplicationContext(), Resources.string.common_word_warning));
        aBVar.a(Resources.drawable.break_warning_96_red);
        aBVar.a(xB.b().a(this, Resources.string.myprojects_settings_message_package_rename));
        aBVar.b(xB.b().a(this, Resources.string.common_word_ok),
                Helper.getDialogDismissListener(aBVar));
        aBVar.show();
    }

    public final int a(String str, int i) {
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return i;
        }
    }

    public final void a(Bitmap bitmap, String str) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(str)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
        } catch (IOException ignored) {
        }
    }

    public static class a extends LinearLayout {

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
            a = context;
            b = i;
            setTag(i);
            wB.a(context, this, Resources.layout.myproject_color);
            c = findViewById(Resources.id.layout_theme_color);
            d = findViewById(Resources.id.color);
            e = findViewById(Resources.id.name);
        }
    }

    class b extends MA {

        public b(Context context) {
            super(context);
            MyProjectSettingActivity.this.a(this);
            k();
        }

        @Override
        public void a() {
            h();
            Intent intent = getIntent();
            intent.putExtra("sc_id", w);
            intent.putExtra("is_new", !O);
            intent.putExtra("index", intent.getIntExtra("index", -1));
            setResult(-1, intent);
            finish();
        }

        @Override
        public void b() {
            HashMap<String, Object> data = new HashMap<>();
            data.put("sc_id", w);
            data.put("my_sc_pkg_name", B.getText().toString());
            data.put("my_ws_name", C.getText().toString());
            data.put("my_app_name", A.getText().toString());
            if (O) {
                data.put("custom_icon", N);
                data.put("sc_ver_code", I.getText().toString());
                data.put("sc_ver_name", J.getText().toString());
                data.put("sketchware_ver", GB.d(getApplicationContext()));
                for (int i = 0; i < t.length; i++) {
                    data.put(t[i], v[i]);
                }
                lC.b(w, data);
            } else {
                data.put("my_sc_reg_dt", new nB().a("yyyyMMddHHmmss"));
                data.put("custom_icon", N);
                data.put("sc_ver_code", I.getText().toString());
                data.put("sc_ver_name", J.getText().toString());
                data.put("sketchware_ver", GB.d(getApplicationContext()));
                for (int i = 0; i < t.length; i++) {
                    data.put(t[i], v[i]);
                }
                lC.a(w, data);
                wq.a(getApplicationContext(), w);
                new oB().b(wq.b(w));
            }
        }

        @Override
        public void a(String str) {
            h();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}
