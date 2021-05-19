package mod.agus.jcoderz.dx.cf.code;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstMemberRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Bits;
import mod.agus.jcoderz.dx.util.IntList;

public final class BasicBlocker implements BytecodeArray.Visitor {
    private final int[] blockSet;
    private final ByteCatchList[] catchLists;
    private final int[] liveSet;
    private final ConcreteMethod method;
    private final IntList[] targetLists;
    private final int[] workSet;
    private int previousOffset;

    private BasicBlocker(ConcreteMethod concreteMethod) {
        if (concreteMethod == null) {
            throw new NullPointerException("method == null");
        }
        this.method = concreteMethod;
        int size = concreteMethod.getCode().size() + 1;
        this.workSet = Bits.makeBitSet(size);
        this.liveSet = Bits.makeBitSet(size);
        this.blockSet = Bits.makeBitSet(size);
        this.targetLists = new IntList[size];
        this.catchLists = new ByteCatchList[size];
        this.previousOffset = -1;
    }

    public static ByteBlockList identifyBlocks(ConcreteMethod concreteMethod) {
        BasicBlocker basicBlocker = new BasicBlocker(concreteMethod);
        basicBlocker.doit();
        return basicBlocker.getBlockList();
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void visitInvalid(int i, int i2, int i3) {
        visitCommon(i2, i3, true);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void visitNoArgs(int i, int i2, int i3, Type type) {
        switch (i) {
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 79:
            case 80:
            case 81:
            case 82:
            case 83:
            case 84:
            case 85:
            case 86:
            case 190:
            case 194:
            case 195:
                visitCommon(i2, i3, true);
                visitThrowing(i2, i3, true);
                return;
            case 108:
            case 112:
                visitCommon(i2, i3, true);
                if (type == Type.INT || type == Type.LONG) {
                    visitThrowing(i2, i3, true);
                    return;
                }
                return;
            case 172:
            case 177:
                visitCommon(i2, i3, false);
                this.targetLists[i2] = IntList.EMPTY;
                return;
            case 191:
                visitCommon(i2, i3, false);
                visitThrowing(i2, i3, false);
                return;
            default:
                visitCommon(i2, i3, true);
                return;
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void visitLocal(int i, int i2, int i3, int i4, Type type, int i5) {
        if (i == 169) {
            visitCommon(i2, i3, false);
            this.targetLists[i2] = IntList.EMPTY;
            return;
        }
        visitCommon(i2, i3, true);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void visitConstant(int i, int i2, int i3, Constant constant, int i4) {
        visitCommon(i2, i3, true);
        if ((constant instanceof CstMemberRef) || (constant instanceof CstType) || (constant instanceof CstString)) {
            visitThrowing(i2, i3, true);
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void visitBranch(int i, int i2, int i3, int i4) {
        switch (i) {
            case 167:
                visitCommon(i2, i3, false);
                this.targetLists[i2] = IntList.makeImmutable(i4);
                break;
            case 168:
                addWorkIfNecessary(i2, true);
            default:
                int i5 = i2 + i3;
                visitCommon(i2, i3, true);
                addWorkIfNecessary(i5, true);
                this.targetLists[i2] = IntList.makeImmutable(i5, i4);
                break;
        }
        addWorkIfNecessary(i4, true);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void visitSwitch(int i, int i2, int i3, SwitchList switchList, int i4) {
        visitCommon(i2, i3, false);
        addWorkIfNecessary(switchList.getDefaultTarget(), true);
        int size = switchList.size();
        for (int i5 = 0; i5 < size; i5++) {
            addWorkIfNecessary(switchList.getTarget(i5), true);
        }
        this.targetLists[i2] = switchList.getTargets();
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void visitNewarray(int i, int i2, CstType cstType, ArrayList<Constant> arrayList) {
        visitCommon(i, i2, true);
        visitThrowing(i, i2, true);
    }

    private ByteBlockList getBlockList() {
        int i;
        ByteCatchList byteCatchList;
        ByteBlock[] byteBlockArr = new ByteBlock[this.method.getCode().size()];
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int findFirst = Bits.findFirst(this.blockSet, i2 + 1);
            if (findFirst < 0) {
                break;
            }
            if (Bits.get(this.liveSet, i2)) {
                IntList intList = null;
                int i4 = findFirst - 1;
                while (true) {
                    if (i4 < i2) {
                        i4 = -1;
                        break;
                    }
                    intList = this.targetLists[i4];
                    if (intList != null) {
                        break;
                    }
                    i4--;
                }
                if (intList == null) {
                    intList = IntList.makeImmutable(findFirst);
                    byteCatchList = ByteCatchList.EMPTY;
                } else {
                    byteCatchList = this.catchLists[i4];
                    if (byteCatchList == null) {
                        byteCatchList = ByteCatchList.EMPTY;
                    }
                }
                byteBlockArr[i3] = new ByteBlock(i2, i2, findFirst, intList, byteCatchList);
                i = i3 + 1;
            } else {
                i = i3;
            }
            i2 = findFirst;
            i3 = i;
        }
        ByteBlockList byteBlockList = new ByteBlockList(i3);
        for (int i5 = 0; i5 < i3; i5++) {
            byteBlockList.set(i5, byteBlockArr[i5]);
        }
        return byteBlockList;
    }

    private void doit() {
        BytecodeArray code = this.method.getCode();
        ByteCatchList catches = this.method.getCatches();
        int size = catches.size();
        Bits.set(this.workSet, 0);
        Bits.set(this.blockSet, 0);
        while (!Bits.isEmpty(this.workSet)) {
            try {
                code.processWorkSet(this.workSet, this);
                for (int i = 0; i < size; i++) {
                    ByteCatchList.Item item = catches.get(i);
                    int startPc = item.getStartPc();
                    int endPc = item.getEndPc();
                    if (Bits.anyInRange(this.liveSet, startPc, endPc)) {
                        Bits.set(this.blockSet, startPc);
                        Bits.set(this.blockSet, endPc);
                        addWorkIfNecessary(item.getHandlerPc(), true);
                    }
                }
            } catch (IllegalArgumentException e) {
                throw new SimException("flow of control falls off end of method", e);
            }
        }
    }

    private void addWorkIfNecessary(int i, boolean z) {
        if (!Bits.get(this.liveSet, i)) {
            Bits.set(this.workSet, i);
        }
        if (z) {
            Bits.set(this.blockSet, i);
        }
    }

    private void visitCommon(int i, int i2, boolean z) {
        Bits.set(this.liveSet, i);
        if (z) {
            addWorkIfNecessary(i + i2, false);
        } else {
            Bits.set(this.blockSet, i + i2);
        }
    }

    private void visitThrowing(int i, int i2, boolean z) {
        int i3 = i + i2;
        if (z) {
            addWorkIfNecessary(i3, true);
        }
        ByteCatchList listFor = this.method.getCatches().listFor(i);
        this.catchLists[i] = listFor;
        IntList[] intListArr = this.targetLists;
        if (!z) {
            i3 = -1;
        }
        intListArr[i] = listFor.toTargetList(i3);
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public int getPreviousOffset() {
        return this.previousOffset;
    }

    @Override // mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor
    public void setPreviousOffset(int i) {
        this.previousOffset = i;
    }
}
