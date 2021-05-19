package mod.agus.jcoderz.dx.cf.direct;

import mod.agus.jcoderz.dx.cf.attrib.RawAttribute;
import mod.agus.jcoderz.dx.cf.iface.Attribute;
import mod.agus.jcoderz.dx.cf.iface.ParseException;
import mod.agus.jcoderz.dx.cf.iface.ParseObserver;
import mod.agus.jcoderz.dx.rop.cst.ConstantPool;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public class AttributeFactory {
    public static final int CTX_CLASS = 0;
    public static final int CTX_CODE = 3;
    public static final int CTX_COUNT = 4;
    public static final int CTX_FIELD = 1;
    public static final int CTX_METHOD = 2;

    public final Attribute parse(DirectClassFile directClassFile, int i, int i2, ParseObserver parseObserver) {
        if (directClassFile == null) {
            throw new NullPointerException("cf == null");
        } else if (i < 0 || i >= 4) {
            throw new IllegalArgumentException("bad context");
        } else {
            CstString cstString = null;
            try {
                ByteArray bytes = directClassFile.getBytes();
                ConstantPool constantPool = directClassFile.getConstantPool();
                int unsignedShort = bytes.getUnsignedShort(i2);
                int i3 = bytes.getInt(i2 + 2);
                CstString cstString2 = (CstString) constantPool.get(unsignedShort);
                if (parseObserver != null) {
                    parseObserver.parsed(bytes, i2, 2, "name: " + cstString2.toHuman());
                    parseObserver.parsed(bytes, i2 + 2, 4, "length: " + Hex.u4(i3));
                }
                return parse0(directClassFile, i, cstString2.getString(), i2 + 6, i3, parseObserver);
            } catch (ParseException e) {
                e.addContext("...while parsing " + (0 != 0 ? cstString.toHuman() + " " : "") + "attribute at offset " + Hex.u4(i2));
                throw e;
            }
        }
    }

    public Attribute parse0(DirectClassFile directClassFile, int i, String str, int i2, int i3, ParseObserver parseObserver) {
        ByteArray bytes = directClassFile.getBytes();
        RawAttribute rawAttribute = new RawAttribute(str, bytes, i2, i3, directClassFile.getConstantPool());
        if (parseObserver != null) {
            parseObserver.parsed(bytes, i2, i3, "attribute data");
        }
        return rawAttribute;
    }
}
