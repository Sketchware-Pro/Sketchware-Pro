package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.FixedSizeList;
import mod.agus.jcoderz.dx.util.IntList;

public final class ByteCatchList extends FixedSizeList {
    public static final ByteCatchList EMPTY = new ByteCatchList(0);

    public ByteCatchList(int i) {
        super(i);
    }

    private static boolean typeNotFound(Item item, Item[] itemArr, int i) {
        CstType exceptionClass = item.getExceptionClass();
        for (int i2 = 0; i2 < i; i2++) {
            CstType exceptionClass2 = itemArr[i2].getExceptionClass();
            if (exceptionClass2 == exceptionClass || exceptionClass2 == CstType.OBJECT) {
                return false;
            }
        }
        return true;
    }

    public int byteLength() {
        return (size() * 8) + 2;
    }

    public Item get(int i) {
        return (Item) get0(i);
    }

    public void set(int i, Item item) {
        if (item == null) {
            throw new NullPointerException("item == null");
        }
        set0(i, item);
    }

    public void set(int i, int i2, int i3, int i4, CstType cstType) {
        set0(i, new Item(i2, i3, i4, cstType));
    }

    public ByteCatchList listFor(int i) {
        int size = size();
        Item[] itemArr = new Item[size];
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            Item item = get(i3);
            if (item.covers(i) && typeNotFound(item, itemArr, i2)) {
                itemArr[i2] = item;
                i2++;
            }
        }
        if (i2 == 0) {
            return EMPTY;
        }
        ByteCatchList byteCatchList = new ByteCatchList(i2);
        for (int i4 = 0; i4 < i2; i4++) {
            byteCatchList.set(i4, itemArr[i4]);
        }
        byteCatchList.setImmutable();
        return byteCatchList;
    }

    public IntList toTargetList(int i) {
        int i2 = 1;
        if (i < -1) {
            throw new IllegalArgumentException("noException < -1");
        }
        boolean z = i >= 0;
        int size = size();
        if (size != 0) {
            if (!z) {
                i2 = 0;
            }
            IntList intList = new IntList(i2 + size);
            for (int i3 = 0; i3 < size; i3++) {
                intList.add(get(i3).getHandlerPc());
            }
            if (z) {
                intList.add(i);
            }
            intList.setImmutable();
            return intList;
        } else if (z) {
            return IntList.makeImmutable(i);
        } else {
            return IntList.EMPTY;
        }
    }

    public TypeList toRopCatchList() {
        int size = size();
        if (size == 0) {
            return StdTypeList.EMPTY;
        }
        StdTypeList stdTypeList = new StdTypeList(size);
        for (int i = 0; i < size; i++) {
            stdTypeList.set(i, get(i).getExceptionClass().getClassType());
        }
        stdTypeList.setImmutable();
        return stdTypeList;
    }

    public static class Item {
        private final int endPc;
        private final CstType exceptionClass;
        private final int handlerPc;
        private final int startPc;

        public Item(int i, int i2, int i3, CstType cstType) {
            if (i < 0) {
                throw new IllegalArgumentException("startPc < 0");
            } else if (i2 < i) {
                throw new IllegalArgumentException("endPc < startPc");
            } else if (i3 < 0) {
                throw new IllegalArgumentException("handlerPc < 0");
            } else {
                this.startPc = i;
                this.endPc = i2;
                this.handlerPc = i3;
                this.exceptionClass = cstType;
            }
        }

        public int getStartPc() {
            return this.startPc;
        }

        public int getEndPc() {
            return this.endPc;
        }

        public int getHandlerPc() {
            return this.handlerPc;
        }

        public CstType getExceptionClass() {
            return this.exceptionClass != null ? this.exceptionClass : CstType.OBJECT;
        }

        public boolean covers(int i) {
            return i >= this.startPc && i < this.endPc;
        }
    }
}
