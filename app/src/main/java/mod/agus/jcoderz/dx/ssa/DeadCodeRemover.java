package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.ssa.SsaInsn;

public class DeadCodeRemover {
    private int regCount;
    private SsaMethod ssaMeth;
    private final ArrayList<SsaInsn>[] useList = this.ssaMeth.getUseListCopy();
    private final BitSet worklist = new BitSet(this.regCount);

    public static void process(SsaMethod ssaMethod) {
        new DeadCodeRemover(ssaMethod).run();
    }

    private DeadCodeRemover(SsaMethod ssaMethod) {
        this.ssaMeth = ssaMethod;
        this.regCount = ssaMethod.getRegCount();
    }

    private void run() {
        pruneDeadInstructions();
        HashSet hashSet = new HashSet();
        this.ssaMeth.forEachInsn(new NoSideEffectVisitor(this.worklist));
        while (true) {
            int nextSetBit = this.worklist.nextSetBit(0);
            if (nextSetBit < 0) {
                this.ssaMeth.deleteInsns(hashSet);
                return;
            }
            this.worklist.clear(nextSetBit);
            if (this.useList[nextSetBit].size() == 0 || isCircularNoSideEffect(nextSetBit, null)) {
                SsaInsn definitionForRegister = this.ssaMeth.getDefinitionForRegister(nextSetBit);
                if (!hashSet.contains(definitionForRegister)) {
                    RegisterSpecList sources = definitionForRegister.getSources();
                    int size = sources.size();
                    for (int i = 0; i < size; i++) {
                        RegisterSpec registerSpec = sources.get(i);
                        this.useList[registerSpec.getReg()].remove(definitionForRegister);
                        if (!hasSideEffect(this.ssaMeth.getDefinitionForRegister(registerSpec.getReg()))) {
                            this.worklist.set(registerSpec.getReg());
                        }
                    }
                    hashSet.add(definitionForRegister);
                }
            }
        }
    }

    private void pruneDeadInstructions() {
        HashSet hashSet = new HashSet();
        this.ssaMeth.computeReachability();
        Iterator<SsaBasicBlock> it = this.ssaMeth.getBlocks().iterator();
        while (it.hasNext()) {
            SsaBasicBlock next = it.next();
            if (!next.isReachable()) {
                for (int i = 0; i < next.getInsns().size(); i++) {
                    SsaInsn ssaInsn = next.getInsns().get(i);
                    RegisterSpecList sources = ssaInsn.getSources();
                    int size = sources.size();
                    if (size != 0) {
                        hashSet.add(ssaInsn);
                    }
                    for (int i2 = 0; i2 < size; i2++) {
                        this.useList[sources.get(i2).getReg()].remove(ssaInsn);
                    }
                    RegisterSpec result = ssaInsn.getResult();
                    if (result != null) {
                        Iterator<SsaInsn> it2 = this.useList[result.getReg()].iterator();
                        while (it2.hasNext()) {
                            SsaInsn next2 = it2.next();
                            if (next2 instanceof PhiInsn) {
                                ((PhiInsn) next2).removePhiRegister(result);
                            }
                        }
                    }
                }
            }
        }
        this.ssaMeth.deleteInsns(hashSet);
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0044  */
    private boolean isCircularNoSideEffect(int i, BitSet bitSet) {
        if (bitSet != null && bitSet.get(i)) {
            return true;
        }
        Iterator<SsaInsn> it = this.useList[i].iterator();
        while (it.hasNext()) {
            if (hasSideEffect(it.next())) {
                return false;
            }
        }
        if (bitSet == null) {
            bitSet = new BitSet(this.regCount);
        }
        bitSet.set(i);
        Iterator<SsaInsn> it2 = this.useList[i].iterator();
        while (it2.hasNext()) {
            RegisterSpec result = it2.next().getResult();
            if (result == null || !isCircularNoSideEffect(result.getReg(), bitSet)) {
                return false;
            }
            while (it2.hasNext()) {
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static boolean hasSideEffect(SsaInsn ssaInsn) {
        if (ssaInsn == null) {
            return true;
        }
        return ssaInsn.hasSideEffect();
    }

    /* access modifiers changed from: private */
    public static class NoSideEffectVisitor implements SsaInsn.Visitor {
        BitSet noSideEffectRegs;

        public NoSideEffectVisitor(BitSet bitSet) {
            this.noSideEffectRegs = bitSet;
        }

        @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
        public void visitMoveInsn(NormalSsaInsn normalSsaInsn) {
            if (!DeadCodeRemover.hasSideEffect(normalSsaInsn)) {
                this.noSideEffectRegs.set(normalSsaInsn.getResult().getReg());
            }
        }

        @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
        public void visitPhiInsn(PhiInsn phiInsn) {
            if (!DeadCodeRemover.hasSideEffect(phiInsn)) {
                this.noSideEffectRegs.set(phiInsn.getResult().getReg());
            }
        }

        @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
        public void visitNonMoveInsn(NormalSsaInsn normalSsaInsn) {
            RegisterSpec result = normalSsaInsn.getResult();
            if (!DeadCodeRemover.hasSideEffect(normalSsaInsn) && result != null) {
                this.noSideEffectRegs.set(result.getReg());
            }
        }
    }
}
