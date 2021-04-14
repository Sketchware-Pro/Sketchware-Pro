package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class StdAttributeList extends FixedSizeList implements AttributeList {
    public StdAttributeList(int i) {
        super(i);
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.AttributeList
    public Attribute get(int i) {
        return (Attribute) get0(i);
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.AttributeList
    public int byteLength() {
        int size = size();
        int i = 2;
        for (int i2 = 0; i2 < size; i2++) {
            i += get(i2).byteLength();
        }
        return i;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.AttributeList
    public Attribute findFirst(String str) {
        int size = size();
        for (int i = 0; i < size; i++) {
            Attribute attribute = get(i);
            if (attribute.getName().equals(str)) {
                return attribute;
            }
        }
        return null;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.AttributeList
    public Attribute findNext(Attribute attribute) {
        int size = size();
        for (int i = 0; i < size; i++) {
            if (get(i) == attribute) {
                String name = attribute.getName();
                for (int i2 = i + 1; i2 < size; i2++) {
                    Attribute attribute2 = get(i2);
                    if (attribute2.getName().equals(name)) {
                        return attribute2;
                    }
                }
                return null;
            }
        }
        return null;
    }

    public void set(int i, Attribute attribute) {
        set0(i, attribute);
    }
}
