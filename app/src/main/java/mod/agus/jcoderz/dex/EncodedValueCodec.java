package mod.agus.jcoderz.dex;

import mod.agus.jcoderz.dex.util.ByteInput;
import mod.agus.jcoderz.dex.util.ByteOutput;

public final class EncodedValueCodec {
    private EncodedValueCodec() {
    }

    public static void writeSignedIntegralValue(ByteOutput byteOutput, int i, long j) {
        int numberOfLeadingZeros = ((65 - Long.numberOfLeadingZeros((j >> 63) ^ j)) + 7) >> 3;
        byteOutput.writeByte(((numberOfLeadingZeros - 1) << 5) | i);
        while (numberOfLeadingZeros > 0) {
            byteOutput.writeByte((byte) ((int) j));
            j >>= 8;
            numberOfLeadingZeros--;
        }
    }

    public static void writeUnsignedIntegralValue(ByteOutput byteOutput, int i, long j) {
        int numberOfLeadingZeros = 64 - Long.numberOfLeadingZeros(j);
        if (numberOfLeadingZeros == 0) {
            numberOfLeadingZeros = 1;
        }
        int i2 = (numberOfLeadingZeros + 7) >> 3;
        byteOutput.writeByte(((i2 - 1) << 5) | i);
        while (i2 > 0) {
            byteOutput.writeByte((byte) ((int) j));
            j >>= 8;
            i2--;
        }
    }

    public static void writeRightZeroExtendedValue(ByteOutput byteOutput, int i, long j) {
        int numberOfTrailingZeros = 64 - Long.numberOfTrailingZeros(j);
        if (numberOfTrailingZeros == 0) {
            numberOfTrailingZeros = 1;
        }
        int i2 = (numberOfTrailingZeros + 7) >> 3;
        long j2 = j >> (64 - (i2 * 8));
        byteOutput.writeByte(((i2 - 1) << 5) | i);
        while (i2 > 0) {
            byteOutput.writeByte((byte) ((int) j2));
            j2 >>= 8;
            i2--;
        }
    }

    public static int readSignedInt(ByteInput byteInput, int i) {
        int i2 = 0;
        for (int i3 = i; i3 >= 0; i3--) {
            i2 = (i2 >>> 8) | ((byteInput.readByte() & 255) << 24);
        }
        return i2 >> ((3 - i) * 8);
    }

    public static int readUnsignedInt(ByteInput byteInput, int i, boolean z) {
        int i2 = 0;
        if (!z) {
            int i3 = 0;
            for (int i4 = i; i4 >= 0; i4--) {
                i3 = (i3 >>> 8) | ((byteInput.readByte() & 255) << 24);
            }
            return i3 >>> ((3 - i) * 8);
        }
        while (i >= 0) {
            i2 = (i2 >>> 8) | ((byteInput.readByte() & 255) << 24);
            i--;
        }
        return i2;
    }

    public static long readSignedLong(ByteInput byteInput, int i) {
        long j = 0;
        for (int i2 = i; i2 >= 0; i2--) {
            j = (j >>> 8) | ((((long) byteInput.readByte()) & 255) << 56);
        }
        return j >> ((7 - i) * 8);
    }

    public static long readUnsignedLong(ByteInput byteInput, int i, boolean z) {
        long j = 0;
        if (!z) {
            long j2 = 0;
            for (int i2 = i; i2 >= 0; i2--) {
                j2 = (j2 >>> 8) | ((((long) byteInput.readByte()) & 255) << 56);
            }
            return j2 >>> ((7 - i) * 8);
        }
        while (i >= 0) {
            j = (j >>> 8) | ((((long) byteInput.readByte()) & 255) << 56);
            i--;
        }
        return j;
    }
}
