package mod.agus.jcoderz.dex;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UTFDataFormatException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.stream.Stream;
import java.util.zip.Adler32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import mod.agus.jcoderz.dex.ClassData;
import mod.agus.jcoderz.dex.Code;
import mod.agus.jcoderz.dex.util.ByteInput;
import mod.agus.jcoderz.dex.util.ByteOutput;
import mod.agus.jcoderz.dex.util.FileUtils;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;

public final class Dex {
    private static final int CHECKSUM_OFFSET = 8;
    private static final int CHECKSUM_SIZE = 4;
    static final short[] EMPTY_SHORT_ARRAY = new short[0];
    private static final int SIGNATURE_OFFSET = 12;
    private static final int SIGNATURE_SIZE = 20;
    private ByteBuffer data;
    private final FieldIdTable fieldIds;
    private final MethodIdTable methodIds;
    private int nextSectionStart;
    private final ProtoIdTable protoIds;
    private final StringTable strings;
    private final TableOfContents tableOfContents;
    private final TypeIndexToDescriptorIndexTable typeIds;
    private final TypeIndexToDescriptorTable typeNames;

    public Dex(byte[] bArr) throws IOException {
        this(ByteBuffer.wrap(bArr));
    }

    private Dex(ByteBuffer byteBuffer) throws IOException {
        this.tableOfContents = new TableOfContents();
        this.nextSectionStart = 0;
        this.strings = new StringTable(this, null);
        this.typeIds = new TypeIndexToDescriptorIndexTable(this, null);
        this.typeNames = new TypeIndexToDescriptorTable(this, null);
        this.protoIds = new ProtoIdTable(this, null);
        this.fieldIds = new FieldIdTable(this, null);
        this.methodIds = new MethodIdTable(this, null);
        this.data = byteBuffer;
        this.data.order(ByteOrder.LITTLE_ENDIAN);
        this.tableOfContents.readFrom(this);
    }

    public Dex(int i) throws IOException {
        this.tableOfContents = new TableOfContents();
        this.nextSectionStart = 0;
        this.strings = new StringTable(this, null);
        this.typeIds = new TypeIndexToDescriptorIndexTable(this, null);
        this.typeNames = new TypeIndexToDescriptorTable(this, null);
        this.protoIds = new ProtoIdTable(this, null);
        this.fieldIds = new FieldIdTable(this, null);
        this.methodIds = new MethodIdTable(this, null);
        this.data = ByteBuffer.wrap(new byte[i]);
        this.data.order(ByteOrder.LITTLE_ENDIAN);
    }

    public Dex(InputStream inputStream) throws IOException {
        this.tableOfContents = new TableOfContents();
        this.nextSectionStart = 0;
        this.strings = new StringTable(this, null);
        this.typeIds = new TypeIndexToDescriptorIndexTable(this, null);
        this.typeNames = new TypeIndexToDescriptorTable(this, null);
        this.protoIds = new ProtoIdTable(this, null);
        this.fieldIds = new FieldIdTable(this, null);
        this.methodIds = new MethodIdTable(this, null);
        loadFrom(inputStream);
    }

    public Dex(File file) throws IOException {
        this.tableOfContents = new TableOfContents();
        this.nextSectionStart = 0;
        this.strings = new StringTable(this, null);
        this.typeIds = new TypeIndexToDescriptorIndexTable(this, null);
        this.typeNames = new TypeIndexToDescriptorTable(this, null);
        this.protoIds = new ProtoIdTable(this, null);
        this.fieldIds = new FieldIdTable(this, null);
        this.methodIds = new MethodIdTable(this, null);
        if (FileUtils.hasArchiveSuffix(file.getName())) {
            ZipFile zipFile = new ZipFile(file);
            ZipEntry entry = zipFile.getEntry(DexFormat.DEX_IN_JAR_NAME);
            if (entry != null) {
                loadFrom(zipFile.getInputStream(entry));
                zipFile.close();
                return;
            }
            throw new DexException("Expected classes.dex in " + file);
        } else if (file.getName().endsWith(".dex")) {
            loadFrom(new FileInputStream(file));
        } else {
            throw new DexException("unknown output extension: " + file);
        }
    }

