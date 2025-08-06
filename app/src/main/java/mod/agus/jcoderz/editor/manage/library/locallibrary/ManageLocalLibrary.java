package mod.agus.jcoderz.editor.manage.library.locallibrary;

import android.os.Environment;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import mod.hey.studios.util.Helper;
import mod.jbk.util.LogUtil;
import pro.sketchware.utility.FilePathUtil;
import pro.sketchware.utility.FileUtil;
import pro.sketchware.utility.SketchwareUtil;

public class ManageLocalLibrary {

    private final String projectId;
    public ArrayList<HashMap<String, Object>> list;

    public ManageLocalLibrary(String sc_id) {
        projectId = sc_id;
        String localLibraryConfigPath = new FilePathUtil().getPathLocalLibrary(projectId);
        if (FileUtil.isExistFile(localLibraryConfigPath)) {
            try {
                list = new Gson().fromJson(FileUtil.readFile(localLibraryConfigPath), Helper.TYPE_MAP_LIST);

                if (list == null) {
                    LogUtil.w(getClass().getSimpleName(), "Read null from file " + localLibraryConfigPath + ", deleting invalid configuration.");
                    if (!new File(localLibraryConfigPath).delete()) {
                        LogUtil.e(getClass().getSimpleName(), "Couldn't delete file " + localLibraryConfigPath);
                    }

                    // fall-through to shared error handler
                } else {
                    return;
                }
            } catch (JsonParseException e) {
                // fall-through to shared error handler
            }

            SketchwareUtil.toastError("Invalid Local library configuration found! Temporarily using none");
        }
        list = new ArrayList<>();
    }

    public ArrayList<String> getAssets() {
        ArrayList<String> assets = new ArrayList<>();

        for (int i = 0, listSize = list.size(); i < listSize; i++) {
            HashMap<String, Object> localLibrary = list.get(i);

            if (localLibrary.containsKey("assetsPath")) {
                Object assetsPath = localLibrary.get("assetsPath");

                if (assetsPath instanceof String) {
                    assets.add((String) assetsPath);
                } else {
                    SketchwareUtil.toastError("Invalid assets path of enabled Local library #" + i, Toast.LENGTH_LONG);
                }
            }
        }

        return assets;
    }

    public ArrayList<String> getDexLocalLibrary() {
        ArrayList<String> dexes = new ArrayList<>();

        for (int i = 0, listSize = list.size(); i < listSize; i++) {
            HashMap<String, Object> localLibrary = list.get(i);
            Object dexPath = localLibrary.get("dexPath");

            if (dexPath instanceof String) {
                dexes.add((String) dexPath);
            } else {
                SketchwareUtil.toastError("Invalid DEX path of enabled Local library #" + i, Toast.LENGTH_LONG);
            }
        }

        return dexes;
    }

    public ArrayList<String> getExtraDexes() {
        ArrayList<String> extraDexes = new ArrayList<>();

        for (String localLibraryDexPath : getDexLocalLibrary()) {
            File dexPath = new File(localLibraryDexPath);
            if (dexPath.getParentFile() != null) {
                File[] dexPathFiles = dexPath.getParentFile().listFiles();

                if (dexPathFiles != null) {
                    for (File dexPathFile : dexPathFiles) {
                        String dexPathFilename = dexPathFile.getName();
                        if (!dexPathFilename.equals("classes.dex")
                                && dexPathFilename.startsWith("classes")
                                && dexPathFilename.endsWith(".dex")) {
                            extraDexes.add(dexPathFile.getAbsolutePath());
                        }
                    }
                }
            }
        }

        return extraDexes;
    }

    public ArrayList<String> getGenLocalLibrary() {
        ArrayList<String> genPaths = new ArrayList<>();

        for (String packageName : getPackageNames()) {
            if (!packageName.isEmpty()) {
                File projectGenFolder = new File(Environment.getExternalStorageDirectory(),
                        ".sketchware/mysc/".concat(projectId).concat("/gen"));
                String rJavaPath = packageName.replace(".", File.separator)
                        .concat(File.separator).concat("R.java");
                genPaths.add(new File(projectGenFolder, rJavaPath).getAbsolutePath());
            }
        }

        return genPaths;
    }

