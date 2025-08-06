package pro.sketchware.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import a.a.a.xB;
import pro.sketchware.R;
import pro.sketchware.databinding.ProgressMsgBoxBinding;

public class ProgressDialog {

    private final Context context;
    private final ProgressMsgBoxBinding binding;
    private boolean isCancelable = true;

    private AlertDialog dialog;

    public ProgressDialog(Context context) {
        this.context = context;
        binding = ProgressMsgBoxBinding.inflate(LayoutInflater.from(context));
    }

    public void setMessage(String message) {
        binding.tvProgress.setText(message);
    }

    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        if (dialog != null) dialog.setOnCancelListener(onCancelListener);
    }

    public void show() {
        if (dialog == null) {
            var dialogBuilder = new MaterialAlertDialogBuilder(context);
            dialogBuilder.setTitle(getStr(R.string.common_message_loading));
            dialogBuilder.setView(binding.getRoot());
            dialog = dialogBuilder.create();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
    }

    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public void setCancelable(boolean isCancelable) {
        this.isCancelable = isCancelable;
    }

    private String getStr(@StringRes int resId) {
        return xB.b().a(context, resId);
    }
}