package mod.tyron.compiler;

import android.util.Log;

import com.besome.sketch.SketchApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import a.a.a.Dp;
import a.a.a.Jp;
import a.a.a.KB;
import a.a.a.Kp;
import a.a.a.oB;
import a.a.a.yq;
import mod.agus.jcoderz.editor.library.ExtLibSelected;
import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.build.BuildSettings;
import mod.tyron.compiler.file.JavaFile;

import static com.besome.sketch.SketchApplication.getContext;

public class IncrementalJavaCompiler extends Compiler {

    public static final String TAG = IncrementalJavaCompiler.class.getSimpleName();

    private final String SAVE_PATH;

    private final StringBuffer errorBuffer = new StringBuffer();

    private final FilePathUtil filePathUtil = new FilePathUtil();

    private final yq projectConfig;
    private final BuildSettings buildSettings;
    private final ManageLocalLibrary mll;

    private Compiler.Result onResultListener;

    private final ArrayList<String> deletedFiles = new ArrayList<>();

    private Dp dp;
    private final oB g;
    private final File l;
    private final Kp n;


    public IncrementalJavaCompiler(yq projectConfig) {
        SAVE_PATH = FileUtil.getExternalStorageDir() + "/.sketchware/mysc/" + projectConfig.b + "/incremental";

        this.projectConfig = projectConfig;
        this.buildSettings = new BuildSettings(projectConfig.b);
        this.mll = new ManageLocalLibrary(projectConfig.b);
        this.g = new oB(false);
        this.l = new File(getContext().getFilesDir(), "libs");
        this.n = new Kp();

        dp = new Dp(getContext(), projectConfig);
        dp.j();
    }

    public void setOnResultListener(Compiler.Result result) {
        onResultListener = result;
    }

    @Override
    public ArrayList<File> getSourceFiles() {

        ArrayList<JavaFile> oldFiles = findJavaFiles(new File(SAVE_PATH));

        //combine all the java files from sketchware and java manager
        ArrayList<JavaFile> newFiles = new ArrayList<>(getSketchwareFiles());
        newFiles.addAll(findJavaFiles(projectConfig.c));

        return new ArrayList<>(getModifiedFiles(oldFiles, newFiles));
    }

