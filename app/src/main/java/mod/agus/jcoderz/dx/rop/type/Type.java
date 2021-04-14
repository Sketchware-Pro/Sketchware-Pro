package mod.agus.jcoderz.dx.rop.type;

import com.github.angads25.filepicker.model.DialogConfigs;
import io.github.rosemoe.editor.widget.CodeEditor;
import java.util.HashMap;
import mod.agus.jcoderz.dx.util.Hex;
import org.eclipse.jdt.internal.compiler.util.Util;

public final class Type implements TypeBearer, Comparable<Type> {
    public static final Type ANNOTATION = intern("Ljava/lang/annotation/Annotation;");
    public static final Type BOOLEAN = new Type("Z", 1);
    public static final Type BOOLEAN_ARRAY = BOOLEAN.getArrayType();
    public static final Type BOOLEAN_CLASS = intern("Ljava/lang/Boolean;");
    public static final int BT_ADDR = 10;
    public static final int BT_BOOLEAN = 1;
    public static final int BT_BYTE = 2;
    public static final int BT_CHAR = 3;
    public static final int BT_COUNT = 11;
    public static final int BT_DOUBLE = 4;
    public static final int BT_FLOAT = 5;
    public static final int BT_INT = 6;
    public static final int BT_LONG = 7;
    public static final int BT_OBJECT = 9;
    public static final int BT_SHORT = 8;
    public static final int BT_VOID = 0;
    public static final Type BYTE = new Type("B", 2);
    public static final Type BYTE_ARRAY = BYTE.getArrayType();
    public static final Type BYTE_CLASS = intern("Ljava/lang/Byte;");
    public static final Type CHAR = new Type("C", 3);
    public static final Type CHARACTER_CLASS = intern("Ljava/lang/Character;");
    public static final Type CHAR_ARRAY = CHAR.getArrayType();
    public static final Type CLASS = intern("Ljava/lang/Class;");
    public static final Type CLONEABLE = intern("Ljava/lang/Cloneable;");
    public static final Type DOUBLE = new Type("D", 4);
    public static final Type DOUBLE_ARRAY = DOUBLE.getArrayType();
    public static final Type DOUBLE_CLASS = intern("Ljava/lang/Double;");
    public static final Type FLOAT = new Type("F", 5);
    public static final Type FLOAT_ARRAY = FLOAT.getArrayType();
    public static final Type FLOAT_CLASS = intern("Ljava/lang/Float;");
    public static final Type INT = new Type("I", 6);
    public static final Type INTEGER_CLASS = intern("Ljava/lang/Integer;");
    public static final Type INT_ARRAY = INT.getArrayType();
    public static final Type KNOWN_NULL = new Type("<null>", 9);
    public static final Type LONG = new Type("J", 7);
    public static final Type LONG_ARRAY = LONG.getArrayType();
    public static final Type LONG_CLASS = intern("Ljava/lang/Long;");
    public static final Type OBJECT = intern("Ljava/lang/Object;");
    public static final Type OBJECT_ARRAY = OBJECT.getArrayType();
    public static final Type RETURN_ADDRESS = new Type("<addr>", 10);
    public static final Type SERIALIZABLE = intern("Ljava/io/Serializable;");
    public static final Type SHORT = new Type("S", 8);
    public static final Type SHORT_ARRAY = SHORT.getArrayType();
    public static final Type SHORT_CLASS = intern("Ljava/lang/Short;");
    public static final Type STRING = intern("Ljava/lang/String;");
    public static final Type THROWABLE = intern("Ljava/lang/Throwable;");
    public static final Type VOID = new Type("V", 0);
    public static final Type VOID_CLASS = intern("Ljava/lang/Void;");
    private static final HashMap<String, Type> internTable = new HashMap<>((int) CodeEditor.DEFAULT_CURSOR_BLINK_PERIOD);
    private Type arrayType;
    private final int basicType;
    private String className;
    private Type componentType;
    private final String descriptor;
    private Type initializedType;
    private final int newAt;

    static {
        putIntern(BOOLEAN);
        putIntern(BYTE);
        putIntern(CHAR);
        putIntern(DOUBLE);
        putIntern(FLOAT);
        putIntern(INT);
        putIntern(LONG);
        putIntern(SHORT);
    }

    public static Type intern(String str) {
        Type type;
        synchronized (internTable) {
            type = internTable.get(str);
        }
        if (type != null) {
            return type;
        }
        try {
            char charAt = str.charAt(0);
            if (charAt == '[') {
                return intern(str.substring(1)).getArrayType();
            }
            int length = str.length();
            if (charAt == 'L' && str.charAt(length - 1) == ';') {
                int i = length - 1;
                for (int i2 = 1; i2 < i; i2++) {
                    switch (str.charAt(i2)) {
                        case '(':
                        case ')':
                        case '.':
                        case ';':
                        case '[':
                            throw new IllegalArgumentException("bad descriptor: " + str);
                        case '/':
                            if (i2 != 1 && i2 != length - 1 && str.charAt(i2 - 1) != '/') {
                                break;
                            } else {
                                throw new IllegalArgumentException("bad descriptor: " + str);
                            }
                    }
                }
                return putIntern(new Type(str, 9));
            }
            throw new IllegalArgumentException("bad descriptor: " + str);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("descriptor is empty");
        } catch (NullPointerException e2) {
            throw new NullPointerException("descriptor == null");
        }
    }

    public static Type internReturnType(String str) {
        try {
            if (str.equals("V")) {
                return VOID;
            }
            return intern(str);
        } catch (NullPointerException e) {
            throw new NullPointerException("descriptor == null");
        }
    }

