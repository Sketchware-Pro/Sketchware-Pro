package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.util.Hex;

public final class Merger {
    private Merger() {
    }

    public static OneLocalsArray mergeLocals(OneLocalsArray oneLocalsArray, OneLocalsArray oneLocalsArray2) {
        if (oneLocalsArray == oneLocalsArray2) {
            return oneLocalsArray;
        }
        int maxLocals = oneLocalsArray.getMaxLocals();
        if (oneLocalsArray2.getMaxLocals() != maxLocals) {
            throw new SimException("mismatched maxLocals values");
        }
        OneLocalsArray oneLocalsArray3 = null;
        for (int i = 0; i < maxLocals; i++) {
            TypeBearer orNull = oneLocalsArray.getOrNull(i);
            TypeBearer mergeType = mergeType(orNull, oneLocalsArray2.getOrNull(i));
            if (mergeType != orNull) {
                if (oneLocalsArray3 == null) {
                    oneLocalsArray3 = oneLocalsArray.copy();
                }
                if (mergeType == null) {
                    oneLocalsArray3.invalidate(i);
                } else {
                    oneLocalsArray3.set(i, mergeType);
                }
            }
        }
        if (oneLocalsArray3 == null) {
            return oneLocalsArray;
        }
        oneLocalsArray3.setImmutable();
        return oneLocalsArray3;
    }

    public static ExecutionStack mergeStack(ExecutionStack executionStack, ExecutionStack executionStack2) {
        if (executionStack == executionStack2) {
            return executionStack;
        }
        int size = executionStack.size();
        if (executionStack2.size() != size) {
            throw new SimException("mismatched stack depths");
        }
        ExecutionStack executionStack3 = null;
        for (int i = 0; i < size; i++) {
            TypeBearer peek = executionStack.peek(i);
            TypeBearer peek2 = executionStack2.peek(i);
            TypeBearer mergeType = mergeType(peek, peek2);
            if (mergeType != peek) {
                if (executionStack3 == null) {
                    executionStack3 = executionStack.copy();
                }
                if (mergeType == null) {
                    try {
                        throw new SimException("incompatible: " + peek + ", " + peek2);
                    } catch (SimException e) {
                        e.addContext("...while merging stack[" + Hex.u2(i) + "]");
                        throw e;
                    }
                } else {
                    executionStack3.change(i, mergeType);
                }
            }
        }
        if (executionStack3 == null) {
            return executionStack;
        }
        executionStack3.setImmutable();
        return executionStack3;
    }

    public static TypeBearer mergeType(TypeBearer typeBearer, TypeBearer typeBearer2) {
        if (typeBearer == null || typeBearer.equals(typeBearer2)) {
            return typeBearer;
        }
        if (typeBearer2 == null) {
            return null;
        }
        Type type = typeBearer.getType();
        Type type2 = typeBearer2.getType();
        if (type == type2) {
            return type;
        }
        if (!type.isReference() || !type2.isReference()) {
            if (!type.isIntlike() || !type2.isIntlike()) {
                return null;
            }
            return Type.INT;
        } else if (type == Type.KNOWN_NULL) {
            return type2;
        } else {
            if (type2 == Type.KNOWN_NULL) {
                return type;
            }
            if (!type.isArray() || !type2.isArray()) {
                return Type.OBJECT;
            }
            TypeBearer mergeType = mergeType(type.getComponentType(), type2.getComponentType());
            if (mergeType == null) {
                return Type.OBJECT;
            }
            return ((Type) mergeType).getArrayType();
        }
    }

    public static boolean isPossiblyAssignableFrom(TypeBearer typeBearer, TypeBearer typeBearer2) {
        Type type;
        int i;
        Type type2;
        int i2;
        Type type3 = typeBearer.getType();
        Type type4 = typeBearer2.getType();
        if (type3.equals(type4)) {
            return true;
        }
        int basicType = type3.getBasicType();
        int basicType2 = type4.getBasicType();
        if (basicType == 10) {
            type = Type.OBJECT;
            i = 9;
        } else {
            type = type3;
            i = basicType;
        }
        if (basicType2 == 10) {
            type2 = Type.OBJECT;
            i2 = 9;
        } else {
            type2 = type4;
            i2 = basicType2;
        }
        if (i != 9 || i2 != 9) {
            return type.isIntlike() && type2.isIntlike();
        }
        if (type == Type.KNOWN_NULL) {
            return false;
        }
        if (type2 == Type.KNOWN_NULL) {
            return true;
        }
        if (type == Type.OBJECT) {
            return true;
        }
        if (type.isArray()) {
            if (!type2.isArray()) {
                return false;
            }
            do {
                type = type.getComponentType();
                type2 = type2.getComponentType();
                if (!type.isArray()) {
                    break;
                }
            } while (type2.isArray());
            return isPossiblyAssignableFrom(type, type2);
        } else if (type2.isArray()) {
            return type == Type.SERIALIZABLE || type == Type.CLONEABLE;
        } else {
            return true;
        }
    }
}
