package mod.trindadedev.ui.fragments.settings.appearance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import pro.sketchware.R;
import pro.sketchware.databinding.FragmentSettingsAppearanceBinding;

import mod.trindadedev.manage.theme.ThemeManager;
import mod.trindadedev.ui.fragments.BaseFragment;

public class SettingsAppearanceFragment extends BaseFragment {

    private FragmentSettingsAppearanceBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsAppearanceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureToolbar(binding.toolbar);
        configureThemeController();
    }
    
    private void configureThemeController() {
        switch (ThemeManager.getCurrentTheme(requireContext())) {
            case ThemeManager.THEME_LIGHT:
                binding.toggleThemes.check(R.id.theme_light);
                break;
            case ThemeManager.THEME_DARK:
                binding.toggleThemes.check(R.id.theme_dark);
                break;
            default:
                binding.toggleThemes.check(R.id.theme_system);
                break;
        }

        binding.toggleThemes.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.theme_light) {
                    ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_LIGHT);
                } else if (checkedId == R.id.theme_system) {
                    ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_SYSTEM);
                } else if (checkedId == R.id.theme_dark) {
                    ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_DARK);
                } else {
                    ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_SYSTEM);
                }
            }
        });
    }
}