package mod.agus.jcoderz.dx.util;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import mod.agus.jcoderz.dex.Leb128;
import mod.agus.jcoderz.dex.util.ByteOutput;
import mod.agus.jcoderz.dex.util.ExceptionWithContext;

public final class ByteArrayAnnotatedOutput implements AnnotatedOutput, ByteOutput {
    private static final int DEFAULT_SIZE = 1000;
    private final boolean stretchy;
    private int annotationWidth;
    private ArrayList<Annotation> annotations;
    private int cursor;
    private byte[] data;
    private int hexCols;
    private boolean verbose;

    public ByteArrayAnnotatedOutput(byte[] bArr) {
        this(bArr, false);
    }

    public ByteArrayAnnotatedOutput() {
        this(1000);
    }

    public ByteArrayAnnotatedOutput(int i) {
        this(new byte[i], true);
    }

    private ByteArrayAnnotatedOutput(byte[] bArr, boolean z) {
        if (bArr == null) {
            throw new NullPointerException("data == null");
        }
        this.stretchy = z;
        this.data = bArr;
        this.cursor = 0;
        this.verbose = false;
        this.annotations = null;
        this.annotationWidth = 0;
        this.hexCols = 0;
    }

    private static void throwBounds() {
        throw new IndexOutOfBoundsException("attempt to write past the end");
    }

    public byte[] getArray() {
        return this.data;
    }

    public byte[] toByteArray() {
        byte[] bArr = new byte[this.cursor];
        System.arraycopy(this.data, 0, bArr, 0, this.cursor);
        return bArr;
    }

    @Override // mod.agus.jcoderz.dx.util.Output
    public int getCursor() {
        return this.cursor;
    }

    @Override // mod.agus.jcoderz.dx.util.Output
    public void assertCursor(int i) {
        if (this.cursor != i) {
            throw new ExceptionWithContext("expected cursor " + i + "; actual value: " + this.cursor);
        }
    }

    @Override // mod.agus.jcoderz.dx.util.Output, mod.agus.jcoderz.dex.util.ByteOutput
    public void writeByte(int i) {
        int i2 = this.cursor;
        int i3 = i2 + 1;
        if (this.stretchy) {
            ensureCapacity(i3);
        } else if (i3 > this.data.length) {
            throwBounds();
            return;
        }
        this.data[i2] = (byte) i;
        this.cursor = i3;
    }

    @Override // mod.agus.jcoderz.dx.util.Output
    public void writeShort(int i) {
        int i2 = this.cursor;
        int i3 = i2 + 2;
        if (this.stretchy) {
            ensureCapacity(i3);
        } else if (i3 > this.data.length) {
            throwBounds();
            return;
        }
        this.data[i2] = (byte) i;
        this.data[i2 + 1] = (byte) (i >> 8);
        this.cursor = i3;
    }

    @Override // mod.agus.jcoderz.dx.util.Output
    public void writeInt(int i) {
        int i2 = this.cursor;
        int i3 = i2 + 4;
        if (this.stretchy) {
            ensureCapacity(i3);
        } else if (i3 > this.data.length) {
            throwBounds();
            return;
        }
        this.data[i2] = (byte) i;
        this.data[i2 + 1] = (byte) (i >> 8);
        this.data[i2 + 2] = (byte) (i >> 16);
        this.data[i2 + 3] = (byte) (i >> 24);
        this.cursor = i3;
    }

    @Override // mod.agus.jcoderz.dx.util.Output
    public void writeLong(long j) {
        int i = this.cursor;
        int i2 = i + 8;
        if (this.stretchy) {
            ensureCapacity(i2);
        } else if (i2 > this.data.length) {
            throwBounds();
            return;
        }
        int i3 = (int) j;
        this.data[i] = (byte) i3;
        this.data[i + 1] = (byte) (i3 >> 8);
        this.data[i + 2] = (byte) (i3 >> 16);
        this.data[i + 3] = (byte) (i3 >> 24);
        int i4 = (int) (j >> 32);
        this.data[i + 4] = (byte) i4;
        this.data[i + 5] = (byte) (i4 >> 8);
        this.data[i + 6] = (byte) (i4 >> 16);
        this.data[i + 7] = (byte) (i4 >> 24);
        this.cursor = i2;
    }

