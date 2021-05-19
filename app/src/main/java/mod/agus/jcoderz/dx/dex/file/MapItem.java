package mod.agus.jcoderz.dx.dex.file;

import java.util.ArrayList;

import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

public final class MapItem extends OffsettedItem {
    private static final int ALIGNMENT = 4;
    private static final int WRITE_SIZE = 12;
    private final Item firstItem;
    private final int itemCount;
    private final Item lastItem;
    private final Section section;
    private final ItemType type;

    private MapItem(ItemType itemType, Section section2, Item item, Item item2, int i) {
        super(4, 12);
        if (itemType == null) {
            throw new NullPointerException("type == null");
        } else if (section2 == null) {
            throw new NullPointerException("section == null");
        } else if (item == null) {
            throw new NullPointerException("firstItem == null");
        } else if (item2 == null) {
            throw new NullPointerException("lastItem == null");
        } else if (i <= 0) {
            throw new IllegalArgumentException("itemCount <= 0");
        } else {
            this.type = itemType;
            this.section = section2;
            this.firstItem = item;
            this.lastItem = item2;
            this.itemCount = i;
        }
    }

    private MapItem(Section section2) {
        super(4, 12);
        if (section2 == null) {
            throw new NullPointerException("section == null");
        }
        this.type = ItemType.TYPE_MAP_LIST;
        this.section = section2;
        this.firstItem = null;
        this.lastItem = null;
        this.itemCount = 1;
    }

    public static void addMap(Section[] sectionArr, MixedItemSection mixedItemSection) {
        if (sectionArr == null) {
            throw new NullPointerException("sections == null");
        } else if (mixedItemSection.items().size() != 0) {
            throw new IllegalArgumentException("mapSection.items().size() != 0");
        } else {
            ArrayList arrayList = new ArrayList(50);
            for (Section section2 : sectionArr) {
                int i = 0;
                Item item = null;
                Item item2 = null;
                ItemType itemType = null;
                for (Item item3 : section2.items()) {
                    ItemType itemType2 = item3.itemType();
                    if (itemType2 != itemType) {
                        if (i != 0) {
                            arrayList.add(new MapItem(itemType, section2, item2, item, i));
                        }
                        i = 0;
                        item2 = item3;
                        itemType = itemType2;
                    }
                    i++;
                    item = item3;
                }
                if (i != 0) {
                    arrayList.add(new MapItem(itemType, section2, item2, item, i));
                } else if (section2 == mixedItemSection) {
                    arrayList.add(new MapItem(mixedItemSection));
                }
            }
            mixedItemSection.add(new UniformListItem(ItemType.TYPE_MAP_LIST, arrayList));
        }
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public ItemType itemType() {
        return ItemType.TYPE_MAP_ITEM;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append(getClass().getName());
        stringBuffer.append('{');
        stringBuffer.append(this.section.toString());
        stringBuffer.append(' ');
        stringBuffer.append(this.type.toHuman());
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.Item
    public void addContents(DexFile dexFile) {
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    public final String toHuman() {
        return toString();
    }

    @Override // mod.agus.jcoderz.dx.dex.file.OffsettedItem
    protected void writeTo0(DexFile dexFile, AnnotatedOutput annotatedOutput) {
        int absoluteItemOffset;
        int mapValue = this.type.getMapValue();
        if (this.firstItem == null) {
            absoluteItemOffset = this.section.getFileOffset();
        } else {
            absoluteItemOffset = this.section.getAbsoluteItemOffset(this.firstItem);
        }
        if (annotatedOutput.annotates()) {
            annotatedOutput.annotate(0, offsetString() + ' ' + this.type.getTypeName() + " map");
            annotatedOutput.annotate(2, "  type:   " + Hex.u2(mapValue) + " // " + this.type.toString());
            annotatedOutput.annotate(2, "  unused: 0");
            annotatedOutput.annotate(4, "  size:   " + Hex.u4(this.itemCount));
            annotatedOutput.annotate(4, "  offset: " + Hex.u4(absoluteItemOffset));
        }
        annotatedOutput.writeShort(mapValue);
        annotatedOutput.writeShort(0);
        annotatedOutput.writeInt(this.itemCount);
        annotatedOutput.writeInt(absoluteItemOffset);
    }
}
