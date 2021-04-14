package mod.agus.jcoderz.dx.rop.type;

import mod.agus.jcoderz.dx.util.ToHuman;

public interface TypeBearer extends ToHuman {
    int getBasicFrameType();

    int getBasicType();

    TypeBearer getFrameType();

    Type getType();

    boolean isConstant();
}
