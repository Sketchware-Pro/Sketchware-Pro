package pro.sketchware.fragments.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.OnBackPressedDispatcher;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.transition.MaterialSharedAxis;

import pro.sketchware.R;

import dev.chrisbanes.insetter.Insetter;

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
        setReturnTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));
        setReenterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false));
    }

    public void openFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
            .replace(R.id.settings_fragment_container, fragment)
            .addToBackStack(null)
            .commit();
    }

    public void configureToolbar(MaterialToolbar toolbar) {
        final OnBackPressedDispatcher onBackPressedDispatcher = requireActivity().getOnBackPressedDispatcher();
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressedDispatcher.onBackPressed();
        });
    }

    public void handleInsetts(View root) {
        Insetter.builder()
            .padding(WindowInsetsCompat.Type.navigationBars())
            .applyToView(root);
    }
}

