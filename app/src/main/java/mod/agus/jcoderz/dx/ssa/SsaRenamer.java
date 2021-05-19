package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.IntList;

public class SsaRenamer implements Runnable {
    private static final boolean DEBUG = false;
    private final int ropRegCount;
    private final SsaMethod ssaMeth;
    private final ArrayList<LocalItem> ssaRegToLocalItems;
    private final RegisterSpec[][] startsForBlocks;
    private int nextSsaReg;
    private IntList ssaRegToRopReg;
    private int threshold;

    public SsaRenamer(SsaMethod ssaMethod) {
        this.ropRegCount = ssaMethod.getRegCount();
        this.ssaMeth = ssaMethod;
        this.nextSsaReg = this.ropRegCount;
        this.threshold = 0;
        this.startsForBlocks = new RegisterSpec[ssaMethod.getBlocks().size()][];
        this.ssaRegToLocalItems = new ArrayList<>();
        RegisterSpec[] registerSpecArr = new RegisterSpec[this.ropRegCount];
        for (int i = 0; i < this.ropRegCount; i++) {
            registerSpecArr[i] = RegisterSpec.make(i, Type.VOID);
        }
        this.startsForBlocks[ssaMethod.getEntryBlockIndex()] = registerSpecArr;
    }

    public SsaRenamer(SsaMethod ssaMethod, int i) {
        this(ssaMethod);
        this.threshold = i;
    }

    public static RegisterSpec[] dupArray(RegisterSpec[] registerSpecArr) {
        RegisterSpec[] registerSpecArr2 = new RegisterSpec[registerSpecArr.length];
        System.arraycopy(registerSpecArr, 0, registerSpecArr2, 0, registerSpecArr.length);
        return registerSpecArr2;
    }

