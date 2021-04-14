package mod.agus.jcoderz.dx.io.instructions;

public final class SparseSwitchPayloadDecodedInstruction extends DecodedInstruction {
    private final int[] keys;
    private final int[] targets;

    public SparseSwitchPayloadDecodedInstruction(InstructionCodec instructionCodec, int i, int[] iArr, int[] iArr2) {
        super(instructionCodec, i, 0, null, 0, 0);
        if (iArr.length != iArr2.length) {
            throw new IllegalArgumentException("keys/targets length mismatch");
        }
        this.keys = iArr;
        this.targets = iArr2;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public int getRegisterCount() {
        return 0;
    }

    public int[] getKeys() {
        return this.keys;
    }

    public int[] getTargets() {
        return this.targets;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public DecodedInstruction withIndex(int i) {
        throw new UnsupportedOperationException("no index in instruction");
    }
}
