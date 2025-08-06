package com.besome.sketch.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.besome.sketch.beans.ProjectFileBean;

import a.a.a.jC;
import pro.sketchware.databinding.FileSelectorPopupSelectJavaListItemBinding;
import pro.sketchware.listeners.ItemClickListener;

public class JavaFileAdapter extends RecyclerView.Adapter<JavaFileAdapter.ViewHolder> {

    private final String sc_id;
    private ItemClickListener<ProjectFileBean> listener;

    public JavaFileAdapter(String sc_id) {
        this.sc_id = sc_id;
    }

    public void setOnItemClickListener(ItemClickListener<ProjectFileBean> listener) {
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(jC.b(sc_id).b().get(position));
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        var inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(FileSelectorPopupSelectJavaListItemBinding.inflate(inflater, parent, false));
    }

    @Override
    public int getItemCount() {
        return jC.b(sc_id).b().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FileSelectorPopupSelectJavaListItemBinding binding;

        public ViewHolder(FileSelectorPopupSelectJavaListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ProjectFileBean projectFile) {
            binding.tvFilename.setVisibility(View.VISIBLE);
            binding.tvLinkedFilename.setVisibility(View.VISIBLE);
            binding.tvFilename.setText(projectFile.getJavaName());
            binding.tvLinkedFilename.setText(projectFile.getXmlName());
            binding.getRoot().setOnClickListener(v -> {
                if (listener != null) {
                    listener.onItemClick(projectFile);
                }
            });
        }
    }
}
