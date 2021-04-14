package mod.agus.jcoderz.dx.rop.cst;

public final class CstMethodRef extends CstBaseMethodRef {
    public CstMethodRef(CstType cstType, CstNat cstNat) {
        super(cstType, cstNat);
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public String typeName() {
        return "method";
    }
}
