package com.besome.sketch.beans;

import com.besome.sketch.lib.base.BaseBean;

public class QuizBean extends BaseBean {
    public static final int QUIZ_TYPE_ANSWER = 2;
    public static final int QUIZ_TYPE_INFO = 0;
    public static final int QUIZ_TYPE_OX = 1;
    public int answer;
    public String answerA;
    public String answerB;
    public String question;
    public int type;

    public QuizBean() {
    }

    public QuizBean(String question, int answer) {
        type = QUIZ_TYPE_OX;
        this.question = question;
        this.answer = answer;
    }

    public QuizBean(String question, int answer, String answerA, String answerB) {
        type = QUIZ_TYPE_ANSWER;
        this.question = question;
        this.answer = answer;
        this.answerA = answerA;
        this.answerB = answerB;
    }
}
