package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public final class HighRegisterPrefix extends VariableSizeInsn {
    private SimpleInsn[] insns;

    public HighRegisterPrefix(SourcePosition sourcePosition, RegisterSpecList registerSpecList) {
        super(sourcePosition, registerSpecList);
        if (registerSpecList.size() == 0) {
            throw new IllegalArgumentException("registers.size() == 0");
        }
        this.insns = null;
    }

    private static SimpleInsn moveInsnFor(RegisterSpec registerSpec, int i) {
        return DalvInsn.makeMove(SourcePosition.NO_INFO, RegisterSpec.make(i, registerSpec.getType()), registerSpec);
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public int codeSize() {
        calculateInsnsIfNecessary();
        int i = 0;
        for (SimpleInsn simpleInsn : this.insns) {
            i += simpleInsn.codeSize();
        }
        return i;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public void writeTo(AnnotatedOutput annotatedOutput) {
        calculateInsnsIfNecessary();
        for (SimpleInsn simpleInsn : this.insns) {
            simpleInsn.writeTo(annotatedOutput);
        }
    }

    private void calculateInsnsIfNecessary() {
        int i = 0;
        if (this.insns == null) {
            RegisterSpecList registers = getRegisters();
            int size = registers.size();
            this.insns = new SimpleInsn[size];
            for (int i2 = 0; i2 < size; i2++) {
                RegisterSpec registerSpec = registers.get(i2);
                this.insns[i2] = moveInsnFor(registerSpec, i);
                i += registerSpec.getCategory();
            }
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public DalvInsn withRegisters(RegisterSpecList registerSpecList) {
        return new HighRegisterPrefix(getPosition(), registerSpecList);
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public String argString() {
        return null;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public String listingString0(boolean z) {
        int i = 0;
        RegisterSpecList registers = getRegisters();
        int size = registers.size();
        StringBuffer stringBuffer = new StringBuffer(100);
        for (int i2 = 0; i2 < size; i2++) {
            RegisterSpec registerSpec = registers.get(i2);
            SimpleInsn moveInsnFor = moveInsnFor(registerSpec, i);
            if (i2 != 0) {
                stringBuffer.append('\n');
            }
            stringBuffer.append(moveInsnFor.listingString0(z));
            i += registerSpec.getCategory();
        }
        return stringBuffer.toString();
    }
}
