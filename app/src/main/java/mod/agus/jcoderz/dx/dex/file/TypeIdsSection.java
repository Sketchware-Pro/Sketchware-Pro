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

import mod.agus.jcoderz.dex.DexFormat;
import mod.agus.jcoderz.dex.DexIndexOverflowException;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.AnnotatedOutput;
import mod.agus.jcoderz.dx.util.Hex;

import java.util.Collection;
import java.util.TreeMap;

/**
 * Type identifiers list section of a {@code .dex} file.
 */
public final class TypeIdsSection extends UniformItemSection {
    /**
     * {@code non-null;} map from types to {@link mod.agus.jcoderz.dx.dex.file.TypeIdItem} instances
     */
    private final TreeMap<mod.agus.jcoderz.dx.rop.type.Type, mod.agus.jcoderz.dx.dex.file.TypeIdItem> typeIds;

    /**
     * Constructs an instance. The file offset is initially unknown.
     *
     * @param file {@code non-null;} file that this instance is part of
     */
    public TypeIdsSection(DexFile file) {
        super("type_ids", file, 4);

        typeIds = new TreeMap<mod.agus.jcoderz.dx.rop.type.Type, mod.agus.jcoderz.dx.dex.file.TypeIdItem>();
    }

    /** {@inheritDoc} */
    @Override
    public Collection<? extends Item> items() {
        return typeIds.values();
    }

    /** {@inheritDoc} */
    @Override
    public mod.agus.jcoderz.dx.dex.file.IndexedItem get(Constant cst) {
        if (cst == null) {
            throw new NullPointerException("cst == null");
        }

        throwIfNotPrepared();

        mod.agus.jcoderz.dx.rop.type.Type type = ((mod.agus.jcoderz.dx.rop.cst.CstType) cst).getClassType();
        IndexedItem result = typeIds.get(type);

        if (result == null) {
            throw new IllegalArgumentException("not found: " + cst);
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

        int sz = typeIds.size();
        int offset = (sz == 0) ? 0 : getFileOffset();

        if (sz > DexFormat.MAX_TYPE_IDX + 1) {
            throw new DexIndexOverflowException(
                    String.format("Too many type identifiers to fit in one dex file: %1$d; max is %2$d.%n"
                                    + "You may try using multi-dex. If multi-dex is enabled then the list of "
                                    + "classes for the main dex list is too large.",
                            items().size(), DexFormat.MAX_MEMBER_IDX + 1));
        }

        if (out.annotates()) {
            out.annotate(4, "type_ids_size:   " + mod.agus.jcoderz.dx.util.Hex.u4(sz));
            out.annotate(4, "type_ids_off:    " + Hex.u4(offset));
        }

        out.writeInt(sz);
        out.writeInt(offset);
    }

    /**
     * Interns an element into this instance.
     *
     * @param type {@code non-null;} the type to intern
     * @return {@code non-null;} the interned reference
     */
    public synchronized mod.agus.jcoderz.dx.dex.file.TypeIdItem intern(mod.agus.jcoderz.dx.rop.type.Type type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        throwIfPrepared();

        mod.agus.jcoderz.dx.dex.file.TypeIdItem result = typeIds.get(type);

        if (result == null) {
            result = new mod.agus.jcoderz.dx.dex.file.TypeIdItem(new mod.agus.jcoderz.dx.rop.cst.CstType(type));
            typeIds.put(type, result);
        }

        return result;
    }

    /**
     * Interns an element into this instance.
     *
     * @param type {@code non-null;} the type to intern
     * @return {@code non-null;} the interned reference
     */
    public synchronized mod.agus.jcoderz.dx.dex.file.TypeIdItem intern(mod.agus.jcoderz.dx.rop.cst.CstType type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        throwIfPrepared();

        mod.agus.jcoderz.dx.rop.type.Type typePerSe = type.getClassType();
        mod.agus.jcoderz.dx.dex.file.TypeIdItem result = typeIds.get(typePerSe);

        if (result == null) {
            result = new mod.agus.jcoderz.dx.dex.file.TypeIdItem(type);
            typeIds.put(typePerSe, result);
        }

        return result;
    }

    /**
     * Gets the index of the given type, which must have
     * been added to this instance.
     *
     * @param type {@code non-null;} the type to look up
     * @return {@code >= 0;} the reference's index
     */
    public int indexOf(Type type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        throwIfNotPrepared();

        mod.agus.jcoderz.dx.dex.file.TypeIdItem item = typeIds.get(type);

        if (item == null) {
            throw new IllegalArgumentException("not found: " + type);
        }

        return item.getIndex();
    }

    /**
     * Gets the index of the given type, which must have
     * been added to this instance.
     *
     * @param type {@code non-null;} the type to look up
     * @return {@code >= 0;} the reference's index
     */
    public int indexOf(CstType type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }

        return indexOf(type.getClassType());
    }

    /** {@inheritDoc} */
    @Override
    protected void orderItems() {
        int idx = 0;

        for (Object i : items()) {
            ((TypeIdItem) i).setIndex(idx);
            idx++;
        }
    }
}
