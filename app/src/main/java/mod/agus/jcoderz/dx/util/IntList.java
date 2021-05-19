package mod.agus.jcoderz.dx.util;

import java.util.Arrays;

public final class IntList extends MutabilityControl {
    public static final IntList EMPTY = new IntList(0);

    static {
        EMPTY.setImmutable();
    }

    private int size;
    private boolean sorted;
    private int[] values;

    public IntList() {
        this(4);
    }

    public IntList(int i) {
        super(true);
        try {
            this.values = new int[i];
            this.size = 0;
            this.sorted = true;
        } catch (NegativeArraySizeException e) {
            throw new IllegalArgumentException("size < 0");
        }
    }

    public static IntList makeImmutable(int i) {
        IntList intList = new IntList(1);
        intList.add(i);
        intList.setImmutable();
        return intList;
    }

    public static IntList makeImmutable(int i, int i2) {
        IntList intList = new IntList(2);
        intList.add(i);
        intList.add(i2);
        intList.setImmutable();
        return intList;
    }

    public int hashCode() {
        int i = 0;
        for (int i2 = 0; i2 < this.size; i2++) {
            i = (i * 31) + this.values[i2];
        }
        return i;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof IntList)) {
            return false;
        }
        IntList intList = (IntList) obj;
        if (!(this.sorted == intList.sorted && this.size == intList.size)) {
            return false;
        }
        for (int i = 0; i < this.size; i++) {
            if (this.values[i] != intList.values[i]) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer((this.size * 5) + 10);
        stringBuffer.append('{');
        for (int i = 0; i < this.size; i++) {
            if (i != 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(this.values[i]);
        }
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    public int size() {
        return this.size;
    }

    public int get(int i) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("n >= size()");
        }
        try {
            return this.values[i];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IndexOutOfBoundsException("n < 0");
        }
    }

    public void set(int i, int i2) {
        throwIfImmutable();
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("n >= size()");
        }
        try {
            this.values[i] = i2;
            this.sorted = false;
        } catch (ArrayIndexOutOfBoundsException e) {
            if (i < 0) {
                throw new IllegalArgumentException("n < 0");
            }
        }
    }

    public void add(int i) {
        boolean z = true;
        throwIfImmutable();
        growIfNeeded();
        int[] iArr = this.values;
        int i2 = this.size;
        this.size = i2 + 1;
        iArr[i2] = i;
        if (this.sorted && this.size > 1) {
            if (i < this.values[this.size - 2]) {
                z = false;
            }
            this.sorted = z;
        }
    }

    public void insert(int i, int i2) {
        if (i > this.size) {
            throw new IndexOutOfBoundsException("n > size()");
        }
        growIfNeeded();
        System.arraycopy(this.values, i, this.values, i + 1, this.size - i);
        this.values[i] = i2;
        this.size++;
        this.sorted = this.sorted && (i == 0 || i2 > this.values[i + -1]) && (i == this.size + -1 || i2 < this.values[i + 1]);
    }

    public void removeIndex(int i) {
        if (i >= this.size) {
            throw new IndexOutOfBoundsException("n >= size()");
        }
        System.arraycopy(this.values, i + 1, this.values, i, (this.size - i) - 1);
        this.size--;
    }

    private void growIfNeeded() {
        if (this.size == this.values.length) {
            int[] iArr = new int[(((this.size * 3) / 2) + 10)];
            System.arraycopy(this.values, 0, iArr, 0, this.size);
            this.values = iArr;
        }
    }

    public int top() {
        return get(this.size - 1);
    }

    public int pop() {
        throwIfImmutable();
        this.size--;
        return get(this.size - 1);
    }

    public void pop(int i) {
        throwIfImmutable();
        this.size -= i;
    }

    public void shrink(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("newSize < 0");
        } else if (i > this.size) {
            throw new IllegalArgumentException("newSize > size");
        } else {
            throwIfImmutable();
            this.size = i;
        }
    }

    public IntList mutableCopy() {
        int i = this.size;
        IntList intList = new IntList(i);
        for (int i2 = 0; i2 < i; i2++) {
            intList.add(this.values[i2]);
        }
        return intList;
    }

    public void sort() {
        throwIfImmutable();
        if (!this.sorted) {
            Arrays.sort(this.values, 0, this.size);
            this.sorted = true;
        }
    }

    public int indexOf(int i) {
        int binarysearch = binarysearch(i);
        if (binarysearch >= 0) {
            return binarysearch;
        }
        return -1;
    }

    public int binarysearch(int i) {
        int i2 = this.size;
        if (!this.sorted) {
            for (int i3 = 0; i3 < i2; i3++) {
                if (this.values[i3] == i) {
                    return i3;
                }
            }
            return -i2;
        }
        int i4 = i2;
        int i5 = -1;
        while (i4 > i5 + 1) {
            int i6 = ((i4 - i5) >> 1) + i5;
            if (i <= this.values[i6]) {
                i4 = i6;
            } else {
                i5 = i6;
            }
        }
        if (i4 == i2) {
            return (-i2) - 1;
        }
        if (i != this.values[i4]) {
            return (-i4) - 1;
        }
        return i4;
    }

    public boolean contains(int i) {
        return indexOf(i) >= 0;
    }
}