    public static boolean equalsHandlesNulls(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    public void run() {
        this.ssaMeth.forEachBlockDepthFirstDom(new SsaBasicBlock.Visitor() {

            @Override // mod.agus.jcoderz.dx.ssa.SsaBasicBlock.Visitor
            public void visitBlock(SsaBasicBlock ssaBasicBlock, SsaBasicBlock ssaBasicBlock2) {
                new BlockRenamer(ssaBasicBlock).process();
            }
        });
        this.ssaMeth.setNewRegCount(this.nextSsaReg);
        this.ssaMeth.onInsnsChanged();
    }

    private LocalItem getLocalForNewReg(int i) {
        if (i < this.ssaRegToLocalItems.size()) {
            return this.ssaRegToLocalItems.get(i);
        }
        return null;
    }

    private void setNameForSsaReg(RegisterSpec registerSpec) {
        int reg = registerSpec.getReg();
        LocalItem localItem = registerSpec.getLocalItem();
        this.ssaRegToLocalItems.ensureCapacity(reg + 1);
        while (this.ssaRegToLocalItems.size() <= reg) {
            this.ssaRegToLocalItems.add(null);
        }
        this.ssaRegToLocalItems.set(reg, localItem);
    }

    private boolean isBelowThresholdRegister(int i) {
        return i < this.threshold;
    }

    private boolean isVersionZeroRegister(int i) {
        return i < this.ropRegCount;
    }

    private class BlockRenamer implements SsaInsn.Visitor {
        private final SsaBasicBlock block;
        private final RegisterSpec[] currentMapping;
        private final HashMap<SsaInsn, SsaInsn> insnsToReplace = new HashMap<>();
        private final RenamingMapper mapper = new RenamingMapper();
        private final HashSet<SsaInsn> movesToKeep = new HashSet<>();

        BlockRenamer(SsaBasicBlock ssaBasicBlock) {
            this.block = ssaBasicBlock;
            this.currentMapping = SsaRenamer.this.startsForBlocks[ssaBasicBlock.getIndex()];
            SsaRenamer.this.startsForBlocks[ssaBasicBlock.getIndex()] = null;
        }

        public void process() {
            RegisterSpec[] dupArray;
            this.block.forEachInsn(this);
            updateSuccessorPhis();
            ArrayList<SsaInsn> insns = this.block.getInsns();
            for (int size = insns.size() - 1; size >= 0; size--) {
                SsaInsn ssaInsn = insns.get(size);
                SsaInsn ssaInsn2 = this.insnsToReplace.get(ssaInsn);
                if (ssaInsn2 != null) {
                    insns.set(size, ssaInsn2);
                } else if (ssaInsn.isNormalMoveInsn() && !this.movesToKeep.contains(ssaInsn)) {
                    insns.remove(size);
                }
            }
            Iterator<SsaBasicBlock> it = this.block.getDomChildren().iterator();
            boolean z = true;
            while (it.hasNext()) {
                SsaBasicBlock next = it.next();
                if (next != this.block) {
                    if (z) {
                        dupArray = this.currentMapping;
                    } else {
                        dupArray = SsaRenamer.dupArray(this.currentMapping);
                    }
                    SsaRenamer.this.startsForBlocks[next.getIndex()] = dupArray;
                    z = false;
                }
            }
        }

        private void addMapping(int i, RegisterSpec registerSpec) {
            int reg = registerSpec.getReg();
            LocalItem localItem = registerSpec.getLocalItem();
            this.currentMapping[i] = registerSpec;
            for (int length = this.currentMapping.length - 1; length >= 0; length--) {
                if (reg == this.currentMapping[length].getReg()) {
                    this.currentMapping[length] = registerSpec;
                }
            }
            if (localItem != null) {
                SsaRenamer.this.setNameForSsaReg(registerSpec);
                for (int length2 = this.currentMapping.length - 1; length2 >= 0; length2--) {
                    RegisterSpec registerSpec2 = this.currentMapping[length2];
                    if (reg != registerSpec2.getReg() && localItem.equals(registerSpec2.getLocalItem())) {
                        this.currentMapping[length2] = registerSpec2.withLocalItem(null);
                    }
                }
            }
        }

        @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
        public void visitPhiInsn(PhiInsn phiInsn) {
            processResultReg(phiInsn);
        }

        @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
        public void visitMoveInsn(NormalSsaInsn normalSsaInsn) {
            boolean z = false;
            RegisterSpec result = normalSsaInsn.getResult();
            int reg = result.getReg();
            int reg2 = normalSsaInsn.getSources().get(0).getReg();
            normalSsaInsn.mapSourceRegisters(this.mapper);
            int reg3 = normalSsaInsn.getSources().get(0).getReg();
            LocalItem localItem = this.currentMapping[reg2].getLocalItem();
            LocalItem localItem2 = result.getLocalItem();
            if (localItem2 == null) {
                localItem2 = localItem;
            }
            LocalItem localForNewReg = SsaRenamer.this.getLocalForNewReg(reg3);
            if (localForNewReg == null || localItem2 == null || localItem2.equals(localForNewReg)) {
                z = true;
            }
            RegisterSpec makeLocalOptional = RegisterSpec.makeLocalOptional(reg3, result.getType(), localItem2);
            if (!Optimizer.getPreserveLocals() || (z && SsaRenamer.equalsHandlesNulls(localItem2, localItem) && SsaRenamer.this.threshold == 0)) {
                addMapping(reg, makeLocalOptional);
            } else if (z && localItem == null && SsaRenamer.this.threshold == 0) {
                this.insnsToReplace.put(normalSsaInsn, SsaInsn.makeFromRop(new PlainInsn(Rops.opMarkLocal(makeLocalOptional), SourcePosition.NO_INFO, (RegisterSpec) null, RegisterSpecList.make(RegisterSpec.make(makeLocalOptional.getReg(), makeLocalOptional.getType(), localItem2))), this.block));
                addMapping(reg, makeLocalOptional);
            } else {
                processResultReg(normalSsaInsn);
                this.movesToKeep.add(normalSsaInsn);
            }
        }

        @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
        public void visitNonMoveInsn(NormalSsaInsn normalSsaInsn) {
            normalSsaInsn.mapSourceRegisters(this.mapper);
            processResultReg(normalSsaInsn);
        }

        /* access modifiers changed from: package-private */
        public void processResultReg(SsaInsn ssaInsn) {
            RegisterSpec result = ssaInsn.getResult();
            if (result != null) {
                int reg = result.getReg();
                if (!SsaRenamer.this.isBelowThresholdRegister(reg)) {
                    ssaInsn.changeResultReg(SsaRenamer.this.nextSsaReg);
                    addMapping(reg, ssaInsn.getResult());
                    SsaRenamer.this.nextSsaReg++;
                }
            }
        }

        private void updateSuccessorPhis() {
            PhiInsn.Visitor r2 = new PhiInsn.Visitor() {

                @Override // mod.agus.jcoderz.dx.ssa.PhiInsn.Visitor
                public void visitPhiInsn(PhiInsn phiInsn) {
                    int ropResultReg = phiInsn.getRopResultReg();
                    if (!SsaRenamer.this.isBelowThresholdRegister(ropResultReg)) {
                        RegisterSpec registerSpec = BlockRenamer.this.currentMapping[ropResultReg];
                        if (!SsaRenamer.this.isVersionZeroRegister(registerSpec.getReg())) {
                            phiInsn.addPhiOperand(registerSpec, BlockRenamer.this.block);
                        }
                    }
                }
            };
            BitSet successors = this.block.getSuccessors();
            for (int nextSetBit = successors.nextSetBit(0); nextSetBit >= 0; nextSetBit = successors.nextSetBit(nextSetBit + 1)) {
                SsaRenamer.this.ssaMeth.getBlocks().get(nextSetBit).forEachPhiInsn(r2);
            }
        }

        private class RenamingMapper extends RegisterMapper {
            public RenamingMapper() {
            }

            @Override // mod.agus.jcoderz.dx.ssa.RegisterMapper
            public int getNewRegisterCount() {
                return SsaRenamer.this.nextSsaReg;
            }

            @Override // mod.agus.jcoderz.dx.ssa.RegisterMapper
            public RegisterSpec map(RegisterSpec registerSpec) {
                if (registerSpec == null) {
                    return null;
                }
                return registerSpec.withReg(BlockRenamer.this.currentMapping[registerSpec.getReg()].getReg());
            }
        }
    }
}
