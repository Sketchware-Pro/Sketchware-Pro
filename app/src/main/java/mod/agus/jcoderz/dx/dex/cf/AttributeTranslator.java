package mod.agus.jcoderz.dx.dex.cf;

import java.util.ArrayList;
import mod.agus.jcoderz.dx.cf.attrib.AttAnnotationDefault;
import mod.agus.jcoderz.dx.cf.attrib.AttEnclosingMethod;
import mod.agus.jcoderz.dx.cf.attrib.AttExceptions;
import mod.agus.jcoderz.dx.cf.attrib.AttInnerClasses;
import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeInvisibleAnnotations;
import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeInvisibleParameterAnnotations;
import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeVisibleAnnotations;
import mod.agus.jcoderz.dx.cf.attrib.AttRuntimeVisibleParameterAnnotations;
import mod.agus.jcoderz.dx.cf.attrib.AttSignature;
import mod.agus.jcoderz.dx.cf.attrib.InnerClassList;
import mod.agus.jcoderz.dx.cf.direct.DirectClassFile;
import mod.agus.jcoderz.dx.cf.iface.AttributeList;
import mod.agus.jcoderz.dx.cf.iface.Method;
import mod.agus.jcoderz.dx.cf.iface.MethodList;
import mod.agus.jcoderz.dx.dex.file.AnnotationUtils;
import mod.agus.jcoderz.dx.rop.annotation.Annotation;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationVisibility;
import mod.agus.jcoderz.dx.rop.annotation.Annotations;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationsList;
import mod.agus.jcoderz.dx.rop.annotation.NameValuePair;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.Warning;

class AttributeTranslator {
    private AttributeTranslator() {
    }

    public static TypeList getExceptions(Method method) {
        AttExceptions attExceptions = (AttExceptions) method.getAttributes().findFirst(AttExceptions.ATTRIBUTE_NAME);
        if (attExceptions == null) {
            return StdTypeList.EMPTY;
        }
        return attExceptions.getExceptions();
    }

    public static Annotations getAnnotations(AttributeList attributeList) {
        Annotations annotations0 = getAnnotations0(attributeList);
        Annotation signature = getSignature(attributeList);
        if (signature != null) {
            return Annotations.combine(annotations0, signature);
        }
        return annotations0;
    }

    /* JADX WARNING: Removed duplicated region for block: B:8:0x001f  */
    public static Annotations getClassAnnotations(DirectClassFile directClassFile, CfOptions cfOptions) {
        Annotations annotations;
        Annotation translateAnnotationDefaults;
        CstType thisClass = directClassFile.getThisClass();
        AttributeList attributes = directClassFile.getAttributes();
        Annotations annotations2 = getAnnotations(attributes);
        Annotation translateEnclosingMethod = translateEnclosingMethod(attributes);
        try {
            Annotations translateInnerClasses = translateInnerClasses(thisClass, attributes, translateEnclosingMethod == null);
            if (translateInnerClasses != null) {
                annotations = Annotations.combine(annotations2, translateInnerClasses);
                if (translateEnclosingMethod != null) {
                    annotations = Annotations.combine(annotations, translateEnclosingMethod);
                }
                if (!AccessFlags.isAnnotation(directClassFile.getAccessFlags()) && (translateAnnotationDefaults = translateAnnotationDefaults(directClassFile)) != null) {
                    return Annotations.combine(annotations, translateAnnotationDefaults);
                }
            }
        } catch (Warning e) {
            cfOptions.warn.println("warning: " + e.getMessage());
        }
        annotations = annotations2;
        if (translateEnclosingMethod != null) {
        }
        return !AccessFlags.isAnnotation(directClassFile.getAccessFlags()) ? annotations : annotations;
    }

    public static Annotations getMethodAnnotations(Method method) {
        Annotations annotations = getAnnotations(method.getAttributes());
        TypeList exceptions = getExceptions(method);
        if (exceptions.size() != 0) {
            return Annotations.combine(annotations, AnnotationUtils.makeThrows(exceptions));
        }
        return annotations;
    }

    private static Annotations getAnnotations0(AttributeList attributeList) {
        AttRuntimeVisibleAnnotations attRuntimeVisibleAnnotations = (AttRuntimeVisibleAnnotations) attributeList.findFirst(AttRuntimeVisibleAnnotations.ATTRIBUTE_NAME);
        AttRuntimeInvisibleAnnotations attRuntimeInvisibleAnnotations = (AttRuntimeInvisibleAnnotations) attributeList.findFirst(AttRuntimeInvisibleAnnotations.ATTRIBUTE_NAME);
        if (attRuntimeVisibleAnnotations == null) {
            if (attRuntimeInvisibleAnnotations == null) {
                return Annotations.EMPTY;
            }
            return attRuntimeInvisibleAnnotations.getAnnotations();
        } else if (attRuntimeInvisibleAnnotations == null) {
            return attRuntimeVisibleAnnotations.getAnnotations();
        } else {
            return Annotations.combine(attRuntimeVisibleAnnotations.getAnnotations(), attRuntimeInvisibleAnnotations.getAnnotations());
        }
    }

    private static Annotation getSignature(AttributeList attributeList) {
        AttSignature attSignature = (AttSignature) attributeList.findFirst(AttSignature.ATTRIBUTE_NAME);
        if (attSignature == null) {
            return null;
        }
        return AnnotationUtils.makeSignature(attSignature.getSignature());
    }

