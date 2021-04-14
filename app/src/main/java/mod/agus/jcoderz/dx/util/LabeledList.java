package mod.agus.jcoderz.dx.util;

import java.util.Arrays;

public class LabeledList extends FixedSizeList {
    private final IntList labelToIndex;

    public LabeledList(int i) {
        super(i);
        this.labelToIndex = new IntList(i);
    }

    public LabeledList(LabeledList labeledList) {
        super(labeledList.size());
        this.labelToIndex = labeledList.labelToIndex.mutableCopy();
        int size = labeledList.size();
        for (int i = 0; i < size; i++) {
            Object r2 = labeledList.get0(i);
            if (r2 != null) {
                set0(i, r2);
            }
        }
    }

    public final int getMaxLabel() {
        int size = this.labelToIndex.size() - 1;
        while (size >= 0 && this.labelToIndex.get(size) < 0) {
            size--;
        }
        int i = size + 1;
        this.labelToIndex.shrink(i);
        return i;
    }

    private void removeLabel(int i) {
        this.labelToIndex.set(i, -1);
    }

    private void addLabelIndex(int i, int i2) {
        int size = this.labelToIndex.size();
        for (int i3 = 0; i3 <= i - size; i3++) {
            this.labelToIndex.add(-1);
        }
        this.labelToIndex.set(i, i2);
    }

    public final int indexOfLabel(int i) {
        if (i >= this.labelToIndex.size()) {
            return -1;
        }
        return this.labelToIndex.get(i);
    }

    public final int[] getLabelsInOrder() {
        int size = size();
        int[] iArr = new int[size];
        for (int i = 0; i < size; i++) {
            LabeledItem labeledItem = (LabeledItem) get0(i);
            if (labeledItem == null) {
                throw new NullPointerException("null at index " + i);
            }
            iArr[i] = labeledItem.getLabel();
        }
        Arrays.sort(iArr);
        return iArr;
    }

    @Override // mod.agus.jcoderz.dx.util.FixedSizeList
    public void shrinkToFit() {
        super.shrinkToFit();
        rebuildLabelToIndex();
    }

    private void rebuildLabelToIndex() {
        int size = size();
        for (int i = 0; i < size; i++) {
            LabeledItem labeledItem = (LabeledItem) get0(i);
            if (labeledItem != null) {
                this.labelToIndex.set(labeledItem.getLabel(), i);
            }
        }
    }

    public void set(int i, LabeledItem labeledItem) {
        LabeledItem labeledItem2 = (LabeledItem) getOrNull0(i);
        set0(i, labeledItem);
        if (labeledItem2 != null) {
            removeLabel(labeledItem2.getLabel());
        }
        if (labeledItem != null) {
            addLabelIndex(labeledItem.getLabel(), i);
        }
    }
}
