package a.a.a;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mod.jbk.build.BuiltInLibraries;
import mod.jbk.editor.manage.library.ExcludeBuiltInLibrariesActivity;

/**
 * A class to keep track of a project's built-in libraries.
 */

public class BuiltInLibraryManager {

    private final ArrayList<String> libraryNames = new ArrayList<>();
    private final ArrayList<Jp> libraries = new ArrayList<>();
    private final List<BuiltInLibraries.BuiltInLibrary> excludedLibraries;

    public BuiltInLibraryManager(String projectId) {
        excludedLibraries = ExcludeBuiltInLibrariesActivity.getExcludedLibraries(projectId);
    }

    /**
     * Add a built-in library to the project libraries list.
     * Won't add a library if it's in the list already,
     * or it got excluded with {@link ExcludeBuiltInLibrariesActivity}.
     *
     * @param libraryName The built-in library's name, e.g. material-1.0.0
     */
    public void addLibrary(String libraryName) {
        Optional<BuiltInLibraries.BuiltInLibrary> library = BuiltInLibraries.BuiltInLibrary.ofName(libraryName);
        //noinspection SimplifyOptionalCallChains because #isEmpty() isn't available on Android.
        if (!library.isPresent() || !excludedLibraries.contains(library.get())) {
            if (!libraryNames.contains(libraryName)) {
                Log.d(ProjectBuilder.TAG, "Added built-in library \"" + libraryName + "\" to project's dependencies");
                libraryNames.add(libraryName);
                libraries.add(new Jp(libraryName));
                addDependencies(libraryName);
            } else {
                Log.v(ProjectBuilder.TAG, "Didn't add built-in library \"" + libraryName + "\" to project's dependencies again");
            }
        } else {
            Log.v(ProjectBuilder.TAG, "Didn't add built-in library \"" + libraryName + "\" to project's dependencies as it's excluded");
            Log.v(ProjectBuilder.TAG, "Adding its dependencies though");
            addDependencies(libraryName);
        }
    }

    private void addDependencies(String libraryName) {
        for (String libraryDependency : BuiltInLibraryUtils.getKnownDependencies(libraryName)) {
            addLibrary(libraryDependency);
        }
    }

    public boolean containsLibrary(String libraryName) {
        Optional<BuiltInLibraries.BuiltInLibrary> library = BuiltInLibraries.BuiltInLibrary.ofName(libraryName);
        //noinspection SimplifyOptionalCallChains because #isEmpty() isn't available on Android.
        if (!library.isPresent()) {
            return false;
        }
        return libraries.contains(new Jp(library.get().getName()));
    }

    /**
     * @return {@link BuiltInLibraryManager#libraries}
     */
    public ArrayList<Jp> getLibraries() {
        return libraries;
    }
}
