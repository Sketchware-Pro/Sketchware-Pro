package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef;

public final class MethodIdItem extends MemberIdItem {
    public MethodIdItem(CstBaseMethodRef cstBaseMethodRef) {
        super(cstBaseMethodRef);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_METHOD_ID_ITEM;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.MemberIdItem, mod.agus.jcoderz.dx.dex.file.IdItem, mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        super.addContents(dexFile);
        dexFile.getProtoIds().intern(getMethodRef().getPrototype());
    }

    public CstBaseMethodRef getMethodRef() {
        return (CstBaseMethodRef) getRef();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.MemberIdItem
    protected int getTypoidIdx(DexFile dexFile) {
        return dexFile.getProtoIds().indexOf(getMethodRef().getPrototype());
    }

    @Override // mod.agus.jcoderz.dx.dex.file.MemberIdItem
    protected String getTypoidName() {
        return "proto_idx";
    }
}
