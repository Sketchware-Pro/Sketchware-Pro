package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.PlainCstInsn;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.TypedConstant;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;

public class ConstCollector {
    private static boolean COLLECT_ONE_LOCAL = false;
    private static boolean COLLECT_STRINGS = false;
    private static final int MAX_COLLECTED_CONSTANTS = 5;
    private final SsaMethod ssaMeth;
    private Object RegisterMapper;

    public static void process(SsaMethod ssaMethod) {
        new ConstCollector(ssaMethod).run();
    }

    private ConstCollector(SsaMethod ssaMethod) {
        this.ssaMeth = ssaMethod;
    }

    private void run() {
        int regCount = this.ssaMeth.getRegCount();
        ArrayList<TypedConstant> constsSortedByCountUse = getConstsSortedByCountUse();
        int min = Math.min(constsSortedByCountUse.size(), 5);
        SsaBasicBlock entryBlock = this.ssaMeth.getEntryBlock();
        HashMap<TypedConstant, RegisterSpec> hashMap = new HashMap<>(min);
        for (int i = 0; i < min; i++) {
            TypedConstant typedConstant = constsSortedByCountUse.get(i);
            RegisterSpec make = RegisterSpec.make(this.ssaMeth.makeNewSsaReg(), typedConstant);
            Rop opConst = Rops.opConst(typedConstant);
            if (opConst.getBranchingness() == 1) {
                entryBlock.addInsnToHead(new PlainCstInsn(Rops.opConst(typedConstant), SourcePosition.NO_INFO, make, RegisterSpecList.EMPTY, typedConstant));
            } else {
                SsaBasicBlock entryBlock2 = this.ssaMeth.getEntryBlock();
                SsaBasicBlock primarySuccessor = entryBlock2.getPrimarySuccessor();
                SsaBasicBlock insertNewSuccessor = entryBlock2.insertNewSuccessor(primarySuccessor);
                insertNewSuccessor.replaceLastInsn(new ThrowingCstInsn(opConst, SourcePosition.NO_INFO, RegisterSpecList.EMPTY, StdTypeList.EMPTY, typedConstant));
                insertNewSuccessor.insertNewSuccessor(primarySuccessor).addInsnToHead(new PlainInsn(Rops.opMoveResultPseudo(make.getTypeBearer()), SourcePosition.NO_INFO, make, RegisterSpecList.EMPTY));
            }
            hashMap.put(typedConstant, make);
        }
        updateConstUses(hashMap, regCount);
    }

    private ArrayList<TypedConstant> getConstsSortedByCountUse() {
        int regCount = this.ssaMeth.getRegCount();
        final HashMap hashMap = new HashMap();
        HashSet hashSet = new HashSet();
        for (int i = 0; i < regCount; i++) {
            SsaInsn definitionForRegister = this.ssaMeth.getDefinitionForRegister(i);
            if (!(definitionForRegister == null || definitionForRegister.getOpcode() == null)) {
                RegisterSpec result = definitionForRegister.getResult();
                TypeBearer typeBearer = result.getTypeBearer();
                if (typeBearer.isConstant()) {
                    TypedConstant typedConstant = (TypedConstant) typeBearer;
                    if (definitionForRegister.getOpcode().getOpcode() == 56) {
                        ArrayList<SsaInsn> insns = this.ssaMeth.getBlocks().get(definitionForRegister.getBlock().getPredecessors().nextSetBit(0)).getInsns();
                        definitionForRegister = insns.get(insns.size() - 1);
                    }
                    if (!definitionForRegister.canThrow() || ((typedConstant instanceof CstString) && COLLECT_STRINGS && definitionForRegister.getBlock().getSuccessors().cardinality() <= 1)) {
                        if (this.ssaMeth.isRegALocal(result)) {
                            if (COLLECT_ONE_LOCAL && !hashSet.contains(typedConstant)) {
                                hashSet.add(typedConstant);
                            }
                        }
                        Integer num = (Integer) hashMap.get(typedConstant);
                        if (num == null) {
                            hashMap.put(typedConstant, 1);
                        } else {
                            hashMap.put(typedConstant, Integer.valueOf(num.intValue() + 1));
                        }
                    }
                }
            }
        }
        ArrayList<TypedConstant> arrayList = new ArrayList<>();
        for (Iterator iterator = hashMap.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (((Integer) entry.getValue()).intValue() > 1) {
                arrayList.add((TypedConstant) entry.getKey());
            }
        }
        Collections.sort(arrayList, new Comparator<Constant>() {
            /* class mod.agus.jcoderz.dx.ssa.ConstCollector.AnonymousClass1 */

            public int compare(Constant constant, Constant constant2) {
                int intValue = ((Integer) hashMap.get(constant2)).intValue() - ((Integer) hashMap.get(constant)).intValue();
                if (intValue == 0) {
                    return constant.compareTo(constant2);
                }
                return intValue;
            }

            public boolean equals(Object obj) {
                return obj == this;
            }
        });
        return arrayList;
    }

