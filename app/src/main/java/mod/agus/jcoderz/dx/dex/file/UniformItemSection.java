package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public abstract class UniformItemSection extends Section {
    public abstract IndexedItem get(Constant constant);

    protected abstract void orderItems();

    public UniformItemSection(String str, DexFile dexFile, int i) {
        super(str, dexFile, i);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    public final int writeSize() {
        Collection<? extends Item> items = items();
        int size = items.size();
        if (size == 0) {
            return 0;
        }
        return ((Item) items.iterator().next()).writeSize() * size;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    protected final void prepare0() {
        DexFile file = getFile();
        orderItems();
        for (Item item : items()) {
            item.addContents(file);
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    protected final void writeTo0(AnnotatedOutput annotatedOutput) {
        DexFile file = getFile();
        int alignment = getAlignment();
        for (Item item : items()) {
            item.writeTo(file, annotatedOutput);
            annotatedOutput.alignTo(alignment);
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    public final int getAbsoluteItemOffset(Item item) {
        IndexedItem indexedItem = (IndexedItem) item;
        return getAbsoluteOffset(indexedItem.getIndex() * indexedItem.writeSize());
    }
}
