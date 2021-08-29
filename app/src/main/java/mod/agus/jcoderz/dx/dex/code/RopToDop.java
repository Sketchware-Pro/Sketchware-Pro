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

package mod.agus.jcoderz.dx.dex.code;

import java.util.HashMap;

import mod.agus.jcoderz.dx.rop.code.Insn;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.rop.cst.CstMethodHandle;
import mod.agus.jcoderz.dx.rop.cst.CstProtoRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;

/**
 * Translator from rop-level {@link mod.agus.jcoderz.dx.rop.code.Insn} instances to corresponding
 * {@link mod.agus.jcoderz.dx.dex.code.Dop} instances.
 */
public final class RopToDop {
    /** {@code non-null;} map from all the common rops to dalvik opcodes */
    private static final HashMap<mod.agus.jcoderz.dx.rop.code.Rop, mod.agus.jcoderz.dx.dex.code.Dop> MAP;

    /**
     * This class is uninstantiable.
     */
    private RopToDop() {
        // This space intentionally left blank.
    }

    /*
     * The following comment lists each opcode that should be considered
     * the "head" of an opcode chain, in terms of the process of fitting
     * an instruction's arguments to an actual opcode. This list is
     * automatically generated and may be of use in double-checking the
     * manually-generated static initialization code for this class.
     *
     * TODO: Make opcode-gen produce useful code in this case instead
     * of just a comment.
     */

