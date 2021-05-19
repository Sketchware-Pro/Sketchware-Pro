package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mod.agus.jcoderz.dx.rop.code.BasicBlock;
import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.InsnList;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;
import mod.agus.jcoderz.dx.util.IntSet;

public final class SsaBasicBlock {
    public static final Comparator<SsaBasicBlock> LABEL_COMPARATOR = new LabelComparator();
    private final ArrayList<SsaBasicBlock> domChildren;
    private final int index;
    private final ArrayList<SsaInsn> insns;
    private IntSet liveIn;
    private IntSet liveOut;
    private int movesFromPhisAtBeginning = 0;
    private int movesFromPhisAtEnd = 0;
    private final SsaMethod parent;
    private BitSet predecessors;
    private int primarySuccessor = -1;
    private int reachable = -1;
    private final int ropLabel;
    private IntList successorList;
    private BitSet successors;

    public SsaBasicBlock(int i, int i2, SsaMethod ssaMethod) {
        this.parent = ssaMethod;
        this.index = i;
        this.insns = new ArrayList<>();
        this.ropLabel = i2;
        this.predecessors = new BitSet(ssaMethod.getBlocks().size());
        this.successors = new BitSet(ssaMethod.getBlocks().size());
        this.successorList = new IntList();
        this.domChildren = new ArrayList<>();
    }

    public static SsaBasicBlock newFromRop(RopMethod ropMethod, int i, SsaMethod ssaMethod) {
        BasicBlockList blocks = ropMethod.getBlocks();
        BasicBlock basicBlock = blocks.get(i);
        SsaBasicBlock ssaBasicBlock = new SsaBasicBlock(i, basicBlock.getLabel(), ssaMethod);
        InsnList insns2 = basicBlock.getInsns();
        ssaBasicBlock.insns.ensureCapacity(insns2.size());
        int size = insns2.size();
        for (int i2 = 0; i2 < size; i2++) {
            ssaBasicBlock.insns.add(new NormalSsaInsn(insns2.get(i2), ssaBasicBlock));
        }
        ssaBasicBlock.predecessors = SsaMethod.bitSetFromLabelList(blocks, ropMethod.labelToPredecessors(basicBlock.getLabel()));
        ssaBasicBlock.successors = SsaMethod.bitSetFromLabelList(blocks, basicBlock.getSuccessors());
        ssaBasicBlock.successorList = SsaMethod.indexListFromLabelList(blocks, basicBlock.getSuccessors());
        if (ssaBasicBlock.successorList.size() != 0) {
            int primarySuccessor2 = basicBlock.getPrimarySuccessor();
            ssaBasicBlock.primarySuccessor = primarySuccessor2 < 0 ? -1 : blocks.indexOfLabel(primarySuccessor2);
        }
        return ssaBasicBlock;
    }

    private static void setRegsUsed(BitSet bitSet, RegisterSpec registerSpec) {
        bitSet.set(registerSpec.getReg());
        if (registerSpec.getCategory() > 1) {
            bitSet.set(registerSpec.getReg() + 1);
        }
    }

    private static boolean checkRegUsed(BitSet bitSet, RegisterSpec registerSpec) {
        int reg = registerSpec.getReg();
        return bitSet.get(reg) || (registerSpec.getCategory() == 2 && bitSet.get(reg + 1));
    }

    public void addDomChild(SsaBasicBlock ssaBasicBlock) {
        this.domChildren.add(ssaBasicBlock);
    }

    public ArrayList<SsaBasicBlock> getDomChildren() {
        return this.domChildren;
    }

    public void addPhiInsnForReg(int i) {
        this.insns.add(0, new PhiInsn(i, this));
    }

    public void addPhiInsnForReg(RegisterSpec registerSpec) {
        this.insns.add(0, new PhiInsn(registerSpec, this));
    }

    public void addInsnToHead(Insn insn) {
        SsaInsn makeFromRop = SsaInsn.makeFromRop(insn, this);
        this.insns.add(getCountPhiInsns(), makeFromRop);
        this.parent.onInsnAdded(makeFromRop);
    }

