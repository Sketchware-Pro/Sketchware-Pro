package mod.jbk.build;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import a.a.a.Jp;
import a.a.a.KB;
import a.a.a.ProjectBuilder;
import a.a.a.oB;
import pro.sketchware.SketchApplication;

public class BuiltInLibraries {
    public static final File EXTRACTED_COMPILE_ASSETS_PATH = new File(SketchApplication.getContext().getFilesDir(), "libs");
    public static final File EXTRACTED_BUILT_IN_LIBRARIES_PATH = new File(EXTRACTED_COMPILE_ASSETS_PATH, "libs");
    public static final File EXTRACTED_BUILT_IN_LIBRARY_DEX_FILES_PATH = new File(EXTRACTED_COMPILE_ASSETS_PATH, "dexs");

    // None final so that field values won't be optimized into code, and to allow easy changing of library names due to that

    public static String ANDROIDX_ACTIVITY = "activity-1.3.0";
    public static String ANDROIDX_ANNOTATION = "annotation-1.3.0";
    public static String ANDROIDX_ANNOTATION_EXPERIMENTAL = "annotation-experimental-1.2.0";
    public static String ANDROIDX_APPCOMPAT = "appcompat-1.4.0";
    public static String ANDROIDX_APPCOMPAT_RESOURCES = "appcompat-resources-1.4.0";
    public static String ANDROIDX_ASYNCLAYOUTINFLATER = "asynclayoutinflater-1.0.0";
    public static String ANDROIDX_BROWSER = "browser-1.5.0";
    public static String ANDROIDX_CARDVIEW = "cardview-1.0.0";
    public static String ANDROIDX_COLLECTION = "collection-1.2.0";
    public static String ANDROIDX_CONCURRENT_FUTURES = "concurrent-futures-1.1.0";
    public static String ANDROIDX_CONSTRAINTLAYOUT = "constraintlayout-2.1.2";
    public static String ANDROIDX_CONSTRAINTLAYOUT_CORE = "constraintlayout-core-1.0.2";
    public static String ANDROIDX_COORDINATORLAYOUT = "coordinatorlayout-1.1.0";
    public static String ANDROIDX_CORE = "core-1.7.0";
    public static String ANDROIDX_CORE_COMMON = "core-common-2.1.0";
    public static String ANDROIDX_CORE_KTX = "core-ktx-1.5.0-beta01";
    public static String ANDROIDX_CORE_RUNTIME = "core-runtime-2.1.0";
    public static String ANDROIDX_CURSORADAPTER = "cursoradapter-1.0.0";
    public static String ANDROIDX_CUSTOMVIEW = "customview-1.1.0";
    public static String ANDROIDX_DOCUMENTFILE = "documentfile-1.0.1";
    public static String ANDROIDX_DRAWERLAYOUT = "drawerlayout-1.1.1";
    public static String ANDROIDX_DYNAMIC_ANIMATION = "dynamic-animation-1.1.0-alpha03";
    public static String ANDROIDX_EMOJI2 = "emoji2-1.0.1";
    public static String ANDROIDX_EMOJI2_VIEWS_HELPER = "emoji2-views-helper-1.0.1";
    public static String ANDROIDX_EXIFINTERFACE = "exifinterface-1.0.0";
    public static String ANDROIDX_FRAGMENT = "fragment-1.3.6";
    public static String ANDROIDX_INTERPOLATOR = "interpolator-1.0.0";
    public static String ANDROIDX_LEGACY_SUPPORT_CORE_UI = "legacy-support-core-ui-1.0.0";
    public static String ANDROIDX_LEGACY_SUPPORT_CORE_UTILS = "legacy-support-core-utils-1.0.0";
    public static String ANDROIDX_LEGACY_SUPPORT_V4 = "legacy-support-v4-1.0.0";
    public static String ANDROIDX_LEGACY_SUPPORT_V13 = "legacy-support-v13-1.0.0";
    public static String ANDROIDX_LIFECYCLE_COMMON = "lifecycle-common-2.4.0";
    public static String ANDROIDX_LIFECYCLE_LIVEDATA = "lifecycle-livedata-2.4.0";
    public static String ANDROIDX_LIFECYCLE_LIVEDATA_CORE = "lifecycle-livedata-core-2.4.0";
    public static String ANDROIDX_LIFECYCLE_PROCESS = "lifecycle-process-2.4.0";
    public static String ANDROIDX_LIFECYCLE_RUNTIME = "lifecycle-runtime-2.4.0";
    public static String ANDROIDX_LIFECYCLE_SERVICE = "lifecycle-service-2.4.0";
    public static String ANDROIDX_LIFECYCLE_VIEWMODEL = "lifecycle-viewmodel-2.3.1";
    public static String ANDROIDX_LIFECYCLE_VIEWMODEL_SAVEDSTATE = "lifecycle-viewmodel-savedstate-2.3.1";
    public static String ANDROIDX_LOADER = "loader-1.1.0";
    public static String ANDROIDX_LOCALBROADCASTMANAGER = "localbroadcastmanager-1.0.0";
    public static String ANDROIDX_MEDIA = "media-1.2.1";
    public static String ANDROIDX_MULTIDEX = "multidex-2.0.1";
    public static String ANDROIDX_PRINT = "print-1.0.0";
    public static String ANDROIDX_RECYCLERVIEW = "recyclerview-1.2.1";
    public static String ANDROIDX_RESOURCEINSPECTION_ANNOTATION = "resourceinspection-annotation-1.0.0";
    public static String ANDROIDX_ROOM_COMMON = "room-common-2.2.6";
    public static String ANDROIDX_ROOM_RUNTIME = "room-runtime-2.2.6";
    public static String ANDROIDX_SAVEDSTATE = "savedstate-1.1.0";
    public static String ANDROIDX_SLIDINGPANELAYOUT = "slidingpanelayout-1.1.0";
    public static String ANDROIDX_SQLITE = "sqlite-2.2.0";
    public static String ANDROIDX_SQLITE_FRAMEWORK = "sqlite-framework-2.2.0";
    public static String ANDROIDX_STARTUP_RUNTIME = "startup-runtime-1.1.0";
    public static String ANDROIDX_SWIPEREFRESHLAYOUT = "swiperefreshlayout-1.2.0-alpha01";
    public static String ANDROIDX_TRACING = "tracing-1.0.0";
    public static String ANDROIDX_TRANSITION = "transition-1.4.1";
    public static String ANDROIDX_VECTORDRAWABLE = "vectordrawable-1.1.0";
    public static String ANDROIDX_VECTORDRAWABLE_ANIMATED = "vectordrawable-animated-1.1.0";
    public static String ANDROIDX_VERSIONEDPARCELABLE = "versionedparcelable-1.1.1";
    public static String ANDROIDX_VIEWPAGER2 = "viewpager2-1.0.0";
    public static String ANDROIDX_VIEWPAGER = "viewpager-1.0.0";
    public static String ANDROIDX_WORK_RUNTIME = "work-runtime-2.7.1";
    public static String FIREBASE_AUTH = "firebase-auth-19.0.0";
    public static String FIREBASE_AUTH_INTEROP = "firebase-auth-interop-18.0.0";
    public static String FIREBASE_COMMON = "firebase-common-19.3.1";
    public static String FIREBASE_COMPONENTS = "firebase-components-16.0.0";
    public static String FIREBASE_DATABASE = "firebase-database-19.3.1";
    public static String FIREBASE_DATABASE_COLLECTION = "firebase-database-collection-17.0.0";
    public static String FIREBASE_DYNAMIC_LINKS = "firebase-dynamic-links-19.0.0";
    public static String FIREBASE_IID = "firebase-iid-19.0.0";
    public static String FIREBASE_IID_INTEROP = "firebase-iid-interop-17.0.0";
    public static String FIREBASE_MEASUREMENT_CONNECTOR = "firebase-measurement-connector-18.0.0";
    public static String FIREBASE_MESSAGING = "firebase-messaging-19.0.0";
    public static String FIREBASE_STORAGE = "firebase-storage-19.0.0";
    public static String CIRCLE_IMAGEVIEW = "circleimageview-3.1.0";
    public static String CODE_VIEW = "CodeView-0.4.0";
    public static String FACEBOOK_ADS_AUDIENCE_NETWORK_SDK = "audience-network-sdk-5.9.0";
    public static String GLIDE_ANNOTATIONS = "annotations-4.11.0";
    public static String GLIDE_DISKLRUCACHE = "disklrucache-4.11.0";
    public static String GLIDE_GIFDECODER = "gifdecoder-4.11.0";
    public static String GLIDE = "glide-4.11.0";
    public static String GOOGLE_AUTO_VALUE_ANNOTATIONS = "auto-value-annotations-1.6.5";
    public static String GSON = "gson-2.8.7";
    public static String GUAVA_LISTENABLEFUTURE = "listenablefuture-1.0.0";
    public static String HTTP_LEGACY_ANDROID_28 = "http-legacy-android-28";
    public static String JETBRAINS_ANNOTATIONS = "annotations-13.0";
    public static String KOTLIN_STDLIB = "kotlin-stdlib-1.4.30-M1-release-152";
    public static String KOTLIN_STDLIB_JDK7 = "kotlin-stdlib-jdk7-1.4.30-M1-release-152";
    public static String LOTTIE = "lottie-3.4.0";
    public static String MATERIAL = "material-1.6.1";
    public static String OKHTTP = "okhttp-3.9.1";
    public static String OKIO = "okio-1.17.4";
    public static String ONESIGNAL = "OneSignal-3.14.0";
    public static String OTPVIEW = "OTPView-0.1.0";
    public static String PATTERN_LOCK_VIEW = "pattern-lock-view";
    public static String PLAY_SERVICES_ADS = "play-services-ads-22.2.0";
    public static String PLAY_SERVICES_ADS_BASE = "play-services-ads-base-22.2.0";
    public static String PLAY_SERVICES_ADS_IDENTIFIER = "play-services-ads-identifier-18.0.1";
    public static String PLAY_SERVICES_ADS_LITE = "play-services-ads-lite-22.2.0";
    public static String PLAY_SERVICES_APPSET = "play-services-appset-16.0.2";
    public static String PLAY_SERVICES_AUTH = "play-services-auth-19.0.0";
    public static String PLAY_SERVICES_AUTH_API_PHONE = "play-services-auth-api-phone-17.0.5";
    public static String PLAY_SERVICES_AUTH_BASE = "play-services-auth-base-17.1.2";
    public static String PLAY_SERVICES_BASE = "play-services-base-18.2.0";
    public static String PLAY_SERVICES_BASEMENT = "play-services-basement-18.2.0";
    public static String PLAY_SERVICES_GASS = "play-services-gass-20.0.0";
    public static String PLAY_SERVICES_GCM = "play-services-gcm-17.0.0";
    public static String PLAY_SERVICES_IID = "play-services-iid-17.0.0";
    public static String PLAY_SERVICES_LOCATION = "play-services-location-18.0.0";
    public static String PLAY_SERVICES_MAPS = "play-services-maps-17.0.1";
    public static String PLAY_SERVICES_MEASUREMENT_BASE = "play-services-measurement-base-20.1.2";
    public static String PLAY_SERVICES_MEASUREMENT_SDK_API = "play-services-measurement-sdk-api-20.1.2";
    public static String PLAY_SERVICES_PLACES_PLACEREPORT = "play-services-places-placereport-17.0.0";
    public static String PLAY_SERVICES_STATS = "play-services-stats-17.0.0";
    public static String PLAY_SERVICES_TASKS = "play-services-tasks-18.0.2";
    public static String UMP_USER_MESSAGING_PLATFORM = "user-messaging-platform-2.0.0";
    public static String WAVE_SIDE_BAR = "wave-side-bar";
    public static String YOUTUBE_PLAYER = "android-youtube-player-10.0.5";

