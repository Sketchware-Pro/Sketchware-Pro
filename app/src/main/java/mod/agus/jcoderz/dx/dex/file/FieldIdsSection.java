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
import mod.agus.jcoderz.dx.rop.cst.CstFieldRef;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

/**
 * Field refs list section of a {@code .dex} file.
 */
public final class FieldIdsSection extends MemberIdsSection {
    /**
     * {@code non-null;} map from field constants to {@link
     * mod.agus.jcoderz.dx.dex.file.FieldIdItem} instances
     */
    private final TreeMap<mod.agus.jcoderz.dx.rop.cst.CstFieldRef, mod.agus.jcoderz.dx.dex.file.FieldIdItem> fieldIds;

    /**
     * Constructs an instance. The file offset is initially unknown.
     *
     * @param file {@code non-null;} file that this instance is part of
     */
    public FieldIdsSection(DexFile file) {
        super("field_ids", file);

        fieldIds = new TreeMap<mod.agus.jcoderz.dx.rop.cst.CstFieldRef, mod.agus.jcoderz.dx.dex.file.FieldIdItem>();
    }

    /** {@inheritDoc} */
    @Override
    public Collection<? extends Item> items() {
        return fieldIds.values();
    }

    /** {@inheritDoc} */
    @Override
    public mod.agus.jcoderz.dx.dex.file.IndexedItem get(Constant cst) {
        if (cst == null) {
            throw new NullPointerException("cst == null");
        }

        throwIfNotPrepared();

        IndexedItem result = fieldIds.get((mod.agus.jcoderz.dx.rop.cst.CstFieldRef) cst);

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

        int sz = fieldIds.size();
        int offset = (sz == 0) ? 0 : getFileOffset();

        if (out.annotates()) {
            out.annotate(4, "field_ids_size:  " + mod.agus.jcoderz.dx.util.Hex.u4(sz));
            out.annotate(4, "field_ids_off:   " + Hex.u4(offset));
        }

        out.writeInt(sz);
        out.writeInt(offset);
    }

    /**
     * Interns an element into this instance.
     *
     * @param field {@code non-null;} the reference to intern
     * @return {@code non-null;} the interned reference
     */
    public synchronized mod.agus.jcoderz.dx.dex.file.FieldIdItem intern(mod.agus.jcoderz.dx.rop.cst.CstFieldRef field) {
        if (field == null) {
            throw new NullPointerException("field == null");
        }

        throwIfPrepared();

        mod.agus.jcoderz.dx.dex.file.FieldIdItem result = fieldIds.get(field);

        if (result == null) {
            result = new mod.agus.jcoderz.dx.dex.file.FieldIdItem(field);
            fieldIds.put(field, result);
        }

        return result;
    }

    /**
     * Gets the index of the given reference, which must have been added
     * to this instance.
     *
     * @param ref {@code non-null;} the reference to look up
     * @return {@code >= 0;} the reference's index
     */
    public int indexOf(CstFieldRef ref) {
        if (ref == null) {
            throw new NullPointerException("ref == null");
        }

        throwIfNotPrepared();

        FieldIdItem item = fieldIds.get(ref);

        if (item == null) {
            throw new IllegalArgumentException("not found");
        }

        return item.getIndex();
    }
}
