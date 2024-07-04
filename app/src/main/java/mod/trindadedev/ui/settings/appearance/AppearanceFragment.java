package mod.trindadedev.ui.settings.appearance;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.ChipGroup;
import com.google.android.material.transition.MaterialSharedAxis;

import com.sketchware.remod.R;
import com.sketchware.remod.databinding.PrefencesContentAppbarBinding;
import com.sketchware.remod.databinding.PreferenceSketchwareThemeBinding;
import com.besome.sketch.editor.property.PropertySwitchItem;

import mod.hey.studios.util.Helper;
import mod.hilal.saif.activities.tools.ConfigActivity;

import mod.trindadedev.manage.theme.ThemeManager;

import mod.trindadedev.ui.preferences.PreferenceGroup;
import mod.trindadedev.ui.preferences.Preference;
import mod.trindadedev.ui.preferences.PreferencePopup;

public class AppearanceFragment extends Fragment {
    private PrefencesContentAppbarBinding binding;
    private PreferenceSketchwareThemeBinding skThemeBinding;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
        setReturnTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
        setReenterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
    }
    
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

        int theme = ThemeManager.getSketchwareTheme(requireContext());
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
         
         skThemeBinding.preferenceSwitchDynamic.setChecked(ThemeManager.getDynamicTheme(context));
         
         skThemeBinding.preferenceSwitchDynamicContent.setOnClickListener(v -> {
             skThemeBinding.preferenceSwitchDynamic.setChecked(!skThemeBinding.preferenceSwitchDynamic.isChecked());
             ThemeManager.setUseMonet(context, skThemeBinding.preferenceSwitchDynamic.isChecked());
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
         
         PropertySwitchItem useMd3InViewPane = new PropertySwitchItem(context);
         useMd3InViewPane.setName(getString(R.string.appearance_viewpane_theme));
         useMd3InViewPane.setDesc(getString(R.string.appearance_viewpane_theme_description));
         useMd3InViewPane.setValue(ThemeManager.getViewPaneUseMd3(context));
         useMd3InViewPane.setSwitchChangedListener((buttonView, isChecked) -> {
              applyMD3Theme(isChecked);
         });
        
         editorsPreferenceGroup.addPreference(useMd3InViewPane);
    }
    
    private void goToFragment (Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
    
    private void applyMD3Theme(boolean value) {
        ThemeManager.applyTheme(requireContext(), value);
    }
}