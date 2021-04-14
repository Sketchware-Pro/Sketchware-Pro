package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;

public final class CstBoolean extends CstLiteral32 {
    public static final CstBoolean VALUE_FALSE = new CstBoolean(false);
    public static final CstBoolean VALUE_TRUE = new CstBoolean(true);

    public static CstBoolean make(boolean z) {
        return z ? VALUE_TRUE : VALUE_FALSE;
    }

    public static CstBoolean make(int i) {
        if (i == 0) {
            return VALUE_FALSE;
        }
        if (i == 1) {
            return VALUE_TRUE;
        }
        throw new IllegalArgumentException("bogus value: " + i);
    }

    private CstBoolean(boolean z) {
        super(z ? 1 : 0);
    }

    public String toString() {
        return getValue() ? "boolean{true}" : "boolean{false}";
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public Type getType() {
        return Type.BOOLEAN;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public String typeName() {
        return "boolean";
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return getValue() ? "true" : "false";
    }

    public boolean getValue() {
        return getIntBits() != 0;
    }
}
