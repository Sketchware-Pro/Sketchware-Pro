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

package mod.agus.jcoderz.dx.dex.file;

import java.util.Collection;
import java.util.TreeMap;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstNat;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

/**
 * Strings list section of a {@code .dex} file.
 */
public final class StringIdsSection
        extends UniformItemSection {
    /**
     * {@code non-null;} map from string constants to {@link
     * StringIdItem} instances
     */
    private final TreeMap<mod.agus.jcoderz.dx.rop.cst.CstString, StringIdItem> strings;

    /**
     * Constructs an instance. The file offset is initially unknown.
     *
     * @param file {@code non-null;} file that this instance is part of
     */
    public StringIdsSection(DexFile file) {
        super("string_ids", file, 4);

        strings = new TreeMap<mod.agus.jcoderz.dx.rop.cst.CstString, StringIdItem>();
    }

    /** {@inheritDoc} */
    @Override
    public Collection<? extends Item> items() {
        return strings.values();
    }

    /** {@inheritDoc} */
    @Override
    public IndexedItem get(Constant cst) {
        if (cst == null) {
            throw new NullPointerException("cst == null");
        }

        throwIfNotPrepared();

        IndexedItem result = strings.get((mod.agus.jcoderz.dx.rop.cst.CstString) cst);

        if (result == null) {
            throw new IllegalArgumentException("not found");
        }

        return result;
    }

    /**
     * Writes the portion of the file header that refers to this instance.
     *
     * @param out {@code non-null;} where to write
     */
    public void writeHeaderPart(AnnotatedOutput out) {
        throwIfNotPrepared();

        int sz = strings.size();
        int offset = (sz == 0) ? 0 : getFileOffset();

        if (out.annotates()) {
            out.annotate(4, "string_ids_size: " + mod.agus.jcoderz.dx.util.Hex.u4(sz));
            out.annotate(4, "string_ids_off:  " + Hex.u4(offset));
        }

        out.writeInt(sz);
        out.writeInt(offset);
    }

    /**
     * Interns an element into this instance.
     *
     * @param string {@code non-null;} the string to intern, as a regular Java
     * {@code String}
     * @return {@code non-null;} the interned string
     */
    public StringIdItem intern(String string) {
        return intern(new StringIdItem(new mod.agus.jcoderz.dx.rop.cst.CstString(string)));
    }

    /**
     * Interns an element into this instance.
     *
     * @param string {@code non-null;} the string to intern, as a constant
     * @return {@code non-null;} the interned string
     */
    public StringIdItem intern(mod.agus.jcoderz.dx.rop.cst.CstString string) {
        return intern(new StringIdItem(string));
    }

    /**
     * Interns an element into this instance.
     *
     * @param string {@code non-null;} the string to intern
     * @return {@code non-null;} the interned string
     */
    public synchronized StringIdItem intern(StringIdItem string) {
        if (string == null) {
            throw new NullPointerException("string == null");
        }

        throwIfPrepared();

        mod.agus.jcoderz.dx.rop.cst.CstString value = string.getValue();
        StringIdItem already = strings.get(value);

        if (already != null) {
            return already;
        }

        strings.put(value, string);
        return string;
    }

    /**
     * Interns the components of a name-and-type into this instance.
     *
     * @param nat {@code non-null;} the name-and-type
     */
    public synchronized void intern(CstNat nat) {
        intern(nat.getName());
        intern(nat.getDescriptor());
    }

    /**
     * Gets the index of the given string, which must have been added
     * to this instance.
     *
     * @param string {@code non-null;} the string to look up
     * @return {@code >= 0;} the string's index
     */
    public int indexOf(CstString string) {
        if (string == null) {
            throw new NullPointerException("string == null");
        }

        throwIfNotPrepared();

        StringIdItem s = strings.get(string);

        if (s == null) {
            throw new IllegalArgumentException("not found");
        }

        return s.getIndex();
    }

    /** {@inheritDoc} */
    @Override
    protected void orderItems() {
        int idx = 0;

        for (StringIdItem s : strings.values()) {
            s.setIndex(idx);
            idx++;
        }
    }
}