    public static Dex create(ByteBuffer byteBuffer) throws IOException {
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        if (byteBuffer.get(0) == 100 && byteBuffer.get(1) == 101 && byteBuffer.get(2) == 121 && byteBuffer.get(3) == 10) {
            byteBuffer.position(8);
            int i = byteBuffer.getInt();
            int i2 = byteBuffer.getInt();
            byteBuffer.position(i);
            byteBuffer.limit(i + i2);
            byteBuffer = byteBuffer.slice();
        }
        return new Dex(byteBuffer);
    }

    private void loadFrom(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[8192];
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                inputStream.close();
                this.data = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
                this.data.order(ByteOrder.LITTLE_ENDIAN);
                this.tableOfContents.readFrom(this);
                return;
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }

    /* access modifiers changed from: private */
    public static void checkBounds(int i, int i2) {
        if (i < 0 || i >= i2) {
            throw new IndexOutOfBoundsException("index:" + i + ", length=" + i2);
        }
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        ByteBuffer duplicate = this.data.duplicate();
        duplicate.clear();
        while (duplicate.hasRemaining()) {
            int min = Math.min(bArr.length, duplicate.remaining());
            duplicate.get(bArr, 0, min);
            outputStream.write(bArr, 0, min);
        }
    }

    public void writeTo(File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        writeTo(fileOutputStream);
        fileOutputStream.close();
    }

    public TableOfContents getTableOfContents() {
        return this.tableOfContents;
    }

    public Section open(int i) {
        if (i < 0 || i >= this.data.capacity()) {
            throw new IllegalArgumentException("position=" + i + " length=" + this.data.capacity());
        }
        ByteBuffer duplicate = this.data.duplicate();
        duplicate.order(ByteOrder.LITTLE_ENDIAN);
        duplicate.position(i);
        duplicate.limit(this.data.capacity());
        return new Section(this, "section", duplicate, null);
    }

    public Section appendSection(int i, String str) {
        if ((i & 3) != 0) {
            throw new IllegalStateException("Not four byte aligned!");
        }
        int i2 = this.nextSectionStart + i;
        ByteBuffer duplicate = this.data.duplicate();
        duplicate.order(ByteOrder.LITTLE_ENDIAN);
        duplicate.position(this.nextSectionStart);
        duplicate.limit(i2);
        Section section = new Section(this, str, duplicate, null);
        this.nextSectionStart = i2;
        return section;
    }

    public int getLength() {
        return this.data.capacity();
    }

    public int getNextSectionStart() {
        return this.nextSectionStart;
    }

    public byte[] getBytes() {
        ByteBuffer duplicate = this.data.duplicate();
        byte[] bArr = new byte[duplicate.capacity()];
        duplicate.position(0);
        duplicate.get(bArr);
        return bArr;
    }

    public List<String> strings() {
        return this.strings;
    }

    public List<Integer> typeIds() {
        return this.typeIds;
    }

    public List<String> typeNames() {
        return this.typeNames;
    }

    public List<ProtoId> protoIds() {
        return this.protoIds;
    }

    public List<FieldId> fieldIds() {
        return this.fieldIds;
    }

    public List<MethodId> methodIds() {
        return this.methodIds;
    }

    public Iterable<ClassDef> classDefs() {
        return new ClassDefIterable(this, null);
    }

    public TypeList readTypeList(int i) {
        if (i == 0) {
            return TypeList.EMPTY;
        }
        return open(i).readTypeList();
    }

    public ClassData readClassData(ClassDef classDef) {
        int classDataOffset = classDef.getClassDataOffset();
        if (classDataOffset != 0) {
            return open(classDataOffset).readClassData();
        }
        throw new IllegalArgumentException("offset == 0");
    }

    public Code readCode(ClassData.Method method) {
        int codeOffset = method.getCodeOffset();
        if (codeOffset != 0) {
            return open(codeOffset).readCode();
        }
        throw new IllegalArgumentException("offset == 0");
    }

    public byte[] computeSignature() throws IOException {
        try {
            MessageDigest instance = MessageDigest.getInstance(McElieceCCA2KeyGenParameterSpec.SHA1);
            byte[] bArr = new byte[8192];
            ByteBuffer duplicate = this.data.duplicate();
            duplicate.limit(duplicate.capacity());
            duplicate.position(32);
            while (duplicate.hasRemaining()) {
                int min = Math.min(bArr.length, duplicate.remaining());
                duplicate.get(bArr, 0, min);
                instance.update(bArr, 0, min);
            }
            return instance.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError();
        }
    }

