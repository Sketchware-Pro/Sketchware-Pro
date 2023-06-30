package com.besome.sketch.editor.event;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

import a.a.a.wB;
import a.a.a.xB;

public class CollapsibleEventLayout extends FrameLayout {
    public Context a;
    public View b;
    public View c;
    public TextView d;
    public TextView e;
    public LinearLayout f;
    public LinearLayout g;
    public View h;
    public TextView i;
    public AnimatorSet j;
    public AnimatorSet k;
    public AnimatorSet l;
    public AnimatorSet m;
    public CollapsibleButton n;
    public CollapsibleButton o;
    public CollapsibleButton p;

    public CollapsibleEventLayout(Context context) {
        super(context);
        a(context);
    }

    public final void a(Context context) {
        this.a = context;
        wB.a(context, this, R.layout.fr_logic_list_item_buttons);
        this.g = findViewById(R.id.project_buttons);
        this.f = findViewById(R.id.ll_warning);
        this.h = findViewById(R.id.confirm_layout);
        this.b = findViewById(R.id.confirm_yes);
        this.c = findViewById(R.id.confirm_no);
        this.i = findViewById(R.id.tv_warning_message);
        this.d = findViewById(R.id.confirm_yes_text);
        this.d.setText(xB.b().a(getContext(), R.string.common_word_continue));
        this.e = findViewById(R.id.confirm_no_text);
        this.e.setText(xB.b().a(getContext(), R.string.common_word_cancel));
        this.h.setVisibility(INVISIBLE);
        this.f.setVisibility(GONE);
        this.i.setText(xB.b().a(getContext(), R.string.common_message_confirm));
        this.n = a(0, R.drawable.ic_reset_color_32dp, xB.b().a(context, R.string.common_word_reset));
        this.o = a(1, R.drawable.delete_96, xB.b().a(context, R.string.common_word_delete));
        this.p = a(2, R.drawable.ic_bookmark_red_48dp, xB.b().a(context, R.string.logic_list_menu_add_to_collection));
        this.p.setVisibility(GONE);
        this.g.addView(this.n);
        this.g.addView(this.o);
        this.g.addView(this.p);
        this.j = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_in);
        this.k = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_out);
        this.l = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_in);
        this.m = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_out);
    }

    public void b() {
        this.o.setVisibility(GONE);
    }

    public void c() {
        this.p.setVisibility(GONE);
    }

    public void d() {
        this.k.setTarget(this.g);
        this.j.setTarget(this.h);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.k).with(this.j);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                o.setEnabled(false);
                c.setEnabled(true);
                g.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                f.setVisibility(VISIBLE);
                h.setVisibility(VISIBLE);
                o.setEnabled(false);
                c.setEnabled(false);
            }
        });
        animatorSet.start();
    }

    public void e() {
        this.o.setVisibility(VISIBLE);
    }

    public void f() {
        this.p.setVisibility(VISIBLE);
    }

    public void setButtonOnClickListener(View.OnClickListener onClickListener) {
        this.b.setOnClickListener(onClickListener);
        this.c.setOnClickListener(onClickListener);
        this.n.setOnClickListener(onClickListener);
        this.o.setOnClickListener(onClickListener);
        this.p.setOnClickListener(onClickListener);
    }

    public final CollapsibleButton a(int i, int i2, String str) {
        CollapsibleButton collapsibleButton = new CollapsibleButton(this.a);
        collapsibleButton.b = i;
        collapsibleButton.d.setImageResource(i2);
        collapsibleButton.e.setText(str);
        return collapsibleButton;
    }

    public void a() {
        this.l.setTarget(this.g);
        this.m.setTarget(this.h);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.l).with(this.m);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                o.setEnabled(true);
                c.setEnabled(false);
                h.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                f.setVisibility(GONE);
                g.setVisibility(VISIBLE);
                o.setEnabled(false);
                c.setEnabled(false);
            }
        });
        animatorSet.start();
    }
}
