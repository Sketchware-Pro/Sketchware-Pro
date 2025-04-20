package pro.sketchware.activities.about.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import pro.sketchware.activities.about.adapters.ChangeLogAdapter;
import pro.sketchware.activities.about.models.AboutAppViewModel;
import pro.sketchware.databinding.FragmentAboutAppBinding;

public class ChangeLogFragment extends Fragment {
    private FragmentAboutAppBinding binding;
    private AboutAppViewModel aboutAppData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAboutAppBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        aboutAppData = new ViewModelProvider(requireActivity()).get(AboutAppViewModel.class);

        initViews();
    }

    private void initViews() {
        aboutAppData.getChangelog().observe(getViewLifecycleOwner(), changeLogsArrayList -> {
            if (changeLogsArrayList != null && !changeLogsArrayList.isEmpty()) {
                binding.list.setAdapter(new ChangeLogAdapter(changeLogsArrayList));
            }
        });
    }
}