    public int computeChecksum() throws IOException {
        Adler32 adler32 = new Adler32();
        byte[] bArr = new byte[8192];
        ByteBuffer duplicate = this.data.duplicate();
        duplicate.limit(duplicate.capacity());
        duplicate.position(12);
        while (duplicate.hasRemaining()) {
            int min = Math.min(bArr.length, duplicate.remaining());
            duplicate.get(bArr, 0, min);
            adler32.update(bArr, 0, min);
        }
        return (int) adler32.getValue();
    }

    public void writeHashes() throws IOException {
        open(12).write(computeSignature());
        open(8).writeInt(computeChecksum());
    }

    public int nameIndexFromFieldIndex(int i) {
        checkBounds(i, this.tableOfContents.fieldIds.size);
        return this.data.getInt(this.tableOfContents.fieldIds.off + (i * 8) + 2 + 2);
    }

    public int findStringIndex(String str) {
        return Collections.binarySearch(this.strings, str);
    }

    public int findTypeIndex(String str) {
        return Collections.binarySearch(this.typeNames, str);
    }

    public int findFieldIndex(FieldId fieldId) {
        return Collections.binarySearch(this.fieldIds, fieldId);
    }

    public int findMethodIndex(MethodId methodId) {
        return Collections.binarySearch(this.methodIds, methodId);
    }

    public int findClassDefIndexFromTypeIndex(int i) {
        checkBounds(i, this.tableOfContents.typeIds.size);
        if (!this.tableOfContents.classDefs.exists()) {
            return -1;
        }
        for (int i2 = 0; i2 < this.tableOfContents.classDefs.size; i2++) {
            if (typeIndexFromClassDefIndex(i2) == i) {
                return i2;
            }
        }
        return -1;
    }

    public int typeIndexFromFieldIndex(int i) {
        checkBounds(i, this.tableOfContents.fieldIds.size);
        return this.data.getShort(this.tableOfContents.fieldIds.off + (i * 8) + 2) & 65535;
    }

    public int declaringClassIndexFromMethodIndex(int i) {
        checkBounds(i, this.tableOfContents.methodIds.size);
        return this.data.getShort(this.tableOfContents.methodIds.off + (i * 8)) & 65535;
    }

    public int nameIndexFromMethodIndex(int i) {
        checkBounds(i, this.tableOfContents.methodIds.size);
        return this.data.getInt(this.tableOfContents.methodIds.off + (i * 8) + 2 + 2);
    }

