package mod.agus.jcoderz.dx.ssa.back;

import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.ssa.PhiInsn;
import mod.agus.jcoderz.dx.ssa.SsaBasicBlock;
import mod.agus.jcoderz.dx.ssa.SsaInsn;
import mod.agus.jcoderz.dx.ssa.SsaMethod;

public class LivenessAnalyzer {
    private static int[] $SWITCH_TABLE$mod$agus$jcoderz$dx$ssa$back$LivenessAnalyzer$NextFunction;
    private SsaBasicBlock blockN;
    private final InterferenceGraph interference;
    private final BitSet liveOutBlocks;
    private NextFunction nextFunction;
    private final int regV;
    private final SsaMethod ssaMeth;
    private int statementIndex;
    private final BitSet visitedBlocks;

    /* access modifiers changed from: private */
    public enum NextFunction {
        LIVE_IN_AT_STATEMENT,
        LIVE_OUT_AT_STATEMENT,
        LIVE_OUT_AT_BLOCK,
        DONE
    }

    static int[] $SWITCH_TABLE$mod$agus$jcoderz$dx$ssa$back$LivenessAnalyzer$NextFunction() {
        int[] iArr = $SWITCH_TABLE$mod$agus$jcoderz$dx$ssa$back$LivenessAnalyzer$NextFunction;
        if (iArr == null) {
            iArr = new int[NextFunction.values().length];
            try {
                iArr[NextFunction.DONE.ordinal()] = 4;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[NextFunction.LIVE_IN_AT_STATEMENT.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[NextFunction.LIVE_OUT_AT_BLOCK.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[NextFunction.LIVE_OUT_AT_STATEMENT.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            $SWITCH_TABLE$mod$agus$jcoderz$dx$ssa$back$LivenessAnalyzer$NextFunction = iArr;
        }
        return iArr;
    }

    public static InterferenceGraph constructInterferenceGraph(SsaMethod ssaMethod) {
        int regCount = ssaMethod.getRegCount();
        InterferenceGraph interferenceGraph = new InterferenceGraph(regCount);
        for (int i = 0; i < regCount; i++) {
            new LivenessAnalyzer(ssaMethod, i, interferenceGraph).run();
        }
        coInterferePhis(ssaMethod, interferenceGraph);
        return interferenceGraph;
    }

    private LivenessAnalyzer(SsaMethod ssaMethod, int i, InterferenceGraph interferenceGraph) {
        int size = ssaMethod.getBlocks().size();
        this.ssaMeth = ssaMethod;
        this.regV = i;
        this.visitedBlocks = new BitSet(size);
        this.liveOutBlocks = new BitSet(size);
        this.interference = interferenceGraph;
    }

    private void handleTailRecursion() {
        while (this.nextFunction != NextFunction.DONE) {
            switch ($SWITCH_TABLE$mod$agus$jcoderz$dx$ssa$back$LivenessAnalyzer$NextFunction()[this.nextFunction.ordinal()]) {
                case 1:
                    this.nextFunction = NextFunction.DONE;
                    liveInAtStatement();
                    break;
                case 2:
                    this.nextFunction = NextFunction.DONE;
                    liveOutAtStatement();
                    break;
                case 3:
                    this.nextFunction = NextFunction.DONE;
                    liveOutAtBlock();
                    break;
            }
        }
    }

    public void run() {
        for (SsaInsn ssaInsn : this.ssaMeth.getUseListForRegister(this.regV)) {
            this.nextFunction = NextFunction.DONE;
            if (ssaInsn instanceof PhiInsn) {
                for (SsaBasicBlock ssaBasicBlock : ((PhiInsn) ssaInsn).predBlocksForReg(this.regV, this.ssaMeth)) {
                    this.blockN = ssaBasicBlock;
                    this.nextFunction = NextFunction.LIVE_OUT_AT_BLOCK;
                    handleTailRecursion();
                }
            } else {
                this.blockN = ssaInsn.getBlock();
                this.statementIndex = this.blockN.getInsns().indexOf(ssaInsn);
                if (this.statementIndex < 0) {
                    throw new RuntimeException("insn not found in it's own block");
                }
                this.nextFunction = NextFunction.LIVE_IN_AT_STATEMENT;
                handleTailRecursion();
            }
        }
        while (true) {
            int nextSetBit = this.liveOutBlocks.nextSetBit(0);
            if (nextSetBit >= 0) {
                this.blockN = this.ssaMeth.getBlocks().get(nextSetBit);
                this.liveOutBlocks.clear(nextSetBit);
                this.nextFunction = NextFunction.LIVE_OUT_AT_BLOCK;
                handleTailRecursion();
            } else {
                return;
            }
        }
    }

    private void liveOutAtBlock() {
        if (!this.visitedBlocks.get(this.blockN.getIndex())) {
            this.visitedBlocks.set(this.blockN.getIndex());
            this.blockN.addLiveOut(this.regV);
            this.statementIndex = this.blockN.getInsns().size() - 1;
            this.nextFunction = NextFunction.LIVE_OUT_AT_STATEMENT;
        }
    }

    private void liveInAtStatement() {
        if (this.statementIndex == 0) {
            this.blockN.addLiveIn(this.regV);
            this.liveOutBlocks.or(this.blockN.getPredecessors());
            return;
        }
        this.statementIndex--;
        this.nextFunction = NextFunction.LIVE_OUT_AT_STATEMENT;
    }

    private void liveOutAtStatement() {
        SsaInsn ssaInsn = this.blockN.getInsns().get(this.statementIndex);
        RegisterSpec result = ssaInsn.getResult();
        if (!ssaInsn.isResultReg(this.regV)) {
            if (result != null) {
                this.interference.add(this.regV, result.getReg());
            }
            this.nextFunction = NextFunction.LIVE_IN_AT_STATEMENT;
        }
    }

    private static void coInterferePhis(SsaMethod ssaMethod, InterferenceGraph interferenceGraph) {
        Iterator<SsaBasicBlock> it = ssaMethod.getBlocks().iterator();
        while (it.hasNext()) {
            List<SsaInsn> phiInsns = it.next().getPhiInsns();
            int size = phiInsns.size();
            for (int i = 0; i < size; i++) {
                for (int i2 = 0; i2 < size; i2++) {
                    if (i != i2) {
                        interferenceGraph.add(phiInsns.get(i).getResult().getReg(), phiInsns.get(i2).getResult().getReg());
                    }
                }
            }
        }
    }
}
