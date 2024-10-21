package com.besome.sketch.fragments.projects_store.adapters;

import static mod.ilyasse.utils.UI.loadImageFromUrl;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.fragments.projects_store.ProjectPreviewFragment;
import com.besome.sketch.fragments.projects_store.api.ProjectModel;
import com.google.gson.Gson;
import com.sketchware.remod.databinding.ViewStoreProjectPagerItemBinding;

import java.util.List;

public class StorePagerProjectsAdapter extends RecyclerView.Adapter<StorePagerProjectsAdapter.ViewHolder> {

    private final List<ProjectModel.Project> projects;
    private final FragmentActivity context;

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

        holder.binding.getRoot().setOnClickListener(v -> openProject(project));
    }

    @Override
    public int getItemCount() {
        if (projects == null) {
            return 0;
        }
        return projects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewStoreProjectPagerItemBinding binding;

        public ViewHolder(ViewStoreProjectPagerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private void openProject(ProjectModel.Project project) {
        var fm = context.getSupportFragmentManager();

        if (fm.findFragmentByTag("project_preview") == null) {
            var bundle = new Bundle();
            bundle.putString("project_json", new Gson().toJson(project));

            var bottomSheet = new ProjectPreviewFragment();
            bottomSheet.setArguments(bundle);
            bottomSheet.show(fm, "project_preview");
        }
    }
}