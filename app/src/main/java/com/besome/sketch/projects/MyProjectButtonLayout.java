package com.besome.sketch.projects;

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
import mod.hey.studios.util.Helper;

public class MyProjectButtonLayout extends FrameLayout {

    private Context context;
    private View confirmLayout;
    private View confirmYes;
    private View confirmNo;
    private LinearLayout buttonContainer;
    private AnimatorSet flipTopIn;
    private AnimatorSet flipTopOut;
    private AnimatorSet flipBottomIn;
    private AnimatorSet flipBottomOut;
    private MyProjectButton settings;
    private MyProjectButton backUp;
    private MyProjectButton signExport;
    private MyProjectButton delete;
    private MyProjectButton config;

    public MyProjectButtonLayout(Context context) {
        super(context);
        initialize(context);
    }

    public void setButtonOnClickListener(View.OnClickListener l) {
        confirmYes.setOnClickListener(l);
        confirmNo.setOnClickListener(l);
        settings.setOnClickListener(l);
        backUp.setOnClickListener(l);
        signExport.setOnClickListener(l);
        delete.setOnClickListener(l);
        config.setOnClickListener(l);
    }

    private void initialize(Context context) {
        this.context = context;
        wB.a(context, this, R.layout.myproject_buttons);

        buttonContainer = findViewById(R.id.project_buttons);
        confirmLayout = findViewById(R.id.confirm_layout);
        TextView confirmDelete = findViewById(R.id.confirm_title);
        confirmDelete.setText(Helper.getResString(R.string.language_message_confirm_delete));
        confirmYes = findViewById(R.id.confirm_yes);
        confirmNo = findViewById(R.id.confirm_no);
        TextView confirmYesText = findViewById(R.id.confirm_yes_text);
        confirmYesText.setText(Helper.getResString(R.string.common_word_delete));
        TextView confirmNoText = findViewById(R.id.confirm_no_text);
        confirmNoText.setText(Helper.getResString(R.string.common_word_cancel));
        confirmDelete.setText(Helper.getResString(R.string.myprojects_confirm_project_delete));
        confirmYesText.setText(Helper.getResString(R.string.common_word_delete));
        confirmNoText.setText(Helper.getResString(R.string.common_word_cancel));
        confirmLayout.setVisibility(INVISIBLE);

        settings = createButton(0, R.drawable.settings_96, Helper.getResString(R.string.myprojects_list_menu_title_settings));
        backUp = createButton(1, R.drawable.ic_backup, "Back up");
        signExport = createButton(2, R.drawable.ic_export_grey_48dp, Helper.getResString(R.string.myprojects_list_menu_title_sign_export));
        delete = createButton(3, R.drawable.ic_delete_grey_48dp, Helper.getResString(R.string.myprojects_list_menu_title_delete));
        config = createButton(4, R.drawable.settings_96, "Config");
        buttonContainer.addView(settings);
        buttonContainer.addView(backUp);
        buttonContainer.addView(signExport);
        buttonContainer.addView(delete);
        buttonContainer.addView(config);

        flipTopIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_in);
        flipTopOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_top_out);
        flipBottomIn = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_in);
        flipBottomOut = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.flip_bottom_out);
    }

    /*
     * Referenced by a.a.a.GC.d, a.a.a.IC
     */
    public void b() {
        flipTopOut.setTarget(buttonContainer);
        flipTopIn.setTarget(confirmLayout);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(flipTopOut).with(flipTopIn);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                delete.setEnabled(false);
                confirmNo.setEnabled(true);
                buttonContainer.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                confirmLayout.setVisibility(VISIBLE);
                delete.setEnabled(false);
                confirmNo.setEnabled(false);
            }
        });
        animatorSet.start();
    }

    private MyProjectButton createButton(int id, int imageResId, String label) {
        MyProjectButton myProjectButton = new MyProjectButton(context);
        myProjectButton.b = id;
        myProjectButton.icon.setImageResource(imageResId);
        myProjectButton.name.setText(label);
        return myProjectButton;
    }

    /*
     * Referenced by a.a.a.GC.d
     */
    public void a() {
        flipBottomIn.setTarget(buttonContainer);
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
                buttonContainer.setVisibility(VISIBLE);
                delete.setEnabled(false);
                confirmNo.setEnabled(false);
            }
        });
        animatorSet.start();
    }
}
