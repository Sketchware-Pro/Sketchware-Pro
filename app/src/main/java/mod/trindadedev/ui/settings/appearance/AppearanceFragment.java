package mod.trindadedev.ui.settings.appearance;

import static mod.SketchwareUtil.toast;

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
        
        switch (ThemeManager.getCurrentTheme(requireContext())) {
            case ThemeManager.THEME_LIGHT:
                skThemeBinding.toggleThemes.check(R.id.themeLight);
                break;
            case ThemeManager.THEME_DARK:
                skThemeBinding.toggleThemes.check(R.id.themeDark);
                break;
            default:
                skThemeBinding.toggleThemes.check(R.id.themeSystem);
                break;
        }

        skThemeBinding.toggleThemes.setOnCheckedChangeListener((group, checkedId) -> {
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
    
    public void setupViews(Context context) {
         // Theme-related preferences
         PreferenceGroup themePreferenceGroup = new PreferenceGroup(context, getString(R.string.appearance_theme));
         binding.content.addView(themePreferenceGroup);
         
         skThemeBinding = PreferenceSketchwareThemeBinding.inflate(LayoutInflater.from(context), themePreferenceGroup, false);
         themePreferenceGroup.addPreference(skThemeBinding.getRoot());
         
         // Editor-related preferences
         PreferenceGroup editorsPreferenceGroup = new PreferenceGroup(context, getString(R.string.appearance_editors));
         binding.content.addView(editorsPreferenceGroup);
         
         PreferencePopup codeEditorVersionPreference = new PreferencePopup(context, getString(R.string.appearance_editors_code_editor_title), getString(R.string.appearance_editors_code_editor_description));
         codeEditorVersionPreference.addPopupMenuItem(getString(R.string.appearance_editors_code_editor_legacy));
         codeEditorVersionPreference.addPopupMenuItem(getString(R.string.appearance_editors_code_editor_default));
         codeEditorVersionPreference.setMenuListener(item -> {
             String itemName = item.getTitle().toString();
             if (itemName.equals(getString(R.string.appearance_editors_code_editor_default))) {
                 ConfigActivity.setSetting(ConfigActivity.SETTING_LEGACY_CODE_EDITOR, false);
                 return true;
             } else {
                 ConfigActivity.setSetting(ConfigActivity.SETTING_LEGACY_CODE_EDITOR, true);
                 return true;
             }
         });
         
         editorsPreferenceGroup.addPreference(codeEditorVersionPreference);
    }
}