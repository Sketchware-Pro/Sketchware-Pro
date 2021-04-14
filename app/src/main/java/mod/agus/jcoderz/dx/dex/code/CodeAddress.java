package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;

public final class CodeAddress extends ZeroSizeInsn {
    private final boolean bindsClosely;

    public CodeAddress(SourcePosition sourcePosition) {
        this(sourcePosition, false);
    }

    public CodeAddress(SourcePosition sourcePosition, boolean z) {
        super(sourcePosition);
        this.bindsClosely = z;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public final DalvInsn withRegisters(RegisterSpecList registerSpecList) {
        return new CodeAddress(getPosition());
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public String argString() {
        return null;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public String listingString0(boolean z) {
        return "code-address";
    }

    public boolean getBindsClosely() {
        return this.bindsClosely;
    }
}
