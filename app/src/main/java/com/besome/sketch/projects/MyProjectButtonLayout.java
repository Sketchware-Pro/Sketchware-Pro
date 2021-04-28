package com.besome.sketch.projects;

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
        d.setOnClickListener(onClickListener);
        e.setOnClickListener(onClickListener);
        m.setOnClickListener(onClickListener);
        n.setOnClickListener(onClickListener);
        o.setOnClickListener(onClickListener);
        p.setOnClickListener(onClickListener);
        q.setOnClickListener(onClickListener);
    }

    public final void a(Context context) {
        a = context;
        wB.a(context, this, Resources.layout.myproject_buttons);
        h = findViewById(Resources.id.project_buttons);
        b = findViewById(Resources.id.confirm_layout);
        c = findViewById(Resources.id.confirm_title);
        c.setText(xB.b().a(getContext(), Resources.string.language_message_confirm_delete));
        d = findViewById(Resources.id.confirm_yes);
        e = findViewById(Resources.id.confirm_no);
        f = findViewById(Resources.id.confirm_yes_text);
        f.setText(xB.b().a(getContext(), Resources.string.common_word_delete));
        g = findViewById(Resources.id.confirm_no_text);
        g.setText(xB.b().a(getContext(), Resources.string.common_word_cancel));
        c.setText(xB.b().a(context, Resources.string.myprojects_confirm_project_delete));
        f.setText(xB.b().a(context, Resources.string.common_word_delete));
        g.setText(xB.b().a(context, Resources.string.common_word_cancel));
        b.setVisibility(INVISIBLE);
        m = a(0,
                Resources.drawable.settings_96,
                xB.b().a(context, Resources.string.myprojects_list_menu_title_settings));
        n = a(1,
                Resources.drawable.ic_backup,
                "Back up");
        o = a(2,
                Resources.drawable.ic_export_grey_48dp,
                xB.b().a(context, Resources.string.myprojects_list_menu_title_sign_export));
        p = a(3,
                Resources.drawable.ic_delete_grey_48dp,
                xB.b().a(context, Resources.string.myprojects_list_menu_title_delete));
        q = a(4,
                Resources.drawable.settings_96,
                "Config");
        h.addView(m);
        h.addView(n);
        h.addView(o);
        h.addView(p);
        h.addView(q);
        i = (AnimatorSet) AnimatorInflater.loadAnimator(context, Resources.animator.flip_top_in);
        j = (AnimatorSet) AnimatorInflater.loadAnimator(context, Resources.animator.flip_top_out);
        k = (AnimatorSet) AnimatorInflater.loadAnimator(context, Resources.animator.flip_bottom_in);
        l = (AnimatorSet) AnimatorInflater.loadAnimator(context, Resources.animator.flip_bottom_out);
    }

    public void b() {
        j.setTarget(h);
        i.setTarget(b);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(j).with(i);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                p.setEnabled(false);
                e.setEnabled(true);
                h.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                b.setVisibility(VISIBLE);
                p.setEnabled(false);
                e.setEnabled(false);
            }
        });
        animatorSet.start();
    }

    public final MyProjectButton a(int i2, int i3, String str) {
        MyProjectButton myProjectButton = new MyProjectButton(a);
        myProjectButton.b = i2;
        myProjectButton.d.setImageResource(i3);
        myProjectButton.e.setText(str);
        return myProjectButton;
    }

    public void a() {
        k.setTarget(h);
        l.setTarget(b);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(k).with(l);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                p.setEnabled(true);
                e.setEnabled(false);
                b.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                h.setVisibility(VISIBLE);
                p.setEnabled(false);
                e.setEnabled(false);
            }
        });
        animatorSet.start();
    }
}
