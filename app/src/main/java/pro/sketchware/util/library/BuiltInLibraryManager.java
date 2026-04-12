package pro.sketchware.util.library;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import a.a.a.Jp;
import a.a.a.ProjectBuilder;
import mod.jbk.build.BuiltInLibraries;
import mod.jbk.editor.manage.library.ExcludeBuiltInLibrariesActivity;

/**
 * A class to keep track of a project's built-in libraries.
 */

public class BuiltInLibraryManager {

    private final ArrayList<Jp> libraries = new ArrayList<>();
    private final List<BuiltInLibraries.BuiltInLibrary> excludedLibraries;
    private final Set<String> processedLibraries = new HashSet<>();

    public BuiltInLibraryManager(String projectId) {
        if (ExcludeBuiltInLibrariesActivity.isExcludingEnabled(projectId)) {
            excludedLibraries = ExcludeBuiltInLibrariesActivity.getExcludedLibraries(projectId);
        } else {
            excludedLibraries = new ArrayList<>();
        }
    }

    /**
     * Add a built-in library to the project libraries list.
     * Won't add a library if it's in the list already,
     * or it got excluded with {@link ExcludeBuiltInLibrariesActivity}.
     *
     * @param libraryName The built-in library's name, e.g. material-1.0.0
     */
    public void addLibrary(String libraryName) {
        if (processedLibraries.contains(libraryName)) {
            Log.v(ProjectBuilder.TAG, "Didn't add built-in library \"" + libraryName + "\" to project's dependencies again");
            return;
        }
        processedLibraries.add(libraryName);

        Optional<BuiltInLibraries.BuiltInLibrary> library = BuiltInLibraries.BuiltInLibrary.ofName(libraryName);
        //noinspection SimplifyOptionalCallChains because #isEmpty() isn't available on Android.
        if (!library.isPresent() || !excludedLibraries.contains(library.get())) {
            Log.d(ProjectBuilder.TAG, "Added built-in library \"" + libraryName + "\" to project's dependencies");
            libraries.add(new Jp(libraryName));
        } else {
            Log.v(ProjectBuilder.TAG, "Didn't add built-in library \"" + libraryName + "\" to project's dependencies as it's excluded");
            Log.v(ProjectBuilder.TAG, "Adding its dependencies though");
        }
        addDependencies(libraryName);
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