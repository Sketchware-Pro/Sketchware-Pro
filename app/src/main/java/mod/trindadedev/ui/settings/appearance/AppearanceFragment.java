package mod.trindadedev.ui.settings.appearance;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.ChipGroup;

import com.sketchware.remod.R;
import com.sketchware.remod.databinding.PrefencesContentAppbarBinding;
import com.sketchware.remod.databinding.PreferenceSketchwareThemeBinding;

import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;

import mod.trindadedev.manage.theme.ThemeManager;
import mod.trindadedev.ui.preferences.PreferenceGroup;
import mod.trindadedev.ui.preferences.PreferencePopup;

public class AppearanceFragment extends Fragment {

    private PrefencesContentAppbarBinding binding;
    private PreferenceSketchwareThemeBinding skThemeBinding;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = PrefencesContentAppbarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(requireContext());
        binding.topAppBar.setNavigationOnClickListener(Helper.getBackPressedClickListener(getActivity()));
        binding.topAppBar.setTitle(getString(R.string.appearance));

        int theme = ThemeManager.getThemeInt(requireContext());
        if (theme == ThemeManager.THEME_LIGHT) {
            skThemeBinding.toggleThemes.check(R.id.themeLight);
        } else if (theme == ThemeManager.THEME_DARK) {
            skThemeBinding.toggleThemes.check(R.id.themeDark);
        } else {
            skThemeBinding.toggleThemes.check(R.id.themeSystem);
        }

        skThemeBinding.toggleThemes.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.themeLight:
                    ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_LIGHT);
                    break;
                case R.id.themeDark:
                    ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_DARK);
                    break;
                case R.id.themeSystem:
                default:
                    ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_SYSTEM);
                    break;
            }
        });
    }
    
    public void setupViews(Context context) {
         // Theme-related preferences
         PreferenceGroup themePreferenceGroup = new PreferenceGroup(context, getString(R.string.appearance_theme));
         binding.content.addView(themePreferenceGroup);
         
         skThemeBinding = PreferenceSketchwareThemeBinding.inflate(LayoutInflater.from(context), themePreferenceGroup, false);
         themePreferenceGroup.addPreference(skThemeBinding.getRoot());
         
         skThemeBinding.preferenceSwitchDynamic.setChecked(ThemeManager.isUseDynamic(context));
         
         skThemeBinding.preferenceSwitchDynamicContent.setOnClickListener(v -> {
             skThemeBinding.preferenceSwitchDynamic.setChecked(!skThemeBinding.preferenceSwitchDynamic.isChecked());
             ThemeManager.useDynamicColors(context, skThemeBinding.preferenceSwitchDynamic.isChecked());
         });
         
         // Editor-related preferences
         PreferenceGroup editorsPreferenceGroup = new PreferenceGroup(context, getString(R.string.appearance_editors));
         binding.content.addView(editorsPreferenceGroup);
         
         PreferencePopup codeEditorVersionPreference = new PreferencePopup(context, getString(R.string.appearance_editors_code_editor_title), getString(R.string.appearance_editors_code_editor_description));
         codeEditorVersionPreference.addPopupMenuItem(getString(R.string.appearance_editors_code_editor_legacy));
         codeEditorVersionPreference.addPopupMenuItem(getString(R.string.appearance_editors_code_editor_default));
         codeEditorVersionPreference.setMenuListener(item -> {
             boolean isLegacy = item.getTitle().toString().equals(getString(R.string.appearance_editors_code_editor_legacy));
             ConfigActivity.setSetting(ConfigActivity.SETTING_LEGACY_CODE_EDITOR, isLegacy);
             return true;
         });
         
         editorsPreferenceGroup.addPreference(codeEditorVersionPreference);
    }
}