    // BEGIN(first-opcodes); GENERATED AUTOMATICALLY BY opcode-gen
    //     Opcodes.NOP
    //     Opcodes.MOVE
    //     Opcodes.MOVE_WIDE
    //     Opcodes.MOVE_OBJECT
    //     Opcodes.MOVE_RESULT
    //     Opcodes.MOVE_RESULT_WIDE
    //     Opcodes.MOVE_RESULT_OBJECT
    //     Opcodes.MOVE_EXCEPTION
    //     Opcodes.RETURN_VOID
    //     Opcodes.RETURN
    //     Opcodes.RETURN_WIDE
    //     Opcodes.RETURN_OBJECT
    //     Opcodes.CONST_4
    //     Opcodes.CONST_WIDE_16
    //     Opcodes.CONST_STRING
    //     Opcodes.CONST_CLASS
    //     Opcodes.MONITOR_ENTER
    //     Opcodes.MONITOR_EXIT
    //     Opcodes.CHECK_CAST
    //     Opcodes.INSTANCE_OF
    //     Opcodes.ARRAY_LENGTH
    //     Opcodes.NEW_INSTANCE
    //     Opcodes.NEW_ARRAY
    //     Opcodes.FILLED_NEW_ARRAY
    //     Opcodes.FILL_ARRAY_DATA
    //     Opcodes.THROW
    //     Opcodes.GOTO
    //     Opcodes.PACKED_SWITCH
    //     Opcodes.SPARSE_SWITCH
    //     Opcodes.CMPL_FLOAT
    //     Opcodes.CMPG_FLOAT
    //     Opcodes.CMPL_DOUBLE
    //     Opcodes.CMPG_DOUBLE
    //     Opcodes.CMP_LONG
    //     Opcodes.IF_EQ
    //     Opcodes.IF_NE
    //     Opcodes.IF_LT
    //     Opcodes.IF_GE
    //     Opcodes.IF_GT
    //     Opcodes.IF_LE
    //     Opcodes.IF_EQZ
    //     Opcodes.IF_NEZ
    //     Opcodes.IF_LTZ
    //     Opcodes.IF_GEZ
    //     Opcodes.IF_GTZ
    //     Opcodes.IF_LEZ
    //     Opcodes.AGET
    //     Opcodes.AGET_WIDE
    //     Opcodes.AGET_OBJECT
    //     Opcodes.AGET_BOOLEAN
    //     Opcodes.AGET_BYTE
    //     Opcodes.AGET_CHAR
    //     Opcodes.AGET_SHORT
    //     Opcodes.APUT
    //     Opcodes.APUT_WIDE
    //     Opcodes.APUT_OBJECT
    //     Opcodes.APUT_BOOLEAN
    //     Opcodes.APUT_BYTE
    //     Opcodes.APUT_CHAR
    //     Opcodes.APUT_SHORT
    //     Opcodes.IGET
    //     Opcodes.IGET_WIDE
    //     Opcodes.IGET_OBJECT
    //     Opcodes.IGET_BOOLEAN
    //     Opcodes.IGET_BYTE
    //     Opcodes.IGET_CHAR
    //     Opcodes.IGET_SHORT
    //     Opcodes.IPUT
    //     Opcodes.IPUT_WIDE
    //     Opcodes.IPUT_OBJECT
    //     Opcodes.IPUT_BOOLEAN
    //     Opcodes.IPUT_BYTE
    //     Opcodes.IPUT_CHAR
    //     Opcodes.IPUT_SHORT
    //     Opcodes.SGET
    //     Opcodes.SGET_WIDE
    //     Opcodes.SGET_OBJECT
    //     Opcodes.SGET_BOOLEAN
    //     Opcodes.SGET_BYTE
    //     Opcodes.SGET_CHAR
    //     Opcodes.SGET_SHORT
    //     Opcodes.SPUT
    //     Opcodes.SPUT_WIDE
    //     Opcodes.SPUT_OBJECT
    //     Opcodes.SPUT_BOOLEAN
    //     Opcodes.SPUT_BYTE
    //     Opcodes.SPUT_CHAR
    //     Opcodes.SPUT_SHORT
    //     Opcodes.INVOKE_VIRTUAL
    //     Opcodes.INVOKE_SUPER
    //     Opcodes.INVOKE_DIRECT
    //     Opcodes.INVOKE_STATIC
    //     Opcodes.INVOKE_INTERFACE
    //     Opcodes.NEG_INT
    //     Opcodes.NOT_INT
    //     Opcodes.NEG_LONG
    //     Opcodes.NOT_LONG
    //     Opcodes.NEG_FLOAT
    //     Opcodes.NEG_DOUBLE
    //     Opcodes.INT_TO_LONG
    //     Opcodes.INT_TO_FLOAT
    //     Opcodes.INT_TO_DOUBLE
    //     Opcodes.LONG_TO_INT
    //     Opcodes.LONG_TO_FLOAT
    //     Opcodes.LONG_TO_DOUBLE
    //     Opcodes.FLOAT_TO_INT
    //     Opcodes.FLOAT_TO_LONG
    //     Opcodes.FLOAT_TO_DOUBLE
    //     Opcodes.DOUBLE_TO_INT
    //     Opcodes.DOUBLE_TO_LONG
    //     Opcodes.DOUBLE_TO_FLOAT
    //     Opcodes.INT_TO_BYTE
    //     Opcodes.INT_TO_CHAR
    //     Opcodes.INT_TO_SHORT
    //     Opcodes.ADD_INT_2ADDR
    //     Opcodes.SUB_INT_2ADDR
    //     Opcodes.MUL_INT_2ADDR
    //     Opcodes.DIV_INT_2ADDR
    //     Opcodes.REM_INT_2ADDR
    //     Opcodes.AND_INT_2ADDR
    //     Opcodes.OR_INT_2ADDR
    //     Opcodes.XOR_INT_2ADDR
    //     Opcodes.SHL_INT_2ADDR
    //     Opcodes.SHR_INT_2ADDR
    //     Opcodes.USHR_INT_2ADDR
    //     Opcodes.ADD_LONG_2ADDR
    //     Opcodes.SUB_LONG_2ADDR
    //     Opcodes.MUL_LONG_2ADDR
    //     Opcodes.DIV_LONG_2ADDR
    //     Opcodes.REM_LONG_2ADDR
    //     Opcodes.AND_LONG_2ADDR
    //     Opcodes.OR_LONG_2ADDR
    //     Opcodes.XOR_LONG_2ADDR
    //     Opcodes.SHL_LONG_2ADDR
    //     Opcodes.SHR_LONG_2ADDR
    //     Opcodes.USHR_LONG_2ADDR
    //     Opcodes.ADD_FLOAT_2ADDR
    //     Opcodes.SUB_FLOAT_2ADDR
    //     Opcodes.MUL_FLOAT_2ADDR
    //     Opcodes.DIV_FLOAT_2ADDR
    //     Opcodes.REM_FLOAT_2ADDR
    //     Opcodes.ADD_DOUBLE_2ADDR
    //     Opcodes.SUB_DOUBLE_2ADDR
    //     Opcodes.MUL_DOUBLE_2ADDR
    //     Opcodes.DIV_DOUBLE_2ADDR
    //     Opcodes.REM_DOUBLE_2ADDR
    //     Opcodes.ADD_INT_LIT8
    //     Opcodes.RSUB_INT_LIT8
    //     Opcodes.MUL_INT_LIT8
    //     Opcodes.DIV_INT_LIT8
    //     Opcodes.REM_INT_LIT8
    //     Opcodes.AND_INT_LIT8
    //     Opcodes.OR_INT_LIT8
    //     Opcodes.XOR_INT_LIT8
    //     Opcodes.SHL_INT_LIT8
    //     Opcodes.SHR_INT_LIT8
    //     Opcodes.USHR_INT_LIT8
    //     Opcodes.INVOKE_POLYMORPHIC
    //     Opcodes.INVOKE_CUSTOM
    //     Opcodes.CONST_METHOD_HANDLE
    //     Opcodes.CONST_METHOD_TYPE
    // END(first-opcodes)

