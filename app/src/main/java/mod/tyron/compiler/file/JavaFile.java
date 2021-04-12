package mod.tyron.compiler.file;

import java.io.File;

public class JavaFile extends File {

    public JavaFile(String pathname) {
        super(pathname);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof JavaFile) {
            return ((JavaFile) obj).getName().equals(this.getName());
        }
        return false;
    }
}
