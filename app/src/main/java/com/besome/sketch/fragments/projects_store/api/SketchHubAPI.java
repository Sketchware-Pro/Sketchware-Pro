package com.besome.sketch.fragments.projects_store.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import mod.ilyasse.utils.Network;

public class SketchHubAPI {

    private static final String TAG = "SketchHubAPI";
    private static final String BASE_URL = "https://sketchub.in/api/v3/";
    private static final String GET_PROJECTS_ENDPOINT = "get_project_list";

    private final String apiKey;
    private final Network network = new Network();
    private final Gson gson = new Gson();

    public SketchHubAPI(String apiKey) {
        this.apiKey = apiKey;
    }

    public void getEditorsChoicerProjects(int pageNumber, Consumer<ProjectModel> consumer) {
        getProjects("editor_choice", pageNumber, consumer);
    }

    public void getMostDownloadedProjects(int pageNumber, Consumer<ProjectModel> consumer) {
        getProjects("most_downloaded", pageNumber, consumer);
    }

    public void getRecentProjects(int pageNumber, Consumer<ProjectModel> consumer) {
        getProjects(null, pageNumber, consumer);
    }

    private void getProjects(String scope, int pageNumber, Consumer<ProjectModel> consumer) {
        Map<String, String> formData = new HashMap<>();
        formData.put("api_key", apiKey);
        formData.put("page_number", String.valueOf(pageNumber));
        if (scope != null) formData.put("scope", scope);

        network.postForm(BASE_URL + GET_PROJECTS_ENDPOINT, null, formData, response -> {
            if (response != null && !response.isEmpty()) {
                try {
                    ProjectModel projectModel = gson.fromJson(response, ProjectModel.class);
                    consumer.accept(projectModel);
                } catch (JsonSyntaxException e) {
                    Log.e(TAG, "Failed to parse response", e);
                    consumer.accept(null);
                }
            } else {
                consumer.accept(null);
            }
        });
    }
}
