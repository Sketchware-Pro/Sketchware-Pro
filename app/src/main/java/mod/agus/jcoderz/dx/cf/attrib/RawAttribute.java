package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.cst.ConstantPool;
import mod.agus.jcoderz.dx.util.ByteArray;

public final class RawAttribute extends BaseAttribute {
    private final ByteArray data;
    private final ConstantPool pool;

    public RawAttribute(String str, ByteArray byteArray, ConstantPool constantPool) {
        super(str);
        if (byteArray == null) {
            throw new NullPointerException("data == null");
        }
        this.data = byteArray;
        this.pool = constantPool;
    }

    public RawAttribute(String str, ByteArray byteArray, int i, int i2, ConstantPool constantPool) {
        this(str, byteArray.slice(i, i + i2), constantPool);
    }

    public ByteArray getData() {
        return this.data;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Attribute
    public int byteLength() {
        return this.data.size() + 6;
    }

    public ConstantPool getPool() {
        return this.pool;
    }
}
