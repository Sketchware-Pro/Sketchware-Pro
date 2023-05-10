package a.a.a;

import mod.jbk.build.BuiltInLibraries;
import mod.jbk.build.BuiltInLibraries.BuiltInLibrary;

public class BuiltInLibraryUtils {

    /**
     * Returns the known dependencies for a given built-in library.
     *
     * @apiNote This method won't return the dependencies' sub-dependencies!
     */
    public static String[] getKnownDependencies(String libraryName) {
        for (BuiltInLibrary library : BuiltInLibraries.KNOWN_BUILT_IN_LIBRARIES) {
            if (library.getName().equals(libraryName)) {
                return library.getDependencyNames().toArray(new String[0]);
            }
        }

        throw new IllegalArgumentException("Unknown built-in library '" + libraryName + "'!");
    }

    /**
     * Returns the package name of a given built-in library.
     */
    public static String getPackageName(String libraryName) {
        for (BuiltInLibrary library : BuiltInLibraries.KNOWN_BUILT_IN_LIBRARIES) {
            if (library.getName().equals(libraryName)) {
                return library.getPackageName().orElseThrow(IllegalStateException::new);
            }
        }

        throw new IllegalArgumentException("Unknown built-in library '" + libraryName + "'!");
    }

    /**
     * Returns whether a given built-in library has resources that need to be mapped to a R.java file
     * by a resource processor.
     */
    public static boolean hasResources(String libraryName) {
        for (BuiltInLibrary library : BuiltInLibraries.KNOWN_BUILT_IN_LIBRARIES) {
            if (library.getName().equals(libraryName)) {
                return library.hasResources();
            }
        }

        throw new IllegalArgumentException("Unknown built-in library '" + libraryName + "'!");
    }
}
