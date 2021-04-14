package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.cf.code.ByteCatchList;
import mod.agus.jcoderz.dx.cf.code.BytecodeArray;
import mod.agus.jcoderz.dx.cf.iface.AttributeList;
import mod.agus.jcoderz.dx.util.MutabilityException;

public final class AttCode extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "Code";
    private final AttributeList attributes;
    private final ByteCatchList catches;
    private final BytecodeArray code;
    private final int maxLocals;
    private final int maxStack;

    public AttCode(int i, int i2, BytecodeArray bytecodeArray, ByteCatchList byteCatchList, AttributeList attributeList) {
        super(ATTRIBUTE_NAME);
        if (i < 0) {
            throw new IllegalArgumentException("maxStack < 0");
        } else if (i2 < 0) {
            throw new IllegalArgumentException("maxLocals < 0");
        } else if (bytecodeArray == null) {
            throw new NullPointerException("code == null");
        } else {
            try {
                if (byteCatchList.isMutable()) {
                    throw new MutabilityException("catches.isMutable()");
                }
                try {
                    if (attributeList.isMutable()) {
                        throw new MutabilityException("attributes.isMutable()");
                    }
                    this.maxStack = i;
                    this.maxLocals = i2;
                    this.code = bytecodeArray;
                    this.catches = byteCatchList;
                    this.attributes = attributeList;
                } catch (NullPointerException e) {
                    throw new NullPointerException("attributes == null");
                }
            } catch (NullPointerException e2) {
                throw new NullPointerException("catches == null");
            }
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Attribute
    public int byteLength() {
        return this.code.byteLength() + 10 + this.catches.byteLength() + this.attributes.byteLength();
    }

    public int getMaxStack() {
        return this.maxStack;
    }

    public int getMaxLocals() {
        return this.maxLocals;
    }

    public BytecodeArray getCode() {
        return this.code;
    }

    public ByteCatchList getCatches() {
        return this.catches;
    }

    public AttributeList getAttributes() {
        return this.attributes;
    }
}
