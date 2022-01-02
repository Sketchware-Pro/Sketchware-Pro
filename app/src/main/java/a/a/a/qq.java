package a.a.a;

import java.util.ArrayList;

import mod.agus.jcoderz.manage.library.ExtLibSelection;

public class qq {

    /**
     * @param name A built-in library's name, e.g. material-1.0.0
     * @return A set of known dependencies for a built-in library
     * @apiNote Won't return the dependencies' sub-dependencies!
     */
    public static String[] a(String name) {
        ArrayList<String> knownDependencies = new ArrayList<>();
        switch (name) {
            case "okhttp-3.9.1":
                knownDependencies.add("Okio-1.17.4");
                break;

            case "glide-4.11.0":
                knownDependencies.add("exifinterface-1.0.0");
                knownDependencies.add("fragment-1.0.0");
                knownDependencies.add("vectordrawable-animated-1.0.0");
                knownDependencies.add("annotations-4.11.0");
                knownDependencies.add("disklrucache-4.11.0");
                knownDependencies.add("gifdecoder-4.11.0");
                break;

            case "auto-value-annotations-1.6.5":
                // no-op, apparently none
                break;

            case "firebase-database-19.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("play-services-base-17.1.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-tasks-17.0.0");
                knownDependencies.add("firebase-auth-interop-18.0.0");
                knownDependencies.add("firebase-common-19.0.0");
                knownDependencies.add("firebase-database-collection-17.0.0");
                break;

            case "versionedparcelable-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("collection-1.0.0");
                break;

            case "savedstate-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-common-2.0.0");
                knownDependencies.add("lifecycle-common-2.0.0");
                break;

            case "cardview-1.0.0":
            case "lifecycle-viewmodel-2.0.0":
            case "print-1.0.0":
            case "interpolator-1.0.0":
            case "core-common-2.0.0":
            case "cursoradapter-1.0.0":
            case "localbroadcastmanager-1.0.0":
            case "documentfile-1.0.0":
            case "collection-1.0.0":
            case "lifecycle-common-2.0.0":
            case "tracing-1.0.0":
            case "resourceinspection-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                break;

            case "legacy-support-v13-1.0.0":
                knownDependencies.add("legacy-support-v4-1.0.0");
                break;

            case "play-services-base-17.1.0":
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("fragment-1.0.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-tasks-17.0.0");
                break;

            case "media-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("versionedparcelable-1.0.0");
                break;

            case "legacy-support-core-utils-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("documentfile-1.0.0");
                knownDependencies.add("loader-1.0.0");
                knownDependencies.add("localbroadcastmanager-1.0.0");
                knownDependencies.add("print-1.0.0");
                break;

            case "recyclerview-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("legacy-support-core-ui-1.0.0");
                break;

            case "play-services-measurement-17.0.0":
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("legacy-support-core-utils-1.0.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-measurement-base-17.0.0");
                knownDependencies.add("play-services-measurement-impl-17.0.0");
                knownDependencies.add("play-services-stats-17.0.0");
                break;

            case "legacy-support-v4-1.0.0":
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("media-1.0.0");
                knownDependencies.add("legacy-support-core-utils-1.0.0");
                knownDependencies.add("legacy-support-core-ui-1.0.0");
                knownDependencies.add("fragment-1.0.0");
                break;

            case "lifecycle-livedata-core-2.0.0":
                knownDependencies.add("lifecycle-common-2.0.0");
                knownDependencies.add("core-common-2.0.0");
                knownDependencies.add("core-runtime-2.0.0");
                break;

            case "play-services-ads-18.2.0":
                knownDependencies.add("browser-1.0.0");
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("play-services-ads-base-18.2.0");
                knownDependencies.add("play-services-ads-identifier-17.0.0");
                knownDependencies.add("play-services-ads-lite-18.2.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-gass-18.2.0");
                break;

            case "play-services-measurement-sdk-api-17.0.0":
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-measurement-base-17.0.0");
                break;

            case "coordinatorlayout-1.0.0":
            case "viewpager-1.0.0":
            case "slidingpanelayout-1.0.0":
            case "drawerlayout-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("customview-1.0.0");
                break;

            case "customview-1.0.0":
            case "asynclayoutinflater-1.0.0":
            case "transition-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-1.0.0");
                break;

