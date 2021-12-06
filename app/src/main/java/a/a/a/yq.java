package a.a.a;

import android.content.Context;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.besome.sketch.beans.BlockBean;
import com.besome.sketch.beans.ComponentBean;
import com.besome.sketch.beans.ProjectFileBean;
import com.besome.sketch.beans.ProjectLibraryBean;
import com.besome.sketch.beans.SrcCodeBean;
import com.google.gson.Gson;
import com.sketchware.remod.Resources;

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
     */
    public String A;

    /**
     * Imported fonts directory of current project,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/assets/fonts
     */
    public String B;

    /**
     * Path of compiled resources in a ZIP file of current project,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/InternalDemo.apk.res
     */
    public String C;

    /**
     * DEX file called project.dex (??),
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/project.dex
     */
    public String D;

    /**
     * DEX file called classes.dex (??),
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/classes.dex
     */
    public String E;

    /**
     * DEX file called classes2.dex (??),
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/classes2.dex
     */
    public String F;

    /**
     * Unsigned APK file's path,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/InternalDemo.apk.unsigned
     */
    public String G;

    /**
     * Signed APK file's path,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/InternalDemo.apk
     */
    public String H;

    /**
     * Release APK file's path,
     * e.g. /storage/emulated/0/sketchware/signed_apk/InternalDemo_release.apk
     */
    public String I;

    /**
     * Path of ZIP file containing project's sources (only used/set at {@link com.besome.sketch.export.ExportProjectActivity}).
     */
    public String J;

    /**
     * Unknown,
     * e.g. [SketchApplication.java, DebugActivity.java]
     */
    public ArrayList<String> K;
    public oB L;
    public Gson M;
    public jq N;
    public Zo O;

    /**
     * HashMap containing information about current project stored in
     * /Internal storage/.sketchware/data/sc_id/project, like {custom_icon=false, sc_ver_code=1,
     * my_ws_name=InternalDemo, color_accent=-1.6740915E7,
     * my_app_name=Sketchware Pro Remod Remod Demo, sc_ver_name=1.0, sc_id=605,
     * color_primary=-1.6740915E7, color_control_highlight=5.36907213E8,
     * color_control_normal=-1.1026706E7, my_sc_reg_dt=20210130213630, sketchware_ver=150.0,
     * my_sc_pkg_name=com.jbk.internal.demo, color_primary_dark=-1.674323E7}
     */
    public HashMap<String, Object> a;

    /**
     * ProGuard rules file's path,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/aapt_rules.pro
     */
    public String aapt_rules;

    /**
     * Project's sc_id,
     * e.g. 605
     */
    public String b;

    /**
     * Project's mysc folder path,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/
     */
    public String c;

    /**
     * ProGuarded classes.jar file's path of current project,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/classes_proguard.jar
     */
    public String classes_proguard;

    /**
     * Project name of current project,
     * e.g. InternalDemo
     */
    public String d;

    /**
     * Package name of current project,
     * e.g. com.jbk.internal.demo
     */
    public String e;

    /**
     * Application's name of current project,
     * e.g. Sketchware Pro Remod Remod Demo
     */
    public String f;

    /**
     * Project/Application's accent color (as integer),
     * e.g. -16740915
     */
    public int g;

    /**
     * Project/Application's primary color (as integer),
     * e.g. -16740915
     */
    public int h;

    /**
     * Project/Application's dark primary color (as integer),
     * e.g. -16743230
     */
    public int i;

    /**
     * Project/Application's control highlight color (as integer),
     * e.g. 36907213
     */
    public int j;

    /**
     * Project/Application's normal control color (as integer),
     * e.g. -11026706
     */
    public int k;

    /**
     * Version code of current project,
     * e.g. 1
     */
    public String l;

    /**
     * Version name of current project,
     * e.g. 1.0
     */
    public String m;

    /**
     * Package name of current project,
     * but "folders" separated with slashes (/) instead of periods (.),
     * e.g. com/jbk/internal/demo
     */
    public String n;

    /**
     * Project's compiled MainActivity.java file's path,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/java/com/jbk/internal/demo/MainActivity.java
     */
    public String o;

    /**
     * Project's MainActivity's full Java name,
     * e.g. com.jbk.internal.demo.MainActivity
     */
    public String p;

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
     * Project's compiled SketchApplication.java's path,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/java/com/jbk/internal/demo/SketchApplication.java
     */
    public String q;

    /**
     * Compiled AndroidManifest.xml's path of current project,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/AndroidManifest.xml
     */
    public String r;

    /**
     * Path of ProGuard rules generated by a resource processor,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/rules_generated.pro
     */
    public String rules_generated;

    /**
     * Project's generated Java files directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main
     */
    public String s;

    /**
     * Project's compiled binary directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin
     */
    public String t;

    /**
     * Project's compiled Java classes directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/bin/classes
     */
    public String u;

    /**
     * Project's generated R.java files directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/gen
     */
    public String v;

    /**
     * Generated project resources directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/res
     */
    public String w;

    /**
     * Project's generated layout files directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/res/layout
     */
    public String x;

    /**
     * Project's generated Java files directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/java
     */
    public String y;

    /**
     * Project's imported sounds directory,
     * e.g. /storage/emulated/0/.sketchware/mysc/605/app/src/main/res/raw
     */
    public String z;

    public yq(Context context, String sc_id) {
        this(context, wq.d(sc_id), lC.b(sc_id));
    }

    public yq(Context context, String myscFolderPath, HashMap<String, Object> metadata) {
        N = new jq();
        a = metadata;
        b = yB.c(metadata, "sc_id");
        c = myscFolderPath;
        e = yB.c(metadata, "my_sc_pkg_name");
        d = yB.c(metadata, "my_ws_name");
        f = yB.c(metadata, "my_app_name");
        l = yB.c(metadata, "sc_ver_code");
        m = yB.c(metadata, "sc_ver_name");

        g = yB.a(metadata, "color_accent",
                ContextCompat.getColor(context, Resources.color.color_accent));
        h = yB.a(metadata, "color_primary",
                ContextCompat.getColor(context, Resources.color.color_primary));
        i = yB.a(metadata, "color_primary_dark",
                ContextCompat.getColor(context, Resources.color.color_primary_dark));
        j = yB.a(metadata, "color_control_highlight",
                ContextCompat.getColor(context, Resources.color.color_control_highlight));
        k = yB.a(metadata, "color_control_normal",
                ContextCompat.getColor(context, Resources.color.color_control_normal));
        projectSettings = new ProjectSettings(b);
        b(context);
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
     * Initializes fields with provided {@link Context}.
     */
    public final void b(Context context) {
        L = new oB(true);
        M = new Gson();
        O = new Zo(context);
        K = new ArrayList<>();
        K.add("SketchApplication.java");
        K.add("DebugActivity.java");
        if (!c.endsWith(File.separator)) {
            c += File.separator;
        }
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
        D = t + File.separator + "project.dex";
        E = t + File.separator + "classes.dex";
        F = t + File.separator + "classes2.dex";
        o = y + File.separator + n;
        o += File.separator + "MainActivity.java";
        p = e + "." + "MainActivity.java".replaceAll(".java", "");
        q = y + File.separator + n;
        q += File.separator + "SketchApplication.java";
        G = t + File.separator + d + ".apk.unsigned";
        H = t + File.separator + d + ".apk";
        I = wq.o() + File.separator + d + "_release.apk";
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
     * Generates DebugActivity.java and SketchApplication.java.
     */
    public void a(Context context) {
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

            L.b(y + File.separator
                            + n + File.separator
                            + "SketchApplication.java",
                    sketchApplicationFileContent);
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
        N.a = e;
        N.b = f;
        N.c = l;
        N.d = m;
        N.sc_id = b;
        N.e = O.h();
        N.f = !exportingProject;
        if (firebase.useYn.equals(ProjectLibraryBean.LIB_USE_Y)) {
            N.h = true;
            N.a(jq.PERMISSION_INTERNET);
            N.a(jq.PERMISSION_ACCESS_NETWORK_STATE);
        }
        if (appCompat.useYn.equals(ProjectLibraryBean.LIB_USE_Y)) {
            N.g = true;
        }
        if (adMob.useYn.equals(ProjectLibraryBean.LIB_USE_Y)) {
            N.l = true;
            N.a(jq.PERMISSION_INTERNET);
            N.a(jq.PERMISSION_ACCESS_NETWORK_STATE);
            N.a(adMob);
        }
        if (googleMaps.useYn.equals(ProjectLibraryBean.LIB_USE_Y)) {
            N.m = true;
            N.a(jq.PERMISSION_INTERNET);
            N.a(jq.PERMISSION_ACCESS_NETWORK_STATE);
            N.b(googleMaps);
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
                        N.g = true;
                        N.u = true;
                        N.a(next.getActivityName(), jq.PERMISSION_CAMERA);
                        N.a(next.getActivityName(), jq.PERMISSION_READ_EXTERNAL_STORAGE);
                        N.a(next.getActivityName(), jq.PERMISSION_WRITE_EXTERNAL_STORAGE);
                        break;

                    case ComponentBean.COMPONENT_TYPE_FILE_PICKER:
                        N.a(next.getActivityName(), jq.PERMISSION_READ_EXTERNAL_STORAGE);
                        break;

                    case ComponentBean.COMPONENT_TYPE_FIREBASE:
                        N.o = true;
                        N.j = true;
                        N.a(next.getActivityName(), jq.PERMISSION_INTERNET);
                        N.a(next.getActivityName(), jq.PERMISSION_ACCESS_NETWORK_STATE);
                        break;

                    case ComponentBean.COMPONENT_TYPE_FIREBASE_STORAGE:
                        N.k = true;
                        N.a(next.getActivityName(), jq.PERMISSION_READ_EXTERNAL_STORAGE);
                        N.a(next.getActivityName(), jq.PERMISSION_WRITE_EXTERNAL_STORAGE);
                        break;

                    case ComponentBean.COMPONENT_TYPE_VIBRATOR:
                        N.a(next.getActivityName(), jq.PERMISSION_VIBRATE);
                        break;

                    case ComponentBean.COMPONENT_TYPE_FIREBASE_AUTH:
                        N.i = true;
                        N.a(next.getActivityName()).b = true;
                        break;

                    case ComponentBean.COMPONENT_TYPE_REQUEST_NETWORK:
                        N.o = true;
                        N.p = true;
                        N.a(next.getActivityName(), jq.PERMISSION_INTERNET);
                        N.a(next.getActivityName(), jq.PERMISSION_ACCESS_NETWORK_STATE);
                        break;

                    case ComponentBean.COMPONENT_TYPE_SPEECH_TO_TEXT:
                        N.a(next.getActivityName(), jq.PERMISSION_RECORD_AUDIO);
                        break;

                    case ComponentBean.COMPONENT_TYPE_BLUETOOTH_CONNECT:
                        N.a(next.getActivityName(), jq.PERMISSION_BLUETOOTH);
                        N.a(next.getActivityName(), jq.PERMISSION_BLUETOOTH_ADMIN);
                        break;

                    case ComponentBean.COMPONENT_TYPE_LOCATION_MANAGER:
                        N.a(next.getActivityName(), jq.PERMISSION_ACCESS_FINE_LOCATION);
                        break;

                    default:
                }
            }

            for (Map.Entry<String, ArrayList<BlockBean>> entry : projectDataManager.b(next.getJavaName()).entrySet()) {
                for (BlockBean bean : entry.getValue()) {
                    String opCode = bean.opCode;
                    N.x.setParams(bean.parameters, e, opCode);

                    switch (opCode) {
                        case "setAdmobAppId":
                            N.appId = bean.parameters.get(0);
                            break;

                        case "intentSetAction":
                            // If an Intent setAction (ACTION_CALL) block is used
                            if (bean.parameters.get(1).equals(uq.c[1])) {
                                N.a(next.getActivityName(), jq.PERMISSION_CALL_PHONE);
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
                            N.a(next.getActivityName(), jq.PERMISSION_READ_EXTERNAL_STORAGE);
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
                            N.a(next.getActivityName(), jq.PERMISSION_READ_EXTERNAL_STORAGE);
                            N.a(next.getActivityName(), jq.PERMISSION_WRITE_EXTERNAL_STORAGE);
                            break;

                        case "strToMap":
                        case "mapToStr":
                        case "strToListMap":
                        case "listMapToStr":
                        case "GsonListTojsonString":
                        case "GsonStringToListString":
                        case "GsonStringToListNumber":
                            N.o = true;
                            break;

                        case "setImageUrl":
                            N.n = true;
                            N.a(jq.PERMISSION_INTERNET);
                            break;

                        case "webViewLoadUrl":
                            N.a(jq.PERMISSION_INTERNET);
                            N.a(jq.PERMISSION_ACCESS_NETWORK_STATE);
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
                    CommandBlock.applyCommands("xml/provider_paths.xml", pathsTag.b())));
        }

        for (SrcCodeBean bean : srcCodeBeans) {
            a(bean.srcFileName, bean.source);
        }
        if (N.h || N.l || N.m) {
            ProjectLibraryBean firebaseLibrary = projectLibraryManager.d();
            Mx mx = new Mx();
            mx.a("google_play_services_version", 12451000);
            if (N.h) {
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
            if (N.m) {
                // if p3 is false, then "translatable="false" will be added
                mx.a("google_maps_key", projectLibraryManager.e().data, false);
            }
            String filePath = "values/secrets.xml";
            L.b(w + File.separator + filePath,
                    CommandBlock.applyCommands(filePath, mx.a()));
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
                        new Jx(N, activity, projectDataManager).a()));
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

        if (!javaFiles.contains(new File(javaDir + "RequestNetwork.java")) && N.p) {
            srcCodeBeans.add(new SrcCodeBean("RequestNetwork.java",
                    Lx.j(Lx.h(e))));
        }

        if (!FileUtil.isExistFile(javaDir + "RequestNetworkController.java") && N.p) {
            srcCodeBeans.add(new SrcCodeBean("RequestNetworkController.java",
                    Lx.j(Lx.g(e))));
        }

        if (!javaFiles.contains(new File(javaDir + "BluetoothConnect.java")) && N.b(jq.PERMISSION_BLUETOOTH)) {
            srcCodeBeans.add(new SrcCodeBean("BluetoothConnect.java",
                    Lx.j(Lx.b(e))));
        }

        if (!javaFiles.contains(new File(javaDir + "BluetoothController.java")) && N.b(jq.PERMISSION_BLUETOOTH)) {
            srcCodeBeans.add(new SrcCodeBean("BluetoothController.java",
                    Lx.j(Lx.c(e))));
        }

        if (N.m) {
            if (!javaFiles.contains(new File(javaDir + "GoogleMapController.java")) && N.m) {
                srcCodeBeans.add(new SrcCodeBean("GoogleMapController.java",
                        Lx.j(Lx.f(e))));
            }
        }

        srcCodeBeans.add(new SrcCodeBean("AndroidManifest.xml",
                CommandBlock.applyCommands("AndroidManifest.xml", ix.a())));
        if (N.g) {
            boolean useNewMaterialComponentsTheme = projectSettings.getValue(ProjectSettings.SETTING_ENABLE_BRIDGELESS_THEMES,
                    BuildSettings.SETTING_GENERIC_VALUE_FALSE).equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE);

            Mx colorsFileBuilder = new Mx();
            colorsFileBuilder.a("colorPrimary", String.format("#%06X", h & 0xffffff));
            colorsFileBuilder.a("colorPrimaryDark", String.format("#%06X", i & 0xffffff));
            colorsFileBuilder.a("colorAccent", String.format("#%06X", g & 0xffffff));
            colorsFileBuilder.a("colorControlHighlight", String.format("#%06X", j & 0xffffff));
            colorsFileBuilder.a("colorControlNormal", String.format("#%06X", k & 0xffffff));
            srcCodeBeans.add(new SrcCodeBean("colors.xml",
                    CommandBlock.applyCommands("colors.xml", colorsFileBuilder.a())));

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
                    CommandBlock.applyCommands("styles.xml", stylesFileBuilder.a())));
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
                    CommandBlock.applyCommands("styles.xml", stylesFileBuilder.a())));

            Mx colorsFileBuilder = new Mx();
            colorsFileBuilder.a("colorPrimary", String.format("#%06X", h & 0xffffff));
            colorsFileBuilder.a("colorPrimaryDark", String.format("#%06X", i & 0xffffff));
            colorsFileBuilder.a("colorAccent", String.format("#%06X", g & 0xffffff));
            colorsFileBuilder.a("colorControlHighlight", String.format("#%06X", j & 0xffffff));
            colorsFileBuilder.a("colorControlNormal", String.format("#%06X", k & 0xffffff));
            srcCodeBeans.add(new SrcCodeBean("colors.xml",
                    CommandBlock.applyCommands("colors.xml", colorsFileBuilder.a())));
        }

        Mx stringsFileBuilder = new Mx();
        stringsFileBuilder.b("app_name", f);
        srcCodeBeans.add(new SrcCodeBean("strings.xml",
                CommandBlock.applyCommands("strings.xml", stringsFileBuilder.a())));
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
        boolean isDrawerFile = !isJavaFile && filename.startsWith("_drawer_");

        ArrayList<ProjectFileBean> files = !isDrawerFile ? projectFileManager.b() : projectFileManager.c();

        for (ProjectFileBean file : files) {
            if (filename.equals(isJavaFile ? file.getJavaName() : file.getXmlName())) {
                if (isJavaFile) {
                    return new Jx(N, file, projectDataManager).a();
                } else {
                    Ox xmlGenerator = new Ox(N, file);
                    xmlGenerator.a(eC.a(projectDataManager.d(filename)), projectDataManager.h(filename));
                    return xmlGenerator.b();
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
