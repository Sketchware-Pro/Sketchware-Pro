package mod.agus.jcoderz.dx.dex.code.form;

import mod.agus.jcoderz.dx.dex.code.DalvInsn;
import mod.agus.jcoderz.dx.dex.code.InsnFormat;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public final class SpecialFormat extends InsnFormat {
    public static final InsnFormat THE_ONE = new SpecialFormat();

    private SpecialFormat() {
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public String insnArgString(DalvInsn dalvInsn) {
        throw new RuntimeException("unsupported");
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public String insnCommentString(DalvInsn dalvInsn, boolean z) {
        throw new RuntimeException("unsupported");
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public int codeSize() {
        throw new RuntimeException("unsupported");
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public boolean isCompatible(DalvInsn dalvInsn) {
        return true;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public void writeTo(AnnotatedOutput annotatedOutput, DalvInsn dalvInsn) {
        throw new RuntimeException("unsupported");
    }
}
