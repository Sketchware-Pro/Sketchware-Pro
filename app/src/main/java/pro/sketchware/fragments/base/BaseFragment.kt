package pro.sketchware.fragments.base

import android.os.Bundle
import android.view.View

import androidx.fragment.app.Fragment
import androidx.core.view.WindowInsetsCompat

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.transition.MaterialSharedAxis
import com.sketchware.remod.R

import dev.chrisbanes.insetter.Insetter

import mod.hey.studios.util.Helper

open class BaseFragment() : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    open fun openFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.settings_fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }

    open fun configureToolbar(toolbar: MaterialToolbar) {
        val onBackPressedDispatcher = requireActivity().onBackPressedDispatcher
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    
    open fun handleInsetts(root: View) {
        Insetter.builder()
            .padding(WindowInsetsCompat.Type.navigationBars())
            .applyToView(root)
    }
}
