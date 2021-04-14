package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;

public final class CstFieldRef extends CstMemberRef {
    public static CstFieldRef forPrimitiveType(Type type) {
        return new CstFieldRef(CstType.forBoxedPrimitiveType(type), CstNat.PRIMITIVE_TYPE_NAT);
    }

    public CstFieldRef(CstType cstType, CstNat cstNat) {
        super(cstType, cstNat);
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public String typeName() {
        return "field";
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public Type getType() {
        return getNat().getFieldType();
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.CstMemberRef, mod.agus.jcoderz.dx.rop.cst.Constant
    public int compareTo0(Constant constant) {
        int compareTo0 = super.compareTo0(constant);
        return compareTo0 != 0 ? compareTo0 : getNat().getDescriptor().compareTo((Constant) ((CstFieldRef) constant).getNat().getDescriptor());
    }
}
