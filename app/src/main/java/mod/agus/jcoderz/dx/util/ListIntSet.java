package mod.agus.jcoderz.dx.util;

import java.util.NoSuchElementException;

public class ListIntSet implements IntSet {
    final IntList ints = new IntList();

    public ListIntSet() {
        this.ints.sort();
    }

    @Override // mod.agus.jcoderz.dx.util.IntSet
    public void add(int i) {
        int binarysearch = this.ints.binarysearch(i);
        if (binarysearch < 0) {
            this.ints.insert(-(binarysearch + 1), i);
        }
    }

    @Override // mod.agus.jcoderz.dx.util.IntSet
    public void remove(int i) {
        int indexOf = this.ints.indexOf(i);
        if (indexOf >= 0) {
            this.ints.removeIndex(indexOf);
        }
    }

    @Override // mod.agus.jcoderz.dx.util.IntSet
    public boolean has(int i) {
        return this.ints.indexOf(i) >= 0;
    }

    @Override // mod.agus.jcoderz.dx.util.IntSet
    public void merge(IntSet intSet) {
        int i = 0;
        if (intSet instanceof ListIntSet) {
            ListIntSet listIntSet = (ListIntSet) intSet;
            int size = this.ints.size();
            int size2 = listIntSet.ints.size();
            int i2 = 0;
            while (i < size2 && i2 < size) {
                while (i < size2 && listIntSet.ints.get(i) < this.ints.get(i2)) {
                    add(listIntSet.ints.get(i));
                    i++;
                }
                if (i == size2) {
                    break;
                }
                while (i2 < size && listIntSet.ints.get(i) >= this.ints.get(i2)) {
                    i2++;
                }
            }
            while (i < size2) {
                add(listIntSet.ints.get(i));
                i++;
            }
            this.ints.sort();
        } else if (intSet instanceof BitIntSet) {
            BitIntSet bitIntSet = (BitIntSet) intSet;
            while (i >= 0) {
                this.ints.add(i);
                i = Bits.findFirst(bitIntSet.bits, i + 1);
            }
            this.ints.sort();
        } else {
            IntIterator it = intSet.iterator();
            while (it.hasNext()) {
                add(it.next());
            }
        }
    }

    @Override // mod.agus.jcoderz.dx.util.IntSet
    public int elements() {
        return this.ints.size();
    }

    @Override // mod.agus.jcoderz.dx.util.IntSet
    public IntIterator iterator() {
        return new IntIterator() {
            private int idx = 0;

            @Override // mod.agus.jcoderz.dx.util.IntIterator
            public boolean hasNext() {
                return this.idx < ListIntSet.this.ints.size();
            }

            @Override // mod.agus.jcoderz.dx.util.IntIterator
            public int next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                IntList intList = ListIntSet.this.ints;
                int i = this.idx;
                this.idx = i + 1;
                return intList.get(i);
            }
        };
    }

    public String toString() {
        return this.ints.toString();
    }
}
