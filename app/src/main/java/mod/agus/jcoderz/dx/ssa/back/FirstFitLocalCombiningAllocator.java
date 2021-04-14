package mod.agus.jcoderz.dx.ssa.back;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import mod.agus.jcoderz.dx.rop.code.CstInsn;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.ssa.InterferenceRegisterMapper;
import mod.agus.jcoderz.dx.ssa.NormalSsaInsn;
import mod.agus.jcoderz.dx.ssa.Optimizer;
import mod.agus.jcoderz.dx.ssa.PhiInsn;
import mod.agus.jcoderz.dx.ssa.RegisterMapper;
import mod.agus.jcoderz.dx.ssa.SsaInsn;
import mod.agus.jcoderz.dx.ssa.SsaMethod;
import mod.agus.jcoderz.dx.util.IntIterator;
import mod.agus.jcoderz.dx.util.IntSet;

public class FirstFitLocalCombiningAllocator extends RegisterAllocator {
    private static final boolean DEBUG = false;
    private final ArrayList<NormalSsaInsn> invokeRangeInsns;
    private final Map<LocalItem, ArrayList<RegisterSpec>> localVariables;
    private final InterferenceRegisterMapper mapper;
    private final boolean minimizeRegisters;
    private final ArrayList<NormalSsaInsn> moveResultPseudoInsns;
    private int paramRangeEnd;
    private final ArrayList<PhiInsn> phiInsns;
    private final BitSet reservedRopRegs = new BitSet(this.paramRangeEnd * 2);
    private final BitSet ssaRegsMapped;
    private final BitSet usedRopRegs;

    public enum Alignment {
        EVEN {
            /* access modifiers changed from: package-private */
            @Override // mod.agus.jcoderz.dx.ssa.back.FirstFitLocalCombiningAllocator.Alignment
            public int nextClearBit(BitSet bitSet, int i) {
                int nextClearBit = bitSet.nextClearBit(i);
                while (!FirstFitLocalCombiningAllocator.isEven(nextClearBit)) {
                    nextClearBit = bitSet.nextClearBit(nextClearBit + 1);
                }
                return nextClearBit;
            }
        },
        ODD {
            /* access modifiers changed from: package-private */
            @Override // mod.agus.jcoderz.dx.ssa.back.FirstFitLocalCombiningAllocator.Alignment
            public int nextClearBit(BitSet bitSet, int i) {
                int nextClearBit = bitSet.nextClearBit(i);
                while (FirstFitLocalCombiningAllocator.isEven(nextClearBit)) {
                    nextClearBit = bitSet.nextClearBit(nextClearBit + 1);
                }
                return nextClearBit;
            }
        },
        UNSPECIFIED {
            /* access modifiers changed from: package-private */
            @Override // mod.agus.jcoderz.dx.ssa.back.FirstFitLocalCombiningAllocator.Alignment
            public int nextClearBit(BitSet bitSet, int i) {
                return bitSet.nextClearBit(i);
            }
        };

        Alignment() {

        }

        /* access modifiers changed from: package-private */
        public abstract int nextClearBit(BitSet bitSet, int i);

        /* synthetic */ Alignment(Alignment alignment) {
            this();
        }
    }

    public FirstFitLocalCombiningAllocator(SsaMethod ssaMethod, InterferenceGraph interferenceGraph, boolean z) {
        super(ssaMethod, interferenceGraph);
        this.ssaRegsMapped = new BitSet(ssaMethod.getRegCount());
        this.mapper = new InterferenceRegisterMapper(interferenceGraph, ssaMethod.getRegCount());
        this.minimizeRegisters = z;
        this.paramRangeEnd = ssaMethod.getParamWidth();
        this.reservedRopRegs.set(0, this.paramRangeEnd);
        this.usedRopRegs = new BitSet(this.paramRangeEnd * 2);
        this.localVariables = new TreeMap();
        this.moveResultPseudoInsns = new ArrayList<>();
        this.invokeRangeInsns = new ArrayList<>();
        this.phiInsns = new ArrayList<>();
    }

