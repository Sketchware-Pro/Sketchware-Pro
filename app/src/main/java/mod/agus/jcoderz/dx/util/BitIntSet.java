package mod.agus.jcoderz.dx.util;

import java.util.NoSuchElementException;

public class BitIntSet implements IntSet {
    int[] bits;

    public BitIntSet(int i) {
        this.bits = Bits.makeBitSet(i);
    }

    @Override // mod.agus.jcoderz.dx.util.IntSet
    public void add(int i) {
        ensureCapacity(i);
        Bits.set(this.bits, i, true);
    }

    private void ensureCapacity(int i) {
        if (i >= Bits.getMax(this.bits)) {
            int[] makeBitSet = Bits.makeBitSet(Math.max(i + 1, Bits.getMax(this.bits) * 2));
            System.arraycopy(this.bits, 0, makeBitSet, 0, this.bits.length);
            this.bits = makeBitSet;
        }
    }

    @Override // mod.agus.jcoderz.dx.util.IntSet
    public void remove(int i) {
        if (i < Bits.getMax(this.bits)) {
            Bits.set(this.bits, i, false);
        }
    }

    @Override // mod.agus.jcoderz.dx.util.IntSet
    public boolean has(int i) {
        return i < Bits.getMax(this.bits) && Bits.get(this.bits, i);
    }

    @Override // mod.agus.jcoderz.dx.util.IntSet
    public void merge(IntSet intSet) {
        if (intSet instanceof BitIntSet) {
            BitIntSet bitIntSet = (BitIntSet) intSet;
            ensureCapacity(Bits.getMax(bitIntSet.bits) + 1);
            Bits.or(this.bits, bitIntSet.bits);
        } else if (intSet instanceof ListIntSet) {
            ListIntSet listIntSet = (ListIntSet) intSet;
            int size = listIntSet.ints.size();
            if (size > 0) {
                ensureCapacity(listIntSet.ints.get(size - 1));
            }
            for (int i = 0; i < listIntSet.ints.size(); i++) {
                Bits.set(this.bits, listIntSet.ints.get(i), true);
            }
        } else {
            IntIterator it = intSet.iterator();
            while (it.hasNext()) {
                add(it.next());
            }
        }
    }

    @Override // mod.agus.jcoderz.dx.util.IntSet
    public int elements() {
        return Bits.bitCount(this.bits);
    }

    @Override // mod.agus.jcoderz.dx.util.IntSet
    public IntIterator iterator() {
        return new IntIterator() {
            /* class mod.agus.jcoderz.dx.util.BitIntSet.AnonymousClass1 */
            private int idx;

            {
                this.idx = Bits.findFirst(BitIntSet.this.bits, 0);
            }

            @Override // mod.agus.jcoderz.dx.util.IntIterator
            public boolean hasNext() {
                return this.idx >= 0;
            }

            @Override // mod.agus.jcoderz.dx.util.IntIterator
            public int next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int i = this.idx;
                this.idx = Bits.findFirst(BitIntSet.this.bits, this.idx + 1);
                return i;
            }
        };
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        boolean z = true;
        int findFirst = Bits.findFirst(this.bits, 0);
        while (findFirst >= 0) {
            if (!z) {
                sb.append(", ");
            }
            sb.append(findFirst);
            findFirst = Bits.findFirst(this.bits, findFirst + 1);
            z = false;
        }
        sb.append('}');
        return sb.toString();
    }
}
