package mod.hey.studios.compiler.kotlin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import a.a.a.Dp;
import a.a.a.yq;
import mod.agus.jcoderz.lib.FilePathUtil;

public class KotlinCompilerUtil {

    /**
     * Returns whether there are any .kt files in
     *
     * > .sketchware/mysc/xxx/app/src/main/java,
     * > .sketchware/data/xxx/files/java
     *
     * or not.
     */
    public static boolean areAnyKtFilesPresent(Dp dp) {
        return areAnyKtFilesPresent(dp.f);
    }

    public static boolean areAnyKtFilesPresent(yq yq) {
        return getFilesToCompile(yq).stream()
                .anyMatch(it -> it.getName().endsWith(".kt"));
    }

    /**
     * Returns a list of `.java` & `.kt` files from the specified dirs.
     *
     * The name might be a little misleading, but only `.kt` files
     * are actually compiled by kotlinc, `.java` files are used for
     * classpath. (for Java-Kotlin interoperability)
     */
    static List<File> getFilesToCompile(yq workspace) {
        String scId = workspace.b;
        List<File> mFilesToCompile = new ArrayList<>();

        // .sketchware/mysc/xxx/app/src/main/java
        mFilesToCompile.addAll(getSourceFiles(
                new File(workspace.y)
        ));

        // .sketchware/data/xxx/files/java
        mFilesToCompile.addAll(getSourceFiles(
                new File(new FilePathUtil().getPathJava(scId))
        ));

        return mFilesToCompile;
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
