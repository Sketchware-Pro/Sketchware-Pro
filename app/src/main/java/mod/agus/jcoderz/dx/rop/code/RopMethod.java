package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;

public final class RopMethod {
    private final BasicBlockList blocks;
    private final int firstLabel;
    private IntList exitPredecessors;
    private IntList[] predecessors;

    public RopMethod(BasicBlockList basicBlockList, int i) {
        if (basicBlockList == null) {
            throw new NullPointerException("blocks == null");
        } else if (i < 0) {
            throw new IllegalArgumentException("firstLabel < 0");
        } else {
            this.blocks = basicBlockList;
            this.firstLabel = i;
            this.predecessors = null;
            this.exitPredecessors = null;
        }
    }

    public BasicBlockList getBlocks() {
        return this.blocks;
    }

    public int getFirstLabel() {
        return this.firstLabel;
    }

    public IntList labelToPredecessors(int i) {
        if (this.exitPredecessors == null) {
            calcPredecessors();
        }
        IntList intList = this.predecessors[i];
        if (intList != null) {
            return intList;
        }
        throw new RuntimeException("no such block: " + Hex.u2(i));
    }

    public IntList getExitPredecessors() {
        if (this.exitPredecessors == null) {
            calcPredecessors();
        }
        return this.exitPredecessors;
    }

    public RopMethod withRegisterOffset(int i) {
        RopMethod ropMethod = new RopMethod(this.blocks.withRegisterOffset(i), this.firstLabel);
        if (this.exitPredecessors != null) {
            ropMethod.exitPredecessors = this.exitPredecessors;
            ropMethod.predecessors = this.predecessors;
        }
        return ropMethod;
    }

    private void calcPredecessors() {
        int maxLabel = this.blocks.getMaxLabel();
        IntList[] intListArr = new IntList[maxLabel];
        IntList intList = new IntList(10);
        int size = this.blocks.size();
        for (int i = 0; i < size; i++) {
            BasicBlock basicBlock = this.blocks.get(i);
            int label = basicBlock.getLabel();
            IntList successors = basicBlock.getSuccessors();
            int size2 = successors.size();
            if (size2 == 0) {
                intList.add(label);
            } else {
                for (int i2 = 0; i2 < size2; i2++) {
                    int i3 = successors.get(i2);
                    IntList intList2 = intListArr[i3];
                    if (intList2 == null) {
                        intList2 = new IntList(10);
                        intListArr[i3] = intList2;
                    }
                    intList2.add(label);
                }
            }
        }
        for (int i4 = 0; i4 < maxLabel; i4++) {
            IntList intList3 = intListArr[i4];
            if (intList3 != null) {
                intList3.sort();
                intList3.setImmutable();
            }
        }
        intList.sort();
        intList.setImmutable();
        if (intListArr[this.firstLabel] == null) {
            intListArr[this.firstLabel] = IntList.EMPTY;
        }
        this.predecessors = intListArr;
        this.exitPredecessors = intList;
    }
}
