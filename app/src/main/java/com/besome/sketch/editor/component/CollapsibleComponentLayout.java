package com.besome.sketch.editor.component;

import a.a.a.wB;
import a.a.a.xB;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.besome.sketch.editor.event.CollapsibleButton;
import com.sketchware.remod.R;

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

    public final void a(Context context) {
        this.a = context;
        wB.a(context, this, R.layout.fr_logic_list_item_buttons);
        this.f = (LinearLayout) findViewById(R.id.project_buttons);
        this.h = (LinearLayout) findViewById(R.id.ll_warning);
        this.g = findViewById(R.id.confirm_layout);
        this.b = findViewById(R.id.confirm_yes);
        this.c = findViewById(R.id.confirm_no);
        this.i = (TextView) findViewById(R.id.tv_warning_message);
        this.d = (TextView) findViewById(R.id.confirm_yes_text);
        this.d.setText(xB.b().a(getContext(), R.string.common_word_continue));
        this.e = (TextView) findViewById(R.id.confirm_no_text);
        this.e.setText(xB.b().a(getContext(), R.string.common_word_cancel));
        this.g.setVisibility(INVISIBLE);
        this.h.setVisibility(GONE);
        this.i.setText(xB.b().a(getContext(), R.string.common_message_confirm));
        this.n = a(0, R.drawable.delete_96, xB.b().a(context, R.string.common_word_delete));
        this.f.addView(this.n);
        this.j = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_in);
        this.k = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_out);
        this.l = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_in);
        this.m = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_out);
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
                f.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                g.setVisibility(GONE);
                h.setVisibility(GONE);
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

    public final CollapsibleButton a(int i, int i2, String str) {
        CollapsibleButton collapsibleButton = new CollapsibleButton(this.a);
        collapsibleButton.b = i;
        collapsibleButton.d.setImageResource(i2);
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
                g.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                h.setVisibility(GONE);
                f.setVisibility(VISIBLE);
                n.setEnabled(false);
                c.setEnabled(false);
            }
        });
        animatorSet.start();
    }
}
