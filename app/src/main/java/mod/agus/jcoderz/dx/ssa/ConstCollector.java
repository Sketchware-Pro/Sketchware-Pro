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

package mod.agus.jcoderz.dx.ssa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import mod.agus.jcoderz.dx.rop.code.LocalItem;
import mod.agus.jcoderz.dx.rop.code.PlainCstInsn;
import mod.agus.jcoderz.dx.rop.code.PlainInsn;
import mod.agus.jcoderz.dx.rop.code.RegOps;
import mod.agus.jcoderz.dx.rop.code.RegisterSpec;
import mod.agus.jcoderz.dx.rop.code.RegisterSpecList;
import mod.agus.jcoderz.dx.rop.code.Rop;
import mod.agus.jcoderz.dx.rop.code.Rops;
import mod.agus.jcoderz.dx.rop.code.SourcePosition;
import mod.agus.jcoderz.dx.rop.code.ThrowingCstInsn;
import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.TypedConstant;
import mod.agus.jcoderz.dx.rop.type.StdTypeList;
import mod.agus.jcoderz.dx.rop.type.TypeBearer;

/**
 * Collects constants that are used more than once at the top of the
 * method block. This increases register usage by about 5% but decreases
 * insn size by about 3%.
 */
public class ConstCollector {
    /** Maximum constants to collect per method. Puts cap on reg use */
    private static final int MAX_COLLECTED_CONSTANTS = 5;

    /**
     * Also collect string consts, although they can throw exceptions.
     * This is off now because it just doesn't seem to gain a whole lot.
     * TODO if you turn this on, you must change SsaInsn.hasSideEffect()
     * to return false for const-string insns whose exceptions are not
     * caught in the current method.
     */
    private static final boolean COLLECT_STRINGS = false;

    /**
     * If true, allow one local var to be involved with a collected const.
     * Turned off because it mostly just inserts more moves.
     */
    private static final boolean COLLECT_ONE_LOCAL = false;

    /** method we're processing */
    private final SsaMethod ssaMeth;

    /**
     * Processes a method.
     *
     * @param ssaMethod {@code non-null;} method to process
     */
    public static void process(SsaMethod ssaMethod) {
        ConstCollector cc = new ConstCollector(ssaMethod);
        cc.run();
    }

    /**
     * Constructs an instance.
     *
     * @param ssaMethod {@code non-null;} method to process
     */
    private ConstCollector(SsaMethod ssaMethod) {
        this.ssaMeth = ssaMethod;
    }

    /**
     * Applies the optimization.
     */
    private void run() {
        int regSz = ssaMeth.getRegCount();

        ArrayList<mod.agus.jcoderz.dx.rop.cst.TypedConstant> constantList
                = getConstsSortedByCountUse();

        int toCollect = Math.min(constantList.size(), MAX_COLLECTED_CONSTANTS);

        mod.agus.jcoderz.dx.ssa.SsaBasicBlock start = ssaMeth.getEntryBlock();

        // Constant to new register containing the constant
        HashMap<mod.agus.jcoderz.dx.rop.cst.TypedConstant, mod.agus.jcoderz.dx.rop.code.RegisterSpec> newRegs
                = new HashMap<mod.agus.jcoderz.dx.rop.cst.TypedConstant, mod.agus.jcoderz.dx.rop.code.RegisterSpec> (toCollect);

        for (int i = 0; i < toCollect; i++) {
            mod.agus.jcoderz.dx.rop.cst.TypedConstant cst = constantList.get(i);
            mod.agus.jcoderz.dx.rop.code.RegisterSpec result
                    = mod.agus.jcoderz.dx.rop.code.RegisterSpec.make(ssaMeth.makeNewSsaReg(), cst);

            mod.agus.jcoderz.dx.rop.code.Rop constRop = mod.agus.jcoderz.dx.rop.code.Rops.opConst(cst);

            if (constRop.getBranchingness() == Rop.BRANCH_NONE) {
                start.addInsnToHead(
                        new PlainCstInsn(mod.agus.jcoderz.dx.rop.code.Rops.opConst(cst),
                                mod.agus.jcoderz.dx.rop.code.SourcePosition.NO_INFO, result,
                                mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY, cst));
            } else {
                // We need two new basic blocks along with the new insn
                mod.agus.jcoderz.dx.ssa.SsaBasicBlock entryBlock = ssaMeth.getEntryBlock();
                mod.agus.jcoderz.dx.ssa.SsaBasicBlock successorBlock
                        = entryBlock.getPrimarySuccessor();

                // Insert a block containing the const insn.
                mod.agus.jcoderz.dx.ssa.SsaBasicBlock constBlock
                        = entryBlock.insertNewSuccessor(successorBlock);

                constBlock.replaceLastInsn(
                        new ThrowingCstInsn(constRop, mod.agus.jcoderz.dx.rop.code.SourcePosition.NO_INFO,
                                mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY,
                                StdTypeList.EMPTY, cst));

                // Insert a block containing the move-result-pseudo insn.

                SsaBasicBlock resultBlock
                        = constBlock.insertNewSuccessor(successorBlock);
                mod.agus.jcoderz.dx.rop.code.PlainInsn insn
                    = new mod.agus.jcoderz.dx.rop.code.PlainInsn(
                            mod.agus.jcoderz.dx.rop.code.Rops.opMoveResultPseudo(result.getTypeBearer()),
                            mod.agus.jcoderz.dx.rop.code.SourcePosition.NO_INFO,
                            result, mod.agus.jcoderz.dx.rop.code.RegisterSpecList.EMPTY);

                resultBlock.addInsnToHead(insn);
            }

            newRegs.put(cst, result);
        }

        updateConstUses(newRegs, regSz);
    }

