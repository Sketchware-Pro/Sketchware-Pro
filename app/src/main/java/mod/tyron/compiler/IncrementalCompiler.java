package mod.tyron.compiler;

import android.util.Log;

import com.android.tools.r8.w.P;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

import a.a.a.yq;
import mod.agus.jcoderz.lib.FilePathUtil;
import mod.agus.jcoderz.lib.FileUtil;
import mod.hey.studios.build.BuildSettings;

public class IncrementalCompiler extends Compiler {

    public static final String TAG = IncrementalCompiler.class.getSimpleName();

    private final String SAVE_PATH;

    private final StringBuffer errorBuffer = new StringBuffer();

    private final FilePathUtil filePathUtil = new FilePathUtil();

    private final yq projectConfig;
    private final BuildSettings buildSettings;

    public IncrementalCompiler(yq projectConfig){
        SAVE_PATH = FileUtil.getExternalStorageDir() + "/.sketchware/mysc/" + projectConfig.b + "/incremental";

        this.projectConfig = projectConfig;
        this.buildSettings = new BuildSettings(projectConfig.b);
    }

    @Override
    public ArrayList<File> getSourceFiles() {

        ArrayList<JavaFile> oldFiles = findJavaFiles(new File(SAVE_PATH));

        //combine all the java files from sketchware and java manager
        ArrayList<JavaFile> newFiles = new ArrayList<>(getSketchwareFiles());

        return new ArrayList<>(getModifiedFiles(oldFiles, newFiles));
    }

    @Override
    public ArrayList<File> getLibraries() {
        return null;
    }

    @Override
    public void compile() {

        CompilerOutputStream errorOutputStream = new CompilerOutputStream(errorBuffer);
        PrintWriter errWriter = new PrintWriter(errorOutputStream);

        CompilerOutputStream outputStream = new CompilerOutputStream(new StringBuffer());
        PrintWriter outWriter = new PrintWriter(outputStream);

        ArrayList<File> projectJavaFiles = getSourceFiles();
        ArrayList<String> args = new ArrayList<>();

        args.add("-" + buildSettings.getValue(BuildSettings.SETTING_JAVA_VERSION, BuildSettings.SETTING_JAVA_VERSION_1_7));
        args.add("-nowarn");
        if (!buildSettings.getValue(BuildSettings.SETTING_NO_WARNINGS, "false").equals("true")) {
            args.add("-deprecation");
        }
        args.add("-d");
        args.add(projectConfig.u);
        args.add("-cp");

        org.eclipse.jdt.internal.compiler.batch.Main main = new org.eclipse.jdt.internal.compiler.batch.Main(outWriter, errWriter, false);

        //TODO: do the actual compilation
        throw new UnsupportedOperationException("Not yet implemented");
        //merge the classes to the non modified classes so that we can compare later
        //mergeClasses(projectJavaFiles);
    }

    /**
     * gets all the java files that sketchware pro has generated
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
     * finds all the java files in a given directory
     *
     * @param input input directory
     * @return returns a list of java files
     */
    private ArrayList<JavaFile> findJavaFiles(File input) {
        ArrayList<JavaFile> foundFiles = new ArrayList<>();

        if (input.isDirectory()) {
            for (File child : input.listFiles()) {
                foundFiles.addAll(findJavaFiles(child));
            }
        }else{
            if(input.getName().endsWith(".java")) {
                foundFiles.add(new JavaFile(input.getPath()));
            }
        }
        return foundFiles;
    }

    /**
     * Convenience method for string inputs
     */
    private ArrayList<JavaFile> findJavaFiles(String input){
        return findJavaFiles(new File(input));
    }

    /**
     * Compares two list of java files and outputs the ones that are modified
     */
    private ArrayList<File> getModifiedFiles(ArrayList<JavaFile> oldFiles, ArrayList<JavaFile> newFiles) {
        ArrayList<File> modifiedFiles = new ArrayList<>();

        for (JavaFile newFile : newFiles) {
            if(!oldFiles.contains(newFile)){
                modifiedFiles.add(newFile);
            }else{
                File oldFile = oldFiles.get(oldFiles.indexOf(newFile));

                if (contentModified(oldFile,  newFile)) {
                    modifiedFiles.add(newFile);
                }

                oldFiles.remove(oldFile);
            }
        }

        //we delete the removed classes from the original path
        for (JavaFile removedFile : oldFiles) {
            removedFile.delete();
            Log.d(TAG, "Class no longer exists, deleting file: " + removedFile.getName());
        }

        return modifiedFiles;
    }

    /**
     * merges the modified classes to the non modified files so that we can compare it next compile
     */
    public void mergeClasses(ArrayList<File> files){
        for(File file : files){
            String packagePath = SAVE_PATH + "/java/" + getPackageName(file);
            FileUtil.copyFile(file.getAbsolutePath(), packagePath);
        }
    }

    /**
     *  checks if contents of the file has been modified
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
     * gets the package name of a specific java file
     *
     * @param file java file
     * @return package name
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

        if(packageName.contains("package")){
            return packageName
                    .replace("package ", "")
                    .replace(".", "/")
                    .replace(";", "/") + file.getName();
        }

        return null;
    }
    private static class CompilerOutputStream extends OutputStream {

        public StringBuffer buffer;

        public CompilerOutputStream(StringBuffer buffer) {
            this.buffer = buffer;
        }
        @Override
        public void write(int b) throws IOException {
           buffer.append((char)b);
        }
    }
}
