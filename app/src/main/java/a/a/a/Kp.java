package a.a.a;

import android.util.Log;

import java.util.ArrayList;

/**
 * A class to keep track of a project's built-in libraries.
 */
public class Kp {

    public ArrayList<String> a = new ArrayList<>();
    public ArrayList<Jp> b = new ArrayList<>();

    /**
     * Add a built-in library to the project libraries list.
     * Won't add a library if it's in the list already.
     *
     * @param libraryName The built-in library's name, e.g. material-1.0.0
     */
    public void a(String libraryName) {
        Log.d(Dp.TAG, "Trying to add built-in library \"" + libraryName + "\"");
        if (!a.contains(libraryName)) {
            Log.d(Dp.TAG, "Added built-in library \"" + libraryName + "\" to project's dependencies");
            a.add(libraryName);
            b.add(new Jp(libraryName));
            b(libraryName);
        }
    }

    /**
     * Process a built-in library to add its dependencies if needed.
     *
     * @param libraryName The built-in library's name, e.g. material-1.0.0
     * @implNote          Could probably be made <code>private</code>
     */
    public void b(String libraryName) {
        for (String libraryDependency : qq.a(libraryName)) {
            a(libraryDependency);
        }
    }

    /**
     * @return {@link Kp#b}
     */
    public ArrayList<Jp> a() {
        return b;
    }
}