    static {
        /*
         * Note: The choices made here are to pick the optimistically
         * smallest Dalvik opcode, and leave it to later processing to
         * pessimize. See the automatically-generated comment above
         * for reference.
         */
        MAP = new HashMap<mod.agus.jcoderz.dx.rop.code.Rop, mod.agus.jcoderz.dx.dex.code.Dop>(400);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.NOP,               mod.agus.jcoderz.dx.dex.code.Dops.NOP);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MOVE_INT,          mod.agus.jcoderz.dx.dex.code.Dops.MOVE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MOVE_LONG,         mod.agus.jcoderz.dx.dex.code.Dops.MOVE_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MOVE_FLOAT,        mod.agus.jcoderz.dx.dex.code.Dops.MOVE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MOVE_DOUBLE,       mod.agus.jcoderz.dx.dex.code.Dops.MOVE_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MOVE_OBJECT,       mod.agus.jcoderz.dx.dex.code.Dops.MOVE_OBJECT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MOVE_PARAM_INT,    mod.agus.jcoderz.dx.dex.code.Dops.MOVE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MOVE_PARAM_LONG,   mod.agus.jcoderz.dx.dex.code.Dops.MOVE_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MOVE_PARAM_FLOAT,  mod.agus.jcoderz.dx.dex.code.Dops.MOVE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MOVE_PARAM_DOUBLE, mod.agus.jcoderz.dx.dex.code.Dops.MOVE_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MOVE_PARAM_OBJECT, mod.agus.jcoderz.dx.dex.code.Dops.MOVE_OBJECT);

        /*
         * Note: No entry for MOVE_EXCEPTION, since it varies by
         * exception type. (That is, there is no unique instance to
         * add to the map.)
         */

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONST_INT,         mod.agus.jcoderz.dx.dex.code.Dops.CONST_4);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONST_LONG,        mod.agus.jcoderz.dx.dex.code.Dops.CONST_WIDE_16);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONST_FLOAT,       mod.agus.jcoderz.dx.dex.code.Dops.CONST_4);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONST_DOUBLE,      mod.agus.jcoderz.dx.dex.code.Dops.CONST_WIDE_16);

        /*
         * Note: No entry for CONST_OBJECT, since it needs to turn
         * into either CONST_STRING or CONST_CLASS.
         */

        /*
         * TODO: I think the only case of this is for null, and
         * const/4 should cover that.
         */
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONST_OBJECT_NOTHROW, mod.agus.jcoderz.dx.dex.code.Dops.CONST_4);

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.GOTO,                 mod.agus.jcoderz.dx.dex.code.Dops.GOTO);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_EQZ_INT,           mod.agus.jcoderz.dx.dex.code.Dops.IF_EQZ);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_NEZ_INT,           mod.agus.jcoderz.dx.dex.code.Dops.IF_NEZ);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_LTZ_INT,           mod.agus.jcoderz.dx.dex.code.Dops.IF_LTZ);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_GEZ_INT,           mod.agus.jcoderz.dx.dex.code.Dops.IF_GEZ);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_LEZ_INT,           mod.agus.jcoderz.dx.dex.code.Dops.IF_LEZ);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_GTZ_INT,           mod.agus.jcoderz.dx.dex.code.Dops.IF_GTZ);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_EQZ_OBJECT,        mod.agus.jcoderz.dx.dex.code.Dops.IF_EQZ);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_NEZ_OBJECT,        mod.agus.jcoderz.dx.dex.code.Dops.IF_NEZ);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_EQ_INT,            mod.agus.jcoderz.dx.dex.code.Dops.IF_EQ);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_NE_INT,            mod.agus.jcoderz.dx.dex.code.Dops.IF_NE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_LT_INT,            mod.agus.jcoderz.dx.dex.code.Dops.IF_LT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_GE_INT,            mod.agus.jcoderz.dx.dex.code.Dops.IF_GE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_LE_INT,            mod.agus.jcoderz.dx.dex.code.Dops.IF_LE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_GT_INT,            mod.agus.jcoderz.dx.dex.code.Dops.IF_GT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_EQ_OBJECT,         mod.agus.jcoderz.dx.dex.code.Dops.IF_EQ);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.IF_NE_OBJECT,         mod.agus.jcoderz.dx.dex.code.Dops.IF_NE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.SWITCH,               mod.agus.jcoderz.dx.dex.code.Dops.SPARSE_SWITCH);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.ADD_INT,              mod.agus.jcoderz.dx.dex.code.Dops.ADD_INT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.ADD_LONG,             mod.agus.jcoderz.dx.dex.code.Dops.ADD_LONG_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.ADD_FLOAT,            mod.agus.jcoderz.dx.dex.code.Dops.ADD_FLOAT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.ADD_DOUBLE,           mod.agus.jcoderz.dx.dex.code.Dops.ADD_DOUBLE_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.SUB_INT,              mod.agus.jcoderz.dx.dex.code.Dops.SUB_INT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.SUB_LONG,             mod.agus.jcoderz.dx.dex.code.Dops.SUB_LONG_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.SUB_FLOAT,            mod.agus.jcoderz.dx.dex.code.Dops.SUB_FLOAT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.SUB_DOUBLE,           mod.agus.jcoderz.dx.dex.code.Dops.SUB_DOUBLE_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MUL_INT,              mod.agus.jcoderz.dx.dex.code.Dops.MUL_INT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MUL_LONG,             mod.agus.jcoderz.dx.dex.code.Dops.MUL_LONG_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MUL_FLOAT,            mod.agus.jcoderz.dx.dex.code.Dops.MUL_FLOAT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MUL_DOUBLE,           mod.agus.jcoderz.dx.dex.code.Dops.MUL_DOUBLE_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.DIV_INT,              mod.agus.jcoderz.dx.dex.code.Dops.DIV_INT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.DIV_LONG,             mod.agus.jcoderz.dx.dex.code.Dops.DIV_LONG_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.DIV_FLOAT,            mod.agus.jcoderz.dx.dex.code.Dops.DIV_FLOAT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.DIV_DOUBLE,           mod.agus.jcoderz.dx.dex.code.Dops.DIV_DOUBLE_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.REM_INT,              mod.agus.jcoderz.dx.dex.code.Dops.REM_INT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.REM_LONG,             mod.agus.jcoderz.dx.dex.code.Dops.REM_LONG_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.REM_FLOAT,            mod.agus.jcoderz.dx.dex.code.Dops.REM_FLOAT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.REM_DOUBLE,           mod.agus.jcoderz.dx.dex.code.Dops.REM_DOUBLE_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.NEG_INT,              mod.agus.jcoderz.dx.dex.code.Dops.NEG_INT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.NEG_LONG,             mod.agus.jcoderz.dx.dex.code.Dops.NEG_LONG);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.NEG_FLOAT,            mod.agus.jcoderz.dx.dex.code.Dops.NEG_FLOAT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.NEG_DOUBLE,           mod.agus.jcoderz.dx.dex.code.Dops.NEG_DOUBLE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.AND_INT,              mod.agus.jcoderz.dx.dex.code.Dops.AND_INT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.AND_LONG,             mod.agus.jcoderz.dx.dex.code.Dops.AND_LONG_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.OR_INT,               mod.agus.jcoderz.dx.dex.code.Dops.OR_INT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.OR_LONG,              mod.agus.jcoderz.dx.dex.code.Dops.OR_LONG_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.XOR_INT,              mod.agus.jcoderz.dx.dex.code.Dops.XOR_INT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.XOR_LONG,             mod.agus.jcoderz.dx.dex.code.Dops.XOR_LONG_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.SHL_INT,              mod.agus.jcoderz.dx.dex.code.Dops.SHL_INT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.SHL_LONG,             mod.agus.jcoderz.dx.dex.code.Dops.SHL_LONG_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.SHR_INT,              mod.agus.jcoderz.dx.dex.code.Dops.SHR_INT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.SHR_LONG,             mod.agus.jcoderz.dx.dex.code.Dops.SHR_LONG_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.USHR_INT,             mod.agus.jcoderz.dx.dex.code.Dops.USHR_INT_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.USHR_LONG,            mod.agus.jcoderz.dx.dex.code.Dops.USHR_LONG_2ADDR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.NOT_INT,              mod.agus.jcoderz.dx.dex.code.Dops.NOT_INT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.NOT_LONG,             mod.agus.jcoderz.dx.dex.code.Dops.NOT_LONG);

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.ADD_CONST_INT,        mod.agus.jcoderz.dx.dex.code.Dops.ADD_INT_LIT8);
        // Note: No dalvik ops for other types of add_const.

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.SUB_CONST_INT,        mod.agus.jcoderz.dx.dex.code.Dops.RSUB_INT_LIT8);
        /*
         * Note: No dalvik ops for any type of sub_const; instead
         * there's a *reverse* sub (constant - reg) for ints only.
         */

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MUL_CONST_INT,        mod.agus.jcoderz.dx.dex.code.Dops.MUL_INT_LIT8);
        // Note: No dalvik ops for other types of mul_const.

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.DIV_CONST_INT,        mod.agus.jcoderz.dx.dex.code.Dops.DIV_INT_LIT8);
        // Note: No dalvik ops for other types of div_const.

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.REM_CONST_INT,        mod.agus.jcoderz.dx.dex.code.Dops.REM_INT_LIT8);
        // Note: No dalvik ops for other types of rem_const.

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.AND_CONST_INT,        mod.agus.jcoderz.dx.dex.code.Dops.AND_INT_LIT8);
        // Note: No dalvik op for and_const_long.

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.OR_CONST_INT,         mod.agus.jcoderz.dx.dex.code.Dops.OR_INT_LIT8);
        // Note: No dalvik op for or_const_long.

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.XOR_CONST_INT,        mod.agus.jcoderz.dx.dex.code.Dops.XOR_INT_LIT8);
        // Note: No dalvik op for xor_const_long.

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.SHL_CONST_INT,        mod.agus.jcoderz.dx.dex.code.Dops.SHL_INT_LIT8);
        // Note: No dalvik op for shl_const_long.

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.SHR_CONST_INT,        mod.agus.jcoderz.dx.dex.code.Dops.SHR_INT_LIT8);
        // Note: No dalvik op for shr_const_long.

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.USHR_CONST_INT,       mod.agus.jcoderz.dx.dex.code.Dops.USHR_INT_LIT8);
        // Note: No dalvik op for shr_const_long.

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CMPL_LONG,            mod.agus.jcoderz.dx.dex.code.Dops.CMP_LONG);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CMPL_FLOAT,           mod.agus.jcoderz.dx.dex.code.Dops.CMPL_FLOAT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CMPL_DOUBLE,          mod.agus.jcoderz.dx.dex.code.Dops.CMPL_DOUBLE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CMPG_FLOAT,           mod.agus.jcoderz.dx.dex.code.Dops.CMPG_FLOAT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CMPG_DOUBLE,          mod.agus.jcoderz.dx.dex.code.Dops.CMPG_DOUBLE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONV_L2I,             mod.agus.jcoderz.dx.dex.code.Dops.LONG_TO_INT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONV_F2I,             mod.agus.jcoderz.dx.dex.code.Dops.FLOAT_TO_INT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONV_D2I,             mod.agus.jcoderz.dx.dex.code.Dops.DOUBLE_TO_INT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONV_I2L,             mod.agus.jcoderz.dx.dex.code.Dops.INT_TO_LONG);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONV_F2L,             mod.agus.jcoderz.dx.dex.code.Dops.FLOAT_TO_LONG);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONV_D2L,             mod.agus.jcoderz.dx.dex.code.Dops.DOUBLE_TO_LONG);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONV_I2F,             mod.agus.jcoderz.dx.dex.code.Dops.INT_TO_FLOAT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONV_L2F,             mod.agus.jcoderz.dx.dex.code.Dops.LONG_TO_FLOAT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONV_D2F,             mod.agus.jcoderz.dx.dex.code.Dops.DOUBLE_TO_FLOAT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONV_I2D,             mod.agus.jcoderz.dx.dex.code.Dops.INT_TO_DOUBLE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONV_L2D,             mod.agus.jcoderz.dx.dex.code.Dops.LONG_TO_DOUBLE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CONV_F2D,             mod.agus.jcoderz.dx.dex.code.Dops.FLOAT_TO_DOUBLE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.TO_BYTE,              mod.agus.jcoderz.dx.dex.code.Dops.INT_TO_BYTE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.TO_CHAR,              mod.agus.jcoderz.dx.dex.code.Dops.INT_TO_CHAR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.TO_SHORT,             mod.agus.jcoderz.dx.dex.code.Dops.INT_TO_SHORT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.RETURN_VOID,          mod.agus.jcoderz.dx.dex.code.Dops.RETURN_VOID);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.RETURN_INT,           mod.agus.jcoderz.dx.dex.code.Dops.RETURN);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.RETURN_LONG,          mod.agus.jcoderz.dx.dex.code.Dops.RETURN_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.RETURN_FLOAT,         mod.agus.jcoderz.dx.dex.code.Dops.RETURN);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.RETURN_DOUBLE,        mod.agus.jcoderz.dx.dex.code.Dops.RETURN_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.RETURN_OBJECT,        mod.agus.jcoderz.dx.dex.code.Dops.RETURN_OBJECT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.ARRAY_LENGTH,         mod.agus.jcoderz.dx.dex.code.Dops.ARRAY_LENGTH);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.THROW,                mod.agus.jcoderz.dx.dex.code.Dops.THROW);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MONITOR_ENTER,        mod.agus.jcoderz.dx.dex.code.Dops.MONITOR_ENTER);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.MONITOR_EXIT,         mod.agus.jcoderz.dx.dex.code.Dops.MONITOR_EXIT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.AGET_INT,             mod.agus.jcoderz.dx.dex.code.Dops.AGET);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.AGET_LONG,            mod.agus.jcoderz.dx.dex.code.Dops.AGET_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.AGET_FLOAT,           mod.agus.jcoderz.dx.dex.code.Dops.AGET);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.AGET_DOUBLE,          mod.agus.jcoderz.dx.dex.code.Dops.AGET_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.AGET_OBJECT,          mod.agus.jcoderz.dx.dex.code.Dops.AGET_OBJECT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.AGET_BOOLEAN,         mod.agus.jcoderz.dx.dex.code.Dops.AGET_BOOLEAN);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.AGET_BYTE,            mod.agus.jcoderz.dx.dex.code.Dops.AGET_BYTE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.AGET_CHAR,            mod.agus.jcoderz.dx.dex.code.Dops.AGET_CHAR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.AGET_SHORT,           mod.agus.jcoderz.dx.dex.code.Dops.AGET_SHORT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.APUT_INT,             mod.agus.jcoderz.dx.dex.code.Dops.APUT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.APUT_LONG,            mod.agus.jcoderz.dx.dex.code.Dops.APUT_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.APUT_FLOAT,           mod.agus.jcoderz.dx.dex.code.Dops.APUT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.APUT_DOUBLE,          mod.agus.jcoderz.dx.dex.code.Dops.APUT_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.APUT_OBJECT,          mod.agus.jcoderz.dx.dex.code.Dops.APUT_OBJECT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.APUT_BOOLEAN,         mod.agus.jcoderz.dx.dex.code.Dops.APUT_BOOLEAN);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.APUT_BYTE,            mod.agus.jcoderz.dx.dex.code.Dops.APUT_BYTE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.APUT_CHAR,            mod.agus.jcoderz.dx.dex.code.Dops.APUT_CHAR);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.APUT_SHORT,           mod.agus.jcoderz.dx.dex.code.Dops.APUT_SHORT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.NEW_INSTANCE,         mod.agus.jcoderz.dx.dex.code.Dops.NEW_INSTANCE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.CHECK_CAST,           mod.agus.jcoderz.dx.dex.code.Dops.CHECK_CAST);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.INSTANCE_OF,          mod.agus.jcoderz.dx.dex.code.Dops.INSTANCE_OF);

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.GET_FIELD_LONG,       mod.agus.jcoderz.dx.dex.code.Dops.IGET_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.GET_FIELD_FLOAT,      mod.agus.jcoderz.dx.dex.code.Dops.IGET);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.GET_FIELD_DOUBLE,     mod.agus.jcoderz.dx.dex.code.Dops.IGET_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.GET_FIELD_OBJECT,     mod.agus.jcoderz.dx.dex.code.Dops.IGET_OBJECT);
        /*
         * Note: No map entries for get_field_* for non-long integral types,
         * since they need to be handled specially (see dopFor() below).
         */

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.GET_STATIC_LONG,      mod.agus.jcoderz.dx.dex.code.Dops.SGET_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.GET_STATIC_FLOAT,     mod.agus.jcoderz.dx.dex.code.Dops.SGET);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.GET_STATIC_DOUBLE,    mod.agus.jcoderz.dx.dex.code.Dops.SGET_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.GET_STATIC_OBJECT,    mod.agus.jcoderz.dx.dex.code.Dops.SGET_OBJECT);
        /*
         * Note: No map entries for get_static* for non-long integral types,
         * since they need to be handled specially (see dopFor() below).
         */

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.PUT_FIELD_LONG,       mod.agus.jcoderz.dx.dex.code.Dops.IPUT_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.PUT_FIELD_FLOAT,      mod.agus.jcoderz.dx.dex.code.Dops.IPUT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.PUT_FIELD_DOUBLE,     mod.agus.jcoderz.dx.dex.code.Dops.IPUT_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.PUT_FIELD_OBJECT,     mod.agus.jcoderz.dx.dex.code.Dops.IPUT_OBJECT);
        /*
         * Note: No map entries for put_field_* for non-long integral types,
         * since they need to be handled specially (see dopFor() below).
         */

        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.PUT_STATIC_LONG,      mod.agus.jcoderz.dx.dex.code.Dops.SPUT_WIDE);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.PUT_STATIC_FLOAT,     mod.agus.jcoderz.dx.dex.code.Dops.SPUT);
        MAP.put(mod.agus.jcoderz.dx.rop.code.Rops.PUT_STATIC_DOUBLE,    mod.agus.jcoderz.dx.dex.code.Dops.SPUT_WIDE);
        MAP.put(Rops.PUT_STATIC_OBJECT,    mod.agus.jcoderz.dx.dex.code.Dops.SPUT_OBJECT);
        /*
         * Note: No map entries for put_static* for non-long integral types,
         * since they need to be handled specially (see dopFor() below).
         */

        /*
         * Note: No map entries for invoke*, new_array, and
         * filled_new_array, since they need to be handled specially
         * (see dopFor() below).
         */
    }

    /**
     * Returns the dalvik opcode appropriate for the given register-based
     * instruction.
     *
     * @param insn {@code non-null;} the original instruction
     * @return the corresponding dalvik opcode; one of the constants in
     * {@link mod.agus.jcoderz.dx.dex.code.Dops}
     */
    public static mod.agus.jcoderz.dx.dex.code.Dop dopFor(Insn insn) {
        Rop rop = insn.getOpcode();

        /*
         * First, just try looking up the rop in the MAP of easy
         * cases.
         */
        Dop result = MAP.get(rop);
        if (result != null) {
            return result;
        }

        /*
         * There was no easy case for the rop, so look up the opcode, and
         * do something special for each:
         *
         * The move_exception, new_array, filled_new_array, and
         * invoke* opcodes won't be found in MAP, since they'll each
         * have different source and/or result register types / lists.
         *
         * The get* and put* opcodes for (non-long) integral types
         * aren't in the map, since the type signatures aren't
         * sufficient to distinguish between the types (the salient
         * source or result will always be just "int").
         *
         * And const instruction need to distinguish between strings and
         * classes.
         */

        switch (rop.getOpcode()) {
            case mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_EXCEPTION:     return mod.agus.jcoderz.dx.dex.code.Dops.MOVE_EXCEPTION;
            case mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_STATIC:      return mod.agus.jcoderz.dx.dex.code.Dops.INVOKE_STATIC;
            case mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_VIRTUAL:     return mod.agus.jcoderz.dx.dex.code.Dops.INVOKE_VIRTUAL;
            case mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_SUPER:       return mod.agus.jcoderz.dx.dex.code.Dops.INVOKE_SUPER;
            case mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_DIRECT:      return mod.agus.jcoderz.dx.dex.code.Dops.INVOKE_DIRECT;
            case mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_INTERFACE:   return mod.agus.jcoderz.dx.dex.code.Dops.INVOKE_INTERFACE;
            case mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_POLYMORPHIC: return mod.agus.jcoderz.dx.dex.code.Dops.INVOKE_POLYMORPHIC;
            case mod.agus.jcoderz.dx.rop.code.RegOps.INVOKE_CUSTOM:      return mod.agus.jcoderz.dx.dex.code.Dops.INVOKE_CUSTOM;
            case mod.agus.jcoderz.dx.rop.code.RegOps.NEW_ARRAY:          return mod.agus.jcoderz.dx.dex.code.Dops.NEW_ARRAY;
            case mod.agus.jcoderz.dx.rop.code.RegOps.FILLED_NEW_ARRAY:   return mod.agus.jcoderz.dx.dex.code.Dops.FILLED_NEW_ARRAY;
            case mod.agus.jcoderz.dx.rop.code.RegOps.FILL_ARRAY_DATA:    return mod.agus.jcoderz.dx.dex.code.Dops.FILL_ARRAY_DATA;
            case mod.agus.jcoderz.dx.rop.code.RegOps.MOVE_RESULT: {
                RegisterSpec resultReg = insn.getResult();

                if (resultReg == null) {
                    return mod.agus.jcoderz.dx.dex.code.Dops.NOP;
                } else {
                    switch (resultReg.getBasicType()) {
                        case mod.agus.jcoderz.dx.rop.type.Type.BT_INT:
                        case mod.agus.jcoderz.dx.rop.type.Type.BT_FLOAT:
                        case mod.agus.jcoderz.dx.rop.type.Type.BT_BOOLEAN:
                        case mod.agus.jcoderz.dx.rop.type.Type.BT_BYTE:
                        case mod.agus.jcoderz.dx.rop.type.Type.BT_CHAR:
                        case mod.agus.jcoderz.dx.rop.type.Type.BT_SHORT:
                            return mod.agus.jcoderz.dx.dex.code.Dops.MOVE_RESULT;
                        case mod.agus.jcoderz.dx.rop.type.Type.BT_LONG:
                        case mod.agus.jcoderz.dx.rop.type.Type.BT_DOUBLE:
                            return mod.agus.jcoderz.dx.dex.code.Dops.MOVE_RESULT_WIDE;
                        case mod.agus.jcoderz.dx.rop.type.Type.BT_OBJECT:
                            return mod.agus.jcoderz.dx.dex.code.Dops.MOVE_RESULT_OBJECT;
                        default: {
                            throw new RuntimeException("Unexpected basic type");
                        }
                    }
                }
            }

            case mod.agus.jcoderz.dx.rop.code.RegOps.GET_FIELD: {
                mod.agus.jcoderz.dx.rop.cst.CstFieldRef ref =
                    (mod.agus.jcoderz.dx.rop.cst.CstFieldRef) ((mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn) insn).getConstant();
                int basicType = ref.getBasicType();
                switch (basicType) {
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_BOOLEAN: return mod.agus.jcoderz.dx.dex.code.Dops.IGET_BOOLEAN;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_BYTE:    return mod.agus.jcoderz.dx.dex.code.Dops.IGET_BYTE;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_CHAR:    return mod.agus.jcoderz.dx.dex.code.Dops.IGET_CHAR;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_SHORT:   return mod.agus.jcoderz.dx.dex.code.Dops.IGET_SHORT;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_INT:     return mod.agus.jcoderz.dx.dex.code.Dops.IGET;
                }
                break;
            }
            case mod.agus.jcoderz.dx.rop.code.RegOps.PUT_FIELD: {
                mod.agus.jcoderz.dx.rop.cst.CstFieldRef ref =
                    (mod.agus.jcoderz.dx.rop.cst.CstFieldRef) ((mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn) insn).getConstant();
                int basicType = ref.getBasicType();
                switch (basicType) {
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_BOOLEAN: return mod.agus.jcoderz.dx.dex.code.Dops.IPUT_BOOLEAN;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_BYTE:    return mod.agus.jcoderz.dx.dex.code.Dops.IPUT_BYTE;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_CHAR:    return mod.agus.jcoderz.dx.dex.code.Dops.IPUT_CHAR;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_SHORT:   return mod.agus.jcoderz.dx.dex.code.Dops.IPUT_SHORT;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_INT:     return mod.agus.jcoderz.dx.dex.code.Dops.IPUT;
                }
                break;
            }
            case mod.agus.jcoderz.dx.rop.code.RegOps.GET_STATIC: {
                mod.agus.jcoderz.dx.rop.cst.CstFieldRef ref =
                    (mod.agus.jcoderz.dx.rop.cst.CstFieldRef) ((mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn) insn).getConstant();
                int basicType = ref.getBasicType();
                switch (basicType) {
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_BOOLEAN: return mod.agus.jcoderz.dx.dex.code.Dops.SGET_BOOLEAN;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_BYTE:    return mod.agus.jcoderz.dx.dex.code.Dops.SGET_BYTE;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_CHAR:    return mod.agus.jcoderz.dx.dex.code.Dops.SGET_CHAR;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_SHORT:   return mod.agus.jcoderz.dx.dex.code.Dops.SGET_SHORT;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_INT:     return mod.agus.jcoderz.dx.dex.code.Dops.SGET;
                }
                break;
            }
            case mod.agus.jcoderz.dx.rop.code.RegOps.PUT_STATIC: {
                mod.agus.jcoderz.dx.rop.cst.CstFieldRef ref =
                    (CstFieldRef) ((mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn) insn).getConstant();
                int basicType = ref.getBasicType();
                switch (basicType) {
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_BOOLEAN: return mod.agus.jcoderz.dx.dex.code.Dops.SPUT_BOOLEAN;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_BYTE:    return mod.agus.jcoderz.dx.dex.code.Dops.SPUT_BYTE;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_CHAR:    return mod.agus.jcoderz.dx.dex.code.Dops.SPUT_CHAR;
                    case mod.agus.jcoderz.dx.rop.type.Type.BT_SHORT:   return mod.agus.jcoderz.dx.dex.code.Dops.SPUT_SHORT;
                    case Type.BT_INT:     return mod.agus.jcoderz.dx.dex.code.Dops.SPUT;
                }
                break;
            }
            case RegOps.CONST: {
                Constant cst = ((ThrowingCstInsn) insn).getConstant();
                if (cst instanceof CstType) {
                    return mod.agus.jcoderz.dx.dex.code.Dops.CONST_CLASS;
                } else if (cst instanceof CstString) {
                    return mod.agus.jcoderz.dx.dex.code.Dops.CONST_STRING;
                } else if (cst instanceof CstMethodHandle) {
                    return mod.agus.jcoderz.dx.dex.code.Dops.CONST_METHOD_HANDLE;
                } else if (cst instanceof CstProtoRef) {
                    return Dops.CONST_METHOD_TYPE;
                } else {
                    throw new RuntimeException("Unexpected constant type");
                }
            }
        }

        throw new RuntimeException("unknown rop: " + rop);
    }
}
