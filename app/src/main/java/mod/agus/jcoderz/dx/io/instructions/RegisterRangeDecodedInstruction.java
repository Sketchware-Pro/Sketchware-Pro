package mod.agus.jcoderz.dx.io.instructions;

import mod.agus.jcoderz.dx.io.IndexType;

public final class RegisterRangeDecodedInstruction extends DecodedInstruction {
    private final int a;
    private final int registerCount;

    public RegisterRangeDecodedInstruction(InstructionCodec instructionCodec, int i, int i2, IndexType indexType, int i3, long j, int i4, int i5) {
        super(instructionCodec, i, i2, indexType, i3, j);
        this.a = i4;
        this.registerCount = i5;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public int getRegisterCount() {
        return this.registerCount;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public int getA() {
        return this.a;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public DecodedInstruction withIndex(int i) {
        return new RegisterRangeDecodedInstruction(getFormat(), getOpcode(), i, getIndexType(), getTarget(), getLiteral(), this.a, this.registerCount);
    }
}
