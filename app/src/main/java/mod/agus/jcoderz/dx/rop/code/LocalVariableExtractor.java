package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.util.Bits;
import mod.agus.jcoderz.dx.util.IntList;

public final class LocalVariableExtractor {
    private final BasicBlockList blocks;
    private final RopMethod method;
    private final LocalVariableInfo resultInfo;
    private final int[] workSet;

    private LocalVariableExtractor(RopMethod ropMethod) {
        if (ropMethod == null) {
            throw new NullPointerException("method == null");
        }
        BasicBlockList blocks2 = ropMethod.getBlocks();
        int maxLabel = blocks2.getMaxLabel();
        this.method = ropMethod;
        this.blocks = blocks2;
        this.resultInfo = new LocalVariableInfo(ropMethod);
        this.workSet = Bits.makeBitSet(maxLabel);
    }

    public static LocalVariableInfo extract(RopMethod ropMethod) {
        return new LocalVariableExtractor(ropMethod).doit();
    }

    private LocalVariableInfo doit() {
        int firstLabel = this.method.getFirstLabel();
        while (firstLabel >= 0) {
            Bits.clear(this.workSet, firstLabel);
            processBlock(firstLabel);
            firstLabel = Bits.findFirst(this.workSet, 0);
        }
        this.resultInfo.setImmutable();
        return this.resultInfo;
    }

    private void processBlock(int i) {
        RegisterSpecSet registerSpecSet;
        RegisterSpecSet mutableCopyOfStarts = this.resultInfo.mutableCopyOfStarts(i);
        BasicBlock labelToBlock = this.blocks.labelToBlock(i);
        InsnList insns = labelToBlock.getInsns();
        int size = insns.size();
        boolean z = labelToBlock.hasExceptionHandlers() && insns.getLast().getResult() != null;
        int i2 = size - 1;
        RegisterSpecSet registerSpecSet2 = mutableCopyOfStarts;
        for (int i3 = 0; i3 < size; i3++) {
            if (z && i3 == i2) {
                registerSpecSet2.setImmutable();
                registerSpecSet2 = registerSpecSet2.mutableCopy();
            }
            Insn insn = insns.get(i3);
            RegisterSpec localAssignment = insn.getLocalAssignment();
            if (localAssignment == null) {
                RegisterSpec result = insn.getResult();
                if (!(result == null || registerSpecSet2.get(result.getReg()) == null)) {
                    registerSpecSet2.remove(registerSpecSet2.get(result.getReg()));
                }
            } else {
                RegisterSpec withSimpleType = localAssignment.withSimpleType();
                if (!withSimpleType.equals(registerSpecSet2.get(withSimpleType))) {
                    RegisterSpec localItemToSpec = registerSpecSet2.localItemToSpec(withSimpleType.getLocalItem());
                    if (!(localItemToSpec == null || localItemToSpec.getReg() == withSimpleType.getReg())) {
                        registerSpecSet2.remove(localItemToSpec);
                    }
                    this.resultInfo.addAssignment(insn, withSimpleType);
                    registerSpecSet2.put(withSimpleType);
                }
            }
        }
        registerSpecSet2.setImmutable();
        IntList successors = labelToBlock.getSuccessors();
        int size2 = successors.size();
        int primarySuccessor = labelToBlock.getPrimarySuccessor();
        for (int i4 = 0; i4 < size2; i4++) {
            int i5 = successors.get(i4);
            if (i5 == primarySuccessor) {
                registerSpecSet = registerSpecSet2;
            } else {
                registerSpecSet = mutableCopyOfStarts;
            }
            if (this.resultInfo.mergeStarts(i5, registerSpecSet)) {
                Bits.set(this.workSet, i5);
            }
        }
    }
}
