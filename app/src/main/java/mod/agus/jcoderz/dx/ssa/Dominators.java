package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import mod.agus.jcoderz.dx.ssa.DomFront;
import mod.agus.jcoderz.dx.ssa.SsaBasicBlock;

public final class Dominators {
    private ArrayList<SsaBasicBlock> blocks;
    private final DomFront.DomInfo[] domInfos;
    private final DFSInfo[] info = new DFSInfo[(this.blocks.size() + 2)];
    private final SsaMethod meth;
    private final boolean postdom;
    private final ArrayList<SsaBasicBlock> vertex = new ArrayList<>();

    /* access modifiers changed from: private */
    public static final class DFSInfo {
        public SsaBasicBlock ancestor;
        public ArrayList<SsaBasicBlock> bucket = new ArrayList<>();
        public SsaBasicBlock parent;
        public SsaBasicBlock rep;
        public int semidom;
    }

    private Dominators(SsaMethod ssaMethod, DomFront.DomInfo[] domInfoArr, boolean z) {
        this.meth = ssaMethod;
        this.domInfos = domInfoArr;
        this.postdom = z;
        this.blocks = ssaMethod.getBlocks();
    }

    public static Dominators make(SsaMethod ssaMethod, DomFront.DomInfo[] domInfoArr, boolean z) {
        Dominators dominators = new Dominators(ssaMethod, domInfoArr, z);
        dominators.run();
        return dominators;
    }

    private BitSet getSuccs(SsaBasicBlock ssaBasicBlock) {
        if (this.postdom) {
            return ssaBasicBlock.getPredecessors();
        }
        return ssaBasicBlock.getSuccessors();
    }

    private BitSet getPreds(SsaBasicBlock ssaBasicBlock) {
        if (this.postdom) {
            return ssaBasicBlock.getSuccessors();
        }
        return ssaBasicBlock.getPredecessors();
    }

    private void compress(SsaBasicBlock ssaBasicBlock) {
        if (this.info[this.info[ssaBasicBlock.getIndex()].ancestor.getIndex()].ancestor != null) {
            ArrayList arrayList = new ArrayList();
            HashSet hashSet = new HashSet();
            arrayList.add(ssaBasicBlock);
            while (!arrayList.isEmpty()) {
                int size = arrayList.size();
                DFSInfo dFSInfo = this.info[((SsaBasicBlock) arrayList.get(size - 1)).getIndex()];
                SsaBasicBlock ssaBasicBlock2 = dFSInfo.ancestor;
                DFSInfo dFSInfo2 = this.info[ssaBasicBlock2.getIndex()];
                if (!hashSet.add(ssaBasicBlock2) || dFSInfo2.ancestor == null) {
                    arrayList.remove(size - 1);
                    if (dFSInfo2.ancestor != null) {
                        SsaBasicBlock ssaBasicBlock3 = dFSInfo2.rep;
                        if (this.info[ssaBasicBlock3.getIndex()].semidom < this.info[dFSInfo.rep.getIndex()].semidom) {
                            dFSInfo.rep = ssaBasicBlock3;
                        }
                        dFSInfo.ancestor = dFSInfo2.ancestor;
                    }
                } else {
                    arrayList.add(ssaBasicBlock2);
                }
            }
        }
    }

    private SsaBasicBlock eval(SsaBasicBlock ssaBasicBlock) {
        DFSInfo dFSInfo = this.info[ssaBasicBlock.getIndex()];
        if (dFSInfo.ancestor == null) {
            return ssaBasicBlock;
        }
        compress(ssaBasicBlock);
        return dFSInfo.rep;
    }

    private void run() {
        SsaBasicBlock entryBlock;
        int i;
        if (this.postdom) {
            entryBlock = this.meth.getExitBlock();
        } else {
            entryBlock = this.meth.getEntryBlock();
        }
        if (entryBlock != null) {
            this.vertex.add(entryBlock);
            this.domInfos[entryBlock.getIndex()].idom = entryBlock.getIndex();
        }
        this.meth.forEachBlockDepthFirst(this.postdom, new DfsWalker(this, null));
        int size = this.vertex.size() - 1;
        for (int i2 = size; i2 >= 2; i2--) {
            SsaBasicBlock ssaBasicBlock = this.vertex.get(i2);
            DFSInfo dFSInfo = this.info[ssaBasicBlock.getIndex()];
            BitSet preds = getPreds(ssaBasicBlock);
            for (int nextSetBit = preds.nextSetBit(0); nextSetBit >= 0; nextSetBit = preds.nextSetBit(nextSetBit + 1)) {
                SsaBasicBlock ssaBasicBlock2 = this.blocks.get(nextSetBit);
                if (this.info[ssaBasicBlock2.getIndex()] != null && (i = this.info[eval(ssaBasicBlock2).getIndex()].semidom) < dFSInfo.semidom) {
                    dFSInfo.semidom = i;
                }
            }
            this.info[this.vertex.get(dFSInfo.semidom).getIndex()].bucket.add(ssaBasicBlock);
            dFSInfo.ancestor = dFSInfo.parent;
            ArrayList<SsaBasicBlock> arrayList = this.info[dFSInfo.parent.getIndex()].bucket;
            while (!arrayList.isEmpty()) {
                SsaBasicBlock remove = arrayList.remove(arrayList.size() - 1);
                SsaBasicBlock eval = eval(remove);
                if (this.info[eval.getIndex()].semidom < this.info[remove.getIndex()].semidom) {
                    this.domInfos[remove.getIndex()].idom = eval.getIndex();
                } else {
                    this.domInfos[remove.getIndex()].idom = dFSInfo.parent.getIndex();
                }
            }
        }
        for (int i3 = 2; i3 <= size; i3++) {
            SsaBasicBlock ssaBasicBlock3 = this.vertex.get(i3);
            if (this.domInfos[ssaBasicBlock3.getIndex()].idom != this.vertex.get(this.info[ssaBasicBlock3.getIndex()].semidom).getIndex()) {
                this.domInfos[ssaBasicBlock3.getIndex()].idom = this.domInfos[this.domInfos[ssaBasicBlock3.getIndex()].idom].idom;
            }
        }
    }

    /* access modifiers changed from: private */
    public class DfsWalker implements SsaBasicBlock.Visitor {
        private int dfsNum;

        private DfsWalker() {
            this.dfsNum = 0;
        }

        /* synthetic */ DfsWalker(Dominators dominators, DfsWalker dfsWalker) {
            this();
        }

        @Override // mod.agus.jcoderz.dx.ssa.SsaBasicBlock.Visitor
        public void visitBlock(SsaBasicBlock ssaBasicBlock, SsaBasicBlock ssaBasicBlock2) {
            DFSInfo dFSInfo = new DFSInfo();
            int i = this.dfsNum + 1;
            this.dfsNum = i;
            dFSInfo.semidom = i;
            dFSInfo.rep = ssaBasicBlock;
            dFSInfo.parent = ssaBasicBlock2;
            Dominators.this.vertex.add(ssaBasicBlock);
            Dominators.this.info[ssaBasicBlock.getIndex()] = dFSInfo;
        }
    }
}
