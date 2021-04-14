package mod.agus.jcoderz.dex;

import mod.agus.jcoderz.dex.Dex;
import mod.agus.jcoderz.dex.util.Unsigned;

public final class FieldId implements Comparable<FieldId> {
    private final int declaringClassIndex;
    private final Dex dex;
    private final int nameIndex;
    private final int typeIndex;

    public FieldId(Dex dex2, int i, int i2, int i3) {
        this.dex = dex2;
        this.declaringClassIndex = i;
        this.typeIndex = i2;
        this.nameIndex = i3;
    }

    public int getDeclaringClassIndex() {
        return this.declaringClassIndex;
    }

    public int getTypeIndex() {
        return this.typeIndex;
    }

    public int getNameIndex() {
        return this.nameIndex;
    }

    public int compareTo(FieldId fieldId) {
        if (this.declaringClassIndex != fieldId.declaringClassIndex) {
            return Unsigned.compare(this.declaringClassIndex, fieldId.declaringClassIndex);
        }
        if (this.nameIndex != fieldId.nameIndex) {
            return Unsigned.compare(this.nameIndex, fieldId.nameIndex);
        }
        return Unsigned.compare(this.typeIndex, fieldId.typeIndex);
    }

    public void writeTo(Dex.Section section) {
        section.writeUnsignedShort(this.declaringClassIndex);
        section.writeUnsignedShort(this.typeIndex);
        section.writeInt(this.nameIndex);
    }

    public String toString() {
        if (this.dex == null) {
            return String.valueOf(this.declaringClassIndex) + " " + this.typeIndex + " " + this.nameIndex;
        }
        return String.valueOf(this.dex.typeNames().get(this.typeIndex)) + "." + this.dex.strings().get(this.nameIndex);
    }
}
