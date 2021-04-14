package mod.agus.jcoderz.dx.dex.code.form;

import java.util.BitSet;
import mod.agus.jcoderz.dx.dex.code.CstInsn;
import mod.agus.jcoderz.dx.dex.code.DalvInsn;
import mod.agus.jcoderz.dx.dex.code.InsnFormat;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public final class Form35c extends InsnFormat {
    private static final int MAX_NUM_OPS = 5;
    public static final InsnFormat THE_ONE = new Form35c();

    private Form35c() {
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public String insnArgString(DalvInsn dalvInsn) {
        return String.valueOf(regListString(explicitize(dalvInsn.getRegisters()))) + ", " + cstString(dalvInsn);
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
        return 3;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public boolean isCompatible(DalvInsn dalvInsn) {
        if (!(dalvInsn instanceof CstInsn)) {
            return false;
        }
        CstInsn cstInsn = (CstInsn) dalvInsn;
        if (!unsignedFitsInShort(cstInsn.getIndex())) {
            return false;
        }
        Constant constant = cstInsn.getConstant();
        if (((constant instanceof CstMethodRef) || (constant instanceof CstType)) && wordCount(cstInsn.getRegisters()) >= 0) {
            return true;
        }
        return false;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public BitSet compatibleRegs(DalvInsn dalvInsn) {
        RegisterSpecList registers = dalvInsn.getRegisters();
        int size = registers.size();
        BitSet bitSet = new BitSet(size);
        for (int i = 0; i < size; i++) {
            RegisterSpec registerSpec = registers.get(i);
            bitSet.set(i, unsignedFitsInNibble((registerSpec.getCategory() + registerSpec.getReg()) - 1));
        }
        return bitSet;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.InsnFormat
    public void writeTo(AnnotatedOutput annotatedOutput, DalvInsn dalvInsn) {
        int i;
        int i2;
        int i3;
        int i4;
        int index = ((CstInsn) dalvInsn).getIndex();
        RegisterSpecList explicitize = explicitize(dalvInsn.getRegisters());
        int size = explicitize.size();
        int reg = size > 0 ? explicitize.get(0).getReg() : 0;
        if (size > 1) {
            i = explicitize.get(1).getReg();
        } else {
            i = 0;
        }
        if (size > 2) {
            i2 = explicitize.get(2).getReg();
        } else {
            i2 = 0;
        }
        if (size > 3) {
            i3 = explicitize.get(3).getReg();
        } else {
            i3 = 0;
        }
        if (size > 4) {
            i4 = explicitize.get(4).getReg();
        } else {
            i4 = 0;
        }
        write(annotatedOutput, opcodeUnit(dalvInsn, makeByte(i4, size)), (short) index, codeUnit(reg, i, i2, i3));
    }

    private static int wordCount(RegisterSpecList registerSpecList) {
        int i = 0;
        int size = registerSpecList.size();
        if (size > 5) {
            return -1;
        }
        int i2 = 0;
        while (i2 < size) {
            RegisterSpec registerSpec = registerSpecList.get(i2);
            int category = registerSpec.getCategory() + i;
            if (!unsignedFitsInNibble((registerSpec.getReg() + registerSpec.getCategory()) - 1)) {
                return -1;
            }
            i2++;
            i = category;
        }
        if (i > 5) {
            i = -1;
        }
        return i;
    }

    private static RegisterSpecList explicitize(RegisterSpecList registerSpecList) {
        int i = 0;
        int wordCount = wordCount(registerSpecList);
        int size = registerSpecList.size();
        if (wordCount == size) {
            return registerSpecList;
        }
        RegisterSpecList registerSpecList2 = new RegisterSpecList(wordCount);
        for (int i2 = 0; i2 < size; i2++) {
            RegisterSpec registerSpec = registerSpecList.get(i2);
            registerSpecList2.set(i, registerSpec);
            if (registerSpec.getCategory() == 2) {
                registerSpecList2.set(i + 1, RegisterSpec.make(registerSpec.getReg() + 1, Type.VOID));
                i += 2;
            } else {
                i++;
            }
        }
        registerSpecList2.setImmutable();
        return registerSpecList2;
    }
}