    /**
     * Gets all of the collectable constant values used in this method,
     * sorted by most used first. Skips non-collectable consts, such as
     * non-string object constants
     *
     * @return {@code non-null;} list of constants in most-to-least used order
     */
    private ArrayList<mod.agus.jcoderz.dx.rop.cst.TypedConstant> getConstsSortedByCountUse() {
        int regSz = ssaMeth.getRegCount();

        final HashMap<mod.agus.jcoderz.dx.rop.cst.TypedConstant, Integer> countUses
                = new HashMap<mod.agus.jcoderz.dx.rop.cst.TypedConstant, Integer>();

        /*
         * Each collected constant can be used by just one local
         * (used only if COLLECT_ONE_LOCAL is true).
         */
        final HashSet<mod.agus.jcoderz.dx.rop.cst.TypedConstant> usedByLocal
                = new HashSet<mod.agus.jcoderz.dx.rop.cst.TypedConstant>();

        // Count how many times each const value is used.
        for (int i = 0; i < regSz; i++) {
            mod.agus.jcoderz.dx.ssa.SsaInsn insn = ssaMeth.getDefinitionForRegister(i);

            if (insn == null || insn.getOpcode() == null) continue;

            mod.agus.jcoderz.dx.rop.code.RegisterSpec result = insn.getResult();
            mod.agus.jcoderz.dx.rop.type.TypeBearer typeBearer = result.getTypeBearer();

            if (!typeBearer.isConstant()) continue;

            mod.agus.jcoderz.dx.rop.cst.TypedConstant cst = (mod.agus.jcoderz.dx.rop.cst.TypedConstant) typeBearer;

            // Find defining instruction for move-result-pseudo instructions
            if (insn.getOpcode().getOpcode() == RegOps.MOVE_RESULT_PSEUDO) {
                int pred = insn.getBlock().getPredecessors().nextSetBit(0);
                ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn> predInsns;
                predInsns = ssaMeth.getBlocks().get(pred).getInsns();
                insn = predInsns.get(predInsns.size()-1);
            }

            if (insn.canThrow()) {
                /*
                 * Don't move anything other than strings -- the risk
                 * of changing where an exception is thrown is too high.
                 */
                if (!(cst instanceof CstString) || !COLLECT_STRINGS) {
                    continue;
                }
                /*
                 * We can't move any throwable const whose throw will be
                 * caught, so don't count them.
                 */
                if (insn.getBlock().getSuccessors().cardinality() > 1) {
                    continue;
                }
            }

            /*
             * TODO: Might be nice to try and figure out which local
             * wins most when collected.
             */
            if (ssaMeth.isRegALocal(result)) {
                if (!COLLECT_ONE_LOCAL) {
                    continue;
                } else {
                    if (usedByLocal.contains(cst)) {
                        // Count one local usage only.
                        continue;
                    } else {
                        usedByLocal.add(cst);
                    }
                }
            }

            Integer has = countUses.get(cst);
            if (has == null) {
                countUses.put(cst, 1);
            } else {
                countUses.put(cst, has + 1);
            }
        }

        // Collect constants that have been reused.
        ArrayList<mod.agus.jcoderz.dx.rop.cst.TypedConstant> constantList = new ArrayList<mod.agus.jcoderz.dx.rop.cst.TypedConstant>();
        for (Map.Entry<mod.agus.jcoderz.dx.rop.cst.TypedConstant, Integer> entry : countUses.entrySet()) {
            if (entry.getValue() > 1) {
                constantList.add(entry.getKey());
            }
        }

        // Sort by use, with most used at the beginning of the list.
        Collections.sort(constantList, new Comparator<mod.agus.jcoderz.dx.rop.cst.Constant>() {
            @Override
            public int compare(mod.agus.jcoderz.dx.rop.cst.Constant a, Constant b) {
                int ret;
                ret = countUses.get(b) - countUses.get(a);

                if (ret == 0) {
                    /*
                     * Provide sorting determinisim for constants with same
                     * usage count.
                     */
                    ret = a.compareTo(b);
                }

                return ret;
            }

            @Override
            public boolean equals (Object obj) {
                return obj == this;
            }
        });

        return constantList;
    }

