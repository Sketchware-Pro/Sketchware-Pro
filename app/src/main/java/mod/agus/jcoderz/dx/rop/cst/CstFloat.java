package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Hex;

public final class CstFloat extends CstLiteral32 {
    public static final CstFloat VALUE_0 = make(Float.floatToIntBits(0.0f));
    public static final CstFloat VALUE_1 = make(Float.floatToIntBits(1.0f));
    public static final CstFloat VALUE_2 = make(Float.floatToIntBits(2.0f));

    public static CstFloat make(int i) {
        return new CstFloat(i);
    }

    private CstFloat(int i) {
        super(i);
    }

    public String toString() {
        int intBits = getIntBits();
        return "float{0x" + Hex.u4(intBits) + " / " + Float.intBitsToFloat(intBits) + '}';
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public Type getType() {
        return Type.FLOAT;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public String typeName() {
        return "float";
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return Float.toString(Float.intBitsToFloat(getIntBits()));
    }

    public float getValue() {
        return Float.intBitsToFloat(getIntBits());
    }
}
