package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;
import java.util.TreeMap;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class StringIdsSection extends UniformItemSection {
    private final TreeMap<CstString, StringIdItem> strings = new TreeMap<>();

    public StringIdsSection(DexFile dexFile) {
        super("string_ids", dexFile, 4);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    public Collection<? extends Item> items() {
        return this.strings.values();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.UniformItemSection
    public IndexedItem get(Constant constant) {
        if (constant == null) {
            throw new NullPointerException("cst == null");
        }
        throwIfNotPrepared();
        StringIdItem stringIdItem = this.strings.get((CstString) constant);
        if (stringIdItem != null) {
            return stringIdItem;
        }
        throw new IllegalArgumentException("not found");
    }

    public void writeHeaderPart(AnnotatedOutput annotatedOutput) {
        throwIfNotPrepared();
        int size = this.strings.size();
        int fileOffset = size == 0 ? 0 : getFileOffset();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "string_ids_size: " + Hex.u4(size));
            annotatedOutput.annotate(4, "string_ids_off:  " + Hex.u4(fileOffset));
        }
        annotatedOutput.writeInt(size);
        annotatedOutput.writeInt(fileOffset);
    }

    public StringIdItem intern(String str) {
        return intern(new StringIdItem(new CstString(str)));
    }

    public StringIdItem intern(CstString cstString) {
        return intern(new StringIdItem(cstString));
    }

    public synchronized StringIdItem intern(StringIdItem stringIdItem) {
        StringIdItem stringIdItem2;
        if (stringIdItem == null) {
            throw new NullPointerException("string == null");
        }
        throwIfPrepared();
        CstString value = stringIdItem.getValue();
        stringIdItem2 = this.strings.get(value);
        if (stringIdItem2 == null) {
            this.strings.put(value, stringIdItem);
            stringIdItem2 = stringIdItem;
        }
        return stringIdItem2;
    }

    public synchronized void intern(CstNat cstNat) {
        intern(cstNat.getName());
        intern(cstNat.getDescriptor());
    }

    public int indexOf(CstString cstString) {
        if (cstString == null) {
            throw new NullPointerException("string == null");
        }
        throwIfNotPrepared();
        StringIdItem stringIdItem = this.strings.get(cstString);
        if (stringIdItem != null) {
            return stringIdItem.getIndex();
        }
        throw new IllegalArgumentException("not found");
    }

    @Override // mod.agus.jcoderz.dx.dex.file.UniformItemSection
    protected void orderItems() {
        int i = 0;
        for (StringIdItem stringIdItem : this.strings.values()) {
            stringIdItem.setIndex(i);
            i++;
        }
    }
}
