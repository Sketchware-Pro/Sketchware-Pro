package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class CatchTable extends FixedSizeList implements Comparable<CatchTable> {
    public static final CatchTable EMPTY = new CatchTable(0);

    public CatchTable(int i) {
        super(i);
    }

    public Entry get(int i) {
        return (Entry) get0(i);
    }

    public void set(int i, Entry entry) {
        set0(i, entry);
    }

    public int compareTo(CatchTable catchTable) {
        if (this == catchTable) {
            return 0;
        }
        int size = size();
        int size2 = catchTable.size();
        int min = Math.min(size, size2);
        for (int i = 0; i < min; i++) {
            int compareTo = get(i).compareTo(catchTable.get(i));
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

    public static class Entry implements Comparable<Entry> {
        private final int end;
        private final CatchHandlerList handlers;
        private final int start;

        public Entry(int i, int i2, CatchHandlerList catchHandlerList) {
            if (i < 0) {
                throw new IllegalArgumentException("start < 0");
            } else if (i2 <= i) {
                throw new IllegalArgumentException("end <= start");
            } else if (catchHandlerList.isMutable()) {
                throw new IllegalArgumentException("handlers.isMutable()");
            } else {
                this.start = i;
                this.end = i2;
                this.handlers = catchHandlerList;
            }
        }

        public int hashCode() {
            return (((this.start * 31) + this.end) * 31) + this.handlers.hashCode();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Entry) || compareTo((Entry) obj) != 0) {
                return false;
            }
            return true;
        }

        public int compareTo(Entry entry) {
            if (this.start < entry.start) {
                return -1;
            }
            if (this.start > entry.start) {
                return 1;
            }
            if (this.end < entry.end) {
                return -1;
            }
            if (this.end > entry.end) {
                return 1;
            }
            return this.handlers.compareTo(entry.handlers);
        }

        public int getStart() {
            return this.start;
        }

        public int getEnd() {
            return this.end;
        }

        public CatchHandlerList getHandlers() {
            return this.handlers;
        }
    }
}
