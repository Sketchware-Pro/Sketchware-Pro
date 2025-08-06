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

import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;
import mod.agus.jcoderz.dx.util.Hex;

/**
 * Utility methods to merge various frame information.
 */
public final class Merger {
    /**
     * This class is uninstantiable.
     */
    private Merger() {
        // This space intentionally left blank.
    }

    /**
     * Merges two locals arrays. If the merged result is the same as the first
     * argument, then return the first argument (not a copy).
     *
     * @param locals1 {@code non-null;} a locals array
     * @param locals2 {@code non-null;} another locals array
     * @return {@code non-null;} the result of merging the two locals arrays
     */
    public static mod.agus.jcoderz.dx.cf.code.OneLocalsArray mergeLocals(mod.agus.jcoderz.dx.cf.code.OneLocalsArray locals1,
                                                                         mod.agus.jcoderz.dx.cf.code.OneLocalsArray locals2) {
        if (locals1 == locals2) {
            // Easy out.
            return locals1;
        }

        int sz = locals1.getMaxLocals();
        OneLocalsArray result = null;

        if (locals2.getMaxLocals() != sz) {
            throw new SimException("mismatched maxLocals values");
        }

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.rop.type.TypeBearer tb1 = locals1.getOrNull(i);
            mod.agus.jcoderz.dx.rop.type.TypeBearer tb2 = locals2.getOrNull(i);
            mod.agus.jcoderz.dx.rop.type.TypeBearer resultType = mergeType(tb1, tb2);
            if (resultType != tb1) {
                /*
                 * We only need to do anything when the result differs
                 * from what is in the first array, since that's what the
                 * result gets initialized to.
                 */
                if (result == null) {
                    result = locals1.copy();
                }

                if (resultType == null) {
                    result.invalidate(i);
                } else {
                    result.set(i, resultType);
                }
            }
        }

        if (result == null) {
            return locals1;
        }

