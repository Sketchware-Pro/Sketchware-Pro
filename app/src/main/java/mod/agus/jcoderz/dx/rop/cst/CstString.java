package mod.agus.jcoderz.dx.rop.cst;

import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;

import mod.agus.jcoderz.dx.io.Opcodes;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public final class CstString extends TypedConstant {
    public static final CstString EMPTY_STRING = new CstString("");
    private final ByteArray bytes;
    private final String string;

    public CstString(String str) {
        if (str == null) {
            throw new NullPointerException("string == null");
        }
        this.string = str.intern();
        this.bytes = new ByteArray(stringToUtf8Bytes(str));
    }

    public CstString(ByteArray byteArray) {
        if (byteArray == null) {
            throw new NullPointerException("bytes == null");
        }
        this.bytes = byteArray;
        this.string = utf8BytesToString(byteArray).intern();
    }

    public static byte[] stringToUtf8Bytes(String str) {
        int length = str.length();
        byte[] bArr = new byte[(length * 3)];
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            char charAt = str.charAt(i2);
            if (charAt != 0 && charAt < 128) {
                bArr[i] = (byte) charAt;
                i++;
            } else if (charAt < 2048) {
                bArr[i] = (byte) (((charAt >> 6) & 31) | 192);
                bArr[i + 1] = (byte) ((charAt & '?') | 128);
                i += 2;
            } else {
                bArr[i] = (byte) (((charAt >> '\f') & 15) | Opcodes.SHL_INT_LIT8);
                bArr[i + 1] = (byte) (((charAt >> 6) & 63) | 128);
                bArr[i + 2] = (byte) ((charAt & '?') | 128);
                i += 3;
            }
        }
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        return bArr2;
    }

    public static String utf8BytesToString(ByteArray byteArray) {
        char c;
        int size = byteArray.size();
        char[] cArr = new char[size];
        int i = 0;
        int i2 = 0;
        while (size > 0) {
            int unsignedByte = byteArray.getUnsignedByte(i);
            switch (unsignedByte >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    size--;
                    if (unsignedByte != 0) {
                        c = (char) unsignedByte;
                        i++;
                        break;
                    } else {
                        return throwBadUtf8(unsignedByte, i);
                    }
                case 8:
                case 9:
                case 10:
                case 11:
                default:
                    return throwBadUtf8(unsignedByte, i);
                case 12:
                case 13:
                    size -= 2;
                    if (size < 0) {
                        return throwBadUtf8(unsignedByte, i);
                    }
                    int unsignedByte2 = byteArray.getUnsignedByte(i + 1);
                    if ((unsignedByte2 & 192) != 128) {
                        return throwBadUtf8(unsignedByte2, i + 1);
                    }
                    int i3 = ((unsignedByte & 31) << 6) | (unsignedByte2 & 63);
                    if (i3 == 0 || i3 >= 128) {
                        c = (char) i3;
                        i += 2;
                        break;
                    } else {
                        return throwBadUtf8(unsignedByte2, i + 1);
                    }
                case 14:
                    size -= 3;
                    if (size >= 0) {
                        int unsignedByte3 = byteArray.getUnsignedByte(i + 1);
                        if ((unsignedByte3 & 192) == 128) {
                            int unsignedByte4 = byteArray.getUnsignedByte(i + 2);
                            if ((unsignedByte3 & 192) == 128) {
                                int i4 = ((unsignedByte & 15) << 12) | ((unsignedByte3 & 63) << 6) | (unsignedByte4 & 63);
                                if (i4 >= 2048) {
                                    c = (char) i4;
                                    i += 3;
                                    break;
                                } else {
                                    return throwBadUtf8(unsignedByte4, i + 2);
                                }
                            } else {
                                return throwBadUtf8(unsignedByte4, i + 2);
                            }
                        } else {
                            return throwBadUtf8(unsignedByte3, i + 1);
                        }
                    } else {
                        return throwBadUtf8(unsignedByte, i);
                    }
            }
            cArr[i2] = c;
            i2++;
        }
        return new String(cArr, 0, i2);
    }

    private static String throwBadUtf8(int i, int i2) {
        throw new IllegalArgumentException("bad utf-8 byte " + Hex.u1(i) + " at offset " + Hex.u4(i2));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CstString)) {
            return false;
        }
        return this.string.equals(((CstString) obj).string);
    }

    public int hashCode() {
        return this.string.hashCode();
    }

    /* access modifiers changed from: protected */
    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public int compareTo0(Constant constant) {
        return this.string.compareTo(((CstString) constant).string);
    }

    public String toString() {
        return "string{\"" + toHuman() + "\"}";
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public String typeName() {
        return "utf8";
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public boolean isCategory2() {
        return false;
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        int length = this.string.length();
        StringBuilder sb = new StringBuilder((length * 3) / 2);
        for (int i = 0; i < length; i++) {
            char charAt = this.string.charAt(i);
            if (charAt >= ' ' && charAt < 127) {
                if (charAt == '\'' || charAt == '\"' || charAt == '\\') {
                    sb.append('\\');
                }
                sb.append(charAt);
            } else if (charAt <= 127) {
                switch (charAt) {
                    case '\t':
                        sb.append("\\t");
                        continue;
                    case '\n':
                        sb.append("\\n");
                        continue;
                    case 11:
                    case '\f':
                    default:
                        char charAt2 = i < length - 1 ? this.string.charAt(i + 1) : 0;
                        boolean z = charAt2 >= '0' && charAt2 <= '7';
                        sb.append('\\');
                        for (int i2 = 6; i2 >= 0; i2 -= 3) {
                            char c = (char) (((charAt >> i2) & 7) + 48);
                            if (c != '0' || z) {
                                sb.append(c);
                                z = true;
                            }
                        }
                        if (!z) {
                            sb.append(ExternalAnnotationProvider.NULLABLE);
                            break;
                        } else {
                            continue;
                        }
                    case '\r':
                        sb.append("\\r");
                        continue;
                }
            } else {
                sb.append("\\u");
                sb.append(Character.forDigit(charAt >> '\f', 16));
                sb.append(Character.forDigit((charAt >> '\b') & 15, 16));
                sb.append(Character.forDigit((charAt >> 4) & 15, 16));
                sb.append(Character.forDigit(charAt & 15, 16));
            }
        }
        return sb.toString();
    }

    public String toQuoted() {
        return '\"' + toHuman() + '\"';
    }

    public String toQuoted(int i) {
        String str;
        String human = toHuman();
        if (human.length() <= i - 2) {
            str = "";
        } else {
            human = human.substring(0, i - 5);
            str = "...";
        }
        return '\"' + human + str + '\"';
    }

    public String getString() {
        return this.string;
    }

    public ByteArray getBytes() {
        return this.bytes;
    }

    public int getUtf8Size() {
        return this.bytes.size();
    }

    public int getUtf16Size() {
        return this.string.length();
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public Type getType() {
        return Type.STRING;
    }
}
