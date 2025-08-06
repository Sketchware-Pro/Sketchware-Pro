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

package mod.agus.jcoderz.dx.rop.cst;

import mod.agus.jcoderz.dx.rop.type.Type;

/**
 * Constants of type {@code CONSTANT_NameAndType_info}.
 */
public final class CstNat extends mod.agus.jcoderz.dx.rop.cst.Constant {
    /**
     * {@code non-null;} the instance for name {@code TYPE} and descriptor
     * {@code java.lang.Class}, which is useful when dealing with
     * wrapped primitives
     */
    public static final CstNat PRIMITIVE_TYPE_NAT =
        new CstNat(new mod.agus.jcoderz.dx.rop.cst.CstString("TYPE"),
                   new mod.agus.jcoderz.dx.rop.cst.CstString("Ljava/lang/Class;"));

    /** {@code non-null;} the name */
    private final mod.agus.jcoderz.dx.rop.cst.CstString name;

    /** {@code non-null;} the descriptor (type) */
    private final mod.agus.jcoderz.dx.rop.cst.CstString descriptor;

    /**
     * Constructs an instance.
     *
     * @param name {@code non-null;} the name
     * @param descriptor {@code non-null;} the descriptor
     */
    public CstNat(mod.agus.jcoderz.dx.rop.cst.CstString name, mod.agus.jcoderz.dx.rop.cst.CstString descriptor) {
        if (name == null) {
            throw new NullPointerException("name == null");
        }

        if (descriptor == null) {
            throw new NullPointerException("descriptor == null");
        }

        this.name = name;
        this.descriptor = descriptor;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CstNat)) {
            return false;
        }

        CstNat otherNat = (CstNat) other;
        return name.equals(otherNat.name) &&
            descriptor.equals(otherNat.descriptor);
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        return (name.hashCode() * 31) ^ descriptor.hashCode();
    }

    /** {@inheritDoc} */
    @Override
    protected int compareTo0(Constant other) {
        CstNat otherNat = (CstNat) other;
        int cmp = name.compareTo(otherNat.name);

        if (cmp != 0) {
            return cmp;
        }

        return descriptor.compareTo(otherNat.descriptor);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "nat{" + toHuman() + '}';
    }

    /** {@inheritDoc} */
    @Override
    public String typeName() {
        return "nat";
    }

    /** {@inheritDoc} */
    @Override
    public boolean isCategory2() {
        return false;
    }

    /**
     * Gets the name.
     *
     * @return {@code non-null;} the name
     */
    public mod.agus.jcoderz.dx.rop.cst.CstString getName() {
        return name;
    }

    /**
     * Gets the descriptor.
     *
     * @return {@code non-null;} the descriptor
     */
    public CstString getDescriptor() {
        return descriptor;
    }

    /**
     * Returns an unadorned but human-readable version of the name-and-type
     * value.
     *
     * @return {@code non-null;} the human form
     */
    @Override
    public String toHuman() {
        return name.toHuman() + ':' + descriptor.toHuman();
    }

    /**
     * Gets the field type corresponding to this instance's descriptor.
     * This method is only valid to call if the descriptor in fact describes
     * a field (and not a method).
     *
     * @return {@code non-null;} the field type
     */
    public Type getFieldType() {
        return Type.intern(descriptor.getString());
    }

    /**
     * Gets whether this instance has the name of a standard instance
     * initialization method. This is just a convenient shorthand for
     * {@code getName().getString().equals("<init>")}.
     *
     * @return {@code true} iff this is a reference to an
     * instance initialization method
     */
    public final boolean isInstanceInit() {
        return name.getString().equals("<init>");
    }

    /**
     * Gets whether this instance has the name of a standard class
     * initialization method. This is just a convenient shorthand for
     * {@code getName().getString().equals("<clinit>")}.
     *
     * @return {@code true} iff this is a reference to an
     * instance initialization method
     */
    public final boolean isClassInit() {
        return name.getString().equals("<clinit>");
    }
}
