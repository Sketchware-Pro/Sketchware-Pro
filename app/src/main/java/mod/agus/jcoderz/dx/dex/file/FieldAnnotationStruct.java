package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.rop.annotation.Annotations;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.ToHuman;

public final class FieldAnnotationStruct implements ToHuman, Comparable<FieldAnnotationStruct> {
    private AnnotationSetItem annotations;
    private final CstFieldRef field;


    @Override // java.lang.Comparable
    public /* bridge */ /* synthetic */ int compareTo(FieldAnnotationStruct fieldAnnotationStruct) {
        return compareTo(fieldAnnotationStruct);
    }

    public FieldAnnotationStruct(CstFieldRef cstFieldRef, AnnotationSetItem annotationSetItem) {
        if (cstFieldRef == null) {
            throw new NullPointerException("field == null");
        } else if (annotationSetItem == null) {
            throw new NullPointerException("annotations == null");
        } else {
            this.field = cstFieldRef;
            this.annotations = annotationSetItem;
        }
    }

    public int hashCode() {
        return this.field.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FieldAnnotationStruct)) {
            return false;
        }
        return this.field.equals(((FieldAnnotationStruct) obj).field);
    }

    public int compareToTwo(FieldAnnotationStruct fieldAnnotationStruct) {
        return this.field.compareTo((Constant) fieldAnnotationStruct.field);
    }

    public void addContents(DexFile dexFile) {
        FieldIdsSection fieldIds = dexFile.getFieldIds();
        MixedItemSection wordData = dexFile.getWordData();
        fieldIds.intern(this.field);
        this.annotations = (AnnotationSetItem) wordData.intern(this.annotations);
    }

    public void writeTo(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        int indexOf = dexFile.getFieldIds().indexOf(this.field);
        int absoluteOffset = this.annotations.getAbsoluteOffset();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(0, "    " + this.field.toHuman());
            annotatedOutput.annotate(4, "      field_idx:       " + Hex.u4(indexOf));
            annotatedOutput.annotate(4, "      annotations_off: " + Hex.u4(absoluteOffset));
        }
        annotatedOutput.writeInt(indexOf);
        annotatedOutput.writeInt(absoluteOffset);
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return String.valueOf(this.field.toHuman()) + ": " + this.annotations;
    }

    public CstFieldRef getField() {
        return this.field;
    }

    public Annotations getAnnotations() {
        return this.annotations.getAnnotations();
    }
}
