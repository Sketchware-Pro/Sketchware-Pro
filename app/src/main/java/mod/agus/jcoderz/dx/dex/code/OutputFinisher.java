package mod.agus.jcoderz.dx.dex.code;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import mod.agus.jcoderz.dex.DexException;
import mod.agus.jcoderz.dx.dex.DexOptions;
import mod.agus.jcoderz.dx.dex.code.DalvCode;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecSet;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstMemberRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.ssa.BasicRegisterMapper;

public final class OutputFinisher {
    private final DexOptions dexOptions;
    private boolean hasAnyLocalInfo = false;
    private boolean hasAnyPositionInfo = false;
    private ArrayList<DalvInsn> insns;
    private final int paramSize;
    private int reservedCount = -1;
    private int reservedParameterCount;
    private final int unreservedRegCount;

    public OutputFinisher(DexOptions dexOptions2, int i, int i2, int i3) {
        this.dexOptions = dexOptions2;
        this.unreservedRegCount = i2;
        this.insns = new ArrayList<>(i);
        this.paramSize = i3;
    }

    public boolean hasAnyPositionInfo() {
        return this.hasAnyPositionInfo;
    }

    public boolean hasAnyLocalInfo() {
        return this.hasAnyLocalInfo;
    }

    private static boolean hasLocalInfo(DalvInsn dalvInsn) {
        if (dalvInsn instanceof LocalSnapshot) {
            RegisterSpecSet locals = ((LocalSnapshot) dalvInsn).getLocals();
            int size = locals.size();
            for (int i = 0; i < size; i++) {
                if (hasLocalInfo(locals.get(i))) {
                    return true;
                }
            }
        } else if ((dalvInsn instanceof LocalStart) && hasLocalInfo(((LocalStart) dalvInsn).getLocal())) {
            return true;
        }
        return false;
    }

    private static boolean hasLocalInfo(RegisterSpec registerSpec) {
        return (registerSpec == null || registerSpec.getLocalItem().getName() == null) ? false : true;
    }

    public HashSet<Constant> getAllConstants() {
        HashSet<Constant> hashSet = new HashSet<>(20);
        Iterator<DalvInsn> it = this.insns.iterator();
        while (it.hasNext()) {
            addConstants(hashSet, it.next());
        }
        return hashSet;
    }

    private static void addConstants(HashSet<Constant> hashSet, DalvInsn dalvInsn) {
        if (dalvInsn instanceof CstInsn) {
            hashSet.add(((CstInsn) dalvInsn).getConstant());
        } else if (dalvInsn instanceof LocalSnapshot) {
            RegisterSpecSet locals = ((LocalSnapshot) dalvInsn).getLocals();
            int size = locals.size();
            for (int i = 0; i < size; i++) {
                addConstants(hashSet, locals.get(i));
            }
        } else if (dalvInsn instanceof LocalStart) {
            addConstants(hashSet, ((LocalStart) dalvInsn).getLocal());
        }
    }

    private static void addConstants(HashSet<Constant> hashSet, RegisterSpec registerSpec) {
        if (registerSpec != null) {
            LocalItem localItem = registerSpec.getLocalItem();
            CstString name = localItem.getName();
            CstString signature = localItem.getSignature();
            Type type = registerSpec.getType();
            if (type != Type.KNOWN_NULL) {
                hashSet.add(CstType.intern(type));
            }
            if (name != null) {
                hashSet.add(name);
            }
            if (signature != null) {
                hashSet.add(signature);
            }
        }
    }

    public void add(DalvInsn dalvInsn) {
        this.insns.add(dalvInsn);
        updateInfo(dalvInsn);
    }

    public void insert(int i, DalvInsn dalvInsn) {
        this.insns.add(i, dalvInsn);
        updateInfo(dalvInsn);
    }

    private void updateInfo(DalvInsn dalvInsn) {
        if (!this.hasAnyPositionInfo && dalvInsn.getPosition().getLine() >= 0) {
            this.hasAnyPositionInfo = true;
        }
        if (!this.hasAnyLocalInfo && hasLocalInfo(dalvInsn)) {
            this.hasAnyLocalInfo = true;
        }
    }

