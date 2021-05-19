package mod.agus.jcoderz.dx.command.annotool;

import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jdt.internal.compiler.util.Util;

import java.io.File;
import java.lang.annotation.ElementType;
import java.util.HashSet;
import java.util.Iterator;

import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeInvisibleAnnotations;
import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeVisibleAnnotations;
import mod.agus.jcoderz.dx.cf.attrib.BaseAnnotations;
import mod.agus.jcoderz.dx.cf.direct.ClassPathOpener;
import mod.agus.jcoderz.dx.cf.direct.DirectClassFile;
import mod.agus.jcoderz.dx.cf.direct.StdAttributeFactory;
import mod.agus.jcoderz.dx.cf.iface.Attribute;
import mod.agus.jcoderz.dx.cf.iface.AttributeList;
import mod.agus.jcoderz.dx.rop.annotation.Annotation;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.multidex.ClassPathElement;

public class AnnotationLister {
    private static final String PACKAGE_INFO = "package-info";
    private static int[] $SWITCH_TABLE$mod$agus$jcoderz$dx$command$annotool$Main$PrintType = null;
    private final Main.Arguments args;
    HashSet<String> matchInnerClassesOf = new HashSet<>();
    HashSet<String> matchPackages = new HashSet<>();

    AnnotationLister(Main.Arguments arguments) {
        this.args = arguments;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$mod$agus$jcoderz$dx$command$annotool$Main$PrintType() {
        int[] iArr = $SWITCH_TABLE$mod$agus$jcoderz$dx$command$annotool$Main$PrintType;
        if (iArr == null) {
            iArr = new int[Main.PrintType.values().length];
            try {
                iArr[Main.PrintType.CLASS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Main.PrintType.INNERCLASS.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Main.PrintType.METHOD.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[Main.PrintType.PACKAGE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            $SWITCH_TABLE$mod$agus$jcoderz$dx$command$annotool$Main$PrintType = iArr;
        }
        return iArr;
    }

    public void process() {
        for (String str : this.args.files) {
            new ClassPathOpener(str, true, new ClassPathOpener.Consumer() {
                /* class mod.agus.jcoderz.dx.command.annotool.AnnotationLister.AnonymousClass1 */

                @Override // mod.agus.jcoderz.dx.cf.direct.ClassPathOpener.Consumer
                public boolean processFileBytes(String str, long j, byte[] bArr) {
                    if (str.endsWith(SuffixConstants.SUFFIX_STRING_class)) {
                        DirectClassFile directClassFile = new DirectClassFile(new ByteArray(bArr), str, true);
                        directClassFile.setAttributeFactory(StdAttributeFactory.THE_ONE);
                        AttributeList attributes = directClassFile.getAttributes();
                        String className = directClassFile.getThisClass().getClassType().getClassName();
                        if (className.endsWith(AnnotationLister.PACKAGE_INFO)) {
                            for (Attribute findFirst = attributes.findFirst(AttRuntimeInvisibleAnnotations.ATTRIBUTE_NAME); findFirst != null; findFirst = attributes.findNext(findFirst)) {
                                AnnotationLister.this.visitPackageAnnotation(directClassFile, (BaseAnnotations) findFirst);
                            }
                            for (Attribute findFirst2 = attributes.findFirst(AttRuntimeVisibleAnnotations.ATTRIBUTE_NAME); findFirst2 != null; findFirst2 = attributes.findNext(findFirst2)) {
                                AnnotationLister.this.visitPackageAnnotation(directClassFile, (BaseAnnotations) findFirst2);
                            }
                        } else if (AnnotationLister.this.isMatchingInnerClass(className) || AnnotationLister.this.isMatchingPackage(className)) {
                            AnnotationLister.this.printMatch(directClassFile);
                        } else {
                            for (Attribute findFirst3 = attributes.findFirst(AttRuntimeInvisibleAnnotations.ATTRIBUTE_NAME); findFirst3 != null; findFirst3 = attributes.findNext(findFirst3)) {
                                AnnotationLister.this.visitClassAnnotation(directClassFile, (BaseAnnotations) findFirst3);
                            }
                            for (Attribute findFirst4 = attributes.findFirst(AttRuntimeVisibleAnnotations.ATTRIBUTE_NAME); findFirst4 != null; findFirst4 = attributes.findNext(findFirst4)) {
                                AnnotationLister.this.visitClassAnnotation(directClassFile, (BaseAnnotations) findFirst4);
                            }
                        }
                    }
                    return true;
                }

                @Override // mod.agus.jcoderz.dx.cf.direct.ClassPathOpener.Consumer
                public void onException(Exception exc) {
                    throw new RuntimeException(exc);
                }

                @Override // mod.agus.jcoderz.dx.cf.direct.ClassPathOpener.Consumer
                public void onProcessArchiveStart(File file) {
                }
            }).process();
        }
    }

    private void visitClassAnnotation(DirectClassFile directClassFile, BaseAnnotations baseAnnotations) {
        if (this.args.eTypes.contains(ElementType.TYPE)) {
            for (Annotation annotation : baseAnnotations.getAnnotations().getAnnotations()) {
                if (this.args.aclass.equals(annotation.getType().getClassType().getClassName())) {
                    printMatch(directClassFile);
                }
            }
        }
    }

    private void visitPackageAnnotation(DirectClassFile directClassFile, BaseAnnotations baseAnnotations) {
        String substring;
        if (this.args.eTypes.contains(ElementType.PACKAGE)) {
            String className = directClassFile.getThisClass().getClassType().getClassName();
            int lastIndexOf = className.lastIndexOf(47);
            if (lastIndexOf == -1) {
                substring = "";
            } else {
                substring = className.substring(0, lastIndexOf);
            }
            for (Annotation annotation : baseAnnotations.getAnnotations().getAnnotations()) {
                if (this.args.aclass.equals(annotation.getType().getClassType().getClassName())) {
                    printMatchPackage(substring);
                }
            }
        }
    }

    private void printMatchPackage(String str) {
        Iterator it = this.args.printTypes.iterator();
        while (it.hasNext()) {
            switch ($SWITCH_TABLE$mod$agus$jcoderz$dx$command$annotool$Main$PrintType()[((Main.PrintType) it.next()).ordinal()]) {
                case 1:
                case 2:
                case 3:
                    this.matchPackages.add(str);
                    break;
                case 4:
                    System.out.println(str.replace(ClassPathElement.SEPARATOR_CHAR, Util.C_DOT));
                    break;
            }
        }
    }

    private void printMatch(DirectClassFile directClassFile) {
        Iterator it = this.args.printTypes.iterator();
        while (it.hasNext()) {
            switch ($SWITCH_TABLE$mod$agus$jcoderz$dx$command$annotool$Main$PrintType()[((Main.PrintType) it.next()).ordinal()]) {
                case 1:
                    System.out.println(directClassFile.getThisClass().getClassType().getClassName().replace(ClassPathElement.SEPARATOR_CHAR, Util.C_DOT));
                    break;
                case 2:
                    this.matchInnerClassesOf.add(directClassFile.getThisClass().getClassType().getClassName());
                    break;
            }
        }
    }


    private boolean isMatchingInnerClass(String str) {
        do {
            int lastIndexOf = str.lastIndexOf(36);
            if (lastIndexOf <= 0) {
                return false;
            }
            str = str.substring(0, lastIndexOf);
        } while (!this.matchInnerClassesOf.contains(str));
        return true;
    }

    private boolean isMatchingPackage(String str) {
        String substring;
        int lastIndexOf = str.lastIndexOf(47);
        if (lastIndexOf == -1) {
            substring = "";
        } else {
            substring = str.substring(0, lastIndexOf);
        }
        return this.matchPackages.contains(substring);
    }
}
