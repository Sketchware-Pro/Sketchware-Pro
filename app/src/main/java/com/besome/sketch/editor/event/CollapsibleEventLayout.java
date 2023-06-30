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
        this.projectButtons = findViewById(R.id.project_buttons);
        this.warning = findViewById(R.id.ll_warning);
        this.confirm = findViewById(R.id.confirm_layout);
        this.confirmYes = findViewById(R.id.confirm_yes);
        this.confirmNo = findViewById(R.id.confirm_no);
        TextView warningMessage = findViewById(R.id.tv_warning_message);
        TextView yes = findViewById(R.id.confirm_yes_text);
        yes.setText(xB.b().a(getContext(), R.string.common_word_continue));
        TextView no = findViewById(R.id.confirm_no_text);
        no.setText(xB.b().a(getContext(), R.string.common_word_cancel));
        this.confirm.setVisibility(INVISIBLE);
        this.warning.setVisibility(GONE);
        warningMessage.setText(xB.b().a(getContext(), R.string.common_message_confirm));
        this.reset = a(0, R.drawable.ic_reset_color_32dp, xB.b().a(context, R.string.common_word_reset));
        this.delete = a(1, R.drawable.delete_96, xB.b().a(context, R.string.common_word_delete));
        this.addToCollection = a(2, R.drawable.ic_bookmark_red_48dp, xB.b().a(context, R.string.logic_list_menu_add_to_collection));
        this.addToCollection.setVisibility(GONE);
        this.projectButtons.addView(this.reset);
        this.projectButtons.addView(this.delete);
        this.projectButtons.addView(this.addToCollection);
        this.flipTopIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_in);
        this.flipTopOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_out);
        this.flipBottomIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_in);
        this.flipBottomOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_out);
    }

    public void b() {
        this.delete.setVisibility(GONE);
    }

    public void c() {
        this.addToCollection.setVisibility(GONE);
    }

    public void d() {
        this.flipTopOut.setTarget(this.projectButtons);
        this.flipTopIn.setTarget(this.confirm);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.flipTopOut).with(this.flipTopIn);
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
        this.delete.setVisibility(VISIBLE);
    }

    public void f() {
        this.addToCollection.setVisibility(VISIBLE);
    }

    public void setButtonOnClickListener(View.OnClickListener onClickListener) {
        this.confirmYes.setOnClickListener(onClickListener);
        this.confirmNo.setOnClickListener(onClickListener);
        this.reset.setOnClickListener(onClickListener);
        this.delete.setOnClickListener(onClickListener);
        this.addToCollection.setOnClickListener(onClickListener);
    }

    public final CollapsibleButton a(int i, int i2, String str) {
        CollapsibleButton collapsibleButton = new CollapsibleButton(this.context);
        collapsibleButton.b = i;
        collapsibleButton.d.setImageResource(i2);
        collapsibleButton.e.setText(str);
        return collapsibleButton;
    }

    public void a() {
        this.flipBottomIn.setTarget(this.projectButtons);
        this.flipBottomOut.setTarget(this.confirm);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.flipBottomIn).with(this.flipBottomOut);
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
