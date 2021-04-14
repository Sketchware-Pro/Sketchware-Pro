package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.type.Prototype;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class ProtoIdItem extends IndexedItem {
    private TypeListItem parameterTypes;
    private final Prototype prototype;
    private final CstString shortForm;

    public ProtoIdItem(Prototype prototype2) {
        TypeListItem typeListItem;
        if (prototype2 == null) {
            throw new NullPointerException("prototype == null");
        }
        this.prototype = prototype2;
        this.shortForm = makeShortForm(prototype2);
        StdTypeList parameterTypes2 = prototype2.getParameterTypes();
        if (parameterTypes2.size() == 0) {
            typeListItem = null;
        } else {
            typeListItem = new TypeListItem(parameterTypes2);
        }
        this.parameterTypes = typeListItem;
    }

    private static CstString makeShortForm(Prototype prototype2) {
        StdTypeList parameterTypes2 = prototype2.getParameterTypes();
        int size = parameterTypes2.size();
        StringBuilder sb = new StringBuilder(size + 1);
        sb.append(shortFormCharFor(prototype2.getReturnType()));
        for (int i = 0; i < size; i++) {
            sb.append(shortFormCharFor(parameterTypes2.getType(i)));
        }
        return new CstString(sb.toString());
    }

    private static char shortFormCharFor(Type type) {
        char charAt = type.getDescriptor().charAt(0);
        if (charAt == '[') {
            return 'L';
        }
        return charAt;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_PROTO_ID_ITEM;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public int writeSize() {
        return 12;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        StringIdsSection stringIds = dexFile.getStringIds();
        TypeIdsSection typeIds = dexFile.getTypeIds();
        MixedItemSection typeLists = dexFile.getTypeLists();
        typeIds.intern(this.prototype.getReturnType());
        stringIds.intern(this.shortForm);
        if (this.parameterTypes != null) {
            this.parameterTypes = (TypeListItem) typeLists.intern(this.parameterTypes);
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void writeTo(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        int indexOf = dexFile.getStringIds().indexOf(this.shortForm);
        int indexOf2 = dexFile.getTypeIds().indexOf(this.prototype.getReturnType());
        int absoluteOffsetOr0 = OffsettedItem.getAbsoluteOffsetOr0(this.parameterTypes);
        if (annotatedOutput.annotates()) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.prototype.getReturnType().toHuman());
            sb.append(" proto(");
            StdTypeList parameterTypes2 = this.prototype.getParameterTypes();
            int size = parameterTypes2.size();
            for (int i = 0; i < size; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append(parameterTypes2.getType(i).toHuman());
            }
            sb.append(")");
            annotatedOutput.annotate(0, String.valueOf(indexString()) + ' ' + sb.toString());
            annotatedOutput.annotate(4, "  shorty_idx:      " + Hex.u4(indexOf) + " // " + this.shortForm.toQuoted());
            annotatedOutput.annotate(4, "  return_type_idx: " + Hex.u4(indexOf2) + " // " + this.prototype.getReturnType().toHuman());
            annotatedOutput.annotate(4, "  parameters_off:  " + Hex.u4(absoluteOffsetOr0));
        }
        annotatedOutput.writeInt(indexOf);
        annotatedOutput.writeInt(indexOf2);
        annotatedOutput.writeInt(absoluteOffsetOr0);
    }
}
