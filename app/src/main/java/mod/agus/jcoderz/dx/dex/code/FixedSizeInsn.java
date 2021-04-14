package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public abstract class FixedSizeInsn extends DalvInsn {
    public FixedSizeInsn(Dop dop, SourcePosition sourcePosition, RegisterSpecList registerSpecList) {
        super(dop, sourcePosition, registerSpecList);
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public final int codeSize() {
        return getOpcode().getFormat().codeSize();
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public final void writeTo(AnnotatedOutput annotatedOutput) {
        getOpcode().getFormat().writeTo(annotatedOutput, this);
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public final DalvInsn withRegisterOffset(int i) {
        return withRegisters(getRegisters().withOffset(i));
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public final String listingString0(boolean z) {
        return getOpcode().getFormat().listingString(this, z);
    }
}
