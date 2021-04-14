package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;
import java.util.TreeMap;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class MethodIdsSection extends MemberIdsSection {
    private final TreeMap<CstBaseMethodRef, MethodIdItem> methodIds = new TreeMap<>();

    public MethodIdsSection(DexFile dexFile) {
        super("method_ids", dexFile);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    public Collection<? extends Item> items() {
        return this.methodIds.values();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.UniformItemSection
    public IndexedItem get(Constant constant) {
        if (constant == null) {
            throw new NullPointerException("cst == null");
        }
        throwIfNotPrepared();
        MethodIdItem methodIdItem = this.methodIds.get((CstBaseMethodRef) constant);
        if (methodIdItem != null) {
            return methodIdItem;
        }
        throw new IllegalArgumentException("not found");
    }

    public void writeHeaderPart(AnnotatedOutput annotatedOutput) {
        throwIfNotPrepared();
        int size = this.methodIds.size();
        int fileOffset = size == 0 ? 0 : getFileOffset();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "method_ids_size: " + Hex.u4(size));
            annotatedOutput.annotate(4, "method_ids_off:  " + Hex.u4(fileOffset));
        }
        annotatedOutput.writeInt(size);
        annotatedOutput.writeInt(fileOffset);
    }

    public synchronized MethodIdItem intern(CstBaseMethodRef cstBaseMethodRef) {
        MethodIdItem methodIdItem;
        if (cstBaseMethodRef == null) {
            throw new NullPointerException("method == null");
        }
        throwIfPrepared();
        methodIdItem = this.methodIds.get(cstBaseMethodRef);
        if (methodIdItem == null) {
            methodIdItem = new MethodIdItem(cstBaseMethodRef);
            this.methodIds.put(cstBaseMethodRef, methodIdItem);
        }
        return methodIdItem;
    }

    public int indexOf(CstBaseMethodRef cstBaseMethodRef) {
        if (cstBaseMethodRef == null) {
            throw new NullPointerException("ref == null");
        }
        throwIfNotPrepared();
        MethodIdItem methodIdItem = this.methodIds.get(cstBaseMethodRef);
        if (methodIdItem != null) {
            return methodIdItem.getIndex();
        }
        throw new IllegalArgumentException("not found");
    }
}
