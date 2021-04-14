package mod.agus.jcoderz.dx.io.instructions;

import mod.agus.jcoderz.dx.io.IndexType;

public final class TwoRegisterDecodedInstruction extends DecodedInstruction {
    private final int a;
    private final int b;

    public TwoRegisterDecodedInstruction(InstructionCodec instructionCodec, int i, int i2, IndexType indexType, int i3, long j, int i4, int i5) {
        super(instructionCodec, i, i2, indexType, i3, j);
        this.a = i4;
        this.b = i5;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public int getRegisterCount() {
        return 2;
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
    public DecodedInstruction withIndex(int i) {
        return new TwoRegisterDecodedInstruction(getFormat(), getOpcode(), i, getIndexType(), getTarget(), getLiteral(), this.a, this.b);
    }
}
