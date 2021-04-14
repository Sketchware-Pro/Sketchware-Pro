package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import mod.agus.jcoderz.dx.rop.code.CstInsn;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.TypedConstant;

public class SCCP {
    private static final int CONSTANT = 1;
    private static final int TOP = 0;
    private static final int VARYING = 2;
    private ArrayList<SsaInsn> branchWorklist;
    private ArrayList<SsaBasicBlock> cfgPhiWorklist = new ArrayList<>();
    private ArrayList<SsaBasicBlock> cfgWorklist = new ArrayList<>();
    private BitSet executableBlocks;
    private Constant[] latticeConstants = new Constant[this.regCount];
    private int[] latticeValues = new int[this.regCount];
    private int regCount;
    private SsaMethod ssaMeth;
    private ArrayList<SsaInsn> ssaWorklist;
    private ArrayList<SsaInsn> varyingWorklist;

    private SCCP(SsaMethod ssaMethod) {
        this.ssaMeth = ssaMethod;
        this.regCount = ssaMethod.getRegCount();
        this.executableBlocks = new BitSet(ssaMethod.getBlocks().size());
        this.ssaWorklist = new ArrayList<>();
        this.varyingWorklist = new ArrayList<>();
        this.branchWorklist = new ArrayList<>();
        for (int i = 0; i < this.regCount; i++) {
            this.latticeValues[i] = 0;
            this.latticeConstants[i] = null;
        }
    }

    public static void process(SsaMethod ssaMethod) {
        new SCCP(ssaMethod).run();
    }

    private void addBlockToWorklist(SsaBasicBlock ssaBasicBlock) {
        if (!this.executableBlocks.get(ssaBasicBlock.getIndex())) {
            this.cfgWorklist.add(ssaBasicBlock);
            this.executableBlocks.set(ssaBasicBlock.getIndex());
            return;
        }
        this.cfgPhiWorklist.add(ssaBasicBlock);
    }

    private void addUsersToWorklist(int i, int i2) {
        if (i2 == 2) {
            for (SsaInsn ssaInsn : this.ssaMeth.getUseListForRegister(i)) {
                this.varyingWorklist.add(ssaInsn);
            }
            return;
        }
        for (SsaInsn ssaInsn2 : this.ssaMeth.getUseListForRegister(i)) {
            this.ssaWorklist.add(ssaInsn2);
        }
    }

    private boolean setLatticeValueTo(int i, int i2, Constant constant) {
        if (i2 != 1) {
            if (this.latticeValues[i] == i2) {
                return false;
            }
            this.latticeValues[i] = i2;
            return true;
        } else if (this.latticeValues[i] == i2 && this.latticeConstants[i].equals(constant)) {
            return false;
        } else {
            this.latticeValues[i] = i2;
            this.latticeConstants[i] = constant;
            return true;
        }
    }

    private void simulatePhi(PhiInsn phiInsn) {
        int reg = phiInsn.getResult().getReg();
        if (this.latticeValues[reg] != 2) {
            RegisterSpecList sources = phiInsn.getSources();
            int size = sources.size();
            int i = 0;
            int i2 = 0;
            Constant constant = null;
            while (true) {
                if (i >= size) {
                    break;
                }
                int predBlockIndexForSourcesIndex = phiInsn.predBlockIndexForSourcesIndex(i);
                int reg2 = sources.get(i).getReg();
                int i3 = this.latticeValues[reg2];
                if (this.executableBlocks.get(predBlockIndexForSourcesIndex)) {
                    if (i3 != 1) {
                        i2 = i3;
                        break;
                    } else if (constant == null) {
                        constant = this.latticeConstants[reg2];
                        i2 = 1;
                    } else if (!this.latticeConstants[reg2].equals(constant)) {
                        i2 = 2;
                        break;
                    }
                }
                i++;
            }
            if (setLatticeValueTo(reg, i2, constant)) {
                addUsersToWorklist(reg, i2);
            }
        }
    }

