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
        a = context;
        wB.a(context, this, R.layout.fr_logic_list_item_buttons);
        f = findViewById(R.id.project_buttons);
        h = findViewById(R.id.ll_warning);
        g = findViewById(R.id.confirm_layout);
        b = findViewById(R.id.confirm_yes);
        c = findViewById(R.id.confirm_no);
        i = findViewById(R.id.tv_warning_message);
        d = findViewById(R.id.confirm_yes_text);
        d.setText(xB.b().a(getContext(), R.string.common_word_continue));
        e = findViewById(R.id.confirm_no_text);
        e.setText(xB.b().a(getContext(), R.string.common_word_cancel));
        g.setVisibility(INVISIBLE);
        h.setVisibility(GONE);
        i.setText(xB.b().a(getContext(), R.string.common_message_confirm));
        n = a(0, R.drawable.delete_96, xB.b().a(context, R.string.common_word_delete));
        f.addView(n);
        j = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_in);
        k = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_out);
        l = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_in);
        m = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_out);
    }

    public void b() {
        k.setTarget(f);
        j.setTarget(g);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(k).with(j);
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
        b.setOnClickListener(onClickListener);
        c.setOnClickListener(onClickListener);
        n.setOnClickListener(onClickListener);
    }

    public final CollapsibleButton a(int i, int i2, String str) {
        CollapsibleButton collapsibleButton = new CollapsibleButton(a);
        collapsibleButton.b = i;
        collapsibleButton.d.setImageResource(i2);
        collapsibleButton.e.setText(str);
        return collapsibleButton;
    }

    public void a() {
        l.setTarget(f);
        m.setTarget(g);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(l).with(m);
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
