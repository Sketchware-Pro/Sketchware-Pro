package mod.agus.jcoderz.dx.cf.direct;

import org.eclipse.jdt.internal.compiler.util.SuffixConstants;

import mod.agus.jcoderz.dx.cf.attrib.AttSourceFile;
import mod.agus.jcoderz.dx.cf.cst.ConstantPoolParser;
import mod.agus.jcoderz.dx.cf.iface.Attribute;
import mod.agus.jcoderz.dx.cf.iface.AttributeList;
import mod.agus.jcoderz.dx.cf.iface.ClassFile;
import mod.agus.jcoderz.dx.cf.iface.FieldList;
import mod.agus.jcoderz.dx.cf.iface.MethodList;
import mod.agus.jcoderz.dx.cf.iface.ParseException;
import mod.agus.jcoderz.dx.cf.iface.ParseObserver;
import mod.agus.jcoderz.dx.cf.iface.StdAttributeList;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.ConstantPool;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.cst.StdConstantPool;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public class DirectClassFile implements ClassFile {
    private static final int CLASS_FILE_MAGIC = -889275714;
    private static final int CLASS_FILE_MAX_MAJOR_VERSION = 51;
    private static final int CLASS_FILE_MAX_MINOR_VERSION = 0;
    private static final int CLASS_FILE_MIN_MAJOR_VERSION = 45;
    private final ByteArray bytes;
    private final String filePath;
    private final boolean strictParse;
    private int accessFlags;
    private AttributeFactory attributeFactory;
    private StdAttributeList attributes;
    private FieldList fields;
    private TypeList interfaces;
    private MethodList methods;
    private ParseObserver observer;
    private StdConstantPool pool;
    private CstType superClass;
    private CstType thisClass;

    public DirectClassFile(ByteArray byteArray, String str, boolean z) {
        if (byteArray == null) {
            throw new NullPointerException("bytes == null");
        } else if (str == null) {
            throw new NullPointerException("filePath == null");
        } else {
            this.filePath = str;
            this.bytes = byteArray;
            this.strictParse = z;
            this.accessFlags = -1;
        }
    }

    public DirectClassFile(byte[] bArr, String str, boolean z) {
        this(new ByteArray(bArr), str, z);
    }

    public static String stringOrNone(Object obj) {
        if (obj == null) {
            return "(none)";
        }
        return obj.toString();
    }

    public void setObserver(ParseObserver parseObserver) {
        this.observer = parseObserver;
    }

    public void setAttributeFactory(AttributeFactory attributeFactory2) {
        if (attributeFactory2 == null) {
            throw new NullPointerException("attributeFactory == null");
        }
        this.attributeFactory = attributeFactory2;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public ByteArray getBytes() {
        return this.bytes;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ClassFile
    public int getMagic() {
        parseToInterfacesIfNecessary();
        return getMagic0();
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ClassFile
    public int getMinorVersion() {
        parseToInterfacesIfNecessary();
        return getMinorVersion0();
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ClassFile
    public int getMajorVersion() {
        parseToInterfacesIfNecessary();
        return getMajorVersion0();
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ClassFile
    public int getAccessFlags() {
        parseToInterfacesIfNecessary();
        return this.accessFlags;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ClassFile
    public CstType getThisClass() {
        parseToInterfacesIfNecessary();
        return this.thisClass;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ClassFile
    public CstType getSuperclass() {
        parseToInterfacesIfNecessary();
        return this.superClass;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ClassFile
    public ConstantPool getConstantPool() {
        parseToInterfacesIfNecessary();
        return this.pool;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ClassFile
    public TypeList getInterfaces() {
        parseToInterfacesIfNecessary();
        return this.interfaces;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ClassFile
    public FieldList getFields() {
        parseToEndIfNecessary();
        return this.fields;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ClassFile
    public MethodList getMethods() {
        parseToEndIfNecessary();
        return this.methods;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ClassFile, mod.agus.jcoderz.dx.cf.iface.HasAttribute
    public AttributeList getAttributes() {
        parseToEndIfNecessary();
        return this.attributes;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.ClassFile
    public CstString getSourceFile() {
        Attribute findFirst = getAttributes().findFirst(AttSourceFile.ATTRIBUTE_NAME);
        if (findFirst instanceof AttSourceFile) {
            return ((AttSourceFile) findFirst).getSourceFile();
        }
        return null;
    }

    public TypeList makeTypeList(int i, int i2) {
        if (i2 == 0) {
            return StdTypeList.EMPTY;
        }
        if (this.pool != null) {
            return new DcfTypeList(this.bytes, i, i2, this.pool, this.observer);
        }
        throw new IllegalStateException("pool not yet initialized");
    }

    public int getMagic0() {
        return this.bytes.getInt(0);
    }

    public int getMinorVersion0() {
        return this.bytes.getUnsignedShort(4);
    }

    public int getMajorVersion0() {
        return this.bytes.getUnsignedShort(6);
    }

    private void parseToInterfacesIfNecessary() {
        if (this.accessFlags == -1) {
            parse();
        }
    }

    private void parseToEndIfNecessary() {
        if (this.attributes == null) {
            parse();
        }
    }

    private void parse() {
        try {
            parse0();
        } catch (ParseException e) {
            e.addContext("...while parsing " + this.filePath);
            throw e;
        } catch (RuntimeException e2) {
            ParseException parseException = new ParseException(e2);
            parseException.addContext("...while parsing " + this.filePath);
            throw parseException;
        }
    }

    private boolean isGoodVersion(int i, int i2, int i3) {
        if (i == CLASS_FILE_MAGIC && i2 >= 0) {
            if (i3 == 51) {
                return i2 <= 0;
            } else return i3 < 51 && i3 >= 45;
        }
        return false;
    }

    private void parse0() {
        if (this.bytes.size() < 10) {
            throw new ParseException("severely truncated class file");
        }
        if (this.observer != null) {
            this.observer.parsed(this.bytes, 0, 0, "begin classfile");
            this.observer.parsed(this.bytes, 0, 4, "magic: " + Hex.u4(getMagic0()));
            this.observer.parsed(this.bytes, 4, 2, "minor_version: " + Hex.u2(getMinorVersion0()));
            this.observer.parsed(this.bytes, 6, 2, "major_version: " + Hex.u2(getMajorVersion0()));
        }
        if (!this.strictParse || isGoodVersion(getMagic0(), getMinorVersion0(), getMajorVersion0())) {
            ConstantPoolParser constantPoolParser = new ConstantPoolParser(this.bytes);
            constantPoolParser.setObserver(this.observer);
            this.pool = constantPoolParser.getPool();
            this.pool.setImmutable();
            int endOffset = constantPoolParser.getEndOffset();
            int unsignedShort = this.bytes.getUnsignedShort(endOffset);
            this.thisClass = (CstType) this.pool.get(this.bytes.getUnsignedShort(endOffset + 2));
            this.superClass = (CstType) this.pool.get0Ok(this.bytes.getUnsignedShort(endOffset + 4));
            int unsignedShort2 = this.bytes.getUnsignedShort(endOffset + 6);
            if (this.observer != null) {
                this.observer.parsed(this.bytes, endOffset, 2, "access_flags: " + AccessFlags.classString(unsignedShort));
                this.observer.parsed(this.bytes, endOffset + 2, 2, "this_class: " + this.thisClass);
                this.observer.parsed(this.bytes, endOffset + 4, 2, "super_class: " + stringOrNone(this.superClass));
                this.observer.parsed(this.bytes, endOffset + 6, 2, "interfaces_count: " + Hex.u2(unsignedShort2));
                if (unsignedShort2 != 0) {
                    this.observer.parsed(this.bytes, endOffset + 8, 0, "interfaces:");
                }
            }
            int i = endOffset + 8;
            this.interfaces = makeTypeList(i, unsignedShort2);
            int i2 = (unsignedShort2 * 2) + i;
            if (this.strictParse) {
                String className = this.thisClass.getClassType().getClassName();
                if (!this.filePath.endsWith(SuffixConstants.SUFFIX_STRING_class) || !this.filePath.startsWith(className) || this.filePath.length() != className.length() + 6) {
                    throw new ParseException("class name (" + className + ") does not match path (" + this.filePath + ")");
                }
            }
            this.accessFlags = unsignedShort;
            FieldListParser fieldListParser = new FieldListParser(this, this.thisClass, i2, this.attributeFactory);
            fieldListParser.setObserver(this.observer);
            this.fields = fieldListParser.getList();
            MethodListParser methodListParser = new MethodListParser(this, this.thisClass, fieldListParser.getEndOffset(), this.attributeFactory);
            methodListParser.setObserver(this.observer);
            this.methods = methodListParser.getList();
            AttributeListParser attributeListParser = new AttributeListParser(this, 0, methodListParser.getEndOffset(), this.attributeFactory);
            attributeListParser.setObserver(this.observer);
            this.attributes = attributeListParser.getList();
            this.attributes.setImmutable();
            int endOffset2 = attributeListParser.getEndOffset();
            if (endOffset2 != this.bytes.size()) {
                throw new ParseException("extra bytes at end of class file, at offset " + Hex.u4(endOffset2));
            } else if (this.observer != null) {
                this.observer.parsed(this.bytes, endOffset2, 0, "end classfile");
            }
        } else {
            throw new ParseException("bad class file magic (" + Hex.u4(getMagic0()) + ") or version (" + Hex.u2(getMajorVersion0()) + "." + Hex.u2(getMinorVersion0()) + ")");
        }
    }

    public static class DcfTypeList implements TypeList {
        private final ByteArray bytes;
        private final StdConstantPool pool;
        private final int size;

        public DcfTypeList(ByteArray byteArray, int i, int i2, StdConstantPool stdConstantPool, ParseObserver parseObserver) {
            if (i2 < 0) {
                throw new IllegalArgumentException("size < 0");
            }
            ByteArray slice = byteArray.slice(i, (i2 * 2) + i);
            this.bytes = slice;
            this.size = i2;
            this.pool = stdConstantPool;
            for (int i3 = 0; i3 < i2; i3++) {
                int i4 = i3 * 2;
                try {
                    CstType cstType = (CstType) stdConstantPool.get(slice.getUnsignedShort(i4));
                    if (parseObserver != null) {
                        parseObserver.parsed(slice, i4, 2, "  " + cstType);
                    }
                } catch (ClassCastException e) {
                    throw new RuntimeException("bogus class cpi", e);
                }
            }
        }

        @Override // mod.agus.jcoderz.dx.rop.type.TypeList
        public boolean isMutable() {
            return false;
        }

        @Override // mod.agus.jcoderz.dx.rop.type.TypeList
        public int size() {
            return this.size;
        }

        @Override // mod.agus.jcoderz.dx.rop.type.TypeList
        public int getWordCount() {
            return this.size;
        }

        @Override // mod.agus.jcoderz.dx.rop.type.TypeList
        public Type getType(int i) {
            return ((CstType) this.pool.get(this.bytes.getUnsignedShort(i * 2))).getClassType();
        }

        @Override // mod.agus.jcoderz.dx.rop.type.TypeList
        public TypeList withAddedType(Type type) {
            throw new UnsupportedOperationException("unsupported");
        }
    }
}