    private void fixLocalAssignment(RegisterSpec registerSpec, RegisterSpec registerSpec2) {
        for (SsaInsn ssaInsn : this.ssaMeth.getUseListForRegister(registerSpec.getReg())) {
            RegisterSpec localAssignment = ssaInsn.getLocalAssignment();
            if (!(localAssignment == null || ssaInsn.getResult() == null)) {
                LocalItem localItem = localAssignment.getLocalItem();
                ssaInsn.setResultLocal(null);
                registerSpec2 = registerSpec2.withLocalItem(localItem);
                SsaInsn makeFromRop = SsaInsn.makeFromRop(new PlainInsn(Rops.opMarkLocal(registerSpec2), SourcePosition.NO_INFO, (RegisterSpec) null, RegisterSpecList.make(registerSpec2)), ssaInsn.getBlock());
                ArrayList<SsaInsn> insns = ssaInsn.getBlock().getInsns();
                insns.add(insns.indexOf(ssaInsn) + 1, makeFromRop);
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:8:0x002e, code lost:
        r1 = r9.get((r0 = (mod.agus.jcoderz.dx.rop.cst.TypedConstant) r0));
     */
    private void updateConstUses(HashMap<TypedConstant, RegisterSpec> hashMap, int i) {
        TypedConstant typedConstant = null;
        final RegisterSpec registerSpec = null;
        HashSet hashSet = new HashSet();
        ArrayList<SsaInsn>[] useListCopy = this.ssaMeth.getUseListCopy();
        for (int i2 = 0; i2 < i; i2++) {
            SsaInsn definitionForRegister = this.ssaMeth.getDefinitionForRegister(i2);
            if (definitionForRegister != null) {
                final RegisterSpec result = definitionForRegister.getResult();
                TypeBearer typeBearer = definitionForRegister.getResult().getTypeBearer();
                if (typeBearer.isConstant() && registerSpec != null) {
                    if (this.ssaMeth.isRegALocal(result)) {
                        if (COLLECT_ONE_LOCAL && !hashSet.contains(typedConstant)) {
                            hashSet.add(typedConstant);
                            fixLocalAssignment(result, hashMap.get(typedConstant));
                        }
                    }
                    RegisterMapper registerMapper = new RegisterMapper() {
                        /* class mod.agus.jcoderz.dx.ssa.ConstCollector.AnonymousClass2 */

                        @Override // mod.agus.jcoderz.dx.ssa.RegisterMapper
                        public int getNewRegisterCount() {
                            return ConstCollector.this.ssaMeth.getRegCount();
                        }

                        @Override // mod.agus.jcoderz.dx.ssa.RegisterMapper
                        public RegisterSpec map(RegisterSpec registerSpec) {
                            if (registerSpec.getReg() == result.getReg()) {
                                return registerSpec.withLocalItem(registerSpec.getLocalItem());
                            }
                            return registerSpec;
                        }
                    };
                    Iterator<SsaInsn> it = useListCopy[result.getReg()].iterator();
                    while (it.hasNext()) {
                        SsaInsn next = it.next();
                        if (!next.canThrow() || next.getBlock().getSuccessors().cardinality() <= 1) {
                            next.mapSourceRegisters((mod.agus.jcoderz.dx.ssa.RegisterMapper) RegisterMapper);
                        }
                    }
                }
            }
        }
    }
}
