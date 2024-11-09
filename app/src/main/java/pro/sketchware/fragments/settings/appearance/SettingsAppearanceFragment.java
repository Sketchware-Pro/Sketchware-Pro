package pro.sketchware.fragments.settings.appearance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.google.android.material.card.MaterialCardView;
import pro.sketchware.databinding.FragmentSettingsAppearanceBinding;
import pro.sketchware.utility.theme.ThemeManager;
import a.a.a.qA;

public class SettingsAppearanceFragment extends qA {

    private FragmentSettingsAppearanceBinding binding;
    private MaterialCardView selectedThemeCard;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsAppearanceBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupToolbar();
        initializeThemeSettings();
        setupClickListeners();
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> {
            if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
                requireActivity().onBackPressed();
            }
        });
    }

    private void initializeThemeSettings() {
        // Initialize system theme switch
        boolean isSystemTheme = ThemeManager.isSystemTheme(requireContext());
        binding.switchSystem.setChecked(isSystemTheme);

        // Set the appropriate theme card selection
        updateThemeCardSelection(ThemeManager.getCurrentTheme(requireContext()));

        // Disable theme cards if system theme is enabled
        setThemeCardsEnabled(!isSystemTheme);
    }

    private void setupClickListeners() {
        // System theme card click listener
        binding.themeSystem.setOnClickListener(v ->
                binding.switchSystem.setChecked(!binding.switchSystem.isChecked())
        );

        // System theme switch listener
        binding.switchSystem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                setThemeCardsEnabled(!isChecked);
                if (isChecked) {
                    clearThemeCardSelection();
                    ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_SYSTEM);
                }
            }
        });

        // Light theme card click listener
        binding.themeLight.setOnClickListener(v -> {
            if (!binding.switchSystem.isChecked()) {
                updateThemeCardSelection(ThemeManager.THEME_LIGHT);
                ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_LIGHT);
            }
        });

        // Dark theme card click listener
        binding.themeDark.setOnClickListener(v -> {
            if (!binding.switchSystem.isChecked()) {
                updateThemeCardSelection(ThemeManager.THEME_DARK);
                ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_DARK);
            }
        });
    }

    private void updateThemeCardSelection(int theme) {
        clearThemeCardSelection();

        MaterialCardView newSelection = null;
        switch (theme) {
            case ThemeManager.THEME_LIGHT:
                newSelection = binding.themeLight;
                break;
            case ThemeManager.THEME_DARK:
                newSelection = binding.themeDark;
                break;
        }

        if (newSelection != null && !binding.switchSystem.isChecked()) {
            newSelection.setChecked(true);
            selectedThemeCard = newSelection;
        }
    }

    private void clearThemeCardSelection() {
        if (selectedThemeCard != null) {
            selectedThemeCard.setChecked(false);
            selectedThemeCard = null;
        }
    }

    private void setThemeCardsEnabled(boolean enabled) {
        binding.themeLight.setEnabled(enabled);
        binding.themeDark.setEnabled(enabled);

        float alpha = enabled ? 1.0f : 0.5f;
        binding.themeLight.setAlpha(alpha);
        binding.themeDark.setAlpha(alpha);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}