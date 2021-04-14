package com.besome.sketch.editor.component;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besome.sketch.editor.event.CollapsibleButton;

import a.a.a.wB;
import a.a.a.xB;

public class CollapsibleComponentLayout extends FrameLayout {
    public Context a;
    public View b;
    public View c;
    public TextView d;
    public TextView e;
    public LinearLayout f;
    public View g;
    public LinearLayout h;
    public TextView i;
    public AnimatorSet j;
    public AnimatorSet k;
    public AnimatorSet l;
    public AnimatorSet m;
    public CollapsibleButton n;

    public CollapsibleComponentLayout(Context context) {
        super(context);
        a(context);
    }

    @SuppressLint("ResourceType")
    public final void a(Context context) {
        this.a = context;
        wB.a(context, this, 2131427432);
        this.f = (LinearLayout) findViewById(2131231612);
        this.h = (LinearLayout) findViewById(2131231479);
        this.g = findViewById(2131230922);
        this.b = findViewById(2131230927);
        this.c = findViewById(2131230923);
        this.i = (TextView) findViewById(2131232287);
        this.d = (TextView) findViewById(2131230929);
        this.d.setText(xB.b().a(getContext(), 2131624980));
        this.e = (TextView) findViewById(2131230925);
        this.e.setText(xB.b().a(getContext(), 2131624974));
        this.g.setVisibility(4);
        this.h.setVisibility(8);
        this.i.setText(xB.b().a(getContext(), 2131624941));
        this.n = a(0, 2131165524, xB.b().a(context, 2131624986));
        this.f.addView(this.n);
        this.j = (AnimatorSet) AnimatorInflater.loadAnimator(context, 2130837513);
        this.k = (AnimatorSet) AnimatorInflater.loadAnimator(context, 2130837514);
        this.l = (AnimatorSet) AnimatorInflater.loadAnimator(context, 2130837507);
        this.m = (AnimatorSet) AnimatorInflater.loadAnimator(context, 2130837508);
    }

    public void b() {
        this.k.setTarget(this.f);
        this.j.setTarget(this.g);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.k).with(this.j);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                n.setEnabled(false);
                c.setEnabled(true);
                f.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                g.setVisibility(View.VISIBLE);
                h.setVisibility(View.VISIBLE);
                n.setEnabled(false);
                c.setEnabled(false);
            }
        });
        animatorSet.start();
    }

    public void setButtonOnClickListener(View.OnClickListener onClickListener) {
        this.b.setOnClickListener(onClickListener);
        this.c.setOnClickListener(onClickListener);
        this.n.setOnClickListener(onClickListener);
    }

    public final CollapsibleButton a(int i2, int i3, String str) {
        CollapsibleButton collapsibleButton = new CollapsibleButton(this.a);
        collapsibleButton.b = i2;
        collapsibleButton.d.setImageResource(i3);
        collapsibleButton.e.setText(str);
        return collapsibleButton;
    }

    public void a() {
        this.l.setTarget(this.f);
        this.m.setTarget(this.g);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.l).with(this.m);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                n.setEnabled(true);
                c.setEnabled(false);
                setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                h.setVisibility(View.GONE);
                f.setVisibility(View.VISIBLE);
                n.setEnabled(false);
                c.setEnabled(false);
            }
        });
        animatorSet.start();
    }
}
