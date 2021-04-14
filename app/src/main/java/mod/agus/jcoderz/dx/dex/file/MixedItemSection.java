package mod.agus.jcoderz.dx.dex.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class MixedItemSection extends Section {
    private static /* synthetic */ int[] $SWITCH_TABLE$mod$agus$jcoderz$dx$dex$file$MixedItemSection$SortType;
    private static final Comparator<OffsettedItem> TYPE_SORTER = new Comparator<OffsettedItem>() {
        /* class mod.agus.jcoderz.dx.dex.file.MixedItemSection.1 */

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.Object] */
        @Override // java.util.Comparator
        public /* bridge */ /* synthetic */ int compare(OffsettedItem offsettedItem, OffsettedItem offsettedItem2) {
            return compare(offsettedItem, offsettedItem2);
        }

        public int compareTwo(OffsettedItem offsettedItem, OffsettedItem offsettedItem2) {
            return offsettedItem.itemType().compareTo((ItemType) offsettedItem2.itemType());
        }
    };
    private final HashMap<OffsettedItem, OffsettedItem> interns = new HashMap<>(100);
    private final ArrayList<OffsettedItem> items = new ArrayList<>(100);
    private final SortType sort;
    private int writeSize;

    enum SortType {
        NONE,
        TYPE,
        INSTANCE
    }

    static int[] $SWITCH_TABLE$mod$agus$jcoderz$dx$dex$file$MixedItemSection$SortType() {
        int[] iArr = $SWITCH_TABLE$mod$agus$jcoderz$dx$dex$file$MixedItemSection$SortType;
        if (iArr == null) {
            iArr = new int[SortType.values().length];
            try {
                iArr[SortType.INSTANCE.ordinal()] = 3;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[SortType.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[SortType.TYPE.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            $SWITCH_TABLE$mod$agus$jcoderz$dx$dex$file$MixedItemSection$SortType = iArr;
        }
        return iArr;
    }

    public MixedItemSection(String str, DexFile dexFile, int i, SortType sortType) {
        super(str, dexFile, i);
        this.sort = sortType;
        this.writeSize = -1;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    public Collection<? extends Item> items() {
        return this.items;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    public int writeSize() {
        throwIfNotPrepared();
        return this.writeSize;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    public int getAbsoluteItemOffset(Item item) {
        return ((OffsettedItem) item).getAbsoluteOffset();
    }

    public int size() {
        return this.items.size();
    }

    public void writeHeaderPart(AnnotatedOutput annotatedOutput) {
        throwIfNotPrepared();
        if (this.writeSize == -1) {
            throw new RuntimeException("write size not yet set");
        }
        int i = this.writeSize;
        int fileOffset = i == 0 ? 0 : getFileOffset();
        String name = getName();
        if (name == null) {
            name = "<unnamed>";
        }
        char[] cArr = new char[(15 - name.length())];
        Arrays.fill(cArr, ' ');
        String str = new String(cArr);
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(4, String.valueOf(name) + "_size:" + str + Hex.u4(i));
            annotatedOutput.annotate(4, String.valueOf(name) + "_off: " + str + Hex.u4(fileOffset));
        }
        annotatedOutput.writeInt(i);
        annotatedOutput.writeInt(fileOffset);
    }

    public void add(OffsettedItem offsettedItem) {
        throwIfPrepared();
        try {
            if (offsettedItem.getAlignment() > getAlignment()) {
                throw new IllegalArgumentException("incompatible item alignment");
            }
            this.items.add(offsettedItem);
        } catch (NullPointerException e) {
            throw new NullPointerException("item == null");
        }
    }

    public synchronized <T extends OffsettedItem> T intern(T t) {
        T t2;
        throwIfPrepared();
        t2 = (T) this.interns.get(t);
        if (t2 == null) {
            add(t);
            this.interns.put(t, t);
            t2 = t;
        }
        return t2;
    }

    public <T extends OffsettedItem> T get(T t) {
        throwIfNotPrepared();
        T t2 = (T) this.interns.get(t);
        if (t2 != null) {
            return t2;
        }
        throw new NoSuchElementException(t.toString());
    }

    public void writeIndexAnnotation(AnnotatedOutput annotatedOutput, ItemType itemType, String str) {
        throwIfNotPrepared();
        TreeMap treeMap = new TreeMap();
        Iterator<OffsettedItem> it = this.items.iterator();
        while (it.hasNext()) {
            OffsettedItem next = it.next();
            if (next.itemType() == itemType) {
                treeMap.put(next.toHuman(), next);
            }
        }
        if (treeMap.size() != 0) {
            annotatedOutput.annotate(0, str);
            for (Iterator iterator = treeMap.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry entry = (Map.Entry) iterator.next();
                annotatedOutput.annotate(0, String.valueOf(((OffsettedItem) entry.getValue()).offsetString()) + ' ' + ((String) entry.getKey()) + '\n');
            }
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    protected void prepare0() {
        DexFile file = getFile();
        int i = 0;
        while (true) {
            int size = this.items.size();
            if (i < size) {
                int i2 = i;
                while (i2 < size) {
                    this.items.get(i2).addContents(file);
                    i2++;
                }
                i = i2;
            } else {
                return;
            }
        }
    }

    public void placeItems() {
        throwIfNotPrepared();
        switch ($SWITCH_TABLE$mod$agus$jcoderz$dx$dex$file$MixedItemSection$SortType()[this.sort.ordinal()]) {
            case 2:
                Collections.sort(this.items, TYPE_SORTER);
                break;
            case 3:
                Collections.sort(this.items);
                break;
        }
        int size = this.items.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            OffsettedItem offsettedItem = this.items.get(i2);
            try {
                int place = offsettedItem.place(this, i);
                if (place < i) {
                    throw new RuntimeException("bogus place() result for " + offsettedItem);
                }
                i = place + offsettedItem.writeSize();
            } catch (RuntimeException e) {
                throw ExceptionWithContext.withContext(e, "...while placing " + offsettedItem);
            }
        }
        this.writeSize = i;
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Section
    protected void writeTo0(AnnotatedOutput annotatedOutput) {
        boolean annotates = annotatedOutput.annotates();
        DexFile file = getFile();
        Iterator<OffsettedItem> it = this.items.iterator();
        int i = 0;
        boolean z = true;
        while (it.hasNext()) {
            OffsettedItem next = it.next();
            if (annotates) {
                if (z) {
                    z = false;
                } else {
                    annotatedOutput.annotate(0, "\n");
                }
            }
            int alignment = next.getAlignment() - 1;
            int i2 = (alignment ^ -1) & (i + alignment);
            if (i != i2) {
                annotatedOutput.writeZeroes(i2 - i);
                i = i2;
            }
            next.writeTo(file, annotatedOutput);
            i = next.writeSize() + i;
        }
        if (i != this.writeSize) {
            throw new RuntimeException("output size mismatch");
        }
    }
}
