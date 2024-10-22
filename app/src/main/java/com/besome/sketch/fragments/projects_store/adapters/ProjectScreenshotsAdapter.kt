package com.besome.sketch.fragments.projects_store.adapters;

import static mod.ilyasse.utils.UI.loadImageFromUrl;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sketchware.remod.databinding.ViewStoreProjectScreenshotBinding;

import java.util.List;

public class ProjectScreenshotsAdapter extends RecyclerView.Adapter<ProjectScreenshotsAdapter.ViewHolder> {

    private final List<String> screenshots;

    public ProjectScreenshotsAdapter(List<String> screenshots) {
        this.screenshots = screenshots;
    }

    @NonNull
    @Override
    public ProjectScreenshotsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewStoreProjectScreenshotBinding binding = ViewStoreProjectScreenshotBinding.inflate(inflater, parent, false);
        return new ProjectScreenshotsAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ProjectScreenshotsAdapter.ViewHolder holder, int position) {
        String screenshot = screenshots.get(position);
        loadImageFromUrl(holder.binding.screenshot, screenshot);
    }

    @Override
    public int getItemCount() {
        if (screenshots == null) {
            return 0;
        }
        return screenshots.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewStoreProjectScreenshotBinding binding;

        public ViewHolder(ViewStoreProjectScreenshotBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
