package a.a.a;

import com.besome.sketch.beans.QuizBean;

import java.util.ArrayList;

public class tq {
    public static ArrayList<QuizBean> a;

    public static ArrayList<QuizBean> a() {
        if (a == null) {
            a = new ArrayList<>();
        }

        // X-O Questions
        a.add(new QuizBean("Gyroscope component is supported on every device.", QuizBean.QUIZ_FALSE));
        a.add(new QuizBean("What programming language does Sketchware primarily use for logic?", QuizBean.QUIZ_ANSWER_B, "Python", "Java"));
        a.add(new QuizBean("How do you display a message to the user in Sketchware?\n", QuizBean.QUIZ_ANSWER_A, "Toast.show()", "System.out.println()"));
        a.add(new QuizBean("What’s the first step to save user data in Sketchware?", QuizBean.QUIZ_ANSWER_A, "Use SharedPreferences", "Use a save button"));
        a.add(new QuizBean("Sketchware allows you to create Android apps without writing any code.", QuizBean.QUIZ_TRUE));
        a.add(new QuizBean("The \"ListView\" component is used to display a list of items in Sketchware.", QuizBean.QUIZ_TRUE));
        a.add(new QuizBean("The Toast function in Sketchware is used to display long-term notifications to the user.", QuizBean.QUIZ_FALSE));
        a.add(new QuizBean("You must manually write Java code to handle button clicks in Sketchware.", QuizBean.QUIZ_FALSE));
        a.add(new QuizBean("Sketchware apps can only be run on Android devices.", QuizBean.QUIZ_TRUE));
        a.add(new QuizBean("In Sketchware, you can save data using SharedPreferences.", QuizBean.QUIZ_TRUE));

        a.add(new QuizBean("The \"if...else\" block in Sketchware is used to create animations.", QuizBean.QUIZ_FALSE));
        a.add(new QuizBean("Sketchware is a great tool for beginners to learn Android app development.", QuizBean.QUIZ_TRUE));
        a.add(new QuizBean("You can preview your app’s UI directly in Sketchware before running it.", QuizBean.QUIZ_FALSE));


        // Questions with options
        a.add(new QuizBean("This component is used to animate Widgets.", QuizBean.QUIZ_ANSWER_B, "MediaPlayer", "ObjectAnimator"));
        a.add(new QuizBean("This component is used to play longer sound files.", QuizBean.QUIZ_ANSWER_B, "SoundPool", "MediaPlayer"));
        a.add(new QuizBean("This width / height property value fills up the parent's entire space given.", QuizBean.QUIZ_ANSWER_B, "WRAP_CONTENT", "MATCH_PARENT"));
        a.add(new QuizBean("This property lets you position the children inside the layout.", QuizBean.QUIZ_ANSWER_A, "layout gravity", "gravity"));
        a.add(new QuizBean("This property lets you add spacing around the widget.", QuizBean.QUIZ_ANSWER_B, "padding", "margin"));
        a.add(new QuizBean("How do you change the transparency of the background?", QuizBean.QUIZ_ANSWER_A, "alpha", "background color"));
        a.add(new QuizBean("How do you set a number keyboard type for EditText?", QuizBean.QUIZ_ANSWER_A, "ime", "numberDecimal"));
        a.add(new QuizBean("Which lifecycle event gets called when an activity is restarted?", QuizBean.QUIZ_ANSWER_A, "onResume", "onStop"));
        a.add(new QuizBean("Which property gets applied if you apply both \"singleLine=true\" and \"lines=3\" property on EditText?", QuizBean.QUIZ_ANSWER_A, "singleLine", "lines=3"));
        return a;
    }
}
