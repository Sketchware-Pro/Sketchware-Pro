package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;

public abstract class VariableSizeInsn extends DalvInsn {
    public VariableSizeInsn(SourcePosition sourcePosition, RegisterSpecList registerSpecList) {
        super(Dops.SPECIAL_FORMAT, sourcePosition, registerSpecList);
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public final DalvInsn withOpcode(Dop dop) {
        throw new RuntimeException("unsupported");
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public final DalvInsn withRegisterOffset(int i) {
        return withRegisters(getRegisters().withOffset(i));
    }
}
