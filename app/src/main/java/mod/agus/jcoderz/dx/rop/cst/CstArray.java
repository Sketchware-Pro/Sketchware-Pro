package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class CstArray extends Constant {
    private final List list;

    public CstArray(List list2) {
        if (list2 == null) {
            throw new NullPointerException("list == null");
        }
        list2.throwIfMutable();
        this.list = list2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CstArray)) {
            return false;
        }
        return this.list.equals(((CstArray) obj).list);
    }

    public int hashCode() {
        return this.list.hashCode();
    }

    /* access modifiers changed from: protected */
    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public int compareTo0(Constant constant) {
        return this.list.compareTo(((CstArray) constant).list);
    }

    public String toString() {
        return this.list.toString("array{", ", ", "}");
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public String typeName() {
        return "array";
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public boolean isCategory2() {
        return false;
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return this.list.toHuman("{", ", ", "}");
    }

    public List getList() {
        return this.list;
    }

    public static final class List extends FixedSizeList implements Comparable<List> {
        public List(int i) {
            super(i);
        }

        public int compareTo(List list) {
            int size = size();
            int size2 = list.size();
            int i = size < size2 ? size : size2;
            for (int i2 = 0; i2 < i; i2++) {
                int compareTo = ((Constant) get0(i2)).compareTo((Constant) list.get0(i2));
                if (compareTo != 0) {
                    return compareTo;
                }
            }
            if (size < size2) {
                return -1;
            }
            if (size > size2) {
                return 1;
            }
            return 0;
        }

        public Constant get(int i) {
            return (Constant) get0(i);
        }

        public void set(int i, Constant constant) {
            set0(i, constant);
        }
    }
}
