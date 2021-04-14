package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.cf.iface.Attribute;

public abstract class BaseAttribute implements Attribute {
    private final String name;

    public BaseAttribute(String str) {
        if (str == null) {
            throw new NullPointerException("name == null");
        }
        this.name = str;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Attribute
    public String getName() {
        return this.name;
    }
}
