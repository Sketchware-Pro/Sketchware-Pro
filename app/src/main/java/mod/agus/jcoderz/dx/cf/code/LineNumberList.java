package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class LineNumberList extends FixedSizeList {
    public static final LineNumberList EMPTY = new LineNumberList(0);

    public static LineNumberList concat(LineNumberList lineNumberList, LineNumberList lineNumberList2) {
        if (lineNumberList == EMPTY) {
            return lineNumberList2;
        }
        int size = lineNumberList.size();
        int size2 = lineNumberList2.size();
        LineNumberList lineNumberList3 = new LineNumberList(size + size2);
        for (int i = 0; i < size; i++) {
            lineNumberList3.set(i, lineNumberList.get(i));
        }
        for (int i2 = 0; i2 < size2; i2++) {
            lineNumberList3.set(size + i2, lineNumberList2.get(i2));
        }
        return lineNumberList3;
    }

    public LineNumberList(int i) {
        super(i);
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

    public void set(int i, int i2, int i3) {
        set0(i, new Item(i2, i3));
    }

    public int pcToLine(int i) {
        int size = size();
        int i2 = 0;
        int i3 = -1;
        int i4 = -1;
        while (i2 < size) {
            Item item = get(i2);
            int startPc = item.getStartPc();
            if (startPc <= i && startPc > i3) {
                i4 = item.getLineNumber();
                if (startPc == i) {
                    break;
                }
            } else {
                startPc = i3;
            }
            i2++;
            i3 = startPc;
        }
        return i4;
    }

    public static class Item {
        private final int lineNumber;
        private final int startPc;

        public Item(int i, int i2) {
            if (i < 0) {
                throw new IllegalArgumentException("startPc < 0");
            } else if (i2 < 0) {
                throw new IllegalArgumentException("lineNumber < 0");
            } else {
                this.startPc = i;
                this.lineNumber = i2;
            }
        }

        public int getStartPc() {
            return this.startPc;
        }

        public int getLineNumber() {
            return this.lineNumber;
        }
    }
}
