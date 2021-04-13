package com.besome.sketch.beans;

import a.a.a.nA;

public class QuizBean extends nA {
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

    public QuizBean(String str, int i) {
        this.type = 1;
        this.question = str;
        this.answer = i;
    }

    public QuizBean(String str, int i, String str2, String str3) {
        this.type = 2;
        this.question = str;
        this.answer = i;
        this.answerA = str2;
        this.answerB = str3;
    }
}
