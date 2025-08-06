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

import mod.agus.jcoderz.dx.dex.DexOptions;
import mod.agus.jcoderz.dx.dex.code.form.Form10t;
import mod.agus.jcoderz.dx.dex.code.form.Form10x;
import mod.agus.jcoderz.dx.dex.code.form.Form11n;
import mod.agus.jcoderz.dx.dex.code.form.Form11x;
import mod.agus.jcoderz.dx.dex.code.form.Form12x;
import mod.agus.jcoderz.dx.dex.code.form.Form20t;
import mod.agus.jcoderz.dx.dex.code.form.Form21c;
import mod.agus.jcoderz.dx.dex.code.form.Form21h;
import mod.agus.jcoderz.dx.dex.code.form.Form21s;
import mod.agus.jcoderz.dx.dex.code.form.Form21t;
import mod.agus.jcoderz.dx.dex.code.form.Form22b;
import mod.agus.jcoderz.dx.dex.code.form.Form22c;
import mod.agus.jcoderz.dx.dex.code.form.Form22s;
import mod.agus.jcoderz.dx.dex.code.form.Form22t;
import mod.agus.jcoderz.dx.dex.code.form.Form22x;
import mod.agus.jcoderz.dx.dex.code.form.Form23x;
import mod.agus.jcoderz.dx.dex.code.form.Form30t;
import mod.agus.jcoderz.dx.dex.code.form.Form31c;
import mod.agus.jcoderz.dx.dex.code.form.Form31i;
import mod.agus.jcoderz.dx.dex.code.form.Form31t;
import mod.agus.jcoderz.dx.dex.code.form.Form32x;
import mod.agus.jcoderz.dx.dex.code.form.Form35c;
import mod.agus.jcoderz.dx.dex.code.form.Form3rc;
import mod.agus.jcoderz.dx.dex.code.form.Form45cc;
import mod.agus.jcoderz.dx.dex.code.form.Form4rcc;
import mod.agus.jcoderz.dx.dex.code.form.Form51l;
import mod.agus.jcoderz.dx.dex.code.form.SpecialFormat;
import mod.agus.jcoderz.dx.io.Opcodes;

/**
 * Standard instances of {@link mod.agus.jcoderz.dx.dex.code.Dop} and utility methods for getting
 * them.
 */
public final class Dops {
    /** {@code non-null;} array containing all the standard instances */
    private static final mod.agus.jcoderz.dx.dex.code.Dop[] DOPS;

