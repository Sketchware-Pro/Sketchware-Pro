package mod.agus.jcoderz.dx.dex.file;

import java.util.Arrays;
import java.util.Comparator;

import mod.agus.jcoderz.dx.rop.annotation.Annotation;
import mod.agus.jcoderz.dx.rop.annotation.AnnotationVisibility;
import mod.agus.jcoderz.dx.rop.annotation.NameValuePair;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput;

public final class AnnotationItem extends OffsettedItem {
    private static final int ALIGNMENT = 1;
    private static final TypeIdSorter TYPE_ID_SORTER = new TypeIdSorter(null);
    private static final int VISIBILITY_BUILD = 0;
    private static final int VISIBILITY_RUNTIME = 1;
    private static final int VISIBILITY_SYSTEM = 2;
    private static /* synthetic */ int[] $SWITCH_TABLE$mod$agus$jcoderz$dx$rop$annotation$AnnotationVisibility = null;
    private final Annotation annotation;
    private byte[] encodedForm;
    private TypeIdItem type;

    public AnnotationItem(Annotation annotation2, DexFile dexFile) {
        super(1, -1);
        if (annotation2 == null) {
            throw new NullPointerException("annotation == null");
        }
        this.annotation = annotation2;
        this.type = null;
        this.encodedForm = null;
        addContents(dexFile);
    }

    static int[] $SWITCH_TABLE$mod$agus$jcoderz$dx$rop$annotation$AnnotationVisibility() {
        int[] iArr = $SWITCH_TABLE$mod$agus$jcoderz$dx$rop$annotation$AnnotationVisibility;
        if (iArr == null) {
            iArr = new int[AnnotationVisibility.values().length];
            try {
                iArr[AnnotationVisibility.BUILD.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[AnnotationVisibility.EMBEDDED.ordinal()] = 4;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[AnnotationVisibility.RUNTIME.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[AnnotationVisibility.SYSTEM.ordinal()] = 3;
            } catch (NoSuchFieldError e4) {
            }
            $SWITCH_TABLE$mod$agus$jcoderz$dx$rop$annotation$AnnotationVisibility = iArr;
        }
        return iArr;
    }

    public static void sortByTypeIdIndex(AnnotationItem[] annotationItemArr) {
        Arrays.sort(annotationItemArr, TYPE_ID_SORTER);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_ANNOTATION_ITEM;
    }

    public int hashCode() {
        return this.annotation.hashCode();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    protected int compareTo0(OffsettedItem offsettedItem) {
        return this.annotation.compareTo(((AnnotationItem) offsettedItem).annotation);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    public String toHuman() {
        return this.annotation.toHuman();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        this.type = dexFile.getTypeIds().intern(this.annotation.getType());
        ValueEncoder.addContents(dexFile, this.annotation);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    protected void place0(Section section, int i) {
        ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput();
        new ValueEncoder(section.getFile(), byteArrayAnnotatedOutput).writeAnnotation(this.annotation, false);
        this.encodedForm = byteArrayAnnotatedOutput.toByteArray();
        setWriteSize(this.encodedForm.length + 1);
    }

    public void annotateTo(AnnotatedOutput annotatedOutput, String str) {
        annotatedOutput.annotate(0, str + "visibility: " + this.annotation.getVisibility().toHuman());
        annotatedOutput.annotate(0, str + "type: " + this.annotation.getType().toHuman());
        for (NameValuePair nameValuePair : this.annotation.getNameValuePairs()) {
            annotatedOutput.annotate(0, str + nameValuePair.getName().toHuman() + ": " + ValueEncoder.constantToHuman(nameValuePair.getValue()));
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    protected void writeTo0(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        boolean annotates = annotatedOutput.annotates();
        AnnotationVisibility visibility = this.annotation.getVisibility();
        if (annotates) {
            annotatedOutput.annotate(0, offsetString() + " annotation");
            annotatedOutput.annotate(1, "  visibility: VISBILITY_" + visibility);
        }
        switch ($SWITCH_TABLE$mod$agus$jcoderz$dx$rop$annotation$AnnotationVisibility()[visibility.ordinal()]) {
            case 1:
                annotatedOutput.writeByte(1);
                break;
            case 2:
                annotatedOutput.writeByte(0);
                break;
            case 3:
                annotatedOutput.writeByte(2);
                break;
            default:
                throw new RuntimeException("shouldn't happen");
        }
        if (annotates) {
            new ValueEncoder(dexFile, annotatedOutput).writeAnnotation(this.annotation, true);
        } else {
            annotatedOutput.write(this.encodedForm);
        }
    }

    private static class TypeIdSorter implements Comparator<AnnotationItem> {
        private TypeIdSorter() {
        }

        TypeIdSorter(TypeIdSorter typeIdSorter) {
            this();
        }

        @Override
        public int compare(AnnotationItem annotationItem, AnnotationItem annotationItem2) {
            return compare(annotationItem, annotationItem2);
        }

        public int compareTwo(AnnotationItem annotationItem1, AnnotationItem annotationItem2) {
            int index = annotationItem1.type.getIndex();
            int index2 = annotationItem2.type.getIndex();
            if (index < index2) {
                return -1;
            }
            if (index > index2) {
                return 1;
            }
            return 0;
        }
    }
}
