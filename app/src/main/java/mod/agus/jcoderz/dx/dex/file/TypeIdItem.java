package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class TypeIdItem extends IdItem {
    public TypeIdItem(CstType cstType) {
        super(cstType);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_TYPE_ID_ITEM;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public int writeSize() {
        return 4;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.IdItem, mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        dexFile.getStringIds().intern(getDefiningClass().getDescriptor());
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void writeTo(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        CstString descriptor = getDefiningClass().getDescriptor();
        int indexOf = dexFile.getStringIds().indexOf(descriptor);
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(0, String.valueOf(indexString()) + ' ' + descriptor.toHuman());
            annotatedOutput.annotate(4, "  descriptor_idx: " + Hex.u4(indexOf));
        }
        annotatedOutput.writeInt(indexOf);
    }
}
