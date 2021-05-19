package mod.agus.jcoderz.dx.dex.code.form;

import java.util.BitSet;

import mod.agus.jcoderz.dx.dex.code.DalvInsn;
import mod.agus.jcoderz.dx.dex.code.InsnFormat;
import mod.agus.jcoderz.dx.dex.code.SimpleInsn;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public final class Form12x extends InsnFormat {
    public static final InsnFormat THE_ONE = new Form12x();

    private Form12x() {
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public String insnArgString(DalvInsn dalvInsn) {
        RegisterSpecList registers = dalvInsn.getRegisters();
        int size = registers.size();
        return registers.get(size - 2).regString() + ", " + registers.get(size - 1).regString();
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public String insnCommentString(DalvInsn dalvInsn, boolean z) {
        return "";
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public int codeSize() {
        return 1;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public boolean isCompatible(DalvInsn dalvInsn) {
        RegisterSpec registerSpec;
        RegisterSpec registerSpec2;
        if (!(dalvInsn instanceof SimpleInsn)) {
            return false;
        }
        RegisterSpecList registers = dalvInsn.getRegisters();
        switch (registers.size()) {
            case 2:
                registerSpec = registers.get(0);
                registerSpec2 = registers.get(1);
                break;
            case 3:
                registerSpec = registers.get(1);
                registerSpec2 = registers.get(2);
                if (registerSpec.getReg() != registers.get(0).getReg()) {
                    return false;
                }
                break;
            default:
                return false;
        }
        return unsignedFitsInNibble(registerSpec.getReg()) && unsignedFitsInNibble(registerSpec2.getReg());
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public BitSet compatibleRegs(DalvInsn dalvInsn) {
        RegisterSpecList registers = dalvInsn.getRegisters();
        BitSet bitSet = new BitSet(2);
        int reg = registers.get(0).getReg();
        int reg2 = registers.get(1).getReg();
        switch (registers.size()) {
            case 2:
                bitSet.set(0, unsignedFitsInNibble(reg));
                bitSet.set(1, unsignedFitsInNibble(reg2));
                break;
            case 3:
                if (reg != reg2) {
                    bitSet.set(0, false);
                    bitSet.set(1, false);
                } else {
                    boolean unsignedFitsInNibble = unsignedFitsInNibble(reg2);
                    bitSet.set(0, unsignedFitsInNibble);
                    bitSet.set(1, unsignedFitsInNibble);
                }
                bitSet.set(2, unsignedFitsInNibble(registers.get(2).getReg()));
                break;
            default:
                throw new AssertionError();
        }
        return bitSet;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public void writeTo(AnnotatedOutput annotatedOutput, DalvInsn dalvInsn) {
        RegisterSpecList registers = dalvInsn.getRegisters();
        int size = registers.size();
        write(annotatedOutput, opcodeUnit(dalvInsn, makeByte(registers.get(size - 2).getReg(), registers.get(size - 1).getReg())));
    }
}
