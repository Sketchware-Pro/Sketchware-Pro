package a.a.a;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.besome.sketch.tools.QuizBoard;
import com.sketchware.remod.R;

import mod.hey.studios.util.Helper;

public class Ep extends Dialog {

    private final TextView tvProgress;
    private final QuizBoard quizBoard;
    private boolean cancelOnBackPressed;

    public Ep(Context context) {
        super(context, R.style.progress);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.build_progress_msg_box);

        LinearLayout quizLayout = findViewById(R.id.layout_quiz);
        quizBoard = new QuizBoard(getContext());
        quizLayout.addView(quizBoard);
        LottieAnimationView animationView = findViewById(R.id.animation_view);
        animationView.setScale(2);
        setTitle(Helper.getResString(R.string.common_message_progress));
        tvProgress = findViewById(R.id.tv_progress);
        tvProgress.setText(Helper.getResString(R.string.common_message_loading));
        super.setCanceledOnTouchOutside(false);
        super.setCancelable(true);
    }

    public void a(String text) {
        tvProgress.setText(text);
    }

    public void a(boolean cancellable) {
        cancelOnBackPressed = cancellable;
    }

    public boolean a() {
        return cancelOnBackPressed;
    }

    @Override
    public void dismiss() {
        quizBoard.a();
        super.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (!cancelOnBackPressed) {
            super.onBackPressed();
        }
    }
}
