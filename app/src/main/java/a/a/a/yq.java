package a.a.a;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.core.content.ContextCompat;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.beans.SrcCodeBean;
import com.sketchware.remod.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.build.BuildSettings;
import mod.hey.studios.project.ProjectSettings;
import mod.hilal.saif.blocks.CommandBlock;

public class yq {

    /**
     * Firebase Database storage location RegExp matcher to remove unwanted parts of storage URL
     * to get the actual project ID.
     * <p/>
     * Users should enter the entire storage URL without <code>https://</code> at the beginning and
     * <code>/</code> at the end, e.g. <code>sk-pro-default-rtdb.firebaseio.com</code>.
     */
    private static final String FIREBASE_DATABASE_STORAGE_LOCATION_MATCHER = "(-default-rtdb)?\\.[a-z](.?)+";

    /**
     * Assets directory of current project,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/assets
     * <p>
     * Proposed new name: assetsPath
     */
    public final String A;

    /**
     * Imported fonts directory of current project,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/assets/fonts
     * <p>
     * Proposed new name: fontsPath
     */
    public final String B;

    /**
     * Path of compiled resources in a ZIP file of current project,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/InternalDemo.apk.res
     * <p>
     * Proposed new name: resourcesApkPath
     */
    public final String C;

    /**
     * DEX file called classes.dex (??),
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/classes.dex
     * <p>
     * Proposed new name: classesDexPath
     */
    public final String E;

    /**
     * Unsigned APK file's path,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/InternalDemo.apk.unsigned
     * <p>
     * Proposed new name: unsignedUnalignedApkPath
     */
    public final String G;

    /**
     * Signed APK file's path,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/InternalDemo.apk
     * <p>
     * Proposed new name: finalToInstallApkPath
     */
    public final String H;

    /**
     * Release APK file's path,
     * e.g. /storage/emulated/0/sketchware/signed_apk/InternalDemo_release.apk
     * <p>
     * Proposed new name: releaseApkPath
     */
    public final String I;

    /**
     * Path of ZIP file containing project's sources (only used/set at {@link com.besome.sketch.export.ExportProjectActivity}).
     * <p>
     * Proposed new name: exportedSourcesZipPath
     */
    public String J;

    /**
     * Unknown,
     * e.g. [SketchApplication.java, DebugActivity.java]
     */
    public final ArrayList<String> K;

    /**
     * Proposed new name: fileUtil
     */
    public final oB L;
    public jq N;
    public final Zo O;

    /**
     * ProGuard rules file's path,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/aapt_rules.pro
     */
    public String aapt_rules;

    /**
     * Path of the aligned, still unsigned APK,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/InternalDemo.apk.unsigned.aligned
     * <p>
     * Proposed new name: unsignedAlignedApkPath
     */
    public final String alignedApkPath;

    /**
     * Proposed new name: sc_id
     */
    public final String b;

    /**
     * Project's mysc folder path,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/
     *
     * Proposed new name: projectMyscPath
     */
    public final String c;

    /**
     * ProGuarded classes.jar file's path of current project,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/classes_proguard.jar
     */
    public String classes_proguard;

    /**
     * Proposed new name: projectName
     */
    public final String d;

    /**
     * Proposed new name: packageName
     */
    public final String e;

    /**
     * Proposed new name: applicationName
     */
    public final String f;

    /**
     * Proposed new name: colorAccent
     */
    public final int g;

    /**
     * Proposed new name: colorPrimary
     */
    public final int h;

    /**
     * Proposed new name: colorPrimaryDark
     */
    public final int i;

    /**
     * Proposed new name: colorControlHighlight
     */
    public final int j;

    /**
     * Proposed new name: colorControlNormal
     */
    public final int k;

    /**
     * Proposed new name: versionCode
     */
    public final String l;

    /**
     * Proposed new name: versionName
     */
    public final String m;

    /**
     * Package name of current project,
     * but "folders" separated with slashes (/) instead of periods (.),
     * e.g. com/jbk/internal/demo
     *
     * Proposed new name: packageNameAsFolders
     */
    public final String n;

    /**
     * Path of file containing the ProGuard mapping,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/mapping.txt
     */
    public String printmapping;

    /**
     * Path of file containing ProGuard seeds,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/seeds.txt
     */
    public String printseeds;

    /**
     * Path of file containing ProGuard usage,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/usage.txt
     */
    public String printusage;

    /**
     * Current project's ProjectSettings object
     */
    public ProjectSettings projectSettings;

    /**
     * Compiled AndroidManifest.xml's path of current project,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/AndroidManifest.xml
     *
     * Proposed new name: androidManifestPath
     */
    public final String r;

    /**
     * Path of ProGuard rules generated by a resource processor,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/rules_generated.pro
     */
    public String rules_generated;

