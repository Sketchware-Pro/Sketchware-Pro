package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.TypeBearer;

public abstract class TypedConstant extends Constant implements TypeBearer {
    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public final TypeBearer getFrameType() {
        return this;
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public final int getBasicType() {
        return getType().getBasicType();
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public final int getBasicFrameType() {
        return getType().getBasicFrameType();
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public final boolean isConstant() {
        return true;
    }
}
