package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class StdFieldList extends FixedSizeList implements FieldList {
    public StdFieldList(int i) {
        super(i);
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.FieldList
    public Field get(int i) {
        return (Field) get0(i);
    }

    public void set(int i, Field field) {
        set0(i, field);
    }
}
