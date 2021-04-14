package mod.agus.jcoderz.dx.cf.attrib;

public final class AttSynthetic extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "Synthetic";

    public AttSynthetic() {
        super(ATTRIBUTE_NAME);
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Attribute
    public int byteLength() {
        return 6;
    }
}
