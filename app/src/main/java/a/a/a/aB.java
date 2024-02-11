package a.a.a;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * Sketchware dialog that wrappes the material alert dialog builder.
 */
public class aB extends MaterialAlertDialogBuilder {

    private AlertDialog dialog;
    private OnClickListener positiveListener;
    private OnClickListener negativeListener;
    private OnClickListener neutralListener;
    private boolean autoDismiss = true;


    public aB(Activity activity) {
        super(activity);
    }

    /**
     * Set the dialog's image's resource ID
     */
    public void a(@DrawableRes int resId) {
        setIcon(resId);
    }

    /**
     * Set the dialog's custom view
     */
    public void a(View customView) {
        FrameLayout view = new FrameLayout(getContext());
        view.setLayoutParams(
                new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                )
        );
        view.addView(customView);
        setView(view);
    }

    /**
     * Set the dialog's message
     */
    public void a(String message) {
        setMessage(message);
    }

    /**
     * Set the dialog's "No" button text and listener
     */
    public void a(String noText, OnClickListener noListener) {
        negativeListener = noListener;
        setNegativeButton(noText, null);
    }

    /**
     * Set the dialog's title
     */
    public void b(String title) {
        setTitle(title);
    }

    /**
     * Set the dialog's "Yes" button text and listener
     */
    public void b(String yesText, OnClickListener yesListener) {
        positiveListener = yesListener;
        setPositiveButton(yesText, null);
    }

    public void configureDefaultButton(String defaultText, OnClickListener defaultListener) {
        neutralListener = defaultListener;
        setNeutralButton(defaultText, null);
    }

    public void autoDismiss(boolean autoDismiss) {
        this.autoDismiss = autoDismiss;
    }

    @Override
    public AlertDialog show() {
        dialog = super.create();

        dialog.setOnShowListener(dialogInterface -> setupButtonListeners());

        dialog.show();
        return dialog;
    }

    private void setupButtonListeners() {
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        if (positiveButton != null && positiveListener != null) {
            positiveButton.setOnClickListener(v -> {
                if (autoDismiss) dialog.dismiss();
                positiveListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
            });
        }

        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        if (negativeButton != null && negativeListener != null) {
            negativeButton.setOnClickListener(v -> {
                if (autoDismiss) dialog.dismiss();
                negativeListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
            });
        }

        Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        if (neutralButton != null && neutralListener != null) {
            neutralButton.setOnClickListener(v -> {
                if (autoDismiss) dialog.dismiss();
                neutralListener.onClick(dialog, DialogInterface.BUTTON_NEUTRAL);
            });
        }
    }
}
