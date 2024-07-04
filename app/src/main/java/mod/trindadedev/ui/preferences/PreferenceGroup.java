package mod.trindadedev.ui.preferences;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.view.LayoutInflater;

import com.sketchware.remod.databinding.PreferenceGroupBinding;

public class PreferenceGroup extends LinearLayout {
    public Context context;
    public PreferenceGroupBinding binding;

    public PreferenceGroup(Context context, String title) {
        super(context);
        this.context = context;

        binding = PreferenceGroupBinding.inflate(LayoutInflater.from(context), this, true);
        binding.preferenceGroupTitle.setText(title);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void addPreference(View view) {
        binding.preferenceGroupContent.addView(view);
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        binding.preferenceGroup.setOnClickListener(listener);
    }
}