    @Override // mod.agus.jcoderz.dx.util.Output
    public int writeUleb128(int i) {
        if (this.stretchy) {
            ensureCapacity(this.cursor + 5);
        }
        int i2 = this.cursor;
        Leb128.writeUnsignedLeb128(this, i);
        return this.cursor - i2;
    }

    @Override // mod.agus.jcoderz.dx.util.Output
    public int writeSleb128(int i) {
        if (this.stretchy) {
            ensureCapacity(this.cursor + 5);
        }
        int i2 = this.cursor;
        Leb128.writeSignedLeb128(this, i);
        return this.cursor - i2;
    }

    @Override // mod.agus.jcoderz.dx.util.Output
    public void write(ByteArray byteArray) {
        int size = byteArray.size();
        int i = this.cursor;
        int i2 = size + i;
        if (this.stretchy) {
            ensureCapacity(i2);
        } else if (i2 > this.data.length) {
            throwBounds();
            return;
        }
        byteArray.getBytes(this.data, i);
        this.cursor = i2;
    }

    @Override // mod.agus.jcoderz.dx.util.Output
    public void write(byte[] bArr, int i, int i2) {
        int i3 = this.cursor;
        int i4 = i3 + i2;
        int i5 = i + i2;
        if ((i | i2 | i4) < 0 || i5 > bArr.length) {
            throw new IndexOutOfBoundsException("bytes.length " + bArr.length + "; " + i + "..!" + i4);
        }
        if (this.stretchy) {
            ensureCapacity(i4);
        } else if (i4 > this.data.length) {
            throwBounds();
            return;
        }
        System.arraycopy(bArr, i, this.data, i3, i2);
        this.cursor = i4;
    }

