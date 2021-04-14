package mod.agus.jcoderz.dx.cf.direct;

import mod.agus.jcoderz.dx.cf.iface.AttributeList;
import mod.agus.jcoderz.dx.cf.iface.Member;
import mod.agus.jcoderz.dx.cf.iface.StdMethod;
import mod.agus.jcoderz.dx.cf.iface.StdMethodList;
import mod.agus.jcoderz.dx.rop.code.AccessFlags;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstType;

public final class MethodListParser extends MemberListParser {
    private final StdMethodList methods = new StdMethodList(getCount());

    public MethodListParser(DirectClassFile directClassFile, CstType cstType, int i, AttributeFactory attributeFactory) {
        super(directClassFile, cstType, i, attributeFactory);
    }

    public StdMethodList getList() {
        parseIfNecessary();
        return this.methods;
    }

    @Override // mod.agus.jcoderz.dx.cf.direct.MemberListParser
    public String humanName() {
        return "method";
    }

    @Override // mod.agus.jcoderz.dx.cf.direct.MemberListParser
    public String humanAccessFlags(int i) {
        return AccessFlags.methodString(i);
    }

    @Override // mod.agus.jcoderz.dx.cf.direct.MemberListParser
    public int getAttributeContext() {
        return 2;
    }

    @Override // mod.agus.jcoderz.dx.cf.direct.MemberListParser
    public Member set(int i, int i2, CstNat cstNat, AttributeList attributeList) {
        StdMethod stdMethod = new StdMethod(getDefiner(), i2, cstNat, attributeList);
        this.methods.set(i, stdMethod);
        return stdMethod;
    }
}
