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

import pro.sketchware.activities.main.fragments.projects_store.adapters.StorePagerProjectsAdapter;
import pro.sketchware.activities.main.fragments.projects_store.adapters.StoreProjectsAdapter;
import pro.sketchware.activities.main.fragments.projects_store.api.SketchHubAPI;
import pro.sketchware.activities.main.fragments.projects_store.classes.CenterZoomListener;
import pro.sketchware.activities.main.fragments.projects_store.classes.HorizontalItemDecoration;
import pro.sketchware.BuildConfig;
import pro.sketchware.R;
import pro.sketchware.databinding.FragmentProjectsStoreBinding;

public class ProjectsStoreFragment extends Fragment {
    private FragmentProjectsStoreBinding binding;
    private SketchHubAPI sketchHubAPI;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProjectsStoreBinding.inflate(inflater, container, false);
        sketchHubAPI = new SketchHubAPI(BuildConfig.SKETCHUB_API_KEY);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.storeSideNote.setSelected(true);
        setupRecyclerView(binding.editorsChoiceProjectsRecyclerView);
        fetchData();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new CenterZoomListener());

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.recycler_view_item_spacing);
        recyclerView.addItemDecoration(new HorizontalItemDecoration(spacingInPixels));

        recyclerView.setClipToPadding(false);
        recyclerView.setClipChildren(false);

        ViewParent parent = recyclerView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).setClipChildren(false);
            ((ViewGroup) parent).setClipToPadding(false);
        }

        int sidePadding = getResources().getDimensionPixelSize(R.dimen.recycler_view_item_spacing);
        recyclerView.setPadding(sidePadding, 0, sidePadding, 0);
    }

    private void fetchData() {
        sketchHubAPI.getEditorsChoicerProjects(1, projectModel -> {
            var activity = getActivity();
            if (projectModel != null && activity != null) {
                binding.editorsChoiceProjectsRecyclerView.setAdapter(new StorePagerProjectsAdapter(projectModel.getProjects(), activity));
            }
        });
        sketchHubAPI.getMostDownloadedProjects(1, projectModel -> {
            var activity = getActivity();
            if (projectModel != null && activity != null) {
                binding.mostDownloadedProjectsRecyclerView.setAdapter(new StoreProjectsAdapter(projectModel.getProjects(), activity));
            }
        });
        sketchHubAPI.getRecentProjects(1, projectModel -> {
            var activity = getActivity();
            if (projectModel != null && activity != null) {
                binding.recentProjectsRecyclerView.setAdapter(new StoreProjectsAdapter(projectModel.getProjects(), activity));
            }
        });
    }
}
