package mod.ilyasse.activities.about.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sketchware.remod.databinding.FragmentAboutAppBinding;

import mod.ilyasse.activities.about.adapters.AboutAdapters;
import mod.ilyasse.activities.about.models.AboutAppViewModel;

public class TeamFragment extends Fragment {
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
        aboutAppData.getTeamMembers().observe(getViewLifecycleOwner(), teamArrayList -> {
            if (teamArrayList != null && !teamArrayList.isEmpty()) {
                binding.list.setAdapter(new AboutAdapters.TeamRecyclerAdapter(teamArrayList));
            }
        });
    }
}
