package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.util.Hex;

public final class ReturnAddress implements TypeBearer {
    private final int subroutineAddress;

    public ReturnAddress(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("subroutineAddress < 0");
        }
        this.subroutineAddress = i;
    }

    public String toString() {
        return "<addr:" + Hex.u2(this.subroutineAddress) + ">";
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return toString();
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public Type getType() {
        return Type.RETURN_ADDRESS;
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public TypeBearer getFrameType() {
        return this;
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public int getBasicType() {
        return Type.RETURN_ADDRESS.getBasicType();
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public int getBasicFrameType() {
        return Type.RETURN_ADDRESS.getBasicFrameType();
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public boolean isConstant() {
        return false;
    }

    public boolean equals(Object obj) {
        if ((obj instanceof ReturnAddress) && this.subroutineAddress == ((ReturnAddress) obj).subroutineAddress) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.subroutineAddress;
    }

    public int getSubroutineAddress() {
        return this.subroutineAddress;
    }
}
