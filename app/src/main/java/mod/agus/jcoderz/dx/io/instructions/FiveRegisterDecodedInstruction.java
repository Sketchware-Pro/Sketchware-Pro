package mod.agus.jcoderz.dx.io.instructions;

import mod.agus.jcoderz.dx.io.IndexType;

public final class FiveRegisterDecodedInstruction extends DecodedInstruction {
    private final int a;
    private final int b;
    private final int c;
    private final int d;
    private final int e;

    public FiveRegisterDecodedInstruction(InstructionCodec instructionCodec, int i, int i2, IndexType indexType, int i3, long j, int i4, int i5, int i6, int i7, int i8) {
        super(instructionCodec, i, i2, indexType, i3, j);
        this.a = i4;
        this.b = i5;
        this.c = i6;
        this.d = i7;
        this.e = i8;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public int getRegisterCount() {
        return 5;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public int getA() {
        return this.a;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public int getB() {
        return this.b;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public int getC() {
        return this.c;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public int getD() {
        return this.d;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public int getE() {
        return this.e;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public DecodedInstruction withIndex(int i) {
        return new FiveRegisterDecodedInstruction(getFormat(), getOpcode(), i, getIndexType(), getTarget(), getLiteral(), this.a, this.b, this.c, this.d, this.e);
    }
}
