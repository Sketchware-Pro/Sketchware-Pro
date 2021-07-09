package mod.agus.jcoderz.editor.manage.library.locallibrary;

import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.util.Helper;

public class ManageLocalLibrary {
    
    public FilePathUtil fpu = new FilePathUtil();
    public ArrayList<HashMap<String, Object>> list;
    public String numProj;

    public ManageLocalLibrary(String sc_id) {
        numProj = sc_id;
        if (FileUtil.isExistFile(fpu.getPathLocalLibrary(numProj))) {
            list = new Gson().fromJson(FileUtil.readFile(fpu.getPathLocalLibrary(numProj)), Helper.TYPE_MAP_LIST);
        } else {
            list = new ArrayList<>();
        }
    }

    public ArrayList<String> getAssets() {
        ArrayList<String> assets = new ArrayList<>();

        for (HashMap<String, Object> localLibrary : list) {
            if (localLibrary.containsKey("assetsPath")) {
                assets.add(localLibrary.get("assetsPath").toString());
            }
        }

        return assets;
    }

    public ArrayList<String> getDexLocalLibrary() {
        ArrayList<String> dexes = new ArrayList<>();

        for (HashMap<String, Object> map : list) {
            dexes.add(map.get("dexPath").toString());
        }

        return dexes;
    }

    public ArrayList<String> getExtraDexes() {
        ArrayList<String> extraDexes = new ArrayList<>();
        Iterator<HashMap<String, Object>> iterator = list.iterator();

        while (true) {
            HashMap<String, Object> next;
            do {
                if (!iterator.hasNext()) {
                    return extraDexes;
                }

                next = iterator.next();
            } while (!next.containsKey("dexPath"));

            File[] dexPathFiles = (new File(next.get("dexPath").toString())).getParentFile().listFiles();

            if (dexPathFiles != null) {
                for (File dexPathFile : dexPathFiles) {
                    if (!dexPathFile.getName().equals("classes.dex")
                            && dexPathFile.getName().startsWith("classes")
                            && dexPathFile.getName().endsWith(".dex")) {
                        extraDexes.add(dexPathFile.getAbsolutePath());
                    }
                }
            }
        }
    }

    public ArrayList<String> getGenLocalLibrary() {
        ArrayList<String> genPaths = new ArrayList<>();

        for (HashMap<String, Object> localLibrary : list) {
            if (localLibrary.containsKey("packageName") && !localLibrary.get("packageName").toString().isEmpty()) {
                genPaths.add(new File(Environment.getExternalStorageDirectory(), ".sketchware/mysc/" + numProj + "/gen").getAbsolutePath()
                        + File.separator + localLibrary.get("packageName").toString().replace(".", File.separator)
                        + File.separator + "R.java");
            }
        }

        return genPaths;
    }

    public ArrayList<String> getImportLocalLibrary() {
        ArrayList<String> imports = new ArrayList<>();

        for (HashMap<String, Object> localLibrary : list) {
            if (localLibrary.containsKey("packageName") && !localLibrary.get("packageName").toString().isEmpty()) {
                String importToAdd = localLibrary.get("packageName").toString() +
                        ".*";
                imports.add(importToAdd);
            }
        }

        return imports;
    }

    public String getJarLocalLibrary() {
        StringBuilder jarPath = new StringBuilder();

        for (HashMap<String, Object> localLibrary : list) {
            jarPath.append(":");
            jarPath.append(localLibrary.get("jarPath").toString());
        }

        return jarPath.toString();
    }

    public String getManifestMerge() {
        StringBuilder manifestPath = new StringBuilder();

        for (HashMap<String, Object> localLibrary : list) {
            if (localLibrary.containsKey("manifestPath")) {
                manifestPath.append(FileUtil.readFile(localLibrary.get("manifestPath").toString()));
                manifestPath.append("\n");
            }
        }

        return manifestPath.toString();
    }

    public ArrayList<String> getNativeLibs() {
        ArrayList<String> nativeLibs = new ArrayList<>();

        for (HashMap<String, Object> localLibrary : list) {
            if (localLibrary.containsKey("dexPath")) {
                File jniFile = new File((new File(localLibrary.get("dexPath").toString())).getParentFile(), "jni");
                if (jniFile.exists()) {
                    nativeLibs.add(jniFile.getAbsolutePath());
                }
            }
        }

        return nativeLibs;
    }

    public String getPackageNameLocalLibrary() {
        StringBuilder packageName = new StringBuilder();

        for (HashMap<String, Object> localLibrary : list) {
            if (localLibrary.containsKey("packageName") && !localLibrary.get("packageName").toString().isEmpty()) {
                packageName.append(localLibrary.get("packageName").toString());
                packageName.append(":");
            }
        }

        return packageName.toString();
    }

    public ArrayList<String> getPgRules() {
        ArrayList<String> proguardRules = new ArrayList<>();

        for (HashMap<String, Object> localLibrary : list) {
            if (localLibrary.containsKey("pgRulesPath")) {
                proguardRules.add(localLibrary.get("pgRulesPath").toString());
            }
        }

        return proguardRules;
    }

    public ArrayList<String> getResLocalLibrary() {
        ArrayList<String> localLibraryRes = new ArrayList<>();

        for (HashMap<String, Object> localLibrary : list) {
            if (localLibrary.containsKey("resPath")) {
                localLibraryRes.add(localLibrary.get("resPath").toString());
            }
        }

        return localLibraryRes;
    }
}
