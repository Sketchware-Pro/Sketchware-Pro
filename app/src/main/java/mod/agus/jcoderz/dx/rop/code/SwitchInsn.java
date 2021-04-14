package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.IntList;

public final class SwitchInsn extends Insn {
    private final IntList cases;

    public SwitchInsn(Rop rop, SourcePosition sourcePosition, RegisterSpec registerSpec, RegisterSpecList registerSpecList, IntList intList) {
        super(rop, sourcePosition, registerSpec, registerSpecList);
        if (rop.getBranchingness() != 5) {
            throw new IllegalArgumentException("bogus branchingness");
        } else if (intList == null) {
            throw new NullPointerException("cases == null");
        } else {
            this.cases = intList;
        }
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public String getInlineString() {
        return this.cases.toString();
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public TypeList getCatches() {
        return StdTypeList.EMPTY;
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public void accept(Insn.Visitor visitor) {
        visitor.visitSwitchInsn(this);
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public Insn withAddedCatch(Type type) {
        throw new UnsupportedOperationException("unsupported");
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public Insn withRegisterOffset(int i) {
        return new SwitchInsn(getOpcode(), getPosition(), getResult().withOffset(i), getSources().withOffset(i), this.cases);
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public boolean contentEquals(Insn insn) {
        return false;
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public Insn withNewRegisters(RegisterSpec registerSpec, RegisterSpecList registerSpecList) {
        return new SwitchInsn(getOpcode(), getPosition(), registerSpec, registerSpecList, this.cases);
    }

    public IntList getCases() {
        return this.cases;
    }
}
