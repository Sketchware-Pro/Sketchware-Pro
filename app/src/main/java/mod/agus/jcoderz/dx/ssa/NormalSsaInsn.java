package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.ssa.SsaInsn;

public final class NormalSsaInsn extends SsaInsn implements Cloneable {
    private Insn insn;

    NormalSsaInsn(Insn insn2, SsaBasicBlock ssaBasicBlock) {
        super(insn2.getResult(), ssaBasicBlock);
        this.insn = insn2;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public final void mapSourceRegisters(RegisterMapper registerMapper) {
        RegisterSpecList sources = this.insn.getSources();
        RegisterSpecList map = registerMapper.map(sources);
        if (map != sources) {
            this.insn = this.insn.withNewRegisters(getResult(), map);
            getBlock().getParent().onSourcesChanged(this, sources);
        }
    }

    public final void changeOneSource(int i, RegisterSpec registerSpec) {
        RegisterSpecList sources = this.insn.getSources();
        int size = sources.size();
        RegisterSpecList registerSpecList = new RegisterSpecList(size);
        int i2 = 0;
        while (i2 < size) {
            registerSpecList.set(i2, i2 == i ? registerSpec : sources.get(i2));
            i2++;
        }
        registerSpecList.setImmutable();
        RegisterSpec registerSpec2 = sources.get(i);
        if (registerSpec2.getReg() != registerSpec.getReg()) {
            getBlock().getParent().onSourceChanged(this, registerSpec2, registerSpec);
        }
        this.insn = this.insn.withNewRegisters(getResult(), registerSpecList);
    }

    public final void setNewSources(RegisterSpecList registerSpecList) {
        if (this.insn.getSources().size() != registerSpecList.size()) {
            throw new RuntimeException("Sources counts don't match");
        }
        this.insn = this.insn.withNewRegisters(getResult(), registerSpecList);
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn, mod.agus.jcoderz.dx.ssa.SsaInsn, java.lang.Object
    public NormalSsaInsn clone() {
        return (NormalSsaInsn) super.clone();
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public RegisterSpecList getSources() {
        return this.insn.getSources();
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return toRopInsn().toHuman();
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public Insn toRopInsn() {
        return this.insn.withNewRegisters(getResult(), this.insn.getSources());
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public Rop getOpcode() {
        return this.insn.getOpcode();
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public Insn getOriginalRopInsn() {
        return this.insn;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public RegisterSpec getLocalAssignment() {
        RegisterSpec result;
        if (this.insn.getOpcode().getOpcode() == 54) {
            result = this.insn.getSources().get(0);
        } else {
            result = getResult();
        }
        if (result == null || result.getLocalItem() == null) {
            return null;
        }
        return result;
    }

    public void upgradeToLiteral() {
        RegisterSpecList sources = this.insn.getSources();
        this.insn = this.insn.withSourceLiteral();
        getBlock().getParent().onSourcesChanged(this, sources);
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public boolean isNormalMoveInsn() {
        return this.insn.getOpcode().getOpcode() == 2;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public boolean isMoveException() {
        return this.insn.getOpcode().getOpcode() == 4;
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public boolean canThrow() {
        return this.insn.canThrow();
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public void accept(SsaInsn.Visitor visitor) {
        if (isNormalMoveInsn()) {
            visitor.visitMoveInsn(this);
        } else {
            visitor.visitNonMoveInsn(this);
        }
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public boolean isPhiOrMove() {
        return isNormalMoveInsn();
    }

    @Override // mod.agus.jcoderz.dx.ssa.SsaInsn
    public boolean hasSideEffect() {
        boolean z;
        Rop opcode = getOpcode();
        if (opcode.getBranchingness() != 1) {
            return true;
        }
        if (!Optimizer.getPreserveLocals() || getLocalAssignment() == null) {
            z = false;
        } else {
            z = true;
        }
        switch (opcode.getOpcode()) {
            case 2:
            case 5:
            case 55:
                return z;
            default:
                return true;
        }
    }
}
