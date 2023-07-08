package com.besome.sketch.lib.base;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.besome.sketch.editor.event.CollapsibleButton;
import com.sketchware.remod.R;

import java.util.List;

import a.a.a.wB;
import mod.hey.studios.util.Helper;

public abstract class CollapsibleLayout extends FrameLayout {
    private View confirmYes;
    private View confirmNo;
    private LinearLayout projectButtons;
    private View confirmLayout;
    private AnimatorSet flipTopIn;
    private AnimatorSet flipTopOut;
    private AnimatorSet flipBottomIn;
    private AnimatorSet flipBottomOut;
    private List<CollapsibleButton> buttons;

    public CollapsibleLayout(@NonNull Context context) {
        this(context, null);
    }

    public CollapsibleLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.fr_logic_list_item_buttons);
        projectButtons = findViewById(R.id.project_buttons);
        confirmLayout = findViewById(R.id.confirm_layout);
        confirmYes = findViewById(R.id.confirm_yes);
        confirmNo = findViewById(R.id.confirm_no);
        TextView warningMessage = findViewById(R.id.tv_warning_message);
        TextView yes = findViewById(R.id.confirm_yes_text);
        yes.setText(Helper.getResString(yes, R.string.common_word_continue));
        TextView no = findViewById(R.id.confirm_no_text);
        no.setText(Helper.getResString(no, R.string.common_word_cancel));
        warningMessage.setText(Helper.getResString(warningMessage, R.string.common_message_confirm));
        flipTopIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_in);
        flipTopOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_out);
        flipBottomIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_in);
        flipBottomOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_out);
        buttons = initializeButtons(context);
        buttons.forEach(projectButtons::addView);
        if (isInEditMode()) {
            confirmLayout.setVisibility(GONE);
        }
    }

    protected abstract List<CollapsibleButton> initializeButtons(@NonNull Context context);

    public final void setButtonOnClickListener(View.OnClickListener listener) {
        confirmYes.setOnClickListener(listener);
        confirmNo.setOnClickListener(listener);
        buttons.forEach(v -> v.setOnClickListener(listener));
    }

    public final void showConfirmation() {
        flipTopOut.setTarget(projectButtons);
        flipTopIn.setTarget(confirmLayout);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(flipTopOut).with(flipTopIn);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                confirmYes.setEnabled(true);
                confirmNo.setEnabled(true);
                projectButtons.setVisibility(GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                confirmLayout.setVisibility(VISIBLE);
                buttons.forEach(button -> button.setEnabled(false));
                confirmYes.setEnabled(false);
                confirmNo.setEnabled(false);
            }
        });
        animatorSet.start();
    }

    public final void hideConfirmation() {
        flipBottomIn.setTarget(projectButtons);
        flipBottomOut.setTarget(confirmLayout);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(flipBottomIn).with(flipBottomOut);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                buttons.forEach(button -> button.setEnabled(true));
                confirmLayout.setVisibility(GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                projectButtons.setVisibility(VISIBLE);
                confirmYes.setEnabled(false);
                confirmNo.setEnabled(false);
            }
        });
        animatorSet.start();
    }
}
