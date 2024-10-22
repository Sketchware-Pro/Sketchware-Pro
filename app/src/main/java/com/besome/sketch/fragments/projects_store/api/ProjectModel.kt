package com.besome.sketch.fragments.projects_store.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProjectModel {
    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("total_pages")
    @Expose
    var totalPages: String? = null

    @SerializedName("projects")
    @Expose
    var projects: List<Project>? = null

    class Project {
        @SerializedName("id")
        @Expose
        var id: String? = null

        @SerializedName("title")
        @Expose
        var title: String? = null

        @SerializedName("description")
        @Expose
        var description: String? = null

        @SerializedName("whatsnew")
        @Expose
        var whatsnew: String? = null

        @SerializedName("category")
        @Expose
        var category: String? = null

        @SerializedName("project_type")
        @Expose
        var projectType: String? = null

        @SerializedName("demo_link")
        @Expose
        var demoLink: String? = null

        @SerializedName("video_url")
        @Expose
        var videoUrl: String? = null

        @SerializedName("icon")
        @Expose
        var icon: String? = null

        @SerializedName("screenshot1")
        @Expose
        var screenshot1: String? = null

        @SerializedName("screenshot2")
        @Expose
        var screenshot2: String? = null

        @SerializedName("screenshot3")
        @Expose
        var screenshot3: String? = null

        @SerializedName("screenshot4")
        @Expose
        var screenshot4: String? = null

        @SerializedName("screenshot5")
        @Expose
        var screenshot5: String? = null

        @SerializedName("project_size")
        @Expose
        var projectSize: String? = null

        @SerializedName("likes")
        @Expose
        var likes: String? = null

        @SerializedName("comments")
        @Expose
        var comments: String? = null

        @SerializedName("downloads")
        @Expose
        var downloads: String? = null

        @SerializedName("uid")
        @Expose
        var uid: String? = null

        @SerializedName("timestamp")
        @Expose
        var timestamp: String? = null

        @SerializedName("published_timestamp")
        @Expose
        var publishedTimestamp: String? = null

        @SerializedName("is_verified")
        @Expose
        var isVerified: String? = null

        @SerializedName("is_editor_choice")
        @Expose
        var isEditorChoice: String? = null

        @SerializedName("user_name")
        @Expose
        var userName: String? = null

        @SerializedName("user_profile_pic")
        @Expose
        var userProfilePic: String? = null

        @SerializedName("user_badge")
        @Expose
        var userBadge: String? = null
    }
}
