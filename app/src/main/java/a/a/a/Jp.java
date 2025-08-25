package a.a.a;

import java.util.Objects;

import mod.jbk.build.BuiltInLibraries;
import pro.sketchware.util.library.BuiltInLibraryUtils;

/**
 * An object representing a built-in library, e.g. the MDC library (nicknamed material-1.0.0)
 */
public class Jp {
    private final String name;
    private final String packageName;
    private final boolean hasResources;
    private final boolean hasAssets;

    public Jp(String libraryName) {
        name = libraryName;
        hasResources = BuiltInLibraryUtils.hasResources(libraryName);
        hasAssets = libraryName.equals(BuiltInLibraries.CODEVIEW);
        if (hasResources || hasAssets) {
            packageName = BuiltInLibraryUtils.getPackageName(libraryName);
        } else {
            packageName = "";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jp jp = (Jp) o;
        return name.equals(jp.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * @return The library's name inside libs.zip or dexs.zip, e.g. material-1.0.0
     */
    public String getName() {
        return name;
    }

    /**
     * @return The library's base package name, e.g. com.google.android.material
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @return <code>true</code> if the library has resources that need constants in an R class,
     * <code>false</code> otherwise
     */
    public boolean hasResources() {
        return hasResources;
    }

    /**
     * @return <code>true</code> if the library has assets that need to be put into the APK,
     * <code>false</code> otherwise
     */
    public boolean hasAssets() {
        return hasAssets;
    }
}
