package com.besome.sketch.tools;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.besome.sketch.beans.QuizBean;
import com.sketchware.remod.R;
import com.sketchware.remod.databinding.QuizBoardBinding;

import java.util.ArrayList;
import java.util.Random;

import a.a.a.mB;
import a.a.a.tq;
import a.a.a.wB;

public class QuizBoard extends LinearLayout implements View.OnClickListener {

    private ArrayList<QuizBean> q;
    private QuizBean r;
    private a s;
    private QuizBoardBinding quizBinding;

    public QuizBoard(Context var1) {
        super(var1);
        initialize(var1);
    }

    private void setData(QuizBean quizBean) {
        r = quizBean;
        quizBinding.tvQuestion.setText(quizBean.question);
        int var2 = quizBean.type;
        if (var2 == 1) {
            quizBinding.layoutAnswerOx.setVisibility(View.VISIBLE);
            quizBinding.imgAnswerO.setVisibility(View.VISIBLE);
            quizBinding.imgAnswerX.setVisibility(View.VISIBLE);
            mB.a(quizBinding.imgAnswerO, 1);
            mB.a(quizBinding.imgAnswerX, 1);
            quizBinding.imgAnswerO.setOnClickListener(this);
            quizBinding.imgAnswerX.setOnClickListener(this);
            quizBinding.layoutAnswerAb.setVisibility(View.GONE);
        } else if (var2 == 2) {
            quizBinding.layoutAnswerAb.setVisibility(View.VISIBLE);
            quizBinding.viewAnswerA.setOnClickListener(this);
            quizBinding.viewAnswerB.setOnClickListener(this);
            quizBinding.tvAnswerA.setText(quizBean.answerA);
            quizBinding.tvAnswerB.setText(quizBean.answerB);
            quizBinding.layoutAnswerOx.setVisibility(View.GONE);
        }
    }

    private void setTimeoutProgress(int var1) {
        int var2 = var1 / 250;
        var1 = quizBinding.timeoutBar.getChildCount();

        while (true) {
            --var1;
            if (var1 < var2) {
                return;
            }

            quizBinding.timeoutBar.getChildAt(var1).setBackgroundColor(0xffeeeeee);
        }
    }

    public void a() {
        if (s != null) {
            s.cancel();
            s = null;
        }
    }

    private void initialize(Context var1) {
        wB.a(var1, this, R.layout.quiz_board);
        quizBinding = QuizBoardBinding.inflate(((Activity) getContext()).getLayoutInflater(), this, true);
        g();
    }

    public void b() {
        ArrayList<QuizBean> var2 = q;
        if (var2 == null || var2.isEmpty()) {
            q = tq.a();
        }

        int var1 = (new Random()).nextInt(q.size());
        setData((QuizBean) q.remove(var1));
        e();
    }

    public final void c() {
        quizBinding.imgAnswerO.setOnClickListener((View.OnClickListener) null);
        quizBinding.imgAnswerX.setOnClickListener((View.OnClickListener) null);
        quizBinding.viewAnswerA.setOnClickListener((View.OnClickListener) null);
        quizBinding.viewAnswerB.setOnClickListener((View.OnClickListener) null);
    }

    public final void d() {
        quizBinding.imgAnswerO.setTranslationX(wB.a(getContext(), -50.0F));
        quizBinding.imgAnswerO.setAlpha(1.0F);
        quizBinding.imgAnswerO.setScaleX(0.9F);
        quizBinding.imgAnswerO.setScaleY(0.9F);
        quizBinding.imgAnswerX.setTranslationX(wB.a(getContext(), 50.0F));
        quizBinding.imgAnswerX.setAlpha(1.0F);
        quizBinding.imgAnswerX.setScaleX(0.9F);
        quizBinding.imgAnswerX.setScaleY(0.9F);
        quizBinding.imgAnswerA.setScaleX(0.0F);
        quizBinding.imgAnswerA.setScaleY(0.0F);
        quizBinding.imgAnswerA.setAlpha(0.0F);
        quizBinding.imgAnswerB.setScaleX(0.0F);
        quizBinding.imgAnswerB.setScaleY(0.0F);
        quizBinding.imgAnswerB.setAlpha(0.0F);
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

                    mB.a(quizBinding.imgAnswerO, 0);
                    mB.a(quizBinding.imgAnswerX, 1);
                    quizBinding.imgAnswerX.animate().scaleX(1.0F).scaleY(1.0F).translationX(0.0F).alpha(1.0F).start();
                    var3 = quizBinding.imgAnswerO;
                } else {
                    mB.a(quizBinding.imgAnswerO, 1);
                    mB.a(quizBinding.imgAnswerX, 0);
                    quizBinding.imgAnswerO.animate().scaleX(1.0F).scaleY(1.0F).translationX(0.0F).alpha(1.0F).start();
                    var3 = quizBinding.imgAnswerX;
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

                    var3 = quizBinding.imgAnswerB;
                } else {
                    var3 = quizBinding.imgAnswerA;
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
            int id = var1.getId();
            if (id == R.id.img_answer_o || id == R.id.img_answer_x
                    || id == R.id.view_answer_a || id == R.id.view_answer_b) {
                f();
            }
            if (r.type == 2) {
                var1.getId();
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
            (new Handler()).post(() -> quizBinding.tvRemaingTime.setText(String.valueOf(millisUntilFinished / 1000L + 1L)));
        }
    }
}
