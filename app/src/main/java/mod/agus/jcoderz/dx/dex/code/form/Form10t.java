package mod.agus.jcoderz.dx.dex.code.form;

import mod.agus.jcoderz.dx.dex.code.DalvInsn;
import mod.agus.jcoderz.dx.dex.code.InsnFormat;
import mod.agus.jcoderz.dx.dex.code.TargetInsn;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public final class Form10t extends InsnFormat {
    public static final InsnFormat THE_ONE = new Form10t();

    private Form10t() {
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public String insnArgString(DalvInsn dalvInsn) {
        return branchString(dalvInsn);
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public String insnCommentString(DalvInsn dalvInsn, boolean z) {
        return branchComment(dalvInsn);
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public int codeSize() {
        return 1;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public boolean isCompatible(DalvInsn dalvInsn) {
        if (!(dalvInsn instanceof TargetInsn) || dalvInsn.getRegisters().size() != 0) {
            return false;
        }
        TargetInsn targetInsn = (TargetInsn) dalvInsn;
        if (targetInsn.hasTargetOffset()) {
            return branchFits(targetInsn);
        }
        return true;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public boolean branchFits(TargetInsn targetInsn) {
        int targetOffset = targetInsn.getTargetOffset();
        return targetOffset != 0 && signedFitsInByte(targetOffset);
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public void writeTo(AnnotatedOutput annotatedOutput, DalvInsn dalvInsn) {
        write(annotatedOutput, opcodeUnit(dalvInsn, ((TargetInsn) dalvInsn).getTargetOffset() & 255));
    }
}