    /**
     * Project's generated Java files directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main
     *
     * Proposed new name: generatedFilesPath
     */
    public final String s;

    /**
     * Project's compiled binary directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin
     *
     * Proposed new name: binDirectoryPath
     */
    public final String t;

    /**
     * Project's compiled Java classes directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/classes
     *
     * Proposed new name: compiledClassesPath
     */
    public final String u;

    /**
     * Path of the unaligned but signed APK,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/InternalDemo.apk.signed.unaligned
     */
    public final String unalignedSignedApkPath;

    /**
     * Project's generated R.java files directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/gen
     *
     * Proposed new name: rJavaDirectoryPath
     */
    public final String v;

    /**
     * Generated project resources directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/res
     *
     * Proposed new name: resFilesPath
     */
    public final String w;

    /**
     * Project's generated layout files directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/res/layout
     *
     * Proposed new name: layoutFilesPath
     */
    public final String x;

    /**
     * Project's generated Java files directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/java
     *
     * Proposed new name: javaFilesPath
     */
    public final String y;

    /**
     * Project's imported sounds directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/res/raw
     *
     * Proposed new name: importedSoundsPath
     */
    public final String z;

    public yq(Context context, String sc_id) {
        this(context, wq.d(sc_id), lC.b(sc_id));
    }

    public yq(Context context, String myscFolderPath, HashMap<String, Object> metadata) {
        N = new jq();
        b = yB.c(metadata, "sc_id");
        c = myscFolderPath.endsWith(File.separator) ? myscFolderPath : myscFolderPath + File.separator;
        e = yB.c(metadata, "my_sc_pkg_name");
        d = yB.c(metadata, "my_ws_name");
        f = yB.c(metadata, "my_app_name");
        l = yB.c(metadata, "sc_ver_code");
        m = yB.c(metadata, "sc_ver_name");

        g = yB.a(metadata, "color_accent", ContextCompat.getColor(context, R.color.color_accent));
        h = yB.a(metadata, "color_primary", ContextCompat.getColor(context, R.color.color_primary));
        i = yB.a(metadata, "color_primary_dark", ContextCompat.getColor(context, R.color.color_primary_dark));
        j = yB.a(metadata, "color_control_highlight", ContextCompat.getColor(context, R.color.color_control_highlight));
        k = yB.a(metadata, "color_control_normal", ContextCompat.getColor(context, R.color.color_control_normal));
        projectSettings = new ProjectSettings(b);

        L = new oB(true);
        O = new Zo(context);
        K = new ArrayList<>();
        K.add("SketchApplication.java");
        K.add("DebugActivity.java");
        n = e.replaceAll("\\.", File.separator);
        t = c + "bin";
        u = t + File.separator + "classes";
        classes_proguard = t + File.separator + "classes_proguard.jar";
        aapt_rules = t + File.separator + "aapt_rules.pro";
        printseeds = t + File.separator + "seeds.txt";
        printusage = t + File.separator + "usage.txt";
        printmapping = t + File.separator + "mapping.txt";
        rules_generated = t + File.separator + "rules_generated.pro";
        v = c + "gen";
        s = c + "app" + File.separator + "src" + File.separator + "main";
        y = s + File.separator + "java";
        w = s + File.separator + "res";
        x = w + File.separator + "layout";
        z = w + File.separator + "raw";
        A = s + File.separator + "assets";
        B = A + File.separator + "fonts";
        r = c + "app" + File.separator + "src" + File.separator + "main" + File.separator + "AndroidManifest.xml";
        C = t + File.separator + d + ".apk.res";
        E = t + File.separator + "classes.dex";
        G = t + File.separator + d + ".apk.unsigned";
        alignedApkPath = G + ".aligned";
        unalignedSignedApkPath = t + File.separator + d + ".apk.signed.unaligned";
        H = t + File.separator + d + ".apk";
        I = wq.o() + File.separator + d + "_release.apk";
    }

    /**
     * Deletes the directory {@link yq#w}/values-v21/.
     */
    public void a() {
        File file = new File(w + File.separator + "values-v21");
        if (file.exists()) {
            L.a(file);
        }
    }

    /**
     * Does nothing.
     */
    public void b() {
    }

    /**
     * Does nothing.
     */
    public void c() {
    }

    /**
     * Creates {@link yq#t}, {@link yq#u}, {@link yq#v}, {@link yq#y}, {@link yq#w}, {@link yq#x},
     * {@link yq#z}, {@link yq#A}, {@link yq#B}, then generates DebugActivity.java and
     * SketchApplication.java.
     */
    public void c(Context context) {
        L.f(t);
        L.f(u);
        L.f(v);
        L.f(y);
        L.f(w);
        L.f(x);
        L.f(z);
        L.f(A);
        L.f(B);
        a(context);
    }

