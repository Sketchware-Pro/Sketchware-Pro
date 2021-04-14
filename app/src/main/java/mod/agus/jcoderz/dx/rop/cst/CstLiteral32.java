package mod.agus.jcoderz.dx.rop.cst;

public abstract class CstLiteral32 extends CstLiteralBits {
    private final int bits;

    CstLiteral32(int i) {
        this.bits = i;
    }

    public final boolean equals(Object obj) {
        return obj != null && getClass() == obj.getClass() && this.bits == ((CstLiteral32) obj).bits;
    }

    public final int hashCode() {
        return this.bits;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public int compareTo0(Constant constant) {
        int i = ((CstLiteral32) constant).bits;
        if (this.bits < i) {
            return -1;
        }
        if (this.bits > i) {
            return 1;
        }
        return 0;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public final boolean isCategory2() {
        return false;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.CstLiteralBits
    public final boolean fitsInInt() {
        return true;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.CstLiteralBits
    public final int getIntBits() {
        return this.bits;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.CstLiteralBits
    public final long getLongBits() {
        return (long) this.bits;
    }
}
