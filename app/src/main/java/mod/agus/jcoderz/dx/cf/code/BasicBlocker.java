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

import java.util.ArrayList;

import mod.agus.jcoderz.dx.rop.cst.Constant;
import mod.agus.jcoderz.dx.rop.cst.CstInvokeDynamic;
import mod.agus.jcoderz.dx.rop.cst.CstMemberRef;
import mod.agus.jcoderz.dx.rop.cst.CstMethodHandle;
import mod.agus.jcoderz.dx.rop.cst.CstProtoRef;
import mod.agus.jcoderz.dx.rop.cst.CstString;
import mod.agus.jcoderz.dx.rop.cst.CstType;
import mod.agus.jcoderz.dx.rop.type.Type;
import mod.agus.jcoderz.dx.util.Bits;
import mod.agus.jcoderz.dx.util.IntList;

/**
 * Utility that identifies basic blocks in bytecode.
 */
public final class BasicBlocker implements mod.agus.jcoderz.dx.cf.code.BytecodeArray.Visitor {
    /** {@code non-null;} method being converted */
    private final mod.agus.jcoderz.dx.cf.code.ConcreteMethod method;

    /**
     * {@code non-null;} work set; bits indicate offsets in need of
     * examination
     */
    private final int[] workSet;

    /**
     * {@code non-null;} live set; bits indicate potentially-live
     * opcodes; contrawise, a bit that isn't on is either in the
     * middle of an instruction or is a definitely-dead opcode
     */
    private final int[] liveSet;

    /**
     * {@code non-null;} block start set; bits indicate the starts of
     * basic blocks, including the opcodes that start blocks of
     * definitely-dead code
     */
    private final int[] blockSet;

    /**
     * {@code non-null, sparse;} for each instruction offset to a branch of
     * some sort, the list of targets for that instruction
     */
    private final mod.agus.jcoderz.dx.util.IntList[] targetLists;

    /**
     * {@code non-null, sparse;} for each instruction offset to a throwing
     * instruction, the list of exception handlers for that instruction
     */
    private final mod.agus.jcoderz.dx.cf.code.ByteCatchList[] catchLists;

    /** offset of the previously parsed bytecode */
    private int previousOffset;

    /**
     * Identifies and enumerates the basic blocks in the given method,
     * returning a list of them. The returned list notably omits any
     * definitely-dead code that is identified in the process.
     *
     * @param method {@code non-null;} method to convert
     * @return {@code non-null;} list of basic blocks
     */
    public static ByteBlockList identifyBlocks(mod.agus.jcoderz.dx.cf.code.ConcreteMethod method) {
        BasicBlocker bb = new BasicBlocker(method);

        bb.doit();
        return bb.getBlockList();
    }

    /**
     * Constructs an instance. This class is not publicly instantiable; use
     * {@link #identifyBlocks}.
     *
     * @param method {@code non-null;} method to convert
     */
    private BasicBlocker(ConcreteMethod method) {
        if (method == null) {
            throw new NullPointerException("method == null");
        }

        this.method = method;

        /*
         * The "+1" below is so the idx-past-end is also valid,
         * avoiding a special case, but without preventing
         * flow-of-control falling past the end of the method from
         * getting properly reported.
         */
        int sz = method.getCode().size() + 1;

        workSet = mod.agus.jcoderz.dx.util.Bits.makeBitSet(sz);
        liveSet = mod.agus.jcoderz.dx.util.Bits.makeBitSet(sz);
        blockSet = mod.agus.jcoderz.dx.util.Bits.makeBitSet(sz);
        targetLists = new mod.agus.jcoderz.dx.util.IntList[sz];
        catchLists = new mod.agus.jcoderz.dx.cf.code.ByteCatchList[sz];
        previousOffset = -1;
    }

    /*
     * Note: These methods are defined implementation of the interface
     * BytecodeArray.Visitor; since the class isn't publicly
     * instantiable, no external code ever gets a chance to actually
     * call these methods.
     */

    /** {@inheritDoc} */
    @Override
    public void visitInvalid(int opcode, int offset, int length) {
        visitCommon(offset, length, true);
    }

