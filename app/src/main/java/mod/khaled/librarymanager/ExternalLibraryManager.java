package mod.khaled.librarymanager;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;

public class ExternalLibraryManager {
    final String sc_id;
    private final List<ExternalLibraryItem> externalLibraryItemArrayList = new ArrayList<>();
    private final List<String> librariesInProjectHashes = new ArrayList<>();

    public ExternalLibraryManager(@Nullable String sc_id) {
        this.sc_id = sc_id;
        loadExternalLibraries();

        String externalLibraryDataPath = new FilePathUtil().getPathExternalLibrary(sc_id);
        if (sc_id != null && !FileUtil.readFile(externalLibraryDataPath).isBlank()) {
            String[] libraryHashes = FileUtil.readFile(externalLibraryDataPath).split(",");
            librariesInProjectHashes.addAll(Arrays.asList(libraryHashes));
        }
    }


    public void addLibraryToProject(String libraryHash) {
        if (librariesInProjectHashes.contains(libraryHash)) return;
        if (sc_id == null) return;

        librariesInProjectHashes.add(libraryHash);
        FileUtil.writeFile(new FilePathUtil().getPathExternalLibrary(sc_id),
                String.join(",", librariesInProjectHashes));
    }

    public void removeLibraryFromProject(String libraryHash) {
        if (!librariesInProjectHashes.contains(libraryHash)) return;
        if (sc_id == null) return;

        librariesInProjectHashes.remove(libraryHash);
        FileUtil.writeFile(new FilePathUtil().getPathExternalLibrary(sc_id),
                String.join(",", librariesInProjectHashes));
    }

    public List<ExternalLibraryItem> getExternalLibraryItemArrayList() {
        return externalLibraryItemArrayList;
    }

    public List<String> getLibrariesInProjectHashes() {
        return librariesInProjectHashes;
    }

    public static List<String> getLibrariesInProjectHashes(String sc_id) {
        List<String> librariesInProjectHashes = new ArrayList<>();
        String externalLibraryDataPath = new FilePathUtil().getPathExternalLibrary(sc_id);
        if (!FileUtil.readFile(externalLibraryDataPath).isBlank()) {
            String[] libraryHashes = FileUtil.readFile(externalLibraryDataPath).split(",");
            librariesInProjectHashes.addAll(Arrays.asList(libraryHashes));
        }
        return librariesInProjectHashes;
    }

    List<ExternalLibraryItem> loadExternalLibraries() {
        String externalLibrariesDir = FilePathUtil.getExternalLibrariesDir();

        if (!FileUtil.isExistFile(externalLibrariesDir)) return new ArrayList<>();
        ArrayList<String> librariesList = new ArrayList<>();
        FileUtil.listDir(externalLibrariesDir, librariesList);

        externalLibraryItemArrayList.clear();
        for (String libraryPath : librariesList)
            externalLibraryItemArrayList.add(new ExternalLibraryItem(libraryPath));

        return externalLibraryItemArrayList;
    }

    public List<String> getAssets() {
        List<String> assetsPath = new ArrayList<>();

        for (ExternalLibraryItem externalLibraryItem : externalLibraryItemArrayList) {
            if (!librariesInProjectHashes.contains(externalLibraryItem.getLibraryHash())) continue;

            if (FileUtil.isExistFile(externalLibraryItem.getAssetsPath()))
                assetsPath.add(externalLibraryItem.getAssetsPath());
        }

        return assetsPath;
    }

    public List<String> getDexLocalLibrary() {
        List<String> dexPaths = new ArrayList<>();

        for (ExternalLibraryItem externalLibraryItem : externalLibraryItemArrayList) {
            if (!librariesInProjectHashes.contains(externalLibraryItem.getLibraryHash())) continue;

            if (FileUtil.isExistFile(externalLibraryItem.getDexPath()))
                dexPaths.add(externalLibraryItem.getDexPath());
        }

        return dexPaths;
    }


    public List<String> getExtraDexes() {
        List<String> extraDexes = new ArrayList<>();

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


    public List<String> getImportLocalLibrary() {
        List<String> imports = new ArrayList<>();

        for (String packageName : getPackageNames()) {
            if (!packageName.isEmpty()) {
                imports.add(packageName.concat(".*"));
            }
        }

        return imports;
    }


    public String getJarLocalLibrary() {
        StringBuilder classpath = new StringBuilder();

        for (ExternalLibraryItem externalLibraryItem : externalLibraryItemArrayList) {
            if (!librariesInProjectHashes.contains(externalLibraryItem.getLibraryHash())) continue;

            if (FileUtil.isExistFile(externalLibraryItem.getJarPath()))
                classpath.append(":").append(externalLibraryItem.getJarPath());
        }

        return classpath.toString();
    }

    public List<String> getNativeLibs() {
        List<String> nativeLibraryDirectories = new ArrayList<>();

        for (String localLibraryDexPath : getDexLocalLibrary()) {
            File localLibraryDexFile = new File(localLibraryDexPath);
            File jniFolder = new File(localLibraryDexFile.getParentFile(), "jni");
            if (jniFolder.isDirectory()) {
                nativeLibraryDirectories.add(jniFolder.getAbsolutePath());
            }
        }

        return nativeLibraryDirectories;
    }

    public List<String> getPackageNames() {
        List<String> packageNames = new ArrayList<>();

        for (ExternalLibraryItem externalLibraryItem : externalLibraryItemArrayList) {
            if (!librariesInProjectHashes.contains(externalLibraryItem.getLibraryPkg())) continue;

            packageNames.add(externalLibraryItem.getPackageName());
        }

        return packageNames;
    }

    public String getPackageNameLocalLibrary() {
        StringBuilder packageNames = new StringBuilder();

        for (String packageName : getPackageNames()) {
            if (packageName.isEmpty()) continue;
            packageNames.append(packageName);
            packageNames.append(":");
        }

        return packageNames.toString();
    }

    public List<String> getPgRules() {
        List<String> proguardRules = new ArrayList<>();

        for (ExternalLibraryItem externalLibraryItem : externalLibraryItemArrayList) {
            if (!librariesInProjectHashes.contains(externalLibraryItem.getLibraryHash())) continue;

            if (FileUtil.isExistFile(externalLibraryItem.getPgRulesPath()))
                proguardRules.add(externalLibraryItem.getResPath());
        }

        return proguardRules;
    }

    public List<String> getResLocalLibrary() {
        List<String> localLibraryRes = new ArrayList<>();

        for (ExternalLibraryItem externalLibraryItem : externalLibraryItemArrayList) {
            if (!librariesInProjectHashes.contains(externalLibraryItem.getLibraryHash())) continue;

            if (FileUtil.isExistFile(externalLibraryItem.getResPath()))
                localLibraryRes.add(externalLibraryItem.getResPath());
        }

        return localLibraryRes;
    }

    public String getLibraryNameFromHash(String libraryHash) {
        for (ExternalLibraryItem libraryItem : externalLibraryItemArrayList)
            if (libraryHash.equals(libraryItem.getLibraryHash()))
                return libraryItem.getLibraryName();
        return libraryHash + " Not found";
    }
}
