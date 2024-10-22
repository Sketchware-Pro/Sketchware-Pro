package com.besome.sketch.fragments.projects_store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.besome.sketch.fragments.projects_store.adapters.StorePagerProjectsAdapter
import com.besome.sketch.fragments.projects_store.adapters.StoreProjectsAdapter
import com.besome.sketch.fragments.projects_store.api.ProjectModel
import com.besome.sketch.fragments.projects_store.api.SketchHubAPI
import com.besome.sketch.fragments.projects_store.classes.CenterZoomListener
import com.besome.sketch.fragments.projects_store.classes.HorizontalItemDecoration
import com.sketchware.remod.BuildConfig
import com.sketchware.remod.R
import com.sketchware.remod.databinding.FragmentProjectsStoreBinding

class ProjectsStoreFragment : Fragment() {
    private lateinit var binding: FragmentProjectsStoreBinding
    private lateinit var sketchHubAPI: SketchHubAPI

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProjectsStoreBinding.inflate(inflater, container, false)
        sketchHubAPI = SketchHubAPI(BuildConfig.SKETCHUB_API_KEY)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.storeSideNote.isSelected = true
        setupRecyclerView(binding.editorsChoiceProjectsRecyclerView)
        fetchData()
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        recyclerView.addOnScrollListener(CenterZoomListener())

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.recycler_view_item_spacing)
        recyclerView.addItemDecoration(HorizontalItemDecoration(spacingInPixels))

        recyclerView.clipToPadding = false
        recyclerView.clipChildren = false

        val parent = recyclerView.parent
        if (parent is ViewGroup) {
            parent.clipChildren = false
            parent.clipToPadding = false
        }

        val sidePadding = resources.getDimensionPixelSize(R.dimen.recycler_view_item_spacing)
        recyclerView.setPadding(sidePadding, 0, sidePadding, 0)
    }

    private fun fetchData() {
        sketchHubAPI.getEditorsChoicerProjects(
            1
        ) { projectModel: ProjectModel? ->
            if (projectModel != null) {
                binding.editorsChoiceProjectsRecyclerView.adapter =
                    StorePagerProjectsAdapter(projectModel.projects, requireActivity())
            }
        }
        sketchHubAPI.getMostDownloadedProjects(
            1
        ) { projectModel: ProjectModel? ->
            if (projectModel != null) {
                binding.mostDownloadedProjectsRecyclerView.adapter =
                    StoreProjectsAdapter(projectModel.projects, requireActivity())
            }
        }
        sketchHubAPI.getRecentProjects(
            1
        ) { projectModel: ProjectModel? ->
            if (projectModel != null) {
                binding.recentProjectsRecyclerView.adapter =
                    StoreProjectsAdapter(projectModel.projects, requireActivity())
            }
        }
    }
}