    public void replaceLastInsn(Insn insn) {
        if (insn.getOpcode().getBranchingness() == 1) {
            throw new IllegalArgumentException("last insn must branch");
        }
        SsaInsn makeFromRop = SsaInsn.makeFromRop(insn, this);
        this.insns.set(this.insns.size() - 1, makeFromRop);
        this.parent.onInsnRemoved(this.insns.get(this.insns.size() - 1));
        this.parent.onInsnAdded(makeFromRop);
    }

    public void forEachPhiInsn(PhiInsn.Visitor visitor) {
        int size = this.insns.size();
        for (int i = 0; i < size; i++) {
            SsaInsn ssaInsn = this.insns.get(i);
            if (ssaInsn instanceof PhiInsn) {
                visitor.visitPhiInsn((PhiInsn) ssaInsn);
            } else {
                return;
            }
        }
    }

    public void removeAllPhiInsns() {
        this.insns.subList(0, getCountPhiInsns()).clear();
    }

    private int getCountPhiInsns() {
        int size = this.insns.size();
        int i = 0;
        while (i < size && (this.insns.get(i) instanceof PhiInsn)) {
            i++;
        }
        return i;
    }

    public ArrayList<SsaInsn> getInsns() {
        return this.insns;
    }

    public List<SsaInsn> getPhiInsns() {
        return this.insns.subList(0, getCountPhiInsns());
    }

    public int getIndex() {
        return this.index;
    }

    public int getRopLabel() {
        return this.ropLabel;
    }

    public String getRopLabelString() {
        return Hex.u2(this.ropLabel);
    }

    public BitSet getPredecessors() {
        return this.predecessors;
    }

    public BitSet getSuccessors() {
        return this.successors;
    }

    public IntList getSuccessorList() {
        return this.successorList;
    }

    public int getPrimarySuccessorIndex() {
        return this.primarySuccessor;
    }

    public int getPrimarySuccessorRopLabel() {
        return this.parent.blockIndexToRopLabel(this.primarySuccessor);
    }

    public SsaBasicBlock getPrimarySuccessor() {
        if (this.primarySuccessor < 0) {
            return null;
        }
        return this.parent.getBlocks().get(this.primarySuccessor);
    }

    public IntList getRopLabelSuccessorList() {
        IntList intList = new IntList(this.successorList.size());
        int size = this.successorList.size();
        for (int i = 0; i < size; i++) {
            intList.add(this.parent.blockIndexToRopLabel(this.successorList.get(i)));
        }
        return intList;
    }

    public SsaMethod getParent() {
        return this.parent;
    }

    public SsaBasicBlock insertNewPredecessor() {
        SsaBasicBlock makeNewGotoBlock = this.parent.makeNewGotoBlock();
        makeNewGotoBlock.predecessors = this.predecessors;
        makeNewGotoBlock.successors.set(this.index);
        makeNewGotoBlock.successorList.add(this.index);
        makeNewGotoBlock.primarySuccessor = this.index;
        this.predecessors = new BitSet(this.parent.getBlocks().size());
        this.predecessors.set(makeNewGotoBlock.index);
        for (int nextSetBit = makeNewGotoBlock.predecessors.nextSetBit(0); nextSetBit >= 0; nextSetBit = makeNewGotoBlock.predecessors.nextSetBit(nextSetBit + 1)) {
            this.parent.getBlocks().get(nextSetBit).replaceSuccessor(this.index, makeNewGotoBlock.index);
        }
        return makeNewGotoBlock;
    }

    public SsaBasicBlock insertNewSuccessor(SsaBasicBlock ssaBasicBlock) {
        SsaBasicBlock makeNewGotoBlock = this.parent.makeNewGotoBlock();
        if (!this.successors.get(ssaBasicBlock.index)) {
            throw new RuntimeException("Block " + ssaBasicBlock.getRopLabelString() + " not successor of " + getRopLabelString());
        }
        makeNewGotoBlock.predecessors.set(this.index);
        makeNewGotoBlock.successors.set(ssaBasicBlock.index);
        makeNewGotoBlock.successorList.add(ssaBasicBlock.index);
        makeNewGotoBlock.primarySuccessor = ssaBasicBlock.index;
        for (int size = this.successorList.size() - 1; size >= 0; size--) {
            if (this.successorList.get(size) == ssaBasicBlock.index) {
                this.successorList.set(size, makeNewGotoBlock.index);
            }
        }
        if (this.primarySuccessor == ssaBasicBlock.index) {
            this.primarySuccessor = makeNewGotoBlock.index;
        }
        this.successors.clear(ssaBasicBlock.index);
        this.successors.set(makeNewGotoBlock.index);
        ssaBasicBlock.predecessors.set(makeNewGotoBlock.index);
        ssaBasicBlock.predecessors.set(this.index, this.successors.get(ssaBasicBlock.index));
        return makeNewGotoBlock;
    }

