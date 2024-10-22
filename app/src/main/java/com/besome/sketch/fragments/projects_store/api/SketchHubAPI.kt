package com.besome.sketch.fragments.projects_store.api

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import mod.ilyasse.utils.Network
import java.util.function.Consumer

class SketchHubAPI(private val apiKey: String) {
    private val network = Network()
    private val gson = Gson()

    fun getEditorsChoicerProjects(pageNumber: Int, consumer: Consumer<ProjectModel?>) {
        getProjects("editor_choice", pageNumber, consumer)
    }

    fun getMostDownloadedProjects(pageNumber: Int, consumer: Consumer<ProjectModel?>) {
        getProjects("most_downloaded", pageNumber, consumer)
    }

    fun getRecentProjects(pageNumber: Int, consumer: Consumer<ProjectModel?>) {
        getProjects(null, pageNumber, consumer)
    }

    private fun getProjects(scope: String?, pageNumber: Int, consumer: Consumer<ProjectModel?>) {
        val formData: MutableMap<String, String> = HashMap()
        formData["api_key"] = apiKey
        formData["page_number"] = pageNumber.toString()
        if (scope != null) formData["scope"] = scope

        network.postForm(
            BASE_URL + GET_PROJECTS_ENDPOINT, null, formData
        ) { response: String? ->
            if (response != null && !response.isEmpty()) {
                try {
                    val projectModel =
                        gson.fromJson(response, ProjectModel::class.java)
                    consumer.accept(projectModel)
                } catch (e: JsonSyntaxException) {
                    Log.e(TAG, "Failed to parse response", e)
                    consumer.accept(null)
                }
            } else {
                consumer.accept(null)
            }
        }
    }

    companion object {
        private const val TAG = "SketchHubAPI"
        private const val BASE_URL = "https://sketchub.in/api/v3/"
        private const val GET_PROJECTS_ENDPOINT = "get_project_list"
    }
}
