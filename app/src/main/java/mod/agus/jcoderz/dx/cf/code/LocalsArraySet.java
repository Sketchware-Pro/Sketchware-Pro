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

package mod.agus.jcoderz.dx.cf.code;

import mod.agus.jcoderz.dex.util.ExceptionWithContext;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.util.Hex;

import java.util.ArrayList;

/**
 * Representation of a set of local variable arrays, with Java semantics.
 * This peculiar case is to support in-method subroutines, which can
 * have different locals sets for each caller.
 *
 * <p><b>Note:</b> For the most part, the documentation for this class
 * ignores the distinction between {@link mod.agus.jcoderz.dx.rop.type.Type} and {@link
 * mod.agus.jcoderz.dx.rop.type.TypeBearer}.</p>
 */
public class LocalsArraySet extends mod.agus.jcoderz.dx.cf.code.LocalsArray {

    /**
     * The primary LocalsArray represents the locals as seen from
     * the subroutine itself, which is the merged representation of all the
     * individual locals states.
     */
    private final mod.agus.jcoderz.dx.cf.code.OneLocalsArray primary;

    /**
     * Indexed by label of caller block: the locals specific to each caller's
     * invocation of the subroutine.
     */
    private final ArrayList<mod.agus.jcoderz.dx.cf.code.LocalsArray> secondaries;

    /**
     * Constructs an instance. The locals array initially consists of
     * all-uninitialized values (represented as {@code null}s).
     *
     * @param maxLocals {@code >= 0;} the maximum number of locals this instance
     * can refer to
     */
    public LocalsArraySet(int maxLocals) {
        super(maxLocals != 0);
        primary = new mod.agus.jcoderz.dx.cf.code.OneLocalsArray(maxLocals);
        secondaries = new ArrayList();
    }

    /**
     * Constructs an instance with the specified primary and secondaries set.
     *
     * @param primary {@code non-null;} primary locals to use
     * @param secondaries {@code non-null;} secondaries set, indexed by subroutine
     * caller label.
     */
    public LocalsArraySet(mod.agus.jcoderz.dx.cf.code.OneLocalsArray primary,
                          ArrayList<mod.agus.jcoderz.dx.cf.code.LocalsArray> secondaries) {
        super(primary.getMaxLocals() > 0);

        this.primary = primary;
        this.secondaries = secondaries;
    }

