package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.ssa.SsaInsn;
import mod.agus.jcoderz.dx.util.Hex;

public final class PhiInsn extends SsaInsn {
    private final ArrayList<Operand> operands = new ArrayList<>();
    private final int ropResultReg;
    private RegisterSpecList sources;

    public interface Visitor {
        void visitPhiInsn(PhiInsn phiInsn);
    }

    public PhiInsn(RegisterSpec registerSpec, SsaBasicBlock ssaBasicBlock) {
        super(registerSpec, ssaBasicBlock);
        this.ropResultReg = registerSpec.getReg();
    }

    public PhiInsn(int i, SsaBasicBlock ssaBasicBlock) {
        super(RegisterSpec.make(i, Type.VOID), ssaBasicBlock);
        this.ropResultReg = i;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn, mod.agus.jcoderz.dx.ssa.SsaInsn, java.lang.Object
    public PhiInsn clone() {
        throw new UnsupportedOperationException("can't clone phi");
    }

    public void updateSourcesToDefinitions(SsaMethod ssaMethod) {
        Iterator<Operand> it = this.operands.iterator();
        while (it.hasNext()) {
            Operand next = it.next();
            next.regSpec = next.regSpec.withType(ssaMethod.getDefinitionForRegister(next.regSpec.getReg()).getResult().getType());
        }
        this.sources = null;
    }

    public void changeResultType(TypeBearer typeBearer, LocalItem localItem) {
        setResult(RegisterSpec.makeLocalOptional(getResult().getReg(), typeBearer, localItem));
    }

    public int getRopResultReg() {
        return this.ropResultReg;
    }

    public void addPhiOperand(RegisterSpec registerSpec, SsaBasicBlock ssaBasicBlock) {
        this.operands.add(new Operand(registerSpec, ssaBasicBlock.getIndex(), ssaBasicBlock.getRopLabel()));
        this.sources = null;
    }

    public void removePhiRegister(RegisterSpec registerSpec) {
        ArrayList arrayList = new ArrayList();
        Iterator<Operand> it = this.operands.iterator();
        while (it.hasNext()) {
            Operand next = it.next();
            if (next.regSpec.getReg() == registerSpec.getReg()) {
                arrayList.add(next);
            }
        }
        this.operands.removeAll(arrayList);
        this.sources = null;
    }

    public int predBlockIndexForSourcesIndex(int i) {
        return this.operands.get(i).blockIndex;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public Rop getOpcode() {
        return null;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public Insn getOriginalRopInsn() {
        return null;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public boolean canThrow() {
        return false;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public RegisterSpecList getSources() {
        if (this.sources != null) {
            return this.sources;
        }
        if (this.operands.size() == 0) {
            return RegisterSpecList.EMPTY;
        }
        int size = this.operands.size();
        this.sources = new RegisterSpecList(size);
        for (int i = 0; i < size; i++) {
            this.sources.set(i, this.operands.get(i).regSpec);
        }
        this.sources.setImmutable();
        return this.sources;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public boolean isRegASource(int i) {
        Iterator<Operand> it = this.operands.iterator();
        while (it.hasNext()) {
            if (it.next().regSpec.getReg() == i) {
                return true;
            }
        }
        return false;
    }

    public boolean areAllOperandsEqual() {
        if (this.operands.size() == 0) {
            return true;
        }
        int reg = this.operands.get(0).regSpec.getReg();
        Iterator<Operand> it = this.operands.iterator();
        while (it.hasNext()) {
            if (reg != it.next().regSpec.getReg()) {
                return false;
            }
        }
        return true;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public final void mapSourceRegisters(RegisterMapper registerMapper) {
        Iterator<Operand> it = this.operands.iterator();
        while (it.hasNext()) {
            Operand next = it.next();
            RegisterSpec registerSpec = next.regSpec;
            next.regSpec = registerMapper.map(registerSpec);
            if (registerSpec != next.regSpec) {
                getBlock().getParent().onSourceChanged(this, registerSpec, next.regSpec);
            }
        }
        this.sources = null;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public Insn toRopInsn() {
        throw new IllegalArgumentException("Cannot convert phi insns to rop form");
    }

    public List<SsaBasicBlock> predBlocksForReg(int i, SsaMethod ssaMethod) {
        ArrayList arrayList = new ArrayList();
        Iterator<Operand> it = this.operands.iterator();
        while (it.hasNext()) {
            Operand next = it.next();
            if (next.regSpec.getReg() == i) {
                arrayList.add(ssaMethod.getBlocks().get(next.blockIndex));
            }
        }
        return arrayList;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public boolean isPhiOrMove() {
        return true;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public boolean hasSideEffect() {
        return Optimizer.getPreserveLocals() && getLocalAssignment() != null;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public void accept(SsaInsn.Visitor visitor) {
        visitor.visitPhiInsn(this);
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return toHumanWithInline(null);
    }

    /* access modifiers changed from: protected */
    public final String toHumanWithInline(String str) {
        StringBuffer stringBuffer = new StringBuffer(80);
        stringBuffer.append(SourcePosition.NO_INFO);
        stringBuffer.append(": phi");
        if (str != null) {
            stringBuffer.append("(");
            stringBuffer.append(str);
            stringBuffer.append(")");
        }
        RegisterSpec result = getResult();
        if (result == null) {
            stringBuffer.append(" .");
        } else {
            stringBuffer.append(" ");
            stringBuffer.append(result.toHuman());
        }
        stringBuffer.append(" <-");
        int size = getSources().size();
        if (size == 0) {
            stringBuffer.append(" .");
        } else {
            for (int i = 0; i < size; i++) {
                stringBuffer.append(" ");
                stringBuffer.append(String.valueOf(this.sources.get(i).toHuman()) + "[b=" + Hex.u2(this.operands.get(i).ropLabel) + "]");
            }
        }
        return stringBuffer.toString();
    }

    /* access modifiers changed from: private */
    public static class Operand {
        public final int blockIndex;
        public RegisterSpec regSpec;
        public final int ropLabel;

        public Operand(RegisterSpec registerSpec, int i, int i2) {
            this.regSpec = registerSpec;
            this.blockIndex = i;
            this.ropLabel = i2;
        }
    }
}
