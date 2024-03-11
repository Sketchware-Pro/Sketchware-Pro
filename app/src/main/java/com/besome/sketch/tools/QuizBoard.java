//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.besome.sketch.tools;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.besome.sketch.beans.QuizBean;
import com.sketchware.remod.R.id;
import com.sketchware.remod.R.layout;

import java.util.ArrayList;
import java.util.Random;

import a.a.a.lI;
import a.a.a.mB;
import a.a.a.mI;
import a.a.a.tq;
import a.a.a.wB;

public class QuizBoard extends LinearLayout implements View.OnClickListener {
    public final int a = 15000;
    public final int b = 250;
    public final int c = 2000;
    public TextView d;
    public TextView e;
    public RelativeLayout f;
    public ImageView g;
    public ImageView h;
    public LinearLayout i;
    public View j;
    public View k;
    public TextView l;
    public TextView m;
    public ImageView n;
    public ImageView o;
    public LinearLayout p;
    public ArrayList<QuizBean> q;
    public QuizBean r;
    public a s;

    public QuizBoard(Context var1) {
        super(var1);
        this.a(var1);
    }

    private void setData(QuizBean var1) {
        this.r = var1;
        this.e.setText(var1.question);
        int var2 = var1.type;
        if (var2 == 1) {
            this.f.setVisibility(0);
            this.g.setVisibility(0);
            this.h.setVisibility(0);
            mB.a(this.g, 1);
            mB.a(this.h, 1);
            this.g.setOnClickListener(this);
            this.h.setOnClickListener(this);
            this.i.setVisibility(8);
        } else if (var2 == 2) {
            this.i.setVisibility(0);
            this.j.setOnClickListener(this);
            this.k.setOnClickListener(this);
            this.l.setText(var1.answerA);
            this.m.setText(var1.answerB);
            this.f.setVisibility(8);
        }

    }

    private void setTimeoutProgress(int var1) {
        int var2 = var1 / 250;
        var1 = this.p.getChildCount();

        while (true) {
            --var1;
            if (var1 < var2) {
                return;
            }

            this.p.getChildAt(var1).setBackgroundColor(-1118482);
        }
    }

    public void a() {
        a var1 = this.s;
        if (var1 != null) {
            var1.cancel();
            this.s = null;
        }

    }

    public final void a(Context var1) {
        wB.a(var1, this, layout.quiz_board);
        this.d = (TextView) this.findViewById(id.tv_remaing_time);
        this.e = (TextView) this.findViewById(id.tv_question);
        this.p = (LinearLayout) this.findViewById(id.timeout_bar);
        this.f = (RelativeLayout) this.findViewById(id.layout_answer_ox);
        this.g = (ImageView) this.findViewById(id.img_answer_o);
        this.h = (ImageView) this.findViewById(id.img_answer_x);
        this.i = (LinearLayout) this.findViewById(id.layout_answer_ab);
        this.j = this.findViewById(id.view_answer_a);
        this.k = this.findViewById(id.view_answer_b);
        this.l = (TextView) this.findViewById(id.tv_answer_a);
        this.m = (TextView) this.findViewById(id.tv_answer_b);
        this.n = (ImageView) this.findViewById(id.img_answer_a);
        this.o = (ImageView) this.findViewById(id.img_answer_b);
        this.g();
    }

    public void b() {
        ArrayList var2 = this.q;
        if (var2 == null || var2.size() <= 0) {
            this.q = tq.a();
        }

        int var1 = (new Random()).nextInt(this.q.size());
        this.setData((QuizBean) this.q.remove(var1));
        this.e();
    }

    public final void c() {
        this.g.setOnClickListener((View.OnClickListener) null);
        this.h.setOnClickListener((View.OnClickListener) null);
        this.j.setOnClickListener((View.OnClickListener) null);
        this.k.setOnClickListener((View.OnClickListener) null);
    }

    public final void d() {
        this.g.setTranslationX(wB.a(this.getContext(), -50.0F));
        this.g.setAlpha(1.0F);
        this.g.setScaleX(0.9F);
        this.g.setScaleY(0.9F);
        this.h.setTranslationX(wB.a(this.getContext(), 50.0F));
        this.h.setAlpha(1.0F);
        this.h.setScaleX(0.9F);
        this.h.setScaleY(0.9F);
        this.n.setScaleX(0.0F);
        this.n.setScaleY(0.0F);
        this.n.setAlpha(0.0F);
        this.o.setScaleX(0.0F);
        this.o.setScaleY(0.0F);
        this.o.setAlpha(0.0F);
    }

    public final void e() {
        a var1 = this.s;
        if (var1 != null) {
            var1.cancel();
        }

        this.s = null;
        var1 = new a(this, 15000L, 250L);
        this.s = var1;
        var1.start();
    }

    public void f() {
        label32:
        {
            QuizBean var2 = this.r;
            int var1 = var2.type;
            ImageView var3;
            ViewPropertyAnimator var4;
            if (var1 == 1) {
                var1 = var2.answer;
                if (var1 != 0) {
                    if (var1 != 1) {
                        break label32;
                    }

                    mB.a(this.g, 0);
                    mB.a(this.h, 1);
                    this.h.animate().scaleX(1.0F).scaleY(1.0F).translationX(0.0F).alpha(1.0F).start();
                    var3 = this.g;
                } else {
                    mB.a(this.g, 1);
                    mB.a(this.h, 0);
                    this.g.animate().scaleX(1.0F).scaleY(1.0F).translationX(0.0F).alpha(1.0F).start();
                    var3 = this.h;
                }

                var4 = var3.animate().scaleX(1.0F).scaleY(1.0F).translationX(0.0F).alpha(0.0F);
            } else {
                if (var1 != 2) {
                    break label32;
                }

                var1 = var2.answer;
                if (var1 != 0) {
                    if (var1 != 1) {
                        break label32;
                    }

                    var3 = this.o;
                } else {
                    var3 = this.n;
                }

                var4 = var3.animate().scaleX(1.0F).scaleY(1.0F).alpha(1.0F);
            }

            var4.start();
        }

        this.c();
        (new Handler()).postDelayed(new lI(this), 2000L);
    }

    public void g() {
        this.d();
        this.b();
    }

    public void onClick(View var1) {
        if (!mB.a()) {
            this.a();
            switch (var1.getId()) {
                case 2131231109:
                case 2131231110:
                case 2131232314:
                case 2131232315:
                    this.f();
                default:
                    if (this.r.type == 2) {
                        var1.getId();
                    }

            }
        }
    }

    public class a extends CountDownTimer {
        public final QuizBoard a;

        public a(QuizBoard var1, long var2, long var4) {
            super(var2, var4);
            this.a = var1;
        }

        public void onFinish() {
            this.a.f();
        }

        public void onTick(long var1) {
            (new Handler()).post(new mI(this, var1));
        }
    }
}
