//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.besome.sketch.help;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.besome.sketch.lib.ui.PropertyOneLineItem;
import com.besome.sketch.lib.ui.PropertyTwoLineItem;
import com.besome.sketch.lib.utils.GoogleApiUtil;
import com.google.android.gms.analytics.HitBuilders.EventBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import a.a.a.GB;
import a.a.a.MA;
import a.a.a.aB;
import a.a.a.bB;
import a.a.a.mB;
import a.a.a.pz;
import a.a.a.qz;
import a.a.a.rz;
import a.a.a.wB;
import a.a.a.xB;

public class ProgramInfoActivity extends BaseAppCompatActivity implements OnClickListener {
    public Toolbar k;
    public LinearLayout l;
    public final int m = 1;
    public final int n = 2;
    public final int o = 4;
    public final int p = 5;
    public final int q = 6;
    public final int r = 8;
    public final int s = 9;
    public final int t = 11;
    public final int u = 12;
    public final int v = 13;
    public final int w = 14;
    public final int x = 15;
    public final int y = 17;

    public ProgramInfoActivity() {
    }

    public final void a(int var1, int var2, int var3) {
        this.a(var1, this.getString(var2), this.getString(var3));
    }

    public final void a(int var1, String var2) {
        PropertyOneLineItem var3 = new PropertyOneLineItem(this);
        var3.setKey(var1);
        var3.setName(var2);
        this.l.addView(var3);
        if (var1 == 1 || var1 == 2 || var1 == 14 || var1 == 15) {
            var3.setOnClickListener(this);
        }

    }

    public final void a(int var1, String var2, String var3) {
        PropertyTwoLineItem var4 = new PropertyTwoLineItem(this);
        var4.setKey(var1);
        var4.setName(var2);
        var4.setDesc(var3);
        this.l.addView(var4);
        var4.setBackgroundColor(-592138);
        var4.setOnClickListener(this);
        if (var1 != 4) {
            if (var1 == 6 || var1 == 8) {
                return;
            }

            if (var1 != 17) {
                if (var1 != 11) {
                }

                return;
            }
        }

        var4.setBackgroundColor(-1);
    }

    public final void b(int var1, int var2) {
        this.a(var1, this.getString(var2));
    }

    public final void b(String var1) {
        Intent var2 = new Intent("android.intent.action.SEND");
        var2.addCategory("android.intent.category.DEFAULT");
        var2.putExtra("android.intent.extra.SUBJECT", "Sketchware - Create your own Android apps using block language, directly on your device!");
        var2.putExtra("android.intent.extra.TEXT", var1);
        var2.setType("text/plain");
        this.startActivity(Intent.createChooser(var2, "Share"));
        EventBuilder var3 = new EventBuilder();
        var3.setCategory("user_campign");
        var3.setAction("share");
        var3.setLabel("friend");
        super.d.send(var3.build());
    }

    public final void l() {
        Intent var1 = new Intent("android.intent.action.VIEW", Uri.parse(xB.b().a(this.getApplicationContext(), 2131624004)));
        var1.setFlags(536870912);
        Intent var2 = Intent.createChooser(var1, xB.b().a(this.getApplicationContext(), 2131624976));
        var1.addFlags(1);
        var1.addFlags(2);
        var1.addFlags(64);
        this.startActivity(var2);
    }

    public final void m() {
        aB var1 = new aB(this);
        var1.b(xB.b().a(this.getApplicationContext(), 2131625764));
        var1.a(2131166052);
        View var2 = wB.a(this, 2131427367);
        RadioGroup var3 = var2.findViewById(2131231670);
        ((RadioButton) var2.findViewById(2131231650)).setText(xB.b().a(this.getApplicationContext(), 2131625766));
        ((RadioButton) var2.findViewById(2131231656)).setText(xB.b().a(this.getApplicationContext(), 2131625765));
        var1.a(var2);
        var1.b(xB.b().a(this.getApplicationContext(), 2131625050), new qz(this, var3, var1));
        var1.a(xB.b().a(this.getApplicationContext(), 2131624974), new rz(this, var1));
        var1.show();
    }