    public static Type internClassName(String str) {
        if (str == null) {
            throw new NullPointerException("name == null");
        } else if (str.startsWith("[")) {
            return intern(str);
        } else {
            return intern(String.valueOf('L') + str + ';');
        }
    }

    private Type(String str, int i, int i2) {
        if (str == null) {
            throw new NullPointerException("descriptor == null");
        } else if (i < 0 || i >= 11) {
            throw new IllegalArgumentException("bad basicType");
        } else if (i2 < -1) {
            throw new IllegalArgumentException("newAt < -1");
        } else {
            this.descriptor = str;
            this.basicType = i;
            this.newAt = i2;
            this.arrayType = null;
            this.componentType = null;
            this.initializedType = null;
        }
    }

    private Type(String str, int i) {
        this(str, i, -1);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Type)) {
            return false;
        }
        return this.descriptor.equals(((Type) obj).descriptor);
    }

    public int hashCode() {
        return this.descriptor.hashCode();
    }

    public int compareTo(Type type) {
        return this.descriptor.compareTo(type.descriptor);
    }

    public String toString() {
        return this.descriptor;
    }

    @Override // mod.agus.jcoderz.dx.util.ToHuman
    public String toHuman() {
        switch (this.basicType) {
            case 0:
                return "void";
            case 1:
                return "boolean";
            case 2:
                return "byte";
            case 3:
                return "char";
            case 4:
                return "double";
            case 5:
                return "float";
            case 6:
                return "int";
            case 7:
                return "long";
            case 8:
                return "short";
            case 9:
                if (isArray()) {
                    return String.valueOf(getComponentType().toHuman()) + "[]";
                }
                return getClassName().replace(DialogConfigs.DIRECTORY_SEPERATOR, ".");
            default:
                return this.descriptor;
        }
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public Type getType() {
        return this;
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public Type getFrameType() {
        switch (this.basicType) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 8:
                return INT;
            case 4:
            case 5:
            case 7:
            default:
                return this;
        }
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public int getBasicType() {
        return this.basicType;
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public int getBasicFrameType() {
        switch (this.basicType) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 8:
                return 6;
            case 4:
            case 5:
            case 7:
            default:
                return this.basicType;
        }
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeBearer
    public boolean isConstant() {
        return false;
    }

    public String getDescriptor() {
        return this.descriptor;
    }

    public String getClassName() {
        if (this.className == null) {
            if (!isReference()) {
                throw new IllegalArgumentException("not an object type: " + this.descriptor);
            } else if (this.descriptor.charAt(0) == '[') {
                this.className = this.descriptor;
            } else {
                this.className = this.descriptor.substring(1, this.descriptor.length() - 1);
            }
        }
        return this.className;
    }

    public int getCategory() {
        switch (this.basicType) {
            case 4:
            case 7:
                return 2;
            case 5:
            case 6:
            default:
                return 1;
        }
    }

    public boolean isCategory1() {
        switch (this.basicType) {
            case 4:
            case 7:
                return false;
            case 5:
            case 6:
            default:
                return true;
        }
    }

    public boolean isCategory2() {
        switch (this.basicType) {
            case 4:
            case 7:
                return true;
            case 5:
            case 6:
            default:
                return false;
        }
    }

    public boolean isIntlike() {
        switch (this.basicType) {
            case 1:
            case 2:
            case 3:
            case 6:
            case 8:
                return true;
            case 4:
            case 5:
            case 7:
            default:
                return false;
        }
    }

    public boolean isPrimitive() {
        switch (this.basicType) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return true;
            default:
                return false;
        }
    }

    public boolean isReference() {
        return this.basicType == 9;
    }

    public boolean isArray() {
        return this.descriptor.charAt(0) == '[';
    }

    public boolean isArrayOrKnownNull() {
        return isArray() || equals(KNOWN_NULL);
    }

    public boolean isUninitialized() {
        return this.newAt >= 0;
    }

    public int getNewAt() {
        return this.newAt;
    }

    public Type getInitializedType() {
        if (this.initializedType != null) {
            return this.initializedType;
        }
        throw new IllegalArgumentException("initialized type: " + this.descriptor);
    }

    public Type getArrayType() {
        if (this.arrayType == null) {
            this.arrayType = putIntern(new Type(String.valueOf((char) Util.C_ARRAY) + this.descriptor, 9));
        }
        return this.arrayType;
    }

    public Type getComponentType() {
        if (this.componentType == null) {
            if (this.descriptor.charAt(0) != '[') {
                throw new IllegalArgumentException("not an array type: " + this.descriptor);
            }
            this.componentType = intern(this.descriptor.substring(1));
        }
        return this.componentType;
    }

    public Type asUninitialized(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("newAt < 0");
        } else if (!isReference()) {
            throw new IllegalArgumentException("not a reference type: " + this.descriptor);
        } else if (isUninitialized()) {
            throw new IllegalArgumentException("already uninitialized: " + this.descriptor);
        } else {
            Type type = new Type(String.valueOf('N') + Hex.u2(i) + this.descriptor, 9, i);
            type.initializedType = this;
            return putIntern(type);
        }
    }

    private static Type putIntern(Type type) {
        synchronized (internTable) {
            String descriptor2 = type.getDescriptor();
            Type type2 = internTable.get(descriptor2);
            if (type2 != null) {
                return type2;
            }
            internTable.put(descriptor2, type);
            return type;
        }
    }
}
