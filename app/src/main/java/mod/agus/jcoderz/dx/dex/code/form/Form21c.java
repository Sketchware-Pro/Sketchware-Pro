package mod.agus.jcoderz.dx.dex.code.form;

import java.util.BitSet;
import mod.agus.jcoderz.dx.dex.code.CstInsn;
import mod.agus.jcoderz.dx.dex.code.DalvInsn;
import mod.agus.jcoderz.dx.dex.code.InsnFormat;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public final class Form21c extends InsnFormat {
    public static final InsnFormat THE_ONE = new Form21c();

    private Form21c() {
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public String insnArgString(DalvInsn dalvInsn) {
        return String.valueOf(dalvInsn.getRegisters().get(0).regString()) + ", " + cstString(dalvInsn);
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public String insnCommentString(DalvInsn dalvInsn, boolean z) {
        if (z) {
            return cstComment(dalvInsn);
        }
        return "";
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public int codeSize() {
        return 2;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public boolean isCompatible(DalvInsn dalvInsn) {
        RegisterSpec registerSpec;
        if (!(dalvInsn instanceof CstInsn)) {
            return false;
        }
        RegisterSpecList registers = dalvInsn.getRegisters();
        switch (registers.size()) {
            case 1:
                registerSpec = registers.get(0);
                break;
            case 2:
                registerSpec = registers.get(0);
                if (registerSpec.getReg() != registers.get(1).getReg()) {
                    return false;
                }
                break;
            default:
                return false;
        }
        if (!unsignedFitsInByte(registerSpec.getReg())) {
            return false;
        }
        CstInsn cstInsn = (CstInsn) dalvInsn;
        int index = cstInsn.getIndex();
        Constant constant = cstInsn.getConstant();
        if (!unsignedFitsInShort(index)) {
            return false;
        }
        return (constant instanceof CstType) || (constant instanceof CstFieldRef) || (constant instanceof CstString);
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public BitSet compatibleRegs(DalvInsn dalvInsn) {
        RegisterSpecList registers = dalvInsn.getRegisters();
        int size = registers.size();
        BitSet bitSet = new BitSet(size);
        boolean unsignedFitsInByte = unsignedFitsInByte(registers.get(0).getReg());
        if (size == 1) {
            bitSet.set(0, unsignedFitsInByte);
        } else if (registers.get(0).getReg() == registers.get(1).getReg()) {
            bitSet.set(0, unsignedFitsInByte);
            bitSet.set(1, unsignedFitsInByte);
        }
        return bitSet;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public void writeTo(AnnotatedOutput annotatedOutput, DalvInsn dalvInsn) {
        write(annotatedOutput, opcodeUnit(dalvInsn, dalvInsn.getRegisters().get(0).getReg()), (short) ((CstInsn) dalvInsn).getIndex());
    }
}
