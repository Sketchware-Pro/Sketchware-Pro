package mod.agus.jcoderz.dex;

import mod.agus.jcoderz.dex.util.ByteInput;
import mod.agus.jcoderz.dx.io.Opcodes;

public final class EncodedValueReader {
    public static final int ENCODED_ANNOTATION = 29;
    public static final int ENCODED_ARRAY = 28;
    public static final int ENCODED_BOOLEAN = 31;
    public static final int ENCODED_BYTE = 0;
    public static final int ENCODED_CHAR = 3;
    public static final int ENCODED_DOUBLE = 17;
    public static final int ENCODED_ENUM = 27;
    public static final int ENCODED_FIELD = 25;
    public static final int ENCODED_FLOAT = 16;
    public static final int ENCODED_INT = 4;
    public static final int ENCODED_LONG = 6;
    public static final int ENCODED_METHOD = 26;
    public static final int ENCODED_NULL = 30;
    public static final int ENCODED_SHORT = 2;
    public static final int ENCODED_STRING = 23;
    public static final int ENCODED_TYPE = 24;
    private static final int MUST_READ = -1;
    private int annotationType;
    private int arg;
    protected final ByteInput in;
    private int type;

    public EncodedValueReader(ByteInput byteInput) {
        this.type = -1;
        this.in = byteInput;
    }

    public EncodedValueReader(EncodedValue encodedValue) {
        this(encodedValue.asByteInput());
    }

    public EncodedValueReader(ByteInput byteInput, int i) {
        this.type = -1;
        this.in = byteInput;
        this.type = i;
    }

    public EncodedValueReader(EncodedValue encodedValue, int i) {
        this(encodedValue.asByteInput(), i);
    }

    public int peek() {
        if (this.type == -1) {
            int readByte = this.in.readByte() & 255;
            this.type = readByte & 31;
            this.arg = (readByte & Opcodes.SHL_INT_LIT8) >> 5;
        }
        return this.type;
    }

    public int readArray() {
        checkType(28);
        this.type = -1;
        return Leb128.readUnsignedLeb128(this.in);
    }

    public int readAnnotation() {
        checkType(29);
        this.type = -1;
        this.annotationType = Leb128.readUnsignedLeb128(this.in);
        return Leb128.readUnsignedLeb128(this.in);
    }

    public int getAnnotationType() {
        return this.annotationType;
    }

    public int readAnnotationName() {
        return Leb128.readUnsignedLeb128(this.in);
    }

    public byte readByte() {
        checkType(0);
        this.type = -1;
        return (byte) EncodedValueCodec.readSignedInt(this.in, this.arg);
    }

    public short readShort() {
        checkType(2);
        this.type = -1;
        return (short) EncodedValueCodec.readSignedInt(this.in, this.arg);
    }

    public char readChar() {
        checkType(3);
        this.type = -1;
        return (char) EncodedValueCodec.readUnsignedInt(this.in, this.arg, false);
    }

    public int readInt() {
        checkType(4);
        this.type = -1;
        return EncodedValueCodec.readSignedInt(this.in, this.arg);
    }

    public long readLong() {
        checkType(6);
        this.type = -1;
        return EncodedValueCodec.readSignedLong(this.in, this.arg);
    }

    public float readFloat() {
        checkType(16);
        this.type = -1;
        return Float.intBitsToFloat(EncodedValueCodec.readUnsignedInt(this.in, this.arg, true));
    }

    public double readDouble() {
        checkType(17);
        this.type = -1;
        return Double.longBitsToDouble(EncodedValueCodec.readUnsignedLong(this.in, this.arg, true));
    }

    public int readString() {
        checkType(23);
        this.type = -1;
        return EncodedValueCodec.readUnsignedInt(this.in, this.arg, false);
    }

    public int readType() {
        checkType(24);
        this.type = -1;
        return EncodedValueCodec.readUnsignedInt(this.in, this.arg, false);
    }

    public int readField() {
        checkType(25);
        this.type = -1;
        return EncodedValueCodec.readUnsignedInt(this.in, this.arg, false);
    }

    public int readEnum() {
        checkType(27);
        this.type = -1;
        return EncodedValueCodec.readUnsignedInt(this.in, this.arg, false);
    }

    public int readMethod() {
        checkType(26);
        this.type = -1;
        return EncodedValueCodec.readUnsignedInt(this.in, this.arg, false);
    }

    public void readNull() {
        checkType(30);
        this.type = -1;
    }

    public boolean readBoolean() {
        checkType(31);
        this.type = -1;
        return this.arg != 0;
    }

    public void skipValue() {
        int i = 0;
        switch (peek()) {
            case 0:
                readByte();
                return;
            case 1:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            default:
                throw new DexException("Unexpected type: " + Integer.toHexString(this.type));
            case 2:
                readShort();
                return;
            case 3:
                readChar();
                return;
            case 4:
                readInt();
                return;
            case 6:
                readLong();
                return;
            case 16:
                readFloat();
                return;
            case 17:
                readDouble();
                return;
            case 23:
                readString();
                return;
            case 24:
                readType();
                return;
            case 25:
                readField();
                return;
            case 26:
                readMethod();
                return;
            case 27:
                readEnum();
                return;
            case 28:
                int readArray = readArray();
                while (i < readArray) {
                    skipValue();
                    i++;
                }
                return;
            case 29:
                int readAnnotation = readAnnotation();
                while (i < readAnnotation) {
                    readAnnotationName();
                    skipValue();
                    i++;
                }
                return;
            case 30:
                readNull();
                return;
            case 31:
                readBoolean();
                return;
        }
    }

    private void checkType(int i) {
        if (peek() != i) {
            throw new IllegalStateException(String.format("Expected %x but was %x", Integer.valueOf(i), Integer.valueOf(peek())));
        }
    }
}
