package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dex.Leb128;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public final class StringDataItem extends OffsettedItem {
    private final CstString value;

    public StringDataItem(CstString cstString) {
        super(1, writeSize(cstString));
        this.value = cstString;
    }

    private static int writeSize(CstString cstString) {
        return Leb128.unsignedLeb128Size(cstString.getUtf16Size()) + cstString.getUtf8Size() + 1;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_STRING_DATA_ITEM;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    public void writeTo0(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        ByteArray bytes = this.value.getBytes();
        int utf16Size = this.value.getUtf16Size();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(Leb128.unsignedLeb128Size(utf16Size), "utf16_size: " + Hex.u4(utf16Size));
            annotatedOutput.annotate(bytes.size() + 1, this.value.toQuoted());
        }
        annotatedOutput.writeUleb128(utf16Size);
        annotatedOutput.write(bytes);
        annotatedOutput.writeByte(0);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    public String toHuman() {
        return this.value.toQuoted();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    protected int compareTo0(OffsettedItem offsettedItem) {
        return this.value.compareTo((Constant) ((StringDataItem) offsettedItem).value);
    }
}
