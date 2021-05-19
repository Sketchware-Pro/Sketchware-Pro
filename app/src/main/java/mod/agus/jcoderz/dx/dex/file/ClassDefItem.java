package mod.agus.jcoderz.dx.dex.file;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.annotation.Annotations;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationsList;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstArray;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.Writers;

public final class ClassDefItem extends IndexedItem {
    private final int accessFlags;
    private final ClassDataItem classData;
    private final CstString sourceFile;
    private final CstType superclass;
    private final CstType thisClass;
    private AnnotationsDirectoryItem annotationsDirectory;
    private TypeListItem interfaces;
    private EncodedArrayItem staticValuesItem;

    public ClassDefItem(CstType cstType, int i, CstType cstType2, TypeList typeList, CstString cstString) {
        if (cstType == null) {
            throw new NullPointerException("thisClass == null");
        } else if (typeList == null) {
            throw new NullPointerException("interfaces == null");
        } else {
            this.thisClass = cstType;
            this.accessFlags = i;
            this.superclass = cstType2;
            this.interfaces = typeList.size() == 0 ? null : new TypeListItem(typeList);
            this.sourceFile = cstString;
            this.classData = new ClassDataItem(cstType);
            this.staticValuesItem = null;
            this.annotationsDirectory = new AnnotationsDirectoryItem();
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_CLASS_DEF_ITEM;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public int writeSize() {
        return 32;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        TypeIdsSection typeIds = dexFile.getTypeIds();
        MixedItemSection byteData = dexFile.getByteData();
        MixedItemSection wordData = dexFile.getWordData();
        MixedItemSection typeLists = dexFile.getTypeLists();
        StringIdsSection stringIds = dexFile.getStringIds();
        typeIds.intern(this.thisClass);
        if (!this.classData.isEmpty()) {
            dexFile.getClassData().add(this.classData);
            CstArray staticValuesConstant = this.classData.getStaticValuesConstant();
            if (staticValuesConstant != null) {
                this.staticValuesItem = (EncodedArrayItem) byteData.intern(new EncodedArrayItem(staticValuesConstant));
            }
        }
        if (this.superclass != null) {
            typeIds.intern(this.superclass);
        }
        if (this.interfaces != null) {
            this.interfaces = (TypeListItem) typeLists.intern(this.interfaces);
        }
        if (this.sourceFile != null) {
            stringIds.intern(this.sourceFile);
        }
        if (this.annotationsDirectory.isEmpty()) {
            return;
        }
        if (this.annotationsDirectory.isInternable()) {
            this.annotationsDirectory = (AnnotationsDirectoryItem) wordData.intern(this.annotationsDirectory);
        } else {
            wordData.add(this.annotationsDirectory);
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void writeTo(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        int indexOf;
        int absoluteOffset;
        int indexOf2;
        int absoluteOffset2;
        String human;
        String human2;
        boolean annotates = annotatedOutput.annotates();
        TypeIdsSection typeIds = dexFile.getTypeIds();
        int indexOf3 = typeIds.indexOf(this.thisClass);
        if (this.superclass == null) {
            indexOf = -1;
        } else {
            indexOf = typeIds.indexOf(this.superclass);
        }
        int absoluteOffsetOr0 = OffsettedItem.getAbsoluteOffsetOr0(this.interfaces);
        if (this.annotationsDirectory.isEmpty()) {
            absoluteOffset = 0;
        } else {
            absoluteOffset = this.annotationsDirectory.getAbsoluteOffset();
        }
        if (this.sourceFile == null) {
            indexOf2 = -1;
        } else {
            indexOf2 = dexFile.getStringIds().indexOf(this.sourceFile);
        }
        if (this.classData.isEmpty()) {
            absoluteOffset2 = 0;
        } else {
            absoluteOffset2 = this.classData.getAbsoluteOffset();
        }
        int absoluteOffsetOr02 = OffsettedItem.getAbsoluteOffsetOr0(this.staticValuesItem);
        if (annotates) {
            annotatedOutput.annotate(0, indexString() + ' ' + this.thisClass.toHuman());
            annotatedOutput.annotate(4, "  class_idx:           " + Hex.u4(indexOf3));
            annotatedOutput.annotate(4, "  access_flags:        " + AccessFlags.classString(this.accessFlags));
            StringBuilder append = new StringBuilder("  superclass_idx:      ").append(Hex.u4(indexOf)).append(" // ");
            if (this.superclass == null) {
                human = "<none>";
            } else {
                human = this.superclass.toHuman();
            }
            annotatedOutput.annotate(4, append.append(human).toString());
            annotatedOutput.annotate(4, "  interfaces_off:      " + Hex.u4(absoluteOffsetOr0));
            if (absoluteOffsetOr0 != 0) {
                TypeList list = this.interfaces.getList();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    annotatedOutput.annotate(0, "    " + list.getType(i).toHuman());
                }
            }
            StringBuilder append2 = new StringBuilder("  source_file_idx:     ").append(Hex.u4(indexOf2)).append(" // ");
            if (this.sourceFile == null) {
                human2 = "<none>";
            } else {
                human2 = this.sourceFile.toHuman();
            }
            annotatedOutput.annotate(4, append2.append(human2).toString());
            annotatedOutput.annotate(4, "  annotations_off:     " + Hex.u4(absoluteOffset));
            annotatedOutput.annotate(4, "  class_data_off:      " + Hex.u4(absoluteOffset2));
            annotatedOutput.annotate(4, "  static_values_off:   " + Hex.u4(absoluteOffsetOr02));
        }
        annotatedOutput.writeInt(indexOf3);
        annotatedOutput.writeInt(this.accessFlags);
        annotatedOutput.writeInt(indexOf);
        annotatedOutput.writeInt(absoluteOffsetOr0);
        annotatedOutput.writeInt(indexOf2);
        annotatedOutput.writeInt(absoluteOffset);
        annotatedOutput.writeInt(absoluteOffset2);
        annotatedOutput.writeInt(absoluteOffsetOr02);
    }

    public CstType getThisClass() {
        return this.thisClass;
    }

    public int getAccessFlags() {
        return this.accessFlags;
    }

    public CstType getSuperclass() {
        return this.superclass;
    }

    public TypeList getInterfaces() {
        if (this.interfaces == null) {
            return StdTypeList.EMPTY;
        }
        return this.interfaces.getList();
    }

    public CstString getSourceFile() {
        return this.sourceFile;
    }

    public void addStaticField(EncodedField encodedField, Constant constant) {
        this.classData.addStaticField(encodedField, constant);
    }

    public void addInstanceField(EncodedField encodedField) {
        this.classData.addInstanceField(encodedField);
    }

    public void addDirectMethod(EncodedMethod encodedMethod) {
        this.classData.addDirectMethod(encodedMethod);
    }

    public void addVirtualMethod(EncodedMethod encodedMethod) {
        this.classData.addVirtualMethod(encodedMethod);
    }

    public ArrayList<EncodedMethod> getMethods() {
        return this.classData.getMethods();
    }

    public void setClassAnnotations(Annotations annotations, DexFile dexFile) {
        this.annotationsDirectory.setClassAnnotations(annotations, dexFile);
    }

    public void addFieldAnnotations(CstFieldRef cstFieldRef, Annotations annotations, DexFile dexFile) {
        this.annotationsDirectory.addFieldAnnotations(cstFieldRef, annotations, dexFile);
    }

    public void addMethodAnnotations(CstMethodRef cstMethodRef, Annotations annotations, DexFile dexFile) {
        this.annotationsDirectory.addMethodAnnotations(cstMethodRef, annotations, dexFile);
    }

    public void addParameterAnnotations(CstMethodRef cstMethodRef, AnnotationsList annotationsList, DexFile dexFile) {
        this.annotationsDirectory.addParameterAnnotations(cstMethodRef, annotationsList, dexFile);
    }

    public Annotations getMethodAnnotations(CstMethodRef cstMethodRef) {
        return this.annotationsDirectory.getMethodAnnotations(cstMethodRef);
    }

    public AnnotationsList getParameterAnnotations(CstMethodRef cstMethodRef) {
        return this.annotationsDirectory.getParameterAnnotations(cstMethodRef);
    }

    public void debugPrint(Writer writer, boolean z) {
        PrintWriter printWriterFor = Writers.printWriterFor(writer);
        printWriterFor.println(getClass().getName() + " {");
        printWriterFor.println("  accessFlags: " + Hex.u2(this.accessFlags));
        printWriterFor.println("  superclass: " + this.superclass);
        printWriterFor.println("  interfaces: " + (this.interfaces == null ? "<none>" : this.interfaces));
        printWriterFor.println("  sourceFile: " + (this.sourceFile == null ? "<none>" : this.sourceFile.toQuoted()));
        this.classData.debugPrint(writer, z);
        this.annotationsDirectory.debugPrint(printWriterFor);
        printWriterFor.println("}");
    }
}
