package mod.agus.jcoderz.dx.cf.direct;

import mod.agus.jcoderz.dx.cf.iface.AttributeList;
import mod.agus.jcoderz.dx.cf.iface.Member;
import mod.agus.jcoderz.dx.cf.iface.ParseException;
import mod.agus.jcoderz.dx.cf.iface.ParseObserver;
import mod.agus.jcoderz.dx.cf.iface.StdAttributeList;
import mod.agus.jcoderz.dx.rop.cst.ConstantPool;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.util.ByteArray;
import mod.agus.jcoderz.dx.util.Hex;

public abstract class MemberListParser {
    private final AttributeFactory attributeFactory;
    private final DirectClassFile cf;
    private final CstType definer;
    private int endOffset;
    private ParseObserver observer;
    private final int offset;

    public abstract int getAttributeContext();

    public abstract String humanAccessFlags(int i);

    public abstract String humanName();

    public abstract Member set(int i, int i2, CstNat cstNat, AttributeList attributeList);

    public MemberListParser(DirectClassFile directClassFile, CstType cstType, int i, AttributeFactory attributeFactory2) {
        if (directClassFile == null) {
            throw new NullPointerException("cf == null");
        } else if (i < 0) {
            throw new IllegalArgumentException("offset < 0");
        } else if (attributeFactory2 == null) {
            throw new NullPointerException("attributeFactory == null");
        } else {
            this.cf = directClassFile;
            this.definer = cstType;
            this.offset = i;
            this.attributeFactory = attributeFactory2;
            this.endOffset = -1;
        }
    }

    public int getEndOffset() {
        parseIfNecessary();
        return this.endOffset;
    }

    public final void setObserver(ParseObserver parseObserver) {
        this.observer = parseObserver;
    }

    public final void parseIfNecessary() {
        if (this.endOffset < 0) {
            parse();
        }
    }

    public final int getCount() {
        return this.cf.getBytes().getUnsignedShort(this.offset);
    }

    public final CstType getDefiner() {
        return this.definer;
    }

    private void parse() {
        int attributeContext = getAttributeContext();
        int count = getCount();
        int i = this.offset + 2;
        ByteArray bytes = this.cf.getBytes();
        ConstantPool constantPool = this.cf.getConstantPool();
        if (this.observer != null) {
            this.observer.parsed(bytes, this.offset, 2, String.valueOf(humanName()) + "s_count: " + Hex.u2(count));
        }
        for (int i2 = 0; i2 < count; i2++) {
            try {
                int unsignedShort = bytes.getUnsignedShort(i);
                int unsignedShort2 = bytes.getUnsignedShort(i + 2);
                int unsignedShort3 = bytes.getUnsignedShort(i + 4);
                CstString cstString = (CstString) constantPool.get(unsignedShort2);
                CstString cstString2 = (CstString) constantPool.get(unsignedShort3);
                if (this.observer != null) {
                    this.observer.startParsingMember(bytes, i, cstString.getString(), cstString2.getString());
                    this.observer.parsed(bytes, i, 0, "\n" + humanName() + "s[" + i2 + "]:\n");
                    this.observer.changeIndent(1);
                    this.observer.parsed(bytes, i, 2, "access_flags: " + humanAccessFlags(unsignedShort));
                    this.observer.parsed(bytes, i + 2, 2, "name: " + cstString.toHuman());
                    this.observer.parsed(bytes, i + 4, 2, "descriptor: " + cstString2.toHuman());
                }
                AttributeListParser attributeListParser = new AttributeListParser(this.cf, attributeContext, i + 6, this.attributeFactory);
                attributeListParser.setObserver(this.observer);
                i = attributeListParser.getEndOffset();
                StdAttributeList list = attributeListParser.getList();
                list.setImmutable();
                Member member = set(i2, unsignedShort, new CstNat(cstString, cstString2), list);
                if (this.observer != null) {
                    this.observer.changeIndent(-1);
                    this.observer.parsed(bytes, i, 0, "end " + humanName() + "s[" + i2 + "]\n");
                    this.observer.endParsingMember(bytes, i, cstString.getString(), cstString2.getString(), member);
                }
            } catch (ParseException e) {
                e.addContext("...while parsing " + humanName() + "s[" + i2 + "]");
                throw e;
            } catch (RuntimeException e2) {
                ParseException parseException = new ParseException(e2);
                parseException.addContext("...while parsing " + humanName() + "s[" + i2 + "]");
                throw parseException;
            }
        }
        this.endOffset = i;
    }
}
