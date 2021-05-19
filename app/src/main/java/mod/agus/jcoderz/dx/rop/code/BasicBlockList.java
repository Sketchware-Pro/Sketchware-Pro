package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;
import mod.agus.jcoderz.dx.util.LabeledItem;
import mod.agus.jcoderz.dx.util.LabeledList;

public final class BasicBlockList extends LabeledList {
    private int regCount;

    public BasicBlockList(int i) {
        super(i);
        this.regCount = -1;
    }

    private BasicBlockList(BasicBlockList basicBlockList) {
        super(basicBlockList);
        this.regCount = basicBlockList.regCount;
    }

    public BasicBlock get(int i) {
        return (BasicBlock) get0(i);
    }

    public void set(int i, BasicBlock basicBlock) {
        super.set(i, (LabeledItem) basicBlock);
        this.regCount = -1;
    }

    public int getRegCount() {
        if (this.regCount == -1) {
            RegCountVisitor regCountVisitor = new RegCountVisitor();
            forEachInsn(regCountVisitor);
            this.regCount = regCountVisitor.getRegCount();
        }
        return this.regCount;
    }

    public int getInstructionCount() {
        int i;
        int size = size();
        int i2 = 0;
        int i3 = 0;
        while (i2 < size) {
            BasicBlock basicBlock = (BasicBlock) getOrNull0(i2);
            if (basicBlock != null) {
                i = basicBlock.getInsns().size() + i3;
            } else {
                i = i3;
            }
            i2++;
            i3 = i;
        }
        return i3;
    }

    public int getEffectiveInstructionCount() {
        int i;
        int size = size();
        int i2 = 0;
        int i3 = 0;
        while (i2 < size) {
            BasicBlock basicBlock = (BasicBlock) getOrNull0(i2);
            if (basicBlock != null) {
                InsnList insns = basicBlock.getInsns();
                int size2 = insns.size();
                i = i3;
                for (int i4 = 0; i4 < size2; i4++) {
                    if (insns.get(i4).getOpcode().getOpcode() != 54) {
                        i++;
                    }
                }
            } else {
                i = i3;
            }
            i2++;
            i3 = i;
        }
        return i3;
    }

    public BasicBlock labelToBlock(int i) {
        int indexOfLabel = indexOfLabel(i);
        if (indexOfLabel >= 0) {
            return get(indexOfLabel);
        }
        throw new IllegalArgumentException("no such label: " + Hex.u2(i));
    }

    public void forEachInsn(Insn.Visitor visitor) {
        int size = size();
        for (int i = 0; i < size; i++) {
            get(i).getInsns().forEach(visitor);
        }
    }

    public BasicBlockList withRegisterOffset(int i) {
        int size = size();
        BasicBlockList basicBlockList = new BasicBlockList(size);
        for (int i2 = 0; i2 < size; i2++) {
            BasicBlock basicBlock = (BasicBlock) get0(i2);
            if (basicBlock != null) {
                basicBlockList.set(i2, basicBlock.withRegisterOffset(i));
            }
        }
        if (isImmutable()) {
            basicBlockList.setImmutable();
        }
        return basicBlockList;
    }

    public BasicBlockList getMutableCopy() {
        return new BasicBlockList(this);
    }

    public BasicBlock preferredSuccessorOf(BasicBlock basicBlock) {
        int primarySuccessor = basicBlock.getPrimarySuccessor();
        IntList successors = basicBlock.getSuccessors();
        switch (successors.size()) {
            case 0:
                return null;
            case 1:
                return labelToBlock(successors.get(0));
            default:
                if (primarySuccessor != -1) {
                    return labelToBlock(primarySuccessor);
                }
                return labelToBlock(successors.get(0));
        }
    }

    public boolean catchesEqual(BasicBlock basicBlock, BasicBlock basicBlock2) {
        if (!StdTypeList.equalContents(basicBlock.getExceptionHandlerTypes(), basicBlock2.getExceptionHandlerTypes())) {
            return false;
        }
        IntList successors = basicBlock.getSuccessors();
        IntList successors2 = basicBlock2.getSuccessors();
        int size = successors.size();
        int primarySuccessor = basicBlock.getPrimarySuccessor();
        int primarySuccessor2 = basicBlock2.getPrimarySuccessor();
        if ((primarySuccessor == -1 || primarySuccessor2 == -1) && primarySuccessor != primarySuccessor2) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            int i2 = successors.get(i);
            int i3 = successors2.get(i);
            if (i2 == primarySuccessor) {
                if (i3 != primarySuccessor2) {
                    return false;
                }
            } else if (i2 != i3) {
                return false;
            }
        }
        return true;
    }

    private static class RegCountVisitor implements Insn.Visitor {
        private int regCount = 0;

        public int getRegCount() {
            return this.regCount;
        }

        @Override // mod.agus.jcoderz.dx.rop.code.Insn.Visitor
        public void visitPlainInsn(PlainInsn plainInsn) {
            visit(plainInsn);
        }

        @Override // mod.agus.jcoderz.dx.rop.code.Insn.Visitor
        public void visitPlainCstInsn(PlainCstInsn plainCstInsn) {
            visit(plainCstInsn);
        }

        @Override // mod.agus.jcoderz.dx.rop.code.Insn.Visitor
        public void visitSwitchInsn(SwitchInsn switchInsn) {
            visit(switchInsn);
        }

        @Override // mod.agus.jcoderz.dx.rop.code.Insn.Visitor
        public void visitThrowingCstInsn(ThrowingCstInsn throwingCstInsn) {
            visit(throwingCstInsn);
        }

        @Override // mod.agus.jcoderz.dx.rop.code.Insn.Visitor
        public void visitThrowingInsn(ThrowingInsn throwingInsn) {
            visit(throwingInsn);
        }

        @Override // mod.agus.jcoderz.dx.rop.code.Insn.Visitor
        public void visitFillArrayDataInsn(FillArrayDataInsn fillArrayDataInsn) {
            visit(fillArrayDataInsn);
        }

        private void visit(Insn insn) {
            RegisterSpec result = insn.getResult();
            if (result != null) {
                processReg(result);
            }
            RegisterSpecList sources = insn.getSources();
            int size = sources.size();
            for (int i = 0; i < size; i++) {
                processReg(sources.get(i));
            }
        }

        private void processReg(RegisterSpec registerSpec) {
            int nextReg = registerSpec.getNextReg();
            if (nextReg > this.regCount) {
                this.regCount = nextReg;
            }
        }
    }
}
