package mod.agus.jcoderz.dx.dex.file;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.Adler32;
import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.dex.DexOptions;
import mod.agus.jcoderz.dx.dex.file.MixedItemSection;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstBaseMethodRef;
import mod.agus.jcoderz.dx.rop.cst.CstEnumRef;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.ByteArrayAnnotatedOutput;

public final class DexFile {
    private final MixedItemSection byteData = new MixedItemSection("byte_data", this, 1, MixedItemSection.SortType.TYPE);
    private final MixedItemSection classData = new MixedItemSection(null, this, 1, MixedItemSection.SortType.NONE);
    private final ClassDefsSection classDefs = new ClassDefsSection(this);
    private DexOptions dexOptions;
    private int dumpWidth = 79;
    private final FieldIdsSection fieldIds = new FieldIdsSection(this);
    private int fileSize = -1;
    private final HeaderSection header = new HeaderSection(this);
    private final MixedItemSection map = new MixedItemSection("map", this, 4, MixedItemSection.SortType.NONE);
    private final MethodIdsSection methodIds = new MethodIdsSection(this);
    private final ProtoIdsSection protoIds = new ProtoIdsSection(this);
    private final Section[] sections = {this.header, this.stringIds, this.typeIds, this.protoIds, this.fieldIds, this.methodIds, this.classDefs, this.wordData, this.typeLists, this.stringData, this.byteData, this.classData, this.map};
    private final MixedItemSection stringData = new MixedItemSection("string_data", this, 1, MixedItemSection.SortType.INSTANCE);
    private final StringIdsSection stringIds = new StringIdsSection(this);
    private final TypeIdsSection typeIds = new TypeIdsSection(this);
    private final MixedItemSection typeLists = new MixedItemSection(null, this, 4, MixedItemSection.SortType.NONE);
    private final MixedItemSection wordData = new MixedItemSection("word_data", this, 4, MixedItemSection.SortType.TYPE);

    public DexFile(DexOptions dexOptions2) {
        this.dexOptions = dexOptions2;
    }

    public boolean isEmpty() {
        return this.classDefs.items().isEmpty();
    }

    public DexOptions getDexOptions() {
        return this.dexOptions;
    }

    public void add(ClassDefItem classDefItem) {
        this.classDefs.add(classDefItem);
    }

