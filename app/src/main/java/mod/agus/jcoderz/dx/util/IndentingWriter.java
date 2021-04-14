package mod.agus.jcoderz.dx.util;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;

public final class IndentingWriter extends FilterWriter {
    private boolean collectingIndent;
    private int column;
    private int indent;
    private final int maxIndent;
    private final String prefix;
    private final int width;

    public IndentingWriter(Writer writer, int i, String str) {
        super(writer);
        if (writer == null) {
            throw new NullPointerException("out == null");
        } else if (i < 0) {
            throw new IllegalArgumentException("width < 0");
        } else if (str == null) {
            throw new NullPointerException("prefix == null");
        } else {
            this.width = i != 0 ? i : Integer.MAX_VALUE;
            this.maxIndent = i >> 1;
            this.prefix = str.length() == 0 ? null : str;
            bol();
        }
    }

    public IndentingWriter(Writer writer, int i) {
        this(writer, i, "");
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public void write(int i) throws IOException {
        synchronized (this.lock) {
            if (this.collectingIndent) {
                if (i == 32) {
                    this.indent++;
                    if (this.indent >= this.maxIndent) {
                        this.indent = this.maxIndent;
                        this.collectingIndent = false;
                    }
                } else {
                    this.collectingIndent = false;
                }
            }
            if (this.column == this.width && i != 10) {
                this.out.write(10);
                this.column = 0;
            }
            if (this.column == 0) {
                if (this.prefix != null) {
                    this.out.write(this.prefix);
                }
                if (!this.collectingIndent) {
                    for (int i2 = 0; i2 < this.indent; i2++) {
                        this.out.write(32);
                    }
                    this.column = this.indent;
                }
            }
            this.out.write(i);
            if (i == 10) {
                bol();
            } else {
                this.column++;
            }
        }
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public void write(char[] cArr, int i, int i2) throws IOException {
        synchronized (this.lock) {
            while (i2 > 0) {
                write(cArr[i]);
                i++;
                i2--;
            }
        }
    }

    @Override // java.io.FilterWriter, java.io.Writer
    public void write(String str, int i, int i2) throws IOException {
        synchronized (this.lock) {
            while (i2 > 0) {
                write(str.charAt(i));
                i++;
                i2--;
            }
        }
    }

    private void bol() {
        boolean z;
        this.column = 0;
        if (this.maxIndent != 0) {
            z = true;
        } else {
            z = false;
        }
        this.collectingIndent = z;
        this.indent = 0;
    }
}
