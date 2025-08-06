/*
 * Copyright (C) 2017 The Android Open Source Project
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
package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstDouble;
import mod.agus.jcoderz.dx.rop.cst.CstFloat;
import mod.agus.jcoderz.dx.rop.cst.CstInteger;
import mod.agus.jcoderz.dx.rop.cst.CstLong;
import mod.agus.jcoderz.dx.rop.cst.CstMethodHandle;
import mod.agus.jcoderz.dx.rop.cst.CstProtoRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.util.FixedSizeList;

/**
 * List of bootstrap method arguments, which are part of the contents of
 * {@code BootstrapMethods} attributes.
 */
public class BootstrapMethodArgumentsList extends FixedSizeList {
    /**
     * Constructs an instance.
     *
     * @param count the number of elements to be in the list
     */
    public BootstrapMethodArgumentsList(int count) {
        super(count);
    }

    /**
     * Gets the bootstrap argument from the indicated position.
     *
     * @param n position of argument to get
     * @return {@code Constant} instance
     */
    public mod.agus.jcoderz.dx.rop.cst.Constant get(int n) {
        return (mod.agus.jcoderz.dx.rop.cst.Constant) get0(n);
    }

    /**
     * Sets the bootstrap argument at the indicated position.
     *
     * @param n position of argument to set
     * @param cst {@code Constant} instance
     */
    public void set(int n, Constant cst) {
        // The set of permitted types is defined by the JVMS 8, section 4.7.23.
        if (cst instanceof CstString ||
            cst instanceof CstType ||
            cst instanceof CstInteger ||
            cst instanceof CstLong ||
            cst instanceof CstFloat ||
            cst instanceof CstDouble ||
            cst instanceof CstMethodHandle ||
            cst instanceof CstProtoRef) {
            set0(n, cst);
        } else {
            Class<?> klass = cst.getClass();
            throw new IllegalArgumentException("bad type for bootstrap argument: " + klass);
        }
    }
}