    /**
     * Delete {@link yq#I} if it exists.
     */
    public void d() {
        if (g()) {
            new File(I).delete();
        }
    }

    /**
     * Prepares to compile and creates /Internal storage/.sketchware/mysc/&lt;sc_id&gt;/ directories,
     * {@link yq#t}, {@link yq#u} and {@link yq#v}.
     */
    public void e() {
        L.f(t);
        L.f(u);
        L.f(v);
    }

    /**
     * Deletes temporary compile cache directories, {@link yq#t} and {@link yq#v}. The used method
     * logs all files and folders which get deleted.
     */
    public void f() {
        L.b(t);
        L.b(v);
    }

    /**
     * @return If {@link yq#I} exists.
     */
    public boolean g() {
        return new File(I).exists();
    }

    /**
     * Generates top-level build.gradle, build.gradle for module ':app' and settings.gradle files.
     */
    public void h() {
        L.b(c + File.separator + "app" + File.separator + "build.gradle",
                Lx.a(28, 21, 28, N));
        L.b(c + File.separator + "settings.gradle", Lx.a());
        L.b(c + File.separator + "build.gradle", Lx.c("3.4.2", "4.3.3"));
    }

    /**
     * Extracts a ZIP archive from assets to {@link yq#w}.
     */
    public void a(Context context, String str) {
        try {
            KB.a(context, str, w);
        } catch (Exception e2) {
            Log.e("ERROR", e2.getMessage(), e2);
        }
    }

