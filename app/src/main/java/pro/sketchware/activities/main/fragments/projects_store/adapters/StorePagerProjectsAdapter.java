package pro.sketchware.activities.main.fragments.projects_store.adapters;

import static pro.sketchware.utility.UI.loadImageFromUrl;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import pro.sketchware.activities.main.fragments.projects_store.ProjectPreviewActivity;
import pro.sketchware.activities.main.fragments.projects_store.api.ProjectModel;
import pro.sketchware.databinding.ViewStoreProjectPagerItemBinding;

public class StorePagerProjectsAdapter extends RecyclerView.Adapter<StorePagerProjectsAdapter.ViewHolder> {

    private final List<ProjectModel.Project> projects;
    private final FragmentActivity context;
    private final Gson gson = new Gson();

    public StorePagerProjectsAdapter(List<ProjectModel.Project> projects, FragmentActivity context) {
        this.projects = projects;
        this.context = context;
    }

    @NonNull
    @Override
    public StorePagerProjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewStoreProjectPagerItemBinding binding = ViewStoreProjectPagerItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(StorePagerProjectsAdapter.ViewHolder holder, int position) {
        ProjectModel.Project project = projects.get(position);

        holder.binding.projectTitle.setText(project.getTitle());
        holder.binding.projectDesc.setText(project.getDescription());
        loadImageFromUrl(holder.binding.projectImage, project.getIcon());
        loadImageFromUrl(holder.binding.screenshot1, project.getScreenshot1());
        loadImageFromUrl(holder.binding.screenshot2, project.getScreenshot2());
        loadImageFromUrl(holder.binding.screenshot3, project.getScreenshot3());

        holder.itemView.setScaleX(1f);
        holder.itemView.setScaleY(1f);

        holder.binding.getRoot().setOnClickListener(v -> openProject(project, v));
    }

    @Override
    public int getItemCount() {
        if (projects == null) {
            return 0;
        }
        return projects.size();
    }

    private void openProject(ProjectModel.Project project, View view) {
        var fm = context.getSupportFragmentManager();

        if (fm.findFragmentByTag("project_preview") == null) {
            var bundle = new Bundle();
            bundle.putString("project_json", gson.toJson(project));

            var intent = new Intent(context, ProjectPreviewActivity.class);
            intent.putExtras(bundle);
            var options = ActivityOptions.makeSceneTransitionAnimation(
                    context,
                    view,
                    "project_preview"
            );
            context.startActivity(intent, options.toBundle());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewStoreProjectPagerItemBinding binding;

        public ViewHolder(ViewStoreProjectPagerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
