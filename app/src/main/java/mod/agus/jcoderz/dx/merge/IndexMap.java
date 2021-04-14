package mod.agus.jcoderz.dx.merge;

import java.util.HashMap;
import mod.agus.jcoderz.dex.Annotation;
import mod.agus.jcoderz.dex.ClassDef;
import mod.agus.jcoderz.dex.Dex;
import mod.agus.jcoderz.dex.DexException;
import mod.agus.jcoderz.dex.EncodedValue;
import mod.agus.jcoderz.dex.EncodedValueCodec;
import mod.agus.jcoderz.dex.EncodedValueReader;
import mod.agus.jcoderz.dex.FieldId;
import mod.agus.jcoderz.dex.Leb128;
import mod.agus.jcoderz.dex.MethodId;
import mod.agus.jcoderz.dex.ProtoId;
import mod.agus.jcoderz.dex.TableOfContents;
import mod.agus.jcoderz.dex.TypeList;
import mod.agus.jcoderz.dex.util.ByteOutput;
import mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput;

public final class IndexMap {
    private final HashMap<Integer, Integer> annotationDirectoryOffsets = new HashMap<>();
    private final HashMap<Integer, Integer> annotationOffsets = new HashMap<>();
    private final HashMap<Integer, Integer> annotationSetOffsets = new HashMap<>();
    private final HashMap<Integer, Integer> annotationSetRefListOffsets = new HashMap<>();
    public final short[] fieldIds;
    public final short[] methodIds;
    public final short[] protoIds;
    private final HashMap<Integer, Integer> staticValuesOffsets = new HashMap<>();
    public final int[] stringIds;
    private final Dex target;
    public final short[] typeIds;
    private final HashMap<Integer, Integer> typeListOffsets = new HashMap<>();

    public IndexMap(Dex dex, TableOfContents tableOfContents) {
        this.target = dex;
        this.stringIds = new int[tableOfContents.stringIds.size];
        this.typeIds = new short[tableOfContents.typeIds.size];
        this.protoIds = new short[tableOfContents.protoIds.size];
        this.fieldIds = new short[tableOfContents.fieldIds.size];
        this.methodIds = new short[tableOfContents.methodIds.size];
        this.typeListOffsets.put(0, 0);
        this.annotationSetOffsets.put(0, 0);
        this.annotationDirectoryOffsets.put(0, 0);
        this.staticValuesOffsets.put(0, 0);
    }

    public void putTypeListOffset(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        }
        this.typeListOffsets.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void putAnnotationOffset(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        }
        this.annotationOffsets.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void putAnnotationSetOffset(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        }
        this.annotationSetOffsets.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void putAnnotationSetRefListOffset(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        }
        this.annotationSetRefListOffsets.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void putAnnotationDirectoryOffset(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        }
        this.annotationDirectoryOffsets.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public void putStaticValuesOffset(int i, int i2) {
        if (i <= 0 || i2 <= 0) {
            throw new IllegalArgumentException();
        }
        this.staticValuesOffsets.put(Integer.valueOf(i), Integer.valueOf(i2));
    }

    public int adjustString(int i) {
        if (i == -1) {
            return -1;
        }
        return this.stringIds[i];
    }

    public int adjustType(int i) {
        if (i == -1) {
            return -1;
        }
        return this.typeIds[i] & 65535;
    }

    public TypeList adjustTypeList(TypeList typeList) {
        if (typeList == TypeList.EMPTY) {
            return typeList;
        }
        short[] sArr = (short[]) typeList.getTypes().clone();
        for (int i = 0; i < sArr.length; i++) {
            sArr[i] = (short) adjustType(sArr[i]);
        }
        return new TypeList(this.target, sArr);
    }

    public int adjustProto(int i) {
        return this.protoIds[i] & 65535;
    }

    public int adjustField(int i) {
        return this.fieldIds[i] & 65535;
    }

    public int adjustMethod(int i) {
        return this.methodIds[i] & 65535;
    }

    public int adjustTypeListOffset(int i) {
        return this.typeListOffsets.get(Integer.valueOf(i)).intValue();
    }

    public int adjustAnnotation(int i) {
        return this.annotationOffsets.get(Integer.valueOf(i)).intValue();
    }

    public int adjustAnnotationSet(int i) {
        return this.annotationSetOffsets.get(Integer.valueOf(i)).intValue();
    }

    public int adjustAnnotationSetRefList(int i) {
        return this.annotationSetRefListOffsets.get(Integer.valueOf(i)).intValue();
    }

    public int adjustAnnotationDirectory(int i) {
        return this.annotationDirectoryOffsets.get(Integer.valueOf(i)).intValue();
    }

    public int adjustStaticValues(int i) {
        return this.staticValuesOffsets.get(Integer.valueOf(i)).intValue();
    }

    public MethodId adjust(MethodId methodId) {
        return new MethodId(this.target, adjustType(methodId.getDeclaringClassIndex()), adjustProto(methodId.getProtoIndex()), adjustString(methodId.getNameIndex()));
    }

    public FieldId adjust(FieldId fieldId) {
        return new FieldId(this.target, adjustType(fieldId.getDeclaringClassIndex()), adjustType(fieldId.getTypeIndex()), adjustString(fieldId.getNameIndex()));
    }

    public ProtoId adjust(ProtoId protoId) {
        return new ProtoId(this.target, adjustString(protoId.getShortyIndex()), adjustType(protoId.getReturnTypeIndex()), adjustTypeListOffset(protoId.getParametersOffset()));
    }

