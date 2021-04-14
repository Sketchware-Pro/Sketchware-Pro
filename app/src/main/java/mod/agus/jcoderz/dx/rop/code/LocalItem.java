package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstString;

public class LocalItem implements Comparable<LocalItem> {
    private final CstString name;
    private final CstString signature;

    public static LocalItem make(CstString cstString, CstString cstString2) {
        if (cstString == null && cstString2 == null) {
            return null;
        }
        return new LocalItem(cstString, cstString2);
    }

    private LocalItem(CstString cstString, CstString cstString2) {
        this.name = cstString;
        this.signature = cstString2;
    }

    public boolean equals(Object obj) {
        if ((obj instanceof LocalItem) && compareTo((LocalItem) obj) == 0) {
            return true;
        }
        return false;
    }

    private static int compareHandlesNulls(CstString cstString, CstString cstString2) {
        if (cstString == cstString2) {
            return 0;
        }
        if (cstString == null) {
            return -1;
        }
        if (cstString2 == null) {
            return 1;
        }
        return cstString.compareTo((Constant) cstString2);
    }

    public int compareTo(LocalItem localItem) {
        int compareHandlesNulls = compareHandlesNulls(this.name, localItem.name);
        if (compareHandlesNulls != 0) {
            return compareHandlesNulls;
        }
        return compareHandlesNulls(this.signature, localItem.signature);
    }

    public int hashCode() {
        int i = 0;
        int hashCode = (this.name == null ? 0 : this.name.hashCode()) * 31;
        if (this.signature != null) {
            i = this.signature.hashCode();
        }
        return hashCode + i;
    }

    public String toString() {
        if (this.name != null && this.signature == null) {
            return this.name.toQuoted();
        }
        if (this.name == null && this.signature == null) {
            return "";
        }
        return "[" + (this.name == null ? "" : this.name.toQuoted()) + "|" + (this.signature == null ? "" : this.signature.toQuoted());
    }

    public CstString getName() {
        return this.name;
    }

    public CstString getSignature() {
        return this.signature;
    }
}
