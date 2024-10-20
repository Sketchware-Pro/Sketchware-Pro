package com.besome.sketch.fragments.projects_store.adapters;

import static mod.ilyasse.utils.UI.loadImageFromUrl;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.fragments.projects_store.api.ProjectModel;
import com.sketchware.remod.databinding.ViewStoreProjectItemBinding;

import java.util.List;

public class StoreProjectsAdapter extends RecyclerView.Adapter<StoreProjectsAdapter.ViewHolder> {

    private final List<ProjectModel.Project> projects;

    public StoreProjectsAdapter(List<ProjectModel.Project> projects) {
        this.projects = projects;
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
        holder.binding.projectDesc.setText(project.getDescription());
        loadImageFromUrl(holder.binding.projectImage, project.getIcon());

        holder.itemView.setScaleX(1f);
        holder.itemView.setScaleY(1f);

        holder.binding.getRoot().setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://web.sketchub.in/p/" + project.getId()));
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (projects == null) {
            return 0;
        }
        return projects.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewStoreProjectItemBinding binding;

        public ViewHolder(ViewStoreProjectItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}