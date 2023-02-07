package mod.khaled.librarymanager;

import androidx.annotation.Nullable;

import java.io.File;

import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;

public class ExternalLibraryItem {
    public static final String LIBRARY_PKG_SEPERATOR = "@";

    private final String libraryPath;
    private String libraryName;
    private final String libraryPkg;

    public String getPackageName() {
        File packageNameFile = new File(getPackageNamePath());
        if (FileUtil.isExistFile(packageNameFile.getAbsolutePath()))
            return FileUtil.readFile(packageNameFile.getAbsolutePath());
        return libraryPkg.replace(LIBRARY_PKG_SEPERATOR, ":").split(LIBRARY_PKG_SEPERATOR)[0];
    }

    public String getPackageNamePath() {
        return new File(libraryPath, "packageName").getAbsolutePath();
    }

    public String getResPath() {
        return new File(libraryPath, "res").getAbsolutePath();
    }

    public String getJarPath() {
        return new File(libraryPath, "classes.jar").getAbsolutePath();
    }

    public String getDexPath() {
        return new File(libraryPath, "classes.dex").getAbsolutePath();
    }

    public String getManifestPath() {
        return new File(libraryPath, "AndroidManifest.xml").getAbsolutePath();
    }

    public String getPgRulesPath() {
        return new File(libraryPath, "proguard.txt").getAbsolutePath();
    }

    public String getAssetsPath() {
        return new File(libraryPath, "assets").getAbsolutePath();
    }


    public ExternalLibraryItem(String libraryName, String libraryPkg) {
        this.libraryName = libraryName;
        this.libraryPkg = libraryPkg;

        this.libraryPath = FilePathUtil.getExternalLibraryDir(libraryPkg);
    }

    //TODO: Actual hashing when
    public String getLibraryHash() {
        return String.valueOf((libraryName + libraryPkg).hashCode());
    }

    public String getLibraryName() {
        return libraryName;
    }

    public String getLibraryPkg() {
        return libraryPkg.replace(LIBRARY_PKG_SEPERATOR, ":");
    }

    public String getLibraryFolderName() {
        return libraryPkg.replace(":", LIBRARY_PKG_SEPERATOR);
    }

    ExternalLibraryItem(String libraryFolderPath) {
        this.libraryPath = libraryFolderPath;
        File libraryPathFolder = new File(libraryFolderPath);
        this.libraryPkg = libraryPathFolder.getName();

        File libraryNameFile = new File(libraryPathFolder.getAbsolutePath(), "libraryName");
        if (!FileUtil.isExistFile(libraryNameFile.getAbsolutePath()))
            FileUtil.writeFile(libraryNameFile.getAbsolutePath(), generateLibName(libraryPathFolder.getName()));

        this.libraryName = FileUtil.readFile(libraryNameFile.getAbsolutePath());
    }

    public void deleteLibraryFromStorage() {
        if (!FileUtil.isExistFile(libraryPath)) return;
        FileUtil.deleteFile(libraryPath);
    }

    public void renameLibrary(@Nullable String newName) {
        if (!FileUtil.isExistFile(libraryPath)) return;
        File libraryNameFile = new File(libraryPath, "libraryName");
        if (!FileUtil.isExistFile(libraryNameFile.getAbsolutePath()))
            FileUtil.writeFile(libraryNameFile.getAbsolutePath(), newName == null ? generateLibName(getLibraryPkg()) : newName);

        this.libraryName = FileUtil.readFile(libraryNameFile.getAbsolutePath());
    }

    public static String generateLibName(String libraryPkg) {
        try {
            String[] split = libraryPkg.split(":");
            return split[split.length - 2];
        } catch (Exception ignored) {
            return "YourMOMLibrary";
        }
    }
}
