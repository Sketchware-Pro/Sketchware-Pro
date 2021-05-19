package mod.agus.jcoderz.dx.rop.cst;

public abstract class CstLiteralBits extends TypedConstant {
    public abstract boolean fitsInInt();

    public abstract int getIntBits();

    public abstract long getLongBits();

    public boolean fitsIn16Bits() {
        if (!fitsInInt()) {
            return false;
        }
        int intBits = getIntBits();
        return ((short) intBits) == intBits;
    }

    public boolean fitsIn8Bits() {
        if (!fitsInInt()) {
            return false;
        }
        int intBits = getIntBits();
        return ((byte) intBits) == intBits;
    }
}
