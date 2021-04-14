package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;

public final class FieldIdItem extends MemberIdItem {
    public FieldIdItem(CstFieldRef cstFieldRef) {
        super(cstFieldRef);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_FIELD_ID_ITEM;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.MemberIdItem, mod.agus.jcoderz.dx.dex.file.IdItem, mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        super.addContents(dexFile);
        dexFile.getTypeIds().intern(getFieldRef().getType());
    }

    public CstFieldRef getFieldRef() {
        return (CstFieldRef) getRef();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.MemberIdItem
    protected int getTypoidIdx(DexFile dexFile) {
        return dexFile.getTypeIds().indexOf(getFieldRef().getType());
    }

    @Override // mod.agus.jcoderz.dx.dex.file.MemberIdItem
    protected String getTypoidName() {
        return "type_idx";
    }
}
