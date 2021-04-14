package mod.agus.jcoderz.dx.cf.attrib;

public final class AttDeprecated extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "Deprecated";

    public AttDeprecated() {
        super(ATTRIBUTE_NAME);
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Attribute
    public int byteLength() {
        return 6;
    }
}
