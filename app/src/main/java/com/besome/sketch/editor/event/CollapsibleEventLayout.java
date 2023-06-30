package com.besome.sketch.editor.event;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sketchware.remod.R;

import a.a.a.wB;
import a.a.a.xB;

public class CollapsibleEventLayout extends FrameLayout {
    private Context context;
    private View confirmYes;
    private View confirmNo;
    private LinearLayout warning;
    private LinearLayout projectButtons;
    private View confirm;
    private AnimatorSet flipTopIn;
    private AnimatorSet flipTopOut;
    private AnimatorSet flipBottomIn;
    private AnimatorSet flipBottomOut;
    private CollapsibleButton reset;
    private CollapsibleButton delete;
    private CollapsibleButton addToCollection;

    public CollapsibleEventLayout(Context context) {
        super(context);
        initialize(context);
    }

    private void initialize(Context context) {
        this.context = context;
        wB.a(context, this, R.layout.fr_logic_list_item_buttons);
        projectButtons = findViewById(R.id.project_buttons);
        warning = findViewById(R.id.ll_warning);
        confirm = findViewById(R.id.confirm_layout);
        confirmYes = findViewById(R.id.confirm_yes);
        confirmNo = findViewById(R.id.confirm_no);
        TextView warningMessage = findViewById(R.id.tv_warning_message);
        TextView yes = findViewById(R.id.confirm_yes_text);
        yes.setText(xB.b().a(getContext(), R.string.common_word_continue));
        TextView no = findViewById(R.id.confirm_no_text);
        no.setText(xB.b().a(getContext(), R.string.common_word_cancel));
        confirm.setVisibility(INVISIBLE);
        warning.setVisibility(GONE);
        warningMessage.setText(xB.b().a(getContext(), R.string.common_message_confirm));
        reset = a(0, R.drawable.ic_reset_color_32dp, xB.b().a(context, R.string.common_word_reset));
        delete = a(1, R.drawable.delete_96, xB.b().a(context, R.string.common_word_delete));
        addToCollection = a(2, R.drawable.ic_bookmark_red_48dp, xB.b().a(context, R.string.logic_list_menu_add_to_collection));
        addToCollection.setVisibility(GONE);
        projectButtons.addView(reset);
        projectButtons.addView(delete);
        projectButtons.addView(addToCollection);
        flipTopIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_in);
        flipTopOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_out);
        flipBottomIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_in);
        flipBottomOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_out);
    }

    public void b() {
        delete.setVisibility(GONE);
    }

    public void c() {
        addToCollection.setVisibility(GONE);
    }

    public void d() {
        flipTopOut.setTarget(projectButtons);
        flipTopIn.setTarget(confirm);
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
                warning.setVisibility(VISIBLE);
                confirm.setVisibility(VISIBLE);
                delete.setEnabled(false);
                confirmNo.setEnabled(false);
            }
        });
        animatorSet.start();
    }

    public void e() {
        delete.setVisibility(VISIBLE);
    }

    public void f() {
        addToCollection.setVisibility(VISIBLE);
    }

    public void setButtonOnClickListener(View.OnClickListener onClickListener) {
        confirmYes.setOnClickListener(onClickListener);
        confirmNo.setOnClickListener(onClickListener);
        reset.setOnClickListener(onClickListener);
        delete.setOnClickListener(onClickListener);
        addToCollection.setOnClickListener(onClickListener);
    }

    public final CollapsibleButton a(int i, int i2, String str) {
        CollapsibleButton collapsibleButton = new CollapsibleButton(context);
        collapsibleButton.b = i;
        collapsibleButton.d.setImageResource(i2);
        collapsibleButton.e.setText(str);
        return collapsibleButton;
    }

    public void a() {
        flipBottomIn.setTarget(projectButtons);
        flipBottomOut.setTarget(confirm);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(flipBottomIn).with(flipBottomOut);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                delete.setEnabled(true);
                confirmNo.setEnabled(false);
                confirm.setVisibility(INVISIBLE);
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