            case "play-services-measurement-sdk-17.0.0":
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-measurement-base-17.0.0");
                knownDependencies.add("play-services-measurement-impl-17.0.0");
                knownDependencies.add("play-services-measurement-sdk-api-17.0.0");
                break;

            case "firebase-common-19.0.0":
                knownDependencies.add("auto-value-annotations-1.6.5");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-tasks-17.0.0");
                break;

            case "play-services-measurement-impl-17.0.0":
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("play-services-ads-identifier-17.0.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-measurement-base-17.0.0");
                knownDependencies.add("play-services-stats-17.0.0");
                break;

            case "play-services-maps-17.0.0":
                knownDependencies.add("fragment-1.0.0");
                knownDependencies.add("play-services-base-17.1.0");
                knownDependencies.add("play-services-basement-17.0.0");
                break;

            case "firebase-storage-19.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("play-services-base-17.1.0");
                knownDependencies.add("play-services-tasks-17.0.0");
                knownDependencies.add("firebase-auth-interop-18.0.0");
                knownDependencies.add("firebase-common-19.0.0");
                break;

            case "play-services-basement-17.0.0":
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("fragment-1.0.0");
                break;

            case "play-services-tasks-17.0.0":
            case "play-services-ads-identifier-17.0.0":
            case "play-services-measurement-base-17.0.0":
                knownDependencies.add("play-services-basement-17.0.0");
                break;

            case "loader-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("lifecycle-livedata-2.0.0");
                knownDependencies.add("lifecycle-viewmodel-2.0.0");
                break;

            case "play-services-stats-17.0.0":
                knownDependencies.add("legacy-support-core-utils-1.0.0");
                knownDependencies.add("play-services-basement-17.0.0");
                break;

            case "activity-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("lifecycle-runtime-2.0.0");
                knownDependencies.add("lifecycle-viewmodel-2.0.0");
                knownDependencies.add("savedstate-1.0.0");
                knownDependencies.add("lifecycle-viewmodel-savedstate-2.3.1");
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("tracing-1.0.0");
                break;

            case "vectordrawable-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("collection-1.0.0");
                break;

            case "core-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("annotation-experimental-1.1.0");
                knownDependencies.add("lifecycle-runtime-2.0.0");
                knownDependencies.add("versionedparcelable-1.0.0");
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("concurrent-futures-1.1.0");
                break;