    public ClassDefItem getClassOrNull(String str) {
        try {
            return (ClassDefItem) this.classDefs.get(new CstType(Type.internClassName(str)));
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public void writeTo(OutputStream outputStream, Writer writer, boolean z) throws IOException {
        boolean z2 = writer != null;
        ByteArrayAnnotatedOutput dex0 = toDex0(z2, z);
        if (outputStream != null) {
            outputStream.write(dex0.getArray());
        }
        if (z2) {
            dex0.writeAnnotationsTo(writer);
        }
    }

    public byte[] toDex(Writer writer, boolean z) throws IOException {
        boolean z2 = writer != null;
        ByteArrayAnnotatedOutput dex0 = toDex0(z2, z);
        if (z2) {
            dex0.writeAnnotationsTo(writer);
        }
        return dex0.getArray();
    }

    public void setDumpWidth(int i) {
        if (i < 40) {
            throw new IllegalArgumentException("dumpWidth < 40");
        }
        this.dumpWidth = i;
    }

    public int getFileSize() {
        if (this.fileSize >= 0) {
            return this.fileSize;
        }
        throw new RuntimeException("file size not yet known");
    }

    MixedItemSection getStringData() {
        return this.stringData;
    }

    MixedItemSection getWordData() {
        return this.wordData;
    }

    MixedItemSection getTypeLists() {
        return this.typeLists;
    }

    MixedItemSection getMap() {
        return this.map;
    }

    StringIdsSection getStringIds() {
        return this.stringIds;
    }

    public ClassDefsSection getClassDefs() {
        return this.classDefs;
    }

    MixedItemSection getClassData() {
        return this.classData;
    }

    public TypeIdsSection getTypeIds() {
        return this.typeIds;
    }

    ProtoIdsSection getProtoIds() {
        return this.protoIds;
    }

    public FieldIdsSection getFieldIds() {
        return this.fieldIds;
    }

    public MethodIdsSection getMethodIds() {
        return this.methodIds;
    }

    MixedItemSection getByteData() {
        return this.byteData;
    }

    Section getFirstDataSection() {
        return this.wordData;
    }

    Section getLastDataSection() {
        return this.map;
    }

    void internIfAppropriate(Constant constant) {
        if (constant instanceof CstString) {
            this.stringIds.intern((CstString) constant);
        } else if (constant instanceof CstType) {
            this.typeIds.intern((CstType) constant);
        } else if (constant instanceof CstBaseMethodRef) {
            this.methodIds.intern((CstBaseMethodRef) constant);
        } else if (constant instanceof CstFieldRef) {
            this.fieldIds.intern((CstFieldRef) constant);
        } else if (constant instanceof CstEnumRef) {
            this.fieldIds.intern(((CstEnumRef) constant).getFieldRef());
        } else if (constant == null) {
            throw new NullPointerException("cst == null");
        }
    }

    IndexedItem findItemOrNull(Constant constant) {
        if (constant instanceof CstString) {
            return this.stringIds.get(constant);
        }
        if (constant instanceof CstType) {
            return this.typeIds.get(constant);
        }
        if (constant instanceof CstBaseMethodRef) {
            return this.methodIds.get(constant);
        }
        if (constant instanceof CstFieldRef) {
            return this.fieldIds.get(constant);
        }
        return null;
    }

    private ByteArrayAnnotatedOutput toDex0(boolean z, boolean z2) {
        ExceptionWithContext exceptionWithContext;
        this.classDefs.prepare();
        this.classData.prepare();
        this.wordData.prepare();
        this.byteData.prepare();
        this.methodIds.prepare();
        this.fieldIds.prepare();
        this.protoIds.prepare();
        this.typeLists.prepare();
        this.typeIds.prepare();
        this.stringIds.prepare();
        this.stringData.prepare();
        this.header.prepare();
        int length = this.sections.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            Section section = this.sections[i];
            int fileOffset = section.setFileOffset(i2);
            if (fileOffset < i2) {
                throw new RuntimeException("bogus placement for section " + i);
            }
            try {
                if (section == this.map) {
                    MapItem.addMap(this.sections, this.map);
                    this.map.prepare();
                }
                if (section instanceof MixedItemSection) {
                    ((MixedItemSection) section).placeItems();
                }
                i++;
                i2 = fileOffset + section.writeSize();
            } catch (RuntimeException e) {
                throw ExceptionWithContext.withContext(e, "...while writing section " + i);
            }
        }
        this.fileSize = i2;
        byte[] bArr = new byte[this.fileSize];
        ByteArrayAnnotatedOutput byteArrayAnnotatedOutput = new ByteArrayAnnotatedOutput(bArr);
        if (z) {
            byteArrayAnnotatedOutput.enableAnnotations(this.dumpWidth, z2);
        }
        for (int i3 = 0; i3 < length; i3++) {
            try {
                Section section2 = this.sections[i3];
                int fileOffset2 = section2.getFileOffset() - byteArrayAnnotatedOutput.getCursor();
                if (fileOffset2 < 0) {
                    throw new ExceptionWithContext("excess write of " + (-fileOffset2));
                }
                byteArrayAnnotatedOutput.writeZeroes(section2.getFileOffset() - byteArrayAnnotatedOutput.getCursor());
                section2.writeTo(byteArrayAnnotatedOutput);
            } catch (RuntimeException e2) {
                if (e2 instanceof ExceptionWithContext) {
                    exceptionWithContext = (ExceptionWithContext) e2;
                } else {
                    exceptionWithContext = new ExceptionWithContext(e2);
                }
                exceptionWithContext.addContext("...while writing section " + i3);
                throw exceptionWithContext;
            }
        }
        if (byteArrayAnnotatedOutput.getCursor() != this.fileSize) {
            throw new RuntimeException("foreshortened write");
        }
        calcSignature(bArr);
        calcChecksum(bArr);
        if (z) {
            this.wordData.writeIndexAnnotation(byteArrayAnnotatedOutput, ItemType.TYPE_CODE_ITEM, "\nmethod code index:\n\n");
            getStatistics().writeAnnotation(byteArrayAnnotatedOutput);
            byteArrayAnnotatedOutput.finishAnnotating();
        }
        return byteArrayAnnotatedOutput;
    }

    public Statistics getStatistics() {
        Statistics statistics = new Statistics();
        for (Section section : this.sections) {
            statistics.addAll(section);
        }
        return statistics;
    }

    private static void calcSignature(byte[] bArr) {
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(bArr, 32, bArr.length - 32);
            try {
                int digest = instance.digest(bArr, 12, 20);
                if (digest != 20) {
                    throw new RuntimeException("unexpected digest write: " + digest + " bytes");
                }
            } catch (DigestException e) {
                throw new RuntimeException(e);
            }
        } catch (NoSuchAlgorithmException e2) {
            throw new RuntimeException(e2);
        }
    }

    private static void calcChecksum(byte[] bArr) {
        Adler32 adler32 = new Adler32();
        adler32.update(bArr, 12, bArr.length - 12);
        int value = (int) adler32.getValue();
        bArr[8] = (byte) value;
        bArr[9] = (byte) (value >> 8);
        bArr[10] = (byte) (value >> 16);
        bArr[11] = (byte) (value >> 24);
    }
}
