package mod.agus.jcoderz.dx.dex.file;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.annotation.Annotation;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationVisibility;
import mod.agus.jcoderz.dx.rop.annotation.NameValuePair;
import mod.agus.jcoderz.dx.rop.cst.CstAnnotation;
import mod.agus.jcoderz.dx.rop.cst.CstArray;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstKnownNull;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.cst.TypedConstant;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;

public final class AnnotationUtils {
    private static final CstString ACCESS_FLAGS_STRING = new CstString("accessFlags");
    private static final CstType ANNOTATION_DEFAULT_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/AnnotationDefault;"));
    private static final CstType ENCLOSING_CLASS_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/EnclosingClass;"));
    private static final CstType ENCLOSING_METHOD_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/EnclosingMethod;"));
    private static final CstType INNER_CLASS_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/InnerClass;"));
    private static final CstType MEMBER_CLASSES_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/MemberClasses;"));
    private static final CstString NAME_STRING = new CstString("name");
    private static final CstType SIGNATURE_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/Signature;"));
    private static final CstType THROWS_TYPE = CstType.intern(Type.intern("Ldalvik/annotation/Throws;"));
    private static final CstString VALUE_STRING = new CstString("value");

    private AnnotationUtils() {
    }

    public static Annotation makeAnnotationDefault(Annotation annotation) {
        Annotation annotation2 = new Annotation(ANNOTATION_DEFAULT_TYPE, AnnotationVisibility.SYSTEM);
        annotation2.put(new NameValuePair(VALUE_STRING, new CstAnnotation(annotation)));
        annotation2.setImmutable();
        return annotation2;
    }

    public static Annotation makeEnclosingClass(CstType cstType) {
        Annotation annotation = new Annotation(ENCLOSING_CLASS_TYPE, AnnotationVisibility.SYSTEM);
        annotation.put(new NameValuePair(VALUE_STRING, cstType));
        annotation.setImmutable();
        return annotation;
    }

    public static Annotation makeEnclosingMethod(CstMethodRef cstMethodRef) {
        Annotation annotation = new Annotation(ENCLOSING_METHOD_TYPE, AnnotationVisibility.SYSTEM);
        annotation.put(new NameValuePair(VALUE_STRING, cstMethodRef));
        annotation.setImmutable();
        return annotation;
    }

    public static Annotation makeInnerClass(CstString cstString, int i) {
        Annotation annotation = new Annotation(INNER_CLASS_TYPE, AnnotationVisibility.SYSTEM);
        TypedConstant typedConstant = cstString;
        if (cstString == null) {
            typedConstant = CstKnownNull.THE_ONE;
        }
        annotation.put(new NameValuePair(NAME_STRING, typedConstant));
        annotation.put(new NameValuePair(ACCESS_FLAGS_STRING, CstInteger.make(i)));
        annotation.setImmutable();
        return annotation;
    }

    public static Annotation makeMemberClasses(TypeList typeList) {
        CstArray makeCstArray = makeCstArray(typeList);
        Annotation annotation = new Annotation(MEMBER_CLASSES_TYPE, AnnotationVisibility.SYSTEM);
        annotation.put(new NameValuePair(VALUE_STRING, makeCstArray));
        annotation.setImmutable();
        return annotation;
    }

    public static Annotation makeSignature(CstString cstString) {
        Annotation annotation = new Annotation(SIGNATURE_TYPE, AnnotationVisibility.SYSTEM);
        String string = cstString.getString();
        int length = string.length();
        ArrayList arrayList = new ArrayList(20);
        int i = 0;
        while (i < length) {
            int i2 = i + 1;
            if (string.charAt(i) == 'L') {
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    char charAt = string.charAt(i2);
                    if (charAt == ';') {
                        i2++;
                        break;
                    }
                    if (charAt == '<') {
                        break;
                    }
                    i2++;
                }
            } else {
                while (i2 < length && string.charAt(i2) != 'L') {
                    i2++;
                }
            }
            arrayList.add(string.substring(i, i2));
            i = i2;
        }
        int size = arrayList.size();
        CstArray.List list = new CstArray.List(size);
        for (int i3 = 0; i3 < size; i3++) {
            list.set(i3, new CstString((String) arrayList.get(i3)));
        }
        list.setImmutable();
        annotation.put(new NameValuePair(VALUE_STRING, new CstArray(list)));
        annotation.setImmutable();
        return annotation;
    }

    public static Annotation makeThrows(TypeList typeList) {
        CstArray makeCstArray = makeCstArray(typeList);
        Annotation annotation = new Annotation(THROWS_TYPE, AnnotationVisibility.SYSTEM);
        annotation.put(new NameValuePair(VALUE_STRING, makeCstArray));
        annotation.setImmutable();
        return annotation;
    }

    private static CstArray makeCstArray(TypeList typeList) {
        int size = typeList.size();
        CstArray.List list = new CstArray.List(size);
        for (int i = 0; i < size; i++) {
            list.set(i, CstType.intern(typeList.getType(i)));
        }
        list.setImmutable();
        return new CstArray(list);
    }
}
