package mod.agus.jcoderz.dx.rop.code;

import java.util.HashMap;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.util.ToHuman;

public final class RegisterSpec implements TypeBearer, ToHuman, Comparable<RegisterSpec> {
    public static final String PREFIX = "v";
    private static final ForComparison theInterningItem = new ForComparison(null);
    private static final HashMap<Object, RegisterSpec> theInterns = new HashMap<>(1000);
    private final LocalItem local;
    private final int reg;
    private final TypeBearer type;

    private RegisterSpec(int i, TypeBearer typeBearer, LocalItem localItem) {
        if (i < 0) {
            throw new IllegalArgumentException("reg < 0");
        } else if (typeBearer == null) {
            throw new NullPointerException("type == null");
        } else {
            this.reg = i;
            this.type = typeBearer;
            this.local = localItem;
        }
    }

    /* synthetic */ RegisterSpec(int i, TypeBearer typeBearer, LocalItem localItem, RegisterSpec registerSpec) {
        this(i, typeBearer, localItem);
    }

    private static RegisterSpec intern(int i, TypeBearer typeBearer, LocalItem localItem) {
        RegisterSpec registerSpec;
        synchronized (theInterns) {
            theInterningItem.set(i, typeBearer, localItem);
            registerSpec = theInterns.get(theInterningItem);
            if (registerSpec == null) {
                registerSpec = theInterningItem.toRegisterSpec();
                theInterns.put(registerSpec, registerSpec);
            }
        }
        return registerSpec;
    }

    public static RegisterSpec make(int i, TypeBearer typeBearer) {
        return intern(i, typeBearer, null);
    }

    public static RegisterSpec make(int i, TypeBearer typeBearer, LocalItem localItem) {
        if (localItem != null) {
            return intern(i, typeBearer, localItem);
        }
        throw new NullPointerException("local  == null");
    }

    public static RegisterSpec makeLocalOptional(int i, TypeBearer typeBearer, LocalItem localItem) {
        return intern(i, typeBearer, localItem);
    }

    public static String regString(int i) {
        return PREFIX + i;
    }

    /* access modifiers changed from: private */
    public static int hashCodeOf(int i, TypeBearer typeBearer, LocalItem localItem) {
        return ((((localItem != null ? localItem.hashCode() : 0) * 31) + typeBearer.hashCode()) * 31) + i;
    }

    public boolean equals(Object obj) {
        if (obj instanceof RegisterSpec) {
            RegisterSpec registerSpec = (RegisterSpec) obj;
            return equals(registerSpec.reg, registerSpec.type, registerSpec.local);
        } else if (!(obj instanceof ForComparison)) {
            return false;
        } else {
            ForComparison forComparison = (ForComparison) obj;
            return equals(forComparison.reg, forComparison.type, forComparison.local);
        }
    }

    public boolean equalsUsingSimpleType(RegisterSpec registerSpec) {
        return matchesVariable(registerSpec) && this.reg == registerSpec.reg;
    }

