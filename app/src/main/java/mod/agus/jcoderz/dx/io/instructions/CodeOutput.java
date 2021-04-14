package mod.agus.jcoderz.dx.io.instructions;

public interface CodeOutput extends CodeCursor {
    void write(short s);

    void write(short s, short s2);

    void write(short s, short s2, short s3);

    void write(short s, short s2, short s3, short s4);

    void write(short s, short s2, short s3, short s4, short s5);

    void write(byte[] bArr);

    void write(int[] iArr);

    void write(long[] jArr);

    void write(short[] sArr);

    void writeInt(int i);

    void writeLong(long j);
}
