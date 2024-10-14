/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mod.agus.jcoderz.dx.rop.code;

import mod.agus.jcoderz.dx.util.Hex;

/**
 * All the register-based opcodes, and related utilities.
 *
 * <p><b>Note:</b> Opcode descriptions use a rough pseudocode. {@code r}
 * is the result register, {@code x} is the first argument,
 * {@code y} is the second argument, and {@code z} is the
 * third argument. The expression which describes
 * the operation uses Java-ish syntax but is preceded by type indicators for
 * each of the values.
 */
public final class RegOps {
    /** {@code nop()} */
    public static final int NOP = 1;

    /** {@code T: any type; r,x: T :: r = x;} */
    public static final int MOVE = 2;

    /** {@code T: any type; r,param(x): T :: r = param(x)} */
    public static final int MOVE_PARAM = 3;

    /**
     * {@code T: Throwable; r: T :: r = caught_exception}.
     * <b>Note:</b> This opcode should only ever be used in the
     * first instruction of a block, and such blocks must be
     * the start of an exception handler.
     */
    public static final int MOVE_EXCEPTION = 4;

    /** {@code T: any type; r, literal: T :: r = literal;} */
    public static final int CONST = 5;

    /** {@code goto label} */
    public static final int GOTO = 6;

    /**
     * {@code T: int or Object; x,y: T :: if (x == y) goto
     * label}
     */
    public static final int IF_EQ = 7;

    /**
     * {@code T: int or Object; x,y: T :: if (x != y) goto
     * label}
     */
    public static final int IF_NE = 8;

    /** {@code x,y: int :: if (x < y) goto label} */
    public static final int IF_LT = 9;

    /** {@code x,y: int :: if (x >= y) goto label} */
    public static final int IF_GE = 10;

    /** {@code x,y: int :: if (x <= y) goto label} */
    public static final int IF_LE = 11;

    /** {@code x,y: int :: if (x > y) goto label} */
    public static final int IF_GT = 12;

    /** {@code x: int :: goto table[x]} */
    public static final int SWITCH = 13;

    /** {@code T: any numeric type; r,x,y: T :: r = x + y} */
    public static final int ADD = 14;

    /** {@code T: any numeric type; r,x,y: T :: r = x - y} */
    public static final int SUB = 15;

    /** {@code T: any numeric type; r,x,y: T :: r = x * y} */
    public static final int MUL = 16;

    /** {@code T: any numeric type; r,x,y: T :: r = x / y} */
    public static final int DIV = 17;

    /**
     * {@code T: any numeric type; r,x,y: T :: r = x % y}
     * (Java-style remainder)
     */
    public static final int REM = 18;

    /** {@code T: any numeric type; r,x: T :: r = -x} */
    public static final int NEG = 19;

    /** {@code T: any integral type; r,x,y: T :: r = x & y} */
    public static final int AND = 20;

    /** {@code T: any integral type; r,x,y: T :: r = x | y} */
    public static final int OR = 21;

    /** {@code T: any integral type; r,x,y: T :: r = x ^ y} */
    public static final int XOR = 22;

    /**
     * {@code T: any integral type; r,x: T; y: int :: r = x << y}
     */
    public static final int SHL = 23;

    /**
     * {@code T: any integral type; r,x: T; y: int :: r = x >> y}
     * (signed right-shift)
     */
    public static final int SHR = 24;

    /**
     * {@code T: any integral type; r,x: T; y: int :: r = x >>> y}
     * (unsigned right-shift)
     */
    public static final int USHR = 25;

    /** {@code T: any integral type; r,x: T :: r = ~x} */
    public static final int NOT = 26;

    /**
     * {@code T: any numeric type; r: int; x,y: T :: r = (x == y) ? 0
     * : (x > y) ? 1 : -1} (Java-style "cmpl" where a NaN is
     * considered "less than" all other values; also used for integral
     * comparisons)
     */
    public static final int CMPL = 27;

    /**
     * {@code T: any floating point type; r: int; x,y: T :: r = (x == y) ? 0
     * : (x < y) ? -1 : 1} (Java-style "cmpg" where a NaN is
     * considered "greater than" all other values)
     */
    public static final int CMPG = 28;

    /**
     * {@code T: any numeric type; U: any numeric type; r: T; x: U ::
     * r = (T) x} (numeric type conversion between the four
     * "real" numeric types)
     */
    public static final int CONV = 29;

    /**
     * {@code r,x: int :: r = (x << 24) >> 24} (Java-style
     * convert int to byte)
     */
    public static final int TO_BYTE = 30;

