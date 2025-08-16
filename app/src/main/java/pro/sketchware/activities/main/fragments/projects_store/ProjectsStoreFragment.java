package pro.sketchware.activities.main.fragments.projects_store;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.material.transition.MaterialFadeThrough;

import pro.sketchware.BuildConfig;
import pro.sketchware.activities.main.fragments.projects_store.adapters.StorePagerProjectsAdapter;
import pro.sketchware.activities.main.fragments.projects_store.adapters.StoreProjectsAdapter;
import pro.sketchware.activities.main.fragments.projects_store.api.SketchubAPI;
import pro.sketchware.activities.main.fragments.projects_store.classes.CenterZoomListener;
import pro.sketchware.databinding.FragmentProjectsStoreBinding;
import pro.sketchware.utility.UI;

public class ProjectsStoreFragment extends Fragment {
    private FragmentProjectsStoreBinding binding;
    private SketchubAPI sketchubAPI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setEnterTransition(new MaterialFadeThrough());
        setReturnTransition(new MaterialFadeThrough());
        setExitTransition(new MaterialFadeThrough());
        setReenterTransition(new MaterialFadeThrough());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProjectsStoreBinding.inflate(inflater, container, false);
        sketchubAPI = new SketchubAPI(BuildConfig.SKETCHUB_API_KEY);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView(binding.editorsChoiceProjectsRecyclerView);
        fetchData();

        UI.addSystemWindowInsetToPadding(binding.textEditorsChoice, true, false, true, false);
        UI.addSystemWindowInsetToPadding(binding.editorsChoiceProjectsRecyclerView, true, false, true, false);
        UI.addSystemWindowInsetToPadding(binding.textRecent, true, false, true, false);
        UI.addSystemWindowInsetToPadding(binding.recentProjectsRecyclerView, true, false, true, false);
        UI.addSystemWindowInsetToPadding(binding.textMostDownloaded, true, false, true, false);
        UI.addSystemWindowInsetToPadding(binding.mostDownloadedProjectsRecyclerView, true, false, true, true);
        UI.addSystemWindowInsetToMargin(binding.cardWarning, true, false, true, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // avoid memory leaks
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new CenterZoomListener());

        recyclerView.setClipToPadding(false);
        recyclerView.setClipChildren(false);

        ViewParent parent = recyclerView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).setClipChildren(false);
            ((ViewGroup) parent).setClipToPadding(false);
        }
    }

    private void fetchData() {
        var activity = getActivity();
        sketchubAPI.getEditorsChoicerProjects(1, projectModel -> {
            if (projectModel != null) {
                binding.editorsChoiceProjectsRecyclerView.setAdapter(new StorePagerProjectsAdapter(projectModel.getProjects(), activity));
            }
        });
        sketchubAPI.getMostDownloadedProjects(1, projectModel -> {
            if (projectModel != null) {
                binding.mostDownloadedProjectsRecyclerView.setAdapter(new StoreProjectsAdapter(projectModel.getProjects(), activity));
            }
        });
        sketchubAPI.getRecentProjects(1, projectModel -> {
            if (projectModel != null) {
                binding.recentProjectsRecyclerView.setAdapter(new StoreProjectsAdapter(projectModel.getProjects(), activity));
            }
        });
    }
}
