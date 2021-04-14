package com.besome.sketch;

import a.a.a.Cn;
import a.a.a.Dn;
import a.a.a.EA;
import a.a.a.En;
import a.a.a.Fn;
import a.a.a.GB;
import a.a.a.Gn;
import a.a.a.Zo;
import a.a.a.bB;
import a.a.a.ci;
import a.a.a.wB;
import a.a.a.xB;
import mod.ilyasse.activities.about.AboutModActivity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainDrawer extends LinearLayout {
    public Context a;
    public a b;
    public ImageView c;
    public ImageView d;
    public ImageView e;
    public EA f;
    public Zo g;
    private final Intent toabout = new Intent();

    /* access modifiers changed from: package-private */
    public enum b {
        eMenu_orders,
        eMenu_docs,
        eMenu_system_settings,
        eMenu_program_info,
        eMenu_language_settings,
        eMenu_export_urls;
        
        public String h;
        public int i;

        public int d() {
            return this.i;
        }

        public String e() {
            return this.h;
        }
    }

    public MainDrawer(Context context) {
        super(context);
        a(context);
    }

    public final void g() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.a);
        builder.setItems((CharSequence[]) new String[]{xB.b().a(getContext(), 2131625593), xB.b().a(getContext(), 2131625594)}, (DialogInterface.OnClickListener) new Fn(this));
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(true);
        create.show();
    }

    public final void h() {
        changeLogDialog(this.a);
    }

    public void i() {
        if (this.g.h()) {
            b bVar = b.eMenu_orders;
            bVar.i = 2131165402;
            bVar.h = xB.b().a(getContext(), 2131625600);
        } else {
            b bVar2 = b.eMenu_orders;
            bVar2.i = 2131165956;
            bVar2.h = xB.b().a(getContext(), 2131625605);
        }
        this.b.c();
    }

    public final void a(Context context) {
        this.a = context;
        this.f = new EA(context);
        this.g = new Zo(context);
        wB.a(context, this, 2131427502);
        this.c = (ImageView) findViewById(2131231743);
        this.d = (ImageView) findViewById(2131231744);
        this.e = (ImageView) findViewById(2131231745);
        RecyclerView findViewById = findViewById(2131231519);
        findViewById.setHasFixedSize(true);
        findViewById.setLayoutManager(new LinearLayoutManager(getContext()));
        findViewById.setItemAnimator(new ci());
        this.b = new a();
        findViewById.setAdapter(this.b);
        d();
        c();
    }

    public final void b() {
        String string = this.a.getString(2131625397);
        try {
            this.a.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("fb://facewebmodal/f?href=" + string)));
        } catch (Exception unused) {
            this.a.startActivity(Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse(string)), xB.b().a(getContext(), 2131624976)));
        }
    }

    public final void c() {
        this.c.setOnClickListener(new Cn(this));
        this.d.setOnClickListener(new Dn(this));
        this.e.setOnClickListener(new En(this));
    }

    public final void d() {
        if (this.g.h()) {
            b bVar = b.eMenu_orders;
            bVar.i = 2131166149;
            bVar.h = "About Modders";
        } else {
            b bVar2 = b.eMenu_orders;
            bVar2.i = 2131166149;
            bVar2.h = "About Modders";
        }
        b bVar3 = b.eMenu_docs;
        bVar3.i = 2131165899;
        xB.b().a(getContext(), 2131625596);
        bVar3.h = "Change Log";
        b bVar4 = b.eMenu_system_settings;
        bVar4.i = 2131166153;
        bVar4.h = xB.b().a(getContext(), 2131625606);
        b bVar5 = b.eMenu_program_info;
        bVar5.i = 2131166149;
        bVar5.h = xB.b().a(getContext(), 2131625599);
        b bVar6 = b.eMenu_language_settings;
        bVar6.i = 2131165931;
        bVar6.h = xB.b().a(getContext(), 2131625598);
        b bVar7 = b.eMenu_export_urls;
        bVar7.i = 2131165750;
        bVar7.h = xB.b().a(getContext(), 2131625597);
    }

    public final void e() {
        if (GB.h(this.a)) {
            try {
                this.a.startActivity(this.a.getPackageManager().getLaunchIntentForPackage("com.Slack"));
            } catch (Exception unused) {
                f();
            }
        } else {
            bB.a(this.a, xB.b().a(getContext(), 2131624932), 0).show();
        }
    }

    public final void f() {
        if (GB.h(this.a)) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(xB.b().a(getContext(), 2131626369)));
                intent.setFlags(536870912);
                this.a.startActivity(Intent.createChooser(intent, xB.b().a(getContext(), 2131624976)));
            } catch (Exception unused) {
                Intent intent2 = new Intent("android.intent.action.VIEW", Uri.parse(this.a.getString(2131626370)));
                intent2.setFlags(536870912);
                this.a.startActivity(Intent.createChooser(intent2, xB.b().a(getContext(), 2131624976)));
            }
        } else {
            bB.a(this.a, xB.b().a(getContext(), 2131624932), 0).show();
        }
    }

    public MainDrawer(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    /* access modifiers changed from: package-private */
    public class a extends RecyclerView.a<RecyclerView.v> {
        public int c = -1;

        /* renamed from: com.besome.sketch.MainDrawer$a$a  reason: collision with other inner class name */
        class C0000a extends RecyclerView.v {
            public ImageView t;
            public TextView u;
            public TextView v;

            public C0000a(View view) {
                super(view);
                this.t = (ImageView) view.findViewById(2131231199);
                this.u = (TextView) view.findViewById(2131232036);
                this.v = (TextView) view.findViewById(2131231973);
            }
        }

        class b extends RecyclerView.v {
            public ImageView t;
            public TextView u;

            public b(View view) {
                super(view);
                this.u = (TextView) view.findViewById(2131232044);
                this.t = (ImageView) view.findViewById(2131231151);
                view.setOnClickListener(new Gn(this, a.this));
            }
        }

        public a() {
        }

        public int a() {
            return b.values().length + 1;
        }

        public int b(int i) {
            return i == 0 ? 0 : 1;
        }

        public RecyclerView.v b(ViewGroup viewGroup, int i) {
            if (i == 0) {
                return new C0000a(LayoutInflater.from(viewGroup.getContext()).inflate(2131427503, viewGroup, false));
            }
            return new b(LayoutInflater.from(viewGroup.getContext()).inflate(2131427504, viewGroup, false));
        }

        public void b(RecyclerView.v vVar, int i) {
            if (vVar instanceof C0000a) {
                C0000a aVar = (C0000a) vVar;
                if (MainDrawer.this.f.a()) {
                    aVar.u.setVisibility(0);
                    aVar.t.setVisibility(0);
                    if (MainDrawer.this.f.g().isEmpty()) {
                        aVar.u.setText(MainDrawer.this.f.e());
                    } else {
                        aVar.u.setText(MainDrawer.this.f.g());
                    }
                    if (MainDrawer.this.f.m()) {
                        aVar.t.setImageResource(2131165612);
                    } else if (MainDrawer.this.f.n()) {
                        aVar.t.setImageResource(2131165651);
                    } else {
                        aVar.t.setVisibility(8);
                    }
                } else {
                    aVar.u.setVisibility(8);
                    aVar.t.setVisibility(8);
                }
            } else if (vVar instanceof b) {
                b bVar = (b) vVar;
                bVar.t.setImageResource(b.values()[i > 0 ? i - 1 : i].d());
                TextView textView = bVar.u;
                b[] values = b.values();
                if (i > 0) {
                    i--;
                }
                textView.setText(values[i].e());
            }
        }
    }

    public final void a() {
        try {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(this.a.getString(2131624004)));
            intent.setFlags(536870912);
            intent.addFlags(1);
            intent.addFlags(2);
            intent.addFlags(64);
            this.a.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            this.a.startActivity(Intent.createChooser(new Intent("android.intent.action.VIEW", Uri.parse(this.a.getString(2131624004))), xB.b().a(getContext(), 2131624976)));
        }
    }

    public void changeLogDialog(Context context) {
        toabout.setClass(this, AboutModActivity.class);
        this.startActivity();
    }
}
