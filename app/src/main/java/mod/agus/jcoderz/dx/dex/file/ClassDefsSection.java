package mod.agus.jcoderz.dx.dex.file;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class ClassDefsSection extends UniformItemSection {
    private final TreeMap<Type, ClassDefItem> classDefs = new TreeMap<>();
    private ArrayList<ClassDefItem> orderedDefs = null;

    public ClassDefsSection(DexFile dexFile) {
        super("class_defs", dexFile, 4);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    public Collection<? extends Item> items() {
        if (this.orderedDefs != null) {
            return this.orderedDefs;
        }
        return this.classDefs.values();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.UniformItemSection
    public IndexedItem get(Constant constant) {
        if (constant == null) {
            throw new NullPointerException("cst == null");
        }
        throwIfNotPrepared();
        ClassDefItem classDefItem = this.classDefs.get(((CstType) constant).getClassType());
        if (classDefItem != null) {
            return classDefItem;
        }
        throw new IllegalArgumentException("not found");
    }

    public void writeHeaderPart(AnnotatedOutput annotatedOutput) {
        throwIfNotPrepared();
        int size = this.classDefs.size();
        int fileOffset = size == 0 ? 0 : getFileOffset();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "class_defs_size: " + Hex.u4(size));
            annotatedOutput.annotate(4, "class_defs_off:  " + Hex.u4(fileOffset));
        }
        annotatedOutput.writeInt(size);
        annotatedOutput.writeInt(fileOffset);
    }

    public void add(ClassDefItem classDefItem) {
        try {
            Type classType = classDefItem.getThisClass().getClassType();
            throwIfPrepared();
            if (this.classDefs.get(classType) != null) {
                throw new IllegalArgumentException("already added: " + classType);
            }
            this.classDefs.put(classType, classDefItem);
        } catch (NullPointerException e) {
            throw new NullPointerException("clazz == null");
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.UniformItemSection
    protected void orderItems() {
        int size = this.classDefs.size();
        int i = 0;
        this.orderedDefs = new ArrayList<>(size);
        for (Type type : this.classDefs.keySet()) {
            i = orderItems0(type, i, size - i);
        }
    }

    private int orderItems0(Type type, int i, int i2) {
        ClassDefItem classDefItem = this.classDefs.get(type);
        if (classDefItem == null || classDefItem.hasIndex()) {
            return i;
        }
        if (i2 < 0) {
            throw new RuntimeException("class circularity with " + type);
        }
        int i3 = i2 - 1;
        CstType superclass = classDefItem.getSuperclass();
        if (superclass != null) {
            i = orderItems0(superclass.getClassType(), i, i3);
        }
        TypeList interfaces = classDefItem.getInterfaces();
        int size = interfaces.size();
        for (int i4 = 0; i4 < size; i4++) {
            i = orderItems0(interfaces.getType(i4), i, i3);
        }
        classDefItem.setIndex(i);
        this.orderedDefs.add(classDefItem);
        return i + 1;
    }
}
