package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.MutabilityException;

public final class AttExceptions extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "Exceptions";
    private final TypeList exceptions;

    public AttExceptions(TypeList typeList) {
        super(ATTRIBUTE_NAME);
        try {
            if (typeList.isMutable()) {
                throw new MutabilityException("exceptions.isMutable()");
            }
            this.exceptions = typeList;
        } catch (NullPointerException e) {
            throw new NullPointerException("exceptions == null");
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Attribute
    public int byteLength() {
        return (this.exceptions.size() * 2) + 8;
    }

    public TypeList getExceptions() {
        return this.exceptions;
    }
}
