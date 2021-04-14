package mod.agus.jcoderz.dx.merge;

import java.util.Comparator;
import mod.agus.jcoderz.dex.ClassDef;
import mod.agus.jcoderz.dex.Dex;

final class SortableType {
    public static final Comparator<SortableType> NULLS_LAST_ORDER = new Comparator<SortableType>() {

        @Override // java.util.Comparator
        public /* bridge */ /* synthetic */ int compare(SortableType sortableType, SortableType sortableType2) {
            return compare(sortableType, sortableType2);
        }

        public int compareTwo(SortableType sortableType, SortableType sortableType2) {
            if (sortableType == sortableType2) {
                return 0;
            }
            if (sortableType2 == null) {
                return -1;
            }
            if (sortableType == null) {
                return 1;
            }
            if (sortableType.depth != sortableType2.depth) {
                return sortableType.depth - sortableType2.depth;
            }
            return sortableType.getTypeIndex() - sortableType2.getTypeIndex();
        }
    };
    private ClassDef classDef;
    private int depth = -1;
    private final Dex dex;
    private final IndexMap indexMap;

    public SortableType(Dex dex2, IndexMap indexMap2, ClassDef classDef2) {
        this.dex = dex2;
        this.indexMap = indexMap2;
        this.classDef = classDef2;
    }

    public Dex getDex() {
        return this.dex;
    }

    public IndexMap getIndexMap() {
        return this.indexMap;
    }

    public ClassDef getClassDef() {
        return this.classDef;
    }

    public int getTypeIndex() {
        return this.classDef.getTypeIndex();
    }

    public boolean tryAssignDepth(SortableType[] sortableTypeArr) {
        int i;
        if (this.classDef.getSupertypeIndex() == -1) {
            i = 0;
        } else {
            SortableType sortableType = sortableTypeArr[this.classDef.getSupertypeIndex()];
            if (sortableType == null) {
                i = 1;
            } else if (sortableType.depth == -1) {
                return false;
            } else {
                i = sortableType.depth;
            }
        }
        for (short s : this.classDef.getInterfaces()) {
            SortableType sortableType2 = sortableTypeArr[s];
            if (sortableType2 == null) {
                i = Math.max(i, 1);
            } else if (sortableType2.depth == -1) {
                return false;
            } else {
                i = Math.max(i, sortableType2.depth);
            }
        }
        this.depth = i + 1;
        return true;
    }

    public boolean isDepthAssigned() {
        return this.depth != -1;
    }
}