    public final void n() {
        String var1 = xB.b().a(this.getApplicationContext(), 2131625397);

        try {
            StringBuilder var2 = new StringBuilder();
            var2.append("fb://facewebmodal/f?href=");
            var2.append(var1);
            Uri var3 = Uri.parse(var2.toString());
            Intent var5 = new Intent("android.intent.action.VIEW", var3);
            this.startActivity(var5);
        } catch (Exception var4) {
            this.startActivity(Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse(var1)), xB.b().a(this.getApplicationContext(), 2131624976)));
        }

    }

    public final void o() {
        Intent var1 = new Intent("android.intent.action.VIEW", Uri.parse("https://ideas.sketchware.io/"));
        var1.setFlags(536870912);
        Intent var2 = Intent.createChooser(var1, xB.b().a(this.getApplicationContext(), 2131624976));
        var1.addFlags(1);
        var1.addFlags(2);
        var1.addFlags(64);
        this.startActivity(var2);
    }

    public void onClick(View var1) {
        if (!mB.a()) {
            switch (var1.getId()) {
                case 2131230807:
                    this.m();
                    break;
                case 2131230808:
                    if (!GB.h(this.getApplicationContext())) {
                        bB.a(this.getApplicationContext(), xB.b().a(this.getApplicationContext(), 2131624932), 0).show();
                        return;
                    }

                    this.p();
            }

            int var2;
            if (var1 instanceof PropertyOneLineItem) {
                var2 = ((PropertyOneLineItem) var1).getKey();
                if (var2 != 1) {
                    if (var2 != 2) {
                        if (var2 != 14) {
                            if (var2 == 15) {
                                if (!GB.h(this.getApplicationContext())) {
                                    bB.a(this.getApplicationContext(), xB.b().a(this.getApplicationContext(), 2131624932), 0).show();
                                    return;
                                }

                                this.s();
                            }
                        } else {
                            if (!GB.h(this.getApplicationContext())) {
                                bB.a(this.getApplicationContext(), xB.b().a(this.getApplicationContext(), 2131624932), 0).show();
                                return;
                            }

                            this.v();
                        }
                    } else {
                        if (!GB.h(this.getApplicationContext())) {
                            bB.a(this.getApplicationContext(), xB.b().a(this.getApplicationContext(), 2131624932), 0).show();
                            return;
                        }

                        (new ProgramInfoActivity.a(this, this.getApplicationContext())).execute();
                    }
                } else {
                    this.u();
                }
            }

            if (var1 instanceof PropertyTwoLineItem) {
                var2 = ((PropertyTwoLineItem) var1).getKey();
                if (var2 != 4) {
                    if (var2 != 6) {
                        if (var2 != 8) {
                            if (var2 != 17) {
                                if (var2 != 11) {
                                    if (var2 == 12) {
                                        this.q();
                                    }
                                } else {
                                    if (!GB.h(this.getApplicationContext())) {
                                        bB.a(this.getApplicationContext(), xB.b().a(this.getApplicationContext(), 2131624932), 0).show();
                                        return;
                                    }

                                    this.r();
                                }
                            } else {
                                this.o();
                            }
                        } else {
                            this.l();
                        }
                    } else {
                        this.n();
                    }
                } else {
                    if (!GB.h(this.getApplicationContext())) {
                        bB.a(this.getApplicationContext(), xB.b().a(this.getApplicationContext(), 2131624932), 0).show();
                        return;
                    }

                    this.t();
                }
            }

        }
    }

    public void onCreate(Bundle var1) {
        super.onCreate(var1);
        this.setContentView(2131427612);
        this.k = this.findViewById(2131231847);
        this.a(this.k);
        this.d().d(true);
        this.d().e(true);
        this.findViewById(2131231370).setVisibility(8);
        this.d().a(xB.b().a(this, 2131625599));
        this.k.setNavigationOnClickListener(new pz(this));
        this.l = this.findViewById(2131230932);
        TextView var2 = this.findViewById(2131232167);
        StringBuilder var3 = new StringBuilder();
        var3.append("Version ");
        var3.append(GB.e(this.getApplicationContext()));
        var2.setText(var3.toString());
        Button var4 = this.findViewById(2131230807);
        var4.setText(xB.b().a(this.getApplicationContext(), 2131625761));
        var4.setOnClickListener(this);
        var4 = this.findViewById(2131230808);
        var4.setText(xB.b().a(this.getApplicationContext(), 2131625760));
        var4.setOnClickListener(this);
        this.a(4, 2131625767, 2131625320);
        this.a(17, 2131625771, 2131625407);
        this.a(2, xB.b().a(this, 2131625603));
        this.b(5, 2131626406);
        this.a(6, 2131626413, 2131625397);
        this.a(8, 2131626405, 2131624004);
        this.b(9, 2131626409);
        this.a(11, 2131626411, 2131625053);
        this.a(12, 2131626408, 2131625051);
        this.a(13, 2131626410, 2131625052);
        this.b(14, 2131625769);
        this.b(1, 2131625772);
        this.b(15, 2131625768);
    }

    public final void p() {
        Intent var1 = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.besome.sketch&referrer=utm_source%3Din_sketchware%26utm_medium%3Dcheck_update"));
        var1.setFlags(536870912);
        var1.addFlags(1);
        var1.addFlags(2);
        var1.addFlags(64);
        var1.setFlags(536870912);
        this.startActivity(var1);
    }

    public final void q() {
        Intent var1 = new Intent("android.intent.action.SENDTO", Uri.parse("mailto:help@sketchware.io"));
        var1.setFlags(536870912);
        var1.addFlags(1);
        var1.addFlags(2);
        var1.addFlags(64);
        this.startActivity(var1);
    }

    public final void r() {
        Intent var1 = new Intent("android.intent.action.VIEW");
        var1.setFlags(536870912);
        var1.setData(Uri.parse("http://sketchware.io"));
        var1.addFlags(1);
        var1.addFlags(2);
        var1.addFlags(64);
        this.startActivity(Intent.createChooser(var1, xB.b().a(this.getApplicationContext(), 2131624976)));
    }

    public final void s() {
        Intent var1 = new Intent(this.getApplicationContext(), LicenseActivity.class);
        var1.setFlags(536870912);
        var1.addFlags(1);
        var1.addFlags(2);
        var1.addFlags(64);
        this.startActivity(var1);
    }

    public final void t() {
        Intent var1 = new Intent("android.intent.action.VIEW", Uri.parse("https://docs.sketchware.io/blog"));
        var1.setFlags(536870912);
        Intent var2 = Intent.createChooser(var1, xB.b().a(this.getApplicationContext(), 2131624976));
        var1.addFlags(1);
        var1.addFlags(2);
        var1.addFlags(64);
        this.startActivity(var2);
    }

    public final void u() {
        Intent var1 = new Intent(this.getApplicationContext(), SystemInfoActivity.class);
        var1.setFlags(536870912);
        var1.addFlags(1);
        var1.addFlags(2);
        var1.addFlags(64);
        this.startActivity(var1);
    }

    public final void v() {
        Intent var1 = new Intent("android.intent.action.VIEW");
        var1.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        String var2 = "http://sketchware.io/terms.html";
        Uri var3 = Uri.parse(var2);
        var1.addFlags(1);
        var1.addFlags(2);
        var1.addFlags(64);
        var1.setData(var3);
        this.startActivity(Intent.createChooser(var1, xB.b().a(this.getApplicationContext(), 2131624976)));
    }

    public class a extends MA {
        public String c;
        public final ProgramInfoActivity d;

        public a(ProgramInfoActivity var1, Context var2) {
            super(var2);
            this.d = var1;
            var1.a(this);
        }

        public void a() {
            String var1 = this.c;
            if (var1 != null) {
                this.d.b(var1);
            } else {
                bB.b(super.a, xB.b().a(super.a, 2131626337), 0).show();
            }

        }

        public void a(String var1) {
            bB.b(super.a, xB.b().a(super.a, 2131626337), 0).show();
        }

        public void b() {
            String var2 = null;
            try {
                var2 = "http://sketchware.io/link.jsp?id=hbyp&title=" +
                        URLEncoder.encode("Sketchware - IDE in Your Pocket", "utf-8") +
                        "&description=" +
                        URLEncoder.encode("Create your own mobile applications on your smartphone", "utf-8") +
                        "&image=" +
                        URLEncoder.encode("http://sketchware.io/images/sketchware_meta_en.png", "utf-8");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            this.c = (new GoogleApiUtil()).a(var2);
        }

        @Override
        protected String doInBackground(Void... voids) {
            return a(voids);
        }
    }
}
