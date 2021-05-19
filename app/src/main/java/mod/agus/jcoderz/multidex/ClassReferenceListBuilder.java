package mod.agus.jcoderz.multidex;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import mod.agus.jcoderz.dx.cf.direct.DirectClassFile;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.ConstantPool;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;

public class ClassReferenceListBuilder {
    static final boolean assertionsDisabled = (!ClassReferenceListBuilder.class.desiredAssertionStatus());
    private static final String CLASS_EXTENSION = ".class";
    private final Set<String> classNames = new HashSet();
    private final Path path;

    public ClassReferenceListBuilder(Path path2) {
        this.path = path2;
    }

    @Deprecated
    public static void main(String[] strArr) {
        MainDexListBuilder.main(strArr);
    }

    public void addRoots(ZipFile zipFile) throws IOException {
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            String name = ((ZipEntry) entries.nextElement()).getName();
            if (name.endsWith(".class")) {
                this.classNames.add(name.substring(0, name.length() - ".class".length()));
            }
        }
        Enumeration<? extends ZipEntry> entries2 = zipFile.entries();
        while (entries2.hasMoreElements()) {
            String name2 = ((ZipEntry) entries2.nextElement()).getName();
            if (name2.endsWith(".class")) {
                try {
                    addDependencies(this.path.getClass(name2).getConstantPool());
                } catch (FileNotFoundException e) {
                    throw new IOException("Class " + name2 + " is missing form original class path " + this.path, e);
                }
            }
        }
    }

    public Set<String> getClassNames() {
        return this.classNames;
    }

    private void addDependencies(ConstantPool constantPool) {
        Constant[] entries = constantPool.getEntries();
        for (Constant constant : entries) {
            if (constant instanceof CstType) {
                checkDescriptor(((CstType) constant).getClassType());
            } else if (constant instanceof CstFieldRef) {
                checkDescriptor(((CstFieldRef) constant).getType());
            } else if (constant instanceof CstMethodRef) {
                Prototype prototype = ((CstMethodRef) constant).getPrototype();
                checkDescriptor(prototype.getReturnType());
                StdTypeList parameterTypes = prototype.getParameterTypes();
                for (int i = 0; i < parameterTypes.size(); i++) {
                    checkDescriptor(parameterTypes.get(i));
                }
            }
        }
    }

    private void checkDescriptor(Type type) {
        String descriptor = type.getDescriptor();
        if (descriptor.endsWith(";")) {
            int lastIndexOf = descriptor.lastIndexOf(91);
            if (lastIndexOf < 0) {
                addClassWithHierachy(descriptor.substring(1, descriptor.length() - 1));
            } else if (assertionsDisabled || (descriptor.length() > lastIndexOf + 3 && descriptor.charAt(lastIndexOf + 1) == 'L')) {
                addClassWithHierachy(descriptor.substring(lastIndexOf + 2, descriptor.length() - 1));
            } else {
                throw new AssertionError();
            }
        }
    }

    private void addClassWithHierachy(String str) {
        if (!this.classNames.contains(str)) {
            try {
                DirectClassFile directClassFile = this.path.getClass(str + ".class");
                this.classNames.add(str);
                CstType superclass = directClassFile.getSuperclass();
                if (superclass != null) {
                    addClassWithHierachy(superclass.getClassType().getClassName());
                }
                TypeList interfaces = directClassFile.getInterfaces();
                int size = interfaces.size();
                for (int i = 0; i < size; i++) {
                    addClassWithHierachy(interfaces.getType(i).getClassName());
                }
            } catch (FileNotFoundException e) {
            }
        }
    }
}
