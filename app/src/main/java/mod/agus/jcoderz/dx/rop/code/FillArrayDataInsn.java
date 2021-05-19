package mod.agus.jcoderz.dx.rop.code;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;

public final class FillArrayDataInsn extends Insn {
    private final Constant arrayType;
    private final ArrayList<Constant> initValues;

    public FillArrayDataInsn(Rop rop, SourcePosition sourcePosition, RegisterSpecList registerSpecList, ArrayList<Constant> arrayList, Constant constant) {
        super(rop, sourcePosition, null, registerSpecList);
        if (rop.getBranchingness() != 1) {
            throw new IllegalArgumentException("bogus branchingness");
        }
        this.initValues = arrayList;
        this.arrayType = constant;
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public TypeList getCatches() {
        return StdTypeList.EMPTY;
    }

    public ArrayList<Constant> getInitValues() {
        return this.initValues;
    }

    public Constant getConstant() {
        return this.arrayType;
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public void accept(Insn.Visitor visitor) {
        visitor.visitFillArrayDataInsn(this);
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public Insn withAddedCatch(Type type) {
        throw new UnsupportedOperationException("unsupported");
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public Insn withRegisterOffset(int i) {
        return new FillArrayDataInsn(getOpcode(), getPosition(), getSources().withOffset(i), this.initValues, this.arrayType);
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public Insn withNewRegisters(RegisterSpec registerSpec, RegisterSpecList registerSpecList) {
        return new FillArrayDataInsn(getOpcode(), getPosition(), registerSpecList, this.initValues, this.arrayType);
    }
}
