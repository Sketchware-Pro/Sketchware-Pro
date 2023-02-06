package mod.khaled.librarymanager;

import mod.agus.jcoderz.lib.FilePathUtil;

public class ExternalLibraryItem {
    public static final String LIBRARY_PKG_SEPERATOR = "@";

    private final String libraryPath;
    private final String libraryName;
    private final String libraryPkg;

    public String getPackageName() {
        return libraryPkg.replace(LIBRARY_PKG_SEPERATOR, ":").split(LIBRARY_PKG_SEPERATOR)[0];
    }

    public String getResPath() {
        return libraryPath + "/res";
    }

    public String getJarPath() {
        return libraryPath + "/classes.jar";
    }

    public String getDexPath() {
        return libraryPath + "/classes.dex";
    }

    public String getManifestPath() {
        return libraryPath + "/AndroidManifest.xml";
    }

    public String getPgRulesPath() {
        return libraryPath + "/proguard.txt";
    }

    public String getAssetsPath() {
        return libraryPath + "/assets";
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

    ExternalLibraryItem(String filePath) {
        this.libraryPath = FilePathUtil.getExternalLibraryDir(filePath);
        this.libraryName = libraryPath;
        this.libraryPkg = libraryPath;
    }
}