    /**
     * Copies a file to the project's app icon path, {@link yq#w}/drawable-xhdpi/app_icon.png
     */
    public void a(String iconPath) {
        try {
            L.a(iconPath, w + File.separator + "drawable-xhdpi" + File.separator + "app_icon.png");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /**
     * Generates DebugActivity.java and SketchApplication.java. and SketchLogger.java(If Necessary)
     */
    public void a(Context context) {

        boolean logCatEnabled = N.isDebugBuild && new BuildSettings(b).getValue(
                BuildSettings.SETTING_ENABLE_LOGCAT, BuildSettings.SETTING_GENERIC_VALUE_FALSE).equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE);

        String javaDir = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + b + "/files/java/";
        if (!new File(javaDir, "DebugActivity.java").exists()) {
            L.b(y + File.separator
                            + n + File.separator
                            + "DebugActivity.java",
                    L.b(
                            context,
                            "debug" + File.separator
                                    + "DebugActivity.java"
                    ).replaceAll("<\\?package_name\\?>", e));
        }

        if (!new File(javaDir, "SketchApplication.java").exists()) {
            boolean applyMultiDex = projectSettings.getMinSdkVersion() < 21;

            String sketchApplicationFileContent = L.b(
                    context,
                    "debug" + File.separator + "SketchApplication.java"
            ).replaceAll("<\\?package_name\\?>", e);
            if (applyMultiDex) {
                sketchApplicationFileContent = sketchApplicationFileContent.replaceAll(
                        "Application \\{", "androidx.multidex.MultiDexApplication \\{");
            }
            if (logCatEnabled) {
                sketchApplicationFileContent = sketchApplicationFileContent.replace(
                        "super.onCreate();", "SketchLogger.startLogging();\n" +
                                "        super.onCreate();").replace(
                        "Process.killProcess(Process.myPid());",
                        "                SketchLogger.broadcastLog(Log.getStackTraceString(throwable));\n" +
                                "                SketchLogger.stopLogging();\n" + "Process.killProcess(Process.myPid());"
                );
            }

            L.b(y + File.separator
                            + n + File.separator
                            + "SketchApplication.java",
                    sketchApplicationFileContent);
        }

        if (logCatEnabled) {
            if (!new File(javaDir, "SketchLogger.java").exists()) {
                L.b(y + File.separator
                                + n + File.separator
                                + "SketchLogger.java",
                        L.b(
                                context,
                                "debug" + File.separator
                                        + "SketchLogger.java"
                        ).replaceAll("<\\?package_name\\?>", e));
            }
        }
    }

    /**
     * Writes a project file to its correct location. Java files, for example, get saved to
     * <pre>
     *     {@link yq#y} + File.separator + {@link yq#n}
     * </pre>, while AndroidManifest.xml gets saved to {@link yq#r}.
     */
    public void a(String fileName, String fileContent) {
        if (fileName.endsWith("java")) {
            L.b(y + File.separator + n + File.separator + fileName, fileContent);
        } else if (fileName.equals("AndroidManifest.xml")) {
            L.b(r, fileContent);
        } else if (fileName.equals("colors.xml") || fileName.equals("styles.xml") || fileName.equals("strings.xml")) {
            L.b(w + File.separator + "values" + File.separator + fileName, fileContent);
        } else if (fileName.equals("provider_paths.xml")) {
            L.b(w + File.separator + "xml" + File.separator + fileName, fileContent);
        } else {
            L.b(x + File.separator + fileName, fileContent);
        }
    }

    public void a(iC projectLibraryManager, hC projectFileManager, eC projectDataManager, boolean exportingProject) {
        ProjectLibraryBean adMob = projectLibraryManager.b();
        ProjectLibraryBean appCompat = projectLibraryManager.c();
        ProjectLibraryBean firebase = projectLibraryManager.d();
        ProjectLibraryBean googleMaps = projectLibraryManager.e();
        N = new jq();
        N.packageName = e;
        N.projectName = f;
        N.versionCode = l;
        N.versionName = m;
        N.sc_id = b;
        N.e = O.h();
        N.isDebugBuild = !exportingProject;
        if (firebase.useYn.equals(ProjectLibraryBean.LIB_USE_Y)) {
            N.isFirebaseEnabled = true;
            N.addPermission(jq.PERMISSION_INTERNET);
            N.addPermission(jq.PERMISSION_ACCESS_NETWORK_STATE);
        }
        if (appCompat.useYn.equals(ProjectLibraryBean.LIB_USE_Y)) {
            N.isAppCompatUsed = true;
        }
        if (adMob.useYn.equals(ProjectLibraryBean.LIB_USE_Y)) {
            N.isAdMobEnabled = true;
            N.addPermission(jq.PERMISSION_INTERNET);
            N.addPermission(jq.PERMISSION_ACCESS_NETWORK_STATE);
            N.setupAdmob(adMob);
        }
        if (googleMaps.useYn.equals(ProjectLibraryBean.LIB_USE_Y)) {
            N.isMapUsed = true;
            N.addPermission(jq.PERMISSION_INTERNET);
            N.addPermission(jq.PERMISSION_ACCESS_NETWORK_STATE);
            N.setupGoogleMap(googleMaps);
        }
        for (ProjectFileBean next : projectFileManager.b()) {
            if (next.hasActivityOption(ProjectFileBean.OPTION_ACTIVITY_DRAWER)) {
                N.a(next.getActivityName()).a = true;
            }
            for (ComponentBean component : projectDataManager.e(next.getJavaName())) {
                N.x.handleComponent(component.type);

                switch (component.type) {
                    case ComponentBean.COMPONENT_TYPE_CAMERA:
                    case 35:
                        N.isAppCompatUsed = true;
                        N.u = true;
                        N.addPermission(next.getActivityName(), jq.PERMISSION_CAMERA);
                        N.addPermission(next.getActivityName(), jq.PERMISSION_READ_EXTERNAL_STORAGE);
                        N.addPermission(next.getActivityName(), jq.PERMISSION_WRITE_EXTERNAL_STORAGE);
                        break;

                    case ComponentBean.COMPONENT_TYPE_FILE_PICKER:
                        N.addPermission(next.getActivityName(), jq.PERMISSION_READ_EXTERNAL_STORAGE);
                        break;

                    case ComponentBean.COMPONENT_TYPE_FIREBASE:
                        N.isGsonUsed = true;
                        N.isFirebaseDatabaseUsed = true;
                        N.addPermission(next.getActivityName(), jq.PERMISSION_INTERNET);
                        N.addPermission(next.getActivityName(), jq.PERMISSION_ACCESS_NETWORK_STATE);
                        break;

                    case ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE:
                        N.isFirebaseStorageUsed = true;
                        N.addPermission(next.getActivityName(), jq.PERMISSION_READ_EXTERNAL_STORAGE);
                        N.addPermission(next.getActivityName(), jq.PERMISSION_WRITE_EXTERNAL_STORAGE);
                        break;

                    case ComponentBean.COMPONENT_TYPE_VIBRATOR:
                        N.addPermission(next.getActivityName(), jq.PERMISSION_VIBRATE);
                        break;

                    case ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH:
                        N.isFirebaseAuthUsed = true;
                        N.a(next.getActivityName()).b = true;
                        break;

                    case ComponentBean.COMPONENT_TYPE_REQUEST_NETWORK:
                        N.isGsonUsed = true;
                        N.isHttp3Used = true;
                        N.addPermission(next.getActivityName(), jq.PERMISSION_INTERNET);
                        N.addPermission(next.getActivityName(), jq.PERMISSION_ACCESS_NETWORK_STATE);
                        break;

                    case ComponentBean.COMPONENT_TYPE_SPEECH_TO_TEXT:
                        N.addPermission(next.getActivityName(), jq.PERMISSION_RECORD_AUDIO);
                        break;

                    case ComponentBean.COMPONENT_TYPE_BLUETOOTH_CONNECT:
                        N.addPermission(next.getActivityName(), jq.PERMISSION_BLUETOOTH);
                        N.addPermission(next.getActivityName(), jq.PERMISSION_BLUETOOTH_ADMIN);
                        break;

                    case ComponentBean.COMPONENT_TYPE_LOCATION_MANAGER:
                        N.addPermission(next.getActivityName(), jq.PERMISSION_ACCESS_FINE_LOCATION);
                        break;

                    case ComponentBean.COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS:
                        N.isDynamicLinkUsed = true;
                        break;

                    default:
                }
            }

            for (Map.Entry<String, ArrayList<BlockBean>> entry : projectDataManager.b(next.getJavaName()).entrySet()) {
                for (BlockBean bean : entry.getValue()) {
                    String opCode = bean.opCode;
                    N.x.setParams(bean.parameters, e, opCode);

                    switch (opCode) {
                        case "FirebaseDynamicLink setDataHost":
                        case "setDynamicLinkDataHost":
                            if (bean.parameters.size() >= 2) {
                                N.dlDataList.add(new Pair<>(bean.parameters.get(0), bean.parameters.get(1)));
                            }
                            break;

                        case "setAdmobAppId":
                            N.appId = bean.parameters.get(0);
                            break;

                        case "intentSetAction":
                            // If an Intent setAction (ACTION_CALL) block is used
                            if (bean.parameters.get(1).equals(uq.c[1])) {
                                N.addPermission(next.getActivityName(), jq.PERMISSION_CALL_PHONE);
                            }
                            break;

                        case "fileutilread":
                        case "fileutilisexist":
                        case "fileutillistdir":
                        case "fileutilisdir":
                        case "fileutilisfile":
                        case "fileutillength":
                        case "fileutilStartsWith":
                        case "fileutilEndsWith":
                        case "getJpegRotate":
                        case "setImageFilePath":
                        case "fileutilGetLastSegmentPath":
                            N.addPermission(next.getActivityName(), jq.PERMISSION_READ_EXTERNAL_STORAGE);
                            break;

                        case "fileutilwrite":
                        case "fileutilcopy":
                        case "fileutilcopydir":
                        case "fileutilmove":
                        case "fileutildelete":
                        case "fileutilmakedir":
                        case "resizeBitmapFileRetainRatio":
                        case "resizeBitmapFileToSquare":
                        case "resizeBitmapFileToCircle":
                        case "resizeBitmapFileWithRoundedBorder":
                        case "cropBitmapFileFromCenter":
                        case "rotateBitmapFile":
                        case "scaleBitmapFile":
                        case "skewBitmapFile":
                        case "setBitmapFileColorFilter":
                        case "setBitmapFileBrightness":
                        case "setBitmapFileContrast":
                            N.addPermission(next.getActivityName(), jq.PERMISSION_READ_EXTERNAL_STORAGE);
                            N.addPermission(next.getActivityName(), jq.PERMISSION_WRITE_EXTERNAL_STORAGE);
                            break;

                        case "strToMap":
                        case "mapToStr":
                        case "strToListMap":
                        case "listMapToStr":
                        case "GsonListTojsonString":
                        case "GsonStringToListString":
                        case "GsonStringToListNumber":
                            N.isGsonUsed = true;
                            break;

                        case "setImageUrl":
                            N.isGlideUsed = true;
                            N.addPermission(jq.PERMISSION_INTERNET);
                            break;

                        case "webViewLoadUrl":
                            N.addPermission(jq.PERMISSION_INTERNET);
                            N.addPermission(jq.PERMISSION_ACCESS_NETWORK_STATE);
                            break;

                        default:
                    }
                }
            }
            N.b();
        }
    }

    /**
     * Simply calls {@link yq#b(hC, eC, iC, boolean)} with the same arguments and <code>false</code>.
     *
     * @see yq#b(hC, eC, iC, boolean)
     */
    public void b(hC projectFileManager, eC projectDataManager, iC projectLibraryManager) {
        b(projectFileManager, projectDataManager, projectLibraryManager, false);
    }

    /**
     * Generates the project's files, such as layouts, Java files, but also build.gradle and secrets.xml.
     */
    public void b(hC projectFileManager, eC projectDataManger, iC projectLibraryManager, boolean exportingProject) {
        ArrayList<SrcCodeBean> srcCodeBeans = a(projectFileManager, projectDataManger, projectLibraryManager, exportingProject);
        if (N.u) {
            Nx pathsTag = new Nx("paths");
            pathsTag.a("xmlns", "android", "http://schemas.android.com/apk/res/android");
            Nx externalPathTag = new Nx("external-path");
            externalPathTag.a("", "name", "external_files");
            externalPathTag.a("", "path", ".");
            pathsTag.a(externalPathTag);
            srcCodeBeans.add(new SrcCodeBean("provider_paths.xml",
                    CommandBlock.applyCommands("xml/provider_paths.xml", pathsTag.toCode())));
        }

        for (SrcCodeBean bean : srcCodeBeans) {
            a(bean.srcFileName, bean.source);
        }
        if (N.isFirebaseEnabled || N.isAdMobEnabled || N.isMapUsed) {
            ProjectLibraryBean firebaseLibrary = projectLibraryManager.d();
            Mx mx = new Mx();
            mx.a("google_play_services_version", 12451000);
            if (N.isFirebaseEnabled) {
                mx.a("firebase_database_url", "https://" + firebaseLibrary.data, false);
                mx.a("project_id", firebaseLibrary.data.trim().replaceAll(FIREBASE_DATABASE_STORAGE_LOCATION_MATCHER, ""), false);
                mx.a("google_app_id", firebaseLibrary.reserved1, false);
                if (firebaseLibrary.reserved2 != null && firebaseLibrary.reserved2.length() > 0) {
                    mx.a("google_api_key", firebaseLibrary.reserved2, false);
                }
                if (firebaseLibrary.reserved3 != null && firebaseLibrary.reserved3.length() > 0) {
                    mx.a("google_storage_bucket", firebaseLibrary.reserved3, false);
                }
            }
            if (N.isMapUsed) {
                // if p3 is false, then "translatable="false" will be added
                mx.a("google_maps_key", projectLibraryManager.e().data, false);
            }
            String filePath = "values/secrets.xml";
            L.b(w + File.separator + filePath,
                    CommandBlock.applyCommands(filePath, mx.toCode()));
        }
        h();
    }

    /**
     * Get source code files that are viewable in SrcCodeViewer
     */
    public ArrayList<SrcCodeBean> a(hC projectFileManager, eC projectDataManager, iC projectLibraryManager, boolean exportingProject) {
        a(projectLibraryManager, projectFileManager, projectDataManager, exportingProject);
        CommandBlock.x();

        final String javaDir = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + b + "/files/java/";
        final String layoutDir = FileUtil.getExternalStorageDir() + "/.sketchware/data/" + b + "/files/resource/layout/";
        List<File> javaFiles;
        {
            File[] files = new File(javaDir).listFiles();
            if (files == null) files = new File[0];
            javaFiles = Arrays.asList(files);
        }
        List<File> layoutFiles;
        {
            File[] files = new File(layoutDir).listFiles();
            if (files == null) files = new File[0];
            layoutFiles = Arrays.asList(files);
        }

        // Generate Activities unless a custom version of it exists already
        // at /Internal storage/.sketchware/data/<sc_id>/files/java/
        ArrayList<SrcCodeBean> srcCodeBeans = new ArrayList<>();
        for (ProjectFileBean activity : projectFileManager.b()) {
            if (!javaFiles.contains(new File(javaDir + activity.getJavaName()))) {
                srcCodeBeans.add(new SrcCodeBean(activity.getJavaName(),
                        new Jx(N, activity, projectDataManager).generateCode()));
            }
        }

        // Generate layouts unless a custom version of it exists already
        // at /Internal storage/.sketchware/data/<sc_id>/files/resource/layout/
        {
            ArrayList<ProjectFileBean> regularLayouts = projectFileManager.b();
            for (ProjectFileBean layout : regularLayouts) {
                String xmlName = layout.getXmlName();
                Ox ox = new Ox(N, layout);
                ox.a(eC.a(projectDataManager.d(xmlName)), projectDataManager.h(xmlName));
                if (!layoutFiles.contains(new File(layoutDir + xmlName))) {
                    srcCodeBeans.add(new SrcCodeBean(xmlName,
                            CommandBlock.applyCommands(xmlName, ox.b())));
                }
            }
        }
        {
            ArrayList<ProjectFileBean> drawerLayouts = projectFileManager.c();
            for (ProjectFileBean drawerFile : drawerLayouts) {
                String xmlName = drawerFile.getXmlName();
                Ox ox = new Ox(N, drawerFile);
                ox.a(eC.a(projectDataManager.d(xmlName)));
                if (!layoutFiles.contains(new File(layoutDir + xmlName))) {
                    srcCodeBeans.add(new SrcCodeBean(xmlName,
                            CommandBlock.applyCommands(xmlName, ox.b())));
                }
            }
        }

        Ix ix = new Ix(N, projectFileManager.b());
        ix.setYq(this);

        // Make generated classes viewable
        if (!javaFiles.contains(new File(javaDir + "SketchwareUtil.java"))) {
            srcCodeBeans.add(new SrcCodeBean("SketchwareUtil.java",
                    Lx.i(e)));
        }

        if (!javaFiles.contains(new File(javaDir + "FileUtil.java"))) {
            srcCodeBeans.add(new SrcCodeBean("FileUtil.java",
                    Lx.e(e)));
        }

        if (!javaFiles.contains(new File(javaDir + "RequestNetwork.java")) && N.isHttp3Used) {
            srcCodeBeans.add(new SrcCodeBean("RequestNetwork.java",
                    Lx.j(Lx.h(e))));
        }

        if (!FileUtil.isExistFile(javaDir + "RequestNetworkController.java") && N.isHttp3Used) {
            srcCodeBeans.add(new SrcCodeBean("RequestNetworkController.java",
                    Lx.j(Lx.g(e))));
        }

        if (!javaFiles.contains(new File(javaDir + "BluetoothConnect.java")) && N.hasPermission(jq.PERMISSION_BLUETOOTH)) {
            srcCodeBeans.add(new SrcCodeBean("BluetoothConnect.java",
                    Lx.j(Lx.b(e))));
        }

        if (!javaFiles.contains(new File(javaDir + "BluetoothController.java")) && N.hasPermission(jq.PERMISSION_BLUETOOTH)) {
            srcCodeBeans.add(new SrcCodeBean("BluetoothController.java",
                    Lx.j(Lx.c(e))));
        }

        if (N.isMapUsed) {
            if (!javaFiles.contains(new File(javaDir + "GoogleMapController.java")) && N.isMapUsed) {
                srcCodeBeans.add(new SrcCodeBean("GoogleMapController.java",
                        Lx.j(Lx.f(e))));
            }
        }

        srcCodeBeans.add(new SrcCodeBean("AndroidManifest.xml",
                CommandBlock.applyCommands("AndroidManifest.xml", ix.a())));
        if (N.isAppCompatUsed) {
            boolean useNewMaterialComponentsTheme = projectSettings.getValue(ProjectSettings.SETTING_ENABLE_BRIDGELESS_THEMES,
                    BuildSettings.SETTING_GENERIC_VALUE_FALSE).equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE);

            Mx colorsFileBuilder = new Mx();
            colorsFileBuilder.a("colorPrimary", String.format("#%06X", h & 0xffffff));
            colorsFileBuilder.a("colorPrimaryDark", String.format("#%06X", i & 0xffffff));
            colorsFileBuilder.a("colorAccent", String.format("#%06X", g & 0xffffff));
            colorsFileBuilder.a("colorControlHighlight", String.format("#%06X", j & 0xffffff));
            colorsFileBuilder.a("colorControlNormal", String.format("#%06X", k & 0xffffff));
            srcCodeBeans.add(new SrcCodeBean("colors.xml",
                    CommandBlock.applyCommands("colors.xml", colorsFileBuilder.toCode())));

            Mx stylesFileBuilder = new Mx();
            stylesFileBuilder.c("AppTheme", "Theme.MaterialComponents.Light.NoActionBar" + (useNewMaterialComponentsTheme ? "" : ".Bridge"));
            stylesFileBuilder.a("AppTheme", "colorPrimary", "@color/colorPrimary");
            stylesFileBuilder.a("AppTheme", "colorPrimaryDark", "@color/colorPrimaryDark");
            stylesFileBuilder.a("AppTheme", "colorAccent", "@color/colorAccent");
            stylesFileBuilder.a("AppTheme", "colorControlHighlight", "@color/colorControlHighlight");
            stylesFileBuilder.a("AppTheme", "colorControlNormal", "@color/colorControlNormal");
            stylesFileBuilder.c("AppTheme.FullScreen", "AppTheme");
            stylesFileBuilder.a("AppTheme.FullScreen", "android:windowFullscreen", "true");
            stylesFileBuilder.a("AppTheme.FullScreen", "android:windowContentOverlay", "@null");
            stylesFileBuilder.c("AppTheme.AppBarOverlay", "ThemeOverlay.MaterialComponents.Dark.ActionBar");
            stylesFileBuilder.c("AppTheme.PopupOverlay", "ThemeOverlay.MaterialComponents.Light");
            srcCodeBeans.add(new SrcCodeBean("styles.xml",
                    CommandBlock.applyCommands("styles.xml", stylesFileBuilder.toCode())));
        } else {
            Mx stylesFileBuilder = new Mx();
            stylesFileBuilder.c("AppTheme", "@android:style/Theme.Material.Light.DarkActionBar");
            stylesFileBuilder.a("AppTheme", "android:colorPrimary", "@color/colorPrimary");
            stylesFileBuilder.a("AppTheme", "android:colorPrimaryDark", "@color/colorPrimaryDark");
            stylesFileBuilder.a("AppTheme", "android:colorAccent", "@color/colorAccent");
            stylesFileBuilder.a("AppTheme", "android:colorControlHighlight", "@color/colorControlHighlight");
            stylesFileBuilder.a("AppTheme", "android:colorControlNormal", "@color/colorControlNormal");
            stylesFileBuilder.c("FullScreen", "@android:style/Theme.Material.Light.NoActionBar.Fullscreen");
            stylesFileBuilder.a("FullScreen", "android:colorPrimary", "@color/colorPrimary");
            stylesFileBuilder.a("FullScreen", "android:colorPrimaryDark", "@color/colorPrimaryDark");
            stylesFileBuilder.a("FullScreen", "android:colorAccent", "@color/colorAccent");
            stylesFileBuilder.a("FullScreen", "android:colorControlHighlight", "@color/colorControlHighlight");
            stylesFileBuilder.a("FullScreen", "android:colorControlNormal", "@color/colorControlNormal");
            stylesFileBuilder.c("NoActionBar", "@android:style/Theme.Material.Light.NoActionBar");
            stylesFileBuilder.a("NoActionBar", "android:colorPrimary", "@color/colorPrimary");
            stylesFileBuilder.a("NoActionBar", "android:colorPrimaryDark", "@color/colorPrimaryDark");
            stylesFileBuilder.a("NoActionBar", "android:colorAccent", "@color/colorAccent");
            stylesFileBuilder.a("NoActionBar", "android:colorControlHighlight", "@color/colorControlHighlight");
            stylesFileBuilder.a("NoActionBar", "android:colorControlNormal", "@color/colorControlNormal");
            stylesFileBuilder.c("NoStatusBar", "AppTheme");
            stylesFileBuilder.a("NoStatusBar", "android:windowFullscreen", "true");
            srcCodeBeans.add(new SrcCodeBean("styles.xml",
                    CommandBlock.applyCommands("styles.xml", stylesFileBuilder.toCode())));

            Mx colorsFileBuilder = new Mx();
            colorsFileBuilder.a("colorPrimary", String.format("#%06X", h & 0xffffff));
            colorsFileBuilder.a("colorPrimaryDark", String.format("#%06X", i & 0xffffff));
            colorsFileBuilder.a("colorAccent", String.format("#%06X", g & 0xffffff));
            colorsFileBuilder.a("colorControlHighlight", String.format("#%06X", j & 0xffffff));
            colorsFileBuilder.a("colorControlNormal", String.format("#%06X", k & 0xffffff));
            srcCodeBeans.add(new SrcCodeBean("colors.xml",
                    CommandBlock.applyCommands("colors.xml", colorsFileBuilder.toCode())));
        }

        Mx stringsFileBuilder = new Mx();
        stringsFileBuilder.b("app_name", f);
        srcCodeBeans.add(new SrcCodeBean("strings.xml",
                CommandBlock.applyCommands("strings.xml", stringsFileBuilder.toCode())));
        CommandBlock.x();
        return srcCodeBeans;
    }

