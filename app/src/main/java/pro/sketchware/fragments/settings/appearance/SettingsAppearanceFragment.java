package pro.sketchware.fragments.settings.appearance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

import a.a.a.qA;
import pro.sketchware.databinding.FragmentSettingsAppearanceBinding;
import pro.sketchware.utility.theme.ThemeManager;

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

        {
            View view1 = binding.content;
            int left = view1.getPaddingLeft();
            int top = view1.getPaddingTop();
            int right = view1.getPaddingRight();
            int bottom = view1.getPaddingBottom();

            ViewCompat.setOnApplyWindowInsetsListener(view1, (v, i) -> {
                Insets insets = i.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
                v.setPadding(left + insets.left, top, right + insets.right, bottom + insets.bottom);
                return i;
            });
        }
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> {
            if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                requireActivity().getSupportFragmentManager().popBackStack();
            } else {
                requireActivity().onBackPressed();
            }
        });

        {
            View view1 = binding.appBarLayout;
            int left = view1.getPaddingLeft();
            int top = view1.getPaddingTop();
            int right = view1.getPaddingRight();
            int bottom = view1.getPaddingBottom();

            ViewCompat.setOnApplyWindowInsetsListener(view1, (v, i) -> {
                Insets insets = i.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
                v.setPadding(left + insets.left, top + insets.top, right + insets.right, bottom);
                return i;
            });
        }
    }

    private void initializeThemeSettings() {
        boolean isSystemTheme = ThemeManager.isSystemTheme(requireContext());
        binding.switchSystem.setChecked(isSystemTheme);

        updateThemeCardSelection(ThemeManager.getCurrentTheme(requireContext()));

        setThemeCardsEnabled(!isSystemTheme);
    }

    private void setupClickListeners() {
        binding.themeSystem.setOnClickListener(v -> binding.switchSystem.setChecked(!binding.switchSystem.isChecked()));

        binding.switchSystem.setOnCheckedChangeListener((buttonView, isChecked) -> {
            unselectSelectedThemeCard();
            setThemeCardsEnabled(!isChecked);
            if (isChecked) {
                ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_SYSTEM);
                return;
            }
            int theme = ThemeManager.getSystemAppliedTheme(requireContext());
            ThemeManager.applyTheme(requireContext(), theme);
            updateThemeCardSelection(theme);
        });

        binding.themeLight.setOnClickListener(v -> {
            if (!binding.switchSystem.isChecked()) {
                updateThemeCardSelection(ThemeManager.THEME_LIGHT);
                ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_LIGHT);
            }
        });

        binding.themeDark.setOnClickListener(v -> {
            if (!binding.switchSystem.isChecked()) {
                updateThemeCardSelection(ThemeManager.THEME_DARK);
                ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_DARK);
            }
        });
    }

    private void updateThemeCardSelection(int theme) {
        unselectSelectedThemeCard();

        MaterialCardView newSelection = switch (theme) {
            case ThemeManager.THEME_LIGHT -> binding.themeLight;
            case ThemeManager.THEME_DARK -> binding.themeDark;
            default -> null;
        };

        if (newSelection != null && !binding.switchSystem.isChecked()) {
            newSelection.setChecked(true);
            selectedThemeCard = newSelection;
        }
    }

    private void unselectSelectedThemeCard() {
        if (selectedThemeCard != null) {
            selectedThemeCard.setChecked(false);
            selectedThemeCard = null;
        }
    }

    private void setThemeCardsEnabled(boolean enabled) {
        binding.themeLight.setEnabled(enabled);
        binding.themeDark.setEnabled(enabled);

        float alpha = enabled ? 1.0f : 0.5f;
        binding.themeLight.animate().alpha(alpha).start();
        binding.themeDark.animate().alpha(alpha).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}