    private void simulateBlock(SsaBasicBlock ssaBasicBlock) {
        Iterator<SsaInsn> it = ssaBasicBlock.getInsns().iterator();
        while (it.hasNext()) {
            SsaInsn next = it.next();
            if (next instanceof PhiInsn) {
                simulatePhi((PhiInsn) next);
            } else {
                simulateStmt(next);
            }
        }
    }

    private void simulatePhiBlock(SsaBasicBlock ssaBasicBlock) {
        Iterator<SsaInsn> it = ssaBasicBlock.getInsns().iterator();
        while (it.hasNext()) {
            SsaInsn next = it.next();
            if (next instanceof PhiInsn) {
                simulatePhi((PhiInsn) next);
            } else {
                return;
            }
        }
    }

    private static String latticeValName(int i) {
        switch (i) {
            case 0:
                return "TOP";
            case 1:
                return "CONSTANT";
            case 2:
                return "VARYING";
            default:
                return "UNKNOWN";
        }
    }

    private void simulateBranch(SsaInsn ssaInsn) {
        boolean z = false;
        boolean z2 = false;
        int i;
        CstInteger cstInteger;
        Rop opcode = ssaInsn.getOpcode();
        RegisterSpecList sources = ssaInsn.getSources();
        if (opcode.getBranchingness() == 4) {
            RegisterSpec registerSpec = sources.get(0);
            int reg = registerSpec.getReg();
            CstInteger cstInteger2 = (this.ssaMeth.isRegALocal(registerSpec) || this.latticeValues[reg] != 1) ? null : (CstInteger) this.latticeConstants[reg];
            if (sources.size() == 2) {
                RegisterSpec registerSpec2 = sources.get(1);
                int reg2 = registerSpec2.getReg();
                if (!this.ssaMeth.isRegALocal(registerSpec2) && this.latticeValues[reg2] == 1) {
                    cstInteger = (CstInteger) this.latticeConstants[reg2];
                    if (cstInteger2 == null && sources.size() == 1) {
                        switch (cstInteger2.getBasicType()) {
                            case 6:
                                int value = cstInteger2.getValue();
                                switch (opcode.getOpcode()) {
                                    case 7:
                                        z = value == 0;
                                        z2 = true;
                                        break;
                                    case 8:
                                        z = value != 0;
                                        z2 = true;
                                        break;
                                    case 9:
                                        z = value < 0;
                                        z2 = true;
                                        break;
                                    case 10:
                                        z = value >= 0;
                                        z2 = true;
                                        break;
                                    case 11:
                                        z = value <= 0;
                                        z2 = true;
                                        break;
                                    case 12:
                                        z = value > 0;
                                        z2 = true;
                                        break;
                                    default:
                                        throw new RuntimeException("Unexpected op");
                                }
                            default:
                                z = false;
                                z2 = false;
                                break;
                        }
                        SsaBasicBlock block = ssaInsn.getBlock();
                        if (!z2) {
                        }
                    } else if (!(cstInteger2 == null || cstInteger == null)) {
                        switch (cstInteger2.getBasicType()) {
                            case 6:
                                int value2 = cstInteger2.getValue();
                                int value3 = cstInteger.getValue();
                                switch (opcode.getOpcode()) {
                                    case 7:
                                        z = value2 == value3;
                                        z2 = true;
                                        break;
                                    case 8:
                                        z = value2 != value3;
                                        z2 = true;
                                        break;
                                    case 9:
                                        z = value2 < value3;
                                        z2 = true;
                                        break;
                                    case 10:
                                        z = value2 >= value3;
                                        z2 = true;
                                        break;
                                    case 11:
                                        z = value2 <= value3;
                                        z2 = true;
                                        break;
                                    case 12:
                                        z = value2 > value3;
                                        z2 = true;
                                        break;
                                    default:
                                        throw new RuntimeException("Unexpected op");
                                }
                        }
                        SsaBasicBlock block2 = ssaInsn.getBlock();
                        if (!z2) {
                            if (z) {
                                i = block2.getSuccessorList().get(1);
                            } else {
                                i = block2.getSuccessorList().get(0);
                            }
                            addBlockToWorklist(this.ssaMeth.getBlocks().get(i));
                            this.branchWorklist.add(ssaInsn);
                            return;
                        }
                        for (int i2 = 0; i2 < block2.getSuccessorList().size(); i2++) {
                            addBlockToWorklist(this.ssaMeth.getBlocks().get(block2.getSuccessorList().get(i2)));
                        }
                        return;
                    }
                }
            }
            cstInteger = null;
            if (cstInteger2 == null) {
            }
            switch (cstInteger2.getBasicType()) {
            }
            SsaBasicBlock block22 = ssaInsn.getBlock();
            if (!z2) {
            }
        }
        z = false;
        z2 = false;
        SsaBasicBlock block222 = ssaInsn.getBlock();
        if (!z2) {
        }
    }