    public static final BuiltInLibrary[] KNOWN_BUILT_IN_LIBRARIES = {
            new BuiltInLibrary(ANDROIDX_ACTIVITY, List.of(ANDROIDX_ANNOTATION, ANDROIDX_COLLECTION, ANDROIDX_CORE, ANDROIDX_LIFECYCLE_RUNTIME,
                    ANDROIDX_LIFECYCLE_VIEWMODEL, ANDROIDX_LIFECYCLE_VIEWMODEL_SAVEDSTATE, ANDROIDX_SAVEDSTATE, ANDROIDX_TRACING)),

            new BuiltInLibrary(ANDROIDX_ANNOTATION),
            new BuiltInLibrary(ANDROIDX_ANNOTATION_EXPERIMENTAL),
            new BuiltInLibrary(ANDROIDX_APPCOMPAT, List.of(ANDROIDX_ACTIVITY, ANDROIDX_ANNOTATION, ANDROIDX_APPCOMPAT_RESOURCES, ANDROIDX_COLLECTION,
                    ANDROIDX_CORE, ANDROIDX_CURSORADAPTER, ANDROIDX_DRAWERLAYOUT, ANDROIDX_EMOJI2, ANDROIDX_EMOJI2_VIEWS_HELPER, ANDROIDX_FRAGMENT,
                    ANDROIDX_LIFECYCLE_RUNTIME, ANDROIDX_LIFECYCLE_VIEWMODEL, ANDROIDX_RESOURCEINSPECTION_ANNOTATION, ANDROIDX_SAVEDSTATE),
                    "androidx.appcompat"),

            new BuiltInLibrary(ANDROIDX_APPCOMPAT_RESOURCES, List.of(ANDROIDX_ANNOTATION, ANDROIDX_COLLECTION, ANDROIDX_CORE, ANDROIDX_VECTORDRAWABLE,
                    ANDROIDX_VECTORDRAWABLE_ANIMATED), "androidx.appcompat.resources"),

            new BuiltInLibrary(ANDROIDX_ASYNCLAYOUTINFLATER, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE)),
            new BuiltInLibrary(ANDROIDX_BROWSER, List.of(ANDROIDX_ANNOTATION, ANDROIDX_COLLECTION, ANDROIDX_CONCURRENT_FUTURES, ANDROIDX_CORE, ANDROIDX_INTERPOLATOR,
                    GUAVA_LISTENABLEFUTURE), "androidx.browser"),

