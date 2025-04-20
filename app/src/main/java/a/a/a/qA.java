package a.a.a;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedDispatcher;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.transition.MaterialSharedAxis;

import dev.chrisbanes.insetter.Insetter;
import pro.sketchware.R;

public class qA extends Fragment {

    public Activity a;
    @Deprecated
    public Context b;

    public qA() {
    }

    public void a() {
        if (getActivity() instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) getActivity()).h();
        }
    }

    public void addTask(MA var1) {
        if (getActivity() instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) getActivity()).addTask(var1);
        }
    }

    public void b() {
        if (getActivity() instanceof BaseAppCompatActivity) {
            ((BaseAppCompatActivity) getActivity()).k();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a = getActivity();
        b = a.getApplicationContext();
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
