package mod.trindadedev.ui.settings.appearance;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.activity.EdgeToEdge;

import com.sketchware.remod.databinding.SettingsAppearanceActivityBinding;
import com.sketchware.remod.R;

public class AppearanceActivity extends AppCompatActivity {
    private SettingsAppearanceActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EdgeToEdge.enable(this);
        super.onCreate(savedInstanceState);
        binding = SettingsAppearanceActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        openFragment(new AppearanceFragment());
    }
    
    private void openFragment (Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}