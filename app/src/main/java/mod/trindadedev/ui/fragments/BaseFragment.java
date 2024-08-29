package mod.trindadedev.ui.fragments;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;

import com.sketchware.remod.R;

import mod.hey.studios.util.Helper;

public class BaseFragment extends Fragment {

    public void configureToolbar(MaterialToolbar view) {
        view.setNavigationOnClickListener(Helper.getBackPressedClickListener(getActivity()));
    }

    public void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.settings_fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
