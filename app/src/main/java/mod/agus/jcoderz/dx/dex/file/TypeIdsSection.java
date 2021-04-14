package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;
import mod.agus.jcoderz.dex.DexIndexOverflowException;
import mod.agus.jcoderz.dx.command.dexer.Main;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class TypeIdsSection extends UniformItemSection {
    private final TreeMap<Type, TypeIdItem> typeIds = new TreeMap<>();

    public TypeIdsSection(DexFile dexFile) {
        super("type_ids", dexFile, 4);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    public Collection<? extends Item> items() {
        return this.typeIds.values();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.UniformItemSection
    public IndexedItem get(Constant constant) {
        if (constant == null) {
            throw new NullPointerException("cst == null");
        }
        throwIfNotPrepared();
        TypeIdItem typeIdItem = this.typeIds.get(((CstType) constant).getClassType());
        if (typeIdItem != null) {
            return typeIdItem;
        }
        throw new IllegalArgumentException("not found: " + constant);
    }

    public void writeHeaderPart(AnnotatedOutput annotatedOutput) {
        throwIfNotPrepared();
        int size = this.typeIds.size();
        int fileOffset = size == 0 ? 0 : getFileOffset();
        if (size > 65536) {
            throw new DexIndexOverflowException("Too many type references: " + size + "; max is " + AccessFlags.ACC_CONSTRUCTOR + ".\n" + Main.getTooManyIdsErrorMessage());
        }
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "type_ids_size:   " + Hex.u4(size));
            annotatedOutput.annotate(4, "type_ids_off:    " + Hex.u4(fileOffset));
        }
        annotatedOutput.writeInt(size);
        annotatedOutput.writeInt(fileOffset);
    }

    public synchronized TypeIdItem intern(Type type) {
        TypeIdItem typeIdItem;
        if (type == null) {
            throw new NullPointerException("type == null");
        }
        throwIfPrepared();
        typeIdItem = this.typeIds.get(type);
        if (typeIdItem == null) {
            typeIdItem = new TypeIdItem(new CstType(type));
            this.typeIds.put(type, typeIdItem);
        }
        return typeIdItem;
    }

    public synchronized TypeIdItem intern(CstType cstType) {
        TypeIdItem typeIdItem;
        if (cstType == null) {
            throw new NullPointerException("type == null");
        }
        throwIfPrepared();
        Type classType = cstType.getClassType();
        typeIdItem = this.typeIds.get(classType);
        if (typeIdItem == null) {
            typeIdItem = new TypeIdItem(cstType);
            this.typeIds.put(classType, typeIdItem);
        }
        return typeIdItem;
    }

    public int indexOf(Type type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }
        throwIfNotPrepared();
        TypeIdItem typeIdItem = this.typeIds.get(type);
        if (typeIdItem != null) {
            return typeIdItem.getIndex();
        }
        throw new IllegalArgumentException("not found: " + type);
    }

    public int indexOf(CstType cstType) {
        if (cstType != null) {
            return indexOf(cstType.getClassType());
        }
        throw new NullPointerException("type == null");
    }

    @Override // mod.agus.jcoderz.dx.dex.file.UniformItemSection
    protected void orderItems() {
        int i = 0;
        Iterator<? extends Item> it = items().iterator();
        while (it.hasNext()) {
            ((TypeIdItem) it.next()).setIndex(i);
            i++;
        }
    }
}
