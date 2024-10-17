package mod.ilyasse.activities.about.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sketchware.remod.databinding.FragmentMajorChangesBinding;

import mod.ilyasse.activities.about.models.AboutAppViewModel;

public class MajorChangesFragment extends Fragment {
    private FragmentMajorChangesBinding binding;
    private AboutAppViewModel aboutAppData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMajorChangesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        aboutAppData = new ViewModelProvider(requireActivity()).get(AboutAppViewModel.class);

        initViews();
    }

    private void initViews() {

    }
}