    /**
     * pseudo-opcode used for nonstandard formatted "instructions"
     * (which are mostly not actually instructions, though they do
     * appear in instruction lists). TODO: Retire the usage of this
     * constant.
     */
    public static final mod.agus.jcoderz.dx.dex.code.Dop SPECIAL_FORMAT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SPECIAL_FORMAT, mod.agus.jcoderz.dx.io.Opcodes.SPECIAL_FORMAT,
                mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, SpecialFormat.THE_ONE, false);

    // BEGIN(dops); GENERATED AUTOMATICALLY BY opcode-gen
    public static final mod.agus.jcoderz.dx.dex.code.Dop NOP =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.NOP, mod.agus.jcoderz.dx.io.Opcodes.NOP,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form10x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MOVE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MOVE, mod.agus.jcoderz.dx.io.Opcodes.MOVE,
            mod.agus.jcoderz.dx.io.Opcodes.MOVE_FROM16, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MOVE_FROM16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MOVE_FROM16, mod.agus.jcoderz.dx.io.Opcodes.MOVE,
            mod.agus.jcoderz.dx.io.Opcodes.MOVE_16, mod.agus.jcoderz.dx.dex.code.form.Form22x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MOVE_16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MOVE_16, mod.agus.jcoderz.dx.io.Opcodes.MOVE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form32x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MOVE_WIDE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MOVE_WIDE, mod.agus.jcoderz.dx.io.Opcodes.MOVE_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.MOVE_WIDE_FROM16, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MOVE_WIDE_FROM16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MOVE_WIDE_FROM16, mod.agus.jcoderz.dx.io.Opcodes.MOVE_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.MOVE_WIDE_16, mod.agus.jcoderz.dx.dex.code.form.Form22x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MOVE_WIDE_16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MOVE_WIDE_16, mod.agus.jcoderz.dx.io.Opcodes.MOVE_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form32x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MOVE_OBJECT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MOVE_OBJECT, mod.agus.jcoderz.dx.io.Opcodes.MOVE_OBJECT,
            mod.agus.jcoderz.dx.io.Opcodes.MOVE_OBJECT_FROM16, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MOVE_OBJECT_FROM16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MOVE_OBJECT_FROM16, mod.agus.jcoderz.dx.io.Opcodes.MOVE_OBJECT,
            mod.agus.jcoderz.dx.io.Opcodes.MOVE_OBJECT_16, Form22x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MOVE_OBJECT_16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MOVE_OBJECT_16, mod.agus.jcoderz.dx.io.Opcodes.MOVE_OBJECT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form32x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MOVE_RESULT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MOVE_RESULT, mod.agus.jcoderz.dx.io.Opcodes.MOVE_RESULT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form11x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MOVE_RESULT_WIDE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MOVE_RESULT_WIDE, mod.agus.jcoderz.dx.io.Opcodes.MOVE_RESULT_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form11x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MOVE_RESULT_OBJECT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MOVE_RESULT_OBJECT, mod.agus.jcoderz.dx.io.Opcodes.MOVE_RESULT_OBJECT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form11x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MOVE_EXCEPTION =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MOVE_EXCEPTION, mod.agus.jcoderz.dx.io.Opcodes.MOVE_EXCEPTION,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form11x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop RETURN_VOID =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.RETURN_VOID, mod.agus.jcoderz.dx.io.Opcodes.RETURN_VOID,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form10x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop RETURN =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.RETURN, mod.agus.jcoderz.dx.io.Opcodes.RETURN,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form11x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop RETURN_WIDE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.RETURN_WIDE, mod.agus.jcoderz.dx.io.Opcodes.RETURN_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form11x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop RETURN_OBJECT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.RETURN_OBJECT, mod.agus.jcoderz.dx.io.Opcodes.RETURN_OBJECT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form11x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CONST_4 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CONST_4, mod.agus.jcoderz.dx.io.Opcodes.CONST,
            mod.agus.jcoderz.dx.io.Opcodes.CONST_16, Form11n.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CONST_16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CONST_16, mod.agus.jcoderz.dx.io.Opcodes.CONST,
            mod.agus.jcoderz.dx.io.Opcodes.CONST_HIGH16, mod.agus.jcoderz.dx.dex.code.form.Form21s.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CONST =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CONST, mod.agus.jcoderz.dx.io.Opcodes.CONST,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form31i.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CONST_HIGH16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CONST_HIGH16, mod.agus.jcoderz.dx.io.Opcodes.CONST,
            mod.agus.jcoderz.dx.io.Opcodes.CONST, mod.agus.jcoderz.dx.dex.code.form.Form21h.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CONST_WIDE_16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CONST_WIDE_16, mod.agus.jcoderz.dx.io.Opcodes.CONST_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.CONST_WIDE_HIGH16, Form21s.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CONST_WIDE_32 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CONST_WIDE_32, mod.agus.jcoderz.dx.io.Opcodes.CONST_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.CONST_WIDE, Form31i.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CONST_WIDE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CONST_WIDE, mod.agus.jcoderz.dx.io.Opcodes.CONST_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form51l.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CONST_WIDE_HIGH16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CONST_WIDE_HIGH16, mod.agus.jcoderz.dx.io.Opcodes.CONST_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.CONST_WIDE_32, Form21h.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CONST_STRING =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CONST_STRING, mod.agus.jcoderz.dx.io.Opcodes.CONST_STRING,
            mod.agus.jcoderz.dx.io.Opcodes.CONST_STRING_JUMBO, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CONST_STRING_JUMBO =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CONST_STRING_JUMBO, mod.agus.jcoderz.dx.io.Opcodes.CONST_STRING,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form31c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CONST_CLASS =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CONST_CLASS, mod.agus.jcoderz.dx.io.Opcodes.CONST_CLASS,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MONITOR_ENTER =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MONITOR_ENTER, mod.agus.jcoderz.dx.io.Opcodes.MONITOR_ENTER,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form11x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MONITOR_EXIT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MONITOR_EXIT, mod.agus.jcoderz.dx.io.Opcodes.MONITOR_EXIT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form11x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CHECK_CAST =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CHECK_CAST, mod.agus.jcoderz.dx.io.Opcodes.CHECK_CAST,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INSTANCE_OF =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INSTANCE_OF, mod.agus.jcoderz.dx.io.Opcodes.INSTANCE_OF,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop ARRAY_LENGTH =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.ARRAY_LENGTH, mod.agus.jcoderz.dx.io.Opcodes.ARRAY_LENGTH,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop NEW_INSTANCE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.NEW_INSTANCE, mod.agus.jcoderz.dx.io.Opcodes.NEW_INSTANCE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop NEW_ARRAY =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.NEW_ARRAY, mod.agus.jcoderz.dx.io.Opcodes.NEW_ARRAY,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop FILLED_NEW_ARRAY =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.FILLED_NEW_ARRAY, mod.agus.jcoderz.dx.io.Opcodes.FILLED_NEW_ARRAY,
            mod.agus.jcoderz.dx.io.Opcodes.FILLED_NEW_ARRAY_RANGE, mod.agus.jcoderz.dx.dex.code.form.Form35c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop FILLED_NEW_ARRAY_RANGE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.FILLED_NEW_ARRAY_RANGE, mod.agus.jcoderz.dx.io.Opcodes.FILLED_NEW_ARRAY,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form3rc.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop FILL_ARRAY_DATA =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.FILL_ARRAY_DATA, mod.agus.jcoderz.dx.io.Opcodes.FILL_ARRAY_DATA,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form31t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop THROW =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.THROW, mod.agus.jcoderz.dx.io.Opcodes.THROW,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form11x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop GOTO =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.GOTO, mod.agus.jcoderz.dx.io.Opcodes.GOTO,
            mod.agus.jcoderz.dx.io.Opcodes.GOTO_16, Form10t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop GOTO_16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.GOTO_16, mod.agus.jcoderz.dx.io.Opcodes.GOTO,
            mod.agus.jcoderz.dx.io.Opcodes.GOTO_32, Form20t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop GOTO_32 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.GOTO_32, mod.agus.jcoderz.dx.io.Opcodes.GOTO,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form30t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop PACKED_SWITCH =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.PACKED_SWITCH, mod.agus.jcoderz.dx.io.Opcodes.PACKED_SWITCH,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form31t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SPARSE_SWITCH =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SPARSE_SWITCH, mod.agus.jcoderz.dx.io.Opcodes.SPARSE_SWITCH,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form31t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CMPL_FLOAT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CMPL_FLOAT, mod.agus.jcoderz.dx.io.Opcodes.CMPL_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CMPG_FLOAT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CMPG_FLOAT, mod.agus.jcoderz.dx.io.Opcodes.CMPG_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CMPL_DOUBLE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CMPL_DOUBLE, mod.agus.jcoderz.dx.io.Opcodes.CMPL_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CMPG_DOUBLE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CMPG_DOUBLE, mod.agus.jcoderz.dx.io.Opcodes.CMPG_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CMP_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CMP_LONG, mod.agus.jcoderz.dx.io.Opcodes.CMP_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IF_EQ =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IF_EQ, mod.agus.jcoderz.dx.io.Opcodes.IF_EQ,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IF_NE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IF_NE, mod.agus.jcoderz.dx.io.Opcodes.IF_NE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IF_LT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IF_LT, mod.agus.jcoderz.dx.io.Opcodes.IF_LT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IF_GE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IF_GE, mod.agus.jcoderz.dx.io.Opcodes.IF_GE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IF_GT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IF_GT, mod.agus.jcoderz.dx.io.Opcodes.IF_GT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IF_LE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IF_LE, mod.agus.jcoderz.dx.io.Opcodes.IF_LE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form22t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IF_EQZ =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IF_EQZ, mod.agus.jcoderz.dx.io.Opcodes.IF_EQZ,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IF_NEZ =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IF_NEZ, mod.agus.jcoderz.dx.io.Opcodes.IF_NEZ,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IF_LTZ =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IF_LTZ, mod.agus.jcoderz.dx.io.Opcodes.IF_LTZ,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IF_GEZ =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IF_GEZ, mod.agus.jcoderz.dx.io.Opcodes.IF_GEZ,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IF_GTZ =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IF_GTZ, mod.agus.jcoderz.dx.io.Opcodes.IF_GTZ,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IF_LEZ =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IF_LEZ, mod.agus.jcoderz.dx.io.Opcodes.IF_LEZ,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form21t.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop AGET =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.AGET, mod.agus.jcoderz.dx.io.Opcodes.AGET,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop AGET_WIDE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.AGET_WIDE, mod.agus.jcoderz.dx.io.Opcodes.AGET_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop AGET_OBJECT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.AGET_OBJECT, mod.agus.jcoderz.dx.io.Opcodes.AGET_OBJECT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop AGET_BOOLEAN =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.AGET_BOOLEAN, mod.agus.jcoderz.dx.io.Opcodes.AGET_BOOLEAN,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop AGET_BYTE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.AGET_BYTE, mod.agus.jcoderz.dx.io.Opcodes.AGET_BYTE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop AGET_CHAR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.AGET_CHAR, mod.agus.jcoderz.dx.io.Opcodes.AGET_CHAR,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop AGET_SHORT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.AGET_SHORT, mod.agus.jcoderz.dx.io.Opcodes.AGET_SHORT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop APUT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.APUT, mod.agus.jcoderz.dx.io.Opcodes.APUT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop APUT_WIDE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.APUT_WIDE, mod.agus.jcoderz.dx.io.Opcodes.APUT_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop APUT_OBJECT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.APUT_OBJECT, mod.agus.jcoderz.dx.io.Opcodes.APUT_OBJECT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop APUT_BOOLEAN =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.APUT_BOOLEAN, mod.agus.jcoderz.dx.io.Opcodes.APUT_BOOLEAN,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop APUT_BYTE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.APUT_BYTE, mod.agus.jcoderz.dx.io.Opcodes.APUT_BYTE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop APUT_CHAR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.APUT_CHAR, mod.agus.jcoderz.dx.io.Opcodes.APUT_CHAR,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop APUT_SHORT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.APUT_SHORT, mod.agus.jcoderz.dx.io.Opcodes.APUT_SHORT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IGET =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IGET, mod.agus.jcoderz.dx.io.Opcodes.IGET,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IGET_WIDE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IGET_WIDE, mod.agus.jcoderz.dx.io.Opcodes.IGET_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IGET_OBJECT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IGET_OBJECT, mod.agus.jcoderz.dx.io.Opcodes.IGET_OBJECT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IGET_BOOLEAN =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IGET_BOOLEAN, mod.agus.jcoderz.dx.io.Opcodes.IGET_BOOLEAN,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IGET_BYTE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IGET_BYTE, mod.agus.jcoderz.dx.io.Opcodes.IGET_BYTE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IGET_CHAR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IGET_CHAR, mod.agus.jcoderz.dx.io.Opcodes.IGET_CHAR,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IGET_SHORT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IGET_SHORT, mod.agus.jcoderz.dx.io.Opcodes.IGET_SHORT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IPUT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IPUT, mod.agus.jcoderz.dx.io.Opcodes.IPUT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IPUT_WIDE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IPUT_WIDE, mod.agus.jcoderz.dx.io.Opcodes.IPUT_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IPUT_OBJECT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IPUT_OBJECT, mod.agus.jcoderz.dx.io.Opcodes.IPUT_OBJECT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IPUT_BOOLEAN =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IPUT_BOOLEAN, mod.agus.jcoderz.dx.io.Opcodes.IPUT_BOOLEAN,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IPUT_BYTE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IPUT_BYTE, mod.agus.jcoderz.dx.io.Opcodes.IPUT_BYTE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IPUT_CHAR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IPUT_CHAR, mod.agus.jcoderz.dx.io.Opcodes.IPUT_CHAR,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop IPUT_SHORT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.IPUT_SHORT, mod.agus.jcoderz.dx.io.Opcodes.IPUT_SHORT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form22c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SGET =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SGET, mod.agus.jcoderz.dx.io.Opcodes.SGET,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SGET_WIDE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SGET_WIDE, mod.agus.jcoderz.dx.io.Opcodes.SGET_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SGET_OBJECT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SGET_OBJECT, mod.agus.jcoderz.dx.io.Opcodes.SGET_OBJECT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SGET_BOOLEAN =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SGET_BOOLEAN, mod.agus.jcoderz.dx.io.Opcodes.SGET_BOOLEAN,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SGET_BYTE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SGET_BYTE, mod.agus.jcoderz.dx.io.Opcodes.SGET_BYTE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SGET_CHAR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SGET_CHAR, mod.agus.jcoderz.dx.io.Opcodes.SGET_CHAR,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SGET_SHORT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SGET_SHORT, mod.agus.jcoderz.dx.io.Opcodes.SGET_SHORT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SPUT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SPUT, mod.agus.jcoderz.dx.io.Opcodes.SPUT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SPUT_WIDE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SPUT_WIDE, mod.agus.jcoderz.dx.io.Opcodes.SPUT_WIDE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SPUT_OBJECT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SPUT_OBJECT, mod.agus.jcoderz.dx.io.Opcodes.SPUT_OBJECT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SPUT_BOOLEAN =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SPUT_BOOLEAN, mod.agus.jcoderz.dx.io.Opcodes.SPUT_BOOLEAN,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SPUT_BYTE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SPUT_BYTE, mod.agus.jcoderz.dx.io.Opcodes.SPUT_BYTE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SPUT_CHAR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SPUT_CHAR, mod.agus.jcoderz.dx.io.Opcodes.SPUT_CHAR,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SPUT_SHORT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SPUT_SHORT, mod.agus.jcoderz.dx.io.Opcodes.SPUT_SHORT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INVOKE_VIRTUAL =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INVOKE_VIRTUAL, mod.agus.jcoderz.dx.io.Opcodes.INVOKE_VIRTUAL,
            mod.agus.jcoderz.dx.io.Opcodes.INVOKE_VIRTUAL_RANGE, mod.agus.jcoderz.dx.dex.code.form.Form35c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INVOKE_SUPER =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INVOKE_SUPER, mod.agus.jcoderz.dx.io.Opcodes.INVOKE_SUPER,
            mod.agus.jcoderz.dx.io.Opcodes.INVOKE_SUPER_RANGE, mod.agus.jcoderz.dx.dex.code.form.Form35c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INVOKE_DIRECT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INVOKE_DIRECT, mod.agus.jcoderz.dx.io.Opcodes.INVOKE_DIRECT,
            mod.agus.jcoderz.dx.io.Opcodes.INVOKE_DIRECT_RANGE, mod.agus.jcoderz.dx.dex.code.form.Form35c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INVOKE_STATIC =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INVOKE_STATIC, mod.agus.jcoderz.dx.io.Opcodes.INVOKE_STATIC,
            mod.agus.jcoderz.dx.io.Opcodes.INVOKE_STATIC_RANGE, mod.agus.jcoderz.dx.dex.code.form.Form35c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INVOKE_INTERFACE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INVOKE_INTERFACE, mod.agus.jcoderz.dx.io.Opcodes.INVOKE_INTERFACE,
            mod.agus.jcoderz.dx.io.Opcodes.INVOKE_INTERFACE_RANGE, mod.agus.jcoderz.dx.dex.code.form.Form35c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INVOKE_VIRTUAL_RANGE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INVOKE_VIRTUAL_RANGE, mod.agus.jcoderz.dx.io.Opcodes.INVOKE_VIRTUAL,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form3rc.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INVOKE_SUPER_RANGE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INVOKE_SUPER_RANGE, mod.agus.jcoderz.dx.io.Opcodes.INVOKE_SUPER,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form3rc.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INVOKE_DIRECT_RANGE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INVOKE_DIRECT_RANGE, mod.agus.jcoderz.dx.io.Opcodes.INVOKE_DIRECT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form3rc.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INVOKE_STATIC_RANGE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INVOKE_STATIC_RANGE, mod.agus.jcoderz.dx.io.Opcodes.INVOKE_STATIC,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form3rc.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INVOKE_INTERFACE_RANGE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INVOKE_INTERFACE_RANGE, mod.agus.jcoderz.dx.io.Opcodes.INVOKE_INTERFACE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form3rc.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop NEG_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.NEG_INT, mod.agus.jcoderz.dx.io.Opcodes.NEG_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop NOT_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.NOT_INT, mod.agus.jcoderz.dx.io.Opcodes.NOT_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop NEG_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.NEG_LONG, mod.agus.jcoderz.dx.io.Opcodes.NEG_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop NOT_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.NOT_LONG, mod.agus.jcoderz.dx.io.Opcodes.NOT_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop NEG_FLOAT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.NEG_FLOAT, mod.agus.jcoderz.dx.io.Opcodes.NEG_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop NEG_DOUBLE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.NEG_DOUBLE, mod.agus.jcoderz.dx.io.Opcodes.NEG_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INT_TO_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INT_TO_LONG, mod.agus.jcoderz.dx.io.Opcodes.INT_TO_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INT_TO_FLOAT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INT_TO_FLOAT, mod.agus.jcoderz.dx.io.Opcodes.INT_TO_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INT_TO_DOUBLE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INT_TO_DOUBLE, mod.agus.jcoderz.dx.io.Opcodes.INT_TO_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop LONG_TO_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.LONG_TO_INT, mod.agus.jcoderz.dx.io.Opcodes.LONG_TO_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop LONG_TO_FLOAT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.LONG_TO_FLOAT, mod.agus.jcoderz.dx.io.Opcodes.LONG_TO_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop LONG_TO_DOUBLE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.LONG_TO_DOUBLE, mod.agus.jcoderz.dx.io.Opcodes.LONG_TO_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop FLOAT_TO_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.FLOAT_TO_INT, mod.agus.jcoderz.dx.io.Opcodes.FLOAT_TO_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop FLOAT_TO_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.FLOAT_TO_LONG, mod.agus.jcoderz.dx.io.Opcodes.FLOAT_TO_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop FLOAT_TO_DOUBLE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.FLOAT_TO_DOUBLE, mod.agus.jcoderz.dx.io.Opcodes.FLOAT_TO_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop DOUBLE_TO_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.DOUBLE_TO_INT, mod.agus.jcoderz.dx.io.Opcodes.DOUBLE_TO_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop DOUBLE_TO_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.DOUBLE_TO_LONG, mod.agus.jcoderz.dx.io.Opcodes.DOUBLE_TO_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop DOUBLE_TO_FLOAT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.DOUBLE_TO_FLOAT, mod.agus.jcoderz.dx.io.Opcodes.DOUBLE_TO_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INT_TO_BYTE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INT_TO_BYTE, mod.agus.jcoderz.dx.io.Opcodes.INT_TO_BYTE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INT_TO_CHAR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INT_TO_CHAR, mod.agus.jcoderz.dx.io.Opcodes.INT_TO_CHAR,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INT_TO_SHORT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INT_TO_SHORT, mod.agus.jcoderz.dx.io.Opcodes.INT_TO_SHORT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop ADD_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.ADD_INT, mod.agus.jcoderz.dx.io.Opcodes.ADD_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SUB_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SUB_INT, mod.agus.jcoderz.dx.io.Opcodes.SUB_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MUL_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MUL_INT, mod.agus.jcoderz.dx.io.Opcodes.MUL_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop DIV_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.DIV_INT, mod.agus.jcoderz.dx.io.Opcodes.DIV_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop REM_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.REM_INT, mod.agus.jcoderz.dx.io.Opcodes.REM_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop AND_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.AND_INT, mod.agus.jcoderz.dx.io.Opcodes.AND_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop OR_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.OR_INT, mod.agus.jcoderz.dx.io.Opcodes.OR_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop XOR_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.XOR_INT, mod.agus.jcoderz.dx.io.Opcodes.XOR_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SHL_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SHL_INT, mod.agus.jcoderz.dx.io.Opcodes.SHL_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SHR_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SHR_INT, mod.agus.jcoderz.dx.io.Opcodes.SHR_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop USHR_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.USHR_INT, mod.agus.jcoderz.dx.io.Opcodes.USHR_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop ADD_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.ADD_LONG, mod.agus.jcoderz.dx.io.Opcodes.ADD_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SUB_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SUB_LONG, mod.agus.jcoderz.dx.io.Opcodes.SUB_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MUL_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MUL_LONG, mod.agus.jcoderz.dx.io.Opcodes.MUL_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop DIV_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.DIV_LONG, mod.agus.jcoderz.dx.io.Opcodes.DIV_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop REM_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.REM_LONG, mod.agus.jcoderz.dx.io.Opcodes.REM_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop AND_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.AND_LONG, mod.agus.jcoderz.dx.io.Opcodes.AND_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop OR_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.OR_LONG, mod.agus.jcoderz.dx.io.Opcodes.OR_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop XOR_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.XOR_LONG, mod.agus.jcoderz.dx.io.Opcodes.XOR_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SHL_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SHL_LONG, mod.agus.jcoderz.dx.io.Opcodes.SHL_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SHR_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SHR_LONG, mod.agus.jcoderz.dx.io.Opcodes.SHR_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop USHR_LONG =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.USHR_LONG, mod.agus.jcoderz.dx.io.Opcodes.USHR_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop ADD_FLOAT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.ADD_FLOAT, mod.agus.jcoderz.dx.io.Opcodes.ADD_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SUB_FLOAT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SUB_FLOAT, mod.agus.jcoderz.dx.io.Opcodes.SUB_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MUL_FLOAT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MUL_FLOAT, mod.agus.jcoderz.dx.io.Opcodes.MUL_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop DIV_FLOAT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.DIV_FLOAT, mod.agus.jcoderz.dx.io.Opcodes.DIV_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop REM_FLOAT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.REM_FLOAT, mod.agus.jcoderz.dx.io.Opcodes.REM_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop ADD_DOUBLE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.ADD_DOUBLE, mod.agus.jcoderz.dx.io.Opcodes.ADD_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SUB_DOUBLE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SUB_DOUBLE, mod.agus.jcoderz.dx.io.Opcodes.SUB_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MUL_DOUBLE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop DIV_DOUBLE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.DIV_DOUBLE, mod.agus.jcoderz.dx.io.Opcodes.DIV_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop REM_DOUBLE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.REM_DOUBLE, mod.agus.jcoderz.dx.io.Opcodes.REM_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form23x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop ADD_INT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.ADD_INT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.ADD_INT,
            mod.agus.jcoderz.dx.io.Opcodes.ADD_INT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SUB_INT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SUB_INT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.SUB_INT,
            mod.agus.jcoderz.dx.io.Opcodes.SUB_INT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MUL_INT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MUL_INT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.MUL_INT,
            mod.agus.jcoderz.dx.io.Opcodes.MUL_INT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop DIV_INT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.DIV_INT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.DIV_INT,
            mod.agus.jcoderz.dx.io.Opcodes.DIV_INT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop REM_INT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.REM_INT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.REM_INT,
            mod.agus.jcoderz.dx.io.Opcodes.REM_INT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop AND_INT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.AND_INT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.AND_INT,
            mod.agus.jcoderz.dx.io.Opcodes.AND_INT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop OR_INT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.OR_INT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.OR_INT,
            mod.agus.jcoderz.dx.io.Opcodes.OR_INT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop XOR_INT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.XOR_INT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.XOR_INT,
            mod.agus.jcoderz.dx.io.Opcodes.XOR_INT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SHL_INT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SHL_INT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.SHL_INT,
            mod.agus.jcoderz.dx.io.Opcodes.SHL_INT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SHR_INT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SHR_INT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.SHR_INT,
            mod.agus.jcoderz.dx.io.Opcodes.SHR_INT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop USHR_INT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.USHR_INT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.USHR_INT,
            mod.agus.jcoderz.dx.io.Opcodes.USHR_INT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop ADD_LONG_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.ADD_LONG_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.ADD_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.ADD_LONG, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SUB_LONG_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SUB_LONG_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.SUB_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.SUB_LONG, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MUL_LONG_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MUL_LONG_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.MUL_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.MUL_LONG, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop DIV_LONG_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.DIV_LONG_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.DIV_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.DIV_LONG, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop REM_LONG_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.REM_LONG_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.REM_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.REM_LONG, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop AND_LONG_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.AND_LONG_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.AND_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.AND_LONG, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop OR_LONG_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.OR_LONG_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.OR_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.OR_LONG, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop XOR_LONG_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.XOR_LONG_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.XOR_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.XOR_LONG, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SHL_LONG_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SHL_LONG_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.SHL_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.SHL_LONG, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SHR_LONG_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SHR_LONG_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.SHR_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.SHR_LONG, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop USHR_LONG_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.USHR_LONG_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.USHR_LONG,
            mod.agus.jcoderz.dx.io.Opcodes.USHR_LONG, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop ADD_FLOAT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.ADD_FLOAT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.ADD_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.ADD_FLOAT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SUB_FLOAT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SUB_FLOAT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.SUB_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.SUB_FLOAT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MUL_FLOAT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MUL_FLOAT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.MUL_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.MUL_FLOAT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop DIV_FLOAT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.DIV_FLOAT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.DIV_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.DIV_FLOAT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop REM_FLOAT_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.REM_FLOAT_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.REM_FLOAT,
            mod.agus.jcoderz.dx.io.Opcodes.REM_FLOAT, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop ADD_DOUBLE_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.ADD_DOUBLE_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.ADD_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.ADD_DOUBLE, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SUB_DOUBLE_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SUB_DOUBLE_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.SUB_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.SUB_DOUBLE, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MUL_DOUBLE_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.MUL_DOUBLE, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop DIV_DOUBLE_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.DIV_DOUBLE_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.DIV_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.DIV_DOUBLE, mod.agus.jcoderz.dx.dex.code.form.Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop REM_DOUBLE_2ADDR =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.REM_DOUBLE_2ADDR, mod.agus.jcoderz.dx.io.Opcodes.REM_DOUBLE,
            mod.agus.jcoderz.dx.io.Opcodes.REM_DOUBLE, Form12x.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop ADD_INT_LIT16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.ADD_INT_LIT16, mod.agus.jcoderz.dx.io.Opcodes.ADD_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22s.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop RSUB_INT =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.RSUB_INT, mod.agus.jcoderz.dx.io.Opcodes.RSUB_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22s.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MUL_INT_LIT16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MUL_INT_LIT16, mod.agus.jcoderz.dx.io.Opcodes.MUL_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22s.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop DIV_INT_LIT16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.DIV_INT_LIT16, mod.agus.jcoderz.dx.io.Opcodes.DIV_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22s.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop REM_INT_LIT16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.REM_INT_LIT16, mod.agus.jcoderz.dx.io.Opcodes.REM_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22s.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop AND_INT_LIT16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.AND_INT_LIT16, mod.agus.jcoderz.dx.io.Opcodes.AND_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22s.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop OR_INT_LIT16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.OR_INT_LIT16, mod.agus.jcoderz.dx.io.Opcodes.OR_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22s.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop XOR_INT_LIT16 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.XOR_INT_LIT16, mod.agus.jcoderz.dx.io.Opcodes.XOR_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form22s.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop ADD_INT_LIT8 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.ADD_INT_LIT8, mod.agus.jcoderz.dx.io.Opcodes.ADD_INT,
            mod.agus.jcoderz.dx.io.Opcodes.ADD_INT_LIT16, mod.agus.jcoderz.dx.dex.code.form.Form22b.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop RSUB_INT_LIT8 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.RSUB_INT_LIT8, mod.agus.jcoderz.dx.io.Opcodes.RSUB_INT,
            mod.agus.jcoderz.dx.io.Opcodes.RSUB_INT, mod.agus.jcoderz.dx.dex.code.form.Form22b.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop MUL_INT_LIT8 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.MUL_INT_LIT8, mod.agus.jcoderz.dx.io.Opcodes.MUL_INT,
            mod.agus.jcoderz.dx.io.Opcodes.MUL_INT_LIT16, mod.agus.jcoderz.dx.dex.code.form.Form22b.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop DIV_INT_LIT8 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.DIV_INT_LIT8, mod.agus.jcoderz.dx.io.Opcodes.DIV_INT,
            mod.agus.jcoderz.dx.io.Opcodes.DIV_INT_LIT16, mod.agus.jcoderz.dx.dex.code.form.Form22b.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop REM_INT_LIT8 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.REM_INT_LIT8, mod.agus.jcoderz.dx.io.Opcodes.REM_INT,
            mod.agus.jcoderz.dx.io.Opcodes.REM_INT_LIT16, mod.agus.jcoderz.dx.dex.code.form.Form22b.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop AND_INT_LIT8 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.AND_INT_LIT8, mod.agus.jcoderz.dx.io.Opcodes.AND_INT,
            mod.agus.jcoderz.dx.io.Opcodes.AND_INT_LIT16, mod.agus.jcoderz.dx.dex.code.form.Form22b.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop OR_INT_LIT8 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.OR_INT_LIT8, mod.agus.jcoderz.dx.io.Opcodes.OR_INT,
            mod.agus.jcoderz.dx.io.Opcodes.OR_INT_LIT16, mod.agus.jcoderz.dx.dex.code.form.Form22b.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop XOR_INT_LIT8 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.XOR_INT_LIT8, mod.agus.jcoderz.dx.io.Opcodes.XOR_INT,
            mod.agus.jcoderz.dx.io.Opcodes.XOR_INT_LIT16, mod.agus.jcoderz.dx.dex.code.form.Form22b.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SHL_INT_LIT8 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SHL_INT_LIT8, mod.agus.jcoderz.dx.io.Opcodes.SHL_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22b.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop SHR_INT_LIT8 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.SHR_INT_LIT8, mod.agus.jcoderz.dx.io.Opcodes.SHR_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form22b.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop USHR_INT_LIT8 =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.USHR_INT_LIT8, mod.agus.jcoderz.dx.io.Opcodes.USHR_INT,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form22b.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INVOKE_POLYMORPHIC =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INVOKE_POLYMORPHIC, mod.agus.jcoderz.dx.io.Opcodes.INVOKE_POLYMORPHIC,
            mod.agus.jcoderz.dx.io.Opcodes.INVOKE_POLYMORPHIC_RANGE, Form45cc.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INVOKE_POLYMORPHIC_RANGE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INVOKE_POLYMORPHIC_RANGE, mod.agus.jcoderz.dx.io.Opcodes.INVOKE_POLYMORPHIC,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form4rcc.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INVOKE_CUSTOM =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INVOKE_CUSTOM, mod.agus.jcoderz.dx.io.Opcodes.INVOKE_CUSTOM,
            mod.agus.jcoderz.dx.io.Opcodes.INVOKE_CUSTOM_RANGE, Form35c.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop INVOKE_CUSTOM_RANGE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.INVOKE_CUSTOM_RANGE, mod.agus.jcoderz.dx.io.Opcodes.INVOKE_CUSTOM,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form3rc.THE_ONE, false);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CONST_METHOD_HANDLE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CONST_METHOD_HANDLE, mod.agus.jcoderz.dx.io.Opcodes.CONST_METHOD_HANDLE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, mod.agus.jcoderz.dx.dex.code.form.Form21c.THE_ONE, true);

    public static final mod.agus.jcoderz.dx.dex.code.Dop CONST_METHOD_TYPE =
        new mod.agus.jcoderz.dx.dex.code.Dop(mod.agus.jcoderz.dx.io.Opcodes.CONST_METHOD_TYPE, mod.agus.jcoderz.dx.io.Opcodes.CONST_METHOD_TYPE,
            mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT, Form21c.THE_ONE, true);

    // END(dops)

    // Static initialization.
    static {
        DOPS = new mod.agus.jcoderz.dx.dex.code.Dop[mod.agus.jcoderz.dx.io.Opcodes.MAX_VALUE - mod.agus.jcoderz.dx.io.Opcodes.MIN_VALUE + 1];

        set(SPECIAL_FORMAT);

        // BEGIN(dops-init); GENERATED AUTOMATICALLY BY opcode-gen
        set(NOP);
        set(MOVE);
        set(MOVE_FROM16);
        set(MOVE_16);
        set(MOVE_WIDE);
        set(MOVE_WIDE_FROM16);
        set(MOVE_WIDE_16);
        set(MOVE_OBJECT);
        set(MOVE_OBJECT_FROM16);
        set(MOVE_OBJECT_16);
        set(MOVE_RESULT);
        set(MOVE_RESULT_WIDE);
        set(MOVE_RESULT_OBJECT);
        set(MOVE_EXCEPTION);
        set(RETURN_VOID);
        set(RETURN);
        set(RETURN_WIDE);
        set(RETURN_OBJECT);
        set(CONST_4);
        set(CONST_16);
        set(CONST);
        set(CONST_HIGH16);
        set(CONST_WIDE_16);
        set(CONST_WIDE_32);
        set(CONST_WIDE);
        set(CONST_WIDE_HIGH16);
        set(CONST_STRING);
        set(CONST_STRING_JUMBO);
        set(CONST_CLASS);
        set(MONITOR_ENTER);
        set(MONITOR_EXIT);
        set(CHECK_CAST);
        set(INSTANCE_OF);
        set(ARRAY_LENGTH);
        set(NEW_INSTANCE);
        set(NEW_ARRAY);
        set(FILLED_NEW_ARRAY);
        set(FILLED_NEW_ARRAY_RANGE);
        set(FILL_ARRAY_DATA);
        set(THROW);
        set(GOTO);
        set(GOTO_16);
        set(GOTO_32);
        set(PACKED_SWITCH);
        set(SPARSE_SWITCH);
        set(CMPL_FLOAT);
        set(CMPG_FLOAT);
        set(CMPL_DOUBLE);
        set(CMPG_DOUBLE);
        set(CMP_LONG);
        set(IF_EQ);
        set(IF_NE);
        set(IF_LT);
        set(IF_GE);
        set(IF_GT);
        set(IF_LE);
        set(IF_EQZ);
        set(IF_NEZ);
        set(IF_LTZ);
        set(IF_GEZ);
        set(IF_GTZ);
        set(IF_LEZ);
        set(AGET);
        set(AGET_WIDE);
        set(AGET_OBJECT);
        set(AGET_BOOLEAN);
        set(AGET_BYTE);
        set(AGET_CHAR);
        set(AGET_SHORT);
        set(APUT);
        set(APUT_WIDE);
        set(APUT_OBJECT);
        set(APUT_BOOLEAN);
        set(APUT_BYTE);
        set(APUT_CHAR);
        set(APUT_SHORT);
        set(IGET);
        set(IGET_WIDE);
        set(IGET_OBJECT);
        set(IGET_BOOLEAN);
        set(IGET_BYTE);
        set(IGET_CHAR);
        set(IGET_SHORT);
        set(IPUT);
        set(IPUT_WIDE);
        set(IPUT_OBJECT);
        set(IPUT_BOOLEAN);
        set(IPUT_BYTE);
        set(IPUT_CHAR);
        set(IPUT_SHORT);
        set(SGET);
        set(SGET_WIDE);
        set(SGET_OBJECT);
        set(SGET_BOOLEAN);
        set(SGET_BYTE);
        set(SGET_CHAR);
        set(SGET_SHORT);
        set(SPUT);
        set(SPUT_WIDE);
        set(SPUT_OBJECT);
        set(SPUT_BOOLEAN);
        set(SPUT_BYTE);
        set(SPUT_CHAR);
        set(SPUT_SHORT);
        set(INVOKE_VIRTUAL);
        set(INVOKE_SUPER);
        set(INVOKE_DIRECT);
        set(INVOKE_STATIC);
        set(INVOKE_INTERFACE);
        set(INVOKE_VIRTUAL_RANGE);
        set(INVOKE_SUPER_RANGE);
        set(INVOKE_DIRECT_RANGE);
        set(INVOKE_STATIC_RANGE);
        set(INVOKE_INTERFACE_RANGE);
        set(NEG_INT);
        set(NOT_INT);
        set(NEG_LONG);
        set(NOT_LONG);
        set(NEG_FLOAT);
        set(NEG_DOUBLE);
        set(INT_TO_LONG);
        set(INT_TO_FLOAT);
        set(INT_TO_DOUBLE);
        set(LONG_TO_INT);
        set(LONG_TO_FLOAT);
        set(LONG_TO_DOUBLE);
        set(FLOAT_TO_INT);
        set(FLOAT_TO_LONG);
        set(FLOAT_TO_DOUBLE);
        set(DOUBLE_TO_INT);
        set(DOUBLE_TO_LONG);
        set(DOUBLE_TO_FLOAT);
        set(INT_TO_BYTE);
        set(INT_TO_CHAR);
        set(INT_TO_SHORT);
        set(ADD_INT);
        set(SUB_INT);
        set(MUL_INT);
        set(DIV_INT);
        set(REM_INT);
        set(AND_INT);
        set(OR_INT);
        set(XOR_INT);
        set(SHL_INT);
        set(SHR_INT);
        set(USHR_INT);
        set(ADD_LONG);
        set(SUB_LONG);
        set(MUL_LONG);
        set(DIV_LONG);
        set(REM_LONG);
        set(AND_LONG);
        set(OR_LONG);
        set(XOR_LONG);
        set(SHL_LONG);
        set(SHR_LONG);
        set(USHR_LONG);
        set(ADD_FLOAT);
        set(SUB_FLOAT);
        set(MUL_FLOAT);
        set(DIV_FLOAT);
        set(REM_FLOAT);
        set(ADD_DOUBLE);
        set(SUB_DOUBLE);
        set(MUL_DOUBLE);
        set(DIV_DOUBLE);
        set(REM_DOUBLE);
        set(ADD_INT_2ADDR);
        set(SUB_INT_2ADDR);
        set(MUL_INT_2ADDR);
        set(DIV_INT_2ADDR);
        set(REM_INT_2ADDR);
        set(AND_INT_2ADDR);
        set(OR_INT_2ADDR);
        set(XOR_INT_2ADDR);
        set(SHL_INT_2ADDR);
        set(SHR_INT_2ADDR);
        set(USHR_INT_2ADDR);
        set(ADD_LONG_2ADDR);
        set(SUB_LONG_2ADDR);
        set(MUL_LONG_2ADDR);
        set(DIV_LONG_2ADDR);
        set(REM_LONG_2ADDR);
        set(AND_LONG_2ADDR);
        set(OR_LONG_2ADDR);
        set(XOR_LONG_2ADDR);
        set(SHL_LONG_2ADDR);
        set(SHR_LONG_2ADDR);
        set(USHR_LONG_2ADDR);
        set(ADD_FLOAT_2ADDR);
        set(SUB_FLOAT_2ADDR);
        set(MUL_FLOAT_2ADDR);
        set(DIV_FLOAT_2ADDR);
        set(REM_FLOAT_2ADDR);
        set(ADD_DOUBLE_2ADDR);
        set(SUB_DOUBLE_2ADDR);
        set(MUL_DOUBLE_2ADDR);
        set(DIV_DOUBLE_2ADDR);
        set(REM_DOUBLE_2ADDR);
        set(ADD_INT_LIT16);
        set(RSUB_INT);
        set(MUL_INT_LIT16);
        set(DIV_INT_LIT16);
        set(REM_INT_LIT16);
        set(AND_INT_LIT16);
        set(OR_INT_LIT16);
        set(XOR_INT_LIT16);
        set(ADD_INT_LIT8);
        set(RSUB_INT_LIT8);
        set(MUL_INT_LIT8);
        set(DIV_INT_LIT8);
        set(REM_INT_LIT8);
        set(AND_INT_LIT8);
        set(OR_INT_LIT8);
        set(XOR_INT_LIT8);
        set(SHL_INT_LIT8);
        set(SHR_INT_LIT8);
        set(USHR_INT_LIT8);
        set(INVOKE_POLYMORPHIC);
        set(INVOKE_POLYMORPHIC_RANGE);
        set(INVOKE_CUSTOM);
        set(INVOKE_CUSTOM_RANGE);
        set(CONST_METHOD_HANDLE);
        set(CONST_METHOD_TYPE);
        // END(dops-init)
    }

    /**
     * This class is uninstantiable.
     */
    private Dops() {
        // This space intentionally left blank.
    }

    /**
     * Gets the {@link mod.agus.jcoderz.dx.dex.code.Dop} for the given opcode value.
     *
     * @param opcode {@code Opcodes.MIN_VALUE..Opcodes.MAX_VALUE;} the
     * opcode value
     * @return {@code non-null;} the associated opcode instance
     */
    public static mod.agus.jcoderz.dx.dex.code.Dop get(int opcode) {
        int idx = opcode - mod.agus.jcoderz.dx.io.Opcodes.MIN_VALUE;

        try {
            mod.agus.jcoderz.dx.dex.code.Dop result = DOPS[idx];
            if (result != null) {
                return result;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            // Fall through.
        }

        throw new IllegalArgumentException("bogus opcode");
    }

    /**
     * Gets the next {@link mod.agus.jcoderz.dx.dex.code.Dop} in the instruction fitting chain after the
     * given instance, if any.
     *
     * @param opcode {@code non-null;} the opcode
     * @param options {@code non-null;} options, used to determine
     * which opcodes are potentially off-limits
     * @return {@code null-ok;} the next opcode in the same family, in the
     * chain of opcodes to try, or {@code null} if the given opcode is
     * the last in its chain
     */
    public static mod.agus.jcoderz.dx.dex.code.Dop getNextOrNull(mod.agus.jcoderz.dx.dex.code.Dop opcode, DexOptions options) {
      int nextOpcode = opcode.getNextOpcode();

      if (nextOpcode == mod.agus.jcoderz.dx.io.Opcodes.NO_NEXT) {
        return null;
      }

      opcode = get(nextOpcode);

      return opcode;
    }

    /**
     * Puts the given opcode into the table of all ops.
     *
     * @param opcode {@code non-null;} the opcode
     */
    private static void set(Dop opcode) {
        int idx = opcode.getOpcode() - Opcodes.MIN_VALUE;
        DOPS[idx] = opcode;
    }
}
