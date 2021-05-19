package mod.agus.jcoderz.dx.cf.direct;

import mod.agus.jcoderz.dx.cf.iface.Attribute;
import mod.agus.jcoderz.dx.cf.iface.ParseException;
import mod.agus.jcoderz.dx.cf.iface.ParseObserver;
import mod.agus.jcoderz.dx.cf.iface.StdAttributeList;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public final class AttributeListParser {
    private final AttributeFactory attributeFactory;
    private final DirectClassFile cf;
    private final int context;
    private final StdAttributeList list;
    private final int offset;
    private int endOffset;
    private ParseObserver observer;

    public AttributeListParser(DirectClassFile directClassFile, int i, int i2, AttributeFactory attributeFactory2) {
        if (directClassFile == null) {
            throw new NullPointerException("cf == null");
        } else if (attributeFactory2 == null) {
            throw new NullPointerException("attributeFactory == null");
        } else {
            int unsignedShort = directClassFile.getBytes().getUnsignedShort(i2);
            this.cf = directClassFile;
            this.context = i;
            this.offset = i2;
            this.attributeFactory = attributeFactory2;
            this.list = new StdAttributeList(unsignedShort);
            this.endOffset = -1;
        }
    }

    public void setObserver(ParseObserver parseObserver) {
        this.observer = parseObserver;
    }

    public int getEndOffset() {
        parseIfNecessary();
        return this.endOffset;
    }

    public StdAttributeList getList() {
        parseIfNecessary();
        return this.list;
    }

    private void parseIfNecessary() {
        if (this.endOffset < 0) {
            parse();
        }
    }

    private void parse() {
        int size = this.list.size();
        int i = this.offset + 2;
        ByteArray bytes = this.cf.getBytes();
        if (this.observer != null) {
            this.observer.parsed(bytes, this.offset, 2, "attributes_count: " + Hex.u2(size));
        }
        int i2 = i;
        int i3 = 0;
        while (i3 < size) {
            try {
                if (this.observer != null) {
                    this.observer.parsed(bytes, i2, 0, "\nattributes[" + i3 + "]:\n");
                    this.observer.changeIndent(1);
                }
                Attribute parse = this.attributeFactory.parse(this.cf, this.context, i2, this.observer);
                int byteLength = parse.byteLength() + i2;
                this.list.set(i3, parse);
                if (this.observer != null) {
                    this.observer.changeIndent(-1);
                    this.observer.parsed(bytes, byteLength, 0, "end attributes[" + i3 + "]\n");
                }
                i3++;
                i2 = byteLength;
            } catch (ParseException e) {
                e.addContext("...while parsing attributes[" + i3 + "]");
                throw e;
            } catch (RuntimeException e2) {
                ParseException parseException = new ParseException(e2);
                parseException.addContext("...while parsing attributes[" + i3 + "]");
                throw parseException;
            }
        }
        this.endOffset = i2;
    }
}
