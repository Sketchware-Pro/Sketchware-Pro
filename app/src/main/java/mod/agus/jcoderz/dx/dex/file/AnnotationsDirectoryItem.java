package mod.agus.jcoderz.dx.dex.file;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import mod.agus.jcoderz.dx.rop.annotation.Annotations;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationsList;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstMethodRef;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class AnnotationsDirectoryItem extends OffsettedItem {
    private static final int ALIGNMENT = 4;
    private static final int ELEMENT_SIZE = 8;
    private static final int HEADER_SIZE = 16;
    private AnnotationSetItem classAnnotations = null;
    private ArrayList<FieldAnnotationStruct> fieldAnnotations = null;
    private ArrayList<MethodAnnotationStruct> methodAnnotations = null;
    private ArrayList<ParameterAnnotationStruct> parameterAnnotations = null;

    public AnnotationsDirectoryItem() {
        super(4, -1);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_ANNOTATIONS_DIRECTORY_ITEM;
    }

    public boolean isEmpty() {
        return this.classAnnotations == null && this.fieldAnnotations == null && this.methodAnnotations == null && this.parameterAnnotations == null;
    }

    public boolean isInternable() {
        return this.classAnnotations != null && this.fieldAnnotations == null && this.methodAnnotations == null && this.parameterAnnotations == null;
    }

    public int hashCode() {
        if (this.classAnnotations == null) {
            return 0;
        }
        return this.classAnnotations.hashCode();
    }

    /**@Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    public int compareTo0(OffsettedItem offsettedItem) {
        if (isInternable()) {
            return ((OffsettedItem) ((AnnotationsDirectoryItem )offsettedItem).classAnnotations).compareTo(null);
        }
        throw new UnsupportedOperationException("uninternable instance");
    }**/

    public void setClassAnnotations(Annotations annotations, DexFile dexFile) {
        if (annotations == null) {
            throw new NullPointerException("annotations == null");
        } else if (this.classAnnotations != null) {
            throw new UnsupportedOperationException("class annotations already set");
        } else {
            this.classAnnotations = new AnnotationSetItem(annotations, dexFile);
        }
    }

    public void addFieldAnnotations(CstFieldRef cstFieldRef, Annotations annotations, DexFile dexFile) {
        if (this.fieldAnnotations == null) {
            this.fieldAnnotations = new ArrayList<>();
        }
        this.fieldAnnotations.add(new FieldAnnotationStruct(cstFieldRef, new AnnotationSetItem(annotations, dexFile)));
    }

    public void addMethodAnnotations(CstMethodRef cstMethodRef, Annotations annotations, DexFile dexFile) {
        if (this.methodAnnotations == null) {
            this.methodAnnotations = new ArrayList<>();
        }
        this.methodAnnotations.add(new MethodAnnotationStruct(cstMethodRef, new AnnotationSetItem(annotations, dexFile)));
    }

    public void addParameterAnnotations(CstMethodRef cstMethodRef, AnnotationsList annotationsList, DexFile dexFile) {
        if (this.parameterAnnotations == null) {
            this.parameterAnnotations = new ArrayList<>();
        }
        this.parameterAnnotations.add(new ParameterAnnotationStruct(cstMethodRef, annotationsList, dexFile));
    }

    public Annotations getMethodAnnotations(CstMethodRef cstMethodRef) {
        if (this.methodAnnotations == null) {
            return null;
        }
        Iterator<MethodAnnotationStruct> it = this.methodAnnotations.iterator();
        while (it.hasNext()) {
            MethodAnnotationStruct next = it.next();
            if (next.getMethod().equals(cstMethodRef)) {
                return next.getAnnotations();
            }
        }
        return null;
    }

    public AnnotationsList getParameterAnnotations(CstMethodRef cstMethodRef) {
        if (this.parameterAnnotations == null) {
            return null;
        }
        Iterator<ParameterAnnotationStruct> it = this.parameterAnnotations.iterator();
        while (it.hasNext()) {
            ParameterAnnotationStruct next = it.next();
            if (next.getMethod().equals(cstMethodRef)) {
                return next.getAnnotationsList();
            }
        }
        return null;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        MixedItemSection wordData = dexFile.getWordData();
        if (this.classAnnotations != null) {
            this.classAnnotations = (AnnotationSetItem) wordData.intern(this.classAnnotations);
        }
        if (this.fieldAnnotations != null) {
            Iterator<FieldAnnotationStruct> it = this.fieldAnnotations.iterator();
            while (it.hasNext()) {
                it.next().addContents(dexFile);
            }
        }
        if (this.methodAnnotations != null) {
            Iterator<MethodAnnotationStruct> it2 = this.methodAnnotations.iterator();
            while (it2.hasNext()) {
                it2.next().addContents(dexFile);
            }
        }
        if (this.parameterAnnotations != null) {
            Iterator<ParameterAnnotationStruct> it3 = this.parameterAnnotations.iterator();
            while (it3.hasNext()) {
                it3.next().addContents(dexFile);
            }
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    public String toHuman() {
        throw new RuntimeException("unsupported");
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    protected void place0(Section section, int i) {
        setWriteSize(((listSize(this.fieldAnnotations) + listSize(this.methodAnnotations) + listSize(this.parameterAnnotations)) * 8) + 16);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    protected void writeTo0(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        boolean annotates = annotatedOutput.annotates();
        int absoluteOffsetOr0 = OffsettedItem.getAbsoluteOffsetOr0(this.classAnnotations);
        int listSize = listSize(this.fieldAnnotations);
        int listSize2 = listSize(this.methodAnnotations);
        int listSize3 = listSize(this.parameterAnnotations);
        if (annotates) {
            annotatedOutput.annotate(0, String.valueOf(offsetString()) + " annotations directory");
            annotatedOutput.annotate(4, "  class_annotations_off: " + Hex.u4(absoluteOffsetOr0));
            annotatedOutput.annotate(4, "  fields_size:           " + Hex.u4(listSize));
            annotatedOutput.annotate(4, "  methods_size:          " + Hex.u4(listSize2));
            annotatedOutput.annotate(4, "  parameters_size:       " + Hex.u4(listSize3));
        }
        annotatedOutput.writeInt(absoluteOffsetOr0);
        annotatedOutput.writeInt(listSize);
        annotatedOutput.writeInt(listSize2);
        annotatedOutput.writeInt(listSize3);
        if (listSize != 0) {
            Collections.sort(this.fieldAnnotations);
            if (annotates) {
                annotatedOutput.annotate(0, "  fields:");
            }
            Iterator<FieldAnnotationStruct> it = this.fieldAnnotations.iterator();
            while (it.hasNext()) {
                it.next().writeTo(dexFile, annotatedOutput);
            }
        }
        if (listSize2 != 0) {
            Collections.sort(this.methodAnnotations);
            if (annotates) {
                annotatedOutput.annotate(0, "  methods:");
            }
            Iterator<MethodAnnotationStruct> it2 = this.methodAnnotations.iterator();
            while (it2.hasNext()) {
                it2.next().writeTo(dexFile, annotatedOutput);
            }
        }
        if (listSize3 != 0) {
            Collections.sort(this.parameterAnnotations);
            if (annotates) {
                annotatedOutput.annotate(0, "  parameters:");
            }
            Iterator<ParameterAnnotationStruct> it3 = this.parameterAnnotations.iterator();
            while (it3.hasNext()) {
                it3.next().writeTo(dexFile, annotatedOutput);
            }
        }
    }

    private static int listSize(ArrayList<?> arrayList) {
        if (arrayList == null) {
            return 0;
        }
        return arrayList.size();
    }

    void debugPrint(PrintWriter printWriter) {
        if (this.classAnnotations != null) {
            printWriter.println("  class annotations: " + this.classAnnotations);
        }
        if (this.fieldAnnotations != null) {
            printWriter.println("  field annotations:");
            Iterator<FieldAnnotationStruct> it = this.fieldAnnotations.iterator();
            while (it.hasNext()) {
                printWriter.println("    " + it.next().toHuman());
            }
        }
        if (this.methodAnnotations != null) {
            printWriter.println("  method annotations:");
            Iterator<MethodAnnotationStruct> it2 = this.methodAnnotations.iterator();
            while (it2.hasNext()) {
                printWriter.println("    " + it2.next().toHuman());
            }
        }
        if (this.parameterAnnotations != null) {
            printWriter.println("  parameter annotations:");
            Iterator<ParameterAnnotationStruct> it3 = this.parameterAnnotations.iterator();
            while (it3.hasNext()) {
                printWriter.println("    " + it3.next().toHuman());
            }
        }
    }
}