        result.setImmutable();
        return result;
    }

    /**
     * Merges two stacks. If the merged result is the same as the first
     * argument, then return the first argument (not a copy).
     *
     * @param stack1 {@code non-null;} a stack
     * @param stack2 {@code non-null;} another stack
     * @return {@code non-null;} the result of merging the two stacks
     */
    public static mod.agus.jcoderz.dx.cf.code.ExecutionStack mergeStack(mod.agus.jcoderz.dx.cf.code.ExecutionStack stack1,
                                                                        mod.agus.jcoderz.dx.cf.code.ExecutionStack stack2) {
        if (stack1 == stack2) {
            // Easy out.
            return stack1;
        }

        int sz = stack1.size();
        ExecutionStack result = null;

        if (stack2.size() != sz) {
            throw new SimException("mismatched stack depths");
        }

        for (int i = 0; i < sz; i++) {
            mod.agus.jcoderz.dx.rop.type.TypeBearer tb1 = stack1.peek(i);
            mod.agus.jcoderz.dx.rop.type.TypeBearer tb2 = stack2.peek(i);
            mod.agus.jcoderz.dx.rop.type.TypeBearer resultType = mergeType(tb1, tb2);
            if (resultType != tb1) {
                /*
                 * We only need to do anything when the result differs
                 * from what is in the first stack, since that's what the
                 * result gets initialized to.
                 */
                if (result == null) {
                    result = stack1.copy();
                }

                try {
                    if (resultType == null) {
                        throw new SimException("incompatible: " + tb1 + ", " +
                                               tb2);
                    } else {
                        result.change(i, resultType);
                    }
                } catch (SimException ex) {
                    ex.addContext("...while merging stack[" + Hex.u2(i) + "]");
                    throw ex;
                }
            }
        }

        if (result == null) {
            return stack1;
        }

        result.setImmutable();
        return result;
    }

    /**
     * Merges two frame types.
     *
     * @param ft1 {@code non-null;} a frame type
     * @param ft2 {@code non-null;} another frame type
     * @return {@code non-null;} the result of merging the two types
     */
    public static mod.agus.jcoderz.dx.rop.type.TypeBearer mergeType(mod.agus.jcoderz.dx.rop.type.TypeBearer ft1, mod.agus.jcoderz.dx.rop.type.TypeBearer ft2) {
        if ((ft1 == null) || ft1.equals(ft2)) {
            return ft1;
        } else if (ft2 == null) {
            return null;
        } else {
            mod.agus.jcoderz.dx.rop.type.Type type1 = ft1.getType();
            mod.agus.jcoderz.dx.rop.type.Type type2 = ft2.getType();

            if (type1 == type2) {
                return type1;
            } else if (type1.isReference() && type2.isReference()) {
                if (type1 == mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL) {
                    /*
                     * A known-null merges with any other reference type to
                     * be that reference type.
                     */
                    return type2;
                } else if (type2 == mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL) {
                    /*
                     * The same as above, but this time it's type2 that's
                     * the known-null.
                     */
                    return type1;
                } else if (type1.isArray() && type2.isArray()) {
                    mod.agus.jcoderz.dx.rop.type.TypeBearer componentUnion =
                        mergeType(type1.getComponentType(),
                                type2.getComponentType());
                    if (componentUnion == null) {
                        /*
                         * At least one of the types is a primitive type,
                         * so the merged result is just Object.
                         */
                        return mod.agus.jcoderz.dx.rop.type.Type.OBJECT;
                    }
                    return ((mod.agus.jcoderz.dx.rop.type.Type) componentUnion).getArrayType();
                } else {
                    /*
                     * All other unequal reference types get merged to be
                     * Object in this phase. This is fine here, but it
                     * won't be the right thing to do in the verifier.
                     */
                    return mod.agus.jcoderz.dx.rop.type.Type.OBJECT;
                }
            } else if (type1.isIntlike() && type2.isIntlike()) {
                /*
                 * Merging two non-identical int-like types results in
                 * the type int.
                 */
                return mod.agus.jcoderz.dx.rop.type.Type.INT;
            } else {
                return null;
            }
        }
    }

    /**
     * Returns whether the given supertype is possibly assignable from
     * the given subtype. This takes into account primitiveness,
     * int-likeness, known-nullness, and array dimensions, but does
     * not assume anything about class hierarchy other than that the
     * type {@code Object} is the supertype of all reference
     * types and all arrays are assignable to
     * {@code Serializable} and {@code Cloneable}.
     *
     * @param supertypeBearer {@code non-null;} the supertype
     * @param subtypeBearer {@code non-null;} the subtype
     */
    public static boolean isPossiblyAssignableFrom(mod.agus.jcoderz.dx.rop.type.TypeBearer supertypeBearer,
                                                   TypeBearer subtypeBearer) {
        mod.agus.jcoderz.dx.rop.type.Type supertype = supertypeBearer.getType();
        mod.agus.jcoderz.dx.rop.type.Type subtype = subtypeBearer.getType();

        if (supertype.equals(subtype)) {
            // Easy out.
            return true;
        }

        int superBt = supertype.getBasicType();
        int subBt = subtype.getBasicType();

        // Treat return types as Object for the purposes of this method.

        if (superBt == mod.agus.jcoderz.dx.rop.type.Type.BT_ADDR) {
            supertype = mod.agus.jcoderz.dx.rop.type.Type.OBJECT;
            superBt = mod.agus.jcoderz.dx.rop.type.Type.BT_OBJECT;
        }

        if (subBt == mod.agus.jcoderz.dx.rop.type.Type.BT_ADDR) {
            subtype = mod.agus.jcoderz.dx.rop.type.Type.OBJECT;
            subBt = mod.agus.jcoderz.dx.rop.type.Type.BT_OBJECT;
        }

        if ((superBt != mod.agus.jcoderz.dx.rop.type.Type.BT_OBJECT) || (subBt != mod.agus.jcoderz.dx.rop.type.Type.BT_OBJECT)) {
            /*
             * No two distinct primitive types are assignable in this sense,
             * unless they are both int-like.
             */
            return supertype.isIntlike() && subtype.isIntlike();
        }

        // At this point, we know both types are reference types.

        if (supertype == mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL) {
            /*
             * A known-null supertype is only assignable from another
             * known-null (handled in the easy out at the top of the
             * method).
             */
            return false;
        } else if (subtype == mod.agus.jcoderz.dx.rop.type.Type.KNOWN_NULL) {
            /*
             * A known-null subtype is in fact assignable to any
             * reference type.
             */
            return true;
        } else if (supertype == mod.agus.jcoderz.dx.rop.type.Type.OBJECT) {
            /*
             * Object is assignable from any reference type.
             */
            return true;
        } else if (supertype.isArray()) {
            // The supertype is an array type.
            if (! subtype.isArray()) {
                // The subtype isn't an array, and so can't be assignable.
                return false;
            }

            /*
             * Strip off as many matched component types from both
             * types as possible, and check the assignability of the
             * results.
             */
            do {
                supertype = supertype.getComponentType();
                subtype = subtype.getComponentType();
            } while (supertype.isArray() && subtype.isArray());

            return isPossiblyAssignableFrom(supertype, subtype);
        } else if (subtype.isArray()) {
            /*
             * Other than Object (handled above), array types are
             * assignable only to Serializable and Cloneable.
             */
            return (supertype == mod.agus.jcoderz.dx.rop.type.Type.SERIALIZABLE) ||
                (supertype == Type.CLONEABLE);
        } else {
            /*
             * All other unequal reference types are considered at
             * least possibly assignable.
             */
            return true;
        }
    }
}
