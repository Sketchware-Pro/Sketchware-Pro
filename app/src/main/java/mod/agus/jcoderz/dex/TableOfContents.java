package mod.agus.jcoderz.dex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import mod.agus.jcoderz.dex.Dex;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.util.Util;

public final class TableOfContents {
    public final Section annotationSetRefLists = new Section(4098);
    public final Section annotationSets = new Section(4099);
    public final Section annotations = new Section(Binding.INTERSECTION_TYPE);
    public final Section annotationsDirectories = new Section(8198);
    public int checksum;
    public final Section classDatas = new Section(8192);
    public final Section classDefs = new Section(6);
    public final Section codes = new Section(8193);
    public int dataOff;
    public int dataSize;
    public final Section debugInfos = new Section(8195);
    public final Section encodedArrays = new Section(8197);
    public final Section fieldIds = new Section(4);
    public int fileSize;
    public final Section header = new Section(0);
    public int linkOff;
    public int linkSize;
    public final Section mapList = new Section(4096);
    public final Section methodIds = new Section(5);
    public final Section protoIds = new Section(3);
    public final Section[] sections = {this.header, this.stringIds, this.typeIds, this.protoIds, this.fieldIds, this.methodIds, this.classDefs, this.mapList, this.typeLists, this.annotationSetRefLists, this.annotationSets, this.classDatas, this.codes, this.stringDatas, this.debugInfos, this.annotations, this.encodedArrays, this.annotationsDirectories};
    public byte[] signature = new byte[20];
    public final Section stringDatas = new Section(8194);
    public final Section stringIds = new Section(1);
    public final Section typeIds = new Section(2);
    public final Section typeLists = new Section(4097);

    public void readFrom(Dex dex) throws IOException {
        readHeader(dex.open(0));
        readMap(dex.open(this.mapList.off));
        computeSizesFromOffsets();
    }

    private void readHeader(Dex.Section section) throws UnsupportedEncodingException {
        byte[] readByteArray = section.readByteArray(8);
        if (DexFormat.magicToApi(readByteArray) != 13) {
            throw new DexException("Unexpected magic: " + Arrays.toString(readByteArray));
        }
        this.checksum = section.readInt();
        this.signature = section.readByteArray(20);
        this.fileSize = section.readInt();
        int readInt = section.readInt();
        if (readInt != 112) {
            throw new DexException("Unexpected header: 0x" + Integer.toHexString(readInt));
        }
        int readInt2 = section.readInt();
        if (readInt2 != 305419896) {
            throw new DexException("Unexpected endian tag: 0x" + Integer.toHexString(readInt2));
        }
        this.linkSize = section.readInt();
        this.linkOff = section.readInt();
        this.mapList.off = section.readInt();
        if (this.mapList.off == 0) {
            throw new DexException("Cannot merge dex files that do not contain a map");
        }
        this.stringIds.size = section.readInt();
        this.stringIds.off = section.readInt();
        this.typeIds.size = section.readInt();
        this.typeIds.off = section.readInt();
        this.protoIds.size = section.readInt();
        this.protoIds.off = section.readInt();
        this.fieldIds.size = section.readInt();
        this.fieldIds.off = section.readInt();
        this.methodIds.size = section.readInt();
        this.methodIds.off = section.readInt();
        this.classDefs.size = section.readInt();
        this.classDefs.off = section.readInt();
        this.dataSize = section.readInt();
        this.dataOff = section.readInt();
    }

    private void readMap(Dex.Section section) throws IOException {
        int readInt = section.readInt();
        Section section2 = null;
        int i = 0;
        while (i < readInt) {
            short readShort = section.readShort();
            section.readShort();
            Section section3 = getSection(readShort);
            int readInt2 = section.readInt();
            int readInt3 = section.readInt();
            if ((section3.size == 0 || section3.size == readInt2) && (section3.off == -1 || section3.off == readInt3)) {
                section3.size = readInt2;
                section3.off = readInt3;
                if (section2 == null || section2.off <= section3.off) {
                    i++;
                    section2 = section3;
                } else {
                    throw new DexException("Map is unsorted at " + section2 + ", " + section3);
                }
            } else {
                throw new DexException("Unexpected map value for 0x" + Integer.toHexString(readShort));
            }
        }
        Arrays.sort(this.sections);
    }

    public void computeSizesFromOffsets() {
        int i = this.dataSize + this.dataOff;
        for (int length = this.sections.length - 1; length >= 0; length--) {
            Section section = this.sections[length];
            if (section.off != -1) {
                if (section.off > i) {
                    throw new DexException("Map is unsorted at " + section);
                }
                section.byteCount = i - section.off;
                i = section.off;
            }
        }
    }

    private Section getSection(short s) {
        Section[] sectionArr = this.sections;
        for (Section section : sectionArr) {
            if (section.type == s) {
                return section;
            }
        }
        throw new IllegalArgumentException("No such map item: " + ((int) s));
    }

    public void writeHeader(Dex.Section section) throws IOException {
        section.write(DexFormat.apiToMagic(13).getBytes(Util.UTF_8));
        section.writeInt(this.checksum);
        section.write(this.signature);
        section.writeInt(this.fileSize);
        section.writeInt(112);
        section.writeInt(DexFormat.ENDIAN_TAG);
        section.writeInt(this.linkSize);
        section.writeInt(this.linkOff);
        section.writeInt(this.mapList.off);
        section.writeInt(this.stringIds.size);
        section.writeInt(this.stringIds.off);
        section.writeInt(this.typeIds.size);
        section.writeInt(this.typeIds.off);
        section.writeInt(this.protoIds.size);
        section.writeInt(this.protoIds.off);
        section.writeInt(this.fieldIds.size);
        section.writeInt(this.fieldIds.off);
        section.writeInt(this.methodIds.size);
        section.writeInt(this.methodIds.off);
        section.writeInt(this.classDefs.size);
        section.writeInt(this.classDefs.off);
        section.writeInt(this.dataSize);
        section.writeInt(this.dataOff);
    }

    public void writeMap(Dex.Section section) throws IOException {
        int i = 0;
        for (Section section2 : this.sections) {
            if (section2.exists()) {
                i++;
            }
        }
        section.writeInt(i);
        Section[] sectionArr = this.sections;
        for (Section section3 : sectionArr) {
            if (section3.exists()) {
                section.writeShort(section3.type);
                section.writeShort((short) 0);
                section.writeInt(section3.size);
                section.writeInt(section3.off);
            }
        }
    }

    public static class Section implements Comparable<Section> {
        public int byteCount = 0;
        public int off = -1;
        public int size = 0;
        public final short type;

        public Section(int i) {
            this.type = (short) i;
        }

        public boolean exists() {
            return this.size > 0;
        }

        public int compareTo(Section section) {
            if (this.off != section.off) {
                return this.off < section.off ? -1 : 1;
            }
            return 0;
        }

        public String toString() {
            return String.format("Section[type=%#x,off=%#x,size=%#x]", Short.valueOf(this.type), Integer.valueOf(this.off), Integer.valueOf(this.size));
        }
    }
}
