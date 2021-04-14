package mod.agus.jcoderz.dx.cf.iface;

import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;

public abstract class StdMember implements Member {
    private final int accessFlags;
    private final AttributeList attributes;
    private final CstType definingClass;
    private final CstNat nat;

    public StdMember(CstType cstType, int i, CstNat cstNat, AttributeList attributeList) {
        if (cstType == null) {
            throw new NullPointerException("definingClass == null");
        } else if (cstNat == null) {
            throw new NullPointerException("nat == null");
        } else if (attributeList == null) {
            throw new NullPointerException("attributes == null");
        } else {
            this.definingClass = cstType;
            this.accessFlags = i;
            this.nat = cstNat;
            this.attributes = attributeList;
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append(getClass().getName());
        stringBuffer.append('{');
        stringBuffer.append(this.nat.toHuman());
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Member
    public final CstType getDefiningClass() {
        return this.definingClass;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Member
    public final int getAccessFlags() {
        return this.accessFlags;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Member
    public final CstNat getNat() {
        return this.nat;
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Member
    public final CstString getName() {
        return this.nat.getName();
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Member
    public final CstString getDescriptor() {
        return this.nat.getDescriptor();
    }

    @Override // mod.agus.jcoderz.dx.cf.iface.Member, mod.agus.jcoderz.dx.cf.iface.HasAttribute
    public final AttributeList getAttributes() {
        return this.attributes;
    }
}
