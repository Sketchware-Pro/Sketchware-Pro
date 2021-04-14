package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Hex;

public final class CstDouble extends CstLiteral64 {
    public static final CstDouble VALUE_0 = new CstDouble(Double.doubleToLongBits(0.0d));
    public static final CstDouble VALUE_1 = new CstDouble(Double.doubleToLongBits(1.0d));

    public static CstDouble make(long j) {
        return new CstDouble(j);
    }

    private CstDouble(long j) {
        super(j);
    }

    public String toString() {
        long longBits = getLongBits();
        return "double{0x" + Hex.u8(longBits) + " / " + Double.longBitsToDouble(longBits) + '}';
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public Type getType() {
        return Type.DOUBLE;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public String typeName() {
        return "double";
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return Double.toString(Double.longBitsToDouble(getLongBits()));
    }

    public double getValue() {
        return Double.longBitsToDouble(getLongBits());
    }
}