    /**
     * Constructs an instance which is a copy of another.
     *
     * @param toCopy {@code non-null;} instance to copy.
     */
    private LocalsArraySet(LocalsArraySet toCopy) {
        super(toCopy.getMaxLocals() > 0);

        primary = toCopy.primary.copy();
        secondaries = new ArrayList(toCopy.secondaries.size());

        int sz = toCopy.secondaries.size();
        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.cf.code.LocalsArray la = toCopy.secondaries.get(i);

            if (la == null) {
                secondaries.add(null);
            } else {
                secondaries.add(la.copy());
            }
        }
    }


    /** {@inheritDoc} */
    @Override
    public void setImmutable() {
        primary.setImmutable();

        for (mod.agus.jcoderz.dx.cf.code.LocalsArray la : secondaries) {
            if (la != null) {
                la.setImmutable();
            }
        }
        super.setImmutable();
    }

    /** {@inheritDoc} */
    @Override
    public mod.agus.jcoderz.dx.cf.code.LocalsArray copy() {
        return new LocalsArraySet(this);
    }

    /** {@inheritDoc} */
    @Override
    public void annotate(ExceptionWithContext ex) {
        ex.addContext("(locals array set; primary)");
        primary.annotate(ex);

        int sz = secondaries.size();
        for (int label = 0; label < sz; label++) {
            mod.agus.jcoderz.dx.cf.code.LocalsArray la = secondaries.get(label);

            if (la != null) {
                ex.addContext("(locals array set: primary for caller "
                        + mod.agus.jcoderz.dx.util.Hex.u2(label) + ')');

                la.getPrimary().annotate(ex);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public String toHuman() {
        StringBuilder sb = new StringBuilder();

        sb.append("(locals array set; primary)\n");

        sb.append(getPrimary().toHuman());
        sb.append('\n');

        int sz = secondaries.size();
        for (int label = 0; label < sz; label++) {
            mod.agus.jcoderz.dx.cf.code.LocalsArray la = secondaries.get(label);

            if (la != null) {
                sb.append("(locals array set: primary for caller "
                        + mod.agus.jcoderz.dx.util.Hex.u2(label) + ")\n");

                sb.append(la.getPrimary().toHuman());
                sb.append('\n');
            }
        }

        return sb.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void makeInitialized(Type type) {
        int len = primary.getMaxLocals();

        if (len == 0) {
            // We have to check for this before checking for immutability.
            return;
        }

        throwIfImmutable();

        primary.makeInitialized(type);

        for (mod.agus.jcoderz.dx.cf.code.LocalsArray la : secondaries) {
            if (la != null) {
                la.makeInitialized(type);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public int getMaxLocals() {
        return primary.getMaxLocals();
    }

    /** {@inheritDoc} */
    @Override
    public void set(int idx, mod.agus.jcoderz.dx.rop.type.TypeBearer type) {
        throwIfImmutable();

        primary.set(idx, type);

        for (mod.agus.jcoderz.dx.cf.code.LocalsArray la : secondaries) {
            if (la != null) {
                la.set(idx, type);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void set(RegisterSpec spec) {
        set(spec.getReg(), spec);
    }

    /** {@inheritDoc} */
    @Override
    public void invalidate(int idx) {
        throwIfImmutable();

        primary.invalidate(idx);

        for (mod.agus.jcoderz.dx.cf.code.LocalsArray la : secondaries) {
            if (la != null) {
                la.invalidate(idx);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public mod.agus.jcoderz.dx.rop.type.TypeBearer getOrNull(int idx) {
        return primary.getOrNull(idx);
    }

    /** {@inheritDoc} */
    @Override
    public mod.agus.jcoderz.dx.rop.type.TypeBearer get(int idx) {
        return primary.get(idx);
    }

    /** {@inheritDoc} */
    @Override
    public mod.agus.jcoderz.dx.rop.type.TypeBearer getCategory1(int idx) {
        return primary.getCategory1(idx);
    }

    /** {@inheritDoc} */
    @Override
    public TypeBearer getCategory2(int idx) {
        return primary.getCategory2(idx);
    }

    /**
     * Merges this set with another {@code LocalsArraySet} instance.
     *
     * @param other {@code non-null;} to merge
     * @return {@code non-null;} this instance if merge was a no-op, or
     * new merged instance.
     */
    private LocalsArraySet mergeWithSet(LocalsArraySet other) {
        mod.agus.jcoderz.dx.cf.code.OneLocalsArray newPrimary;
        ArrayList<mod.agus.jcoderz.dx.cf.code.LocalsArray> newSecondaries;
        boolean secondariesChanged = false;

        newPrimary = primary.merge(other.getPrimary());

        int sz1 = secondaries.size();
        int sz2 = other.secondaries.size();
        int sz = Math.max(sz1, sz2);
        newSecondaries = new ArrayList(sz);

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.cf.code.LocalsArray la1 = (i < sz1 ? secondaries.get(i) : null);
            mod.agus.jcoderz.dx.cf.code.LocalsArray la2 = (i < sz2 ? other.secondaries.get(i) : null);
            mod.agus.jcoderz.dx.cf.code.LocalsArray resultla = null;

            if (la1 == la2) {
                resultla = la1;
            } else if (la1 == null) {
                resultla = la2;
            } else if (la2 == null) {
                resultla = la1;
            } else {
                try {
                    resultla = la1.merge(la2);
                } catch (SimException ex) {
                    ex.addContext(
                            "Merging locals set for caller block " + mod.agus.jcoderz.dx.util.Hex.u2(i));
                }
            }

            secondariesChanged = secondariesChanged || (la1 != resultla);

            newSecondaries.add(resultla);
        }

        if ((primary == newPrimary) && ! secondariesChanged ) {
            return this;
        }

        return new LocalsArraySet(newPrimary, newSecondaries);
    }

    /**
     * Merges this set with a {@code OneLocalsArray} instance.
     *
     * @param other {@code non-null;} to merge
     * @return {@code non-null;} this instance if merge was a no-op, or
     * new merged instance.
     */
    private LocalsArraySet mergeWithOne(mod.agus.jcoderz.dx.cf.code.OneLocalsArray other) {
        mod.agus.jcoderz.dx.cf.code.OneLocalsArray newPrimary;
        ArrayList<mod.agus.jcoderz.dx.cf.code.LocalsArray> newSecondaries;
        boolean secondariesChanged = false;

        newPrimary = primary.merge(other.getPrimary());
        newSecondaries = new ArrayList(secondaries.size());

        int sz = secondaries.size();
        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.cf.code.LocalsArray la = secondaries.get(i);
            mod.agus.jcoderz.dx.cf.code.LocalsArray resultla = null;

            if (la != null) {
                try {
                    resultla = la.merge(other);
                } catch (SimException ex) {
                    ex.addContext("Merging one locals against caller block "
                                    + Hex.u2(i));
                }
            }

            secondariesChanged = secondariesChanged || (la != resultla);

            newSecondaries.add(resultla);
        }

        if ((primary == newPrimary) && ! secondariesChanged ) {
            return this;
        }

        return new LocalsArraySet(newPrimary, newSecondaries);
    }

    /** {@inheritDoc} */
    @Override
    public LocalsArraySet merge(mod.agus.jcoderz.dx.cf.code.LocalsArray other) {
        LocalsArraySet result;

        try {
            if (other instanceof LocalsArraySet) {
                result = mergeWithSet((LocalsArraySet) other);
            } else {
                result = mergeWithOne((mod.agus.jcoderz.dx.cf.code.OneLocalsArray) other);
            }
        } catch (SimException ex) {
            ex.addContext("underlay locals:");
            annotate(ex);
            ex.addContext("overlay locals:");
            other.annotate(ex);
            throw ex;
        }

        result.setImmutable();
        return result;
    }

    /**
     * Gets the {@code LocalsArray} instance for a specified subroutine
     * caller label, or null if label has no locals associated with it.
     *
     * @param label {@code >= 0;} subroutine caller label
     * @return {@code null-ok;} locals if available.
     */
    private mod.agus.jcoderz.dx.cf.code.LocalsArray getSecondaryForLabel(int label) {
        if (label >= secondaries.size()) {
            return null;
        }

        return secondaries.get(label);
    }

    /** {@inheritDoc} */
    @Override
    public LocalsArraySet mergeWithSubroutineCaller
            (mod.agus.jcoderz.dx.cf.code.LocalsArray other, int predLabel) {

        mod.agus.jcoderz.dx.cf.code.LocalsArray mine = getSecondaryForLabel(predLabel);
        mod.agus.jcoderz.dx.cf.code.LocalsArray newSecondary;
        mod.agus.jcoderz.dx.cf.code.OneLocalsArray newPrimary;

        newPrimary = primary.merge(other.getPrimary());

        if (mine == other) {
            newSecondary = mine;
        } else if (mine == null) {
            newSecondary = other;
        } else {
            newSecondary = mine.merge(other);
        }

        if ((newSecondary == mine) && (newPrimary == primary)) {
            return this;
        } else {
            /*
             * We're going to re-build a primary as a merge of all the
             * secondaries.
             */
            newPrimary = null;

            int szSecondaries = secondaries.size();
            int sz = Math.max(predLabel + 1, szSecondaries);
            ArrayList<mod.agus.jcoderz.dx.cf.code.LocalsArray> newSecondaries = new ArrayList(sz);
            for (int i = 0; i < sz; i++) {
                mod.agus.jcoderz.dx.cf.code.LocalsArray la = null;

                if (i == predLabel) {
                    /*
                     * This LocalsArray always replaces any existing one,
                     * since this is the result of a refined iteration.
                     */
                    la = newSecondary;
                } else if (i < szSecondaries) {
                    la = secondaries.get(i);
                }

                if (la != null) {
                    if (newPrimary == null) {
                        newPrimary = la.getPrimary();
                    } else {
                        newPrimary = newPrimary.merge(la.getPrimary());
                    }
                }

                newSecondaries.add(la);
            }

            LocalsArraySet result
                    = new LocalsArraySet(newPrimary, newSecondaries);
            result.setImmutable();
            return result;
        }
    }

    /**
     * Returns a LocalsArray instance representing the locals state that should
     * be used when returning to a subroutine caller.
     *
     * @param subLabel {@code >= 0;} A calling label of a subroutine
     * @return {@code null-ok;} an instance for this subroutine, or null if subroutine
     * is not in this set.
     */
    public mod.agus.jcoderz.dx.cf.code.LocalsArray subArrayForLabel(int subLabel) {
        LocalsArray result = getSecondaryForLabel(subLabel);
        return result;
    }

    /**{@inheritDoc}*/
    @Override
    protected OneLocalsArray getPrimary() {
        return primary;
    }
}
