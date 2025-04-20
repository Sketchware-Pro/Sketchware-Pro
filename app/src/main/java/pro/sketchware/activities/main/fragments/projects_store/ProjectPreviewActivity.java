package pro.sketchware.activities.main.fragments.projects_store;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;

import androidx.activity.EdgeToEdge;
import androidx.core.content.ContextCompat;
import androidx.transition.ChangeBounds;
import androidx.transition.TransitionManager;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.transition.platform.MaterialContainerTransform;
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback;
import com.google.gson.Gson;

import java.util.ArrayList;

import pro.sketchware.activities.main.fragments.projects_store.adapters.ProjectScreenshotsAdapter;
import pro.sketchware.activities.main.fragments.projects_store.api.ProjectModel;
import pro.sketchware.databinding.FragmentStoreProjectPreviewBinding;
import pro.sketchware.utility.UI;

public class ProjectPreviewActivity extends BaseAppCompatActivity {
    private FragmentStoreProjectPreviewBinding binding;
    private ProjectModel.Project project;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);

        binding = FragmentStoreProjectPreviewBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);

        setContentView(binding.getRoot());

        binding.getRoot().setTransitionName("project_preview");
        setEnterSharedElementCallback(new MaterialContainerTransformSharedElementCallback());
        getWindow().setSharedElementEnterTransition(new MaterialContainerTransform().addTarget(binding.getRoot()).setDuration(400L));
        getWindow().setSharedElementReturnTransition(new MaterialContainerTransform().addTarget(binding.getRoot()).setDuration(400L));

        loadProjectData(getIntent().getExtras());

        initializeLogic();
    }

    private void initializeLogic() {
        binding.projectTitle.setText(project.getTitle());
        binding.projectAuthor.setText(project.getUserName());
        binding.projectDownloads.setText(project.getDownloads());
        binding.projectLikes.setText(project.getLikes());
        binding.projectComments.setText(project.getComments());
        binding.projectSize.setText(project.getProjectSize());
        binding.projectCategory.setText(project.getCategory());
        binding.btnOpenProject.setOnClickListener(v -> openProject());
        binding.projectDescription.setText(project.getDescription());
        binding.projectDescription.setMaxLines(4);
        binding.seeMore.setOnClickListener(v -> {
            if (binding.projectDescription.getMaxLines() == 4) {
                ChangeBounds changeBounds = new ChangeBounds();

                changeBounds.addTarget(binding.projectDescription);
                changeBounds.setDuration(300);
                TransitionManager.beginDelayedTransition(binding.getRoot(), changeBounds);

                binding.projectDescription.setMaxLines(Integer.MAX_VALUE);
                binding.seeMore.setText("See less");
            } else {
                ChangeBounds changeBounds = new ChangeBounds();

                changeBounds.addTarget(binding.projectDescription);
                changeBounds.setDuration(300);
                TransitionManager.beginDelayedTransition(binding.getRoot(), changeBounds);

                binding.projectDescription.setMaxLines(4);
                binding.seeMore.setText("See more");
            }
        });
        UI.loadImageFromUrl(binding.projectImage, project.getIcon());
        UI.loadImageFromUrl(binding.screenshot1, project.getScreenshot1());
        UI.loadImageFromUrl(binding.screenshot2, project.getScreenshot2());
        UI.loadImageFromUrl(binding.screenshot3, project.getScreenshot3());

        binding.collapsingToolbar.setTitle(project.getTitle());
        binding.collapsingToolbar.setExpandedTitleTextColor(ContextCompat.getColorStateList(this, android.R.color.transparent));
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        binding.toolbar.setNavigationOnClickListener(v -> finishAfterTransition());

        ArrayList<String> screenshots = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
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

    private void loadProjectData(Bundle bundle) {
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
