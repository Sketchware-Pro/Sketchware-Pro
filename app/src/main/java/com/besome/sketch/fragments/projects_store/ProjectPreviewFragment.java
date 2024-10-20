package com.besome.sketch.fragments.projects_store;

import static mod.ilyasse.utils.UI.loadImageFromUrl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.besome.sketch.fragments.projects_store.adapters.ProjectScreenshotsAdapter;
import com.besome.sketch.fragments.projects_store.api.ProjectModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.gson.Gson;
import com.sketchware.remod.databinding.FragmentStoreProjectPreviewBinding;

import java.util.ArrayList;

public class ProjectPreviewFragment extends BottomSheetDialogFragment {
    private FragmentStoreProjectPreviewBinding binding;
    private ProjectModel.Project project;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentStoreProjectPreviewBinding.inflate(inflater, container, false);
        initializeLogic();
        return binding.getRoot();
    }

    private void initializeLogic() {
        loadProjectData();

        binding.projectTitle.setText(project.getTitle());
        binding.projectDownloads.setText(project.getDownloads());
        binding.projectLikes.setText(project.getLikes());
        binding.projectComments.setText(project.getComments());
        binding.projectSize.setText(project.getProjectSize());
        binding.projectCategory.setText(project.getCategory());
        binding.btnOpenProject.setOnClickListener(v -> openProject());
        loadImageFromUrl(binding.projectImage, project.getIcon());
        loadImageFromUrl(binding.screenshot1, project.getScreenshot1());
        loadImageFromUrl(binding.screenshot2, project.getScreenshot2());
        loadImageFromUrl(binding.screenshot3, project.getScreenshot3());

        ArrayList<String> screenshots = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String screenshot = getScreenshot(i);
            if (screenshot != null && !screenshot.isEmpty()) {
                screenshots.add(screenshot);
            }
        }

        binding.projectScreenshots.setAdapter(new ProjectScreenshotsAdapter(screenshots));
    }

    private String getScreenshot(int index) {
        return switch (index) {
            case 0 -> project.getScreenshot1();
            case 1 -> project.getScreenshot2();
            case 2 -> project.getScreenshot3();
            case 3 -> project.getScreenshot4();
            case 4 -> project.getScreenshot5();
            default -> null;
        };
    }

    private void loadProjectData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            String json = bundle.getString("project_json");
            project = new Gson().fromJson(json, ProjectModel.Project.class);
        }
    }

    private void openProject() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://web.sketchub.in/p/" + project.getId()));
        startActivity(intent);
    }
}