            case "core-runtime-2.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-common-2.0.0");
                break;

            case "play-services-gass-18.2.0":
                knownDependencies.add("play-services-ads-base-18.2.0");
                knownDependencies.add("play-services-basement-17.0.0");
                break;

            case "vectordrawable-animated-1.0.0":
                knownDependencies.add("vectordrawable-1.0.0");
                knownDependencies.add("interpolator-1.0.0");
                knownDependencies.add("collection-1.0.0");
                break;

            case "play-services-ads-lite-18.2.0":
                knownDependencies.add("play-services-ads-base-18.2.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-measurement-17.0.0");
                knownDependencies.add("play-services-measurement-sdk-17.0.0");
                knownDependencies.add("play-services-measurement-sdk-api-17.0.0");
                break;

            case "appcompat-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("cursoradapter-1.0.0");
                knownDependencies.add("activity-1.0.0");
                knownDependencies.add("fragment-1.0.0");
                knownDependencies.add("appcompat-resources-1.1.0");
                knownDependencies.add("drawerlayout-1.0.0");
                knownDependencies.add("savedstate-1.0.0");
                knownDependencies.add("emoji2-1.0.1");
                knownDependencies.add("emoji2-views-helper-1.0.1");
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("lifecycle-runtime-2.0.0");
                knownDependencies.add("lifecycle-viewmodel-2.0.0");
                knownDependencies.add("resourceinspection-annotation-1.0.0");
                break;

            case "browser-1.0.0":
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("interpolator-1.0.0");
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("legacy-support-core-ui-1.0.0");
                break;

            case "legacy-support-core-ui-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("legacy-support-core-utils-1.0.0");
                knownDependencies.add("customview-1.0.0");
                knownDependencies.add("viewpager-1.0.0");
                knownDependencies.add("coordinatorlayout-1.0.0");
                knownDependencies.add("drawerlayout-1.0.0");
                knownDependencies.add("slidingpanelayout-1.0.0");
                knownDependencies.add("interpolator-1.0.0");
                knownDependencies.add("swiperefreshlayout-1.0.0");
                knownDependencies.add("constraintlayout-2.1.2");
                knownDependencies.add("asynclayoutinflater-1.0.0");
                knownDependencies.add("cursoradapter-1.0.0");
                break;

            case "material-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("appcompat-1.0.0");
                knownDependencies.add("cardview-1.0.0");
                knownDependencies.add("coordinatorlayout-1.0.0");
                knownDependencies.add("constraintlayout-2.1.2");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("drawerlayout-1.0.0");
                knownDependencies.add("dynamic-animation-1.1.0");
                knownDependencies.add("annotation-experimental-1.1.0");
                knownDependencies.add("fragment-1.0.0");
                knownDependencies.add("lifecycle-runtime-2.0.0");
                knownDependencies.add("recyclerview-1.0.0");
                knownDependencies.add("transition-1.0.0");
                knownDependencies.add("vectordrawable-1.0.0");
                knownDependencies.add("viewpager2-1.0.0");
                break;

            case "lifecycle-livedata-2.0.0":
                knownDependencies.add("core-runtime-2.0.0");
                knownDependencies.add("lifecycle-livedata-core-2.0.0");
                knownDependencies.add("core-common-2.0.0");
                break;

            case "firebase-auth-interop-18.0.0":
                knownDependencies.add("play-services-base-17.1.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-tasks-17.0.0");
                knownDependencies.add("firebase-common-19.0.0");
                break;

            case "appcompat-resources-1.1.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("vectordrawable-1.0.0");
                knownDependencies.add("vectordrawable-animated-1.0.0");
                knownDependencies.add("collection-1.0.0");
                break;

            case "firebase-auth-19.0.0":
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("fragment-1.0.0");
                knownDependencies.add("localbroadcastmanager-1.0.0");
                knownDependencies.add("play-services-base-17.1.0");
                knownDependencies.add("play-services-basement-17.0.0");
                knownDependencies.add("play-services-tasks-17.0.0");
                knownDependencies.add("firebase-auth-interop-18.0.0");
                knownDependencies.add("firebase-common-19.0.0");
                break;

            case "swiperefreshlayout-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("interpolator-1.0.0");
                break;

            case "fragment-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("viewpager-1.0.0");
                knownDependencies.add("loader-1.0.0");
                knownDependencies.add("activity-1.0.0");
                knownDependencies.add("lifecycle-livedata-core-2.0.0");
                knownDependencies.add("lifecycle-viewmodel-2.0.0");
                knownDependencies.add("lifecycle-viewmodel-savedstate-2.3.1");
                knownDependencies.add("savedstate-1.0.0");
                knownDependencies.add("annotation-experimental-1.1.0");
                break;

            case "lifecycle-runtime-2.0.0":
                knownDependencies.add("lifecycle-common-2.0.0");
                knownDependencies.add("core-common-2.0.0");
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("core-runtime-2.0.0");
                break;

            case "viewpager2-1.0.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("fragment-1.0.0");
                knownDependencies.add("recyclerview-1.0.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("collection-1.0.0");
                break;

            case "lifecycle-viewmodel-savedstate-2.3.1":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("savedstate-1.0.0");
                knownDependencies.add("lifecycle-livedata-core-2.0.0");
                knownDependencies.add("lifecycle-viewmodel-2.0.0");
                break;

            case "concurrent-futures-1.1.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("listenablefuture-1.0.0");
                break;

            case "startup-runtime-1.1.0":
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("tracing-1.0.0");
                break;

            case "lifecycle-process-2.4.0":
                knownDependencies.add("lifecycle-runtime-2.0.0");
                knownDependencies.add("startup-runtime-1.1.0");
                break;

            case "emoji2-1.0.1":
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("startup-runtime-1.1.0");
                knownDependencies.add("collection-1.0.0");
                knownDependencies.add("annotation-1.1.0");
                knownDependencies.add("lifecycle-process-2.4.0");
                break;

            case "emoji2-views-helper-1.0.1":
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("emoji2-1.0.1");
                knownDependencies.add("collection-1.0.0");
                break;

            case "constraintlayout-2.1.2":
                knownDependencies.add("appcompat-1.0.0");
                knownDependencies.add("core-1.0.0");
                knownDependencies.add("constraintlayout-core-1.0.2");
                break;

            default:
                knownDependencies = ExtLibSelection.a(name);
                break;
        }
        return knownDependencies.toArray(new String[0]);
    }

    /**
     * @param name Built-in library name, e.g. material-1.0.0
     * @return Package name of built-in library, e.g. com.google.android.material
     */
    public static String b(String name) {
        switch (name) {
            case "appcompat-1.0.0":
                return "androidx.appcompat";

            case "appcompat-resources-1.1.0":
                return "androidx.appcompat.resources";

            case "browser-1.0.0":
                return "androidx.browser";

            case "cardview-1.0.0":
                return "androidx.cardview";

            case "constraintlayout-2.1.2":
                return "androidx.constraintlayout.widget";

            case "coordinatorlayout-1.0.0":
                return "androidx.coordinatorlayout";

            case "core-1.0.0":
                return "androidx.core";

            case "drawerlayout-1.0.0":
                return "androidx.drawerlayout";

            case "fragment-1.0.0":
                return "androidx.fragment";

            case "lifecycle-runtime-2.0.0":
                return "androidx.lifecycle.runtime";

            case "lifecycle-viewmodel-2.0.0":
                return "androidx.lifecycle.viewmodel";

            case "material-1.0.0":
                return "com.google.android.material";

            case "media-1.0.0":
                return "androidx.media";

            case "recyclerview-1.0.0":
                return "androidx.recyclerview";

            case "play-services-ads-18.2.0":
                return "com.google.android.gms.ads.impl";

            case "play-services-ads-lite-18.2.0":
                return "com.google.android.gms.ads";

            case "play-services-base-17.1.0":
                return "com.google.android.gms.base";

            case "play-services-basement-17.0.0":
                return "com.google.android.gms.common";

            case "play-services-maps-17.0.0":
                return "com.google.android.gms.maps";

            case "savedstate-1.0.0":
                return "androidx.savedstate";

            case "swiperefreshlayout-1.0.0":
                return "androidx.swiperefreshlayout";

            case "transition-1.0.0":
                return "androidx.transition";

            case "work-runtime-2.1.0":
                return "androidx.work";

            default:
                return ExtLibSelection.b(name);
        }
    }

    /**
     * @param str The built-in library's name, e.g. material-1.0.0
     * @return Whether the built-in library has resources that need to be mapped to a R.java file by a resource processor
     */
    public static boolean c(String str) {
        switch (str) {
            case "appcompat-1.0.0":
            case "appcompat-resources-1.1.0":
            case "browser-1.0.0":
            case "cardview-1.0.0":
            case "constraintlayout-2.1.2":
            case "coordinatorlayout-1.0.0":
            case "core-1.0.0":
            case "drawerlayout-1.0.0":
            case "fragment-1.0.0":
            case "lifecycle-runtime-2.0.0":
            case "lifecycle-viewmodel-2.0.0":
            case "material-1.0.0":
            case "media-1.0.0":
            case "play-services-ads-18.2.0":
            case "play-services-ads-lite-18.2.0":
            case "play-services-base-17.1.0":
            case "play-services-basement-17.0.0":
            case "play-services-maps-17.0.0":
            case "recyclerview-1.0.0":
            case "savedstate-1.0.0":
            case "swiperefreshlayout-1.0.0":
            case "transition-1.0.0":
            case "work-runtime-2.1.0":
            case "viewpager2-1.0.0":
                return true;

            default:
                return ExtLibSelection.c(str);
        }
    }
}
