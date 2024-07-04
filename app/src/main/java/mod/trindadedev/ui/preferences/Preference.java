package mod.trindadedev.ui.preferences;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.sketchware.remod.databinding.PreferenceBinding;

public class Preference extends LinearLayout {
    private PreferenceBinding binding;

    public Preference(Context context, String title, String description, View.OnClickListener listenerClick) {
        super(context);
        binding = PreferenceBinding.inflate(LayoutInflater.from(context), this, true);
        
        binding.preferenceTitle.setText(title);
        binding.preferenceDescription.setText(description);
        
        binding.preference.setOnClickListener(listenerClick);
    }
}