    public void replaceSuccessor(int i, int i2) {
        if (i != i2) {
            this.successors.set(i2);
            if (this.primarySuccessor == i) {
                this.primarySuccessor = i2;
            }
            for (int size = this.successorList.size() - 1; size >= 0; size--) {
                if (this.successorList.get(size) == i) {
                    this.successorList.set(size, i2);
                }
            }
            this.successors.clear(i);
            this.parent.getBlocks().get(i2).predecessors.set(this.index);
            this.parent.getBlocks().get(i).predecessors.clear(this.index);
        }
    }

    public void removeSuccessor(int i) {
        int i2 = 0;
        for (int size = this.successorList.size() - 1; size >= 0; size--) {
            if (this.successorList.get(size) == i) {
                i2 = size;
            } else {
                this.primarySuccessor = this.successorList.get(size);
            }
        }
        this.successorList.removeIndex(i2);
        this.successors.clear(i);
        this.parent.getBlocks().get(i).predecessors.clear(this.index);
    }

    public void exitBlockFixup(SsaBasicBlock ssaBasicBlock) {
        if (this != ssaBasicBlock && this.successorList.size() == 0) {
            this.successors.set(ssaBasicBlock.index);
            this.successorList.add(ssaBasicBlock.index);
            this.primarySuccessor = ssaBasicBlock.index;
            ssaBasicBlock.predecessors.set(this.index);
        }
    }

    public void addMoveToEnd(RegisterSpec registerSpec, RegisterSpec registerSpec2) {
        if (registerSpec.getReg() != registerSpec2.getReg()) {
            NormalSsaInsn normalSsaInsn = (NormalSsaInsn) this.insns.get(this.insns.size() - 1);
            if (normalSsaInsn.getResult() != null || normalSsaInsn.getSources().size() > 0) {
                int nextSetBit = this.successors.nextSetBit(0);
                while (nextSetBit >= 0) {
                    this.parent.getBlocks().get(nextSetBit).addMoveToBeginning(registerSpec, registerSpec2);
                    nextSetBit = this.successors.nextSetBit(nextSetBit + 1);
                }
                return;
            }
            this.insns.add(this.insns.size() - 1, new NormalSsaInsn(new PlainInsn(Rops.opMove(registerSpec.getType()), SourcePosition.NO_INFO, registerSpec, RegisterSpecList.make(registerSpec2)), this));
            this.movesFromPhisAtEnd++;
        }
    }

    public void addMoveToBeginning(RegisterSpec registerSpec, RegisterSpec registerSpec2) {
        if (registerSpec.getReg() != registerSpec2.getReg()) {
            this.insns.add(getCountPhiInsns(), new NormalSsaInsn(new PlainInsn(Rops.opMove(registerSpec.getType()), SourcePosition.NO_INFO, registerSpec, RegisterSpecList.make(registerSpec2)), this));
            this.movesFromPhisAtBeginning++;
        }
    }

