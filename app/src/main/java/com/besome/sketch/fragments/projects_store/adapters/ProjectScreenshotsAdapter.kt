package com.besome.sketch.fragments.projects_store.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sketchware.remod.databinding.ViewStoreProjectScreenshotBinding
import mod.ilyasse.utils.UI

class ProjectScreenshotsAdapter(private val screenshots: List<String>) :
    RecyclerView.Adapter<ProjectScreenshotsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewStoreProjectScreenshotBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val screenshot = screenshots[position]
        UI.loadImageFromUrl(holder.binding.screenshot, screenshot)
    }

    override fun getItemCount(): Int {
        return screenshots.size
    }

    class ViewHolder(val binding: ViewStoreProjectScreenshotBinding) :
        RecyclerView.ViewHolder(binding.root)
}
