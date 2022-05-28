package a.a.a;

import mod.jbk.build.BuiltInLibraries;
import mod.jbk.build.BuiltInLibraries.BuiltInLibrary;

public class qq {

    /**
     * @param name A built-in library's name, e.g. material-1.0.0
     * @return A set of known dependencies for a built-in library
     * @apiNote Won't return the dependencies' sub-dependencies!
     */
    public static String[] a(String name) {
        for (BuiltInLibrary library : BuiltInLibraries.KNOWN_BUILT_IN_LIBRARIES) {
            if (library.getName().equals(name)) {
                return library.getDependencyNames().toArray(new String[0]);
            }
        }

        throw new IllegalArgumentException("Unknown built-in library '" + name + "'!");
    }

    /**
     * @param name Built-in library name, e.g. material-1.0.0
     * @return Package name of built-in library, e.g. com.google.android.material
     */
    public static String b(String name) {
        for (BuiltInLibrary library : BuiltInLibraries.KNOWN_BUILT_IN_LIBRARIES) {
            if (library.getName().equals(name)) {
                return library.getPackageName().orElseThrow(IllegalStateException::new);
            }
        }

        throw new IllegalArgumentException("Unknown built-in library '" + name + "'!");
    }

    /**
     * @param name The built-in library's name, e.g. material-1.0.0
     * @return Whether the built-in library has resources that need to be mapped to a R.java file by a resource processor
     */
    public static boolean c(String name) {
        for (BuiltInLibrary library : BuiltInLibraries.KNOWN_BUILT_IN_LIBRARIES) {
            if (library.getName().equals(name)) {
                return library.hasResources();
            }
        }

        throw new IllegalArgumentException("Unknown built-in library '" + name + "'!");
    }
}
