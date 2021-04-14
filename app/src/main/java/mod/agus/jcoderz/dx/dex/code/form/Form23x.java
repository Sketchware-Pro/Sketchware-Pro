package mod.agus.jcoderz.dx.dex.code.form;

import java.util.BitSet;
import mod.agus.jcoderz.dx.dex.code.DalvInsn;
import mod.agus.jcoderz.dx.dex.code.InsnFormat;
import mod.agus.jcoderz.dx.dex.code.SimpleInsn;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public final class Form23x extends InsnFormat {
    public static final InsnFormat THE_ONE = new Form23x();

    private Form23x() {
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public String insnArgString(DalvInsn dalvInsn) {
        RegisterSpecList registers = dalvInsn.getRegisters();
        return String.valueOf(registers.get(0).regString()) + ", " + registers.get(1).regString() + ", " + registers.get(2).regString();
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public String insnCommentString(DalvInsn dalvInsn, boolean z) {
        return "";
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public int codeSize() {
        return 2;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public boolean isCompatible(DalvInsn dalvInsn) {
        RegisterSpecList registers = dalvInsn.getRegisters();
        return (dalvInsn instanceof SimpleInsn) && registers.size() == 3 && unsignedFitsInByte(registers.get(0).getReg()) && unsignedFitsInByte(registers.get(1).getReg()) && unsignedFitsInByte(registers.get(2).getReg());
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public BitSet compatibleRegs(DalvInsn dalvInsn) {
        RegisterSpecList registers = dalvInsn.getRegisters();
        BitSet bitSet = new BitSet(3);
        bitSet.set(0, unsignedFitsInByte(registers.get(0).getReg()));
        bitSet.set(1, unsignedFitsInByte(registers.get(1).getReg()));
        bitSet.set(2, unsignedFitsInByte(registers.get(2).getReg()));
        return bitSet;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public void writeTo(AnnotatedOutput annotatedOutput, DalvInsn dalvInsn) {
        RegisterSpecList registers = dalvInsn.getRegisters();
        write(annotatedOutput, opcodeUnit(dalvInsn, registers.get(0).getReg()), codeUnit(registers.get(1).getReg(), registers.get(2).getReg()));
    }
}
