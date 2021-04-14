package mod.agus.jcoderz.dx.rop.type;

import mod.agus.jcoderz.dx.util.FixedSizeList;

public final class StdTypeList extends FixedSizeList implements TypeList {
    public static final StdTypeList BOOLEANARR_INT = make(Type.BOOLEAN_ARRAY, Type.INT);
    public static final StdTypeList BYTEARR_INT = make(Type.BYTE_ARRAY, Type.INT);
    public static final StdTypeList CHARARR_INT = make(Type.CHAR_ARRAY, Type.INT);
    public static final StdTypeList DOUBLE = make(Type.DOUBLE);
    public static final StdTypeList DOUBLEARR_INT = make(Type.DOUBLE_ARRAY, Type.INT);
    public static final StdTypeList DOUBLE_DOUBLE = make(Type.DOUBLE, Type.DOUBLE);
    public static final StdTypeList DOUBLE_DOUBLEARR_INT = make(Type.DOUBLE, Type.DOUBLE_ARRAY, Type.INT);
    public static final StdTypeList DOUBLE_OBJECT = make(Type.DOUBLE, Type.OBJECT);
    public static final StdTypeList EMPTY = new StdTypeList(0);
    public static final StdTypeList FLOAT = make(Type.FLOAT);
    public static final StdTypeList FLOATARR_INT = make(Type.FLOAT_ARRAY, Type.INT);
    public static final StdTypeList FLOAT_FLOAT = make(Type.FLOAT, Type.FLOAT);
    public static final StdTypeList FLOAT_FLOATARR_INT = make(Type.FLOAT, Type.FLOAT_ARRAY, Type.INT);
    public static final StdTypeList FLOAT_OBJECT = make(Type.FLOAT, Type.OBJECT);
    public static final StdTypeList INT = make(Type.INT);
    public static final StdTypeList INTARR_INT = make(Type.INT_ARRAY, Type.INT);
    public static final StdTypeList INT_BOOLEANARR_INT = make(Type.INT, Type.BOOLEAN_ARRAY, Type.INT);
    public static final StdTypeList INT_BYTEARR_INT = make(Type.INT, Type.BYTE_ARRAY, Type.INT);
    public static final StdTypeList INT_CHARARR_INT = make(Type.INT, Type.CHAR_ARRAY, Type.INT);
    public static final StdTypeList INT_INT = make(Type.INT, Type.INT);
    public static final StdTypeList INT_INTARR_INT = make(Type.INT, Type.INT_ARRAY, Type.INT);
    public static final StdTypeList INT_OBJECT = make(Type.INT, Type.OBJECT);
    public static final StdTypeList INT_SHORTARR_INT = make(Type.INT, Type.SHORT_ARRAY, Type.INT);
    public static final StdTypeList LONG = make(Type.LONG);
    public static final StdTypeList LONGARR_INT = make(Type.LONG_ARRAY, Type.INT);
    public static final StdTypeList LONG_INT = make(Type.LONG, Type.INT);
    public static final StdTypeList LONG_LONG = make(Type.LONG, Type.LONG);
    public static final StdTypeList LONG_LONGARR_INT = make(Type.LONG, Type.LONG_ARRAY, Type.INT);
    public static final StdTypeList LONG_OBJECT = make(Type.LONG, Type.OBJECT);
    public static final StdTypeList OBJECT = make(Type.OBJECT);
    public static final StdTypeList OBJECTARR_INT = make(Type.OBJECT_ARRAY, Type.INT);
    public static final StdTypeList OBJECT_OBJECT = make(Type.OBJECT, Type.OBJECT);
    public static final StdTypeList OBJECT_OBJECTARR_INT = make(Type.OBJECT, Type.OBJECT_ARRAY, Type.INT);
    public static final StdTypeList RETURN_ADDRESS = make(Type.RETURN_ADDRESS);
    public static final StdTypeList SHORTARR_INT = make(Type.SHORT_ARRAY, Type.INT);
    public static final StdTypeList THROWABLE = make(Type.THROWABLE);

    public static StdTypeList make(Type type) {
        StdTypeList stdTypeList = new StdTypeList(1);
        stdTypeList.set(0, type);
        return stdTypeList;
    }

    public static StdTypeList make(Type type, Type type2) {
        StdTypeList stdTypeList = new StdTypeList(2);
        stdTypeList.set(0, type);
        stdTypeList.set(1, type2);
        return stdTypeList;
    }

    public static StdTypeList make(Type type, Type type2, Type type3) {
        StdTypeList stdTypeList = new StdTypeList(3);
        stdTypeList.set(0, type);
        stdTypeList.set(1, type2);
        stdTypeList.set(2, type3);
        return stdTypeList;
    }

    public static StdTypeList make(Type type, Type type2, Type type3, Type type4) {
        StdTypeList stdTypeList = new StdTypeList(4);
        stdTypeList.set(0, type);
        stdTypeList.set(1, type2);
        stdTypeList.set(2, type3);
        stdTypeList.set(3, type4);
        return stdTypeList;
    }

    public static String toHuman(TypeList typeList) {
        int size = typeList.size();
        if (size == 0) {
            return "<empty>";
        }
        StringBuffer stringBuffer = new StringBuffer(100);
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                stringBuffer.append(", ");
            }
            stringBuffer.append(typeList.getType(i).toHuman());
        }
        return stringBuffer.toString();
    }

    public static int hashContents(TypeList typeList) {
        int size = typeList.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i = (i * 31) + typeList.getType(i2).hashCode();
        }
        return i;
    }

    public static boolean equalContents(TypeList typeList, TypeList typeList2) {
        int size = typeList.size();
        if (typeList2.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!typeList.getType(i).equals(typeList2.getType(i))) {
                return false;
            }
        }
        return true;
    }

    public static int compareContents(TypeList typeList, TypeList typeList2) {
        int size = typeList.size();
        int size2 = typeList2.size();
        int min = Math.min(size, size2);
        for (int i = 0; i < min; i++) {
            int compareTo = typeList.getType(i).compareTo(typeList2.getType(i));
            if (compareTo != 0) {
                return compareTo;
            }
        }
        if (size == size2) {
            return 0;
        }
        if (size < size2) {
            return -1;
        }
        return 1;
    }

    public StdTypeList(int i) {
        super(i);
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeList
    public Type getType(int i) {
        return get(i);
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeList
    public int getWordCount() {
        int size = size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            i += get(i2).getCategory();
        }
        return i;
    }

    @Override // mod.agus.jcoderz.dx.rop.type.TypeList
    public TypeList withAddedType(Type type) {
        int size = size();
        StdTypeList stdTypeList = new StdTypeList(size + 1);
        for (int i = 0; i < size; i++) {
            stdTypeList.set0(i, get0(i));
        }
        stdTypeList.set(size, type);
        stdTypeList.setImmutable();
        return stdTypeList;
    }

    public Type get(int i) {
        return (Type) get0(i);
    }

    public void set(int i, Type type) {
        set0(i, type);
    }

    public StdTypeList withFirst(Type type) {
        int size = size();
        StdTypeList stdTypeList = new StdTypeList(size + 1);
        stdTypeList.set0(0, type);
        for (int i = 0; i < size; i++) {
            stdTypeList.set0(i + 1, getOrNull0(i));
        }
        return stdTypeList;
    }
}
