package mod.agus.jcoderz.dex;

import junit.framework.TestCase;
import mod.agus.jcoderz.dex.util.ByteArrayByteInput;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;

public final class EncodedValueReaderTest extends TestCase {
    public void testReadByte() {
        assertEquals(Opcodes.OPC_ior, readerOf(0, 128).readByte());
        assertEquals((byte) -1, readerOf(0, 255).readByte());
        assertEquals((byte) 0, readerOf(0, 0).readByte());
        assertEquals((byte) 1, readerOf(0, 1).readByte());
        assertEquals(Opcodes.OPC_land, readerOf(0, 127).readByte());
    }

    public void testReadShort() {
        assertEquals(Short.MIN_VALUE, readerOf(34, 0, 128).readShort());
        assertEquals(0, readerOf(2, 0).readShort());
        assertEquals(171, readerOf(34, 171, 0).readShort());
        assertEquals(-21555, readerOf(34, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171).readShort());
        assertEquals(Short.MAX_VALUE, readerOf(34, 255, 127).readShort());
    }

    public void testReadInt() {
        assertEquals(Integer.MIN_VALUE, readerOf(100, 0, 0, 0, 128).readInt());
        assertEquals(0, readerOf(4, 0).readInt());
        assertEquals(171, readerOf(36, 171, 0).readInt());
        assertEquals(43981, readerOf(68, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171, 0).readInt());
        assertEquals(11259375, readerOf(100, 239, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171, 0).readInt());
        assertEquals(-1412567295, readerOf(100, 1, 239, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171).readInt());
        assertEquals(Integer.MAX_VALUE, readerOf(100, 255, 255, 255, 127).readInt());
    }

    public void testReadLong() {
        assertEquals(Long.MIN_VALUE, readerOf(-26, 0, 0, 0, 0, 0, 0, 0, 128).readLong());
        assertEquals(0, readerOf(6, 0).readLong());
        assertEquals(171, readerOf(38, 171, 0).readLong());
        assertEquals(43981, readerOf(70, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171, 0).readLong());
        assertEquals(11259375, readerOf(102, 239, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171, 0).readLong());
        assertEquals(2882400001L, readerOf(-122, 1, 239, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171, 0).readLong());
        assertEquals(737894400291L, readerOf(-90, 35, 1, 239, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171, 0).readLong());
        assertEquals(188900966474565L, readerOf(-58, 69, 35, 1, 239, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171, 0).readLong());
        assertEquals(48358647417488743L, readerOf(-26, 103, 69, 35, 1, 239, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171, 0).readLong());
        assertEquals(-6066930334832433271L, readerOf(-26, 137, 103, 69, 35, 1, 239, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171).readLong());
        assertEquals(ClassFileConstants.JDK_DEFERRED, readerOf(-26, 255, 255, 255, 255, 255, 255, 255, 127).readLong());
    }

    public void testReadFloat() {
        assertEquals(Float.valueOf(Float.NEGATIVE_INFINITY), Float.valueOf(readerOf(48, -128, -1).readFloat()));
        assertEquals(Float.valueOf(Float.POSITIVE_INFINITY), Float.valueOf(readerOf(48, -128, 127).readFloat()));
        assertEquals(Float.valueOf(Float.NaN), Float.valueOf(readerOf(48, -64, 127).readFloat()));
        assertEquals(Float.valueOf(-0.0f), Float.valueOf(readerOf(16, -128).readFloat()));
        assertEquals(Float.valueOf(0.0f), Float.valueOf(readerOf(16, 0).readFloat()));
        assertEquals(Float.valueOf(0.5f), Float.valueOf(readerOf(16, 63).readFloat()));
        assertEquals(Float.valueOf(1.0f), Float.valueOf(readerOf(48, -128, 63).readFloat()));
        assertEquals(Float.valueOf(1000000.0f), Float.valueOf(readerOf(80, 36, 116, 73).readFloat()));
        assertEquals(Float.valueOf(1.0E12f), Float.valueOf(readerOf(112, -91, -44, 104, 83).readFloat()));
    }

    public void testReadDouble() {
        assertEquals(Double.valueOf(Double.NEGATIVE_INFINITY), Double.valueOf(readerOf(49, -16, -1).readDouble()));
        assertEquals(Double.valueOf(Double.POSITIVE_INFINITY), Double.valueOf(readerOf(49, -16, 127).readDouble()));
        assertEquals(Double.valueOf(Double.NaN), Double.valueOf(readerOf(49, -8, 127).readDouble()));
        assertEquals(Double.valueOf(-0.0d), Double.valueOf(readerOf(17, -128).readDouble()));
        assertEquals(Double.valueOf(0.0d), Double.valueOf(readerOf(17, 0).readDouble()));
        assertEquals(Double.valueOf(0.5d), Double.valueOf(readerOf(49, -32, 63).readDouble()));
        assertEquals(Double.valueOf(1.0d), Double.valueOf(readerOf(49, -16, 63).readDouble()));
        assertEquals(Double.valueOf(1000000.0d), Double.valueOf(readerOf(113, -128, -124, 46, 65).readDouble()));
        assertEquals(Double.valueOf(1.0E12d), Double.valueOf(readerOf(-111, -94, -108, 26, 109, 66).readDouble()));
        assertEquals(Double.valueOf(1.0E24d), Double.valueOf(readerOf(-15, -76, -99, -39, 121, 67, 120, -22, 68).readDouble()));
    }

    public void testReadChar() {
        assertEquals(0, readerOf(3, 0).readChar());
        assertEquals(171, readerOf(3, 171).readChar());
        assertEquals(43981, readerOf(35, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171).readChar());
        assertEquals(65535, readerOf(35, 255, 255).readChar());
    }

    public void testReadBoolean() {
        assertEquals(true, readerOf(63).readBoolean());
        assertEquals(false, readerOf(31).readBoolean());
    }

    public void testReadNull() {
        readerOf(30).readNull();
    }

    public void testReadReference() {
        assertEquals(171, readerOf(23, 171).readString());
        assertEquals(43981, readerOf(55, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171).readString());
        assertEquals(11259375, readerOf(87, 239, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171).readString());
        assertEquals(-1412567295, readerOf(119, 1, 239, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, 171).readString());
    }

    public void testReadWrongType() {
        try {
            readerOf(23, 171).readField();
            fail();
        } catch (IllegalStateException e) {
        }
    }

    private EncodedValueReader readerOf(int... iArr) {
        byte[] bArr = new byte[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            bArr[i] = (byte) iArr[i];
        }
        return new EncodedValueReader(new ByteArrayByteInput(bArr));
    }
}
