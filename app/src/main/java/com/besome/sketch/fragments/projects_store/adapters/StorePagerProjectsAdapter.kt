package com.besome.sketch.fragments.projects_store.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.besome.sketch.fragments.projects_store.ProjectPreviewActivity
import com.besome.sketch.fragments.projects_store.api.ProjectModel
import com.google.gson.Gson
import com.sketchware.remod.databinding.ViewStoreProjectPagerItemBinding
import mod.ilyasse.utils.UI

class StorePagerProjectsAdapter(
    private val projects: List<ProjectModel.Project>?,
    private val context: FragmentActivity
) :
    RecyclerView.Adapter<StorePagerProjectsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewStoreProjectPagerItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects!![position]

        holder.binding.projectTitle.text = project.title
        holder.binding.projectDesc.text = project.description
        UI.loadImageFromUrl(holder.binding.projectImage, project.icon)
        UI.loadImageFromUrl(holder.binding.screenshot1, project.screenshot1)
        UI.loadImageFromUrl(holder.binding.screenshot2, project.screenshot2)
        UI.loadImageFromUrl(holder.binding.screenshot3, project.screenshot3)

        holder.itemView.scaleX = 1f
        holder.itemView.scaleY = 1f

        holder.binding.root.setOnClickListener { v: View? -> openProject(project) }
    }

    override fun getItemCount(): Int {
        if (projects == null) {
            return 0
        }
        return projects.size
    }

    class ViewHolder(val binding: ViewStoreProjectPagerItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val gson = Gson()

    private fun openProject(project: ProjectModel.Project) {
        val fm = context.supportFragmentManager

        if (fm.findFragmentByTag("project_preview") == null) {
            val bundle = Bundle()
            bundle.putString("project_json", gson.toJson(project))

            val intent = Intent(context, ProjectPreviewActivity::class.java)
            intent.putExtras(bundle)
            context.startActivity(intent)
        }
    }
}
