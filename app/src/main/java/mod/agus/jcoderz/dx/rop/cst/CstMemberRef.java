package mod.agus.jcoderz.dx.rop.cst;

import org.eclipse.jdt.internal.compiler.util.Util;

public abstract class CstMemberRef extends TypedConstant {
    private final CstType definingClass;
    private final CstNat nat;

    CstMemberRef(CstType cstType, CstNat cstNat) {
        if (cstType == null) {
            throw new NullPointerException("definingClass == null");
        } else if (cstNat == null) {
            throw new NullPointerException("nat == null");
        } else {
            this.definingClass = cstType;
            this.nat = cstNat;
        }
    }

    public final boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CstMemberRef cstMemberRef = (CstMemberRef) obj;
        if (!this.definingClass.equals(cstMemberRef.definingClass) || !this.nat.equals(cstMemberRef.nat)) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return (this.definingClass.hashCode() * 31) ^ this.nat.hashCode();
    }

    /* access modifiers changed from: protected */
    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public int compareTo0(Constant constant) {
        CstMemberRef cstMemberRef = (CstMemberRef) constant;
        int compareTo = this.definingClass.compareTo((Constant) cstMemberRef.definingClass);
        return compareTo != 0 ? compareTo : this.nat.getName().compareTo((Constant) cstMemberRef.nat.getName());
    }

    public final String toString() {
        return String.valueOf(typeName()) + '{' + toHuman() + '}';
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public final boolean isCategory2() {
        return false;
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public final String toHuman() {
        return String.valueOf(this.definingClass.toHuman()) + Util.C_DOT + this.nat.toHuman();
    }

    public final CstType getDefiningClass() {
        return this.definingClass;
    }

    public final CstNat getNat() {
        return this.nat;
    }
}
