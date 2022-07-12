package com.besome.sketch.tools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;

import a.a.a.RB;
import a.a.a.SB;
import a.a.a.VB;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.iI;
import a.a.a.mB;
import a.a.a.wq;
import a.a.a.xB;
import mod.hey.studios.util.Helper;

public class NewKeyStoreActivity extends BaseAppCompatActivity implements OnClickListener {

    public RB A, B, C, D, y, z;
    public iI E;
    public int k = 25;
    public Toolbar l;
    public EditText m;
    public EditText n;
    public EditText o;
    public EditText p;
    public EditText q;
    public EditText r;
    public EditText s;
    public EditText t;
    public EditText u;
    public VB v;
    public SB w, x;

    private void a(boolean var1, String var2) {
        aB var3 = new aB(this);
        var3.b(Helper.getResString(2131625738));
        if (var1) {
            var3.a(2131165404);
            var3.a(Helper.getResString(2131625719));
        } else {
            var3.a(2131165548);
            var3.a(Helper.getResString(2131625722));
        }

        var3.b(Helper.getResString(2131624977), view -> {
            if (!mB.a()) {
                var3.dismiss();
                if (var1) {
                    Intent intent = new Intent();
                    intent.putExtra("pwd", var2);
                    setResult(-1, intent);
                    finish();
                } else {
                    finish();
                }
            }
        });
        var3.show();
    }

    private void l() {
        if (w.b() && x.b()) {
            String text = n.getText().toString();
            if (!text.equals(o.getText().toString())) {
                bB.b(getApplicationContext(), Helper.getResString(2131625727), 0).show();
                n.setText("");
                o.setText("");
                return;
            }

            if (!v.b()) return;

            if (!y.b()) return;

            if (!z.b()) return;

            if (!A.b()) return;

            if (!B.b()) return;

            if (!C.b()) return;

            if (!D.b()) return;

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CN=");
            stringBuilder.append(p.getText().toString());
            stringBuilder.append("OU=");
            stringBuilder.append(q.getText().toString());
            stringBuilder.append("O=");
            stringBuilder.append(r.getText().toString());
            stringBuilder.append("L=");
            stringBuilder.append(s.getText().toString());
            stringBuilder.append("ST=");
            stringBuilder.append(t.getText().toString());
            stringBuilder.append("C=");
            stringBuilder.append(u.getText().toString());

            try {
                E.a(wq.j(), stringBuilder.toString(), k, m.getText().toString(), text);
                a(true, text);
            } catch (Exception e) {
                e.printStackTrace();
                a(false, null);
            }
        }

    }

    @Override
    public void onClick(View var1) {
        if (!mB.a()) {
            switch (var1.getId()) {
                case 2131230833:
                    finish();
                    break;
                case 2131230834:
                    l();
            }
        }
    }

    @Override
    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        if (!super.j()) {
            finish();
        }

        setContentView(2131427471);
        l = findViewById(2131231847);
        a(l);
        findViewById(2131231370).setVisibility(View.GONE);
        d().a(Helper.getResString(2131625739));
        d().e(true);
        d().d(true);
        l.setNavigationOnClickListener(Helper.getBackPressedClickListener(this));
        E = new iI();
        ((TextView) findViewById(2131232058)).setText(Helper.getResString(2131625739));
        Button var2 = findViewById(2131230834);
        var2.setText(Helper.getResString(2131625729));
        var2.setOnClickListener(this);
        var2 = findViewById(2131230833);
        var2.setText(Helper.getResString(2131624974));
        var2.setOnClickListener(this);
        ((TextView) findViewById(2131232022)).setText(wq.D);
        m = findViewById(2131231035);
        ((TextInputLayout) findViewById(2131231820)).setHint(Helper.getResString(2131625730));
        n = findViewById(2131231036);
        ((TextInputLayout) findViewById(2131231821)).setHint(Helper.getResString(2131625726));
        o = findViewById(2131231037);
        ((TextInputLayout) findViewById(2131231822)).setHint(Helper.getResString(2131625732));
        EditText var3 = findViewById(2131231045);
        ((TextInputLayout) findViewById(2131231832)).setHint(Helper.getResString(2131625740));
        var3.setText(String.valueOf(k));
        ((TextView) findViewById(2131231898)).setText(Helper.getResString(2131625738));
        p = findViewById(2131231029);
        ((TextInputLayout) findViewById(2131231811)).setHint(Helper.getResString(2131625734));
        q = findViewById(2131231032);
        ((TextInputLayout) findViewById(2131231814)).setHint(Helper.getResString(2131625736));
        r = findViewById(2131231031);
        ((TextInputLayout) findViewById(2131231813)).setHint(Helper.getResString(2131625735));
        s = findViewById(2131231030);
        ((TextInputLayout) findViewById(2131231812)).setHint(Helper.getResString(2131625731));
        t = findViewById(2131231033);
        ((TextInputLayout) findViewById(2131231815)).setHint(Helper.getResString(2131625737));
        u = findViewById(2131231028);
        ((TextInputLayout) findViewById(2131231810)).setHint(Helper.getResString(2131625733));
        v = new VB(getApplicationContext(), findViewById(2131231820));
        w = new SB(getApplicationContext(), findViewById(2131231821), 4, 32);
        x = new SB(getApplicationContext(), findViewById(2131231822), 4, 32);
        y = new RB(getApplicationContext(), findViewById(2131231811));
        z = new RB(getApplicationContext(), findViewById(2131231814));
        A = new RB(getApplicationContext(), findViewById(2131231813));
        B = new RB(getApplicationContext(), findViewById(2131231812));
        C = new RB(getApplicationContext(), findViewById(2131231815));
        D = new RB(getApplicationContext(), findViewById(2131231810));
        m.setPrivateImeOptions("defaultInputmode=english;");
        p.setPrivateImeOptions("defaultInputmode=english;");
        q.setPrivateImeOptions("defaultInputmode=english;");
        r.setPrivateImeOptions("defaultInputmode=english;");
        s.setPrivateImeOptions("defaultInputmode=english;");
        t.setPrivateImeOptions("defaultInputmode=english;");
        u.setPrivateImeOptions("defaultInputmode=english;");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!super.j()) {
            finish();
        }
    }
}
