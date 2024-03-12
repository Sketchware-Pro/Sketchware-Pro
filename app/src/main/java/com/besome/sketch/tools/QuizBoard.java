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

import a.a.a.mB;
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
        a(var1);
    }

    private void setData(QuizBean var1) {
        r = var1;
        e.setText(var1.question);
        int var2 = var1.type;
        if (var2 == 1) {
            f.setVisibility(0);
            g.setVisibility(0);
            h.setVisibility(0);
            mB.a(g, 1);
            mB.a(h, 1);
            g.setOnClickListener(this);
            h.setOnClickListener(this);
            i.setVisibility(8);
        } else if (var2 == 2) {
            i.setVisibility(0);
            j.setOnClickListener(this);
            k.setOnClickListener(this);
            l.setText(var1.answerA);
            m.setText(var1.answerB);
            f.setVisibility(8);
        }

    }

    private void setTimeoutProgress(int var1) {
        int var2 = var1 / 250;
        var1 = p.getChildCount();

        while (true) {
            --var1;
            if (var1 < var2) {
                return;
            }

            p.getChildAt(var1).setBackgroundColor(-1118482);
        }
    }

    public void a() {
        a var1 = s;
        if (var1 != null) {
            var1.cancel();
            s = null;
        }

    }

    public final void a(Context var1) {
        wB.a(var1, this, layout.quiz_board);
        d = (TextView) findViewById(id.tv_remaing_time);
        e = (TextView) findViewById(id.tv_question);
        p = (LinearLayout) findViewById(id.timeout_bar);
        f = (RelativeLayout) findViewById(id.layout_answer_ox);
        g = (ImageView) findViewById(id.img_answer_o);
        h = (ImageView) findViewById(id.img_answer_x);
        i = (LinearLayout) findViewById(id.layout_answer_ab);
        j = findViewById(id.view_answer_a);
        k = findViewById(id.view_answer_b);
        l = (TextView) findViewById(id.tv_answer_a);
        m = (TextView) findViewById(id.tv_answer_b);
        n = (ImageView) findViewById(id.img_answer_a);
        o = (ImageView) findViewById(id.img_answer_b);
        g();
    }

    public void b() {
        ArrayList var2 = q;
        if (var2 == null || var2.size() <= 0) {
            q = tq.a();
        }

        int var1 = (new Random()).nextInt(q.size());
        setData((QuizBean) q.remove(var1));
        e();
    }

    public final void c() {
        g.setOnClickListener((View.OnClickListener) null);
        h.setOnClickListener((View.OnClickListener) null);
        j.setOnClickListener((View.OnClickListener) null);
        k.setOnClickListener((View.OnClickListener) null);
    }

    public final void d() {
        g.setTranslationX(wB.a(getContext(), -50.0F));
        g.setAlpha(1.0F);
        g.setScaleX(0.9F);
        g.setScaleY(0.9F);
        h.setTranslationX(wB.a(getContext(), 50.0F));
        h.setAlpha(1.0F);
        h.setScaleX(0.9F);
        h.setScaleY(0.9F);
        n.setScaleX(0.0F);
        n.setScaleY(0.0F);
        n.setAlpha(0.0F);
        o.setScaleX(0.0F);
        o.setScaleY(0.0F);
        o.setAlpha(0.0F);
    }

    public final void e() {
        a var1 = s;
        if (var1 != null) {
            var1.cancel();
        }

        s = null;
        var1 = new a(this, 15000L, 250L);
        s = var1;
        var1.start();
    }

    public void f() {
        label32:
        {
            QuizBean var2 = r;
            int var1 = var2.type;
            ImageView var3;
            ViewPropertyAnimator var4;
            if (var1 == 1) {
                var1 = var2.answer;
                if (var1 != 0) {
                    if (var1 != 1) {
                        break label32;
                    }

                    mB.a(g, 0);
                    mB.a(h, 1);
                    h.animate().scaleX(1.0F).scaleY(1.0F).translationX(0.0F).alpha(1.0F).start();
                    var3 = g;
                } else {
                    mB.a(g, 1);
                    mB.a(h, 0);
                    g.animate().scaleX(1.0F).scaleY(1.0F).translationX(0.0F).alpha(1.0F).start();
                    var3 = h;
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

                    var3 = o;
                } else {
                    var3 = n;
                }

                var4 = var3.animate().scaleX(1.0F).scaleY(1.0F).alpha(1.0F);
            }

            var4.start();
        }

        c();
        (new Handler()).postDelayed(() -> {
            d();
            b();
        }, 2000L);
    }

    public void g() {
        d();
        b();
    }

    public void onClick(View var1) {
        if (!mB.a()) {
            a();
            switch (var1.getId()) {
                case 2131231109:
                case 2131231110:
                case 2131232314:
                case 2131232315:
                    f();
                default:
                    if (r.type == 2) {
                        var1.getId();
                    }

            }
        }
    }

    public class a extends CountDownTimer {
        public final QuizBoard a;

        public a(QuizBoard var1, long var2, long var4) {
            super(var2, var4);
            a = var1;
        }

        public void onFinish() {
            a.f();
        }

        public void onTick(long millisUntilFinished) {
            (new Handler()).post(() -> {
                if (d != null) {
                    d.setText(String.valueOf(millisUntilFinished / 1000L + 1L));
                }
            });
        }
    }
}
