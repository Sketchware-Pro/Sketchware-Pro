package pro.sketchware.utility;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.analytics.FirebaseAnalytics;

public class AnalyticsHelper {

    private static final String TAG = "AnalyticsHelper";

    public static final String EVENT_EXPORT_PROJECT = "export_project";
    public static final String EVENT_ADD_LIBRARY = "add_library";
    public static final String EVENT_FEATURE_SEARCH = "feature_search";
    public static final String EVENT_UI_COMPONENT_ADDED = "ui_component_added";

    public static final String PARAM_EXPORT_TYPE = "export_type";
    public static final String PARAM_PROJECT_NAME = "project_name";
    public static final String PARAM_IS_SUCCESSFUL = "is_successful";

    public static final String PARAM_LIBRARY_NAME = "library_name";
    public static final String PARAM_LIBRARY_TYPE = "library_type";

    public static final String PARAM_SEARCH_QUERY = "search_query";
    public static final String PARAM_SEARCH_CONTEXT = "search_context";

    public static final String PARAM_COMPONENT_TYPE = "component_type";

    public static void logExportProject(@Nullable Context context, @NonNull String exportType, @Nullable String projectName, boolean isSuccessful) {
        if (context == null) return;
        try {
            Bundle bundle = new Bundle();
            bundle.putString(PARAM_EXPORT_TYPE, exportType);
            if (projectName != null) {
                bundle.putString(PARAM_PROJECT_NAME, projectName);
            }
            bundle.putBoolean(PARAM_IS_SUCCESSFUL, isSuccessful);
            FirebaseAnalytics.getInstance(context).logEvent(EVENT_EXPORT_PROJECT, bundle);
        } catch (Exception e) {
            Log.w(TAG, "Failed to log export_project", e);
        }
    }

    public static void logAddLibrary(@Nullable Context context, @NonNull String libraryName, @NonNull String libraryType) {
        if (context == null) return;
        try {
            Bundle bundle = new Bundle();
            bundle.putString(PARAM_LIBRARY_NAME, libraryName);
            bundle.putString(PARAM_LIBRARY_TYPE, libraryType);
            FirebaseAnalytics.getInstance(context).logEvent(EVENT_ADD_LIBRARY, bundle);
        } catch (Exception e) {
            Log.w(TAG, "Failed to log add_library", e);
        }
    }

    public static void logFeatureSearch(@Nullable Context context, @NonNull String searchQuery, @NonNull String searchContext) {
        if (context == null) return;
        try {
            String trimmed = searchQuery.trim();
            if (trimmed.isEmpty()) return;
            Bundle bundle = new Bundle();
            bundle.putString(PARAM_SEARCH_QUERY, trimmed);
            bundle.putString(PARAM_SEARCH_CONTEXT, searchContext);
            FirebaseAnalytics.getInstance(context).logEvent(EVENT_FEATURE_SEARCH, bundle);
        } catch (Exception e) {
            Log.w(TAG, "Failed to log feature_search", e);
        }
    }

    public static void logUiComponentAdded(@Nullable Context context, @NonNull String componentType) {
        if (context == null) return;
        try {
            Bundle bundle = new Bundle();
            bundle.putString(PARAM_COMPONENT_TYPE, componentType);
            FirebaseAnalytics.getInstance(context).logEvent(EVENT_UI_COMPONENT_ADDED, bundle);
        } catch (Exception e) {
            Log.w(TAG, "Failed to log ui_component_added", e);
        }
    }
}