    @Override
    public void compile() {

        if (onResultListener == null ) {
            throw new IllegalStateException("No result listeners were set");
        }

        j();

        ArrayList<File> projectJavaFiles = getSourceFiles();

        if (projectJavaFiles.isEmpty()) {
            Log.d(TAG, "Java files are up to date, skipping compilation");
            onResultListener.onResult(true, "Files up to date");
            return;
        }

        CompilerOutputStream errorOutputStream = new CompilerOutputStream(new StringBuffer());
        PrintWriter errWriter = new PrintWriter(errorOutputStream);

        CompilerOutputStream outputStream = new CompilerOutputStream(new StringBuffer());
        PrintWriter outWriter = new PrintWriter(outputStream);

        ArrayList<String> args = new ArrayList<>();

        args.add("-" + buildSettings.getValue(BuildSettings.SETTING_JAVA_VERSION, BuildSettings.SETTING_JAVA_VERSION_1_7));
        args.add("-nowarn");
        if (!buildSettings.getValue(BuildSettings.SETTING_NO_WARNINGS, "false").equals("true")) {
            args.add("-deprecation");
        }
        args.add("-d");
        args.add(SAVE_PATH + "/classes");
        args.add("-proc:none");
        args.add("-cp");

        args.add(l.getAbsolutePath() + "/jdk/rt.jar:" + l.getAbsolutePath() + "/android.jar:" +
                getLibrariesJarFile());

        for (File file : projectJavaFiles) {
            args.add(file.getAbsolutePath());
        }




        org.eclipse.jdt.internal.compiler.batch.Main main = new org.eclipse.jdt.internal.compiler.batch.Main(outWriter, errWriter, false, null, null);

        main.compile(args.toArray(new String[0]));
        boolean success = true;

        if (main.globalErrorsCount > 0) {
            success = false;
            onResultListener.onResult(success, errorOutputStream.buffer.toString());
        }

        try {
            errorOutputStream.close();
            outputStream.close();
            errWriter.close();
            outWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //merge the classes to the non modified classes so that we can compare later
        if(success) {
            Log.d(TAG, "Merging modified java files");
            mergeClasses(projectJavaFiles);
        }
    }

    /**
     * Gets all Java source code files, that Sketchware Pro has generated.
     */
    private ArrayList<JavaFile> getSketchwareFiles() {
        ArrayList<JavaFile> arrayList = new ArrayList<>();

        if (FileUtil.isExistFile(filePathUtil.getPathJava(projectConfig.b))) {
            arrayList.addAll(findJavaFiles(filePathUtil.getPathJava(projectConfig.b)));
        }

        if (FileUtil.isExistFile(filePathUtil.getPathBroadcast(projectConfig.b))) {
            arrayList.addAll(findJavaFiles(filePathUtil.getPathBroadcast(projectConfig.b)));
        }

        if (FileUtil.isExistFile(filePathUtil.getPathService(projectConfig.b))) {
            arrayList.addAll(findJavaFiles(filePathUtil.getPathService(projectConfig.b)));
        }

        return arrayList;
    }

    /**
     * Finds all Java source code files in a given directory.
     *
     * @param input input directory
     * @return returns a list of java files
     */
    private ArrayList<JavaFile> findJavaFiles(File input) {
        ArrayList<JavaFile> foundFiles = new ArrayList<>();

        if (input.isDirectory()) {
            File[] contents = input.listFiles();
            if (contents != null) {
                for (File child : contents) {
                    foundFiles.addAll(findJavaFiles(child));
                }
            }
        } else {
            if (input.getName().endsWith(".java")) {
                foundFiles.add(new JavaFile(input.getPath()));
            }
        }
        return foundFiles;
    }

    /**
     * Convenience method for {@link IncrementalJavaCompiler#findJavaFiles(File)} with Strings.
     */
    private ArrayList<JavaFile> findJavaFiles(String input) {
        return findJavaFiles(new File(input));
    }

    /**
     * Compares two list of Java source code files, and outputs ones, that are modified.
     */
    private ArrayList<File> getModifiedFiles(ArrayList<JavaFile> oldFiles, ArrayList<JavaFile> newFiles) {
        ArrayList<File> modifiedFiles = new ArrayList<>();

        for (JavaFile newFile : newFiles) {
            if (!oldFiles.contains(newFile)) {
                modifiedFiles.add(newFile);
            } else {
                File oldFile = oldFiles.get(oldFiles.indexOf(newFile));

                if (contentModified(oldFile, newFile)) {
                    modifiedFiles.add(newFile);
                }

                oldFiles.remove(oldFile);
            }
        }

        //we delete the removed classes from the original path
        for (JavaFile removedFile : oldFiles) {
            Log.d(TAG, "Class no longer exists, deleting file: " + removedFile.getName());
            if (!removedFile.delete()) {
                Log.w(TAG, "Failed to delete file " + removedFile.getAbsolutePath());
            } else {
                String name = removedFile.getName().substring(0, removedFile.getName().indexOf("."));
                deleteClassInDir(name, new File(SAVE_PATH + "/classes"));
            }
        }

        return modifiedFiles;
    }

    /**
     * merges the modified classes to the non modified files so that we can compare it next compile
     */
    public void mergeClasses(ArrayList<File> files) {
        for (File file : files) {
            String packagePath = SAVE_PATH + "/java/" + getPackageName(file);
            FileUtil.copyFile(file.getAbsolutePath(), packagePath);
        }
    }

    private boolean deleteClassInDir(String name, File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();

            if (children != null) {
                for (File child : children) {
                    deleteClassInDir(name, child);
                }
            }
        }else {
            String dirName = dir.getName().substring(0, dir.getName().indexOf("."));

            if (dirName.contains("$")) {
                dirName = dirName.substring(0, dirName.indexOf("$"));
            }

            if (dirName.equals(name)) {
                return dir.delete();
            }
        }

        return false;
    }

    /**
     * checks if contents of the file has been modified
     */
    private boolean contentModified(File old, File newFile) {

        if (old.isDirectory() || newFile.isDirectory()) {
            throw new IllegalArgumentException("Given file must be a java file");
        }

        if (!old.exists()) {
            return true;
        }

        if (!newFile.exists()) {
            return false;
        }

        if (newFile.length() > old.length()) {
            return true;
        }

        return newFile.lastModified() > old.lastModified();
    }