            new BuiltInLibrary(ANDROIDX_CARDVIEW, List.of(ANDROIDX_ANNOTATION), "androidx.cardview"),
            new BuiltInLibrary(ANDROIDX_COLLECTION, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(ANDROIDX_CONCURRENT_FUTURES, List.of(ANDROIDX_ANNOTATION, GUAVA_LISTENABLEFUTURE)),
            new BuiltInLibrary(ANDROIDX_CONSTRAINTLAYOUT, List.of(ANDROIDX_APPCOMPAT, ANDROIDX_CORE, ANDROIDX_CONSTRAINTLAYOUT_CORE),
                    "androidx.constraintlayout.widget"),

            new BuiltInLibrary(ANDROIDX_CONSTRAINTLAYOUT_CORE),
            new BuiltInLibrary(ANDROIDX_COORDINATORLAYOUT, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE, ANDROIDX_CUSTOMVIEW),
                    "androidx.coordinatorlayout"),

            new BuiltInLibrary(ANDROIDX_CORE, List.of(ANDROIDX_ANNOTATION, ANDROIDX_ANNOTATION_EXPERIMENTAL, ANDROIDX_COLLECTION,
                    ANDROIDX_CONCURRENT_FUTURES, ANDROIDX_LIFECYCLE_RUNTIME, ANDROIDX_VERSIONEDPARCELABLE), "androidx.core"),

            new BuiltInLibrary(ANDROIDX_CORE_KTX),
            new BuiltInLibrary(ANDROIDX_CORE_COMMON, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(ANDROIDX_CORE_RUNTIME, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE_COMMON)),
            new BuiltInLibrary(ANDROIDX_CURSORADAPTER, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(ANDROIDX_CUSTOMVIEW, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE)),
            new BuiltInLibrary(ANDROIDX_DOCUMENTFILE, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(ANDROIDX_DRAWERLAYOUT, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE, ANDROIDX_CUSTOMVIEW),
                    "androidx.drawerlayout"),

            new BuiltInLibrary(ANDROIDX_DYNAMIC_ANIMATION),
            new BuiltInLibrary(ANDROIDX_EMOJI2, List.of(ANDROIDX_ANNOTATION, ANDROIDX_COLLECTION, ANDROIDX_CORE, ANDROIDX_LIFECYCLE_PROCESS,
                    ANDROIDX_STARTUP_RUNTIME)),

