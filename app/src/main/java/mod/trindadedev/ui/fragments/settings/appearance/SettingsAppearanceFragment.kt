package mod.trindadedev.ui.fragments.settings.appearance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sketchware.remod.R
import com.sketchware.remod.databinding.FragmentSettingsAppearanceBinding
import mod.trindadedev.manage.theme.ThemeManager
import mod.trindadedev.ui.fragments.BaseFragment

class SettingsAppearanceFragment : BaseFragment() {

    private var _binding: FragmentSettingsAppearanceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsAppearanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureToolbar(binding.toolbar)
        configureThemeController()
    }

    private fun configureThemeController() {
        when (ThemeManager.getCurrentTheme(requireContext())) {
            ThemeManager.THEME_LIGHT -> binding.toggleThemes.check(R.id.theme_light)
            ThemeManager.THEME_DARK -> binding.toggleThemes.check(R.id.theme_dark)
            else -> binding.toggleThemes.check(R.id.theme_system)
        }

        binding.toggleThemes.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.theme_light -> ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_LIGHT)
                    R.id.theme_system -> ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_SYSTEM)
                    R.id.theme_dark -> ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_DARK)
                    else -> ThemeManager.applyTheme(requireContext(), ThemeManager.THEME_SYSTEM)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
