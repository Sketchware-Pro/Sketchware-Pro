package mod.agus.jcoderz.dx.io.instructions;

import java.io.EOFException;

import mod.agus.jcoderz.dex.DexException;
import mod.agus.jcoderz.dx.io.IndexType;
import mod.agus.jcoderz.dx.io.OpcodeInfo;
import mod.agus.jcoderz.dx.io.Opcodes;
import mod.agus.jcoderz.dx.util.Hex;

public abstract class DecodedInstruction {
    private final InstructionCodec format;
    private final int index;
    private final IndexType indexType;
    private final long literal;
    private final int opcode;
    private final int target;

    public DecodedInstruction(InstructionCodec instructionCodec, int i, int i2, IndexType indexType2, int i3, long j) {
        if (instructionCodec == null) {
            throw new NullPointerException("format == null");
        } else if (!Opcodes.isValidShape(i)) {
            throw new IllegalArgumentException("invalid opcode");
        } else {
            this.format = instructionCodec;
            this.opcode = i;
            this.index = i2;
            this.indexType = indexType2;
            this.target = i3;
            this.literal = j;
        }
    }

    public static DecodedInstruction decode(CodeInput codeInput) throws EOFException {
        int read = codeInput.read();
        return OpcodeInfo.getFormat(Opcodes.extractOpcodeFromUnit(read)).decode(read, codeInput);
    }

    public static DecodedInstruction[] decodeAll(short[] sArr) {
        DecodedInstruction[] decodedInstructionArr = new DecodedInstruction[sArr.length];
        ShortArrayCodeInput shortArrayCodeInput = new ShortArrayCodeInput(sArr);
        while (shortArrayCodeInput.hasMore()) {
            try {
                decodedInstructionArr[shortArrayCodeInput.cursor()] = decode(shortArrayCodeInput);
            } catch (EOFException e) {
                throw new DexException(e);
            }
        }
        return decodedInstructionArr;
    }

    public abstract int getRegisterCount();

    public abstract DecodedInstruction withIndex(int i);

    public final InstructionCodec getFormat() {
        return this.format;
    }

    public final int getOpcode() {
        return this.opcode;
    }

    public final short getOpcodeUnit() {
        return (short) this.opcode;
    }

    public final int getIndex() {
        return this.index;
    }

    public final short getIndexUnit() {
        return (short) this.index;
    }

    public final IndexType getIndexType() {
        return this.indexType;
    }

    public final int getTarget() {
        return this.target;
    }

    public final int getTarget(int i) {
        return this.target - i;
    }

    public final short getTargetUnit(int i) {
        int target2 = getTarget(i);
        if (target2 == ((short) target2)) {
            return (short) target2;
        }
        throw new DexException("Target out of range: " + Hex.s4(target2));
    }

    public final int getTargetByte(int i) {
        int target2 = getTarget(i);
        if (target2 == ((byte) target2)) {
            return target2 & 255;
        }
        throw new DexException("Target out of range: " + Hex.s4(target2));
    }

    public final long getLiteral() {
        return this.literal;
    }

    public final int getLiteralInt() {
        if (this.literal == ((long) ((int) this.literal))) {
            return (int) this.literal;
        }
        throw new DexException("Literal out of range: " + Hex.u8(this.literal));
    }

    public final short getLiteralUnit() {
        if (this.literal == ((long) ((short) ((int) this.literal)))) {
            return (short) ((int) this.literal);
        }
        throw new DexException("Literal out of range: " + Hex.u8(this.literal));
    }

    public final int getLiteralByte() {
        if (this.literal == ((long) ((byte) ((int) this.literal)))) {
            return ((int) this.literal) & 255;
        }
        throw new DexException("Literal out of range: " + Hex.u8(this.literal));
    }

    public final int getLiteralNibble() {
        if (this.literal >= -8 && this.literal <= 7) {
            return ((int) this.literal) & 15;
        }
        throw new DexException("Literal out of range: " + Hex.u8(this.literal));
    }

    public int getA() {
        return 0;
    }

    public int getB() {
        return 0;
    }

    public int getC() {
        return 0;
    }

    public int getD() {
        return 0;
    }

    public int getE() {
        return 0;
    }

    public final short getRegisterCountUnit() {
        int registerCount = getRegisterCount();
        if ((-65536 & registerCount) == 0) {
            return (short) registerCount;
        }
        throw new DexException("Register count out of range: " + Hex.u8((long) registerCount));
    }

    public final short getAUnit() {
        int a = getA();
        if ((-65536 & a) == 0) {
            return (short) a;
        }
        throw new DexException("Register A out of range: " + Hex.u8((long) a));
    }

    public final short getAByte() {
        int a = getA();
        if ((a & -256) == 0) {
            return (short) a;
        }
        throw new DexException("Register A out of range: " + Hex.u8((long) a));
    }

    public final short getANibble() {
        int a = getA();
        if ((a & -16) == 0) {
            return (short) a;
        }
        throw new DexException("Register A out of range: " + Hex.u8((long) a));
    }

    public final short getBUnit() {
        int b = getB();
        if ((-65536 & b) == 0) {
            return (short) b;
        }
        throw new DexException("Register B out of range: " + Hex.u8((long) b));
    }

    public final short getBByte() {
        int b = getB();
        if ((b & -256) == 0) {
            return (short) b;
        }
        throw new DexException("Register B out of range: " + Hex.u8((long) b));
    }

    public final short getBNibble() {
        int b = getB();
        if ((b & -16) == 0) {
            return (short) b;
        }
        throw new DexException("Register B out of range: " + Hex.u8((long) b));
    }

    public final short getCUnit() {
        int c = getC();
        if ((-65536 & c) == 0) {
            return (short) c;
        }
        throw new DexException("Register C out of range: " + Hex.u8((long) c));
    }

    public final short getCByte() {
        int c = getC();
        if ((c & -256) == 0) {
            return (short) c;
        }
        throw new DexException("Register C out of range: " + Hex.u8((long) c));
    }

    public final short getCNibble() {
        int c = getC();
        if ((c & -16) == 0) {
            return (short) c;
        }
        throw new DexException("Register C out of range: " + Hex.u8((long) c));
    }

    public final short getDUnit() {
        int d = getD();
        if ((-65536 & d) == 0) {
            return (short) d;
        }
        throw new DexException("Register D out of range: " + Hex.u8((long) d));
    }

    public final short getDByte() {
        int d = getD();
        if ((d & -256) == 0) {
            return (short) d;
        }
        throw new DexException("Register D out of range: " + Hex.u8((long) d));
    }

    public final short getDNibble() {
        int d = getD();
        if ((d & -16) == 0) {
            return (short) d;
        }
        throw new DexException("Register D out of range: " + Hex.u8((long) d));
    }

    public final short getENibble() {
        int e = getE();
        if ((e & -16) == 0) {
            return (short) e;
        }
        throw new DexException("Register E out of range: " + Hex.u8((long) e));
    }

    public final void encode(CodeOutput codeOutput) {
        this.format.encode(this, codeOutput);
    }
}
