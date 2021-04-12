package mod.tyron.compiler.file;

import java.io.File;

public class ClassFile extends File {

    public ClassFile(String pathname) {
        super(pathname);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ClassFile) {
            return ((ClassFile) obj).getName().equals(this.getName());
        }
        return false;
    }
}
