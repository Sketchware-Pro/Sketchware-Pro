package mod.agus.jcoderz.dx.rop.annotation;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstString;

public final class NameValuePair implements Comparable<NameValuePair> {
    private final CstString name;
    private final Constant value;

    public NameValuePair(CstString cstString, Constant constant) {
        if (cstString == null) {
            throw new NullPointerException("name == null");
        } else if (constant == null) {
            throw new NullPointerException("value == null");
        } else {
            this.name = cstString;
            this.value = constant;
        }
    }

    public String toString() {
        return String.valueOf(this.name.toHuman()) + ":" + this.value;
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + this.value.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof NameValuePair)) {
            return false;
        }
        NameValuePair nameValuePair = (NameValuePair) obj;
        if (!this.name.equals(nameValuePair.name) || !this.value.equals(nameValuePair.value)) {
            return false;
        }
        return true;
    }

    public int compareTo(NameValuePair nameValuePair) {
        int compareTo = this.name.compareTo((Constant) nameValuePair.name);
        return compareTo != 0 ? compareTo : this.value.compareTo(nameValuePair.value);
    }

    public CstString getName() {
        return this.name;
    }

    public Constant getValue() {
        return this.value;
    }
}
