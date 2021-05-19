package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.cst.Constant;

public final class CstInsn extends FixedSizeInsn {
    private final Constant constant;
    private int classIndex;
    private int index;

    public CstInsn(Dop dop, SourcePosition sourcePosition, RegisterSpecList registerSpecList, Constant constant2) {
        super(dop, sourcePosition, registerSpecList);
        if (constant2 == null) {
            throw new NullPointerException("constant == null");
        }
        this.constant = constant2;
        this.index = -1;
        this.classIndex = -1;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public DalvInsn withOpcode(Dop dop) {
        CstInsn cstInsn = new CstInsn(dop, getPosition(), getRegisters(), this.constant);
        if (this.index >= 0) {
            cstInsn.setIndex(this.index);
        }
        if (this.classIndex >= 0) {
            cstInsn.setClassIndex(this.classIndex);
        }
        return cstInsn;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public DalvInsn withRegisters(RegisterSpecList registerSpecList) {
        CstInsn cstInsn = new CstInsn(getOpcode(), getPosition(), registerSpecList, this.constant);
        if (this.index >= 0) {
            cstInsn.setIndex(this.index);
        }
        if (this.classIndex >= 0) {
            cstInsn.setClassIndex(this.classIndex);
        }
        return cstInsn;
    }

    public Constant getConstant() {
        return this.constant;
    }

    public int getIndex() {
        if (this.index >= 0) {
            return this.index;
        }
        throw new RuntimeException("index not yet set for " + this.constant);
    }

    public void setIndex(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("index < 0");
        } else if (this.index >= 0) {
            throw new RuntimeException("index already set");
        } else {
            this.index = i;
        }
    }

    public boolean hasIndex() {
        return this.index >= 0;
    }

    public int getClassIndex() {
        if (this.classIndex >= 0) {
            return this.classIndex;
        }
        throw new RuntimeException("class index not yet set");
    }

    public void setClassIndex(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("index < 0");
        } else if (this.classIndex >= 0) {
            throw new RuntimeException("class index already set");
        } else {
            this.classIndex = i;
        }
    }

    public boolean hasClassIndex() {
        return this.classIndex >= 0;
    }

    @Override // mod.agus.jcoderz.dx.dex.code.DalvInsn
    public String argString() {
        return this.constant.toHuman();
    }
}
