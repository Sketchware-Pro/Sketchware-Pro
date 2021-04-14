package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeList;
import mod.agus.jcoderz.dx.util.Hex;

public final class Rop {
    public static final int BRANCH_GOTO = 3;
    public static final int BRANCH_IF = 4;
    public static final int BRANCH_MAX = 6;
    public static final int BRANCH_MIN = 1;
    public static final int BRANCH_NONE = 1;
    public static final int BRANCH_RETURN = 2;
    public static final int BRANCH_SWITCH = 5;
    public static final int BRANCH_THROW = 6;
    private final int branchingness;
    private final TypeList exceptions;
    private final boolean isCallLike;
    private final String nickname;
    private final int opcode;
    private final Type result;
    private final TypeList sources;

    public Rop(int i, Type type, TypeList typeList, TypeList typeList2, int i2, boolean z, String str) {
        if (type == null) {
            throw new NullPointerException("result == null");
        } else if (typeList == null) {
            throw new NullPointerException("sources == null");
        } else if (typeList2 == null) {
            throw new NullPointerException("exceptions == null");
        } else if (i2 < 1 || i2 > 6) {
            throw new IllegalArgumentException("bogus branchingness");
        } else if (typeList2.size() == 0 || i2 == 6) {
            this.opcode = i;
            this.result = type;
            this.sources = typeList;
            this.exceptions = typeList2;
            this.branchingness = i2;
            this.isCallLike = z;
            this.nickname = str;
        } else {
            throw new IllegalArgumentException("exceptions / branchingness mismatch");
        }
    }

    public Rop(int i, Type type, TypeList typeList, TypeList typeList2, int i2, String str) {
        this(i, type, typeList, typeList2, i2, false, str);
    }

    public Rop(int i, Type type, TypeList typeList, int i2, String str) {
        this(i, type, typeList, StdTypeList.EMPTY, i2, false, str);
    }

    public Rop(int i, Type type, TypeList typeList, String str) {
        this(i, type, typeList, StdTypeList.EMPTY, 1, false, str);
    }

    public Rop(int i, Type type, TypeList typeList, TypeList typeList2, String str) {
        this(i, type, typeList, typeList2, 6, false, str);
    }

    public Rop(int i, TypeList typeList, TypeList typeList2) {
        this(i, Type.VOID, typeList, typeList2, 6, true, null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Rop)) {
            return false;
        }
        Rop rop = (Rop) obj;
        return this.opcode == rop.opcode && this.branchingness == rop.branchingness && this.result == rop.result && this.sources.equals(rop.sources) && this.exceptions.equals(rop.exceptions);
    }

    public int hashCode() {
        return (((((((this.opcode * 31) + this.branchingness) * 31) + this.result.hashCode()) * 31) + this.sources.hashCode()) * 31) + this.exceptions.hashCode();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(40);
        stringBuffer.append("Rop{");
        stringBuffer.append(RegOps.opName(this.opcode));
        if (this.result != Type.VOID) {
            stringBuffer.append(" ");
            stringBuffer.append(this.result);
        } else {
            stringBuffer.append(" .");
        }
        stringBuffer.append(" <-");
        int size = this.sources.size();
        if (size == 0) {
            stringBuffer.append(" .");
        } else {
            for (int i = 0; i < size; i++) {
                stringBuffer.append(' ');
                stringBuffer.append(this.sources.getType(i));
            }
        }
        if (this.isCallLike) {
            stringBuffer.append(" call");
        }
        int size2 = this.exceptions.size();
        if (size2 == 0) {
            switch (this.branchingness) {
                case 1:
                    stringBuffer.append(" flows");
                    break;
                case 2:
                    stringBuffer.append(" returns");
                    break;
                case 3:
                    stringBuffer.append(" gotos");
                    break;
                case 4:
                    stringBuffer.append(" ifs");
                    break;
                case 5:
                    stringBuffer.append(" switches");
                    break;
                default:
                    stringBuffer.append(" " + Hex.u1(this.branchingness));
                    break;
            }
        } else {
            stringBuffer.append(" throws");
            for (int i2 = 0; i2 < size2; i2++) {
                stringBuffer.append(' ');
                if (this.exceptions.getType(i2) == Type.THROWABLE) {
                    stringBuffer.append("<any>");
                } else {
                    stringBuffer.append(this.exceptions.getType(i2));
                }
            }
        }
        stringBuffer.append('}');
        return stringBuffer.toString();
    }

    public int getOpcode() {
        return this.opcode;
    }

    public Type getResult() {
        return this.result;
    }

    public TypeList getSources() {
        return this.sources;
    }

    public TypeList getExceptions() {
        return this.exceptions;
    }

    public int getBranchingness() {
        return this.branchingness;
    }

    public boolean isCallLike() {
        return this.isCallLike;
    }

    public boolean isCommutative() {
        switch (this.opcode) {
            case 14:
            case 16:
            case 20:
            case 21:
            case 22:
                return true;
            case 15:
            case 17:
            case 18:
            case 19:
            default:
                return false;
        }
    }

    public String getNickname() {
        if (this.nickname != null) {
            return this.nickname;
        }
        return toString();
    }

    public final boolean canThrow() {
        return this.exceptions.size() != 0;
    }
}