    public void reverseBranch(int i, CodeAddress codeAddress) {
        int size = (this.insns.size() - i) - 1;
        try {
            this.insns.set(size, ((TargetInsn) this.insns.get(size)).withNewTargetAndReversed(codeAddress));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("too few instructions");
        } catch (ClassCastException e2) {
            throw new IllegalArgumentException("non-reversible instruction");
        }
    }

    public void assignIndices(DalvCode.AssignIndicesCallback assignIndicesCallback) {
        Iterator<DalvInsn> it = this.insns.iterator();
        while (it.hasNext()) {
            DalvInsn next = it.next();
            if (next instanceof CstInsn) {
                assignIndices((CstInsn) next, assignIndicesCallback);
            }
        }
    }

    private static void assignIndices(CstInsn cstInsn, DalvCode.AssignIndicesCallback assignIndicesCallback) {
        int index;
        Constant constant = cstInsn.getConstant();
        int index2 = assignIndicesCallback.getIndex(constant);
        if (index2 >= 0) {
            cstInsn.setIndex(index2);
        }
        if ((constant instanceof CstMemberRef) && (index = assignIndicesCallback.getIndex(((CstMemberRef) constant).getDefiningClass())) >= 0) {
            cstInsn.setClassIndex(index);
        }
    }

    public DalvInsnList finishProcessingAndGetList() {
        if (this.reservedCount >= 0) {
            throw new UnsupportedOperationException("already processed");
        }
        Dop[] makeOpcodesArray = makeOpcodesArray();
        reserveRegisters(makeOpcodesArray);
        if (this.dexOptions.ALIGN_64BIT_REGS_IN_OUTPUT_FINISHER) {
            align64bits(makeOpcodesArray);
        }
        massageInstructions(makeOpcodesArray);
        assignAddressesAndFixBranches();
        return DalvInsnList.makeImmutable(this.insns, this.reservedCount + this.unreservedRegCount + this.reservedParameterCount);
    }

    private Dop[] makeOpcodesArray() {
        int size = this.insns.size();
        Dop[] dopArr = new Dop[size];
        for (int i = 0; i < size; i++) {
            dopArr[i] = this.insns.get(i).getOpcode();
        }
        return dopArr;
    }

    private boolean reserveRegisters(Dop[] dopArr) {
        int i = this.reservedCount < 0 ? 0 : this.reservedCount;
        boolean z = false;
        while (true) {
            int calculateReservedCount = calculateReservedCount(dopArr);
            if (i >= calculateReservedCount) {
                this.reservedCount = i;
                return z;
            }
            z = true;
            int i2 = calculateReservedCount - i;
            int size = this.insns.size();
            for (int i3 = 0; i3 < size; i3++) {
                DalvInsn dalvInsn = this.insns.get(i3);
                if (!(dalvInsn instanceof CodeAddress)) {
                    this.insns.set(i3, dalvInsn.withRegisterOffset(i2));
                }
            }
            i = calculateReservedCount;
        }
    }

    private int calculateReservedCount(Dop[] dopArr) {
        int i;
        int size = this.insns.size();
        int i2 = this.reservedCount;
        for (int i3 = 0; i3 < size; i3++) {
            DalvInsn dalvInsn = this.insns.get(i3);
            Dop dop = dopArr[i3];
            Dop findOpcodeForInsn = findOpcodeForInsn(dalvInsn, dop);
            if (findOpcodeForInsn == null) {
                i = dalvInsn.getMinimumRegisterRequirement(findExpandedOpcodeForInsn(dalvInsn).getFormat().compatibleRegs(dalvInsn));
            } else {
                if (dop == findOpcodeForInsn) {
                }
                i = i2;
            }
            dopArr[i3] = findOpcodeForInsn;
            i2 = i;
        }
        return i2;
    }

    private Dop findOpcodeForInsn(DalvInsn dalvInsn, Dop dop) {
        while (dop != null && (!dop.getFormat().isCompatible(dalvInsn) || (this.dexOptions.forceJumbo && dop.getOpcode() == 26))) {
            dop = Dops.getNextOrNull(dop, this.dexOptions);
        }
        return dop;
    }

