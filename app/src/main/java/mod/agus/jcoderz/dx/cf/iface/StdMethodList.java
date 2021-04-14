package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class StdMethodList extends FixedSizeList implements MethodList {
    public StdMethodList(int i) {
        super(i);
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.MethodList
    public Method get(int i) {
        return (Method) get0(i);
    }

    public void set(int i, Method method) {
        set0(i, method);
    }
}
