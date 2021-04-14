package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.rop.type.TypeList;

public final class PlainInsn extends Insn {
    public PlainInsn(Rop rop, SourcePosition sourcePosition, RegisterSpec registerSpec, RegisterSpecList registerSpecList) {
        super(rop, sourcePosition, registerSpec, registerSpecList);
        switch (rop.getBranchingness()) {
            case 5:
            case 6:
                throw new IllegalArgumentException("bogus branchingness");
            default:
                if (registerSpec != null && rop.getBranchingness() != 1) {
                    throw new IllegalArgumentException("can't mix branchingness with result");
                }
                return;
        }
    }

    public PlainInsn(Rop rop, SourcePosition sourcePosition, RegisterSpec registerSpec, RegisterSpec registerSpec2) {
        this(rop, sourcePosition, registerSpec, RegisterSpecList.make(registerSpec2));
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public TypeList getCatches() {
        return StdTypeList.EMPTY;
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public void accept(Insn.Visitor visitor) {
        visitor.visitPlainInsn(this);
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public Insn withAddedCatch(Type type) {
        throw new UnsupportedOperationException("unsupported");
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public Insn withRegisterOffset(int i) {
        return new PlainInsn(getOpcode(), getPosition(), getResult().withOffset(i), getSources().withOffset(i));
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public Insn withSourceLiteral() {
        CstInteger cstInteger;
        int i;
        RegisterSpecList sources = getSources();
        int size = sources.size();
        if (size == 0) {
            return this;
        }
        TypeBearer typeBearer = sources.get(size - 1).getTypeBearer();
        if (!typeBearer.isConstant()) {
            TypeBearer typeBearer2 = sources.get(0).getTypeBearer();
            if (size != 2 || !typeBearer2.isConstant()) {
                return this;
            }
            Constant constant = (Constant) typeBearer2;
            RegisterSpecList withoutFirst = sources.withoutFirst();
            return new PlainCstInsn(Rops.ropFor(getOpcode().getOpcode(), getResult(), withoutFirst, constant), getPosition(), getResult(), withoutFirst, constant);
        }
        Constant constant2 = (Constant) typeBearer;
        RegisterSpecList withoutLast = sources.withoutLast();
        try {
            int opcode = getOpcode().getOpcode();
            if (opcode != 15 || !(constant2 instanceof CstInteger)) {
                cstInteger = (CstInteger) constant2;
                i = opcode;
            } else {
                cstInteger = CstInteger.make(-((CstInteger) constant2).getValue());
                i = 14;
            }
            return new PlainCstInsn(Rops.ropFor(i, getResult(), withoutLast, cstInteger), getPosition(), getResult(), withoutLast, cstInteger);
        } catch (IllegalArgumentException e) {
            return this;
        }
    }

    @Override // mod.agus.jcoderz.dx.rop.code.Insn
    public Insn withNewRegisters(RegisterSpec registerSpec, RegisterSpecList registerSpecList) {
        return new PlainInsn(getOpcode(), getPosition(), registerSpec, registerSpecList);
    }
}
