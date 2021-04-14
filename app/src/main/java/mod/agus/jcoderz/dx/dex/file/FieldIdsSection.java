package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;
import java.util.TreeMap;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class FieldIdsSection extends MemberIdsSection {
    private final TreeMap<CstFieldRef, FieldIdItem> fieldIds = new TreeMap<>();

    public FieldIdsSection(DexFile dexFile) {
        super("field_ids", dexFile);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    public Collection<? extends Item> items() {
        return this.fieldIds.values();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.UniformItemSection
    public IndexedItem get(Constant constant) {
        if (constant == null) {
            throw new NullPointerException("cst == null");
        }
        throwIfNotPrepared();
        FieldIdItem fieldIdItem = this.fieldIds.get((CstFieldRef) constant);
        if (fieldIdItem != null) {
            return fieldIdItem;
        }
        throw new IllegalArgumentException("not found");
    }

    public void writeHeaderPart(AnnotatedOutput annotatedOutput) {
        throwIfNotPrepared();
        int size = this.fieldIds.size();
        int fileOffset = size == 0 ? 0 : getFileOffset();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "field_ids_size:  " + Hex.u4(size));
            annotatedOutput.annotate(4, "field_ids_off:   " + Hex.u4(fileOffset));
        }
        annotatedOutput.writeInt(size);
        annotatedOutput.writeInt(fileOffset);
    }

    public synchronized FieldIdItem intern(CstFieldRef cstFieldRef) {
        FieldIdItem fieldIdItem;
        if (cstFieldRef == null) {
            throw new NullPointerException("field == null");
        }
        throwIfPrepared();
        fieldIdItem = this.fieldIds.get(cstFieldRef);
        if (fieldIdItem == null) {
            fieldIdItem = new FieldIdItem(cstFieldRef);
            this.fieldIds.put(cstFieldRef, fieldIdItem);
        }
        return fieldIdItem;
    }

    public int indexOf(CstFieldRef cstFieldRef) {
        if (cstFieldRef == null) {
            throw new NullPointerException("ref == null");
        }
        throwIfNotPrepared();
        FieldIdItem fieldIdItem = this.fieldIds.get(cstFieldRef);
        if (fieldIdItem != null) {
            return fieldIdItem.getIndex();
        }
        throw new IllegalArgumentException("not found");
    }
}
