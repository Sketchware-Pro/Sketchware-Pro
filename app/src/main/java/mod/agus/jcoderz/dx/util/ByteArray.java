package mod.agus.jcoderz.dx.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ByteArray {
    private final byte[] bytes;
    private final int size;
    private final int start;

    public ByteArray(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new NullPointerException("bytes == null");
        } else if (i < 0) {
            throw new IllegalArgumentException("start < 0");
        } else if (i2 < i) {
            throw new IllegalArgumentException("end < start");
        } else if (i2 > bArr.length) {
            throw new IllegalArgumentException("end > bytes.length");
        } else {
            this.bytes = bArr;
            this.start = i;
            this.size = i2 - i;
        }
    }

    public ByteArray(byte[] bArr) {
        this(bArr, 0, bArr.length);
    }

    public int size() {
        return this.size;
    }

    public ByteArray slice(int i, int i2) {
        checkOffsets(i, i2);
        return new ByteArray(this.bytes, this.start + i, this.start + i2);
    }

    public int underlyingOffset(int i, byte[] bArr) {
        if (bArr == this.bytes) {
            return this.start + i;
        }
        throw new IllegalArgumentException("wrong bytes");
    }

    public int getByte(int i) {
        checkOffsets(i, i + 1);
        return getByte0(i);
    }

    public int getShort(int i) {
        checkOffsets(i, i + 2);
        return (getByte0(i) << 8) | getUnsignedByte0(i + 1);
    }

    public int getInt(int i) {
        checkOffsets(i, i + 4);
        return (getByte0(i) << 24) | (getUnsignedByte0(i + 1) << 16) | (getUnsignedByte0(i + 2) << 8) | getUnsignedByte0(i + 3);
    }

    public long getLong(int i) {
        checkOffsets(i, i + 8);
        return (((long) ((((getByte0(i) << 24) | (getUnsignedByte0(i + 1) << 16)) | (getUnsignedByte0(i + 2) << 8)) | getUnsignedByte0(i + 3))) << 32) | (((long) ((getByte0(i + 4) << 24) | (getUnsignedByte0(i + 5) << 16) | (getUnsignedByte0(i + 6) << 8) | getUnsignedByte0(i + 7))) & 4294967295L);
    }

    public int getUnsignedByte(int i) {
        checkOffsets(i, i + 1);
        return getUnsignedByte0(i);
    }

    public int getUnsignedShort(int i) {
        checkOffsets(i, i + 2);
        return (getUnsignedByte0(i) << 8) | getUnsignedByte0(i + 1);
    }

    public void getBytes(byte[] bArr, int i) {
        if (bArr.length - i < this.size) {
            throw new IndexOutOfBoundsException("(out.length - offset) < size()");
        }
        System.arraycopy(this.bytes, this.start, bArr, i, this.size);
    }

    private void checkOffsets(int i, int i2) {
        if (i < 0 || i2 < i || i2 > this.size) {
            throw new IllegalArgumentException("bad range: " + i + ".." + i2 + "; actual size " + this.size);
        }
    }

    private int getByte0(int i) {
        return this.bytes[this.start + i];
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private int getUnsignedByte0(int i) {
        return this.bytes[this.start + i] & 255;
    }

    public MyDataInputStream makeDataInputStream() {
        return new MyDataInputStream(makeInputStream());
    }

    public MyInputStream makeInputStream() {
        return new MyInputStream();
    }

    public interface GetCursor {
        int getCursor();
    }

    public static class MyDataInputStream extends DataInputStream {
        private final MyInputStream wrapped;

        public MyDataInputStream(MyInputStream myInputStream) {
            super(myInputStream);
            this.wrapped = myInputStream;
        }
    }

    public class MyInputStream extends InputStream {
        private int cursor = 0;
        private int mark = 0;

        public MyInputStream() {
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            if (this.cursor >= ByteArray.this.size) {
                return -1;
            }
            int unsignedByte0 = ByteArray.this.getUnsignedByte0(this.cursor);
            this.cursor++;
            return unsignedByte0;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) {
            if (i + i2 > bArr.length) {
                i2 = bArr.length - i;
            }
            int i3 = ByteArray.this.size - this.cursor;
            if (i2 > i3) {
                i2 = i3;
            }
            System.arraycopy(ByteArray.this.bytes, this.cursor + ByteArray.this.start, bArr, i, i2);
            this.cursor += i2;
            return i2;
        }

        @Override // java.io.InputStream
        public int available() {
            return ByteArray.this.size - this.cursor;
        }

        public void mark(int i) {
            this.mark = this.cursor;
        }

        @Override // java.io.InputStream
        public void reset() {
            this.cursor = this.mark;
        }

        public boolean markSupported() {
            return true;
        }
    }
}
