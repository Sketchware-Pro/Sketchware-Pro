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

import com.sketchware.remod.Resources;

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
        a = context;
        wB.a(context, this, Resources.layout.fr_logic_list_item_buttons);
        g = findViewById(Resources.id.project_buttons);
        f = findViewById(Resources.id.ll_warning);
        h = findViewById(Resources.id.confirm_layout);
        b = findViewById(Resources.id.confirm_yes);
        c = findViewById(Resources.id.confirm_no);
        i = findViewById(Resources.id.tv_warning_message);
        d = findViewById(Resources.id.confirm_yes_text);
        d.setText(xB.b().a(
                getContext(),
                Resources.string.common_word_continue
        ));
        e = findViewById(Resources.id.confirm_no_text);
        e.setText(xB.b().a(
                getContext(),
                Resources.string.common_word_cancel
        ));
        h.setVisibility(INVISIBLE);
        f.setVisibility(GONE);
        i.setText(xB.b().a(
                getContext(),
                Resources.string.common_message_confirm
        ));
        n = a(
                0,
                Resources.drawable.ic_reset_color_32dp,
                xB.b().a(
                        context,
                        Resources.string.common_word_reset
                )
        );
        o = a(
                1,
                Resources.drawable.delete_96,
                xB.b().a(
                        context,
                        Resources.string.common_word_delete
                )
        );
        p = a(
                2,
                Resources.drawable.ic_bookmark_red_48dp,
                xB.b().a(
                        context,
                        Resources.string.logic_list_menu_add_to_collection
                )
        );
        p.setVisibility(GONE);
        g.addView(n);
        g.addView(o);
        g.addView(p);
        j = (AnimatorSet) AnimatorInflater.loadAnimator(
                context,
                Resources.animator.flip_top_in
        );
        k = (AnimatorSet) AnimatorInflater.loadAnimator(
                context,
                Resources.animator.flip_top_out
        );
        l = (AnimatorSet) AnimatorInflater.loadAnimator(
                context,
                Resources.animator.flip_bottom_in
        );
        m = (AnimatorSet) AnimatorInflater.loadAnimator(
                context,
                Resources.animator.flip_bottom_out
        );
    }

    public void b() {
        o.setVisibility(GONE);
    }

    public void c() {
        p.setVisibility(GONE);
    }

    public void d() {
        k.setTarget(g);
        j.setTarget(h);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(k).with(j);
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
        o.setVisibility(VISIBLE);
    }

    public void f() {
        p.setVisibility(VISIBLE);
    }

    public void setButtonOnClickListener(View.OnClickListener onClickListener) {
        b.setOnClickListener(onClickListener);
        c.setOnClickListener(onClickListener);
        n.setOnClickListener(onClickListener);
        o.setOnClickListener(onClickListener);
        p.setOnClickListener(onClickListener);
    }

    public final CollapsibleButton a(int i, int imageResource, String text) {
        CollapsibleButton collapsibleButton = new CollapsibleButton(a);
        collapsibleButton.b = i;
        collapsibleButton.d.setImageResource(imageResource);
        collapsibleButton.e.setText(text);
        return collapsibleButton;
    }

    public void a() {
        l.setTarget(g);
        m.setTarget(h);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(l).with(m);
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
