package com.besome.sketch.beans;

import a.a.a.nA;

public class QuizBean extends nA {
    public static final int QUIZ_TYPE_ANSWER = 2;
    public static final int QUIZ_TYPE_OX = 1;

    public static final int QUIZ_TRUE = 1;
    public static final int QUIZ_FALSE = 0;
    public static final int QUIZ_ANSWER_A = 0;
    public static final int QUIZ_ANSWER_B = 1;

    public int answer;
    public String answerA;
    public String answerB;
    public String question;
    public int type;

    public QuizBean(String question, int answer) {
        this.type = QUIZ_TYPE_OX;
        this.question = question;
        this.answer = answer;
    }

    public QuizBean(String question, int answer, String answerA, String answerB) {
        this.type = QUIZ_TYPE_ANSWER;
        this.question = question;
        this.answer = answer;
        this.answerA = answerA;
        this.answerB = answerB;
    }
}
