package mod.agus.jcoderz.dex;

public final class ClassDef {
    public static final int NO_INDEX = -1;
    private final int accessFlags;
    private final int annotationsOffset;
    private final Dex buffer;
    private final int classDataOffset;
    private final int interfacesOffset;
    private final int offset;
    private final int sourceFileIndex;
    private final int staticValuesOffset;
    private final int supertypeIndex;
    private final int typeIndex;

    public ClassDef(Dex dex, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        this.buffer = dex;
        this.offset = i;
        this.typeIndex = i2;
        this.accessFlags = i3;
        this.supertypeIndex = i4;
        this.interfacesOffset = i5;
        this.sourceFileIndex = i6;
        this.annotationsOffset = i7;
        this.classDataOffset = i8;
        this.staticValuesOffset = i9;
    }

    public int getOffset() {
        return this.offset;
    }

    public int getTypeIndex() {
        return this.typeIndex;
    }

    public int getSupertypeIndex() {
        return this.supertypeIndex;
    }

    public int getInterfacesOffset() {
        return this.interfacesOffset;
    }

    public short[] getInterfaces() {
        return this.buffer.readTypeList(this.interfacesOffset).getTypes();
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public int getSourceFileIndex() {
        return this.sourceFileIndex;
    }

    public int getAnnotationsOffset() {
        return this.annotationsOffset;
    }

    public int getClassDataOffset() {
        return this.classDataOffset;
    }

    public int getStaticValuesOffset() {
        return this.staticValuesOffset;
    }

    public String toString() {
        if (this.buffer == null) {
            return this.typeIndex + " " + this.supertypeIndex;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.buffer.typeNames().get(this.typeIndex));
        if (this.supertypeIndex != -1) {
            sb.append(" extends ").append(this.buffer.typeNames().get(this.supertypeIndex));
        }
        return sb.toString();
    }
}