    /**
     * Get generated source code of a file.
     *
     * @return The file's code or an empty String if not found
     */
    public String getFileSrc(String filename, hC projectFileManager, eC projectDataManager, iC projectLibraryManager) {
        a(projectLibraryManager, projectFileManager, projectDataManager, false);
        boolean isJavaFile = filename.endsWith(".java");
        boolean isXmlFile = filename.endsWith(".xml");
        boolean isManifestFile = filename.equals("AndroidManifest.xml");
        ArrayList<ProjectFileBean> files = new ArrayList<>(projectFileManager.b());
        files.addAll(new ArrayList<>(projectFileManager.c()));

        if (isXmlFile) {
            /*
              Generating every java file is necessary to make command blocks for xml work
             */
            for (ProjectFileBean file : files) {
                CommandBlock.CBForXml(new Jx(N, file, projectDataManager).generateCode());
            }
        }

        if (isManifestFile) {
            Ix ix = new Ix(N, projectFileManager.b());
            ix.setYq(this);
            return CommandBlock.applyCommands("AndroidManifest.xml", ix.a());
        }

        for (ProjectFileBean file : files) {
            if (filename.equals(isJavaFile ? file.getJavaName() : file.getXmlName())) {
                if (isJavaFile) {
                    return new Jx(N, file, projectDataManager).generateCode();
                } else if (isXmlFile) {
                    Ox xmlGenerator = new Ox(N, file);
                    xmlGenerator.a(eC.a(projectDataManager.d(filename)), projectDataManager.h(filename));
                    return CommandBlock.applyCommands(filename, xmlGenerator.b());
                }
            }
        }

        return "";
    }

    /**
     * Calls {@link yq#a(hC, eC, iC, boolean)} with the same parameters and <code>false</code>.
     */
    public ArrayList<SrcCodeBean> a(hC hCVar, eC eCVar, iC iCVar) {
        return a(hCVar, eCVar, iCVar, false);
    }
}
