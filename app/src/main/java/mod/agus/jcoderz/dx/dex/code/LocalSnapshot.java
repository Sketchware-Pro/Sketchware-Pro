package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecSet;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.ssa.RegisterMapper;

public final class LocalSnapshot extends ZeroSizeInsn {
    private final RegisterSpecSet locals;

    public LocalSnapshot(SourcePosition sourcePosition, RegisterSpecSet registerSpecSet) {
        super(sourcePosition);
        if (registerSpecSet == null) {
            throw new NullPointerException("locals == null");
        }
        this.locals = registerSpecSet;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.ZeroSizeInsn, mod.agus.jcoderz.dx.dex.code.DalvInsn
    public DalvInsn withRegisterOffset(int i) {
        return new LocalSnapshot(getPosition(), this.locals.withOffset(i));
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public DalvInsn withRegisters(RegisterSpecList registerSpecList) {
        return new LocalSnapshot(getPosition(), this.locals);
    }

    public RegisterSpecSet getLocals() {
        return this.locals;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public String argString() {
        return this.locals.toString();
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public String listingString0(boolean z) {
        int size = this.locals.size();
        int maxSize = this.locals.getMaxSize();
        StringBuffer stringBuffer = new StringBuffer((size * 40) + 100);
        stringBuffer.append("local-snapshot");
        for (int i = 0; i < maxSize; i++) {
            RegisterSpec registerSpec = this.locals.get(i);
            if (registerSpec != null) {
                stringBuffer.append("\n  ");
                stringBuffer.append(LocalStart.localString(registerSpec));
            }
        }
        return stringBuffer.toString();
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public DalvInsn withMapper(RegisterMapper registerMapper) {
        return new LocalSnapshot(getPosition(), registerMapper.map(this.locals));
    }
}