    public ClassDef adjust(ClassDef classDef) {
        return new ClassDef(this.target, classDef.getOffset(), adjustType(classDef.getTypeIndex()), classDef.getAccessFlags(), adjustType(classDef.getSupertypeIndex()), adjustTypeListOffset(classDef.getInterfacesOffset()), classDef.getSourceFileIndex(), classDef.getAnnotationsOffset(), classDef.getClassDataOffset(), classDef.getStaticValuesOffset());
    }

    public SortableType adjust(SortableType sortableType) {
        return new SortableType(sortableType.getDex(), sortableType.getIndexMap(), adjust(sortableType.getClassDef()));
    }

    public EncodedValue adjustEncodedValue(EncodedValue encodedValue) {
        ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput(32);
        new EncodedValueTransformer(byteArrayAnnotatedOutput).transform(new EncodedValueReader(encodedValue));
        return new EncodedValue(byteArrayAnnotatedOutput.toByteArray());
    }

    public EncodedValue adjustEncodedArray(EncodedValue encodedValue) {
        ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput(32);
        new EncodedValueTransformer(byteArrayAnnotatedOutput).transformArray(new EncodedValueReader(encodedValue, 28));
        return new EncodedValue(byteArrayAnnotatedOutput.toByteArray());
    }

    public Annotation adjust(Annotation annotation) {
        ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput(32);
        new EncodedValueTransformer(byteArrayAnnotatedOutput).transformAnnotation(annotation.getReader());
        return new Annotation(this.target, annotation.getVisibility(), new EncodedValue(byteArrayAnnotatedOutput.toByteArray()));
    }

    private final class EncodedValueTransformer {
        private final ByteOutput out;

        public EncodedValueTransformer(ByteOutput byteOutput) {
            this.out = byteOutput;
        }

        public void transform(EncodedValueReader encodedValueReader) {
            int i = 0;
            switch (encodedValueReader.peek()) {
                case 0:
                    EncodedValueCodec.writeSignedIntegralValue(this.out, 0, (long) encodedValueReader.readByte());
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
                    throw new DexException("Unexpected type: " + Integer.toHexString(encodedValueReader.peek()));
                case 2:
                    EncodedValueCodec.writeSignedIntegralValue(this.out, 2, (long) encodedValueReader.readShort());
                    return;
                case 3:
                    EncodedValueCodec.writeUnsignedIntegralValue(this.out, 3, (long) encodedValueReader.readChar());
                    return;
                case 4:
                    EncodedValueCodec.writeSignedIntegralValue(this.out, 4, (long) encodedValueReader.readInt());
                    return;
                case 6:
                    EncodedValueCodec.writeSignedIntegralValue(this.out, 6, encodedValueReader.readLong());
                    return;
                case 16:
                    EncodedValueCodec.writeRightZeroExtendedValue(this.out, 16, ((long) Float.floatToIntBits(encodedValueReader.readFloat())) << 32);
                    return;
                case 17:
                    EncodedValueCodec.writeRightZeroExtendedValue(this.out, 17, Double.doubleToLongBits(encodedValueReader.readDouble()));
                    return;
                case 23:
                    EncodedValueCodec.writeUnsignedIntegralValue(this.out, 23, (long) IndexMap.this.adjustString(encodedValueReader.readString()));
                    return;
                case 24:
                    EncodedValueCodec.writeUnsignedIntegralValue(this.out, 24, (long) IndexMap.this.adjustType(encodedValueReader.readType()));
                    return;
                case 25:
                    EncodedValueCodec.writeUnsignedIntegralValue(this.out, 25, (long) IndexMap.this.adjustField(encodedValueReader.readField()));
                    return;
                case 26:
                    EncodedValueCodec.writeUnsignedIntegralValue(this.out, 26, (long) IndexMap.this.adjustMethod(encodedValueReader.readMethod()));
                    return;
                case 27:
                    EncodedValueCodec.writeUnsignedIntegralValue(this.out, 27, (long) IndexMap.this.adjustField(encodedValueReader.readEnum()));
                    return;
                case 28:
                    writeTypeAndArg(28, 0);
                    transformArray(encodedValueReader);
                    return;
                case 29:
                    writeTypeAndArg(29, 0);
                    transformAnnotation(encodedValueReader);
                    return;
                case 30:
                    encodedValueReader.readNull();
                    writeTypeAndArg(30, 0);
                    return;
                case 31:
                    if (encodedValueReader.readBoolean()) {
                        i = 1;
                    }
                    writeTypeAndArg(31, i);
                    return;
            }
        }

        public void transformAnnotation(EncodedValueReader encodedValueReader) {
            int readAnnotation = encodedValueReader.readAnnotation();
            Leb128.writeUnsignedLeb128(this.out, IndexMap.this.adjustType(encodedValueReader.getAnnotationType()));
            Leb128.writeUnsignedLeb128(this.out, readAnnotation);
            for (int i = 0; i < readAnnotation; i++) {
                Leb128.writeUnsignedLeb128(this.out, IndexMap.this.adjustString(encodedValueReader.readAnnotationName()));
                transform(encodedValueReader);
            }
        }

        public void transformArray(EncodedValueReader encodedValueReader) {
            int readArray = encodedValueReader.readArray();
            Leb128.writeUnsignedLeb128(this.out, readArray);
            for (int i = 0; i < readArray; i++) {
                transform(encodedValueReader);
            }
        }

        private void writeTypeAndArg(int i, int i2) {
            this.out.writeByte((i2 << 5) | i);
        }
    }
}