    public ArrayList<String> getImportLocalLibrary() {
        ArrayList<String> imports = new ArrayList<>();

        for (String packageName : getPackageNames()) {
            if (!packageName.isEmpty()) {
                imports.add(packageName.concat(".*"));
            }
        }

        return imports;
    }

    public ArrayList<File> getLocalLibraryJars() {
        ArrayList<File> jars = new ArrayList<>();

        for (int i = 0, listSize = list.size(); i < listSize; i++) {
            HashMap<String, Object> localLibrary = list.get(i);
            Object jarPath = localLibrary.get("jarPath");

            if (jarPath instanceof String) {
                jars.add(new File((String) jarPath));
            } else {
                SketchwareUtil.toastError("Invalid JAR path of enabled Local library #" + i + "->" + localLibrary.get("name"), Toast.LENGTH_LONG);
            }
        }

        return jars;
    }

    public String getJarLocalLibrary() {
        StringBuilder classpath = new StringBuilder();

        for (int i = 0, listSize = list.size(); i < listSize; i++) {
            HashMap<String, Object> localLibrary = list.get(i);
            Object jarPath = localLibrary.get("jarPath");

            if (jarPath instanceof String) {
                classpath.append(":");
                classpath.append((String) jarPath);
            } else {
                SketchwareUtil.toastError("Invalid JAR path of enabled Local library #" + i + "->" + localLibrary.get("name"), Toast.LENGTH_LONG);
            }
        }

        return classpath.toString();
    }

    public ArrayList<String> getNativeLibs() {
        ArrayList<String> nativeLibraryDirectories = new ArrayList<>();

        for (String localLibraryDexPath : getDexLocalLibrary()) {
            File localLibraryDexFile = new File(localLibraryDexPath);
            File jniFolder = new File(localLibraryDexFile.getParentFile(), "jni");
            if (jniFolder.isDirectory()) {
                nativeLibraryDirectories.add(jniFolder.getAbsolutePath());
            }
        }

        return nativeLibraryDirectories;
    }

    public ArrayList<String> getPackageNames() {
        ArrayList<String> packageNames = new ArrayList<>();

        for (int i = 0, listSize = list.size(); i < listSize; i++) {
            HashMap<String, Object> localLibrary = list.get(i);
            if (localLibrary.containsKey("packageName")) {
                Object packageName = localLibrary.get("packageName");

                if (packageName instanceof String) {
                    packageNames.add((String) packageName);
                } else {
                    SketchwareUtil.toastError("Invalid package name of enabled Local library #" + i, Toast.LENGTH_LONG);
                }
            }
        }

        return packageNames;
    }

    public String getPackageNameLocalLibrary() {
        StringBuilder packageNames = new StringBuilder();

        for (String packageName : getPackageNames()) {
            if (!packageName.isEmpty()) {
                packageNames.append(packageName);
                packageNames.append(":");
            }
        }

        return packageNames.toString();
    }

    public ArrayList<String> getPgRules() {
        ArrayList<String> proguardRules = new ArrayList<>();

        for (int i = 0, listSize = list.size(); i < listSize; i++) {
            HashMap<String, Object> localLibrary = list.get(i);
            if (localLibrary.containsKey("pgRulesPath")) {
                Object proguardRulesPath = localLibrary.get("pgRulesPath");

                if (proguardRulesPath instanceof String) {
                    proguardRules.add((String) proguardRulesPath);
                } else {
                    SketchwareUtil.toastError("Invalid ProGuard path of enabled Local library #" + i, Toast.LENGTH_LONG);
                }
            }
        }

        return proguardRules;
    }

    public ArrayList<String> getResLocalLibrary() {
        ArrayList<String> localLibraryRes = new ArrayList<>();

        for (int i = 0, listSize = list.size(); i < listSize; i++) {
            HashMap<String, Object> localLibrary = list.get(i);
            if (localLibrary.containsKey("resPath")) {
                Object resPath = localLibrary.get("resPath");

                if (resPath instanceof String) {
                    localLibraryRes.add((String) resPath);
                } else {
                    SketchwareUtil.toastError("Invalid res/ folder path of enabled Local library #" + i, Toast.LENGTH_LONG);
                }
            }
        }

        return localLibraryRes;
    }
}
