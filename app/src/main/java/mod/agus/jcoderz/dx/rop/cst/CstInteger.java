package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Hex;

public final class CstInteger extends CstLiteral32 {
    private static final CstInteger[] cache = new CstInteger[511];
    public static final CstInteger VALUE_0 = make(0);
    public static final CstInteger VALUE_1 = make(1);
    public static final CstInteger VALUE_2 = make(2);
    public static final CstInteger VALUE_3 = make(3);
    public static final CstInteger VALUE_4 = make(4);
    public static final CstInteger VALUE_5 = make(5);
    public static final CstInteger VALUE_M1 = make(-1);

    private CstInteger(int i) {
        super(i);
    }

    public static CstInteger make(int i) {
        int length = (Integer.MAX_VALUE & i) % cache.length;
        CstInteger cstInteger = cache[length];
        if (cstInteger != null && cstInteger.getValue() == i) {
            return cstInteger;
        }
        CstInteger cstInteger2 = new CstInteger(i);
        cache[length] = cstInteger2;
        return cstInteger2;
    }

    public String toString() {
        int intBits = getIntBits();
        return "int{0x" + Hex.u4(intBits) + " / " + intBits + '}';
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public Type getType() {
        return Type.INT;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public String typeName() {
        return "int";
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return Integer.toString(getIntBits());
    }

    public int getValue() {
        return getIntBits();
    }
}
