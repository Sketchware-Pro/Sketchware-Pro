package mod.tyron.compiler;

import com.android.tools.r8.D8;
import com.besome.sketch.SketchApplication;

import java.io.File;
import java.util.ArrayList;

import a.a.a.yq;
import mod.hey.studios.project.ProjectSettings;

public class IncrementalD8Compiler extends Compiler {

    private final String CLASS_PATH;

    private final yq projectConfig;
    private final ProjectSettings projectSettings;

    public IncrementalD8Compiler(yq projectConfig) {
        this.projectConfig = projectConfig;

        projectSettings = new ProjectSettings(projectConfig.sc_id);
        CLASS_PATH = projectConfig.projectMyscPath + "/incremental/classes";
    }

    @Override
    public ArrayList<File> getSourceFiles() {
        return findClassFiles(new File(CLASS_PATH));
    }

    @Override
    public void compile() {
        ArrayList<String> args = new ArrayList<>();
        ArrayList<File> files = getSourceFiles();

        File outputFile = new File(projectConfig.projectMyscPath + "/incremental/build");

        for (File file : files) {
            args.add(file.getAbsolutePath());
        }

        if (!outputFile.exists()) {
            outputFile.mkdirs();
        }

        args.add("--intermediate");
        args.add("--min-api");
        args.add(projectSettings.getValue("min_sdk", "21"));
        args.add("--lib");
        args.add(SketchApplication.getContext().getFilesDir() + "/libs/android.jar");
        args.add("--output");
        args.add(outputFile.getAbsolutePath());
        args.add("--file-per-class");

        try {
            D8.main(args.toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<File> findClassFiles(File input) {
        ArrayList<File> foundFiles = new ArrayList<>();

        if (input.isDirectory()) {
            File[] contents = input.listFiles();
            if (contents != null) {
                for (File child : contents) {
                    foundFiles.addAll(findClassFiles(child));
                }
            }
        } else {
            if (input.getName().endsWith(".class")) {
                foundFiles.add(input);
            }
        }
        return foundFiles;
    }
}
