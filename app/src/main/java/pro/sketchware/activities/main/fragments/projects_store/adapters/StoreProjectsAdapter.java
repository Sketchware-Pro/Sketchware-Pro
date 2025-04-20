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
import pro.sketchware.databinding.ViewStoreProjectItemBinding;

public class StoreProjectsAdapter extends RecyclerView.Adapter<StoreProjectsAdapter.ViewHolder> {

    private final List<ProjectModel.Project> projects;
    private final FragmentActivity context;
    private final Gson gson = new Gson();

    public StoreProjectsAdapter(List<ProjectModel.Project> projects, FragmentActivity context) {
        this.projects = projects;
        this.context = context;
    }

    @NonNull
    @Override
    public StoreProjectsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewStoreProjectItemBinding binding = ViewStoreProjectItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(StoreProjectsAdapter.ViewHolder holder, int position) {
        ProjectModel.Project project = projects.get(position);

        holder.binding.projectTitle.setText(project.getTitle());
        holder.binding.projectLikes.setText(project.getLikes());
        holder.binding.projectDownloads.setText(project.getDownloads());
        loadImageFromUrl(holder.binding.projectImage, project.getIcon());

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
        private final ViewStoreProjectItemBinding binding;

        public ViewHolder(ViewStoreProjectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
