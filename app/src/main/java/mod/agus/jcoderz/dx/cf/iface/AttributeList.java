package mod.agus.jcoderz.dx.cf.iface;

public interface AttributeList {
    int byteLength();

    Attribute findFirst(String str);

    Attribute findNext(Attribute attribute);

    Attribute get(int i);

    boolean isMutable();

    int size();
}
