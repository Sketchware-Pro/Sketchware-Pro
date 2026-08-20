package dev.aldi.sayuti.editor.manage;

import static pro.sketchware.utility.FileUtil.deleteFile;
import static pro.sketchware.utility.FileUtil.getExternalStorageDir;
import static pro.sketchware.utility.FileUtil.isExistFile;
import static pro.sketchware.utility.FileUtil.listDirAsFile;
import static pro.sketchware.utility.FileUtil.readFile;
import static pro.sketchware.utility.FileUtil.writeFile;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import mod.hey.studios.util.Helper;

public class LocalLibrariesUtil {
    private static final String localLibsPath = getExternalStorageDir().concat("/.sketchware/libs/local_libs/");

    // Persistent cache of computed local library sizes, keyed by library name.
    // Recursively walking every library folder to sum up file sizes is expensive
    // (it was previously being redone on every single screen open, for every
    // library, which is what made this screen slow to load). We only recompute
    // a library's size when its folder's lastModified timestamp changes, and we
    // persist the cache to disk so it survives app restarts too.
    private static final String sizeCacheFile = getExternalStorageDir().concat("/.sketchware/libs/local_libs_size_cache.json");
    private static final Map<String, CachedSize> sizeCache = loadSizeCache();

    private static final class CachedSize {
        long dirLastModified;
        String formattedSize;

        CachedSize(long dirLastModified, String formattedSize) {
            this.dirLastModified = dirLastModified;
            this.formattedSize = formattedSize;
        }
    }

    private static Map<String, CachedSize> loadSizeCache() {
        try {
            if (isExistFile(sizeCacheFile)) {
                String content = readFile(sizeCacheFile);
                if (!content.isEmpty()) {
                    Map<String, CachedSize> loaded = new Gson().fromJson(content,
                            new TypeToken<ConcurrentHashMap<String, CachedSize>>() {}.getType());
                    if (loaded != null) return loaded;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ConcurrentHashMap<>();
    }

    private static void persistSizeCache() {
        writeFile(sizeCacheFile, new Gson().toJson(sizeCache));
    }

    public static List<LocalLibrary> getAllLocalLibraries() {
        ArrayList<File> localLibraryFiles = new ArrayList<>();
        listDirAsFile(localLibsPath, localLibraryFiles);
        localLibraryFiles.sort(new LocalLibrariesComparator());

        List<LocalLibrary> localLibraries = new LinkedList<>();
        for (File libraryFile : localLibraryFiles) {
            if (libraryFile.isDirectory()) {
                localLibraries.add(LocalLibrary.fromFile(libraryFile, getCachedSize(libraryFile)));
            }
        }

        return localLibraries;
    }

    private static String getCachedSize(File libraryFile) {
        String name = libraryFile.getName();
        long lastModified = libraryFile.lastModified();

        CachedSize cached = sizeCache.get(name);
        if (cached != null && cached.dirLastModified == lastModified) {
            return cached.formattedSize;
        }

        String formattedSize = pro.sketchware.utility.FileUtil.formatFileSize(
                pro.sketchware.utility.FileUtil.getFileSize(libraryFile));
        sizeCache.put(name, new CachedSize(lastModified, formattedSize));
        persistSizeCache();
        return formattedSize;
    }

    /**
     * Drops any cached size info for the given local library. Call this after a
     * library is deleted so a stale size can't be shown for a future re-download.
     */
    public static void invalidateSizeCache(String libraryName) {
        if (sizeCache.remove(libraryName) != null) {
            persistSizeCache();
        }
    }

    public static ArrayList<HashMap<String, Object>> getLocalLibraries(String scId) {
        File localLibFile = getLocalLibFile(scId);
        String fileContent;
        if (!localLibFile.exists() || (fileContent = readFile(localLibFile.getAbsolutePath())).isEmpty()) {
            writeFile(localLibFile.getAbsolutePath(), "[]");
            return new ArrayList<>();
        }
        return new Gson().fromJson(fileContent, Helper.TYPE_MAP_LIST);
    }

    public static void deleteSelectedLocalLibraries(String scId, List<LocalLibrary> localLibraries, ArrayList<HashMap<String, Object>> projectUsedLibs) {
        localLibraries.removeIf(library -> {
            if (library.isSelected()) {
                deleteFile(localLibsPath.concat(library.getName()));
                invalidateSizeCache(library.getName());
                if (projectUsedLibs != null) {
                    int indexToRemove = -1;
                    for (int i = 0; i < projectUsedLibs.size(); i++) {
                        Map<String, Object> libraryMap = projectUsedLibs.get(i);
                        if (library.getName().equals(libraryMap.get("name").toString())) {
                            indexToRemove = i;
                            break;
                        }
                    }
                    if (indexToRemove != -1) {
                        projectUsedLibs.remove(indexToRemove);
                    }
                }
                return true;
            }
            return false;
        });
        if (projectUsedLibs != null)
            rewriteLocalLibFile(scId, new Gson().toJson(projectUsedLibs));
    }

    public static File getLocalLibFile(String scId) {
        return new File(getExternalStorageDir().concat("/.sketchware/data/").concat(scId.concat("/local_library")));
    }

    public static void rewriteLocalLibFile(String scId, String newContent) {
        writeFile(getLocalLibFile(scId).getAbsolutePath(), newContent);
    }

    public static HashMap<String, Object> createLibraryMap(String name, String dependency) {
        String configPath = localLibsPath + name + "/config";
        String resPath = localLibsPath + name + "/res";
        String jarPath = localLibsPath + name + "/classes.jar";
        String dexPath = localLibsPath + name + "/classes.dex";
        String manifestPath = localLibsPath + name + "/AndroidManifest.xml";
        String pgRulesPath = localLibsPath + name + "/proguard.txt";
        String assetsPath = localLibsPath + name + "/assets";

        HashMap<String, Object> localLibrary = new HashMap<>();
        localLibrary.put("name", name);
        if (dependency != null) {
            localLibrary.put("dependency", dependency);
        }
        if (isExistFile(configPath)) {
            localLibrary.put("packageName", readFile(configPath));
        }
        if (isExistFile(resPath)) {
            localLibrary.put("resPath", resPath);
        }
        if (isExistFile(jarPath)) {
            localLibrary.put("jarPath", jarPath);
        }
        if (isExistFile(dexPath)) {
            localLibrary.put("dexPath", dexPath);
        }
        if (isExistFile(manifestPath)) {
            localLibrary.put("manifestPath", manifestPath);
        }
        if (isExistFile(pgRulesPath)) {
            localLibrary.put("pgRulesPath", pgRulesPath);
        }
        if (isExistFile(assetsPath)) {
            localLibrary.put("assetsPath", assetsPath);
        }
        return localLibrary;
    }
}
