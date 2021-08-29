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

package mod.agus.jcoderz.dx.ssa;

import mod.agus.jcoderz.dx.cf.code.Merger;

import java.util.BitSet;
import java.util.List;

import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;

/**
 * Resolves the result types of phi instructions. When phi instructions
 * are inserted, their result types are set to BT_VOID (which is a nonsensical
 * type for a register) but must be resolve to a real type before converting
 * out of SSA form.<p>
 *
 * The resolve is done as an iterative merge of each phi's operand types.
 * Phi operands may be themselves be the result of unresolved phis,
 * and the algorithm tries to find the most-fit type (for example, if every
 * operand is the same constant value or the same local variable info, we want
 * that to be reflected).<p>
 *
 * This algorithm assumes a dead-code remover has already removed all
 * circular-only phis that may have been inserted.
 */
public class PhiTypeResolver {

    mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth;
    /** indexed by register; all registers still defined by unresolved phis */
    private final BitSet worklist;

    /**
     * Resolves all phi types in the method
     * @param ssaMeth method to process
     */
    public static void process (mod.agus.jcoderz.dx.ssa.SsaMethod ssaMeth) {
        new PhiTypeResolver(ssaMeth).run();
    }

    private PhiTypeResolver(SsaMethod ssaMeth) {
        this.ssaMeth = ssaMeth;
        worklist = new BitSet(ssaMeth.getRegCount());
    }

    /**
     * Runs the phi-type resolver.
     */
    private void run() {

        int regCount = ssaMeth.getRegCount();

        for (int reg = 0; reg < regCount; reg++) {
            mod.agus.jcoderz.dx.ssa.SsaInsn definsn = ssaMeth.getDefinitionForRegister(reg);

            if (definsn != null
                    && (definsn.getResult().getBasicType() == mod.agus.jcoderz.dx.rop.type.Type.BT_VOID)) {
                worklist.set(reg);
            }
        }

        int reg;
        while ( 0 <= (reg = worklist.nextSetBit(0))) {
            worklist.clear(reg);

            /*
             * definitions on the worklist have a type of BT_VOID, which
             * must have originated from a PhiInsn.
             */
            PhiInsn definsn = (PhiInsn)ssaMeth.getDefinitionForRegister(reg);

            if (resolveResultType(definsn)) {
                /*
                 * If the result type has changed, re-resolve all phis
                 * that use this.
                 */

                List<mod.agus.jcoderz.dx.ssa.SsaInsn> useList = ssaMeth.getUseListForRegister(reg);

                int sz = useList.size();
                for (int i = 0; i < sz; i++ ) {
                    SsaInsn useInsn = useList.get(i);
                    mod.agus.jcoderz.dx.rop.code.RegisterSpec resultReg = useInsn.getResult();
                    if (resultReg != null && useInsn instanceof PhiInsn) {
                        worklist.set(resultReg.getReg());
                    }
                }
            }
        }
    }

    /**
     * Returns true if a and b are equal, whether
     * or not either of them are null.
     * @param a
     * @param b
     * @return true if equal
     */
    private static boolean equalsHandlesNulls(mod.agus.jcoderz.dx.rop.code.LocalItem a, mod.agus.jcoderz.dx.rop.code.LocalItem b) {
        return (a == b) || ((a != null) && a.equals(b));
    }

    /**
     * Resolves the result of a phi insn based on its operands. The "void"
     * type, which is a nonsensical type for a register, is used for
     * registers defined by as-of-yet-unresolved phi operations.
     *
     * @return true if the result type changed, false if no change
     */
    boolean resolveResultType(PhiInsn insn) {
        insn.updateSourcesToDefinitions(ssaMeth);

        RegisterSpecList sources = insn.getSources();

        // Start by finding the first non-void operand
        mod.agus.jcoderz.dx.rop.code.RegisterSpec first = null;
        int firstIndex = -1;

        int szSources = sources.size();
        for (int i = 0 ; i <szSources ; i++) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec rs = sources.get(i);

            if (rs.getBasicType() != mod.agus.jcoderz.dx.rop.type.Type.BT_VOID) {
                first = rs;
                firstIndex = i;
            }
        }

        if (first == null) {
            // All operands are void -- we're not ready to resolve yet
            return false;
        }

        mod.agus.jcoderz.dx.rop.code.LocalItem firstLocal = first.getLocalItem();
        mod.agus.jcoderz.dx.rop.type.TypeBearer mergedType = first.getType();
        boolean sameLocals = true;
        for (int i = 0 ; i < szSources ; i++) {
            if (i == firstIndex) {
                continue;
            }

            mod.agus.jcoderz.dx.rop.code.RegisterSpec rs = sources.get(i);

            // Just skip void (unresolved phi results) for now
            if (rs.getBasicType() == Type.BT_VOID){
                continue;
            }

            sameLocals = sameLocals
                    && equalsHandlesNulls(firstLocal, rs.getLocalItem());

            mergedType = Merger.mergeType(mergedType, rs.getType());
        }

        TypeBearer newResultType;

        if (mergedType != null) {
            newResultType = mergedType;
        } else {
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < szSources; i++) {
                sb.append(sources.get(i).toString());
                sb.append(' ');
            }

            throw new RuntimeException ("Couldn't map types in phi insn:" + sb);
        }

        LocalItem newLocal = sameLocals ? firstLocal : null;

        RegisterSpec result = insn.getResult();

        if ((result.getTypeBearer() == newResultType)
                && equalsHandlesNulls(newLocal, result.getLocalItem())) {
            return false;
        }

        insn.changeResultType(newResultType, newLocal);

        return true;
    }
}