            new BuiltInLibrary(ANDROIDX_EMOJI2_VIEWS_HELPER, List.of(ANDROIDX_COLLECTION, ANDROIDX_CORE, ANDROIDX_EMOJI2)),
            new BuiltInLibrary(ANDROIDX_EXIFINTERFACE, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(ANDROIDX_FRAGMENT, List.of(ANDROIDX_ACTIVITY, ANDROIDX_ANNOTATION, ANDROIDX_ANNOTATION_EXPERIMENTAL,
                    ANDROIDX_COLLECTION, ANDROIDX_CORE, ANDROIDX_LIFECYCLE_LIVEDATA_CORE, ANDROIDX_LIFECYCLE_VIEWMODEL,
                    ANDROIDX_LIFECYCLE_VIEWMODEL_SAVEDSTATE, ANDROIDX_LOADER, ANDROIDX_SAVEDSTATE, ANDROIDX_VIEWPAGER), "androidx.fragment"),

            new BuiltInLibrary(ANDROIDX_INTERPOLATOR, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(ANDROIDX_LEGACY_SUPPORT_CORE_UI, List.of(ANDROIDX_ANNOTATION, ANDROIDX_ASYNCLAYOUTINFLATER, ANDROIDX_CONSTRAINTLAYOUT,
                    ANDROIDX_COORDINATORLAYOUT, ANDROIDX_CORE, ANDROIDX_CURSORADAPTER, ANDROIDX_CUSTOMVIEW, ANDROIDX_DRAWERLAYOUT, ANDROIDX_INTERPOLATOR,
                    ANDROIDX_LEGACY_SUPPORT_CORE_UTILS, ANDROIDX_SLIDINGPANELAYOUT, ANDROIDX_SWIPEREFRESHLAYOUT, ANDROIDX_VIEWPAGER)),

            new BuiltInLibrary(ANDROIDX_LEGACY_SUPPORT_CORE_UTILS, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE, ANDROIDX_DOCUMENTFILE, ANDROIDX_LOADER,
                    ANDROIDX_LOCALBROADCASTMANAGER, ANDROIDX_PRINT)),

            new BuiltInLibrary(ANDROIDX_LEGACY_SUPPORT_V13, List.of(ANDROIDX_LEGACY_SUPPORT_V4)),
            new BuiltInLibrary(ANDROIDX_LEGACY_SUPPORT_V4, List.of(ANDROIDX_CORE, ANDROIDX_FRAGMENT, ANDROIDX_LEGACY_SUPPORT_CORE_UI,
                    ANDROIDX_LEGACY_SUPPORT_CORE_UTILS, ANDROIDX_MEDIA)),

            new BuiltInLibrary(ANDROIDX_LIFECYCLE_COMMON, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(ANDROIDX_LIFECYCLE_LIVEDATA, List.of(ANDROIDX_CORE_COMMON, ANDROIDX_CORE_RUNTIME, ANDROIDX_LIFECYCLE_LIVEDATA_CORE)),
            new BuiltInLibrary(ANDROIDX_LIFECYCLE_LIVEDATA_CORE, List.of(ANDROIDX_LIFECYCLE_COMMON, ANDROIDX_CORE_COMMON, ANDROIDX_CORE_RUNTIME)),
            new BuiltInLibrary(ANDROIDX_LIFECYCLE_PROCESS, List.of(ANDROIDX_LIFECYCLE_RUNTIME, ANDROIDX_STARTUP_RUNTIME)),
            new BuiltInLibrary(ANDROIDX_LIFECYCLE_RUNTIME, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE_COMMON, ANDROIDX_CORE_RUNTIME,
                    ANDROIDX_LIFECYCLE_COMMON), "androidx.lifecycle.runtime"),

            new BuiltInLibrary(ANDROIDX_LIFECYCLE_SERVICE, List.of(ANDROIDX_LIFECYCLE_RUNTIME)),
            new BuiltInLibrary(ANDROIDX_LIFECYCLE_VIEWMODEL, List.of(ANDROIDX_ANNOTATION), "androidx.lifecycle.viewmodel"),
            new BuiltInLibrary(ANDROIDX_LIFECYCLE_VIEWMODEL_SAVEDSTATE, List.of(ANDROIDX_ANNOTATION, ANDROIDX_SAVEDSTATE,
                    ANDROIDX_LIFECYCLE_LIVEDATA_CORE, ANDROIDX_LIFECYCLE_VIEWMODEL)),

            new BuiltInLibrary(ANDROIDX_LOADER, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE, ANDROIDX_LIFECYCLE_LIVEDATA, ANDROIDX_LIFECYCLE_VIEWMODEL)),
            new BuiltInLibrary(ANDROIDX_LOCALBROADCASTMANAGER, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(ANDROIDX_MEDIA, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE, ANDROIDX_VERSIONEDPARCELABLE), "androidx.media"),
            new BuiltInLibrary(ANDROIDX_MULTIDEX),
            new BuiltInLibrary(ANDROIDX_PRINT, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(ANDROIDX_RECYCLERVIEW, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE, ANDROIDX_LEGACY_SUPPORT_CORE_UI),
                    "androidx.recyclerview"),

            new BuiltInLibrary(ANDROIDX_RESOURCEINSPECTION_ANNOTATION, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(ANDROIDX_ROOM_COMMON, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(ANDROIDX_ROOM_RUNTIME, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE_RUNTIME, ANDROIDX_ROOM_COMMON,
                    ANDROIDX_SQLITE_FRAMEWORK, ANDROIDX_SQLITE)),

            new BuiltInLibrary(ANDROIDX_SAVEDSTATE, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE_COMMON, ANDROIDX_LIFECYCLE_COMMON),
                    "androidx.savedstate"),

