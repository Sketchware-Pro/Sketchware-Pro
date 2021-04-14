package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.util.ToHuman;

public abstract class Constant implements ToHuman, Comparable<Constant> {

    public abstract int compareTo0(Constant constant);

    public abstract boolean isCategory2();

    public abstract String typeName();

    public final int compareTo(Constant constant) {
        Class<?> cls = getClass();
        Class<?> cls2 = constant.getClass();
        if (cls != cls2) {
            return cls.getName().compareTo(cls2.getName());
        }
        return compareTo0(constant);
    }
}
