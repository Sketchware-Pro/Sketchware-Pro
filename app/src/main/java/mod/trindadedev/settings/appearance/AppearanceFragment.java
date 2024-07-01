package mod.trindadedev.settings.appearance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sketchware.remod.R;
import com.sketchware.remod.databinding.SettingsAppearanceBinding;

import mod.hey.studios.util.Helper;
import mod.trindadedev.settings.appearance.theme.manage.ThemeManager;

public class AppearanceFragment extends Fragment {
    private SettingsAppearanceBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = SettingsAppearanceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.topAppBar.setNavigationOnClickListener(Helper.getBackPressedClickListener(getActivity()));

        switch (ThemeManager.getCurrentTheme(requireContext())) {
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
                if (checkedId == R.id.themeLight) {
                    ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_LIGHT);
                } else if (checkedId == R.id.themeSystem) {
                    ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_SYSTEM);
                } else if (checkedId == R.id.themeDark) {
                    ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_DARK);
                } else {
                    ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_SYSTEM);
                }
            }
        });
    }
}