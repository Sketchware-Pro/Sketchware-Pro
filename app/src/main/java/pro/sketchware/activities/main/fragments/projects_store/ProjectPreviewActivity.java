package pro.sketchware.activities.main.fragments.projects_store;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import androidx.core.widget.NestedScrollView;

import com.besome.sketch.lib.base.BaseAppCompatActivity;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import pro.sketchware.activities.main.fragments.projects_store.adapters.ProjectScreenshotsAdapter;
import pro.sketchware.activities.main.fragments.projects_store.api.ProjectModel;
import pro.sketchware.databinding.FragmentStoreProjectPreviewBinding;
import pro.sketchware.utility.SketchwareUtil;
import pro.sketchware.utility.UI;

public class ProjectPreviewActivity extends BaseAppCompatActivity {
    private static final long TITLE_CONTAINER_FADE_DURATION = 150L;

    private FragmentStoreProjectPreviewBinding binding;
    private ProjectModel.Project project;
    private boolean isTitleContainerShown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        enableEdgeToEdgeNoContrast();
        super.onCreate(savedInstanceState);

        binding = FragmentStoreProjectPreviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadProjectData(getIntent().getExtras());
    }

    private void loadProjectData(Bundle bundle) {
        if (bundle == null) return;

        String json = bundle.getString("project_json");
        project = new Gson().fromJson(json, ProjectModel.Project.class);

        binding.name.setText(project.getTitle());
        binding.author.setText(project.getUserName());
        binding.description.setText(project.getDescription());

        String whatIsNew = project.getWhatsnew();
        if (whatIsNew.isEmpty()) {
            binding.cardWhatIsNew.setVisibility(View.GONE);
        } else {
            binding.cardWhatIsNew.setVisibility(View.VISIBLE);
            binding.whatIsNew.setText(whatIsNew);
        }

        if (project.getIsEditorChoice().equals("1")) {
            addChip("Editor's Choice");
        }

        if (project.getIsVerified().equals("1")) {
            addChip("Verified");
        }

        addChip(project.getCategory());

        binding.downloads.setText("Downloads: " + project.getDownloads());
        binding.filesize.setText("Size: " + project.getProjectSize());
        binding.timestamp.setText("Released: " + DateFormat.getDateInstance().format(new Date(Long.parseLong(project.getPublishedTimestamp()))));
        binding.btnComments.setOnClickListener(v -> openCommentsSheet());
        binding.btnDownload.setOnClickListener(v -> SketchwareUtil.toastError("Downloading projects is unavailable right now!"));
        binding.btnOpenIn.setOnClickListener(v -> openProject());
        binding.btnBack.setOnClickListener(v -> finish());

        binding.toolbarTitle.setSelected(true);
        binding.toolbarTitle.setText(project.getTitle());
        binding.toolbarSubtitle.setText(project.getUserName());

        ArrayList<String> screenshots = new ArrayList<>();
        for (int i = 0; i <= 4; i++) {
            String screenshot = getScreenshot(i);
            if (screenshot != null && !screenshot.isEmpty()) {
                screenshots.add(screenshot);
            }
        }

        binding.screenshots.setAdapter(new ProjectScreenshotsAdapter(screenshots));

        UI.loadImageFromUrl(binding.icon, project.getIcon());
        UI.addSystemWindowInsetToPadding(binding.content, true, true, true, true);
        UI.addSystemWindowInsetToMargin(binding.buttonsContainer, true, false, true, true);
        UI.addSystemWindowInsetToPadding(binding.topScrim, false, true, false, false);
        UI.addSystemWindowInsetToPadding(binding.toolbar, true, true, true, false);

        binding.scrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, v1, v2, v3, v4) -> {
            int[] location = new int[2];
            binding.author.getLocationOnScreen(location);

            if (location[1] + binding.author.getHeight() + UI.getStatusBarHeight(ProjectPreviewActivity.this) < binding.toolbar.getHeight()) {
                if (isTitleContainerShown) return;
                isTitleContainerShown = true;

                binding.topScrim.animate().alpha(1f).setDuration(TITLE_CONTAINER_FADE_DURATION).start();
                binding.toolbarTitleContainer.animate()
                        .alpha(1f)
                        .translationY(0f)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(TITLE_CONTAINER_FADE_DURATION)
                        .withStartAction(() -> binding.toolbarTitleContainer.setVisibility(View.VISIBLE))
                        .start();
            } else {
                if (!isTitleContainerShown) return;
                isTitleContainerShown = false;

                binding.topScrim.animate().alpha(0f).setDuration(TITLE_CONTAINER_FADE_DURATION).start();
                binding.toolbarTitleContainer.animate()
                        .translationY(24f)
                        .alpha(0f)
                        .setInterpolator(new LinearInterpolator())
                        .setDuration(TITLE_CONTAINER_FADE_DURATION)
                        .withEndAction(() -> binding.toolbarTitleContainer.setVisibility(View.INVISIBLE))
                        .start();
            }
        });
    }

    private void addChip(String name) {
        Chip chip = new Chip(binding.chipsContainer.getContext());
        chip.setText(name);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -1);
        params.setMarginEnd(SketchwareUtil.dpToPx(12f));
        binding.chipsContainer.addView(chip, params);
    }

    private void openCommentsSheet() {
        CommentsBottomSheet sheet = new CommentsBottomSheet();
        sheet.show(getSupportFragmentManager(), /* tag= */ CommentsBottomSheet.class.getSimpleName());
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

    private void openProject() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://web.sketchub.in/p/" + project.getId()));
        startActivity(intent);
    }
}
