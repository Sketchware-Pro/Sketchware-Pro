package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;

import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public abstract class Section {
    private final int alignment;
    private final DexFile file;
    private final String name;
    private int fileOffset;
    private boolean prepared;

    public Section(String str, DexFile dexFile, int i) {
        if (dexFile == null) {
            throw new NullPointerException("file == null");
        }
        validateAlignment(i);
        this.name = str;
        this.file = dexFile;
        this.alignment = i;
        this.fileOffset = -1;
        this.prepared = false;
    }

    public static void validateAlignment(int i) {
        if (i <= 0 || ((i - 1) & i) != 0) {
            throw new IllegalArgumentException("invalid alignment");
        }
    }

    public abstract int getAbsoluteItemOffset(Item item);

    public abstract Collection<? extends Item> items();

    protected abstract void prepare0();

    public abstract int writeSize();

    protected abstract void writeTo0(AnnotatedOutput annotatedOutput);

    public final DexFile getFile() {
        return this.file;
    }

    public final int getAlignment() {
        return this.alignment;
    }

    public final int getFileOffset() {
        if (this.fileOffset >= 0) {
            return this.fileOffset;
        }
        throw new RuntimeException("fileOffset not set");
    }

    public final int setFileOffset(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("fileOffset < 0");
        } else if (this.fileOffset >= 0) {
            throw new RuntimeException("fileOffset already set");
        } else {
            int i2 = this.alignment - 1;
            int i3 = (i2 ^ -1) & (i + i2);
            this.fileOffset = i3;
            return i3;
        }
    }

    public final void writeTo(AnnotatedOutput annotatedOutput) {
        throwIfNotPrepared();
        align(annotatedOutput);
        int cursor = annotatedOutput.getCursor();
        if (this.fileOffset < 0) {
            this.fileOffset = cursor;
        } else if (this.fileOffset != cursor) {
            throw new RuntimeException("alignment mismatch: for " + this + ", at " + cursor + ", but expected " + this.fileOffset);
        }
        if (annotatedOutput.annotates()) {
            if (this.name != null) {
                annotatedOutput.annotate(0, "\n" + this.name + ":");
            } else if (cursor != 0) {
                annotatedOutput.annotate(0, "\n");
            }
        }
        writeTo0(annotatedOutput);
    }

    public final int getAbsoluteOffset(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("relative < 0");
        } else if (this.fileOffset >= 0) {
            return this.fileOffset + i;
        } else {
            throw new RuntimeException("fileOffset not yet set");
        }
    }

    public final void prepare() {
        throwIfPrepared();
        prepare0();
        this.prepared = true;
    }

    protected final void throwIfNotPrepared() {
        if (!this.prepared) {
            throw new RuntimeException("not prepared");
        }
    }

    protected final void throwIfPrepared() {
        if (this.prepared) {
            throw new RuntimeException("already prepared");
        }
    }

    protected final void align(AnnotatedOutput annotatedOutput) {
        annotatedOutput.alignTo(this.alignment);
    }

    protected final String getName() {
        return this.name;
    }
}
