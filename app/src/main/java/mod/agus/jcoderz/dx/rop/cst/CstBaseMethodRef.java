package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.Type;

public abstract class CstBaseMethodRef extends CstMemberRef {
    private Prototype instancePrototype = null;
    private final Prototype prototype = Prototype.intern(getNat().getDescriptor().getString());

    CstBaseMethodRef(CstType cstType, CstNat cstNat) {
        super(cstType, cstNat);
    }

    public final Prototype getPrototype() {
        return this.prototype;
    }

    public final Prototype getPrototype(boolean z) {
        if (z) {
            return this.prototype;
        }
        if (this.instancePrototype == null) {
            this.instancePrototype = this.prototype.withFirstParameter(getDefiningClass().getClassType());
        }
        return this.instancePrototype;
    }

    /* access modifiers changed from: protected */
    @Override // mod.agus.jcoderz.dx.rop.cst.CstMemberRef, mod.agus.jcoderz.dx.rop.cst.Constant
    public final int compareTo0(Constant constant) {
        int compareTo0 = super.compareTo0(constant);
        return compareTo0 != 0 ? compareTo0 : this.prototype.compareTo(((CstBaseMethodRef) constant).prototype);
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public final Type getType() {
        return this.prototype.getReturnType();
    }

    public final int getParameterWordCount(boolean z) {
        return getPrototype(z).getParameterTypes().getWordCount();
    }

    public final boolean isInstanceInit() {
        return getNat().isInstanceInit();
    }

    public final boolean isClassInit() {
        return getNat().isClassInit();
    }
}
