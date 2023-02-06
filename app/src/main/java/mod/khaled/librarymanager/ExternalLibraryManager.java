package mod.khaled.librarymanager;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;

public class ExternalLibraryManager {
    final String sc_id;
    private final ArrayList<ExternalLibraryItem> externalLibraryItemArrayList = new ArrayList<>();
    private final ArrayList<String> librariesInProjectHashes = new ArrayList<>();

    public ExternalLibraryManager(String sc_id) {
        this.sc_id = sc_id;
        loadExternalLibraries();

        String externalLibraryDataPath = new FilePathUtil().getPathExternalLibrary(sc_id);
        if (!FileUtil.readFile(externalLibraryDataPath).isBlank()) {
            String[] libraryHashes = FileUtil.readFile(externalLibraryDataPath).split(",");
            librariesInProjectHashes.addAll(Arrays.asList(libraryHashes));
        }
    }

    public ArrayList<ExternalLibraryItem> getExternalLibraryItemArrayList() {
        return externalLibraryItemArrayList;
    }

    public ArrayList<String> getLibrariesInProjectHashes() {
        return librariesInProjectHashes;
    }

    ArrayList<ExternalLibraryItem> loadExternalLibraries() {
        String externalLibrariesDir = FilePathUtil.getExternalLibrariesDir();

        if (!FileUtil.isExistFile(externalLibrariesDir)) return new ArrayList<>();
        ArrayList<String> librariesList = new ArrayList<>();
        FileUtil.listDir(externalLibrariesDir, librariesList);

        externalLibraryItemArrayList.clear();
        for (String libraryPath : librariesList)
            externalLibraryItemArrayList.add(new ExternalLibraryItem(libraryPath));

        return externalLibraryItemArrayList;
    }

    public ArrayList<String> getAssets() {
        ArrayList<String> assetsPath = new ArrayList<>();

        for (ExternalLibraryItem externalLibraryItem : externalLibraryItemArrayList) {
            if (!librariesInProjectHashes.contains(externalLibraryItem.getLibraryHash())) continue;

            if (FileUtil.isExistFile(externalLibraryItem.getAssetsPath()))
                assetsPath.add(externalLibraryItem.getAssetsPath());
        }

        return assetsPath;
    }

    public ArrayList<String> getDexLocalLibrary() {
        ArrayList<String> dexPaths = new ArrayList<>();

        for (ExternalLibraryItem externalLibraryItem : externalLibraryItemArrayList) {
            if (!librariesInProjectHashes.contains(externalLibraryItem.getLibraryHash())) continue;

            if (FileUtil.isExistFile(externalLibraryItem.getDexPath()))
                dexPaths.add(externalLibraryItem.getDexPath());
        }

        return dexPaths;
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


    public ArrayList<String> getImportLocalLibrary() {
        ArrayList<String> imports = new ArrayList<>();

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

    public ArrayList<String> getPgRules() {
        ArrayList<String> proguardRules = new ArrayList<>();

        for (ExternalLibraryItem externalLibraryItem : externalLibraryItemArrayList) {
            if (!librariesInProjectHashes.contains(externalLibraryItem.getLibraryHash())) continue;

            if (FileUtil.isExistFile(externalLibraryItem.getPgRulesPath()))
                proguardRules.add(externalLibraryItem.getResPath());
        }

        return proguardRules;
    }

    public ArrayList<String> getResLocalLibrary() {
        ArrayList<String> localLibraryRes = new ArrayList<>();

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
