package com.besome.sketch.tools;

import static com.besome.sketch.beans.QuizBean.QUIZ_ANSWER_A;
import static com.besome.sketch.beans.QuizBean.QUIZ_TRUE;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.besome.sketch.beans.QuizBean;

import java.util.ArrayList;
import java.util.Random;

import a.a.a.mB;
import a.a.a.tq;
import pro.sketchware.R;
import pro.sketchware.databinding.QuizBoardBinding;

public class QuizBoard extends LinearLayout implements View.OnClickListener {

    private ArrayList<QuizBean> q;
    private QuizBean quizBean;
    private a s;
    private QuizBoardBinding quizBinding;

    public QuizBoard(Context context) {
        super(context);
        initialize(context);
    }

    private void setData(QuizBean quizBean) {
        this.quizBean = quizBean;
        quizBinding.tvQuestion.setText(quizBean.question);
        if (quizBean.type == 1) {
            quizBinding.layoutAnswerOx.setVisibility(View.VISIBLE);
            quizBinding.imgAnswerO.setVisibility(View.VISIBLE);
            quizBinding.imgAnswerX.setVisibility(View.VISIBLE);
            mB.a(quizBinding.imgAnswerO, 1);
            mB.a(quizBinding.imgAnswerX, 1);
            quizBinding.imgAnswerO.setOnClickListener(this);
            quizBinding.imgAnswerX.setOnClickListener(this);
            quizBinding.layoutAnswerAb.setVisibility(View.GONE);
        } else if (quizBean.type == 2) {
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

    private void initialize(Context context) {
        quizBinding = QuizBoardBinding.inflate(((Activity) context).getLayoutInflater(), this, true);
        g();
    }

    public void b() {
        if (q == null || q.isEmpty()) {
            q = tq.a();
        }

        int var1 = (new Random()).nextInt(q.size());
        setData(q.remove(var1));
        e();
    }

    public final void invalidateClickListeners() {
        quizBinding.imgAnswerO.setOnClickListener(null);
        quizBinding.imgAnswerX.setOnClickListener(null);
        quizBinding.viewAnswerA.setOnClickListener(null);
        quizBinding.viewAnswerB.setOnClickListener(null);
    }

    public final void resetQuizViews() {
        quizBinding.imgAnswerO.setVisibility(View.VISIBLE);
        quizBinding.imgAnswerX.setVisibility(View.VISIBLE);
        quizBinding.separator.setVisibility(View.VISIBLE);

        quizBinding.imgAnswerA.setVisibility(View.GONE);
        quizBinding.imgAnswerB.setVisibility(View.GONE);
    }

    private void setXOResult(int answer) {
        if (answer == QUIZ_TRUE) {
            mB.a(quizBinding.imgAnswerO, 1);
            mB.a(quizBinding.imgAnswerX, 0);
            quizBinding.imgAnswerX.setVisibility(View.GONE);
        } else {
            mB.a(quizBinding.imgAnswerO, 0);
            mB.a(quizBinding.imgAnswerX, 1);
            quizBinding.imgAnswerO.setVisibility(View.GONE);
        }
        quizBinding.separator.setVisibility(View.GONE);
    }

    private void setABResult(int answer) {
        if (answer == QUIZ_ANSWER_A) {
            quizBinding.imgAnswerA.setVisibility(View.VISIBLE);
        } else {
            quizBinding.imgAnswerB.setVisibility(View.VISIBLE);
        }
    }

    public final void e() {
        a var1 = s;
        if (var1 != null) {
            var1.cancel();
        }

        s = null;
        var1 = new a(15000L, 250L);
        s = var1;
        var1.start();
    }

    public void f() {
        QuizBean quizBean = this.quizBean;
        int quizType = quizBean.type;

        if (quizType == 1) {
            setXOResult(quizBean.answer);
        } else {
            setABResult(quizBean.answer);
        }

        invalidateClickListeners();
        (new Handler()).postDelayed(() -> {
            resetQuizViews();
            b();
        }, 2000); // ask next question after 2 secs
    }

    public void g() {
        resetQuizViews();
        b();
    }

    public void onClick(View var1) {
        if (!mB.a()) {
            a();
            int id = var1.getId();
            if (id == R.id.img_answer_o || id == R.id.img_answer_x || id == R.id.view_answer_a || id == R.id.view_answer_b) {
                f();
            }
            if (quizBean.type == 2) {
                var1.getId();
            }
        }
    }

    public class a extends CountDownTimer {
        public a(long var2, long var4) {
            super(var2, var4);
        }

        public void onFinish() {
            f();
        }

        public void onTick(long millisUntilFinished) {
            (new Handler()).post(() -> quizBinding.tvRemaingTime.setText(String.valueOf(millisUntilFinished / 1000L + 1L)));
        }
    }
}
