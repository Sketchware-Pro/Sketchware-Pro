package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class StringIdItem extends IndexedItem implements Comparable {
    private StringDataItem data;
    private final CstString value;

    public StringIdItem(CstString cstString) {
        if (cstString == null) {
            throw new NullPointerException("value == null");
        }
        this.value = cstString;
        this.data = null;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof StringIdItem)) {
            return false;
        }
        return this.value.equals(((StringIdItem) obj).value);
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        return this.value.compareTo((Constant) ((StringIdItem) obj).value);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_STRING_ID_ITEM;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public int writeSize() {
        return 4;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        if (this.data == null) {
            MixedItemSection stringData = dexFile.getStringData();
            this.data = new StringDataItem(this.value);
            stringData.add(this.data);
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void writeTo(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        int absoluteOffset = this.data.getAbsoluteOffset();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(0, String.valueOf(indexString()) + ' ' + this.value.toQuoted(100));
            annotatedOutput.annotate(4, "  string_data_off: " + Hex.u4(absoluteOffset));
        }
        annotatedOutput.writeInt(absoluteOffset);
    }

    public CstString getValue() {
        return this.value;
    }

    public StringDataItem getData() {
        return this.data;
    }
}
