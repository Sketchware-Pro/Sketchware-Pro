package pro.sketchware.lib.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;

import pro.sketchware.R;
import pro.sketchware.databinding.ViewBottomSheetDialogBinding;

public class BottomSheetDialogView extends BottomSheetDialog {
    private final ViewBottomSheetDialogBinding binding;

    public BottomSheetDialogView(Context context) {
        super(context);
        binding = ViewBottomSheetDialogBinding.inflate(LayoutInflater.from(getContext()));
        setContentView(binding.getRoot());
    }

    @Override
    public void show() {
        super.show();
        if (getWindow() != null) {
            getWindow().findViewById(R.id.design_bottom_sheet)
                    .setBackgroundResource(android.R.color.transparent);
        }
    }

    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
    }

    public void setTitle(String title) {
        binding.title.setText(title);
    }

    public void setDescription(String description) {
        binding.description.setText(description);
    }

    public void setImage(int resId) {
        binding.image.setImageResource(resId);
    }

    public void setImage(String imageUrl) {
        Glide.with(getContext()).load(imageUrl).into(binding.image);
    }

    public void setPositiveButton(String text, OnClickListener listener) {
        binding.positiveButton.setText(text);
        binding.positiveButton.setVisibility(View.VISIBLE);
        binding.positiveButton.setOnClickListener(v -> {
            listener.onClick(this, BUTTON_POSITIVE);
            dismiss();
        });
    }

    public void setPositiveButtonText(String text) {
        binding.positiveButton.setText(text);
    }

    public void setNegativeButtonText(String text) {
        binding.negativeButton.setText(text);
    }

    public void setNegativeButton(String text, OnClickListener listener) {
        binding.negativeButton.setText(text);
        binding.negativeButton.setVisibility(View.VISIBLE);
        binding.negativeButton.setOnClickListener(v -> {
            listener.onClick(this, BUTTON_NEGATIVE);
            dismiss();
        });
    }


    // ----------------- Getters -----------------

    public MaterialButton getPositiveButton() {
        return binding.positiveButton;
    }

    public MaterialButton getNegativeButton() {
        return binding.negativeButton;
    }
}