    private Dop findExpandedOpcodeForInsn(DalvInsn dalvInsn) {
        Dop findOpcodeForInsn = findOpcodeForInsn(dalvInsn.getLowRegVersion(), dalvInsn.getOpcode());
        if (findOpcodeForInsn != null) {
            return findOpcodeForInsn;
        }
        throw new DexException("No expanded opcode for " + dalvInsn);
    }

    private void massageInstructions(Dop[] dopArr) {
        if (this.reservedCount == 0) {
            int size = this.insns.size();
            for (int i = 0; i < size; i++) {
                DalvInsn dalvInsn = this.insns.get(i);
                Dop opcode = dalvInsn.getOpcode();
                Dop dop = dopArr[i];
                if (opcode != dop) {
                    this.insns.set(i, dalvInsn.withOpcode(dop));
                }
            }
            return;
        }
        this.insns = performExpansion(dopArr);
    }

    private ArrayList<DalvInsn> performExpansion(Dop[] dopArr) {
        Dop dop;
        DalvInsn dalvInsn;
        DalvInsn dalvInsn2;
        DalvInsn dalvInsn3;
        int size = this.insns.size();
        ArrayList<DalvInsn> arrayList = new ArrayList<>(size * 2);
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < size; i++) {
            DalvInsn dalvInsn4 = this.insns.get(i);
            Dop opcode = dalvInsn4.getOpcode();
            Dop dop2 = dopArr[i];
            if (dop2 != null) {
                dalvInsn2 = null;
                dalvInsn = null;
                dop = dop2;
                dalvInsn3 = dalvInsn4;
            } else {
                Dop findExpandedOpcodeForInsn = findExpandedOpcodeForInsn(dalvInsn4);
                BitSet compatibleRegs = findExpandedOpcodeForInsn.getFormat().compatibleRegs(dalvInsn4);
                DalvInsn expandedPrefix = dalvInsn4.expandedPrefix(compatibleRegs);
                DalvInsn expandedSuffix = dalvInsn4.expandedSuffix(compatibleRegs);
                DalvInsn expandedVersion = dalvInsn4.expandedVersion(compatibleRegs);
                dop = findExpandedOpcodeForInsn;
                dalvInsn = expandedPrefix;
                dalvInsn2 = expandedSuffix;
                dalvInsn3 = expandedVersion;
            }
            if (!(dalvInsn3 instanceof CodeAddress) || !((CodeAddress) dalvInsn3).getBindsClosely()) {
                if (dalvInsn != null) {
                    arrayList.add(dalvInsn);
                }
                if (!(dalvInsn3 instanceof ZeroSizeInsn) && arrayList2.size() > 0) {
                    Iterator it = arrayList2.iterator();
                    while (it.hasNext()) {
                        arrayList.add((CodeAddress) it.next());
                    }
                    arrayList2.clear();
                }
                if (dop != opcode) {
                    dalvInsn3 = dalvInsn3.withOpcode(dop);
                }
                arrayList.add(dalvInsn3);
                if (dalvInsn2 != null) {
                    arrayList.add(dalvInsn2);
                }
            } else {
                arrayList2.add((CodeAddress) dalvInsn3);
            }
        }
        return arrayList;
    }

    private void assignAddressesAndFixBranches() {
        do {
            assignAddresses();
        } while (fixBranches());
    }

    private void assignAddresses() {
        int size = this.insns.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            DalvInsn dalvInsn = this.insns.get(i2);
            dalvInsn.setAddress(i);
            i += dalvInsn.codeSize();
        }
    }

    private boolean fixBranches() {
        int i;
        int i2;
        boolean z;
        int i3 = 0;
        int size = this.insns.size();
        boolean z2 = false;
        while (i3 < size) {
            DalvInsn dalvInsn = this.insns.get(i3);
            if (!(dalvInsn instanceof TargetInsn)) {
                z = z2;
            } else {
                Dop opcode = dalvInsn.getOpcode();
                TargetInsn targetInsn = (TargetInsn) dalvInsn;
                if (opcode.getFormat().branchFits(targetInsn)) {
                    z = z2;
                } else {
                    if (opcode.getFamily() == 40) {
                        Dop findOpcodeForInsn = findOpcodeForInsn(dalvInsn, opcode);
                        if (findOpcodeForInsn == null) {
                            throw new UnsupportedOperationException("method too long");
                        }
                        this.insns.set(i3, dalvInsn.withOpcode(findOpcodeForInsn));
                        i2 = i3;
                        i = size;
                    } else {
                        try {
                            CodeAddress codeAddress = (CodeAddress) this.insns.get(i3 + 1);
                            this.insns.set(i3, new TargetInsn(Dops.GOTO, targetInsn.getPosition(), RegisterSpecList.EMPTY, targetInsn.getTarget()));
                            this.insns.add(i3, targetInsn.withNewTargetAndReversed(codeAddress));
                            i = size + 1;
                            i2 = i3 + 1;
                        } catch (IndexOutOfBoundsException e) {
                            throw new IllegalStateException("unpaired TargetInsn (dangling)");
                        } catch (ClassCastException e2) {
                            throw new IllegalStateException("unpaired TargetInsn");
                        }
                    }
                    size = i;
                    i3 = i2;
                    z = true;
                }
            }
            i3++;
            z2 = z;
        }
        return z2;
    }

    private void align64bits(Dop[] dopArr) {
        boolean z;
        do {
            int i = ((this.unreservedRegCount + this.reservedCount) + this.reservedParameterCount) - this.paramSize;
            Iterator<DalvInsn> it = this.insns.iterator();
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            int i5 = 0;
            while (it.hasNext()) {
                RegisterSpecList registers = it.next().getRegisters();
                int i6 = i2;
                int i7 = i3;
                int i8 = i4;
                int i9 = i5;
                int i10 = 0;
                while (i10 < registers.size()) {
                    RegisterSpec registerSpec = registers.get(i10);
                    if (registerSpec.isCategory2()) {
                        if (registerSpec.getReg() >= i) {
                            z = true;
                        } else {
                            z = false;
                        }
                        if (registerSpec.isEvenRegister()) {
                            if (z) {
                                i6++;
                            } else {
                                i8++;
                            }
                        } else if (z) {
                            i7++;
                        } else {
                            i9++;
                        }
                    }
                    i10++;
                    i6 = i6;
                    i7 = i7;
                    i8 = i8;
                    i9 = i9;
                }
                i5 = i9;
                i4 = i8;
                i3 = i7;
                i2 = i6;
            }
            if (i3 > i2 && i5 > i4) {
                addReservedRegisters(1);
            } else if (i3 > i2) {
                addReservedParameters(1);
            } else if (i5 > i4) {
                addReservedRegisters(1);
                if (this.paramSize != 0 && i2 > i3) {
                    addReservedParameters(1);
                }
            } else {
                return;
            }
        } while (reserveRegisters(dopArr));
    }

    private void addReservedParameters(int i) {
        shiftParameters(i);
        this.reservedParameterCount += i;
    }

    private void addReservedRegisters(int i) {
        shiftAllRegisters(i);
        this.reservedCount += i;
    }

    private void shiftAllRegisters(int i) {
        int size = this.insns.size();
        for (int i2 = 0; i2 < size; i2++) {
            DalvInsn dalvInsn = this.insns.get(i2);
            if (!(dalvInsn instanceof CodeAddress)) {
                this.insns.set(i2, dalvInsn.withRegisterOffset(i));
            }
        }
    }

    private void shiftParameters(int i) {
        int size = this.insns.size();
        int i2 = this.reservedParameterCount + this.unreservedRegCount + this.reservedCount;
        int i3 = i2 - this.paramSize;
        BasicRegisterMapper basicRegisterMapper = new BasicRegisterMapper(i2);
        for (int i4 = 0; i4 < i2; i4++) {
            if (i4 >= i3) {
                basicRegisterMapper.addMapping(i4, i4 + i, 1);
            } else {
                basicRegisterMapper.addMapping(i4, i4, 1);
            }
        }
        for (int i5 = 0; i5 < size; i5++) {
            DalvInsn dalvInsn = this.insns.get(i5);
            if (!(dalvInsn instanceof CodeAddress)) {
                this.insns.set(i5, dalvInsn.withMapper(basicRegisterMapper));
            }
        }
    }
}
