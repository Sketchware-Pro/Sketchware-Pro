package mod.trindadedev.settings.appearance;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mod.trindadedev.settings.appearance.theme.manage.ThemeManager;

import com.sketchware.remod.databinding.SettingsAppearanceBinding;
import com.sketchware.remod.R;

public class AppearanceFragment extends Fragment {
    private SettingsAppearanceBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = SettingsAppearanceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch(ThemeManager.getCurrentTheme(requireContext())) {
            case ThemeManager.THEME_SYSTEM:
                binding.toggleThemes.check(R.id.themeSystem);
                break;
            case ThemeManager.THEME_LIGHT:
                binding.toggleThemes.check(R.id.themeLight);
                break;
            case ThemeManager.THEME_DARK:
                binding.toggleThemes.check(R.id.themeDark);
                break;
            default:
                binding.toggleThemes.check(R.id.themeSystem);
                break;
        }

        binding.toggleThemes.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != View.NO_ID) {
                switch (checkedId) {
                    case R.id.themeLight:
                        ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_LIGHT);
                        break;
                    case R.id.themeSystem:
                        ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_SYSTEM);
                        break;
                    case R.id.themeDark:
                        ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_DARK);
                        break;
                    default:
                        ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_SYSTEM);
                        break;
                }
            }
        });
    }
}