    /**
     * {@code r,x: int :: r = x & 0xffff} (Java-style convert int to char)
     */
    public static final int TO_CHAR = 31;

    /**
     * {@code r,x: int :: r = (x << 16) >> 16} (Java-style
     * convert int to short)
     */
    public static final int TO_SHORT = 32;

    /** {@code T: return type for the method; x: T; return x} */
    public static final int RETURN = 33;

    /** {@code T: any type; r: int; x: T[]; :: r = x.length} */
    public static final int ARRAY_LENGTH = 34;

    /** {@code x: Throwable :: throw(x)} */
    public static final int THROW = 35;

    /** {@code x: Object :: monitorenter(x)} */
    public static final int MONITOR_ENTER = 36;

    /** {@code x: Object :: monitorexit(x)} */
    public static final int MONITOR_EXIT = 37;

    /** {@code T: any type; r: T; x: T[]; y: int :: r = x[y]} */
    public static final int AGET = 38;

    /** {@code T: any type; x: T; y: T[]; z: int :: x[y] = z} */
    public static final int APUT = 39;

    /**
     * {@code T: any non-array object type :: r =
     * alloc(T)} (allocate heap space for an object)
     */
    public static final int NEW_INSTANCE = 40;

    /** {@code T: any array type; r: T; x: int :: r = new T[x]} */
    public static final int NEW_ARRAY = 41;

    /**
     * {@code T: any array type; r: T; x: int; v0..vx: T :: r = new T[x]
     * {v0, ..., vx}}
     */
    public static final int FILLED_NEW_ARRAY = 42;

    /**
     * {@code T: any object type; x: Object :: (T) x} (can
     * throw {@code ClassCastException})
     */
    public static final int CHECK_CAST = 43;

    /**
     * {@code T: any object type; x: Object :: x instanceof T}
     */
    public static final int INSTANCE_OF = 44;

    /**
     * {@code T: any type; r: T; x: Object; f: instance field spec of
     * type T :: r = x.f}
     */
    public static final int GET_FIELD = 45;

    /**
     * {@code T: any type; r: T; f: static field spec of type T :: r =
     * f}
     */
    public static final int GET_STATIC = 46;

    /**
     * {@code T: any type; x: T; y: Object; f: instance field spec of type
     * T :: y.f = x}
     */
    public static final int PUT_FIELD = 47;

    /**
     * {@code T: any type; f: static field spec of type T; x: T :: f = x}
     */
    public static final int PUT_STATIC = 48;

    /**
     * {@code Tr, T0, T1...: any types; r: Tr; m: static method spec;
     * y0: T0; y1: T1 ... :: r = m(y0, y1, ...)} (call static
     * method)
     */
    public static final int INVOKE_STATIC = 49;

    /**
     * {@code Tr, T0, T1...: any types; r: Tr; x: Object; m: instance method
     * spec; y0: T0; y1: T1 ... :: r = x.m(y0, y1, ...)} (call normal
     * virtual method)
     */
    public static final int INVOKE_VIRTUAL = 50;

    /**
     * {@code Tr, T0, T1...: any types; r: Tr; x: Object; m: instance method
     * spec; y0: T0; y1: T1 ... :: r = x.m(y0, y1, ...)} (call
     * superclass virtual method)
     */
    public static final int INVOKE_SUPER = 51;

    /**
     * {@code Tr, T0, T1...: any types; r: Tr; x: Object; m: instance method
     * spec; y0: T0; y1: T1 ... :: r = x.m(y0, y1, ...)} (call
     * direct/special method)
     */
    public static final int INVOKE_DIRECT = 52;

    /**
     * {@code Tr, T0, T1...: any types; r: Tr; x: Object; m: interface
     * (instance) method spec; y0: T0; y1: T1 ... :: r = x.m(y0, y1,
     * ...)} (call interface method)
     */
    public static final int INVOKE_INTERFACE = 53;

    /**
     * {@code T0: any type; name: local variable name  :: mark(name,T0)}
     * (mark beginning or end of local variable name)
     */
    public static final int MARK_LOCAL = 54;

    /**
     * {@code T: Any type; r: T :: r = return_type}.
     * <b>Note:</b> This opcode should only ever be used in the
     * first instruction of a block following an invoke-*.
     */
    public static final int MOVE_RESULT = 55;

    /**
     * {@code T: Any type; r: T :: r = return_type}.
     * <b>Note:</b> This opcode should only ever be used in the
     * first instruction of a block following a non-invoke throwing insn
     */
    public static final int MOVE_RESULT_PSEUDO = 56;

