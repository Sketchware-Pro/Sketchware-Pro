package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;
import mod.agus.jcoderz.dex.EncodedValueCodec;
import mod.agus.jcoderz.dx.rop.annotation.Annotation;
import mod.agus.jcoderz.dx.rop.annotation.NameValuePair;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstAnnotation;
import mod.agus.jcoderz.dx.rop.cst.CstArray;
import mod.agus.jcoderz.dx.rop.cst.CstBoolean;
import mod.agus.jcoderz.dx.rop.cst.CstByte;
import mod.agus.jcoderz.dx.rop.cst.CstChar;
import mod.agus.jcoderz.dx.rop.cst.CstDouble;
import mod.agus.jcoderz.dx.rop.cst.CstEnumRef;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstFloat;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstKnownNull;
import mod.agus.jcoderz.dx.rop.cst.CstLiteralBits;
import mod.agus.jcoderz.dx.rop.cst.CstLong;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstShort;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class ValueEncoder {
    private static final int VALUE_ANNOTATION = 29;
    private static final int VALUE_ARRAY = 28;
    private static final int VALUE_BOOLEAN = 31;
    private static final int VALUE_BYTE = 0;
    private static final int VALUE_CHAR = 3;
    private static final int VALUE_DOUBLE = 17;
    private static final int VALUE_ENUM = 27;
    private static final int VALUE_FIELD = 25;
    private static final int VALUE_FLOAT = 16;
    private static final int VALUE_INT = 4;
    private static final int VALUE_LONG = 6;
    private static final int VALUE_METHOD = 26;
    private static final int VALUE_NULL = 30;
    private static final int VALUE_SHORT = 2;
    private static final int VALUE_STRING = 23;
    private static final int VALUE_TYPE = 24;
    private final DexFile file;
    private final AnnotatedOutput out;

    public ValueEncoder(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        if (dexFile == null) {
            throw new NullPointerException("file == null");
        } else if (annotatedOutput == null) {
            throw new NullPointerException("out == null");
        } else {
            this.file = dexFile;
            this.out = annotatedOutput;
        }
    }

    public void writeConstant(Constant constant) {
        int constantToValueType = constantToValueType(constant);
        switch (constantToValueType) {
            case 0:
            case 2:
            case 4:
            case 6:
                EncodedValueCodec.writeSignedIntegralValue(this.out, constantToValueType, ((CstLiteralBits) constant).getLongBits());
                return;
            case 1:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            default:
                throw new RuntimeException("Shouldn't happen");
            case 3:
                EncodedValueCodec.writeUnsignedIntegralValue(this.out, constantToValueType, ((CstLiteralBits) constant).getLongBits());
                return;
            case 16:
                EncodedValueCodec.writeRightZeroExtendedValue(this.out, constantToValueType, ((CstFloat) constant).getLongBits() << 32);
                return;
            case 17:
                EncodedValueCodec.writeRightZeroExtendedValue(this.out, constantToValueType, ((CstDouble) constant).getLongBits());
                return;
            case 23:
                EncodedValueCodec.writeUnsignedIntegralValue(this.out, constantToValueType, (long) this.file.getStringIds().indexOf((CstString) constant));
                return;
            case 24:
                EncodedValueCodec.writeUnsignedIntegralValue(this.out, constantToValueType, (long) this.file.getTypeIds().indexOf((CstType) constant));
                return;
            case 25:
                EncodedValueCodec.writeUnsignedIntegralValue(this.out, constantToValueType, (long) this.file.getFieldIds().indexOf((CstFieldRef) constant));
                return;
            case 26:
                EncodedValueCodec.writeUnsignedIntegralValue(this.out, constantToValueType, (long) this.file.getMethodIds().indexOf((CstMethodRef) constant));
                return;
            case 27:
                EncodedValueCodec.writeUnsignedIntegralValue(this.out, constantToValueType, (long) this.file.getFieldIds().indexOf(((CstEnumRef) constant).getFieldRef()));
                return;
            case 28:
                this.out.writeByte(constantToValueType);
                writeArray((CstArray) constant, false);
                return;
            case 29:
                this.out.writeByte(constantToValueType);
                writeAnnotation(((CstAnnotation) constant).getAnnotation(), false);
                return;
            case 30:
                this.out.writeByte(constantToValueType);
                return;
            case 31:
                this.out.writeByte(constantToValueType | (((CstBoolean) constant).getIntBits() << 5));
                return;
        }
    }

    private static int constantToValueType(Constant constant) {
        if (constant instanceof CstByte) {
            return 0;
        }
        if (constant instanceof CstShort) {
            return 2;
        }
        if (constant instanceof CstChar) {
            return 3;
        }
        if (constant instanceof CstInteger) {
            return 4;
        }
        if (constant instanceof CstLong) {
            return 6;
        }
        if (constant instanceof CstFloat) {
            return 16;
        }
        if (constant instanceof CstDouble) {
            return 17;
        }
        if (constant instanceof CstString) {
            return 23;
        }
        if (constant instanceof CstType) {
            return 24;
        }
        if (constant instanceof CstFieldRef) {
            return 25;
        }
        if (constant instanceof CstMethodRef) {
            return 26;
        }
        if (constant instanceof CstEnumRef) {
            return 27;
        }
        if (constant instanceof CstArray) {
            return 28;
        }
        if (constant instanceof CstAnnotation) {
            return 29;
        }
        if (constant instanceof CstKnownNull) {
            return 30;
        }
        if (constant instanceof CstBoolean) {
            return 31;
        }
        throw new RuntimeException("Shouldn't happen");
    }

    public void writeArray(CstArray cstArray, boolean z) {
        boolean z2 = z && this.out.annotates();
        CstArray.List list = cstArray.getList();
        int size = list.size();
        if (z2) {
            this.out.annotate("  size: " + Hex.u4(size));
        }
        this.out.writeUleb128(size);
        for (int i = 0; i < size; i++) {
            Constant constant = list.get(i);
            if (z2) {
                this.out.annotate("  [" + Integer.toHexString(i) + "] " + constantToHuman(constant));
            }
            writeConstant(constant);
        }
        if (z2) {
            this.out.endAnnotation();
        }
    }

    public void writeAnnotation(Annotation annotation, boolean z) {
        int i;
        boolean z2 = z && this.out.annotates();
        StringIdsSection stringIds = this.file.getStringIds();
        TypeIdsSection typeIds = this.file.getTypeIds();
        CstType type = annotation.getType();
        int indexOf = typeIds.indexOf(type);
        if (z2) {
            this.out.annotate("  type_idx: " + Hex.u4(indexOf) + " // " + type.toHuman());
        }
        this.out.writeUleb128(typeIds.indexOf(annotation.getType()));
        Collection<NameValuePair> nameValuePairs = annotation.getNameValuePairs();
        int size = nameValuePairs.size();
        if (z2) {
            this.out.annotate("  size: " + Hex.u4(size));
        }
        this.out.writeUleb128(size);
        int i2 = 0;
        for (NameValuePair nameValuePair : nameValuePairs) {
            CstString name = nameValuePair.getName();
            int indexOf2 = stringIds.indexOf(name);
            Constant value = nameValuePair.getValue();
            if (z2) {
                this.out.annotate(0, "  elements[" + i2 + "]:");
                i = i2 + 1;
                this.out.annotate("    name_idx: " + Hex.u4(indexOf2) + " // " + name.toHuman());
            } else {
                i = i2;
            }
            this.out.writeUleb128(indexOf2);
            if (z2) {
                this.out.annotate("    value: " + constantToHuman(value));
            }
            writeConstant(value);
            i2 = i;
        }
        if (z2) {
            this.out.endAnnotation();
        }
    }

    public static String constantToHuman(Constant constant) {
        if (constantToValueType(constant) == 30) {
            return "null";
        }
        return constant.typeName() + ' ' + constant.toHuman();
    }

    public static void addContents(DexFile dexFile, Annotation annotation) {
        TypeIdsSection typeIds = dexFile.getTypeIds();
        StringIdsSection stringIds = dexFile.getStringIds();
        typeIds.intern(annotation.getType());
        for (NameValuePair nameValuePair : annotation.getNameValuePairs()) {
            stringIds.intern(nameValuePair.getName());
            addContents(dexFile, nameValuePair.getValue());
        }
    }

    public static void addContents(DexFile dexFile, Constant constant) {
        if (constant instanceof CstAnnotation) {
            addContents(dexFile, ((CstAnnotation) constant).getAnnotation());
        } else if (constant instanceof CstArray) {
            CstArray.List list = ((CstArray) constant).getList();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                addContents(dexFile, list.get(i));
            }
        } else {
            dexFile.internIfAppropriate(constant);
        }
    }
}