    public short[] parameterTypeIndicesFromMethodIndex(int i) {
        checkBounds(i, this.tableOfContents.methodIds.size);
        int i2 = this.data.getShort(this.tableOfContents.methodIds.off + (i * 8) + 2) & 65535;
        checkBounds(i2, this.tableOfContents.protoIds.size);
        ByteBuffer byteBuffer = this.data;
        int i3 = byteBuffer.getInt((i2 * 12) + this.tableOfContents.protoIds.off + 4 + 4);
        if (i3 == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        int i4 = this.data.getInt(i3);
        if (i4 <= 0) {
            throw new AssertionError("Unexpected parameter type list size: " + i4);
        }
        int i5 = i3 + 4;
        short[] sArr = new short[i4];
        for (int i6 = 0; i6 < i4; i6++) {
            sArr[i6] = this.data.getShort(i5);
            i5 += 2;
        }
        return sArr;
    }

    public int returnTypeIndexFromMethodIndex(int i) {
        checkBounds(i, this.tableOfContents.methodIds.size);
        int i2 = this.data.getShort(this.tableOfContents.methodIds.off + (i * 8) + 2) & 65535;
        checkBounds(i2, this.tableOfContents.protoIds.size);
        int i3 = i2 * 12;
        return this.data.getInt(i3 + this.tableOfContents.protoIds.off + 4);
    }

    public int descriptorIndexFromTypeIndex(int i) {
        checkBounds(i, this.tableOfContents.typeIds.size);
        return this.data.getInt(this.tableOfContents.typeIds.off + (i * 4));
    }

    public int typeIndexFromClassDefIndex(int i) {
        checkBounds(i, this.tableOfContents.classDefs.size);
        return this.data.getInt(this.tableOfContents.classDefs.off + (i * 32));
    }

    public int annotationDirectoryOffsetFromClassDefIndex(int i) {
        checkBounds(i, this.tableOfContents.classDefs.size);
        return this.data.getInt(this.tableOfContents.classDefs.off + (i * 32) + 4 + 4 + 4 + 4 + 4);
    }

    public short[] interfaceTypeIndicesFromClassDefIndex(int i) {
        checkBounds(i, this.tableOfContents.classDefs.size);
        int i2 = this.data.getInt(this.tableOfContents.classDefs.off + (i * 32) + 4 + 4 + 4);
        if (i2 == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        int i3 = this.data.getInt(i2);
        if (i3 <= 0) {
            throw new AssertionError("Unexpected interfaces list size: " + i3);
        }
        int i4 = i2 + 4;
        short[] sArr = new short[i3];
        for (int i5 = 0; i5 < i3; i5++) {
            sArr[i5] = this.data.getShort(i4);
            i4 += 2;
        }
        return sArr;
    }

    public final class Section implements ByteInput, ByteOutput {
        private final ByteBuffer data;
        private final int initialPosition;
        private final String name;

        private Section(String str, ByteBuffer byteBuffer) {
            this.name = str;
            this.data = byteBuffer;
            this.initialPosition = byteBuffer.position();
        }

        Section(Dex dex, String str, ByteBuffer byteBuffer, Section section) {
            this(str, byteBuffer);
        }

        public int getPosition() {
            return this.data.position();
        }

        public int readInt() {
            return this.data.getInt();
        }

        public short readShort() {
            return this.data.getShort();
        }

        public int readUnsignedShort() {
            return readShort() & 65535;
        }

        @Override // mod.agus.jcoderz.dex.util.ByteInput
        public byte readByte() {
            return this.data.get();
        }

        public byte[] readByteArray(int i) {
            byte[] bArr = new byte[i];
            this.data.get(bArr);
            return bArr;
        }

        public short[] readShortArray(int i) {
            if (i == 0) {
                return Dex.EMPTY_SHORT_ARRAY;
            }
            short[] sArr = new short[i];
            for (int i2 = 0; i2 < i; i2++) {
                sArr[i2] = readShort();
            }
            return sArr;
        }

        public int readUleb128() {
            return Leb128.readUnsignedLeb128(this);
        }

        public int readUleb128p1() {
            return Leb128.readUnsignedLeb128(this) - 1;
        }

        public int readSleb128() {
            return Leb128.readSignedLeb128(this);
        }

        public void writeUleb128p1(int i) {
            writeUleb128(i + 1);
        }

        public TypeList readTypeList() {
            short[] readShortArray = readShortArray(readInt());
            alignToFourBytes();
            return new TypeList(Dex.this, readShortArray);
        }

        public String readString() {
            int readInt = readInt();
            int position = this.data.position();
            int limit = this.data.limit();
            this.data.position(readInt);
            this.data.limit(this.data.capacity());
            try {
                int readUleb128 = readUleb128();
                String decode = Mutf8.decode(this, new char[readUleb128]);
                if (decode.length() != readUleb128) {
                    throw new DexException("Declared length " + readUleb128 + " doesn't match decoded length of " + decode.length());
                }
                this.data.position(position);
                this.data.limit(limit);
                return decode;
            } catch (UTFDataFormatException e) {
                throw new DexException(e);
            } catch (Throwable th) {
                this.data.position(position);
                this.data.limit(limit);
                throw th;
            }
        }

        public FieldId readFieldId() {
            return new FieldId(Dex.this, readUnsignedShort(), readUnsignedShort(), readInt());
        }

        public MethodId readMethodId() {
            return new MethodId(Dex.this, readUnsignedShort(), readUnsignedShort(), readInt());
        }

        public ProtoId readProtoId() {
            return new ProtoId(Dex.this, readInt(), readInt(), readInt());
        }

        public ClassDef readClassDef() {
            return new ClassDef(Dex.this, getPosition(), readInt(), readInt(), readInt(), readInt(), readInt(), readInt(), readInt(), readInt());
        }


        private Code readCode() {
            Code.Try[] tryArr;
            Code.CatchHandler[] catchHandlerArr;
            int readUnsignedShort = readUnsignedShort();
            int readUnsignedShort2 = readUnsignedShort();
            int readUnsignedShort3 = readUnsignedShort();
            int readUnsignedShort4 = readUnsignedShort();
            int readInt = readInt();
            short[] readShortArray = readShortArray(readInt());
            if (readUnsignedShort4 > 0) {
                if (readShortArray.length % 2 == 1) {
                    readShort();
                }
                Section open = Dex.this.open(this.data.position());
                skip(readUnsignedShort4 * 8);
                catchHandlerArr = readCatchHandlers();
                tryArr = open.readTries(readUnsignedShort4, catchHandlerArr);
            } else {
                tryArr = new Code.Try[0];
                catchHandlerArr = new Code.CatchHandler[0];
            }
            return new Code(readUnsignedShort, readUnsignedShort2, readUnsignedShort3, readInt, readShortArray, tryArr, catchHandlerArr);
        }

        private Code.CatchHandler[] readCatchHandlers() {
            int position = this.data.position();
            int readUleb128 = readUleb128();
            Code.CatchHandler[] catchHandlerArr = new Code.CatchHandler[readUleb128];
            for (int i = 0; i < readUleb128; i++) {
                catchHandlerArr[i] = readCatchHandler(this.data.position() - position);
            }
            return catchHandlerArr;
        }

        private Code.Try[] readTries(int i, Code.CatchHandler[] catchHandlerArr) {
            Code.Try[] tryArr = new Code.Try[i];
            for (int i2 = 0; i2 < i; i2++) {
                tryArr[i2] = new Code.Try(readInt(), readUnsignedShort(), findCatchHandlerIndex(catchHandlerArr, readUnsignedShort()));
            }
            return tryArr;
        }

        private int findCatchHandlerIndex(Code.CatchHandler[] catchHandlerArr, int i) {
            for (int i2 = 0; i2 < catchHandlerArr.length; i2++) {
                if (catchHandlerArr[i2].getOffset() == i) {
                    return i2;
                }
            }
            throw new IllegalArgumentException();
        }

        private Code.CatchHandler readCatchHandler(int i) {
            int readSleb128 = readSleb128();
            int abs = Math.abs(readSleb128);
            int[] iArr = new int[abs];
            int[] iArr2 = new int[abs];
            for (int i2 = 0; i2 < abs; i2++) {
                iArr[i2] = readUleb128();
                iArr2[i2] = readUleb128();
            }
            return new Code.CatchHandler(iArr, iArr2, readSleb128 <= 0 ? readUleb128() : -1, i);
        }


        private ClassData readClassData() {
            return new ClassData(readFields(readUleb128()), readFields(readUleb128()), readMethods(readUleb128()), readMethods(readUleb128()));
        }

        private ClassData.Field[] readFields(int i) {
            ClassData.Field[] fieldArr = new ClassData.Field[i];
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                i2 += readUleb128();
                fieldArr[i3] = new ClassData.Field(i2, readUleb128());
            }
            return fieldArr;
        }

        private ClassData.Method[] readMethods(int i) {
            ClassData.Method[] methodArr = new ClassData.Method[i];
            int i2 = 0;
            for (int i3 = 0; i3 < i; i3++) {
                i2 += readUleb128();
                methodArr[i3] = new ClassData.Method(i2, readUleb128(), readUleb128());
            }
            return methodArr;
        }

        private byte[] getBytesFrom(int i) {
            byte[] bArr = new byte[(this.data.position() - i)];
            this.data.position(i);
            this.data.get(bArr);
            return bArr;
        }

        public Annotation readAnnotation() {
            byte readByte = readByte();
            int position = this.data.position();
            new EncodedValueReader(this, 29).skipValue();
            return new Annotation(Dex.this, readByte, new EncodedValue(getBytesFrom(position)));
        }

        public EncodedValue readEncodedArray() {
            int position = this.data.position();
            new EncodedValueReader(this, 28).skipValue();
            return new EncodedValue(getBytesFrom(position));
        }

        public void skip(int i) {
            if (i < 0) {
                throw new IllegalArgumentException();
            }
            this.data.position(this.data.position() + i);
        }

        public void alignToFourBytes() {
            this.data.position((this.data.position() + 3) & -4);
        }

        public void alignToFourBytesWithZeroFill() {
            while ((this.data.position() & 3) != 0) {
                this.data.put((byte) 0);
            }
        }

        public void assertFourByteAligned() {
            if ((this.data.position() & 3) != 0) {
                throw new IllegalStateException("Not four byte aligned!");
            }
        }

        public void write(byte[] bArr) {
            this.data.put(bArr);
        }

        @Override // mod.agus.jcoderz.dex.util.ByteOutput
        public void writeByte(int i) {
            this.data.put((byte) i);
        }

        public void writeShort(short s) {
            this.data.putShort(s);
        }

        public void writeUnsignedShort(int i) {
            short s = (short) i;
            if (i != (65535 & s)) {
                throw new IllegalArgumentException("Expected an unsigned short: " + i);
            }
            writeShort(s);
        }

        public void write(short[] sArr) {
            for (short s : sArr) {
                writeShort(s);
            }
        }

        public void writeInt(int i) {
            this.data.putInt(i);
        }

        public void writeUleb128(int i) {
            try {
                Leb128.writeUnsignedLeb128(this, i);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DexException("Section limit " + this.data.limit() + " exceeded by " + this.name);
            }
        }

        public void writeSleb128(int i) {
            try {
                Leb128.writeSignedLeb128(this, i);
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new DexException("Section limit " + this.data.limit() + " exceeded by " + this.name);
            }
        }

        public void writeStringData(String str) {
            try {
                writeUleb128(str.length());
                write(Mutf8.encode(str));
                writeByte(0);
            } catch (UTFDataFormatException e) {
                throw new AssertionError();
            }
        }

        public void writeTypeList(TypeList typeList) {
            short[] types = typeList.getTypes();
            writeInt(types.length);
            for (short s : types) {
                writeShort(s);
            }
            alignToFourBytesWithZeroFill();
        }

        public int remaining() {
            return this.data.remaining();
        }

        public int used() {
            return this.data.position() - this.initialPosition;
        }
    }

    public final class StringTable extends AbstractList<String> implements RandomAccess {
        private StringTable() {
        }

        StringTable(Dex dex, StringTable stringTable) {
            this();
        }

        @Override // java.util.List, java.util.Collection, java.lang.Iterable
        public Spliterator<String> spliterator() {
            return null;
        }

        @Override // java.util.Collection
        public Stream<String> stream() {
            return null;
        }

        @Override // java.util.Collection
        public Stream<String> parallelStream() {
            return null;
        }

        @Override // java.util.List, java.util.AbstractList
        public String get(int i) {
            Dex.checkBounds(i, Dex.this.tableOfContents.stringIds.size);
            return Dex.this.open(Dex.this.tableOfContents.stringIds.off + (i * 4)).readString();
        }

        public int size() {
            return Dex.this.tableOfContents.stringIds.size;
        }
    }

    private final class TypeIndexToDescriptorIndexTable extends AbstractList<Integer> implements RandomAccess {
        private TypeIndexToDescriptorIndexTable() {
        }

        /* synthetic */ TypeIndexToDescriptorIndexTable(Dex dex, TypeIndexToDescriptorIndexTable typeIndexToDescriptorIndexTable) {
            this();
        }

        @Override // java.util.List, java.util.Collection, java.lang.Iterable
        public Spliterator<Integer> spliterator() {
            return null;
        }

        @Override // java.util.Collection
        public Stream<Integer> stream() {
            return null;
        }

        @Override // java.util.Collection
        public Stream<Integer> parallelStream() {
            return null;
        }

        @Override // java.util.List, java.util.AbstractList
        public Integer get(int i) {
            return Integer.valueOf(Dex.this.descriptorIndexFromTypeIndex(i));
        }

        public int size() {
            return Dex.this.tableOfContents.typeIds.size;
        }
    }

    private final class TypeIndexToDescriptorTable extends AbstractList<String> implements RandomAccess {
        private TypeIndexToDescriptorTable() {
        }

        TypeIndexToDescriptorTable(Dex dex, TypeIndexToDescriptorTable typeIndexToDescriptorTable) {
            this();
        }

        @Override // java.util.List, java.util.Collection, java.lang.Iterable
        public Spliterator<String> spliterator() {
            return null;
        }

        @Override // java.util.Collection
        public Stream<String> stream() {
            return null;
        }

        @Override // java.util.Collection
        public Stream<String> parallelStream() {
            return null;
        }

        @Override // java.util.List, java.util.AbstractList
        public String get(int i) {
            return Dex.this.strings.get(Dex.this.descriptorIndexFromTypeIndex(i));
        }

        public int size() {
            return Dex.this.tableOfContents.typeIds.size;
        }
    }

    private final class ProtoIdTable extends AbstractList<ProtoId> implements RandomAccess {
        private ProtoIdTable() {
        }

        ProtoIdTable(Dex dex, ProtoIdTable protoIdTable) {
            this();
        }

        @Override // java.util.List, java.util.Collection, java.lang.Iterable
        public Spliterator<ProtoId> spliterator() {
            return null;
        }

        @Override // java.util.Collection
        public Stream<ProtoId> stream() {
            return null;
        }

        @Override // java.util.Collection
        public Stream<ProtoId> parallelStream() {
            return null;
        }

        @Override // java.util.List, java.util.AbstractList
        public ProtoId get(int i) {
            Dex.checkBounds(i, Dex.this.tableOfContents.protoIds.size);
            return Dex.this.open(Dex.this.tableOfContents.protoIds.off + (i * 12)).readProtoId();
        }

        public int size() {
            return Dex.this.tableOfContents.protoIds.size;
        }
    }

    private final class FieldIdTable extends AbstractList<FieldId> implements RandomAccess {
        private FieldIdTable() {
        }

        FieldIdTable(Dex dex, FieldIdTable fieldIdTable) {
            this();
        }

        @Override // java.util.List, java.util.Collection, java.lang.Iterable
        public Spliterator<FieldId> spliterator() {
            return null;
        }

        @Override // java.util.Collection
        public Stream<FieldId> stream() {
            return null;
        }

        @Override // java.util.Collection
        public Stream<FieldId> parallelStream() {
            return null;
        }

        @Override // java.util.List, java.util.AbstractList
        public FieldId get(int i) {
            Dex.checkBounds(i, Dex.this.tableOfContents.fieldIds.size);
            return Dex.this.open(Dex.this.tableOfContents.fieldIds.off + (i * 8)).readFieldId();
        }

        public int size() {
            return Dex.this.tableOfContents.fieldIds.size;
        }
    }

    private final class MethodIdTable extends AbstractList<MethodId> implements RandomAccess {
        private MethodIdTable() {
        }

        MethodIdTable(Dex dex, MethodIdTable methodIdTable) {
            this();
        }

        @Override // java.util.List, java.util.Collection, java.lang.Iterable
        public Spliterator<MethodId> spliterator() {
            return null;
        }

        @Override // java.util.Collection
        public Stream<MethodId> stream() {
            return null;
        }

        @Override // java.util.Collection
        public Stream<MethodId> parallelStream() {
            return null;
        }

        @Override // java.util.List, java.util.AbstractList
        public MethodId get(int i) {
            Dex.checkBounds(i, Dex.this.tableOfContents.methodIds.size);
            return Dex.this.open(Dex.this.tableOfContents.methodIds.off + (i * 8)).readMethodId();
        }

        public int size() {
            return Dex.this.tableOfContents.methodIds.size;
        }
    }

    private final class ClassDefIterator implements Iterator<ClassDef> {
        private int count;
        private final Section in;

        private ClassDefIterator() {
            this.in = Dex.this.open(Dex.this.tableOfContents.classDefs.off);
            this.count = 0;
        }

        ClassDefIterator(Dex dex, ClassDefIterator classDefIterator) {
            this();
        }

        public boolean hasNext() {
            return this.count < Dex.this.tableOfContents.classDefs.size;
        }

        @Override // java.util.Iterator
        public ClassDef next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.count++;
            return this.in.readClassDef();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private final class ClassDefIterable implements Iterable {
        private ClassDefIterable() {
        }

        ClassDefIterable(Dex dex, ClassDefIterable classDefIterable) {
            this();
        }

        @Override // java.lang.Iterable
        public Spliterator<ClassDef> spliterator() {
            return null;
        }

        @Override // java.lang.Iterable
        public Iterator<? extends Object> iterator() {
            if (!Dex.this.tableOfContents.classDefs.exists()) {
                return Collections.emptySet().iterator();
            }
            return new ClassDefIterator(Dex.this, null);
        }
    }
}