    /**
     * Inserts mark-locals if necessary when changing a register. If
     * the definition of {@code origReg} is associated with a local
     * variable, then insert a mark-local for {@code newReg} just below
     * it. We expect the definition of  {@code origReg} to ultimately
     * be removed by the dead code eliminator
     *
     * @param origReg {@code non-null;} original register
     * @param newReg {@code non-null;} new register that will replace
     * {@code origReg}
     */
    private void fixLocalAssignment(mod.agus.jcoderz.dx.rop.code.RegisterSpec origReg,
                                    mod.agus.jcoderz.dx.rop.code.RegisterSpec newReg) {
        for (mod.agus.jcoderz.dx.ssa.SsaInsn use : ssaMeth.getUseListForRegister(origReg.getReg())) {
            mod.agus.jcoderz.dx.rop.code.RegisterSpec localAssignment = use.getLocalAssignment();
            if (localAssignment == null) {
                continue;
            }

            if (use.getResult() == null) {
                /*
                 * This is a mark-local. it will be updated when all uses
                 * are updated.
                 */
                continue;
            }

            LocalItem local = localAssignment.getLocalItem();

            // Un-associate original use.
            use.setResultLocal(null);

            // Now add a mark-local to the new reg immediately after.
            newReg = newReg.withLocalItem(local);

            mod.agus.jcoderz.dx.ssa.SsaInsn newInsn
                    = mod.agus.jcoderz.dx.ssa.SsaInsn.makeFromRop(
                        new PlainInsn(Rops.opMarkLocal(newReg),
                        SourcePosition.NO_INFO, null,
                                RegisterSpecList.make(newReg)),
                    use.getBlock());

            ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn> insns = use.getBlock().getInsns();

            insns.add(insns.indexOf(use) + 1, newInsn);
        }
    }

    /**
     * Updates all uses of various consts to use the values in the newly
     * assigned registers.
     *
     * @param newRegs {@code non-null;} mapping between constant and new reg
     * @param origRegCount {@code >=0;} original SSA reg count, not including
     * newly added constant regs
     */
    private void updateConstUses(HashMap<mod.agus.jcoderz.dx.rop.cst.TypedConstant, mod.agus.jcoderz.dx.rop.code.RegisterSpec> newRegs,
            int origRegCount) {

        /*
         * set of constants associated with a local variable; used
         * only if COLLECT_ONE_LOCAL is true.
         */
        final HashSet<mod.agus.jcoderz.dx.rop.cst.TypedConstant> usedByLocal
                = new HashSet<mod.agus.jcoderz.dx.rop.cst.TypedConstant>();

        final ArrayList<mod.agus.jcoderz.dx.ssa.SsaInsn>[] useList = ssaMeth.getUseListCopy();

        for (int i = 0; i < origRegCount; i++) {
            mod.agus.jcoderz.dx.ssa.SsaInsn insn = ssaMeth.getDefinitionForRegister(i);

            if (insn == null) {
                continue;
            }

            final mod.agus.jcoderz.dx.rop.code.RegisterSpec origReg = insn.getResult();
            TypeBearer typeBearer = insn.getResult().getTypeBearer();

            if (!typeBearer.isConstant()) continue;

            mod.agus.jcoderz.dx.rop.cst.TypedConstant cst = (TypedConstant) typeBearer;
            final mod.agus.jcoderz.dx.rop.code.RegisterSpec newReg = newRegs.get(cst);

            if (newReg == null) {
                continue;
            }

            if (ssaMeth.isRegALocal(origReg)) {
                if (!COLLECT_ONE_LOCAL) {
                    continue;
                } else {
                    /*
                     * TODO: If the same local gets the same cst
                     * multiple times, it would be nice to reuse the
                     * register.
                     */
                    if (usedByLocal.contains(cst)) {
                        continue;
                    } else {
                        usedByLocal.add(cst);
                        fixLocalAssignment(origReg, newRegs.get(cst));
                    }
                }
            }

            // maps an original const register to the new collected register
            mod.agus.jcoderz.dx.ssa.RegisterMapper mapper = new RegisterMapper() {
                @Override
                public int getNewRegisterCount() {
                    return ssaMeth.getRegCount();
                }

                @Override
                public mod.agus.jcoderz.dx.rop.code.RegisterSpec map(RegisterSpec registerSpec) {
                    if (registerSpec.getReg() == origReg.getReg()) {
                        return newReg.withLocalItem(
                                registerSpec.getLocalItem());
                    }

                    return registerSpec;
                }
            };

            for (SsaInsn use : useList[origReg.getReg()]) {
                if (use.canThrow()
                        && use.getBlock().getSuccessors().cardinality() > 1) {
                    continue;
                }
                use.mapSourceRegisters(mapper);
            }
        }
    }
}
