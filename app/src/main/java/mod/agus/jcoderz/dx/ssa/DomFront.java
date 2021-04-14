package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import mod.agus.jcoderz.dx.util.IntSet;

public class DomFront {
    private static boolean DEBUG = false;
    private final DomInfo[] domInfos;
    private final SsaMethod meth;
    private final ArrayList<SsaBasicBlock> nodes;

    public static class DomInfo {
        public IntSet dominanceFrontiers;
        public int idom = -1;
    }

    public DomFront(SsaMethod ssaMethod) {
        this.meth = ssaMethod;
        this.nodes = ssaMethod.getBlocks();
        int size = this.nodes.size();
        this.domInfos = new DomInfo[size];
        for (int i = 0; i < size; i++) {
            this.domInfos[i] = new DomInfo();
        }
    }

    public DomInfo[] run() {
        int size = this.nodes.size();
        if (DEBUG) {
            for (int i = 0; i < size; i++) {
                System.out.println("pred[" + i + "]: " + this.nodes.get(i).getPredecessors());
            }
        }
        Dominators.make(this.meth, this.domInfos, false);
        if (DEBUG) {
            for (int i2 = 0; i2 < size; i2++) {
                System.out.println("idom[" + i2 + "]: " + this.domInfos[i2].idom);
            }
        }
        buildDomTree();
        if (DEBUG) {
            debugPrintDomChildren();
        }
        for (int i3 = 0; i3 < size; i3++) {
            this.domInfos[i3].dominanceFrontiers = SetFactory.makeDomFrontSet(size);
        }
        calcDomFronts();
        if (DEBUG) {
            for (int i4 = 0; i4 < size; i4++) {
                System.out.println("df[" + i4 + "]: " + this.domInfos[i4].dominanceFrontiers);
            }
        }
        return this.domInfos;
    }

    private void debugPrintDomChildren() {
        int size = this.nodes.size();
        for (int i = 0; i < size; i++) {
            SsaBasicBlock ssaBasicBlock = this.nodes.get(i);
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append('{');
            Iterator<SsaBasicBlock> it = ssaBasicBlock.getDomChildren().iterator();
            boolean z = false;
            while (it.hasNext()) {
                SsaBasicBlock next = it.next();
                if (z) {
                    stringBuffer.append(',');
                }
                stringBuffer.append(next);
                z = true;
            }
            stringBuffer.append('}');
            System.out.println("domChildren[" + ssaBasicBlock + "]: " + ((Object) stringBuffer));
        }
    }

    private void buildDomTree() {
        int size = this.nodes.size();
        for (int i = 0; i < size; i++) {
            DomInfo domInfo = this.domInfos[i];
            if (domInfo.idom != -1) {
                this.nodes.get(domInfo.idom).addDomChild(this.nodes.get(i));
            }
        }
    }

    private void calcDomFronts() {
        int size = this.nodes.size();
        for (int i = 0; i < size; i++) {
            DomInfo domInfo = this.domInfos[i];
            BitSet predecessors = this.nodes.get(i).getPredecessors();
            if (predecessors.cardinality() > 1) {
                for (int nextSetBit = predecessors.nextSetBit(0); nextSetBit >= 0; nextSetBit = predecessors.nextSetBit(nextSetBit + 1)) {
                    int i2 = nextSetBit;
                    while (i2 != domInfo.idom && i2 != -1) {
                        DomInfo domInfo2 = this.domInfos[i2];
                        if (domInfo2.dominanceFrontiers.has(i)) {
                            break;
                        }
                        domInfo2.dominanceFrontiers.add(i);
                        i2 = domInfo2.idom;
                    }
                }
            }
        }
    }
}
