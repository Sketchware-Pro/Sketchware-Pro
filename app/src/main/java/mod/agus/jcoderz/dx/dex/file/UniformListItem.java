package mod.agus.jcoderz.dx.dex.file;

import java.util.List;
import mod.agus.jcoderz.dx.dex.file.OffsettedItem;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class UniformListItem<T extends OffsettedItem> extends OffsettedItem {
    private static final int HEADER_SIZE = 4;
    private final ItemType itemType;
    private final List<T> items;

    public UniformListItem(ItemType itemType2, List<T> list) {
        super(getAlignment(list), writeSize(list));
        if (itemType2 == null) {
            throw new NullPointerException("itemType == null");
        }
        this.items = list;
        this.itemType = itemType2;
    }

    private static int getAlignment(List<? extends OffsettedItem> list) {
        try {
            return Math.max(4, ((OffsettedItem) list.get(0)).getAlignment());
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("items.size() == 0");
        } catch (NullPointerException e2) {
            throw new NullPointerException("items == null");
        }
    }

    private static int writeSize(List<? extends OffsettedItem> list) {
        return (((OffsettedItem) list.get(0)).writeSize() * list.size()) + getAlignment(list);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return this.itemType;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append(getClass().getName());
        stringBuffer.append(this.items);
        return stringBuffer.toString();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        for (T t : this.items) {
            t.addContents(dexFile);
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    public final String toHuman() {
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append("{");
        boolean z = true;
        for (T t : this.items) {
            if (z) {
                z = false;
            } else {
                stringBuffer.append(", ");
            }
            stringBuffer.append(t.toHuman());
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public final List<T> getItems() {
        return this.items;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    protected void place0(Section section, int i) {
        boolean z = true;
        int headerSize = i + headerSize();
        int i2 = -1;
        int i3 = -1;
        for (T t : this.items) {
            int writeSize = t.writeSize();
            if (z) {
                i2 = t.getAlignment();
                z = false;
                i3 = writeSize;
            } else if (writeSize != i3) {
                throw new UnsupportedOperationException("item size mismatch");
            } else if (t.getAlignment() != i2) {
                throw new UnsupportedOperationException("item alignment mismatch");
            }
            headerSize = t.place(section, headerSize) + writeSize;
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    protected void writeTo0(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        int size = this.items.size();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(0, String.valueOf(offsetString()) + " " + typeName());
            annotatedOutput.annotate(4, "  size: " + Hex.u4(size));
        }
        annotatedOutput.writeInt(size);
        for (T t : this.items) {
            t.writeTo(dexFile, annotatedOutput);
        }
    }

    private int headerSize() {
        return getAlignment();
    }
}