    public boolean matchesVariable(RegisterSpec registerSpec) {
        if (registerSpec == null || !this.type.getType().equals(registerSpec.type.getType())) {
            return false;
        }
        return this.local == registerSpec.local || (this.local != null && this.local.equals(registerSpec.local));
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private boolean equals(int i, TypeBearer typeBearer, LocalItem localItem) {
        return this.reg == i && this.type.equals(typeBearer) && (this.local == localItem || (this.local != null && this.local.equals(localItem)));
    }

    public int compareTo(RegisterSpec registerSpec) {
        if (this.reg < registerSpec.reg) {
            return -1;
        }
        if (this.reg > registerSpec.reg) {
            return 1;
        }
        int compareTo = this.type.getType().compareTo(registerSpec.type.getType());
        if (compareTo != 0) {
            return compareTo;
        }
        if (this.local == null) {
            if (registerSpec.local == null) {
                return 0;
            }
            return -1;
        } else if (registerSpec.local == null) {
            return 1;
        } else {
            return this.local.compareTo(registerSpec.local);
        }
    }

    public int hashCode() {
        return hashCodeOf(this.reg, this.type, this.local);
    }

    public String toString() {
        return toString0(false);
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return toString0(true);
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public Type getType() {
        return this.type.getType();
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public TypeBearer getFrameType() {
        return this.type.getFrameType();
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public final int getBasicType() {
        return this.type.getBasicType();
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public final int getBasicFrameType() {
        return this.type.getBasicFrameType();
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public final boolean isConstant() {
        return false;
    }

    public int getReg() {
        return this.reg;
    }

    public TypeBearer getTypeBearer() {
        return this.type;
    }

    public LocalItem getLocalItem() {
        return this.local;
    }

    public int getNextReg() {
        return this.reg + getCategory();
    }

    public int getCategory() {
        return this.type.getType().getCategory();
    }

    public boolean isCategory1() {
        return this.type.getType().isCategory1();
    }

    public boolean isCategory2() {
        return this.type.getType().isCategory2();
    }

    public String regString() {
        return regString(this.reg);
    }

    public RegisterSpec intersect(RegisterSpec registerSpec, boolean z) {
        LocalItem localItem;
        boolean z2;
        Type type2;
        RegisterSpec make;
        if (this == registerSpec) {
            return this;
        }
        if (registerSpec == null || this.reg != registerSpec.getReg()) {
            return null;
        }
        if (this.local == null || !this.local.equals(registerSpec.getLocalItem())) {
            localItem = null;
        } else {
            localItem = this.local;
        }
        z2 = localItem == this.local;
        if ((z && !z2) || (type2 = getType()) != registerSpec.getType()) {
            return null;
        }
        TypeBearer typeBearer = this.type.equals(registerSpec.getTypeBearer()) ? this.type : type2;
        if (typeBearer == this.type && z2) {
            return this;
        }
        if (localItem == null) {
            make = make(this.reg, typeBearer);
        } else {
            make = make(this.reg, typeBearer, localItem);
        }
        return make;
    }

    public RegisterSpec withReg(int i) {
        return this.reg == i ? this : makeLocalOptional(i, this.type, this.local);
    }

    public RegisterSpec withType(TypeBearer typeBearer) {
        return makeLocalOptional(this.reg, typeBearer, this.local);
    }

    public RegisterSpec withOffset(int i) {
        return i == 0 ? this : withReg(this.reg + i);
    }

    public RegisterSpec withSimpleType() {
        Type type2;
        TypeBearer typeBearer = this.type;
        if (typeBearer instanceof Type) {
            type2 = (Type) typeBearer;
        } else {
            type2 = typeBearer.getType();
        }
        if (type2.isUninitialized()) {
            type2 = type2.getInitializedType();
        }
        return type2 == typeBearer ? this : makeLocalOptional(this.reg, type2, this.local);
    }

    public RegisterSpec withLocalItem(LocalItem localItem) {
        if (this.local != localItem) {
            return (this.local == null || !this.local.equals(localItem)) ? makeLocalOptional(this.reg, this.type, localItem) : this;
        }
        return this;
    }

    public boolean isEvenRegister() {
        return (getReg() & 1) == 0;
    }

    private String toString0(boolean z) {
        StringBuffer stringBuffer = new StringBuffer(40);
        stringBuffer.append(regString());
        stringBuffer.append(":");
        if (this.local != null) {
            stringBuffer.append(this.local.toString());
        }
        Type type2 = this.type.getType();
        stringBuffer.append(type2);
        if (type2 != this.type) {
            stringBuffer.append("=");
            if (z && (this.type instanceof CstString)) {
                stringBuffer.append(((CstString) this.type).toQuoted());
            } else if (!z || !(this.type instanceof Constant)) {
                stringBuffer.append(this.type);
            } else {
                stringBuffer.append(this.type.toHuman());
            }
        }
        return stringBuffer.toString();
    }

    public static class ForComparison {
        private LocalItem local;
        private int reg;
        private TypeBearer type;

        private ForComparison() {
        }

        ForComparison(ForComparison forComparison) {
            this();
        }

        public void set(int i, TypeBearer typeBearer, LocalItem localItem) {
            this.reg = i;
            this.type = typeBearer;
            this.local = localItem;
        }

        public RegisterSpec toRegisterSpec() {
            return new RegisterSpec(this.reg, this.type, this.local, null);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof RegisterSpec)) {
                return false;
            }
            return ((RegisterSpec) obj).equals(this.reg, this.type, this.local);
        }

        public int hashCode() {
            return RegisterSpec.hashCodeOf(this.reg, this.type, this.local);
        }
    }
}
