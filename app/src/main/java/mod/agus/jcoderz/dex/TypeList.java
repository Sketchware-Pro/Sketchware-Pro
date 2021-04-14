package mod.agus.jcoderz.dex;

import mod.agus.jcoderz.dex.util.Unsigned;

public final class TypeList implements Comparable<TypeList> {
    public static final TypeList EMPTY = new TypeList(null, Dex.EMPTY_SHORT_ARRAY);
    private final Dex dex;
    private final short[] types;

    public TypeList(Dex dex2, short[] sArr) {
        this.dex = dex2;
        this.types = sArr;
    }

    public short[] getTypes() {
        return this.types;
    }

    public int compareTo(TypeList typeList) {
        int i = 0;
        while (i < this.types.length && i < typeList.types.length) {
            if (this.types[i] != typeList.types[i]) {
                return Unsigned.compare(this.types[i], typeList.types[i]);
            }
            i++;
        }
        return Unsigned.compare(this.types.length, typeList.types.length);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        int length = this.types.length;
        for (int i = 0; i < length; i++) {
            sb.append(this.dex != null ? this.dex.typeNames().get(this.types[i]) : Short.valueOf(this.types[i]));
        }
        sb.append(")");
        return sb.toString();
    }
}