    /** {@code T: Any primitive type; v0..vx: T :: {v0, ..., vx}} */
    public static final int FILL_ARRAY_DATA = 57;

    /**
     * {@code Tr, T0, T1...: any types; r: Tr; x: java.lang.invoke.MethodHandle;
     * m: signature polymorphic method
     * spec; y0: T0; y1: T1 ... :: r = x.m(y0, y1, ...)} (call signature
     * polymorphic method)
     */
    public static final int INVOKE_POLYMORPHIC = 58;

    /**
     * {@Code Tr, T0, T1...: any types; r: Tr; m: method spec;
     * y0: T0; y1: T1 ... :: r = m(y0, y1, ...)
     * <b>Note:</b> The signature of the invoked target is determined by the
     * dynamic invocation call site information.
     */
    public static final int INVOKE_CUSTOM = 59;

    /**
     * This class is uninstantiable.
     */
    private RegOps() {
        // This space intentionally left blank.
    }

    /**
     * Gets the name of the given opcode.
     *
     * @param opcode the opcode
     * @return {@code non-null;} its name
     */
    public static String opName(int opcode) {
        return switch (opcode) {
            case NOP -> "nop";
            case MOVE -> "move";
            case MOVE_PARAM -> "move-param";
            case MOVE_EXCEPTION -> "move-exception";
            case CONST -> "const";
            case GOTO -> "goto";
            case IF_EQ -> "if-eq";
            case IF_NE -> "if-ne";
            case IF_LT -> "if-lt";
            case IF_GE -> "if-ge";
            case IF_LE -> "if-le";
            case IF_GT -> "if-gt";
            case SWITCH -> "switch";
            case ADD -> "add";
            case SUB -> "sub";
            case MUL -> "mul";
            case DIV -> "div";
            case REM -> "rem";
            case NEG -> "neg";
            case AND -> "and";
            case OR -> "or";
            case XOR -> "xor";
            case SHL -> "shl";
            case SHR -> "shr";
            case USHR -> "ushr";
            case NOT -> "not";
            case CMPL -> "cmpl";
            case CMPG -> "cmpg";
            case CONV -> "conv";
            case TO_BYTE -> "to-byte";
            case TO_CHAR -> "to-char";
            case TO_SHORT -> "to-short";
            case RETURN -> "return";
            case ARRAY_LENGTH -> "array-length";
            case THROW -> "throw";
            case MONITOR_ENTER -> "monitor-enter";
            case MONITOR_EXIT -> "monitor-exit";
            case AGET -> "aget";
            case APUT -> "aput";
            case NEW_INSTANCE -> "new-instance";
            case NEW_ARRAY -> "new-array";
            case FILLED_NEW_ARRAY -> "filled-new-array";
            case CHECK_CAST -> "check-cast";
            case INSTANCE_OF -> "instance-of";
            case GET_FIELD -> "get-field";
            case GET_STATIC -> "get-static";
            case PUT_FIELD -> "put-field";
            case PUT_STATIC -> "put-static";
            case INVOKE_STATIC -> "invoke-static";
            case INVOKE_VIRTUAL -> "invoke-virtual";
            case INVOKE_SUPER -> "invoke-super";
            case INVOKE_DIRECT -> "invoke-direct";
            case INVOKE_INTERFACE -> "invoke-interface";
            case MOVE_RESULT -> "move-result";
            case MOVE_RESULT_PSEUDO -> "move-result-pseudo";
            case FILL_ARRAY_DATA -> "fill-array-data";
            case INVOKE_POLYMORPHIC -> "invoke-polymorphic";
            case INVOKE_CUSTOM -> "invoke-custom";
            default -> "unknown-" + Hex.u1(opcode);
        };

    }

    /**
     * Given an IF_* RegOp, returns the right-to-left flipped version. For
     * example, IF_GT becomes IF_LT.
     *
     * @param opcode An IF_* RegOp
     * @return flipped IF Regop
     */
    public static int flippedIfOpcode(final int opcode) {
        return switch (opcode) {
            case RegOps.IF_EQ, RegOps.IF_NE -> opcode;
            case RegOps.IF_LT -> RegOps.IF_GT;
            case RegOps.IF_GE -> RegOps.IF_LE;
            case RegOps.IF_LE -> RegOps.IF_GE;
            case RegOps.IF_GT -> RegOps.IF_LT;
            default -> throw new RuntimeException("Unrecognized IF regop: " + opcode);
        };
    }
}
