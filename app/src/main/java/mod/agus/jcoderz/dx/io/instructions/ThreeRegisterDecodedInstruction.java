package mod.agus.jcoderz.dx.io.instructions;

import mod.agus.jcoderz.dx.io.IndexType;

public final class ThreeRegisterDecodedInstruction extends DecodedInstruction {
    private final int a;
    private final int b;
    private final int c;

    public ThreeRegisterDecodedInstruction(InstructionCodec instructionCodec, int i, int i2, IndexType indexType, int i3, long j, int i4, int i5, int i6) {
        super(instructionCodec, i, i2, indexType, i3, j);
        this.a = i4;
        this.b = i5;
        this.c = i6;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public int getRegisterCount() {
        return 3;
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
    public DecodedInstruction withIndex(int i) {
        return new ThreeRegisterDecodedInstruction(getFormat(), getOpcode(), i, getIndexType(), getTarget(), getLiteral(), this.a, this.b, this.c);
    }
}
