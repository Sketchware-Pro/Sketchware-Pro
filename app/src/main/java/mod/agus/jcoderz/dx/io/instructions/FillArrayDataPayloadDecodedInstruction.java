package mod.agus.jcoderz.dx.io.instructions;

public final class FillArrayDataPayloadDecodedInstruction extends DecodedInstruction {
    private final Object data;
    private final int elementWidth;
    private final int size;

    private FillArrayDataPayloadDecodedInstruction(InstructionCodec instructionCodec, int i, Object obj, int i2, int i3) {
        super(instructionCodec, i, 0, null, 0, 0);
        this.data = obj;
        this.size = i2;
        this.elementWidth = i3;
    }

    public FillArrayDataPayloadDecodedInstruction(InstructionCodec instructionCodec, int i, byte[] bArr) {
        this(instructionCodec, i, bArr, bArr.length, 1);
    }

    public FillArrayDataPayloadDecodedInstruction(InstructionCodec instructionCodec, int i, short[] sArr) {
        this(instructionCodec, i, sArr, sArr.length, 2);
    }

    public FillArrayDataPayloadDecodedInstruction(InstructionCodec instructionCodec, int i, int[] iArr) {
        this(instructionCodec, i, iArr, iArr.length, 4);
    }

    public FillArrayDataPayloadDecodedInstruction(InstructionCodec instructionCodec, int i, long[] jArr) {
        this(instructionCodec, i, jArr, jArr.length, 8);
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public int getRegisterCount() {
        return 0;
    }

    public short getElementWidthUnit() {
        return (short) this.elementWidth;
    }

    public int getSize() {
        return this.size;
    }

    public Object getData() {
        return this.data;
    }

    @Override // mod.agus.jcoderz.dx.io.instructions.DecodedInstruction
    public DecodedInstruction withIndex(int i) {
        throw new UnsupportedOperationException("no index in instruction");
    }
}
