package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.rop.annotation.Annotations;
import mod.agus.jcoderz.dx.util.MutabilityException;

public abstract class BaseAnnotations extends BaseAttribute {
    private final Annotations annotations;
    private final int byteLength;

    public BaseAnnotations(String str, Annotations annotations2, int i) {
        super(str);
        try {
            if (annotations2.isMutable()) {
                throw new MutabilityException("annotations.isMutable()");
            }
            this.annotations = annotations2;
            this.byteLength = i;
        } catch (NullPointerException e) {
            throw new NullPointerException("annotations == null");
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Attribute
    public final int byteLength() {
        return this.byteLength + 6;
    }

    public final Annotations getAnnotations() {
        return this.annotations;
    }
}
