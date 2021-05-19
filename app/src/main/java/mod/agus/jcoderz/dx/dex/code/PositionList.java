package mod.agus.jcoderz.dx.dex.code;

import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class PositionList extends FixedSizeList {
    public static final PositionList EMPTY = new PositionList(0);
    public static final int IMPORTANT = 3;
    public static final int LINES = 2;
    public static final int NONE = 1;

    public PositionList(int i) {
        super(i);
    }

    public static PositionList make(DalvInsnList dalvInsnList, int i) {
        switch (i) {
            case 1:
                return EMPTY;
            case 2:
            case 3:
                SourcePosition sourcePosition = SourcePosition.NO_INFO;
                int size = dalvInsnList.size();
                Entry[] entryArr = new Entry[size];
                int i2 = 0;
                boolean z = false;
                SourcePosition sourcePosition2 = sourcePosition;
                for (int i3 = 0; i3 < size; i3++) {
                    DalvInsn dalvInsn = dalvInsnList.get(i3);
                    if (dalvInsn instanceof CodeAddress) {
                        z = true;
                    } else {
                        SourcePosition position = dalvInsn.getPosition();
                        if (!position.equals(sourcePosition) && !position.sameLine(sourcePosition2) && (i != 3 || z)) {
                            entryArr[i2] = new Entry(dalvInsn.getAddress(), position);
                            i2++;
                            z = false;
                            sourcePosition2 = position;
                        }
                    }
                }
                PositionList positionList = new PositionList(i2);
                for (int i4 = 0; i4 < i2; i4++) {
                    positionList.set(i4, entryArr[i4]);
                }
                positionList.setImmutable();
                return positionList;
            default:
                throw new IllegalArgumentException("bogus howMuch");
        }
    }

    public Entry get(int i) {
        return (Entry) get0(i);
    }

    public void set(int i, Entry entry) {
        set0(i, entry);
    }

    public static class Entry {
        private final int address;
        private final SourcePosition position;

        public Entry(int i, SourcePosition sourcePosition) {
            if (i < 0) {
                throw new IllegalArgumentException("address < 0");
            } else if (sourcePosition == null) {
                throw new NullPointerException("position == null");
            } else {
                this.address = i;
                this.position = sourcePosition;
            }
        }

        public int getAddress() {
            return this.address;
        }

        public SourcePosition getPosition() {
            return this.position;
        }
    }
}
