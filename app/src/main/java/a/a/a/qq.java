package a.a.a;

import java.util.ArrayList;

import mod.jbk.build.BuiltInLibraries;

public class qq {

    /**
     * @param name A built-in library's name, e.g. material-1.0.0
     * @return A set of known dependencies for a built-in library
     * @apiNote Won't return the dependencies' sub-dependencies!
     */
    public static String[] a(String name) {
        ArrayList<String> knownDependencies = new ArrayList<>();

        if (name.equals(BuiltInLibraries.ANDROIDX_ACTIVITY)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_RUNTIME);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_VIEWMODEL);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_VIEWMODEL_SAVEDSTATE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_SAVEDSTATE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_TRACING);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_APPCOMPAT)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ACTIVITY);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_APPCOMPAT_RESOURCES);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CURSORADAPTER);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_DRAWERLAYOUT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_EMOJI2);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_EMOJI2_VIEWS_HELPER);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_FRAGMENT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_RUNTIME);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_VIEWMODEL);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_RESOURCEINSPECTION_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_SAVEDSTATE);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_APPCOMPAT_RESOURCES)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_VECTORDRAWABLE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_VECTORDRAWABLE_ANIMATED);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_ASYNCLAYOUTINFLATER) || name.equals(BuiltInLibraries.ANDROIDX_CUSTOMVIEW) ||
                name.equals(BuiltInLibraries.ANDROIDX_TRANSITION)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_BROWSER)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_INTERPOLATOR);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LEGACY_SUPPORT_CORE_UI);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_CARDVIEW) || name.equals(BuiltInLibraries.ANDROIDX_COLLECTION) ||
                name.equals(BuiltInLibraries.ANDROIDX_CORE_COMMON) || name.equals(BuiltInLibraries.ANDROIDX_CURSORADAPTER) ||
                name.equals(BuiltInLibraries.ANDROIDX_DOCUMENTFILE) || name.equals(BuiltInLibraries.ANDROIDX_INTERPOLATOR) ||
                name.equals(BuiltInLibraries.ANDROIDX_LIFECYCLE_COMMON) || name.equals(BuiltInLibraries.ANDROIDX_LIFECYCLE_VIEWMODEL) ||
                name.equals(BuiltInLibraries.ANDROIDX_LOCALBROADCASTMANAGER) || name.equals(BuiltInLibraries.ANDROIDX_PRINT) ||
                name.equals(BuiltInLibraries.ANDROIDX_RESOURCEINSPECTION_ANNOTATION) || name.equals(BuiltInLibraries.ANDROIDX_TRACING)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_CONCURRENT_FUTURES)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LISTENABLEFUTURE);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_CONSTRAINTLAYOUT)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_APPCOMPAT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CONSTRAINTLAYOUT_CORE);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_COORDINATORLAYOUT) || name.equals(BuiltInLibraries.ANDROIDX_DRAWERLAYOUT) ||
                name.equals(BuiltInLibraries.ANDROIDX_SLIDINGPANELAYOUT) || name.equals(BuiltInLibraries.ANDROIDX_VIEWPAGER)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CUSTOMVIEW);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_CORE)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION_EXPERIMENTAL);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CONCURRENT_FUTURES);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_RUNTIME);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_VERSIONEDPARCELABLE);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_CORE_RUNTIME)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE_COMMON);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_EMOJI2)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_PROCESS);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_STARTUP_RUNTIME);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_EMOJI2_VIEWS_HELPER)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_EMOJI2);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_EXIFINTERFACE) || name.equals(BuiltInLibraries.CIRCLE_IMAGEVIEW) ||
                name.equals(BuiltInLibraries.GLIDE_GIFDECODER)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_FRAGMENT)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ACTIVITY);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION_EXPERIMENTAL);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_LIVEDATA_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_VIEWMODEL);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_VIEWMODEL_SAVEDSTATE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LOADER);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_SAVEDSTATE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_VIEWPAGER);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_LEGACY_SUPPORT_CORE_UI)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ASYNCLAYOUTINFLATER);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CONSTRAINTLAYOUT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COORDINATORLAYOUT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CURSORADAPTER);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CUSTOMVIEW);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_DRAWERLAYOUT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_INTERPOLATOR);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LEGACY_SUPPORT_CORE_UTILS);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_SLIDINGPANELAYOUT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_SWIPEREFRESHLAYOUT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_VIEWPAGER);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_LEGACY_SUPPORT_CORE_UTILS)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_DOCUMENTFILE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LOADER);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LOCALBROADCASTMANAGER);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_PRINT);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_LEGACY_SUPPORT_V13)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LEGACY_SUPPORT_V4);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_LEGACY_SUPPORT_V4)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_FRAGMENT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LEGACY_SUPPORT_CORE_UI);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LEGACY_SUPPORT_CORE_UTILS);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_MEDIA);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_LIFECYCLE_LIVEDATA)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE_COMMON);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE_RUNTIME);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_LIVEDATA_CORE);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_LIFECYCLE_LIVEDATA_CORE)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_COMMON);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE_COMMON);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE_RUNTIME);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_LIFECYCLE_PROCESS)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_RUNTIME);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_STARTUP_RUNTIME);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_LIFECYCLE_RUNTIME)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE_COMMON);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE_RUNTIME);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_COMMON);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_LIFECYCLE_VIEWMODEL_SAVEDSTATE)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_SAVEDSTATE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_LIVEDATA_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_VIEWMODEL);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_LOADER)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_LIVEDATA);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_VIEWMODEL);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_MEDIA)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_VERSIONEDPARCELABLE);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_RECYCLERVIEW)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LEGACY_SUPPORT_CORE_UI);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_SAVEDSTATE)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE_COMMON);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_COMMON);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_STARTUP_RUNTIME)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_TRACING);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_SWIPEREFRESHLAYOUT)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_INTERPOLATOR);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_VECTORDRAWABLE)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_VECTORDRAWABLE_ANIMATED)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_INTERPOLATOR);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_VECTORDRAWABLE);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_VERSIONEDPARCELABLE)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
        } else if (name.equals(BuiltInLibraries.ANDROIDX_VIEWPAGER2)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_FRAGMENT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_RECYCLERVIEW);
        } else if (name.equals(BuiltInLibraries.FIREBASE_AUTH)) {
            knownDependencies.add(BuiltInLibraries.FIREBASE_AUTH_INTEROP);
            knownDependencies.add(BuiltInLibraries.FIREBASE_COMMON);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_FRAGMENT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LOCALBROADCASTMANAGER);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_TASKS);
        } else if (name.equals(BuiltInLibraries.FIREBASE_AUTH_INTEROP)) {
            knownDependencies.add(BuiltInLibraries.FIREBASE_COMMON);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_TASKS);
        } else if (name.equals(BuiltInLibraries.FIREBASE_COMMON)) {
            knownDependencies.add(BuiltInLibraries.GOOGLE_AUTO_VALUE_ANNOTATIONS);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_TASKS);
        } else if (name.equals(BuiltInLibraries.FIREBASE_DATABASE)) {
            knownDependencies.add(BuiltInLibraries.FIREBASE_AUTH_INTEROP);
            knownDependencies.add(BuiltInLibraries.FIREBASE_COMMON);
            knownDependencies.add(BuiltInLibraries.FIREBASE_DATABASE_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_TASKS);
        } else if (name.equals(BuiltInLibraries.FIREBASE_DYNAMIC_LINKS)) {
            knownDependencies.add(BuiltInLibraries.FIREBASE_COMMON);
            knownDependencies.add(BuiltInLibraries.FIREBASE_MEASUREMENT_CONNECTOR);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_TASKS);
        } else if (name.equals(BuiltInLibraries.FIREBASE_IID)) {
            knownDependencies.add(BuiltInLibraries.FIREBASE_COMMON);
            knownDependencies.add(BuiltInLibraries.FIREBASE_IID_INTEROP);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LEGACY_SUPPORT_CORE_UTILS);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_STATS);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_TASKS);
        } else if (name.equals(BuiltInLibraries.FIREBASE_IID_INTEROP)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
        } else if (name.equals(BuiltInLibraries.FIREBASE_MEASUREMENT_CONNECTOR) || name.equals(BuiltInLibraries.PLAY_SERVICES_PLACES_PLACEREPORT)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
        } else if (name.equals(BuiltInLibraries.FIREBASE_MESSAGING)) {
            knownDependencies.add(BuiltInLibraries.FIREBASE_COMMON);
            knownDependencies.add(BuiltInLibraries.FIREBASE_IID);
            knownDependencies.add(BuiltInLibraries.FIREBASE_MEASUREMENT_CONNECTOR);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_TASKS);
        } else if (name.equals(BuiltInLibraries.FIREBASE_STORAGE)) {
            knownDependencies.add(BuiltInLibraries.FIREBASE_AUTH_INTEROP);
            knownDependencies.add(BuiltInLibraries.FIREBASE_COMMON);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_TASKS);
        } else if (name.equals(BuiltInLibraries.GLIDE)) {
            knownDependencies.add(BuiltInLibraries.GLIDE_ANNOTATIONS);
            knownDependencies.add(BuiltInLibraries.GLIDE_DISKLRUCACHE);
            knownDependencies.add(BuiltInLibraries.GLIDE_GIFDECODER);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_EXIFINTERFACE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_FRAGMENT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_VECTORDRAWABLE_ANIMATED);
        } else if (name.equals(BuiltInLibraries.KOTLIN_STDLIB)) {
            knownDependencies.add(BuiltInLibraries.JETBRAINS_ANNOTATIONS);
        } else if (name.equals(BuiltInLibraries.KOTLIN_STDLIB_JDK7)) {
            knownDependencies.add(BuiltInLibraries.KOTLIN_STDLIB);
        } else if (name.equals(BuiltInLibraries.LOTTIE)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_APPCOMPAT);
            knownDependencies.add(BuiltInLibraries.OKIO);
        } else if (name.equals(BuiltInLibraries.MATERIAL)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_ANNOTATION_EXPERIMENTAL);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_APPCOMPAT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CARDVIEW);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CONSTRAINTLAYOUT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COORDINATORLAYOUT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_DRAWERLAYOUT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_DYNAMIC_ANIMATION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_FRAGMENT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LIFECYCLE_RUNTIME);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_RECYCLERVIEW);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_TRANSITION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_VECTORDRAWABLE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_VIEWPAGER2);
        } else if (name.equals(BuiltInLibraries.OKHTTP)) {
            knownDependencies.add(BuiltInLibraries.OKIO);
        } else if (name.equals(BuiltInLibraries.ONESIGNAL)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CARDVIEW);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_FRAGMENT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_BROWSER);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_MEDIA);
            knownDependencies.add(BuiltInLibraries.FIREBASE_MESSAGING);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_LOCATION);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_ADS_IDENTIFIER);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASE);
        } else if (name.equals(BuiltInLibraries.OTPVIEW)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_APPCOMPAT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE_KTX);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CONSTRAINTLAYOUT);
            knownDependencies.add(BuiltInLibraries.KOTLIN_STDLIB_JDK7);
        } else if (name.equals(BuiltInLibraries.PATTERN_LOCK_VIEW)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.JETBRAINS_ANNOTATIONS);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_ADS)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_ADS_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_ADS_IDENTIFIER);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_ADS_LITE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_GASS);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_BROWSER);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_ADS_IDENTIFIER) || name.equals(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT_BASE) ||
                name.equals(BuiltInLibraries.PLAY_SERVICES_TASKS)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_ADS_LITE)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_ADS_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT_SDK);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT_SDK_API);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_AUTH)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_AUTH_API_PHONE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_AUTH_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_TASKS);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_FRAGMENT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LOADER);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_BASE)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_TASKS);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_FRAGMENT);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_BASEMENT)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_FRAGMENT);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_GASS)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_ADS_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_GCM)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LEGACY_SUPPORT_CORE_UTILS);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_IID);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_STATS);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_IID)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_STATS);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_TASKS);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_LOCATION)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_PLACES_PLACEREPORT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_TASKS);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_MAPS)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_FRAGMENT);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT_IMPL);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_STATS);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LEGACY_SUPPORT_CORE_UTILS);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT_IMPL)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_ADS_IDENTIFIER);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_STATS);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_CORE);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT_SDK)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT_BASE);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT_IMPL);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT_SDK_API);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_COLLECTION);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT_SDK_API)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_MEASUREMENT_BASE);
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_STATS)) {
            knownDependencies.add(BuiltInLibraries.PLAY_SERVICES_BASEMENT);
            knownDependencies.add(BuiltInLibraries.ANDROIDX_LEGACY_SUPPORT_CORE_UTILS);
        } else if (name.equals(BuiltInLibraries.YOUTUBE_PLAYER)) {
            knownDependencies.add(BuiltInLibraries.ANDROIDX_APPCOMPAT);
            knownDependencies.add(BuiltInLibraries.KOTLIN_STDLIB_JDK7);
        }

        return knownDependencies.toArray(new String[0]);
    }

    /**
     * @param name Built-in library name, e.g. material-1.0.0
     * @return Package name of built-in library, e.g. com.google.android.material
     */
    public static String b(String name) {
        if (name.equals(BuiltInLibraries.ANDROIDX_APPCOMPAT)) {
            return "androidx.appcompat";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_APPCOMPAT_RESOURCES)) {
            return "androidx.appcompat.resources";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_BROWSER)) {
            return "androidx.browser";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_CARDVIEW)) {
            return "androidx.cardview";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_CONSTRAINTLAYOUT)) {
            return "androidx.constraintlayout.widget";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_COORDINATORLAYOUT)) {
            return "androidx.coordinatorlayout";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_CORE)) {
            return "androidx.core";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_DRAWERLAYOUT)) {
            return "androidx.drawerlayout";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_FRAGMENT)) {
            return "androidx.fragment";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_LIFECYCLE_RUNTIME)) {
            return "androidx.lifecycle.runtime";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_LIFECYCLE_VIEWMODEL)) {
            return "androidx.lifecycle.viewmodel";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_MEDIA)) {
            return "androidx.media";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_RECYCLERVIEW)) {
            return "androidx.recyclerview";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_SAVEDSTATE)) {
            return "androidx.savedstate";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_STARTUP_RUNTIME)) {
            return "androidx.startup";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_SWIPEREFRESHLAYOUT)) {
            return "androidx.swiperefreshlayout";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_TRANSITION)) {
            return "androidx.transition";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_VIEWPAGER2)) {
            return "androidx.viewpager2";
        } else if (name.equals(BuiltInLibraries.ANDROIDX_WORK_RUNTIME)) {
            return "androidx.work";
        } else if (name.equals(BuiltInLibraries.CIRCLE_IMAGEVIEW)) {
            return "de.hdodenhof.circleimageview";
        } else if (name.equals(BuiltInLibraries.CODE_VIEW)) {
            return "br.tiagohm.codeview";
        } else if (name.equals(BuiltInLibraries.FIREBASE_MESSAGING)) {
            return "com.google.firebase.messaging";
        } else if (name.equals(BuiltInLibraries.LOTTIE)) {
            return "com.airbnb.lottie";
        } else if (name.equals(BuiltInLibraries.ONESIGNAL)) {
            return "com.onesignal";
        } else if (name.equals(BuiltInLibraries.OTPVIEW)) {
            return "affan.ahmad.otp";
        } else if (name.equals(BuiltInLibraries.PATTERN_LOCK_VIEW)) {
            return "com.andrognito.patternlockview";
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_ADS)) {
            return "com.google.android.gms.ads.impl";
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_AUTH)) {
            return "com.google.android.gms.auth.api";
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_ADS_LITE)) {
            return "com.google.android.gms.ads";
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_BASE)) {
            return "com.google.android.gms.base";
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_BASEMENT)) {
            return "com.google.android.gms.common";
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_GCM)) {
            return "com.google.android.gms.gcm";
        } else if (name.equals(BuiltInLibraries.PLAY_SERVICES_MAPS)) {
            return "com.google.android.gms.maps";
        } else if (name.equals(BuiltInLibraries.MATERIAL)) {
            return "com.google.android.material";
        } else if (name.equals(BuiltInLibraries.WAVE_SIDE_BAR)) {
            return "com.sayuti.lib";
        } else if (name.equals(BuiltInLibraries.YOUTUBE_PLAYER)) {
            return "com.pierfrancescosoffritti.androidyoutubeplayer";
        } else {
            throw new IllegalArgumentException("No known package name for built-in library '" + name + "'.");
        }
    }

    /**
     * @param name The built-in library's name, e.g. material-1.0.0
     * @return Whether the built-in library has resources that need to be mapped to a R.java file by a resource processor
     */
    public static boolean c(String name) {
        return name.equals(BuiltInLibraries.ANDROIDX_APPCOMPAT) || name.equals(BuiltInLibraries.ANDROIDX_APPCOMPAT_RESOURCES) ||
                name.equals(BuiltInLibraries.ANDROIDX_BROWSER) || name.equals(BuiltInLibraries.ANDROIDX_CARDVIEW) ||
                name.equals(BuiltInLibraries.ANDROIDX_CONSTRAINTLAYOUT) || name.equals(BuiltInLibraries.ANDROIDX_COORDINATORLAYOUT) ||
                name.equals(BuiltInLibraries.ANDROIDX_CORE) || name.equals(BuiltInLibraries.ANDROIDX_DRAWERLAYOUT) ||
                name.equals(BuiltInLibraries.ANDROIDX_FRAGMENT) || name.equals(BuiltInLibraries.ANDROIDX_LIFECYCLE_RUNTIME) ||
                name.equals(BuiltInLibraries.ANDROIDX_LIFECYCLE_VIEWMODEL) || name.equals(BuiltInLibraries.ANDROIDX_MEDIA) ||
                name.equals(BuiltInLibraries.ANDROIDX_RECYCLERVIEW) || name.equals(BuiltInLibraries.ANDROIDX_SAVEDSTATE) ||
                name.equals(BuiltInLibraries.ANDROIDX_STARTUP_RUNTIME) || name.equals(BuiltInLibraries.ANDROIDX_SWIPEREFRESHLAYOUT) ||
                name.equals(BuiltInLibraries.ANDROIDX_TRANSITION) || name.equals(BuiltInLibraries.ANDROIDX_WORK_RUNTIME) ||
                name.equals(BuiltInLibraries.ANDROIDX_VIEWPAGER2) || name.equals(BuiltInLibraries.CIRCLE_IMAGEVIEW) ||
                name.equals(BuiltInLibraries.CODE_VIEW) || name.equals(BuiltInLibraries.FIREBASE_MESSAGING) ||
                name.equals(BuiltInLibraries.LOTTIE) || name.equals(BuiltInLibraries.MATERIAL) ||
                name.equals(BuiltInLibraries.ONESIGNAL) || name.equals(BuiltInLibraries.OTPVIEW) ||
                name.equals(BuiltInLibraries.PATTERN_LOCK_VIEW) || name.equals(BuiltInLibraries.PLAY_SERVICES_ADS) ||
                name.equals(BuiltInLibraries.PLAY_SERVICES_ADS_LITE) || name.equals(BuiltInLibraries.PLAY_SERVICES_AUTH) ||
                name.equals(BuiltInLibraries.PLAY_SERVICES_BASE) || name.equals(BuiltInLibraries.PLAY_SERVICES_BASEMENT) ||
                name.equals(BuiltInLibraries.PLAY_SERVICES_GCM) || name.equals(BuiltInLibraries.PLAY_SERVICES_MAPS) ||
                name.equals(BuiltInLibraries.WAVE_SIDE_BAR) || name.equals(BuiltInLibraries.YOUTUBE_PLAYER);
    }
}
