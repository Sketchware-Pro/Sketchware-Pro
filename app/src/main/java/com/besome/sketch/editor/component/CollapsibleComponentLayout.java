package com.besome.sketch.editor.component;

import a.a.a.wB;
import a.a.a.xB;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.besome.sketch.editor.event.CollapsibleButton;
import com.sketchware.remod.R;

public class CollapsibleComponentLayout extends FrameLayout {
    private Context context;
    private View confirmYes;
    private View confirmNo;
    private LinearLayout projectButtons;
    private View confirmLayout;
    private LinearLayout warning;
    private AnimatorSet flipTopIn;
    private AnimatorSet flipTopOut;
    private AnimatorSet flipBottomIn;
    private AnimatorSet flipBottomOut;
    private CollapsibleButton delete;

    public CollapsibleComponentLayout(Context context) {
        this(context, null);
    }

    public CollapsibleComponentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initialize(context);
    }

    private void initialize(Context context) {
        this.context = context;
        wB.a(context, this, R.layout.fr_logic_list_item_buttons);
        projectButtons = findViewById(R.id.project_buttons);
        warning = findViewById(R.id.ll_warning);
        confirmLayout = findViewById(R.id.confirm_layout);
        confirmYes = findViewById(R.id.confirm_yes);
        confirmNo = findViewById(R.id.confirm_no);
        TextView i = findViewById(R.id.tv_warning_message);
        TextView d = findViewById(R.id.confirm_yes_text);
        d.setText(xB.b().a(getContext(), R.string.common_word_continue));
        TextView e = findViewById(R.id.confirm_no_text);
        e.setText(xB.b().a(getContext(), R.string.common_word_cancel));
        confirmLayout.setVisibility(INVISIBLE);
        warning.setVisibility(GONE);
        i.setText(xB.b().a(getContext(), R.string.common_message_confirm));
        delete = createDelete(0, R.drawable.delete_96, xB.b().a(context, R.string.common_word_delete));
        projectButtons.addView(delete);
        flipTopIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_in);
        flipTopOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_out);
        flipBottomIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_in);
        flipBottomOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_out);
    }

    public void b() {
        flipTopOut.setTarget(projectButtons);
        flipTopIn.setTarget(confirmLayout);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(flipTopOut).with(flipTopIn);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                delete.setEnabled(false);
                confirmNo.setEnabled(true);
                projectButtons.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                confirmLayout.setVisibility(GONE);
                warning.setVisibility(GONE);
                delete.setEnabled(false);
                confirmNo.setEnabled(false);
            }
        });
        animatorSet.start();
    }

    public void setButtonOnClickListener(View.OnClickListener onClickListener) {
        confirmYes.setOnClickListener(onClickListener);
        confirmNo.setOnClickListener(onClickListener);
        delete.setOnClickListener(onClickListener);
    }

    private CollapsibleButton createDelete(int i, int i2, String str) {
        CollapsibleButton collapsibleButton = new CollapsibleButton(context);
        collapsibleButton.b = i;
        collapsibleButton.d.setImageResource(i2);
        collapsibleButton.e.setText(str);
        return collapsibleButton;
    }

    public CollapsibleButton getDeleteButton() {
        return delete;
    }

    public void a() {
        flipBottomIn.setTarget(projectButtons);
        flipBottomOut.setTarget(confirmLayout);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(flipBottomIn).with(flipBottomOut);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                delete.setEnabled(true);
                confirmNo.setEnabled(false);
                confirmLayout.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                warning.setVisibility(GONE);
                projectButtons.setVisibility(VISIBLE);
                delete.setEnabled(false);
                confirmNo.setEnabled(false);
            }
        });
        animatorSet.start();
    }
}
