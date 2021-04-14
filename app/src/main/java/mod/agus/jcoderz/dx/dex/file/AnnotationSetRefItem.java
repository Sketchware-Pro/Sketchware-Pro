package mod.agus.jcoderz.dx.dex.file;

import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class AnnotationSetRefItem extends OffsettedItem {
    private static final int ALIGNMENT = 4;
    private static final int WRITE_SIZE = 4;
    private AnnotationSetItem annotations;

    public AnnotationSetRefItem(AnnotationSetItem annotationSetItem) {
        super(4, 4);
        if (annotationSetItem == null) {
            throw new NullPointerException("annotations == null");
        }
        this.annotations = annotationSetItem;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_ANNOTATION_SET_REF_ITEM;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
        this.annotations = (AnnotationSetItem) dexFile.getWordData().intern(this.annotations);
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    public String toHuman() {
        return this.annotations.toHuman();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    protected void writeTo0(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        int absoluteOffset = this.annotations.getAbsoluteOffset();
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, "  annotations_off: " + Hex.u4(absoluteOffset));
        }
        annotatedOutput.writeInt(absoluteOffset);
    }
}
