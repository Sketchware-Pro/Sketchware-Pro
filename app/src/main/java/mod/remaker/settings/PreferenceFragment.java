package mod.remaker.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.sketchware.remod.R;
import com.sketchware.remod.databinding.PreferenceFragmentBinding;

public abstract class PreferenceFragment extends Fragment {
    protected abstract String getTitle(Context context);

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PreferenceFragmentBinding binding = PreferenceFragmentBinding.inflate(inflater, container, false);
        binding.toolbar.setTitle(getTitle(getContext()));

        return binding.getRoot();
    }
}
