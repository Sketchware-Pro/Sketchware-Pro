package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.util.AnnotatedOutput;

public abstract class Item {
    public abstract void addContents(DexFile dexFile);

    public abstract ItemType itemType();

    public abstract int writeSize();

    public abstract void writeTo(DexFile dexFile, AnnotatedOutput annotatedOutput);

    public final String typeName() {
        return itemType().toHuman();
    }
}