    /**
     * Gets the package name of a specific Java source code file.
     *
     * @param file The Java file
     * @return The file's package name, in Java format
     */
    private String getPackageName(File file) {
        String packageName = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            while (!packageName.contains("package")) {
                packageName = reader.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (packageName.contains("package")) {

            packageName = packageName.replace("package ", "")
                    .replace(";", ".")
                    .replace(".", "/");

            if(!packageName.endsWith("/")){
                packageName = packageName.concat("/");
            }

            return packageName + file.getName();
        }

        return null;
    }

    private String getLibrariesJarFile() {
        StringBuilder sb = new StringBuilder();

        for (Jp next : n.a()) {
            sb.append(l.getAbsolutePath()).append("/").append("libs").append("/").append(next.a()).append("/").append("classes.jar").append(":");
        }

        sb.append(mll.getJarLocalLibrary());

        return sb.toString();
    }
    private static class CompilerOutputStream extends OutputStream {

        public StringBuffer buffer;

        public CompilerOutputStream(StringBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void write(int b)  {
            buffer.append((char) b);
        }
    }

    public final boolean a(String fileInAssets, String compareTo) {
        long length;
        File compareToFile = new File(compareTo);
        long lengthOfFileInAssets = g.a(getContext(), fileInAssets);
        if (compareToFile.exists()) {
            length = compareToFile.length();
        } else {
            length = 0;
        }
        if (lengthOfFileInAssets == length) {
            return false;
        }
        g.a(compareToFile);
        g.a(getContext(), fileInAssets, compareTo);
        return true;
    }

    public void j() {
        /* If l doesn't exist, create it */
        if (!g.e(l.getAbsolutePath())) {
            g.f(l.getAbsolutePath());
        }

        String m = "libs";
        String androidJarPath = new File(l, "android.jar.zip").getAbsolutePath();
        String dexsArchivePath = new File(l, "dexs.zip").getAbsolutePath();
        String libsArchivePath = new File(l, "libs.zip").getAbsolutePath();
        String dexsDirectoryPath = new File(l, "dexs").getAbsolutePath();
        String libsDirectoryPath = new File(l, "libs").getAbsolutePath();
        String testkeyDirectoryPath = new File(l, "testkey").getAbsolutePath();
        if (a(m + File.separator + "android.jar.zip", androidJarPath)) {
            /* Delete android.jar */
            g.c(l.getAbsolutePath() + File.separator + "android.jar");
            new KB().a(androidJarPath, l.getAbsolutePath());
        }
        if (a(m + File.separator + "dexs.zip", dexsArchivePath)) {
            g.b(dexsDirectoryPath);
            g.f(dexsDirectoryPath);
            new KB().a(dexsArchivePath, dexsDirectoryPath);
        }
        if (a(m + File.separator + "libs.zip", libsArchivePath)) {
            g.b(libsDirectoryPath);
            g.f(libsDirectoryPath);
            new KB().a(libsArchivePath, libsDirectoryPath);
        }
        String jdkArchivePathInAssets = m + File.separator + "jdk.zip";
        String jdkArchivePath = new File(l, "jdk.zip").getAbsolutePath();
        /* Check if file size has changed */
        if (a(jdkArchivePathInAssets, jdkArchivePath)) {
            String jdkDirectoryPath = new File(l, "jdk").getAbsolutePath();
            /* Delete the directory? */
            g.b(jdkDirectoryPath);
            /* Create the directories? */
            g.f(jdkDirectoryPath);
            /* Extract the archive to the directory? */
            new KB().a(jdkArchivePath, jdkDirectoryPath);
        }
        String testkeyArchivePathInAssets = m + File.separator + "testkey.zip";
        String testkeyArchivePath = new File(l, "testkey.zip").getAbsolutePath();
        if (a(testkeyArchivePathInAssets, testkeyArchivePath)) {
            /* We need to copy testkey.zip to filesDir */
            g.b(testkeyDirectoryPath);
            g.f(testkeyDirectoryPath);
            new KB().a(testkeyArchivePath, testkeyDirectoryPath);

        }

        if (projectConfig.N.g) {
            n.a("appcompat-1.0.0");
            n.a("coordinatorlayout-1.0.0");
            n.a("material-1.0.0");
        }
        if (projectConfig.N.h) {
            n.a("firebase-common-19.0.0");
        }
        if (projectConfig.N.i) {
            n.a("firebase-auth-19.0.0");
        }
        if (projectConfig.N.j) {
            n.a("firebase-database-19.0.0");
        }
        if (projectConfig.N.k) {
            n.a("firebase-storage-19.0.0");
        }
        if (projectConfig.N.m) {
            n.a("play-services-maps-17.0.0");
        }
        if (projectConfig.N.l) {
            n.a("play-services-ads-18.2.0");
        }
        if (projectConfig.N.o) {
            n.a("gson-2.8.0");
        }
        if (projectConfig.N.n) {
            n.a("glide-4.11.0");
        }
        if (projectConfig.N.p) {
            n.a("okhttp-3.9.1");
        }
        ExtLibSelected.a(projectConfig.N.x, n);
    }
}
