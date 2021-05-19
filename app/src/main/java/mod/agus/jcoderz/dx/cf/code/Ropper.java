package mod.agus.jcoderz.dx.cf.code;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import mod.agus.jcoderz.dx.cf.iface.MethodList;
import mod.agus.jcoderz.dx.rop.code.BasicBlock;
import mod.agus.jcoderz.dx.rop.code.BasicBlockList;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.InsnList;
import mod.agus.jcoderz.dx.rop.code.PlainCstInsn;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.RopMethod;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn;
import mod.agus.jcoderz.dx.rop.code.ThrowingInsn;
import mod.agus.jcoderz.dx.rop.code.TranslationAdvice;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.Bits;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.IntList;

public final class Ropper {
    private static final int PARAM_ASSIGNMENT = -1;
    private static final int RETURN = -2;
    private static final int SPECIAL_LABEL_COUNT = 7;
    private static final int SYNCH_CATCH_1 = -6;
    private static final int SYNCH_CATCH_2 = -7;
    private static final int SYNCH_RETURN = -3;
    private static final int SYNCH_SETUP_1 = -4;
    private static final int SYNCH_SETUP_2 = -5;
    private final ByteBlockList blocks;
    private final CatchInfo[] catchInfos;
    private final ExceptionSetupLabelAllocator exceptionSetupLabelAllocator;
    private final RopperMachine machine;
    private final int maxLabel;
    private final int maxLocals;
    private final ConcreteMethod method;
    private final ArrayList<BasicBlock> result;
    private final ArrayList<IntList> resultSubroutines;
    private final Simulator sim;
    private final Frame[] startFrames;
    private final Subroutine[] subroutines;
    private boolean hasSubroutines;
    private boolean synchNeedsExceptionHandler;

    private Ropper(ConcreteMethod concreteMethod, TranslationAdvice translationAdvice, MethodList methodList) {
        if (concreteMethod == null) {
            throw new NullPointerException("method == null");
        } else if (translationAdvice == null) {
            throw new NullPointerException("advice == null");
        } else {
            this.method = concreteMethod;
            this.blocks = BasicBlocker.identifyBlocks(concreteMethod);
            this.maxLabel = this.blocks.getMaxLabel();
            this.maxLocals = concreteMethod.getMaxLocals();
            this.machine = new RopperMachine(this, concreteMethod, translationAdvice, methodList);
            this.sim = new Simulator(this.machine, concreteMethod);
            this.startFrames = new Frame[this.maxLabel];
            this.subroutines = new Subroutine[this.maxLabel];
            this.result = new ArrayList<>((this.blocks.size() * 2) + 10);
            this.resultSubroutines = new ArrayList<>((this.blocks.size() * 2) + 10);
            this.catchInfos = new CatchInfo[this.maxLabel];
            this.synchNeedsExceptionHandler = false;
            this.startFrames[0] = new Frame(this.maxLocals, concreteMethod.getMaxStack());
            this.exceptionSetupLabelAllocator = new ExceptionSetupLabelAllocator();
        }
    }

    public static RopMethod convert(ConcreteMethod concreteMethod, TranslationAdvice translationAdvice, MethodList methodList) {
        try {
            Ropper ropper = new Ropper(concreteMethod, translationAdvice, methodList);
            ropper.doit();
            return ropper.getRopMethod();
        } catch (SimException e) {
            e.addContext("...while working on method " + concreteMethod.getNat().toHuman());
            throw e;
        }
    }

    public int getFirstTempStackReg() {
        int normalRegCount = getNormalRegCount();
        return isSynchronized() ? normalRegCount + 1 : normalRegCount;
    }

    private int getSpecialLabel(int i) {
        return this.maxLabel + this.method.getCatches().size() + (i ^ -1);
    }

    private int getMinimumUnreservedLabel() {
        return this.maxLabel + this.method.getCatches().size() + 7;
    }

    private int getAvailableLabel() {
        int minimumUnreservedLabel = getMinimumUnreservedLabel();
        Iterator<BasicBlock> it = this.result.iterator();
        int i = minimumUnreservedLabel;
        while (it.hasNext()) {
            int label = it.next().getLabel();
            if (label >= i) {
                i = label + 1;
            }
        }
        return i;
    }

    private boolean isSynchronized() {
        return (this.method.getAccessFlags() & 32) != 0;
    }

    private boolean isStatic() {
        return (this.method.getAccessFlags() & 8) != 0;
    }

    private int getNormalRegCount() {
        return this.maxLocals + this.method.getMaxStack();
    }

