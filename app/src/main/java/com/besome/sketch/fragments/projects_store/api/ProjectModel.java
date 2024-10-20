package com.besome.sketch.fragments.projects_store.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ProjectModel {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("total_pages")
    @Expose
    private String totalPages;
    @SerializedName("projects")
    @Expose
    private List<Project> projects;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public static class Project {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("whatsnew")
        @Expose
        private String whatsnew;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("project_type")
        @Expose
        private String projectType;
        @SerializedName("demo_link")
        @Expose
        private String demoLink;
        @SerializedName("video_url")
        @Expose
        private String videoUrl;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("screenshot1")
        @Expose
        private String screenshot1;
        @SerializedName("screenshot2")
        @Expose
        private String screenshot2;
        @SerializedName("screenshot3")
        @Expose
        private String screenshot3;
        @SerializedName("screenshot4")
        @Expose
        private String screenshot4;
        @SerializedName("screenshot5")
        @Expose
        private String screenshot5;
        @SerializedName("project_size")
        @Expose
        private String projectSize;
        @SerializedName("likes")
        @Expose
        private String likes;
        @SerializedName("comments")
        @Expose
        private String comments;
        @SerializedName("downloads")
        @Expose
        private String downloads;
        @SerializedName("uid")
        @Expose
        private String uid;
        @SerializedName("timestamp")
        @Expose
        private String timestamp;
        @SerializedName("published_timestamp")
        @Expose
        private String publishedTimestamp;
        @SerializedName("is_verified")
        @Expose
        private String isVerified;
        @SerializedName("is_editor_choice")
        @Expose
        private String isEditorChoice;
        @SerializedName("user_name")
        @Expose
        private String userName;
        @SerializedName("user_profile_pic")
        @Expose
        private String userProfilePic;
        @SerializedName("user_badge")
        @Expose
        private String userBadge;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getWhatsnew() {
            return whatsnew;
        }

        public void setWhatsnew(String whatsnew) {
            this.whatsnew = whatsnew;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getProjectType() {
            return projectType;
        }

        public void setProjectType(String projectType) {
            this.projectType = projectType;
        }

        public String getDemoLink() {
            return demoLink;
        }

        public void setDemoLink(String demoLink) {
            this.demoLink = demoLink;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getScreenshot1() {
            return screenshot1;
        }

        public void setScreenshot1(String screenshot1) {
            this.screenshot1 = screenshot1;
        }

        public String getScreenshot2() {
            return screenshot2;
        }

        public void setScreenshot2(String screenshot2) {
            this.screenshot2 = screenshot2;
        }

        public String getScreenshot3() {
            return screenshot3;
        }

        public void setScreenshot3(String screenshot3) {
            this.screenshot3 = screenshot3;
        }

        public String getScreenshot4() {
            return screenshot4;
        }

        public void setScreenshot4(String screenshot4) {
            this.screenshot4 = screenshot4;
        }

        public String getScreenshot5() {
            return screenshot5;
        }

        public void setScreenshot5(String screenshot5) {
            this.screenshot5 = screenshot5;
        }

        public String getProjectSize() {
            return projectSize;
        }

        public void setProjectSize(String projectSize) {
            this.projectSize = projectSize;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public String getDownloads() {
            return downloads;
        }

        public void setDownloads(String downloads) {
            this.downloads = downloads;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public String getPublishedTimestamp() {
            return publishedTimestamp;
        }

        public void setPublishedTimestamp(String publishedTimestamp) {
            this.publishedTimestamp = publishedTimestamp;
        }

        public String getIsVerified() {
            return isVerified;
        }

        public void setIsVerified(String isVerified) {
            this.isVerified = isVerified;
        }

        public String getIsEditorChoice() {
            return isEditorChoice;
        }

        public void setIsEditorChoice(String isEditorChoice) {
            this.isEditorChoice = isEditorChoice;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserProfilePic() {
            return userProfilePic;
        }

        public void setUserProfilePic(String userProfilePic) {
            this.userProfilePic = userProfilePic;
        }

        public String getUserBadge() {
            return userBadge;
        }

        public void setUserBadge(String userBadge) {
            this.userBadge = userBadge;
        }

    }
}