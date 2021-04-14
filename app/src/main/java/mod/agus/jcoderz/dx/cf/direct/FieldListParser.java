package mod.agus.jcoderz.dx.cf.direct;

import mod.agus.jcoderz.dx.cf.iface.AttributeList;
import mod.agus.jcoderz.dx.cf.iface.Member;
import mod.agus.jcoderz.dx.cf.iface.StdField;
import mod.agus.jcoderz.dx.cf.iface.StdFieldList;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstType;

public final class FieldListParser extends MemberListParser {
    private final StdFieldList fields = new StdFieldList(getCount());

    public FieldListParser(DirectClassFile directClassFile, CstType cstType, int i, AttributeFactory attributeFactory) {
        super(directClassFile, cstType, i, attributeFactory);
    }

    public StdFieldList getList() {
        parseIfNecessary();
        return this.fields;
    }


    @Override // mod.agus.jcoderz.dx.cf.direct.MemberListParser
    public String humanName() {
        return "field";
    }

    @Override // mod.agus.jcoderz.dx.cf.direct.MemberListParser
    public String humanAccessFlags(int i) {
        return AccessFlags.fieldString(i);
    }

    @Override // mod.agus.jcoderz.dx.cf.direct.MemberListParser
    public int getAttributeContext() {
        return 1;
    }

    @Override // mod.agus.jcoderz.dx.cf.direct.MemberListParser
    public Member set(int i, int i2, CstNat cstNat, AttributeList attributeList) {
        StdField stdField = new StdField(getDefiner(), i2, cstNat, attributeList);
        this.fields.set(i, stdField);
        return stdField;
    }
}
