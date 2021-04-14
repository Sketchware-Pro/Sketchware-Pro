package mod.agus.jcoderz.dex;

import java.io.UTFDataFormatException;
import mod.agus.jcoderz.dex.util.ByteInput;
import mod.agus.jcoderz.dx.io.Opcodes;

public final class Mutf8 {
    private Mutf8() {
    }

    public static String decode(ByteInput byteInput, char[] cArr) throws UTFDataFormatException {
        int i = 0;
        while (true) {
            char readByte = (char) (byteInput.readByte() & 255);
            if (readByte == 0) {
                return new String(cArr, 0, i);
            }
            cArr[i] = readByte;
            if (readByte < 128) {
                i++;
            } else if ((readByte & 224) == 192) {
                int readByte2 = byteInput.readByte() & 255;
                if ((readByte2 & 192) != 128) {
                    throw new UTFDataFormatException("bad second byte");
                }
                cArr[i] = (char) (((readByte & 31) << 6) | (readByte2 & 63));
                i++;
            } else if ((readByte & 240) == 224) {
                int readByte3 = byteInput.readByte() & 255;
                int readByte4 = byteInput.readByte() & 255;
                if ((readByte3 & 192) == 128 && (readByte4 & 192) == 128) {
                    cArr[i] = (char) (((readByte & 15) << 12) | ((readByte3 & 63) << 6) | (readByte4 & 63));
                    i++;
                }
            } else {
                throw new UTFDataFormatException("bad byte");
            }
        }
    }

    private static long countBytes(String str, boolean z) throws UTFDataFormatException {
        int length = str.length();
        long j = 0;
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt != 0 && charAt <= 127) {
                j++;
            } else if (charAt <= 2047) {
                j += 2;
            } else {
                j += 3;
            }
            if (z && j > 65535) {
                throw new UTFDataFormatException("String more than 65535 UTF bytes long");
            }
        }
        return j;
    }

    public static void encode(byte[] bArr, int i, String str) {
        int i2;
        int length = str.length();
        int i3 = 0;
        while (i3 < length) {
            char charAt = str.charAt(i3);
            if (charAt != 0 && charAt <= 127) {
                i2 = i + 1;
                bArr[i] = (byte) charAt;
            } else if (charAt <= 2047) {
                int i4 = i + 1;
                bArr[i] = (byte) (((charAt >> 6) & 31) | 192);
                i2 = i4 + 1;
                bArr[i4] = (byte) ((charAt & '?') | 128);
            } else {
                int i5 = i + 1;
                bArr[i] = (byte) (((charAt >> '\f') & 15) | Opcodes.SHL_INT_LIT8);
                int i6 = i5 + 1;
                bArr[i5] = (byte) (((charAt >> 6) & 63) | 128);
                i2 = i6 + 1;
                bArr[i6] = (byte) ((charAt & '?') | 128);
            }
            i3++;
            i = i2;
        }
    }

    public static byte[] encode(String str) throws UTFDataFormatException {
        byte[] bArr = new byte[((int) countBytes(str, true))];
        encode(bArr, 0, str);
        return bArr;
    }
}
