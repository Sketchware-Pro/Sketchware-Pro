package mod.agus.jcoderz.dx.rop.cst;

import org.eclipse.jdt.internal.compiler.util.Util;

import mod.agus.jcoderz.dx.rop.type.Type;

public final class CstNat extends Constant {
    public static final CstNat PRIMITIVE_TYPE_NAT = new CstNat(new CstString("TYPE"), new CstString("Ljava/lang/Class;"));
    private final CstString descriptor;
    private final CstString name;

    public CstNat(CstString cstString, CstString cstString2) {
        if (cstString == null) {
            throw new NullPointerException("name == null");
        } else if (cstString2 == null) {
            throw new NullPointerException("descriptor == null");
        } else {
            this.name = cstString;
            this.descriptor = cstString2;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CstNat)) {
            return false;
        }
        CstNat cstNat = (CstNat) obj;
        return this.name.equals(cstNat.name) && this.descriptor.equals(cstNat.descriptor);
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) ^ this.descriptor.hashCode();
    }

    /* access modifiers changed from: protected */
    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public int compareTo0(Constant constant) {
        CstNat cstNat = (CstNat) constant;
        int compareTo = this.name.compareTo((Constant) cstNat.name);
        return compareTo != 0 ? compareTo : this.descriptor.compareTo((Constant) cstNat.descriptor);
    }

    public String toString() {
        return "nat{" + toHuman() + '}';
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public String typeName() {
        return "nat";
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public boolean isCategory2() {
        return false;
    }

    public CstString getName() {
        return this.name;
    }

    public CstString getDescriptor() {
        return this.descriptor;
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return this.name.toHuman() + Util.C_COLON + this.descriptor.toHuman();
    }

    public Type getFieldType() {
        return Type.intern(this.descriptor.getString());
    }

    public final boolean isInstanceInit() {
        return this.name.getString().equals("<init>");
    }

    public final boolean isClassInit() {
        return this.name.getString().equals("<clinit>");
    }
}