    private Constant simulateMath(SsaInsn ssaInsn, int i) {
        Constant constant;
        Constant constant2;
        boolean z;
        int i2 = 0;
        Insn originalRopInsn = ssaInsn.getOriginalRopInsn();
        int opcode = ssaInsn.getOpcode().getOpcode();
        RegisterSpecList sources = ssaInsn.getSources();
        int reg = sources.get(0).getReg();
        if (this.latticeValues[reg] != 1) {
            constant = null;
        } else {
            constant = this.latticeConstants[reg];
        }
        if (sources.size() == 1) {
            constant2 = ((CstInsn) originalRopInsn).getConstant();
        } else {
            int reg2 = sources.get(1).getReg();
            if (this.latticeValues[reg2] != 1) {
                constant2 = null;
            } else {
                constant2 = this.latticeConstants[reg2];
            }
        }
        if (constant == null || constant2 == null) {
            return null;
        }
        switch (i) {
            case 6:
                int value = ((CstInteger) constant).getValue();
                int value2 = ((CstInteger) constant2).getValue();
                switch (opcode) {
                    case 14:
                        i2 = value2 + value;
                        z = false;
                        break;
                    case 15:
                        if (sources.size() != 1) {
                            i2 = value - value2;
                            z = false;
                            break;
                        } else {
                            i2 = value2 - value;
                            z = false;
                            break;
                        }
                    case 16:
                        i2 = value2 * value;
                        z = false;
                        break;
                    case 17:
                        if (value2 != 0) {
                            i2 = value / value2;
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    case 18:
                        if (value2 != 0) {
                            i2 = value % value2;
                            z = false;
                            break;
                        } else {
                            z = true;
                            break;
                        }
                    case 19:
                    default:
                        throw new RuntimeException("Unexpected op");
                    case 20:
                        i2 = value2 & value;
                        z = false;
                        break;
                    case 21:
                        i2 = value2 | value;
                        z = false;
                        break;
                    case 22:
                        i2 = value2 ^ value;
                        z = false;
                        break;
                    case 23:
                        i2 = value << value2;
                        z = false;
                        break;
                    case 24:
                        i2 = value >> value2;
                        z = false;
                        break;
                    case 25:
                        i2 = value >>> value2;
                        z = false;
                        break;
                }
                if (!z) {
                    return CstInteger.make(i2);
                }
                return null;
            default:
                return null;
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private void simulateStmt(SsaInsn ssaInsn) {
        int i;
        Constant constant;
        Insn originalRopInsn = ssaInsn.getOriginalRopInsn();
        if (originalRopInsn.getOpcode().getBranchingness() != 1 || originalRopInsn.getOpcode().isCallLike()) {
            simulateBranch(ssaInsn);
        }
        int opcode = ssaInsn.getOpcode().getOpcode();
        RegisterSpec result = ssaInsn.getResult();
        if (result == null) {
            if (opcode == 17 || opcode == 18) {
                result = ssaInsn.getBlock().getPrimarySuccessor().getInsns().get(0).getResult();
            } else {
                return;
            }
        }
        int reg = result.getReg();
        switch (opcode) {
            case 2:
                if (ssaInsn.getSources().size() == 1) {
                    int reg2 = ssaInsn.getSources().get(0).getReg();
                    i = this.latticeValues[reg2];
                    constant = this.latticeConstants[reg2];
                    break;
                }
                constant = null;
                i = 2;
                break;
            case 5:
                constant = ((CstInsn) originalRopInsn).getConstant();
                i = 1;
                break;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                constant = simulateMath(ssaInsn, result.getBasicType());
                if (constant == null) {
                    i = 2;
                    break;
                } else {
                    i = 1;
                    break;
                }
            case 56:
                if (this.latticeValues[reg] == 1) {
                    i = this.latticeValues[reg];
                    constant = this.latticeConstants[reg];
                    break;
                }
                constant = null;
                i = 2;
                break;
            default:
                constant = null;
                i = 2;
                break;
        }
        if (setLatticeValueTo(reg, i, constant)) {
            addUsersToWorklist(reg, i);
        }
    }

    private void run() {
        addBlockToWorklist(this.ssaMeth.getEntryBlock());
        while (true) {
            if (!this.cfgWorklist.isEmpty() || !this.cfgPhiWorklist.isEmpty() || !this.ssaWorklist.isEmpty() || !this.varyingWorklist.isEmpty()) {
                while (!this.cfgWorklist.isEmpty()) {
                    simulateBlock(this.cfgWorklist.remove(this.cfgWorklist.size() - 1));
                }
                while (!this.cfgPhiWorklist.isEmpty()) {
                    simulatePhiBlock(this.cfgPhiWorklist.remove(this.cfgPhiWorklist.size() - 1));
                }
                while (!this.varyingWorklist.isEmpty()) {
                    SsaInsn remove = this.varyingWorklist.remove(this.varyingWorklist.size() - 1);
                    if (this.executableBlocks.get(remove.getBlock().getIndex())) {
                        if (remove instanceof PhiInsn) {
                            simulatePhi((PhiInsn) remove);
                        } else {
                            simulateStmt(remove);
                        }
                    }
                }
                while (!this.ssaWorklist.isEmpty()) {
                    SsaInsn remove2 = this.ssaWorklist.remove(this.ssaWorklist.size() - 1);
                    if (this.executableBlocks.get(remove2.getBlock().getIndex())) {
                        if (remove2 instanceof PhiInsn) {
                            simulatePhi((PhiInsn) remove2);
                        } else {
                            simulateStmt(remove2);
                        }
                    }
                }
            } else {
                replaceConstants();
                replaceBranches();
                return;
            }
        }
    }

    private void replaceConstants() {
        for (int i = 0; i < this.regCount; i++) {
            if (this.latticeValues[i] == 1 && (this.latticeConstants[i] instanceof TypedConstant)) {
                SsaInsn definitionForRegister = this.ssaMeth.getDefinitionForRegister(i);
                if (!definitionForRegister.getResult().getTypeBearer().isConstant()) {
                    definitionForRegister.setResult(definitionForRegister.getResult().withType((TypedConstant) this.latticeConstants[i]));
                    for (SsaInsn ssaInsn : this.ssaMeth.getUseListForRegister(i)) {
                        if (!ssaInsn.isPhiOrMove()) {
                            NormalSsaInsn normalSsaInsn = (NormalSsaInsn) ssaInsn;
                            RegisterSpecList sources = ssaInsn.getSources();
                            int indexOfRegister = sources.indexOfRegister(i);
                            normalSsaInsn.changeOneSource(indexOfRegister, sources.get(indexOfRegister).withType((TypedConstant) this.latticeConstants[i]));
                        }
                    }
                }
            }
        }
    }

    private void replaceBranches() {
        Iterator<SsaInsn> it = this.branchWorklist.iterator();
        while (it.hasNext()) {
            SsaInsn next = it.next();
            SsaBasicBlock block = next.getBlock();
            int size = block.getSuccessorList().size();
            int i = 0;
            int i2 = -1;
            while (i < size) {
                int i3 = block.getSuccessorList().get(i);
                if (this.executableBlocks.get(i3)) {
                    i3 = i2;
                }
                i++;
                i2 = i3;
            }
            if (size == 2 && i2 != -1) {
                block.replaceLastInsn(new PlainInsn(Rops.GOTO, next.getOriginalRopInsn().getPosition(), (RegisterSpec) null, RegisterSpecList.EMPTY));
                block.removeSuccessor(i2);
            }
        }
    }
}
