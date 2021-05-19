package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public abstract class OffsettedItem extends Item implements Comparable<OffsettedItem> {
    private final int alignment;
    private Section addedTo;
    private int offset;
    private int writeSize;

    public OffsettedItem(int i, int i2) {
        Section.validateAlignment(i);
        if (i2 < -1) {
            throw new IllegalArgumentException("writeSize < -1");
        }
        this.alignment = i;
        this.writeSize = i2;
        this.addedTo = null;
        this.offset = -1;
    }

    public static int getAbsoluteOffsetOr0(OffsettedItem offsettedItem) {
        if (offsettedItem == null) {
            return 0;
        }
        return offsettedItem.getAbsoluteOffset();
    }

    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(OffsettedItem offsettedItem) {
        return compareTo(offsettedItem);
    }

    public abstract String toHuman();

    protected abstract void writeTo0(DexFile dexFile, AnnotatedOutput annotatedOutput);

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        OffsettedItem offsettedItem = (OffsettedItem) obj;
        if (itemType() != offsettedItem.itemType()) {
            return false;
        }
        return compareTo0(offsettedItem) == 0;
    }

    public final int compareToTwo(OffsettedItem offsettedItem) {
        if (this == offsettedItem) {
            return 0;
        }
        ItemType itemType = itemType();
        ItemType itemType2 = offsettedItem.itemType();
        if (itemType != itemType2) {
            return itemType.compareTo((ItemType) itemType2);
        }
        return compareTo0(offsettedItem);
    }

    public final void setWriteSize(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("writeSize < 0");
        } else if (this.writeSize >= 0) {
            throw new UnsupportedOperationException("writeSize already set");
        } else {
            this.writeSize = i;
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public final int writeSize() {
        if (this.writeSize >= 0) {
            return this.writeSize;
        }
        throw new UnsupportedOperationException("writeSize is unknown");
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public final void writeTo(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        annotatedOutput.alignTo(this.alignment);
        try {
            if (this.writeSize < 0) {
                throw new UnsupportedOperationException("writeSize is unknown");
            }
            annotatedOutput.assertCursor(getAbsoluteOffset());
            writeTo0(dexFile, annotatedOutput);
        } catch (RuntimeException e) {
            throw ExceptionWithContext.withContext(e, "...while writing " + this);
        }
    }

    public final int getRelativeOffset() {
        if (this.offset >= 0) {
            return this.offset;
        }
        throw new RuntimeException("offset not yet known");
    }

    public final int getAbsoluteOffset() {
        if (this.offset >= 0) {
            return this.addedTo.getAbsoluteOffset(this.offset);
        }
        throw new RuntimeException("offset not yet known");
    }

    public final int place(Section section, int i) {
        if (section == null) {
            throw new NullPointerException("addedTo == null");
        } else if (i < 0) {
            throw new IllegalArgumentException("offset < 0");
        } else if (this.addedTo != null) {
            throw new RuntimeException("already written");
        } else {
            int i2 = this.alignment - 1;
            int i3 = (i2 ^ -1) & (i + i2);
            this.addedTo = section;
            this.offset = i3;
            place0(section, i3);
            return i3;
        }
    }

    public final int getAlignment() {
        return this.alignment;
    }

    public final String offsetString() {
        return '[' + Integer.toHexString(getAbsoluteOffset()) + ']';
    }

    protected int compareTo0(OffsettedItem offsettedItem) {
        throw new UnsupportedOperationException("unsupported");
    }

    protected void place0(Section section, int i) {
    }
}
