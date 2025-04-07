package mod.hey.studios.compiler.kotlin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import a.a.a.ProjectBuilder;
import a.a.a.yq;
import pro.sketchware.utility.FilePathUtil;

public class KotlinCompilerUtil {

    /**
     * Returns whether there are any .kt files in
     * <p>
     * > .sketchware/mysc/xxx/app/src/main/java,
     * > .sketchware/mysc/xxx/gen,
     * > .sketchware/data/xxx/files/java
     * <p>
     * or not.
     */
    public static boolean areAnyKtFilesPresent(ProjectBuilder bui) {
        return areAnyKtFilesPresent(bui.yq);
    }

    public static boolean areAnyKtFilesPresent(yq yq) {
        return getFilesToCompile(yq).stream()
                .anyMatch(it -> it.getName().endsWith(".kt"));
    }

    /**
     * Returns a list of `.java` & `.kt` files from the specified dirs.
     * <p>
     * The name might be a little misleading, but only `.kt` files
     * are actually compiled by kotlinc, `.java` files are used for
     * classpath. (for Java-Kotlin interoperability)
     */
    static List<File> getFilesToCompile(yq workspace) {
        String scId = workspace.sc_id;
        List<File> mFilesToCompile = new ArrayList<>();

        // .sketchware/mysc/xxx/app/src/main/java
        mFilesToCompile.addAll(getSourceFiles(
                new File(workspace.javaFilesPath)
        ));

        // .sketchware/mysc/xxx/gen
        mFilesToCompile.addAll(getSourceFiles(
                new File(workspace.rJavaDirectoryPath)
        ));

        // .sketchware/data/xxx/files/java
        mFilesToCompile.addAll(getSourceFiles(
                new File(new FilePathUtil().getPathJava(scId))
        ));

        return mFilesToCompile;
    }

    /**
     * Returns a list of available kotlin compiler plugins (.jar)
     * found in `/.sketchware/data/xxx/files/kt_plugins` dir.
     */
    static List<File> getCompilerPlugins(yq workspace) {
        String scId = workspace.sc_id;

        File pluginDir = new File(new FilePathUtil().getPathKotlinCompilerPlugins(scId));
        if (!pluginDir.exists()) {
            return Collections.emptyList();
        }

        File[] children = pluginDir.listFiles(c -> c.getName().endsWith(".jar"));
        if (children == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(Arrays.asList(children));
    }

    private static List<File> getSourceFiles(File dir) {
        List<File> files = new ArrayList<>();

        File[] children = dir.listFiles();
        if (children == null) return files;

        for (File child : children) {
            if (child.isDirectory()) {
                files.addAll(getSourceFiles(child));
            } else {
                if (child.getName().endsWith(".kt") || child.getName().endsWith(".java")) {
                    files.add(child);
                }
            }
        }

        return files;
    }
}
