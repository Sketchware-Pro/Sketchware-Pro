package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.io.OpcodeInfo;
import mod.agus.jcoderz.dx.io.Opcodes;

public final class Dop {
    private final int family;
    private final InsnFormat format;
    private final boolean hasResult;
    private final int nextOpcode;
    private final int opcode;

    public Dop(int i, int i2, int i3, InsnFormat insnFormat, boolean z) {
        if (!Opcodes.isValidShape(i)) {
            throw new IllegalArgumentException("bogus opcode");
        } else if (!Opcodes.isValidShape(i2)) {
            throw new IllegalArgumentException("bogus family");
        } else if (!Opcodes.isValidShape(i3)) {
            throw new IllegalArgumentException("bogus nextOpcode");
        } else if (insnFormat == null) {
            throw new NullPointerException("format == null");
        } else {
            this.opcode = i;
            this.family = i2;
            this.nextOpcode = i3;
            this.format = insnFormat;
            this.hasResult = z;
        }
    }

    public String toString() {
        return getName();
    }

    public int getOpcode() {
        return this.opcode;
    }

    public int getFamily() {
        return this.family;
    }

    public InsnFormat getFormat() {
        return this.format;
    }

    public boolean hasResult() {
        return this.hasResult;
    }

    public String getName() {
        return OpcodeInfo.getName(this.opcode);
    }

    public int getNextOpcode() {
        return this.nextOpcode;
    }

    public Dop getOppositeTest() {
        switch (this.opcode) {
            case 50:
                return Dops.IF_NE;
            case 51:
                return Dops.IF_EQ;
            case 52:
                return Dops.IF_GE;
            case 53:
                return Dops.IF_LT;
            case 54:
                return Dops.IF_LE;
            case 55:
                return Dops.IF_GT;
            case 56:
                return Dops.IF_NEZ;
            case 57:
                return Dops.IF_EQZ;
            case 58:
                return Dops.IF_GEZ;
            case 59:
                return Dops.IF_LTZ;
            case 60:
                return Dops.IF_LEZ;
            case 61:
                return Dops.IF_GTZ;
            default:
                throw new IllegalArgumentException("bogus opcode: " + this);
        }
    }
}