    private void scheduleUseBeforeAssigned(List<SsaInsn> list) {
        int i;
        SsaInsn ssaInsn;
        int i2;
        BitSet bitSet = new BitSet(this.parent.getRegCount());
        BitSet bitSet2 = new BitSet(this.parent.getRegCount());
        int size = list.size();
        int i3 = 0;
        while (i3 < size) {
            for (int i4 = i3; i4 < size; i4++) {
                setRegsUsed(bitSet, list.get(i4).getSources().get(0));
                setRegsUsed(bitSet2, list.get(i4).getResult());
            }
            int i5 = i3;
            int i6 = i3;
            while (i5 < size) {
                if (!checkRegUsed(bitSet, list.get(i5).getResult())) {
                    i2 = i6 + 1;
                    Collections.swap(list, i5, i6);
                } else {
                    i2 = i6;
                }
                i5++;
                i6 = i2;
            }
            if (i3 == i6) {
                int i7 = i6;
                while (true) {
                    if (i7 >= size) {
                        ssaInsn = null;
                        break;
                    }
                    ssaInsn = list.get(i7);
                    if (checkRegUsed(bitSet, ssaInsn.getResult()) && checkRegUsed(bitSet2, ssaInsn.getSources().get(0))) {
                        Collections.swap(list, i6, i7);
                        break;
                    }
                    i7++;
                }
                RegisterSpec result = ssaInsn.getResult();
                RegisterSpec withReg = result.withReg(this.parent.borrowSpareRegister(result.getCategory()));
                int i8 = i6 + 1;
                list.add(i6, new NormalSsaInsn(new PlainInsn(Rops.opMove(result.getType()), SourcePosition.NO_INFO, withReg, ssaInsn.getSources()), this));
                list.set(i8, new NormalSsaInsn(new PlainInsn(Rops.opMove(result.getType()), SourcePosition.NO_INFO, result, RegisterSpecList.make(withReg)), this));
                i = list.size();
                i6 = i8;
            } else {
                i = size;
            }
            bitSet.clear();
            bitSet2.clear();
            i3 = i6;
            size = i;
        }
    }

    public void addLiveOut(int i) {
        if (this.liveOut == null) {
            this.liveOut = SetFactory.makeLivenessSet(this.parent.getRegCount());
        }
        this.liveOut.add(i);
    }

    public void addLiveIn(int i) {
        if (this.liveIn == null) {
            this.liveIn = SetFactory.makeLivenessSet(this.parent.getRegCount());
        }
        this.liveIn.add(i);
    }

    public IntSet getLiveInRegs() {
        if (this.liveIn == null) {
            this.liveIn = SetFactory.makeLivenessSet(this.parent.getRegCount());
        }
        return this.liveIn;
    }

    public IntSet getLiveOutRegs() {
        if (this.liveOut == null) {
            this.liveOut = SetFactory.makeLivenessSet(this.parent.getRegCount());
        }
        return this.liveOut;
    }

    public boolean isExitBlock() {
        return this.index == this.parent.getExitBlockIndex();
    }

    public boolean isReachable() {
        if (this.reachable == -1) {
            this.parent.computeReachability();
        }
        return this.reachable == 1;
    }

    public void setReachable(int i) {
        this.reachable = i;
    }

    public void scheduleMovesFromPhis() {
        if (this.movesFromPhisAtBeginning > 1) {
            scheduleUseBeforeAssigned(this.insns.subList(0, this.movesFromPhisAtBeginning));
            if (this.insns.get(this.movesFromPhisAtBeginning).isMoveException()) {
                throw new RuntimeException("Unexpected: moves from phis before move-exception");
            }
        }
        if (this.movesFromPhisAtEnd > 1) {
            scheduleUseBeforeAssigned(this.insns.subList((this.insns.size() - this.movesFromPhisAtEnd) - 1, this.insns.size() - 1));
        }
        this.parent.returnSpareRegisters();
    }

    public void forEachInsn(SsaInsn.Visitor visitor) {
        int size = this.insns.size();
        for (int i = 0; i < size; i++) {
            this.insns.get(i).accept(visitor);
        }
    }

    public String toString() {
        return "{" + this.index + ":" + Hex.u2(this.ropLabel) + '}';
    }

    public interface Visitor {
        void visitBlock(SsaBasicBlock ssaBasicBlock, SsaBasicBlock ssaBasicBlock2);
    }

    public static final class LabelComparator implements Comparator<SsaBasicBlock> {
        public int compare(SsaBasicBlock ssaBasicBlock, SsaBasicBlock ssaBasicBlock2) {
            int i = ssaBasicBlock.ropLabel;
            int i2 = ssaBasicBlock2.ropLabel;
            if (i < i2) {
                return -1;
            }
            if (i > i2) {
                return 1;
            }
            return 0;
        }
    }
}