    private RegisterSpec getSynchReg() {
        int i = 1;
        int normalRegCount = getNormalRegCount();
        if (normalRegCount >= 1) {
            i = normalRegCount;
        }
        return RegisterSpec.make(i, Type.OBJECT);
    }

    private int labelToResultIndex(int i) {
        int size = this.result.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (this.result.get(i2).getLabel() == i) {
                return i2;
            }
        }
        return -1;
    }

    private BasicBlock labelToBlock(int i) {
        int labelToResultIndex = labelToResultIndex(i);
        if (labelToResultIndex >= 0) {
            return this.result.get(labelToResultIndex);
        }
        throw new IllegalArgumentException("no such label " + Hex.u2(i));
    }

    private void addBlock(BasicBlock basicBlock, IntList intList) {
        if (basicBlock == null) {
            throw new NullPointerException("block == null");
        }
        this.result.add(basicBlock);
        intList.throwIfMutable();
        this.resultSubroutines.add(intList);
    }

    private boolean addOrReplaceBlock(BasicBlock basicBlock, IntList intList) {
        boolean z;
        if (basicBlock == null) {
            throw new NullPointerException("block == null");
        }
        int labelToResultIndex = labelToResultIndex(basicBlock.getLabel());
        if (labelToResultIndex < 0) {
            z = false;
        } else {
            removeBlockAndSpecialSuccessors(labelToResultIndex);
            z = true;
        }
        this.result.add(basicBlock);
        intList.throwIfMutable();
        this.resultSubroutines.add(intList);
        return z;
    }

    private boolean addOrReplaceBlockNoDelete(BasicBlock basicBlock, IntList intList) {
        boolean z;
        if (basicBlock == null) {
            throw new NullPointerException("block == null");
        }
        int labelToResultIndex = labelToResultIndex(basicBlock.getLabel());
        if (labelToResultIndex < 0) {
            z = false;
        } else {
            this.result.remove(labelToResultIndex);
            this.resultSubroutines.remove(labelToResultIndex);
            z = true;
        }
        this.result.add(basicBlock);
        intList.throwIfMutable();
        this.resultSubroutines.add(intList);
        return z;
    }

    private void removeBlockAndSpecialSuccessors(int i) {
        int minimumUnreservedLabel = getMinimumUnreservedLabel();
        IntList successors = this.result.get(i).getSuccessors();
        int size = successors.size();
        this.result.remove(i);
        this.resultSubroutines.remove(i);
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = successors.get(i2);
            if (i3 >= minimumUnreservedLabel) {
                int labelToResultIndex = labelToResultIndex(i3);
                if (labelToResultIndex < 0) {
                    throw new RuntimeException("Invalid label " + Hex.u2(i3));
                }
                removeBlockAndSpecialSuccessors(labelToResultIndex);
            }
        }
    }

    private RopMethod getRopMethod() {
        int size = this.result.size();
        BasicBlockList basicBlockList = new BasicBlockList(size);
        for (int i = 0; i < size; i++) {
            basicBlockList.set(i, this.result.get(i));
        }
        basicBlockList.setImmutable();
        return new RopMethod(basicBlockList, getSpecialLabel(-1));
    }

    private void doit() {
        int[] makeBitSet = Bits.makeBitSet(this.maxLabel);
        Bits.set(makeBitSet, 0);
        addSetupBlocks();
        setFirstFrame();
        while (true) {
            int findFirst = Bits.findFirst(makeBitSet, 0);
            if (findFirst < 0) {
                break;
            }
            Bits.clear(makeBitSet, findFirst);
            try {
                processBlock(this.blocks.labelToBlock(findFirst), this.startFrames[findFirst], makeBitSet);
            } catch (SimException e) {
                e.addContext("...while working on block " + Hex.u2(findFirst));
                throw e;
            }
        }
        addReturnBlock();
        addSynchExceptionHandlerBlock();
        addExceptionSetupBlocks();
        if (this.hasSubroutines) {
            inlineSubroutines();
        }
    }

    private void setFirstFrame() {
        this.startFrames[0].initializeWithParameters(this.method.getEffectiveDescriptor().getParameterTypes());
        this.startFrames[0].setImmutable();
    }

    private void processBlock(ByteBlock byteBlock, Frame frame, int[] iArr) {
        int i;
        IntList intList;
        int i2;
        IntList intList2;
        int i3;
        IntList intList3;
        Insn insn;
        SourcePosition position;
        IntList intList4;
        ByteCatchList catches = byteBlock.getCatches();
        this.machine.startBlock(catches.toRopCatchList());
        Frame copy = frame.copy();
        this.sim.simulate(byteBlock, copy);
        copy.setImmutable();
        int extraBlockCount = this.machine.getExtraBlockCount();
        ArrayList<Insn> insns = this.machine.getInsns();
        int size = insns.size();
        int size2 = catches.size();
        IntList successors = byteBlock.getSuccessors();
        Subroutine subroutine = null;
        if (this.machine.hasJsr()) {
            i = 1;
            int i4 = successors.get(1);
            if (this.subroutines[i4] == null) {
                this.subroutines[i4] = new Subroutine(i4);
            }
            this.subroutines[i4].addCallerBlock(byteBlock.getLabel());
            subroutine = this.subroutines[i4];
            intList = successors;
        } else if (this.machine.hasRet()) {
            int subroutineAddress = this.machine.getReturnAddress().getSubroutineAddress();
            if (this.subroutines[subroutineAddress] == null) {
                this.subroutines[subroutineAddress] = new Subroutine(this, subroutineAddress, byteBlock.getLabel());
            } else {
                this.subroutines[subroutineAddress].addRetBlock(byteBlock.getLabel());
            }
            IntList successors2 = this.subroutines[subroutineAddress].getSuccessors();
            this.subroutines[subroutineAddress].mergeToSuccessors(copy, iArr);
            i = successors2.size();
            intList = successors2;
        } else if (this.machine.wereCatchesUsed()) {
            i = size2;
            intList = successors;
        } else {
            i = 0;
            intList = successors;
        }
        int size3 = intList.size();
        for (int i5 = i; i5 < size3; i5++) {
            int i6 = intList.get(i5);
            try {
                mergeAndWorkAsNecessary(i6, byteBlock.getLabel(), subroutine, copy, iArr);
            } catch (SimException e) {
                e.addContext("...while merging to block " + Hex.u2(i6));
                throw e;
            }
        }
        if (size3 != 0 || !this.machine.returns()) {
            i2 = size3;
            intList2 = intList;
        } else {
            i2 = 1;
            intList2 = IntList.makeImmutable(getSpecialLabel(-2));
        }
        if (i2 == 0) {
            i3 = -1;
        } else {
            int primarySuccessorIndex = this.machine.getPrimarySuccessorIndex();
            if (primarySuccessorIndex >= 0) {
                i3 = intList2.get(primarySuccessorIndex);
            } else {
                i3 = primarySuccessorIndex;
            }
        }
        boolean z = isSynchronized() && this.machine.canThrow();
        if (z || size2 != 0) {
            IntList intList5 = new IntList(i2);
            boolean z2 = false;
            int i7 = 0;
            while (i7 < size2) {
                ByteCatchList.Item item = catches.get(i7);
                CstType exceptionClass = item.getExceptionClass();
                int handlerPc = item.getHandlerPc();
                boolean z3 = z2 | (exceptionClass == CstType.OBJECT);
                try {
                    mergeAndWorkAsNecessary(handlerPc, byteBlock.getLabel(), null, copy.makeExceptionHandlerStartFrame(exceptionClass), iArr);
                    CatchInfo catchInfo = this.catchInfos[handlerPc];
                    if (catchInfo == null) {
                        catchInfo = new CatchInfo(this, null);
                        this.catchInfos[handlerPc] = catchInfo;
                    }
                    intList5.add(catchInfo.getSetup(exceptionClass.getClassType()).getLabel());
                    i7++;
                    z2 = z3;
                } catch (SimException e2) {
                    e2.addContext("...while merging exception to block " + Hex.u2(handlerPc));
                    throw e2;
                }
            }
            if (z && !z2) {
                intList5.add(getSpecialLabel(SYNCH_CATCH_1));
                this.synchNeedsExceptionHandler = true;
                for (int i8 = (size - extraBlockCount) - 1; i8 < size; i8++) {
                    Insn insn2 = insns.get(i8);
                    if (insn2.canThrow()) {
                        insns.set(i8, insn2.withAddedCatch(Type.OBJECT));
                    }
                }
            }
            if (i3 >= 0) {
                intList5.add(i3);
            }
            intList5.setImmutable();
            intList3 = intList5;
        } else {
            intList3 = intList2;
        }
        int indexOf = intList3.indexOf(i3);
        int i9 = i3;
        IntList intList6 = intList3;
        int i10 = extraBlockCount;
        int i11 = size;
        while (i10 > 0) {
            int i12 = i11 - 1;
            Insn insn3 = insns.get(i12);
            boolean z4 = insn3.getOpcode().getBranchingness() == 1;
            InsnList insnList = new InsnList(z4 ? 2 : 1);
            insnList.set(0, insn3);
            if (z4) {
                insnList.set(1, new PlainInsn(Rops.GOTO, insn3.getPosition(), (RegisterSpec) null, RegisterSpecList.EMPTY));
                intList4 = IntList.makeImmutable(i9);
            } else {
                intList4 = intList6;
            }
            insnList.setImmutable();
            int availableLabel = getAvailableLabel();
            addBlock(new BasicBlock(availableLabel, insnList, intList4, i9), copy.getSubroutines());
            intList6 = intList6.mutableCopy();
            intList6.set(indexOf, availableLabel);
            intList6.setImmutable();
            i9 = availableLabel;
            i10--;
            i11 = i12;
        }
        if (i11 == 0) {
            insn = null;
        } else {
            insn = insns.get(i11 - 1);
        }
        if (insn == null || insn.getOpcode().getBranchingness() == 1) {
            if (insn == null) {
                position = SourcePosition.NO_INFO;
            } else {
                position = insn.getPosition();
            }
            insns.add(new PlainInsn(Rops.GOTO, position, (RegisterSpec) null, RegisterSpecList.EMPTY));
            i11++;
        }
        InsnList insnList2 = new InsnList(i11);
        for (int i13 = 0; i13 < i11; i13++) {
            insnList2.set(i13, insns.get(i13));
        }
        insnList2.setImmutable();
        addOrReplaceBlock(new BasicBlock(byteBlock.getLabel(), insnList2, intList6, i9), copy.getSubroutines());
    }

    private void mergeAndWorkAsNecessary(int i, int i2, Subroutine subroutine, Frame frame, int[] iArr) {
        Frame mergeWith;
        Frame frame2 = this.startFrames[i];
        if (frame2 != null) {
            if (subroutine != null) {
                mergeWith = frame2.mergeWithSubroutineCaller(frame, subroutine.getStartBlock(), i2);
            } else {
                mergeWith = frame2.mergeWith(frame);
            }
            if (mergeWith != frame2) {
                this.startFrames[i] = mergeWith;
                Bits.set(iArr, i);
                return;
            }
            return;
        }
        if (subroutine != null) {
            this.startFrames[i] = frame.makeNewSubroutineStartFrame(i, i2);
        } else {
            this.startFrames[i] = frame;
        }
        Bits.set(iArr, i);
    }

    private void addSetupBlocks() {
        int i;
        InsnList insnList;
        RegisterSpec makeLocalOptional;
        LocalVariableList localVariables = this.method.getLocalVariables();
        SourcePosition makeSourcePosistion = this.method.makeSourcePosistion(0);
        StdTypeList parameterTypes = this.method.getEffectiveDescriptor().getParameterTypes();
        int size = parameterTypes.size();
        InsnList insnList2 = new InsnList(size + 1);
        int i2 = 0;
        int i3 = 0;
        while (i2 < size) {
            Type type = parameterTypes.get(i2);
            LocalVariableList.Item pcAndIndexToLocal = localVariables.pcAndIndexToLocal(0, i3);
            if (pcAndIndexToLocal == null) {
                makeLocalOptional = RegisterSpec.make(i3, type);
            } else {
                makeLocalOptional = RegisterSpec.makeLocalOptional(i3, type, pcAndIndexToLocal.getLocalItem());
            }
            insnList2.set(i2, new PlainCstInsn(Rops.opMoveParam(type), makeSourcePosistion, makeLocalOptional, RegisterSpecList.EMPTY, CstInteger.make(i3)));
            i2++;
            i3 += type.getCategory();
        }
        insnList2.set(size, new PlainInsn(Rops.GOTO, makeSourcePosistion, (RegisterSpec) null, RegisterSpecList.EMPTY));
        insnList2.setImmutable();
        boolean isSynchronized = isSynchronized();
        if (isSynchronized) {
            i = getSpecialLabel(-4);
        } else {
            i = 0;
        }
        addBlock(new BasicBlock(getSpecialLabel(-1), insnList2, IntList.makeImmutable(i), i), IntList.EMPTY);
        if (isSynchronized) {
            RegisterSpec synchReg = getSynchReg();
            if (isStatic()) {
                ThrowingCstInsn throwingCstInsn = new ThrowingCstInsn(Rops.CONST_OBJECT, makeSourcePosistion, RegisterSpecList.EMPTY, StdTypeList.EMPTY, this.method.getDefiningClass());
                InsnList insnList3 = new InsnList(1);
                insnList3.set(0, throwingCstInsn);
                insnList = insnList3;
            } else {
                InsnList insnList4 = new InsnList(2);
                insnList4.set(0, new PlainCstInsn(Rops.MOVE_PARAM_OBJECT, makeSourcePosistion, synchReg, RegisterSpecList.EMPTY, CstInteger.VALUE_0));
                insnList4.set(1, new PlainInsn(Rops.GOTO, makeSourcePosistion, (RegisterSpec) null, RegisterSpecList.EMPTY));
                insnList = insnList4;
            }
            int specialLabel = getSpecialLabel(SYNCH_SETUP_2);
            insnList.setImmutable();
            addBlock(new BasicBlock(i, insnList, IntList.makeImmutable(specialLabel), specialLabel), IntList.EMPTY);
            InsnList insnList5 = new InsnList(isStatic() ? 2 : 1);
            if (isStatic()) {
                insnList5.set(0, new PlainInsn(Rops.opMoveResultPseudo(synchReg), makeSourcePosistion, synchReg, RegisterSpecList.EMPTY));
            }
            insnList5.set(isStatic() ? 1 : 0, new ThrowingInsn(Rops.MONITOR_ENTER, makeSourcePosistion, RegisterSpecList.make(synchReg), StdTypeList.EMPTY));
            insnList5.setImmutable();
            addBlock(new BasicBlock(specialLabel, insnList5, IntList.makeImmutable(0), 0), IntList.EMPTY);
        }
    }

    private void addReturnBlock() {
        int i;
        RegisterSpecList make;
        Rop returnOp = this.machine.getReturnOp();
        if (returnOp != null) {
            SourcePosition returnPosition = this.machine.getReturnPosition();
            int specialLabel = getSpecialLabel(-2);
            if (isSynchronized()) {
                InsnList insnList = new InsnList(1);
                insnList.set(0, new ThrowingInsn(Rops.MONITOR_EXIT, returnPosition, RegisterSpecList.make(getSynchReg()), StdTypeList.EMPTY));
                insnList.setImmutable();
                i = getSpecialLabel(-3);
                addBlock(new BasicBlock(specialLabel, insnList, IntList.makeImmutable(i), i), IntList.EMPTY);
            } else {
                i = specialLabel;
            }
            InsnList insnList2 = new InsnList(1);
            TypeList sources = returnOp.getSources();
            if (sources.size() == 0) {
                make = RegisterSpecList.EMPTY;
            } else {
                make = RegisterSpecList.make(RegisterSpec.make(0, sources.getType(0)));
            }
            insnList2.set(0, new PlainInsn(returnOp, returnPosition, (RegisterSpec) null, make));
            insnList2.setImmutable();
            addBlock(new BasicBlock(i, insnList2, IntList.EMPTY, -1), IntList.EMPTY);
        }
    }

    private void addSynchExceptionHandlerBlock() {
        if (this.synchNeedsExceptionHandler) {
            SourcePosition makeSourcePosistion = this.method.makeSourcePosistion(0);
            RegisterSpec make = RegisterSpec.make(0, Type.THROWABLE);
            InsnList insnList = new InsnList(2);
            insnList.set(0, new PlainInsn(Rops.opMoveException(Type.THROWABLE), makeSourcePosistion, make, RegisterSpecList.EMPTY));
            insnList.set(1, new ThrowingInsn(Rops.MONITOR_EXIT, makeSourcePosistion, RegisterSpecList.make(getSynchReg()), StdTypeList.EMPTY));
            insnList.setImmutable();
            int specialLabel = getSpecialLabel(SYNCH_CATCH_2);
            addBlock(new BasicBlock(getSpecialLabel(SYNCH_CATCH_1), insnList, IntList.makeImmutable(specialLabel), specialLabel), IntList.EMPTY);
            InsnList insnList2 = new InsnList(1);
            insnList2.set(0, new ThrowingInsn(Rops.THROW, makeSourcePosistion, RegisterSpecList.make(make), StdTypeList.EMPTY));
            insnList2.setImmutable();
            addBlock(new BasicBlock(specialLabel, insnList2, IntList.EMPTY, -1), IntList.EMPTY);
        }
    }

    private void addExceptionSetupBlocks() {
        int length = this.catchInfos.length;
        for (int i = 0; i < length; i++) {
            CatchInfo catchInfo = this.catchInfos[i];
            if (catchInfo != null) {
                for (ExceptionHandlerSetup exceptionHandlerSetup : catchInfo.getSetups()) {
                    SourcePosition position = labelToBlock(i).getFirstInsn().getPosition();
                    InsnList insnList = new InsnList(2);
                    insnList.set(0, new PlainInsn(Rops.opMoveException(exceptionHandlerSetup.getCaughtType()), position, RegisterSpec.make(this.maxLocals, exceptionHandlerSetup.getCaughtType()), RegisterSpecList.EMPTY));
                    insnList.set(1, new PlainInsn(Rops.GOTO, position, (RegisterSpec) null, RegisterSpecList.EMPTY));
                    insnList.setImmutable();
                    addBlock(new BasicBlock(exceptionHandlerSetup.getLabel(), insnList, IntList.makeImmutable(i), i), this.startFrames[i].getSubroutines());
                }
            }
        }
    }

    private boolean isSubroutineCaller(BasicBlock basicBlock) {
        int i;
        IntList successors = basicBlock.getSuccessors();
        return successors.size() >= 2 && (i = successors.get(1)) < this.subroutines.length && this.subroutines[i] != null;
    }

    private void inlineSubroutines() {
        final IntList intList = new IntList(4);
        forEachNonSubBlockDepthFirst(0, new BasicBlock.Visitor() {

            @Override // mod.agus.jcoderz.dx.rop.code.BasicBlock.Visitor
            public void visitBlock(BasicBlock basicBlock) {
                if (Ropper.this.isSubroutineCaller(basicBlock)) {
                    intList.add(basicBlock.getLabel());
                }
            }
        });
        int availableLabel = getAvailableLabel();
        ArrayList arrayList = new ArrayList(availableLabel);
        for (int i = 0; i < availableLabel; i++) {
            arrayList.add(null);
        }
        for (int i2 = 0; i2 < this.result.size(); i2++) {
            BasicBlock basicBlock = this.result.get(i2);
            if (basicBlock != null) {
                arrayList.set(basicBlock.getLabel(), this.resultSubroutines.get(i2));
            }
        }
        int size = intList.size();
        for (int i3 = 0; i3 < size; i3++) {
            new SubroutineInliner(new LabelAllocator(getAvailableLabel()), arrayList).inlineSubroutineCalledFrom(labelToBlock(intList.get(i3)));
        }
        deleteUnreachableBlocks();
    }

    private void deleteUnreachableBlocks() {
        final IntList intList = new IntList(this.result.size());
        this.resultSubroutines.clear();
        forEachNonSubBlockDepthFirst(getSpecialLabel(-1), new BasicBlock.Visitor() {

            @Override // mod.agus.jcoderz.dx.rop.code.BasicBlock.Visitor
            public void visitBlock(BasicBlock basicBlock) {
                intList.add(basicBlock.getLabel());
            }
        });
        intList.sort();
        for (int size = this.result.size() - 1; size >= 0; size--) {
            if (intList.indexOf(this.result.get(size).getLabel()) < 0) {
                this.result.remove(size);
            }
        }
    }

    private Subroutine subroutineFromRetBlock(int i) {
        for (int length = this.subroutines.length - 1; length >= 0; length--) {
            if (this.subroutines[length] != null) {
                Subroutine subroutine = this.subroutines[length];
                if (subroutine.retBlocks.get(i)) {
                    return subroutine;
                }
            }
        }
        return null;
    }

    private InsnList filterMoveReturnAddressInsns(InsnList insnList) {
        int i;
        int i2 = 0;
        int size = insnList.size();
        int i3 = 0;
        for (int i4 = 0; i4 < size; i4++) {
            if (insnList.get(i4).getOpcode() != Rops.MOVE_RETURN_ADDRESS) {
                i3++;
            }
        }
        if (i3 == size) {
            return insnList;
        }
        InsnList insnList2 = new InsnList(i3);
        int i5 = 0;
        while (i5 < size) {
            Insn insn = insnList.get(i5);
            if (insn.getOpcode() != Rops.MOVE_RETURN_ADDRESS) {
                i = i2 + 1;
                insnList2.set(i2, insn);
            } else {
                i = i2;
            }
            i5++;
            i2 = i;
        }
        insnList2.setImmutable();
        return insnList2;
    }

    private void forEachNonSubBlockDepthFirst(int i, BasicBlock.Visitor visitor) {
        forEachNonSubBlockDepthFirst0(labelToBlock(i), visitor, new BitSet(this.maxLabel));
    }

    private void forEachNonSubBlockDepthFirst0(BasicBlock basicBlock, BasicBlock.Visitor visitor, BitSet bitSet) {
        int labelToResultIndex;
        visitor.visitBlock(basicBlock);
        bitSet.set(basicBlock.getLabel());
        IntList successors = basicBlock.getSuccessors();
        int size = successors.size();
        for (int i = 0; i < size; i++) {
            int i2 = successors.get(i);
            if (!bitSet.get(i2) && ((!isSubroutineCaller(basicBlock) || i <= 0) && (labelToResultIndex = labelToResultIndex(i2)) >= 0)) {
                forEachNonSubBlockDepthFirst0(this.result.get(labelToResultIndex), visitor, bitSet);
            }
        }
    }

    public static class ExceptionHandlerSetup {
        private final Type caughtType;
        private final int label;

        ExceptionHandlerSetup(Type type, int i) {
            this.caughtType = type;
            this.label = i;
        }

        public Type getCaughtType() {
            return this.caughtType;
        }

        public int getLabel() {
            return this.label;
        }
    }

    public static class LabelAllocator {
        int nextAvailableLabel;

        LabelAllocator(int i) {
            this.nextAvailableLabel = i;
        }

        public int getNextLabel() {
            int i = this.nextAvailableLabel;
            this.nextAvailableLabel = i + 1;
            return i;
        }
    }

    public class CatchInfo {
        private final Map<Type, ExceptionHandlerSetup> setups;

        private CatchInfo() {
            this.setups = new HashMap();
        }

        CatchInfo(Ropper ropper, CatchInfo catchInfo) {
            this();
        }

        public ExceptionHandlerSetup getSetup(Type type) {
            ExceptionHandlerSetup exceptionHandlerSetup = this.setups.get(type);
            if (exceptionHandlerSetup != null) {
                return exceptionHandlerSetup;
            }
            ExceptionHandlerSetup exceptionHandlerSetup2 = new ExceptionHandlerSetup(type, Ropper.this.exceptionSetupLabelAllocator.getNextLabel());
            this.setups.put(type, exceptionHandlerSetup2);
            return exceptionHandlerSetup2;
        }

        public Collection<ExceptionHandlerSetup> getSetups() {
            return this.setups.values();
        }
    }

    public class Subroutine {
        private final BitSet callerBlocks;
        private final BitSet retBlocks;
        private final int startBlock;

        Subroutine(int i) {
            this.startBlock = i;
            this.retBlocks = new BitSet(Ropper.this.maxLabel);
            this.callerBlocks = new BitSet(Ropper.this.maxLabel);
            Ropper.this.hasSubroutines = true;
        }

        Subroutine(Ropper ropper, int i, int i2) {
            this(i);
            addRetBlock(i2);
        }

        public int getStartBlock() {
            return this.startBlock;
        }

        public void addRetBlock(int i) {
            this.retBlocks.set(i);
        }

        public void addCallerBlock(int i) {
            this.callerBlocks.set(i);
        }

        public IntList getSuccessors() {
            IntList intList = new IntList(this.callerBlocks.size());
            int nextSetBit = this.callerBlocks.nextSetBit(0);
            while (nextSetBit >= 0) {
                intList.add(Ropper.this.labelToBlock(nextSetBit).getSuccessors().get(0));
                nextSetBit = this.callerBlocks.nextSetBit(nextSetBit + 1);
            }
            intList.setImmutable();
            return intList;
        }

        public void mergeToSuccessors(Frame frame, int[] iArr) {
            int nextSetBit = this.callerBlocks.nextSetBit(0);
            while (nextSetBit >= 0) {
                int i = Ropper.this.labelToBlock(nextSetBit).getSuccessors().get(0);
                Frame subFrameForLabel = frame.subFrameForLabel(this.startBlock, nextSetBit);
                if (subFrameForLabel != null) {
                    Ropper.this.mergeAndWorkAsNecessary(i, -1, null, subFrameForLabel, iArr);
                } else {
                    Bits.set(iArr, nextSetBit);
                }
                nextSetBit = this.callerBlocks.nextSetBit(nextSetBit + 1);
            }
        }
    }

    public class ExceptionSetupLabelAllocator extends LabelAllocator {
        int maxSetupLabel;

        ExceptionSetupLabelAllocator() {
            super(Ropper.this.maxLabel);
            this.maxSetupLabel = Ropper.this.maxLabel + Ropper.this.method.getCatches().size();
        }

        @Override // mod.agus.jcoderz.dx.cf.code.Ropper.LabelAllocator
        public int getNextLabel() {
            if (this.nextAvailableLabel >= this.maxSetupLabel) {
                throw new IndexOutOfBoundsException();
            }
            int i = this.nextAvailableLabel;
            this.nextAvailableLabel = i + 1;
            return i;
        }
    }

    public class SubroutineInliner {
        private final LabelAllocator labelAllocator;
        private final ArrayList<IntList> labelToSubroutines;
        private final HashMap<Integer, Integer> origLabelToCopiedLabel = new HashMap<>();
        private final BitSet workList;
        private int subroutineStart;
        private int subroutineSuccessor;

        SubroutineInliner(LabelAllocator labelAllocator2, ArrayList<IntList> arrayList) {
            this.workList = new BitSet(Ropper.this.maxLabel);
            this.labelAllocator = labelAllocator2;
            this.labelToSubroutines = arrayList;
        }

        public void inlineSubroutineCalledFrom(BasicBlock basicBlock) {
            this.subroutineSuccessor = basicBlock.getSuccessors().get(0);
            this.subroutineStart = basicBlock.getSuccessors().get(1);
            int mapOrAllocateLabel = mapOrAllocateLabel(this.subroutineStart);
            int nextSetBit = this.workList.nextSetBit(0);
            while (nextSetBit >= 0) {
                this.workList.clear(nextSetBit);
                int intValue = this.origLabelToCopiedLabel.get(Integer.valueOf(nextSetBit)).intValue();
                copyBlock(nextSetBit, intValue);
                if (Ropper.this.isSubroutineCaller(Ropper.this.labelToBlock(nextSetBit))) {
                    new SubroutineInliner(this.labelAllocator, this.labelToSubroutines).inlineSubroutineCalledFrom(Ropper.this.labelToBlock(intValue));
                }
                nextSetBit = this.workList.nextSetBit(0);
            }
            Ropper.this.addOrReplaceBlockNoDelete(new BasicBlock(basicBlock.getLabel(), basicBlock.getInsns(), IntList.makeImmutable(mapOrAllocateLabel), mapOrAllocateLabel), this.labelToSubroutines.get(basicBlock.getLabel()));
        }

        private void copyBlock(int i, int i2) {
            IntList intList;
            BasicBlock labelToBlock = Ropper.this.labelToBlock(i);
            IntList successors = labelToBlock.getSuccessors();
            int i3 = -1;
            if (Ropper.this.isSubroutineCaller(labelToBlock)) {
                intList = IntList.makeImmutable(mapOrAllocateLabel(successors.get(0)), successors.get(1));
            } else {
                Subroutine subroutineFromRetBlock = Ropper.this.subroutineFromRetBlock(i);
                if (subroutineFromRetBlock == null) {
                    int primarySuccessor = labelToBlock.getPrimarySuccessor();
                    int size = successors.size();
                    IntList intList2 = new IntList(size);
                    int i4 = 0;
                    while (i4 < size) {
                        int i5 = successors.get(i4);
                        int mapOrAllocateLabel = mapOrAllocateLabel(i5);
                        intList2.add(mapOrAllocateLabel);
                        if (primarySuccessor != i5) {
                            mapOrAllocateLabel = i3;
                        }
                        i4++;
                        i3 = mapOrAllocateLabel;
                    }
                    intList2.setImmutable();
                    intList = intList2;
                } else if (subroutineFromRetBlock.startBlock != this.subroutineStart) {
                    throw new RuntimeException("ret instruction returns to label " + Hex.u2(subroutineFromRetBlock.startBlock) + " expected: " + Hex.u2(this.subroutineStart));
                } else {
                    intList = IntList.makeImmutable(this.subroutineSuccessor);
                    i3 = this.subroutineSuccessor;
                }
            }
            Ropper.this.addBlock(new BasicBlock(i2, Ropper.this.filterMoveReturnAddressInsns(labelToBlock.getInsns()), intList, i3), this.labelToSubroutines.get(i2));
        }

        private boolean involvedInSubroutine(int i, int i2) {
            IntList intList = this.labelToSubroutines.get(i);
            return intList != null && intList.size() > 0 && intList.top() == i2;
        }

        private int mapOrAllocateLabel(int i) {
            Integer num = this.origLabelToCopiedLabel.get(Integer.valueOf(i));
            if (num != null) {
                return num.intValue();
            }
            if (!involvedInSubroutine(i, this.subroutineStart)) {
                return i;
            }
            int nextLabel = this.labelAllocator.getNextLabel();
            this.workList.set(i);
            this.origLabelToCopiedLabel.put(Integer.valueOf(i), Integer.valueOf(nextLabel));
            while (this.labelToSubroutines.size() <= nextLabel) {
                this.labelToSubroutines.add(null);
            }
            this.labelToSubroutines.set(nextLabel, this.labelToSubroutines.get(i));
            return nextLabel;
        }
    }
}
