package mod.tyron.compiler;

import java.io.File;
import java.util.ArrayList;

public abstract class Compiler {

    abstract ArrayList<File> getSourceFiles();

    abstract ArrayList<File> getLibraries();

    abstract void compile();
}
