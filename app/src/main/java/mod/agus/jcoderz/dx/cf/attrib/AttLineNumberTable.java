package mod.agus.jcoderz.dx.cf.attrib;

import mod.agus.jcoderz.dx.cf.code.LineNumberList;
import mod.agus.jcoderz.dx.util.MutabilityException;

public final class AttLineNumberTable extends BaseAttribute {
    public static final String ATTRIBUTE_NAME = "LineNumberTable";
    private final LineNumberList lineNumbers;

    public AttLineNumberTable(LineNumberList lineNumberList) {
        super(ATTRIBUTE_NAME);
        try {
            if (lineNumberList.isMutable()) {
                throw new MutabilityException("lineNumbers.isMutable()");
            }
            this.lineNumbers = lineNumberList;
        } catch (NullPointerException e) {
            throw new NullPointerException("lineNumbers == null");
        }
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Attribute
    public int byteLength() {
        return (this.lineNumbers.size() * 4) + 8;
    }

    public LineNumberList getLineNumbers() {
        return this.lineNumbers;
    }
}
