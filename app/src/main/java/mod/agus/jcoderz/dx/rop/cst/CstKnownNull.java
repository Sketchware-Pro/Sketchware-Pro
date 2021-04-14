package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;

public final class CstKnownNull extends CstLiteralBits {
    public static final CstKnownNull THE_ONE = new CstKnownNull();

    private CstKnownNull() {
    }

    public boolean equals(Object obj) {
        return obj instanceof CstKnownNull;
    }

    public int hashCode() {
        return 1147565434;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public int compareTo0(Constant constant) {
        return 0;
    }

    public String toString() {
        return "known-null";
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public Type getType() {
        return Type.KNOWN_NULL;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public String typeName() {
        return "known-null";
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public boolean isCategory2() {
        return false;
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return "null";
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.CstLiteralBits
    public boolean fitsInInt() {
        return true;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.CstLiteralBits
    public int getIntBits() {
        return 0;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.CstLiteralBits
    public long getLongBits() {
        return 0;
    }
}
