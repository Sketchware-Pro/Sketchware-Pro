package com.besome.sketch.fragments.projects_store.adapters

import android.app.ActivityOptions
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
import com.sketchware.remod.databinding.ViewStoreProjectItemBinding
import mod.ilyasse.utils.UI

class StoreProjectsAdapter(
    private val projects: List<ProjectModel.Project>?,
    private val context: FragmentActivity
) :
    RecyclerView.Adapter<StoreProjectsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ViewStoreProjectItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val project = projects!![position]

        holder.binding.projectTitle.text = project.title
        holder.binding.projectLikes.text = project.likes
        holder.binding.projectDownloads.text = project.downloads
        UI.loadImageFromUrl(holder.binding.projectImage, project.icon)

        holder.itemView.scaleX = 1f
        holder.itemView.scaleY = 1f

        holder.binding.root.setOnClickListener { v: View -> openProject(project, v) }
    }

    override fun getItemCount(): Int {
        if (projects == null) {
            return 0
        }
        return projects.size
    }

    class ViewHolder(val binding: ViewStoreProjectItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val gson = Gson()

    private fun openProject(project: ProjectModel.Project, view: View) {
        val fm = context.supportFragmentManager

        if (fm.findFragmentByTag("project_preview") == null) {
            val bundle = Bundle()
            bundle.putString("project_json", gson.toJson(project))

            val intent = Intent(context, ProjectPreviewActivity::class.java)
            intent.putExtras(bundle)
            val options = ActivityOptions.makeSceneTransitionAnimation(
                context,
                view,
                "project_preview"
            )
            context.startActivity(intent, options.toBundle())
        }
    }
}
