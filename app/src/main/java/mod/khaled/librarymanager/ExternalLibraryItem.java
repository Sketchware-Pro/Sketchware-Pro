package mod.khaled.librarymanager;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // Fallback: use manifest
        if (FileUtil.isExistFile(getManifestPath())) {
            String content = FileUtil.readFile(getManifestPath());

            Pattern p = Pattern.compile("<manifest.*package=\"(.*?)\"", Pattern.DOTALL);
            Matcher m = p.matcher(content);

            if (m.find()) return m.group(1);
        }
        //Fallback: use dependency name
        return getLibraryPkg().split(":")[0];
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
        this.libraryName = libraryName.trim();
        this.libraryPkg = libraryPkg.trim();

        this.libraryPath = FilePathUtil.getExternalLibraryDir(libraryPkg);
    }

    //TODO: Actual hashing when
    public String getLibraryHash() {
        return String.valueOf((libraryPkg).hashCode());
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
