package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.util.Hex;
import mod.agus.jcoderz.dx.util.LabeledItem;
import mod.agus.jcoderz.dx.util.LabeledList;

public final class ByteBlockList extends LabeledList {
    public ByteBlockList(int i) {
        super(i);
    }

    public ByteBlock get(int i) {
        return (ByteBlock) get0(i);
    }

    public ByteBlock labelToBlock(int i) {
        int indexOfLabel = indexOfLabel(i);
        if (indexOfLabel >= 0) {
            return get(indexOfLabel);
        }
        throw new IllegalArgumentException("no such label: " + Hex.u2(i));
    }

    public void set(int i, ByteBlock byteBlock) {
        super.set(i, (LabeledItem) byteBlock);
    }
}
