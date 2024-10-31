package mod.trindadedev.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.transition.MaterialSharedAxis;
import pro.sketchware.R;

import mod.hey.studios.util.Helper;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
        setReturnTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
        setReenterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
    }

    public void configureToolbar(MaterialToolbar view) {
        view.setNavigationOnClickListener(Helper.getBackPressedClickListener(getActivity()));
    }

    public void openFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.settings_fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
