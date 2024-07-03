package mod.trindadedev.ui.preferences;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.sketchware.remod.databinding.PreferenceBinding;

public class PreferencePopup extends LinearLayout {
    private PreferenceBinding binding;
    public PopupMenu popupMenu;

    public PreferencePopup(Context context, String title, String description) {
        super(context);
        binding = PreferenceBinding.inflate(LayoutInflater.from(context), this, true);
        binding.preferenceTitle.setText(title);
        binding.preferenceDescription.setText(description);
        
        popupMenu = new PopupMenu(getContext(), this);
        
        binding.preference.setOnClickListener(v -> popupMenu.show());
    }

    public void addPopupMenuItem(String itemTitle) {
        popupMenu.getMenu().add(itemTitle);
    }
    
    public void setMenuListener (PopupMenu.OnMenuItemClickListener listener) {
        popupMenu.setOnMenuItemClickListener(listener);
    }
}