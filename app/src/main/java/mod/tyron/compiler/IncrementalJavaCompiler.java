package mod.tyron.compiler;

import static com.besome.sketch.SketchApplication.getContext;

import android.util.Log;

import org.eclipse.jdt.internal.compiler.batch.Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import a.a.a.Dp;
import a.a.a.oB;
import a.a.a.yq;
import mod.agus.jcoderz.editor.manage.library.locallibrary.ManageLocalLibrary;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.build.BuildSettings;
import mod.jbk.util.LogUtil;
import mod.tyron.compiler.file.JavaFile;

public class IncrementalJavaCompiler extends Compiler {

    public static final String TAG = IncrementalJavaCompiler.class.getSimpleName();

    private final String SAVE_PATH;

    private final StringBuffer errorBuffer = new StringBuffer();

    private final FilePathUtil filePathUtil = new FilePathUtil();

    private final yq projectConfig;
    private final BuildSettings buildSettings;
    private final Dp compileHelper;
    private final ManageLocalLibrary manageLocalLibrary;
    private final ArrayList<String> builtInLibraries = new ArrayList<>();
    private final oB fileUtil;
    private final File libs;
    private Compiler.Result onResultListener;

    public IncrementalJavaCompiler(yq projectConfig) {
        SAVE_PATH = FileUtil.getExternalStorageDir() + "/.sketchware/mysc/" + projectConfig.sc_id + "/incremental";

        this.projectConfig = projectConfig;
        buildSettings = new BuildSettings(projectConfig.sc_id);
        compileHelper = new Dp(getContext(), projectConfig);
        compileHelper.getBuiltInLibrariesReady();
        manageLocalLibrary = new ManageLocalLibrary(projectConfig.sc_id);
        fileUtil = new oB(false);
        libs = new File(getContext().getFilesDir(), "libs");
    }

    public void setOnResultListener(Compiler.Result result) {
        onResultListener = result;
    }

    @Override
    public ArrayList<File> getSourceFiles() {
        ArrayList<JavaFile> oldFiles = findJavaFiles(new File(SAVE_PATH));

        //combine all the java files from sketchware pro
        ArrayList<JavaFile> newFiles = new ArrayList<>(getSketchwareFiles());
        //add original sketchware generated files
        newFiles.addAll(findJavaFiles(projectConfig.projectMyscPath + "/app/src/main/java"));

        //add R.java files
        newFiles.addAll(findJavaFiles(projectConfig.projectMyscPath + "/gen"));

        for (String library : getBuiltInLibraries()) {
            JavaFile rFile = new JavaFile(library + "/R.java");
            if (rFile.exists()) {
                newFiles.add(rFile);
            }
        }

        return new ArrayList<>(getModifiedFiles(oldFiles, newFiles));
    }

