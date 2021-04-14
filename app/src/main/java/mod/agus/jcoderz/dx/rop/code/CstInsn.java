package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.cst.Constant;

public abstract class CstInsn extends Insn {
    private final Constant cst;

    public CstInsn(Rop rop, SourcePosition sourcePosition, RegisterSpec registerSpec, RegisterSpecList registerSpecList, Constant constant) {
        super(rop, sourcePosition, registerSpec, registerSpecList);
        if (constant == null) {
            throw new NullPointerException("cst == null");
        }
        this.cst = constant;
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public String getInlineString() {
        return this.cst.toHuman();
    }

    public Constant getConstant() {
        return this.cst;
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public boolean contentEquals(Insn insn) {
        return super.contentEquals(insn) && this.cst.equals(((CstInsn) insn).getConstant());
    }
}
