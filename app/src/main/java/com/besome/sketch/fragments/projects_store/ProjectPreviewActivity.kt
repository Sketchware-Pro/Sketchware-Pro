package com.besome.sketch.fragments.projects_store

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.besome.sketch.fragments.projects_store.adapters.ProjectScreenshotsAdapter
import com.besome.sketch.fragments.projects_store.api.ProjectModel
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.google.gson.Gson
import com.sketchware.remod.databinding.FragmentStoreProjectPreviewBinding
import mod.ilyasse.utils.UI

class ProjectPreviewActivity : AppCompatActivity() {
    private lateinit var binding: FragmentStoreProjectPreviewBinding
    private lateinit var project: ProjectModel.Project

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentStoreProjectPreviewBinding.inflate(
            layoutInflater
        )

        enableEdgeToEdge()

        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)

        binding.root.transitionName = "project_preview"
        setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
        window.sharedElementEnterTransition = MaterialContainerTransform().apply {
            addTarget(binding.root)
            duration = 400L
        }
        window.sharedElementReturnTransition = MaterialContainerTransform().apply {
            addTarget(binding.root)
            duration = 400L
        }

        setContentView(binding.root)

        loadProjectData(intent.extras)

        initializeLogic()
    }

    private fun initializeLogic() {
        binding.apply {
            projectTitle.text = project.title
            projectAuthor.text = project.userName
            projectDownloads.text = project.downloads
            projectLikes.text = project.likes
            projectComments.text = project.comments
            projectSize.text = project.projectSize
            projectCategory.text = project.category
            btnOpenProject.setOnClickListener { _ -> openProject() }
            projectDescription.text = project.description
            projectDescription.maxLines = 4
            seeMore.setOnClickListener {
                if (projectDescription.maxLines == 4) {
                    projectDescription.maxLines = Int.MAX_VALUE
                    seeMore.text = "See less"
                } else {
                    projectDescription.maxLines = 4
                    seeMore.text = "See more"
                }
            }
            UI.loadImageFromUrl(projectImage, project.icon)
            UI.loadImageFromUrl(screenshot1, project.screenshot1)
            UI.loadImageFromUrl(screenshot2, project.screenshot2)
            UI.loadImageFromUrl(screenshot3, project.screenshot3)

            collapsingToolbar.title = project.title
            collapsingToolbar.setExpandedTitleTextColor(ContextCompat.getColorStateList(this@ProjectPreviewActivity, android.R.color.transparent)!!)
            setSupportActionBar(toolbar)
            getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()!!.setDisplayShowHomeEnabled(true)

            toolbar.setNavigationOnClickListener { _ -> finishAfterTransition() }
        }

        val screenshots = ArrayList<String>()
        for (i in 0..4) {
            val screenshot = getScreenshot(i)
            if (!screenshot.isNullOrEmpty()) {
                screenshots.add(screenshot)
            }
        }

        binding.projectScreenshots.adapter = ProjectScreenshotsAdapter(screenshots)
    }

    private fun getScreenshot(index: Int): String? {
        return when (index) {
            0 -> project.screenshot1
            1 -> project.screenshot2
            2 -> project.screenshot3
            3 -> project.screenshot4
            4 -> project.screenshot5
            else -> null
        }
    }

    private fun loadProjectData(bundle: Bundle?) {
        if (bundle != null) {
            val json = bundle.getString("project_json")
            project = Gson().fromJson(json, ProjectModel.Project::class.java)
        }
    }

    private fun openProject() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse("https://web.sketchub.in/p/" + project.id))
        startActivity(intent)
    }
}