    @Override // mod.agus.jcoderz.dx.ssa.back.RegisterAllocator
    public boolean wantsParamsMovedHigh() {
        return true;
    }

    @Override // mod.agus.jcoderz.dx.ssa.back.RegisterAllocator
    public RegisterMapper allocateRegisters() {
        analyzeInstructions();
        handleLocalAssociatedParams();
        handleUnassociatedParameters();
        handleInvokeRangeInsns();
        handleLocalAssociatedOther();
        handleCheckCastResults();
        handlePhiInsns();
        handleNormalUnassociated();
        return this.mapper;
    }

    private void printLocalVars() {
        System.out.println("Printing local vars");
        for (Map.Entry<LocalItem, ArrayList<RegisterSpec>> entry : this.localVariables.entrySet()) {
            StringBuilder sb = new StringBuilder();
            sb.append('{');
            sb.append(' ');
            Iterator<RegisterSpec> it = entry.getValue().iterator();
            while (it.hasNext()) {
                sb.append('v');
                sb.append(it.next().getReg());
                sb.append(' ');
            }
            sb.append('}');
            System.out.printf("Local: %s Registers: %s\n", entry.getKey(), sb);
        }
    }

    private void handleLocalAssociatedParams() {
        int i;
        int i2;
        for (ArrayList<RegisterSpec> arrayList : this.localVariables.values()) {
            int size = arrayList.size();
            int i3 = -1;
            int i4 = 0;
            while (true) {
                if (i4 >= size) {
                    i = i3;
                    i2 = 0;
                    break;
                }
                RegisterSpec registerSpec = arrayList.get(i4);
                int parameterIndexForReg = getParameterIndexForReg(registerSpec.getReg());
                if (parameterIndexForReg >= 0) {
                    int category = registerSpec.getCategory();
                    addMapping(registerSpec, parameterIndexForReg);
                    i2 = category;
                    i = parameterIndexForReg;
                    break;
                }
                i4++;
                i3 = parameterIndexForReg;
            }
            if (i >= 0) {
                tryMapRegs(arrayList, i, i2, true);
            }
        }
    }

    private int getParameterIndexForReg(int i) {
        Rop opcode;
        SsaInsn definitionForRegister = this.ssaMeth.getDefinitionForRegister(i);
        if (definitionForRegister == null || (opcode = definitionForRegister.getOpcode()) == null || opcode.getOpcode() != 3) {
            return -1;
        }
        return ((CstInteger) ((CstInsn) definitionForRegister.getOriginalRopInsn()).getConstant()).getValue();
    }

    private void handleLocalAssociatedOther() {
        boolean z;
        int i;
        for (ArrayList<RegisterSpec> arrayList : this.localVariables.values()) {
            boolean z2 = false;
            int i2 = this.paramRangeEnd;
            while (true) {
                int size = arrayList.size();
                int i3 = 0;
                int i4 = 1;
                while (i3 < size) {
                    RegisterSpec registerSpec = arrayList.get(i3);
                    int category = registerSpec.getCategory();
                    if (this.ssaRegsMapped.get(registerSpec.getReg()) || category <= i4) {
                        i = i4;
                    } else {
                        i = category;
                    }
                    i3++;
                    i4 = i;
                }
                int findRopRegForLocal = findRopRegForLocal(i2, i4);
                if (canMapRegs(arrayList, findRopRegForLocal)) {
                    z = tryMapRegs(arrayList, findRopRegForLocal, i4, true);
                } else {
                    z = z2;
                }
                int i5 = findRopRegForLocal + 1;
                if (!z) {
                    i2 = i5;
                    z2 = z;
                }
            }
        }
    }