    @Override
    public void compile() {
        if (onResultListener == null) {
            throw new IllegalStateException("No result listeners were set");
        }

        ArrayList<File> projectJavaFiles = getSourceFiles();

        if (projectJavaFiles.isEmpty()) {
            Log.d(TAG, "Java files are up to date, skipping compilation");
            onResultListener.onResult(true, Compiler.TYPE_JAVA, "Files up to date");
            return;
        }

        Log.d(TAG, "Found " + projectJavaFiles.size() + " file(s) that are modified");

        CompilerOutputStream errorOutputStream = new CompilerOutputStream(new StringBuffer());
        PrintWriter errWriter = new PrintWriter(errorOutputStream);

        CompilerOutputStream outputStream = new CompilerOutputStream(new StringBuffer());
        PrintWriter outWriter = new PrintWriter(outputStream);

        ArrayList<String> args = new ArrayList<>();

        args.add("-" + buildSettings.getValue(BuildSettings.SETTING_JAVA_VERSION, BuildSettings.SETTING_JAVA_VERSION_1_7));

        args.add("-nowarn");

        if (!buildSettings.getValue(BuildSettings.SETTING_NO_WARNINGS, BuildSettings.SETTING_GENERIC_VALUE_FALSE)
                .equals(BuildSettings.SETTING_GENERIC_VALUE_TRUE)) {
            args.add("-deprecation");
        }

        args.add("-d");
        args.add(SAVE_PATH + "/classes");

        args.add("-proc:none");

        args.add("-cp");
        args.add(compileHelper.getProGuardClasspath());

        for (File file : projectJavaFiles) {
            args.add(file.getAbsolutePath());
        }

        args.add("-sourcepath");
        args.add(SAVE_PATH + "/java");

        Main main = new Main(outWriter, errWriter, false, null, null);

        Log.d(TAG, "Compiling now!");
        LogUtil.log(TAG, "Eclipse's arguments: ",
                "Logging Eclipse's arguments over multiple lines because of length.",
                args);
        main.compile(args.toArray(new String[0]));
        boolean success = true;

        if (main.globalErrorsCount > 0) {
            success = false;
            onResultListener.onResult(false, Compiler.TYPE_JAVA, errorOutputStream.buffer.toString());
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
        if (success) {
            Log.d(TAG, "Merging modified java files");
            mergeClasses(projectJavaFiles);

            onResultListener.onResult(true, Compiler.TYPE_JAVA, "Success.");
        }
    }

    /**
     * Gets all Java source code files, that Sketchware Pro has generated.
     */
    private ArrayList<JavaFile> getSketchwareFiles() {
        ArrayList<JavaFile> arrayList = new ArrayList<>();

        if (FileUtil.isExistFile(filePathUtil.getPathJava(projectConfig.sc_id))) {
            arrayList.addAll(findJavaFiles(filePathUtil.getPathJava(projectConfig.sc_id)));
        }

        if (FileUtil.isExistFile(filePathUtil.getPathBroadcast(projectConfig.sc_id))) {
            arrayList.addAll(findJavaFiles(filePathUtil.getPathBroadcast(projectConfig.sc_id)));
        }

        if (FileUtil.isExistFile(filePathUtil.getPathService(projectConfig.sc_id))) {
            arrayList.addAll(findJavaFiles(filePathUtil.getPathService(projectConfig.sc_id)));
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
                //workaround to exclude network request files when theres no component available
                if (input.getName().equals("RequestNetwork.java")
                        || input.getName().equals("RequestNetworkController.java")) {
                    if (projectConfig.N.isHttp3Used) {
                        foundFiles.add(new JavaFile(input.getPath()));
                    }
                } else {
                    foundFiles.add(new JavaFile(input.getPath()));
                }
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
                    if (oldFile.delete()) {
                        Log.d(TAG, oldFile.getName() + ": Removed old class file that has been modified");
                    }
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
        } else {
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
            throw new IllegalArgumentException("Given file must be a Java file");
        }

        if (!old.exists() || !newFile.exists()) {
            return true;
        }

        if (newFile.length() != old.length()) {
            return true;
        }

        int oldFileHash = FileUtil.readFile(old.getAbsolutePath()).hashCode();
        int newFileHash = FileUtil.readFile(newFile.getAbsolutePath()).hashCode();
        return oldFileHash == newFileHash;
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

            if (!packageName.endsWith("/")) {
                packageName = packageName.concat("/");
            }

            return packageName + file.getName();
        }

        return null;
    }

    private String getLibrariesJarFile() {
        StringBuilder sb = new StringBuilder();

        for (String library : getBuiltInLibraries()) {
            sb.append(library).append("/").append("classes.jar");
        }

        if (buildSettings.getValue(BuildSettings.SETTING_NO_HTTP_LEGACY, "false").equals("false")) {
            sb.append(":");
            sb.append(libs.getAbsolutePath());
            sb.append("/");
            sb.append("libs");
            sb.append("/");
            sb.append("http-legacy-android-28");
            sb.append("/");
            sb.append("classes.jar");
        }

        sb.append(manageLocalLibrary.getJarLocalLibrary());

        return sb.toString();
    }

    public ArrayList<String> getBuiltInLibraries() {

        String path = getContext().getFilesDir() + "/libs/libs/";
        ArrayList<String> arrayList = new ArrayList<>();

        if (projectConfig.N.g) {
            arrayList.add(":" + path + "appcompat-1.0.0");
            arrayList.add(":" + path + "coordinatorlayout-1.0.0");
            arrayList.add(":" + path + "material-1.0.0");
        }
        if (projectConfig.N.isFirebaseEnabled) {
            arrayList.add(":" + path + "firebase-common-19.0.0");
        }
        if (projectConfig.N.isFirebaseAuthUsed) {
            arrayList.add(":" + path + "firebase-auth-19.0.0");
        }
        if (projectConfig.N.isFirebaseDatabaseUsed) {
            arrayList.add(":" + path + "firebase-database-19.0.0");
        }
        if (projectConfig.N.isFirebaseStorageUsed) {
            arrayList.add(":" + path + "firebase-storage-19.0.0");
        }
        if (projectConfig.N.isMapUsed) {
            arrayList.add(":" + path + "play-services-maps-17.0.0");
        }
        if (projectConfig.N.isAdMobEnabled) {
            arrayList.add(":" + path + "play-services-ads-18.2.0");
        }

        if (projectConfig.N.isGlideUsed) {
            arrayList.add(":" + path + "glide-4.11.0");
        }
        if (projectConfig.N.isHttp3Used) {
            arrayList.add(":" + path + "okhttp-3.9.1");
        }
        if (projectConfig.N.isGsonUsed) {
            arrayList.add(":" + path + "gson-2.8.0");
        }

        return arrayList;

    }

    private static class CompilerOutputStream extends OutputStream {

        public StringBuffer buffer;

        public CompilerOutputStream(StringBuffer buffer) {
            this.buffer = buffer;
        }

        @Override
        public void write(int b) {
            buffer.append((char) b);
        }
    }

}