    private static Annotation translateEnclosingMethod(AttributeList attributeList) {
        AttEnclosingMethod attEnclosingMethod = (AttEnclosingMethod) attributeList.findFirst(AttEnclosingMethod.ATTRIBUTE_NAME);
        if (attEnclosingMethod == null) {
            return null;
        }
        CstType enclosingClass = attEnclosingMethod.getEnclosingClass();
        CstNat method = attEnclosingMethod.getMethod();
        if (method == null) {
            return AnnotationUtils.makeEnclosingClass(enclosingClass);
        }
        return AnnotationUtils.makeEnclosingMethod(new CstMethodRef(enclosingClass, method));
    }

    private static Annotations translateInnerClasses(CstType cstType, AttributeList attributeList, boolean z) {
        AttInnerClasses attInnerClasses = (AttInnerClasses) attributeList.findFirst(AttInnerClasses.ATTRIBUTE_NAME);
        if (attInnerClasses == null) {
            return null;
        }
        InnerClassList innerClasses = attInnerClasses.getInnerClasses();
        int size = innerClasses.size();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        InnerClassList.Item item = null;
        while (i < size) {
            InnerClassList.Item item2 = innerClasses.get(i);
            CstType innerClass = item2.getInnerClass();
            if (!innerClass.equals(cstType)) {
                if (cstType.equals(item2.getOuterClass())) {
                    arrayList.add(innerClass.getClassType());
                }
                item2 = item;
            }
            i++;
            item = item2;
        }
        int size2 = arrayList.size();
        if (item == null && size2 == 0) {
            return null;
        }
        Annotations annotations = new Annotations();
        if (item != null) {
            annotations.add(AnnotationUtils.makeInnerClass(item.getInnerName(), item.getAccessFlags()));
            if (z) {
                if (item.getOuterClass() == null) {
                    throw new Warning("Ignoring InnerClasses attribute for an anonymous inner class\n(" + cstType.toHuman() + ") that doesn't come with an\n" + "associated EnclosingMethod attribute. " + "This class was probably produced by a\n" + "compiler that did not target the modern " + ".class file format. The recommended\n" + "solution is to recompile the class from " + "source, using an up-to-date compiler\n" + "and without specifying any \"-target\" type " + "options. The consequence of ignoring\n" + "this warning is that reflective operations " + "on this class will incorrectly\n" + "indicate that it is *not* an inner class.");
                }
                annotations.add(AnnotationUtils.makeEnclosingClass(item.getOuterClass()));
            }
        }
        if (size2 != 0) {
            StdTypeList stdTypeList = new StdTypeList(size2);
            for (int i2 = 0; i2 < size2; i2++) {
                stdTypeList.set(i2, (Type) arrayList.get(i2));
            }
            stdTypeList.setImmutable();
            annotations.add(AnnotationUtils.makeMemberClasses(stdTypeList));
        }
        annotations.setImmutable();
        return annotations;
    }

    public static AnnotationsList getParameterAnnotations(Method method) {
        AttributeList attributes = method.getAttributes();
        AttRuntimeVisibleParameterAnnotations attRuntimeVisibleParameterAnnotations = (AttRuntimeVisibleParameterAnnotations) attributes.findFirst(AttRuntimeVisibleParameterAnnotations.ATTRIBUTE_NAME);
        AttRuntimeInvisibleParameterAnnotations attRuntimeInvisibleParameterAnnotations = (AttRuntimeInvisibleParameterAnnotations) attributes.findFirst(AttRuntimeInvisibleParameterAnnotations.ATTRIBUTE_NAME);
        if (attRuntimeVisibleParameterAnnotations == null) {
            if (attRuntimeInvisibleParameterAnnotations == null) {
                return AnnotationsList.EMPTY;
            }
            return attRuntimeInvisibleParameterAnnotations.getParameterAnnotations();
        } else if (attRuntimeInvisibleParameterAnnotations == null) {
            return attRuntimeVisibleParameterAnnotations.getParameterAnnotations();
        } else {
            return AnnotationsList.combine(attRuntimeVisibleParameterAnnotations.getParameterAnnotations(), attRuntimeInvisibleParameterAnnotations.getParameterAnnotations());
        }
    }

    private static Annotation translateAnnotationDefaults(DirectClassFile directClassFile) {
        boolean z;
        CstType thisClass = directClassFile.getThisClass();
        MethodList methods = directClassFile.getMethods();
        int size = methods.size();
        Annotation annotation = new Annotation(thisClass, AnnotationVisibility.EMBEDDED);
        int i = 0;
        boolean z2 = false;
        while (i < size) {
            Method method = methods.get(i);
            AttAnnotationDefault attAnnotationDefault = (AttAnnotationDefault) method.getAttributes().findFirst(AttAnnotationDefault.ATTRIBUTE_NAME);
            if (attAnnotationDefault != null) {
                annotation.add(new NameValuePair(method.getNat().getName(), attAnnotationDefault.getValue()));
                z = true;
            } else {
                z = z2;
            }
            i++;
            z2 = z;
        }
        if (!z2) {
            return null;
        }
        annotation.setImmutable();
        return AnnotationUtils.makeAnnotationDefault(annotation);
    }
}
