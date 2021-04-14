package mod.agus.jcoderz.dx.dex.code.form;

import java.util.BitSet;
import mod.agus.jcoderz.dx.dex.code.CstInsn;
import mod.agus.jcoderz.dx.dex.code.DalvInsn;
import mod.agus.jcoderz.dx.dex.code.InsnFormat;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstLiteralBits;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public final class Form31i extends InsnFormat {
    public static final InsnFormat THE_ONE = new Form31i();

    private Form31i() {
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public String insnArgString(DalvInsn dalvInsn) {
        return String.valueOf(dalvInsn.getRegisters().get(0).regString()) + ", " + literalBitsString((CstLiteralBits) ((CstInsn) dalvInsn).getConstant());
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public String insnCommentString(DalvInsn dalvInsn, boolean z) {
        return literalBitsComment((CstLiteralBits) ((CstInsn) dalvInsn).getConstant(), 32);
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public int codeSize() {
        return 3;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public boolean isCompatible(DalvInsn dalvInsn) {
        RegisterSpecList registers = dalvInsn.getRegisters();
        if (!(dalvInsn instanceof CstInsn) || registers.size() != 1 || !unsignedFitsInByte(registers.get(0).getReg())) {
            return false;
        }
        Constant constant = ((CstInsn) dalvInsn).getConstant();
        if (!(constant instanceof CstLiteralBits)) {
            return false;
        }
        return ((CstLiteralBits) constant).fitsInInt();
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public BitSet compatibleRegs(DalvInsn dalvInsn) {
        RegisterSpecList registers = dalvInsn.getRegisters();
        BitSet bitSet = new BitSet(1);
        bitSet.set(0, unsignedFitsInByte(registers.get(0).getReg()));
        return bitSet;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public void writeTo(AnnotatedOutput annotatedOutput, DalvInsn dalvInsn) {
        RegisterSpecList registers = dalvInsn.getRegisters();
        write(annotatedOutput, opcodeUnit(dalvInsn, registers.get(0).getReg()), ((CstLiteralBits) ((CstInsn) dalvInsn).getConstant()).getIntBits());
    }
}