            new BuiltInLibrary(ANDROIDX_SLIDINGPANELAYOUT, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE, ANDROIDX_CUSTOMVIEW)),
            new BuiltInLibrary(ANDROIDX_SQLITE, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(ANDROIDX_SQLITE_FRAMEWORK, List.of(ANDROIDX_ANNOTATION, ANDROIDX_SQLITE)),
            new BuiltInLibrary(ANDROIDX_STARTUP_RUNTIME, List.of(ANDROIDX_ANNOTATION, ANDROIDX_TRACING), "androidx.startup"),
            new BuiltInLibrary(ANDROIDX_SWIPEREFRESHLAYOUT, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE, ANDROIDX_INTERPOLATOR),
                    "androidx.swiperefreshlayout"),

            new BuiltInLibrary(ANDROIDX_TRACING, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(ANDROIDX_TRANSITION, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE), "androidx.transition"),
            new BuiltInLibrary(ANDROIDX_VECTORDRAWABLE, List.of(ANDROIDX_ANNOTATION, ANDROIDX_COLLECTION, ANDROIDX_CORE)),
            new BuiltInLibrary(ANDROIDX_VECTORDRAWABLE_ANIMATED, List.of(ANDROIDX_COLLECTION, ANDROIDX_INTERPOLATOR, ANDROIDX_VECTORDRAWABLE)),
            new BuiltInLibrary(ANDROIDX_VERSIONEDPARCELABLE, List.of(ANDROIDX_ANNOTATION, ANDROIDX_COLLECTION)),
            new BuiltInLibrary(ANDROIDX_VIEWPAGER, List.of(ANDROIDX_ANNOTATION, ANDROIDX_CORE, ANDROIDX_CUSTOMVIEW)),
            new BuiltInLibrary(ANDROIDX_VIEWPAGER2, List.of(ANDROIDX_ANNOTATION, ANDROIDX_COLLECTION, ANDROIDX_CORE, ANDROIDX_FRAGMENT,
                    ANDROIDX_RECYCLERVIEW), "androidx.viewpager2"),

            new BuiltInLibrary(ANDROIDX_WORK_RUNTIME, List.of(ANDROIDX_ANNOTATION_EXPERIMENTAL, ANDROIDX_CORE, ANDROIDX_LIFECYCLE_LIVEDATA,
                    ANDROIDX_LIFECYCLE_SERVICE, ANDROIDX_ROOM_RUNTIME, ANDROIDX_SQLITE, ANDROIDX_SQLITE_FRAMEWORK, ANDROIDX_STARTUP_RUNTIME,
                    GUAVA_LISTENABLEFUTURE), "androidx.work"),
            new BuiltInLibrary(CIRCLE_IMAGEVIEW, List.of(ANDROIDX_ANNOTATION), "de.hdodenhof.circleimageview"),
            new BuiltInLibrary(CODE_VIEW, List.of(), "br.tiagohm.codeview"),
            new BuiltInLibrary(FACEBOOK_ADS_AUDIENCE_NETWORK_SDK),
            new BuiltInLibrary(FIREBASE_AUTH, List.of(FIREBASE_AUTH_INTEROP, FIREBASE_COMMON, ANDROIDX_COLLECTION, ANDROIDX_CORE, ANDROIDX_FRAGMENT,
                    ANDROIDX_LOCALBROADCASTMANAGER, PLAY_SERVICES_BASE, PLAY_SERVICES_BASEMENT, PLAY_SERVICES_TASKS)),

            new BuiltInLibrary(FIREBASE_AUTH_INTEROP, List.of(FIREBASE_COMMON, PLAY_SERVICES_BASE, PLAY_SERVICES_BASEMENT, PLAY_SERVICES_TASKS)),
            new BuiltInLibrary(FIREBASE_COMMON, List.of(FIREBASE_COMPONENTS, PLAY_SERVICES_BASEMENT, PLAY_SERVICES_TASKS)),
            new BuiltInLibrary(FIREBASE_COMPONENTS, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(FIREBASE_DATABASE, List.of(FIREBASE_AUTH_INTEROP, FIREBASE_COMMON, FIREBASE_COMPONENTS, FIREBASE_DATABASE_COLLECTION, ANDROIDX_ANNOTATION,
                    PLAY_SERVICES_BASE, PLAY_SERVICES_BASEMENT, PLAY_SERVICES_TASKS)),

            new BuiltInLibrary(FIREBASE_DATABASE_COLLECTION),
            new BuiltInLibrary(FIREBASE_DYNAMIC_LINKS, List.of(FIREBASE_COMMON, FIREBASE_MEASUREMENT_CONNECTOR, PLAY_SERVICES_BASE,
                    PLAY_SERVICES_BASEMENT, PLAY_SERVICES_TASKS)),

            new BuiltInLibrary(FIREBASE_IID, List.of(FIREBASE_COMMON, FIREBASE_IID_INTEROP, ANDROIDX_COLLECTION, ANDROIDX_CORE,
                    ANDROIDX_LEGACY_SUPPORT_CORE_UTILS, PLAY_SERVICES_BASEMENT, PLAY_SERVICES_STATS, PLAY_SERVICES_TASKS)),

            new BuiltInLibrary(FIREBASE_IID_INTEROP, List.of(PLAY_SERVICES_BASE, PLAY_SERVICES_BASEMENT)),
            new BuiltInLibrary(FIREBASE_MEASUREMENT_CONNECTOR, List.of(PLAY_SERVICES_BASEMENT)),
            new BuiltInLibrary(FIREBASE_MESSAGING, List.of(FIREBASE_COMMON, FIREBASE_IID, FIREBASE_MEASUREMENT_CONNECTOR, ANDROIDX_COLLECTION,
                    ANDROIDX_CORE, PLAY_SERVICES_BASEMENT, PLAY_SERVICES_TASKS), "com.google.firebase.messaging"),

            new BuiltInLibrary(FIREBASE_STORAGE, List.of(FIREBASE_AUTH_INTEROP, FIREBASE_COMMON, ANDROIDX_ANNOTATION, PLAY_SERVICES_BASE,
                    PLAY_SERVICES_TASKS)),

            new BuiltInLibrary(GLIDE, List.of(GLIDE_ANNOTATIONS, GLIDE_DISKLRUCACHE, GLIDE_GIFDECODER, ANDROIDX_EXIFINTERFACE, ANDROIDX_FRAGMENT,
                    ANDROIDX_VECTORDRAWABLE_ANIMATED), "com.bumptech.glide"),

            new BuiltInLibrary(GLIDE_ANNOTATIONS),
            new BuiltInLibrary(GLIDE_DISKLRUCACHE),
            new BuiltInLibrary(GLIDE_GIFDECODER, List.of(ANDROIDX_ANNOTATION)),
            new BuiltInLibrary(GOOGLE_AUTO_VALUE_ANNOTATIONS),
            new BuiltInLibrary(GSON),
            new BuiltInLibrary(GUAVA_LISTENABLEFUTURE),
            new BuiltInLibrary(HTTP_LEGACY_ANDROID_28),
            new BuiltInLibrary(JETBRAINS_ANNOTATIONS),
            new BuiltInLibrary(KOTLIN_STDLIB, List.of(JETBRAINS_ANNOTATIONS)),
            new BuiltInLibrary(KOTLIN_STDLIB_JDK7, List.of(KOTLIN_STDLIB)),
            new BuiltInLibrary(LOTTIE, List.of(ANDROIDX_APPCOMPAT, OKIO), "com.airbnb.lottie"),
            new BuiltInLibrary(MATERIAL, List.of(ANDROIDX_ANNOTATION, ANDROIDX_ANNOTATION_EXPERIMENTAL, ANDROIDX_APPCOMPAT, ANDROIDX_CARDVIEW,
                    ANDROIDX_CONSTRAINTLAYOUT, ANDROIDX_COORDINATORLAYOUT, ANDROIDX_CORE, ANDROIDX_DRAWERLAYOUT, ANDROIDX_DYNAMIC_ANIMATION,
                    ANDROIDX_FRAGMENT, ANDROIDX_LIFECYCLE_RUNTIME, ANDROIDX_RECYCLERVIEW, ANDROIDX_TRANSITION, ANDROIDX_VECTORDRAWABLE,
                    ANDROIDX_VIEWPAGER2), "com.google.android.material"),

            new BuiltInLibrary(OKHTTP, List.of(OKIO)),
            new BuiltInLibrary(OKIO),
            new BuiltInLibrary(ONESIGNAL, List.of(ANDROIDX_BROWSER, ANDROIDX_CARDVIEW, ANDROIDX_FRAGMENT, ANDROIDX_MEDIA, FIREBASE_MESSAGING,
                    PLAY_SERVICES_ADS_IDENTIFIER, PLAY_SERVICES_BASE, PLAY_SERVICES_LOCATION), "com.onesignal"),

            new BuiltInLibrary(OTPVIEW, List.of(ANDROIDX_APPCOMPAT, ANDROIDX_CORE_KTX, ANDROIDX_CONSTRAINTLAYOUT, KOTLIN_STDLIB_JDK7),
                    "affan.ahmad.otp"),

            new BuiltInLibrary(PATTERN_LOCK_VIEW, List.of(ANDROIDX_CORE, JETBRAINS_ANNOTATIONS), "com.andrognito.patternlockview"),
            new BuiltInLibrary(PLAY_SERVICES_ADS, List.of(PLAY_SERVICES_ADS_BASE, PLAY_SERVICES_ADS_IDENTIFIER,
                    PLAY_SERVICES_ADS_LITE, PLAY_SERVICES_APPSET, PLAY_SERVICES_BASEMENT, PLAY_SERVICES_TASKS,
                    ANDROIDX_BROWSER, ANDROIDX_COLLECTION, ANDROIDX_CORE),
                    "com.google.android.gms.ads.impl"),

            new BuiltInLibrary(PLAY_SERVICES_ADS_BASE, List.of(PLAY_SERVICES_BASEMENT)),
            new BuiltInLibrary(PLAY_SERVICES_ADS_IDENTIFIER, List.of(PLAY_SERVICES_BASEMENT)),
            new BuiltInLibrary(PLAY_SERVICES_ADS_LITE, List.of(PLAY_SERVICES_ADS_BASE, PLAY_SERVICES_BASEMENT,
                    PLAY_SERVICES_MEASUREMENT_SDK_API, ANDROIDX_WORK_RUNTIME, UMP_USER_MESSAGING_PLATFORM), "com.google.android.gms.ads"),

            new BuiltInLibrary(PLAY_SERVICES_APPSET, List.of(PLAY_SERVICES_BASE, PLAY_SERVICES_BASEMENT, PLAY_SERVICES_TASKS)),
            new BuiltInLibrary(PLAY_SERVICES_AUTH, List.of(PLAY_SERVICES_AUTH_API_PHONE, PLAY_SERVICES_AUTH_BASE, PLAY_SERVICES_BASE,
                    PLAY_SERVICES_BASEMENT, PLAY_SERVICES_TASKS, ANDROIDX_FRAGMENT, ANDROIDX_LOADER), "com.google.android.gms.auth.api"),

            new BuiltInLibrary(PLAY_SERVICES_AUTH_API_PHONE),
            new BuiltInLibrary(PLAY_SERVICES_AUTH_BASE),
            new BuiltInLibrary(PLAY_SERVICES_BASE, List.of(PLAY_SERVICES_BASEMENT, PLAY_SERVICES_TASKS, ANDROIDX_COLLECTION, ANDROIDX_CORE,
                    ANDROIDX_FRAGMENT), "com.google.android.gms.base"),

            new BuiltInLibrary(PLAY_SERVICES_BASEMENT, List.of(ANDROIDX_COLLECTION, ANDROIDX_CORE, ANDROIDX_FRAGMENT),
                    "com.google.android.gms.common"),

            new BuiltInLibrary(PLAY_SERVICES_GASS, List.of(PLAY_SERVICES_ADS_BASE, PLAY_SERVICES_BASEMENT)),
            new BuiltInLibrary(PLAY_SERVICES_GCM, List.of(PLAY_SERVICES_BASE, PLAY_SERVICES_BASEMENT, PLAY_SERVICES_IID, PLAY_SERVICES_STATS,
                    ANDROIDX_COLLECTION, ANDROIDX_CORE, ANDROIDX_LEGACY_SUPPORT_CORE_UTILS), "com.google.android.gms.gcm"),

            new BuiltInLibrary(PLAY_SERVICES_IID, List.of(PLAY_SERVICES_BASE, PLAY_SERVICES_BASEMENT, PLAY_SERVICES_STATS, PLAY_SERVICES_TASKS,
                    ANDROIDX_COLLECTION, ANDROIDX_CORE)),

            new BuiltInLibrary(PLAY_SERVICES_LOCATION, List.of(PLAY_SERVICES_BASE, PLAY_SERVICES_BASEMENT, PLAY_SERVICES_PLACES_PLACEREPORT,
                    PLAY_SERVICES_TASKS)),

            new BuiltInLibrary(PLAY_SERVICES_MAPS, List.of(PLAY_SERVICES_BASE, PLAY_SERVICES_BASEMENT, ANDROIDX_FRAGMENT),
                    "com.google.android.gms.maps"),

            new BuiltInLibrary(PLAY_SERVICES_MEASUREMENT_BASE, List.of(PLAY_SERVICES_BASEMENT)),
            new BuiltInLibrary(PLAY_SERVICES_MEASUREMENT_SDK_API, List.of(PLAY_SERVICES_BASEMENT, PLAY_SERVICES_MEASUREMENT_BASE)),
            new BuiltInLibrary(PLAY_SERVICES_PLACES_PLACEREPORT, List.of(PLAY_SERVICES_BASEMENT)),
            new BuiltInLibrary(PLAY_SERVICES_STATS, List.of(PLAY_SERVICES_BASEMENT, ANDROIDX_LEGACY_SUPPORT_CORE_UTILS)),
            new BuiltInLibrary(PLAY_SERVICES_TASKS, List.of(PLAY_SERVICES_BASEMENT)),
            new BuiltInLibrary(UMP_USER_MESSAGING_PLATFORM, List.of(ANDROIDX_ANNOTATION, PLAY_SERVICES_ADS_IDENTIFIER,
                    PLAY_SERVICES_BASEMENT)),
            new BuiltInLibrary(WAVE_SIDE_BAR, List.of(), "com.sayuti.lib"),
            new BuiltInLibrary(YOUTUBE_PLAYER, List.of(ANDROIDX_APPCOMPAT, ANDROIDX_RECYCLERVIEW, KOTLIN_STDLIB_JDK7),
                    "com.pierfrancescosoffritti.androidyoutubeplayer"),
    };

    public static File getLibraryPath(String libraryName) {
        return new File(EXTRACTED_BUILT_IN_LIBRARIES_PATH, libraryName);
    }

    public static String getLibraryPathString(String libraryName) {
        return getLibraryPath(libraryName).getAbsolutePath();
    }

    public static File getLibraryClassesJarPath(String libraryName) {
        return new File(getLibraryPath(libraryName), "classes.jar");
    }

    public static String getLibraryClassesJarPathString(String libraryName) {
        return getLibraryClassesJarPath(libraryName).getAbsolutePath();
    }

    public static File getLibraryDexFile(String libraryName) {
        return new File(EXTRACTED_BUILT_IN_LIBRARY_DEX_FILES_PATH, libraryName + ".dex");
    }

    public static String getLibraryDexFilePath(String libraryName) {
        return new File(EXTRACTED_BUILT_IN_LIBRARY_DEX_FILES_PATH, libraryName + ".dex").getAbsolutePath();
    }

    /**
     * @throws IllegalArgumentException Thrown if the specified library doesn't have any assets.
     */
    public static File getLibraryAssets(String libraryName) {
        Jp library = new Jp(libraryName);

        if (library.hasAssets()) {
            return new File(EXTRACTED_BUILT_IN_LIBRARIES_PATH, libraryName + File.separator + "assets");
        } else {
            throw new IllegalArgumentException("Built-in library '" + libraryName + "' doesn't have any assets.");
        }
    }

    /**
     * @throws IllegalArgumentException Thrown if the specified library doesn't have any assets.
     */
    public static String getLibraryAssetsPath(String libraryName) {
        return getLibraryAssets(libraryName).getAbsolutePath();
    }

    public static File getLibraryResources(String libraryName) {
        Jp library = new Jp(libraryName);

        if (library.hasResources()) {
            return new File(EXTRACTED_BUILT_IN_LIBRARIES_PATH, libraryName + File.separator + "res");
        } else {
            throw new IllegalArgumentException("Built-in library '" + libraryName + "' doesn't have any resources.");
        }
    }

    public static String getLibraryResourcesPath(String libraryName) {
        return getLibraryResources(libraryName).getAbsolutePath();
    }

    public static File getLibraryProguardConfiguration(String libraryName) {
        return new File(EXTRACTED_BUILT_IN_LIBRARIES_PATH, libraryName + File.separator + "proguard.txt");
    }

    public static String getLibraryProguardConfigurationPath(String libraryName) {
        return getLibraryProguardConfiguration(libraryName).getAbsolutePath();
    }

    public static void extractCompileAssets(@NonNull BuildProgressReceiver... progressReceivers) {
        if (!EXTRACTED_COMPILE_ASSETS_PATH.exists()) {
            if (!EXTRACTED_COMPILE_ASSETS_PATH.mkdirs()) {
                throw new RuntimeException(new IOException("Failed to create directory " + EXTRACTED_COMPILE_ASSETS_PATH));
            }
        }

        String dexsArchiveName = "dexs.zip";
        String libsArchiveName = "libs.zip";
        String testkeyArchiveName = "testkey.zip";

        String dexsArchivePath = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, dexsArchiveName).getAbsolutePath();
        String libsArchivePath = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, libsArchiveName).getAbsolutePath();
        String testkeyArchivePath = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, testkeyArchiveName).getAbsolutePath();
        String dexsDirectoryPath = BuiltInLibraries.EXTRACTED_BUILT_IN_LIBRARY_DEX_FILES_PATH.getAbsolutePath();
        String libsDirectoryPath = BuiltInLibraries.EXTRACTED_BUILT_IN_LIBRARIES_PATH.getAbsolutePath();
        String testkeyDirectoryPath = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, "testkey").getAbsolutePath();

        String baseAssetsPath = "libs" + File.separator;
        oB fileUtil = new oB(false);

        maybeExtractAndroidJar(progressReceivers);

        if (ProjectBuilder.hasFileChanged(baseAssetsPath + dexsArchiveName, dexsArchivePath)) {
            for (BuildProgressReceiver receiver : progressReceivers) {
                receiver.onProgress("Extracting built-in libraries' DEX files...", 4);
            }
            /* Delete the directory */
            fileUtil.b(dexsDirectoryPath);
            /* Create the directories */
            fileUtil.f(dexsDirectoryPath);
            /* Extract dexs.zip to dexs/ */
            new KB().a(dexsArchivePath, dexsDirectoryPath);
        }
        if (ProjectBuilder.hasFileChanged(baseAssetsPath + libsArchiveName, libsArchivePath)) {
            for (BuildProgressReceiver receiver : progressReceivers) {
                receiver.onProgress("Extracting built-in libraries' resources...", 5);
            }
            /* Delete the directory */
            fileUtil.b(libsDirectoryPath);
            /* Create the directories */
            fileUtil.f(libsDirectoryPath);
            /* Extract libs.zip to libs/ */
            new KB().a(libsArchivePath, libsDirectoryPath);
        }
        maybeExtractCoreLambdaStubsJar();
        if (ProjectBuilder.hasFileChanged(baseAssetsPath + testkeyArchiveName, testkeyArchivePath)) {
            for (BuildProgressReceiver receiver : progressReceivers) {
                receiver.onProgress("Extracting built-in signing keys...", 6);
            }
            /* Delete the directory */
            fileUtil.b(testkeyDirectoryPath);
            /* Create the directories */
            fileUtil.f(testkeyDirectoryPath);
            /* Extract testkey.zip to testkey/ */
            new KB().a(testkeyArchivePath, testkeyDirectoryPath);
        }
    }

    public static void maybeExtractAndroidJar(@NonNull BuildProgressReceiver... receivers) {
        String androidJarArchiveName = "android.jar.zip";
        String androidJarPath = new File(EXTRACTED_COMPILE_ASSETS_PATH, androidJarArchiveName).getAbsolutePath();
        if (ProjectBuilder.hasFileChanged("libs" + File.separator + androidJarArchiveName, androidJarPath)) {
            for (BuildProgressReceiver receiver : receivers) {
                receiver.onProgress("Extracting built-in android.jar...", 7);
            }
            /* Delete android.jar */
            new oB().c(EXTRACTED_COMPILE_ASSETS_PATH.getAbsolutePath() + File.separator + "android.jar");
            /* Extract android.jar.zip to android.jar */
            new KB().a(androidJarPath, EXTRACTED_COMPILE_ASSETS_PATH.getAbsolutePath());
        }
    }

    public static void maybeExtractCoreLambdaStubsJar() {
        String coreLambdaStubsJarName = "core-lambda-stubs.jar";
        String coreLambdaStubsJarPath = new File(BuiltInLibraries.EXTRACTED_COMPILE_ASSETS_PATH, coreLambdaStubsJarName).getAbsolutePath();
        ProjectBuilder.hasFileChanged("libs" + File.separator + coreLambdaStubsJarName, coreLambdaStubsJarPath);
    }

    public static class BuiltInLibrary implements Parcelable {
        public static final Creator<BuiltInLibrary> CREATOR = new Creator<>() {
            @Override
            public BuiltInLibrary createFromParcel(Parcel in) {
                return new BuiltInLibrary(in);
            }

            @Override
            public BuiltInLibrary[] newArray(int size) {
                return new BuiltInLibrary[size];
            }
        };
        private final String name;
        private final List<String> dependencyNames;
        private final String packageName;
        private final boolean hasResources;

        /**
         * @param packageName Can be <code>null</code> for no resources, though then
         *                    {@link #BuiltInLibrary(String, List)} is advised.
         */
        public BuiltInLibrary(String name, List<String> dependencyNames, String packageName) {
            this.name = name;
            this.dependencyNames = dependencyNames;
            this.packageName = packageName;
            hasResources = packageName != null;
        }

        /**
         * Constructs a {@link BuiltInLibrary} with specified dependencies but no resources.
         */
        public BuiltInLibrary(String name, List<String> dependencyNames) {
            this(name, dependencyNames, null);
        }

        /**
         * Constructs a {@link BuiltInLibrary} with no dependencies and resources.
         */
        public BuiltInLibrary(String name) {
            this(name, List.of());
        }

        protected BuiltInLibrary(Parcel in) {
            name = in.readString();
            dependencyNames = in.createStringArrayList();
            packageName = in.readString();
            hasResources = in.readInt() != 0;
        }

        public static Optional<BuiltInLibrary> ofName(String name) {
            for (BuiltInLibrary builtInLibrary : KNOWN_BUILT_IN_LIBRARIES) {
                if (builtInLibrary.getName().equals(name)) {
                    return Optional.of(builtInLibrary);
                }
            }

            return Optional.empty();
        }

        public String getName() {
            return name;
        }

        public List<String> getDependencyNames() {
            return dependencyNames;
        }

        public Optional<String> getPackageName() {
            return packageName == null ? Optional.empty() : Optional.of(packageName);
        }

        public boolean hasResources() {
            return hasResources;
        }

        @Override
        @NonNull
        public String toString() {
            return "BuiltInLibrary{" +
                    "name='" + name + '\'' +
                    ", dependencyNames=" + dependencyNames +
                    ", packageName='" + packageName + '\'' +
                    ", hasResources=" + hasResources +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeStringList(dependencyNames);
            dest.writeString(packageName);
            dest.writeInt(hasResources ? 1 : 0);
        }
    }
}