    private boolean tryMapRegs(ArrayList<RegisterSpec> arrayList, int i, int i2, boolean z) {
        Iterator<RegisterSpec> it = arrayList.iterator();
        boolean z2 = false;
        while (it.hasNext()) {
            RegisterSpec next = it.next();
            if (!this.ssaRegsMapped.get(next.getReg())) {
                boolean tryMapReg = tryMapReg(next, i, i2);
                if (!tryMapReg || z2) {
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (tryMapReg && z) {
                    markReserved(i, next.getCategory());
                }
            }
        }
        return !z2;
    }

    private boolean tryMapReg(RegisterSpec registerSpec, int i, int i2) {
        if (registerSpec.getCategory() > i2 || this.ssaRegsMapped.get(registerSpec.getReg()) || !canMapReg(registerSpec, i)) {
            return false;
        }
        addMapping(registerSpec, i);
        return true;
    }

    private void markReserved(int i, int i2) {
        this.reservedRopRegs.set(i, i + i2, true);
    }

    private boolean rangeContainsReserved(int i, int i2) {
        for (int i3 = i; i3 < i + i2; i3++) {
            if (this.reservedRopRegs.get(i3)) {
                return true;
            }
        }
        return false;
    }

    private boolean isThisPointerReg(int i) {
        return i == 0 && !this.ssaMeth.isStatic();
    }

    private Alignment getAlignment(int i) {
        Alignment alignment = Alignment.UNSPECIFIED;
        if (i != 2) {
            return alignment;
        }
        if (isEven(this.paramRangeEnd)) {
            return Alignment.EVEN;
        }
        return Alignment.ODD;
    }

    private int findNextUnreservedRopReg(int i, int i2) {
        return findNextUnreservedRopReg(i, i2, getAlignment(i2));
    }

    private int findNextUnreservedRopReg(int i, int i2, Alignment alignment) {
        int nextClearBit = alignment.nextClearBit(this.reservedRopRegs, i);
        while (true) {
            int i3 = 1;
            while (i3 < i2 && !this.reservedRopRegs.get(nextClearBit + i3)) {
                i3++;
            }
            if (i3 == i2) {
                return nextClearBit;
            }
            nextClearBit = alignment.nextClearBit(this.reservedRopRegs, nextClearBit + i3);
        }
    }

    private int findRopRegForLocal(int i, int i2) {
        Alignment alignment = getAlignment(i2);
        int nextClearBit = alignment.nextClearBit(this.usedRopRegs, i);
        while (true) {
            int i3 = 1;
            while (i3 < i2 && !this.usedRopRegs.get(nextClearBit + i3)) {
                i3++;
            }
            if (i3 == i2) {
                return nextClearBit;
            }
            nextClearBit = alignment.nextClearBit(this.usedRopRegs, nextClearBit + i3);
        }
    }

    private void handleUnassociatedParameters() {
        int regCount = this.ssaMeth.getRegCount();
        for (int i = 0; i < regCount; i++) {
            if (!this.ssaRegsMapped.get(i)) {
                int parameterIndexForReg = getParameterIndexForReg(i);
                RegisterSpec definitionSpecForSsaReg = getDefinitionSpecForSsaReg(i);
                if (parameterIndexForReg >= 0) {
                    addMapping(definitionSpecForSsaReg, parameterIndexForReg);
                }
            }
        }
    }

    private void handleInvokeRangeInsns() {
        Iterator<NormalSsaInsn> it = this.invokeRangeInsns.iterator();
        while (it.hasNext()) {
            adjustAndMapSourceRangeRange(it.next());
        }
    }

    private void handleCheckCastResults() {
        boolean z;
        boolean z2;
        boolean z3;
        Iterator<NormalSsaInsn> it = this.moveResultPseudoInsns.iterator();
        while (it.hasNext()) {
            NormalSsaInsn next = it.next();
            RegisterSpec result = next.getResult();
            int reg = result.getReg();
            BitSet predecessors = next.getBlock().getPredecessors();
            if (predecessors.cardinality() == 1) {
                ArrayList<SsaInsn> insns = this.ssaMeth.getBlocks().get(predecessors.nextSetBit(0)).getInsns();
                SsaInsn ssaInsn = insns.get(insns.size() - 1);
                if (ssaInsn.getOpcode().getOpcode() == 43) {
                    RegisterSpec registerSpec = ssaInsn.getSources().get(0);
                    int reg2 = registerSpec.getReg();
                    int category = registerSpec.getCategory();
                    boolean z4 = this.ssaRegsMapped.get(reg);
                    boolean z5 = this.ssaRegsMapped.get(reg2);
                    if ((!z5) && z4) {
                        z = tryMapReg(registerSpec, this.mapper.oldToNew(reg), category);
                    } else {
                        z = z5;
                    }
                    if (z4) {
                        z2 = false;
                    } else {
                        z2 = true;
                    }
                    if (z2 && z) {
                        z4 = tryMapReg(result, this.mapper.oldToNew(reg2), category);
                    }
                    if (!z4 || !z) {
                        int findNextUnreservedRopReg = findNextUnreservedRopReg(this.paramRangeEnd, category);
                        ArrayList<RegisterSpec> arrayList = new ArrayList<>(2);
                        arrayList.add(result);
                        arrayList.add(registerSpec);
                        while (!tryMapRegs(arrayList, findNextUnreservedRopReg, category, false)) {
                            findNextUnreservedRopReg = findNextUnreservedRopReg(findNextUnreservedRopReg + 1, category);
                        }
                    }
                    if (ssaInsn.getOriginalRopInsn().getCatches().size() != 0) {
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    int oldToNew = this.mapper.oldToNew(reg);
                    if (oldToNew != this.mapper.oldToNew(reg2) && !z3) {
                        ((NormalSsaInsn) ssaInsn).changeOneSource(0, insertMoveBefore(ssaInsn, registerSpec));
                        addMapping(ssaInsn.getSources().get(0), oldToNew);
                    }
                }
            }
        }
    }

    private void handlePhiInsns() {
        Iterator<PhiInsn> it = this.phiInsns.iterator();
        while (it.hasNext()) {
            processPhiInsn(it.next());
        }
    }

    private void handleNormalUnassociated() {
        RegisterSpec definitionSpecForSsaReg;
        int regCount = this.ssaMeth.getRegCount();
        for (int i = 0; i < regCount; i++) {
            if (!this.ssaRegsMapped.get(i) && (definitionSpecForSsaReg = getDefinitionSpecForSsaReg(i)) != null) {
                int category = definitionSpecForSsaReg.getCategory();
                int findNextUnreservedRopReg = findNextUnreservedRopReg(this.paramRangeEnd, category);
                while (!canMapReg(definitionSpecForSsaReg, findNextUnreservedRopReg)) {
                    findNextUnreservedRopReg = findNextUnreservedRopReg(findNextUnreservedRopReg + 1, category);
                }
                addMapping(definitionSpecForSsaReg, findNextUnreservedRopReg);
            }
        }
    }

    private boolean canMapRegs(ArrayList<RegisterSpec> arrayList, int i) {
        Iterator<RegisterSpec> it = arrayList.iterator();
        while (it.hasNext()) {
            RegisterSpec next = it.next();
            if (!this.ssaRegsMapped.get(next.getReg()) && !canMapReg(next, i)) {
                return false;
            }
        }
        return true;
    }

    private boolean canMapReg(RegisterSpec registerSpec, int i) {
        return !spansParamRange(i, registerSpec.getCategory()) && !this.mapper.interferes(registerSpec, i);
    }

    private boolean spansParamRange(int i, int i2) {
        return i < this.paramRangeEnd && i + i2 > this.paramRangeEnd;
    }

    private void analyzeInstructions() {
        this.ssaMeth.forEachInsn(new SsaInsn.Visitor() {
            /* class mod.agus.jcoderz.dx.ssa.back.FirstFitLocalCombiningAllocator.AnonymousClass1 */

            @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
            public void visitMoveInsn(NormalSsaInsn normalSsaInsn) {
                processInsn(normalSsaInsn);
            }

            @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
            public void visitPhiInsn(PhiInsn phiInsn) {
                processInsn(phiInsn);
            }

            @Override // mod.agus.jcoderz.dx.ssa.SsaInsn.Visitor
            public void visitNonMoveInsn(NormalSsaInsn normalSsaInsn) {
                processInsn(normalSsaInsn);
            }

            private void processInsn(SsaInsn ssaInsn) {
                RegisterSpec localAssignment = ssaInsn.getLocalAssignment();
                if (localAssignment != null) {
                    LocalItem localItem = localAssignment.getLocalItem();
                    ArrayList arrayList = (ArrayList) FirstFitLocalCombiningAllocator.this.localVariables.get(localItem);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        FirstFitLocalCombiningAllocator.this.localVariables.put(localItem, arrayList);
                    }
                    arrayList.add(localAssignment);
                }
                if (ssaInsn instanceof NormalSsaInsn) {
                    if (ssaInsn.getOpcode().getOpcode() == 56) {
                        FirstFitLocalCombiningAllocator.this.moveResultPseudoInsns.add((NormalSsaInsn) ssaInsn);
                    } else if (Optimizer.getAdvice().requiresSourcesInOrder(ssaInsn.getOriginalRopInsn().getOpcode(), ssaInsn.getSources())) {
                        FirstFitLocalCombiningAllocator.this.invokeRangeInsns.add((NormalSsaInsn) ssaInsn);
                    }
                } else if (ssaInsn instanceof PhiInsn) {
                    FirstFitLocalCombiningAllocator.this.phiInsns.add((PhiInsn) ssaInsn);
                }
            }
        });
    }

    private void addMapping(RegisterSpec registerSpec, int i) {
        int reg = registerSpec.getReg();
        if (this.ssaRegsMapped.get(reg) || !canMapReg(registerSpec, i)) {
            throw new RuntimeException("attempt to add invalid register mapping");
        }
        int category = registerSpec.getCategory();
        this.mapper.addMapping(registerSpec.getReg(), i, category);
        this.ssaRegsMapped.set(reg);
        this.usedRopRegs.set(i, category + i);
    }

    private void adjustAndMapSourceRangeRange(NormalSsaInsn normalSsaInsn) {
        int findRangeAndAdjust = findRangeAndAdjust(normalSsaInsn);
        RegisterSpecList sources = normalSsaInsn.getSources();
        int size = sources.size();
        int i = 0;
        int i2 = findRangeAndAdjust;
        while (i < size) {
            RegisterSpec registerSpec = sources.get(i);
            int reg = registerSpec.getReg();
            int category = registerSpec.getCategory();
            int i3 = i2 + category;
            if (!this.ssaRegsMapped.get(reg)) {
                LocalItem localItemForReg = getLocalItemForReg(reg);
                addMapping(registerSpec, i2);
                if (localItemForReg != null) {
                    markReserved(i2, category);
                    ArrayList<RegisterSpec> arrayList = this.localVariables.get(localItemForReg);
                    int size2 = arrayList.size();
                    for (int i4 = 0; i4 < size2; i4++) {
                        RegisterSpec registerSpec2 = arrayList.get(i4);
                        if (-1 == sources.indexOfRegister(registerSpec2.getReg())) {
                            tryMapReg(registerSpec2, i2, category);
                        }
                    }
                }
            }
            i++;
            i2 = i3;
        }
    }

    private int findRangeAndAdjust(NormalSsaInsn normalSsaInsn) {
        int oldToNew;
        BitSet bitSet;
        int fitPlanForRange;
        RegisterSpecList sources = normalSsaInsn.getSources();
        int size = sources.size();
        int[] iArr = new int[size];
        int i = 0;
        int i2 = 0;
        while (i < size) {
            iArr[i] = sources.get(i).getCategory();
            i++;
            i2 = iArr[i] + i2;
        }
        int i3 = 0;
        BitSet bitSet2 = null;
        int i4 = -1;
        int i5 = Integer.MIN_VALUE;
        for (int i6 = 0; i6 < size; i6++) {
            int reg = sources.get(i6).getReg();
            if (i6 != 0) {
                i3 -= iArr[i6 - 1];
            }
            if (this.ssaRegsMapped.get(reg) && (oldToNew = this.mapper.oldToNew(reg) + i3) >= 0 && !spansParamRange(oldToNew, i2) && (fitPlanForRange = fitPlanForRange(oldToNew, normalSsaInsn, iArr, (bitSet = new BitSet(size)))) >= 0) {
                int cardinality = fitPlanForRange - bitSet.cardinality();
                if (cardinality > i5) {
                    bitSet2 = bitSet;
                    i4 = oldToNew;
                    i5 = cardinality;
                }
                if (fitPlanForRange == i2) {
                    break;
                }
            }
        }
        if (i4 == -1) {
            bitSet2 = new BitSet(size);
            i4 = findAnyFittingRange(normalSsaInsn, i2, iArr, bitSet2);
        }
        for (int nextSetBit = bitSet2.nextSetBit(0); nextSetBit >= 0; nextSetBit = bitSet2.nextSetBit(nextSetBit + 1)) {
            normalSsaInsn.changeOneSource(nextSetBit, insertMoveBefore(normalSsaInsn, sources.get(nextSetBit)));
        }
        return i4;
    }

    private int findAnyFittingRange(NormalSsaInsn normalSsaInsn, int i, int[] iArr, BitSet bitSet) {
        Alignment alignment;
        int i2 = 0;
        Alignment alignment2 = Alignment.UNSPECIFIED;
        int i3 = 0;
        int i4 = 0;
        for (int i5 : iArr) {
            if (i5 == 2) {
                if (isEven(i4)) {
                    i3++;
                } else {
                    i2++;
                }
                i4 += 2;
            } else {
                i4++;
            }
        }
        if (i2 > i3) {
            if (isEven(this.paramRangeEnd)) {
                alignment = Alignment.ODD;
            } else {
                alignment = Alignment.EVEN;
            }
        } else if (i3 <= 0) {
            alignment = alignment2;
        } else if (isEven(this.paramRangeEnd)) {
            alignment = Alignment.EVEN;
        } else {
            alignment = Alignment.ODD;
        }
        int i6 = this.paramRangeEnd;
        while (true) {
            int findNextUnreservedRopReg = findNextUnreservedRopReg(i6, i, alignment);
            if (fitPlanForRange(findNextUnreservedRopReg, normalSsaInsn, iArr, bitSet) >= 0) {
                return findNextUnreservedRopReg;
            }
            i6 = findNextUnreservedRopReg + 1;
            bitSet.clear();
        }
    }

    private int fitPlanForRange(int i, NormalSsaInsn normalSsaInsn, int[] iArr, BitSet bitSet) {
        RegisterSpecList sources = normalSsaInsn.getSources();
        int size = sources.size();
        int i2 = 0;
        RegisterSpecList ssaSetToSpecs = ssaSetToSpecs(normalSsaInsn.getBlock().getLiveOutRegs());
        BitSet bitSet2 = new BitSet(this.ssaMeth.getRegCount());
        int i3 = i;
        for (int i4 = 0; i4 < size; i4++) {
            RegisterSpec registerSpec = sources.get(i4);
            int reg = registerSpec.getReg();
            int i5 = iArr[i4];
            if (i4 != 0) {
                i3 += iArr[i4 - 1];
            }
            if (this.ssaRegsMapped.get(reg) && this.mapper.oldToNew(reg) == i3) {
                i2 += i5;
            } else if (rangeContainsReserved(i3, i5)) {
                return -1;
            } else {
                if (!this.ssaRegsMapped.get(reg) && canMapReg(registerSpec, i3) && !bitSet2.get(reg)) {
                    i2 += i5;
                } else if (this.mapper.areAnyPinned(ssaSetToSpecs, i3, i5) || this.mapper.areAnyPinned(sources, i3, i5)) {
                    return -1;
                } else {
                    bitSet.set(i4);
                }
            }
            bitSet2.set(reg);
        }
        return i2;
    }

    /* access modifiers changed from: package-private */
    public RegisterSpecList ssaSetToSpecs(IntSet intSet) {
        RegisterSpecList registerSpecList = new RegisterSpecList(intSet.elements());
        IntIterator it = intSet.iterator();
        int i = 0;
        while (it.hasNext()) {
            registerSpecList.set(i, getDefinitionSpecForSsaReg(it.next()));
            i++;
        }
        return registerSpecList;
    }

    private LocalItem getLocalItemForReg(int i) {
        for (Map.Entry<LocalItem, ArrayList<RegisterSpec>> entry : this.localVariables.entrySet()) {
            Iterator<RegisterSpec> it = entry.getValue().iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().getReg() == i) {
                        return entry.getKey();
                    }
                }
            }
        }
        return null;
    }

    private void processPhiInsn(PhiInsn phiInsn) {
        RegisterSpec result = phiInsn.getResult();
        int reg = result.getReg();
        int category = result.getCategory();
        RegisterSpecList sources = phiInsn.getSources();
        int size = sources.size();
        ArrayList<RegisterSpec> arrayList = new ArrayList<>();
        Multiset multiset = new Multiset(size + 1);
        if (this.ssaRegsMapped.get(reg)) {
            multiset.add(this.mapper.oldToNew(reg));
        } else {
            arrayList.add(result);
        }
        for (int i = 0; i < size; i++) {
            RegisterSpec result2 = this.ssaMeth.getDefinitionForRegister(sources.get(i).getReg()).getResult();
            int reg2 = result2.getReg();
            if (this.ssaRegsMapped.get(reg2)) {
                multiset.add(this.mapper.oldToNew(reg2));
            } else {
                arrayList.add(result2);
            }
        }
        for (int i2 = 0; i2 < multiset.getSize(); i2++) {
            tryMapRegs(arrayList, multiset.getAndRemoveHighestCount(), category, false);
        }
        int findNextUnreservedRopReg = findNextUnreservedRopReg(this.paramRangeEnd, category);
        while (!tryMapRegs(arrayList, findNextUnreservedRopReg, category, false)) {
            findNextUnreservedRopReg = findNextUnreservedRopReg(findNextUnreservedRopReg + 1, category);
        }
    }

    /* access modifiers changed from: private */
    public static boolean isEven(int i) {
        return (i & 1) == 0;
    }

    /* access modifiers changed from: private */
    public static class Multiset {
        private final int[] count;
        private final int[] reg;
        private int size = 0;

        public Multiset(int i) {
            this.reg = new int[i];
            this.count = new int[i];
        }

        public void add(int i) {
            for (int i2 = 0; i2 < this.size; i2++) {
                if (this.reg[i2] == i) {
                    int[] iArr = this.count;
                    iArr[i2] = iArr[i2] + 1;
                    return;
                }
            }
            this.reg[this.size] = i;
            this.count[this.size] = 1;
            this.size++;
        }

        public int getAndRemoveHighestCount() {
            int i = 0;
            int i2 = -1;
            int i3 = -1;
            for (int i4 = 0; i4 < this.size; i4++) {
                if (i < this.count[i4]) {
                    i2 = this.reg[i4];
                    i = this.count[i4];
                    i3 = i4;
                }
            }
            this.count[i3] = 0;
            return i2;
        }

        public int getSize() {
            return this.size;
        }
    }
}
