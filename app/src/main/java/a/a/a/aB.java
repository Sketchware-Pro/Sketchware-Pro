package a.a.a;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class aB extends AlertDialog {

    private final Activity activity;
    public int dialogImageResId = -1;
    public String dialogTitleText = "";
    public String dialogMessageText = "";
    public boolean msgIsSelectable = false;
    public boolean canDismissDialogOnDefaultBtnClicked = true;
    public View dialogCustomView;
    public String dialogDefaultText = "Default";
    public View.OnClickListener dialogDefaultListener = null;
    public String dialogNoText = "No";
    public View.OnClickListener dialogNoListener = null;
    public String dialogYesText = "Yes";
    public View.OnClickListener dialogYesListener = null;
    public boolean cancelable = true;
    public boolean canceledOnTouchOutside = true;
    private AlertDialog dialog;

    public aB(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    @Override
    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    /**
     * Set the dialog's image's resource ID
     */
    public void a(@DrawableRes int resId) {
        dialogImageResId = resId;
    }

    /**
     * Set the dialog's custom view
     */
    public void a(View customView) {
        dialogCustomView = customView;
    }

    /**
     * Set the dialog's message
     */
    public void a(String message) {
        dialogMessageText = message;
    }

    /**
     * Set the dialog's "No" button text and listener
     */
    public void a(String noText, View.OnClickListener noListener) {
        dialogNoText = noText;
        dialogNoListener = noListener;
    }

    /**
     * Set the dialog's title
     */
    public void b(String title) {
        dialogTitleText = title;
    }

    /**
     * Set the dialog's "Yes" button text and listener
     */
    public void b(String yesText, View.OnClickListener yesListener) {
        dialogYesText = yesText;
        dialogYesListener = yesListener;
    }

    public void configureDefaultButton(String defaultText, View.OnClickListener defaultListener) {
        dialogDefaultText = defaultText;
        dialogDefaultListener = defaultListener;
    }

    public void setDismissOnDefaultButtonClick(boolean dismissOnDefaultClick) {
        canDismissDialogOnDefaultBtnClicked = dismissOnDefaultClick;
    }

    public void setMessageIsSelectable(boolean msgIsSelectable) {
        this.msgIsSelectable = msgIsSelectable;
    }

    @Override
    public void show() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(activity);
        builder.setCancelable(cancelable);

        if (!dialogTitleText.isEmpty()) {
            builder.setTitle(dialogTitleText);
        }

        if (dialogCustomView != null) {
            builder.setView(dialogCustomView);
        } else if (!dialogMessageText.isEmpty()) {
            builder.setMessage(dialogMessageText);
        }

        if (dialogImageResId != -1) {
            builder.setIcon(dialogImageResId);
        }

        if (dialogDefaultListener != null) {
            builder.setNeutralButton(dialogDefaultText, null);
        }

        if (dialogNoListener != null) {
            builder.setNegativeButton(dialogNoText, null);
        }

        if (dialogYesListener != null) {
            builder.setPositiveButton(dialogYesText, null);
        }

        dialog = builder.create();

        if (msgIsSelectable) {
            dialog.setOnShowListener(dialogInterface -> {
                TextView messageView = dialog.findViewById(android.R.id.message);
                if (messageView != null) {
                    messageView.setTextIsSelectable(true);
                }
            });
        }

        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        dialog.show();

        if (dialogDefaultListener != null) {
            Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
            neutralButton.setOnClickListener(v -> {
                dialogDefaultListener.onClick(v);
                if (canDismissDialogOnDefaultBtnClicked) {
                    dialog.dismiss();
                }
            });
        }

        if (dialogNoListener != null) {
            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setOnClickListener(v -> {
                dialogNoListener.onClick(v);
                dialog.dismiss();
            });
        }

        if (dialogYesListener != null) {
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {
                dialogYesListener.onClick(v);
                dialog.dismiss();
            });
        }
    }

    @Override
    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        } else {
            super.dismiss();
        }
    }
}
