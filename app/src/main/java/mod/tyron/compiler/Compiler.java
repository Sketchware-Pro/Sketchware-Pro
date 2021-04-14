package mod.tyron.compiler;

import java.io.File;
import java.util.ArrayList;

import a.a.a.zy;

public abstract class Compiler {

    public interface Result {
        void onResult(boolean success, String message);
    }

    abstract public ArrayList<?> getSourceFiles();

    abstract public void compile();
}
