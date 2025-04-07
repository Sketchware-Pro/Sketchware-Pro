package pro.sketchware.activities.about.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import mod.hey.studios.util.Helper;
import pro.sketchware.R;
import pro.sketchware.activities.about.adapters.CommitAdapter;
import pro.sketchware.activities.about.models.AboutAppViewModel;
import pro.sketchware.activities.about.models.AboutResponseModel;
import pro.sketchware.databinding.FragmentBetaChangesBinding;
import pro.sketchware.utility.Network;
import pro.sketchware.utility.SketchwareUtil;

public class BetaChangesFragment extends Fragment {

    private FragmentBetaChangesBinding binding;
    private AboutAppViewModel aboutAppData;
    private CommitAdapter adapter;

    private int nextPage = 1;
    private boolean isLoading = false;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBetaChangesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        aboutAppData = new ViewModelProvider(requireActivity()).get(AboutAppViewModel.class);

        adapter = new CommitAdapter(aboutAppData);
        binding.list.setAdapter(adapter);

        aboutAppData.getCommitDetailsList().observe(getViewLifecycleOwner(), commitDetailsArrayList -> {
            if (commitDetailsArrayList != null) {
                adapter.hideLoading();
                adapter.addItems(commitDetailsArrayList);
            }
        });

        binding.list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1) && !isLoading) {
                    adapter.showLoading();
                    loadCommitData(nextPage);
                }
            }
        });

        loadCommitData(nextPage);
    }

    private void loadCommitData(int page) {
        if (isLoading) return;

        isLoading = true;
        String commitsUrl = String.format(Helper.getResString(R.string.link_github_commits_url),
                100, // 100 ==> commits per page
                page);

        try {

            new Network().get(commitsUrl, response -> {
                isLoading = false;

                if (response != null) {
                    Gson gson = new Gson();
                    AboutResponseModel.CommitDetails[] commitDetailsArray = gson.fromJson(response, AboutResponseModel.CommitDetails[].class);
                    if (commitDetailsArray != null) {
                        aboutAppData.setCommitDetailsList(new ArrayList<>(Arrays.asList(commitDetailsArray)));
                        nextPage++;
                    }
                }
            });
        } catch (Exception ignored) {
            SketchwareUtil.toastError(Helper.getResString(R.string.github_api_error_message));
        }
    }
}