    /** {@inheritDoc} */
    @Override
    public void visitNoArgs(int opcode, int offset, int length, mod.agus.jcoderz.dx.rop.type.Type type) {
        switch (opcode) {
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IRETURN:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.RETURN: {
                visitCommon(offset, length, false);
                targetLists[offset] = mod.agus.jcoderz.dx.util.IntList.EMPTY;
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ATHROW: {
                visitCommon(offset, length, false);
                visitThrowing(offset, length, false);
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.FALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.AALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.BALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.CALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.SALOAD:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.LASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.FASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.DASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.AASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.BASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.CASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.SASTORE:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.ARRAYLENGTH:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.MONITORENTER:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.MONITOREXIT: {
                /*
                 * These instructions can all throw, so they have to end
                 * the block they appear in (since throws are branches).
                 */
                visitCommon(offset, length, true);
                visitThrowing(offset, length, true);
                break;
            }
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IDIV:
            case mod.agus.jcoderz.dx.cf.code.ByteOps.IREM: {
                /*
                 * The int and long versions of division and remainder may
                 * throw, but not the other types.
                 */
                visitCommon(offset, length, true);
                if ((type == mod.agus.jcoderz.dx.rop.type.Type.INT) || (type == mod.agus.jcoderz.dx.rop.type.Type.LONG)) {
                    visitThrowing(offset, length, true);
                }
                break;
            }
            default: {
                visitCommon(offset, length, true);
                break;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visitLocal(int opcode, int offset, int length,
                           int idx, Type type, int value) {
        if (opcode == mod.agus.jcoderz.dx.cf.code.ByteOps.RET) {
            visitCommon(offset, length, false);
            targetLists[offset] = mod.agus.jcoderz.dx.util.IntList.EMPTY;
        } else {
            visitCommon(offset, length, true);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visitConstant(int opcode, int offset, int length,
                              mod.agus.jcoderz.dx.rop.cst.Constant cst, int value) {
        visitCommon(offset, length, true);

        if (cst instanceof CstMemberRef || cst instanceof mod.agus.jcoderz.dx.rop.cst.CstType ||
            cst instanceof CstString || cst instanceof CstInvokeDynamic ||
            cst instanceof CstMethodHandle || cst instanceof CstProtoRef) {
            /*
             * Instructions with these sorts of constants have the
             * possibility of throwing, so this instruction needs to
             * end its block (since it can throw, and possible-throws
             * are branch points).
             */
            visitThrowing(offset, length, true);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void visitBranch(int opcode, int offset, int length,
            int target) {
        switch (opcode) {
            case mod.agus.jcoderz.dx.cf.code.ByteOps.GOTO: {
                visitCommon(offset, length, false);
                targetLists[offset] = mod.agus.jcoderz.dx.util.IntList.makeImmutable(target);
                break;
            }
            case ByteOps.JSR: {
                /*
                 * Each jsr is quarantined into a separate block (containing
                 * only the jsr instruction) but is otherwise treated
                 * as a conditional branch. (That is to say, both its
                 * target and next instruction begin new blocks.)
                 */
                addWorkIfNecessary(offset, true);
                // Fall through to next case...
            }
            default: {
                int next = offset + length;
                visitCommon(offset, length, true);
                addWorkIfNecessary(next, true);
                targetLists[offset] = mod.agus.jcoderz.dx.util.IntList.makeImmutable(next, target);
                break;
            }
        }

        addWorkIfNecessary(target, true);
    }

    /** {@inheritDoc} */
    @Override
    public void visitSwitch(int opcode, int offset, int length,
                            SwitchList cases, int padding) {
        visitCommon(offset, length, false);
        addWorkIfNecessary(cases.getDefaultTarget(), true);

        int sz = cases.size();
        for (int i = 0; i < sz; i++) {
            addWorkIfNecessary(cases.getTarget(i), true);
        }

        targetLists[offset] = cases.getTargets();
    }

    /** {@inheritDoc} */
    @Override
    public void visitNewarray(int offset, int length, CstType type,
            ArrayList<Constant> intVals) {
        visitCommon(offset, length, true);
        visitThrowing(offset, length, true);
    }

    /**
     * Extracts the list of basic blocks from the bit sets.
     *
     * @return {@code non-null;} the list of basic blocks
     */
    private ByteBlockList getBlockList() {
        mod.agus.jcoderz.dx.cf.code.BytecodeArray bytes = method.getCode();
        mod.agus.jcoderz.dx.cf.code.ByteBlock[] bbs = new mod.agus.jcoderz.dx.cf.code.ByteBlock[bytes.size()];
        int count = 0;

        for (int at = 0, next; /*at*/; at = next) {
            next = mod.agus.jcoderz.dx.util.Bits.findFirst(blockSet, at + 1);
            if (next < 0) {
                break;
            }

            if (mod.agus.jcoderz.dx.util.Bits.get(liveSet, at)) {
                /*
                 * Search backward for the branch or throwing
                 * instruction at the end of this block, if any. If
                 * there isn't any, then "next" is the sole target.
                 */
                mod.agus.jcoderz.dx.util.IntList targets = null;
                int targetsAt = -1;
                mod.agus.jcoderz.dx.cf.code.ByteCatchList blockCatches;

                for (int i = next - 1; i >= at; i--) {
                    targets = targetLists[i];
                    if (targets != null) {
                        targetsAt = i;
                        break;
                    }
                }

                if (targets == null) {
                    targets = IntList.makeImmutable(next);
                    blockCatches = mod.agus.jcoderz.dx.cf.code.ByteCatchList.EMPTY;
                } else {
                    blockCatches = catchLists[targetsAt];
                    if (blockCatches == null) {
                        blockCatches = mod.agus.jcoderz.dx.cf.code.ByteCatchList.EMPTY;
                    }
                }

                bbs[count] =
                    new ByteBlock(at, at, next, targets, blockCatches);
                count++;
            }
        }

        ByteBlockList result = new ByteBlockList(count);
        for (int i = 0; i < count; i++) {
            result.set(i, bbs[i]);
        }

        return result;
    }

    /**
     * Does basic block identification.
     */
    private void doit() {
        BytecodeArray bytes = method.getCode();
        mod.agus.jcoderz.dx.cf.code.ByteCatchList catches = method.getCatches();
        int catchSz = catches.size();

        /*
         * Start by setting offset 0 as the start of a block and in need
         * of work...
         */
        mod.agus.jcoderz.dx.util.Bits.set(workSet, 0);
        mod.agus.jcoderz.dx.util.Bits.set(blockSet, 0);

        /*
         * And then process the work set, add new work based on
         * exception ranges that are active, and iterate until there's
         * nothing left to work on.
         */
        while (!mod.agus.jcoderz.dx.util.Bits.isEmpty(workSet)) {
            try {
                bytes.processWorkSet(workSet, this);
            } catch (IllegalArgumentException ex) {
                // Translate the exception.
                throw new SimException("flow of control falls off " +
                                       "end of method",
                                       ex);
            }

            for (int i = 0; i < catchSz; i++) {
                mod.agus.jcoderz.dx.cf.code.ByteCatchList.Item item = catches.get(i);
                int start = item.getStartPc();
                int end = item.getEndPc();
                if (mod.agus.jcoderz.dx.util.Bits.anyInRange(liveSet, start, end)) {
                    mod.agus.jcoderz.dx.util.Bits.set(blockSet, start);
                    mod.agus.jcoderz.dx.util.Bits.set(blockSet, end);
                    addWorkIfNecessary(item.getHandlerPc(), true);
                }
            }
        }
    }

    /**
     * Sets a bit in the work set, but only if the instruction in question
     * isn't yet known to be possibly-live.
     *
     * @param offset offset to the instruction in question
     * @param blockStart {@code true} iff this instruction starts a
     * basic block
     */
    private void addWorkIfNecessary(int offset, boolean blockStart) {
        if (!mod.agus.jcoderz.dx.util.Bits.get(liveSet, offset)) {
            mod.agus.jcoderz.dx.util.Bits.set(workSet, offset);
        }

        if (blockStart) {
            mod.agus.jcoderz.dx.util.Bits.set(blockSet, offset);
        }
    }

    /**
     * Helper method used by all the visitor methods.
     *
     * @param offset offset to the instruction
     * @param length length of the instruction, in bytes
     * @param nextIsLive {@code true} iff the instruction after
     * the indicated one is possibly-live (because this one isn't an
     * unconditional branch, a return, or a switch)
     */
    private void visitCommon(int offset, int length, boolean nextIsLive) {
        mod.agus.jcoderz.dx.util.Bits.set(liveSet, offset);

        if (nextIsLive) {
            /*
             * If the next instruction is flowed to by this one, just
             * add it to the work set, and then a subsequent visit*()
             * will deal with it as appropriate.
             */
            addWorkIfNecessary(offset + length, false);
        } else {
            /*
             * If the next instruction isn't flowed to by this one,
             * then mark it as a start of a block but *don't* add it
             * to the work set, so that in the final phase we can know
             * dead code blocks as those marked as blocks but not also marked
             * live.
             */
            Bits.set(blockSet, offset + length);
        }
    }

    /**
     * Helper method used by all the visitor methods that deal with
     * opcodes that possibly throw. This method should be called after calling
     * {@link #visitCommon}.
     *
     * @param offset offset to the instruction
     * @param length length of the instruction, in bytes
     * @param nextIsLive {@code true} iff the instruction after
     * the indicated one is possibly-live (because this one isn't an
     * unconditional throw)
     */
    private void visitThrowing(int offset, int length, boolean nextIsLive) {
        int next = offset + length;

        if (nextIsLive) {
            addWorkIfNecessary(next, true);
        }

        ByteCatchList catches = method.getCatches().listFor(offset);
        catchLists[offset] = catches;
        targetLists[offset] = catches.toTargetList(nextIsLive ? next : -1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPreviousOffset(int offset) {
        previousOffset = offset;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPreviousOffset() {
        return previousOffset;
    }
}
