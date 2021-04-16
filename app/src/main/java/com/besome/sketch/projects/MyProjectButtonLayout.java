package com.besome.sketch.projects;

import a.a.a.mC;
import a.a.a.nC;
import a.a.a.wB;
import a.a.a.xB;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyProjectButtonLayout extends FrameLayout {
    public Context a;
    public View b;
    public TextView c;
    public View d;
    public View e;
    public TextView f;
    public TextView g;
    public LinearLayout h;
    public AnimatorSet i;
    public AnimatorSet j;
    public AnimatorSet k;
    public AnimatorSet l;
    public MyProjectButton m;
    public MyProjectButton n;
    public MyProjectButton o;
    public MyProjectButton p;
    public MyProjectButton q;

    public MyProjectButtonLayout(Context context) {
        super(context);
        a(context);
    }

    public void setButtonOnClickListener(View.OnClickListener onClickListener) {
        this.d.setOnClickListener(onClickListener);
        this.e.setOnClickListener(onClickListener);
        this.m.setOnClickListener(onClickListener);
        this.n.setOnClickListener(onClickListener);
        this.o.setOnClickListener(onClickListener);
        this.p.setOnClickListener(onClickListener);
        this.q.setOnClickListener(onClickListener);
    }

    public final void a(Context context) {
        this.a = context;
        wB.a(context, this, 2131427581);
        this.h = (LinearLayout) findViewById(2131231612);
        this.b = findViewById(2131230922);
        this.c = (TextView) findViewById(2131230926);
        this.c.setText(xB.b().a(getContext(), 2131625466));
        this.d = findViewById(2131230927);
        this.e = findViewById(2131230923);
        this.f = (TextView) findViewById(2131230929);
        this.f.setText(xB.b().a(getContext(), 2131624986));
        this.g = (TextView) findViewById(2131230925);
        this.g.setText(xB.b().a(getContext(), 2131624974));
        this.c.setText(xB.b().a(context, 2131625636));
        this.f.setText(xB.b().a(context, 2131624986));
        this.g.setText(xB.b().a(context, 2131624974));
        this.b.setVisibility(4);
        this.m = a(0, 2131166124, xB.b().a(context, 2131625666));
        xB.b().a(context, 2131625665);
        this.n = a(1, 2131166383, "Backup");
        this.o = a(2, 2131165749, xB.b().a(context, 2131625669));
        this.p = a(3, 2131165728, xB.b().a(context, 2131625663));
        this.q = a(4, 2131166124, "Config");
        this.h.addView(this.m);
        this.h.addView(this.n);
        this.h.addView(this.o);
        this.h.addView(this.p);
        this.h.addView(this.q);
        this.i = (AnimatorSet) AnimatorInflater.loadAnimator(context, 2130837513);
        this.j = (AnimatorSet) AnimatorInflater.loadAnimator(context, 2130837514);
        this.k = (AnimatorSet) AnimatorInflater.loadAnimator(context, 2130837507);
        this.l = (AnimatorSet) AnimatorInflater.loadAnimator(context, 2130837508);
    }

    public void b() {
        this.j.setTarget(this.h);
        this.i.setTarget(this.b);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.j).with(this.i);
        animatorSet.addListener(new mC(this));
        animatorSet.start();
    }

    public final MyProjectButton a(int i2, int i3, String str) {
        MyProjectButton myProjectButton = new MyProjectButton(this.a);
        myProjectButton.b = i2;
        myProjectButton.d.setImageResource(i3);
        myProjectButton.e.setText(str);
        return myProjectButton;
    }

    public void a() {
        this.k.setTarget(this.h);
        this.l.setTarget(this.b);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.k).with(this.l);
        animatorSet.addListener(new nC(this));
        animatorSet.start();
    }
}
