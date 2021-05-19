package mod.agus.jcoderz.dex;

import mod.agus.jcoderz.dex.util.Unsigned;

public final class MethodId implements Comparable<MethodId> {
    private final int declaringClassIndex;
    private final Dex dex;
    private final int nameIndex;
    private final int protoIndex;

    public MethodId(Dex dex2, int i, int i2, int i3) {
        this.dex = dex2;
        this.declaringClassIndex = i;
        this.protoIndex = i2;
        this.nameIndex = i3;
    }

    public int getDeclaringClassIndex() {
        return this.declaringClassIndex;
    }

    public int getProtoIndex() {
        return this.protoIndex;
    }

    public int getNameIndex() {
        return this.nameIndex;
    }

    public int compareTo(MethodId methodId) {
        if (this.declaringClassIndex != methodId.declaringClassIndex) {
            return Unsigned.compare(this.declaringClassIndex, methodId.declaringClassIndex);
        }
        if (this.nameIndex != methodId.nameIndex) {
            return Unsigned.compare(this.nameIndex, methodId.nameIndex);
        }
        return Unsigned.compare(this.protoIndex, methodId.protoIndex);
    }

    public void writeTo(Dex.Section section) {
        section.writeUnsignedShort(this.declaringClassIndex);
        section.writeUnsignedShort(this.protoIndex);
        section.writeInt(this.nameIndex);
    }

    public String toString() {
        if (this.dex == null) {
            return this.declaringClassIndex + " " + this.protoIndex + " " + this.nameIndex;
        }
        return this.dex.typeNames().get(this.declaringClassIndex) + "." + this.dex.strings().get(this.nameIndex) + this.dex.readTypeList(this.dex.protoIds().get(this.protoIndex).getParametersOffset());
    }
}
