package mod.agus.jcoderz.dx.dex.code;

import java.util.BitSet;

import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.ssa.RegisterMapper;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.TwoColumnOutput;

public abstract class DalvInsn {
    private final Dop opcode;
    private final SourcePosition position;
    private final RegisterSpecList registers;
    private int address;

    public DalvInsn(Dop dop, SourcePosition sourcePosition, RegisterSpecList registerSpecList) {
        if (dop == null) {
            throw new NullPointerException("opcode == null");
        } else if (sourcePosition == null) {
            throw new NullPointerException("position == null");
        } else if (registerSpecList == null) {
            throw new NullPointerException("registers == null");
        } else {
            this.address = -1;
            this.opcode = dop;
            this.position = sourcePosition;
            this.registers = registerSpecList;
        }
    }

    public static SimpleInsn makeMove(SourcePosition sourcePosition, RegisterSpec registerSpec, RegisterSpec registerSpec2) {
        Dop dop;
        boolean z = true;
        if (registerSpec.getCategory() != 1) {
            z = false;
        }
        boolean isReference = registerSpec.getType().isReference();
        int reg = registerSpec.getReg();
        if ((registerSpec2.getReg() | reg) < 16) {
            dop = isReference ? Dops.MOVE_OBJECT : z ? Dops.MOVE : Dops.MOVE_WIDE;
        } else if (reg < 256) {
            dop = isReference ? Dops.MOVE_OBJECT_FROM16 : z ? Dops.MOVE_FROM16 : Dops.MOVE_WIDE_FROM16;
        } else if (isReference) {
            dop = Dops.MOVE_OBJECT_16;
        } else {
            dop = z ? Dops.MOVE_16 : Dops.MOVE_WIDE_16;
        }
        return new SimpleInsn(dop, sourcePosition, RegisterSpecList.make(registerSpec, registerSpec2));
    }

    public abstract String argString();

    public abstract int codeSize();

    public abstract String listingString0(boolean z);

    public abstract DalvInsn withOpcode(Dop dop);

    public abstract DalvInsn withRegisterOffset(int i);

    public abstract DalvInsn withRegisters(RegisterSpecList registerSpecList);

    public abstract void writeTo(AnnotatedOutput annotatedOutput);

    public final String toString() {
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append(identifierString());
        stringBuffer.append(' ');
        stringBuffer.append(this.position);
        stringBuffer.append(": ");
        stringBuffer.append(this.opcode.getName());
        boolean z = false;
        if (this.registers.size() != 0) {
            stringBuffer.append(this.registers.toHuman(" ", ", ", null));
            z = true;
        }
        String argString = argString();
        if (argString != null) {
            if (z) {
                stringBuffer.append(',');
            }
            stringBuffer.append(' ');
            stringBuffer.append(argString);
        }
        return stringBuffer.toString();
    }

    public final boolean hasAddress() {
        return this.address >= 0;
    }

    public final int getAddress() {
        if (this.address >= 0) {
            return this.address;
        }
        throw new RuntimeException("address not yet known");
    }

    public final void setAddress(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("address < 0");
        }
        this.address = i;
    }

    public final Dop getOpcode() {
        return this.opcode;
    }

    public final SourcePosition getPosition() {
        return this.position;
    }

    public final RegisterSpecList getRegisters() {
        return this.registers;
    }

    public final boolean hasResult() {
        return this.opcode.hasResult();
    }

    public final int getMinimumRegisterRequirement(BitSet bitSet) {
        boolean hasResult = hasResult();
        int size = this.registers.size();
        int category = (!hasResult || bitSet.get(0)) ? 0 : this.registers.get(0).getCategory();
        int i = 0;
        for (int i2 = hasResult ? 1 : 0; i2 < size; i2++) {
            if (!bitSet.get(i2)) {
                i += this.registers.get(i2).getCategory();
            }
        }
        return Math.max(i, category);
    }

    public DalvInsn getLowRegVersion() {
        return withRegisters(this.registers.withExpandedRegisters(0, hasResult(), null));
    }

    public DalvInsn expandedPrefix(BitSet bitSet) {
        RegisterSpecList registerSpecList = this.registers;
        boolean z = bitSet.get(0);
        if (hasResult()) {
            bitSet.set(0);
        }
        RegisterSpecList subset = registerSpecList.subset(bitSet);
        if (hasResult()) {
            bitSet.set(0, z);
        }
        if (subset.size() == 0) {
            return null;
        }
        return new HighRegisterPrefix(this.position, subset);
    }

    public DalvInsn expandedSuffix(BitSet bitSet) {
        if (!hasResult() || bitSet.get(0)) {
            return null;
        }
        RegisterSpec registerSpec = this.registers.get(0);
        return makeMove(this.position, registerSpec, registerSpec.withReg(0));
    }

    public DalvInsn expandedVersion(BitSet bitSet) {
        return withRegisters(this.registers.withExpandedRegisters(0, hasResult(), bitSet));
    }

    public final String identifierString() {
        if (this.address == -1) {
            return Hex.u4(System.identityHashCode(this));
        }
        return String.format("%04x", Integer.valueOf(this.address));
    }

    public final String listingString(String str, int i, boolean z) {
        String listingString0 = listingString0(z);
        if (listingString0 == null) {
            return null;
        }
        String str2 = str + identifierString() + ": ";
        int length = str2.length();
        return TwoColumnOutput.toString(str2, length, "", listingString0, i == 0 ? listingString0.length() : i - length);
    }

    public final int getNextAddress() {
        return getAddress() + codeSize();
    }

    public DalvInsn withMapper(RegisterMapper registerMapper) {
        return withRegisters(registerMapper.map(getRegisters()));
    }
}
