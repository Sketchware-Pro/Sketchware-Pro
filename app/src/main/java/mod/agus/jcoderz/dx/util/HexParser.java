package mod.agus.jcoderz.dx.util;

public final class HexParser {
    private HexParser() {
    }

    public static byte[] parse(String str) {
        String substring;
        int i;
        int indexOf;
        int length = str.length();
        byte[] bArr = new byte[(length / 2)];
        int i2 = 0;
        int i3 = 0;
        while (i3 < length) {
            int indexOf2 = str.indexOf(10, i3);
            if (indexOf2 < 0) {
                indexOf2 = length;
            }
            int indexOf3 = str.indexOf(35, i3);
            if (indexOf3 < 0 || indexOf3 >= indexOf2) {
                substring = str.substring(i3, indexOf2);
            } else {
                substring = str.substring(i3, indexOf3);
            }
            int i4 = indexOf2 + 1;
            int indexOf4 = substring.indexOf(58);
            if (indexOf4 != -1 && ((indexOf = substring.indexOf(34)) == -1 || indexOf >= indexOf4)) {
                String trim = substring.substring(0, indexOf4).trim();
                substring = substring.substring(indexOf4 + 1);
                if (Integer.parseInt(trim, 16) != i2) {
                    throw new RuntimeException("bogus offset marker: " + trim);
                }
            }
            int length2 = substring.length();
            int i5 = 0;
            boolean z = false;
            int i6 = -1;
            while (i5 < length2) {
                char charAt = substring.charAt(i5);
                if (z) {
                    if (charAt == '\"') {
                        z = false;
                        i = i2;
                    } else {
                        bArr[i2] = (byte) charAt;
                        i = i2 + 1;
                    }
                } else if (charAt <= ' ') {
                    i = i2;
                } else if (charAt != '\"') {
                    int digit = Character.digit(charAt, 16);
                    if (digit == -1) {
                        throw new RuntimeException("bogus digit character: \"" + charAt + "\"");
                    } else if (i6 == -1) {
                        i6 = digit;
                        i = i2;
                    } else {
                        bArr[i2] = (byte) ((i6 << 4) | digit);
                        i = i2 + 1;
                        i6 = -1;
                    }
                } else if (i6 != -1) {
                    throw new RuntimeException("spare digit around offset " + Hex.u4(i2));
                } else {
                    z = true;
                    i = i2;
                }
                i5++;
                i2 = i;
            }
            if (i6 != -1) {
                throw new RuntimeException("spare digit around offset " + Hex.u4(i2));
            } else if (z) {
                throw new RuntimeException("unterminated quote around offset " + Hex.u4(i2));
            } else {
                i3 = i4;
            }
        }
        if (i2 >= bArr.length) {
            return bArr;
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, 0, bArr2, 0, i2);
        return bArr2;
    }
}