    @Override // mod.agus.jcoderz.dx.util.Output
    public void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    @Override // mod.agus.jcoderz.dx.util.Output
    public void writeZeroes(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("count < 0");
        }
        int i2 = this.cursor + i;
        if (this.stretchy) {
            ensureCapacity(i2);
        } else if (i2 > this.data.length) {
            throwBounds();
            return;
        }
        this.cursor = i2;
    }

    @Override // mod.agus.jcoderz.dx.util.Output
    public void alignTo(int i) {
        int i2 = i - 1;
        if (i < 0 || (i2 & i) != 0) {
            throw new IllegalArgumentException("bogus alignment");
        }
        int i3 = (i2 ^ -1) & (this.cursor + i2);
        if (this.stretchy) {
            ensureCapacity(i3);
        } else if (i3 > this.data.length) {
            throwBounds();
            return;
        }
        this.cursor = i3;
    }

    @Override // mod.agus.jcoderz.dx.util.AnnotatedOutput
    public boolean annotates() {
        return this.annotations != null;
    }

    @Override // mod.agus.jcoderz.dx.util.AnnotatedOutput
    public boolean isVerbose() {
        return this.verbose;
    }

    @Override // mod.agus.jcoderz.dx.util.AnnotatedOutput
    public void annotate(String str) {
        if (this.annotations != null) {
            endAnnotation();
            this.annotations.add(new Annotation(this.cursor, str));
        }
    }

    @Override // mod.agus.jcoderz.dx.util.AnnotatedOutput
    public void annotate(int i, String str) {
        if (this.annotations != null) {
            endAnnotation();
            int size = this.annotations.size();
            int end = size == 0 ? 0 : this.annotations.get(size - 1).getEnd();
            if (end <= this.cursor) {
                end = this.cursor;
            }
            this.annotations.add(new Annotation(end, end + i, str));
        }
    }

    @Override // mod.agus.jcoderz.dx.util.AnnotatedOutput
    public void endAnnotation() {
        int size;
        if (this.annotations != null && (size = this.annotations.size()) != 0) {
            this.annotations.get(size - 1).setEndIfUnset(this.cursor);
        }
    }

    @Override // mod.agus.jcoderz.dx.util.AnnotatedOutput
    public int getAnnotationWidth() {
        return this.annotationWidth - (((this.hexCols * 2) + 8) + (this.hexCols / 2));
    }

    public void enableAnnotations(int i, boolean z) {
        int i2 = 6;
        if (this.annotations != null || this.cursor != 0) {
            throw new RuntimeException("cannot enable annotations");
        } else if (i < 40) {
            throw new IllegalArgumentException("annotationWidth < 40");
        } else {
            int i3 = (((i - 7) / 15) + 1) & -2;
            if (i3 >= 6) {
                if (i3 > 10) {
                    i2 = 10;
                } else {
                    i2 = i3;
                }
            }
            this.annotations = new ArrayList<>(1000);
            this.annotationWidth = i;
            this.hexCols = i2;
            this.verbose = z;
        }
    }

    public void finishAnnotating() {
        endAnnotation();
        if (this.annotations != null) {
            for (int size = this.annotations.size(); size > 0; size--) {
                Annotation annotation = this.annotations.get(size - 1);
                if (annotation.getStart() > this.cursor) {
                    this.annotations.remove(size - 1);
                } else if (annotation.getEnd() > this.cursor) {
                    annotation.setEnd(this.cursor);
                    return;
                } else {
                    return;
                }
            }
        }
    }

    public void writeAnnotationsTo(Writer writer) throws IOException {
        String text;
        int annotationWidth2 = getAnnotationWidth();
        TwoColumnOutput twoColumnOutput = new TwoColumnOutput(writer, (this.annotationWidth - annotationWidth2) - 1, annotationWidth2, "|");
        Writer left = twoColumnOutput.getLeft();
        Writer right = twoColumnOutput.getRight();
        int size = this.annotations.size();
        int i = 0;
        int i2 = 0;
        while (i2 < this.cursor && i < size) {
            Annotation annotation = this.annotations.get(i);
            int start = annotation.getStart();
            if (i2 < start) {
                text = "";
            } else {
                int end = annotation.getEnd();
                i++;
                text = annotation.getText();
                i2 = start;
                start = end;
            }
            left.write(Hex.dump(this.data, i2, start - i2, i2, this.hexCols, 6));
            right.write(text);
            twoColumnOutput.flush();
            i2 = start;
        }
        if (i2 < this.cursor) {
            left.write(Hex.dump(this.data, i2, this.cursor - i2, i2, this.hexCols, 6));
        }
        while (i < size) {
            right.write(this.annotations.get(i).getText());
            i++;
        }
        twoColumnOutput.flush();
    }

    private void ensureCapacity(int i) {
        if (this.data.length < i) {
            byte[] bArr = new byte[((i * 2) + 1000)];
            System.arraycopy(this.data, 0, bArr, 0, this.cursor);
            this.data = bArr;
        }
    }

    /* access modifiers changed from: private */
    public static class Annotation {
        private final int start;
        private final String text;
        private int end;

        public Annotation(int i, int i2, String str) {
            this.start = i;
            this.end = i2;
            this.text = str;
        }

        public Annotation(int i, String str) {
            this(i, Integer.MAX_VALUE, str);
        }

        public void setEndIfUnset(int i) {
            if (this.end == Integer.MAX_VALUE) {
                this.end = i;
            }
        }

        public int getStart() {
            return this.start;
        }

        public int getEnd() {
            return this.end;
        }

        public void setEnd(int i) {
            this.end = i;
        }

        public String getText() {
            return this.text;
        }
    }
}
