package mod.agus.jcoderz.multidex;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipFile;

import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeVisibleAnnotations;
import mod.agus.jcoderz.dx.cf.direct.DirectClassFile;
import mod.agus.jcoderz.dx.cf.iface.Attribute;
import mod.agus.jcoderz.dx.cf.iface.FieldList;
import mod.agus.jcoderz.dx.cf.iface.HasAttribute;
import mod.agus.jcoderz.dx.cf.iface.MethodList;

public class MainDexListBuilder {
    private static final String CLASS_EXTENSION = ".class";
    private static final String DISABLE_ANNOTATION_RESOLUTION_WORKAROUND = "--disable-annotation-resolution-workaround";
    private static final String EOL = System.getProperty("line.separator");
    private static final int STATUS_ERROR = 1;
    private static final String USAGE_MESSAGE = ("Usage:" + EOL + EOL + "Short version: Don't use this." + EOL + EOL + "Slightly longer version: This tool is used by mainDexClasses script to build" + EOL + "the main dex list." + EOL);
    private final Set<String> filesToKeep = new HashSet();

    public MainDexListBuilder(boolean z, String str, String str2) throws IOException {
        Throwable th;
        Path path;
        ZipFile zipFile = null;
        try {
            ZipFile zipFile2 = new ZipFile(str);
            try {
                Path path2 = new Path(str2);
                try {
                    ClassReferenceListBuilder classReferenceListBuilder = new ClassReferenceListBuilder(path2);
                    classReferenceListBuilder.addRoots(zipFile2);
                    Iterator<String> it = classReferenceListBuilder.getClassNames().iterator();
                    while (it.hasNext()) {
                        this.filesToKeep.add(it.next() + ".class");
                    }
                    if (z) {
                        keepAnnotated(path2);
                    }
                    try {
                        zipFile2.close();
                    } catch (IOException e) {
                    }
                    if (path2 != null) {
                        for (ClassPathElement classPathElement : path2.elements) {
                            try {
                                classPathElement.close();
                            } catch (IOException e2) {
                            }
                        }
                    }
                } catch (Throwable th2) {
                    zipFile = zipFile2;
                    th = th2;
                    path = path2;
                    try {
                        zipFile.close();
                    } catch (IOException e3) {
                    }
                    if (path != null) {
                        for (ClassPathElement classPathElement2 : path.elements) {
                            try {
                                classPathElement2.close();
                            } catch (IOException e4) {
                            }
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                path = null;
                zipFile = zipFile2;
                zipFile.close();
                if (path != null) {
                }
                throw th;
            }
        } catch (IOException e5) {
            throw new IOException("\"" + str + "\" can not be read as a zip archive. (" + e5.getMessage() + ")", e5);
        } catch (Throwable th4) {
            th = th4;
            path = null;
            zipFile.close();
            if (path != null) {
            }
            try {
                throw th;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    public static void main(String[] strArr) {
        boolean z = true;
        int i = 0;
        while (i < strArr.length - 2) {
            if (strArr[i].equals(DISABLE_ANNOTATION_RESOLUTION_WORKAROUND)) {
                z = false;
            } else {
                System.err.println("Invalid option " + strArr[i]);
                printUsage();
                System.err.println("exit code 1");
            }
            i++;
        }
        if (strArr.length - i != 2) {
            printUsage();
            System.err.println("exit code 1");
        }
        try {
            printList(new MainDexListBuilder(z, strArr[i], strArr[i + 1]).getMainDexList());
        } catch (IOException e) {
            System.err.println("A fatal error occured: " + e.getMessage());
            System.err.println("exit code 1");
        }
    }

    private static void printUsage() {
        System.err.print(USAGE_MESSAGE);
    }

    private static void printList(Set<String> set) {
        for (String str : set) {
            System.out.println(str);
        }
    }

    public Set<String> getMainDexList() {
        return this.filesToKeep;
    }

    private void keepAnnotated(Path path) throws FileNotFoundException {
        for (ClassPathElement classPathElement : path.getElements()) {
            for (String str : classPathElement.list()) {
                if (str.endsWith(".class")) {
                    DirectClassFile directClassFile = path.getClass(str);
                    if (hasRuntimeVisibleAnnotation(directClassFile)) {
                        this.filesToKeep.add(str);
                    } else {
                        MethodList methods = directClassFile.getMethods();
                        int i = 0;
                        while (true) {
                            if (i >= methods.size()) {
                                FieldList fields = directClassFile.getFields();
                                int i2 = 0;
                                while (true) {
                                    if (i2 >= fields.size()) {
                                        break;
                                    } else if (hasRuntimeVisibleAnnotation(fields.get(i2))) {
                                        this.filesToKeep.add(str);
                                        break;
                                    } else {
                                        i2++;
                                    }
                                }
                            } else if (hasRuntimeVisibleAnnotation(methods.get(i))) {
                                this.filesToKeep.add(str);
                                break;
                            } else {
                                i++;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean hasRuntimeVisibleAnnotation(HasAttribute hasAttribute) {
        Attribute findFirst = hasAttribute.getAttributes().findFirst(AttRuntimeVisibleAnnotations.ATTRIBUTE_NAME);
        return findFirst != null && ((AttRuntimeVisibleAnnotations) findFirst).getAnnotations().size() > 0;
    }
}
