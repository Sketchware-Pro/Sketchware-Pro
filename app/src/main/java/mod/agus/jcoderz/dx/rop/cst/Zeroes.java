/*
 * Copyright (C) 2008 The Android Open Source Project
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

package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;

/**
 * Utility for turning types into zeroes.
 */
public final class Zeroes {
    /**
     * This class is uninstantiable.
     */
    private Zeroes() {
        // This space intentionally left blank.
    }

    /**
     * Gets the "zero" (or {@code null}) value for the given type.
     *
     * @param type {@code non-null;} the type in question
     * @return {@code non-null;} its "zero" value
     */
    public static Constant zeroFor(Type type) {
        return switch (type.getBasicType()) {
            case Type.BT_BOOLEAN -> CstBoolean.VALUE_FALSE;
            case Type.BT_BYTE -> CstByte.VALUE_0;
            case Type.BT_CHAR -> CstChar.VALUE_0;
            case Type.BT_DOUBLE -> CstDouble.VALUE_0;
            case Type.BT_FLOAT -> CstFloat.VALUE_0;
            case Type.BT_INT -> CstInteger.VALUE_0;
            case Type.BT_LONG -> CstLong.VALUE_0;
            case Type.BT_SHORT -> CstShort.VALUE_0;
            case Type.BT_OBJECT -> CstKnownNull.THE_ONE;
            default -> throw new UnsupportedOperationException("no zero for type: " +
                    type.toHuman());
        };
    }
}
