package mod.remaker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import pro.sketchware.databinding.CustomViewAttributeBinding;

public class CustomAttributeView extends FrameLayout {

    private final CustomViewAttributeBinding binding;

    public CustomAttributeView(Context context) {
        super(context);
        binding = CustomViewAttributeBinding.inflate(LayoutInflater.from(context), this, true);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        binding.cusAttrLayout.setOnClickListener(listener);
    }

    public TextView getTextView() {
        return binding.cusAttrText;
    }

    public ImageView getImageView() {
        return binding.cusAttrBtn;
    }

    @Override
    public void setOnLongClickListener(View.OnLongClickListener listener) {
        binding.cusAttrLayout.setOnLongClickListener(listener);
    }
}
