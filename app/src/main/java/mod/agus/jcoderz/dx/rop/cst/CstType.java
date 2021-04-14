package mod.agus.jcoderz.dx.rop.cst;

import java.util.HashMap;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.multidex.ClassPathElement;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.util.Util;

public final class CstType extends TypedConstant {
    public static final CstType BOOLEAN = intern(Type.BOOLEAN_CLASS);
    public static final CstType BOOLEAN_ARRAY = intern(Type.BOOLEAN_ARRAY);
    public static final CstType BYTE = intern(Type.BYTE_CLASS);
    public static final CstType BYTE_ARRAY = intern(Type.BYTE_ARRAY);
    public static final CstType CHARACTER = intern(Type.CHARACTER_CLASS);
    public static final CstType CHAR_ARRAY = intern(Type.CHAR_ARRAY);
    public static final CstType DOUBLE = intern(Type.DOUBLE_CLASS);
    public static final CstType DOUBLE_ARRAY = intern(Type.DOUBLE_ARRAY);
    public static final CstType FLOAT = intern(Type.FLOAT_CLASS);
    public static final CstType FLOAT_ARRAY = intern(Type.FLOAT_ARRAY);
    public static final CstType INTEGER = intern(Type.INTEGER_CLASS);
    public static final CstType INT_ARRAY = intern(Type.INT_ARRAY);
    public static final CstType LONG = intern(Type.LONG_CLASS);
    public static final CstType LONG_ARRAY = intern(Type.LONG_ARRAY);
    public static final CstType OBJECT = intern(Type.OBJECT);
    public static final CstType SHORT = intern(Type.SHORT_CLASS);
    public static final CstType SHORT_ARRAY = intern(Type.SHORT_ARRAY);
    public static final CstType VOID = intern(Type.VOID_CLASS);
    private static final HashMap<Type, CstType> interns = new HashMap<>(100);
    private CstString descriptor;
    private final Type type;

    public static CstType forBoxedPrimitiveType(Type type2) {
        switch (type2.getBasicType()) {
            case 0:
                return VOID;
            case 1:
                return BOOLEAN;
            case 2:
                return BYTE;
            case 3:
                return CHARACTER;
            case 4:
                return DOUBLE;
            case 5:
                return FLOAT;
            case 6:
                return INTEGER;
            case 7:
                return LONG;
            case 8:
                return SHORT;
            default:
                throw new IllegalArgumentException("not primitive: " + type2);
        }
    }

    public static CstType intern(Type type2) {
        CstType cstType;
        synchronized (interns) {
            cstType = interns.get(type2);
            if (cstType == null) {
                cstType = new CstType(type2);
                interns.put(type2, cstType);
            }
        }
        return cstType;
    }

    public CstType(Type type2) {
        if (type2 == null) {
            throw new NullPointerException("type == null");
        } else if (type2 == Type.KNOWN_NULL) {
            throw new UnsupportedOperationException("KNOWN_NULL is not representable");
        } else {
            this.type = type2;
            this.descriptor = null;
        }
    }

    public boolean equals(Object obj) {
        if ((obj instanceof CstType) && this.type == ((CstType) obj).type) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.type.hashCode();
    }

    /* access modifiers changed from: protected */
    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public int compareTo0(Constant constant) {
        return this.type.getDescriptor().compareTo(((CstType) constant).type.getDescriptor());
    }

    public String toString() {
        return "type{" + toHuman() + '}';
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public Type getType() {
        return Type.CLASS;
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public String typeName() {
        return "type";
    }

    @Override // mod.agus.jcoderz.dx.rop.cst.Constant
    public boolean isCategory2() {
        return false;
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        return this.type.toHuman();
    }

    public Type getClassType() {
        return this.type;
    }

    public CstString getDescriptor() {
        if (this.descriptor == null) {
            this.descriptor = new CstString(this.type.getDescriptor());
        }
        return this.descriptor;
    }

    public String getPackageName() {
        String string = getDescriptor().getString();
        int lastIndexOf = string.lastIndexOf(47);
        int lastIndexOf2 = string.lastIndexOf(91);
        if (lastIndexOf == -1) {
            return CompilerOptions.DEFAULT;
        }
        return string.substring(lastIndexOf2 + 2, lastIndexOf).replace(ClassPathElement.SEPARATOR_CHAR, Util.C_DOT);
    }
}
